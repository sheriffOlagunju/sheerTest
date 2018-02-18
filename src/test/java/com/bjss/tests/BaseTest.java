package com.bjss.tests;

import org.openqa.selenium.support.PageFactory;
import com.bjss.pages.HomePage;

import util.SeleniumBaseTest;

public abstract class BaseTest extends SeleniumBaseTest {

	public enum ProductSize {

		SMALL_SIZE("S"),
		MEDIUM_SIZE("M"),
		LARGE_SIZE("L");
		
		private String sizeOfProduct;
		
		ProductSize(String size){
			sizeOfProduct = size;
		}
		
		public String getItemSize(){
			return sizeOfProduct;
		}
	}
	
	public HomePage instantiateHomePage() {
		info("Navigating to base url");
		goToHomePage();
		return PageFactory.initElements(driver, HomePage.class);
	}
}
