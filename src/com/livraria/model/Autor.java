package com.livraria.model;

import java.util.Date;

public class Autor {
	private String nome;
	private Date nascimento;
	private Date falecimento;
	private String localMorte;
	private String biografia;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	public Date getFalecimento() {
		return falecimento;
	}
	public void setFalecimento(Date falecimento) {
		this.falecimento = falecimento;
	}
	public String getLocalMorte() {
		return localMorte;
	}
	public void setLocalMorte(String localMorte) {
		this.localMorte = localMorte;
	}
	public String getBiografia() {
		return biografia;
	}
	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	
	public int getIdade() {
		return 0;
	}
}
