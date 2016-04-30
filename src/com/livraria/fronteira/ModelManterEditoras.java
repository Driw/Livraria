package com.livraria.fronteira;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.diverproject.util.DateUtil;

import com.livraria.entidades.Editora;
import com.livraria.util.ComponentUtil;

@SuppressWarnings("serial")
public class ModelManterEditoras extends DefaultTableModel
{
	private static final int COLUNA_NOME = 0;
	private static final int COLUNA_CNPJ = 1;
	private static final int COLUNA_TELEFONE = 2;
	private static final int COLUNA_CONTRATO = 3;

	private static final String COLUNS[] = new String[]
	{
		"Nome", "CNPJ", "Telefone", "Duração do Contrato"
	};

	private static final Class<?> COLUNS_TYPE[] = new Class[]
	{
		String.class, String.class, String.class, String.class
	};

	private List<Editora> editoras = new ArrayList<Editora>();

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
		return editoras == null ? 0 : editoras.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Editora editora = editoras.get(row);

		if (editora != null)
			switch (column)
			{
				case COLUNA_NOME: return editora.getNome();
				case COLUNA_CNPJ:
					String cnpj = ComponentUtil.cnpjFormmat(editora.getCnpj());
					return cnpj == null ? "-" : cnpj;

				case COLUNA_TELEFONE:
					String telefone = ComponentUtil.telefoneFormmat(editora.getTelefone());
					return telefone == null ? "-" : telefone;

				case COLUNA_CONTRATO:
					String inicio = DateUtil.toString(editora.getContratoInicio());
					String fim = DateUtil.toString(editora.getContratoFim());
					return String.format("%s a %s", inicio, fim);
			}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1)
	{
		return false;
	}

	public Editora getLinha(int linha)
	{
		if (linha >= 0 && linha < editoras.size())
			return editoras.get(linha);

		return null;
	}
	
	public void removerLinha(int linha)
	{
		editoras.remove(linha);

		fireTableDataChanged();
	}

	public void atualizarLista(List<Editora> lista)
	{
		editoras = lista;

		fireTableDataChanged();
	}

	public void remover(Editora editora)
	{
		for (int i = 0; i < editoras.size(); i++)
			if (editoras.get(i).equals(editora))
			{
				editoras.remove(i);
				fireTableDataChanged();

				return;
			}
	}
}
