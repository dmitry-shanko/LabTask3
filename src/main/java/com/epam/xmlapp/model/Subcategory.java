package com.epam.xmlapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subcategory implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8432392238441040951L;

	
	private String name;
	private List<Product> products = new ArrayList<Product>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void add(Product product)
	{
		if ((null != products) && (null != product))
		{
			this.products.add(product);
		}
	}

}
