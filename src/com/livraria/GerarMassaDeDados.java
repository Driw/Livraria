package com.livraria;

import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.diverproject.util.MessageUtil;
import org.diverproject.util.lang.FloatUtil;
import org.diverproject.util.lang.StringUtil;

import com.livraria.controle.ControleAutor;
import com.livraria.controle.ControleCategoria;
import com.livraria.controle.ControleEditora;
import com.livraria.controle.ControleLivro;
import com.livraria.entidades.Autor;
import com.livraria.entidades.Categoria;
import com.livraria.entidades.Editora;
import com.livraria.entidades.Livro;

public class GerarMassaDeDados
{
	private int quantiaCategorias = 408;
	private int quantiaEditoras;
	private int quantiaAutores;
	private Random random;

	private ControleCategoria controleCategoria;
	private ControleEditora controleEditora;
	private ControleAutor controleAutor;
	private ControleLivro controleLivro;

	public GerarMassaDeDados()
	{
		random = new Random();
		controleCategoria = new ControleCategoria();
		controleLivro = new ControleLivro();
		controleEditora = new ControleEditora();
		controleAutor = new ControleAutor();
	}

	public void truncate()
	{
		try {

			controleLivro.trucate();
			controleEditora.truncate();
			controleAutor.truncate();

		} catch (SQLException e) {
			e.printStackTrace();
			MessageUtil.die(e);
		}
	}

	public void gerarEditoras(int quantidade)
	{
		quantiaEditoras = quantidade;

		for (int i = 0; i < quantidade; i++)
		{
			int id = i+1;
			String cnpj = gerarCNPJ();
			String nome = String.format("Editora %d", i + 1);
			Date contratoInicio = null;
			Date contratoFim = null;

			if (random.nextInt(3) == 0)
			{
				Date contratos[] = gerarContrato();
				contratoInicio = contratos[0];
				contratoFim = contratos[1];
			}

			String endereco = String.format("Rua %d, nº %d", i+1, random.nextInt(9999));
			String telefone = gerarTelefone();

			Editora editora = new Editora();
			editora.setID(id);
			editora.setNome(nome);
			editora.setCnpj(cnpj);
			editora.setContrato(contratoInicio, contratoFim);
			editora.setEndereco(endereco);
			editora.setTelefone(telefone);

			try {
				controleEditora.adicionar(editora);
				System.out.printf("Editora %d/%d gerada com êxito!\n", i+1, quantidade);
			} catch (SQLException e) {
				e.printStackTrace();
				MessageUtil.die(e);
			}
		}
	}

	public void gerarAutores(int quantidade)
	{
		quantiaAutores = quantidade;

		for (int i = 0; i < quantidade; i++)
		{
			int id = i+1;
			String nome = String.format("Autor %d", i + 1);

			Date datas[] = gerarVida();
			Date nascimento = datas[0];
			Date falecimento = datas[1];

			String localMorte = String.format("Cidade %d", i + 1);
			String biografia = String.format("Biografia do Aturo %d", i + 1);

			Autor autor = new Autor();
			autor.setID(id);
			autor.setNome(nome);
			autor.setNascimento(nascimento);
			autor.setFalecimento(falecimento);
			autor.setLocalMorte(localMorte);
			autor.setBiografia(biografia);

			try {
				controleAutor.adicionar(autor);
				System.out.printf("Autor %d/%d gerado com êxito!\n", i+1, quantidade);
			} catch (SQLException e) {
				e.printStackTrace();
				MessageUtil.die(e);
			}
		}
	}

