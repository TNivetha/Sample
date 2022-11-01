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

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CCD_CI extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();

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
		if ((!Keys.isEmpty()) || (!(Keys==null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}

	@BeforeClass(alwaysRun = true)
	public void login() throws Exception {

		ciBooking.ci_login();
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@Test(groups = { "customer_identification", "global_search_firstname" })
	public void tc1_CI_GS_FirstName() throws Exception {

		ciBooking.ci_GS_FirstName();

	}

	@Test(groups = { "customer_identification", "global_search_lastname" })
	public void tc2_CI_GS_LastName() throws Exception {

		ciBooking.ci_GS_LastName();

	}

	@Test(groups = { "customer_identification", "global_search_account" })
	public void tc3_CI_GS_CustAcct() throws Exception {

		ciBooking.CI_GS_CustAcct();

	}

	@Test(groups = { "customer_identification", "global_search_header" })
	public void tc4_CI_GS_Header() throws Exception {

		ciBooking.CI_GS_Header();

	}

	@Test(groups = { "customer_identification", "global_search_buttons" })
	public void tc5_CI_GS_VerifyBtns() throws Exception {

		ciBooking.CI_GS_VerifyBtns();

	}

	@Test(groups = { "customer_identification", "global_search_contact" })
	public void tc6_CI_GS_VerifyContactDtls() throws Exception {

		ciBooking.ci_GS_VerifyContactDtls();

	}

	@Test(groups = { "customer_identification", "global_search_accountNumber" })
	public void tc7_CI_GS_AcctNum() throws Exception {

		ciBooking.ci_GS_AcctNum();

	}

	@Test(groups = { "customer_identification", "global_search_detailsAcctNum" })
	public void tc8_CI_GS_AcctNum_Details() throws Exception {

		ciBooking.CI_GS_AcctNum_Details();

	}

	@Test(groups = { "customer_identification", "global_search_verifyStream" })
	public void tc9_CI_GS_VerifyStream() throws Exception {

		ciBooking.ci_GS_VerifyStream();

	}

	@Test(groups = { "customer_identification", "left_search_accountName" })
	public void tc10_CI_LeftSearch_AcctName() throws Exception {

		ciBooking.ci_LeftSearch_AcctName();

	}

	@Test(groups = { "customer_identification", "left_search_salesCountry" })
	public void tc11_CI_LeftSearch_SalesCountry() throws Exception {

		ciBooking.ci_LeftSearch_SalesCountry();

	}

	@Test(groups = { "customer_identification", "left_search_accountNumber" })
	public void tc12_CI_LeftSearch_AcctNum() throws Exception {

		ciBooking.ci_LeftSearch_AcctNum();

	}

	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	@AfterMethod(alwaysRun = true)
	public void closeTab() throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.CloseTab();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
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
