package com.bjss.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	public static WebDriver webDriver = null;
	public static WebDriverWait _webDriverWait;

	private static final long DEFAULT_WAIT_TIME_OUT = 30;
	private static final By LOCATOR_LOGOUT_LINK = By.cssSelector(".logout");

	public BasePage(WebDriver driver) {
		webDriver = driver;
		_webDriverWait = new WebDriverWait(webDriver, DEFAULT_WAIT_TIME_OUT);
	}

	protected void waitForDocumentReadyStateComplete() {
		_webDriverWait.until(ExpectedConditions.jsReturnsValue("document readyState complete"));
	}

	protected WebElement waitForVisibilityOfElement(WebElement element) {
		return _webDriverWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	protected WebElement waitForVisibilityOfElement(By locator, WebElement rootWebElement) {
        return _webDriverWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(rootWebElement,locator));
    }

	protected void waitForVisibilityOfAllElements(List<WebElement> allElements) {
		_webDriverWait.until(ExpectedConditions.visibilityOfAllElements(allElements));
	}

	public void executeJS(String code, WebElement e) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript(code, e);
	}

	public void scrollScreenUntilVisibiltyofElement(WebElement element) {
		executeJS("arguments[0].scrollIntoView(true)", element);
	}

	protected void hoverOverElement(WebElement el, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(el).build().perform();
	}

	public void switchToIframe(String iframeName) {
		WebElement iframe = webDriver.findElement(By.className(iframeName));
		webDriver.switchTo().frame(iframe);
	}

	public boolean isElementPresent(String cssLocator) {
		return !webDriver.findElements(By.cssSelector(cssLocator)).isEmpty();
	}

	protected void sendKeysDelayed(WebElement element, boolean clear, CharSequence... keysToSend) {
		waitForVisibilityOfElement(element);
		if (clear) {
			element.clear();
		}
		element.click();
		Actions actions = new Actions(webDriver);
		actions.sendKeys(keysToSend).perform();
	}

	protected String getElementText(WebElement element, String attribute) {
		waitForVisibilityOfElement(element);
		if (!attribute.isEmpty()) {
			return element.getAttribute(attribute).trim();
		}
		return element.getText().trim();
	}

	protected void selectDropDownByVisibleText(WebElement e, String val) {
		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(val);
	}
	
	protected void selectDropDownByIndex(WebElement e, int index) {
		Select dropdown = new Select(e);
		dropdown.selectByIndex(index);
	}

	public LoginPage logout() {
		WebElement logoutLink = _webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_LOGOUT_LINK));
		scrollScreenUntilVisibiltyofElement(logoutLink);
		logoutLink.click();
		return PageFactory.initElements(webDriver, LoginPage.class);
	}

	public void waitForPageLoad() {
		Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
		wait.until(new com.google.common.base.Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}
}
