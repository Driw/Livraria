package com.livraria.entidades;

import java.util.Date;

public class Categoria
{
	private int id;
	private String codigo;
	private String tema;

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public String getTema()
	{
		return tema;
	}

	public void setTema(String tema)
	{
		this.tema = tema;
	}
	
	public void copiar(Categoria categoria)
	{
		this.id = categoria.id;
		this.codigo = categoria.codigo;
		this.tema = categoria.tema;
	}
}
