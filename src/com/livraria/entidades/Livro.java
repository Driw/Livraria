
package com.livraria.entidades;

import java.util.Date;

public class Livro
{
	private int id;
	private String isbn;
	private int categoria;
	private String titulo;
	private float preco;
	private float desconto;
	private Date publicacao;
	private Editora editora;
	private LivroAutores autores;

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getIsbn()
	{
		return isbn;
	}

	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	public int getCategoria()
	{
		return categoria;
	}

	public void setCategoria(int categoria)
	{
		this.categoria = categoria;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public float getPreco()
	{
		return preco;
	}

	public void setPreco(float preco)
	{
		this.preco = preco;
	}

	public float getDesconto()
	{
		return desconto;
	}

	public void setDesconto(float desconto)
	{
		this.desconto = desconto;
	}

	public Date getPublicacao()
	{
		return publicacao;
	}

	public void setPublicacao(Date publicacao)
	{
		this.publicacao = publicacao;
	}

	public Editora getEditora()
	{
		return editora;
	}

	public void setEditora(Editora editora)
	{
		this.editora = editora;
	}

	public LivroAutores getLivroAutores()
	{
		return autores == null ? (autores = new LivroAutores(this)) : autores;
	}
}
