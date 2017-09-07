package com.conns.assignment.pages;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.conns.assignment.executor.*;

public class TestPage {
	Logger logger;
	Executor executor;

	public TestPage() {

		logger = Logger.getLogger(TestPage.class);
		executor = new Executor();
	}

	private final String TESTDATA = "TestData.json";



}
