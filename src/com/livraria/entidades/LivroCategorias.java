
package com.livraria.entidades;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;

public class LivroCategorias
{
	private List<Categoria> categorias;

	public LivroCategorias()
	{
		this.categorias = new DynamicList<Categoria>();
	}

	public int tamanho()
	{
		return categorias.size();
	}

	public boolean adicionar(Categoria categoria)
	{
		if (categorias.contains(categoria))
			return false;

		return categorias.add(categoria);
	}

	public boolean remover(Categoria categoria)
	{
		return categorias.remove(categoria);
	}

	public Categoria obter(int categoriaID)
	{
		for (Categoria autor : categorias)
			if (autor.getID() == categoriaID)
				return autor;

		return null;
	}

	public Categoria[] listar()
	{
		Categoria lista[] = new Categoria[categorias.size()];

		for (int i = 0; i < lista.length; i++)
			lista[i] = categorias.get(i);

		return lista;
	}

	public void limpar()
	{
		categorias.clear();
	}
}
