package util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleniumBaseTest {

	protected static WebDriver driver;

	protected WebDriverWait webDriverWait;

	private static Properties prop;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static Properties getProp() {
		return prop;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void info(String info) {
		logger.info(info);
	}

	private void loadProperties() {
		try {
			if (prop == null) {
				prop = new Properties();
				prop.load(new FileInputStream("resources/application.properties"));
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	protected void instantiateDriver() {
		driver = getDriverInstance();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(getSystemProperties("implicit_wait", getProp())),
				TimeUnit.SECONDS);
	}

	private WebDriver getDriverInstance() {
		DesiredCapabilities capability;
		ChromeOptions options = new ChromeOptions();
		switch (getSystemProperties("browser", getProp())) {
		
		case "chrome":
			options.addArguments("test-type", "--start-maximizied", "disable-infobars", "chrome.switches",
					"--disable-extensions");
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("credentials_enable_service", false);
			prefs.put("password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			capability = DesiredCapabilities.chrome();
			capability.setCapability("nativeEvents", false);
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/resources/chromedriver");
			driver = new ChromeDriver(capability);
			break;

		case "firefox":
			// Firefox driver can be used here as appropriate
			break;
		default:
			break;
		}
		return driver;
	}
	
	private void maximizeWindow() {
		info("Maximizing window");
		driver.manage().window().maximize();
	}

	public void goToHomePage() {
		maximizeWindow();
		String urlLink = getSystemProperties("url", getProp());
		info("Navigating to this url -----> "+ urlLink);
		driver.navigate().to(urlLink);
	}

	public static String getSystemProperties(String propertyName, Properties properties) {
		final String systemProperty = System.getProperty(propertyName);
		return systemProperty != null ? systemProperty : properties.getProperty(propertyName);
	}

	public void instantiateProperties() {
		loadProperties();
		instantiateDriver();
	}

	public void closeBrowser() {
		getDriver().quit();
	}

}
