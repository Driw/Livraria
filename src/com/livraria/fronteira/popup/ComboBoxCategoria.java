package com.livraria.fronteira.popup;

import com.livraria.entidades.Categoria;

public class ComboBoxCategoria extends ComboBoxItem<Categoria>
{
	public ComboBoxCategoria(Categoria categoria)
	{
		super(categoria);
	}

	@Override
	public String toString(Categoria categoria)
	{
		return categoria.getTema();
	}
}
