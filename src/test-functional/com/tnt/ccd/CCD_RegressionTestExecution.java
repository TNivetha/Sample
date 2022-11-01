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
public class CCD_RegressionTestExecution extends Driver{
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_Booking bQflow = new CCD_CI_Booking();
	CCD_CI_Template bookingTemplate = new CCD_CI_Template();
	CCD_CustomerIdentificationAndQuickBookingFlow ciQuickBooking = new CCD_CustomerIdentificationAndQuickBookingFlow();
	CCD_GeneralEnquiryFlow generalEnquiryFlow = new CCD_GeneralEnquiryFlow();
	CCD_CI_BookingException ciBookingException = new CCD_CI_BookingException();
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	
	
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
		bQflow.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);

	}

	@Test(groups = { "bookingflow", "SISP" })
	public void SISP_Booking() throws Exception {

		bQflow.BK_SISP_Flow();

	}

	@Test(groups = { "bookingflow", "regression" })
	public void SIRP_Booking() throws Exception {

		bQflow.BK_SIRP_Flow();

	}

	@Test(groups = { "booking", "regression" })
	public void RIRP_Booking() throws Exception {

		bQflow.BK_RIRP_Flow();

	}
	@Test(groups = { "booking", "dgflow" })
	public void dangerous_Goods_Booking() throws Exception {

		bQflow.BK_SISP_DG_Flow();

	}
	@Test(groups = { "booking", "regression" })
	public void newCustomerBooking() throws Exception {

		bQflow.BK_NewCustomerBooking();

	}

	@Test(groups = { "booking", "regression" })
	public void booking_AuditPage_Validation() throws Exception {

		bQflow.BK_BookingAuditPageValidation_EditBooking();

	}
	@Test(groups = { "booking", "regression" })
	public void consignmentNote_ConsignmentNumberCreation() throws Exception {

		bQflow.BK_verifyConSignmentNoCreation_SISP();

	}
	@Test(groups = { "booking", "regression" })
	public void verifyAllProductOptions_booking() throws Exception {

		bQflow.BK_VerifyAllProductOptions();

	}
	@Test(groups = { "booking", "regression" })
	public void createSpecialServiceBooking() throws Exception {

		bQflow.BK_CreateSpecialServiceBooking_SISPFlow();

	}
	@Test(groups = { "booking", "regression" })
	public void holdBooking() throws Exception {

		bQflow.BK_verifyBookingwithHoldStatus();

	}
	@Test(groups = { "booking", "regression" })
	public void bookingClone() throws Exception {

		bQflow.BK_verifyBookingClone();

	}
	@Test(priority = 1, groups = { "quoteflow", "regression" })
	public void quoteFlow_Sender() throws Exception {

		bQflow.Q_QuoteFlow();
		bQflow.Q_QuoteDetails_Validation();
		bQflow.Q_ConvertToBookingSender();
		bQflow.Q_QuoteToBooking_DetailsValidation();
	}

	@Test(priority = 2, groups = { "quote", "regression" })
	public void quoteFlow_Receiver() throws Exception {

		bQflow.Q_QuoteFlow_Receiver();
		bQflow.Q_QuoteDetails_Validation();
		bQflow.Q_ConvertToBookingReceiver();
		bQflow.Q_QuoteToBooking_DetailsValidation();
	}
	@Test(groups= {"template","regression"})
	public void regularTemplate_Flow_SISP() throws Exception {

		bookingTemplate.TP_New_Template_Create();
	}
	@Test(groups= {"template","regression"})
	public void viewRegularTemplate_SISP() throws Exception {

		bookingTemplate.bK_BookingViewTemplatePage();
	}
	@Test(groups= {"templateS","Vregression"})
	public void regularTemplate_SIRP() throws Exception {

		bookingTemplate.tp_New_Template_SIRP();
	}
	@Test(groups= {"templateS","Vregression"})
	public void viewRegularTemplate_SIRP() throws Exception {
		
		bookingTemplate.view_SIRP_Template();
	}
	@Test(groups = { "quick_booking", "qbk_SISP" })
	public void quickBooking_SISP() throws Exception {

		ciQuickBooking.bK_QuickBooking_Flow();
	}
	@Test(groups = { "quick_booking", "edit_booking" })
	public void quickBooking_Edit() throws Exception {

		ciQuickBooking.editQuickBooking();
	}
	@Test(groups = { "general_enquiry","Callback" })
	public void generalEnquiry_Callback_Activity() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreen();
		generalEnquiryFlow.validateAddGeneralEnquiryWithDiffRecipientAndCallbackActivity();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}

	// adding general enquiry with recipient and activity as complaint
	@Test(groups = { "general_enquiry","Complaint" })
	public void generalEnquiry_complaint_Activity() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreen();
		generalEnquiryFlow.validateAddGeneralEnquiryWithRecipientAndComplaintActivity();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}
	@Test(groups = { "general_enquiry","package_group" })
	public void generalEnquiry_package_group() throws Exception {

		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.validateAddGeneralEnquiryWithPackagingGroup();
		generalEnquiryFlow.validateAddGeneralEnquiryWithCallbackDateTime();
	}
	
	@Test(groups = { "general_enquiry","globalSearchGeneralEnquiry" })
	public void verifyGlobalSearchOnGeneralEnquiry() throws Exception {
		generalEnquiryFlow.navigateToGeneralEnquiryScreenthroughCustomerIdentification();
		generalEnquiryFlow.verifyGlobalSearchOnGeneralEnquiry();
	}
	@Test(groups = { "booking_Exception","DBC" })
	@Parameters({"env"})
	public void DBCBookingException_for_DedicatedAccount(String env) throws Exception {
		ciBookingException.veifyDBCBooking_for_dedicatedAccount(env);
	}

	@Test(groups = { "booking_Exception","HOLD" })
	public void holdBookingException_for_DedicatedAccount() throws Exception {
		ciBookingException.veifyHLDBooking_for_dedicatedAccount();
	}

	/*
	 * @Test(groups = { "booking_ExceptionR","REOPEN" }) public void
	 * verifyReopenFunctionality_on_bookingException() throws Exception {
	 * ciBookingException.verifyReopenFunctionality_on_bookingException(); }
	 */

	@AfterMethod(alwaysRun = true)
	public void closeTab(ITestResult result,Method method) throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.verifyComponentError();
		CCD_Connectivity.verifyBookingUpdateorCreationError();
		CCD_Connectivity.verifyGenralEnquiryScreen();;
		CCD_Connectivity.CloseTab();
	}
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		Test_Initializer.After_Method(result);
		extent.flush();
	}	

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

		Test_Initializer.AfterSuite();
	}



}
