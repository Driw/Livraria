package com.livraria.fronteira;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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

import org.diverproject.util.DateUtil;
import org.diverproject.util.MessageUtil;

import com.livraria.controle.ControleLivro;
import com.livraria.entidades.Livro;
import com.livraria.util.ComponentUtil;
import com.livraria.util.FronteiraException;

@SuppressWarnings("serial")
public class FronteiraManterLivros extends JPanel implements IFronteira
{
	private static final int FILTRO_TITULO = 0;
	private static final int FILTRO_ISBN = 1;
	private static final int FILTRO_RESUMO = 2;
	private static final int FILTRO_SUMARIO = 3;
	private static final int FILTRO_PRECO = 4;
	private JTable tableConsulta;
	private JTextField tfFiltro;
	private JTextField tfISBN;
	private JTextField tfTitulo;
	private JFormattedTextField tfPreco;
	private JFormattedTextField tfPublicacao;
	private JTextField tfPaginas;
	private JComboBox<String> cbCapa;
	private JFormattedTextField tfPrecoCusto;
	private JTextField tfMargemLucro;
	private Livro livro;
	private ControleLivro controleLivro = new ControleLivro();
	private ModelManterLivros model = new ModelManterLivros();
	
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

		tfPreco = new JFormattedTextField();
		ComponentUtil.setValueMask(tfPreco);
		tfPreco.setBounds(170, 85, 150, 25);
		panelDados.add(tfPreco);

		JLabel lblPublicacao = new JLabel("Publicação :");
		lblPublicacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPublicacao.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPublicacao.setBounds(340, 84, 150, 25);
		panelDados.add(lblPublicacao);

		tfPublicacao = new JFormattedTextField();
		ComponentUtil.setDataMask(tfPublicacao);
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

		tfPrecoCusto = new JFormattedTextField();
		ComponentUtil.setValueMask(tfPrecoCusto);
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
		
		btnAdicionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarLivro();
			}
		});
		
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarLivro();
			}
		});
		
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirLivro();
			}
		});
		
		btnLimpar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callLimparCampos();
			}
		});
		
		btnResumo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callAdicionarResumo();
			}
		});
		
		btnSumario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callAdicionarSumario();
			}
		});
		
		btnAdicionarAutor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callAdicionarAutor();
			}
		});
		
		btnAdicionarCategoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				callAdicionarCategoria();
			}
		});
		
		btnConsultaSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callConsultaSelecionar();
			}
		});
		
		btnConsultaExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callConsultaExcluir();
			}
		});
		
		btnConsultaVerAutores.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callConsultaVerAutores();
			}
		});
	}

	@Override
	public String getTitle()
	{
		return "Manter Livros";
	}
	
	private Livro criarLivro() throws FronteiraException
	{
		if (tfTitulo.getText().length() < 3)
			throw new FronteiraException("Nome: nome muito curto");

		Date publicacao;

		if (tfPublicacao.getText().equals("__/__/____"))
			publicacao = null;
		else
			try {
				publicacao = DateUtil.toDate(tfPublicacao.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Publicação: inválida");
			}
		
		String isbn = tfISBN.getText().length() == 0 ? null : tfISBN.getText();
		int paginas = tfPaginas.getText().length() == 0 ? null : Integer.parseInt(tfPaginas.getText());
		float preco = tfPreco.getText().length() == 0 ? null : Float.parseFloat(tfPreco.getText());
		
		
		Livro livro = new Livro();
		livro.setTitulo(tfTitulo.getText());
		livro.setIsbn(isbn);
		livro.setPreco(preco);
		livro.setPublicacao(publicacao);
		livro.setPaginas(paginas);
		livro.setCapa(cbCapa.getSelectedIndex());

		return livro;
	}
	
	private void callAdicionarLivro()
	{
		try {
			livro = criarLivro();

			if (controleLivro.adicionar(livro))
			{
				MessageUtil.showInfo("Adicionar Livro", "Livro '%s' adicionado com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Adicionar Livro", "Não foi possível adicionar o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Livro", "Falha ao adicionar o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Livro", "Verifique o campo abaixo:\n- %s", livro.getTitulo(), e.getMessage());
		}
	}
	
	private void callAtualizarLivro()
	{
		try {

			livro = criarLivro();

			if (controleLivro.atualizar(livro))
			{
				MessageUtil.showInfo("Atualizar Livro", "Livro '%s' atualizado com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Livro", "Não foi possível atualizar o livro'%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Livro", "Falha ao atualizar a livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		} catch (FronteiraException e) {
			e.printStackTrace();
		}
	}
	
	private void callExcluirLivro()
	{
		try {

			if (controleLivro.excluir(livro.getID()))
			{
				MessageUtil.showInfo("Excluir Livro", "Livro '%s' excluído com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Livro", "Não foi possível excluir o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Livro", "Falha ao excluir o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		}
	}
	
	private void callConsultaSelecionar()
	{
		livro = model.getLinha(tableConsulta.getSelectedRow());
		tfTitulo.setText(livro.getTitulo());
		tfISBN.setText(livro.getIsbn());
		tfPreco.setText(String.valueOf(livro.getPreco()));
		tfPublicacao.setText(livro.getPublicacao().toString());
		tfPaginas.setText(String.valueOf(livro.getPaginas()));
		cbCapa.setSelectedIndex(livro.getCapa());
	}
	
	private void callConsultaExcluir()
	{
		try {

			Livro livro = model.getLinha(tableConsulta.getSelectedRow());

			if (controleLivro.excluir(livro.getID()))
			{
				model.removerLinha(tableConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Livro", "Livro '%s' exclu�do com �xito!", livro.getTitulo());
			}

			else
				MessageUtil.showWarning("Excluir Livro", "Não foi possível excluir o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Livro", "Falha ao excluir o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		}
	}
	
	private void callFiltro(String filtro, int filtroTipo)
	{
		try {
			List<Livro> livros = null;

			switch (filtroTipo)
			{
				case FILTRO_TITULO:
					livros = controleLivro.filtrarPorTitulo(filtro);
					break;

				case FILTRO_ISBN:
					livros = controleLivro.filtrarPorISBN(filtro);
					break;

				case FILTRO_RESUMO:
					livros = controleLivro.filtrarPorResumo(filtro);
					break;
				case FILTRO_SUMARIO:
					livros = controleLivro.filtrarPorSumario(filtro);
					break;

				case FILTRO_PRECO:
//					livros = controleLivro.filtrarPorPreco(null, null);
					break;
			}

			model.atualizarLista(livros);

		} catch (SQLException e) {
			MessageUtil.showError("Filtrar Livros", "Falha ao filtrar Livros.\n- %s", e.getMessage());
		}
	}
	
	private void callLimparCampos()
	{
		tfTitulo.setText("");
		tfISBN.setText("");
		tfFiltro.setText("");
		tfPreco.setText("");
		tfPublicacao.setText("");
		tfPaginas.setText("");
		tfPrecoCusto.setText("");
		tfMargemLucro.setText("");
		cbCapa.setSelectedIndex(0);
		livro = null;
		
		livro = null;
	}
}
