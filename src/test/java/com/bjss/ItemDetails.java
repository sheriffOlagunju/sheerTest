package com.bjss;

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

	public void setItemSize(String size) {
		itemSize = size;
	}

	public void setItemName(String name) {
	     itemName = name;
	}
	
	public void setItemColor(String color) {
		itemColor = color;
	}

	public void setItemPrice(double productPrice) {
		itemPrice = productPrice;
	}
	public void setItemQuantity(int quantity) {
		 quantity = itemQuantity;
	}
	
	@Override
	public String toString() {
		System.out.println("Details of added item is size:"+itemSize +" name:"+itemName+" color: "+itemColor+" price: "+itemPrice+" quantity: "+ itemQuantity);
		return "Details of added item is size:"+itemSize +" name:"+itemName+" color: "+itemColor+" price: "+itemPrice+" quantity: "+ itemQuantity;
		
	}

}
