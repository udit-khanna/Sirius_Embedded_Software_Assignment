package com.conns.assignment.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import com.conns.assignment.executor.*;

public class TestPage {
	private static final String ZIPCODE = "29104";
	Logger logger;
	Executor executor;
	private final String TESTDATA = "TestData.json";
	private JSONObject testDataJsonObj;
	private JSONObject mobileJsonObj;

	private String mobileXCoordinates;
	private String mobileYCoordinates;
	
	private ArrayList<Integer> storeLocatorPosInDesktop;
	private ArrayList<Integer> storeLocatorPosInMobile; 
	private ArrayList<Integer> payBillPosInDesktop;
	private ArrayList<Integer> payBillPosInMobile;
	private ArrayList<Integer> cartPosInDesktop;
	private ArrayList<Integer> cartPosInMobile;
	
	private int totalHyperlinksInDesktopMode;
	private int totalHyperlinksInMobileMode;
	
	private ArrayList<String> listOfAdImagesInDesktopMode;
	private ArrayList<String> listOfAdTextInDesktopMode;
	private ArrayList<String> listOfAdImagesInMobileMode;
	private ArrayList<String> listOfAdTextInMobileMode;

	private static final String EXPECTEDMESSAGEZIPCODE = "Delivery is available at your location.";
	private static final String EXPECTEDMESSAGEADDTOCARTINDESKTOP = "was added to your shopping cart.";
	private String expectedItemAdded;
	
	//locators
	@FindBy(id="store-locator")
	private WebElement storeLocator;
	
	@FindBy(id="pay-bill")
	private WebElement payBillIcon;
	
	@FindBy(id="cartHeader")
	private WebElement cartIcon;
	
	@FindBy(xpath = "//div[@class='item']/a/img")
	private List<WebElement> adImages;
	
	@FindBy(xpath = "//div[@class='item']/h2/a")
	private List<WebElement> adTexts;
	
	@FindBy(xpath = "//li[@id='li-primary-pronav-tv--audio---electronics']")
	private WebElement menuElement;
	
	@FindBy(xpath = "//a[@class='pronav-cat-a-sub1767-2']")
	private WebElement firstElementOnMenu;
	
	@FindBy(xpath = "//div[@class='rwd-category-list-btn']/div/button[2]")
	private WebElement addToCartCDP;
	
	@FindBy(xpath = "//button[@id='add-to-cart-submit-button']")
	private WebElement addToCartPDP;
	
	@FindBy(xpath = "//input[@id='warehouse-zip-code']")
	private WebElement zipCodeInputField;
	
	@FindBy(xpath = "//button[@class='button side-button' and @onclick='warehouseForm.submit()']")
	private WebElement updateButton;
	
	@FindBy(xpath = "//div[@id='warehouse_content']/span")
	private WebElement checkZipText;
	
	@FindBy(xpath = "//button[@id='warehouse_cart']")
	private WebElement addToCartWindow;
	
	@FindBy(xpath = "//li[@class='success-msg']/ul/li/span")
	private WebElement addedToCartMessage;
	
	@FindBy(xpath = "//h2[@class='product-name']/a")
	private WebElement addedItemInCartDesktopMode;
	
	@FindBy(xpath = "//strong[@id='cartHeader']")
	private WebElement cartIconInMobileMode;
	
	@FindBy(xpath = "//p[@class='product-name']/a")
	private WebElement checkItemAdded;

	public TestPage() {
		logger = Logger.getLogger(TestPage.class);
		executor = new Executor();
	}

	
	public void getMobileDimensions(){
		try {
			testDataJsonObj = executor.getJsonObject(TESTDATA);
			mobileJsonObj = testDataJsonObj.getJSONObject("mobile");
			mobileXCoordinates = mobileJsonObj.getString("x_coordinates").toString();
			mobileYCoordinates = mobileJsonObj.getString("y_coordinates").toString();
		} catch (Exception exception) {
			logger.fatal(exception);
			exception.printStackTrace();
		}
	}

	public void changeBrowserDimensionsMobile() {
		logger.info("Changing Browser dimensions");
		getMobileDimensions();
		executor.setWindowSize(Integer.parseInt(mobileXCoordinates), Integer.parseInt(mobileYCoordinates));
	}


	public void checkStoresIconReposition() {
		logger.info("Checking repositioning of Store Locator icon");
		executor.maximiseWindow();
		storeLocatorPosInDesktop = executor.getPositionOfElement(storeLocator);
		changeBrowserDimensionsMobile();
		storeLocatorPosInMobile = executor.getPositionOfElement(storeLocator);
		executor.comparePositionsOfElement(storeLocatorPosInDesktop, storeLocatorPosInMobile);
	}


