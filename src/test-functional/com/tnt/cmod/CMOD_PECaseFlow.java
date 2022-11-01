package com.tnt.cmod;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

public class CMOD_PECaseFlow extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_Connectivity conectivity = new CCD_Connectivity();
	CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
	CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();

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

	@BeforeMethod(alwaysRun = true)
	public void Login() {
		conectivity.Login_To_Salesforce();
		conectivity.CloseTab();
	}

	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		elapsedTime = 0;
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	// Create a con from India (22875) to UK (78867)
	@Test(groups = { "peCaseFlow", "createCase" })
	public void pe_CreateCase() {
		reusable.CreateCase_PE();
	}

	@Test(groups = { "peCaseFlow", "caseDetailsValidation" })
	public void casedetails_Validation() {
		FF_EBS.casedetails_caseebs_PE();
	}

	@Test(groups = { "peCaseFlow", "monitoring" })
	public void monitoring_Activity() {
		reusable.MonitoringActivity("pe_case");
	}

	@Test(groups = { "peCaseFlow", "callBack" })
	public void callback_Activity() {
		reusable.callback_activity("pe_case");
	}

	@Test(groups = { "peCaseFlow", "actions" })
	public void actions() {
		reusable.action("pe_case");
		reusable.internal_tabclose();
	}

	@Test(groups = { "peCaseFlow", "customerRemark" })
	public void customer_Remark() {
		reusable.customer_remark("pe_case");
	}

	@Test(groups = { "peCaseFlow", "caseRemark" })
	public void case_Remark() {
		reusable.Case_remark("pe_case");
		reusable.internal_tabclose();
	}

	@Test(groups = { "peCaseFlow", "email" })
	public void email() {
		reusable.internal_tabclose();
		FF_EBS.Email_EBS("pe");
	}

	@Test(groups = { "peCaseFlow", "rfi" })
	public void rfi() {
		reusable.internal_tabclose();
		reusable.RFI();
	}

	@Test(groups = { "peCaseFlow", "closeCase" })
	public void closeCase() {
		reusable.internal_tabclose();
		reusable.CloseCase("pe_case");
	}

	// Sprint PI2 R1 Starts here
	// B-563726 TC1
	@Test(groups = { "Sprint PI2 R1", "B-563726", "PECaseFlow", "Contact Screen Actions" })
	public void validateOptOutReasonsOnContactScreen() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.validateOptOutReasonsOnContactScreen();
	}

	// B-563726 TC2 (Blocked)
	@Test(groups = { "Sprint PI2 R1", "B-563726", "PECaseFlow", "Contact Screen Actions" })
	public void optsOutPNAndValidateErrorMessage() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.optsOutPNAndValidateErrorMessage();
	}

	// Sprint PI2 R1 Ends here
	@Test(groups = { "peCaseFlowRegression" })
	public void peCaseFlowRegression() {
		CMOD_CaseActions cmodCaseActions = new CMOD_CaseActions();
		cmodCaseActions.peCaseFlowRegression();
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
