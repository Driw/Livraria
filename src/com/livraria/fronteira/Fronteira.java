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
	private static final Font FONT = new Font("verdana", Font.PLAIN, 12);

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

		container.setFont(FONT);
	}

	private void usarContainer(Container container)
	{
		frame.setContentPane(container);
		frame.setSize(container.getWidth() + 16, container.getHeight() + 38);
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
