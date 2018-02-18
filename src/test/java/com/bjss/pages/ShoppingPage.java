package com.bjss.pages;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.bjss.ItemDetails;
import com.bjss.ItemList;
import com.bjss.tests.BaseTest.ProductSize;

/**
 * @author sheriffolagunju
 *
 */
public class ShoppingPage extends BasePage {

	@FindBy(css = "a[title=Women]")
	private WebElement womenCategory;

	@FindBy(css = "[class*=sf-menu]>li:nth-child(2)>a")
	private WebElement dresses;

	@FindBy(css = ".product_img_link>img")
	private List<WebElement> displayedProducts;

	@FindBy(css = ".quick-view")
	private List<WebElement> quickView;

	@FindBy(css = "[class*=fancybox-item]")
	private WebElement fancyBoxPopup;

	@FindBy(id = "group_1")
	private WebElement sizeDropDown;

	@FindBy(css = " #group_1>option[selected]")
	private WebElement selectedSize;

	@FindBy(id = "quantity_wanted")
	private WebElement quantity;

	@FindBy(id = "our_price_display")
	private WebElement price;

	@FindBy(css = ".selected > [class *= color_pick]")
	private WebElement selectedColor;

	@FindBy(css = "h1")
	private WebElement itemName;

	@FindBy(css = ".exclusive")
	private WebElement addToCart;
	
	private static final By CONTINUE_SHOPPING = By.cssSelector("span[title*=Continue]>span");
	
	@FindBy(id = "layer_cart")
	private WebElement cartPopUp;

	private static final By TO_CHECKOUT = By.cssSelector("[rel=nofollow][title*=Proceed]");

	@FindBy(css = "[class*=step_current]>span")
	private WebElement cartSummary;

	private static int randomNumber;

	public ShoppingPage(WebDriver driver) {
		super(driver);
		waitForPageLoad();
	}

	public ShoppingPage viewProducts(WebElement element) {
		waitForVisibilityOfElement(element);
		scrollScreenUntilVisibiltyofElement(element);
		element.click();
		waitForVisibilityOfAllElements(displayedProducts);
		return this;
	}

	public ShoppingPage viewWomenProducts() {
		viewProducts(womenCategory);
		return this;
	}

	public ShoppingPage doQuickView() {
		randomNumber = new Random().nextInt(displayedProducts.size());
		WebElement element = displayedProducts.get(randomNumber);
		scrollScreenUntilVisibiltyofElement(element);
		hoverOverElement(element, webDriver);
		waitForVisibilityOfElement(quickView.get(randomNumber));
		quickView.get(randomNumber).click();
		waitForVisibilityOfElement(fancyBoxPopup);
		return this;
	}

	public String getCurrentSize() {
		Boolean frameDisplayed = isElementPresent("[class*=fancybox-item]");
		if (frameDisplayed) {
			switchToIframe("fancybox-iframe");
		}
		return getElementText(selectedSize, "");
	}

	public String changeSize(String currentSize) {
		String changedToSize = null;
		if (currentSize.equals(ProductSize.SMALL_SIZE.getItemSize())) {
			selectDropDownByVisibleText(sizeDropDown, ProductSize.MEDIUM_SIZE.getItemSize());
			changedToSize = ProductSize.MEDIUM_SIZE.getItemSize();
		} else if (currentSize.equals(ProductSize.MEDIUM_SIZE.getItemSize())) {
			selectDropDownByVisibleText(sizeDropDown, ProductSize.LARGE_SIZE.getItemSize());
			changedToSize = ProductSize.LARGE_SIZE.getItemSize();
		} else if (currentSize.equals(ProductSize.LARGE_SIZE.getItemSize())) {
			selectDropDownByVisibleText(sizeDropDown, ProductSize.SMALL_SIZE.getItemSize());
			changedToSize = ProductSize.SMALL_SIZE.getItemSize();
		}
		return changedToSize;
	}

	public ItemDetails getDetailsOfProduct(String size) {
		String retrievedQuantity = getElementText(quantity, "value");
		int quantityOfProduct = Integer.parseInt(retrievedQuantity);
		double quantityPrice = Double.parseDouble(getElementText(price, "").replace("$", "").trim());
		String colorOfProduct = getElementText(selectedColor, "name");
		String nameOfProduct = getElementText(itemName, "");
		ItemDetails items = new ItemDetails(size, quantityPrice, colorOfProduct, nameOfProduct, quantityOfProduct);

		ItemList list = new ItemList();
		list.addItems(items);
		items.toString();
		return items;
	}

	public ShoppingPage thenAddItemToCart() {
		waitForVisibilityOfElement(addToCart);
		addToCart.click();
		return this;
	}

	public ShoppingPage continueShopping() {
		WebElement continueOnShopping = waitForVisibilityOfElement(CONTINUE_SHOPPING,cartPopUp);
		continueOnShopping.click();
		return this;
	}

	public ShoppingPage addAnotherItem() {
		viewProducts(dresses);
		doQuickView();
		return this;
	}

	public CartPage checkBasket() {
		WebElement proceedToCheckout =  waitForVisibilityOfElement(TO_CHECKOUT,cartPopUp);
		proceedToCheckout.click();
		waitForVisibilityOfElement(cartSummary);
		return PageFactory.initElements(webDriver, CartPage.class);
	}

}
