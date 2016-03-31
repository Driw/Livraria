package com.livraria.model;

import java.util.Date;

import com.livraria.controle.LivroAutores;

public class Livro {
	private String isbn;
	private int categoria;
	private String titulo;
	private float preco;
	private Date publicacao;
	private Editora editora;
	private Autor autor;
	private LivroAutores livrosPorAutor;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public Date getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}
	public Editora getEditora() {
		return editora;
	}
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	public LivroAutores getLivrosPorAutor() {
		return livrosPorAutor;
	}
	public void setLivrosPorAutor(LivroAutores livrosPorAutor) {
		this.livrosPorAutor = livrosPorAutor;
	}
	
}
