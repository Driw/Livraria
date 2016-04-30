package com.livraria.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.entidades.Categoria;

public class ControleCategoria {
	
	private static final Connection connection;
	
	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}
	
	public boolean adicionar(Categoria categoria) throws SQLException
	{
		String sql = "INSERT INTO cdus (codigo, tema) values (?, ?)";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, categoria.getCodigo());
		ps.setString(2, categoria.getTema());
		
		return ps.executeUpdate() == Conexao.INSERT_SUCCESSFUL;
	}
	
	public boolean atualizar(Categoria categoria) throws SQLException
	{
		
		String sql = "UPDATE cdus SET codigo = ?, tema = ? WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, categoria.getCodigo());
		ps.setString(2, categoria.getTema());
		ps.setInt(3, categoria.getID());

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}
	
	public boolean excluir(int id) throws SQLException
	{
		String sql = "DELETE FROM cdus WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public Categoria selecionar(int id) throws SQLException
	{
		String sql = "SELECT * FROM cdus WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		Categoria categoria = null;

		if (rs.next())
			categoria = criar(rs);

		return categoria;
	}
	
	public List<Categoria> listar() throws SQLException
	{
		String sql = "SELECT * FROM cdus";
		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		List<Categoria> categorias = concluirFiltragem(rs);

		return categorias;
	}

	public List<Categoria> filtrarPorCodigo(String codigo) throws SQLException
	{
		String sql = "SELECT * FROM cdus WHERE codigo LIKE '%"+codigo+"%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}
	
	public List<Categoria> filtrarPorTema(String tema) throws SQLException
	{
		String sql = "SELECT * FROM cdus WHERE tema LIKE '%"+tema+"%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public List<Categoria> concluirFiltragem(ResultSet rs) throws SQLException
	{
		List<Categoria> categorias = new ArrayList<Categoria>();

		while (rs.next())
		{
			Categoria categoria = criar(rs);
			categorias.add(categoria);
		}

		return categorias;
	}

	private Categoria criar(ResultSet rs) throws SQLException
	{
		Categoria categoria = new Categoria();
		categoria.setID(rs.getInt("id"));
		categoria.setCodigo(rs.getString("codigo"));
		categoria.setTema(rs.getString("tema"));

		return categoria;
	}

	public boolean existe(String tema) throws SQLException
	{
		String sql = "SELECT COUNT(*) as count FROM cdus WHERE tema = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, tema);

		ResultSet rs = ps.executeQuery();
		rs.next();

		return rs.getInt("count") != 0;
	}
}