package com.bjss.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage{
	
	private static final By PRODUCT_NAME = By.cssSelector("td:nth-child(2)>p>a");
	
	private static final By OTHER_DESCRIPTION = By.cssSelector("td:nth-child(2)>small>a");
	
	private static final By PRODUCT_QUANTITY = By.cssSelector("[class*=cart_quantity_input]");
	
	private static final By PRODUCT_PRICE = By.cssSelector("td:nth-child(6)>span");
	
	@FindBy(css = "tr[id^=product_]")
	private List<WebElement> rowsOfProducts;
	
	@FindBy(id = "total_product")
	private WebElement totalPrice;
	
	@FindBy(id = "total_shipping")
	private WebElement shippingCharge;
	
	@FindBy(id = "total_price")
	private WebElement grandTotal;
	
	@FindBy(css = "[class^=button][title*=Proceed]>span")
	private WebElement proceedToCheckout;
	
	@FindBy(css = "[name=processAddress]")
	private WebElement proceesAddress;
	
	@FindBy(css = "[name=processCarrier]")
	private WebElement processCarrier;
	
	@FindBy(id = "cgv")
	private WebElement agreeBtn;
	
	@FindBy(css = ".bankwire")
	private WebElement bankwire;
	
	@FindBy(css = "#cart_navigation>button")
	private WebElement confirmOrder;
	
	@FindBy(css = "[title=Orders]")
	private WebElement orders;
	
	@FindBy(xpath = ".//*[@id='center_column']/div")
	private WebElement orderNumber;

	public CartPage(WebDriver driver) {
		super(driver);
		waitForPageLoad();
	}
	
	public String getProductName(int whichRow) {
		WebElement nameOfProduct = waitForVisibilityOfElement(PRODUCT_NAME,rowsOfProducts.get(whichRow));
		return getElementText(nameOfProduct,"");
	}
	
	private String otherDetails(int whichRow) {
		WebElement otherDescriptions = waitForVisibilityOfElement(OTHER_DESCRIPTION,rowsOfProducts.get(whichRow));
		return getElementText(otherDescriptions,"");	
	}
	
	public String getColor(int whichRow) {
		String  colorValue = otherDetails(whichRow);
		return colorValue.split(",")[0].split(":")[1].trim();
	}
	
	public String getSize(int whichRow) {
		String  sizeValue = otherDetails(whichRow);
		return sizeValue.split(",")[1].split(":")[1].trim();
	}
	
	public int getQuantity(int whichRow) {
		WebElement productQuantity = waitForVisibilityOfElement(PRODUCT_QUANTITY,rowsOfProducts.get(whichRow));
		String retrievedValue =  getElementText(productQuantity,"value");
		return Integer.parseInt(retrievedValue);
	}
	
	public double getEachProductPrice(int whichRow) {
		WebElement productPrice = waitForVisibilityOfElement(PRODUCT_PRICE,rowsOfProducts.get(whichRow));
		String retrievedPrice=  getElementText(productPrice,"").replace("$", "");
		return Double.parseDouble(retrievedPrice);
	}
	
	public double getOnlyProductTotalPrice() {
		waitForVisibilityOfElement(totalPrice);
		String retrievedPrice =  getElementText(totalPrice,"").replace("$", "");
		return Double.parseDouble(retrievedPrice);		
	}
	
	public double getShippingCharge() {
		waitForVisibilityOfElement(shippingCharge);
		String retrievedPrice =  getElementText(shippingCharge,"").replace("$", "");
		return Double.parseDouble(retrievedPrice);		
	}
	
	public double getGrandTotalPrice() {
		waitForVisibilityOfElement(grandTotal);
		String retrievedPrice =  getElementText(grandTotal,"").replace("$", "");
		return Double.parseDouble(retrievedPrice);		
	}
	
	private void waitThenClick(WebElement element, boolean needToAgree) {
		waitForVisibilityOfElement(element);
		scrollScreenUntilVisibiltyofElement(element);
		if(needToAgree) {
			agreeBtn.click();
		}
		element.click();
	}
	
	public CartPage checkoutOrder() {
		waitThenClick(proceedToCheckout,false);
		waitThenClick(proceesAddress,false);
		waitThenClick(processCarrier,true);
		return this;
	}
	
	public CartPage  payByWire() {
		waitThenClick(bankwire,false);
		waitThenClick(confirmOrder, false);
		return this;
	}
	
	public String retrieveOrder() {
		return getElementText(orderNumber,"").split("reference")[1].split("in")[0].trim();
	}
	
	public ReviewOrderPage viewOrders() {
		waitThenClick(orders,false);
		return PageFactory.initElements(webDriver, ReviewOrderPage.class);
	}
}
