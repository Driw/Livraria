package com.livraria.fronteira.popup;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class PopUpComboBox<E> extends JFrame
{
	private PopUpComboBoxListener<E> listener;
	private JComboBox<ComboBoxItem<E>> cbItens;

	public PopUpComboBox(JPanel fronteira)
	{
		setSize(480, 80);
		setLocationRelativeTo(fronteira);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);

		cbItens = new JComboBox<>();
		cbItens.setBounds(10, 11, 344, 30);
		cbItens.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					callSelecionar();

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER)
					setVisible(false);
			}
		});
		panel.add(cbItens);
		
		JButton btnSelecionar = new JButton("Selecionar");
		btnSelecionar.setBounds(364, 11, 100, 30);
		btnSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callSelecionar();
			}
		});
		panel.add(btnSelecionar);
	}

	public void abrir(String title)
	{
		setTitle(title);
		setVisible(true);
	}

	public void setListener(PopUpComboBoxListener<E> listener)
	{
		this.listener = listener;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void carregar(List<E> itens)
	{
		cbItens.removeAllItems();

		for (E item : itens)
			if (item instanceof ComboBoxItem)
				cbItens.addItem((ComboBoxItem) item);
	}

	@SuppressWarnings("unchecked")
	private void callSelecionar()
	{
		if (listener != null)
		{
			Object item = cbItens.getSelectedItem();
			listener.selecionado((E) item);
		}

		dispose();
	}
}
