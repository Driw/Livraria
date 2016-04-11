
package com.livraria.entidades;

import java.util.Date;

public class Editora
{
	private int id;
	private String cnpj;
	private String nome;
	private String endereco;
	private String telefone;
	private Date contratoInicio;
	private Date contratoFim;

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getCnpj()
	{
		return cnpj;
	}

	public void setCnpj(String cnpj)
	{
		this.cnpj = cnpj;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getEndereco()
	{
		return endereco;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	public String getTelefone()
	{
		return telefone;
	}

	public void setTelefone(String telefone)
	{
		this.telefone = telefone;
	}

	public Date getContratoInicio()
	{
		return contratoInicio;
	}

	public Date getContratoFim()
	{
		return contratoFim;
	}

	public void setContrato(Date contratoInicio, Date contratoFim)
	{
		if (contratoInicio != null && contratoFim != null)
		{
			this.contratoInicio = contratoInicio;
			this.contratoFim = contratoFim;
		}
	}

	public boolean validarCNPJ()
	{
		// TODO

		return true;
	}

	public boolean hasContrato()
	{
		return contratoInicio != null && contratoFim != null;
	}

	public int getDuracaoContrato()
	{
		return !hasContrato() ? 0 : (int) (contratoFim.getTime() - contratoInicio.getTime() / 86400);
	}
}
