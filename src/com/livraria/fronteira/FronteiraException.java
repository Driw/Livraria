package com.livraria.fronteira;

@SuppressWarnings("serial")
public class FronteiraException extends Exception
{
	public FronteiraException(String message)
	{
		super(message);
	}

	public FronteiraException(String format, Object... args)
	{
		super(String.format(format, args));
	}
}
