package com.bjss;

/**
 * @author sheriffolagunju
 *
 */
public class ItemDetails {

	private String itemSize;
	private String itemName;
	private String itemColor;
	private double itemPrice;
	private int itemQuantity;

	public ItemDetails(String size, double price, String color, String name, int quantity) {
		this.itemName = name;
		this.itemColor = color;
		this.itemPrice = price;
		this.itemSize = size;
		this.itemQuantity = quantity;
	}

	public String getItemSize() {
		return itemSize;
	}

	public String getItemName() {
		return itemName;
	}
	
	public String getItemColor() {
		return itemColor;
	}

	public double getItemPrice() {
		return itemPrice;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}

}
