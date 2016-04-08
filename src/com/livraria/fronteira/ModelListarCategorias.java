package com.livraria.fronteira;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.livraria.entidades.Categoria;

@SuppressWarnings("serial")
public class ModelListarCategorias extends DefaultTableModel
{
	private static final int COLUNA_Tema = 1;

	private static final String COLUNS[] = new String[]
	{
		"Tema"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class
	};

	private List<Categoria> autores = new ArrayList<Categoria>();

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
		return autores == null ? 0 : autores.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Categoria autor = autores.get(row);

		if (autor != null)
			switch (column)
			{
				case COLUNA_Tema: return autor.getTema();
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
		return autores.get(linha);
	}

	public void atualizarLista(List<Categoria> lista)
	{
		autores = lista;

		fireTableDataChanged();
	}
}
