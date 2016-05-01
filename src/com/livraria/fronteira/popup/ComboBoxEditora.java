package com.livraria.fronteira.popup;

import com.livraria.entidades.Editora;

public class ComboBoxEditora extends ComboBoxItem<Editora>
{
	public ComboBoxEditora(Editora editora)
	{
		super(editora);
	}

	@Override
	public String toString(Editora editora)
	{
		return editora.getNome();
	}
}
