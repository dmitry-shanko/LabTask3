package com.epam.xmlapp.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.parser.exception.NotValidXMLException;

public abstract class XMLParser 
{
	private static final Logger log = LoggerFactory.getLogger(XMLParser.class);
	private String xmlFileName;
	private String dtdFileName;
	private String xsdFileName;

	public abstract List<Category> parse();
	
	/**
	 * @return the xmlFileName
	 */
	public String getXmlFileName() {
		return xmlFileName;
	}
	/**
	 * @param xmlFileName the xmlFileName to set
	 */
	public void setXmlFileName(String xmlFileName) 
	{
		this.xmlFileName = xmlFileName;
	}
	/**
	 * @return the dtdFileNams
	 */
	public String getDtdFileName() {
		return dtdFileName;
	}
	/**
	 * @param dtdFileNams the dtdFileNams to set
	 */
	public void setDtdFileName(String dtdFileName) 
	{
		this.dtdFileName = dtdFileName;
	}
	/**
	 * @return the xsdFileName
	 */
	public String getXsdFileName() {
		return xsdFileName;
	}
	/**
	 * @param xsdFileName the xsdFileName to set
	 */
	public void setXsdFileName(String xsdFileName) {
		this.xsdFileName = xsdFileName;
	}	
	
	protected boolean checkSchema() throws NotValidXMLException
	{
		try 
		{
			if ((xmlFileName != null) && (dtdFileName != null) && (xsdFileName != null))
			{
				SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);	        
				File xsdFile = new File(xsdFileName);
				Schema schema = factory.newSchema(xsdFile);
				Validator validator = schema.newValidator();	
				StreamSource source = null;
				source = new StreamSource(xmlFileName);		        
				validator.validate(source);
				log.debug("Selected XML document is valid for selected XSD Schema: xmlFileName={}, xsdFileName={}", xmlFileName, xsdFileName);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				dbf.setValidating(true);
				DocumentBuilder db;
				InputSource is = null;
				try 
				{
					db = dbf.newDocumentBuilder();
					db.setErrorHandler(new SAXErrorHandler());
					is = new InputSource(xmlFileName);
					db.parse(is);					
					log.debug("Selected XML document is valid for selected DTD Schema: xmlFileName={}, dtdFileName={}", xmlFileName, dtdFileName);
					return true;
				} 
				catch (ParserConfigurationException e) 
				{
					log.error("Can't validate XML file in protected void checkSchema() because of ParserConfigurationException", e);
					throw new NotValidXMLException("Can't validate XML file in protected void checkSchema() because of ParserConfigurationException", e);
				}
				catch (Exception e1)
				{
					log.error("Exception catched during validating DTD schema.", e1);
				}
			}
			else
			{
				log.error("Not valid filenames: xmlFileName={}, dtdFileName={}, xsdFileName={}", new String[]{xmlFileName, dtdFileName, xsdFileName});
			}
		}
		catch (SAXException ex) 
		{
			log.error("{} has encountered an error. Can't proceed, catched an exception:{}.", getClass(), ex.getClass());
			throw new NotValidXMLException("Can't validate XML File.", ex);
		} 
		catch (IOException e) 
		{
			log.error("{} has encountered an error. Can't proceed, catched an exception:{}.", getClass(), e.getClass());
			throw new NotValidXMLException("Can't validate XML File. Can't read XML File (file does not exist or something else).", e);
		}  		
		return false;
	}
}
