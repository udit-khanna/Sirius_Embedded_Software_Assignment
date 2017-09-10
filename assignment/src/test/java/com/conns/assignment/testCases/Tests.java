package com.conns.assignment.testCases;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.conns.assignment.executor.Executor;
import com.conns.assignment.pages.TestPage;

public class Tests extends BaseTest{
	
	public Tests(){
		logger = Logger.getLogger(Tests.class);
	}
	
	@Test(priority = 1)
	public void checkRepositionedElements(){
		logger.info("inside checkRepositionedElements");
		testPage = PageFactory.initElements(Executor.driver, TestPage.class);
		testPage.checkStoresIconReposition();
		testPage.checkPayBillIconReposition();
		testPage.checkCartIconReposition();
	}
	
	@Test(priority = 2)
	public void checkNumberOfHyperlinks(){
		logger.info("inside checkNumberOfHyperlinks");
		testPage = PageFactory.initElements(Executor.driver, TestPage.class);
		testPage.checkNumberOfHyperlinksInDesktopMode();
		testPage.checkNumberOfHyperlinksInMobileMode();
		testPage.compareNumberOfHyperLinks();
	}
	
	@Test(priority = 3)
	public void checkAdInformation(){
		logger.info("inside checkAdInformation");
		testPage = PageFactory.initElements(Executor.driver, TestPage.class);
		testPage.getAdImagesInDesktopMode();
		testPage.getAdTextInDesktopMode();
		testPage.getAdImagesInMobileMode();
		testPage.getAdTextInMobileMode();
		testPage.compareAdImages();
		testPage.compareAdTexts();
	}
	
	@Test(priority = 4)
	public void checkCartContentsInDifferentModes(){
		logger.info("inside checkCartContentsInDifferentModes");
		testPage = PageFactory.initElements(Executor.driver, TestPage.class);
		testPage.hoverOverMenuItemAndClick();
		testPage.clickAddToCartOnCDP();
		testPage.enterZipCode();
		testPage.clickUpdateButton();
		testPage.checkZipCodeAccepted();
		testPage.clickAddToCartOnWindow();
		testPage.checkItemAddedToCartInDesktopMode();
		testPage.checkItemAddedToCartInMobileMode();
	}
}
