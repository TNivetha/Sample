package com.tnt.cmod;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CMOD_Create_Close_ReopenCases extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_Connectivity conectivity = new CCD_Connectivity();
	CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
	CMOD_ReactiveCaseFlow ff = new CMOD_ReactiveCaseFlow();

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
		reusable.support_Login();
		conectivity.CloseTab();
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
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
	 * Sprint PI2.2 Starts here US B-466623 TC1-TC3: Update consignment in 'CONNORT'
	 * In column Frontline_Login in DB
	 */
	@Test(groups = { "Sprint PI 2.2", "B-466623", "closeCase", "consumerTypeDrpDwnLstCloseCase" })
	public void us_B466623_ConsumerTypeDrpDwnLstValidation_CloseCase() {
		reusable.us_B466623_ConsumerTypeDrpDwnLstValidation_CloseCase();
	}

	// US B-466623 TC4
	@Test(groups = { "Sprint PI 2.2", "B-466623", "closeCase", "consumerTypeDrpDwnLstMergedAction" })
	public void us_B466623_TC4_ConsumerTypeDrpDwnCloseCase_MegredActions() {
		reusable.us_B466623_TC4_ConsumerTypeDrpDwnCloseCase_MegredActions();
	}

	// B-466620 4-14 Merged into 2 Test Methods
	@Test(priority = 1, enabled = true, groups = { "Sprint PI 2.2", "B-466620", "closeCase",
			"Close Case DropDown Actions" })
	public void closeCaseThenAddAnyIssueLocationOwnerShipRootCauseAndVerify() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.closeCaseThenAddAnyIssueLocationOwnerShipRootCauseAndVerify();
	}
	// Sprint PI2.2 ends (includes B-466620 & B-466623)

	/*
	 * Sprint PI2.3 B-592940- [Business] The Case Summary Tab Updated Consignment
	 * Status Description
	 */
	@Test(groups = { "Sprint PI 2.3", "B-592940", "CaseSummary", "Updated Consignment Status Description" })
	public void us_B592940_CaseSummaryTab_UpdatedConsignmentStatus_Description() {
		CMOD_FunctionalFlow_Updated ff_updated = new CMOD_FunctionalFlow_Updated();
		ff_updated.b592940_CaseSummaryTab_UpdatedConsignmentStatus_Description();

	}

	/*
	 * Sprint PI2.4 B-703296- [Business] Root Cause Analysis (non-mandatory change)
	 * & Enhancement
	 */
	@Test(groups = { "Sprint PI 2.4", "B-703296", "Root Cause Analysis" })
	public void us_B703296_RootCauseAnalysis_NonMmandatoryChange_and_Enhancement() {
		CMOD_FunctionalFlow_Updated ff_updated = new CMOD_FunctionalFlow_Updated();
		ff_updated.b703296_RootCauseAnalysis_NonMmandatoryChange_and_Enhancement("re_case");

	}

	// Sprint PI 3.1 Starts here
	/*
	 * B-697296- [Business] INC017143511: Duplicate cases created / Validation for
	 * new case reason & case B-697296 Tcs - TC1 & TC4 & TC5 & TC7
	 */

	@Test(groups = { "Sprint PI 3.1", "B-697296", "Track And Trace Actions" })
	public void reusableSearchConsignmentAndViewCases() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.reusableSearchConsignmentAndViewCases();
			TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
			if (ttPage.areCaseNumbersDisplayed() == true)
				;
			{
				Pass_Message("Cases are viewed");
			}
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-697296 Tcs - TC3
	@Test(groups = { "Sprint PI 3.1", "B-697296", "Track And Trace Actions" })
	public void createCaseWithExistingCauseReasonAndValidateError() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.searchConsignmentAndCreateCaseWithSameOptions();
			TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
			Boolean hasErrorMessageVanished = ttPage.waitForErrorMessage();
			if (hasErrorMessageVanished == true) {
				Pass_Message("Error Message for Case created with same options is displayed");
			}
			softAssertion.assertAll();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-697296 Tcs - TC6
	@Test(groups = { "Sprint PI 3.1", "B-697296", "Track And Trace Actions" })
	public void clickEyeFinderOnCase() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.reusableSearchConsignmentAndViewCases();
			cmodCaseActions.scrollAcrossCasesTableAndClickEyeIcon();
			cmodCaseActions.validateConsignmentOpened();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}
	// Sprint PI 3.1 ends here

	// Sprint PI 3.4 Starts here
	// B-781730 blocked TC
	@Test(groups = { "Sprint 3.4", "B-781730", "CMOD Case Actions" })
	public void searchCMODCaseVerifyOwnerEqualsSupportQueue() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.searchCMODCaseVerifyOwnerEqualsSupportQueue();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-784291 TC10 & Merged with B-432224 TC2 & TC3
	// Merged B-807162 TC1 & TC2 into this test method
	@Test(groups = { "Sprint 3.4", "Sprint 3.5", "B-807162", "B-784291", "B-432224", "Track And Trace Actions" })
	public void searchO2OConsignmentAndValidate() {
		// Edit the DBA Call in the Code
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.searchO2OConsignmentAndValidate();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-432224 TCs - TC1
	@Test(groups = { "Sprint 3.4", "B-432224", "Track And Trace Actions" })
	public void searchO2OConsAndSelectRouteTo() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.searchO2OConsAndSelectRouteTo();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-432224 TCs - TC4 - TC7
	@Test(groups = { "Sprint 3.4", "B-432224", "Track And Trace Actions" })
	public void createReactiveCaseAndSelectTaskOptions() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.createReactiveCaseAndSelectTaskOptions();
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}
	// Sprint PI 3.4 Ends here (Inclues B-432224, B-784291 & B-781730 Together user
	// stories)

	// Sprint PI 3.5 Starts here
	// TC1 & TC2 Orange to Orange
	@Test(groups = { "Sprint 3.5", "B-807162", "OToOConsignmentValidation" })
	public void searchOToOConsignmentAndValidate() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.reusableSearchConsignmentInTTP("CASENUM1");
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-807162 TC3 - Orange to Purple
	@Test(groups = { "Sprint 3.5", "B-807162" })
	public void searchOToPConsignmentAndValidate() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.reusableSearchConsignmentInTTP("CASENUM1");
			// change the value of CASENUM1 to a different table entry - bottom 2 methods
			// need changing
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	// B-807162 TC4 / TC5 / TC6 / TC7 / TC8- Purple to Orange
	@Test(groups = { "Sprint 3.5", "B-807162" })
	public void searchPToOConsignmentAndValidate() {
		try {
			CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
			cmodCaseActions.reusableSearchConsignmentInTTP("CASENUM1");
			// change the value of CASENUM1 to a different table entry
		} catch (Exception e) {
			Fail_Message("Test has failed");
		}
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		conectivity.CloseTab();
		getDriver().navigate().refresh();
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
