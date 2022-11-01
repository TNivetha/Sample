/**
 * Objective : Driver class is used to initialize browser driver.
 * @Author Swati Jadhav
 * Date : 04/09/2019
 */

package com.tnt.commonutilities;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver extends Extent_Report {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String ClassName;
	public static String Browser_Type;
	public static String Testing_Type;
	public static String Script_Type;
	public static String Key_Array[];
	public static SoftAssert softAssertion = new SoftAssert();
	public static String method_name;

	/*
	 * This method is used to initialize the browser based on Browser_Type retrieved
	 * from database
	 */

	public static void Initialize_Driver() {
		System.out.println("Initializing Driver ---");
		if (Browser_Type.equalsIgnoreCase("chrome")) {
			boolean localMachine = false; // TEMP

			System.setProperty("webdriver.chrome.whitelistedIps", ""); // local connections only

			ChromeOptions ops = new ChromeOptions();
			/*
			 * ops.addArguments("disable-features=NetworkService");
			 * ops.addArguments("--disable-notifications",
			 * "--disable-dev-shm-usage","--disable-gpu", "--no-sandbox");
			 * ops.addArguments("--headless"); ops.setHeadless(true);
			 * ops.addArguments("--verbose"); WebDriverManager.chromedriver().setup();
			 */

			WebDriverManager.chromedriver().proxy("10.205.176.12:8080").proxyUser("3905691").proxyPass("Jssn@0504").setup();
			String driverPath = System.getProperty("CHROME_DRIVER");
			System.out.println("Driver: Got path '" + driverPath + "' ");

			if (driverPath != null && driverPath.length() > 0) {
				// If chromedriver was set in environment variable use that
				System.setProperty("webdriver.chrome.driver", driverPath);
				localMachine = true;
			}
			driver = new ChromeDriver(ops);

			if (localMachine) {
				// This allows execution size for all users to be on same resolution no matter
				// what machine
				java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				// double height = screenSize.getHeight();

				if (width > 1280) {
					driver.manage().window().setSize(new Dimension(1280, 1024));
				} else {
					driver.manage().window().maximize();
				}
			} else {
				driver.manage().window().maximize();
			}

		} else if (Browser_Type.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
		} else if (Browser_Type.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		wait = new WebDriverWait(driver, 30);
	}

	/*
	 * This method is used to add logger for Passed test cases in extent report with
	 * screenshot
	 */
	public static void Pass_Message(String Message){
		logger.pass(Message);
		// logger.log(Status.INFO, "",
		// MediaEntityBuilder.createScreenCaptureFromPath(addscreenshot()).build());
	}

	public static void Pass_Message_with_screenCapture(String Message)  {
		logger.pass(Message);
		logger.log(Status.INFO, "",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(method_name)).build());
	}

	public static void report_MapValues(String Message, HashMap map)  {
		logger.pass(Message);
		logger.pass(MarkupHelper.createOrderedList(map).getMarkup());
		// logger.log(Status.INFO, "",
		// MediaEntityBuilder.createScreenCaptureFromPath(addscreenshot()).build());
	}

	public static void attachScreenShot(String messageInfo, String screenShotID) {
		logger.log(Status.INFO, messageInfo,
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(screenShotID)).build());
	}

	/*
	 * This method is used to add logger for Passed test cases in extent report with
	 * screenshot
	 */
	public static void Pass_Message_withoutScreenCapture(String Message) {
		logger.pass(Message);

	}

	/*
	 * This method is used to add logger for Failed test cases in extent report with
	 * screenshot
	 */
	public static void Fail_Message(String Message) {
		logger.fail(Message);
	}

	/* This method is used to clear browser cookies */
	public static void deleteBrowserCookies() throws InterruptedException {
		Actions keyAction = new Actions(Driver.driver);
		Driver.driver.get("chrome://settings/clearBrowserData");
		Action modifierkey = keyAction.sendKeys(Keys.ENTER).build();
		modifierkey.perform();
	}

	public static String takeScreenshot(String methodName) {
		String screenshotName = getScreenshotPath(methodName);
		String directory = System.getProperty("user.dir") + "/ExtentReports/";
		new File(directory).mkdirs();
		String path = directory + screenshotName;
		try {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(FilenameUtils.getName(path)));
			System.out.println("Successfully captured a screenshot");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public static String getScreenshotPath(String methodname) {
		Date d = new Date();
		String fileName = methodname + d.toString().replace(":", "_").replace(" ", "_") + ".png";
		return fileName;
	}
}
