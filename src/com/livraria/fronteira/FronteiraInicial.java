package com.livraria.fronteira;

import java.awt.Container;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class FronteiraInicial extends Container
{
	public FronteiraInicial()
	{
		setSize(720, 480);
		setLayout(null);

		JPanel panelManter = new JPanel();
		panelManter.setBorder(new TitledBorder(null, "Gerenciar Livros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelManter.setBounds(10, 11, 240, 150);
		add(panelManter);
		panelManter.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnManterLivros = new JButton("Manter Livros");
		btnManterLivros.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manterLivros();
			}
		});
		panelManter.add(btnManterLivros);

		JButton btnManterAutores = new JButton("Manter Autores");
		btnManterAutores.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manterAutores();
			}
		});
		panelManter.add(btnManterAutores);

		JButton btnManterEditoras = new JButton("Manter Editoras");
		btnManterEditoras.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manterEditoras();
			}
		});
		panelManter.add(btnManterEditoras);
	}

	private void manterLivros()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraManterLivros.class);
	}

	private void manterAutores()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraManterAutores.class);
	}

	private void manterEditoras()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraManterEditoras.class);
	}
}
