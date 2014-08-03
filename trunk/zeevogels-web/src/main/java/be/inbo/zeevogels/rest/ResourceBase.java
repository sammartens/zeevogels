package be.inbo.zeevogels.rest;

import static be.inbo.zeevogels.rest.util.ResponseWrapper.wrapIt;

import javax.ws.rs.core.Response;

public abstract class ResourceBase {

	protected static final String DEFAULT_PAGE_SIZE = "10";

	protected Response buildSuccessfulResponse() {
		return Response.ok(wrapIt(null, true)).build();
	}

	protected Response buildSuccessfulResponse(Object data) {
		return buildSuccessfulResponse(data, null);
	}

	protected Response buildSuccessfulResponse(Object data, Long total) {
		return Response.ok(wrapIt(data, true, total)).build();
	}

	protected Response buildFailureResponse() {
		return Response.ok(wrapIt(null, false)).build();
	}

	protected Response buildFailureResponse(String message) {
		return Response.ok(wrapIt(null, false, message)).build();
	}
}
