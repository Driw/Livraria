package com.livraria.fronteira.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import com.livraria.entidades.CarrinhoItem;
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class ModelCarrinhoItem extends DefaultTableModel
{
	private static final int COLUNA_QUANTIDADE = 0;
	private static final int COLUNA_ISBN = 1;
	private static final int COLUNA_TITULO = 2;
	private static final int COLUNA_EDITORA = 3;
	private static final int COLUNA_PRECO = 4;
	private static final int COLUNA_PRECO_TOTAL = 5;

	private static final String COLUNS[] = new String[]
	{
		"Quantidade", "ISBN", "Nome", "Editora", "Preço Unid.", "Preço"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		Integer.class, String.class, String.class, String.class, String.class, String.class
	};

	private List<CarrinhoItem> itens = new ArrayList<CarrinhoItem>();

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
		return itens == null ? 0 : itens.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		CarrinhoItem item = itens.get(row);

		if (item != null)
			switch (column)
			{
				case COLUNA_QUANTIDADE: return item.getQuantidade();
				case COLUNA_ISBN: return ComponentUtil.isbnFormmat(item.getLivro().getIsbn());
				case COLUNA_TITULO: return item.getLivro().getTitulo();
				case COLUNA_EDITORA: return item.getLivro().getEditora().getNome();
				case COLUNA_PRECO: return String.format(Locale.US, "R$ %3.2f", (float) item.getLivro().getPreco());
				case COLUNA_PRECO_TOTAL: return String.format(Locale.US, "R$ %3.2f", (float) item.getQuantidade() * item.getLivro().getPreco());
			}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1)
	{
		return false;
	}

	public CarrinhoItem getLinha(int linha)
	{
		return itens.get(linha);
	}

	public void atualizarLista(List<CarrinhoItem> lista)
	{
		itens = lista;

		fireTableDataChanged();
	}
}
