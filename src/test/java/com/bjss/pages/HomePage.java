package com.bjss.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author sheriffolagunju
 *
 */
public class HomePage extends BasePage {
	
	    @FindBy(className = "login")
	    private WebElement signIn;

	    @FindBy(css = "[class *='logo']")
	    private WebElement logo;
	    
	    @FindBy(id = "email")
	    private WebElement emailField;
	
	
	public HomePage(WebDriver driver) {
		super(driver);
		waitForPageLoad();
	}
	
	public Boolean checkHomePageLogo() {
		waitForVisibilityOfElement(logo);
		return logo.isDisplayed();
	}
	
	public void clickSignInLink() {
		waitForVisibilityOfElement(signIn);
		signIn.click();
		waitForVisibilityOfElement(emailField);
	}

}
