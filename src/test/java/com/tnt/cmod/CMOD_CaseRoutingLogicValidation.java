package com.tnt.cmod;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccd.CCD_Quote;
import com.tnt.ccdobjects.CaseDetailsPage;
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

public class CMOD_CaseRoutingLogicValidation extends Driver {
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
		login_GB();
		// login_DE();
		// login_NL();
		// cmodFFReusable.support_Login();
		// connectivity.CloseTab();
	}

	@BeforeMethod
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@DataProvider
	public Object[][] createCase_OtoO_Origin() {
		Object data[][] = TestUtil.getTestData("OtoO_Origin");
		return data;
	}

	@DataProvider
	public Object[][] createCase_OtoO_Dest() {
		Object data[][] = TestUtil.getTestData("OtoO_Dest");
		return data;
	}

	@DataProvider
	public Object[][] createCase_OtoO_TP() {
		Object data[][] = TestUtil.getTestData("OtoO_TP");
		return data;
	}

	@DataProvider
	public Object[][] createCase_OtoP_Origin() {
		Object data[][] = TestUtil.getTestData("OtoP_Origin");
		return data;
	}

	@DataProvider
	public Object[][] createCase_OtoP_Dest() {
		Object data[][] = TestUtil.getTestData("OtoP_Dest");
		return data;
	}

	@DataProvider
	public Object[][] createCase_OtoP_TP() {
		Object data[][] = TestUtil.getTestData("OtoP_TP");
		return data;
	}

	@DataProvider
	public Object[][] createCase_PtoO_Origin() {
		Object data[][] = TestUtil.getTestData("PtoO_Origin");
		return data;
	}

	@DataProvider
	public Object[][] createCase_PtoO_Dest() {
		Object data[][] = TestUtil.getTestData("PtoO_Dest");
		return data;
	}

	@DataProvider
	public Object[][] createCase_PtoO_TP() {
		Object data[][] = TestUtil.getTestData("PtoO_TP");
		return data;
	}

	@DataProvider
	public Object[][] caseRoutingLogic_CMODtoCCD() {
		Object data[][] = TestUtil.getTestData("CMODtoCCD");
		return data;
	}

	// Corssops test data keys
	/*
	 * TNT_US_TO_GB_SIT_2 for P2O TNT_US_TO_CA_SIT for P2P TNT_GB_TO_DE_SIT_2 for
	 * O2O TNT_NL_TO_US_SIT_2 for O2P
	 */

	// take login_OtoO_Origin();
	@org.testng.annotations.Test(priority = 2, dataProvider = "createCase_OtoO_Origin", enabled = false)
	public void createCase_OtoO_Origin(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}

		try {
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
			ccpage.clickCaseCreatebtn();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
						+ ", ODPair-" + odPair);
			}
			caseDetailsValidation(caseOwnQueue, coCountry);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
			if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
				Pass_Message("As expected Origin box is checked");
			} else {
				Fail_Message("Origin box is not checked");
			}

		} catch (Exception e2) {
			Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
					+ " and Consignment: " + conNum);
			getDriver().navigate().refresh();
		}
	}

	// take login_OtoO_Dest();
	@org.testng.annotations.Test(priority = 4, dataProvider = "createCase_OtoO_Dest", enabled = false)
	public void createCase_OtoO_Destination(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}
		try {
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
			ccpage.clickCaseCreatebtn();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
						+ ", ODPair-" + odPair);
			}
			caseDetailsValidation(caseOwnQueue, coCountry);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			if (cdPage.verifyDestinationCaseChkBoxIsChecked() == true) {
				Pass_Message("As expected Destination box is checked");
			} else {
				Fail_Message("Destination checkbox is not checked");
			}

		} catch (Exception e2) {
			Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
					+ " and Consignment: " + conNum);
			getDriver().navigate().refresh();
		}
	}

	// Take login_OtoO_Origin();

	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_OtoO_TP", enabled = false)
	public void createCase_OtoO_TP(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		{
			try {
				connectivity.CloseTab();
				if (ttPage.verifyNewSearchDisplayed() == true) {
					connectivity.searchConWithNewSearchBtn(conNum);
				} else {
					connectivity.TrackandTraceCCD(conNum);
				}
			} catch (Exception e) {
				connectivity.TrackandTraceCCD(conNum);
			}
			try {
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
				ccpage.clickCaseCreatebtn();
				boolean val = hp.verifyCaseDisplayed();
				Assert.assertEquals(val, true);
				{
					Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
							+ ", ODPair-" + odPair);
				}
				caseDetailsValidation(caseOwnQueue, coCountry);
				CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
				cdPage.clickCaseInformation();
				System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
				if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
					Pass_Message("As expected Origin box is checked");
				} else {
					Fail_Message("Origin box is not checked");
				}
				cmodFFReusable.logout();
			} catch (Exception e2) {
				Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
						+ " and Consignment: " + conNum);
				getDriver().navigate().refresh();
			}
		}
	}

	// OtoP

	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_OtoP_Origin", enabled = false)
	public void createCase_OtoP_Origin(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}
		try {
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
			ccpage.clickCaseCreatebtn();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
						+ ", ODPair-" + odPair);
			}
			caseDetailsValidation(caseOwnQueue, coCountry);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
			if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
				Pass_Message("As expected Origin box is checked");
			} else {
				Fail_Message("Origin box is not checked");
			}
		} catch (Exception e2) {
			Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
					+ " and Consignment: " + conNum);
			getDriver().navigate().refresh();
		}
	}

	// take login_OtoO_Dest();
	@org.testng.annotations.Test(priority = 2, dataProvider = "createCase_OtoP_Dest", enabled = false)
	public void createCase_OtoP_Destination(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}

		try {
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
			ccpage.clickCaseCreatebtn();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
						+ ", ODPair-" + odPair);
			}
			caseDetailsValidation(caseOwnQueue, coCountry);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			if (cdPage.verifyDestinationCaseChkBoxIsChecked() == true) {
				Pass_Message("As expected Destination box is checked");
			} else {
				Fail_Message("Destination checkbox is not checked");
			}
		} catch (Exception e2) {
			Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
					+ " and Consignment: " + conNum);
			getDriver().navigate().refresh();
		}
	}

	// Take login_OtoO_Origin();
	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_OtoP_TP", enabled = false)
	public void createCase_OtoP_TP(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		{
			try {
				connectivity.CloseTab();
				if (ttPage.verifyNewSearchDisplayed() == true) {
					connectivity.searchConWithNewSearchBtn(conNum);
				}
			} catch (Exception e) {
				connectivity.TrackandTraceCCD(conNum);
			}
			try {
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
				ccpage.clickCaseCreatebtn();
				boolean val = hp.verifyCaseDisplayed();
				Assert.assertEquals(val, true);
				{
					Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
							+ ", ODPair-" + odPair);
				}
				caseDetailsValidation(caseOwnQueue, coCountry);
				CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
				cdPage.clickCaseInformation();
				System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
				if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
					Pass_Message("As expected Origin box is checked");
				} else {
					Fail_Message("Origin box is not checked");
				}
			} catch (Exception e2) {
				Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
						+ " and Consignment: " + conNum);
				getDriver().navigate().refresh();
			}
		}
	}

	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_PtoO_Origin", enabled = false)
	public void createCase_PtoO_Origin(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}
		try {
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
			ccpage.clickCaseCreatebtn();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(true, val);
			{
				Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
						+ ", ODPair-" + odPair);
			}
			caseDetailsValidation(caseOwnQueue, coCountry);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
			if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
				Pass_Message("As expected Origin box is checked");
			} else {
				Fail_Message("Origin box is not checked");
			}
		} catch (Exception e2) {
			Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
					+ " and Consignment: " + conNum);
			getDriver().navigate().refresh();
		}
	}

	// take login_OtoO_Dest();
	@org.testng.annotations.Test(priority = 2, dataProvider = "createCase_PtoO_Dest", enabled = false)
	public void createCase_PtoO_Destination(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);

			try {
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
				ccpage.clickCaseCreatebtn();
				boolean val = hp.verifyCaseDisplayed();
				Assert.assertEquals(val, true);
				{
					Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
							+ ", ODPair-" + odPair);
				}
				caseDetailsValidation(caseOwnQueue, coCountry);
				CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
				cdPage.clickCaseInformation();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,350)", "");
				if (cdPage.verifyDestinationCaseChkBoxIsChecked() == true) {
					Pass_Message("As expected Destination box is checked");
				} else {
					Fail_Message("Destination checkbox is not checked");
				}
			} catch (Exception e2) {
				Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
						+ " and Consignment: " + conNum);
				getDriver().navigate().refresh();
			}
		}
	}

	// Take login_OtoO_Origin();
	@org.testng.annotations.Test(priority = 1, dataProvider = "createCase_PtoO_TP", enabled = false)
	public void createCase_PtoO_TP(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email,
			String caseOwnQueue, String coCountry) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);

			try {
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
				ccpage.clickCaseCreatebtn();
				boolean val = hp.verifyCaseDisplayed();
				Assert.assertEquals(val, true);
				{
					Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
							+ ", ODPair-" + odPair);
				}
				caseDetailsValidation(caseOwnQueue, coCountry);
				CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
				cdPage.clickCaseInformation();
				System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
				if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
					Pass_Message("As expected Origin box is checked");
				} else {
					Fail_Message("Origin box is not checked");
				}
			} catch (Exception e2) {
				Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum
						+ " and Consignment: " + conNum);
				getDriver().navigate().refresh();
			}
		}
	}

	@org.testng.annotations.Test(priority = 6, dataProvider = "createCase_OtoO", enabled = false)
	public void createCase_ODPair_Retest(String tcNum, String odPair, String conNum, String caseLoc, String routeTo,
			String reason, String cause, String firstname, String lastName, String phone, String email)
			throws Exception {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			connectivity.CloseTab();
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNum);
			} else {
				connectivity.TrackandTraceCCD(conNum);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNum);
		}
		{
			try {
				if (conpage.verifyViewCase() == true) {
					System.out.println(tcNum);
					connectivity.CCD_ViewCase();
					Pass_Message("Reactive Case is created and viewed successfully for TC-" + tcNum + ", Con Number-"
							+ conNum + ", ODPair-" + odPair);
					// caseDetailsValidation();
				}
			} catch (Exception e1) {
				try {
					System.out.println("Retest- " + tcNum);
					ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
					CreateCasePage ccpage = new CreateCasePage(getDriver());
					HomePage hp = new HomePage(getDriver());
					consignmentPage.clickcreatecasebtn();
					ccpage.setCaseLoc(caseLoc);
					if (caseLoc.contains("3rd Party")) {
						ccpage.setCaseRoute(routeTo);
					}
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
						Pass_Message("Reactive Case is created successfully for TC-" + tcNum + ", Con Number-" + conNum
								+ ", ODPair-" + odPair);
					}
					// caseDetailsValidation();
				} catch (Exception e2) {
					Fail_Message("Exception: Reactive Case creation and validation test case is failed for- " + tcNum);
					getDriver().navigate().refresh();
				}
			}
		}
	}

	public void caseDetailsValidation(String caseOwnQueue, String coCountry) {
		try {
			CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.contains(caseOwnQueue)) {
				Pass_Message("Case owner is displayed correctly as: " + caseOwner);
			} else {
				Fail_Message("Case owner is not displayed correctly and its value is: " + caseOwner);
			}
			String caseOwnerCountry = casedetailpage.getcaseOwnerCountry();
			if (caseOwnerCountry.equals(coCountry)) {
				Pass_Message("Case owner country displayed correctly as: " + caseOwnerCountry);
			} else {
				Fail_Message("Case owner Country is not displayed correctly and its value is: " + caseOwnerCountry);
			}
			String CMODid = casedetailpage.getcmodID();
			Assert.assertNotNull(CMODid);
			if (CMODid.isEmpty()) {
				Fail_Message("CMOD is blank");
			} else {
				Pass_Message("CMOD id is displayed as : " + CMODid);
			}
			String shipmentdirection = casedetailpage.getShipmentDirection();
			Assert.assertNotNull(shipmentdirection);
			if (shipmentdirection.isEmpty()) {
				Fail_Message("Shipment Direction is blank");
			} else {
				Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
			}
		} catch (Exception e) {
			Fail_Message("Case Details validation failed");
			// e.printStackTrace();
		}
	}

	public void caseInformationValidation() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickCaseInformation();
			System.out.println(cdPage.verifyOriginCaseChkBoxIsChecked());
			if (cdPage.verifyOriginCaseChkBoxIsChecked() == true) {
				Pass_Message("Origin box is checked");
			} else {
				Fail_Message("Origin box is not checked");
			}
			if (cdPage.verifyDestinationCaseChkBoxIsChecked() == true) {
				Pass_Message("Dest box is checked");
			} else {
				Fail_Message("Dest box is not checked");
			}
		} catch (Exception e) {
			Fail_Message("Case Details validation failed");
			// e.printStackTrace();
		}
	}

	@org.testng.annotations.Test(priority = 7, dataProvider = "caseRoutingLogic_CMODtoCCD", enabled = true)
	public void caseRouting_CMODtoCCD_Validations(String tcNum, String odPair, String conNum, String cmodId,
			String caseLoc, String routeTo, String reason, String cause) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		HomePage hp_obj = new HomePage(getDriver());
		CustomerAccountPage capage = new CustomerAccountPage(getDriver());
		hp_obj.globalSearchClick();
		hp_obj.setTextInGlobalSearch(cmodId);
		boolean cmodCase = capage.verifyCaseDisplayed();
		Assert.assertEquals(cmodCase, true);
		{
			Pass_Message("Case is reflecting CCD application");
		}
		capage.clickCaseRecord();
		casedetailpage.clickCaseDetails();
		String CMODid = casedetailpage.getcmodID();
		if (CMODid.isEmpty()) {
			Fail_Message("CMOD is blank");
		} else {
			Pass_Message("CMOD id is displayed as : " + CMODid);
		}
		String shipmentdirection = casedetailpage.getShipmentDirection();
		if (shipmentdirection.equals(odPair)) {
			Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
		} else {
			Fail_Message("Shipment Direction validation failed");
		}
		// Consignment information validation
		try {
			casedetailpage.clickCaseInformation();

			/*
			 * String casetype=casedetailpage.getcaseType();
			 * System.out.println("CaseType"+casetype); if (casetype.contains("Reactive")) {
			 * Pass_Message("Case Type displayed is 'Reactive case' "); } else {
			 * Fail_Message("Case Type is not displayed correctly"); }
			 */
			String caseGroup1 = casedetailpage.getcaseGroup();
			if (caseGroup1.contains("Shipment")) {
				Pass_Message("Case Group displayed is correctly as: " + caseGroup1);
			} else {
				Fail_Message("Case Group is not displayed correctly");
			}
			String caseReason1 = casedetailpage.getcaseReasonWithCmod();
			if (caseReason1.contains(reason)) {
				Pass_Message("Case Reason displayed correctly as: " + caseReason1);
			} else {
				Fail_Message("Case Reason is not displayed correctly");
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,500)", "");
			String cause1 = casedetailpage.getcaseCausewithCmod();
			if (cause1.contains(cause)) {
				Pass_Message("Cause displayed correctly as: " + cause);
			} else {
				Fail_Message("Cause is not displayed correctly");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Information Page is not validated successfully");
			getDriver().navigate().refresh();
		}
	}

	public void login_GB() {
		try {
			String Username = Database_Connection.retrieveTestData("ACM_USERID", "GLOBALDATA", "KEY",
					CMOD_CaseRoutingLogicValidation.Key_Array[13]);
			String Password = Database_Connection.retrieveTestData("ACM_PASSWORD", "GLOBALDATA", "KEY",
					CMOD_CaseRoutingLogicValidation.Key_Array[13]);
			CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
			reusable.csr_Login(Username, Password);
		} catch (Exception e) {
			Fail_Message("login failed");
			// e.printStackTrace();
		}
	}

	public void login_DE() {
		try {
			String Username = Database_Connection.retrieveTestData("ACM_USERID", "GLOBALDATA", "KEY",
					CMOD_CaseRoutingLogicValidation.Key_Array[12]);
			String Password = Database_Connection.retrieveTestData("ACM_PASSWORD", "GLOBALDATA", "KEY",
					CMOD_CaseRoutingLogicValidation.Key_Array[12]);

			CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
			reusable.csr_Login(Username, Password);
		} catch (Exception e) {
			Fail_Message("login failed");
			// e.printStackTrace();
		}
	}

	public void login_NL() {
		try {
			String Username = Database_Connection.retrieveTestData("ACM_USERID", "GLOBALDATA", "KEY",CMOD_CaseRoutingLogicValidation.Key_Array[11]);
			String Password = Database_Connection.retrieveTestData("ACM_PASSWORD", "GLOBALDATA", "KEY",
					CMOD_CaseRoutingLogicValidation.Key_Array[11]);
			CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
			reusable.csr_Login(Username, Password);
		} catch (Exception e) {
			Fail_Message("login failed");
			// e.printStackTrace();
		}
	}

	@AfterMethod
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		extent.flush();
	}

	@AfterMethod
	public void closeTab() {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		// CCD_Connectivity.CloseTab();
	}

	@AfterClass
	public void afterClass() {
		// driver.quit();
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
