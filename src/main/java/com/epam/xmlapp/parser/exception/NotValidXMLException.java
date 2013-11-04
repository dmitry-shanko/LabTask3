package com.epam.xmlapp.parser.exception;

public class NotValidXMLException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881680319617090473L;

	public NotValidXMLException(String s, Throwable e)
	{
		super(s, e);
	}
	
	public NotValidXMLException(String s)
	{
		super(s);
	}
	
	public NotValidXMLException(Throwable e)
	{
		super(e);
	}
	
	public NotValidXMLException()
	{
		super();
	}
}
