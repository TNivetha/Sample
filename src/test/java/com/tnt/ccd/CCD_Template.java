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
public class CCD_Template extends Driver {
	// Commit on 11-Jan-2021
	long elapsedTime = 0;
	long startTime = 0;

	CCD_CI_Template bookingTemplate = new CCD_CI_Template();
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();

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
		ciBooking.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun=true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@Test(groups= {"template","template_flow"})
	public void TP_New_Template_Flow() throws Exception {

		bookingTemplate.TP_New_Template_Create();
	}

	// template
	@Test(groups= {"template","cancelbtn_validation"})
	public void b477644_New_Template_To_Validate_Cancel_Button() throws Exception {

		bookingTemplate.to_validate_cancel_button();
	}

	@Test(groups= {"template","contact"})
	public void b477644_New_Template_To_Fill_Data_In_RequestingSales_and_AuthorisingOperation_Contact()
			throws Exception {

		bookingTemplate.new_Template_To_Fill_Data_In_RequestingSales_and_AuthorisingOperation_Contact();
	}

	@Test(groups= {"template","schedule_functionality"})
	public void b477644_New_Template_To_Validate_Add_Schedule_Functionality() throws Exception {

		bookingTemplate.new_Template_To_Validate_Add_Schedule_Functionality();
	}


	@Test(groups= {"template","unconfirmed_functionality"})
	public void b523081_New_Template_To_Validate_Unconfirmed_Held_Functionality() throws Exception {

		bookingTemplate.to_validate_unconfirmed_held_functionality();
	}

	// View template
	@Test(groups= {"template","view_template"})
	public void tc490179_View_Template() throws Exception {

		bookingTemplate.bK_BookingViewTemplatePage();
	}

	@Test(groups= {"template","edit_weight"})
	public void tc371374_View_And_Edit_Template_Update_Weight() throws Exception {

		bookingTemplate.bK_BookingView_and_Edit_TemplatePage_Edit_Weight();
	}

	@Test(groups= {"template","edit_day"})
	public void tc371374_View_And_Edit_Template_Add_Additional_Day() throws Exception {

		bookingTemplate.bK_BookingView_and_Edit_TemplatePage_Add_Additional_Day();
	}

	@Test(groups= {"template","edit_readytime"})
	public void tc371374_View_And_Edit_Template_Add_ReadyTime_And_ToTime_To_Existing_Booking() throws Exception {

		bookingTemplate.bK_BookingView_and_Edit_TemplatePage_Add_ReadyTime_and_TOTime();
	}

	@Test(groups= {"template","edit_unavailabletime"})
	public void tc371374_View_And_Edit_Template_Add_Unavailable_Time_To_Existing_Booking() throws Exception {

		bookingTemplate.bK_BookingView_and_Edit_TemplatePage_Add_Unavailable_Time();
	}

	@Test(groups= {"template","cancel_validation"})
	public void b504343_New_Template_To_Validate_Cancel_Operations() throws Exception {

		bookingTemplate.to_validate_cancel_button();
	}
	
	@Test(groups= {"template","cancel_validation"})
	public void b504343_New_Template_To_Create_Template_and_Cancel_button_operations() throws Exception {

		bookingTemplate.to_create_template();
	}

	@Test(groups= {"template","consignment"})
	public void b477644_TC86_New_Template_to_enter_multiple_items_of_consignment() throws Exception {

		bookingTemplate.to_enter_multiple_items_of_consignment();
	}

	@Test(groups= {"template","consignment_deletion"})
	public void b477644_TC87_New_Template_to_delete_multiple_items_of_consignment() throws Exception {

		bookingTemplate.to_delete_multiple_items_of_consignment();
	}

	@Test(groups= {"template","collectiondetails"})
	public void b477644_TC88_New_Template_to_enter_collection_details_in_driverInstruction() throws Exception {

		bookingTemplate.to_enter_collectionText_in_driverInstruction();
	}

	@Test(groups= {"template","address"})
	public void validateCustomerDescriptionPostCodeTownNotEmpty() throws Exception {

		bookingTemplate.new_Tempalte_toValidateCustomerDescriptionPostCodeTownNotEmpty();
	}

	@Test(groups= {"template","weight_validation"})
	public void validateTotalWeightMandatory() throws Exception {

		bookingTemplate.new_Tempalte_toValidateTotalWeight();

	}

	@Test(groups= {"template","band_validation"})
	public void validateBandMandatory() throws Exception {

		bookingTemplate.new_Tempalte_toValidateBandValue();
	}
	
	@Test(groups= {"template","SIRP"})
	public void tp_New_Template_Flow_SIRP() throws Exception {

		bookingTemplate.tp_New_Template_SIRP();
	}
	@Test(groups= {"template","view_SIRP_template"})
	public void view_SIRP_Template() throws Exception {
		
		bookingTemplate.view_SIRP_Template();
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

	@AfterMethod(alwaysRun = true)
	public void closeTab(ITestResult result,Method method) throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.verifyComponentError();
		CCD_Connectivity.CloseTab();
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		//driver.quit();
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
