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
/**
 * This Class used to test General Enquiry related Test scenario's
 *
 * @author W125KDI
 *
 */
public class CCD_GeneralEnquiry extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_GeneralEnquiryFlow generalEnquiryFlow = new CCD_GeneralEnquiryFlow();
	CCD_CI_Booking ci_booking = new CCD_CI_Booking();

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass(alwaysRun=true)
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
			ci_booking.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun=true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	// adding general enquiry with different recipient and activity as callback
	@Test(groups = { "general_enquiry","Callback" })
	public void validateAddGeneralEnquiryWithDiffRecipientAndCallbackActivity() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreen();
		generalEnquiryFlow.validateAddGeneralEnquiryWithDiffRecipientAndCallbackActivity();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}

	// adding general enquiry with recipient and activity as complaint
	@Test(groups = { "general_enquiry","Complaint" })
	public void validateAddGeneralEnquiryWithRecipientAndComplaintActivity() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreen();
		generalEnquiryFlow.validateAddGeneralEnquiryWithRecipientAndComplaintActivity();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}
	
	@Test(groups = { "general_enquiry","details_validation" })
	public void validateAddGeneralEnquirywithdetails() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.validateAddGeneralEnquiryWithDiffRecipientAndCallbackActivity();
		generalEnquiryFlow.adddetailsontheGEScreen();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}
	@Test(groups = { "general_enquiry","package_group" })
	public void validateAddGeneralEnquirywithpackageGroup() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.validateAddGeneralEnquiryWithPackagingGroup();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}

	@Test(groups = { "general_enquiry","noBussinessLocation" })
	public void verifyActivityNotAvailableErrorMessage() throws Exception {
		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.verifyActivityNotAvailable();
	}
	
	@Test(groups = { "general_enquiry","globalSearchGeneralEnquiry" })
	public void verifyGlobalSearchOnGeneralEnquiry() throws Exception {
		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.verifyGlobalSearchOnGeneralEnquiry();
	}
	
	@AfterMethod(alwaysRun=true)
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	@AfterMethod(alwaysRun=true)
	public void closeTab() throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.verifyGenralEnquiryScreen();
		CCD_Connectivity.CloseTab();
		
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		getDriver().quit();
	}

	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
