
package com.livraria.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.model.Editora;

public class ControleEditora
{
	
	private static final MySQL mysql;
	private static Conexao conexao;
	private static final Connection c;
	
	static
	{
		mysql = conexao.getMySQL();
		c = mysql.getConnection();
	}
	
	public boolean adicionar(Editora e)
	{
		boolean validar = false;
		try
		{
			String sql = "INSERT INTO editoras values (?, ?, ?, ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, e.getCnpj());
			ps.setString(2, e.getNome());
			ps.setString(3, e.getEndereco());
			ps.setString(4, e.getTelefone());
			validar = ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return validar;
	}

	public boolean atualizar(Editora e)
	{
		boolean validar = false;
		try
		{
			String sql = "UPDATE editoras SET cnpj = ?, nome = ?, endereco = ?, "
					+ "telefone = ? WHERE id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, e.getCnpj());
			ps.setString(2, e.getNome());
			ps.setString(3, e.getEndereco());
			ps.setString(4, e.getTelefone());
			ps.setInt(5, e.getID());
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
			String sql = "DELETE FROM editoras WHERE id = ?";
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

	public Editora selecionar(int id)
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

	public List<Editora> listar()
	{
		List<Editora> editoras = new ArrayList<Editora>();
		
		try
		{
			String sql = "SELECT * FROM editoras";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Editora editora = new Editora();
				editora.setID(rs.getInt("id"));
				editora.setCnpj(rs.getString("cnpj"));
				editora.setNome(rs.getString("nome"));
				editora.setEndereco(rs.getString("enredeco"));
				editora.setTelefone(rs.getString("telefone"));
				editoras.add(editora);
			}
			
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		return editoras;
	}
}
