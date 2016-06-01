package com.livraria.fronteira;

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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.diverproject.util.DateUtil;
import org.diverproject.util.MessageUtil;

import com.livraria.controle.ControleEditora;
import com.livraria.entidades.Editora;
import com.livraria.fronteira.model.ModelManterEditoras;
import com.livraria.fronteira.popup.PopUpVerLivros;
import com.livraria.util.ComponentUtil;
import com.livraria.util.FronteiraException;

@SuppressWarnings("serial")
public class FronteiraManterEditoras extends JPanel implements IFronteira
{
	private static final JPanel INSTANCE = new FronteiraManterEditoras();

	private static final int FILTRO_NOME = 0;
	private static final int FILTRO_CNPF = 1;
	private static final int FILTRO_TELEFONE = 2;

	private ModelManterEditoras model;
	private ControleEditora controleEditora;
	private Editora editora;

	private JTextField tfNome;
	private JFormattedTextField tfCnpj;
	private JTextField tfEndereco;
	private JFormattedTextField tfTelefone;
	private JFormattedTextField tfContratoInicio;
	private JFormattedTextField tfContratoFim;

	private JTextField tfFiltro;
	private JComboBox<String> cbFiltro;
	private JTable tableConsulta;

	private PopUpVerLivros popup;

	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;

