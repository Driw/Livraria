
package com.livraria.entidades;

import java.util.Date;

import org.diverproject.util.collection.List;
import org.diverproject.util.collection.abstraction.DynamicList;

public class Carrinho
{
	public static final int CARRINHO_CRIADO = 1;
	public static final int CARRINHO_GUARDADO = 2;
	public static final int CARRINHO_ENVIADO = 3;
	public static final int CARRINHO_EM_ESPERA = 4;
	public static final int CARRINHO_CONCLUIDO = 5;

	private int numeroPedido;
	private Date criado;
	private Date concluido;
	private int estado;
	private List<CarrinhoItem> items;

	public Carrinho()
	{
		this.items = new DynamicList<CarrinhoItem>();
	}

	public int getID()
	{
		return numeroPedido;
	}

	public void setID(int id)
	{
		this.numeroPedido = id;
	}

	public Date getCriado()
	{
		return criado;
	}

	public void setCriado(Date criado)
	{
		this.criado = criado;
	}

	public Date getConcluido()
	{
		return concluido;
	}

	public void setConcluido(Date concluido)
	{
		this.concluido = concluido;
	}

	public int getEstado()
	{
		return estado;
	}

	public void setEstado(int estado)
	{
		this.estado = estado;
	}

	public Livro getLivro(int indice)
	{
		return items.get(indice).getLivro();
	}

	public int getQuantidade(int indice)
	{
		return items.get(indice).getQuantidade();
	}

	public void adicionar(int quantidade, Livro livro)
	{
		for (CarrinhoItem item : items)
			if (item.getLivro().equals(livro))
			{
				item.aumentarQuantidade(quantidade);
				return;
			}

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

	public int getNumeroItens()
	{
		return items.size();
	}
}
