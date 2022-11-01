package com.tnt.cmod;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CMOD_CaseUpdate extends Driver {
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

	@Test(groups = { "caseUpdate", "caseUpdateColumn", "caseUpdateColumnDisplayed" })
	public void US_B513632_TC1_validateCaseUpdateColumn() {
		reusable.support_Login();
		conectivity.CloseTab();
		reusable.validateCaseUpdateColumn();
	}

	@Test(groups = { "caseUpdate", "caseUpdateColumn", "caseUpdateColumnNotDisplayed" })
	public void US_B513632_TC2_validateCaseUpdateColumnNotVisible() {
		reusable.US_B513632_TC2_validateCaseUpdateColumnNotVisible();
	}

	@Test(groups = { "caseUpdate", "blankValidationInOwnerLogin" })
	public void us_B515916_TC3_caseUpdatedbyOwner_CaseUpdateBlankValidationInOwnerLogin() {
		reusable.us_B515916_TC3_caseUpdatedbyOwn_CaseUpdateBlankValidationInOwnLogin();
	}

	@Test(groups = { "caseUpdate", "caseUpdateColumn1", "byOIB_caseRemarks" })
	public void us_B515916_TC4_CaseUpdatedbyOIB_CaseRemarksVal() {
		reusable.us_B515916_TC4_CaseUpdatedbyOIB_CaseRemarksVal();
	}

	// Test Data source: CMOD, CONNORT
	@Test(groups = { "caseUpdate", "caseUpdateColumn1", "byOIB_email" })
	public void us_B515916_TC5_CaseUpdatedbyOIB_Email() {
		reusable.support_Login();
		reusable.us_B515916_CaseUpdatedbyOIB_Email();
		// reusable.support_Login();
		reusable.us_B852245_CaseUpdatePopUpValidation_Cancel();
		reusable.us_B515916_CaseUpdatedbyOIB_Email_Validation();

	}

	@Test(groups = { "caseUpdate", "caseUpdateColumn1", "layout" })
	public void us_B515916_TC6_LayoutValidation() {
		// RC
		reusable.support_Login();
		reusable.us_B515916_TC6_LayoutValidation();
	}

	@Test(groups = { "caseUpdate", "caseUpdateColumn1", "byOIB_caseStatus" })
	public void us_B515916_TC7_CaseUpdatedbyOIB_CaseStatus() {
		reusable.us_B515916_TC7_CaseUpdatedbyOIB_CaseStatus();
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
