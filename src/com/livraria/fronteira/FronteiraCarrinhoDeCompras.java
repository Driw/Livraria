package com.livraria.fronteira;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.diverproject.util.MessageUtil;
import org.diverproject.util.lang.IntUtil;

import com.livraria.controle.ControleCarrinho;
import com.livraria.entidades.Carrinho;
import com.livraria.entidades.CarrinhoItem;
import com.livraria.entidades.Livro;
import com.livraria.fronteira.model.ModelCarrinhoItem;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FronteiraCarrinhoDeCompras extends JPanel
{
	private static final JPanel INSTANCE = new FronteiraCarrinhoDeCompras();

	private ModelCarrinhoItem model;
	private ControleCarrinho controleCarrinho;

	private JTable tableLivros;
	private JTextField tfTotal;

	private FronteiraCarrinhoDeCompras()
	{
		controleCarrinho = new ControleCarrinho();

		setSize(820, 400);
		setLayout(null);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Carrinho de Compras", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPesquisa.setBounds(0, 0, 820, 278);
		panelPesquisa.setLayout(null);
		add(panelPesquisa);

		model = new ModelCarrinhoItem();

		tableLivros = new JTable();
		tableLivros.setModel(model);
		tableLivros.getColumnModel().getColumn(0).setResizable(false);
		tableLivros.getColumnModel().getColumn(0).setMinWidth(100);
		tableLivros.getColumnModel().getColumn(0).setMaxWidth(100);
		tableLivros.getColumnModel().getColumn(1).setResizable(false);
		tableLivros.getColumnModel().getColumn(1).setMinWidth(110);
		tableLivros.getColumnModel().getColumn(1).setMaxWidth(110);
		tableLivros.getColumnModel().getColumn(2).setResizable(false);
		tableLivros.getColumnModel().getColumn(2).setMinWidth(240);
		tableLivros.getColumnModel().getColumn(2).setMaxWidth(240);
		tableLivros.getColumnModel().getColumn(3).setResizable(false);
		tableLivros.getColumnModel().getColumn(3).setMinWidth(180);
		tableLivros.getColumnModel().getColumn(3).setMaxWidth(180);
		tableLivros.getColumnModel().getColumn(4).setResizable(false);
		tableLivros.getColumnModel().getColumn(4).setMinWidth(80);
		tableLivros.getColumnModel().getColumn(4).setMaxWidth(80);
		tableLivros.getColumnModel().getColumn(5).setResizable(false);
		tableLivros.getColumnModel().getColumn(5).setMinWidth(100);
		tableLivros.getColumnModel().getColumn(5).setMaxWidth(100);
		tableLivros.setFont(Fronteira.FONT_COMPONENTES);

		JScrollPane spResultados = new JScrollPane(tableLivros);
		spResultados.setBounds(10, 21, 800, 257);
		panelPesquisa.add(spResultados);

		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(null);
		panelInfo.setBounds(10, 289, 800, 40);
		add(panelInfo);

		JLabel lblTotal = new JLabel("Total da Compra : R$ ");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(271, 11, 120, 25);
		panelInfo.add(lblTotal);

		tfTotal = new JTextField();
		tfTotal.setHorizontalAlignment(SwingConstants.LEFT);
		tfTotal.setBounds(401, 11, 140, 25);
		tfTotal.setEditable(false);
		panelInfo.add(tfTotal);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(new GridLayout(0, 5, 10, 0));
		panel.setBounds(0, 340, 820, 60);
		add(panel);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAtualizar();
			}
		});
		btnAtualizar.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnAtualizar);

		JButton btnAlterarQuantidade = new JButton("Alterar Quantidade");
		btnAlterarQuantidade.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callAlterarQuantidade();
				callAtualizar();
			}
		});
		btnAlterarQuantidade.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnAlterarQuantidade);

		JButton btnRemoverLivro = new JButton("Remover Livro");
		btnRemoverLivro.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callRemoverLivro();
				callAtualizar();
			}
		});
		btnRemoverLivro.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnRemoverLivro);

		JButton btnVisualizarDetalhes = new JButton("Visualizar Detalhes");
		btnVisualizarDetalhes.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callVisualizarDetalhes();
			}
		});
		btnVisualizarDetalhes.setFont(Fronteira.FONT_COMPONENTES);
		panel.add(btnVisualizarDetalhes);

		JButton btnLimparCarrinho = new JButton("Continuar Comprando");
		btnLimparCarrinho.setFont(Fronteira.FONT_COMPONENTES);
		btnLimparCarrinho.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				callContinuarComprando();
			}
		});
		panel.add(btnLimparCarrinho);
	}

	private void callAtualizar()
	{
		List<CarrinhoItem> lista = new ArrayList<>();

		try {

			Carrinho carrinho = controleCarrinho.getCarrinho();

			for (int i = 0; i < carrinho.getNumeroItens(); i++)
				lista.add(carrinho.getItem(i));

			tfTotal.setText(String.format(Locale.US, "%.2f", carrinho.calcularTotal()));

		} catch (SQLException e) {
			MessageUtil.showError("Atualizar", "Não foi possível a lista de itens:\n-%s", e.getMessage());
		}

		model.atualizarLista(lista);
	}

	private void callAlterarQuantidade()
	{
		try {

			CarrinhoItem item = model.getLinha(tableLivros.getSelectedRow());

			if (item == null)
			{
				MessageUtil.showWarning("Alterar Quantidade", "Selecione um livro da lista de compras do carrinho.");
				return;
			}

			Livro livro = item.getLivro();

			String input = MessageUtil.showInput("Alterar Quantidade", "Insira abaixo a nova quantidade para '%s':", livro.getTitulo());
			int quantidade = IntUtil.parse(input, 0);

			if (quantidade < 1)
				MessageUtil.showWarning("Alterar Quantidade", "Valor não aceito (quantidade: %s)", quantidade);
			else
			{
				Carrinho carrinho = controleCarrinho.getCarrinho();
				carrinho.atualizar(quantidade, livro);

				MessageUtil.showInfo("Alterar Quantidade", "Quantidade alterada com sucesso!");

				model.fireTableDataChanged();
			}

		} catch (SQLException e) {
			MessageUtil.showError("Alterar Quantidade", "Não foi possível alterar a quantidade:\n-%s", e.getMessage());
		}
	}

	private void callRemoverLivro()
	{
		try {

			CarrinhoItem item = model.getLinha(tableLivros.getSelectedRow());

			Carrinho carrinho = controleCarrinho.getCarrinho();
			carrinho.remover(item.getQuantidade(), item.getLivro());

		} catch (SQLException e) {
			MessageUtil.showError("Alterar Quantidade", "Não foi possível alterar a quantidade:\n-%s", e.getMessage());
		}
	}

	private void callVisualizarDetalhes()
	{
		CarrinhoItem item = model.getLinha(tableLivros.getSelectedRow());

		if (item == null)
		{
			MessageUtil.showWarning("Visualizar Detalhes", "Selecione um livro da lista de compras do carrinho.");
			return;
		}

		JPanel jPanel = FronteiraVisualizarDetalhes.getInstance();
		FronteiraVisualizarDetalhes visualizarDetalhes = (FronteiraVisualizarDetalhes) jPanel;
		visualizarDetalhes.setVolarPara(FronteiraCarrinhoDeCompras.class);
		visualizarDetalhes.carregar(item.getLivro());

		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraVisualizarDetalhes.class);
	}

	private void callContinuarComprando()
	{
		Fronteira fronteira = Fronteira.getInstancia();
		fronteira.setFronteira(FronteiraPesquisarLivros.class);
	}

	public static JPanel getInstance()
	{
		return INSTANCE;
	}
}
