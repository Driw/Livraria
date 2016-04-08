
package com.livraria.entidades;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;

public class LivroAutores
{
	private List<Autor> autores;

	public LivroAutores()
	{
		this.autores = new DynamicList<Autor>();
	}

	public int tamanho()
	{
		return autores.size();
	}

	public boolean adicionar(Autor autor)
	{
		if (autores.contains(autor))
			return false;

		return autores.add(autor);
	}

	public boolean remover(Autor autor)
	{
		return autores.remove(autor);
	}

	public Autor obter(int autorID)
	{
		for (Autor autor : autores)
			if (autor.getID() == autorID)
				return autor;

		return null;
	}

	public Autor[] listar()
	{
		Autor lista[] = new Autor[autores.size()];

		for (int i = 0; i < lista.length; i++)
			lista[i] = autores.get(i);

		return lista;
	}

	public void limpar()
	{
		autores.clear();
	}
}
