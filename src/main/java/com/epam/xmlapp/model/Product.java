package com.epam.xmlapp.model;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7370706380601305592L;
	
	private String productName;
	private String provider;
	private String model;
	private String color;
	private String price;
	private Date dateOfIssue;
	private boolean notInStock;
	public Product()
	{
		
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the dateOfIssue
	 */
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	/**
	 * @param dateOfIssue the dateOfIssue to set
	 */
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	/**
	 * @return the notInStock
	 */
	public boolean isNotInStock() {
		return notInStock;
	}
	/**
	 * @param notInStock the notInStock to set
	 */
	public void setNotInStock(boolean notInStock) {
		this.notInStock = notInStock;
	}

	
}
