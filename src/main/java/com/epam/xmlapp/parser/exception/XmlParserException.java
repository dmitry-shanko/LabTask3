package com.epam.xmlapp.parser.exception;

public class XmlParserException extends Exception
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7794091086546671885L;
	
	public XmlParserException(String s, Throwable e)
	{
		super(s, e);
	}
	
	public XmlParserException(String s)
	{
		super(s);
	}
	
	public XmlParserException(Throwable e)
	{
		super(e);
	}
	
	public XmlParserException()
	{
		super();
	}
}
