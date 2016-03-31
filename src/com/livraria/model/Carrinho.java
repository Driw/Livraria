
package com.livraria.model;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;

public class Carrinho
{
	private int id;
	private List<CarrinhoItem> items;

	public Carrinho()
	{
		this.items = new DynamicList<CarrinhoItem>();
	}

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public Livro getLivro(int indice)
	{
		return items.get(indice).getLivro();
	}

	public void adicionar(int quantidade, Livro livro)
	{
		for (CarrinhoItem item : items)
			if (item.getLivro().equals(livro))
				item.aumentarQuantidade(quantidade);

		CarrinhoItem item = new CarrinhoItem();
		item.setLivro(livro);
		item.setQuantidade(quantidade);

		items.add(item);
	}

	public boolean remover(int quantidade, Livro livro)
	{
		for (CarrinhoItem item : items)
			if (item.getLivro().equals(livro))
				return item.diminuirQuantidade(quantidade);

		return false;
	}

	public void atualizar(int quantidade, Livro livro)
	{
		for (CarrinhoItem item : items)
			if (item.getLivro().equals(livro))
				item.setQuantidade(quantidade);
	}

	public Livro[] listar()
	{
		Livro livros[] = new Livro[items.size()];

		for (int i = 0; i < livros.length; i++)
			livros[i] = this.items.get(i).getLivro();

		return livros;
	}
}
