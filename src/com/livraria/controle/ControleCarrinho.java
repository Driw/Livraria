package com.livraria.controle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.entidades.Carrinho;
import com.livraria.entidades.CarrinhoItem;
import com.livraria.entidades.Livro;

public class ControleCarrinho
{
	private static final Connection connection;
	private static Carrinho carrinhoAtual;

	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}

	public Carrinho criar() throws SQLException
	{
		carrinhoAtual = recuperar();

		if (carrinhoAtual != null)
			return carrinhoAtual;

		int estado = Carrinho.CARRINHO_CRIADO;
		Date criado = new Date(System.currentTimeMillis());

		String query = "INSERT INTO carrinhos (criado, concluido, estado) VALUES (?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setDate(1, criado);
		ps.setDate(2, null);
		ps.setInt(3, estado);

		if (ps.executeUpdate() == Conexao.INSERT_SUCCESSFUL)
			return (carrinhoAtual = recuperar());

		throw new SQLException("não foi possível criar o carrinho");
	}

	public Carrinho recuperar() throws SQLException
	{
		String query = "SELECT * FROM carrinhos WHERE (estado = ? OR estado = ?) ORDER BY id ASC";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, Carrinho.CARRINHO_CRIADO);
		ps.setInt(2, Carrinho.CARRINHO_EM_ESPERA);

		ResultSet rs = ps.executeQuery();
		Carrinho carrinho = null;

		if (rs.next())
			carrinho = criar(rs);

		if (carrinho != null)
			carregarItens(carrinho);

		return carrinho;
	}

	public Carrinho carregar(int id) throws SQLException
	{
		String query = "SELECT * FROM carrinhos WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		Carrinho carrinho = null;

		if (rs.next())
			carrinho = criar(rs);

		return carrinho;
	}

	public boolean guardar(Carrinho carrinho) throws SQLException
	{
		String query = "UPDATE carrinhos SET concluido = ?, estado = ? WHERE id = ?";

		Date concluido = null;

		if (carrinho.getConcluido() != null)
			concluido = new Date(carrinho.getConcluido().getTime());

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setDate(1, concluido);
		ps.setInt(2, Carrinho.CARRINHO_EM_ESPERA);
		ps.setInt(3, carrinho.getID());

		if (ps.executeUpdate() == Conexao.UPDATE_SUCCESSFUL)
		{
			carrinho.setEstado(Carrinho.CARRINHO_EM_ESPERA);

			removerItens(carrinho);

			if (!guardarItens(carrinho))
				throw new SQLException("falha ao guardar carrinho");

			return true;
		}

		return false;
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

	private Carrinho criar(ResultSet rs) throws SQLException
	{
		Carrinho carrinho = new Carrinho();
		carrinho.setID(rs.getInt("id"));
		carrinho.setCriado(new Date(rs.getDate("criado").getTime()));
		carrinho.setEstado(rs.getInt("estado"));

		if (rs.getDate("concluido") != null)
			carrinho.setConcluido(new Date(rs.getDate("concluido").getTime()));

		return carrinho;
	}

	private boolean guardarItens(Carrinho carrinho) throws SQLException
	{
		if (carrinho.getNumeroItens() == 0)
			return true;

		String query = "INSERT INTO carrinhos_itens (carrinho, livro, quantidade) VALUES";

		for (int i = 0; i < carrinho.getNumeroItens(); i++)
		{
			query += " (?, ?, ?)";

			if (i < carrinho.getNumeroItens() - 1)
				query += ",";
		}

		PreparedStatement ps = connection.prepareStatement(query);

		for (int i = 0, j = 1; i < carrinho.getNumeroItens(); i++)
		{
			CarrinhoItem item = carrinho.getItem(i);

			ps.setInt(j++, carrinho.getID());
			ps.setInt(j++, item.getLivro().getID());
			ps.setInt(j++, item.getQuantidade());
		}

		return ps.executeUpdate() == carrinho.getNumeroItens();
	}

	private boolean removerItens(Carrinho carrinho) throws SQLException
	{
		String query = "DELETE FROM carrinhos_itens WHERE carrinho = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, carrinho.getID());

		return ps.execute();
	}

	private void carregarItens(Carrinho carrinho) throws SQLException
	{
		String query = "SELECT quantidade, livro FROM carrinhos_itens WHERE carrinho = ?";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, carrinho.getID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			ControleLivro controleLivro = new ControleLivro();
			Livro livro = controleLivro.selecionar(rs.getInt("livro"));

			carrinho.adicionar(rs.getInt("quantidade"), livro);
		}
	}

	public Carrinho getCarrinho() throws SQLException
	{
		if (carrinhoAtual == null)
			carrinhoAtual = criar();

		return carrinhoAtual;
	}
}
