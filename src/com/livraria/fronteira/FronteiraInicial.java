package com.livraria.fronteira;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import com.livraria.util.ComponentUtil;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class FronteiraInicial extends JPanel
{
	private JTextField tfLogin;
	private JPasswordField tfSenha;
	public FronteiraInicial()
	{
		setSize(400, 200);
		setLayout(null);

		JPanel panelLogin = new JPanel();
		panelLogin.setBorder(new TitledBorder(null, "Painel de Acesso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLogin.setBounds(0, 0, 400, 200);
		panelLogin.setLayout(null);
		add(panelLogin);

		JLabel lblLogin = new JLabel("Login :");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(50, 33, 100, 25);
		panelLogin.add(lblLogin);

		tfLogin = new JTextField();
		tfLogin.setBounds(170, 33, 170, 25);
		tfLogin.addKeyListener(ComponentUtil.maxLength(tfLogin, 24));
		panelLogin.add(tfLogin);

		JLabel lblSenha = new JLabel("Senha :");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(50, 69, 100, 25);
		panelLogin.add(lblSenha);

		tfSenha = new JPasswordField();
		tfSenha.setBounds(170, 69, 170, 25);
		tfSenha.addKeyListener(ComponentUtil.maxLength(tfSenha, 24));
		panelLogin.add(tfSenha);

		JPanel panelAcoes = new JPanel();
		panelAcoes.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAcoes.setBounds(10, 129, 380, 60);
		panelLogin.add(panelAcoes);
		panelAcoes.setLayout(new GridLayout(1, 3, 10, 0));

		JButton btnNovaConta = new JButton("Nova Conta");
		panelAcoes.add(btnNovaConta);

		JButton btnEntrar = new JButton("Entrar");
		panelAcoes.add(btnEntrar);

		JButton btnRecuperarSenha = new JButton("Recuperar Senha");
		panelAcoes.add(btnRecuperarSenha);
	}
}
