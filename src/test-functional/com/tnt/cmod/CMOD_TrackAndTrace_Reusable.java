package com.tnt.cmod;

import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;


public class CMOD_TrackAndTrace_Reusable extends Driver {
	UiTestHelper uiTestHelper;
	
	@BeforeSuite
	public void beforeSuite()
	{

		try
		{
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys == null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeMethod
	public void Before_method(Method method)
	{
		Test_Initializer.Before_Method(method);
	}
	
	public CMOD_TrackAndTrace_Reusable(WebDriver driver) {
		Driver.driver = (ThreadLocal<WebDriver>) getDriver();
		uiTestHelper = new UiTestHelper();
		CreateUnallocatedCaseAndValidateReasonsCausesDropDowns();
	}
	
	@Test(priority = 1, enabled = true)
	public void CreateUnallocatedCaseAndValidateReasonsCausesDropDowns() {
		try {
			//ACM_FF_Reusable.support_Login();
			//ACM_Connectivity.CloseTab();
			// Navigate to Track and Trace and use the dropdown menu
			clickUnallocatedCaseButton_on_trackAndTracePage();
			// Case Information Section
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			ccpage.setCaseGroup("Shipment");
			ccpage.setCaseReason("Clearance");
			ccpage.setCauseOption("Awaiting Clearance Instructions");

			updateConsignmentInformation();

			// Sender & Receiver Info section
			updateSender_and_Receiver_Information();

			// Alternative Collection & Delivery Info Section
			updateAlternate_Collection_and_Delivery_information();
			// Create Case and Validate
			ccpage.clickCreateCaseOnUnallocatedForm();
			ccpage.noConsignmentNumberPopUpWindowOkButton();

			boolean isStatusCreated = ccpage.checkStatusIsCreated();
			//Assert.assertTrue(isStatusCreated, "Check Status is set to Created");
			Assert.assertTrue(isStatusCreated, "Created");
			{
				Pass_Message("Unallocated case is created");
			}
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Unallocated case creation failed");
			if (getDriver().findElement(By.xpath("//h2[contains(text(),'New Case: Unallocated Case')]")).isDisplayed())
			{
				getDriver().findElement(By.xpath("//button[contains(text(),'Cancel')]")).click();
			}
		}
	}
	public void reusableCreateUnalloctaedCase() {
		// Navigate to Track and Trace and use the dropdown menu
		clickUnallocatedCaseButton_on_trackAndTracePage();
		// Case Information Section
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		ccpage.setCaseGroup("Shipment");
		ccpage.setCaseReason("Agreement Not Met");
		ccpage.setCauseOption("CS Error Previous agreement not fulfilled");

		updateConsignmentInformation();

		// Sender & Receiver Info section
		updateSender_and_Receiver_Information();

		// Alternative Collection & Delivery Info Section
		updateAlternate_Collection_and_Delivery_information();
		// Create Case and Validate
		ccpage.clickCreateCaseOnUnallocatedForm();
		ccpage.noConsignmentNumberPopUpWindowOkButton();
	}

	public void updateConsignmentInformation() {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		// Consignment Information Section
		ccpage.setCollecionDate("07-Jul-2021");
		ccpage.setCollectionDepot("MK4");
		ccpage.setDestinationDepot("BHX");
		ccpage.setPcsValue("1");
		ccpage.setTotalWeightValue("1");
	}

	public void updateSender_and_Receiver_Information() {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		ccpage.sendersLookUpAndCountryFormBoxes("TEST COMPANY IF EDIT FR CAMPAI");
		ccpage.senderInformationBlock("Bob Smart", "Fake Street", "MK1 1AA", "Test Town", "FakeVille");
		ccpage.sendersCountryTerritoryBox("United Kingdom");
		ccpage.receiverInformationBlock("Alan Berry", "Clown Street", "B1 1AA", "Fake Town");
		ccpage.receiversCountryTerritoryBox("United Kingdom");

	}

	public void updateAlternate_Collection_and_Delivery_information() {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		ccpage.collectionTownAndStreet("Test Street", "Test Town");
		ccpage.deliveryTownAndStreet("Silly Lane", "Silly City");
	}

	public void clickUnallocatedCaseButton_on_trackAndTracePage() {
		// Navigate to Track and Trace and use the dropdown menu
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		TrackAndTracePage ttp_obj = new TrackAndTracePage(getDriver());
		// Search a Consignment with 6 digits
		ttp_obj.ipCosgnNum("123456");
		ttp_obj.clickTrackandTraceSearch();
		// Error message will appear, click the cross in red box, then press create
		// unallocated case when appears
		ttp_obj.acceptErrorMessageAfterCreateCase();
		ttp_obj.clickCreateUnallocatedCaseBtn();
	}
	public void getCaseReason_and_Cause() {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		ArrayList<String> list = new ArrayList<String>();
		UiTestHelper uiTestHelper=new UiTestHelper();

		ccpage.scrolltoCaseInformation();
		ccpage.clickCaseReason();
		int reasonDropDownSize = ccpage.getCaseReasonDropDownSize();
		ccpage.clickCaseReason();
		System.out.println("Size: " + reasonDropDownSize);
		for (int i = 2; i <= reasonDropDownSize; i++) {
			ccpage.clickCaseReason();
			WebElement reasonselect = uiTestHelper.waitForObject(By.xpath(ccpage.caseReasonDropDown + i + "]"));
			uiTestHelper.clickJS(reasonselect);
			String caseReason = ccpage.getCaseReason();
			Pass_Message_withoutScreenCapture("Case Reason for Unallocated case : " + caseReason);
			ccpage.clickCaseCause();
			int causeDropDownSize = ccpage.getCaseCauseDropDownSize();
			ccpage.clickCaseCause();
				for (int j = 2; j <= causeDropDownSize; j++) {
				ccpage.clickCaseCause();
				WebElement causeSelect = uiTestHelper.waitForObject(By.xpath(ccpage.causeDropDown + j + "]"));
				uiTestHelper.clickJS(causeSelect);
				String caseCause = ccpage.getCaseCause();
				list.add(caseCause);
			}
			System.out.println(list.size());
			Pass_Message_withoutScreenCapture("Case Cause --> " + list.toString());

		}
	}

	public void checkUnallocattedCaseReason_with_cause_while_creatingCase() {
		try {
			clickUnallocatedCaseButton_on_trackAndTracePage();
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			ccpage.setCaseGroup("Shipment");
			getCaseReason_and_Cause();
			updateConsignmentInformation();
			updateSender_and_Receiver_Information();
			updateAlternate_Collection_and_Delivery_information();
			// Create Case and Validate
			ccpage.clickCreateCaseOnUnallocatedForm();
			ccpage.noConsignmentNumberPopUpWindowOkButton();

			boolean isStatusCreated = ccpage.checkStatusIsCreated();
			Assert.assertTrue(isStatusCreated, "Check Status is set to Created");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reassign case to Destination functionality is not working");
		}

	}

}
