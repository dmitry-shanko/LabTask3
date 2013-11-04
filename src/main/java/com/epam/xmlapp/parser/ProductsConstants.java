package com.epam.xmlapp.parser;

public enum ProductsConstants
{
	CATEGORY_LIST("categoryList"),
	CATEGORY("category"),
	SUBCATEGORY("subcategory"),
	NAME_ATTR("name"),
	PRODUCT("product"),
	PROVIDER("provider"),
	MODEL("model"),
	COLOR("color"),
	DATE("date"),
	PRICE("price"),
	NOT_IN_STOCK("notInStock");
	private String content;
	private ProductsConstants (String content) 
	{
		this.content = content;
	}

	public String getContent()
	{
		return content;
	}
}
