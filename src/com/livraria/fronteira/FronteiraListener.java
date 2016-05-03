package com.livraria.fronteira;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import org.diverproject.util.MessageUtil;

import com.livraria.Conexao;
import com.livraria.controle.ControleCarrinho;
import com.livraria.entidades.Carrinho;

public class FronteiraListener implements WindowListener
{
	private static final WindowListener INSTANCIA = new FronteiraListener();

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		try {

			ControleCarrinho controleCarrinho = new ControleCarrinho();
			Carrinho carrinho = controleCarrinho.getCarrinho();
			controleCarrinho.guardar(carrinho);

		} catch (SQLException ex) {
			MessageUtil.showError("Libraria", "Não foi possível guardar seu carrinho de compras atual:\n- %s", ex.getMessage());
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		Conexao.getMySQL(); // Garantir conexão com o banco de dados
	}

	public static WindowListener getInstancia()
	{
		return INSTANCIA;
	}
}
