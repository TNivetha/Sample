package com.tnt.cmod;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.tnt.ccd.CCD_CI_Booking;
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
public class CMOD_RegressionTestExecution extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
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

	@BeforeClass(alwaysRun = true)
	public void login() {
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				CMOD_RegressionTestExecution.Key_Array[2]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				CMOD_RegressionTestExecution.Key_Array[2]);
		cmodFFReusable.csr_Login(Username, Password);
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
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	/*
	 * Create a consignment from India (22875) to UK (78867) for Omni Channel Accept
	 * TC Create a consignment from UK (78867) to India (22875) for other Reactive
	 * case flow Test data source for reactiveCaseFlow: Col: CONNORT, Row:
	 * Reg_ReactiveCaseFlow
	 */
	@Test(priority = 1, enabled = true)
	public void create_Case_Frontline_CSR() {
		cmodFFReusable.CreateCaseByFrontlineCSR();
	}

	@Test(priority = 2, enabled = true)
	public void reactive_Case_Creation() {
		String Retrieve = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		cmodFFReusable.login_General();
		FF_EBS.searchConsignmentOption("324682352");
		FF_EBS.CreateCase_EBS();
	}

	@Test(priority = 3, enabled = true)
	public void validate_Reactive_Case_details() {
		FF_EBS.casedetails_caseebs();
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","reg_peCreateCase"})
	@Test(priority = 4, enabled = true)
	public void proactive_Case_Creation() {
		cmodFFReusable.CreateCase_PE();
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","caseDetailsValidation"})
	@Test(priority = 5, enabled = true)
	public void validate_Proactive_Case_Details() {
		FF_EBS.casedetails_caseebs_PE();
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_callBack" })
	@Test(priority = 6, enabled = true)
	public void reactive_Case_Callback_Activity() {
		cmodFFReusable.callback_activity("re_case");
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_monitoring" })
	@Test(priority = 7, enabled = true)
	public void reactive_Case_Monitoring_Activity() {
		cmodFFReusable.MonitoringActivity("re_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pecallBack"})
	@Test(priority = 8, enabled = true)
	public void proactive_Case_Callback_Activity() {
		cmodFFReusable.callback_activity("pe_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_monitoring"})
	@Test(priority = 9, enabled = true)
	public void proactive_Case_Monitoring_Activity() {
		cmodFFReusable.MonitoringActivity("pe_case");
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "task" })
	@Test(priority = 10, enabled = true)
	public void case_Task_Activity() {
		String Retrieve = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[7]);
		cmodFFReusable.task_create(Retrieve);
	}

	// @Test (groups = {"reg_suite","reg_ReactiveCaseFlow", "taskEscalation",
	// "byCO"})
	@Test(priority = 11, enabled = true)
	public void task_Escalation() {
		cmodFFReusable.task_escalation();
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_Actions" })
	@Test(priority = 12, enabled = true)
	public void reactive_Case_Actions() {
		cmodFFReusable.login_General();
		cmodFFReusable.action("re_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_Actions"})
	@Test(priority = 13, enabled = true)
	public void proactive_Case_Actions() {
		cmodFFReusable.action("pe_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_customerRemark"})
	@Test(priority = 14, enabled = true)
	public void reactive_Case_Customer_Remark() {
		cmodFFReusable.customer_remark("re_case");
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_custRemarks" })
	@Test(priority = 15, enabled = true)
	public void proactive_Case_Customer_Remark() {
		cmodFFReusable.customer_remark("pe_case");
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_caseRemarks" })
	@Test(priority = 16, enabled = true)
	public void reactive_Case_Remark() {
		cmodFFReusable.Case_remark("re_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_caseRemark"})
	@Test(priority = 17, enabled = true)
	public void proactive_Case_Remark() {
		cmodFFReusable.Case_remark("pe_case");
	}

	// @Test (groups = { "reg_ReactiveCaseFlow", "taskEscalation", "byOIB" })
	@Test(priority = 18, enabled = true)
	public void TaskEscalation_OIB() {
		cmodFFReusable.task_escalation_OIB();
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow",
	// "taskEscalation","esclationSLA" })
	@Test(priority = 19, enabled = true)
	public void TaskEscalationSLA() {
		cmodFFReusable.task_escalation_OIB();
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "rc_email" })
	@Test(priority = 20, enabled = false)
	public void reactive_Email_Functionality() {
		FF_EBS.Email_EBS("re_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_email"})
	@Test(priority = 21, enabled = false)
	public void proactive_Email_Functionality() {
		FF_EBS.Email_EBS("pe_case");
	}

	// @Test (groups = { "reg_suite","reg_ReactiveCaseFlow", "closeCase" })
	@Test(priority = 22, enabled = true)
	public void reactive_Close_Case() {
		cmodFFReusable.CloseCase("re_case");
	}

	// @Test(groups= {"reg_suite","pe_CaseFlow","pe_closeCase"})
	@Test(priority = 23, enabled = true)
	public void closeCase_PE() {
		cmodFFReusable.CloseCase("pe_case");
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
