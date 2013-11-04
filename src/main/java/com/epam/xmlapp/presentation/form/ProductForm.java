package com.epam.xmlapp.presentation.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.epam.xmlapp.model.Category;

public class ProductForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4337545850084499790L;

	private String parserName;
	private List<Category> categoryList;
	/**
	 * 
	 */
	public ProductForm()
	{
		
	}
	/**
	 * @return the parserName
	 */
	public String getParserName() {
		return parserName;
	}
	/**
	 * @param parserName the parserName to set
	 */
	public void setParserName(String parserName) {
		this.parserName = parserName;
	}
	/**
	 * @return the categoryList
	 */
	public List<Category> getCategoryList() {
		return categoryList;
	}
	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<Category> categoryList) 
	{
		if (null == categoryList)
		{
			categoryList = new ArrayList<Category>();
		}
		this.categoryList = categoryList;
	}
}

