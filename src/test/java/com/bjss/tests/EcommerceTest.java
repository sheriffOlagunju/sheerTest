package com.bjss.tests;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bjss.ItemDetails;
import com.bjss.pages.CartPage;
import com.bjss.pages.HomePage;
import com.bjss.pages.LoginPage;
import com.bjss.pages.ReviewOrderPage;
import com.bjss.pages.ShoppingPage;

import util.NumberUtil;

public class EcommerceTest extends BaseTest {

	private static HomePage homePage;
	private static LoginPage loginPage;
	private static ShoppingPage shoppingPage;
	private static CartPage cartPage;
	private static ReviewOrderPage reviewOrder;
	private static ItemDetails firstItem;
	private static ItemDetails secondItem;

	private static String username;
	private static String password;
	private static String customerName;

	private static String initialSize;
	private static String newSize;
	private static String orderNumber;
	private static final String message = "Automation Test order "+ String.valueOf(NumberUtil.generateRandomNumber(1000,10000));  
	

	private static String secondItemSize;

	@BeforeClass
	public void SetUp() {
		info("Loading properties before starting test");
		instantiateProperties();
		username = getSystemProperties("login_email", getProp());
		password = getSystemProperties("login_password", getProp());
		customerName = getSystemProperties("customer_name", getProp());
		homePage = instantiateHomePage();
	}

	@Test
	(priority = 0)
	public void raiseOrderTest() throws InterruptedException {
		info("Ensuring on hompage");
		Assert.assertTrue(homePage.checkHomePageLogo());
		info("Signing in customer");
		homePage.clickSignInLink();

		loginPage = PageFactory.initElements(driver, LoginPage.class);
		info("Entering login details");
		loginPage.login(username, password);
		info("Verifying user is logged in");
		Assert.assertEquals("Sign out", loginPage.getSignOutLink());
		Assert.assertEquals(customerName, loginPage.getUsernameDisplayed());
		
		info("Starting shopping and quick view");
		shoppingPage = loginPage.startShopping();
		shoppingPage.doQuickView();
		initialSize = shoppingPage.getCurrentSize();
		
		info("Changing size of product and storing it details");
		newSize = shoppingPage.changeSize(initialSize);
		firstItem = shoppingPage.getDetailsOfProduct(newSize);
		info("Adding first product to cart and continuing shopping");
		shoppingPage.thenAddItemToCart()
		            .continueShopping();
		info("Adding second product to cart and checking out");
		shoppingPage.addAnotherItem();
		secondItemSize = shoppingPage.getCurrentSize();
		secondItem = shoppingPage.getDetailsOfProduct(secondItemSize);
		cartPage = shoppingPage.thenAddItemToCart()
				               .checkBasket();
		info("Checking all item details stored in ItemList to what is displayed on screen");
		verifyProductName();
		verifyProductSize();
		verifyProductColor();
		verifyProductQuantity();
		verifyAllProductPrices();
		orderNumber = cartPage.checkoutOrder()
		                      .payByWire()
		                      .retrieveOrder();

		info("Logging out");
		loginPage.logout();
		info("Verifying successful logout");
		Assert.assertTrue(loginPage.verifySuccessfulLogout());
	}
	
	@Test
	(priority = 1)
	public void reviewOrderTest() {
		
		info("Login to review order");
		loginPage.login(username, password);
		info("Reviewing order");
		reviewOrder = cartPage.viewOrders();
		
		info("Reviewing order using "+ orderNumber+" and adding message");
		reviewOrder.viewLastCreatedOrder(orderNumber)
		           .chooseProductAndAddMessage(message);
		info("Asserting message added to order");
		Assert.assertEquals(reviewOrder.getMessage(), message);
		info("Logging out");
		loginPage.logout();
	}
	
