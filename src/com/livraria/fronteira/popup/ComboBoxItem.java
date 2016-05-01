package com.livraria.fronteira.popup;

public abstract class ComboBoxItem<E>
{
	private E element;

	public ComboBoxItem(E element)
	{
		this.element = element;
	}

	public E getElement()
	{
		return element;
	}

	public abstract String toString(E element);

	@Override
	public String toString()
	{
		return toString(element);
	}
}
