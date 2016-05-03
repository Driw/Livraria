package com.livraria.fronteira;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.diverproject.util.MessageUtil;
import org.diverproject.util.lang.IntUtil;

import com.livraria.controle.ControleCarrinho;
import com.livraria.controle.ControlePesquisa;
import com.livraria.entidades.Carrinho;
import com.livraria.entidades.Livro;
import com.livraria.fronteira.model.ModelPesquisaResultados;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class FronteiraPesquisarLivros extends JPanel
{
	private static final JPanel INSTANCE = new FronteiraPesquisarLivros();

	private static final int FILTRO_TITULO = 0;
	private static final int FILTRO_AUTOR = 1;
	private static final int FILTRO_EDITORA = 2;
	private static final int FILTRO_CATEGORIA = 3;

	private static final int MAX_FILTRO = 50;

	private ModelPesquisaResultados model;
	private ControleCarrinho controleCarrinho;

	private JTextField tfFiltro;
	private JComboBox<String> cbFiltro;
	private JTable tableResultados;

	private FronteiraPesquisarLivros()
	{
		controleCarrinho = new ControleCarrinho();

		setSize(820, 470);
		setLayout(null);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pesquisar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPesquisa.setBounds(0, 0, 820, 403);
		panelPesquisa.setLayout(null);
		add(panelPesquisa);

		JLabel lblFiltrar = new JLabel("Filtrar :");
		lblFiltrar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltrar.setBounds(10, 24, 150, 25);
		panelPesquisa.add(lblFiltrar);

		tfFiltro = new JTextField();
		tfFiltro.setBounds(170, 24, 480, 25);
		tfFiltro.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				try {

					callFiltrarPesquisa(tfFiltro.getText(), cbFiltro.getSelectedIndex());

				} catch (SQLException exception) {
					MessageUtil.showException(exception);
				}
			}
		});
		tfFiltro.setFont(Fronteira.FONT_COMPONENTES);
		panelPesquisa.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(660, 23, 150, 25);
		cbFiltro.addItem("Título");
		cbFiltro.addItem("Autor");
		cbFiltro.addItem("Editora");
		cbFiltro.addItem("Categoria");
		cbFiltro.setFont(Fronteira.FONT_COMPONENTES);
		panelPesquisa.add(cbFiltro);

		model = new ModelPesquisaResultados();

		tableResultados = new JTable();
		tableResultados.setModel(model);
		tableResultados.getColumnModel().getColumn(0).setResizable(false);
		tableResultados.getColumnModel().getColumn(0).setMinWidth(110);
		tableResultados.getColumnModel().getColumn(0).setMaxWidth(110);
		tableResultados.getColumnModel().getColumn(1).setResizable(false);
		tableResultados.getColumnModel().getColumn(1).setMinWidth(200);
		tableResultados.getColumnModel().getColumn(1).setMaxWidth(200);
		tableResultados.getColumnModel().getColumn(2).setResizable(false);
		tableResultados.getColumnModel().getColumn(2).setMinWidth(60);
		tableResultados.getColumnModel().getColumn(2).setMaxWidth(60);
		tableResultados.getColumnModel().getColumn(3).setResizable(false);
		tableResultados.getColumnModel().getColumn(3).setMinWidth(60);
		tableResultados.getColumnModel().getColumn(3).setMaxWidth(60);
		tableResultados.getColumnModel().getColumn(4).setResizable(false);
		tableResultados.getColumnModel().getColumn(4).setMinWidth(120);
		tableResultados.getColumnModel().getColumn(4).setMaxWidth(120);
		tableResultados.getColumnModel().getColumn(5).setResizable(false);
		tableResultados.getColumnModel().getColumn(5).setMinWidth(260);
		tableResultados.getColumnModel().getColumn(5).setMaxWidth(260);
		tableResultados.setFont(Fronteira.FONT_COMPONENTES);

		JScrollPane spResultados = new JScrollPane(tableResultados);
		spResultados.setBounds(10, 59, 800, 333);
		panelPesquisa.add(spResultados);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new GridLayout(0, 4, 10, 0));
		panel.setBounds(0, 414, 820, 60);
		add(panel);

		JButton btnAdicionarAoCarrinho = new JButton("Adicionar ao Carrinho");
		btnAdicionarAoCarrinho.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarAoCarrinho();
			}
		});
		btnAdicionarAoCarrinho.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnAdicionarAoCarrinho);

		JButton btnVisualizarDetalhes = new JButton("VisualizarDetalhes");
		btnVisualizarDetalhes.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVisualizarDetalhes();
			}
		});
		btnVisualizarDetalhes.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnVisualizarDetalhes);

		JButton btnAbrirCarrinho = new JButton("Abrir Carrinho");
		btnAbrirCarrinho.setFont(Fronteira.FONT_COMPONENTES);
		btnAbrirCarrinho.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAbrirCarrinho();
			}
		});
		panel.add(btnAbrirCarrinho);

		JButton btnFinalizarPesquisa = new JButton("Finalizar Pesquisa");
		btnFinalizarPesquisa.setEnabled(false);
		btnFinalizarPesquisa.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callFinalizarPesquisa();
			}
		});
		btnFinalizarPesquisa.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnFinalizarPesquisa);
	}

	private void callFiltrarPesquisa(String filtro, int tipoFiltro) throws SQLException
	{
		List<Livro> livros = null;
		ControlePesquisa controlePesquisa = new ControlePesquisa();

		switch (tipoFiltro)
		{
			case FILTRO_TITULO:
				livros = controlePesquisa.pesquisarPorTitulo(filtro, MAX_FILTRO);
				break;

			case FILTRO_AUTOR:
				livros = controlePesquisa.pesquisarPorAutor(filtro, MAX_FILTRO);
				break;

			case FILTRO_EDITORA:
				livros = controlePesquisa.pesquisarPorEditora(filtro, MAX_FILTRO);
				break;

			case FILTRO_CATEGORIA:
				livros = controlePesquisa.pesquisarPorCategoria(filtro, MAX_FILTRO);
				break;
		}

		if (livros != null)
			model.atualizarLista(livros);
	}

	private void callAdicionarAoCarrinho()
	{
		Livro livro = model.getLinha(tableResultados.getSelectedRow());

		if (livro == null)
		{
			MessageUtil.showInfo("Adicionar ao Carrinho", "Nenhum livro foi selecionado.");
			return;
		}

		String input = MessageUtil.showInput("Adicionar ao Carrinho:", "Quantos livros '%s' você deseja:", livro.getTitulo());

		if (input == null || input.isEmpty())
			return;

		int quantidade = IntUtil.parse(input, 0);

		if (quantidade < 0)
			MessageUtil.showWarning("Adicionar ao Carrinho", "Insira um valor numérico positivo (quantidade: %d).", quantidade);

		else if (quantidade == 0)
			MessageUtil.showWarning("Adicionar ao Carrinho", "Insira uma valor numérico (quantidade: %s)", input);

		else
			try {


				Carrinho carrinho = controleCarrinho.getCarrinho();
				carrinho.adicionar(quantidade, livro);

				MessageUtil.showInfo("Adicionar ao Carrinho", "Adicionado %dx '%s' ao carrinho.", quantidade, livro.getTitulo());

			} catch (SQLException e) {
				MessageUtil.showError("Adicionar ao Carrinho", "Falha ao adicionar %dx '%s' ao carrinho:\n- %s", quantidade, livro.getTitulo(), e.getMessage());
			}
	}

	private void callVisualizarDetalhes()
	{
		Livro livro = model.getLinha(tableResultados.getSelectedRow());

		JPanel jPanel = FronteiraVisualizarDetalhes.getInstance();
		FronteiraVisualizarDetalhes visualizarDetalhes = (FronteiraVisualizarDetalhes) jPanel;
		visualizarDetalhes.setVolarPara(FronteiraPesquisarLivros.class);
		visualizarDetalhes.carregar(livro);

		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraVisualizarDetalhes.class);
	}

	private void callAbrirCarrinho()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraCarrinhoDeCompras.class);
	}

	private void callFinalizarPesquisa()
	{
//		MessageUtil.showInfo("Finalizar Pesquisa", "Funcionalidade da Sprint 2");
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
