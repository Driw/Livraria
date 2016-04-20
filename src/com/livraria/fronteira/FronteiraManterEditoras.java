package com.livraria.fronteira;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

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
import javax.swing.border.TitledBorder;

import com.livraria.controle.ControleEditora;
import com.livraria.entidades.Editora;

@SuppressWarnings("serial")
public class FronteiraManterEditoras extends JPanel implements IFronteira
{
	private JTextField tfNome;
	private JTextField tfCnpj;
	private JTextField tfEndereco;
	private JTextField tfTelefone;
	private JTable tabelaConsulta;
	private JTextField tfContratoInicio;
	private JTextField tfContratoFim;
	private JTextField tfFiltro;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private ControleEditora controleEditora = new ControleEditora();
	private ModelManterEditoras model;
	private JComboBox<String> cbFiltro;

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

		tfCnpj = new JTextField();
		tfCnpj.setBounds(170, 84, 480, 25);
		panelDados.add(tfCnpj);

		JLabel lblEndereo = new JLabel("Endereço :");
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

		tfTelefone = new JTextField();
		tfTelefone.setBounds(170, 158, 170, 25);
		panelDados.add(tfTelefone);

		JLabel lblContrato = new JLabel("Contrato :");
		lblContrato.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrato.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrato.setBounds(10, 193, 150, 25);
		panelDados.add(lblContrato);

		tfContratoInicio = new JTextField();
		tfContratoInicio.setBounds(170, 194, 170, 25);
		panelDados.add(tfContratoInicio);

		tfContratoFim = new JTextField();
		tfContratoFim.setBounds(400, 194, 170, 25);
		panelDados.add(tfContratoFim);

		JLabel lblContratoAte = new JLabel("até");
		lblContratoAte.setHorizontalAlignment(SwingConstants.CENTER);
		lblContratoAte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContratoAte.setBounds(350, 193, 40, 25);
		panelDados.add(lblContratoAte);

		JPanel panelDadosAcoes = new JPanel();
		panelDadosAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		cbFiltro.addItem("Endereço");
		cbFiltro.addItem("CNPJ");
		cbFiltro.addItem("Telefone");
		cbFiltro.addItem("Contrato");
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

		JButton btnConsultaVerLivros = new JButton("Ver Livros");
		btnConsultaVerLivros.setFont(Fronteira.FONT_COMPONENTES);
		panelConsultaAcoes.add(btnConsultaVerLivros);
		
		btnAdicionar.addActionListener( this );
		btnAtualizar.addActionListener( this );
		btnLimpar.addActionListener( this );
		btnConsultaSelecionar.addActionListener( this );
		btnConsultaExcluir.addActionListener( this );
	}

	@Override
	public String getTitle()
	{
		return "Manter Editoras";
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Adicionar")
		{
			try
			{
				Editora editora = new Editora();
				editora.setNome(tfNome.getText());
				editora.setCnpj(tfCnpj.getText());
				editora.setEndereco(tfEndereco.getText());
				editora.setTelefone(tfTelefone.getText());
				editora.setContrato(format.parse(tfContratoInicio.getText()), format.parse(tfContratoFim.getText()));
				controleEditora.adicionar(editora);
			}
			catch (ParseException | SQLException e1)
			{
				e1.printStackTrace();
			}
			
		}
		else if (e.getActionCommand() == "Atualizar")
		{
			try
			{
				Editora editora = new Editora();
				editora.setID(controleEditora.getId(tfNome.getText()));
				editora.setNome(tfNome.getText());
				editora.setCnpj(tfCnpj.getText());
				editora.setEndereco(tfEndereco.getText());
				editora.setTelefone(tfTelefone.getText());
				editora.setContrato(format.parse(tfContratoInicio.getText()), format.parse(tfContratoFim.getText()));
				controleEditora.atualizar(editora);
			}
			catch (ParseException | SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "Excluir")
		{
			try
			{
				controleEditora.excluir(model.getLinha(tabelaConsulta.getSelectedRow()).getID());
				tfNome.setText("");
				tfCnpj.setText("");
				tfEndereco.setText("");
				tfTelefone.setText("");
				tfContratoInicio.setText("");
				tfContratoFim.setText("");
				model.removeRow(tabelaConsulta.getSelectedRow());
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "Selecionar")
		{
			try
			{
				Vector<Editora> editoras = new Vector<Editora>();
				if (cbFiltro.getSelectedIndex() == 1)
				{
					editoras = controleEditora.filtrarPorCNPJ(tfFiltro.getText());
				}
				else if (cbFiltro.getSelectedIndex() == 2)
				{
					editoras = controleEditora.filtrarPorNome(tfFiltro.getText());
				}
				else if (cbFiltro.getSelectedIndex() == 3)
				{
					editoras = controleEditora.filtrarPorTelefone(tfFiltro.getText());
				}
				
				model.addRow(editoras);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "Limpar")
		{
			tfNome.setText("");
			tfCnpj.setText("");
			tfEndereco.setText("");
			tfTelefone.setText("");
			tfContratoInicio.setText("");
			tfContratoFim.setText("");
		}
		
	}
}
