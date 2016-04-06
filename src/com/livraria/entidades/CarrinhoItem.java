package com.livraria.entidades;

public class CarrinhoItem
{
	private int quantidade;
	private Livro livro;

	public int getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(int quantidade)
	{
		this.quantidade = quantidade;
	}

	public void aumentarQuantidade(int quantidade)
	{
		this.quantidade += quantidade;
	}

	public boolean diminuirQuantidade(int quantidade)
	{
		if (this.quantidade < quantidade)
			return false;

		this.quantidade -= quantidade;

		return true;
	}

	public Livro getLivro()
	{
		return livro;
	}

	public void setLivro(Livro livro)
	{
		this.livro = livro;
	}
}
