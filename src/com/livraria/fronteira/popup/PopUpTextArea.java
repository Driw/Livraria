package com.livraria.fronteira.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.livraria.fronteira.Fronteira;

import java.awt.GridLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PopUpTextArea extends JFrame
{
	private PopUpTextAreaListener listener;
	private JTextArea textArea;

	public PopUpTextArea(JPanel fronteira)
	{
		setSize(480, 320);
		setLocationRelativeTo(fronteira);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);

		textArea = new JTextArea();
		textArea.setFont(Fronteira.FONT_COMPONENTES);
		textArea.setTabSize(4);
		textArea.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					callSalvar();
					setVisible(false);
				}
			}
		});
		panel.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 11, 444, 218);
		panel.add(scrollPane);

		JPanel panelAcoes = new JPanel();
		panelAcoes.setLayout(new GridLayout(0, 3, 10, 0));
		panelAcoes.setBounds(10, 240, 444, 30);
		panel.add(panelAcoes);

		JFrame self = this;

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setFont(Fronteira.FONT_COMPONENTES);
		btnFechar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				self.setVisible(false);
			}
		});
		panelAcoes.add(btnFechar);

		JButton btnApagar = new JButton("Apagar");
		btnApagar.setFont(Fronteira.FONT_COMPONENTES);
		btnApagar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callApagar();
			}
		});
		panelAcoes.add(btnApagar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(Fronteira.FONT_COMPONENTES);
		btnSalvar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callSalvar();
			}
		});
		panelAcoes.add(btnSalvar);
	}

	public void abrir(String title)
	{
		setTitle(title);
		setVisible(true);
	}

	public void setTexto(String text)
	{
		textArea.setText(text);
	}

	public void setListener(PopUpTextAreaListener listener)
	{
		this.listener = listener;
	}

	private void callApagar()
	{
		if (listener != null)
			listener.apagar();

		textArea.setText("");
	}

	private void callSalvar()
	{
		if (listener != null)
			listener.salvar(textArea);

		setVisible(false);
	}
}
