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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
@Listeners(com.tnt.commonutilities.ListnerImplementation.class)
public class CCD_QuickBooking extends Driver {

	long elapsedTime = 0;
	long startTime = 0;
	CCD_CustomerIdentificationAndQuickBookingFlow ciQuickBooking = new CCD_CustomerIdentificationAndQuickBookingFlow();
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
	@Parameters({"env"})
	public void login(String env) throws Exception {	
			ciBooking.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	// Quick Booking
	@Test(groups = { "quick_booking", "SISP" })
	public void bk_CI_TC39_TC40_TC42_TC43_QuickBooking_SISP_Flow() throws Exception {

		ciQuickBooking.bK_QuickBooking_Flow();
	}

	@Test(groups = { "quick_booking", "contact" })
	public void bk_CI_TC41_QuickBooking_Collection_Contact_Validation() throws Exception {

		ciQuickBooking.bK_CollectContactDetailsValidation();
	}

	@Test(groups = { "quick_booking", "audit" })
	public void bk_CI_TC44_QuickBooking_Remarks_ExceptionHistory_And_Audit_Tab_Validation() throws Exception {

		ciQuickBooking.quickBookingPagesValidation();
	}

	@Test(groups = { "quick_booking", "service_validation" })
	public void bk_CI_TC45_QuickBooking_ValidServices_Tab_Validation() throws Exception {

		ciQuickBooking.qickBooking_ValidServices_Tab_Validation();
	}

	@Test(groups = { "quick_booking", "special_service" })
	public void bk_CI_TC46_QuickBooking_SpecialServiceValidation() throws Exception {

		ciQuickBooking.quickBooking_SpecialServiceValidation();
	}

	@Test(groups = { "quick_booking", "edit_booking" })
	public void bk_CI_TC47_QuickBooking_Edit() throws Exception {

		ciQuickBooking.editQuickBooking();
	}

	@Test(groups = { "quick_booking", "cancel_booking" })
	public void bk_CI_TC48_TC49_QuickBooking_Cancel() throws Exception {

		ciQuickBooking.cancelQuickBooking();
	}
	
	@Test(groups = { "quick_booking", "get_times_convertQuickBookingToBooking" })
	public void getTimingOnAdditionalInformationPage_while_creatingQuickBooking() throws Exception {
		ciQuickBooking.getTimingConvertQuickBookingToBooking();	
	}
	@Test(groups = { "quick_booking", "postal_masc" })
	public void verifyPostcode_masc_for_quickBooking() throws Exception {
		ciQuickBooking.verifyPostcode_masc_for_quickBooking();	
	}
	@Test(groups = { "booking", "quickbooking_collectionInstruction_charlen",  })
	public void BK_quickBooking_CollDriver_Inst_charsLength() throws Exception {

		ciQuickBooking.BK_quickBooking_CollDriver_Inst_charsLength();

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
		getDriver().quit();
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
