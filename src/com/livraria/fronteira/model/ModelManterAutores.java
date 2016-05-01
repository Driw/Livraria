package com.livraria.fronteira.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.diverproject.util.DateUtil;

import com.livraria.entidades.Autor;

@SuppressWarnings("serial")
public class ModelManterAutores extends DefaultTableModel
{
	private static final int COLUNA_NOME = 0;
	private static final int COLUNA_NASCIMENTO = 1;
	private static final int COLUNA_FALECIMENTO = 2;
	private static final int COLUNA_LOCAL_MORTE = 3;
	private static final int COLUNA_BIOGRAFIA = 4;

	private static final String COLUNS[] = new String[]
	{
		"Nome", "D. Nascimento", "D. Falecimento", "L. da Morte", "Biografia"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class, String.class, String.class, String.class, String.class
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
				case COLUNA_NASCIMENTO:
					return autor.getNascimento() == null ? "-" : DateUtil.toString(autor.getNascimento());

				case COLUNA_FALECIMENTO:
					return autor.getFalecimento() == null ? "-" : DateUtil.toString(autor.getFalecimento());

				case COLUNA_LOCAL_MORTE: return autor.getLocalMorte();
				case COLUNA_BIOGRAFIA:
					if (autor.getBiografia() == null)
						return "-";
					else if (autor.getBiografia().length() < 60)
						return autor.getBiografia();
					return autor.getBiografia().substring(0, 60);
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
		if (linha >= 0 && linha < autores.size())
			return autores.get(linha);

		return null;
	}

	public void removerLinha(int linha)
	{
		autores.remove(linha);

		fireTableDataChanged();
	}

	public void atualizarLista(List<Autor> lista)
	{
		autores = lista;

		fireTableDataChanged();
	}

	public void remover(Autor autor)
	{
		for (int i = 0; i < autores.size(); i++)
			if (autores.get(i).equals(autor))
			{
				autores.remove(i);
				fireTableDataChanged();

				return;
			}
	}
}
