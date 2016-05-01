package com.livraria.fronteira.popup;

import com.livraria.entidades.Autor;

public class ComboBoxAutor extends ComboBoxItem<Autor>
{
	public ComboBoxAutor(Autor autor)
	{
		super(autor);
	}

	@Override
	public String toString(Autor autor)
	{
		return autor.getNome();
	}
}
