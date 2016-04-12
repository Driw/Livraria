package com.livraria.controle;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.entidades.Carrinho;

public class ControleCarrinho
{
	private static final Connection connection;

	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}

	public Carrinho criar() throws SQLException, NoSuchAlgorithmException
	{
		int numeroPedido = gerarNumeroPedido(1);
		int estado = Carrinho.CARRINHO_CRIADO;
		Date criado = new Date(System.currentTimeMillis());

		String query = "INSERT INTO carrinhos (id, criado, concluido, estado) VALUES (?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, numeroPedido);
		ps.setDate(2, new java.sql.Date(criado.getTime()));
		ps.setDate(3, null);
		ps.setInt(4, estado);

		Carrinho carrinho = selecionarPorPedido(numeroPedido);

		return carrinho;
	}

	public Carrinho recuperar(int id) throws SQLException
	{
		String query = "SELECT * FROM carrinhos WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		Carrinho carrinho = filtrarResultado(rs);

		return carrinho;
	}

	public boolean guardar(Carrinho carrinho) throws SQLException
	{
		String query = "UPDATE carrinhos SET concluido = ?, estado = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setDate(1, new java.sql.Date(carrinho.getConcluido().getTime()));
		ps.setInt(2, carrinho.getEstado());

		if (ps.executeUpdate() == PreparedStatement.EXECUTE_FAILED)
			throw new SQLException("falha ao guardar carrinho");

		removerItens(carrinho);

		if (!guardarItens(carrinho))
			throw new SQLException("falha ao guardar itens do carrinho");

		return true;
	}

	public Carrinho selecionarPorPedido(int numeroPedido) throws SQLException
	{
		String query = "SELECT * FROM carrinhos WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, numeroPedido);

		ResultSet rs = ps.executeQuery();
		Carrinho carrinho = filtrarResultado(rs);

		return carrinho;
	}

	public boolean finalizarCompra(Carrinho Carrinho) throws SQLException
	{
		// TODO Sprint 2

		return false;
	}

	public List<Carrinho> listarPedidos(/*Cliente cliente*/) throws SQLException
	{
		// TODO Sprint 2

		return null;
	}

	private Carrinho filtrarResultado(ResultSet rs) throws SQLException
	{
		Carrinho carrinho = new Carrinho();
		carrinho.setID(rs.getInt("id"));
		carrinho.setCriado(new Date(rs.getDate("criado").getTime()));
		carrinho.setConcluido(new Date(rs.getDate("concluido").getTime()));
		carrinho.setEstado(rs.getInt("estado"));

		return carrinho;
	}

	private int gerarNumeroPedido(int clienteID) throws NoSuchAlgorithmException, SQLException
	{
		Random random = new Random();
		int numeroPedido = 0;

		do {

			numeroPedido = random.nextInt(899999999);
			numeroPedido += 100000000;

			if (selecionarPorPedido(numeroPedido) != null)
				numeroPedido = 0;

		} while (numeroPedido == 0);

		return numeroPedido;
	}

	private boolean guardarItens(Carrinho carrinho) throws SQLException
	{
		String query = "INSERT INTO carrinhos_itens (carrinho, livro, quantidade) VALUES";

		for (int i = 0; i < carrinho.getNumeroItens(); i++)
			query += " (?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(query);

		for (int i = 0, j = 1; i < carrinho.getNumeroItens(); i++)
		{
			ps.setInt(j++, carrinho.getID());
			ps.setInt(j++, carrinho.getLivro(i).getID());
			ps.setInt(j++, carrinho.getQuantidade(i));
		}

		return ps.executeUpdate() == Conexao.UPDATE_SUCCESSFUL;
	}

	private boolean removerItens(Carrinho carrinho) throws SQLException
	{
		String query = "DELETE * FROM carrinhos_itens WHERE carrinho = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, carrinho.getID());

		return ps.executeUpdate() == Conexao.UPDATE_SUCCESSFUL;
	}
}
