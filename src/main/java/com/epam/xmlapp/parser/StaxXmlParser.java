package com.epam.xmlapp.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.model.Product;
import com.epam.xmlapp.model.Subcategory;
import com.epam.xmlapp.parser.exception.NotValidXMLException;
import com.epam.xmlapp.parser.exception.XmlParserException;

public class StaxXmlParser extends XMLParser
{
	private static final Logger log = LoggerFactory.getLogger(StaxXmlParser.class);
	private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();

	private StaxXmlParser()
	{
		log.debug("{} has been created", getClass());
	}

	@Override
	public List<Category> parse() 
	{
		XMLStreamReader reader = null;
		InputStream input = null;
		try 
		{
			input = new FileInputStream(this.getXmlFileName());
			reader = inputFactory.createXMLStreamReader(input);
			if(this.checkSchema())
			{
				log.info("{} got valid XML File.", getClass());
				if (null != reader)
				{
					try 
					{
						return this.process(reader);
					} 
					catch (XMLStreamException e) 
					{
						log.error("Collecting data encountred an XMLStreamException. Can't collect data.", e);
					}
					catch (XmlParserException e1)
					{
						log.error("Exception in parsing xml file: xmlFileName={}", getXmlFileName());
						log.error("XmlParserException:", e1);
					}
				}
				else
				{
					log.error("Can't work with null reader. Class was not initialized well.");
				}
			}
			else
			{
				log.warn("{} got not valid XML File. Can't proceed.", getClass());
			}
		} 
		catch (NotValidXMLException e) 
		{
			log.error("NotValidXMLException catched:", e);			
		}
		catch (FileNotFoundException | XMLStreamException e) 
		{
			log.error("Can't initialize {} well.", getClass());
			log.error("Catched: ", e);
			return new ArrayList<Category>();
		} 
		finally
		{
			this.close(reader, input);
		}
		return new ArrayList<Category>();
	}

	private List<Category> process(XMLStreamReader reader) throws XMLStreamException, XmlParserException
	{
		Category category = null;
		Subcategory subcategory = null;
		Product product = null;
		List<Category> listCategory = new ArrayList<Category>();
		String name = null;
		while (reader.hasNext()) 
		{
			int type = reader.next();			
			switch (type) 
			{
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				if (null != name)
				{
					log.debug("{}. Name param: name={}", getClass(), name);
					if (name.equalsIgnoreCase(ProductsConstants.CATEGORY_LIST.getContent()))
					{						
						break;
					}
					else
					{
						if (name.equalsIgnoreCase(ProductsConstants.CATEGORY.getContent()))
						{
							category = new Category();
							category.setName(reader.getAttributeValue(0));
							listCategory.add(category);
						}
						else
						{
							if (null == category)
							{
								throw new XmlParserException("Stax Parser encountred an error in XML File. There are no category to collect subcategories: elementName=" + name + ", category=" + category);
							}
							else
							{
								if (name.equalsIgnoreCase(ProductsConstants.SUBCATEGORY.getContent()))
								{
									subcategory = new Subcategory();
									subcategory.setName(reader.getAttributeValue(0));
									category.add(subcategory);
								}
								else
								{
									if (null == subcategory)
									{
										throw new XmlParserException("Stax Parser encountred an error in XML File. There are no subcategory to collect products: elementName=" + name + ", subcategory=" + subcategory);
									}
									else
									{
										if (name.equalsIgnoreCase(ProductsConstants.PRODUCT.getContent()))
										{
											product = new Product();
											product.setProductName(reader.getAttributeValue(0));
											subcategory.add(product);
										}
										else
										{
											if (null == product)
											{
												throw new XmlParserException("Stax Parser encountred an error in XML File. There are no product to collect data: elementName=" + name + ", product=" + product);
											}
											else
											{
												if (name.equalsIgnoreCase(ProductsConstants.NOT_IN_STOCK.getContent()))
												{
													product.setNotInStock(true);
												}											
											}
										}
									}
								}
							}
						}
					}
				}
				else
				{
					throw new XmlParserException("Local name of start element is null.");
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				break;
			case XMLStreamConstants.CHARACTERS:
				if (name != null)
				{					
					String text = reader.getText().trim();
					log.debug("{}. Text content: text={}", getClass(), text);
					if ((text != null) && (text.length() > 0))
					{
						if (name.equalsIgnoreCase(ProductsConstants.COLOR.getContent()))
						{
							product.setColor(text);
						}
						else
						{
							if (name.equalsIgnoreCase(ProductsConstants.MODEL.getContent()))
							{
								product.setModel(text);
							}
							else
							{
								if (name.equalsIgnoreCase(ProductsConstants.PROVIDER.getContent()))
								{
									product.setProvider(text);
								}
								else
								{
									if (name.equalsIgnoreCase(ProductsConstants.DATE.getContent()))
									{
										DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
										java.util.Date utilDate = null;
										try 
										{
											utilDate = dateFormat.parse(text);
										} 
										catch (ParseException e) 
										{
											log.error("Can't parse date", e);
										}
										product.setDateOfIssue(utilDate);
									}
									else
									{
										if (name.equalsIgnoreCase(ProductsConstants.PRICE.getContent()) && (!product.isNotInStock()))
										{
											product.setPrice(text);
										}
									}
								}
							}
						}
					}
				}
				break;
			}
		}
		return listCategory;
	}

	private void close(XMLStreamReader reader, InputStream input)
	{
		try 
		{
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e) 
				{
					log.error("Can't close input stream in " + getClass() + ".", e);
				} 
			}
			if (reader != null)
			{
				reader.close();
			}				
		} 		
		catch (XMLStreamException e) 
		{
			log.error("Can't close XMLReader in " + getClass() + ".", e);
		}
	}
}
