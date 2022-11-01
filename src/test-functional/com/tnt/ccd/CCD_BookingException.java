package com.tnt.ccd;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CCD_BookingException extends Driver {
	// Commit on 11-Jan-2021
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_BookingException ciBookingException = new CCD_CI_BookingException();
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();
	CMOD_FF_Reusable bookingExpActivity = new CMOD_FF_Reusable();
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
			ciBooking.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@Test(priority = 1, enabled = false)
	public void acceptBookingException() throws Exception {

		ciBookingException.acceptandOpenBookingExceptionCase();
	}

	@Test(priority = 2, enabled = false)
	public void bookingException_callBackActivity() throws Exception {

		ciBookingException.bookingException_CallBackActivity();
	}

	@Test(priority = 3, enabled = false)
	public void bookingException_Email() throws Exception {

		ciBookingException.bookingException_Email();
		bookingExpActivity.internal_tabclose();
	}

	@Test(priority = 4, enabled = false)
	public void bookingException_caseRemark() throws Exception {

		ciBookingException.bookingException_caseRemark();
		bookingExpActivity.internal_tabclose();
	}

	@Test(priority = 5, enabled = false)
	public void bookingException_rfi() throws Exception {

		ciBookingException.bookingException_RFI();
	}

	@Test(priority = 6, enabled = false)
	public void bookingException_closeCase() throws Exception {

		ciBookingException.bookingException_CloseCase();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 7, enabled = false)
	public void bookingException_verifyclosedCase() throws Exception {

		ciBookingException.bookingException_VerifyClosedCase();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 8, enabled = false)
	public void release_BookingException() throws Exception {

		ciBookingException.release_BookingException();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 9, enabled = false)
	public void verifyholdBooking_with_bookingException_fields() throws Exception {

		ciBookingException.verifyholdBooking_with_bookingException_fields();
		ACM_Connectivity.CloseTab();

	}

	@Test(priority = 10, enabled = false)
	public void verifyholdBooking_with_bookingException_history_tab() throws Exception {

		ciBookingException.verifyholdBooking_with_bookingException_history_tab();
		ACM_Connectivity.CloseTab();

	}

	@Test(priority = 11, enabled = false)
	@Parameters({"env"})
	public void verifyDBCBookingExceptionforDedicatedAccount(String env) throws Exception {

		ciBookingException.veifyDBCBooking_for_dedicatedAccount(env);
		ACM_Connectivity.CloseTab();

	}

	@Test(priority = 12, enabled = false)
	public void verifyHLDBookingExceptionforDedicatedAccount() throws Exception {

		ciBookingException.veifyHLDBooking_for_dedicatedAccount();
		ACM_Connectivity.CloseTab();

	}

	@Test(priority = 13, enabled = false)
	public void clearBookingException() throws Exception {
		ciBookingException.clearBookingException();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 14, enabled = false)
	public void verifyCaseUpdateColumn() throws Exception {
		ciBookingException.verifyCaseUpdateColumn();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 15, enabled = false)
	public void verifyCaseRemark_bookingException() throws Exception {
		ciBookingException.verifyCaseRemark_bookingException();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 16, enabled = false)
	public void verifySupervisorColumns_on_bookingException() throws Exception {
		ciBookingException.verifySupervisorColumns_on_MyTeamOpenBookingException();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 17, enabled = false)
	public void verifySupervisorColumns_on_MyTeamClosedBookingException() throws Exception {
		ciBookingException.verifySupervisorColumns_on_MyTeamClosedBookingException();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 18, enabled = false)
	public void verifyBookingDescriptionField_on_bookingExceptionQueue() throws Exception {
		ciBookingException.verifyBookingDescriptionField_on_bookingExceptionQueue();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 19, enabled = false)
	public void verifyBookingDescriptionField_on_bookingHistoryTab() throws Exception {
		ciBookingException.verifyBookingDescriptionField_on_bookingHistoryTab();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 20, enabled = false)
	public void verifyBookingDescriptionField_on_Cases() throws Exception {
		ciBookingException.verifyBookingDescriptionField_on_Cases();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 21, enabled = false)
	public void verifyReleaseBooking_while_edit_holdBooking() throws Exception {
		ciBookingException.verifyReleaseBooking_while_edit_holdBooking();
		ACM_Connectivity.CloseTab();
	}
	@Test(priority = 22, enabled = false)
	public void verifyReleaseBooking_while_edit_ackwardFreightBooking() throws Exception {
		ciBookingException.verifyReleaseBooking_while_edit_ackwardFreightBooking();
		ACM_Connectivity.CloseTab();
	}
	@Test(priority = 23, enabled = false)
	public void verifyReleaseBooking_while_edit_dbcBooking() throws Exception {
		ciBookingException.verifyReleaseBooking_while_edit_dbcFreightBooking();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 24, enabled = false)
	public void verifyRelatedTab_when_anyCaseOpens() throws Exception {
		ciBookingException.verifyRelatedTab_when_anyCaseOpens();
		ACM_Connectivity.CloseTab();
	}
	@Test(priority = 25, enabled = false)
	public void verifyOIBRelease_hidden_from_OIBCaseView() throws Exception {
		ciBookingException.verifyOIBRelease_hidden_from_OIBCaseView();
		ACM_Connectivity.CloseTab();
	}

	@Test(priority = 24, enabled = false)
	public void verifyBookingException_Hyperlinks() throws Exception{
		ciBooking.verifyAwkwardFreightEnvelope();
		ciBookingException.verifyBookingException_Hyperlinks();
		ACM_Connectivity.CloseTab();
	}
	
	@Test(priority = 25, enabled = false)
	public void verifyColumnsOnOpenBookingExceptionCasePage() throws Exception{
		ciBookingException.verifyColumnsOnOpenBookingExceptionCasePage();
		ACM_Connectivity.CloseTab();
	}
	
	@Test(priority = 26, enabled = false)
	public void verifyColumnsOnClosedBookingExceptionCasePage() throws Exception{
		ciBookingException.verifyColumnsOnClosedBookingExceptionCasePage();
		ACM_Connectivity.CloseTab();
	}
	
	@Test(priority = 27, enabled = false)
    public void verifyReopenFunctionality_on_bookingException() throws Exception {
        ciBookingException.verifyReopenFunctionality_on_bookingException();
    }
	
	@Test(priority = 28, enabled = true)
    public void verifyBookingException_with_CustomerAccountNumber() throws Exception {
        ciBookingException.verifyBookingException_with_CustomerAccountNumber();
    }
	@Test(priority = 29, enabled = true)
    public void verifyBookingException_ListView_CustomerAccountNumber_and_Name() throws Exception {
        ciBookingException.verifyBookingException_ListView_CustomerAccountNumber_and_Name();
    }
	@Test(groups = { "bookingException", "DPTCaseException" })
    public void verifyDPTBookingExceptionCase() throws Exception {
        ciBookingException.verifyDPTBookingExceptionCase();
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

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		// driver.quit();
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