	@Test
	(priority = 2)
	public void captureFailureTest() {
		info("Login to review order");
		loginPage.login(username, password);
		info("Checking last created");
		cartPage.viewOrders();
		reviewOrder.viewLastCreatedOrder(orderNumber);
		
		info("Getting colors of products");
		String firstProductColor = reviewOrder.getColorOfOrder(1);
		String secondProductColor = reviewOrder.getColorOfOrder(2);
		
		//Passing Assertions
		Assert.assertEquals(firstProductColor, firstItem.getItemColor());
		Assert.assertEquals(secondProductColor, secondItem.getItemColor());
		
		//Failing Assertions
		Assert.assertEquals("Maroon",firstProductColor);
		
		info("Logging out");
		loginPage.logout();
	}

	public void verifyProductName() {
		String firstProductName = firstItem.getItemName();
		String productNameInBasket = cartPage.getProductName(0);
		Assert.assertEquals(firstProductName, productNameInBasket);
		
		String secondProductName = secondItem.getItemName();
		String secondProductNameproductNameInBasket = cartPage.getProductName(1);
		Assert.assertEquals(secondProductName, secondProductNameproductNameInBasket);
	}

	public void verifyProductSize() {
		String firstProductSize = firstItem.getItemSize();
		String productSizeInBasket = cartPage.getSize(0);
		Assert.assertEquals(firstProductSize, productSizeInBasket);

		String secondProductSize = secondItem.getItemSize();
		String secondProductSizeInBasket = cartPage.getSize(1);
		Assert.assertEquals(secondProductSize, secondProductSizeInBasket);
	}

	public void verifyProductColor() {
		String firstProductColor = firstItem.getItemColor();
		String productColorInBasket = cartPage.getColor(0);
		Assert.assertEquals(firstProductColor, productColorInBasket);
		
		String secondProductColor = secondItem.getItemColor();
		String secondProductColorInBasket = cartPage.getColor(1);
		Assert.assertEquals(secondProductColor, secondProductColorInBasket);
	}

	public void verifyProductQuantity() {
		int firstProductQuantity = firstItem.getItemQuantity();
		int productQuantityInBasket = cartPage.getQuantity(0);
		Assert.assertEquals(firstProductQuantity, productQuantityInBasket);
		
		int secondProductQuantity = secondItem.getItemQuantity();
		int secondProductQuantityInBasket = cartPage.getQuantity(1);
		Assert.assertEquals(secondProductQuantity, secondProductQuantityInBasket);
	}

	public void verifyAllProductPrices() {
		double firstProductPrice = formartDouble(firstItem.getItemPrice());
		double productPriceInBasket = formartDouble(cartPage.getEachProductPrice(0));
		Assert.assertTrue(firstProductPrice == productPriceInBasket);

		double secondProductPrice = formartDouble(secondItem.getItemPrice());
		double secondProductPriceInBasket = formartDouble(cartPage.getEachProductPrice(1));
		Assert.assertTrue(secondProductPrice == secondProductPriceInBasket);

	
		double totalPriceWithoutShipping = formartDouble(firstProductPrice + secondProductPrice);
		double productTotalPriceInBasket = formartDouble(cartPage.getOnlyProductTotalPrice());
		Assert.assertTrue(totalPriceWithoutShipping == productTotalPriceInBasket);

		double shippingCharge = formartDouble(cartPage.getShippingCharge());
		double totalPriceWithShipping = formartDouble(totalPriceWithoutShipping + shippingCharge);
	
		double productGrandTotalInBasket = formartDouble(cartPage.getGrandTotalPrice());
		Assert.assertTrue(totalPriceWithShipping == productGrandTotalInBasket);
		Assert.assertTrue((productGrandTotalInBasket - totalPriceWithoutShipping) == shippingCharge  ); 
	}
	
	public double formartDouble(Double inputNumber) {
		String retrievedValue = NumberUtil.formartToTwoDecimalPlace(inputNumber);
		return Double.parseDouble(retrievedValue);
	}

	@AfterClass
	public void tearDown() {
		info("Closing browser after finishing test");
		closeBrowser();
	}
}
