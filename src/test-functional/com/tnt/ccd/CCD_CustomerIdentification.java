package com.tnt.ccd;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
@Listeners(com.tnt.commonutilities.ListnerImplementation.class)
public class CCD_CustomerIdentification extends Driver {
	// Commit on 11-Jan-2021
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CustomerIdentificationAndQuickBookingFlow ciFlow = new CCD_CustomerIdentificationAndQuickBookingFlow();
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

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifyCustomerEnquiryPage() throws Exception {

		ciFlow.CI_verifyCustomerEnquiryPage();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_CustNameandCountry() throws Exception {

		ciFlow.verifySearchResult_CustNameandCountry();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_AccountNoandCountry() throws Exception {

		ciFlow.verifySearchResult_AccountNoandCountry();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_PostalCodeandCountry() throws Exception {

		ciFlow.verifySearchResult_PostalCodeandCountry();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_TelephoneNumberandCountry() throws Exception {

		ciFlow.verifySearchResult_TelephoneNumberandCountry();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_ReferenceNumberandCountry() throws Exception {

		ciFlow.verifySearchResult_ReferenceNumberandCountry();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_with_AccountDetailsPage() throws Exception {

		ciFlow.verifySearchResult_with_AccountDetailsPage();
	}

	@Test(groups = { "customer_identification", "search_result" })
	public void ci_US_409619_verifySearchResult_with_invalidData() throws Exception {

		ciFlow.verifySearchResult_with_invalidData();
	}

	// Customer Identification
	@Test(groups= {"customer_identification","booking_search"})
	public void bk_CI_TC01_BK_CustIdentification_Tab_CheckBookingRefAndDepotField() throws Exception {

		ciFlow.checkBookingRefandDepot();
	}

	@Test(groups= {"customer_identification","quote_search"})
	public void bk_CI_TC02_BK_CustIdentification_Tab_CheckQuoteRefAndDepotField() throws Exception {

		ciFlow.checkQuoteRefandDepot();
	}

	@Test(groups= {"customer_identification","depot_search"})
	public void bk_CI_TC03_CustIdentification_Tab_Depot_Field_Val() throws Exception {

		ciFlow.depotFieldValidation();
	}

	@Test(groups= {"customer_identification","booking_search"})
	public void bk_CI_TC04_CustIdentification_Tab_BookingRef_Field_Val() throws Exception {

		ciFlow.BookingRefFieldValidation();
	}

	@Test(groups= {"customer_identification","dangerous_goods"})
	public void bk_CI_TC05andTC06_CustIdentification_Tab_New_Customer_Booking_DG_PackagingGroupCheck()
			throws Exception {

		ciFlow.validatePackagingGroupField();
	}

	@Test(groups= {"customer_identification","dangerous_goods"})
	public void bk_CI_TC07_TC08_TC09_CustIdentification_Tab_New_Customer_Booking_With_3DG_DryIce_Field_Checked()
			throws Exception {

		// ciFlow.create_Booking_with_DryIceField_Checked_and_3DG_Selected();
	}

	@Test(groups= {"customer_identification","alert_message"})
	public void ci_Validate_Alert_Message_And_Check_Past_Migration_Date_And_Future_Overlap_Date() throws Exception {

		ciFlow.validate_Alert_Message_And_Check_Past_Migration_Date_And_Future_Overlap_Date();
	}

	@Test(groups= {"customer_identification","alert_message"})
	public void ci_Validate_Alert_Message_And_Check_Past_Migration_Date_And_Overlap_Date() throws Exception {

		ciFlow.validate_Alert_Message_And_Check_Past_Migration_Date_And_Overlap_Date();
	}

	@Test(groups= {"customer_identification","alert_message"})
	public void ci_Validate_Account_and_See_No_Alert_Message_And_No_Date() throws Exception {

		ciFlow.validate_Account_and_See_No_Alert_Message_And_No_Date();
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
		CCD_Connectivity.verifyComponentError();
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
			// driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
