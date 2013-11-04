package com.epam.xmlapp.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
/**
 * Default ErrorHandler for this application.
 * @author Dmitry Shanko
 *
 */
public class SAXErrorHandler implements ErrorHandler
{
	private static final Logger log = LoggerFactory.getLogger(SAXErrorHandler.class);

	public SAXErrorHandler() 
	{

	}

	private String getLineAddress(SAXParseException e) 
	{
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}
	/**
	 * Overridden method to log some error in parsing
	 */
	@Override
	public void error(SAXParseException e) throws SAXParseException 
	{
		log.error("ERROR: " + getLineAddress(e) + " - " + e.getMessage());	
		throw e;
	}
	/**
	 * Overridden method to log some Fatal error in parsing
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXParseException 
	{
		log.error("FATAL: " + getLineAddress(e) + " - "	+ e.getMessage());	
		throw e;
	}
	/**
	 * Overridden method to log some warning in parsing
	 */
	@Override
	public void warning(SAXParseException e) throws SAXParseException  
	{		
		log.warn("WARN: " + getLineAddress(e) + "-" + e.getMessage());		
		throw e;
	}

}
