package com.livraria.fronteira;

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
import javax.swing.border.TitledBorder;

import org.diverproject.util.DateUtil;
import org.diverproject.util.MessageUtil;

import com.livraria.controle.ControleEditora;
import com.livraria.entidades.Editora;
import com.livraria.util.ComponentUtil;
import com.livraria.util.FronteiraException;

@SuppressWarnings("serial")
public class FronteiraManterEditoras extends JPanel implements IFronteira
{
	private static final int FILTRO_NOME = 0;
	private static final int FILTRO_ENDERECO = 1;
	private static final int FILTRO_TELEFONE = 2;
	private JTextField tfNome;
	private JFormattedTextField tfCnpj;
	private JTextField tfEndereco;
	private JFormattedTextField tfTelefone;
	private JTable tabelaConsulta;
	private JFormattedTextField tfContratoInicio;
	private JFormattedTextField tfContratoFim;
	private JTextField tfFiltro;
	private ControleEditora controleEditora = new ControleEditora();
	private ModelManterEditoras model;
	private JComboBox<String> cbFiltro;
	private Editora editora;

	public FronteiraManterEditoras()
	{
		setSize(850, 540);

		JLabel lblGerenciarEditoras = new JLabel("GERENCIAR EDITORAS");
		lblGerenciarEditoras.setHorizontalAlignment(SwingConstants.CENTER);
		lblGerenciarEditoras.setFont(Fronteira.FONT_TITULO);
		lblGerenciarEditoras.setBounds(20, 11, 795, 33);
		add(lblGerenciarEditoras);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDados.setBounds(20, 55, 820, 251);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(Fronteira.FONT_COMPONENTES);
		lblNome.setBounds(10, 42, 150, 25);
		panelDados.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(170, 43, 480, 25);
		panelDados.add(tfNome);

		JLabel lblCnpj = new JLabel("CNPJ :");
		lblCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCnpj.setFont(Fronteira.FONT_COMPONENTES);
		lblCnpj.setBounds(10, 83, 150, 25);
		panelDados.add(lblCnpj);

		tfCnpj = new JFormattedTextField();
		ComponentUtil.setCnpjMask(tfCnpj);
		tfCnpj.setBounds(170, 84, 480, 25);
		panelDados.add(tfCnpj);

		JLabel lblEndereo = new JLabel("Endere�o :");
		lblEndereo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereo.setFont(Fronteira.FONT_COMPONENTES);
		lblEndereo.setBounds(10, 121, 150, 25);
		panelDados.add(lblEndereo);

		tfEndereco = new JTextField();
		tfEndereco.setBounds(170, 122, 480, 25);
		panelDados.add(tfEndereco);

		JLabel lblTelefone = new JLabel("Telefone :");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setFont(Fronteira.FONT_COMPONENTES);
		lblTelefone.setBounds(10, 157, 150, 25);
		panelDados.add(lblTelefone);

		tfTelefone = new JFormattedTextField();
		ComponentUtil.setFoneMask(tfTelefone);
		tfTelefone.setBounds(170, 158, 170, 25);
		panelDados.add(tfTelefone);

		JLabel lblContrato = new JLabel("Contrato :");
		lblContrato.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrato.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrato.setBounds(10, 193, 150, 25);
		panelDados.add(lblContrato);

		tfContratoInicio = new JFormattedTextField();
		ComponentUtil.setDataMask(tfContratoInicio);
		tfContratoInicio.setBounds(170, 194, 170, 25);
		panelDados.add(tfContratoInicio);

		tfContratoFim = new JFormattedTextField();
		ComponentUtil.setDataMask(tfContratoFim);
		tfContratoFim.setBounds(400, 194, 170, 25);
		panelDados.add(tfContratoFim);

		JLabel lblContratoAte = new JLabel("at�");
		lblContratoAte.setHorizontalAlignment(SwingConstants.CENTER);
		lblContratoAte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContratoAte.setBounds(350, 193, 40, 25);
		panelDados.add(lblContratoAte);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "A��es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDadosAcoes.setBounds(660, 11, 150, 229);
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

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 317, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consulta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(20, 332, 820, 186);
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

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(505, 22, 145, 25);
		cbFiltro.addItem("Nome");
		cbFiltro.addItem("Endere�o");
		cbFiltro.addItem("Telefone");
		panelConsulta.add(cbFiltro);

		model = new ModelManterEditoras();

		tabelaConsulta = new JTable();
		tabelaConsulta.setModel(model);
		tabelaConsulta.getColumnModel().getColumn(0).setResizable(false);
		tabelaConsulta.getColumnModel().getColumn(0).setMinWidth(240);
		tabelaConsulta.getColumnModel().getColumn(0).setMaxWidth(240);
		tabelaConsulta.getColumnModel().getColumn(1).setResizable(false);
		tabelaConsulta.getColumnModel().getColumn(1).setMinWidth(130);
		tabelaConsulta.getColumnModel().getColumn(1).setMaxWidth(130);
		tabelaConsulta.getColumnModel().getColumn(2).setResizable(false);
		tabelaConsulta.getColumnModel().getColumn(2).setMinWidth(120);
		tabelaConsulta.getColumnModel().getColumn(2).setMaxWidth(120);
		tabelaConsulta.getColumnModel().getColumn(3).setResizable(false);
		tabelaConsulta.getColumnModel().getColumn(3).setMinWidth(150);
		tabelaConsulta.getColumnModel().getColumn(3).setMaxWidth(150);
		tabelaConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaConsulta.setRowSelectionAllowed(false);

		JScrollPane scrollPaneConsulta = new JScrollPane(tabelaConsulta);
		scrollPaneConsulta.setBounds(10, 57, 640, 118);
		panelConsulta.add(scrollPaneConsulta);

		JPanel panelConsultaAcoes = new JPanel();
		panelConsultaAcoes.setBorder(new TitledBorder(null, "A��es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsultaAcoes.setBounds(660, 11, 150, 167);
		panelConsulta.add(panelConsultaAcoes);
		panelConsultaAcoes.setLayout(new GridLayout(4, 1, 0, 5));

		JButton btnConsultaSelecionar = new JButton("Selecionar");
		btnConsultaSelecionar.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaSelecionar);

		JButton btnConsultaExcluir = new JButton("Excluir");
		btnConsultaExcluir.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaExcluir);

		JButton btnConsultaVerLivros = new JButton("Ver Livros");
		btnConsultaVerLivros.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaVerLivros);

		btnAdicionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAdicionarEditora();
			}
		});

		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarEditora();
			}
		});

		btnConsultaExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirEditora();
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

		btnConsultaSelecionar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callFiltro(tfFiltro.getText(), cbFiltro.getSelectedIndex());

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
		
		btnConsultaVerLivros.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVerLivros();
			}
		});
	}

	@Override
	public String getTitle()
	{
		return "Manter Editoras";
	}
	
	private Editora criarEditora() throws FronteiraException
	{
		if (tfNome.getText().length() < 3)
			throw new FronteiraException("Nome: nome muito curto");

		Date contratoInicio, contratoFim;

		if (tfContratoInicio.getText().equals("__/__/____"))
			contratoInicio = null;
		else
			try {
				contratoInicio = DateUtil.toDate(tfContratoInicio.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Inicio do Contrato: inv�lida");
			}

		if (tfContratoFim.getText().equals("__/__/____"))
			contratoFim = null;
		else
			try {
				contratoFim = DateUtil.toDate(tfContratoFim.getText());
			} catch (ParseException e) {
				throw new FronteiraException("Data de Fim de Contrato: inv�lida");
			}
		
		String cnpj = tfCnpj.getText().length() == 0 ? null : tfCnpj.getText();
		String telefone = tfTelefone.getText().length() == 0 ? null : tfTelefone.getText();

		Editora editora = new Editora();
		editora.setNome(tfNome.getText());
		editora.setCnpj(cnpj);
		editora.setEndereco(tfEndereco.getText());
		editora.setTelefone(telefone);
		editora.setContrato(contratoInicio, contratoFim);

		return editora;
	}

	public void callAdicionarEditora()
	{
		try {
			editora = criarEditora();

			if (controleEditora.adicionar(editora))
			{
				MessageUtil.showInfo("Adicionar Editora", "Editora '%s' adicionada com �xtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Adicionar Editora", "Não foi possível adicionar a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Editora", "Falha ao adicionar a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Editora", "Verifique o campo abaixo:\n- %s", editora.getNome(), e.getMessage());
		}
	}
	
	public void callAtualizarEditora()
	{
		try {

			editora = criarEditora();

			if (controleEditora.atualizar(editora))
			{
				MessageUtil.showInfo("Atualizar Editora", "Editora '%s' atualizada com �xtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Editora", "Não foi possível atualizar a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Editora", "Falha ao atualizar a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		} catch (FronteiraException e) {
			e.printStackTrace();
		}
	}
	
	private void callExcluirEditora()
	{
		try {

			if (controleEditora.excluir(editora.getID()))
			{
				MessageUtil.showInfo("Excluir Editora", "Editora '%s' exclu�da com �xtio!", editora.getNome());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Editora", "Não foi possível excluir a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Editora", "Falha ao excluir a editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		}
	}
	
	private void callConsultaSelecionar()
	{
		editora = model.getLinha(tabelaConsulta.getSelectedRow());

		tfNome.setText(editora.getNome());
		tfCnpj.setText(editora.getCnpj());
		tfEndereco.setText(editora.getEndereco());
		tfTelefone.setText(editora.getTelefone());
		tfContratoInicio.setText(DateUtil.toString(editora.getContratoInicio()));
		tfContratoFim.setText(DateUtil.toString(editora.getContratoFim()));
	}
	
	private void callConsultaExcluir()
	{
		try {

			Editora editora = model.getLinha(tabelaConsulta.getSelectedRow());

			if (controleEditora.excluir(editora.getID()))
			{
				model.removerLinha(tabelaConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Editora", "Editora '%s' exclu�do com �xito!", editora.getNome());
			}

			else
				MessageUtil.showWarning("Excluir Editora", "Não foi possível excluir a editora '%s'.", editora.getNome());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Editora", "Falha ao excluir a Editora '%s'.\n- %s", editora.getNome(), e.getMessage());
		}
	}
	
	private void callFiltro(String filtro, int filtroTipo)
	{
		try {

			List<Editora> editoras = null;

			switch (filtroTipo)
			{
				case FILTRO_NOME:
					editoras = controleEditora.filtrarPorNome(filtro);
					break;

				case FILTRO_ENDERECO:
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
	
	private void callVerLivros()
	{
		// TODO Ver Livros
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
	}
	
	
}
