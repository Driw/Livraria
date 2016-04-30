package com.livraria.fronteira;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.livraria.controle.ControleLivro;
import com.livraria.entidades.Autor;
import com.livraria.entidades.Editora;
import com.livraria.entidades.Livro;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.diverproject.util.MessageUtil;

@SuppressWarnings("serial")
public class PopUpVerLivros extends JFrame
{
	private JTable table;
	private ModelPopUpVerLivros model;
	private ControleLivro controleLivro;

	public PopUpVerLivros(JPanel fronteira)
	{
		controleLivro = new ControleLivro();

		setSize(540, 280);
		setLocationRelativeTo(fronteira);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);

		model = new ModelPopUpVerLivros();

		table = new JTable();
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setMinWidth(130);
		table.getColumnModel().getColumn(0).setMaxWidth(130);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setMinWidth(234);
		table.getColumnModel().getColumn(1).setMaxWidth(234);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setMinWidth(70);
		table.getColumnModel().getColumn(3).setMaxWidth(70);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 514, 229);
		panel.add(scrollPane);
	}

	public void carregar(Autor autor)
	{
		setTitle("Livros do Autor: " +autor.getNome());

		try {

			List<Livro> lista = controleLivro.filtrarPorAutor(autor);
			model.atualizarLista(lista);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Livros", "Falha durante a consulta:\n- %s", e.getMessage());
		}
	}

	public void carregar(Editora editora)
	{
		setTitle("Livros do Autor: " +editora.getNome());

		try {

			List<Livro> lista = controleLivro.filtrarPorEditora(editora);
			model.atualizarLista(lista);

		} catch (SQLException e) {
			MessageUtil.showError("Ver Livros", "Falha durante a consulta:\n- %s", e.getMessage());
		}
	}
}
