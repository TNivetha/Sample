package com.tnt.commonutilities;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ListnerImplementation implements ITestListener {
	

	// When Test case get failed, this method is called.
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("The name of the testcase failed is :" + result.getName());
		
	}

	// When Test case get Skipped, this method is called.
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("The name of the testcase Skipped is :" + result.getName());
	}

	// When Test case get Started, this method is called.
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(result.getName() + " test case started");

	}

	// When Test case get passed, this method is called.
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("The name of the testcase passed is :" + result.getName());

	}
	@Override
	public void onFinish(ITestContext Result) {

	}

	@Override
	public void onStart(ITestContext Result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {

	}

	public static String takeScreenshot(WebDriver driver, String methodName) {
		String screenshotName = getScreenshotPath(methodName);
		String directory = System.getProperty("user.dir") + "/ExtentReports/";
		new File(directory).mkdirs();
		String path =directory+screenshotName;
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
