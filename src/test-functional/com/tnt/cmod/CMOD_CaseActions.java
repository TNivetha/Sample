package com.tnt.cmod;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tnt.ccd.CCD_CI_Booking;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.ContactPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.TestMail;
import com.tnt.commonutilities.UiTestHelper;

public class CMOD_CaseActions extends Driver {

	// US B-466621 September TCs
	@Test
	public void closeCaseAndValidateIssueLocationTabExists(){
		// US B-466621 Test Cases - TC1
		String openCaseID = "20125030";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		cmodFFReusable.applyCloseCaseActions();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.waitForSuccessMsg();
		cmodFFReusable.reusableIssueLocationDropDownValidation();
	}

	// US B-466621 Test Cases - TC2 is above merged with another test case as they
	// are identical - //B-703296 Test Cases - TC1
	@Test
	public void closeCaseThenAddIssueLocationAndVerify(){
		// US B-466621 Test Cases - TC3
		String openCaseID = "20127909";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCloseCaseTab();
		ccPage.setIssueLocationField("LIS");
		ccPage.selectCloseCaseSaveButton();
		ccPage.waitForSuccessMsg();
		String firstArticleText = ccPage.getTopArticleText();
		// LIS\n might not be the correct assert value
		// will have to check this when I have sorted the error
		softAssertion.assertEquals(firstArticleText.contains("LIS\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertAll();
		Pass_Message("Issue Location DropDown Menu Exists Still");

	}

	@Test
	public void closeCaseThenAddIssueLocationOwnerShipRootCauseAndVerify() {
		// US B-466621 Test Cases - TC4
		String openCaseID = "20127909";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCloseCaseTab();
		// Issue with the onwership field and the issue location field being run to fast
		// and it trips over on the root cause
		ccPage.setOwnershipField("Lost");
		ccPage.setIssueLocationField("LIS");
		ccPage.setRootCauseField("No tracking");
		ccPage.selectCloseCaseSaveButton();
		ccPage.waitForSuccessMsg();
		String firstArticleText = ccPage.getTopArticleText();
		softAssertion.assertEquals(firstArticleText.contains("LIS\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("Lost\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("Last tracked at hub\n"), true,
				"Top Article Text Contains Expected Value");
	}

	@Test
	public void clickMergeActionsCloseCaseVerifyIssueLocationExistsAndValidate(){
		// US B-466621 Test Cases - TC5 & TC7 - Merged 2 TCs into 1
		String openCaseID = "20122658";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		// on cases page select dropdown and choose my open cases
		cmodFFReusable.clickCasesMenuAndSelectMyOpenCases();
		// search record in search box
		cmodFFReusable.searchCasePageTableAndSelectRecord(openCaseID);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		// when the single row has loaded for case, tick the box and press merged
		// actions
		caseDetailsPage.selectCaseTickBoxInTable(openCaseID);
		caseDetailsPage.clickMergedActionButton();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		// method will switchto iframe / click close case / and click save
		ccPage.switchToIFrameAndClickClosedActionsAndValidate();
		// validation is in the method - switch to iframe
		// I am validating the location box is there before the actions are performed
	}
	// US B-466621 Test Cases - TC6 is above merged with another test case as they
	// are identical - US B-466621 Test Cases - TC3

	@Test
	public void clickMergedActionsOnCasePageSelectThreeDropDownsAndValidateUpdates() {
		// US B-466621 Test Cases - TC8
		String openCaseID = "20127909";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		// press the tab - press the close case tick box, then wait
		ccPage.clickMergedActionsTab();
		ccPage.selectClosedCaseTickBox();
		// items load then fill out the below fields for test
		ccPage.setOwnershipField("Lost");
		ccPage.setIssueLocationField("LIS");
		ccPage.setRootCauseField("No tracking");
		ccPage.selectMergedActionsSaveButton();
		String firstArticleText = ccPage.getTopArticleText();
		softAssertion.assertEquals(firstArticleText.contains("LIS\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("Lost\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("No tracking\n"), true,
				"Top Article Text Contains Expected Value");
	}

	@Test
	public void clickCasesListViewMergedActionsThenCloseCase() {
		// US B-466621 Test Cases - TC9 & TC10 - Merged 2 TCs into 1
		String openCaseID = "20127876";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		// on cases page select dropdown and choose my open cases
		cmodFFReusable.clickCasesMenuAndSelectMyOpenCases();
		// search record in search box
		cmodFFReusable.searchCasePageTableAndSelectRecord(openCaseID);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		// when the single row has loaded for case, tick the box and press merged
		// actions
		caseDetailsPage.selectCaseTickBoxInTable(openCaseID);
		caseDetailsPage.clickMergedActionButton();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.switchToIFrameAndClickClosedActions();

	}

	@Test
	public void listViewMergedActionsSelectThreeDropDownsAndValidateUpdates(){
		// US B-466621 Test Cases - TC11 & TC12 - Merged 2 TCs into 1
		String openCaseID = "20125094";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		// on cases page select dropdown and choose my open cases
		cmodFFReusable.clickCasesMenuAndSelectMyOpenCases();
		// search record in search box
		cmodFFReusable.searchCasePageTableAndSelectRecord(openCaseID);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		// when the single row has loaded for case, tick the box and press merged
		// actions
		caseDetailsPage.selectCaseTickBoxInTable(openCaseID);
		caseDetailsPage.clickMergedActionButton();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		// method will switchto iframe / click close case / and click save
		ccPage.switchToIFrameClickClosedActionsAndSelectThreeDropDowns();
		// search the consignment again
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		String firstArticleText = ccPage.getTopArticleText();
		softAssertion.assertEquals(firstArticleText.contains("LIS\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("Lost\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains("No tracking\n"), true,
				"Top Article Text Contains Expected Value");
	}

	@Test
	public void validateOptOutReasonsOnContactScreen(){
		// Login (covered in the before method)
		// Click dropdown - select contact screen
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickContacts();
		// select a contact from the table
		ContactPage contactPage = new ContactPage(getDriver());
		contactPage.selectContactNameInTable("Test UK");
		contactPage.clickEditButton();
		Boolean isEmailOptOutFieldPresent = contactPage.isEmailOptOutDisplayed();
		Boolean isFaxOptOutFieldPresent = contactPage.isFaxOptOutDisplayed();
		softAssertion.assertTrue(isEmailOptOutFieldPresent.equals(true), "Email Opt out Field is Present");
		softAssertion.assertTrue(isFaxOptOutFieldPresent.equals(true), "Email Opt out Field is Present");
		softAssertion.assertAll();

	}

	public void optsOutPNAndValidateErrorMessage() {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickContacts();
		// select a contact from the table
		ContactPage contactPage = new ContactPage(getDriver());
		contactPage.clickContactDropDownMenu();
		contactPage.clickAllContactsFromDropDownMenu();
		contactPage.selectContactNameInTable("-- YUE");
		contactPage.clickEditButton();
	}

	public void createReactiveCaseAndValidateOIBCaseSubject(){
		// B-558404 TCs
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		cmodFF.CreateCase_EBS();
		// check OIB Field is there
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseInformationTab();
		Boolean isOIBCaseSubjectPresent = ccPage.checkStatusIsCreated();
		softAssertion.assertTrue(isOIBCaseSubjectPresent, "OIB Field is Present");
		softAssertion.assertAll();
	}

	public void createReactiveCaseAndAddOIBMember(){
		// B-558404 TCs
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		cmodFF.CreateCase_EBS();
		// check OIB Field is there
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseDetailsTab();
		ccPage.selectOIBNameEditerPencil();
		ccPage.selectOIBNameBoxSendKeysAndSave("Ben Grimstead");

	}

	public void secondOIBMemberLoginAndValidateCaseSubject(){
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		cmodFF.CreateCase_EBS();
		// enter another user into the OIB Field
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseDetailsTab();
		ccPage.selectOIBNameEditerPencil();
		ccPage.selectOIBNameBoxSendKeysAndSave("Deepti Khopade");
		// Login with another OIB User and proceed with validation steps
		CMOD_FF_Reusable ffReusable = new CMOD_FF_Reusable();
		ffReusable.logout();
		getDriver().navigate().refresh();
		ffReusable.cmod_OIB_Support_Login();
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		// Search for the consignment that we have opened on the other account
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.searchAnyCaseInGlobalMenu(consignmentID);
		// Go to page and click Case Details
		ccPage.selectCaseInformationTab();
		ccPage.selectOIBCaseSubjectBoxSendKeysAndSave("Test Comments");
		// Edit OIB Subject & Save
		// Validate the changes
	}

	public void reusableSearchConsignmentAndViewCases() {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		// Might need to change the class name in the DBA search
		String consignmentID = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		ttPage.clickViewCaseButton();
	}

	public void searchConsignmentAndCreateCaseWithSameOptions() {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		ttPage.clickCreateCaseButton();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		// ccpage.setReason("Agreement Not Met");
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Destination");
		ccpage.setCaseReason("Agreement Not Met");
		ccpage.setCause("CS Error Previous agreement not fulfilled");
		ccpage.setFirstName("Test");
		ccpage.setLastName("Test");
		ccpage.setphone("0123456789");
		ccpage.setEmail("test@fedex.com");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
	}

	public void scrollAcrossCasesTableAndClickEyeIcon() {
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.clickEyeIconInCaseShipmentTable();
	}

	public void validateConsignmentOpened() {
		String consignmentID = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		caseDetailsPage.getConsignmentNumber(consignmentID);
	}

	public void reopenClosedReactiveCaseVerifyCmodId(){
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			String openCaseID = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[3]);
			CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
			cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
			CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
			caseDetailsPage.clickCaseDetails();
			Boolean isCMODCaseIDPresent = caseDetailsPage.isCmodCaseIdDisplayed();
			softAssertion.assertTrue(isCMODCaseIDPresent, "CMOD ID Present in Data Field");
			CreateCasePage ccPage = new CreateCasePage(getDriver());
			ccPage.clickReOpenCaseTab();
			ccPage.clickSelectBoxChooseReOpenReason();
			ccPage.clickReOpenSaveButton();
			ccPage.waitForSuccessMsg();
			String isCreatedDisplayed = ccPage.getCreatedStatus();
			softAssertion.assertEquals(isCreatedDisplayed, "Created");
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test Case Fail");
		}
	}

	public void searchCMODCaseVerifyOwnerEqualsSupportQueue(){
		// B-781730 - 2 TCS blocked due to CMOD - CCD flow
		// edit the DBA table and add the consignment into CASENUM1 - currently null
		String cmodCaseID = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[3]);
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.searchAnyCaseInGlobalMenu(cmodCaseID);
		// once item is found
		// click case summary
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseSummaryTab();
		// validate the case owner field in this tab
		String getSupportQueueName = ccPage.getUserCreationName();
		Assert.assertEquals(getSupportQueueName, "UK - GH - Support 1");
		{
			Pass_Message("Support Queue name displayed in Case Owner is " + getSupportQueueName);
		}
		softAssertion.assertAll();
	}

	public void searchO2OConsignmentAndValidate(){
		HomePage homePage = new HomePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			cmodFF.CreateCase_EBS();
		}
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.applyTaskTabActions("Portugal", "LIS", "TNT-CS");
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.setOriginDestination("Origin");
		ccPage.sendTextToTaskInstructionsBox();
		ccPage.clickTaskSaveButton();
		ccPage.waitForSuccessMsg();
		ccPage.clickTaskQuickLink();
		// when task gets generated - take Task Link - go to table, pick selected task
		// and continue
		ccPage.clickFirstTaskTickBox();
		ccPage.clickTaskHyperLink();
		// open the task up when its made and validate the field is present
		Boolean isEscalationFieldPresent = ccPage.isEscalationSlaFieldDisplayed();
		softAssertion.assertEquals(isEscalationFieldPresent, "true");
		softAssertion.assertAll();
	}

	public void searchO2OConsAndSelectRouteTo(){
		HomePage homePage = new HomePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("TEST", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[3]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			cmodFF.CreateCase_EBS();
		}
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.applyTaskTabActions("Portugal", "LIS", "TNT-CS");
	}

	public void createReactiveCaseAndSelectTaskOptions(){
		HomePage homePage = new HomePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		String consignmentID = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);

		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignmentID);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			cmodFF.CreateCase_EBS();
		}
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.applyTaskTabActions("Portugal", "LIS", "TNT-CS");
		ccPage.clickTaskSaveButton();
		ccPage.waitForSuccessMsg();
	}

	public void reusableSelectCasePageAndUKGhDedicatedView() {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		cdPage.clickCasesDropDownMenu();
		cdPage.selectCasesListViewAndSendKeys();
		// Need a pause between here
		// cdPage.waitForCaseTableToLoad();
		String caseNum = Database_Connection.retrieveTestData("CASENUM", "ACM", "KEY",
				CMOD_InSprintTestExecution.Key_Array[3]);
		cdPage.searchCaseNumberInSearchBox(caseNum);
		// cdPage.waitForCaseTableToLoad();
		cdPage.selectInboundACMTaskFromTable(caseNum);
		// need a big pause here between this loading -
		// Next add the select case from the table view - paramterise a DBA call and
		// pass it in
		// Once row has been located into the table - click the small tick box at the
		// start of the column
		cdPage.clickMoreActionsDropDownMenu();
		cdPage.clickAcceptMenuItem();
		// case page loads
		// then go to the Case Summary Tab
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseSummaryTab();
		// Validate that the Case Owner is - UK - GH - Dedicated 1
		// Case Owner stuff on CreatecasePage object
	}

	public void reusableSearchConsignmentInTTP(String caseNum){
		HomePage homePage = new HomePage(getDriver());
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		// Update Consignment Number according to Consignment type
		String consignment = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ttPage.setFirstConsignmentNumberBox(consignment);
		ttPage.clickTrackandTraceSearch();
		CMOD_FunctionalFlow_Updated cmodFF = new CMOD_FunctionalFlow_Updated();
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			cmodFF.CreateCase_EBS();
		}
		UiTestHelper uiTestHelper = new UiTestHelper();
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.applyTaskTabActions("Portugal", "LIS", "TNT-CS");
	}

	public void peCaseFlowRegression(){
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
		CreateCasePage createCasePage = new CreateCasePage(getDriver());
		String Retrive = "625567133";
		connectivity.TrackandTraceCCD(Retrive);
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			ffUpdated.createCase_PE();
		}

		// Create Task
		createCasePage.clickTaskTab();
		createCasePage.taskCountryTerritoryBox("Portugal");
		createCasePage.setTaskLocation("LIS");
		createCasePage.setCaseRoute("TNT-CS");
		createCasePage.setOriginDestination("Origin");
		createCasePage.sendTextToTaskInstructionsBox();
		createCasePage.clickTaskSaveButton();

		if (casedetailpage.verifySuccessMessage() == true) {
			Pass_Message("Task created successfully");
			String recentTaskID = createCasePage.getRecentTaskId();
			casedetailpage.clickRelatedTab();
			casedetailpage.clickTaskView();
			createCasePage.openRecentlyCreatedTask(recentTaskID);
			if (!recentTaskID.isEmpty()) {
				Pass_Message("Task ID created is " + recentTaskID);
			} else {
				Fail_Message("Task ID is not generated");
			}
			casedetailpage.closeOpenedTask();
			casedetailpage.closeTaskTab();
		} else {
			Fail_Message("Task not Created");
		}

		// Case Details
		casedetailpage.clickCaseDetails();
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String caseOwner = casedetailpage.getcaseOwnerValue();
		if (caseOwner.isEmpty()) {
			Fail_Message("Case owner is not displayed correctly");
		} else {
			Pass_Message("Case owner displayed as: " + caseOwner);
		}
		String caseOwnerCountry = casedetailpage.getcaseOwnerCountry();
		if (caseOwnerCountry.isEmpty()) {
			Fail_Message("Case owner Country is not displayed correctly");
		} else {
			Pass_Message("Case owner Country displayed as: " + caseOwnerCountry);
		}
		String cmodId = casedetailpage.getcmodID();
		if (cmodId.isEmpty()) {
			Fail_Message("CMOD is blank");
		} else {
			Pass_Message("CMOD id is displayed as : " + cmodId);
		}
		String shipmentdirection = casedetailpage.getShipmentDirection();
		if (shipmentdirection.isEmpty()) {
			Fail_Message("Shipment Direction is blank");
		} else {
			Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
		}
		String custdialOwner = casedetailpage.getCustodialOwner();
		if (custdialOwner.isEmpty()) {
			Fail_Message("Custodial Owner is blank");
		} else {
			Pass_Message("Custodial Owner id is displayed as : " + custdialOwner);
		}

		// Actions
		try {
			casedetailpage.clickActionTab();
			String actionType = "Communication with Another Location";
			casedetailpage.setActionType(actionType);
			casedetailpage.setActionCost("3.25");
			casedetailpage.clickActionSave();
			if (casedetailpage.verifySuccessMessage() == true) {
				Pass_Message("Actions created successfully");
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
			} else {
				Fail_Message("Action is not Created");
			}
			boolean actionTable = casedetailpage.actionTableView();
			int Size = casedetailpage.getSize();
			System.out.println("Size: " + Size);
			if (actionTable = true) {
				Pass_Message("Action displayed in action table");
			} else {
				Fail_Message("Action table not present");
			}
			String cost = casedetailpage.getactionTableCost(Size).trim();
			if (cost.contains("3.25")) {
				Pass_Message("Cost verified in actions quicklink");
			} else {
				Fail_Message("Cost verification in actions quicklink has failed");
			}
			casedetailpage.closeActionsTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Action details verification failed");
		}
		// Customer Remark tab
		try {
			casedetailpage.clickCustRemarkTab();
			casedetailpage.setCustRemarkdesc("Update Customer Remarks for testing");
			casedetailpage.clickCustRemarkSave();
			if (casedetailpage.verifySuccessMessage() == true) {
				Pass_Message("Customer Remarks added succesfully");
				casedetailpage.clickCaseInformation();
				String Remarks = casedetailpage.getRemark().trim();
				if (Remarks.contains("Update Customer Remarks for testing")) {
					Pass_Message("Customer remark verified in case information details section succesfully");
				} else {
					Fail_Message("Customer remark has not been displayed in case information section");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer remark verification in case information section failed");
		}

		// Case Remark
		try {
			casedetailpage.clickCaseRemarkTab();
			casedetailpage.setCaseRemarkdesc("Update Case Remarks for testing");
			casedetailpage.clickCaseRemarkSave();
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']")));
			if (casedetailpage.verifySuccessMessage() == true) {
				casedetailpage.clickRelatedTab();
				casedetailpage.clickCaseRemarkVew();
			}
			String datetTime = casedetailpage.getcaseRemarkCreatedTime();
			if (datetTime.isEmpty()) {
				Fail_Message("Created Date of Case Remark is not displayed");
			} else {
				Pass_Message("Created Date of Case Remark is displayed as " + datetTime);
			}
			String comments = casedetailpage.getCaseRemarkComments();
			if (comments.isEmpty()) {
				Fail_Message("Comments of Case Remark is not displayed");
			} else {
				Pass_Message("Comments of Case Remark is displayed as " + comments);
			}
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			casedetailpage.closeCaseRemarksTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case remark verification failed");
		}

		// Email
		try {
			casedetailpage.clickConsignmentInfo();
			casedetailpage.clickDestinationDepot();
			casedetailpage.clickAdditionalInfo();
			String emailTo = casedetailpage.getEmail();
			System.out.println("Mail TO: " + emailTo);
			Support.internal_tabclose();
			casedetailpage.clickEmailTab();
			casedetailpage.clickCompose();
			casedetailpage.setEmailTo(emailTo);
			casedetailpage.setEmailSubject("Request for Information/Action");
			casedetailpage.clickTemplate();
			System.out.println("Template clicked");
			casedetailpage.insertTemplatebtn();
			casedetailpage.selectTemplateType("All Classic Templates");
			casedetailpage.selectTemplate("Generic");
			casedetailpage.clickGenericTemplate();
			Pass_Message("Generic template inserted");
			casedetailpage.clickSendBtn();
			String confirmMessage = casedetailpage.getEmailConfirmMessage();
			if (confirmMessage.isEmpty()) {
				Fail_Message("Email is not Sent Successfully");
			} else {
				Pass_Message("Email sent successfully " + confirmMessage);
			}
			getDriver().navigate().refresh();
			getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email service failed");
		}

		// Escalations
		try {
			getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			casedetailpage.clickRelatedTab();
			casedetailpage.clickEscalation();
			int Size = casedetailpage.getEscalationTableSize();
			casedetailpage.clickescalationTable(Size, "Request for Information");
			casedetailpage.clickescalatebtn();
			boolean escVal = casedetailpage.verifyEmailEscalationMessage();
			Assert.assertEquals(escVal, true);
			{
				Pass_Message("Email cannnot be escalated and an error message is displayed successfully");
			}
			casedetailpage.closeRequestForInfoActionTab();
			casedetailpage.closeEscalationsTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Email escalation failed");
		}

		// Close Case
		try {
			casedetailpage.clickCloseCaseTab();
			casedetailpage.clickConsumerType();
			if (casedetailpage.showBToB() == true && casedetailpage.showBToC() == true
					&& casedetailpage.showUnclearUnknown() == true) {
				Pass_Message("'Consumer Type' Drowpdown list is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Consumer Type' drop down list validation failed");
		}
		try {
			casedetailpage.clickBToB();
			casedetailpage.setComments("Close the Case for testing");
			casedetailpage.clickCloseCaseSave();
			String status = casedetailpage.getCaseStatus();
			System.out.println(status);
			if (status.contains("'Manual-Closed")) {
				Pass_Message("Case is closed Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed after adding Consumer Type drop down value");
		}
	}

	public void reactiveCaseFlowRegression(){
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
		CreateCasePage createCasePage = new CreateCasePage(getDriver());
		String Retrive = "904092545";
		connectivity.TrackandTraceCCD(Retrive);
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			ffUpdated.CreateCase_EBS();
		}

		// Create Task
		createCasePage.clickTaskTab();
		createCasePage.taskCountryTerritoryBox("Portugal");
		createCasePage.setTaskLocation("LIS");
		createCasePage.setCaseRoute("TNT-CS");
		// createCasePage.setOriginDestination("Origin");
		createCasePage.sendTextToTaskInstructionsBox();
		createCasePage.clickTaskSaveButton();

		if (casedetailpage.verifySuccessMessage() == true) {
			Pass_Message("Task created successfully");
			String recentTaskID = createCasePage.getRecentTaskId();
			casedetailpage.clickRelatedTab();
			casedetailpage.clickTaskView();
			createCasePage.openRecentlyCreatedTask(recentTaskID);
			if (!recentTaskID.isEmpty()) {
				Pass_Message("Task ID created is " + recentTaskID);
			} else {
				Fail_Message("Task ID is not generated");
			}
			casedetailpage.closeOpenedTask();
			casedetailpage.closeTaskTab();
		} else {
			Fail_Message("Task not Created");
		}

		// Case Details
		casedetailpage.clickCaseDetails();
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String caseOwner = casedetailpage.getcaseOwnerValue();
		if (caseOwner.isEmpty()) {
			Fail_Message("Case owner is not displayed correctly");
		} else {
			Pass_Message("Case owner displayed as: " + caseOwner);
		}
		String caseOwnerCountry = casedetailpage.getcaseOwnerCountry();
		if (caseOwnerCountry.isEmpty()) {
			Fail_Message("Case owner Country is not displayed correctly");
		} else {
			Pass_Message("Case owner Country displayed as: " + caseOwnerCountry);
		}
		String cmodId = casedetailpage.getcmodID();
		if (cmodId.isEmpty()) {
			Fail_Message("CMOD is blank");
		} else {
			Pass_Message("CMOD id is displayed as : " + cmodId);
		}
		String shipmentdirection = casedetailpage.getShipmentDirection();
		if (shipmentdirection.isEmpty()) {
			Fail_Message("Shipment Direction is blank");
		} else {
			Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
		}
		String custdialOwner = casedetailpage.getCustodialOwner();
		if (custdialOwner.isEmpty()) {
			Fail_Message("Custodial Owner is blank");
		} else {
			Pass_Message("Custodial Owner id is displayed as : " + custdialOwner);
		}

		// Actions
		try {
			casedetailpage.clickActionTab();
			String actionType = "Communication with Another Location";
			casedetailpage.setActionType(actionType);
			casedetailpage.setActionCost("3.25");
			casedetailpage.clickActionSave();
			if (casedetailpage.verifySuccessMessage() == true) {
				Pass_Message("Actions created successfully");
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
			} else {
				Fail_Message("Action is not Created");
			}
			boolean actionTable = casedetailpage.actionTableView();
			int Size = casedetailpage.getSize();
			System.out.println("Size: " + Size);
			if (actionTable = true) {
				Pass_Message("Action displayed in action table");
			} else {
				Fail_Message("Action table not present");
			}
			String cost = casedetailpage.getactionTableCost(Size).trim();
			if (cost.contains("3.25")) {
				Pass_Message("Cost verified in actions quicklink");
			} else {
				Fail_Message("Cost verification in actions quicklink has failed");
			}
			casedetailpage.closeActionsTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Action details verification failed");
		}
		// Customer Remark tab
		try {
			casedetailpage.clickCustRemarkTab();
			casedetailpage.setCustRemarkdesc("Update Customer Remarks for testing");
			casedetailpage.clickCustRemarkSave();
			if (casedetailpage.verifySuccessMessage() == true) {
				Pass_Message("Customer Remarks added succesfully");
				casedetailpage.clickCaseInformation();
				String Remarks = casedetailpage.getRemark().trim();
				if (Remarks.contains("Update Customer Remarks for testing")) {
					Pass_Message("Customer remark verified in case information details section succesfully");
				} else {
					Fail_Message("Customer remark has not been displayed in case information section");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer remark verification in case information section failed");
		}

		// Case Remark
		try {
			casedetailpage.clickCaseRemarkTab();
			casedetailpage.setCaseRemarkdesc("Update Case Remarks for testing");
			casedetailpage.clickCaseRemarkSave();
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']")));
			if (casedetailpage.verifySuccessMessage() == true) {
				casedetailpage.clickRelatedTab();
				casedetailpage.clickCaseRemarkVew();
			}
			String datetTime = casedetailpage.getcaseRemarkCreatedTime();
			if (datetTime.isEmpty()) {
				Fail_Message("Created Date of Case Remark is not displayed");
			} else {
				Pass_Message("Created Date of Case Remark is displayed as " + datetTime);
			}
			String comments = casedetailpage.getCaseRemarkComments();
			if (comments.isEmpty()) {
				Fail_Message("Comments of Case Remark is not displayed");
			} else {
				Pass_Message("Comments of Case Remark is displayed as " + comments);
			}
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			casedetailpage.closeCaseRemarksTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case remark verification failed");
		}

		// Email
		try {
			casedetailpage.clickConsignmentInfo();
			casedetailpage.clickDestinationDepot();
			casedetailpage.clickAdditionalInfo();
			String emailTo = casedetailpage.getEmail();
			System.out.println("Mail TO: " + emailTo);
			Support.internal_tabclose();
			casedetailpage.clickEmailTab();
			casedetailpage.clickCompose();
			casedetailpage.setEmailTo(emailTo);
			casedetailpage.setEmailSubject("Request for Information/Action");
			casedetailpage.clickTemplate();
			System.out.println("Template clicked");
			casedetailpage.insertTemplatebtn();
			casedetailpage.selectTemplateType("All Classic Templates");
			casedetailpage.selectTemplate("Generic");
			casedetailpage.clickGenericTemplate();
			Pass_Message("Generic template inserted");
			casedetailpage.clickSendBtn();
			String confirmMessage = casedetailpage.getEmailConfirmMessage();
			if (confirmMessage.isEmpty()) {
				Fail_Message("Email is not Sent Successfully");
			} else {
				Pass_Message("Email sent successfully " + confirmMessage);
			}
			getDriver().navigate().refresh();
			getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email service failed");
		}

		// Escalations
		try {
			getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			casedetailpage.clickRelatedTab();
			casedetailpage.clickEscalation();
			int Size = casedetailpage.getEscalationTableSize();
			casedetailpage.clickescalationTable(Size, "Request for Information");
			casedetailpage.clickescalatebtn();
			boolean escVal = casedetailpage.verifyEmailEscalationMessage();
			Assert.assertEquals(escVal, true);
			{
				Pass_Message("Email cannnot be escalated and an error message is displayed successfully");
			}
			casedetailpage.closeRequestForInfoActionTab();
			casedetailpage.closeEscalationsTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Email escalation failed");
		}

		// Close Case
		try {
			casedetailpage.clickCloseCaseTab();
			casedetailpage.clickConsumerType();
			if (casedetailpage.showBToB() == true && casedetailpage.showBToC() == true
					&& casedetailpage.showUnclearUnknown() == true) {
				Pass_Message("'Consumer Type' Drowpdown list is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Consumer Type' drop down list validation failed");
		}
		try {
			casedetailpage.clickBToB();
			casedetailpage.setComments("Close the Case for testing");
			casedetailpage.clickCloseCaseSave();
			String status = casedetailpage.getCaseStatus();
			System.out.println(status);
			if (status.contains("'Manual-Closed")) {
				Pass_Message("Case is closed Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed after adding Consumer Type drop down value");
		}
	}

	public void us_B1041152_CCD_Simplify_case_creation_on_contact_part1(String contactname) {
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ContactPage contactPage = new ContactPage(getDriver());
		HomePage homePage = new HomePage(getDriver());
		try {
			ACM_Connectivity.CloseTab();// to close all existing tabs
			Pass_Message("Login to salesforce as CSR successfully");
			OpenContactPage(contactname);
			verifyCasesPanel();
			int totalcases = contactPage.getCasesCount();
			if (totalcases > 0) {
				if (totalcases == 1)
					Pass_Message(totalcases + " Case is Present under Cases Panel");
				if (totalcases > 1)
					Pass_Message(totalcases + " Cases are Present under Cases Panel");
			} else {
				Fail_Message("No Cases Found under Cases Panel");
				totalcases = 0;
			}
			Assert.assertTrue(totalcases == 0);

			String[] cases = contactPage.getCaseDetailsByRowNumber(totalcases);
			validateCaseDetailsOnContactPage(cases);

			Pass_Message("CCD Simplify Case Creation on Contact: Cases Panel Validation Completed successfully");
			// contactPage.selectContactNameInTable();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD Simplify Case Creation on Contact: Cases Panel Validation Failed");
		}
	}

	public void OpenContactPage(String contactname) {
		ContactPage contactPage = new ContactPage(getDriver());
		HomePage homePage = new HomePage(getDriver());
		try {
			homePage.clickDropDownNavigationMenu();
			homePage.clickContacts();

			if (contactPage.VerifyDefaultContactDropDownValue()) {
				contactPage.clickContactDropDownMenu();
			}
			contactPage.clickAllContactsFromDropDownMenu();
			contactPage.clickOnContact(contactname);

			boolean verifyContactPageOpened = contactPage.hasContactOpened(contactname);
			if (verifyContactPageOpened) {
				Pass_Message("Contact Page has Opened");
			} else {
				Fail_Message("Contact Page has not Opened");
			}
			Assert.assertTrue(verifyContactPageOpened, "Contact Page has Opened");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD Simplify Case Creation on Contact: Contact Page has not Opened");
		}
	}

	public void verifyCasesPanel() {
		ContactPage contactPage = new ContactPage(getDriver());
		try {
			boolean isCasesPanelPresent = contactPage.isCasesPanel();
			if (isCasesPanelPresent) {
				Pass_Message("Cases Panel is Displayed Successfully");
			} else {
				Fail_Message("Cases Panel is not Displayed");
			}
			Assert.assertTrue(isCasesPanelPresent, "Cases Panel is Displayed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD Simplify Case Creation on Contact: Cases Panel not displayed");
		}
	}

	public void validateCaseDetailsOnContactPage(String[] cases) {
		try {
			boolean isCaseNumberNull = !cases[0].isEmpty();
			boolean isSubjectNull = !cases[1].isEmpty();
			boolean isCaseTypeNull = !cases[2].isEmpty();
			boolean isDateandTimeNull = !cases[3].isEmpty();

			if (isCaseNumberNull) {
				Pass_Message("Case Number is Displayed as " + cases[0]);
			} else {
				Fail_Message("Case Number is not Displayed");
			}
			softAssertion.assertTrue(isCaseNumberNull, "Case Number is Displayed");

			if (isSubjectNull) {
				Pass_Message("Subject is Displayed as " + cases[1]);
			} else {
				Fail_Message("Subject is not Displayed");
			}
			softAssertion.assertTrue(isCaseNumberNull, "Subject is Displayed");

			if (isCaseTypeNull) {
				Pass_Message("Case Type is Displayed as " + cases[2]);
			} else {
				Fail_Message("Case Type is not Displayed");
			}
			softAssertion.assertTrue(isCaseNumberNull, "Case Type is Displayed as " + cases[2]);

			if (isDateandTimeNull) {
				Pass_Message("Date and Time is Displayed as " + cases[3]);
			} else {
				Fail_Message("Date and Time is not Displayed");
			}
			softAssertion.assertTrue(isCaseNumberNull, "Date and Time is Displayed as " + cases[3]);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Exception: CCD Simplify Case Creation on Contact: Case Details Validation in Cases Panel Failed");
		}
	}

	public void us_B1039338_CCD_Simplify_case_creation_on_contact_part2(String contactname, String casequeue,
			String subject, String country) {
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ContactPage contactPage = new ContactPage(getDriver());
		HomePage homePage = new HomePage(getDriver());
		try {
			ACM_Connectivity.CloseTab();// to close all existing tabs
			Pass_Message("Login to salesforce as CSR successfully");

			homePage.clickDropDownNavigationMenu();
			homePage.clickContacts();

			if (contactPage.VerifyDefaultContactDropDownValue()) {
				contactPage.clickContactDropDownMenu();
			}
			contactPage.clickAllContactsFromDropDownMenu();
			contactPage.clickOnContact(contactname);

			boolean verifyContactPageOpened = contactPage.hasContactOpened(contactname);
			if (verifyContactPageOpened) {
				Pass_Message("Contact Page has Opened");
			} else {
				Fail_Message("Contact Page has not Opened");
			}
			Assert.assertTrue(verifyContactPageOpened, "Contact Page has Opened");

			boolean isCreateCaseButton = contactPage.isCreateCaseButton();
			if (isCreateCaseButton) {
				Pass_Message("Create Case Button on Contact Page Displayed successfully");
			} else {
				Fail_Message("Create Case Button on Contact Page not Displayed");
			}
			Assert.assertTrue(isCreateCaseButton, "Create Case Button on Contact Page Displayed successfully");

			boolean isCreateCasePopUp = contactPage.isCreateCasePopUp();
			if (isCreateCasePopUp) {
				Pass_Message("Create Case PopUp has Displayed");
			} else {
				Fail_Message("Create Case PopUp has not Displayed");
			}
			Assert.assertTrue(isCreateCasePopUp, "Create Case PopUp has displayed");

			String availableFieldsonPopUp = contactPage.verifyFieldsonPopUp();

			boolean isCaseQueue = availableFieldsonPopUp.contains("Case Queue");
			boolean isSubject = availableFieldsonPopUp.contains("Subject");
			boolean isCaseCountry = availableFieldsonPopUp.contains("Case Country");
			boolean isSaveButton = availableFieldsonPopUp.contains("Save");
			boolean isCancelButton = availableFieldsonPopUp.contains("Cancel");
			if (isCaseQueue) {
				Pass_Message("Case Queue Field has Displayed on Create Case PopUp");
			} else {
				Fail_Message("Case Queue Field has not Displayed on Create Case PopUp");
			}
			Assert.assertTrue(isCaseQueue, "Case Queue Field has Displayed on Create Case PopUp");

			if (isSubject) {
				Pass_Message("Subject Field has Displayed on Create Case PopUp");
			} else {
				Fail_Message("Subject Field has not Displayed on Create Case PopUp");
			}
			Assert.assertTrue(isSubject, "Subject Field has Displayed on Create Case PopUp");

			if (isCaseCountry) {
				Pass_Message("Case Country Field has Displayed on Create Case PopUp");
			} else {
				Fail_Message("Case Country Field has not Displayed on Create Case PopUp");
			}
			Assert.assertTrue(isCaseCountry, "Case Country Field has Displayed on Create Case PopUp");

			if (isSaveButton) {
				Pass_Message("Save Button has Displayed on Create Case PopUp");
			} else {
				Fail_Message("Save Button has not Displayed on Create Case PopUp");
			}
			Assert.assertTrue(isSaveButton, "Save Button has Displayed on Create Case PopUp");

			if (isCancelButton) {
				Pass_Message("Cancel Button has Displayed on Create Case PopUp");
			} else {
				Fail_Message("Cancel Button has Displayed on Create Case PopUp");
			}
			Assert.assertTrue(isCancelButton, "Cancel Button has Displayed on Create Case PopUpp");

			contactPage.createCase(casequeue, subject, country);

			boolean isSuccessMessage = contactPage.getSuccessMessage("Case created Successfully");
			if (isSuccessMessage) {
				Pass_Message("Message has Displayed as 'Case Created Successfully'");
			} else {
				Fail_Message("Success Message has not Diplayed");
			}

			boolean hasCasePageOpened = contactPage.isCasePageDisplayed(subject);
			if (hasCasePageOpened) {
				Pass_Message("Case created Successfully and Case Page has Opened");
			} else {
				Fail_Message("Case Page has not Opened");
			}
			Assert.assertTrue(hasCasePageOpened, "Case created Successfully");

			/*
			 * Created case validation on contact page under Cases Panel
			 */
			ACM_Connectivity.CloseTab();// to close all existing tabs
			OpenContactPage(contactname);
			getDriver().navigate().refresh();
			verifyCasesPanel();
			String[] cases = contactPage.getCaseDetailsBySubject(subject);
			validateCaseDetailsOnContactPage(cases);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD Simplify Case Creation on Contact: Case Creation Failed");
		}
	}

	public void CCD_Enhancement_on_DestinationEmailAddressTest() {
		TestMail testmail = new TestMail();
		HomePage homePage = new HomePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		CCD_CI_Booking cibooking = new CCD_CI_Booking();
		CaseDetailsPage casedetailspage = new CaseDetailsPage(getDriver());
		ContactPage contactPage = new ContactPage(getDriver());
		String toMail = "pavankumar.challapalli.osv@dyxebm4bmnuxzrynz7btsijtdwkvlsr6u45kjg48r639iutq8.3o-8lsguai.cs129.case.sandbox.salesforce.com";
		toMail = "senthilkumar.murugesan.osv@r-22zmyk0ajonc34ejn97ohg7e3dcx5kxvwk1rx8w0u336faudd9.1w-v73eae.cs105.case.sandbox.salesforce.com";
		String fromMail = "RAMANSINGH.SHAH@TMAIL.COM";
		String subject = "TEST405";
		String mailBody = "This is test mail body";
		String listType = "All Cases";// SIT
		listType = "ALL Ca";// UAT
		try {
			boolean hasEmailSent = testmail.sendEmail(mailBody, subject, toMail, fromMail);// this method is to send the
																							// email to CCD to create
																							// the auto contact
			if (hasEmailSent) {
				Pass_Message("Email has sent successfully");
			} else {
				Fail_Message("Failed: Email did not send");
			}
			Assert.assertEquals(hasEmailSent, true);
			// cibooking.returnToPage();

			ACM_Connectivity.CloseTab();

			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			homePage.openRequiredListType(listType);
			homePage.sortTheColumn("Descending", "Case Number");
			String subject_ccd = homePage.getSubject();
			boolean hasMailReceivedByCCD = subject_ccd.contains(subject);
			while (!subject_ccd.contains(subject)) {
				homePage.refreshSearch();
				subject_ccd = homePage.getSubject();
			}

			hasMailReceivedByCCD = subject_ccd.contains(subject);
			if (hasMailReceivedByCCD) {
				Pass_Message("Email has received in CCD and email-to-case created successfully");
			} else {
				Fail_Message("Failed: Email did not receive");
			}
			Assert.assertEquals(hasMailReceivedByCCD, true);

			String emailIsNotClickable = "";
			emailIsNotClickable = homePage.isEmailClickable(fromMail);
			if (emailIsNotClickable.equalsIgnoreCase("truetrue")) {
				Pass_Message("Email Address is not clickable in case queue as expected");
			} else if (emailIsNotClickable.equals("falsefalse")) {
				Fail_Message("Email Address is clickable in case queue");
			} else {
				Fail_Message("Email Address is not present in case queue");
			}
			homePage.clickOnLatestCase();
			getDriver().navigate().refresh();

			String contactName = casedetailspage.getCustomerContactName();
			boolean hasContactCreated = contactName!=null;
			if (hasContactCreated) {
				Pass_Message("Contact has created successfully");
			} else {
				Fail_Message("Failed: contact did not create");
			}
			Assert.assertEquals(hasContactCreated, true);

			casedetailspage.clickCaseDetails();
			emailIsNotClickable = casedetailspage.isEmailClickable_CaseDetails(fromMail);
			if (emailIsNotClickable.equalsIgnoreCase("truetrue")) {
				Pass_Message("Email Address is not clickable in case details as expected");
			} else if (emailIsNotClickable.equals("falsefalse")) {
				Fail_Message("Email Address is clickable in case details");
			} else {
				Fail_Message("Email Address is not present in case details");
			}

			casedetailspage.clickCustomerContactName();
			emailIsNotClickable = contactPage.isEmailClickable_ContactHeader(fromMail);
			if (emailIsNotClickable.equalsIgnoreCase("texttext")) {
				Pass_Message("Email Address is not clickable in conatct header as expected");
			} else if (emailIsNotClickable.equals("linklink")) {
				Fail_Message("Email Address is clickable in conatct header");
			} else {
				Fail_Message("Email Address is not present in conatct header");
			}

			emailIsNotClickable = contactPage.isEmailClickable_ContactDetails(fromMail);
			if (emailIsNotClickable.equalsIgnoreCase("truetrue")) {
				Pass_Message("Email Address is not clickable in contact details as expected");
			} else if (emailIsNotClickable.equals("falsefalse")) {
				Fail_Message("Email Address is clickable in contact details");
			} else {
				Fail_Message("Email Address is not present in contact details");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD- Enhancement on Destination Email Address Test Failed");
		}
	}

	public void CCD_Enhancement_on_SalutationValue() {
	
		CaseDetailsPage casedetailspage = new CaseDetailsPage(getDriver());
		ContactPage contactPage = new ContactPage(getDriver());
		String toMail = "pavankumar.challapalli.osv@dyxebm4bmnuxzrynz7btsijtdwkvlsr6u45kjg48r639iutq8.3o-8lsguai.cs129.case.sandbox.salesforce.com";
		toMail = "senthilkumar.murugesan.osv@r-22zmyk0ajonc34ejn97ohg7e3dcx5kxvwk1rx8w0u336faudd9.1w-v73eae.cs105.case.sandbox.salesforce.com";
		String fromMail = "RAMANSINGH.SHAH@TMAIL.COM";
		String subject = "TEST601";
		String mailBody = "This is test mail body";
		String listType = "All Cases";// SIT
		listType = "ALL Ca";// UAT
		try {
			autoContactCreation(mailBody, subject, toMail, fromMail);
			casedetailspage.clickCustomerContactName();
			String salutation = contactPage.getSalutation();
			if (salutation.isEmpty()) {
				Pass_Message("Salutation has set as none as expected");
			} else {
				Fail_Message("Salutation has set as " + salutation + " intead of none");
			}
			Assert.assertEquals(salutation, "");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,450)");
			String referenceSystem = contactPage.getReferenceSystem_Contact();
			if (referenceSystem.equalsIgnoreCase("CCD")) {
				Pass_Message("Reference System has set as " + referenceSystem + " as expected in contact details");
			} else {
				Fail_Message("Reference System has set as " + referenceSystem + " intead of CCD in contact details");
			}
			Assert.assertEquals(referenceSystem, "CCD");

			js.executeScript("window.scrollBy(0,-450)");
			String customername = contactPage.getAndClickCustomerName();
			boolean hasCustomerCreated = customername != null;
			if (hasCustomerCreated) {
				Pass_Message("Customer has created successfully");
			} else {
				Fail_Message("Failed: Customer did not create");
			}
			Assert.assertEquals(hasCustomerCreated, true);
			js.executeScript("window.scrollBy(0,450)");
			referenceSystem = contactPage.getReferenceSystem_Customer();
			if (referenceSystem.equalsIgnoreCase("CCD")) {
				Pass_Message("Reference System has set as " + referenceSystem + " as expected in customer details");
			} else {
				Fail_Message("Reference System has set as " + referenceSystem + " intead of CCD in customer details");
			}
			Assert.assertEquals(referenceSystem, "CCD");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD- Enhancement on Auto Contact Creation");
		}
	}

	public void autoContactCreation(String mailBody, String subject, String toMail, String fromMail) {
		TestMail testmail = new TestMail();
		HomePage homePage = new HomePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		CaseDetailsPage casedetailspage = new CaseDetailsPage(getDriver());
		String listType = "All Cases";// SIT
		listType = "ALL Ca";// UAT
		try {
			boolean hasEmailSent = testmail.sendEmail(mailBody, subject, toMail, fromMail);// this method is to send the
																							// email to CCD to create
																							// the auto contact
			if (hasEmailSent) {
				Pass_Message("Email has sent successfully");
			} else {
				Fail_Message("Failed: Email did not send");
			}
			Assert.assertEquals(hasEmailSent, true);
			// cibooking.returnToPage();

			ACM_Connectivity.CloseTab();

			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			homePage.openRequiredListType(listType);
			homePage.sortTheColumn("Descending", "Case Number");
			String subject_ccd = homePage.getSubject();
			boolean hasMailReceivedByCCD = subject_ccd.contains(subject);
			while (!subject_ccd.contains(subject)) {
				homePage.refreshSearch();
				subject_ccd = homePage.getSubject();
			}

			hasMailReceivedByCCD = subject_ccd.contains(subject);
			if (hasMailReceivedByCCD) {
				Pass_Message("Email has received in CCD and email-to-case created successfully");
			} else {
				Fail_Message("Failed: Email did not receive");
			}
			Assert.assertEquals(hasMailReceivedByCCD, true);
			homePage.clickOnLatestCase();
			getDriver().navigate().refresh();
			String contactName = casedetailspage.getCustomerContactName();
			boolean hasContactCreated = contactName != null;
			if (hasContactCreated) {
				Pass_Message("Contact has created successfully");
			} else {
				Fail_Message("Failed: contact did not create");
			}
			Assert.assertEquals(hasContactCreated, true);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Auto Contact Creation has failed");
		}
	}

	// B-1161274
	public void ccd_Auto_Contact_Creation_part2_ReferenceSystem() {
		HomePage homePage = new HomePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		try {
			// cibooking.returnToPage();

			ACM_Connectivity.CloseTab();
			boolean isRedirectedToCCD = homePage.isRedirectedToCCD();
			if (isRedirectedToCCD) {
				Pass_Message("Logged in to Customer Care Desktop successfully");
			} else {
				Fail_Message("Failed: Navigation to Customer Care Desktop failed");
			}
			Assert.assertEquals(isRedirectedToCCD, true);
			homePage.clickAppLauncher();
			homePage.searchItem("Customers");
			boolean isRedirectedToCustomers = homePage.isRedirectedToCustomers();
			if (isRedirectedToCustomers) {
				Pass_Message("Navigated to Customers Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to Customers Screen failed");
			}
			Assert.assertEquals(isRedirectedToCustomers, true);

			homePage.clickNewCustomer();
			homePage.clickNextButton();
			boolean isRedirectedToNewCustomers = homePage.isRedirectedToNewCustomer();
			if (isRedirectedToNewCustomers) {
				Pass_Message("Navigated to New Customer creation Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to New Customer creation Screen failed");
			}
			Assert.assertEquals(isRedirectedToNewCustomers, true);

			boolean is_ref_sys_fields = homePage.isReferenceSystemField();
			System.out.println("is_ref_sys_fields: " + is_ref_sys_fields);
			if (is_ref_sys_fields) {
				Pass_Message("Reference System Field Displayed successfully");
			} else {
				Fail_Message("Failed: Reference System Field did not Display");
			}
			Assert.assertEquals(is_ref_sys_fields, true);

			homePage.clcikRefSysDropDown();
			String referenceSystem_listValue = homePage.getReferenceSystemList_customer();
			System.out.println("referenceSystem_listValue: " + referenceSystem_listValue);
			int temp = 0;
			if (referenceSystem_listValue.contains("None")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'None' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CCD")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CCD' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CAM")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CAM' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CDB")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CDB' in Reference System Drop Down not Present");
			}

			if (temp == 4) {
				Pass_Message(
						"All the required values are present in Reference System drop down at Customers level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			} else {
				Fail_Message(
						"All the required values are not present in Reference System drop down at Customers level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			}
			Assert.assertEquals(temp, 4);
			homePage.clickCancelEdit();

			ACM_Connectivity.CloseTab();

			homePage.clickAppLauncher();
			homePage.searchItem("Customer Accounts");
			boolean isRedirectedToCustomerAccounts = homePage.isRedirectedToCustomerAccounts();
			if (isRedirectedToCustomerAccounts) {
				Pass_Message("Navigated to Customer Accounts Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to Customer Accounts Screen failed");
			}
			Assert.assertEquals(isRedirectedToCustomerAccounts, true);

			homePage.clickNewButton();
			boolean isRedirectedToNewCustomerAccount = homePage.isRedirectedToNewCustomerAccount();
			if (isRedirectedToNewCustomerAccount) {
				Pass_Message("Navigated to New Customer Account creation Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to New Customer Account creation Screen failed");
			}
			Assert.assertEquals(isRedirectedToNewCustomerAccount, true);

			is_ref_sys_fields = homePage.isReferenceSystemField();
			System.out.println("is_ref_sys_fields: " + is_ref_sys_fields);
			if (is_ref_sys_fields) {
				Pass_Message("Reference System Field Displayed successfully");
			} else {
				Fail_Message("Failed: Reference System Field did no Display");
			}
			Assert.assertEquals(is_ref_sys_fields, true);

			homePage.clcikRefSysDropDown();
			referenceSystem_listValue = homePage.getReferenceSystemList_customer();
			System.out.println("referenceSystem_listValue: " + referenceSystem_listValue);
			temp = 0;
			if (referenceSystem_listValue.contains("None")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'None' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CAM")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CAM' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CDB")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CDB' in Reference System Drop Down not Present");
			}

			if (temp == 3) {
				Pass_Message(
						"All the required values are present in Reference System drop down at Account level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			} else {
				Fail_Message(
						"All the required values are not present in Reference System drop down at Account level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			}
			Assert.assertEquals(temp, 3);
			homePage.clickCancelEdit();
			ACM_Connectivity.CloseTab();

			homePage.clickAppLauncher();
			homePage.searchItem("Contacts");
			boolean isRedirectedToContacts = homePage.isRedirectedToContacts();
			if (isRedirectedToContacts) {
				Pass_Message("Navigated to Contacts Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to Contacts Screen failed");
			}
			Assert.assertEquals(isRedirectedToContacts, true);

			homePage.clickNewButton();
			boolean isRedirectedToNewContact = homePage.isRedirectedToNewContact();
			if (isRedirectedToNewContact) {
				Pass_Message("Navigated to New Contact creation Screen successfully");
			} else {
				Fail_Message("Failed: Navigation to New Contact creation Screen failed");
			}
			Assert.assertEquals(isRedirectedToNewContact, true);

			is_ref_sys_fields = homePage.isReferenceSystemField();
			System.out.println("is_ref_sys_fields: " + is_ref_sys_fields);
			if (is_ref_sys_fields) {
				Pass_Message("Reference System Field Displayed successfully");
			} else {
				Fail_Message("Failed: Reference System Field did no Display");
			}
			Assert.assertEquals(is_ref_sys_fields, true);

			homePage.clcikRefSysDropDown();
			referenceSystem_listValue = homePage.getReferenceSystemList_customer();
			System.out.println("referenceSystem_listValue: " + referenceSystem_listValue);
			temp = 0;
			if (referenceSystem_listValue.contains("None")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'None' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CCD")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CCD' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("ECDC")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'ECDC' in Reference System Drop Down not Present");
			}
			if (referenceSystem_listValue.contains("CDB")) {
				temp++;
			} else {
				Fail_Message("Failed: The Value as 'CDB' in Reference System Drop Down not Present");
			}

			if (temp == 4) {
				Pass_Message(
						"All the required values are present in Reference System drop down at Contact  level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			} else {
				Fail_Message(
						"All the required values are not present in Reference System drop down Contact level. Values in reference system drop down populated as below: "
								+ referenceSystem_listValue);
			}
			Assert.assertEquals(temp, 4);
			homePage.clickCancelEdit();
			// Assert.assertEquals(hasMailReceivedByCCD, true);
			// Thread.sleep(3000);
			// homePage.clickOnLatestCase();
			// Thread.sleep(3000);
			// getDriver().navigate().refresh();
			// Thread.sleep(4000);
			// String contactName = casedetailspage.getCustomerContactName();
			// boolean hasContactCreated = contactName!=null;
			// if (hasContactCreated) {
			// Pass_Message("Contact has created successfully");
			// } else {
			// Fail_Message("Failed: contact did not create");
			// }
			// Assert.assertEquals(hasContactCreated, true);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Auto Contact Creation has failed");
		}
	}

}
