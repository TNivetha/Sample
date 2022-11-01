package com.tnt.cmod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

import util.TestUtil;

//Reactive Case Flow
public class CMOD_InSprintTestExecution extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
	CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();

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
		if ((!Keys.isEmpty()) || (!(Keys == null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}

	@BeforeClass(alwaysRun = true)
	public void login() {

		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.support_Login();
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) throws InterruptedException {
		Test_Initializer.Before_Method(method);
		startTime = 0;
		startTime = System.currentTimeMillis();
	}

	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		elapsedTime = 0;
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		extent.flush();
	}

	/*
	 * EU-POLARIS-CY21-3.5 B-432224 [Business] RFI - Redesign to Tasks - Create and
	 * Send tasks CY21-PI3 TC01, TC02, TC03, TC08, TC09 covered in B-1087053 Scripts
	 * for TC04,05,06 and 07 is written below
	 */
	@Test(groups = { "sprint_PI3.4", "B784291_1", "TASK", "TNT-CS", })
	public void us_B432224_TaskCreation_With_CaseLocation_Orgin_and_Task_Origin() {
		try {
			CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
			String caseLocation = "Origin";
			String orgOrDest = "Origin";
			String consignmentNo = "324682153";// O-to-P consignment
//			cmodFFReusable.taskCreation_basedOnCaseLocation_and_TaskOriginOrDestination(consignmentNo, caseLocation,
//					orgOrDest);
			cmodFFReusable.validateOIBSupportQueue(caseLocation);
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI3.4", "B784291", "TASK", "TNT-CS" })
	public void us_B432224_TaskCreation_With_CaseLocation_Orgin_and_Task_Destination() {
		try {
			CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
			String caseLocation = "Origin";
			String orgOrDest = "Destination";
			String consignmentNo = "324682153";// O-to-P consignment
			cmodFFReusable.taskCreation_basedOnCaseLocation_and_TaskOriginOrDestination(consignmentNo, caseLocation,
					orgOrDest);
			cmodFFReusable.validateOIBSupportQueue(caseLocation);
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI3.4", "B784291", "TASK", "TNT-CS" })
	public void us_B432224_TaskCreation_With_CaseLocation_Destination_and_Task_Origin() {
		try {
			CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
			String caseLocation = "Destination";
			String orgOrDest = "Origin";
			String consignmentNo = "324682153";// O-to-P consignment
			cmodFFReusable.taskCreation_basedOnCaseLocation_and_TaskOriginOrDestination(consignmentNo, caseLocation,
					orgOrDest);
			cmodFFReusable.validateOIBSupportQueue(caseLocation);
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI3.4", "B784291", "TASK", "TNT-CS" })
	public void us_B432224_TaskCreation_With_CaseLocation_Destination_and_Task_Destination() {
		try {
			CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
			String caseLocation = "Destination";
			String orgOrDest = "Destination";
			String consignmentNo = "324682153";// O-to-P consignment
			cmodFFReusable.taskCreation_basedOnCaseLocation_and_TaskOriginOrDestination(consignmentNo, caseLocation,
					orgOrDest);
			cmodFFReusable.validateOIBSupportQueue(caseLocation);
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// Sprint_4.2 start from here
	@Test(groups = { "sprint_PI4.2", "B_852245_CaseUpdate", "byOIB_email", "clearCancelPoUp" })
	// Test Data source: CMOD, CONNORT
	public void us_B852245_TC1_ClearCaseUpdate_Email() {
		try {
			reusable.us_B852245_CaseUpdate_ClearAndCancelEmailPopUpValidations();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.2", "B_780851_TaskEscalation", "levelDisplay" })
	// Test Data source: CMOD, CONNORT
	public void us_B780851_TC1_taskEscalationLevelDisplay() {
		try {
			// reusable.task_create();
			reusable.taskEscalationLevelDisplay();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.2", "B_780851_TaskEscalation", "forDiffLocErrorMsg" })
	// Test Data source: CMOD, CONNORT
	public void us_B780851_TC2_taskEscalation_forDiffLocErrorMsgValidation() {
		try {
			reusable.taskEscalation_forDiffLocErrorMsg();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.2", "B_780851_TaskEscalation", "slaDateTimeDisplay" })
	// Test Data source: CMOD, CONNORT
	public void us_B780851_TC3_taskEscalation_SLADateTimeDisplay() {
		try {
			reusable.taskEscalationSLADateTimeDisplay();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// TODO - TC is blocked as changes have been reverted from SIT
	@Test(groups = { "sprint_PI4.2", "B_780851_TaskEscalation", "changeCouAndLocValidation" })
	// Test Data source: CMOD, CONNORT
	public void us_B780851_TC4_taskEscalation_ChangeCountryAndLocationValidation() {
		try {
			reusable.taskEscalation_ChangeCountryAndLocationValidation();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.2", "B_780851_TaskEscalation", "escByOIB" })
	// Test Data source: CMOD, CONNORT
	public void us_B780851_TC5_taskEscalation_ByOIB() {
		try {
			reusable.task_escalation_OIB();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}
	// Sprint 4.2 ends here
	// Sprint 4.3 starts from here

	// Sprint 3.4 starts
	// B-409569 TCs (blocked due to manual intervention needed to add new case to
	// DBA everytime code is ran)
	@Test(groups = { "sprint_PI3.4", "B-409569" })
	public void selectUKGhDedicatedListViewAndValidate() {
		try {
			cmodCaseActions.reusableSelectCasePageAndUKGhDedicatedView();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// Sprint 3.4 ends
	/*
	 * EU-POLARIS-CY21-3.3: B-760606- [Business] Priority Indicator in Cases - Case
	 * Creation EU-POLARIS-CY21-3.5: B-716112- [Business] Priority Indicator in
	 * Cases - Indicator Development (Update)
	 */
	@Test(groups = { "sprint_PI3.3", "sprint_PI3.5", "B-716112", "B-760606" })
	public void us_B716112_TC01_03_04_PiorityIndicatorInCase_CaseCreation() {
		CMOD_FunctionalFlow_Updated functionalflow = new CMOD_FunctionalFlow_Updated();
		String casetype, cause, reason, preiorityindicator, caseLocation;
		boolean assignToMe;
		String consignmentNo = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		consignmentNo = "324682153";
		caseLocation = "Origin";
		casetype = "Re-Active Case";
		reason = "Undeliverable";
		cause = "Insufficient Address";
		preiorityindicator = "Personal Data";
		assignToMe = true;

		functionalflow.b716112_TC01_PiorityIndicatorInCases(consignmentNo, caseLocation, casetype, reason, cause,
				preiorityindicator, assignToMe);
	}

	@Test(groups = { "sprint_PI3.3", "sprint_PI3.5", "B-716112", "B-760606" })
	public void us_B716112_TC02_PiorityIndicatorInCases_PriorityIndicatorList() {
		CMOD_FunctionalFlow_Updated functionalflow = new CMOD_FunctionalFlow_Updated();
		String casetype, cause, reason, preiorityindicator;
		String consignmentNo = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		consignmentNo = "324682153";
		casetype = "Re-Active Case";
		reason = "Undeliverable";
		cause = "Insufficient Address";
		preiorityindicator = "Personal Data";
		functionalflow.b716112_TC02_PiorityIndicatorListVerification(consignmentNo, casetype, reason, cause,
				preiorityindicator);

	}

	@Test(groups = { "sprint_PI4.3", "B_860466_CaseUpdate", "emailClearInOIB" })
	// Test Data source: CMOD, CONNORT
	public void us_B860466_CaseUpdate_ClearEmailModelPopUpinOIB() {
		try {
			reusable.us_B860466_CaseUpdate_ClearEmailModelPopUpinOIB();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.3", "B_869638_AutoCloseAndReopenCase", "autoCloseReactiveCase" })
	public void us_B869638_AutoCloseReactive() {
		try {
			reusable.applyStatusCodeFromAdmin();
			// reusable.us_B869638_AutoCloseReactive();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@Test(groups = { "sprint_PI4.3", "B_869638_AutoCloseAndReopenCase", "outstandingSLA" })
	public void us_B869638_AutoCloseReactive_outstandingSLA() {
		try {
			reusable.us_B869638_AutoCloseReactive_OutstandingSLA();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// @Prachi - this is the complex test case I have recently been working on
	@Test(groups = { "sprint_PI3.2", "B_700565_Reports", })
	public void createAReportAndValidateAccountFieldID() {
		try {
			reusable.accessReportsAndCreateNewReportWithOptions();
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// Sprint 5.1
	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "ValidateCaseDetails" })
	public void us_B885303_ClearanceCase_Reactive_CreateAndValidateCaseDetails() {
		// TC 7,8,9
		reusable.us_B885303_ClearanceCase_Reactive_CreateAndValidateCaseDetails();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "validate_OIBMemberName" })
	public void us_B885303_ClearanceCase_Reactive_ValidateOIBMemberName() {
		// TC 10,11
		reusable.us_B885303_ClearanceCase_Reactive_ValidateOIBMemberName();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "ValidateCaseDetails_3rdPartyLoc" })
	public void us_B885303_ClearanceCase_Reactive_3rdPartyLocation_CreateAndValidateCaseDetails() {
		// TC 12,13,14
		reusable.us_B885303_ClearanceCase_Reactive_3rdPartyLocation_CreateAndValidateCaseDetails();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "changeCaseOwnerValidation" })
	public void us_B885303_ClearanceCase_Reactive_ChangeOwnerValidation() {
		// TC 15
		reusable.us_B885303_ClearanceCase_Reactive_ChangeOwnerValidation();
		softAssertion.assertAll();
	}

	// Tc 17
	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "inbound_ValidateCaseDetails" })
	public void us_B885303_ClearanceCase_Reactive_Inbound_ValidateCaseDetails() {
		// TC 17
		reusable.us_B885303_ClearanceCase_Reactive_Inbound_ValidateCaseDetails();
		softAssertion.assertAll();
	}

	// Tc 18
	@Test(groups = { "sprint_PI5.1", "B885303_ClearanceCase_Reactive", "inbound_ChangeCaseOwner" })
	public void us_B885303_ClearanceCase_Reactive_Inbound_changeCaseOwner() {
		reusable.us_B885303_ClearanceCase_Reactive_Inbound_changeCaseOwner();
		softAssertion.assertAll();
	}

	// Tc 1
	@Test(groups = { "sprint_PI5.1", "B905245_TaskEscalation", "taskEscalation" })
	public void us_B905245_TaskEscalation() {
		reusable.us_B905245_TaskEscalation();
		softAssertion.assertAll();
	}

	// Sprint 5.1 TC 20
	@Test(groups = { "sprint_PI5.1", "B905245_cmodInboundTaskValidation", "cmodInboundTask_AutomatedProcessVal" })
	public void us_B905245_cmodInboundTask_AutomatedProcessVal() {
		reusable.us_B905245_cmodInboundTask_AutomatedProcessVal();
		softAssertion.assertAll();
	}

	// Sprint 5.1 TC 22
	@Test(groups = { "sprint_PI5.1", "B905245_cmodInboundTaskValidation", "cmodCaseChangeOwnerErrorValidation" })
	public void us_B905245_cmodInboundClearanceCase_ChangeOwnerErrorValidation() {
		reusable.us_B905245_cmodInboundClearanceCase_ChangeOwnerErrorValidation();
		softAssertion.assertAll();
	}

	// Sprint 5.1 TC 23
	@Test(groups = { "sprint_PI5.1", "B905245_cmodInboundTaskValidation", "ccdCaseChangeOwnerErrorValidation" })
	public void us_B905245_CCDClearanceCase_ChangeOwnerErrorValidation() {
		reusable.us_B905245_cmodInboundClearanceCase_ChangeOwnerErrorValidation();
		softAssertion.assertAll();
	}

	// Sprint 5.1 TC 4
	@Test(groups = { "sprint_PI5.1", "B933496_ClareanceCasetaskEscalation", "TaskEsc_EmailValidation" })
	public void us_B933496_ClareanceCasetaskEscalation_EmailValidation() {
		reusable.us_B933496_ClareanceCasetaskEscalation_EmailValidation();
		softAssertion.assertAll();
	}

	// Sprint 5.2 TC1,6
	@Test(groups = { "sprint_PI5.2", "us_B894974_CreateUnallocatedCase", "createUnallocatedCase" })
	public void us_B894974_CreateUnallocatedCase() {
		reusable.us_B894974_CreateUnallocatedCase();
		softAssertion.assertAll();
	}

	// TC 2
	@Test(groups = { "sprint_PI5.2", "us_B894974_CreateUnallocatedCase", "validateAutomatedProcess" })
	public void us_B894974_CreateUnallocatedCase_ValidateAutomatedProcess() {
		reusable.us_B894974_CreateUnallocatedCase_ValidateAutomatedProcess();
		softAssertion.assertAll();
	}

	// TC 1 CMOD task escalation
	@Test(groups = { "sprint_PI5.2", "B912560_CMODChangingTaskOwnership", "validateCMODTaskEscSLAField" })
	public void us_B912560_CMODChangingTaskOwnership_validateEscalationSLAField() {
		reusable.us_B912560_CMODChangingTaskOwnership_validateEscalationSLAField();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.2", "B912560_CMODChangingTaskOwnership", "validateAutomatedProcessInTask" })
	public void us_B912560_CMODChangingTaskOwnership_validateAutomatedProcess() {
		reusable.us_B912560_CMODChangingTaskOwnership_validateAutomatedProcess();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.2", "B876151_StatusCodesinAutoPNFunctionality", "validateNewlyAddedCodes" })
	public void us_B876151_StatusCodesinAutoPNFunctionality_validateNewlyAddedCodes() {
		reusable.us_B876151_StatusCodesinAutoPNFunctionality_validateNewlyAddedCodes();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.4", "B935469_ECDT_EmailToCaseQueue", "Cases" })
	public void us_B935469_ECDT_EmailToCaseQueue() {
		reusable.us_B935469_ECDT_EmailToCaseQueue();
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.4", "B814290_ECDT_ProactveExceptionQueue", "ProactiveExceptions" })
	public void us_B814290_ECDT_ProactveExceptionQueue() {
		reusable.us_B814290_ECDT_ProactveExceptionQueue();
	}

	@DataProvider
	public Iterator<Object[]> getData() {
		ArrayList<Object[]> testdata = TestUtil.getTestDataSLA();
		return testdata.iterator();

	}

	/*
	 * EU-POLARIS-CY21-5.4 B-1030147- [Business] Task SLA Changes CCD><CMOD &
	 * Validation
	 */
	// reactive case with priority indicator
	@Test(dataProvider = "getData", groups = { "test", "sprint_PI5.4", "B1030147_ReactiveCaseWithPriorityIndicator",
			"ReactiveCase" })
	public void usB1030147_ReactiveCaseWithPriorityIndicator(String reason, String cause, String consignmentNo)
			throws Exception {
		reusable.us_B1030147_ReactiveCaseCreationPriorityIndicator("324682024", "Origin", "Re-Active Case", reason,
				cause, "Personal Data", true);
		softAssertion.assertAll();
	}

	// reactive case without priority indicator
	@Test(dataProvider = "getData", groups = { "sprint_PI5.4", "B1030147_ReactiveCaseWithoutPriorityIndicator",
			"ReactiveCase" })
	public void usB1030147_ReactiveCaseWithoutPriorityIndicator(String reason, String cause, String consignmentNo)
			throws Exception {
		// reusable.us_B1030147_ReactiveCaseWithoutPriorityIndicator();
		reusable.us_B1030147_ReactiveCaseCreationPriorityIndicator(consignmentNo, "Origin", "Re-Active Case",
				"Undeliverable", "Insufficient Address", "", true);
		softAssertion.assertAll();
	}

	// pro-active case with priority indicator
	@Test(dataProvider = "getData", groups = { "sprint_PI5.4", "B1030147_ProActiveCaseWithPriorityIndicator",
			"ProActiveCase" })
	public void usB1030147_ProActiveCaseWithPriorityIndicator(String reason, String cause, String consignmentNo)
			throws Exception {
		// reusable.us_B1030147_ProActiveCaseWithPriorityIndicator();
		reusable.us_B1030147_ReactiveCaseCreationPriorityIndicator("638262170", "origin", "Pro-Active Case",
				"Undeliverable", "Insufficient Address", "Personal Data", true);
		softAssertion.assertAll();
	}

	// pro-active case without priority indicator
	@Test(dataProvider = "getData", groups = { "sprint_PI5.4", "B1030147_ProActiveCaseWithoutPriorityIndicator",
			"ProActiveCase" })
	public void usB1030147_ProActiveCaseWithoutPriorityIndicator(String reason, String cause, String consignmentNo)
			throws Exception {
		// reusable.us_B1030147_ProActiveCaseWithoutPriorityIndicator();
		reusable.us_B1030147_ReactiveCaseCreationPriorityIndicator(consignmentNo, "Origin", "Pro-Active Case",
				"Undeliverable", "Insufficient Address", "", true);
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.4", "B1030147_PackageResearchCaseWithoutPriorityIndicator", "PackageResearchCase" })
	public void usB1030147_PackageResearchCaseWithoutPriorityIndicator() {
		reusable.us_B1030147_PackageResearchCaseWithoutPriorityIndicator();
	}

	@Test(groups = { "sprint_PI5.4", "B1030147_PackageResearchCaseWithPriorityIndicator", "PackageResearchCase" })
	public void usB1030147_PackageResearchCaseWithPriorityIndicator() {
		reusable.us_B1030147_PackageResearchCaseWithPriorityIndicator();
	}

	@Test(groups = { "sprint_PI5.4", "B1030147_ServiceChangeWithPriorityIndicator", "ServiceChangeCase" })
	public void usB1030147_ServiceChangeWithPriorityIndicator() {
		reusable.us_B1030147_ServiceChangeWithPriorityIndicator();
	}

	@Test(groups = { "sprint_PI5.4", "B1030147_RecipientInformationNeededWithPriorityIndicator",
			"RecipientInformationNeededCase" })
	public void usB1030147_RecipientInformationNeededWithPriorityIndicator() {
		reusable.us_B1030147_RecipientInformationNeededWithPriorityIndicator();
	}

	/*
	 * EU-POLARIS-CY22- 1.1 starts here B-863446- [Business] Task Escalation
	 * Catalogue
	 */

	/*
	 * EU-POLARIS-CY22- 1.2 B-1030253- [Business] Sending Tasks to any location
	 * 'FedEx Other' *Stretch*
	 */

	@Test(groups = { "sprint_PI5.4", "B1030253", "TaskEscalationSLA", "ReactiveCase" })
	public void us_B1030253_TaskCreationWithFedExOther() {
		String casetype, cause, reason, preiorityindicator, caseLocation;
		boolean assignToMe;
		String consignmentNo = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		consignmentNo = "324682153";
		caseLocation = "Origin";
		casetype = "Re-Active Case";
		reason = "Undeliverable";
		cause = "Insufficient Address";
		preiorityindicator = "Personal Data";
		assignToMe = true;
		reusable.us_B1030253_TaskCreationWithFedExOther(consignmentNo, caseLocation, casetype, reason, cause,
				preiorityindicator, assignToMe);
		softAssertion.assertAll();
	}

	@Test(groups = { "sprint_PI5.4", "B1030253", "TaskEscalationSLA", "ReactiveCase" })
	public void us_B1030253_TC01_to_TC04_TaskCreationWithFedExOther() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String orgOrDest = "Other";
		String country = "Norway";
		String taskLocation = "GDR";
		String consignmentNo = "309869254";
		String routto = "FedEx-OPS";
		cmodFFReusable.taskCreationAndEscaltion_FedEx_Others_or_Overgoods(consignmentNo, routto, orgOrDest, country,
				taskLocation);
		softAssertion.assertAll();
	}

	/*
	 * B-1087053- [Business] TNT-CS Tasks - SLAs and Functionality B-799086-
	 * [Business] Task Escalation within ACM (CS to CS location) TC01 to TC04
	 */
	@Test(groups = { "test", "sprint_PI1.2", "B-1087053", "Task_SLA_Escalation", "ReactiveCase" })
	public void usB1087053_TaskCreationAndEscalation_TNT_CS() {
		String consignmentNo = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		consignmentNo = "324682153";
		reusable.task_create_TNT_CS(consignmentNo);
		// level 1 to 4 escalation
		reusable.taskEscalationFromL1toL4();
		softAssertion.assertAll();
	}

	@Test(groups = { "test", "sprint_PI1.2", "B-799086", "Task_SLA_Escalation", "ReactiveCase" })
	public void usB799086_TC05_TaskCreationAndEscalation_TNT_CS_OIB_user() {
		String consignmentNo = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		consignmentNo = "324682153";
		reusable.task_create_TNT_CS(consignmentNo);
		reusable.task_escalation_OIB();
		softAssertion.assertAll();
	}

	/*
	 * EU-POLARIS-CY22- 1.2 B-1059204- [Business] CCD Task SLA to Update Monitoring
	 * Activity
	 */
	@Test(groups = { "sprint_PI5.4", "B1059204", "TaskEscalationSLA", "MonitorActivitySLA", "ReactiveCase" })
	public void us_B1059204_TC01_to_TC07_TaskCreationAndMonitorActivitySLATime() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String orgOrDest = "Origin";
		String country = "Norway";
		String taskLocation = "GDR";
		String consignmentNo = "324682153";
		String routto = "TNT-OPS";
		String instructions = "TEST";
		cmodFFReusable.taskCreationAndEscaltion_and_MonitorActivitySLA(consignmentNo, routto, orgOrDest, country,
				taskLocation, instructions);
		softAssertion.assertAll();
	}

	/*
	 * EU-POLARIS-CY22- 1.3 B-1057257- [Business] Send Tasks to Overgoods Department
	 */
	@Test(groups = { "sprint_PI5.4", "B1057257", "TaskEscalationSLA", "Overgoods", "ReactiveCase" })
	public void us_B1057257_TC01_03_04_05_06_07_08_TaskCreationWithFedExOvergoods() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String orgOrDest = "Overgoods";
		String country = "Canada";
		String taskLocation = "Overgoods - Canada";
		String consignmentNo = "309869254";
		String routto = "FedEx-OPS";
		cmodFFReusable.taskCreationAndEscaltion_FedEx_Others_or_Overgoods(consignmentNo, routto, orgOrDest, country,
				taskLocation);
		softAssertion.assertAll();
	}

	/*
	 * B-1057257: TC02 validation of Overgoods countries and locations
	 */
	@DataProvider
	public Iterator<String[]> getDataTaskEscalatioFedExOvergoods() {
		ArrayList<String[]> testdata = TestUtil.getTestDataSLAFedExOvergoods();
		return testdata.iterator();

	}

	@Test(dataProvider = "getDataTaskEscalatioFedExOvergoods", groups = { "sprint_PI5.4", "B1057257",
			"TaskEscalationSLA", "ReactiveCase" })
	public void us_B1057257_TC06_to_TC08_TaskCreationWithFedExOvergoods(String country, String taskLocation,
			String assigntoLocation) {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String consignmentNo = "309869254";
		String routto = "FedEx-OPS";
		String orgOrDest = "Overgoods";
		// country = "United States";
		// taskLocation = "PR&R Match requests";
		cmodFFReusable.taskCreationAndEscaltion_FedEx_Overgoods(consignmentNo, routto, orgOrDest, country, taskLocation,
				assigntoLocation);
		softAssertion.assertAll();
	}

	/*
	 * EU-POLARIS-CY22-1.5 B-1033260- [Business] Creating CCD Cases in bulk - UI
	 * Changes
	 */
	@Test(groups = { "sprint_CY22PI1.5", "B1033260", "TaskEscalationSLA", "ReactiveCase" })
	public void usB1033260_CreatingCCD_CasesInBulk_UI_Changes() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.b1033260_ccd_cases_in_bulk_option_on_UI_validation();

	}

	/*
	 * EU-POLARIS-CY21- 5.5 starts here B-912364- [Business]CCD - Auto contact
	 * creation for new contacts //This is not working as per discussed with
	 * Veera-reported to dev team
	 * 
	 * on HOLD
	 */

	/* B-1041152_[Business]CCD_Simplify_case_creation_on_contact(Part1) */
	@Test(groups = { "sprint_PI5.5", "B-1041152", "caseCreationOnContacts_1" })
	public void usB1041152__CCD_Simplify_case_creation_on_contact() {
		String contactname = "Shubham Nagar";
		cmodCaseActions.us_B1041152_CCD_Simplify_case_creation_on_contact_part1(contactname);
	}

	/*
	 * B-1039338[Business]CCD_Simplify_case_creation_on_contact(Part2)
	 * 
	 */
	@Test(groups = { "sprint_PI5.5", "B-1039338", "caseCreationOnContacts" })
	public void usB1039338_CCD_Simplify_case_creation_on_contact() {
		String contactname = "Shubham Nagar", casequeue = "Sales_Support_L2", subject = "TEST07",
				country = "United Kingdom";
		cmodCaseActions.us_B1039338_CCD_Simplify_case_creation_on_contact_part2(contactname, casequeue, subject,
				country);
	}

	/*
	 * B-1118233- [Business] CCD - Simplify Case Creation on Contact (part 3) need
	 * to add data-provider for this user story for different queues
	 */
	@Test(groups = { "sprint_PI5.5", "B-1118233", "caseCreationOnContacts" })
	public void usB1118233_CCD_Simplify_case_creation_on_contact() {
		String contactname = "Shubham Nagar", casequeue = "Sales_Support_L2", subject = "TEST07",
				country = "United Kingdom";
		cmodCaseActions.us_B1039338_CCD_Simplify_case_creation_on_contact_part2(contactname, casequeue, subject,
				country);
	}

	/*
	 * EU-POLARIS-CY22-PI3 Iteration 3.2 starts here B-1158540- [Business] CCD
	 * -Enhancement on Destination Email Address
	 */
	@Test(groups = { "sprint_CY22_PI3_I3.2", "B-1158540", "I" })
	public void b1158540_CCD_Enhancement_on_Destination_Email_Address() {
		cmodCaseActions.CCD_Enhancement_on_DestinationEmailAddressTest();
	}

	/*
	 * EU-POLARIS-CY22-PI3 Iteration 3.2 starts here B-1156309- [Business] CCD
	 * -Enhancement on Auto Contact Creation
	 */
	@Test(groups = { "sprint_CY22_PI3_I3.2", "B-1156309", "I" })
	public void b1156309_CCD_Enhancement_on_Auto_Contact_Creation_Salutation() {
		cmodCaseActions.CCD_Enhancement_on_SalutationValue();
	}

	/*
	 * EU-POLARIS-CY22-PI3 Iteration 3.2 starts here B-1161274- [Business] CCD -Auto
	 * Contact Creation (part2)
	 */
	@Test(groups = { "sprint_CY22_PI3_I3.2", "B-1161274", "I" })
	public void b1161274_Auto_Contact_Creation_ReferencSystem() {
		cmodCaseActions.ccd_Auto_Contact_Creation_part2_ReferenceSystem();
	}

	@AfterMethod(alwaysRun = true)
	public void closeTab() {
		// CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		// CCD_Connectivity.CloseTab();
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
