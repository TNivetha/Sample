/**
 * Objective : Test Initializer class is used as reference for TestNG class by having method definitions at one place for all TestNF annotations. .
 * @Author Swati Jadhav
 * Date : 04/09/2019
 */

package com.tnt.commonutilities;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Test_Initializer extends Driver {
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	/* This method is used for configuring log4j properties */
	public static void BeforeSuite(String className) {
		try {
			Extent_Report.createInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method calls to Extent Report Setup method to initialize report. It
	 * retrieves data needed to start TestNG class execution from database. It calls
	 * to Driver Initialization method to initialize browser driver
	 */
	public static String BeforeClass(String class_name) {
		String RefKey = "";
		test.set(extent.createTest(class_name));
		try {

			ClassName = class_name;
			RefKey = Database_Connection.retrieveTestData("Ref_Key", "JAVA_CLASSES", "Selenium_Classes", ClassName);
			Browser_Type = Database_Connection.retrieveTestData("BROWSER_TYPE", "JAVA_CLASSES", "Selenium_Classes",
					ClassName);
			Testing_Type = Database_Connection.retrieveTestData("TESTING_TYPE", "JAVA_CLASSES", "Selenium_Classes",
					ClassName);
			Script_Type = Database_Connection.retrieveTestData("SCRIPT_TYPE", "JAVA_CLASSES", "Selenium_Classes",
					ClassName);
			Initialize_Driver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RefKey;
	}

	/* This method used for getting method name of currently executing method */
	public static void Before_Method(Method method) {
		method_name = method.getName();
		// logger.set(extent.createTest(method_name));
		logger.set(test.get().createNode(method_name));
		System.out.println("Thread.currentThread().getId(): " + Thread.currentThread().getId() + " " + test);

	}

	/*
	 * This method is used for calling method to send data to influx Db according to
	 * the status of the test case execution
	 */
	public void After_Method(ITestResult result, String Env, String ApplicationName, String ProjectName,
			String App_Version, String Business_Process, String ValueStream, String Business, long elapsedTime) {
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				dbInsertion("Java", ApplicationName, "Passed", Env, ProjectName, App_Version, Business_Process, "NA",
						"NA", method_name, 1.0f, elapsedTime, "Java", "High", ClassName, ValueStream, Business);
			} else if (result.getStatus() == ITestResult.FAILURE) {
				dbInsertion("Java", ApplicationName, "Failed", Env, ProjectName, App_Version, Business_Process, "NA",
						"NA", method_name, 0.0f, elapsedTime, "Java", "High", ClassName, ValueStream, Business);
			}
			if (result.getStatus() == ITestResult.SKIP) {
				dbInsertion("Java", ApplicationName, "Skipped", Env, ProjectName, App_Version, Business_Process, "NA",
						"NA", method_name, 0.0f, elapsedTime, "Java", "High", ClassName, ValueStream, Business);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void After_Method(ITestResult result) {
		System.out.println(result.getMethod().getMethodName());
		getLogger().assignCategory(result.getMethod().getMethodName());
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				getLogger().pass(result.getMethod().getMethodName() + "-  Passed");

			} else if (result.getStatus() == ITestResult.FAILURE) {
				getLogger().fail(result.getMethod().getMethodName() + "-  Failed");
				getLogger().info("Screentshot - " + result.getMethod().getMethodName(), MediaEntityBuilder
						.createScreenCaptureFromPath(takeScreenshot(result.getMethod().getMethodName())).build());
				;
			}
			if (result.getStatus() == ITestResult.SKIP) {
				getLogger().skip(result.getMethod().getMethodName() + "-  Skipped");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is used to close all browser tabs Copying the newly created
	 * report to Old Report folder
	 */
	public static void AfterSuite() {
		try {

			// String source = System.getProperty("user.dir") +
			// "/Reports/New_Report/Report_" + timeStamp;
			String source = System.getProperty("user.dir") + "/ExtentReports/";
			File srcDir = new File(source);
			long millis = System.currentTimeMillis();
			String timeStamp1 = new SimpleDateFormat("dd-MM-yyyy  HH.mm.ss").format(new java.util.Date(millis));
			String destination = System.getProperty("user.dir") + "/Reports/Old_Report/Report_" + timeStamp1;
			File destDir = new File(FilenameUtils.getName(destination));
			try {
				FileUtils.copyDirectory(srcDir, destDir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		extent.flush();
	}

	/* This method is used to send data to influx DB */
	public void dbInsertion(String Scripttype, String appName, String Strresult, String Env, String pname,
			String appVer, String bProc, String tSuit, String tCase, String tMethod, float f, float g, String testType,
			String valLevel, String tclass, String ValueStream, String business) {
		org.influxdb.InfluxDB influxDBcon = InfluxDBFactory.connect("http://gblabs121.tntad.fedex.com:8086", "crossops",
				"password");
		BatchPoints batchPoints = BatchPoints.database("CrossOps_Dash").build();
		Point point1 = Point.measurement("TestResults").tag("ApplicationNameTag", appName)
				.addField("ApplicationName", appName).tag("ResultTag", Strresult).tag("ProjectNameTag", pname)
				.addField("ProjectName", pname).tag("EnvironmentTag", Env).addField("ProjectName", pname)
				.addField("ApplicationVersion", appVer).addField("BusinessProcess", bProc)
				.addField("TestSuitePath", tSuit).addField("TestCase", tCase).addField("TestMethod", tMethod)
				.addField("ValidationResult", f).addField("ValidationDuration", g).tag("TestType", testType)
				.tag("ValidationLevel", valLevel).tag("ScriptType", Scripttype).addField("TestClass", tclass)
				.addField("businessArea", business).addField("ValueStream", ValueStream).build();
		batchPoints.point(point1);
		influxDBcon.write(batchPoints);
		// System.out.println(point1);
	}
}
