package com.epam.xmlapp.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parser.exception.NotValidXMLException;

public class SaxXmlParser extends XMLParser
{
	private static final Logger log = LoggerFactory.getLogger(SaxXmlParser.class);
	
	private SaxXmlParser()
	{
		log.debug("{} has been created", getClass());
	}

	@Override
	public List<Category> parse() 
	{
		XMLReader reader;
		SaxContentHandler handler;
		InputSource input;
		try 
		{
			if(this.checkSchema())
			{
				log.info("{} got valid XML File.", getClass());
				reader = XMLReaderFactory.createXMLReader();
				handler = new SaxContentHandler();
				reader.setContentHandler(handler);
				input = new InputSource(getXmlFileName()); 
				reader.parse(input);
				return handler.getContent();
			}
			else
			{
				log.warn("{} got not valid XML File. Can't proceed.", getClass());
			}
		} 
		catch (NotValidXMLException | SAXException | IOException e) 
		{
			log.error("Can't parse in {} well. Cathced an Exception.", getClass());
			log.error("Error:", e);			
		} 
		return new ArrayList<Category>();
	}
	
	@SuppressWarnings("unused")
	private void close()
	{
		
	}
}
