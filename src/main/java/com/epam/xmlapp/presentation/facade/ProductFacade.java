package com.epam.xmlapp.presentation.facade;

import java.util.List;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parser.XMLParser;

public interface ProductFacade 
{
	XMLParser getParser();
	void setParser(XMLParser parser);
	void setParser(String parserName);
	List<Category> parse();
}
