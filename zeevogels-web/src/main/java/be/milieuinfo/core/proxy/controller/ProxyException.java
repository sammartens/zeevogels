package be.milieuinfo.core.proxy.controller;

public class ProxyException extends RuntimeException {

	private static final long serialVersionUID = -2952209049700902215L;

	public ProxyException() {
		super();
	}

	public ProxyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ProxyException(String arg0) {
		super(arg0);
	}

	public ProxyException(Throwable arg0) {
		super(arg0);
	}

}
