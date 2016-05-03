package com.livraria;

import org.diverproject.util.SystemUtil;

import com.livraria.fronteira.Fronteira;
import com.livraria.fronteira.FronteiraPesquisarLivros;

public class Principal
{
	public static void main(String[] args)
	{
		SystemUtil.setWindowInterface();

		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraPesquisarLivros.class);
		fronteira.mostrar();
	}
}
