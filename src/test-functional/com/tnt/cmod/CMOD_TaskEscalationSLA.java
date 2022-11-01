package com.tnt.cmod;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import com.tnt.ccd.CCD_CI_Booking;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentInfoPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;
import util.TestUtil;

public class CMOD_TaskEscalationSLA extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
	UiTestHelper uiTestHelper = new UiTestHelper();

	@BeforeSuite
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
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

	@BeforeClass
	public void login() {
		cmodFFReusable.support_Login();
	}

	@BeforeMethod
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@DataProvider
	public Object[][] TaskEscalationSLA() {
		Object data[][] = TestUtil.getTestData("EscFromL1");
		return data;
	}

	@DataProvider
	public Object[][] createCase_withoutPR() {
		Object data[][] = TestUtil.getTestData("CreateCaseWoPR");
		return data;
	}

	@DataProvider
	public Object[][] createCase_withPR() {
		Object data[][] = TestUtil.getTestData("CreateCaseWithPR");
		return data;
	}

	@org.testng.annotations.Test(priority = 1, dataProvider = "TaskEscalationSLA", enabled = false)
	public void createCaseWithOutPriorityIndicator(String run, String tcNum, String odPair, String conNum,
			String caseLoc, String routeTo, String reason, String cause, String firstname, String lastName,
			String phone, String email, String result) {
		HomePage homepage = new HomePage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		try {
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			connectivity.TrackandTraceNewSearchBtn();
			connectivity.TrackandTraceCCD(conNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connectivity.CloseTab();
			System.out.println(tcNum);
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			HomePage hp = new HomePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.setCaseLoc(caseLoc);
			ccpage.setReason(reason);
			ccpage.setCause(cause);
			ccpage.setFirstName(firstname);
			ccpage.setLastName(lastName);
			ccpage.setphone(phone);
			ccpage.setEmail(email);
			ccpage.clickCaseAssign();
			ccpage.clickCaseCreatebtn();
			ccpage.createdStatus();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message(
						"Reactive case is created successfully for: TC-" + tcNum + " , ConNum-" + conNum + ", ODPair-"
								+ odPair + ", RouteTo- " + routeTo + ", Cause- " + cause + ", Reason- " + reason + " ");
			}
			caseDetailsValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Exception: Reactive case is not created for: TC-" + tcNum + " , ConNum-" + conNum + ", ODPair-"
							+ odPair + ", RouteTo- " + routeTo + ", Cause- " + cause + ", Reason- " + reason + " ");
		}
	}

	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_withoutPR", enabled = false)
	public void createCaseWithWithoutPriorityIndicator(String tcNum, String odPair, String conNum, String caseLoc,
			String routeTo, String reason, String cause, String firstname, String lastName, String phone,
			String email) {
		HomePage homepage = new HomePage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		try {
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			connectivity.TrackandTraceNewSearchBtn();
			connectivity.TrackandTraceCCD(conNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connectivity.CloseTab();
			System.out.println(tcNum);
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			HomePage hp = new HomePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.setCaseLoc(caseLoc);
			ccpage.setReason(reason);
			ccpage.setCause(cause);
			ccpage.setFirstName(firstname);
			ccpage.setLastName(lastName);
			ccpage.setphone(phone);
			ccpage.setEmail(email);
			ccpage.clickCaseAssign();
			ccpage.clickCaseCreatebtn();
			ccpage.createdStatus();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message(
						"Reactive case is created successfully for: TC-" + tcNum + " , ConNum-" + conNum + ", ODPair-"
								+ odPair + ", RouteTo- " + routeTo + ", Cause- " + cause + ", Reason- " + reason + " ");
			}
			caseDetailsValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Exception: Reactive case is not created for: TC-" + tcNum + " , ConNum-" + conNum + ", ODPair-"
							+ odPair + ", RouteTo- " + routeTo + ", Cause- " + cause + ", Reason- " + reason + " ");
		}
	}

	@org.testng.annotations.Test(priority = 1, dataProvider = "TaskEscalationSLA", enabled = true)
	public void taskEscFromL1(String run, String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email, String result) {
		HomePage homepage = new HomePage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		try {
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			// connectivity.TrackandTraceNewSearchBtn();
			connectivity.TrackandTraceCCD(conNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connectivity.CloseTab();
			System.out.println(tcNum);
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			HomePage hp = new HomePage(getDriver());
//			consignmentPage.clickcreatecasebtn();
//			ccpage.setCaseLoc(caseLoc);
//			ccpage.setReason(reason);
//			ccpage.setCause(cause);
//			ccpage.setFirstName(firstname);
//			ccpage.setLastName(lastName);
//			ccpage.setphone(phone);
//			ccpage.setEmail(email);
//			ccpage.clickCaseAssign();
//			ccpage.clickCaseCreatebtn();
//			ccpage.createdStatus();
//			boolean val= hp.verifyCaseDisplayed();
//			Assert.assertEquals(val, true);
//			{
//				Pass_Message("Reactive case is created successfully for: TC-"+tcNum+" , ConNum-"+conNum+", ODPair-"+odPair+", RouteTo- "+routeTo+", Cause- "+cause+", Reason- "+reason+" ");
//			}
			caseDetailsValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Exception: Reactive case is not created for: TC-" + tcNum + " , ConNum-" + conNum + ", ODPair-"
							+ odPair + ", RouteTo- " + routeTo + ", Cause- " + cause + ", Reason- " + reason + " ");
		}
	}

	public void caseDetailsValidation() {
		try {
			getDriver().manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
			CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
			casedetailpage.clickCaseDetails();
			String CMODid = casedetailpage.getcmodID();
			if (CMODid.isEmpty()) {
				Fail_Message("CMOD is blank");
			} else {
				Pass_Message("CMOD id is displayed as : " + CMODid);
			}
			String shipmentdirection = casedetailpage.getShipmentDirection();
			if (shipmentdirection.isEmpty()) {
				Fail_Message("Shipment Direction is blank");
			} else {
				Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
			}
		} catch (Exception e) {
			Fail_Message("Case Details validation failed");
		}

	}

	/*
	 * @org.testng.annotations.Test(priority = 1, dataProvider = "createCase",
	 * enabled = false) public void createCaseByDataProvider(boolean flag, String
	 * tcNum, String conNum, String caseLoc, String routeTo, String reason, String
	 * cause, String firstname, String lastName, String phone, String email, String
	 * result) { HomePage homepage = new HomePage(getDriver()); CCD_Connectivity
	 * connectivity = new CCD_Connectivity(); try { TrackAndTracePage
	 * trackandtracePage = new TrackAndTracePage(getDriver());
	 * connectivity.TrackandTraceCCD(conNum); } catch (Exception e) {
	 * e.printStackTrace(); } try { System.out.println(tcNum);
	 * System.out.println(flag); ConsignmentPage consignmentPage=new
	 * ConsignmentPage(getDriver()); CreateCasePage ccpage=new
	 * CreateCasePage(getDriver()); HomePage hp=new HomePage(getDriver());
	 * consignmentPage.clickcreatecasebtn(); ccpage.setCaseLoc(caseLoc);
	 * ccpage.setReason(reason); ccpage.setCause(cause);
	 * ccpage.setFirstName(firstname); ccpage.setLastName(lastName);
	 * ccpage.setphone(phone); ccpage.setEmail(email); ccpage.clickCaseAssign();
	 * ccpage.clickCaseCreatebtn(); ccpage.createdStatus(); boolean val=
	 * hp.verifyCaseDisplayed(); Assert.assertEquals(val, true); {
	 * Pass_Message("Reactive case is created successfully"); } } catch (Exception
	 * e) { e.printStackTrace();
	 * Fail_Message("Exception: Reactive Case creation test case is failed"); } }
	 */
	@AfterMethod
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	@AfterMethod
	public void closeTab() {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.CloseTab();
	}

	@AfterClass
	public void afterClass() {
		getDriver().quit();
	}

	@AfterSuite
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
