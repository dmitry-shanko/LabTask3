package com.epam.xmlapp.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.model.Product;
import com.epam.xmlapp.model.Subcategory;

public class SaxContentHandler extends DefaultHandler
{
	private static final Logger log = LoggerFactory.getLogger(SaxContentHandler.class);
	private List<Category> categories;	
	private Category category;
	private Subcategory subcategory;
	private Product product;
	private String qName;
	
	public List<Category> getContent()
	{
		if (null == categories)
		{
			log.warn("Attempt to get null content from {}: categories={}", getClass(), categories);
			return new ArrayList<Category>();
		}
		return categories;		
	}

	@Override
	public void startDocument() throws SAXException 
	{
		categories = new ArrayList<Category>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException 
	{
		log.debug("{} public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException:", getClass());
		log.debug("params: uri={}, localName={}, qName={}, attrs={}", new Object[]{uri, localName, qName, attrs});
		this.qName = qName;
		if (qName != null)
		{
			qName = qName.trim();
			if (qName.equalsIgnoreCase(ProductsConstants.CATEGORY.getContent()))
			{
				category = new Category();
				categories.add(category);
				category.setName(attrs.getValue(0));
			}
			else
			{
				if (!qName.equalsIgnoreCase(ProductsConstants.CATEGORY_LIST.getContent()))
				{
					if (qName.equalsIgnoreCase(ProductsConstants.SUBCATEGORY.getContent()))
					{
						subcategory = new Subcategory();
						category.add(subcategory);
						subcategory.setName(attrs.getValue(0));
					}
					else
					{
						if (qName.equalsIgnoreCase(ProductsConstants.PRODUCT.getContent()))
						{
							product = new Product();
							subcategory.add(product);
							product.setProductName(attrs.getValue(0));
						}
						else
						{
							if ((qName.equalsIgnoreCase(ProductsConstants.NOT_IN_STOCK.getContent())) && (product != null))
							{
								product.setNotInStock(true);
							}
						}
					}
				}
			}
		}
	}	
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		if (ch != null)
		{
			String s = new String(ch, start, length).trim();
			if (product == null || s.length() < 1) 
			{
				return;
			}
			else
			{
				if (qName.equalsIgnoreCase(ProductsConstants.PROVIDER.getContent()))
				{
					product.setProvider(s);
				}
				else
				{
					if (qName.equalsIgnoreCase(ProductsConstants.MODEL.getContent()))
					{
						product.setModel(s);
					}
					else
					{
						if (qName.equalsIgnoreCase(ProductsConstants.COLOR.getContent()))
						{
							product.setColor(s);
						}
						else
						{
							if (qName.equalsIgnoreCase(ProductsConstants.DATE.getContent()))
							{
								DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
								java.util.Date utilDate = null;
								try 
								{
									utilDate = dateFormat.parse(s);
								} 
								catch (ParseException e) 
								{
									log.error("Can't parse date", e);
								}
								product.setDateOfIssue(utilDate);
							}
							else
							{
								if (qName.equalsIgnoreCase(ProductsConstants.PRICE.getContent()))
								{
									product.setPrice(s);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void endDocument() throws SAXException 
	{
		return;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		return;
	}
}