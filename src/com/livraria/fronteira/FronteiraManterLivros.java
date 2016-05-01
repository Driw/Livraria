package com.livraria.fronteira;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.diverproject.util.DateUtil;
import org.diverproject.util.MessageUtil;
import org.diverproject.util.lang.FloatUtil;
import org.diverproject.util.lang.IntUtil;

import com.livraria.controle.ControleAutor;
import com.livraria.controle.ControleCategoria;
import com.livraria.controle.ControleEditora;
import com.livraria.controle.ControleLivro;
import com.livraria.entidades.Autor;
import com.livraria.entidades.Categoria;
import com.livraria.entidades.Editora;
import com.livraria.entidades.Livro;
import com.livraria.entidades.LivroAutores;
import com.livraria.entidades.LivroCategorias;
import com.livraria.fronteira.model.ModelListarAutores;
import com.livraria.fronteira.model.ModelListarCategorias;
import com.livraria.fronteira.model.ModelManterLivros;
import com.livraria.fronteira.popup.ComboBoxAutor;
import com.livraria.fronteira.popup.ComboBoxCategoria;
import com.livraria.fronteira.popup.ComboBoxEditora;
import com.livraria.fronteira.popup.ComboBoxItem;
import com.livraria.fronteira.popup.PopUpComboBox;
import com.livraria.fronteira.popup.PopUpComboBoxListener;
import com.livraria.fronteira.popup.PopUpTextArea;
import com.livraria.fronteira.popup.PopUpTextAreaListener;
import com.livraria.util.ComponentUtil;
import com.livraria.util.FronteiraException;

@SuppressWarnings("serial")
public class FronteiraManterLivros extends JPanel implements IFronteira
{
	private static final int FILTRO_ISBN = 0;
	private static final int FILTRO_TITULO = 1;
	private static final int FILTRO_RESUMO = 2;
	private static final int FILTRO_SUMARIO = 3;
	private static final int FILTRO_PRECO = 4;

	private ModelListarCategorias modelCategorias;
	private ModelListarAutores modelAutores;
	private ModelManterLivros model;
	private ControleLivro controleLivro;
	private Livro livro;

	private JFormattedTextField tfISBN;
	private JTextField tfTitulo;
	private JFormattedTextField tfPreco;
	private JFormattedTextField tfPublicacao;
	private JTextField tfPaginas;
	private JComboBox<ComboBoxItem<Editora>> cbEditoras;
	private JComboBox<String> cbCapa;

	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	private JTable tableConsulta;
	private JTextField tfFiltro;
	private JComboBox<String> cbFiltro;

	private PopUpComboBox<ComboBoxCategoria> popupCategorias;
	private PopUpComboBox<ComboBoxAutor> popupAutores;
	private PopUpTextArea popupResumo;
	private PopUpTextArea popupSumario;
	private String resumo;
	private String sumario;

