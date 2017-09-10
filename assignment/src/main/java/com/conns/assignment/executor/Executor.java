package com.conns.assignment.executor;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.conns.assignment.utils.PropertiesReader;
import com.google.common.base.Function;

/**
 * This is the class to initialize the browser and to define common
 * verification/assertion methods
 * 
 * @author Udit Khanna
 *
 */
public class Executor {
	static Logger logger = Logger.getLogger(Executor.class);

	public static WebDriver driver;
	private static final String FIREFOX = "Firefox";
	private static final String WAIT_PROPERTY = "wait";
	private static final String POLLING = "polling";
	private static final String BROWSER_TYPE = "browserType";
	private static final String CHROME = "Chrome";
	private static final String INTERNET_EXPLORER = "IE";
	private static final String STARTING_FIREFOX = "Starting Firefox browser";
	private static final String STARTING_CHROME = "Starting Chrome browser";
	private static final String STARTING_INTERNET_EXPLORER = "Starting Internet Explorer browser";
	private static final String INVALID_BROWSER_NAME = "Please enter a valid browser name. Currently only Firefox, Chrome and IE are supported";
	private static final String OPENING_URL = "Opening URL: ";
	private static final String ACTUAL_VALUE = "Actual value is: ";
	private static final String EXPECTED_VALUE = "Expected value is: ";
	private static final String VERIFY_TEXT = "Verifying the text";
	private static final String CLOSE_BROWSER = "Closing the browser";
	private static final String IMPLICIT_WAIT = "implicitWait";
	private static final String START_BROWSER = "Inside startBrowser Method";
	private static final String NAVIGATE_TO = "Inside navigateTo Method";
	private static final String GET_JSON = "Getting Json object";
	private static final String WRITE_TEXT = "Entering text is ";
	private static final String TEST_DATA_FILE_PATH = "\\src\\test\\resources\\";
	private static final String TEST_DATA_PATH = System.getProperty("user.dir")
			+ TEST_DATA_FILE_PATH;

	
	/**
	 * Opens a particular browser based on the data in config.properties
	 * 
	 * @return WebDriver driver reference
	 */
	public void startBrowser() {
		logger.info(START_BROWSER);
		try {
			if (PropertiesReader.readProperty(BROWSER_TYPE).equalsIgnoreCase(
					FIREFOX)) {
				// created firefox instance
				driver = new FirefoxDriver();
				logger.info(STARTING_FIREFOX);
			} else if (PropertiesReader.readProperty(BROWSER_TYPE)
					.equalsIgnoreCase(CHROME)) {
				// created chrome instance
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "\\Support Library\\chromedriver.exe");
				driver = new ChromeDriver();
				logger.info(STARTING_CHROME);
			} else if (PropertiesReader.readProperty(BROWSER_TYPE)
					.equalsIgnoreCase(INTERNET_EXPLORER)) {
				// created Internet Explorer instance
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir")
								+ "\\Support Library\\IEDriverServer.exe");
				DesiredCapabilities capabilities = DesiredCapabilities
						.internetExplorer();
				capabilities.setCapability("ie.ensureCleanSession", true);
				driver = new InternetExplorerDriver(capabilities);
				logger.info(STARTING_INTERNET_EXPLORER);
			} else {
				logger.warn(INVALID_BROWSER_NAME);
				Assert.fail(INVALID_BROWSER_NAME);
			}
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	/**
	 * Navigating to the particular URL based on the data in config.properties
	 */
	public void navigateTo(String Url) {
		logger.info(NAVIGATE_TO);
		driver.manage().window().maximize();
		driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(PropertiesReader
								.readProperty(IMPLICIT_WAIT)), TimeUnit.SECONDS);
		try {
			logger.info(OPENING_URL + PropertiesReader.readProperty(Url));
			driver.get(PropertiesReader.readProperty(Url));
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @param filePath
	 *            contains file path
	 * @return
	 */
	public JSONObject getJsonObject(String filePath) {
		JSONObject jsonObject = null;
		InputStream input;
		try {
			logger.info(GET_JSON);
			input = this.getClass().getClassLoader().getResourceAsStream((filePath));
			jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(input)));
			return jsonObject;
		} catch (Exception exception){
			logger.fatal(exception);
			exception.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * Verifying the URL of Page
	 * 
	 * @param text
	 *            expected URL
	 */
	public void verifyUrl(WebDriver driver, String text) {
		try {
			logger.info(VERIFY_TEXT);
			String actual = driver.getCurrentUrl();
			logger.info(ACTUAL_VALUE + actual);
			logger.info(EXPECTED_VALUE + text);
			Assert.assertEquals(actual, text);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
	
	
	/**
	 * Clicking the Button
	 * 
	 * @param element
	 *            contains WebElement
	 */
	public void clickButtonFluently(WebElement element) {
		try {


			new FluentWait<>(element)
					.withTimeout(
							Integer.parseInt(PropertiesReader
									.readProperty(WAIT_PROPERTY)),
							TimeUnit.SECONDS)
					.pollingEvery(
							Integer.parseInt(PropertiesReader
									.readProperty(POLLING)),
							TimeUnit.MILLISECONDS)
					.ignoring(WebDriverException.class)
					.until(new Function<WebElement, Boolean>() {
						@Override
						public Boolean apply(WebElement element) {
							element.click();
							return true;
						}
					});
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}

	}
	
	/**
	 * 
	 * @param element
	 *            contains WebElement of text box.
	 * @param value
	 *            contain input data from user
	 */
	public void sendTextInTextBox(WebElement element, String value) {

		try {
			logger.info( WRITE_TEXT + value);
			element.clear();
			element.sendKeys(value);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	/**
	 * 
	 * @param element
	 *            contains WebElement to get the actual Text
	 * @param text
	 *            contains the expected text
	 */
	public void verifyTextContains(WebElement element, String text) {
		try {
			logger.info(VERIFY_TEXT);
			String actual = element.getText().trim();
			logger.info(ACTUAL_VALUE + actual);
			logger.info(EXPECTED_VALUE + text);
			Assert.assertEquals(actual.contains(text), true);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	/**
	 * This method is used for taking a screenshot within a method. Not to be
	 * confused with captureScreenshotOnFailure() under TestListener class
	 */

	public void captureScreenshot() {
		logger.info("Capture the Screen shot");
		File scrFile;
		try {
			String methodName = Thread.currentThread().getStackTrace()[2]
					.getMethodName();
			String GENERIC_SCREENSHOTPATH = System.getProperty("user.dir")
					+ "\\artifacts\\Passed_Screenshot\\";

			System.out.println("Capture the Screen shot" + methodName);

			scrFile = new File(GENERIC_SCREENSHOTPATH);

			scrFile = ((TakesScreenshot) Executor.driver)
					.getScreenshotAs(OutputType.FILE);
			GENERIC_SCREENSHOTPATH = GENERIC_SCREENSHOTPATH
					+ methodName
					+ "_"
					+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")
							.format(Calendar.getInstance().getTime()) + ".png";
			FileUtils.copyFile(scrFile, new File(GENERIC_SCREENSHOTPATH));

		} catch (Exception exception) {
			logger.warn(exception);
		}
	}
	/**
	 * fluent Wait ToCheck Visiblity of element
	 * 
	 * @param element
	 *            contains WebElement
	 */
	public void fluentWaitToCheckVisible(WebElement element) {
		try {

			new FluentWait<>(element)
					.withTimeout(
							Integer.parseInt(PropertiesReader
									.readProperty(WAIT_PROPERTY)),
							TimeUnit.SECONDS)
					.pollingEvery(
							Integer.parseInt(PropertiesReader
									.readProperty(POLLING)),
							TimeUnit.MILLISECONDS)
					.ignoring(WebDriverException.class)
					.until(new Function<WebElement, Boolean>() {
						@Override
						public Boolean apply(WebElement element) {
							return element.isDisplayed();
						}
					});
		} catch (Exception exception) {
			logger.info(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
	
	
	/**
	 * closing the browser
	 */
	public void closeBrowser() {
		logger.info(CLOSE_BROWSER);

		driver.quit();
	}
	
	/**
	 * Hover over an element and click element then visible
	 * 
	 * @param hoverElement
	 * 				contains WebElement to hover to
	 * @param clickElement
	 * 				contains WebElement to click after hover
	 */
	public void hoverOverElementAndClickAnotherElement(WebElement hoverElement, WebElement clickElement) {
		logger.info("Hover over an element and click element when visible");
		Actions action = new Actions(driver);
		action.moveToElement(hoverElement).perform();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(clickElement));
		action.click(clickElement).build().perform();
	}
	
	/**
	 * To compare values of two strings
	 * 
	 * @param string1
	 *            contains string 1 to compare
	 * @param string2
	 *            contains string 2 to compare
	 */
	public void compareStringvalues(String string1, String string2){
		try {
			logger.info(VERIFY_TEXT);
			logger.info(ACTUAL_VALUE + string1);
			logger.info(EXPECTED_VALUE + string2);
			Assert.assertEquals(string1.contains(string2), true);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
	
	/**
	 * To set windows size
	 * 
	 * @param x
	 *            contains x coordinates
	 * @param y
	 *            contains y coordinates
	 */
	public void setWindowSize(int x, int y){
		logger.info("Setting window size to: " + x + "x" + y);
		try {
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().window().setSize(new Dimension(x,y));
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());		}
	}
	
	/**
	 * To get position of element on the webPage
	 * 
	 * @param element
	 *            contains element
	 * @return
	 * 				List containing x and y coordinates
	 */
	public ArrayList<Integer> getPositionOfElement(WebElement element) {
		logger.info("Getting location of element");
		ArrayList<Integer> returnCoordinates = new ArrayList<>();
		try {
			Point coordinates = element.getLocation();
			returnCoordinates.add(coordinates.getX());
			returnCoordinates.add(coordinates.getY());	
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		return returnCoordinates;		
	}
	
	/**
	 * To compare position of element on the webPage in desktop and mobile mode
	 * 
	 * @param elementPosInDesktop
	 *            contains position of element in desktop
	 * @param elementPosInMobile
	 * 			  contains position of element in mobile
	 */
	public void comparePositionsOfElement(ArrayList<Integer> elementPosInDesktop,
			ArrayList<Integer> elementPosInMobile) {
		logger.info("Comparing position of elements in desktop and mobile mode");
		try {
			elementPosInDesktop.removeAll(elementPosInMobile);
			if(!(elementPosInDesktop.size() == 0)){
				logger.info("Element position changed");
			}else{
				logger.info("Element position not changed");
			}
			assertFalse(elementPosInDesktop.size() == 0);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}
	
	/**
	 * To maximise the current window
	 * 
	 */
	public void maximiseWindow() {
		logger.info("Maximising window size");
		try {
			driver.manage().window().maximize();
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	
	/**
	 * To get all the HyperLinks on webpage
	 * 
	 * @return
	 * 		total number of HyperLinks 
	 * 
	 */
	public int getAllHyperlinks() {
		logger.info("Getting total number of HyperLinks on the page");
		int totalLinks = 0;
		try {
			totalLinks = driver.findElements(By.tagName("a")).size();
			logger.info("Total HyperLinks on page: " + totalLinks);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		return totalLinks;
	}

	/**
	 * To compare number of HyperLinks in Desktop and Mobile mode
	 * 
	 * @param totalHyperlinksInDesktopMode
	 * 			total HyperLinks In Desktop Mode
	 * 
	 * @param totalHyperlinksInMobileMode
	 * 			total HyperLinks In Mobile Mode
	 * 
	 */
	public void compareTotalHyperlinks(int totalHyperlinksInDesktopMode, int totalHyperlinksInMobileMode) {
		logger.info("Comparing total hyperlinks in desktop and mobile mode");
		try {
			if(totalHyperlinksInDesktopMode == totalHyperlinksInMobileMode){
				logger.info("Same number of links on both modes");
			}else if (totalHyperlinksInDesktopMode > totalHyperlinksInMobileMode){
				logger.info("More HyperLinks in Desktop mode");
			}else{
				logger.info("More HyperLinks in Mobile mode");
			}
			assertTrue(totalHyperlinksInDesktopMode == totalHyperlinksInMobileMode);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	
	/**
	 * To get attribute value of list of elements
	 * 
	 * @param listOfElements
	 * 			list Of Elements
	 * 
	 * @param attribute
	 * 			attribute to be get
	 * 
	 * @return
	 * 			List of attribute values
	 * 
	 */
	public ArrayList<String> getAttributeValueOfElements(List<WebElement> listOfElements, String attribute) {
		logger.info("Getting value of attribute: " + attribute);
		ArrayList<String> attributeValues = new ArrayList<String>();
		try {
			for(WebElement element: listOfElements){
				attributeValues.add(element.getAttribute(attribute));
			}
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		return attributeValues;
	}

	/**
	 * To get text of list of elements
	 * 
	 * @param listOfElements
	 * 			list Of Elements
	 * 
	 * @return
	 * 			List of text values
	 * 
	 */
	public ArrayList<String> getTextOfElements(List<WebElement> listOfElements) {
		logger.info("Getting text of elements");
		ArrayList<String> textValues = new ArrayList<String>();
		try {
			for(WebElement element: listOfElements){
				textValues.add(element.getText());
			}
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		return textValues;
	}

	/**
	 * To compare two lists
	 * 
	 * @param list1
	 * 			list1 Of Elements
	 * 
	 * @param list2
	 * 			list1 Of Elements
	 */
	public void compareTwoLists(ArrayList<String> list1,
			ArrayList<String> list2) {
		logger.info("Comparing two lists");
		try {
			list1.removeAll(list2);
			if(list1.size()==0){
				logger.info("Lists are same");
			}else{
				logger.info("Lists are not same");
			}
			assertTrue(list1.size()==0);
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}

	/**
	 * To check Text Of Elements
	 * 
	 * @param element
	 * 			WebElement
	 * 
	 * @param message
	 * 			expected String
	 */
	public void checkTextOfElement(WebElement element, String message) {
		logger.info("Checking text of element");
		try {
			logger.info("Actual Text: " + element.getText());
			logger.info("Expected Text: " + message);
			assertTrue(element.getText().equalsIgnoreCase(message));
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		
	}

	/**
	 * To check if Text is contained in Elements
	 * 
	 * @param element
	 * 			WebElement
	 * 
	 * @param message
	 * 			expected String
	 */
	public void checkTextContains(WebElement element, String message) {
		logger.info("Checking text of element contains expected");
		try {
			logger.info("Actual Text: " + element.getText());
			logger.info("Expected Text: " + message);
			assertTrue(element.getText().contains(message));
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
	}


	/**
	 * To get Text Of element
	 * 
	 * @param element
	 * 			WebElement
	 * 
	 * @return 
	 * 			text of element
	 */
	public String getTextOfElement(WebElement element) {
		logger.info("Getting text of element");
		String returnStr = null;
		try {
			returnStr = element.getText();
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		}
		return returnStr;
	}
}