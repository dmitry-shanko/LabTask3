package com.epam.xmlapp.parser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.xmlapp.model.Category;
import com.epam.xmlapp.model.Product;
import com.epam.xmlapp.model.Subcategory;
import com.epam.xmlapp.parser.exception.NotValidXMLException;

public class DomXmlParser extends XMLParser 
{
	private static final Logger log = LoggerFactory.getLogger(DomXmlParser.class);
	private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


	private DomXmlParser()
	{
		log.debug("{} has been created", getClass());
	}

	@Override
	public List<Category> parse() 
	{
		try 
		{
			if(this.checkSchema())
			{
				log.info("{} got valid XML File.", getClass());
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(getXmlFileName());
				return this.process(doc);
			}
			else
			{
				log.warn("{} got not valid XML File. Can't proceed.", getClass());
			}
		} 
		catch (NotValidXMLException | ParserConfigurationException | SAXException | IOException e) 
		{
			log.error("Can't parse in {} well. Cathced an Exception.", getClass());
			log.error("Error:", e);					
		}
		return new ArrayList<Category>();
	}

	private List<Category> process(Document doc)
	{
		List<Category> categories = new ArrayList<Category>();
		NodeList rootChilds;
		Element root;
		Category category;
		Subcategory subcategory;
		Product product;
		if (null != doc)
		{
			root = doc.getDocumentElement();
			rootChilds = root.getChildNodes();
			NodeList listElements;
			for (int i = 0; i < rootChilds.getLength(); i++)
			{
				Node rootChild = rootChilds.item(i);
				log.debug("First cycle: element={}", rootChild);
				if (rootChild instanceof Element)
				{
					category = new Category();
					log.debug("New Category created.");
					categories.add(category);
					String nameAttr = rootChild.getAttributes().getNamedItem(ProductsConstants.NAME_ATTR.getContent()).getNodeValue();
					log.debug("Category name: name={}", nameAttr);
					category.setName(nameAttr);
					listElements = rootChild.getChildNodes();
					for (int n = 0; n < listElements.getLength(); n++)
					{
						NodeList productList;
						Node listElement = listElements.item(n);
						log.debug("Second cycle: element={}", listElement);
						if (listElement instanceof Element)
						{
							subcategory = new Subcategory();
							log.debug("New Subcategory created.");
							category.add(subcategory);
							nameAttr = listElement.getAttributes().getNamedItem(ProductsConstants.NAME_ATTR.getContent()).getNodeValue();
							log.debug("Subcategory name: name={}", nameAttr);
							subcategory.setName(nameAttr);
							productList = listElement.getChildNodes();
							for (int h = 0; h < productList.getLength(); h++)
							{
								Node productElement = productList.item(h);
								log.debug("Third cycle: element={}", productElement);
								if (productElement instanceof Element)
								{
									product = new Product();
									log.debug("New Product created.");
									subcategory.add(product);
									nameAttr = productElement.getAttributes().getNamedItem(ProductsConstants.NAME_ATTR.getContent()).getNodeValue();
									product.setProductName(nameAttr);
									NodeList productsParams = productElement.getChildNodes();
									for (int k = 0; k < productsParams.getLength(); k++)
									{		
										Node productNode = productsParams.item(k);
										log.debug("Fourth cycle: element={}", productNode);
										if (productNode instanceof Element)
										{
											Element productParam = (Element) productNode;
											String paramName = productParam.getNodeName();
											String textContent = productParam.getTextContent();
											log.debug("Product params: paramName={}, textContent={}", paramName, textContent);
											if ((paramName != null) && ((paramName = paramName.trim()).length() > 0))
											{
												if (paramName.equalsIgnoreCase(ProductsConstants.NOT_IN_STOCK.getContent()))
												{
													product.setNotInStock(true);
												}
												else
												{
													if ((textContent != null) && ((textContent = textContent.trim()).length() > 0))
													{
														for (ProductsConstants pc : ProductsConstants.values())
														{
															String content = pc.getContent();
															if (paramName.equalsIgnoreCase(content))
															{
																switch(pc)
																{
																case COLOR:
																	product.setColor(textContent);
																	break;
																case MODEL:
																	product.setModel(textContent);
																	break;
																case PROVIDER:
																	product.setProvider(textContent);
																	break;
																case PRICE:
																	product.setPrice(textContent);
																	break;
																case DATE:
																	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
																	java.util.Date utilDate = null;
																	try 
																	{
																		utilDate = dateFormat.parse(textContent);
																	} 
																	catch (ParseException e) 
																	{
																		log.error("Can't parse date", e);
																	}
																	product.setDateOfIssue(utilDate);
																	break;
																	default:
																		break;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return categories;
		}
		return new ArrayList<Category>();
	}
}
