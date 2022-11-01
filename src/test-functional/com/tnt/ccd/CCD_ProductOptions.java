package com.tnt.ccd;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.tnt.ccdobjects.LogoutPage;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CCD_ProductOptions extends Driver {

	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();
	

	@BeforeSuite
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys==null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}

	@BeforeMethod
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);

	}

	@Test(priority = 1, enabled = false)
	public void verifyProductOptions_specific_to_country_Switzerland() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_specific_to_country_Switzerland();
	}

	@Test(priority = 2, enabled = false)
	public void verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Switzerland() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Switzerland();
	}
	
	@Test(priority = 3, enabled = false)
	public void verifyProductOptions_while_edit_the_Booking_when_specific_to_country_Switzerland() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_while_edit_the_Booking_when_specific_to_country_Switzerland();
	}
	
	@Test(priority = 4, enabled = true)
	public void verifyProductOptions_specific_to_country_Slovenia() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_specific_to_country_Slovenia();
		ciBooking.verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Slovenia();
		ciBooking.verifyProductOptions_while_edit_the_Booking_when_specific_to_country_slovenia();
		ciBooking.verifyProductOptions_while_create_Quote_when_specific_to_country_Slovenia();
	}

	@Test(priority = 5, enabled = false)
	public void verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Slovenia() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Slovenia();
	}
	
	@Test(priority = 6, enabled = false)
	public void verifyProductOptions_while_edit_the_Booking_when_specific_to_country_Slovenia() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_while_edit_the_Booking_when_specific_to_country_slovenia();
	}
	
	@Test(priority = 7, enabled = false)
	public void verifyProductOptions_while_create_Quote_when_specific_to_country_Slovenia() throws Exception {
		ciBooking.login_SLUser();
		ciBooking.verifyProductOptions_while_create_Quote_when_specific_to_country_Slovenia();
	}


	@AfterMethod
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	@AfterMethod
	public void closeTab() throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		LogoutPage logout=new LogoutPage(driver);
		CCD_Connectivity.CloseTab();
		logout.clickUser();
		
		logout.clickLogout();
	}

	@AfterSuite
	public void afterSuite() {

		Test_Initializer.AfterSuite();
	}
}
