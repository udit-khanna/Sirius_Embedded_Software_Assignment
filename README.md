# Sirius_Embedded_Software_Assignment

This assignment aims to fulfill following tasks:
- Write Scripts to work on at least one Browser in Mobile and Desktop modes.
- Check atleast 3 web elements which get repositioned when the resolution is changed.
- Indicate how the number of links change based in resolution. Count the number of hyperlinks in dekstop mode and then compare through Script if there are same no. of links available in mobile mode.
- Check if carousal scripts do get ad information both image and text for different browser resolutions.
- Add as item to cart in desktop size and go to cart in Mobile size. Verify the contents can be displayed.


FrameWork Structure: POM(Page Object Model)
1. The existing assignment is based on POM frame with TestNG execution framework and Maven Build tool.
2. Folder Structure for the framework:
	PKG: src->main->java contains following:
				- ConFig: containing config.properties containing global variables
				- Utils: containing PropertiesReader.java
				- Executor: containing executor.java which has all the logical implemented methods
				- Pages: containing TestPage.java containing all the locators and page specific methods
				
	PKG: src->test->java contains following:
				- TestCases: containing Basetest.java having BeforeTest and AfterTest methods, also containing initialization of other class references
										Tests.java having all the tests
										
	PKG: src->test->resources contains following:
				- TestData.json which has all testdata required for execution
	
	Folder: log: containing all logs
	
	Folder: Support Library: containing chromedriver.exe, geckodriver.exe, iedriver.exe
	
	Folder: target:  contains executable for the project
					 contains surefire-reports which has emailable-report.html for reporting
					 
	File: log4j.properties containing configuration for log4j
	
	File: pom.xml used for running Maven project
	
	File: testng.xml containing all test suites to be run
	

How to run tests:

Pre-requisites:
1. java 1.8 should be installed on the local machine
2. Maven should be installed on the local machine
3. Open commandline and move to folder */assignment from the root and run command "mvn clean install"

Reports will be generated in folder: *\assignment\target\surefire-reports