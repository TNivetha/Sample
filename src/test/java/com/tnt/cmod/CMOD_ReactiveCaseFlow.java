package com.tnt.cmod;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.tnt.ccd.CCD_CMOD_SmokeTest;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;
//Reactive Case Flow
public class CMOD_ReactiveCaseFlow extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CMOD_FF_Reusable cmodFFReusable=new CMOD_FF_Reusable();
	@BeforeSuite (alwaysRun = true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@BeforeClass (alwaysRun = true)
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys == null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeClass (alwaysRun = true)
	public void login() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[8]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[8]);
		cmodFFReusable.csr_Login(Username, Password);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}
	@BeforeMethod (alwaysRun = true)
	public void Before_method(Method method) throws InterruptedException {
		Test_Initializer.Before_Method(method);
		startTime = 0;
		startTime = System.currentTimeMillis();
	}
	@AfterMethod (alwaysRun = true)
	public void After_Method(ITestResult result) {
		elapsedTime = 0;
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}
	// Create a consignment from India (22875) to UK (78867)
	
	@Test (groups = { "reactiveCaseFlow", "createCaseByFrontlineCSR" })
	public void CreateCaseByFrontlineCSR() {
		cmodFFReusable.CreateCaseByFrontlineCSR();
	}
	@Test (groups = { "reactiveCaseFlow", "omniChannelCaseAccept" })
	public void OminiChannelCaseAccept() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			CCD_Connectivity Connectivity = new CCD_Connectivity();		
			Support.ominiChannelAccept();
			//Connectivity.CloseTab();
			softAssertion.assertAll();
		}
		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Omini Channel Case Accept failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "caseDtlsValidations" })
	public void Casedetails_Validations() {
		try {
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			FF_EBS.casedetails_caseebs();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case Validation failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "callBack" })
	public void Callback_activity() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.callback_activity("re_case");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Call Back Activity service failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "monitoring" })
	public void Monitoring_Activity() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.MonitoringActivity("re_case");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Monitoring Activity service failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "task" })
	public void Task() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			/*
			 * driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
			 * Support.Task_Verify_Task_fields();
			 * driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
			 * Support.Task_PastDate_Deadline();
			 * driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
			 * Support.Task_BlankFiledErrorValidation();
			 * driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
			 */
			String Retrive = Database_Connection.retrieveTestData("CONNORT","ACM","KEY",CMOD_ReactiveCaseFlow.Key_Array[7]);
			Support.task_create(Retrive);
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Task test cases failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "taskEscalation", "byCO" })
	public void TaskEscalation() {
		cmodFFReusable.task_escalation();
		softAssertion.assertAll();
	}
	@Test (groups = { "reactiveCaseFlow", "taskEscalation", "byOIB" })
	public void TaskEscalation_OIB() {
		cmodFFReusable.task_escalation_OIB();
		softAssertion.assertAll();
	}
	@Test (groups = { "reactiveCaseFlow", "taskEscalation", "esclationSLA" })
	public void TaskEscalationSLA() {
		cmodFFReusable.task_escalation_OIB();
		softAssertion.assertAll();
	}
	@Test (groups = { "reactiveCaseFlow", "actions" })
	public void Actions() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.action("re_case");
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Action details verification failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "custRemarks" })
	public void Customer_remark() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.customer_remark("re_case");
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer Remarks details verification failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "caseRemarks" })
	public void Case_remark() {
		try {
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.Case_remark("re_case");
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case Remarks details verification failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "email" })
	public void Email() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			FF_EBS.Email_EBS("re");
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email verification failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "rfi" })
	public void RFI() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			FF_EBS.RFI_EBS();
			Support.internal_tabclose();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("RFI details verification failed");
		}
	}
	@Test (groups = { "reactiveCaseFlow", "closeCase" })
	public void CloseCase() {
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.CloseCase("re_case");
			Support.logout();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case cannot be closed");
		}
	}
	@Test(priority = 15, enabled = false)
	public void validateCaseUpdateColumnVisible() {
		try {
			CCD_Connectivity connection = new CCD_Connectivity();
			CMOD_FF_Reusable frontline = new CMOD_FF_Reusable();
			frontline.frontline_Login();
			frontline.validateCaseUpdateColumn();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case cannot be closed");
		}
	}
	@Test(priority = 16, enabled = false)
	public void validateCaseUpdateColumnNotVisible() {
		try {
			CMOD_FF_Reusable frontline = new CMOD_FF_Reusable();
			//frontline.frontline_Login();
			frontline.US_B513632_TC2_validateCaseUpdateColumnNotVisible();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Pass_Message("Case Update column is not visible");
		}
	}
	@Test (priority = 17, enabled = false)
	public void validateCaseUpdateColumnInMyOpenCasesTable() {
		try {
			CMOD_FF_Reusable frontline = new CMOD_FF_Reusable();
			frontline.frontline_Login();
		} catch (Exception e) {
			e.printStackTrace();
			Pass_Message("Case Update column shows a message and is validated");
		}
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void searchClosedUnallocatedCaseAndReOpen() {
		//CMOD Automation 4.2 Iteration Test Cases - TC1 & TC4 (both cover same steps)
		String unAllocatedCaseID = "20126778";
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(unAllocatedCaseID);
		cmodFFReusable.applyReOpenCaseActions();
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void searchClosedReactiveCaseAndReOpen() {
		//CMOD Automation 4.2 Iteration Test Cases - TC2
		String reActiveCaseID = "20125087";
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(reActiveCaseID);
		cmodFFReusable.applyReOpenCaseActions();
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void searchProActiveCaseAndReOpen() {
		//CMOD Automation 4.2 Iteration Test Cases - TC3
		String proActiveCaseID = "20125030";
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(proActiveCaseID);
		cmodFFReusable.applyReOpenCaseActions();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.waitForSuccessMsg();
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void reOpenReactiveCaseAndValidatePreviousTaskWontOpen() {
		//CMOD Automation 4.2 Iteration Test Cases - TC5
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		String proActiveCaseID = "20125030";
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(proActiveCaseID);
		cmodFFReusable.applyReOpenCaseActions();
		ccPage.waitForSuccessMsg();
		ccPage.clickTaskQuickLink();
		cmodFFReusable.validateCaseHistoryStatusChanges();
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void reOpenReactiveCaseAndCreateTask() {
		//CMOD Automation 4.2 Iteration Test Cases - TC6 & TC10 (both cover same steps)
		searchClosedReactiveCaseAndReOpen();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.waitForSuccessMsg();//add a wait for success message in here 
		UiTestHelper uiTestHelper = new UiTestHelper();
		cmodFFReusable.applyTaskTabActions("United Kingdom", "AT6", "TNT-CS");
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void reOpenCaseCreateTaskAndValidateNoCaseHistory() {
		//CMOD Automation 4.2 Iteration Test Cases - TC7
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		String closedCaseID = "20125094";
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(closedCaseID);
		cmodFFReusable.applyReOpenCaseActions();
		ccPage.waitForSuccessMsg();
		UiTestHelper uiTestHelper = new UiTestHelper();
		cmodFFReusable.applyTaskTabActions("United Kingdom", "AT6", "TNT-CS");
		ccPage.waitForSuccessMsg();
		ccPage.clickCaseHistoryQuickLink();
		cmodFFReusable.validateCaseHistoryStatusChanges();
	}
	@Test(groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void reOpenCaseAndAssignToOwnerWhoReopensIt() {
		//CMOD Automation 4.2 Iteration Test Cases - TC 8 
		searchProActiveCaseAndReOpen();
		cmodFFReusable.validateCaseOwnerOnCasePage("Automation Tester2");	
	}
	@Test (groups = {"CMODFunctionalFlows", "Case Reopening" })
	public void searchClosedCaseAndReOpenWithNewOwner() {
		//CMOD Automation 4.2 Iteration Test Cases - TC9
		String previousCaseID = "20125052";
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(previousCaseID);
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickChangeOnwerButton();
		ccPage.setNewCaseOwner();
		ccPage.clickCaseOwnerSubmitButton();
		ccPage.waitForSuccessMsg();
		cmodFFReusable.applyReOpenCaseActions();
		ccPage.waitForSuccessMsg();
		cmodFFReusable.validateCaseOwnerOnCasePage("Ben Grimstead");
	}
	@Test (groups = { "CMODFunctionalFlows", "Case Management" })
	public void createReactivecaseAndUpdateConsignmentStatus() {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickTrackAndTraceCCD();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		String consignmentNumber = "324661116"; 
		ttPage.setFirstConsignmentNumberBox(consignmentNumber);
		ttPage.clickTrackandTraceSearch();
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.CCD_CreateCase();
		cmodFFReusable.validateConsignmentStatusDescriptionField();
	}
	@Test (groups = { "CMODFunctionalFlows", "Case Management" })
	public void createProactiveCaseAndUpdateConsignmentStatus() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.CCD_CreateCase();
		//		CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
		//		FF_EBS.CreateCase_EBS();
		// proactive case flow is down at the moment cannot work on this
	}
	@Test (groups = { "CMODFunctionalFlows", "Case Management" })
	public void createUnallocatedCaseAndUpdateConsignmentStatus() {
		//B-592490 Test Cases - TC3
		
		CMOD_TrackAndTrace_Reusable	cmodTTReusable = new CMOD_TrackAndTrace_Reusable(getDriver());
		cmodTTReusable.reusableCreateUnalloctaedCase();
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.validateConsignmentStatusDescriptionField();
	}
	@Test (groups = {"CMODFunctionalFlows", "Merged Actions", "Close Case Actions" })
	public void searchOpenCaseAndCloseIt() {
		//B-703296 Test Cases - TC1
		//US B-466621 Test Cases - TC2
		String openCaseID = "20125030";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		cmodFFReusable.applyCloseCaseActions();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.waitForSuccessMsg();
		cmodFFReusable.validateCaseIsManualClosedInCaseInformation();
		cmodFFReusable.beforeNextTestReOpenCaseAfterClosing(openCaseID);
	}
	@Test (groups = { "CMODFunctionalFlows", "Merged Actions" })
	public void closeCaseFromMergedActionsTabAndValidate() {
		//B-703296 Test Cases - TC2
		String openCaseID = "20122658";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		cmodFFReusable.applyMergedActionsTab();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.waitForSuccessMsg();
		cmodFFReusable.validateCaseIsManualClosedInCaseInformation();
		cmodFFReusable.beforeNextTestReOpenCaseAfterClosing(openCaseID);
	}
	@Test (groups = { "CMODFunctionalFlows", "Merged Actions", "Close Case Actions" })
	public void viewMyOpenCasesClickMergeActionsCloseCase() {
		//B-703296 Test Cases - TC3
		//US B-466621 Test Cases - TC6 - Merged TC 
		//add automation for the re-opening of a closed case after process is done 
		String openCaseID = "20122658";
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		//click cases page
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		// on cases page select dropdown and choose my open cases
		cmodFFReusable.clickCasesMenuAndSelectMyOpenCases();
		//search record in search box
		cmodFFReusable.searchCasePageTableAndSelectRecord(openCaseID);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver()); 
		//when the single row has loaded for case, tick the box and press merged actions
		caseDetailsPage.selectCaseTickBoxInTable(openCaseID);
		caseDetailsPage.clickMergedActionButton();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		//method will switchto iframe / click close case / and click save 
		ccPage.switchToIFrameAndClickClosedActionsAndValidate();
		ccPage.waitForSuccessMsg();
		cmodFFReusable.validateCaseIsManualClosedInCaseInformation();
		cmodFFReusable.beforeNextTestReOpenCaseAfterClosing(openCaseID);
	}
	@Test (groups = { "CMODFunctionalFlows","Close Case Actions" })
	public void closeCaseAndValidateIssueLocationTabExists() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.closeCaseAndValidateIssueLocationTabExists();
	}
	@Test (groups = { "CMODFunctionalFlows", "Close Case Actions" })
	public void closeCaseThenAddIssueLocationAndVerify() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.closeCaseThenAddIssueLocationAndVerify();
	}
	@Test (groups = { "CMODFunctionalFlows", "Close Case Actions" })
	public void closeCaseThenAddIssueLocationOwnerShipRootCauseAndVerify() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.closeCaseThenAddIssueLocationOwnerShipRootCauseAndVerify();
	}
	@Test (groups = { "CMODFunctionalFlows", "Close Case Actions" })
	public void clickMergeActionsCloseCaseVerifyIssueLocationExistsAndValidate() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.clickMergeActionsCloseCaseVerifyIssueLocationExistsAndValidate();
	}
	@Test (groups = { "CMODFunctionalFlows","Close Case Actions" })
	public void clickMergedActionsOnCasePageSelectThreeDropDownsAndValidateUpdates() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.clickMergedActionsOnCasePageSelectThreeDropDownsAndValidateUpdates();
	}
	@Test (groups = { "CMODFunctionalFlows", "Close Case Actions" })
	public void clickCasesListViewMergedActionsThenCloseCase() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.clickCasesListViewMergedActionsThenCloseCase();
	}
	@Test (groups = { "CMODFunctionalFlows", "Close Case Actions" })
	public void listViewMergedActionsSelectThreeDropDownsAndValidateUpdates() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.listViewMergedActionsSelectThreeDropDownsAndValidateUpdates();
	}
	//Sprint PI2 2.5 IP starts here 
	//B-558404 TCs #1
	@Test (groups = {"Sprint PI2 2.5 IP", "B-558404", "CMODFunctionalFlows", "Reactive Case Actions" })
	public void createReactiveCaseAndValidateOIBCaseSubject() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.createReactiveCaseAndValidateOIBCaseSubject();
	}//B-558404 TCs #3
	@Test (groups = { "Sprint PI2 2.5 IP", "B-558404","CMODFunctionalFlows", "Reactive Case Actions" })
	public void createReactiveCaseAndAddOIBMember() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.createReactiveCaseAndAddOIBMember();
	}//B-558404 TCs#5
	@Test (groups = {"Sprint PI2 2.5 IP", "B-558404", "CMODFunctionalFlows", "Reactive Case Actions" })
	public void createReactiveCaseAndOIBMemberLogsIn() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.secondOIBMemberLoginAndValidateCaseSubject();
	}
	//Sprint PI2 2.5 IP ends here 
	
	//Sprint PI 3.5 starts here 
	//B-807301 TCs #1, #2, #4 - condensed into 1 
	@Test (groups = {"Sprint PI2 3.5", "B-807301", "CMODFunctionalFlows", "Reactive Case CMOD Actions" })
	public void reopenClosedReactiveCaseVerifyCmodId() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.reopenClosedReactiveCaseVerifyCmodId();
	}
	//Sprint PI 3.5 ends here 
	
	@Test (groups = {"ReactiveCaseRegressionFlow"})
	public void reactiveCaseFlowRegression()  {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.reactiveCaseFlowRegression();
	}
	
	@AfterSuite (alwaysRun = true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
