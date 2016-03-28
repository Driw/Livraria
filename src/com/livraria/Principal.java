package com.livraria;

import com.livraria.fronteira.FronteiraInicial;

import org.diverproject.util.SystemUtil;

import com.livraria.fronteira.Fronteira;

public class Principal
{
	public static void main(String[] args)
	{
		SystemUtil.setWindowInterface();

		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraInicial.class);
		fronteira.mostrar();
	}
}
