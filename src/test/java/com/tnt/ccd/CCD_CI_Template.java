package com.tnt.ccd;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ddf.EscherColorRef.SysIndexSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.NewRegularTemplatePage;
import com.tnt.commonutilities.CcdConstants;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class CCD_CI_Template extends Driver {
	LoginPage login;
	String globalSearch, AcctName;
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	String Country, quoteNum, bookNum, price, quoteNumber;
	UiTestHelper uiTestHelper = new UiTestHelper();

	// New template flow
	public void TP_New_Template_Create() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			to_fill_caller_info_and_contact_and_payment_info_with_Sender();
			to_validate_add_schedule_functionality();
			template.clickCancelbutton();
			template.clickCreateTemplateButton();
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = template.getTemplateConfirmMsg();
			bookNum = book.replace("Regular Booking Template is created. Template Reference Number is: ", "");
			bookNum = bookNum.replace("\"", "");
			if (getDriver().findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Regular Booking Template is completed successfully and Template reference number is: "
						+ bookNum);
			} else {
				Fail_Message("Template Creation is failed");

			}
			Assert.assertNotNull(bookNum,"Regular template id not created");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: New template booking flow failed");

		}
	}

	public void tp_booking_account_search() {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage customerAccountPage = new CustomerAccountPage(getDriver());
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
			// "KEY", ACM_Booking.Key_Array[6]);
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			homepage.searchBooking(AcctName);
			customerAccountPage.selectCustomerAccounts(AcctName);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");

		}
	}
	
	public void tp_booking_account_search_SIRP() {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage customerAccountPage = new CustomerAccountPage(getDriver());
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM","KEY", CCD_CI.Key_Array[5]);
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			homepage.searchBooking(AcctName);
			customerAccountPage.selectCustomerAccounts(AcctName);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");

		}
	}

	public void to_fill_caller_info_and_contact_and_payment_info_with_Sender() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		BookingPage bookingPage = new BookingPage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		try {
			Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
			template.newTemplate();
			Assert.assertEquals(template.verifyTemplatePage(), true, "Template Page not Displayed");
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				template.customerDescription("Description");
				template.setContactName("Roshni Piyush");
				template.setContactPhone("78945632");
				template.paymentTerms("Sender");
				uiTestHelper.scrolldown("100");
				template.setDriverInstructions("Handle with care");
				template.setContents("Documents");
				template.setConsignmentQuantity("2");
				template.setConsignmentWeight("50");
				uiTestHelper.scrolldown("1000");
				template.setrequestingcontactFullName("Shubham Nagar");
				template.setrequestingcontactTelephoneNumber("78945612");
				template.setauthorisingcontactFullName2("Shital Gawande");
				template.setauthorisingcontactTelephoneNumber2("78945614");
				Pass_Message("User is able to add Requested Sales Contact Data and Authorising Operation Contact Data");
				template.schedulesbutton();
				Assert.assertEquals(template.verifyScheduleInfo(), true, "Schedule Info Page not Displayed");
				template.setReadyTime("01:00");
				template.setCloseTime("02:00");
				template.selectScheduleforSaturday();
				Pass_Message("User is able to add schedule");
				uiTestHelper.scrolldown("500");
				template.addScheduleSavebutton();
				Assert.assertEquals(template.verifyTemplatePage(), true, "Schedule Not Saved");
				Pass_Message("Caller, contact and payment information are filled");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: caller, contact and payment information are not filled");

		}
	}

	public void to_create_template() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			to_fill_caller_info_and_contact_and_payment_info_with_Sender();
			template.clickCreateTemplateButton();
			template.clickCancelBtnAfterTemplateCreated();
			template.checkMessageIsDisplayedAfterCancel();
			if (template.checkMessageIsDisplayedAfterCancel()
					.equalsIgnoreCase("Are you sure you want to cancel this regular booking?")) {
				Pass_Message("After clicking on cancel button the message is displaying");
				System.out.println("Are you sure you want to cancel this regular booking?");
			} else {
				Fail_Message("After clicking on cancel button the message is not displaying");
			}
			template.clickYesButtonForTemplateCancelling();
			Pass_Message("User is able to Cancel the regular booking");
			if (template.checkBookingCancelledStatus().equalsIgnoreCase("Cancelled")) {
				Pass_Message("User is able to see the Cancelled status of the booking");
			}
			Assert.assertEquals(template.checkBookingCancelledStatus(), "Cancelled","Booking Not Cancelled");
			System.out.println("Regular template is Cancelled");
			uiTestHelper.propagateException();
			template.CheckImage();
			Assert.assertEquals(template.CheckImage(),"Image not Displayed for Cancelled Booking");
			Pass_Message("Status is cancelled and red Cross is displayed against the template id");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("After clicking on cancel button the message is not displaying");

		}
	}

	// TC86
	public void to_enter_multiple_items_of_consignment() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			template.newTemplate();
			template.customerDescription("Description");
			template.setContactName("Roshni Piyush");
			template.setContactPhone("78945632");
			template.paymentTerms("Sender");
			template.setDriverInstructions("Handle with care");
			template.setContents("Documents");
			template.setConsignmentWeight("50");
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[1]", "21");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[2]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[2]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[2]", "21");
			hashmap.put("(//input[@name='quantity'])[3]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[3]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[3]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[3]", "21");
			hashmap.put("(//input[@name='quantity'])[4]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[4]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[4]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[4]", "21");
			hashmap.put("(//input[@name='quantity'])[5]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[5]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[5]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[5]", "21");
			for (int i = 0; i <= 6 - i; i++) {
				template.addItem();
			}
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Multiple items have been added successfully");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Multiple items has not been added succesfully");

		}
	}

	public void to_delete_multiple_items_of_consignment() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			to_enter_multiple_items_of_consignment();
			List<WebElement> deleteBtn = template.getDeleteButtons();
			deleteBtn.get(0).click();
			deleteBtn.get(1).click();
			Pass_Message("2 Items have been deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Multiple items has not been deleted succesfully");

		}
	}

	public void to_validate_add_schedule_functionality() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			// to_fill_caller_info_and_contact_and_payment_info();
			template.schedulesbutton();
			template.setReadyTime("01:00");
			template.setCloseTime("02:00");
			Pass_Message("User is able to add schedule");
			template.addScheduleSavebutton();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: add schedule validation failed");

		}
	}

	public void to_validate_unconfirmed_held_functionality() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			to_fill_caller_info_and_contact_and_payment_info_with_Sender();
			if (template.isEnabledUnconfirmedHeldButton()) {
				Pass_Message("Unconfirmed Held Button is enabled/clickable");
			} else {
				Fail_Message("Unconfirmed Held Button is not enabled/clickable");
			}
			uiTestHelper.scrolldown("1000");
			template.clickUnconfirmedheldbutton();
			uiTestHelper.propagateException();
			template.checkStatus();
			if (template.checkStatus().equalsIgnoreCase("Unconfirmed")) {
				Pass_Message("Booking is in Unconfirmed held status");
				System.out.println("Booking is in Unconfirmed held status");
			} else {
				Fail_Message("Booking is not in Unconfirmed held status");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: add schedule validation failed");

		}
	}

	public void to_validate_cancel_button() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			to_fill_caller_info_and_contact_and_payment_info_with_Sender();
			uiTestHelper.scrolldown("1000");
			template.isEnabledCancelButton();
			if (template.isEnabledCancelButton()) {
				Pass_Message("Cancel button is present");
			} else {
				Fail_Message("Cancel button is not present");
			}
			template.clickCancelbutton();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to booking template page failed");

		}
	}

	/**
	 * method to validate "View template" functionality
	 *
	 * @throws Exception
	 */
	public void bK_BookingViewTemplatePage() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			template.viewbutton();
			if (template.verifyViewTemplateIcon()) {
				template.getViewTemplateDetails();
			}
			if (template.getViewTemplateDetails() > 0) {
				Pass_Message("User is able to see the list of Regular Order successfully");
			} else {
				Fail_Message("User is not able to see the list of Regular Order");
			}

			String beforetemplateID = template.getTemplateID();
			template.tempViewbutton();
			String aftertemplateID = template.getParticularTemplateID();
			if (beforetemplateID.equalsIgnoreCase(aftertemplateID)) {
				Pass_Message("User is able to view the selected template : " + beforetemplateID);
			} else {
				Fail_Message("User is not able to view the selected template");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to view template page failed");

		}

	}

	public void bK_BookingView_and_Edit_TemplatePage_Edit_Weight() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		try {
			TP_New_Template_Create();
			CCD_Connectivity.CloseTab();
			tp_booking_account_search();
			System.out.println(bookNum);
			uiTestHelper.scrolldown("500");
			template.viewbutton();
			if (template.verifyViewTemplateIcon()) {
				template.viewTemplate(bookNum);
			}
			template.clickTempEditButton();
			uiTestHelper.scrolldown("1000");
			template.updateWeightForExistingTemplate("20");
			Pass_Message("User is able to edit the weight");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("User is not able to edit the weight");
		}
	}

	public void bK_BookingView_and_Edit_TemplatePage_Add_Additional_Day() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		try {
			TP_New_Template_Create();
			CCD_Connectivity.CloseTab();
			tp_booking_account_search();
			template.viewbutton();
			uiTestHelper.scrollUp("-100");
			if (template.verifyViewTemplateIcon()) {
				template.viewTemplate(bookNum);
			}
			template.clickTempEditButton();
			uiTestHelper.scrolldown("1000");
			template.clickAddSchedulesButtonEditBookingfirst();
			template.scheduleforSaturday();
			Pass_Message("User is able to add additional days choices in existing regular booking");
			uiTestHelper.scrolldown("500");
			template.addScheduleSaveBtnAfterAddingDay();
			Pass_Message("User is able to click on Save button in existing regular booking");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("User is not able to add additional days choices in existing regular booking");
		}
	}

	public void bK_BookingView_and_Edit_TemplatePage_Add_ReadyTime_and_TOTime() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		try {
			TP_New_Template_Create();
			CCD_Connectivity.CloseTab();
			tp_booking_account_search();
			template.viewbutton();
			if (template.verifyViewTemplateIcon()) {
				template.viewTemplate(bookNum);
			}
			template.clickTempEditButton();
			uiTestHelper.scrolldown("500");
			template.clickAddSchedulesButtonEditBookingfirst();
			template.clickAddSchedulesButtonEditBookingsecond();
			template.setReadyTimenew("01:00");
			template.setToTimenew("02:00");
			Pass_Message("User is able to add ready time and to time in existing regular booking");
			template.addScheduleSavebuttonfornewschedule();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("User is not able to add ready time and to time in existing regular booking");
		}
	}

	public void bK_BookingView_and_Edit_TemplatePage_Add_Unavailable_Time() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		try {
			TP_New_Template_Create();
			CCD_Connectivity.CloseTab();
			tp_booking_account_search();
			template.viewbutton();
			if (template.verifyViewTemplateIcon()) {
				template.viewTemplate(bookNum);
			}
			template.clickTempEditButton();
			uiTestHelper.scrolldown("500");
			template.clickAddSchedulesButtonEditBookingfirst();
			template.setUnavailableWindowStartTime("01:00");
			template.setUnavailableWindowEndTime("02:00");
			Pass_Message("User is able to edit unavailable time in existing regular booking");
			// uiTestHelper.scrolldown("500");
			template.addunavailableTimeSaveButton();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("User is not able to edit unavailable time in existing regular booking");
		}
	}

	public void to_enter_collectionText_in_driverInstruction() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search();
			template.newTemplate();
			template.customerDescription("Descriptioion");
			template.setContactName("Roshni Piyush");
			template.setContactPhone("78945632");
			template.paymentTerms("Sender");
			template.setDriverInstructions("Handle with care **$");
			template.setContents("Documents");
			template.setConsignmentWeight("50");
			template.setConsignmentQuantity("2");
			template.setConsignmentWeight("50");
			uiTestHelper.scrolldown("500");
			uiTestHelper.scrolldown("500");
			template.setrequestingcontactFullName("Shubham Nagar");
			template.setrequestingcontactTelephoneNumber("78945612");
			template.setauthorisingcontactFullName2("Shital Gawande");
			template.setauthorisingcontactTelephoneNumber2("78945614");
			template.schedulesbutton();
			template.setReadyTime("01:00");
			template.setCloseTime("02:00");
			template.selectScheduleforSaturday();
			template.addScheduleSavebutton();
			Pass_Message("Driver instrution text with Special Character is Completed");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Driver instrution text with Special Character is not Completed");
		}
	}

	/**
	 * Validate Town field is mandatory for Country with PostCode
	 * 
	 * @param postCode
	 * @throws Exception
	 */
	public void validateTownMandateForCountryAndPostCode(String postCode) {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		template.setPostCode(postCode);
		template.clearTown();
		template.clickValidateAddressBtn();
		if (template.isAddressValidateSuccessfully()) {
			Pass_Message("Address validate sucessfully");
			String town = template.getTown();

			if (null != town && !town.isEmpty())
				Pass_Message("Town is mandatory field as field auto populated as  : " + town);
			else
				Fail_Message("Town is not mandatory field");
		}
	}

	/**
	 * Validate Total Weight mandatory
	 * 
	 * @throws Exception
	 */
	public void validateTotalWeightMandatory(String totalWeightValue) {
		CCD_CI_Template ciTemplate = new CCD_CI_Template();
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		ciTemplate.tp_booking_account_search();
		template.newTemplate();
		uiTestHelper.scrolldown("500");
		template.setTotalWeight(totalWeightValue);
		if (template.isBandEnabled())
			Fail_Message("Band is mandatory field");
		else
			Pass_Message("Band is not mandatory field");
	}

	/**
	 * Validate Band mandatory
	 * 
	 * @throws Exception
	 */
	public void validateBandMandatory(String bandVal) {
		CCD_CI_Template ciTemplate = new CCD_CI_Template();
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		ciTemplate.tp_booking_account_search();
		template.newTemplate();
		/*
		 * uiTestHelper.scrolldown("500"); uiTestHelper.scrolldown("500");
		 */
		System.out.println(
				"Before Band value enter Total weight mandatory =" + template.isTotalWeightMandateIconDisplayed());
		template.setBand(bandVal);
		try {
			System.out.println(
					"after Band value enter Total weight mandatory =" + template.isTotalWeightMandateIconDisplayed());
		} catch (org.openqa.selenium.TimeoutException timeout) {
			System.out.println("Total weight is not mandatory when band selected ---- timeout");
			Pass_Message("Total weight is not mandatory when band selected");
		} catch (Exception exception) {
			System.out.println("Total weight is not mandatory when band selected ---- exception");
			Pass_Message("Total weight is not mandatory when band selected");
		}

		Pass_Message("Band value selected successfully");
	}

	/**
	 * validate Post Code Not empty
	 * 
	 * @throws Exception
	 */
	public void validatePostCodeMandatoryField() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		String errorMsg = "";
		String postCode = template.getPostCode();
		if (postCode != null && !postCode.isEmpty()) {
			template.clearPostCode();
			template.clickCreateTemplateButton();
			errorMsg = template.getErrorMessage();
			if (errorMsg.contains(CcdConstants.ADDRESS_INFORMATION))
				Pass_Message("Postcode in Address Information is mandatory field :" + errorMsg);
			else
				Fail_Message("Postcode in Address Information is not mandatory field");
		}

	}

	/**
	 * validate Customer Description Mandatory Field
	 * 
	 * @throws Exception
	 */
	public void validateCustomerDescriptionMandatoryField() {
		CCD_CI_Template ciTemplate = new CCD_CI_Template();
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		String errorMsg = "";
		ciTemplate.tp_booking_account_search();
		template.newTemplate();
		template.clickCreateTemplateButton();
		errorMsg = template.getErrorMessage();

		if (errorMsg.contains(CcdConstants.CUSTOMER_DESCRIPTION))
			Pass_Message("Customer Description is mandatory field :" + errorMsg);
		else
			Fail_Message("Customer Description is not mandatory field");

	}

	public void new_Template_To_Fill_Data_In_RequestingSales_and_AuthorisingOperation_Contact() {
		tp_booking_account_search();
		to_fill_caller_info_and_contact_and_payment_info_with_Sender();
	}

	public void new_Template_To_Validate_Add_Schedule_Functionality() {
		tp_booking_account_search();
		to_fill_caller_info_and_contact_and_payment_info_with_Sender();
		to_validate_add_schedule_functionality();
	}

	public void new_Tempalte_toValidateCustomerDescriptionPostCodeTownNotEmpty() {
		try {
			// Validate Customer Description mandatory field
			validateCustomerDescriptionMandatoryField();
			// Validate PostCode mandatory field
			validatePostCodeMandatoryField();
			// validate Town mandatory field when Country with PostCode
			// String postCode = Database_Connection.retrieveTestData("POST_CODE", "ACM",
			// "KEY", ACM_CI.Key_Array[5]);//TODO GET post code from Database
			String postCode = "2000-100";// TODO GET post code from Database
			validateTownMandateForCountryAndPostCode(postCode);
		} catch (Exception e) {
			Fail_Message("validation of mandatory fields failed for Customer Description PostCode and Town");
		}
	}

	public void new_Tempalte_toValidateTotalWeight() {
		try {
			// String totalWeightValue =
			// Database_Connection.retrieveTestData("TOTAL_WEIGHT", "ACM", "KEY",
			// ACM_CI.Key_Array[5]);//TODO GET total weight from Database
			String totalWeightValue = "12";// TODO GET total weight from Database
			validateTotalWeightMandatory(totalWeightValue);
		} catch (Exception e) {
			Fail_Message("validation of mandatory fields failed for Weight Feild");
		}
	}

	public void new_Tempalte_toValidateBandValue() {
		try {
			// String bandvalue = Database_Connection.retrieveTestData("BAND", "ACM", "KEY",
			// ACM_CI.Key_Array[5]);//TODO GET Band from Database
			String bandvalue = "30.00 - 100.00";// TODO GET Band from Database
			validateBandMandatory(bandvalue);
		} catch (Exception e) {
			Fail_Message("validation of mandatory fields failed for Weight Feild");
		}
	}

	public void tp_New_Template_SIRP() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		try {
			tp_booking_account_search_SIRP();
			to_fill_caller_info_and_contact_and_payment_info_with_Receiver();
			to_validate_add_schedule_functionality();
			template.clickCancelbutton();
			template.clickCreateTemplateButton();
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			try {
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
				String book = template.getTemplateConfirmMsg();
				bookNum = book.replace("Regular Booking Template is created. Template Reference Number is: ", "");
				bookNum = bookNum.replace("\"", "");
				System.out.println(bookNum);
				if (getDriver().findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
					Pass_Message("Regular Booking Template is completed successfully and Template reference number is: "
							+ bookNum);

				} else {
					Fail_Message("Template Creation is failed");

				}
			} catch (Exception e) {
				// TODO: handle exception
				Fail_Message("Template Creation is failed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: New template booking flow failed");

		}
	}

	public void to_fill_caller_info_and_contact_and_payment_info_with_Receiver() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		CustomerAccountPage custaccpage=new CustomerAccountPage(getDriver());
		BookingPage bookingPage=new BookingPage(getDriver());
		try {
			String accountnumber = "008060306";
			Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
			template.newTemplate();
			Assert.assertEquals(template.verifyTemplatePage(), true, "Template Page not Displayed");
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
			}
			template.customerDescription("Description");
			template.setContactName("Shawnee Unique");
			template.setContactPhone("78945632");
			template.paymentTerms("Receiver");
			template.setAccountNumber(accountnumber);
			template.setCountry("Denmark");
			template.validateSearchButton();
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Address Validated Successfully");
			}
			template.setDriverInstructions("Handle with care");
			template.setContents("Documents");
			template.setConsignmentQuantity("2");
			template.setConsignmentWeight("50");
			uiTestHelper.scrolldown("500");
			uiTestHelper.scrolldown("500");
			template.setrequestingcontactFullName("Shubham Nagar");
			template.setrequestingcontactTelephoneNumber("78945612");
			template.setauthorisingcontactFullName2("Shital Gawande");
			template.setauthorisingcontactTelephoneNumber2("78945614");
			Pass_Message("User is able to add Requested Sales Contact Data and Authorising Operation Contact Data");	
			template.schedulesbutton();	
			Assert.assertEquals(template.verifyScheduleInfo(), true, "Schedule Info Page not Displayed");
			template.setReadyTime("01:00");
			template.setCloseTime("02:00");
			template.selectScheduleforSaturday();
			Pass_Message("User is able to add schedule");
			uiTestHelper.scrolldown("500");
			template.addScheduleSavebutton();
			Assert.assertEquals(template.verifyTemplatePage(), true, "Schedule Not Saved");
			Pass_Message("Caller, contact and payment information are filled");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: caller, contact and payment information are not filled");

		}
	}

	public void view_SIRP_Template() {
		NewRegularTemplatePage template = new NewRegularTemplatePage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage customerAccountPage = new CustomerAccountPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
			tp_New_Template_SIRP();
			connectivity.CloseTab();
			System.out.println(bookNum);
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			homepage.searchBooking(bookNum);
			customerAccountPage.selectCustomerAccounts(bookNum);
			/*
			 * tp_booking_account_search_SIRP(); uiTestHelper.scrolldown("500");
			 * template.viewbutton(); Assert.assertEquals(template.verifyViewTemplateIcon(),
			 * true); if (template.verifyViewTemplateIcon()) { uiTestHelper.propagateException();
			 * template.viewTemplate(bookNum); }
			 */
			Assert.assertEquals(template.verifyTemplateEditButton(), true,"Template not viewed");
			template.clickTempEditButton();
			uiTestHelper.scrolldown("500");
			if (!(template.verifyPaymentTermsField()) && !(template.verifyAccNumberField())
					&& !(template.verifyReceiverCountryField())) {
				Pass_Message(
						"While Editing the Template, Payment Terms, Paying Account Number and Country fields are only in read mode");
			}
		
	}

}
