package com.livraria.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import org.diverproject.util.lang.StringUtil;

public class ComponentUtil
{
	public static KeyListener maxLength(JTextComponent textComponent, int length)
	{
		return new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (textComponent.getText().length() >= length)
				{
					e.consume();
					textComponent.setText(textComponent.getText().substring(0, length));
				}
			}
		};
	}

	public static KeyListener keyListenerNumerico()
	{
		return new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() < '0' || e.getKeyChar() > '9')
					e.consume();
			}
		};
	}

	public static void setDataMask(JFormattedTextField textField)
	{
		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			textField.setFormatterFactory(new DefaultFormatterFactory(mask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setFoneMask(JFormattedTextField textField)
	{
		try {
			MaskFormatter mask = new MaskFormatter("(##) ####-####");
			textField.setFormatterFactory(new DefaultFormatterFactory(mask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCnpjMask(JFormattedTextField textField)
	{
		try {
			MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
			textField.setFormatterFactory(new DefaultFormatterFactory(mask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void setValueMask(JFormattedTextField textField)
	{
		try {
			MaskFormatter mask = new MaskFormatter("R$ ###.##");
			mask.setValidCharacters("0123456789");
			mask.setPlaceholderCharacter('0');
			textField.setFormatterFactory(new DefaultFormatterFactory(mask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void setIsbnMask(JFormattedTextField textField)
	{
		try {
			MaskFormatter mask = new MaskFormatter("###-#-##-######-#");
			textField.setFormatterFactory(new DefaultFormatterFactory(mask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	public static String cnpjClear(String cnpj)
	{
		if (cnpj == null)
			return null;

		return cnpj.replace(".", "").replace("-", "").replace("/", "");
	}

	public static String cnpjFormmat(String cnpj)
	{
		if (cnpj == null)
			return null;

		String partes[] =
		{
			StringUtil.sub(cnpj, 0, 2),
			StringUtil.sub(cnpj, 2, 3),
			StringUtil.sub(cnpj, 5, 3),
			StringUtil.sub(cnpj, 8, 4),
			StringUtil.sub(cnpj, 12, 2),
		};

		return String.format("%s.%s.%s/%s-%s", partes[0], partes[1], partes[2], partes[3], partes[4]);
	}

	public static String telefoneClear(String telefone)
	{
		if (telefone == null)
			return null;

		return telefone.replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
	}

	public static String telefoneFormmat(String telefone)
	{
		if (telefone == null)
			return null;

		String partes[] =
		{
			StringUtil.sub(telefone, 0, 2),
			StringUtil.sub(telefone, 2, 4),
			StringUtil.sub(telefone, 6, 4),
		};

		return String.format("(%s) %s-%s", partes[0], partes[1], partes[2]);
	}

	public static String isbnClear(String isbn)
	{
		if (isbn == null)
			return null;

		return isbn.replace("-", "");
	}

	public static String isbnFormmat(String isbn)
	{
		if (isbn == null)
			return null;

		String partes[] =
		{
			StringUtil.sub(isbn, 0, 3),
			StringUtil.sub(isbn, 3, 1),
			StringUtil.sub(isbn, 4, 2),
			StringUtil.sub(isbn, 6, 6),
			StringUtil.sub(isbn, 12, 1),
		};

		return String.format("%s-%s-%s-%s-%s", partes[0], partes[1], partes[2], partes[3], partes[4]);
	}
}
