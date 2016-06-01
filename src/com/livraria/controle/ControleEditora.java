
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
import com.livraria.entidades.Editora;
import com.livraria.util.ComponentUtil;

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
		Date contratoInicio = null;
		Date contratoFim = null;

		if (editora.getContratoInicio() != null)
			contratoInicio = new Date(editora.getContratoInicio().getTime());

		if (editora.getContratoFim() != null)
			contratoFim = new Date(editora.getContratoFim().getTime());

		String sql = "INSERT INTO editoras (cnpj, nome, endereco, telefone, contrato_inicio, contrato_fim) "
					+"values (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, ComponentUtil.cnpjClear(editora.getCnpj()));
		ps.setString(2, editora.getNome());
		ps.setString(3, editora.getEndereco());
		ps.setString(4, ComponentUtil.telefoneClear(editora.getTelefone()));
		ps.setDate(5, contratoInicio);
		ps.setDate(6, contratoFim);

		return ps.executeUpdate() == Conexao.INSERT_SUCCESSFUL;
	}

	public boolean atualizar(Editora editora) throws SQLException
	{
		Date contratoInicio = null;
		Date contratoFim = null;

		if (editora.getContratoInicio() != null)
			contratoInicio = new Date(editora.getContratoInicio().getTime());

		if (editora.getContratoFim() != null)
			contratoFim = new Date(editora.getContratoFim().getTime());

		String sql = "UPDATE editoras SET cnpj = ?, nome = ?, endereco = ?, telefone = ?, "
					+"contrato_inicio = ?, contrato_fim = ? WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, ComponentUtil.cnpjClear(editora.getCnpj()));
		ps.setString(2, editora.getNome());
		ps.setString(3, editora.getEndereco());
		ps.setString(4, ComponentUtil.telefoneClear(editora.getTelefone()));
		ps.setDate(5, contratoInicio);
		ps.setDate(6, contratoFim);
		ps.setInt(7, editora.getID());

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public boolean excluir(Editora editora) throws SQLException
	{
		String sql = "DELETE FROM editoras WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, editora.getID());

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public Editora selecionar(int id) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		Editora editora = null;

		if (rs.next())
			editora = criar(rs);

		return editora;
	}

	public List<Editora> listar() throws SQLException
	{
		String sql = "SELECT * FROM editoras";
		PreparedStatement ps = connection.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		List<Editora> editoras = concluirFiltragem(rs);

		return editoras;
	}

	public List<Editora> filtrarPorCNPJ(String cnpj) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE cnpj LIKE '%" +cnpj+ "%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public List<Editora> filtrarPorNome(String nome) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE nome LIKE '%" +nome+ "%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	public List<Editora> filtrarPorTelefone(String telefone) throws SQLException
	{
		String sql = "SELECT * FROM editoras WHERE telefone LIKE '%" +telefone+ "%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return concluirFiltragem(rs);
	}

	private List<Editora> concluirFiltragem(ResultSet rs) throws SQLException
	{
		List<Editora> editoras = new ArrayList<Editora>();

		while (rs.next())
		{
			Editora editora = criar(rs);
			editoras.add(editora);
		}

		return editoras;
	}

	private Editora criar(ResultSet rs) throws SQLException
	{
		java.util.Date contratoInicio = null;
		java.util.Date contratoFim = null;

		if (rs.getDate("contrato_inicio") != null)
			contratoInicio = new java.util.Date(rs.getDate("contrato_inicio").getTime());

		if (rs.getDate("contrato_fim") != null)
			contratoFim = new java.util.Date(rs.getDate("contrato_fim").getTime());

		Editora editora = new Editora();
		editora.setID(rs.getInt("id"));
		editora.setCnpj(rs.getString("cnpj"));
		editora.setNome(rs.getString("nome"));
		editora.setEndereco(rs.getString("endereco"));
		editora.setTelefone(rs.getString("telefone"));
		editora.setContrato(contratoInicio, contratoFim);

		return editora;
	}

	public boolean existe(String nome) throws SQLException
	{
		String sql = "SELECT COUNT(*) as count FROM editoras WHERE nome = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, nome);

		ResultSet rs = ps.executeQuery();
		rs.next();

		return rs.getInt("count") != 0;
	}

	public void truncate() throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("TRUNCATE editoras");
		ps.executeUpdate();
	}
}
