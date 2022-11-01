package com.tnt.ccd_pickup;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.tnt.ccd.CCD_CI_Booking;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.cmod.CMOD_FunctionalFlow_Updated;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;

//Reactive Case Flow
public class ccd_Pickup_TestExecution extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
	CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!Keys.equals(null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}

	@BeforeClass(alwaysRun = true)
	public void login() throws Exception {
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);		
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				ccd_Pickup_TestExecution.Key_Array[2]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				ccd_Pickup_TestExecution.Key_Array[2]);
		cmodFFReusable.csr_Login(Username, Password);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) throws InterruptedException {
		Test_Initializer.Before_Method(method);
		startTime = 0;
		startTime = System.currentTimeMillis();
	}

	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		elapsedTime = 0;
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		extent.flush();
	}

	@Test(priority = 1, enabled = true)
	public void create_Case_Frontline_CSR() throws Exception {
		//cmodFFReusable.CreateCaseByFrontlineCSR();
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
