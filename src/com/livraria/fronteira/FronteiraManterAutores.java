package com.livraria.fronteira;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.diverproject.util.DateUtil;
import org.diverproject.util.MessageUtil;

import com.livraria.controle.ControleAutor;
import com.livraria.entidades.Autor;
import com.livraria.fronteira.model.ModelManterAutores;
import com.livraria.fronteira.popup.PopUpVerLivros;
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class FronteiraManterAutores extends JPanel implements IFronteira
{
	private static final JPanel INSTANCE = new FronteiraManterAutores();

	private static final int FILTRO_NOME = 0;
	private static final int FILTRO_BIOGRAFIA = 1;
	private static final int FILTRO_LOCAL_DA_MORTE = 2;

	private ModelManterAutores model;
	private ControleAutor controleAutor;
	private Autor autor;

	private JTextField tfNome;
	private JFormattedTextField tfDataNascimento;
	private JFormattedTextField tfDataFalecimento;
	private JTextField tfLocalMorte;
	private JTextArea tfBiografia;
	private JTable tableConsulta;
	private JTextField tfFiltro;
	private JComboBox<String> cbFiltro;

	private PopUpVerLivros popup;

	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	@SuppressWarnings("deprecation")
	public FronteiraManterAutores()
	{
		controleAutor = new ControleAutor();

		setSize(820, 460);
		setLayout(null);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Autor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDados.setBounds(0, 0, 820, 251);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(Fronteira.FONT_COMPONENTES);
		lblNome.setBounds(10, 18, 150, 25);
		panelDados.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(170, 19, 479, 25);
		tfNome.addKeyListener(ComponentUtil.maxLength(tfNome, 48));
		tfNome.setToolTipText("Nome pode ser parcial ou completo, deve ter de 3 a 48 caracteres.");
		panelDados.add(tfNome);

		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento :");
		lblDataDeNascimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeNascimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeNascimento.setBounds(10, 53, 150, 25);
		panelDados.add(lblDataDeNascimento);

		tfDataNascimento = new JFormattedTextField();
		tfDataNascimento.setBounds(170, 55, 140, 25);
		tfDataNascimento.setToolTipText("Se não houver data de nascimento deixar em branco ou com zeros: 00/00/0000.");
		ComponentUtil.setDataMask(tfDataNascimento);
		panelDados.add(tfDataNascimento);

		JLabel lblDataDeFalecimento = new JLabel("Data de Falecimento:");
		lblDataDeFalecimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeFalecimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeFalecimento.setBounds(349, 53, 150, 25);
		panelDados.add(lblDataDeFalecimento);

		tfDataFalecimento = new JFormattedTextField();
		tfDataFalecimento.setBounds(509, 55, 140, 25);
		tfDataFalecimento.setToolTipText("Deixar com 00/00/0000 se estiver vivo ou se não souber data da morte.");
		ComponentUtil.setDataMask(tfDataFalecimento);
		panelDados.add(tfDataFalecimento);

		JLabel lblLocalDaMorte = new JLabel("Possível Local da Morte :");
		lblLocalDaMorte.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalDaMorte.setFont(Fronteira.FONT_COMPONENTES);
		lblLocalDaMorte.setBounds(10, 91, 150, 25);
		panelDados.add(lblLocalDaMorte);

		tfLocalMorte = new JTextField();
		tfLocalMorte.setBounds(170, 92, 479, 25);
		tfLocalMorte.addKeyListener(ComponentUtil.maxLength(tfLocalMorte, 32));
		tfLocalMorte.setToolTipText("Local da morte deve ser especificado com nome de cidade e/ou país, limite de 32 caracteres.");
		panelDados.add(tfLocalMorte);

		JLabel lblBiografia = new JLabel("Biografia :");
		lblBiografia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBiografia.setFont(Fronteira.FONT_COMPONENTES);
		lblBiografia.setBounds(10, 127, 150, 25);
		panelDados.add(lblBiografia);

		tfBiografia = new JTextArea();
		tfBiografia.setLineWrap(true);
		tfBiografia.setTabSize(4);
		tfBiografia.addKeyListener(ComponentUtil.maxLength(tfBiografia, 512));
		tfBiografia.setToolTipText("A biografia deve contrar um pouco sobre a história/trabalhos do autor, limite de 512 caracteres.");

		JScrollPane spBiografia = new JScrollPane(tfBiografia);
		spBiografia.setBounds(170, 127, 479, 113);
		panelDados.add(spBiografia);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 229);
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
				callAdicionarAutor();
			}
		});
		btnAdicionar.setToolTipText("Adicionar irá registrar um novo autor com as informações ao lado.");
		panelDadosAcoes.add(btnAdicionar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarAutor();
			}
		});
		btnAtualizar.setToolTipText("Atualizar só irá funcionar se houver um autor selecionado da cosulta.");
		panelDadosAcoes.add(btnAtualizar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirAutor();
			}
		});
		btnExcluir.setToolTipText("Essa opção de excluir só irá funcionar se houver um autor selecionado da consulta.");
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
		btnLimpar.setToolTipText("Limpa todos os campos relacionado aos dados do autor e desseleciona o autor se houver um selecionado.");
		panelDadosAcoes.add(btnLimpar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 262, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consultar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(0, 274, 820, 186);
		panelConsulta.setLayout(null);
		add(panelConsulta);

		JLabel lblFiltro = new JLabel("Filtro :");
		lblFiltro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(10, 21, 80, 25);
		panelConsulta.add(lblFiltro);

		tfFiltro = new JTextField();
		tfFiltro.setBounds(100, 22, 394, 25);
		tfFiltro.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				callFiltrar(tfFiltro.getText(), cbFiltro.getSelectedIndex());
			}
		});
		tfFiltro.setToolTipText("Conforme digitado algo irá consultar o banco de dados autores de acordo com o filtro selecionado.");
		panelConsulta.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(504, 22, 145, 25);
		cbFiltro.addItem("Nome");
		cbFiltro.addItem("Biografia");
		cbFiltro.setToolTipText("Filtro irá indicar qual a informação do autor que será escolhida como resultado de busca.");
		panelConsulta.add(cbFiltro);

		model = new ModelManterAutores();

		tableConsulta = new JTable();
		tableConsulta.setModel(model);
		tableConsulta.getColumnModel().getColumn(0).setResizable(false);
		tableConsulta.getColumnModel().getColumn(0).setMinWidth(200);
		tableConsulta.getColumnModel().getColumn(0).setMaxWidth(200);
		tableConsulta.getColumnModel().getColumn(1).setResizable(false);
		tableConsulta.getColumnModel().getColumn(1).setMinWidth(100);
		tableConsulta.getColumnModel().getColumn(1).setMaxWidth(100);
		tableConsulta.getColumnModel().getColumn(2).setResizable(false);
		tableConsulta.getColumnModel().getColumn(2).setMinWidth(100);
		tableConsulta.getColumnModel().getColumn(2).setMaxWidth(100);
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(100);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(100);
		tableConsulta.getColumnModel().getColumn(4).setResizable(false);
		tableConsulta.getColumnModel().getColumn(4).setMinWidth(139);
		tableConsulta.getColumnModel().getColumn(4).setMaxWidth(139);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(true);
		tableConsulta.setToolTipText("Tabela contendo todos os autores encontrados de acordo com a filtragem escolhida.");

		JScrollPane scrollPaneConsulta = new JScrollPane(tableConsulta);
		scrollPaneConsulta.setBounds(10, 57, 639, 118);
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
		btnVerTodos.setToolTipText("Essa opção irá buscar todos os autores existentes no banco dedados.");
		panelConsultaAcoes.add(btnVerTodos);

		JButton btnConsultaSelecionar = new JButton("Selecionar");
		btnConsultaSelecionar.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callSelecionarAutor();
			}
		});
		btnConsultaSelecionar.setToolTipText("Puxa os dados do autor selecionado na consulta para os campos, permitindo atualizar ou excluir.");
		panelConsultaAcoes.add(btnConsultaSelecionar);

		JButton btnConsultaExcluir = new JButton("Excluir");
		btnConsultaExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirAutorDireto();
			}
		});
		btnConsultaExcluir.setToolTipText("Permite excluir um autor que esteja selecionado na tabela de consulta.");
		panelConsultaAcoes.add(btnConsultaExcluir);

		JButton btnConsultaVerLivros = new JButton("Ver Livros");
		btnConsultaVerLivros.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaVerLivros.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVerLivros();
			}
		});
		btnConsultaVerLivros.setToolTipText("Ao clicar uma nova tela irá abrir listando todos os livros do autor selecionado na consulta.");
		panelConsultaAcoes.add(btnConsultaVerLivros);

		tfNome.setNextFocusableComponent(tfDataNascimento);
		tfDataNascimento.setNextFocusableComponent(tfDataFalecimento);
		tfDataFalecimento.setNextFocusableComponent(tfLocalMorte);
		tfLocalMorte.setNextFocusableComponent(tfBiografia);
		tfBiografia.setNextFocusableComponent(btnAdicionar);
		btnAdicionar.setNextFocusableComponent(btnAtualizar);
		btnAtualizar.setNextFocusableComponent(btnExcluir);
		btnExcluir.setNextFocusableComponent(btnLimpar);
	}

	@Override
	public String getTitle()
	{
		return "Manter Autores";
	}

	private Autor criarAutor() throws FronteiraException
	{
		if (tfNome.getText().length() == 0)
			throw new FronteiraException("Nome: em branco.");

		if (tfNome.getText().length() < 3)
			throw new FronteiraException("Nome: mínimo de 3 caracteres.");

		if (tfNome.getText().length() > 48)
			throw new FronteiraException("Nome: limite de 48 caracteres.");

		Date nascimento, falecimento;

		if (tfDataNascimento.getText().equals("  /  /    ") || tfDataNascimento.getText().equals("00/00/0000"))
			nascimento = null;
		else
			try {
				nascimento = DateUtil.toDate(tfDataNascimento.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Nascimento: inválida.");
			}

		if (tfDataFalecimento.getText().equals("  /  /    ") || tfDataFalecimento.getText().equals("00/00/0000"))
			falecimento = null;
		else
			try {
				falecimento = DateUtil.toDate(tfDataFalecimento.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Falecimento: inválida.");
			}

		if (nascimento != null && falecimento != null)
			if (nascimento.getTime() >= falecimento.getTime())
				throw new FronteiraException("Data: nascimento deve vir antes do falescimento.");

		if (tfLocalMorte.getText().length() > 32)
			throw new FronteiraException("Possível Morte: limite de 32 caracteres.");

		if (tfBiografia.getText().length() > 512)
			throw new FronteiraException("Biografia: limite de 512 caracteres.");

		String localDaMorte = tfLocalMorte.getText().length() == 0 ? null : tfLocalMorte.getText();
		String biografia = tfBiografia.getText().length() == 0 ? null : tfBiografia.getText();

		Autor autor = new Autor();
		autor.setNome(tfNome.getText());
		autor.setNascimento(nascimento);
		autor.setFalecimento(falecimento);
		autor.setLocalMorte(localDaMorte);
		autor.setBiografia(biografia);

		return autor;
	}

	private void callLimparCampos()
	{
		tfNome.setText("");
		tfDataNascimento.setText("");
		tfDataFalecimento.setText("");
		tfLocalMorte.setText("");
		tfBiografia.setText("");

		autor = null;

		btnAdicionar.setEnabled(true);
		btnAtualizar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

	private void callAdicionarAutor()
	{
		try {

			autor = criarAutor();

			if (controleAutor.existe(autor.getNome()))
				if (!MessageUtil.showYesNo("Adicionar Autor", "Autor '%s' já existente, adicionar mesmo assim?", autor.getNome()))
					return;

			if (controleAutor.adicionar(autor))
			{
				MessageUtil.showInfo("Adicionar Autor", "Autor '%s' adicionado com êxtio!", autor.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Adicionar Autor", "Não foi possível adicionar o autor '%s'.", autor.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Autor", "Falha ao adicionar o autor '%s'.\n- %s", autor.getNome(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Autor", "Verifique o campo abaixo:\n- %s", e.getMessage());
		}
	}

	private void callAtualizarAutor()
	{
		if (autor == null)
		{
			MessageUtil.showInfo("Atualizar Autor", "Selecione um autor na consulta antes.");
			return;
		}

		try {

			Autor autorConsultado = autor;

			autor = criarAutor();
			autor.setID(autorConsultado.getID());

			if (controleAutor.atualizar(autor))
			{
				autorConsultado.copiar(autor);

				MessageUtil.showInfo("Atualizar Autor", "Autor '%s' atualizado com êxtio!", autor.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Autor", "Não foi possível atualizado o autor '%s'.", autor.getNome());

		} catch (FronteiraException e) {
			MessageUtil.showWarning("Atualizar Autor", "Falha ao atualizar autor:\n- %s", e.getMessage());
		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Autor", "Falha ao atualizar o autor '%s'.\n- %s", autor.getNome(), e.getMessage());
		}
	}

	private void callExcluirAutor()
	{
		if (autor == null)
		{
			MessageUtil.showInfo("Excluir Autor", "Selecione um autor na consulta antes.");
			return;
		}

		try {

			if (controleAutor.excluir(autor.getID()))
			{
				model.remover(autor);

				MessageUtil.showInfo("Excluir Autor", "Autor '%s' excluído com êxtio!", autor.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Autor", "Não foi possível excluir o autor '%s'.", autor.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Autor", "Falha ao excluir o autor '%s'.\n- %s", autor.getNome(), e.getMessage());
		}
	}

	private void callFiltrar(String filtro, int filtroTipo)
	{
		try {

			List<Autor> autores = null;

			switch (filtroTipo)
			{
				case FILTRO_NOME:
					autores = controleAutor.filtrarPorNome(filtro);
					break;

				case FILTRO_LOCAL_DA_MORTE:
					autores = controleAutor.filtrarPorLocalMorte(filtro);
					break;

				case FILTRO_BIOGRAFIA:
					autores = controleAutor.filtrarPorBiografia(filtro);
					break;
			}

			model.atualizarLista(autores);

		} catch (SQLException e) {
			MessageUtil.showError("Filtrar Autores", "Falha ao filtrar autores.\n- %s", e.getMessage());
		}
	}

	private void callVerTodos()
	{
		try {

			List<Autor> autores = controleAutor.listar();
			model.atualizarLista(autores);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Todos", "Falha ao listar autores.\n- %s", e.getMessage());
		}
	}

	private void callSelecionarAutor()
	{
		autor = model.getLinha(tableConsulta.getSelectedRow());

		tfNome.setText(autor.getNome());
		tfDataNascimento.setText(DateUtil.toString(autor.getNascimento()));
		tfDataFalecimento.setText(DateUtil.toString(autor.getFalecimento()));
		tfLocalMorte.setText(autor.getLocalMorte());
		tfBiografia.setText(autor.getBiografia());

		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}

	private void callExcluirAutorDireto()
	{
		try {

			Autor autor = model.getLinha(tableConsulta.getSelectedRow());

			if (controleAutor.excluir(autor.getID()))
			{
				model.removerLinha(tableConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Autor", "Autor '%s' excluído com êxito!", autor.getNome());
			}

			else
				MessageUtil.showWarning("Excluir Autor", "Não foi possível excluir o autor '%s'.", autor.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Autor", "Falha ao excluir o autor '%s'.\n- %s", autor.getNome(), e.getMessage());
		}
	}

	private void callVerLivros()
	{
		Autor autor = model.getLinha(tableConsulta.getSelectedRow());

		if (autor == null)
		{
			MessageUtil.showInfo("Ver Livros", "Selecione um autor na consulta!");
			return;
		}

		if (popup == null)
			popup = new PopUpVerLivros(this);

		popup.carregar(autor);
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
