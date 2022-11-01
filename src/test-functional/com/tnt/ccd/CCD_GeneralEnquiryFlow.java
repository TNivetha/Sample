package com.tnt.ccd;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.tnt.ccdobjects.AdditionalInfoPage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.CustomerIdentificationPage;
import com.tnt.ccdobjects.GeneralEnquiryPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

/**
 * This File having the business validation related to General Enquiry
 *
 * @author W125KDI
 *
 */
public class CCD_GeneralEnquiryFlow extends Driver {
	UiTestHelper uiTestHelper = new UiTestHelper();
	/**
	 * Method to validate add General Enquiry With Different Recipient And Callback
	 * Activity
	 *
	 * @throws Exception
	 */
	public void validateAddGeneralEnquiryWithDiffRecipientAndCallbackActivity() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		SoftAssert softAssert=new SoftAssert();
		Assert.assertEquals(generalEnquiryPage.verifyGeneralEnquiryTitle(), true);
		try {
			generalEnquiryPage.setRecipient(getRecipient("CPL"));
			softAssert.assertNotNull(generalEnquiryPage.getRecipient());
			if (!generalEnquiryPage.getRecipient().isEmpty())
				Pass_Message("Selected Recipient as : " + generalEnquiryPage.getRecipient());
			else
				Fail_Message("Failed to select Recipient in General Enquiry");

			generalEnquiryPage.setActivity(getActivityFromDB("Callback (either con or non con)"));
			softAssert.assertNotNull(generalEnquiryPage.getActivity());
			if (!generalEnquiryPage.getActivity().isEmpty())
				Pass_Message("selected Activity as : " + generalEnquiryPage.getActivity());
			else
				Fail_Message("Failed to select Activity in General Enquiry");

		} catch (Exception e) {
			Fail_Message("General Enquiry flow validation failed with Callback activity");
		}
	}

	/**
	 * Method to navigate To General Enquiry Screen
	 *
	 * @throws Exception
	 */
	public void navigateToGeneralEnquiryScreen() {
	
		CustomerAccountPage customerAccountPage = new CustomerAccountPage(driver);
		CCD_CI_Booking ciBooking = new CCD_CI_Booking();
		String acctName = ciBooking.getNonDGApprovedAccountFromDB();
		ciBooking.bookingSelectionOnHomepage(acctName);
		customerAccountPage.selectCustomerAccounts(acctName);
		Assert.assertEquals(customerAccountPage.verifyCustomerAccountPage(), true);
		customerAccountPage.clickContactRadiobtn();
		customerAccountPage.clickGeneralEnquiryButton();
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 */
	public String getRecipient(String recipientvalue) {
		// String recipientValue = Database_Connection.retrieveTestData("RECIPIENT",
		// "ACM", "KEY", ACM_CI.Key_Array[5]);//TODO GET recipient from automation
		// Database
		String recipientValue = recipientvalue;// TODO delete this once GET recipient from automation Database
		return recipientValue;
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 */
	public String getActivityFromDB(String activity) {
		// String activityValue = Database_Connection.retrieveTestData("ACTIVITY",
		// "ACM", "KEY", ACM_CI.Key_Array[5]);//TODO GET Activity from automation
		// Database
		String activityValue = activity;// TODO delete this once GET Activity from automation
										// Database
		return activityValue;
	}

	/**
	 * Method to validate add General Enquiry With Callback Date and Time
	 *
	 * @throws Exception
	 */
	public void validateAddGeneralEnquiryWithCallbackDateTime() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);

		generalEnquiryPage.enterCallbackDate(getCallbackDateFromDB());
		generalEnquiryPage.enterCallbackBeforeTime(getCallbackBeforeTimeFromDB());
		generalEnquiryPage.enterAdditionalInformation(getAdditionalInformationFromDB());
		generalEnquiryPage.setBusinessLocation(getBusinessLocationFromDB("LISBON"));
		Pass_Message("Entered Callback date and callback before time successfully");
		generalEnquiryPage.clickConfirmButton();
		Assert.assertEquals(generalEnquiryPage.isGeneralEnqsuiryCreated(), true);
		if (generalEnquiryPage.isGeneralEnqsuiryCreated()) {
			Pass_Message("General Enquiry is created successfully");
		} else {
			Fail_Message("General Enquiry is not created successfully");
		}
		String successMsg = generalEnquiryPage.getSuccessMessage();
		Assert.assertNotNull(generalEnquiryPage.getSuccessMessage());
		if (!successMsg.isEmpty()) {
			successMsg = successMsg.replace("General Enquiry record ", "");
			successMsg = successMsg.replace(" has been created", "");
			System.out.println("General Enquiry = " + successMsg);
			Pass_Message("General Enquiry is created successfully : " + successMsg);
		} else {
			Fail_Message("General Enquiry is not created successfully");
		}
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 * @throws ParseException
	 */
	public String getCallbackDateFromDB() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		String callbackDtVal = addinfopage.getSystemDateinFormat("dd-MMM-yyyy");
		;// TODO delete this once GET CallBackDate from automation Database
		return callbackDtVal;
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 * @throws ParseException
	 */
	public String getCallbackBeforeTimeFromDB() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		String callbackBeforeTimeVal = addinfopage.getSystemTime();// TODO delete this once GET CallBack time from
																	// automation Database
		return callbackBeforeTimeVal;
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 */
	public String getAdditionalInformationFromDB() {
		// TODO GET AdditionalInformation from automation Database
		// String additionalInformationVal =
		// Database_Connection.retrieveTestData("AdditionalInformation", "ACM", "KEY",
		// ACM_CI.Key_Array[5]);
		String additionalInformationVal = "General Enquiry added for testing purpose";// TODO delete this once GET
																						// AdditionalInformation from
																						// automation Database
		return additionalInformationVal;
	}

	/**
	 * Get Test Data from DB
	 *
	 * @return
	 */
	public String getBusinessLocationFromDB(String location) {
		// TODO GET businessLocation from automation Database
		// String activityValue =
		// Database_Connection.retrieveTestData("BusinessLocation", "ACM", "KEY",
		// ACM_CI.Key_Array[5]);
		String businessLocationVal = location;// TODO delete this once GET BusinessLocation from automation Database
		return businessLocationVal;
	}

	/**
	 * Validate General Enquiry With Recipient And Complaint Activity
	 *
	 * @throws Exception
	 */
	public void validateAddGeneralEnquiryWithRecipientAndComplaintActivity() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		SoftAssert softAssert=new SoftAssert();
		Assert.assertEquals(generalEnquiryPage.verifyGeneralEnquiryTitle(), true);
		try {
			generalEnquiryPage.setRecipient(getRecipient("CPL"));
			softAssert.assertNotNull(generalEnquiryPage.getRecipient());
			if (!generalEnquiryPage.getRecipient().isEmpty())
				Pass_Message("Selected Recipient as : " + generalEnquiryPage.getRecipient());
			else
				Fail_Message("Failed to select Recipient in General Enquiry");

			generalEnquiryPage.setActivity(getActivityFromDB("Complaint"));
			softAssert.assertNotNull(generalEnquiryPage.getActivity());
			if (!generalEnquiryPage.getActivity().isEmpty())
				Pass_Message("selected Activity as : " + generalEnquiryPage.getActivity());
			else
				Fail_Message("Failed to select Activity in General Enquiry");

		} catch (Exception e) {
			Fail_Message("General Enquiry flow validation failed with Complaint activity");
		}

	}

	/**
	 * Method to navigate To General Enquiry Screen
	 *
	 * @throws Exception
	 */
	public void navigateToGeneralEnquiryScreenthroughCustomerIdentification() {
		HomePage homePage = new HomePage(driver);
		CustomerIdentificationPage customerIdentificationPage = new CustomerIdentificationPage(driver);
		homePage.clickDropDownNavigationMenu();
		homePage.clickCustomerIdentification();
		Assert.assertEquals(customerIdentificationPage.verifyCustomerEnquiryPage(),true);
		customerIdentificationPage.clickGeneralEnquiry();
	}

	public void adddetailsontheGEScreen() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		generalEnquiryPage.setContactName("Nivetha");
		generalEnquiryPage.setTelephone("8940732594");
		generalEnquiryPage.setPostal("5771");
		generalEnquiryPage.setCountry("Denmark");
	}

	/**
	 * Method to validate add General Enquiry With Different Recipient And Callback
	 * Activity
	 *
	 * @throws Exception
	 */
	public void validateAddGeneralEnquiryWithPackagingGroup() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		SoftAssert softAssert=new SoftAssert();
		Assert.assertEquals(generalEnquiryPage.verifyGeneralEnquiryTitle(), true);
		try {
			generalEnquiryPage.setRecipient(getRecipient("Supplies"));
			softAssert.assertNotNull(generalEnquiryPage.getRecipient());
			if (!generalEnquiryPage.getRecipient().isEmpty())
				Pass_Message("Selected Recipient as : " + generalEnquiryPage.getRecipient());
			else
				Fail_Message("Failed to select Recipient in General Enquiry");

			generalEnquiryPage.setActivity(getActivityFromDB("Supplies"));
			softAssert.assertNotNull(generalEnquiryPage.getActivity());
			if (!generalEnquiryPage.getActivity().isEmpty())
				Pass_Message("selected Activity as : " + generalEnquiryPage.getActivity());
			else
				Fail_Message("Failed to select Activity in General Enquiry");
			Assert.assertEquals(generalEnquiryPage.verifyPackaging(), true);
			if(generalEnquiryPage.verifyPackaging()==true) {
				Pass_Message("Packaging is displayed when Recipient and Activity as - Supplies");
				generalEnquiryPage.setPackaging("BOTTLE 1");
			}	
			adddetailsontheGEScreen();
			softAssert.assertAll();
		} catch (Exception e) {
			Fail_Message("General Enquiry flow validation failed with packaging");
		}
	}
	
	public void verifyActivityNotAvailable() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		Assert.assertEquals(generalEnquiryPage.verifyGeneralEnquiryTitle(), true);
		try {
			generalEnquiryPage.setRecipient("Admin");
			if (!generalEnquiryPage.getRecipient().isEmpty())
				Pass_Message("Selected Recipient as : " + generalEnquiryPage.getRecipient());
			else
				Fail_Message("Failed to select Recipient in General Enquiry");
			
			generalEnquiryPage.setActivity("Admin Follow Up");
			if (!generalEnquiryPage.getActivity().isEmpty())
				Pass_Message("Selected Activity as : " + generalEnquiryPage.getActivity());
			else
				Fail_Message("Failed to select Activity in General Enquiry");
			Assert.assertEquals(generalEnquiryPage.verifyActivityNotAvailable(), true);
			if(generalEnquiryPage.verifyActivityNotAvailable()==true) {
				Pass_Message("Activity not Available message is displayed");
				adddetailsontheGEScreen();	
				verifyConsignmentNumberField();
				verify500CharLimitOnAddInfo();
			}		
	} catch (Exception e) {
		Fail_Message("Activity not Available message validation on General Enquiry page is failed");
	}
}	
	
	public void verify500CharLimitOnAddInfo() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		
		String addInformation = "It says I need to type at least ten characters, so here's this. Y'know what? I'm gonna type one hundred characters instead. Actually, I'm going to type five hundred characters. I'm definitely not going to type anywhere near one thousand characters, because that'd be ridiculous. Even if I wanted to type one thousand characters, I have to go to bed now anyway, so I simply don't have the time. I mean, I could just type a bunch of random letters or hold down one key, but that would be no fun at all.";
		generalEnquiryPage.enterAdditionalInformation(addInformation);
		
		Assert.assertEquals(addInformation.substring(0, 500), generalEnquiryPage.getAddInfoText()) ;
		Pass_Message("Additional Information Character Length limit is up to 500 Character");
	}
	
	public void verifyConsignmentNumberField() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		Assert.assertTrue(generalEnquiryPage.verifyConsignmentNumberField()); {
			Pass_Message("Consignment Number Field is displayed");
		}
	}
	
	public void verifyGlobalSearchOnGeneralEnquiry() {
		GeneralEnquiryPage generalEnquiryPage = new GeneralEnquiryPage(driver);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		HomePage homePage = new HomePage(driver);
		
		generalEnquiryPage.setRecipient("CPL");
		generalEnquiryPage.setActivity("Complaint");
		//("Callback (either con or non con)");
		generalEnquiryPage.setContactName("Nivetha");
		generalEnquiryPage.setTelephone("8940732594");
		generalEnquiryPage.setPostal("5771");
		generalEnquiryPage.setCountry("Denmark");
	    generalEnquiryPage.enterCallbackDate(getCallbackDateFromDB());
	    generalEnquiryPage.enterCallbackBeforeTime(getCallbackBeforeTimeFromDB());
		generalEnquiryPage.setBusinessLocation("LISBON");
		generalEnquiryPage.enterAdditionalInformation("Additional Information for this Test");
		generalEnquiryPage.clickConfirmButton();
		
		Assert.assertTrue(generalEnquiryPage.isGeneralEnqsuiryCreated()); {
			Pass_Message("General Enquiry is created successfully");
		
		String successMsg = generalEnquiryPage.getSuccessMessage();
		if (!successMsg.isEmpty()) {
			successMsg = successMsg.replace("General Enquiry record ", "");
			successMsg = successMsg.replace(" has been created", "");
			System.out.println("General Enquiry = " + successMsg);
			Pass_Message("General Enquiry is created successfully : " + successMsg);
		} else {
			Fail_Message("General Enquiry is not created successfully");
		}	
		ACM_Connectivity.CloseTab();
		driver.navigate().refresh();
		homePage.searchGlobalSearch(successMsg);
		generalEnquiryPage.openGeneralEnquiry(successMsg);		
		uiTestHelper.propagateException();
		String genEnquiryNun = generalEnquiryPage.getGeneralEnquiryNumber();
		Assert.assertEquals(successMsg, genEnquiryNun);{
			Pass_Message("General Enquiry is searched and opened from Global Search");
		}	
	}
}
}
