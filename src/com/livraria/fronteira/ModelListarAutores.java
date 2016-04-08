package com.livraria.fronteira;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.livraria.entidades.Autor;

@SuppressWarnings("serial")
public class ModelListarAutores extends DefaultTableModel
{
	private static final int COLUNA_NOME = 1;

	private static final String COLUNS[] = new String[]
	{
		"Nome"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class
	};

	private List<Autor> autores = new ArrayList<Autor>();

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
		Autor autor = autores.get(row);

		if (autor != null)
			switch (column)
			{
				case COLUNA_NOME: return autor.getNome();
			}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1)
	{
		return false;
	}

	public Autor getLinha(int linha)
	{
		return autores.get(linha);
	}

	public void atualizarLista(List<Autor> lista)
	{
		autores = lista;

		fireTableDataChanged();
	}
}
