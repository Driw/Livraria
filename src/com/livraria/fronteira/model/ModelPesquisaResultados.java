package com.livraria.fronteira.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import com.livraria.entidades.Livro;
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class ModelPesquisaResultados extends DefaultTableModel
{
	private static final int COLUNA_ISBN = 0;
	private static final int COLUNA_TITULO = 1;
	private static final int COLUNA_PRECO = 2;
	private static final int COLUNA_PAGINAS = 3;
	private static final int COLUNA_EDITORA = 4;
	private static final int COLUNA_PRIMEIRO_AUTOR = 5;

	private static final String COLUNS[] = new String[]
	{
		"ISBN", "Nome", "Preço", "Pag.", "Editora", "Autor(es)"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class, String.class, String.class, Integer.class, String.class, String.class
	};

	private List<Livro> livros = new ArrayList<Livro>();

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
		return livros == null ? 0 : livros.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Livro livro = livros.get(row);

		if (livro != null)
			switch (column)
			{
				case COLUNA_ISBN: return ComponentUtil.isbnFormmat(livro.getIsbn());
				case COLUNA_TITULO: return livro.getTitulo();
				case COLUNA_PRECO: return String.format(Locale.US, "R$ %3.2f", (float) livro.getPreco());
				case COLUNA_PAGINAS: return livro.getPaginas();
				case COLUNA_EDITORA: return livro.getEditora().getNome();
				case COLUNA_PRIMEIRO_AUTOR:
					String autores = livro.getLivroAutores().obter(0).getNome();
						for (int i = 1; i < livro.getLivroAutores().tamanho(); i++)
							autores += ", " +livro.getLivroAutores().obter(i).getNome();
					return autores;
			}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1)
	{
		return false;
	}

	public Livro getLinha(int linha)
	{
		if (linha >= 0 && linha < livros.size())
			return livros.get(linha);

		return null;
	}

	public void atualizarLista(List<Livro> lista)
	{
		livros = lista;

		fireTableDataChanged();
	}
}