	public void gerarLivros(int quantidade)
	{
		if (quantiaEditoras == 0)
			throw new RuntimeException("editoras não foram geradas");

		if (quantiaAutores == 0)
			throw new RuntimeException("autores não foram gerados");

		for (int i = 0; i < quantidade; i++)
		{
			try {

				String isbn = gerarISBN();
				String titulo = String.format("Título do livro %d", i+1);
				Editora editora = controleEditora.selecionar(random.nextInt(quantiaEditoras) + 1);
				float preco = FloatUtil.parse(String.format("%d.%d", random.nextInt(989) + 10, random.nextInt(9)));
				int paginas = random.nextInt(1500);
				int capa = random.nextInt(2);
				Date publicacao = new Date(System.currentTimeMillis() + random.nextInt());
				String resumo = String.format("Resumo do livro %d", i+1);
				String sumario = String.format("Sumário do livro %d", i+1);

				Livro livro = new Livro();
				livro.setIsbn(isbn);
				livro.setTitulo(titulo);
				livro.setEditora(editora);
				livro.setPreco(preco);
				livro.setPaginas(paginas);
				livro.setCapa(capa);
				livro.setPublicacao(publicacao);
				livro.setResumo(resumo);
				livro.setSumario(sumario);

				int categorias = random.nextInt(3) + 1;
				int autores = random.nextInt(3) + 1;

				for (int j = 0; j < categorias; j++)
				{
					Categoria categoria = controleCategoria.selecionar(random.nextInt(quantiaCategorias) + 1);
					livro.getLivroCategorias().adicionar(categoria);
				}

				for (int j = 0; j < autores; j++)
				{
					Autor autor = controleAutor.selecionar(random.nextInt(quantiaAutores) + 1);
					livro.getLivroAutores().adicionar(autor);
				}

				controleLivro.adicionar(livro);
				System.out.printf("Livro %d/%d gerado com êxito!\n", i+1, quantidade);

			} catch (SQLException e) {
				e.printStackTrace();
				MessageUtil.die(e);
			}
		}
	}

	private String gerarCNPJ()
	{
		String a = StringUtil.addStartWhile(Integer.toString(random.nextInt(99)), "0", 2);
		String b = StringUtil.addStartWhile(Integer.toString(random.nextInt(999)), "0", 3);
		String c = StringUtil.addStartWhile(Integer.toString(random.nextInt(999)), "0", 3);
		String d = StringUtil.addStartWhile(Integer.toString(random.nextInt(9999)), "0", 4);
		String e = Integer.toString(random.nextInt(9));

		return String.format("%s.%s.%s/%s-%s", a, b, c, d, e);
	}

	private Date[] gerarContrato()
	{
		int duracao = (random.nextInt(12) * 30) + 30;
		int restam = random.nextInt(duracao);
		long atual = System.currentTimeMillis();

		Date inicio = new Date(atual - (duracao - restam * 86400000));
		Date fim = new Date(atual + (restam * 86400000));

		return new Date[] { inicio, fim };
	}

	private String gerarTelefone()
	{
		String a = StringUtil.addStartWhile(Integer.toString(random.nextInt(99)), "0", 2);
		String b = StringUtil.addStartWhile(Integer.toString(random.nextInt(5999)), "0", 4);
		String c = StringUtil.addStartWhile(Integer.toString(random.nextInt(9999)), "0", 4);

		return String.format("(%s) %s-%s", a, b, c);
	}

	private Date[] gerarVida()
	{
		int diasDeVida = random.nextInt(100 * 365);
		long duracao = diasDeVida * 86400000;
		long nasceuEm = random.nextLong();
		long morreuEm = nasceuEm = duracao;

		Date inicio = new Date(nasceuEm);
		Date fim = null;

		if (morreuEm <= System.currentTimeMillis())
			fim = new Date(morreuEm);

		return new Date[] { inicio, fim };
	}

	private String gerarISBN()
	{
		String a = StringUtil.addStartWhile(Integer.toString(random.nextInt(999)), "0", 3);
		String b = StringUtil.addStartWhile(Integer.toString(random.nextInt(9)), "0", 1);
		String c = StringUtil.addStartWhile(Integer.toString(random.nextInt(99)), "0", 2);
		String d = StringUtil.addStartWhile(Integer.toString(random.nextInt(999999)), "0", 6);
		String e = Integer.toString(random.nextInt(9));

		return String.format("%s-%s-%s-%s-%s", a, b, c, d, e);
	}
}
