package com.bjss.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author sheriffolagunju
 *
 */
public class ReviewOrderPage extends BasePage{
	
	private static final By ORDER_DETAILS = By.cssSelector("td:nth-child(7)>a[class^=btn]");

	private static final By ORDER_MESSAGE = By.cssSelector("div:nth-of-type(5) tr>td:nth-child(2)");
	
	@FindBy(css = "#sendOrderMessage p>select")
	private WebElement selectProduct;
	
	@FindBy(css = "#sendOrderMessage p>textArea")
	private WebElement messageField;
	
	@FindBy(css = "#sendOrderMessage >div>button")
	private WebElement submitMessage;
	
	@FindBy(id = "block-order-detail")
	private WebElement messageBlock;
	
	@FindBy(id = "order-detail-content")
	private WebElement orderTable;

	public ReviewOrderPage (WebDriver driver) {
		super(driver);
	}
	
	public ReviewOrderPage viewLastCreatedOrder(String orderNumber) {
		WebElement lastOrder = webDriver.findElement(By.xpath("//tr[contains(.,'"+orderNumber+"')]"));
		WebElement order = waitForVisibilityOfElement(ORDER_DETAILS,lastOrder);
		order.click();
		return this;	
	}
	
	public ReviewOrderPage chooseProductAndAddMessage(String message) {
		waitForVisibilityOfElement(selectProduct);
		scrollScreenUntilVisibiltyofElement(selectProduct);
		selectDropDownByIndex(selectProduct,1);
		messageField.sendKeys(message);
		submitMessage.click();
		return this;	
	}
	
	public String getMessage() {
		WebElement messageInOrder = waitForVisibilityOfElement(ORDER_MESSAGE,messageBlock);
		scrollScreenUntilVisibiltyofElement(messageInOrder);
		return getElementText(messageInOrder, "");
	}
	 
	public String getColorOfOrder(int productPosition) {
		By orderDetails = By.xpath("//tbody/tr["+productPosition+"]/td[2]/label");
		WebElement orderedProduct = waitForVisibilityOfElement(orderDetails,orderTable);
		scrollScreenUntilVisibiltyofElement(orderedProduct);
		return getElementText(orderedProduct, "").split("-")[1].split(",")[0].split(":")[1].trim();	
	}
}
