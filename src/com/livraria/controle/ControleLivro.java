
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
import com.livraria.entidades.Autor;
import com.livraria.entidades.Editora;
import com.livraria.entidades.Livro;

public class ControleLivro
{
	private static final Connection connection;

	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}

	public boolean adicionar(Livro livro) throws SQLException
	{
		Date publicacao = new Date(livro.getPublicacao().getTime());

		String sql = "INSERT INTO livros (isbn, categoria, titulo, editora, preco, desconto, publicacao) "
					+ "values (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, livro.getIsbn());
		ps.setInt(2, livro.getCategoria());
		ps.setString(3, livro.getTitulo());
		ps.setInt(4, livro.getEditora().getID());
		ps.setFloat(5, livro.getPreco());
		ps.setFloat(6, livro.getDesconto());
		ps.setDate(7, publicacao);

		if (!ps.execute())
			return false;

		for (Autor autor : livro.getLivroAutores().listar())
			if (!adicionarAutor(livro, autor))
				throw new SQLException("falha ao adicionar o autor " +autor.getNome());

		return true;
	}

	public boolean atualizar(Livro livro) throws SQLException
	{
		for (Autor autor : livro.getLivroAutores().listar())
			adicionarAutor(livro, autor);

		Date publicacao = new Date(livro.getPublicacao().getTime());

		String sql = "UPDATE livros SET isbn = ?, categoria = ?, titulo = ?, "
					+ "editora = ?, preco = ?, desconto = ?, publicacao = ? WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, livro.getIsbn());
		ps.setInt(2, livro.getCategoria());
		ps.setString(3, livro.getTitulo());
		ps.setInt(4, livro.getEditora().getID());
		ps.setFloat(5, livro.getPreco());
		ps.setFloat(6, livro.getDesconto());
		ps.setDate(7, publicacao);

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public boolean excluir(int id) throws SQLException
	{
		Livro livro = new Livro();
		livro.setID(id);

		removerAutor(livro);

		String sql = "DELETE FROM livros WHERE id = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		return ps.executeUpdate() != PreparedStatement.EXECUTE_FAILED;
	}

	public Livro selecionar(int id) throws SQLException
	{
		Livro livro = new Livro();

		String sql = "SELECT * FROM livros WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();

		if (!rs.next())
			return null;

		ControleEditora controleEditora = new ControleEditora();
		Editora editora = controleEditora.selecionar(rs.getInt("editora"));

		livro.setID(id);
		livro.setIsbn(rs.getString("isbn"));
		livro.setCategoria(rs.getInt("categoria"));
		livro.setTitulo(rs.getString("titulo"));
		livro.setEditora(editora);
		livro.setPreco(rs.getFloat("preco"));
		livro.setDesconto(rs.getFloat("desconto"));
		livro.setPublicacao(rs.getDate("publicacao"));

		return livro;
	}

	public List<Livro> listar() throws SQLException
	{
		List<Livro> livros = new ArrayList<Livro>();

		String sql = "SELECT * FROM livros";
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		ControleEditora controleEditora = new ControleEditora();

		while (rs.next())
		{
			Editora editora = controleEditora.selecionar(rs.getInt("editora"));

			Livro livro = new Livro();
			livro.setID(rs.getInt("id"));
			livro.setIsbn(rs.getString("isbn"));
			livro.setCategoria(rs.getInt("categoria"));
			livro.setTitulo(rs.getString("titulo"));
			livro.setEditora(editora);
			livro.setPreco(rs.getFloat("preco"));
			livro.setPublicacao(rs.getDate("publicacao"));
			livros.add(livro);
		}

		return livros;
	}

	private boolean adicionarAutor(Livro livro, Autor autor) throws SQLException
	{
		String sql = "SELECT * FROM livros_autores WHERE livro = ? AND autor = ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, livro.getID());
		ps.setInt(2, autor.getID());

		if (ps.executeQuery().next())
			return false;

		sql = "INSERT INTO livros_autores (livro, autor) VALUES (?, ?)";

		ps = connection.prepareStatement(sql);
		ps.setInt(1, livro.getID());
		ps.setInt(2, autor.getID());

		return ps.executeUpdate() == PreparedStatement.EXECUTE_FAILED;
	}

	private boolean removerAutor(Livro livro) throws SQLException
	{
		String sql = "DELETE FROM livros_autores WHERE livro = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, livro.getID());

		return ps.executeUpdate() == PreparedStatement.EXECUTE_FAILED;
	}
}
