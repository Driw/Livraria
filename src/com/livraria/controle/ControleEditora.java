
package com.livraria.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.entidades.Editora;

public class ControleEditora
{
	private static final Connection connection;
	
	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}
	
	public boolean adicionar(Editora editora) throws SQLException
	{
		String sql = "INSERT INTO editoras (cnpj, nome, endereco, telefone) values (?, ?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, editora.getCnpj());
		ps.setString(2, editora.getNome());
		ps.setString(3, editora.getEndereco());
		ps.setString(4, editora.getTelefone());

		return ps.execute();
	}

	public boolean atualizar(Editora editora) throws SQLException
	{
		String sql = "UPDATE editoras SET cnpj = ?, nome = ?, endereco = ?, "
					+ "telefone = ? WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, editora.getCnpj());
		ps.setString(2, editora.getNome());
		ps.setString(3, editora.getEndereco());
		ps.setString(4, editora.getTelefone());
		ps.setInt(5, editora.getID());

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public boolean excluir(int id) throws SQLException
	{
		String sql = "DELETE FROM editoras WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public Editora selecionar(int id) throws SQLException
	{
		Editora editora = new Editora();

		String sql = "SELECT * FROM editoras WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		editora.setID(id);
		editora.setCnpj(rs.getString("cnpj"));
		editora.setNome(rs.getString("nome"));
		editora.setEndereco(rs.getString("enredeco"));
		editora.setTelefone(rs.getString("telefone"));

		return editora;
	}

	public List<Editora> listar() throws SQLException
	{
		List<Editora> editoras = new ArrayList<Editora>();

		String sql = "SELECT * FROM editoras";
		PreparedStatement ps = connection.prepareStatement(sql);
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

		return editoras;
	}

	public Vector<Editora> filtrarPorCNPJ(String cnpj) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE cnpj LIKE '?%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, cnpj);

		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public Vector<Editora> filtrarPorNome(String nome) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE nome = '%?%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, nome);

		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public Vector<Editora> filtrarPorTelefone(String telefone) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE telefone LIKE '?%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, telefone);

		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public Vector<Editora> concluirFiltragem(ResultSet rs) throws SQLException
	{
		Vector<Editora> editoras = new Vector<Editora>();

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

		return editoras;
	}
	
	public int getId(String nome) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT id FROM editoras WHERE nome LIKE '%?%'");
		ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();
		return rs.getInt("id");
	}
	
	
}
