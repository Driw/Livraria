package com.livraria.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class ComponentUtil
{
	public static KeyListener maxLength(JTextField textField, int length)
	{
		return new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (textField.getText().length() >= length)
				{
					e.consume();
					textField.setText(textField.getText().substring(0, length));
				}
			}
		};
	}
}