	public void checkPayBillIconReposition() {
		logger.info("Checking repositioning of Pay Bill icon");
		executor.maximiseWindow();
		payBillPosInDesktop = executor.getPositionOfElement(payBillIcon);
		changeBrowserDimensionsMobile();
		payBillPosInMobile = executor.getPositionOfElement(payBillIcon);
		executor.comparePositionsOfElement(payBillPosInDesktop, payBillPosInMobile);		
	}


	public void checkCartIconReposition() {
		logger.info("Checking repositioning of Cart icon");
		executor.maximiseWindow();
		cartPosInDesktop = executor.getPositionOfElement(cartIcon);
		changeBrowserDimensionsMobile();
		cartPosInMobile = executor.getPositionOfElement(cartIcon);
		executor.comparePositionsOfElement(cartPosInDesktop, cartPosInMobile);		
	}


	public void checkNumberOfHyperlinksInDesktopMode() {
		logger.info("Getting number of hyperlinks on webpage in Desktop mode");
		executor.maximiseWindow();
		totalHyperlinksInDesktopMode = executor.getAllHyperlinks();
	}


	public void checkNumberOfHyperlinksInMobileMode() {
		logger.info("Getting number of hyperlinks on webpage in Mobile mode");
		changeBrowserDimensionsMobile();
		totalHyperlinksInMobileMode = executor.getAllHyperlinks();
	}


	public void compareNumberOfHyperLinks() {
		logger.info("Comparing number of HyperLinks in Desktop and Mobile mode");
		executor.compareTotalHyperlinks(totalHyperlinksInDesktopMode, totalHyperlinksInMobileMode);		
	}


	public void getAdImagesInDesktopMode() {
		logger.info("Getting images of ads in desktop mode");
		executor.maximiseWindow();
		listOfAdImagesInDesktopMode = executor.getAttributeValueOfElements(adImages, "alt");
	}


	public void getAdTextInDesktopMode() {
		logger.info("Getting Text of ads in desktop mode");
		executor.maximiseWindow();
		listOfAdTextInDesktopMode = executor.getTextOfElements(adTexts);
	}


	public void getAdImagesInMobileMode() {
		logger.info("Getting images of ads in mobile mode");
		executor.maximiseWindow();
		listOfAdImagesInMobileMode = executor.getAttributeValueOfElements(adImages, "alt");		
	}


	public void getAdTextInMobileMode() {
		logger.info("Getting Text of ads in Mobile mode");
		executor.maximiseWindow();
		listOfAdTextInMobileMode = executor.getTextOfElements(adTexts);		
	}


	public void compareAdImages() {
		logger.info("Comparing ad images in desktop and mobile mode");
		executor.compareTwoLists(listOfAdImagesInDesktopMode, listOfAdImagesInMobileMode);
	}


	public void compareAdTexts() {
		logger.info("Comparing ad texts in desktop and mobile mode");
		executor.compareTwoLists(listOfAdTextInDesktopMode, listOfAdTextInMobileMode);		
	}


	public void hoverOverMenuItemAndClick() {
		logger.info("Hovering Over the menu element");
		executor.hoverOverElementAndClickAnotherElement(menuElement, firstElementOnMenu);		
	}


	public void clickAddToCartOnCDP() {
		logger.info("Clicking Add to Cart on CDP");
		executor.clickButtonFluently(addToCartCDP);
	}


	public void clickAddToCartOnPDP() {
		logger.info("Clicking Add to Cart on PDP");
		executor.fluentWaitToCheckVisible(addToCartPDP);
		executor.clickButtonFluently(addToCartPDP);
	}


	public void enterZipCode() {
		logger.info("Entering zip code");
		executor.fluentWaitToCheckVisible(zipCodeInputField);
		executor.sendTextInTextBox(zipCodeInputField, ZIPCODE);
	}


	public void clickUpdateButton() {
		logger.info("Clicking on Update button");
		executor.clickButtonFluently(updateButton);
	}


	public void checkZipCodeAccepted() {
		logger.info("Checking Zip code entered is valid");
		executor.checkTextOfElement(checkZipText, EXPECTEDMESSAGEZIPCODE);		
	}


	public void clickAddToCartOnWindow() {
		logger.info("Clicking Add to Cart on Window");
		executor.clickButtonFluently(addToCartWindow);
	}


	public void checkItemAddedToCartInDesktopMode() {
		logger.info("Checking item is added to cart in Dekstop Mode");
		executor.checkTextContains(addedToCartMessage, EXPECTEDMESSAGEADDTOCARTINDESKTOP);
		expectedItemAdded = executor.getTextOfElement(addedItemInCartDesktopMode);
	}


	public void checkItemAddedToCartInMobileMode() {
		logger.info("Check item is added to cart in Mobile Mode");
		changeBrowserDimensionsMobile();
		executor.clickButtonFluently(cartIconInMobileMode);
		executor.checkTextOfElement(checkItemAdded, expectedItemAdded);
	}
	
	


}
