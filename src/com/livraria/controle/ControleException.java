package com.livraria.controle;

@SuppressWarnings("serial")
public class ControleException extends Exception
{
	public ControleException(String message)
	{
		super(message);
	}

	public ControleException(String format, Object... args)
	{
		super(String.format(format, args));
	}
}
