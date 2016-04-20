package com.livraria;

import static org.diverproject.util.MessageUtil.showException;

import java.sql.SQLException;

import org.diverproject.util.sql.MySQL;

public class Conexao
{
	public static final int UPDATE_SUCCESSFUL = 1;
	public static final int INSERT_SUCCESSFUL = 1;
	public static final int DELETE_SUCCESSFUL = 1;

	private static final MySQL mysql;

	static
	{
		mysql = new MySQL();
		mysql.setHost("localhost");
		mysql.setDatabase("livraria");
		mysql.setUsername("livraria");
		mysql.setPassword("mysql");

		try {
			mysql.connect();
		} catch (ClassNotFoundException | SQLException e) {
			showException(e);
			System.exit(0);
		}
	}

	public static MySQL getMySQL()
	{
		return mysql;
	}
}