	@SuppressWarnings("deprecation")
	public FronteiraManterEditoras()
	{
		controleEditora = new ControleEditora();

		setSize(820, 460);
		setLayout(null);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDados.setBounds(0, 0, 820, 251);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(Fronteira.FONT_COMPONENTES);
		lblNome.setBounds(10, 42, 150, 25);
		panelDados.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(170, 43, 480, 25);
		tfNome.setToolTipText("O nome da editra deve conter entre 3 a 32 caracteres sendo obrigatório.");
		panelDados.add(tfNome);

		JLabel lblCnpj = new JLabel("CNPJ :");
		lblCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCnpj.setFont(Fronteira.FONT_COMPONENTES);
		lblCnpj.setBounds(10, 83, 150, 25);
		panelDados.add(lblCnpj);

		tfCnpj = new JFormattedTextField();
		tfCnpj.setBounds(170, 84, 480, 25);
		tfCnpj.setToolTipText("CNPJ é composto por 14 dítigos e será verificado a sua validada.");
		ComponentUtil.setCnpjMask(tfCnpj);
		panelDados.add(tfCnpj);

		JLabel lblEndereo = new JLabel("Endereço :");
		lblEndereo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereo.setFont(Fronteira.FONT_COMPONENTES);
		lblEndereo.setBounds(10, 121, 150, 25);
		panelDados.add(lblEndereo);

		tfEndereco = new JTextField();
		tfEndereco.setBounds(170, 122, 480, 25);
		tfEndereco.setToolTipText("Endereço é um campo opicional e deve conter entre 3 a 48 caracteres.");
		panelDados.add(tfEndereco);

		JLabel lblTelefone = new JLabel("Telefone :");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setFont(Fronteira.FONT_COMPONENTES);
		lblTelefone.setBounds(10, 157, 150, 25);
		panelDados.add(lblTelefone);

		tfTelefone = new JFormattedTextField();
		tfTelefone.setBounds(170, 158, 170, 25);
		tfTelefone.setToolTipText("Telefone é um campo opicional e deve conter apenas núneros, se não houver DD usar 00.");
		ComponentUtil.setFoneMask(tfTelefone);
		panelDados.add(tfTelefone);

		JLabel lblContrato = new JLabel("Contrato :");
		lblContrato.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrato.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrato.setBounds(10, 193, 150, 25);
		panelDados.add(lblContrato);

		tfContratoInicio = new JFormattedTextField();
		tfContratoInicio.setBounds(170, 194, 170, 25);
		tfContratoInicio.setToolTipText("Usar apenas se houver contrato, a data inicial deve ser antes da final.");
		ComponentUtil.setDataMask(tfContratoInicio);
		panelDados.add(tfContratoInicio);

		JLabel lblContratoAte = new JLabel("até");
		lblContratoAte.setHorizontalAlignment(SwingConstants.CENTER);
		lblContratoAte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContratoAte.setBounds(350, 193, 40, 25);
		panelDados.add(lblContratoAte);

		tfContratoFim = new JFormattedTextField();
		tfContratoFim.setBounds(400, 194, 170, 25);
		tfContratoFim.setToolTipText("Usar apenas se houver contrato, a data final deve ser após a do inicio.");
		ComponentUtil.setDataMask(tfContratoFim);
		panelDados.add(tfContratoFim);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 229);
		panelDadosAcoes.setLayout(new GridLayout(5, 1, 0, 5));
		panelDados.add(panelDadosAcoes);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(Fronteira.FONT_COMPONENTES);
		btnAdicionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarEditora();
			}
		});
		btnAdicionar.setToolTipText("Adicionar irá registrar um nova editora com as informações abaixo.");
		panelDadosAcoes.add(btnAdicionar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarEditora();
			}
		});
		btnAtualizar.setToolTipText("Atualizar só irá funcionar se houver uma editora selecionado da cosulta.");
		panelDadosAcoes.add(btnAtualizar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirEditora();
			}
		});
		btnExcluir.setToolTipText("Essa opção de excluir só irá funcionar se houver uma editora selecionado da consulta.");
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
		btnLimpar.setToolTipText("Limpa todos os campos relacionado aos dados da editora e desseleciona o autor se houver um selecionado.");
		panelDadosAcoes.add(btnLimpar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 262, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consulta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(0, 274, 820, 186);
		panelConsulta.setLayout(null);
		add(panelConsulta);

		JLabel lblFiltro = new JLabel("Filtro :");
		lblFiltro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(10, 21, 80, 25);
		panelConsulta.add(lblFiltro);

		tfFiltro = new JTextField();
		tfFiltro.setBounds(100, 22, 395, 25);
		tfFiltro.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				callFiltrar(tfFiltro.getText(), cbFiltro.getSelectedIndex());
			}
		});
		tfFiltro.setToolTipText("Conforme digitado algo irá consultar o banco de dados editoras de acordo com o filtro selecionado.");
		panelConsulta.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(505, 22, 145, 25);
		cbFiltro.addItem("Nome");
		cbFiltro.addItem("CNPJ");
		cbFiltro.addItem("Telefone");
		cbFiltro.setToolTipText("Filtro irá indicar qual a informação da editora que será escolhida como resultado de busca.");
		panelConsulta.add(cbFiltro);

		model = new ModelManterEditoras();

		tableConsulta = new JTable();
		tableConsulta.setModel(model);
		tableConsulta.getColumnModel().getColumn(0).setResizable(false);
		tableConsulta.getColumnModel().getColumn(0).setMinWidth(240);
		tableConsulta.getColumnModel().getColumn(0).setMaxWidth(240);
		tableConsulta.getColumnModel().getColumn(1).setResizable(false);
		tableConsulta.getColumnModel().getColumn(1).setMinWidth(130);
		tableConsulta.getColumnModel().getColumn(1).setMaxWidth(130);
		tableConsulta.getColumnModel().getColumn(2).setResizable(false);
		tableConsulta.getColumnModel().getColumn(2).setMinWidth(120);
		tableConsulta.getColumnModel().getColumn(2).setMaxWidth(120);
		tableConsulta.getColumnModel().getColumn(3).setResizable(false);
		tableConsulta.getColumnModel().getColumn(3).setMinWidth(150);
		tableConsulta.getColumnModel().getColumn(3).setMaxWidth(150);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(true);
		tableConsulta.setToolTipText("Tabela contendo todas as editoras encontrados de acordo com a filtragem escolhida.");

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
		btnVerTodos.setToolTipText("Essa opção irá buscar todas as editoras existentes no banco dedados.");
		panelConsultaAcoes.add(btnVerTodos);

		JButton btnConsultaSelecionar = new JButton("Selecionar");
		btnConsultaSelecionar.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callSelecionarEditora();
			}
		});
		btnConsultaSelecionar.setToolTipText("Puxa os dados da editora selecionado na consulta para os campos, permitindo atualizar ou excluir.");
		panelConsultaAcoes.add(btnConsultaSelecionar);

		JButton btnConsultaExcluir = new JButton("Excluir");
		btnConsultaExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnConsultaExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirEditoraDireto();
			}
		});
		btnConsultaExcluir.setToolTipText("Permite excluir uma editora que esteja selecionado na tabela de consulta.");
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
		btnConsultaVerLivros.setToolTipText("Ao clicar uma nova tela irá abrir listando todos os livros da editora selecionado na consulta.");
		panelConsultaAcoes.add(btnConsultaVerLivros);

		tfNome.setNextFocusableComponent(tfCnpj);
		tfCnpj.setNextFocusableComponent(tfEndereco);
		tfEndereco.setNextFocusableComponent(tfTelefone);
		tfTelefone.setNextFocusableComponent(tfContratoInicio);
		tfContratoInicio.setNextFocusableComponent(tfContratoFim);
		tfContratoFim.setNextFocusableComponent(btnAdicionar);
		btnAdicionar.setNextFocusableComponent(btnAtualizar);
		btnAtualizar.setNextFocusableComponent(btnExcluir);
		btnExcluir.setNextFocusableComponent(btnLimpar);
	}

	@Override
	public String getTitle()
	{
		return "Manter Editoras";
	}

	private Editora criarEditora() throws FronteiraException
	{
		if (tfNome.getText().length() == 0)
			throw new FronteiraException("Nome: em branco.");

		if (tfNome.getText().length() < 3)
			throw new FronteiraException("Nome: mínimo de 3 caracteres.");

		if (tfNome.getText().length() > 32)
			throw new FronteiraException("Nome: limite de 32 caracteres.");

		// TODO VALIDAR CNPJ
		// TODO VALIDAR TELEFONE

		Date contratoInicio, contratoFim;

		if (tfContratoInicio.getText().equals("  /  /    ") || tfContratoInicio.getText().equals("00/00/0000"))
			contratoInicio = null;
		else
			try {
				contratoInicio = DateUtil.toDate(tfContratoInicio.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Contrato (Inicio): inválida.");
			}

		if (tfContratoFim.getText().equals("  /  /    ") || tfContratoFim.getText().equals("00/00/0000"))
			contratoFim = null;
		else
			try {
				contratoFim = DateUtil.toDate(tfContratoFim.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Contrato (Fim): inválida.");
			}

		if (contratoInicio == null && contratoFim != null)
			throw new FronteiraException("Contrato (Inicio): não definido.");

		if (contratoInicio != null && contratoFim == null)
			throw new FronteiraException("Contrato (Fim): não definido.");

		if (contratoInicio != null && contratoFim != null)
			if (contratoInicio.getTime() >= contratoFim.getTime())
				throw new FronteiraException("Contrato: a data de fim deve após o inicio.");

		String cnpj = tfCnpj.getText();
		String telefone = tfTelefone.getText();

		if (cnpj.equals("  .   .   /    -  ") || cnpj.equals("00.000.000/0000-00"))
			cnpj = null;

		if (telefone.equals("(  )     -    ") || telefone.equals("(00) 0000-0000"))
			telefone = null;

		Editora editora = new Editora();
		editora.setNome(tfNome.getText());
		editora.setCnpj(cnpj);
		editora.setEndereco(tfEndereco.getText());
		editora.setTelefone(telefone);
		editora.setContrato(contratoInicio, contratoFim);

		return editora;
	}

	private void callLimparCampos()
	{
		tfNome.setText("");
		tfCnpj.setText("");
		tfEndereco.setText("");
		tfTelefone.setText("");
		tfContratoInicio.setText("");
		tfContratoFim.setText("");

		editora = null;

		btnAdicionar.setEnabled(true);
		btnAtualizar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

	public void callAdicionarEditora()
	{
		try {

			editora = criarEditora();

			if (controleEditora.existe(editora.getNome()))
				if (!MessageUtil.showYesNo("Adicionar Editora", "Editora '%s' já existe, adicionar mesmo assim?", editora.getNome()))
					return;

			if (controleEditora.adicionar(editora))
			{
				MessageUtil.showInfo("Adicionar Editora", "Editora '%s' adicionada com êxtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Adicionar Editora", "Não foi possível adicionar a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Editora", "Falha ao adicionar a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Editora", "Verifique o campo abaixo:\n- %s", e.getMessage());
		}
	}
	
	public void callAtualizarEditora()
	{
		if (editora == null)
		{
			MessageUtil.showInfo("Atualizar Editora", "Selecione uma editora na consulta antes.");
			return;
		}

		try {

			Editora editoraConsultado = editora;

			editora = criarEditora();
			editora.setID(editoraConsultado.getID());

			if (controleEditora.atualizar(editora))
			{
				editoraConsultado.copiar(editora);

				MessageUtil.showInfo("Atualizar Editora", "Editora '%s' atualizada com êxtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Editora", "Não foi possível atualizar a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Editora", "Falha ao atualizar a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Atualizar Editora", "Falha ao atualizar o editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		}
	}

	private void callExcluirEditora()
	{
		if (editora == null)
		{
			MessageUtil.showInfo("Excluir Editora", "Selecione uma editora na consulta antes.");
			return;
		}

		try {

			if (controleEditora.excluir(editora))
			{
				model.remover(editora);

				MessageUtil.showInfo("Excluir Editora", "Editora '%s' excluída com êxtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Editora", "NÃ£o foi possível excluir a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Editora", "Falha ao excluir a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		}
	}

	private void callFiltrar(String filtro, int filtroTipo)
	{
		try {

			List<Editora> editoras = null;

			switch (filtroTipo)
			{
				case FILTRO_NOME:
					editoras = controleEditora.filtrarPorNome(filtro);
					break;

				case FILTRO_CNPF:
					editoras = controleEditora.filtrarPorCNPJ(filtro);
					break;

				case FILTRO_TELEFONE:
					editoras = controleEditora.filtrarPorTelefone(filtro);
					break;
			}

			model.atualizarLista(editoras);

		} catch (SQLException e) {
			MessageUtil.showError("Filtrar Editoras", "Falha ao filtrar Editoras.\n- %s", e.getMessage());
		}
	}

	private void callVerTodos()
	{
		try {

			List<Editora> editoras = controleEditora.listar();
			model.atualizarLista(editoras);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Todos", "Falha ao listar editoras.\n- %s", e.getMessage());
		}
	}

	private void callSelecionarEditora()
	{
		editora = model.getLinha(tableConsulta.getSelectedRow());

		tfNome.setText(editora.getNome());
		tfCnpj.setText(editora.getCnpj());
		tfEndereco.setText(editora.getEndereco());
		tfTelefone.setText(editora.getTelefone());
		tfContratoInicio.setText(DateUtil.toString(editora.getContratoInicio()));
		tfContratoFim.setText(DateUtil.toString(editora.getContratoFim()));

		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}

	private void callExcluirEditoraDireto()
	{
		try {

			Editora editora = model.getLinha(tableConsulta.getSelectedRow());

			if (controleEditora.excluir(editora))
			{
				model.removerLinha(tableConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Editora", "Editora '%s' excluído com êxito!", editora.getNome());
			}

			else
				MessageUtil.showWarning("Excluir Editora", "NÃ£o foi possível excluir a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Editora", "Falha ao excluir a Editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		}
	}

	private void callVerLivros()
	{
		Editora editora = model.getLinha(tableConsulta.getSelectedRow());

		if (editora == null)
		{
			MessageUtil.showInfo("Ver Livros", "Selecione uma editora na consulta!");
			return;
		}

		if (popup == null)
			popup = new PopUpVerLivros(this);

		popup.carregar(editora);
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
