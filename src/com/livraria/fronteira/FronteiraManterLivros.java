package com.livraria.fronteira;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class FronteiraManterLivros extends Container implements IFronteira
{
	private JTable tableConsulta;
	private JTextField tfFiltro;
	private JTextField tfISBN;
	private JTextField tfTitulo;
	private JTextField tfPreco;
	private JTextField tfPublicacao;
	private JTextField tfPaginas;
	private JComboBox<String> cbCapa;
	private JTextField tfPrecoCusto;
	private JTextField tfMargemLucro;

	public FronteiraManterLivros()
	{
		setSize(850, 620);

		JLabel lblGerenciarAutores = new JLabel("GERENCIAR LIVROS");
		lblGerenciarAutores.setHorizontalAlignment(SwingConstants.CENTER);
		lblGerenciarAutores.setFont(Fronteira.FONT_TITULO);
		lblGerenciarAutores.setBounds(20, 11, 795, 33);
		add(lblGerenciarAutores);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Livro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDados.setBounds(20, 55, 820, 331);
		panelDados.setLayout(null);
		add(panelDados);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 309);
		panelDados.add(panelDadosAcoes);
		panelDadosAcoes.setLayout(new GridLayout(5, 1, 0, 5));

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(Fronteira.FONT_COMPONENTES);
		panelDadosAcoes.add(btnLimpar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(Fronteira.FONT_COMPONENTES);
		panelDadosAcoes.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		panelDadosAcoes.add(btnAtualizar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		panelDadosAcoes.add(btnExcluir);

		JLabel lblISBN = new JLabel("ISBN :");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblISBN.setFont(Fronteira.FONT_COMPONENTES);
		lblISBN.setBounds(10, 11, 150, 25);
		panelDados.add(lblISBN);

		tfISBN = new JTextField();
		tfISBN.setBounds(170, 12, 480, 25);
		panelDados.add(tfISBN);

		JLabel lblTitulo = new JLabel("Título :");
		lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTitulo.setBounds(10, 47, 150, 25);
		panelDados.add(lblTitulo);

		tfTitulo = new JTextField();
		tfTitulo.setBounds(170, 48, 480, 25);
		panelDados.add(tfTitulo);

		JLabel lblPreco = new JLabel("Preço :");
		lblPreco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPreco.setBounds(10, 84, 150, 25);
		panelDados.add(lblPreco);

		tfPreco = new JTextField();
		tfPreco.setBounds(170, 85, 150, 25);
		panelDados.add(tfPreco);

		JLabel lblPublicacao = new JLabel("Publicação :");
		lblPublicacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPublicacao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPublicacao.setBounds(340, 84, 150, 25);
		panelDados.add(lblPublicacao);

		tfPublicacao = new JTextField();
		tfPublicacao.setBounds(500, 84, 150, 25);
		panelDados.add(tfPublicacao);

		JLabel lblPaginas = new JLabel("Páginas :");
		lblPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaginas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPaginas.setBounds(10, 120, 150, 25);
		panelDados.add(lblPaginas);

		tfPaginas = new JTextField();
		tfPaginas.setBounds(170, 121, 150, 25);
		panelDados.add(tfPaginas);

		JLabel lblCapa = new JLabel("Capa :");
		lblCapa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCapa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCapa.setBounds(340, 120, 150, 25);
		panelDados.add(lblCapa);

		cbCapa = new JComboBox<String>();
		cbCapa.setBounds(500, 121, 150, 25);
		cbCapa.addItem("Capa Dura");
		cbCapa.addItem("Brochura");
		panelDados.add(cbCapa);

		JLabel lblPrecoCusto = new JLabel("Preço de Custo :");
		lblPrecoCusto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecoCusto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrecoCusto.setBounds(10, 157, 150, 25);
		panelDados.add(lblPrecoCusto);

		tfPrecoCusto = new JTextField();
		tfPrecoCusto.setBounds(170, 158, 150, 25);
		panelDados.add(tfPrecoCusto);

		JLabel lblMargemLucro = new JLabel("Margem de Lucro :");
		lblMargemLucro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMargemLucro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMargemLucro.setBounds(340, 157, 150, 25);
		panelDados.add(lblMargemLucro);

		tfMargemLucro = new JTextField();
		tfMargemLucro.setBounds(500, 158, 150, 25);
		panelDados.add(tfMargemLucro);

		JPanel panelConteudo = new JPanel();
		panelConteudo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conteúdo Adicional", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelConteudo.setBounds(10, 193, 150, 127);
		panelDados.add(panelConteudo);
		panelConteudo.setLayout(new GridLayout(4, 1, 0, 5));

		JButton btnResumo = new JButton("Resumo");
		panelConteudo.add(btnResumo);

		JButton btnSumario = new JButton("Sumário");
		panelConteudo.add(btnSumario);
		
		JButton btnAdicionarAutor = new JButton("Adicionar Autor");
		panelConteudo.add(btnAdicionarAutor);
		
		JButton btnAdicionarCategoria = new JButton("Adicionar Categoria");
		panelConteudo.add(btnAdicionarCategoria);

		JPanel panelAutores = new JPanel();
		panelAutores.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Autores", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAutores.setBounds(170, 194, 230, 126);
		panelAutores.setLayout(null);
		panelDados.add(panelAutores);

		ModelListarAutores modelAutores = new ModelListarAutores();

		JTable tableAutores = new JTable();
		tableAutores.setModel(modelAutores);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAutores.setRowSelectionAllowed(false);

		JScrollPane scrollPaneAutores = new JScrollPane(tableAutores);
		scrollPaneAutores.setBounds(10, 16, 210, 99);
		panelAutores.add(scrollPaneAutores);

		JPanel panelCategorias = new JPanel();
		panelCategorias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Categorias", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCategorias.setBounds(420, 193, 230, 127);
		panelCategorias.setLayout(null);
		panelDados.add(panelCategorias);

		ModelListarCategorias modelCategorias = new ModelListarCategorias();

		JTable tableCategorias = new JTable();
		tableCategorias.setModel(modelCategorias);
		tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCategorias.setRowSelectionAllowed(false);

		JScrollPane scrollPaneCategorias = new JScrollPane(tableCategorias);
		scrollPaneCategorias.setBounds(10, 17, 210, 99);
		panelCategorias.add(scrollPaneCategorias);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 397, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consultar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(20, 412, 820, 186);
		panelConsulta.setLayout(null);
		add(panelConsulta);

		JLabel lblFiltro = new JLabel("Filtro :");
		lblFiltro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(10, 21, 80, 25);
		panelConsulta.add(lblFiltro);

		tfFiltro = new JTextField();
		tfFiltro.setBounds(100, 22, 395, 25);
		panelConsulta.add(tfFiltro);

		JComboBox<String> cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(505, 22, 145, 25);
		cbFiltro.addItem("ISBN");
		cbFiltro.addItem("Título");
		cbFiltro.addItem("Resumo");
		cbFiltro.addItem("Sumário");
		cbFiltro.addItem("Preço");
		panelConsulta.add(cbFiltro);

		ModelManterLivros model = new ModelManterLivros();

		tableConsulta = new JTable();
		tableConsulta.setModel(model);
		tableConsulta.getColumnModel().getColumn(0).setResizable(false);
		tableConsulta.getColumnModel().getColumn(0).setMinWidth(100);
		tableConsulta.getColumnModel().getColumn(0).setMaxWidth(100);
		tableConsulta.getColumnModel().getColumn(1).setResizable(false);
		tableConsulta.getColumnModel().getColumn(1).setMinWidth(160);
		tableConsulta.getColumnModel().getColumn(1).setMaxWidth(160);
		tableConsulta.getColumnModel().getColumn(2).setResizable(false);
		tableConsulta.getColumnModel().getColumn(2).setMinWidth(70);
		tableConsulta.getColumnModel().getColumn(2).setMaxWidth(70);
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(50);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(50);
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(150);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(150);
		tableConsulta.getColumnModel().getColumn(4).setResizable(false);
		tableConsulta.getColumnModel().getColumn(4).setMinWidth(110);
		tableConsulta.getColumnModel().getColumn(4).setMaxWidth(110);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(false);

		JScrollPane scrollPaneConsulta = new JScrollPane(tableConsulta);
		scrollPaneConsulta.setBounds(10, 57, 640, 118);
		panelConsulta.add(scrollPaneConsulta);

		JPanel panelConsultaAcoes = new JPanel();
		panelConsultaAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsultaAcoes.setBounds(660, 11, 150, 167);
		panelConsulta.add(panelConsultaAcoes);
		panelConsultaAcoes.setLayout(new GridLayout(4, 1, 0, 5));

		JButton btnConsultaSelecionar = new JButton("Selecionar");
		btnConsultaSelecionar.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaSelecionar);

		JButton btnConsultaExcluir = new JButton("Excluir");
		btnConsultaExcluir.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaExcluir);

		JButton btnConsultaVerAutores = new JButton("Ver Autores");
		btnConsultaVerAutores.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaVerAutores);
	}

	@Override
	public String getTitle()
	{
		return "Manter Autores";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
