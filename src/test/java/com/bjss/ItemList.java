package com.bjss;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemList {

	private final Map<String, ItemDetails> list;
	
	public ItemList() {
		this.list = new  LinkedHashMap<>();
	}
	
	public void addItems(ItemDetails items) {
		if(items != null) {
			list.put(items.getItemName(), items);	
		}
	}
}