	@SuppressWarnings("deprecation")
	public FronteiraManterLivros()
	{
		controleLivro = new ControleLivro();

		setSize(820, 550);
		setLayout(null);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Livro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDados.setBounds(0, 0, 820, 331);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblISBN = new JLabel("ISBN :");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblISBN.setFont(Fronteira.FONT_COMPONENTES);
		lblISBN.setBounds(10, 11, 150, 25);
		panelDados.add(lblISBN);

		tfISBN = new JFormattedTextField();
		tfISBN.setFont(Fronteira.FONT_COMPONENTES);
		tfISBN.setBounds(170, 12, 480, 25);
		tfISBN.setToolTipText("ISBN é um código internacional de identificação do livro, cada livro possui um diferente.");
		ComponentUtil.setIsbnMask(tfISBN);
		panelDados.add(tfISBN);

		JLabel lblTitulo = new JLabel("Título :");
		lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitulo.setFont(Fronteira.FONT_COMPONENTES);
		lblTitulo.setBounds(10, 47, 150, 25);
		panelDados.add(lblTitulo);

		tfTitulo = new JTextField();
		tfTitulo.setBounds(170, 48, 480, 25);
		tfTitulo.setToolTipText("Título do livro ou nome é usado para facilitar a busca pelo livro, como indica do que se trata.");
		ComponentUtil.maxLength(tfTitulo, 48);
		panelDados.add(tfTitulo);

		JLabel lblPreco = new JLabel("Preço :");
		lblPreco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreco.setFont(Fronteira.FONT_COMPONENTES);
		lblPreco.setBounds(10, 84, 150, 25);
		panelDados.add(lblPreco);

		tfPreco = new JFormattedTextField();
		tfPreco.setFont(Fronteira.FONT_COMPONENTES);
		tfPreco.setBounds(170, 85, 100, 25);
		tfPreco.setToolTipText("Por quantos reais o livro estará sendo vendido na livraria virtual.");
		ComponentUtil.setValueMask(tfPreco);
		panelDados.add(tfPreco);

		JLabel lblPublicacao = new JLabel("Publicação :");
		lblPublicacao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPublicacao.setFont(Fronteira.FONT_COMPONENTES);
		lblPublicacao.setBounds(450, 84, 80, 25);
		panelDados.add(lblPublicacao);

		tfPublicacao = new JFormattedTextField();
		tfPublicacao.setFont(Fronteira.FONT_COMPONENTES);
		tfPublicacao.setBounds(540, 84, 110, 25);
		tfPublicacao.setToolTipText("Data em que o livro foi oficialmente publicado, indica o quão antigo é.");
		ComponentUtil.setDataMask(tfPublicacao);
		panelDados.add(tfPublicacao);

		JLabel lblPaginas = new JLabel("Páginas :");
		lblPaginas.setFont(Fronteira.FONT_COMPONENTES);
		lblPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPaginas.setBounds(280, 84, 70, 25);
		panelDados.add(lblPaginas);

		tfPaginas = new JTextField();
		tfPaginas.setFont(Fronteira.FONT_COMPONENTES);
		tfPaginas.setBounds(360, 84, 80, 25);
		tfPaginas.addKeyListener(ComponentUtil.keyListenerNumerico());
		tfPaginas.addKeyListener(ComponentUtil.maxLength(tfPaginas, 4));
		tfPaginas.setToolTipText("O número de páginas ajuda os clientes a saber o dimensionamento de conteúdo no livro.");
		panelDados.add(tfPaginas);
		
		JLabel lblEditora = new JLabel("Editora :");
		lblEditora.setFont(Fronteira.FONT_COMPONENTES);
		lblEditora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEditora.setBounds(10, 120, 150, 25);
		panelDados.add(lblEditora);

		cbEditoras = new JComboBox<ComboBoxItem<Editora>>();
		cbEditoras.setFont(Fronteira.FONT_COMPONENTES);
		cbEditoras.setBounds(170, 121, 150, 25);
		cbEditoras.setToolTipText("Todo livro deve possuir uma editora válida e registrada no sistema.");
		callCarregarEditoras();
		panelDados.add(cbEditoras);

		JLabel lblCapa = new JLabel("Capa :");
		lblCapa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCapa.setFont(Fronteira.FONT_COMPONENTES);
		lblCapa.setBounds(340, 120, 150, 25);
		panelDados.add(lblCapa);

		cbCapa = new JComboBox<String>();
		cbCapa.setFont(Fronteira.FONT_COMPONENTES);
		cbCapa.setBounds(500, 121, 150, 25);
		cbCapa.addItem("Capa Dura");
		cbCapa.addItem("Brochura");
		cbCapa.setToolTipText("O tipo de capa é apenas uma informação adicional.");
		panelDados.add(cbCapa);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 309);
		panelDadosAcoes.setLayout(new GridLayout(5, 1, 0, 5));
		panelDadosAcoes.setToolTipText("As ações abaixos serão aplicas apenas em relação aos dados preenchidos nos campos.");
		panelDados.add(panelDadosAcoes);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(Fronteira.FONT_COMPONENTES);
		btnAdicionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarLivro();
			}
		});
		btnAdicionar.setToolTipText("Adicionar irá registrar um novo livro com as informações ao lado.");
		panelDadosAcoes.add(btnAdicionar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarLivro();
			}
		});
		btnAtualizar.setToolTipText("Atualizar só irá funcionar se houver um livro selecionado da cosulta.");
		panelDadosAcoes.add(btnAtualizar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirLivro();
			}
		});
		btnExcluir.setToolTipText("Essa opção de excluir só irá funcionar se houver um lvrio selecionado da consulta.");
		panelDadosAcoes.add(btnExcluir);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(Fronteira.FONT_COMPONENTES);
		btnLimpar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callLimparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpa todos os campos relacionado aos dados do livro e desseleciona o livro se houver um selecionado.");
		panelDadosAcoes.add(btnLimpar);

		JPanel panelConteudo = new JPanel();
		panelConteudo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conteúdo Adicional", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelConteudo.setLayout(new GridLayout(4, 1, 0, 5));
		panelConteudo.setBounds(10, 156, 150, 164);
		panelDados.add(panelConteudo);

		JButton btnResumo = new JButton("Resumo");
		btnResumo.setFont(Fronteira.FONT_COMPONENTES);
		btnResumo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAbrirResumo();
			}
		});
		btnResumo.setToolTipText("Abre uma janela que permite digitar de forma mais ampla o resumo do livro.");
		panelConteudo.add(btnResumo);

		JButton btnSumario = new JButton("Sumário");
		btnSumario.setFont(Fronteira.FONT_COMPONENTES);
		btnSumario.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAbrirSumario();
			}
		});
		btnSumario.setToolTipText("Abre uma janela que permite digitar de forma mais ampla o sumário do livro.");
		panelConteudo.add(btnSumario);
		
		JButton btnAdicionarAutor = new JButton("Adicionar Autor");
		btnAdicionarAutor.setFont(Fronteira.FONT_COMPONENTES);
		btnAdicionarAutor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarAutor();
			}
		});
		btnAdicionarAutor.setToolTipText("Abre um PopUp que permite selecionar o autor que deseja adicionar.");
		panelConteudo.add(btnAdicionarAutor);

		JButton btnAdicionarCategoria = new JButton("Adicionar Categoria");
		btnAdicionarCategoria.setFont(Fronteira.FONT_COMPONENTES);
		btnAdicionarCategoria.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarCategoria();
			}
		});
		btnAdicionarCategoria.setToolTipText("Abre um PopUp que permite selecionar o sumário que deseja adicionar.");
		panelConteudo.add(btnAdicionarCategoria);

		JPanel panelAutores = new JPanel();
		panelAutores.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Autores", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAutores.setBounds(170, 157, 230, 163);
		panelAutores.setLayout(null);
		panelDados.add(panelAutores);

		modelAutores = new ModelListarAutores();

		JTable tableAutores = new JTable();
		tableAutores.setModel(modelAutores);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAutores.setRowSelectionAllowed(true);
		tableAutores.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					callRemoverAutor(tableAutores.getSelectedRow());
			}
		});

		JScrollPane scrollPaneAutores = new JScrollPane(tableAutores);
		scrollPaneAutores.setBounds(10, 16, 210, 136);
		panelAutores.add(scrollPaneAutores);

		JPanel panelCategorias = new JPanel();
		panelCategorias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Categorias", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCategorias.setBounds(420, 156, 230, 164);
		panelCategorias.setLayout(null);
		panelDados.add(panelCategorias);

		modelCategorias = new ModelListarCategorias();

		JTable tableCategorias = new JTable();
		tableCategorias.setModel(modelCategorias);
		tableCategorias.setFont(Fronteira.FONT_COMPONENTES);
		tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCategorias.setRowSelectionAllowed(true);
		tableCategorias.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					callRemoverCategoria(tableCategorias.getSelectedRow());
			}
		});

		JScrollPane scrollPaneCategorias = new JScrollPane(tableCategorias);
		scrollPaneCategorias.setBounds(10, 17, 210, 136);
		panelCategorias.add(scrollPaneCategorias);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 342, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consultar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(0, 365, 820, 186);
		panelConsulta.setLayout(null);
		add(panelConsulta);

		JLabel lblFiltro = new JLabel("Filtro :");
		lblFiltro.setFont(Fronteira.FONT_COMPONENTES);
		lblFiltro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltro.setBounds(10, 21, 80, 25);
		panelConsulta.add(lblFiltro);

		tfFiltro = new JTextField();
		tfFiltro.setFont(Fronteira.FONT_COMPONENTES);
		tfFiltro.setBounds(100, 22, 395, 25);
		tfFiltro.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				callFiltrar(tfFiltro.getText(), cbFiltro.getSelectedIndex());
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
				if (cbFiltro.getSelectedIndex() == FILTRO_PRECO)
					if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ' ')
						e.consume();
			}
		});
		panelConsulta.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setFont(Fronteira.FONT_COMPONENTES);
		cbFiltro.setBounds(505, 22, 145, 25);
		cbFiltro.addItem("ISBN");
		cbFiltro.addItem("Título");
		cbFiltro.addItem("Resumo");
		cbFiltro.addItem("Sumário");
		cbFiltro.addItem("Preço");
		cbFiltro.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tfFiltro.setText("");
			}
		});
		panelConsulta.add(cbFiltro);

		model = new ModelManterLivros();

		tableConsulta = new JTable();
		tableConsulta.setModel(model);
		tableConsulta.setFont(Fronteira.FONT_COMPONENTES);
		tableConsulta.getColumnModel().getColumn(0).setResizable(false);
		tableConsulta.getColumnModel().getColumn(0).setMinWidth(120);
		tableConsulta.getColumnModel().getColumn(0).setMaxWidth(120);
		tableConsulta.getColumnModel().getColumn(1).setResizable(false);
		tableConsulta.getColumnModel().getColumn(1).setMinWidth(230);
		tableConsulta.getColumnModel().getColumn(1).setMaxWidth(230);
		tableConsulta.getColumnModel().getColumn(2).setResizable(false);
		tableConsulta.getColumnModel().getColumn(2).setMinWidth(80);
		tableConsulta.getColumnModel().getColumn(2).setMaxWidth(80);
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(60);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(60);
		tableConsulta.getColumnModel().getColumn(4).setResizable(false);
		tableConsulta.getColumnModel().getColumn(4).setMinWidth(150);
		tableConsulta.getColumnModel().getColumn(4).setMaxWidth(150);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(true);
		tableConsulta.setToolTipText("Tabela contendo todas os livros encontrados de acordo com a filtragem escolhida.");

		JScrollPane scrollPaneConsulta = new JScrollPane(tableConsulta);
		scrollPaneConsulta.setBounds(10, 57, 640, 118);
		panelConsulta.add(scrollPaneConsulta);

		JPanel panelConsultaAcoes = new JPanel();
		panelConsultaAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsultaAcoes.setBounds(660, 11, 150, 167);
		panelConsulta.add(panelConsultaAcoes);
		panelConsultaAcoes.setLayout(new GridLayout(4, 1, 0, 5));

		JButton btnVerTodos = new JButton("Ver Todos");
		btnVerTodos.setFont(Fronteira.FONT_COMPONENTES);
		btnVerTodos.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVerTodos();
			}
		});
		btnVerTodos.setToolTipText("Essa opção irá buscar todos os livros existentes no banco dedados.");
		panelConsultaAcoes.add(btnVerTodos);

		JButton btnConsultaSelecionar = new JButton("Selecionar");
		btnConsultaSelecionar.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callSelecionarLivro();
			}
		});
		btnConsultaSelecionar.setToolTipText("Puxa os dados do livro selecionado na consulta para os campos, permitindo atualizar ou excluir.");
		panelConsultaAcoes.add(btnConsultaSelecionar);

		JButton btnConsultaExcluir = new JButton("Excluir");
		btnConsultaExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirLivroDireto();
			}
		});
		btnConsultaExcluir.setToolTipText("Permite excluir um livro que esteja selecionado na tabela de consulta.");
		panelConsultaAcoes.add(btnConsultaExcluir);

		tfISBN.setNextFocusableComponent(tfTitulo);
		tfTitulo.setNextFocusableComponent(tfPreco);
		tfPreco.setNextFocusableComponent(tfPaginas);
		tfPaginas.setNextFocusableComponent(tfPublicacao);
		tfPublicacao.setNextFocusableComponent(cbCapa);
		cbCapa.setNextFocusableComponent(btnResumo);
		btnResumo.setNextFocusableComponent(btnSumario);
		btnSumario.setNextFocusableComponent(btnAdicionarAutor);
		btnAdicionarAutor.setNextFocusableComponent(btnAdicionarCategoria);
		btnAdicionarCategoria.setNextFocusableComponent(btnAdicionar);
		btnAdicionar.setNextFocusableComponent(btnAtualizar);
		btnAtualizar.setNextFocusableComponent(btnExcluir);
		btnExcluir.setNextFocusableComponent(tfISBN);
	}

	@Override
	public String getTitle()
	{
		return "Manter Livros";
	}

	private Livro criarLivro() throws FronteiraException
	{
		String isbn = tfISBN.getText();

		if (isbn.equals("   - -  -      - ") || isbn.equals("000-0-00-000000-0"))
			throw new FronteiraException("ISBN: em branco.");

		if (tfTitulo.getText().length() == 0)
			throw new FronteiraException("Título: em branco");

		if (tfTitulo.getText().length() < 3)
			throw new FronteiraException("Título: mínimo de 3 caracteres.");

		if (tfTitulo.getText().length() > 48)
			throw new FronteiraException("Título: limite de 48 caracteres.");

		// TODO VALIDAR ISBN

		Date publicacao;

		if (tfPublicacao.getText().equals("  /  /    ") || tfPublicacao.getText().equals("00/00/0000"))
			throw new FronteiraException("Data de Publicaçao: campo obrigatório");
		else
			try {
				publicacao = DateUtil.toDate(tfPublicacao.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Publicaçao: inválida");
			}

		if (cbEditoras.getSelectedIndex() == 0)
			throw new FronteiraException("Editora: Selecione uma editora.");

		if (tfPreco.getText().equals("R$    ,  ") || tfPreco.getText().equals("R$ 000.00"))
			throw new FronteiraException("Preço: defina um valor ao livro.");

		if (tfPaginas.getText().length() == 0)
			throw new FronteiraException("Número de Paginas: defina um valor.");

		if (modelAutores.getRowCount() == 0)
			throw new FronteiraException("Autores: selecione ao menos um autor.");

		if (modelCategorias.getRowCount() == 0)
			throw new FronteiraException("Categorias: selecione ao menos uma categoria.");

		Livro livro = new Livro();
		livro.setTitulo(tfTitulo.getText());
		livro.setIsbn(isbn);
		livro.setPreco(FloatUtil.parse(tfPreco.getText().substring(3)));
		livro.setPublicacao(publicacao);
		livro.setPaginas(IntUtil.parse(tfPaginas.getText()));
		livro.setEditora(((ComboBoxEditora) cbEditoras.getSelectedItem()).getElement());
		livro.setCapa(cbCapa.getSelectedIndex());
		livro.setResumo(resumo);
		livro.setSumario(sumario);

		LivroAutores autores = livro.getLivroAutores();
		LivroCategorias categorias = livro.getLivroCategorias();

		autores.limpar();
		categorias.limpar();

		for (int linha = 0; linha < modelAutores.getRowCount(); linha++)
			autores.adicionar(modelAutores.getLinha(linha));

		for (int linha = 0; linha < modelCategorias.getRowCount(); linha++)
			categorias.adicionar(modelCategorias.getLinha(linha));

		return livro;
	}

	private void callLimparCampos()
	{
		tfISBN.setText("");
		tfTitulo.setText("");
		tfPreco.setText("");
		tfPaginas.setText("");
		tfPublicacao.setText("");
		cbEditoras.setSelectedIndex(0);
		cbCapa.setSelectedIndex(0);

		modelAutores.atualizarLista(new ArrayList<>());
		modelCategorias.atualizarLista(new ArrayList<>());

		livro = null;

		btnAdicionar.setEnabled(true);
		btnAtualizar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

	private void callAdicionarLivro()
	{
		try {

			livro = criarLivro();

			if (controleLivro.existe(livro.getTitulo()))
				if (!MessageUtil.showYesNo("Adicionar Livro", "Livro '%s' já existe, adicionar mesmo assim?", livro.getTitulo()))
					return;

			if (controleLivro.adicionar(livro))
			{
				MessageUtil.showInfo("Adicionar Livro", "Livro '%s' adicionado com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Adicionar Livro", "NÃ£o foi possível adicionar o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Livro", "Falha ao adicionar o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Livro", "Verifique o campo abaixo:\n- %s", e.getMessage());
		}
	}
	
	private void callAtualizarLivro()
	{
		if (livro == null)
		{
			MessageUtil.showInfo("AtualizarLivro", "Selecione um livro na consulta antes.");
			return;
		}

		try {

			Livro livroConsultado = livro;

			livro = criarLivro();
			livro.setID(livroConsultado.getID());

			if (controleLivro.atualizar(livro))
			{
				livroConsultado.copiar(livro);

				MessageUtil.showInfo("Atualizar Livro", "Livro '%s' atualizado com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Livro", "NÃ£o foi possÃ­vel atualizar o livro'%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Livro", "Falha ao atualizar a livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		} catch (FronteiraException e) {
			e.printStackTrace();
		}
	}
	
	private void callExcluirLivro()
	{
		if (livro == null)
		{
			MessageUtil.showInfo("Excluir Livro", "Selecione um livro na consulta antes");
			return;
		}

		try {

			if (controleLivro.excluir(livro.getID()))
			{
				model.remover(livro);

				MessageUtil.showInfo("Excluir Livro", "Livro '%s' excluído com exito!", livro.getTitulo());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Livro", "Não foi possível excluir o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Livro", "Falha ao excluir o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		}
	}
	
	private void callFiltrar(String filtro, int filtroTipo)
	{
		try {

			List<Livro> livros = null;

			switch (filtroTipo)
			{
				case FILTRO_ISBN:
					livros = controleLivro.filtrarPorISBN(filtro);
					break;

				case FILTRO_TITULO:
					livros = controleLivro.filtrarPorTitulo(filtro);
					break;

				case FILTRO_RESUMO:
					livros = controleLivro.filtrarPorResumo(filtro);
					break;
				case FILTRO_SUMARIO:
					livros = controleLivro.filtrarPorSumario(filtro);
					break;

				case FILTRO_PRECO:
					String precos[] = filtro.split(" ");
					float minimo = 0.0f;
					float maximo = 0.0f;

					if (precos.length == 2)
					{
						minimo = FloatUtil.parse(precos[0]);
						maximo = FloatUtil.parse(precos[1]);
					}

					livros = controleLivro.filtrarPorPreco(minimo, maximo);
					break;
			}

			model.atualizarLista(livros);

		} catch (SQLException e) {
			MessageUtil.showError("Filtrar Livros", "Falha ao filtrar Livros.\n- %s", e.getMessage());
		}
	}

	private void callVerTodos()
	{
		try {

			List<Livro> livros = controleLivro.listar();
			model.atualizarLista(livros);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Todos", "Falha ao listar livros.\n- %s", e.getMessage());
		}
	}

	private void callSelecionarLivro()
	{
		livro = model.getLinha(tableConsulta.getSelectedRow());

		try {

			controleLivro.carregarAutores(livro);
			controleLivro.carregarCategorias(livro);

		} catch (SQLException e) {

			MessageUtil.showError("Selecionar Livro", "Não foi possível carregar os autores e/ou categorias:\n- %s", e.getMessage());
			livro = null;

			return;
		}

		tfISBN.setText(livro.getIsbn());
		tfTitulo.setText(livro.getTitulo());
		tfPreco.setText(String.format(Locale.US, "R$ %3.2f", livro.getPreco()));
		tfPublicacao.setText(DateUtil.toString(livro.getPublicacao()));
		tfPaginas.setText(String.valueOf(livro.getPaginas()));
		cbCapa.setSelectedIndex(livro.getCapa());

		List<Autor> autores = new ArrayList<Autor>();
		List<Categoria> categorias = new ArrayList<Categoria>();

		for (Autor autor : livro.getLivroAutores().listar())
			autores.add(autor);

		for (Categoria categoria : livro.getLivroCategorias().listar())
			categorias.add(categoria);

		modelAutores.atualizarLista(autores);
		modelCategorias.atualizarLista(categorias);

		cbEditoras.setSelectedIndex(0);

		for (int i = 0; i < cbEditoras.getItemCount(); i++)
			if (cbEditoras.getItemAt(i).getElement().getID() == livro.getEditora().getID())
			{
				cbEditoras.setSelectedIndex(i);
				break;
			}

		resumo = livro.getResumo();
		sumario = livro.getSumario();

		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}
	
	private void callExcluirLivroDireto()
	{
		try {

			Livro livro = model.getLinha(tableConsulta.getSelectedRow());

			if (controleLivro.excluir(livro.getID()))
			{
				model.removerLinha(tableConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Livro", "Livro '%s' excluído com êxito!", livro.getTitulo());
			}

			else
				MessageUtil.showWarning("Excluir Livro", "Não foi possível excluir o livro '%s'.", livro.getTitulo());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Livro", "Falha ao excluir o livro '%s'.\n- %s", livro.getTitulo(), e.getMessage());
		}
	}

	private void callAbrirResumo()
	{
		if (popupResumo == null)
		{
			popupResumo = new PopUpTextArea(this);
			popupResumo.setListener(new PopUpTextAreaListener()
			{
				@Override
				public void salvar(JTextArea textArea)
				{
					resumo = textArea.getText();
				}
				
				@Override
				public void apagar()
				{
					resumo = "";
				}
			});
		}

		popupResumo.setTexto(resumo);
		popupResumo.abrir("Resumo");
	}

	private void callAbrirSumario()
	{
		if (popupSumario == null)
		{
			popupSumario = new PopUpTextArea(this);
			popupSumario.setListener(new PopUpTextAreaListener()
			{
				@Override
				public void salvar(JTextArea textArea)
				{
					sumario = textArea.getText();
				}
				
				@Override
				public void apagar()
				{
					sumario = "";
				}
			});
		}

		popupSumario.setTexto(sumario);
		popupSumario.abrir("Sumario");
	}

	private void callAdicionarAutor()
	{
		if (popupAutores == null)
		{
			List<ComboBoxAutor> itens = new ArrayList<>();

			try {

				ControleAutor controleAutor = new ControleAutor();
				List<Autor> autores = controleAutor.listar();

				for (Autor autor : autores)
				{
					ComboBoxAutor item = new ComboBoxAutor(autor);
					itens.add(item);
				}

			} catch (SQLException e) {
				MessageUtil.showWarning("Adicionar Autor", "Falha ao listar autores:\n- %s", e.getMessage());
			}

			popupAutores = new PopUpComboBox<ComboBoxAutor>(this);
			popupAutores.carregar(itens);
			popupAutores.setListener(new PopUpComboBoxListener<ComboBoxAutor>()
			{
				@Override
				public void selecionado(ComboBoxAutor item)
				{
					Autor autor = item.getElement();
					modelAutores.adicionar(autor);
				}
			});
		}

		popupAutores.abrir("Selecione um Autor:");
	}

	private void callRemoverAutor(int linha)
	{
		modelAutores.apagar(linha);
		modelAutores.fireTableDataChanged();
	}

	private void callAdicionarCategoria()
	{
		if (popupCategorias == null)
		{
			List<ComboBoxCategoria> itens = new ArrayList<>();

			try {

				ControleCategoria controleCategoria = new ControleCategoria();
				List<Categoria> categorias = controleCategoria.listar();

				for (Categoria categoria : categorias)
				{
					ComboBoxCategoria item = new ComboBoxCategoria(categoria);
					itens.add(item);
				}

			} catch (SQLException e) {
				MessageUtil.showWarning("Adicionar Categoria", "Falha ao listar categorias:\n- %s", e.getMessage());
			}

			popupCategorias = new PopUpComboBox<ComboBoxCategoria>(this);
			popupCategorias.carregar(itens);
			popupCategorias.setListener(new PopUpComboBoxListener<ComboBoxCategoria>()
			{
				@Override
				public void selecionado(ComboBoxCategoria item)
				{
					Categoria categoria = item.getElement();
					modelCategorias.adicionar(categoria);
				}
			});
		}

		popupCategorias.abrir("Selecione uma Categoria:");
	}

	private void callRemoverCategoria(int linha)
	{
		modelCategorias.apagar(linha);
		modelCategorias.fireTableDataChanged();
	}

	private void callCarregarEditoras()
	{
		ControleEditora controleEditora = new ControleEditora();

		cbEditoras.removeAllItems();

		Editora editoraNula = new Editora();
		editoraNula.setNome("-");

		cbEditoras.addItem(new ComboBoxEditora(editoraNula));

		try {

			List<Editora> editoras = controleEditora.listar();

			for (Editora editora : editoras)
			{
				ComboBoxItem<Editora> item = new ComboBoxEditora(editora);
				cbEditoras.addItem(item);
			}

		} catch (SQLException e) {
			MessageUtil.die(e);
		}
	}
}
