
package com.livraria.controle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.model.Editora;
import com.livraria.model.Livro;

public class ControleLivro
{
	private static final MySQL mysql;
	private static Conexao conexao;
	private static final Connection c;
	
	static
	{
		mysql = conexao.getMySQL();
		c = mysql.getConnection();
	}
	
	public boolean adicionar(Livro l)
	{
		boolean validar = false;
		Date sd = new Date(l.getPublicacao().getTime());
		try
		{
			String sql = "INSERT INTO livros values (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, l.getIsbn());
			ps.setInt(2, l.getCategoria());
			ps.setString(3, l.getTitulo());
			ps.setInt(4, l.getEditora().getID());
			ps.setFloat(5, l.getPreco());
			ps.setDate(6, sd);
			validar = ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return validar;
	}

	public boolean atualizar(Livro l)
	{
		boolean validar = false;
		Date sd = new Date(l.getPublicacao().getTime());
		try
		{
			String sql = "UPDATE livros SET isbn = ?, categoria = ?, titulo = ?, "
					+ "editora = ?, preco = ?, publicacao = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, l.getIsbn());
			ps.setInt(2, l.getCategoria());
			ps.setString(3, l.getTitulo());
			ps.setInt(4, l.getEditora().getID());
			ps.setFloat(5, l.getPreco());
			ps.setDate(6, sd);
			ps.setInt(7, l.getID());
			validar = ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return validar;
	}

	public boolean excluir(int id)
	{
		boolean validar = false;
		try
		{
			String sql = "DELETE FROM livros WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			validar = ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return validar;
	}

	public Livro selecionar(int id)
	{
		Livro livro = new Livro();
		try
		{
			String sql = "SELECT * FROM livros WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			livro.setID(id);
			livro.setIsbn(rs.getString("isbn"));
			livro.setCategoria(rs.getInt("categoria"));
			livro.setTitulo(rs.getString("titulo"));
			livro.setEditora(getEditora(rs.getInt("editora")));
			livro.setPreco(rs.getFloat("preco"));
			livro.setPublicacao(rs.getDate("publicacao"));
			
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return livro;
	}

	public List<Livro> listar()
	{
		List<Livro> livros = new ArrayList<Livro>();
		
		try
		{
			String sql = "SELECT * FROM livros";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Livro livro = new Livro();
				livro.setID(rs.getInt("id"));
				livro.setIsbn(rs.getString("isbn"));
				livro.setCategoria(rs.getInt("categoria"));
				livro.setTitulo(rs.getString("titulo"));
				livro.setEditora(getEditora(rs.getInt("editora")));
				livro.setPreco(rs.getFloat("preco"));
				livro.setPublicacao(rs.getDate("publicacao"));
				livros.add(livro);
			}
			
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return livros;
	}
	
	public Editora getEditora(int id)
	{
		Editora editora = new Editora();
		try
		{
			String sql = "SELECT * FROM editoras WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			editora.setID(id);
			editora.setCnpj(rs.getString("cnpj"));
			editora.setNome(rs.getString("nome"));
			editora.setEndereco(rs.getString("enredeco"));
			editora.setTelefone(rs.getString("telefone"));
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return editora;
	}
}
