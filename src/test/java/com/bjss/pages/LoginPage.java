package com.bjss.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{

	@FindBy(id = "email")
    private WebElement emailField;
	
	@FindBy(id = "passwd")
    private WebElement passwordField;
	
	@FindBy(id = "SubmitLogin")
    private WebElement submitBtn;
	
	@FindBy(className = "logout")
    private WebElement logoutLink;
	
	@FindBy(css = ".account>span")
    private WebElement displayedName;
	
	@FindBy(css = "a[title=Women]")
	private WebElement womenCategory;
	
	@FindBy(className = "login")
	private WebElement login;

	public LoginPage(WebDriver driver) {
		super(driver);	
	}
	
	public BasePage login(String username, String password) {
		sendKeysDelayed(emailField,true,username);
		sendKeysDelayed(passwordField,true,password);
		submitBtn.click();
		return PageFactory.initElements(webDriver, BasePage.class);
	}
	
	public String getSignOutLink() {
		return getElementText(logoutLink,"");
	}
	
	public String getUsernameDisplayed() {
		return getElementText(displayedName,"");
	}
	
	public Boolean verifySuccessfulLogout() {
		waitForVisibilityOfElement(login);
		if(login.isDisplayed() ) {
			return true;
		}
		return false;
	}

	public ShoppingPage startShopping() {
		waitForVisibilityOfElement(womenCategory);
		womenCategory.click();
		return PageFactory.initElements(webDriver, ShoppingPage.class);
	}
}
