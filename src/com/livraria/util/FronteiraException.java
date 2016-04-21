package com.livraria.util;

public class FronteiraException extends Exception {

	private static final long serialVersionUID = 1L;	
	
	public FronteiraException() {
	}

	public FronteiraException(String arg0) {
		super(arg0);
	}

	public FronteiraException(Throwable arg0) {
		super(arg0);
	}

	public FronteiraException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FronteiraException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
