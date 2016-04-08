package com.livraria.fronteira;

import static org.diverproject.util.MessageUtil.showError;
import static org.diverproject.util.MessageUtil.showException;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;

import com.livraria.JanelaListener;

public class Fronteira
{
	private static final Fronteira INSTANCIA = new Fronteira();
	public static final Font FONT = new Font("verdana", Font.PLAIN, 12);
	public static final Font FONT_TITULO = new Font("Tahoma", Font.PLAIN, 16);
	public static final Font FONT_COMPONENTES = new Font("Tahoma", Font.PLAIN, 12);

	private JFrame frame;

	private Fronteira()
	{
		frame = new JFrame();
		frame.setFont(FONT);
		frame.setTitle("Livraria");
		frame.setSize(720, 480);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(JanelaListener.getInstancia());
	}

	public void setFronteira(Class<?> classe)
	{
		try {

			Object instancia = classe.newInstance();

			if (instancia instanceof Container)
			{
				Container container = (Container) instancia;

				prepararContainer(container);
				usarContainer(container);
			}

			else
				showError("Fronteira", "%s não é válido", classe.getSimpleName());

		} catch (InstantiationException | IllegalAccessException e) {
			showException(e);
		}
	}

	private void prepararContainer(Container container)
	{
		for (Component component : container.getComponents())
			if (component instanceof Container)
				prepararContainer((Container) component);

		if (container.getFont() == null)
			container.setFont(FONT);
	}

	private void usarContainer(Container container)
	{
		frame.setContentPane(container);
		frame.setSize(container.getWidth() + 8, container.getHeight() + 32);

		if (container instanceof IFronteira)
		{
			IFronteira fronteira = (IFronteira) container;
			frame.setLocationRelativeTo(null);
			frame.setTitle(fronteira.getTitle());
		}

		frame.repaint();
	}

	public void mostrar()
	{
		frame.setVisible(true);
	}

	public void fechar()
	{
		frame.setVisible(false);
		frame.dispose();
		frame = null;

		System.exit(0);
	}

	public static Fronteira getInstancia()
	{
		return INSTANCIA;
	}
}
