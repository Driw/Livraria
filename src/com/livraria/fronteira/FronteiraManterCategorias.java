package com.livraria.fronteira;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

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

import org.diverproject.util.MessageUtil;

import com.livraria.controle.ControleCategoria;
import com.livraria.entidades.Categoria;

import com.livraria.util.FronteiraException;

@SuppressWarnings("serial")
public class FronteiraManterCategorias extends JPanel implements IFronteira
{
	private static final int FILTRO_CODIGO = 0;
	private static final int FILTRO_TEMA = 1;
	
	private ModelManterCategorias model;
	private ControleCategoria controleCategoria;
	private Categoria categoria;
	
	private JTextField tfCodigo;
	private JTextField tfTema;
	
	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnExcluir;
	
	private JTextField tfFiltro;
	private JComboBox<String> cbFiltro;
	private JTable tableConsulta;
	
	public FronteiraManterCategorias()
	{
		controleCategoria = new ControleCategoria();
		
		setSize(820, 460);
		setLayout(null);
		
		JPanel panelDados = new JPanel();
		panelDados.setBorder(new TitledBorder(null, "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDados.setBounds(0, 0, 820, 200);
		panelDados.setLayout(null);
		add(panelDados);
		
		JLabel lblCodigo = new JLabel("Codigo :");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setFont(Fronteira.FONT_COMPONENTES);
		lblCodigo.setBounds(10, 42, 150, 25);
		panelDados.add(lblCodigo);

		tfCodigo = new JTextField();
		tfCodigo.setBounds(170, 43, 480, 25);
		panelDados.add(tfCodigo);

		JLabel lblTema = new JLabel("Tema :");
		lblTema.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTema.setFont(Fronteira.FONT_COMPONENTES);
		lblTema.setBounds(10, 83, 150, 25);
		panelDados.add(lblTema);

		tfTema = new JTextField();
		tfTema.setBounds(170, 84, 480, 25);
		panelDados.add(tfTema);
		
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
				callAdicionarCategoria();
			}
		});
		btnAdicionar.setToolTipText("Adicionar irá registrar um nova categoria com as informações abaixo.");
		panelDadosAcoes.add(btnAdicionar);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setEnabled(false);
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizarCategoria();
			}
		});
		btnAtualizar.setToolTipText("Atualizar só irá funcionar se houver uma categoria selecionada da cosulta.");
		panelDadosAcoes.add(btnAtualizar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setFont(Fronteira.FONT_COMPONENTES);
		btnExcluir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callExcluirCategoria();
			}
		});
		btnExcluir.setToolTipText("Essa opção de excluir só irá funcionar se houver uma categoria selecionada da consulta.");
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
		separator.setBounds(0, 212, getWidth(), 12);
		add(separator);

		JPanel panelConsulta = new JPanel();
		panelConsulta.setBorder(new TitledBorder(null, "Consulta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsulta.setBounds(0, 225, 820, 230);
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
		panelConsulta.add(tfFiltro);

		cbFiltro = new JComboBox<String>();
		cbFiltro.setBounds(505, 22, 145, 25);
		cbFiltro.addItem("Codigo");
		cbFiltro.addItem("Tema");
		panelConsulta.add(cbFiltro);
		
		model = new ModelManterCategorias();
		
		tableConsulta = new JTable();
		tableConsulta.setModel(model);
		tableConsulta.getColumnModel().getColumn(0).setResizable(false);
		tableConsulta.getColumnModel().getColumn(0).setMinWidth(150);
		tableConsulta.getColumnModel().getColumn(0).setMaxWidth(150);
		tableConsulta.getColumnModel().getColumn(1).setResizable(false);
		tableConsulta.getColumnModel().getColumn(1).setMinWidth(450);
		tableConsulta.getColumnModel().getColumn(1).setMaxWidth(450);
		tableConsulta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableConsulta.setRowSelectionAllowed(true);
		
		JScrollPane scrollPaneConsulta = new JScrollPane(tableConsulta);
		scrollPaneConsulta.setBounds(10, 57, 640, 160);
		panelConsulta.add(scrollPaneConsulta);

		JPanel panelConsultaAcoes = new JPanel();
		panelConsultaAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConsultaAcoes.setBounds(660, 11, 150, 187);
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
				callSelecionarCategoria();
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
				callExcluirCategoriaDireto();
			}
		});
		panelConsultaAcoes.add(btnConsultaExcluir);
	}
	
	@Override
	public String getTitle()
	{
		return "Manter Editoras";
	}
	
	private Categoria criarCategoria() throws FronteiraException
	{
		if (tfCodigo.getText().length() == 0)
			throw new FronteiraException("Código em branco.");
		
		Categoria categoria = new Categoria();
		categoria.setCodigo(tfCodigo.getText());
		categoria.setTema(tfTema.getText());
		
		return categoria;
	}
	
	public void callAdicionarCategoria()
	{
		try {

			categoria = criarCategoria();

			if (controleCategoria.existe(categoria.getTema()))
				if (!MessageUtil.showYesNo("Adicionar Categoria", "Categoria '%s' já existe, adicionar mesmo assim?", categoria.getTema()))
					return;

			if (controleCategoria.adicionar(categoria))
			{
				MessageUtil.showInfo("Adicionar Categoria", "Categoria '%s' adicionada com êxtio!", categoria.getTema());
				callLimparCampos();
			}
			else
				MessageUtil.showWarning("Adicionar Categoria", "Não foi possível adicionar a categoria '%s'.", categoria.getTema());

		} catch (SQLException e) {
			MessageUtil.showError("Adicionar Categoria", "Falha ao adicionar a categoria '%s'.\n- %s", categoria.getTema(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Adicionar Categoria", "Verifique o campo abaixo:\n- %s", e.getMessage());
		}
	}
	
	public void callAtualizarCategoria()
	{
		if (categoria == null)
		{
			MessageUtil.showInfo("Atualizar Categoria", "Selecione uma categoria na consulta antes.");
			return;
		}

		try {

			Categoria categoriaConsultado = categoria;

			categoria = criarCategoria();
			categoria.setID(categoriaConsultado.getID());

			if (controleCategoria.atualizar(categoria))
			{
				categoriaConsultado.copiar(categoria);

				MessageUtil.showInfo("Atualizar Categoria", "Categoria '%s' atualizada com êxtio!", categoria.getTema());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Atualizar Categoria", "Não foi possível atualizar a categoria '%s'.", categoria.getTema());

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar Categoria", "Falha ao atualizar a categoria '%s'.\n- %s", categoria.getTema(), e.getMessage());
		} catch (FronteiraException e) {
			MessageUtil.showError("Atualizar Categoria", "Falha ao atualizar a categoria '%s'.\n- %s", categoria.getTema(), e.getMessage());
		}
	}
	
	private void callExcluirCategoria()
	{
		if (categoria == null)
		{
			MessageUtil.showInfo("Excluir Categoria", "Selecione uma categoria na consulta antes.");
			return;
		}

		try {

			if (controleCategoria.excluir(categoria))
			{
				model.remover(categoria);

				MessageUtil.showInfo("Excluir Categoria", "Categoria '%s' excluída com êxtio!", categoria.getTema());
				callLimparCampos();
			}

			else
				MessageUtil.showWarning("Excluir Categoria", "NÃ£o foi possível excluir a categoria '%s'.", categoria.getTema());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Categoria", "Falha ao excluir a categoria '%s'.\n- %s", categoria.getTema(), e.getMessage());
		}
	}
	
	private void callFiltrar(String filtro, int filtroTipo)
	{
		try {

			List<Categoria> categorias = null;

			switch (filtroTipo)
			{
				case FILTRO_CODIGO:
					categorias = controleCategoria.filtrarPorCodigo(filtro);
					break;

				case FILTRO_TEMA:
					categorias = controleCategoria.filtrarPorTema(filtro);
					break;
			}

			model.atualizarLista(categorias);

		} catch (SQLException e) {
			MessageUtil.showError("Filtrar Categorias", "Falha ao filtrar categorias.\n- %s", e.getMessage());
		}
	}
	
	private void callVerTodos()
	{
		try {

			List<Categoria> categorias = controleCategoria.listar();
			model.atualizarLista(categorias);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Todos", "Falha ao listar categorias.\n- %s", e.getMessage());
		}
	}
	
	public void callLimparCampos()
	{
		tfCodigo.setText("");
		tfTema.setText("");
		
		categoria = null;
	}
	
	private void callSelecionarCategoria()
	{
		categoria = model.getLinha(tableConsulta.getSelectedRow());
		
		tfCodigo.setText(categoria.getCodigo());
		tfTema.setText(categoria.getTema());
		
		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}

	private void callExcluirCategoriaDireto()
	{
		try {

			Categoria categoria = model.getLinha(tableConsulta.getSelectedRow());
			
			if (controleCategoria.excluir(categoria))
			{
				model.removerLinha(tableConsulta.getSelectedRow());
				callLimparCampos();

				MessageUtil.showInfo("Excluir Categoria", "Categoria '%s' excluído com êxito!", categoria.getTema());
			}

			else
				MessageUtil.showWarning("Excluir Categoria", "NÃ£o foi possível excluir a categoria '%s'.", categoria.getTema());

		} catch (SQLException e) {
			MessageUtil.showError("Excluir Categoria", "Falha ao excluir a categoria '%s'.\n- %s", categoria.getTema(), e.getMessage());
		}
	}
}
