package com.livraria.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.diverproject.util.sql.MySQL;

import com.livraria.Conexao;
import com.livraria.entidades.Editora;
import com.livraria.entidades.Livro;

public class ControlePesquisa
{
	private static final Connection connection;

	static
	{
		MySQL mysql = Conexao.getMySQL();
		connection = mysql.getConnection();
	}

	public List<Livro> pesquisarPorCategoria(String categoria, int quantidade) throws SQLException
	{
		String sql = "SELECT livros.id, livros.isbn, livros.titulo, livros.editora, livros.preco,"
					+" livros.publicacao, livros.paginas, livros.capa, livros.resumo, livros.sumario"
					+" FROM livros"
					+" INNER JOIN livros_categorias ON livros_categorias.livro = livros.id"
					+" INNER JOIN categorias ON categorias.id = livros_categorias.cdu"
					+" WHERE categorias.tema LIKE '%?%'";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, categoria);

		ResultSet rs = ps.executeQuery();
		List<Livro> pesquisado = filtrarResultado(rs);

		return pesquisado;
	}

	public List<Livro> pesquisarPorAutor(String autor, int quantidade) throws SQLException
	{
		String query = "SELECT livros.id, livros.isbn, livros.titulo, livros.editora, livros.preco,"
				+" livros.publicacao, livros.paginas, livros.capa, livros.resumo, livros.sumario"
				+" FROM livros"
				+" INNER JOIN livros_autores ON livros_autores.livro = livros.id"
				+" INNER JOIN autores ON autores.id = livros_autores.autor"
				+" WHERE autores.nome LIKE '%?%'";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, autor);

		ResultSet rs = ps.executeQuery();
		List<Livro> pesquisado = filtrarResultado(rs);

		return pesquisado;
	}

	public List<Livro> pesquisarPorTitulo(String titulo, int quantidade) throws SQLException
	{
		String query = "SELECT * FROM livros WHERE titulo LIKE '%?%'";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, titulo);

		ResultSet rs = ps.executeQuery();
		List<Livro> pesquisado = filtrarResultado(rs);

		return pesquisado;
	}

	public List<Livro> pesquisarPorEditora(String editora, int quantidade) throws SQLException
	{
		String query = "SELECT livros.id, livros.isbn, livros.titulo, livros.editora, livros.preco,"
				+" livros.publicacao, livros.paginas, livros.capa, livros.resumo, livros.sumario"
				+" FROM livros"
				+" INNER JOIN editoras ON editoras.id = livros.editora"
				+" WHERE editoras.nome LIKE '%?%'";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, editora);

		ResultSet rs = ps.executeQuery();
		List<Livro> pesquisado = filtrarResultado(rs);

		return pesquisado;
	}

	private List<Livro> filtrarResultado(ResultSet rs) throws SQLException
	{
		List<Livro> livros = new ArrayList<Livro>();

		ControleEditora controleEditora = new ControleEditora();
		ControleLivro controleLivro = new ControleLivro();

		while (rs.next())
		{
			Editora editora = controleEditora.selecionar(rs.getInt("editora"));

			Livro livro = new Livro();
			livro.setID(rs.getInt("id"));
			livro.setIsbn(rs.getString("isbn"));
			livro.setTitulo(rs.getString("titulo"));
			livro.setEditora(editora);
			livro.setPreco(rs.getFloat("preco"));
			livro.setPublicacao(rs.getDate("publicacao"));
			livro.setPaginas(rs.getInt("paginas"));
			livro.setCapa(rs.getInt("capa"));
			livro.setResumo(rs.getString("resumo"));
			livro.setSumario(rs.getString("sumario"));

			controleLivro.carregarAutores(livro);
			controleLivro.carregarCategorias(livro);

			livros.add(livro);
		}

		return livros;
	}
}
