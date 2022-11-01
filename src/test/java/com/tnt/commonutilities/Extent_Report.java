/**
 * Objective : Extent Report class is used to setup Extent Report.
 * @Author Swati Jadhav
 * Date : 04/09/2019
 */

package com.tnt.commonutilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.View;

import org.apache.commons.io.FileUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class Extent_Report {
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> logger = new ThreadLocal<>();

	public String timeStamp;

	public static ExtentReports createInstance() {
		String reportPath = getReportPath();
		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath).viewConfigurer().viewOrder()
				.as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST }).apply();
		extent = new ExtentReports();
		spark.config().setReportName("CCD Smoke & Regression Report");
		spark.config().setTheme(Theme.STANDARD);
		extent.attachReporter(spark);
		return extent;
	}

	public static String getReportPath() {
		String directory = System.getProperty("user.dir") + "/ExtentReports/";
		new File(directory).mkdirs();
		// Date d = new Date();
		String fileName = "CCD-AutomationReport" + ".html";
		String reportPath = directory + fileName;
		return reportPath;
	}

	public static ExtentTest getLogger() {
		return logger.get();
	}

}
