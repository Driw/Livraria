package com.livraria.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

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

	public static void setDataMask(JFormattedTextField textField) {
		try {
			MaskFormatter dataMask = new MaskFormatter("##/##/####");
			textField.setFormatterFactory(new DefaultFormatterFactory(dataMask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void setFoneMask(JFormattedTextField textField) {
		try {
			MaskFormatter dataMask = new MaskFormatter("(##) ####-####");
			textField.setFormatterFactory(new DefaultFormatterFactory(dataMask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCnpjMask(JFormattedTextField textField) {
		try {
			MaskFormatter dataMask = new MaskFormatter("##.###.###/####-##");
			textField.setFormatterFactory(new DefaultFormatterFactory(dataMask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void setValueMask(JFormattedTextField textField) {
		try {
			MaskFormatter dataMask = new MaskFormatter("###,##");
			textField.setFormatterFactory(new DefaultFormatterFactory(dataMask));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
