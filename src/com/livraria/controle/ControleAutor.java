
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
import com.livraria.model.Autor;

public class ControleAutor
{
	private static final MySQL mysql;
	private static Conexao conexao;
	private static final Connection c;
	
	static
	{
		mysql = conexao.getMySQL();
		c = mysql.getConnection();
	}
	
	public boolean adicionar(Autor a)
	{
		boolean validar = false;
		Date sd = new Date(a.getNascimento().getTime());
		try
		{
			String sql = "INSERT INTO autores values (?, ?, ?, ?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, a.getNome());
			ps.setDate(2, sd);
			sd.setTime(a.getFalecimento().getTime());
			ps.setDate(3, sd);
			ps.setString(4, a.getLocalMorte());
			ps.setString(5, a.getBiografia());
			validar = ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return validar;
	}

	public boolean atualizar(Autor a)
	{
		boolean validar = false;
		Date sd = new Date(a.getNascimento().getTime());
		try
		{
			String sql = "UPDATE autores SET nome = ?, nascimento = ?, falecimento = ?, "
					+ "localMorte = ?, biografia = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, a.getNome());
			ps.setDate(2, sd);
			sd.setTime(a.getFalecimento().getTime());
			ps.setDate(3, sd);
			ps.setString(4, a.getLocalMorte());
			ps.setString(5, a.getBiografia());
			ps.setInt(6, a.getID());
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
			String sql = "DELETE FROM autores WHERE id = ?";
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

	public Autor selecionar(int id)
	{
		Autor autor = new Autor();
		try
		{
			String sql = "SELECT * FROM autores WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			autor.setID(id);
			autor.setNome(rs.getString("nome"));
			autor.setNascimento(rs.getDate("nascimento"));
			autor.setFalecimento(rs.getDate("falecimento"));
			autor.setLocalMorte(rs.getString("localMorte"));
			autor.setBiografia(rs.getString("biografia"));
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return autor;
	}

	public List<Autor> listar()
	{
		List<Autor> autores = new ArrayList<Autor>();
		
		try
		{
			String sql = "SELECT * FROM autores";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Autor autor = new Autor();
				autor.setID(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
				autor.setNascimento(rs.getDate("nascimento"));
				autor.setFalecimento(rs.getDate("falecimento"));
				autor.setLocalMorte(rs.getString("localMorte"));
				autor.setBiografia(rs.getString("biografia"));
				autores.add(autor);
			}
			
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return autores;
	}
}
