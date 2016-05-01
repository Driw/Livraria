package com.livraria.fronteira.model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import com.livraria.entidades.Categoria;

@SuppressWarnings("serial")
public class ModelManterCategorias extends DefaultTableModel
{
	private static final int COLUNA_CODIGO = 0;
	private static final int COLUNA_TEMA = 1;
	
	private static final String COLUNS[] = new String[]
	{
		"Código", "Tema"
	};
	
	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class, String.class
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
				case COLUNA_CODIGO: return categoria.getCodigo();
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
		if (linha >= 0 && linha < categorias.size())
			return categorias.get(linha);

		return null;
	}
	
	public void removerLinha(int linha)
	{
		categorias.remove(linha);

		fireTableDataChanged();
	}

	public void atualizarLista(List<Categoria> lista)
	{
		categorias = lista;

		fireTableDataChanged();
	}

	public void remover(Categoria categoria)
	{
		for (int i = 0; i < categorias.size(); i++)
			if (categorias.get(i).equals(categoria))
			{
				categorias.remove(i);
				fireTableDataChanged();

				return;
			}
	}
}