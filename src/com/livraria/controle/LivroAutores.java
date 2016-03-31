package com.livraria.controle;

import com.livraria.model.Autor;

public class LivroAutores {
	
	public int tamanho() {
		return 0;
	}
	
	public boolean adicionar() {
		return false;
	}
	
	public boolean remover() {
		return false;
	}
	
	public Autor obter() {
		return new Autor();
	}
	
	public Autor[] listar() {
		return new Autor[tamanho()];
	}
	
}
