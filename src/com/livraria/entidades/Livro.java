
package com.livraria.entidades;

import java.util.Date;

public class Livro
{
	private int id;
	private String isbn;
	private String titulo;
	private float preco;
	private Date publicacao;
	private int paginas;
	private int capa;
	private String resumo;
	private String sumario;
	private float precoCusto;
	private float margemLucro;
	private Editora editora;
	private LivroAutores autores;
	private LivroCategorias categorias;

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

	public int getPaginas()
	{
		return paginas;
	}

	public void setPaginas(int paginas)
	{
		this.paginas = paginas;
	}

	public int getCapa()
	{
		return capa;
	}

	public void setCapa(int capa)
	{
		this.capa = capa;
	}

	public String getResumo()
	{
		return resumo;
	}

	public void setResumo(String resumo)
	{
		this.resumo = resumo;
	}

	public String getSumario()
	{
		return sumario;
	}

	public void setSumario(String sumario)
	{
		this.sumario = sumario;
	}

	public float getPrecoCusto()
	{
		return precoCusto;
	}

	public void setPrecoCusto(float precoCusto)
	{
		this.precoCusto = precoCusto;
	}

	public float getMargemLucro()
	{
		return margemLucro;
	}

	public void setMargemLucro(float margemLucro)
	{
		this.margemLucro = margemLucro;
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
		return autores == null ? (autores = new LivroAutores()) : autores;
	}

	public LivroCategorias getLivroCategorias()
	{
		return categorias == null ? (categorias = new LivroCategorias()) : categorias;
	}
}
