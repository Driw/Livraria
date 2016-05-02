package com.livraria.fronteira;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.diverproject.util.DateUtil;

import com.livraria.entidades.Autor;
import com.livraria.entidades.Categoria;
import com.livraria.entidades.Livro;
import com.livraria.fronteira.model.ModelListarAutores;
import com.livraria.fronteira.model.ModelListarCategorias;
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class FronteiraVisualizarDetalhes extends JPanel implements IFronteira
{
	private static final JPanel INSTANCE = new FronteiraVisualizarDetalhes();

	private Class<?> voltarPara;

	private ModelListarCategorias modelCategorias;
	private ModelListarAutores modelAutores;

	private JFormattedTextField tfISBN;
	private JTextField tfTitulo;
	private JFormattedTextField tfPreco;
	private JFormattedTextField tfPublicacao;
	private JTextField tfPaginas;
	private JTextField tfEditora;
	private JComboBox<String> cbCapa;
	private JTextArea taResumo;
	private JTextArea taSumario;

	public FronteiraVisualizarDetalhes()
	{
		setSize(660, 550);
		setLayout(null);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Livro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDados.setBounds(0, 0, 660, 550);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblISBN = new JLabel("ISBN :");
		lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblISBN.setFont(Fronteira.FONT_COMPONENTES);
		lblISBN.setBounds(10, 11, 150, 25);
		panelDados.add(lblISBN);

		tfISBN = new JFormattedTextField();
		tfISBN.setEditable(false);
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
		tfTitulo.setEditable(false);
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
		tfPreco.setEditable(false);
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
		tfPublicacao.setEditable(false);
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
		tfPaginas.setEditable(false);
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

		tfEditora = new JTextField();
		tfEditora.setEditable(false);
		tfEditora.setFont(Fronteira.FONT_COMPONENTES);
		tfEditora.setBounds(170, 121, 150, 25);
		tfEditora.setToolTipText("Todo livro deve possuir uma editora válida e registrada no sistema.");
		panelDados.add(tfEditora);

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
		cbCapa.setEnabled(false);
		panelDados.add(cbCapa);

		JPanel panelReusmo = new JPanel();
		panelReusmo.setBorder(new TitledBorder(null, "Resumo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelReusmo.setBounds(10, 156, 315, 209);
		panelDados.add(panelReusmo);
		panelReusmo.setLayout(new GridLayout(1, 1, 0, 0));

		taResumo = new JTextArea();
		taResumo.setEditable(false);
		taResumo.setFont(Fronteira.FONT_COMPONENTES);
		taResumo.setTabSize(4);

		JScrollPane spResumo = new JScrollPane(taResumo);
		panelReusmo.add(spResumo);

		JPanel panelSumario = new JPanel();
		panelSumario.setBorder(new TitledBorder(null, "Sumário", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSumario.setBounds(335, 156, 315, 209);
		panelDados.add(panelSumario);
		panelSumario.setLayout(new GridLayout(1, 1, 0, 0));

		taSumario = new JTextArea();
		taSumario.setEditable(false);
		taSumario.setFont(Fronteira.FONT_COMPONENTES);
		taSumario.setTabSize(4);

		JScrollPane spSumario = new JScrollPane(taSumario);
		panelSumario.add(spSumario);

		JPanel panelAutores = new JPanel();
		panelAutores.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Autores", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAutores.setBounds(10, 376, 315, 163);
		panelAutores.setLayout(null);
		panelDados.add(panelAutores);

		modelAutores = new ModelListarAutores();

		JTable tableAutores = new JTable();
		tableAutores.setModel(modelAutores);
		tableAutores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAutores.setRowSelectionAllowed(true);

		JScrollPane scrollPaneAutores = new JScrollPane(tableAutores);
		scrollPaneAutores.setBounds(10, 16, 295, 136);
		panelAutores.add(scrollPaneAutores);

		JPanel panelCategorias = new JPanel();
		panelCategorias.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Categorias", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCategorias.setBounds(335, 376, 315, 164);
		panelCategorias.setLayout(null);
		panelDados.add(panelCategorias);

		modelCategorias = new ModelListarCategorias();

		JTable tableCategorias = new JTable();
		tableCategorias.setModel(modelCategorias);
		tableCategorias.setFont(Fronteira.FONT_COMPONENTES);
		tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCategorias.setRowSelectionAllowed(true);

		JScrollPane scrollPaneCategorias = new JScrollPane(tableCategorias);
		scrollPaneCategorias.setBounds(10, 17, 290, 136);
		panelCategorias.add(scrollPaneCategorias);

		requestFocus();

		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					callVoltarPara();
			}
		});
	}

	@Override
	public String getTitle()
	{
		return "Visualizar Detalhes";
	}

	public void setVolarPara(Class<?> classe)
	{
		voltarPara = classe;
	}

	public void carregar(Livro livro)
	{
		tfISBN.setText(livro.getIsbn());
		tfTitulo.setText(livro.getTitulo());
		tfPreco.setText(String.format(Locale.US, "R$ %3.2f", livro.getPreco()));
		tfPublicacao.setText(DateUtil.toString(livro.getPublicacao()));
		tfPaginas.setText(String.valueOf(livro.getPaginas()));
		cbCapa.setSelectedIndex(livro.getCapa());
		tfEditora.setText(livro.getEditora().getNome());
		taResumo.setText(livro.getResumo());
		taSumario.setText(livro.getSumario());

		List<Autor> autores = new ArrayList<Autor>();
		List<Categoria> categorias = new ArrayList<Categoria>();

		for (Autor autor : livro.getLivroAutores().listar())
			autores.add(autor);

		for (Categoria categoria : livro.getLivroCategorias().listar())
			categorias.add(categoria);

		modelAutores.atualizarLista(autores);
		modelCategorias.atualizarLista(categorias);
	}

	private void callVoltarPara()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(voltarPara);
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
