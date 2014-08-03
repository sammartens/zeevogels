package be.milieuinfo.core.proxy.controller;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URI;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ProxyController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	protected HttpClient proxyClient;
	protected boolean handleRedirects = true;
	protected Map<String, URI> proxyMapping;
	protected int maxConnections = 30;
	protected int maxConnectionsPerRoute = 10;

	@PostConstruct
	public void init() {

		HttpParams hcParams = new BasicHttpParams();
		hcParams.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, handleRedirects);
		PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
		pccm.setMaxTotal(maxConnectionsPerRoute);
		pccm.setDefaultMaxPerRoute(maxConnectionsPerRoute);
		proxyClient = new SystemDefaultHttpClient(hcParams);
	}

	@PreDestroy
	public void destroy() {
		// shutdown() must be called according to documentation.
		if (proxyClient != null) {
			proxyClient.getConnectionManager().shutdown();
		}
	}

	@RequestMapping(value = { "/{service}", "/{service}/**/*" })
	@SuppressWarnings("deprecation")
	protected void service(@PathVariable String service, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws ServletException, IOException {
		URI targetUri = getTargetUri(service);
		if (targetUri != null) {
			// Make the Request
			// note: we won't transfer the protocol version because I'm not sure it would truly be compatible
			String method = servletRequest.getMethod();
			String proxyRequestUri = rewriteUrlFromRequest(servletRequest, targetUri, service);
			HttpRequest proxyRequest;
			// spec: RFC 2616, sec 4.3: either these two headers signal that there is a message body.
			if (servletRequest.getHeader(HttpHeaders.CONTENT_LENGTH) != null
					|| servletRequest.getHeader(HttpHeaders.TRANSFER_ENCODING) != null) {
				HttpEntityEnclosingRequest eProxyRequest = new BasicHttpEntityEnclosingRequest(method, proxyRequestUri);
				// Add the input entity (streamed)
				// note: we don't bother ensuring we close the servletInputStream since the container handles it
				eProxyRequest.setEntity(new InputStreamEntity(servletRequest.getInputStream(), servletRequest
						.getContentLength()));
				proxyRequest = eProxyRequest;
			} else
				proxyRequest = new BasicHttpRequest(method, proxyRequestUri);

			copyRequestHeaders(servletRequest, proxyRequest, targetUri);

			try {
				// Execute the request
				if (log.isTraceEnabled()) {
					log.trace("proxy " + method + " uri: " + servletRequest.getRequestURI() + " -- "
							+ proxyRequest.getRequestLine().getUri());
				}
				HttpResponse proxyResponse = proxyClient.execute(URIUtils.extractHost(targetUri), proxyRequest);

				// Process the response
				int statusCode = proxyResponse.getStatusLine().getStatusCode();

				if (doResponseRedirectOrNotModifiedLogic(servletRequest, servletResponse, targetUri, proxyResponse,
						statusCode)) {
					// just to be sure, but is probably a no-op
					EntityUtils.consume(proxyResponse.getEntity());
					return;
				}

				// Pass the response code. This method with the "reason phrase" is deprecated but it's the only way to
				// pass the
				// reason along too.
				// noinspection deprecation
				servletResponse.setStatus(statusCode, proxyResponse.getStatusLine().getReasonPhrase());

				copyResponseHeaders(proxyResponse, servletResponse);

				// Send the content to the client
				copyResponseEntity(proxyResponse, servletResponse);

			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("Something went wrong proxying the request " + servletRequest.getRequestURL() + "?"
							+ servletRequest.getQueryString());
				}
				// abort request, according to best practice with HttpClient
				if (proxyRequest instanceof AbortableHttpRequest) {
					AbortableHttpRequest abortableHttpRequest = (AbortableHttpRequest) proxyRequest;
					abortableHttpRequest.abort();
				}

				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				} else if (e instanceof ServletException) {
					throw (ServletException) e;
				} else if (e instanceof IOException) {
					if (e.getCause() != null && e.getCause() instanceof SocketException) {
						// The client probably aborted(canceled) the (TCP) connection so we couldn't write(flush) the
						// proxy response to the servlet response.
					} else {
						throw (IOException) e;
					}
				} else {
					throw new RuntimeException(e);
				}
			}
		} else {
			throw new ProxyException("Failed to proxy the request '" + servletRequest.getRequestURL() + "?"
					+ servletRequest.getQueryString() + "'. No targetUri in the proxyMapping is declared for service '"
					+ service + "'");
		}
	}

	@ExceptionHandler(ProxyException.class)
	@ResponseStatus(NOT_IMPLEMENTED)
	@ResponseBody
	protected String handleProxyException(ProxyException e) {
		log.error(e.getMessage(), e);
		return e.getMessage();
	}

	private URI getTargetUri(String service) {
		return proxyMapping.get(service);
	}

	private boolean doResponseRedirectOrNotModifiedLogic(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, URI targetUri, HttpResponse proxyResponse, int statusCode)
			throws ServletException, IOException {
		// Check if the proxy response is a redirect
		// The following code is adapted from org.tigris.noodle.filters.CheckForRedirect
		if (statusCode >= HttpServletResponse.SC_MULTIPLE_CHOICES /* 300 */
				&& statusCode < HttpServletResponse.SC_NOT_MODIFIED /* 304 */) {
			Header locationHeader = proxyResponse.getLastHeader(HttpHeaders.LOCATION);
			if (locationHeader == null) {
				throw new ServletException("Received status code: " + statusCode + " but no " + HttpHeaders.LOCATION
						+ " header was found in the response");
			}
			// Modify the redirect to go to this proxy servlet rather that the proxied host
			String locStr = rewriteUrlFromResponse(servletRequest, targetUri, locationHeader.getValue());

			servletResponse.sendRedirect(locStr);
			return true;
		}
		// 304 needs special handling. See:
		// http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
		// We get a 304 whenever passed an 'If-Modified-Since'
		// header and the data on disk has not changed; server
		// responds w/ a 304 saying I'm not going to send the
		// body because the file has not changed.
		if (statusCode == HttpServletResponse.SC_NOT_MODIFIED) {
			servletResponse.setIntHeader(HttpHeaders.CONTENT_LENGTH, 0);
			servletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return true;
		}
		return false;
	}

	protected void closeQuietly(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * These are the "hop-by-hop" headers that should not be copied.
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html I use an HttpClient HeaderGroup class instead of
	 * Set<String> because this approach does case insensitive lookup faster.
	 */
	private static final HeaderGroup hopByHopHeaders;
	static {
		hopByHopHeaders = new HeaderGroup();
		String[] headers = new String[] { "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
				"TE", "Trailers", "Transfer-Encoding", "Upgrade" };
		for (String header : headers) {
			hopByHopHeaders.addHeader(new BasicHeader(header, null));
		}
	}

	/** Copy request headers from the servlet client to the proxy request. */
	protected void copyRequestHeaders(HttpServletRequest servletRequest, HttpRequest proxyRequest, URI targetUri) {
		// Get an Enumeration of all of the header names sent by the client
		Enumeration<?> enumerationOfHeaderNames = servletRequest.getHeaderNames();
		while (enumerationOfHeaderNames.hasMoreElements()) {
			String headerName = (String) enumerationOfHeaderNames.nextElement();
			// Instead the content-length is effectively set via InputStreamEntity
			if (headerName.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH))
				continue;
			if (hopByHopHeaders.containsHeader(headerName))
				continue;
			// As per the Java Servlet API 2.5 documentation:
			// Some headers, such as Accept-Language can be sent by clients
			// as several headers each with a different value rather than
			// sending the header as a comma separated list.
			// Thus, we get an Enumeration of the header values sent by the client
			Enumeration<?> headers = servletRequest.getHeaders(headerName);
			while (headers.hasMoreElements()) {
				String headerValue = (String) headers.nextElement();
				// In case the proxy host is running multiple virtual servers,
				// rewrite the Host header to ensure that we get content from
				// the correct virtual server
				if (headerName.equalsIgnoreCase(HttpHeaders.HOST)) {
					HttpHost host = URIUtils.extractHost(targetUri);
					headerValue = host.getHostName();
					if (host.getPort() != -1)
						headerValue += ":" + host.getPort();
				}
				proxyRequest.addHeader(headerName, headerValue);
			}
		}
	}

	/** Copy proxied response headers back to the servlet client. */
	protected void copyResponseHeaders(HttpResponse proxyResponse, HttpServletResponse servletResponse) {
		for (Header header : proxyResponse.getAllHeaders()) {
			if (hopByHopHeaders.containsHeader(header.getName()))
				continue;
			servletResponse.addHeader(header.getName(), header.getValue());
		}
	}

	/** Copy response body data (the entity) from the proxy to the servlet client. */
	private void copyResponseEntity(HttpResponse proxyResponse, HttpServletResponse servletResponse) throws IOException {
		HttpEntity entity = proxyResponse.getEntity();
		if (entity != null) {
			OutputStream servletOutputStream = servletResponse.getOutputStream();
			try {
				entity.writeTo(servletOutputStream);
			} finally {
				closeQuietly(servletOutputStream);
			}
		}
	}

	private String rewriteUrlFromRequest(HttpServletRequest servletRequest, URI targetUri, String service) {
		StringBuilder uri = new StringBuilder(500);
		uri.append(targetUri.toString());
		// Handle the path given to the servlet
		if (servletRequest.getPathInfo() != null) {// ex: /my/path.html
			uri.append(getProxyServicePathInfo(servletRequest, service));
		}
		// Handle the query string
		String queryString = servletRequest.getQueryString();// ex:(following '?'): name=value&foo=bar#fragment
		if (queryString != null && queryString.length() > 0) {
			uri.append('?');
			int fragIdx = queryString.indexOf('#');
			String queryNoFrag = (fragIdx < 0 ? queryString : queryString.substring(0, fragIdx));
			uri.append(encodeUriQuery(queryNoFrag));
			if (fragIdx >= 0) {
				uri.append('#');
				uri.append(encodeUriQuery(queryString.substring(fragIdx + 1)));
			}
		}
		return uri.toString();
	}

	private String rewriteUrlFromResponse(HttpServletRequest servletRequest, URI targetUri, String theUrl) {
		// TODO document example paths
		if (theUrl.startsWith(targetUri.toString())) {
			String curUrl = servletRequest.getRequestURL().toString();// no query
			String pathInfo = servletRequest.getPathInfo();
			if (pathInfo != null) {
				assert curUrl.endsWith(pathInfo);
				curUrl = curUrl.substring(0, curUrl.length() - pathInfo.length());// take pathInfo off
			}
			theUrl = curUrl + theUrl.substring(targetUri.toString().length());
		}
		return theUrl;
	}

	/**
	 * Stripping the service request from the original requests path info.
	 * 
	 * @param servletRequest
	 * @param service
	 * @return
	 */
	private String getProxyServicePathInfo(HttpServletRequest servletRequest, String service) {
		String serviceRequestPath = "/" + service + "/";
		String pathInfo = servletRequest.getPathInfo();
		if (pathInfo.startsWith(serviceRequestPath)) {
			// return pathInfo.substring(service.length() + 1);
			String requestURI = servletRequest.getRequestURI();
			return requestURI.substring(requestURI.indexOf(serviceRequestPath) + service.length() + 1);
		}
		return pathInfo;
	}

	/**
	 * <p>
	 * Encodes characters in the query or fragment part of the URI.
	 * 
	 * <p>
	 * Unfortunately, an incoming URI sometimes has characters disallowed by the spec. HttpClient insists that the
	 * outgoing proxied request has a valid URI because it uses Java's {@link URI}. To be more forgiving, we must escape
	 * the problematic characters. See the URI class for the spec.
	 * 
	 * @param in example: name=value&foo=bar#fragment
	 */
	static CharSequence encodeUriQuery(CharSequence in) {
		// Note that I can't simply use URI.java to encode because it will escape pre-existing escaped things.
		StringBuilder outBuf = null;
		Formatter formatter = null;
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			boolean escape = true;
			if (c < 128) {
				if (asciiQueryChars.get(c)) {
					escape = false;
				}
			} else if (!Character.isISOControl(c) && !Character.isSpaceChar(c)) {// not-ascii
				escape = false;
			}
			if (!escape) {
				if (outBuf != null)
					outBuf.append(c);
			} else {
				// escape
				if (outBuf == null) {
					outBuf = new StringBuilder(in.length() + 5 * 3);
					outBuf.append(in, 0, i);
					formatter = new Formatter(outBuf);
				}
				// leading %, 0 padded, width 2, capital hex
				formatter.format("%%%02X", (int) c);// TODO
			}
		}
		return outBuf != null ? outBuf : in;
	}

	static final BitSet asciiQueryChars;
	static {
		char[] c_unreserved = "_-!.~'()*".toCharArray();// plus alphanum
		char[] c_punct = ",:$&+=".toCharArray();
		char[] c_reserved = "?/[]@".toCharArray();// plus punct

		asciiQueryChars = new BitSet(128);
		for (char c = 'a'; c <= 'z'; c++)
			asciiQueryChars.set(c);
		for (char c = 'A'; c <= 'Z'; c++)
			asciiQueryChars.set(c);
		for (char c = '0'; c <= '9'; c++)
			asciiQueryChars.set(c);
		for (char c : c_unreserved)
			asciiQueryChars.set(c);
		for (char c : c_punct)
			asciiQueryChars.set(c);
		for (char c : c_reserved)
			asciiQueryChars.set(c);

		asciiQueryChars.set('%');// leave existing percent escapes in place
	}

	public void setHandleRedirects(boolean handleRedirects) {
		this.handleRedirects = handleRedirects;
	}

	public void setProxyMapping(Map<String, URI> proxyMapping) {
		this.proxyMapping = proxyMapping;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
	}

}