
package com.livraria.entidades;

import java.util.Date;

import org.diverproject.util.DateUtil;

public class Autor
{
	private int id;
	private String nome;
	private Date nascimento;
	private Date falecimento;
	private String localMorte;
	private String biografia;

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Date getNascimento()
	{
		return nascimento;
	}

	public void setNascimento(Date nascimento)
	{
		this.nascimento = nascimento;
	}

	public Date getFalecimento()
	{
		return falecimento;
	}

	public void setFalecimento(Date falecimento)
	{
		this.falecimento = falecimento;
	}

	public String getLocalMorte()
	{
		return localMorte;
	}

	public void setLocalMorte(String localMorte)
	{
		this.localMorte = localMorte;
	}

	public String getBiografia()
	{
		return biografia;
	}

	public void setBiografia(String biografia)
	{
		this.biografia = biografia;
	}

	public int getIdade()
	{
		Date inicio = this.nascimento;
		Date fim = this.falecimento;

		if (inicio == null)
			return 0;

		if (fim == null)
			fim = new Date(System.currentTimeMillis());

		int idade = DateUtil.getYear(fim) - DateUtil.getYear(inicio);

		return idade;
	}

	public void copiar(Autor autor)
	{
		this.id = autor.id;
		this.nome = autor.nome;
		this.nascimento = autor.nascimento == null ? null : new Date(autor.nascimento.getTime());
		this.falecimento = autor.falecimento == null ? null : new Date(autor.falecimento.getTime());
		this.localMorte = autor.localMorte;
		this.biografia = autor.biografia;
	}
}
