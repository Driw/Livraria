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
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class FronteiraManterAutores extends JPanel implements IFronteira
{
	private static final int FILTRO_NOME = 0;
	private static final int FILTRO_BIOGRAFIA = 1;
	private static final int FILTRO_LOCAL_DA_MORTE = 2;

	private JTextField tfNome;
	private JFormattedTextField tfDataNascimento;
	private JFormattedTextField tfDataFalecimento;
	private JTextField tfLocalMorte;
	private JTextArea tfBiografia;
	private ModelManterAutores model;
	private JTable tableConsulta;
	private JTextField tfFiltro;
	private ControleAutor controleAutor = new ControleAutor();
	private JComboBox<String> cbFiltro;
	private Autor autor;

	@SuppressWarnings("deprecation")
	public FronteiraManterAutores()
	{		
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
		panelDados.add(tfNome);

		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento :");
		lblDataDeNascimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeNascimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeNascimento.setBounds(10, 53, 150, 25);
		panelDados.add(lblDataDeNascimento);

		tfDataNascimento = new JFormattedTextField();
		tfDataNascimento.setBounds(170, 55, 140, 25);
		ComponentUtil.setDataMask(tfDataNascimento);
		panelDados.add(tfDataNascimento);

		JLabel lblDataDeFalecimento = new JLabel("Data de Falecimento:");
		lblDataDeFalecimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeFalecimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeFalecimento.setBounds(349, 53, 150, 25);
		panelDados.add(lblDataDeFalecimento);

		tfDataFalecimento = new JFormattedTextField();
		tfDataFalecimento.setBounds(509, 55, 140, 25);
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

		JScrollPane spBiografia = new JScrollPane(tfBiografia);
		spBiografia.setBounds(170, 127, 479, 113);
		panelDados.add(spBiografia);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 229);
		panelDados.add(panelDadosAcoes);
		panelDadosAcoes.setLayout(new GridLayout(5, 1, 0, 5));

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(Fronteira.FONT_COMPONENTES);
		btnAdicionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarAutor();
			}
		});
		panelDadosAcoes.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarAutor();
			}
		});
		panelDadosAcoes.add(btnAtualizar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirAutor();
			}
		});
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
		panelConsulta.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(504, 22, 145, 25);
		cbFiltro.addItem("Nome");
		cbFiltro.addItem("Biografia");
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
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(139);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(139);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(true);

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
		if (tfNome.getText().length() < 3)
			throw new FronteiraException("Nome: nome muito curto");

		Date nascimento, falecimento;

		if (tfDataNascimento.getText().equals("__/__/____"))
			nascimento = null;
		else
			try {
				nascimento = DateUtil.toDate(tfDataNascimento.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Nascimento: inválida");
			}

		if (tfDataFalecimento.getText().equals("__/__/____"))
			falecimento = null;
		else
			try {
				falecimento = DateUtil.toDate(tfDataFalecimento.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Falecimento: inválida");
			}

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
	}

	private void callAdicionarAutor()
	{
		try {

			autor = criarAutor();

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
			MessageUtil.showError("Adicionar Autor", "Verifique o campo abaixo:\n- %s", autor.getNome(), e.getMessage());
		}
	}

	private void callAtualizarAutor()
	{
		try {

			autor.setNome(tfNome.getText());
			autor.setNascimento(DateUtil.toDate(tfDataNascimento.getText()));
			autor.setFalecimento(DateUtil.toDate(tfDataFalecimento.getText()));
			autor.setLocalMorte(tfLocalMorte.getText());
			autor.setBiografia(tfBiografia.getText());

			if (controleAutor.atualizar(autor))
			{
				MessageUtil.showInfo("Atualizar Autor", "Autor '%s' atualizado com êxtio!", autor.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Autor", "Não foi possível atualizado o autor '%s'.", autor.getNome());

		} catch (ParseException e) {
			MessageUtil.showWarning("Atualizar Autor", "Problema ao verificar datas.\n- %s", e.getMessage());
		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Autor", "Falha ao atualizar o autor '%s'.\n- %s", autor.getNome(), e.getMessage());
		}
	}

	private void callExcluirAutor()
	{
		try {

			if (controleAutor.excluir(autor.getID()))
			{
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
			MessageUtil.showError("Ver Todos", "Falha ao filtrar autores.\n- %s", e.getMessage());
		}
	}

	private void callSelecionarAutor()
	{
		autor = model.getLinha(tableConsulta.getSelectedRow());

		tfNome.setText(autor.getNome());
		tfDataNascimento.setText(DateUtil.toString(autor.getNascimento()));
		tfDataFalecimento.setText(DateUtil.toString(autor.getNascimento()));
		tfLocalMorte.setText(autor.getLocalMorte());
		tfBiografia.setText(autor.getBiografia());
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
		// TODO Ver Livros
	}
}
