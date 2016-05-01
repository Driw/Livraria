package com.livraria.fronteira.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.livraria.entidades.Categoria;

@SuppressWarnings("serial")
public class ModelListarCategorias extends DefaultTableModel
{
	private static final int COLUNA_TEMA = 0;

	private static final String COLUNS[] = new String[]
	{
		"Tema"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class
	};

	private List<Categoria> categorias = new ArrayList<Categoria>();

	@Override
	public Class<?> getColumnClass(int column)
	{
		return COLUNS_TYPE[column];
	}

	@Override
	public int getColumnCount()
	{
		return COLUNS.length;
	}

	@Override
	public String getColumnName(int column)
	{
		return COLUNS[column];
	}

	@Override
	public int getRowCount()
	{
		return categorias == null ? 0 : categorias.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Categoria categoria = categorias.get(row);

		if (categoria != null)
			switch (column)
			{
				case COLUNA_TEMA: return categoria.getTema();
			}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1)
	{
		return false;
	}

	public Categoria getLinha(int linha)
	{
		return categorias.get(linha);
	}

	public void atualizarLista(List<Categoria> lista)
	{
		categorias = lista;

		fireTableDataChanged();
	}

	public void adicionar(Categoria categoria)
	{
		for (Categoria c : categorias)
			if (c.getID() == categoria.getID())
				return;

		categorias.add(categoria);
		fireTableDataChanged();
	}
}
