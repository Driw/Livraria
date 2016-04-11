package com.livraria.fronteira;

import java.awt.Color;
import java.awt.Container;
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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.livraria.controle.ControleAutor;
import com.livraria.entidades.Autor;

@SuppressWarnings("serial")
public class FronteiraManterAutores extends Container implements IFronteira
{
	private JTextField tfNome;
	private JTextField tfDataNascimento;
	private JTextField tfDataFalecimento;
	private JTextField tfLocalMorte;
	private JTextField tfBiografia;
	private JTable tableConsulta;
	private JTextField tfFiltro;
	private ControleAutor controleAutor = new ControleAutor();
	private JComboBox<String> cbFiltro;
	private ModelManterAutores model;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public FronteiraManterAutores()
	{		
		setSize(850, 540);

		JLabel lblGerenciarAutores = new JLabel("GERENCIAR AUTORES");
		lblGerenciarAutores.setHorizontalAlignment(SwingConstants.CENTER);
		lblGerenciarAutores.setFont(Fronteira.FONT_TITULO);
		lblGerenciarAutores.setBounds(20, 11, 795, 33);
		add(lblGerenciarAutores);

		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dados do Autor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDados.setBounds(20, 55, 820, 251);
		panelDados.setLayout(null);
		add(panelDados);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(Fronteira.FONT_COMPONENTES);
		lblNome.setBounds(10, 18, 150, 25);
		panelDados.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(170, 19, 479, 25);
		panelDados.add(tfNome);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento :");
		lblDataDeNascimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeNascimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeNascimento.setBounds(10, 53, 150, 25);
		panelDados.add(lblDataDeNascimento);

		tfDataNascimento = new JTextField();
		tfDataNascimento.setBounds(170, 55, 140, 25);
		panelDados.add(tfDataNascimento);

		JLabel lblDataDeFalecimento = new JLabel("Data de Falecimento:");
		lblDataDeFalecimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeFalecimento.setFont(Fronteira.FONT_COMPONENTES);
		lblDataDeFalecimento.setBounds(349, 53, 150, 25);
		panelDados.add(lblDataDeFalecimento);

		tfDataFalecimento = new JTextField();
		tfDataFalecimento.setBounds(509, 55, 140, 25);
		panelDados.add(tfDataFalecimento);

		JLabel lblLocalDaMorte = new JLabel("Possível Local da Morte :");
		lblLocalDaMorte.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalDaMorte.setFont(Fronteira.FONT_COMPONENTES);
		lblLocalDaMorte.setBounds(10, 91, 150, 25);
		panelDados.add(lblLocalDaMorte);

		tfLocalMorte = new JTextField();
		tfLocalMorte.setBounds(170, 92, 479, 25);
		panelDados.add(tfLocalMorte);

		JLabel lblBiografia = new JLabel("Biografia :");
		lblBiografia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBiografia.setFont(Fronteira.FONT_COMPONENTES);
		lblBiografia.setBounds(10, 127, 150, 25);
		panelDados.add(lblBiografia);

		tfBiografia = new JTextField();
		tfBiografia.setBounds(170, 127, 479, 113);
		panelDados.add(tfBiografia);

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
		separator.setBounds(10, 317, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consultar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(20, 332, 820, 186);
		panelConsulta.setLayout(null);
		add(panelConsulta);

		JLabel lblFiltro = new JLabel("Filtro :");
		lblFiltro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(10, 21, 80, 25);
		panelConsulta.add(lblFiltro);

		tfFiltro = new JTextField();
		tfFiltro.setBounds(100, 22, 394, 25);
		panelConsulta.add(tfFiltro);

		JComboBox<String> cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(504, 22, 145, 25);
		cbFiltro.addItem("Nome");
		cbFiltro.addItem("Biografia");
		panelConsulta.add(cbFiltro);

		ModelManterAutores model = new ModelManterAutores();

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
		tableConsulta.setRowSelectionAllowed(false);

		JScrollPane scrollPaneConsulta = new JScrollPane(tableConsulta);
		scrollPaneConsulta.setBounds(10, 57, 639, 118);
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
		return "Manter Autores";
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Adicionar")
		{
			try
			{
				Autor a = new Autor();
				a.setNome(tfNome.getText());
				a.setNascimento(format.parse(tfDataNascimento.getText()));
				a.setFalecimento(format.parse(tfDataFalecimento.getText()));
				a.setLocalMorte(tfLocalMorte.getText());
				a.setBiografia(tfBiografia.getText());
				controleAutor.adicionar(a);
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
				Autor a = new Autor();
				a.setNome(tfNome.getText());
				a.setNascimento(format.parse(tfDataNascimento.getText()));
				a.setFalecimento(format.parse(tfDataFalecimento.getText()));
				a.setLocalMorte(tfLocalMorte.getText());
				a.setBiografia(tfBiografia.getText());
				controleAutor.atualizar(a);
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
				controleAutor.excluir(model.getLinha(tableConsulta.getSelectedRow()).getID());
				tfNome.setText("");
				tfDataNascimento.setText("");
				tfDataFalecimento.setText("");
				tfLocalMorte.setText("");
				tfBiografia.setText("");
				model.removeRow(tableConsulta.getSelectedRow());
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
				Vector<Autor> autores = new Vector<Autor>();
				if (cbFiltro.getSelectedIndex() == 1)
				{
					autores = controleAutor.filtrarPorNome(tfFiltro.getText());
				}
				else if (cbFiltro.getSelectedIndex() == 2)
				{
					autores = controleAutor.filtrarPorLocalMorte(tfFiltro.getText());
				}
				else if (cbFiltro.getSelectedIndex() == 3)
				{
					autores = controleAutor.filtrarPorBiografia(tfFiltro.getText());
				}
				
				model.addRow(autores);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		else if (e.getActionCommand() == "Limpar")
		{
			tfNome.setText("");
			tfDataNascimento.setText("");
			tfDataFalecimento.setText("");
			tfLocalMorte.setText("");
			tfBiografia.setText("");
		}
		
	}
}
