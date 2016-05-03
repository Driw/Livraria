package com.livraria.fronteira;

import static org.diverproject.util.MessageUtil.showError;
import static org.diverproject.util.MessageUtil.showException;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JFrame;

import com.livraria.GerarMassaDeDados;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.diverproject.util.MessageUtil;

public class Fronteira
{
	private static final int WIDTH_DIFF = 26;
	private static final int HEIGHT_DIFF = 72;
	private static final int FRONTEIRA_X = 10;
	private static final int FRONTEIRA_Y = 11;
	private static final Fronteira INSTANCIA = new Fronteira();

	public static final Font FONT = new Font("verdana", Font.PLAIN, 12);
	public static final Font FONT_TITULO = new Font("Tahoma", Font.PLAIN, 16);
	public static final Font FONT_COMPONENTES = new Font("Tahoma", Font.PLAIN, 12);

	private JFrame frame;
	private JPanel panel;

	private Fronteira()
	{
		frame = new JFrame();
		frame.setFont(FONT);
		frame.setTitle("Livraria");
		frame.setSize(720, 480);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int width = frame.getWidth() - WIDTH_DIFF;
		int height = frame.getHeight() - HEIGHT_DIFF;

		panel = new JPanel();
		panel.setBounds(FRONTEIRA_X, FRONTEIRA_Y, width, height);
		frame.getContentPane().add(panel);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnMenuPrincipal = new JMenu("Menu Principal");
		menuBar.add(mnMenuPrincipal);

		JMenuItem miGerarMassaDeDados = new JMenuItem("Gerar Massa de Dados");
		miGerarMassaDeDados.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callGerarMassaDeDados();
			}
		});
		mnMenuPrincipal.add(miGerarMassaDeDados);

		JMenuItem miPaginaInicial = new JMenuItem("Página Inicial");
		miPaginaInicial.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callPaginaInicial();
			}
		});
		mnMenuPrincipal.add(miPaginaInicial);

		JMenuItem miManterAutores = new JMenuItem("Manter Autores");
		miManterAutores.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callManterAutores();
			}
		});
		mnMenuPrincipal.add(miManterAutores);

		JMenuItem miManterEditoras = new JMenuItem("Manter Editoras");
		miManterEditoras.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callManterEditoras();
			}
		});
		mnMenuPrincipal.add(miManterEditoras);

		JMenuItem miCategorias = new JMenuItem("Manter Categorias");
		miCategorias.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callManterCategorias();
			}
		});
		mnMenuPrincipal.add(miCategorias);

		JMenuItem miLivros = new JMenuItem("Manter Livros");
		miLivros.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callManterLivros();
			}
		});
		mnMenuPrincipal.add(miLivros);

		JMenu mbCarrinho = new JMenu("Carrinho de Compras");
		menuBar.add(mbCarrinho);

		JMenuItem miPesquisarLivros = new JMenuItem("Pesquisar Livros");
		miPesquisarLivros.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callPesquisarLivros();
			}
		});
		mbCarrinho.add(miPesquisarLivros);

		JMenuItem miVisualizarCarrinho = new JMenuItem("Visualizar Carrinho");
		miVisualizarCarrinho.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVisualizarCarrinho();
			}
		});
		mbCarrinho.add(miVisualizarCarrinho);

		JMenu mnMinhaConta = new JMenu("Minha Conta");
		menuBar.add(mnMinhaConta);

		JMenu mnRelatorios = new JMenu("Relatórios");
		menuBar.add(mnRelatorios);
		frame.addWindowListener(FronteiraListener.getInstancia());
	}

	private void callGerarMassaDeDados()
	{
		GerarMassaDeDados gmd = new GerarMassaDeDados();
		gmd.truncate();

		MessageUtil.showInfo("Gerar Massa De Dados", "Todos os livros, editoras e autores antigos foram removidos.");

		gmd.gerarEditoras(30);
		gmd.gerarAutores(150);
		gmd.gerarLivros(200);

		MessageUtil.showInfo("Gerar Massa De Dados", "Massa de Dados pora teste concluído.");
	}

	private void callPaginaInicial()
	{
		setFronteira(FronteiraLogin.class);
	}

	private void callManterAutores()
	{
		setFronteira(FronteiraManterAutores.class);
	}

	private void callManterEditoras()
	{
		setFronteira(FronteiraManterEditoras.class);
	}

	private void callManterCategorias()
	{
		setFronteira(FronteiraManterCategorias.class);
	}

	private void callManterLivros()
	{
		setFronteira(FronteiraManterLivros.class);
	}

	private void callPesquisarLivros()
	{
		setFronteira(FronteiraPesquisarLivros.class);
	}

	private void callVisualizarCarrinho()
	{
		setFronteira(FronteiraCarrinhoDeCompras.class);
	}

	public void setFronteira(Class<?> classe)
	{
		try {

			Object instancia = null;

			for (Field field : classe.getDeclaredFields())
			{
				field.setAccessible(true);

				if (field.getName().equals("INSTANCE"))
					instancia = field.get(null);
			}

			if (instancia == null)
				showError("Fronteira", "%s não possui instancia", classe.getSimpleName());

			else if (instancia instanceof JPanel)
			{
				JPanel panel = (JPanel) instancia;

				prepararContainer(panel);
				usarContainer(panel);
			}

			else
				showError("Fronteira", "%s não é um JPanel", classe.getSimpleName());

		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
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

	private void usarContainer(JPanel panel)
	{
		frame.getContentPane().remove(this.panel);
		this.panel = panel;

		frame.setSize(panel.getWidth() + WIDTH_DIFF, panel.getHeight() + HEIGHT_DIFF);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel);
		panel.setLocation(FRONTEIRA_X, FRONTEIRA_Y);

		if (panel instanceof IFronteira)
		{
			IFronteira fronteira = (IFronteira) panel;
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
