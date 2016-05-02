package com.livraria.fronteira;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FronteiraCarrinhoDeCompras extends JPanel
{
	private static final JPanel INSTANCE = new FronteiraCarrinhoDeCompras();

	public FronteiraCarrinhoDeCompras()
	{
		setSize(720, 480);
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
