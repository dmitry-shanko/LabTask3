package com.epam.xmlapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Subcategory> subcategories = new ArrayList<Subcategory>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subcategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

	public void add(Subcategory subcategory)
	{
		if ((null != subcategories) && (null != subcategory))
		{
			this.subcategories.add(subcategory);
		}
	}

}