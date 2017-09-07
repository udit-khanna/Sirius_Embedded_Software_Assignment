package com.conns.assignment.testCases;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.conns.assignment.executor.Executor;
import com.conns.assignment.pages.TestPage;



public class BaseTest {
	
	Logger logger;
	Executor executor;
	TestPage amazon;
	private final String Website_URL = "URL";
	private final String STARTING_TESTCASE = "**********************Starting test cases *********************";
	public static List<String> passValue = new ArrayList<String>();
	public static List<String> failValue = new ArrayList<String>();

	public BaseTest() {
		logger = Logger.getLogger(BaseTest.class);
		executor = new Executor();

	}
	
	@BeforeMethod
	public void initialSetUp() {
		PropertyConfigurator.configure("log4j.properties");
		logger.info(STARTING_TESTCASE);
		executor.startBrowser();
		executor.navigateTo(Website_URL);
	}
	
	@AfterMethod
	public void closeBrowser() {
		logger.info("Closing the browser");
		executor.closeBrowser();
	}


}
