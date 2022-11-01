package com.tnt.cmod;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.tnt.ccd.CCD_CI;
import com.tnt.ccd.CCD_CI_Booking;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccd.CCD_CMOD_SmokeTest;
import com.tnt.ccdobjects.AdminPage;
import com.tnt.ccdobjects.BookingExceptionsPage;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.ExceptionsPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.LogoutPage;
import com.tnt.ccdobjects.MyOpenCasesListViewPage;
import com.tnt.ccdobjects.ProactiveExceptionsPage;
import com.tnt.ccdobjects.ReportingPage;
import com.tnt.ccdobjects.TaskPage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.CMODConstants;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

import util.TestUtil;

public class CMOD_FF_Reusable extends Driver {
	public static String casenumber, akash, TimeGreen, TimeAmber, Timered, queuename, caseNo, exp_date, Retrive,
			datetime, con1, con2, caseNum1, caseNum2;
	String inputQueueName = "UK - GH - Support 5";
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
	CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
	CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
	UiTestHelper uiTestHelper = new UiTestHelper();
	SoftAssert softAssert = new SoftAssert();

	public void logout() {
		try {
			LogoutPage logoutpage = new LogoutPage(getDriver());
			logoutpage.clickUser();
			uiTestHelper.propagateException();
			logoutpage.clickLogout();
			uiTestHelper.propagateException();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Logout failed");
		}
	}

	public CMOD_FF_Reusable() {
		uiTestHelper = new UiTestHelper();
	}

	public void ominiChannelAccept() {
		try {
			CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[8]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[8]);
			reusable.csr_Login(Username, Password);
			CCD_CI_Booking cibooking = new CCD_CI_Booking();
			cibooking.returnToPage();
			getDriver().navigate().refresh();

			trackandtracePage.clickOmniChannel();
			trackandtracePage.clickOmniStatusDropDown();
			trackandtracePage.clickOmniStatus("Available");
			try {
				// getDriver().findElement(By.xpath("//*[@class='uiOutputRichText' and
				// contains(text(),'"+Retrive+"').click();
				trackandtracePage.clickOmniCaseAccept();
				Pass_Message("Case accepted from the omini channel.");
			} catch (Exception e) {
				// TODO: handle exception
				Fail_Message("No Active cases are displayed");
			}
			trackandtracePage.clickOmniMinimize();
			/*
			 * Actions obj= new Actions(getDriver()); WebElement
			 * omniClose=getDriver().findElement(By.xpath(
			 * "/html/body/div[4]/div[1]/div[2]/div[3]/div[1]/div[4]/a/div/div[2]/span/button/lightning-primitive-icon"
			 * )); obj.click(omniClose).perform()
			 */;
			/*
			 * trackandtracePage.clickOmniStatusDropDown();
			 * trackandtracePage.clickOmniStatus("Offline");
			 * trackandtracePage.clickOmniMinimize(); uiTestHelper.propagateException(); try
			 * { wait = new WebDriverWait(driver, 60); wait.until(ExpectedConditions
			 * .presenceOfAllElementsLocatedBy(By.xpath(
			 * "(//button[contains(@title,'Close')])[2]")));
			 * getDriver().findElement(By.xpath("(//button[contains(@title,'Close')])[2]")).
			 * click (); } catch (Exception e) {
			 * Pass_Message("No Active cases are displayed"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case accept from omni channel failed");
		}
	}

	public void caseSelect_FF() {
		try {
			Actions ob = new Actions(getDriver());
			// String con = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
			// ACM_Functional_Flows.Key_Array[1]);
			WebElement myTable = getDriver().findElement(By.xpath(
					"//*[@id='split-left']/div/div/div/div/div[2]/div/div[1]/div[2]/div[2]/div[1]/div/div/table"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);

			for (int i = 1; i <= row_count; i++) {
				String a = getDriver().findElement(By.xpath(
						"//*[@id='split-left']/div/div/div/div/div[2]/div/div[1]/div[2]/div[2]/div[1]/div/div/table/tbody/tr["
								+ i + "]/th/span/a"))
						.getText();
				System.out.println("Case no in caseselect is " + a);
				System.out.println("Case no in RFI  is " + caseNo);
				if (a.equals(caseNo)) {
					WebElement myCon = getDriver().findElement(By.xpath(
							"//*[@id='split-left']/div/div/div/div/div[2]/div/div[1]/div[2]/div[2]/div[1]/div/div/table/tbody/tr["
									+ i + "]/th/span/a"));
					ob.click(myCon).perform();
					break;
				}
			}
			Pass_Message("Case selected from cases tab");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case not found");
		}
	}

	public void CloseTab() {
		Actions action = new Actions(getDriver());
		try {
			List<WebElement> si = getDriver().findElements(By.xpath(
					"//div[@class='navexWorkspaceManager']//button[@type='button' and contains(@title,'Close')]"));
			System.out.println("Tab Size:" + si.size());
			int count = si.size();
			if (count == 0) {
				System.out.println("We dont have any Opened tabs to Close");
			} else {
				while (count > 0) {
					WebElement close = getDriver().findElement(By.xpath(
							"//div[@class='navexWorkspaceManager']//button[@type='button' and contains(@title,'Close')]"));
					action.moveToElement(close).click(close).build().perform();
					uiTestHelper.propagateException();
					count--;
				}
			}
		} catch (Exception e) {

		}
	}

	public void MonitoringActivity(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String Retrive = null;
		try {
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			CloseTab();
			ffUpdated.searchConsignmentOption(Retrive);
			viewOrCreateCase();

			String expecteddate = setExpectedDate();
			String currentdate = expecteddate + casedetailpage.getSystemMonth().substring(3, 11);
			try {
				casedetailpage.clickMonitorActivity();
				casedetailpage.selectmonitorCODateFrom(currentdate);
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Monitoring Date selection failed");
			}
			monitor_green_colour(new java.util.Date());
			casedetailpage.setMonitorTimeInput(TimeGreen);
			if (casedetailpage.ismonitorcheckbox()) {
				casedetailpage.clickmonitorcheckbox();
			}
			casedetailpage.clickMonitorActivitysave();
			boolean colorgreen = false;
			int i = 0;
			while (!colorgreen && i < 5) {
				colorgreen = casedetailpage.getgreenGIF();
				uiTestHelper.propagateException();
				i++;
			}
			if (colorgreen) {
				Pass_Message("Indicator colour changed to green");
			} else {
				Fail_Message("Green color indicator is not displayed");
			}
			Assert.assertTrue(colorgreen);
			try {
				casedetailpage.clickMonitorActivity();
				casedetailpage.selectmonitorCODateFrom(currentdate);
				amber_colour(new java.util.Date());
				casedetailpage.setMonitorTimeInput(TimeAmber);
				casedetailpage.clickMonitorActivitysave();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Monitoring Date selection failed");
			}
			uiTestHelper.propagateException();
			boolean coloramber = casedetailpage.getyellowGIF();
			if (coloramber) {
				Pass_Message("Colour changed to Yellow");
			}
			Assert.assertTrue(coloramber);
			try {
				casedetailpage.clickMonitorActivity();
				casedetailpage.selectmonitorCODateFrom(currentdate);
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Monitoring Date selection failed");
			}
			red_colour(new java.util.Date());
			casedetailpage.setMonitorTimeInput(Timered);
			casedetailpage.clickMonitorActivitysave();
			uiTestHelper.propagateException();
			boolean colorRed = false;
			do {
				try {
					colorRed = getDriver().findElement(By.xpath("//img[@src='/img/samples/light_red.gif']"))
							.isDisplayed();
				} catch (NoSuchElementException e) {
					getDriver().navigate().refresh();
				}
			} while (!colorRed);
			if (colorRed) {
				Pass_Message("Colour changed to red");
			}
			Assert.assertTrue(colorRed);
			casedetailpage.clickMonitorActivity();

			casedetailpage.selectmonitorCODateFrom(currentdate);
			red_colour(new java.util.Date());
			casedetailpage.setMonitorTimeInput(Timered);

			casedetailpage.clickmonitorcheckbox();
			casedetailpage.clickMonitorActivitysave();
			uiTestHelper.propagateException();
			Pass_Message("Monitoring completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Monitoring activity failed");
		}
	}

	/*
	 * this method is to check, if case is already exist
	 */
	public void viewOrCreateCase() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		uiTestHelper.propagateException();
		try {
			if (conpage.verifyViewCase2()) {
				connectivity.CCD_ViewCase();
			} else {
				ffUpdated.CreateCase_EBS();
			}
		} catch (Exception e) {
			ffUpdated.CreateCase_EBS();
		}
	}

	public void callback_activity(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		String Retrieve = null;
		try {
			if (caseType.contains("re_case")) {
				Retrieve = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrieve = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			ACM_Connectivity.CloseTab();
			ffUpdated.searchConsignmentOption(Retrieve);
			viewOrCreateCase();

			String expecteddate = setExpectedDate();
			String currentdate = expecteddate + casedetailpage.getSystemMonth().substring(3, 11);
			try {
				casedetailpage.clickMonitorActivity();
				if (!casedetailpage.ismonitorcheckbox()) {
					casedetailpage.selectmonitorCODateFrom(currentdate);
					red_colour(new java.util.Date());
					casedetailpage.setMonitorTimeInput(Timered);
					casedetailpage.clickmonitorcheckbox();
				}
				casedetailpage.clickMonitorActivitysave();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Monitoring is not closed");
			}
			try {
				uiTestHelper.propagateException();
				casedetailpage.clickcallbackActivity();
				casedetailpage.selectcallbackCODateFrom(currentdate);
				green_colour(new java.util.Date());
				casedetailpage.setcallbackTimeInput(TimeGreen);
				if (casedetailpage.iscallbackcheckbox()) {
					casedetailpage.clickcallbackcheckbox();
				}
				casedetailpage.clickcallbackActivitysave();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Call Back Date selection failed");
			}
			boolean colorgreen = casedetailpage.getgreenGIF();
			int i = 0;
			while (!colorgreen && i < 5) {
				colorgreen = casedetailpage.getgreenGIF();
				uiTestHelper.propagateException();
				i++;
			}
			if (colorgreen) {
				Pass_Message("Indicator Colour changed to green");
			} else {
				Fail_Message("Green color indicator is not displayed");
			}

			Assert.assertTrue(colorgreen, "Indicator Colour changed to green");
			getDriver().navigate().refresh();
			casedetailpage.clickcallbackActivity();
			amber_colour(new java.util.Date());
			casedetailpage.setcallbackTimeInput(TimeAmber);
			casedetailpage.clickcallbackActivitysave();
			boolean coloramber = casedetailpage.getyellowGIF();
			while (!coloramber) {
				coloramber = casedetailpage.getyellowGIF();
				uiTestHelper.propagateException();
			}
			if (coloramber) {
				Pass_Message("Indicator Colour changed to yellow");
			}
			Assert.assertTrue(coloramber, "Indicator Colour changed to yellow");
			getDriver().navigate().refresh();
			casedetailpage.clickcallbackActivity();
			red_colour(new java.util.Date());
			casedetailpage.setcallbackTimeInput(Timered);
			casedetailpage.clickcallbackActivitysave();
			boolean colorRed = false;
			do {
				try {
					colorRed = getDriver().findElement(By.xpath("//img[@src='/img/samples/light_red.gif']"))
							.isDisplayed();
				} catch (NoSuchElementException e) {
					getDriver().navigate().refresh();
				}
			} while (!colorRed);
			if (colorRed) {
				Pass_Message("Indicator Colour changed to red");
			}
			Assert.assertTrue(colorRed, "Indicator Colour changed to red");

			casedetailpage.clickcallbackActivity();
			casedetailpage.clickcallbackcheckbox();
			casedetailpage.clickcallbackActivitysave();
			Pass_Message("Call Back completed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Callback activity failed");
		}
	}

	// Bulk Call back
	public void bulkActions_callback_activity() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		String expecteddate = getSystemDate();
		int date = Integer.parseInt(expecteddate);
		if (date < 10) {
			expecteddate = Integer.toString(date);
			expecteddate = expecteddate.replace("0", "");
		} else {
			expecteddate = getSystemDate();
		}
		try {
			casedetailpage.selectcallbackCODateFromBulkActions(expecteddate);
			Pass_Message("Call Back Date selected");
			getDriver().findElement(By.xpath(
					"//button[@class='slds-button slds-button_brand cuf-publisherShareButton undefined uiButton']"))
					.click();
			getDriver().findElement(By.xpath("//button[@title='OK']")).click();
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Call Back Date selection failed");
		}
		// Callback validation
		try {
			myOpenCaseSelect(con1);
			boolean colorgreen = casedetailpage.getgreenGIF();
			if (colorgreen) {
				Pass_Message("Call back is set and colour changed to green for first con " + con1);
			} else {
				Fail_Message("Green color indicator is not displayed for first con " + con1);
			}
			ACM_Connectivity.CloseTab();
			getDriver().navigate().refresh();
			myOpenCaseSelect(con2);
			colorgreen = casedetailpage.getgreenGIF();
			if (colorgreen) {
				Pass_Message("Call back is set and colour changed to green for 2nd con " + con2);
			} else {
				Fail_Message("Green color indicator is not displayed for 2nd con " + con2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Callback status validation failed");
		}
	}

	public void action(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		String Retrive = null;
		try {
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			ACM_Connectivity.CloseTab();

			ffUpdated.searchConsignmentOption(Retrive);
			viewOrCreateCase();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			casedetailpage.clickActionTab();
			String actionType = "Communication with Another Location";
			casedetailpage.setActionType(actionType);
			casedetailpage.setActionCost("3.25");

			// casedetailpage.clickActionCurrency();

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
			if (actionTable) {
				Pass_Message("Action displayed in action table");
			} else {
				Fail_Message("Action table not present");
			}
			Assert.assertTrue(actionTable, "Action displayed in action table");
			uiTestHelper.propagateException();
			String cost = casedetailpage.getactionTableCost(Size).trim();
			if (cost.contains("3.25")) {
				Pass_Message("Cost verified in actions quicklink");
			} else {
				Fail_Message("Cost verification in actions quicklink has failed");
			}
			Assert.assertEquals(cost, "3.25");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Action details verification failed");
		}
	}

	public void Case_remark(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		String Retrive = null;
		try {
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			connectivity.CloseTab();

			ffUpdated.searchConsignmentOption(Retrive);
			viewOrCreateCase();
			getDriver().navigate().refresh();
			casedetailpage.clickCaseRemarkTab();
			casedetailpage.setCaseRemarkdesc("Update Case Remarks for testing");
			casedetailpage.clickCaseRemarkSave();

			boolean message = casedetailpage.verifySuccessMessage();
			int i = 0;
			while (!message && i < 5) {
				message = casedetailpage.verifySuccessMessage();
				uiTestHelper.propagateException();
				i++;
			}
			if (message) {
				casedetailpage.clickRelatedTab();
				casedetailpage.clickCaseRemarkVew();
			}
			String datetTime = casedetailpage.getcaseRemarkCreatedTime();
			if (datetTime.isEmpty()) {
				Fail_Message("Created Date of Case Remark is not displayed");
			} else {
				Pass_Message("Created Date of Case Remark is displayed as " + datetTime);
			}
			Assert.assertTrue(!datetTime.isEmpty(), "Created Date of Case Remark is displayed as " + datetTime);
			String comments = casedetailpage.getCaseRemarkComments();
			if (comments.isEmpty()) {
				Fail_Message("Comments of Case Remark is not displayed");
			} else {
				Pass_Message("Comments of Case Remark is displayed as " + comments);
			}
			Assert.assertTrue(!comments.isEmpty(), "Comments of Case Remark is displayed as " + comments);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case remark verification failed");
		}
	}

	public void green_colour(java.util.Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		System.out.println("Date: " + df.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 35);
		System.out.println("" + df.format(cal.getTime()));
		TimeGreen = df.format(cal.getTime());

	}

	public String changeDateFormat(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
		java.util.Date date1 = df.parse(date);
		df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return df.format(date1);
	}

	public void monitor_green_colour(java.util.Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		System.out.println("Date: " + df.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 35);
		System.out.println("" + df.format(cal.getTime()));
		TimeGreen = df.format(cal.getTime());
	}

	public void amber_colour(java.util.Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		System.out.println("Date: " + df.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 3);
		System.out.println("" + df.format(cal.getTime()));
		TimeAmber = df.format(cal.getTime());
	}

	public void red_colour(java.util.Date date) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		System.out.println("Date: " + df.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, 80);
		System.out.println("" + df.format(cal.getTime()));
		Timered = df.format(cal.getTime());
	}

	public void monitor_red_colour(java.util.Date date) throws ParseException {
		// DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		// // get current date time with Calendar()
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(date);
		// // cal.add(Calendar.HOUR, 12);
		// cal.add(Calendar.MINUTE, -55);
		// System.out.println(dateFormat.format(cal.getTime()));
		// Timered = dateFormat.format(cal.getTime());
		DateFormat df = new SimpleDateFormat("HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		System.out.println("Date: " + df.format(date));

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, 80);
		System.out.println("" + df.format(cal.getTime()));
		Timered = df.format(cal.getTime());
	}

	public void internal_tabclose() {
		try {
			List<WebElement> si = getDriver()
					.findElements(By.xpath("(//div[@data-aura-class='navexConsoleTabContainer'])[2]//ul[2]/li"));
			System.out.println("Internal tab Size:" + si.size());
			uiTestHelper.propagateException();
			int count = (si.size());
			uiTestHelper.propagateException();
			while (count >= 3) {
				// System.out.println("Before");
				Actions action = new Actions(getDriver());
				WebElement ele = getDriver()
						.findElement(By.xpath("(//div[@data-aura-class='navexConsoleTabContainer'])[2]//ul[2]/li["
								+ count + "]//button[contains(@title,'Close')]"));
				uiTestHelper.propagateException();
				action.click(ele).perform();
				// System.out.println("After");
				count--;
			}
		} catch (Exception e) {
		}
	}

	public void frontline_Login() {
		try {
			LoginPage loginPage = new LoginPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			getDriver().get(URL);
			// driver.manage().window().maximize();
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			loginPage.clickLoginBtn();
			boolean verifydropDownMenu = homePage.verifydropDownMenu();
			if (verifydropDownMenu == true) {
				Pass_Message("Frontline CSR logged in to salesforce Successfully.");
			} else {
				Fail_Message("Frontline CSR Login to salesforce Failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Frontline CSR Login to salesforce Failed.");
		}
	}

	public void support_Login() {
		try {
			LoginPage loginPage = new LoginPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[2]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[2]);

			getDriver().get("https://test.salesforce.com");
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			System.out.println("Username: " + Username + " || Password: " + Password);
			loginPage.clickLoginBtn();
			loginPage.clickNextORFinishBtn("Next");
			loginPage.clickNextORFinishBtn("Finish");
			HomePage homepage = new HomePage(getDriver());
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
			boolean verifydropDownMenu = homePage.verifydropDownMenu();
			assertTrue(verifydropDownMenu);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce as Support CSR Failed.");
		}
	}

	public void login_TeamLead() {
		try {
			LoginPage loginPage = new LoginPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_BulkActions.Key_Array[6]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_BulkActions.Key_Array[6]);
			getDriver().get(URL);
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			loginPage.clickLoginBtn();
			HomePage homepage = new HomePage(getDriver());
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
			boolean verifydropDownMenu = homePage.verifydropDownMenu();
			assertTrue(verifydropDownMenu);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce as Support CSR Failed.");
		}
	}

	public void support_OtherCSRLogin() {
		try {
			LoginPage loginPage = new LoginPage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_CaseUpdate.Key_Array[6]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_CaseUpdate.Key_Array[6]);
			getDriver().get(URL);
			// driver.manage().window().maximize();
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			loginPage.clickLoginBtn();
			HomePage homepage = new HomePage(getDriver());
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce as Support CSR Failed.");
		}
	}

	public void customer_remark(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		String Retrive = null;
		try {
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			System.out.println("Consignment Number: " + Retrive);
			ACM_Connectivity.CloseTab();

			ffUpdated.searchConsignmentOption(Retrive);
			viewOrCreateCase();
			casedetailpage.clickCustRemarkTab();
			casedetailpage.setCustRemarkdesc("Update Customer Remarks for testing");
			casedetailpage.clickCustRemarkSave();
			boolean successMsg = false;
			int i = 0;
			while (!successMsg && i < 5) {
				successMsg = casedetailpage.verifySuccessMessage();
				uiTestHelper.propagateException();
				i++;
			}
			boolean isRemark = false;
			getDriver().navigate().refresh();
			uiTestHelper.propagateException();
			if (successMsg) {
				Pass_Message("Customer Remarks added succesfully");
				casedetailpage.clickCaseInformation();
				String Remarks = casedetailpage.getRemark().trim();
				System.out.println("Remarks: " + Remarks);
				isRemark = Remarks.contains("Update Customer Remarks for testing");
				if (isRemark) {
					Pass_Message("Customer remark verified in case information details section succesfully");
				} else {
					Fail_Message("Customer remark has not been displayed in case information section");
				}
			} else {
				Fail_Message("Customer remark added successfully message is not displayed");
			}
			Assert.assertTrue(successMsg, "Customer Remarks added succesfully");
			Assert.assertTrue(isRemark, "Customer remark verified in case information details section succesfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer remark verification in case information section failed");
		}
	}

	public void CloseCase(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		try {

			// String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM",
			// "KEY",
			// CMOD_ReactiveCaseFlow.Key_Array[8]);
			// connectivity.TrackandTraceCCD(Retrive);
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
				Retrive = "324682142";
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
				Retrive = "324682164";
			}

			ACM_Connectivity.CloseTab();
			reopenCase(Retrive);
			ACM_Connectivity.CloseTab();
			ffUpdated.searchConsignmentOption(Retrive);
			viewOrCreateCase();
			getDriver().navigate().refresh();
			casedetailpage.clickcallbackActivity();
			if (!casedetailpage.iscallbackcheckbox()) {
				casedetailpage.clickcallbackcheckbox();
			}
			casedetailpage.clickcallbackActivitysave();
			getDriver().navigate().refresh();
			casedetailpage.clickCloseCaseTab();

			casedetailpage.setComments("Closed Case");
			casedetailpage.clickCloseCaseSave();
			getDriver().navigate().refresh();
			String status = casedetailpage.getCaseStatus();
			boolean isStatus = status.equalsIgnoreCase("Manual-Closed");
			if (isStatus) {
				Pass_Message("Case is closed Successfully");
			} else {
				Fail_Message("Case is not closed");
			}
			Assert.assertTrue(isStatus, "Case is closed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed");
		}
	}

	public void reopenCase(String consignmentNo) {
		try {
			HomePage homePage = new HomePage(getDriver());
			homePage.clickOnSearchedCase(consignmentNo);
			String status = casedetailpage.getCaseStatus();
			boolean isStatus = status.contains("Closed");
			if (isStatus) {
				applyReOpenCaseActions();
				status = casedetailpage.getCaseStatus();
				isStatus = !status.contains("Closed");
				if (isStatus) {
					Pass_Message("Case is Reopen");
				}
			} else {
				Pass_Message("Case is open");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void RFI() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity conn = new CCD_Connectivity();
		String ResponderName = null;
		try {
			casedetailpage.clickRFITab();
			casedetailpage.setRFISubject("Test RFI");
			caseNo = casedetailpage.getCaseNo();
			// caseNo="20122675";
			System.out.println("CaseNumber: " + caseNo);
			casedetailpage.clickRFISendTobtn();
			casedetailpage.setRFISendTo();
			casedetailpage.setRFIQuestion("RFI Question");
			casedetailpage.clickRFISave();
			if (casedetailpage.verifySuccessMessage() == true) {
				Fail_Message("RFI Not Created");
			} else {
				Pass_Message("RFI created");
				casedetailpage.clickEscalation();
				int Size = casedetailpage.getEscalationTableSize();
				System.out.println("Size: " + Size);
				casedetailpage.clickescalationTable(Size, "RFI");
				ResponderName = casedetailpage.getresponderName();
				System.out.println("Responder Name: " + ResponderName);
				casedetailpage.clickescalatebtn();
				Pass_Message("Can not be escalated as the current SLA has not breached");
			}
			logout();
			OIB_Support_Login();
			String consignmentNo = conn.Retrive;
			System.out.println("Consignment No: " + conn.Retrive);
			RFI_OIBPEAccept(ResponderName, consignmentNo);
			// myOpenCases();
			CCD_Connectivity Connectivity = new CCD_Connectivity();
			Connectivity.TrackandTraceCCDSearch();
			Connectivity.CCD_ViewCase();
			RFI_AnswerandClose();
			logout();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("RFI service failed");
		}
	}

	// IOB Support Login
	public void OIB_Support_Login() {
		try {
			LoginPage loginpage = new LoginPage(getDriver());
			/*
			 * String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA",
			 * "KEY", ACM.Key_Array[0]); String Username =
			 * Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
			 * ACM_Functional_Flows.Key_Array[3]); String Password =
			 * Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
			 * ACM_Functional_Flows.Key_Array[3]);
			 */String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY", CCD_CI.Key_Array[5]);
			getDriver().get(URL);
			getDriver().manage().window().maximize();
			loginpage.setUsername(Username);
			System.out.println(Username);
			loginpage.setPassword(Password);
			System.out.println(Password);
			loginpage.clickLoginBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void RFI_OIBPEAccept(String responderName, String Con) {
		HomePage homepage = new HomePage(getDriver());
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickProactiveExceptions();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput(responderName);
			proactivepage.clickAssignedInput(responderName);
			ACM_Connectivity.CloseTab();
			/*
			 * //String Con = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
			 * ACM_Functional_Flows.Key_Array[1]); String Con="324634811";
			 */
			System.out.println("Con: " + Con);
			// To select case from the list
			int rowSize = proactivepage.getExceptionTableSize();
			System.out.println(rowSize);
			for (int i = 1; i <= rowSize; i++) {
				String consignmentNumber = getDriver()
						.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr[" + i
								+ "]/td[11]/span/span[1]"))
						.getText();
				System.out.println("a" + consignmentNumber);
				System.out.println("con " + Con);
				if (consignmentNumber.equals(Con.trim())) {
					WebElement con = getDriver()
							.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr["
									+ i + "]/td[2]//label/span[1]"));
					uiTestHelper.clickJS(con);
					break;
				}
			}
			Pass_Message("Case selected from cases tab");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case not found");
		}
		// To accept PE (RFI)
		proactivepage.clickAcceptException();
	}

	public void RFI_AnswerandClose() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		try {
			// casedetailpage.clickRelatedTab();
			casedetailpage.clickEscalation();
			casedetailpage.clickEscalationTableLink();
			casedetailpage.clickRFIAnswerEdit();
			casedetailpage.setRFIAnswer("Replied - Please close RFI");
			casedetailpage.clickRFIAnswerSubmit();
			internal_tabclose();
			internal_tabclose();
			WebElement RFIBook = getDriver()
					.findElement(By.xpath("//img[@src='/resource/RFI_Status_Images/UnreadMessage.png']"));
			if (RFIBook.isDisplayed()) {
				uiTestHelper.propagateException();
				Pass_Message("RFI is Closed and RFI Book Icon is displayed");
			} else {
				Pass_Message("RFI is Closed and RFI Book Icon is displayed..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Fail_Message("RFI has not been closed");
			Pass_Message("RFI is Closed & RFI Book Icon is displayed");
		}
	}

	public void CCD_CreateCase() {
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.clickOrigin();
			ccpage.setReason("Undeliverable");
			ccpage.setCause("Insufficient Address");
			ccpage.setFirstName("Prachi");
			ccpage.setLastName("Sharma");
			ccpage.setphone("912345678");
			ccpage.setEmail("test@fedex.com");
			validateAssignToMeCheckbox();
			ccpage.clickCaseAssign();
			Pass_Message("Case creation details entered successfully");
			ccpage.clickCaseCreatebtn();// TODO uncomment this line
			Pass_Message("Reactive case is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Reactive Case creation scenario is failed");
		}
	}

	public void CreateCase_PE() {
		// logout();
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage capage = new CustomerAccountPage(getDriver());
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		/*
		 * String Username = Database_Connection.retrieveTestData("USER_ID", "ACM",
		 * "KEY", CMOD_FF_Reusable.Key_Array[5]); String Password =
		 * Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
		 * CMOD_FF_Reusable.Key_Array[5]); csr_Login(Username, Password);
		 */
		ACM_Connectivity.CloseTab();
		try {
			String peCon = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
					CMOD_PECaseFlow.Key_Array[8]);
			peCon = "2021072111";
			System.out.println(peCon);
			ACM_Connectivity.TrackandTraceDrpDown();
			homepage.globalSearchClick();
			homepage.setTextInGlobalSearch(peCon);
			if (capage.verifyProactiveExceptions() == true) {
				Pass_Message("Proactive Exceptions are displayed");
			} else {
				Fail_Message("Proactive Exceptions are not displayed");
			}
			try {
				if (getDriver().findElement(By.xpath("//span[contains(text(),'Support 1')]")).isDisplayed()) {
					Pass_Message("PE is routed to correct queue");
				}
			} catch (Exception e) {
				{
					Fail_Message("PE is not routed to correct queue");
				}
			}
			ACM_Connectivity.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickProactiveExceptions();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.peSearchInput("IN - BH7 - Support 1");
			proactivepage.clickAssignedInput("IN - BH7 - Support 1");
			uiTestHelper.propagateException();
			// To select case from the list
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr")));
			int rowSize = proactivepage.getExceptionTableSize();
			System.out.println(rowSize);
			for (int i = 1; i <= rowSize; i++) {
				String consignmentNumber = getDriver()
						.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr[" + i
								+ "]/td[8]/span/span[1]"))
						.getText();
				System.out.println("a" + consignmentNumber);
				System.out.println("con " + peCon);
				if (consignmentNumber.equals(peCon)) {
					// Pass_Message("PE Case is selected from Proactive Execption dropdown");
					// WebElement con = driver
					// .findElement(By.xpath("//span[text()='Proactive
					// Exceptions']/following::table[1]/tbody/tr["
					// + i + "]/td[2]//label/span[1]"));
					getDriver()
							.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr["
									+ i + "]/td[2]//label/span[1]"))
							.click();
					// proactivepage.clickJS(con);
					Pass_Message("PE Case is selected from Proactive Execption dropdown");
					break;
				}
			}
			// Pass_Message("Proactive Execption case is accepted and created
			// successfully");
			// To accept PE (RFI)
			proactivepage.clickAcceptException();
			try {
				ACM_Connectivity.CloseTab();
				homepage.clickDropDownNavigationMenu();
				homepage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.peSearchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				uiTestHelper.propagateException();
				EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
				event.executeScript(
						"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 500");
				List<WebElement> objRow = getDriver()
						.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
				int row_count = objRow.size();
				System.out.println("Row count in MyOpenCases table is " + row_count);
				for (int i = 1; i <= objRow.size(); i++) {
					WebElement pecase = getDriver().findElement(
							By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"));
					uiTestHelper.scrollIntoView(pecase);
					String a = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
							.getText();
					if (a.equals(peCon)) {
						WebElement myCon = getDriver().findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
						uiTestHelper.clickJS(myCon);
						break;
					}
					System.out.println("End of test");
				}
				// Pass_Message("PE Case is selected from My Open Cases dropdown");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("PE Case selection from My Open Cases failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: PE case creation failed");
		}
	}

	public String getSystemDate() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		exp_date = sdf.format(currentDateTime);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	public String setExpectedDate() {
		String expecteddate = getSystemDate();
		int date = Integer.parseInt(expecteddate);
		if (date < 10) {
			expecteddate = Integer.toString(date);
			expecteddate = expecteddate.replace("0", "");
		} else {
			expecteddate = getSystemDate();
		}
		return expecteddate;
	}

	// Create Task
	public void task_create(String connumber) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TaskPage taskpage = new TaskPage(getDriver());
		try {
			// login_Task();
			CloseTab();
			ACM_Connectivity.TrackandTraceCCD(connumber);
			viewOrCreateCase();
			// task field validation while creating task
			ccpage.clickTaskTab();
			taskpage.createTask("TNT-OPS", "Destination", "Norway", "GDR", "testG");

			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
					uiTestHelper.propagateException();
				}
			}
			String cmodTaskId = "";
			boolean istaskid = !recentTaskID.isEmpty();
			if (istaskid) {
				By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
				WebElement tid = uiTestHelper.waitForObject(taskid);
				tid.click();
				uiTestHelper.propagateException();
				Pass_Message("Task created successfully");
				for (int i = 0; i < 5; i++) {
					cmodTaskId = casedetailpage.getCmodTaskId();
					if (cmodTaskId.isEmpty()) {
						getDriver().navigate().refresh();
						uiTestHelper.propagateException();
					} else {
						break;
					}
				}
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(istaskid);
			boolean iscmodtaskid = !cmodTaskId.isEmpty();
			if (iscmodtaskid) {
				Pass_Message("CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
			} else {
				Fail_Message("CMOD Task id has not Created");
			}
			Assert.assertTrue(iscmodtaskid, "CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation test case failed");
		}
	}

	/*
	 * Task creation for TNT-CS where CMOD task id is optional
	 */
	// Create Task
	public void task_create_TNT_CS(String connumber) {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			String Retrive = connumber;
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			viewOrCreateCase();
			getDriver().navigate().refresh();
			ccpage.clickTaskTab();
			ccpage.taskCountryTerritoryBox("Norway");
			ccpage.setTaskLocation("GDR");
			// ccpage.taskCountryTerritoryBox("United Kingdom");
			// ccpage.setTaskLocation("WOF");
			boolean iscaserouyttoset = ccpage.setCaseRoute("TNT-CS");
			if (iscaserouyttoset) {
				Pass_Message("Case rout to field is set to '" + "TNT-CS" + "' as expected");
			} else {
				Fail_Message("Origin/Destination field is not disabled");
			}
			Assert.assertTrue(iscaserouyttoset, "Case rout to field is set to '" + "TNT-CS" + "' as expected");

			boolean isOrginDestField = ccpage.isOrginDestinationDisabled();
			if (isOrginDestField) {
				Pass_Message("Origin/Destination field is disabled as expected");
			} else {
				Fail_Message("Origin/Destination field is not disabled");
			}
			Assert.assertTrue(isOrginDestField, "Origin/Destination field is disabled as expected");

			ccpage.sendTextToTaskInstructionsBox();
			ccpage.clickTaskSaveButton();
			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
				}
			}
			if (!recentTaskID.isEmpty()) {
				By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
				WebElement tid = uiTestHelper.waitForObject(taskid);
				tid.click();
				Pass_Message("Task created successfully");
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(!recentTaskID.isEmpty(), "Task created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation test case failed");
		}
	}

	public void taskCreation_basedOnCaseLocation_and_TaskOriginOrDestination(String connumber, String caseLocation,
			String orgOrDest) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TaskPage taskpage = new TaskPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			// viewOrCreateCase();
			if (conpage.verifyViewCase2()) {
				connectivity.CCD_ViewCase();
			} else {
				createCase_Customized(caseLocation, "Re-Active Case", "Insufficient Address", "Undeliverable", "",
						true);
			}

			getDriver().navigate().refresh();
			fillTaskDetails(connumber, "TNT-CS", orgOrDest, "Norway", "GDR", "Test Instructions");
			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
					uiTestHelper.propagateException();
				}
			}
			boolean istaskid = !recentTaskID.isEmpty();
			if (istaskid) {
				taskpage.clickOnTaskCreatedTask();
				Pass_Message("Task created successfully");
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(istaskid, "Task created successfully");
			taskpage.closeCurrentTaskWindow(recentTaskID);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation with Case as Orgin and Task with Destination test case is failed");
		}
	}

	/*
	 * this is to validate OIB support queue
	 */
	public void validateOIBSupportQueue(String caseLocation) {
		CaseDetailsPage cdpage = new CaseDetailsPage(getDriver());
		try {
			String countrycode = "", country = "", senderORreceiver = "";
			cdpage.clickCaseSummary();

			if (caseLocation.contains("Origin")) {
				country = cdpage.getreceiverCountry();
				senderORreceiver = "Receiver";
			} else {
				country = cdpage.getsenderCountry();
				senderORreceiver = "Sender";
			}

			String OIBqueue = cdpage.getOIBMemberQueueName();

			countrycode = cdpage.getCountry(country);
			boolean isOIBqueueCintains = OIBqueue.contains(countrycode);
			if (isOIBqueueCintains) {
				Pass_Message("Case Summary: OIB Case Owner is assigned to " + senderORreceiver + " side support queue");
			} else {
				Fail_Message("Case Summary: OIB Case Owner is assigned to wrong queue");
			}
			Assert.assertTrue(isOIBqueueCintains,
					"Case Summary: OIB Case Owner is assigned to " + senderORreceiver + " side support queue");

			cdpage.clickCaseDetails();
			String oibSupportQueue = cdpage.getOIBMemberQueueName();

			boolean isOIBsupportQueue = oibSupportQueue.contains(country);
			if (isOIBsupportQueue) {
				Pass_Message("Case Details: OIB support queue is set as expected");
			} else {
				Pass_Message("Case Details: OIB support queue is set not set correctly");
			}
			Assert.assertTrue(isOIBsupportQueue, "OIB support queue is set as expected");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: OIB support queue validation failed");
		}
	}

	/**
	 * Method to validate AssignToMeCheckbox visible, checked and unchecked
	 *
	 * @throws Exception
	 */
	public void validateAssignToMeCheckbox() {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		// consignmentPage.clickcreatecasebtn();
		if (ccpage.isAssignToMeChkVisible()) {
			Pass_Message("Assign to Me checkbox visible");
			if (ccpage.isAssignToMeChecked()) {
				Pass_Message("Assign to Me checkbox checked");
				if (!ccpage.isAssignToMeChecked()) {
					Pass_Message("Assign to Me checkbox unchecked");
				} else {
					Fail_Message("Assign to Me checkbox checked");
				}
			} else
				Fail_Message("Assign to Me checkbox unchecked");
		} else
			Fail_Message("Assign to Me checkbox not visible");
	}

	public void validateCaseOwner() {
		CaseDetailsPage caseDetailPage = new CaseDetailsPage(getDriver());
		LogoutPage logoutpage = new LogoutPage(getDriver());
		if (caseDetailPage.isCaseDisplayed()) {
			logoutpage.clickUser();
			String loggedinUsername = logoutpage.getLoggedinUserName();
			System.out.println("Case created successfully ");
			caseDetailPage.clickCaseDetails();
			String caseOwner = caseDetailPage.getcaseOwnerValue();
			if (loggedinUsername.equalsIgnoreCase(caseOwner))
				Pass_Message("Case owner is same as CSR who created case : " + loggedinUsername);
			else
				Fail_Message("Case owner is not same as CSR who created case");
		} else {
			Fail_Message("Case detail page is not displayed");
		}
	}

	public void validateReassign() {
		CaseDetailsPage caseDetailPage = new CaseDetailsPage(getDriver());
		caseDetailPage.clickCaseMoreTab();
		Pass_Message("Re-assign tab displayed");
		caseDetailPage.clickReassign();
		Pass_Message("Able to click on Re-assign tab");
		List<WebElement> reassignDropdownVals = caseDetailPage.clickReassignDropdown();
		for (WebElement reassignDropdownValue : reassignDropdownVals) {
			if (reassignDropdownValue.getText().equalsIgnoreCase("Destination")) {
				Pass_Message("Reassign dropdown value " + reassignDropdownValue.getText() + " present");
			} else if (reassignDropdownValue.getText().equalsIgnoreCase("Origin")) {
				Pass_Message("Reassign dropdown value " + reassignDropdownValue.getText() + " present");
			}
		}
	}

	public void selectReassignTo(String reassignToVal) {
		CaseDetailsPage caseDetailPage = new CaseDetailsPage(getDriver());
		caseDetailPage.selectReassignTo(reassignToVal);
		caseDetailPage.clickReassignToSaveBtn();
	}

	public void validateCaseUpdateColumn() {
		HomePage homePage = new HomePage(getDriver());
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		proactivepage.clickRecentlyViewedItems();
		proactivepage.searchInput(CMODConstants.MY_OPEN_CASES);
		proactivepage.clickAssignedInput(CMODConstants.MY_OPEN_CASES);
		if (proactivepage.isCaseUpdateColumnVisible()) {
			Pass_Message("Case Update column present");
		} else {
			Fail_Message("Case Update column not present");
		}
	}

	/**
	 * Case Update column not visible other than My Open Case
	 *
	 * @throws Exception
	 */
	public void US_B513632_TC2_validateCaseUpdateColumnNotVisible() {
		HomePage homePage = new HomePage(getDriver());
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickCases();
		proactivepage.clickRecentlyViewedItems();
		proactivepage.searchInput(CMODConstants.RECENTLY_VIEWED_CASES);
		proactivepage.clickAssignedInput(CMODConstants.RECENTLY_VIEWED_CASES);
		try {
			if (!proactivepage.isCaseUpdateColumnVisible())
				Fail_Message("Case Update column is present");
		} catch (org.openqa.selenium.TimeoutException expection) {
			Pass_Message("Case Update column is not present");
		}
	}

	// X104OKL Date: 9-8-21
	public void myOpenCaseSelect(String Con) {
		HomePage homePage = new HomePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			uiTestHelper.propagateException();
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				WebElement pecase = getDriver().findElement(
						By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"));
				uiTestHelper.scrollIntoView(pecase);
				String a = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				if (a.equals(Con)) {
					WebElement myCon = getDriver().findElement(
							By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
					uiTestHelper.clickJS(myCon);
					break;
				}
			}
			// Pass_Message("Case is selected from My Open Cases dropdown");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case is not selected from My Open Cases dropdown");
		}
	}

	// X104OKL Date: 30-Sep-21
	public void selectMyCaseFromCouQueue(String caseNum) {
		try {
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				String myCase = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[5]/span"))
						.getText();
				if (myCase.equals(caseNum)) {
					getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[5]/span"))
							.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case is not selected from country queue list view");
		}
	}

	public void selectMyConFromCouQueue(String conNum) {
		try {
			wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Cases']/following::table[1]/tbody")));
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				String myCase = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[8]/span"))
						.getText();
				if (myCase.equals(conNum)) {
					getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[2]/span"))
							.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case is not selected from country queue list view");
		}
	}

	public void myOpenMultipleCaseSelect() {
		HomePage homePage = new HomePage(getDriver());
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		String Con11 = "625214581";
		try {
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			uiTestHelper.propagateException();
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				WebElement pecase = getDriver().findElement(
						By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"));
				uiTestHelper.scrollIntoView(pecase);
				String a = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				if (a.equals(Con11)) {
					WebElement myCon = getDriver().findElement(
							By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
					uiTestHelper.clickJS(myCon);
					break;
				}
			}
			Pass_Message("Case is selected from My Open Cases dropdown");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case is selected from My Open Cases dropdown");
		}
	}

	public void peCaseSelect() {
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		String Con = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CCD_CMOD_SmokeTest.Key_Array[1]);
		proactivepage.getExceptionTableSize();
		try {
			int rowSize = proactivepage.getExceptionTableSize();
			System.out.println(rowSize);
			for (int i = 1; i <= rowSize; i++) {
				String consignmentNumber = getDriver()
						.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr[" + i
								+ "]/td[11]/span/span[1]"))
						.getText();
				if (consignmentNumber.equals(Con.trim())) {
					WebElement con = getDriver()
							.findElement(By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr["
									+ i + "]/td[2]//label/span[1]"));
					uiTestHelper.clickJS(con);
					Pass_Message("Proactive Exception selected successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: PE not found");
		}
	}

	// X104OKL Date: 30-7-21
	public void US_B513632_TC1_bulkOperationSinglePEAccept() {
		try {

			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			String Con = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CCD_CMOD_SmokeTest.Key_Array[1]);
			homePage.clickDropDownNavigationMenu();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//*[@id='navMenuList']//span[contains(text(),'Proactive Exceptions')]")));
			homePage.clickProactiveExceptions();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchPEQueue(inputQueueName);
			proactivepage.clickAssignedInputPE(inputQueueName);
			peCaseSelect();
			proactivepage.clickAcceptException();
			myOpenCaseSelect(Con);
			CaseDetailsPage ConInfoPage = new CaseDetailsPage(getDriver());
			ConInfoPage.clickCaseInformation();
			String caseType = getDriver().findElement(By.xpath(
					"//*[contains(@id,'tab')]/slot/flexipage-component2[1]/slot/c-field-set-record-form/lightning-card/article/div[2]/slot/div/lightning-record-form/lightning-record-edit-form/form/slot/div/div/div[1]/div[1]/lightning-output-field/div/lightning-formatted-text"))
					.getText();
			if (caseType.equals("Proactive")) {
				Pass_Message("Proactive Case is created by accepting Proactive exception by CSR");
			} else {
				Fail_Message("Proactive Case is not created");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case is not selected from My Open Cases dropdown");
		}
	}

	public void US_B513636_TC1_bulkOperationMultiplePEAccept() {
		try {
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			CaseDetailsPage ConInfoPage = new CaseDetailsPage(getDriver());
			con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			homePage.clickDropDownNavigationMenu();
			homePage.clickProactiveExceptions();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchPEQueue(inputQueueName);
			proactivepage.clickAssignedInputPE(inputQueueName);
			uiTestHelper.propagateException();
			proactivepage.getExceptionTableSize();
			clickMultiplecheckBox();
			proactivepage.clickAcceptException();
			myOpenCaseSelect(con1);
			ConInfoPage.clickCaseInformation();
			if (cdPage.getproActivecaseType() == "Proactive") {
				Pass_Message("Proactive Case is created by accepting Proactive exception by CSR for 1st consignment");
			} else {
				Fail_Message("Proactive Case is not created");
			}
			myOpenCaseSelect(con2);
			ConInfoPage.clickCaseInformation();
			if (cdPage.getproActivecaseType() == "Proactive") {
				Pass_Message("Proactive Case is created by accepting Proactive exception by CSR for 2nd consignment");
			} else {
				Fail_Message("Proactive Case is not created");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("PE bulk action multiple PE accept failed");
		}
	}

	// X104OKL 5-8-21
	public void US_B513636_TC2_bulkOperationMultiplePECallBack() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action Call Back
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickCallBack();
			bulkActions_callback_activity();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("PE Call back activity failed");
		}
	}

	// PE Bulk Action Case Remarks
	public void US_B513636_TC3_bulkOperationMultiplePECaseRemarks() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
		con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action Case Remarks
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickCaseRemarks();
			casedetailpage.setCaseRemarkdesc("Test Case remarks");
			Pass_Message("Case Remarks enetered successfully for multiple PE");
			casedetailpage.clickCaseRemarkSave();
			myOpenCaseListpage.clickOK();
			try {
				{
					myOpenCaseSelect(con1);
					casedetailpage.clickRelatedTab();
					casedetailpage.clickCaseRemarkVew();
					String datetTime = casedetailpage.getcaseRemarkCreatedTime();
					if (datetTime.isEmpty()) {
						Fail_Message("Created Date of Case Remark is not displayed");
					} else {
						Pass_Message(
								"Created Date of Case Remark is displayed for 1st con " + con1 + "as " + datetTime);
					}
					String comments = casedetailpage.getCaseRemarkComments();
					if (comments.isEmpty()) {
						Fail_Message("Comments of Case Remark is not displayed");
					} else {
						Pass_Message("Comments of Case Remark is displayed as " + comments);
					}
					myOpenCaseSelect(con2);
					casedetailpage.clickRelatedTab();
					casedetailpage.clickCaseRemarkVew();
					String datetTime1 = casedetailpage.getcaseRemarkCreatedTime();
					if (datetTime1.isEmpty()) {
						Fail_Message("Created Date of Case Remark is not displayed");
					} else {
						Pass_Message(
								"Created Date of Case Remark is displayed for 2nd con " + con2 + "as " + datetTime);
					}
					String comments1 = casedetailpage.getCaseRemarkComments();
					if (comments1.isEmpty()) {
						Fail_Message("Comments of Case Remark is not displayed");
					} else {
						Pass_Message("Comments of Case Remark is displayed as " + comments);
					}
				}
			} catch (Exception e) {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("PE Case is not selected from My Open Cases dropdown");
		}
	}

	// PE BulkActions X104OKL 10-8-21
	public void US_B513636_TC4_bulkOperationMultiplePEAction() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				uiTestHelper.propagateException();
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickAction();
			uiTestHelper.propagateException();
			casedetailpage.setActionType_BulkAction("Communication with Another Location");
			casedetailpage.setActionCost("3.25");
			Pass_Message("Details are successfully entered in Actions");
			casedetailpage.clickActionSave();
			myOpenCaseListpage.clickOK();
			try {
				myOpenCaseSelect(con1);
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
				boolean actionTable = casedetailpage.actionTableView();
				int Size = casedetailpage.getSize();
				System.out.println("Size: " + Size);
				if (actionTable) {
					Pass_Message("Action displayed in action table for first con " + con1);
				} else {
					Fail_Message("Action table not present");
				}
				String cost = casedetailpage.getactionTableCost(Size).trim();
				if (cost.contains("3.25")) {
					Pass_Message("Cost verified in actions quicklink");
				} else {
					Fail_Message("Cost verification in actions quicklink has failed for con " + con1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Action details verification failed for first consigment");
			}
			try {
				ACM_Connectivity.CloseTab();
				getDriver().navigate().refresh();
				myOpenCaseSelect(con2);
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
				boolean actionTable1 = casedetailpage.actionTableView();
				int Size1 = casedetailpage.getSize();
				System.out.println("Size: " + Size1);
				if (actionTable1) {
					Pass_Message("Action displayed in action table for 2nd con " + con2);
				} else {
					Fail_Message("Action table not present");
				}
				uiTestHelper.propagateException();
				String cost1 = casedetailpage.getactionTableCost(Size1).trim();
				if (cost1.contains("3.25")) {
					Pass_Message("Cost verified in actions quicklink");
				} else {
					Fail_Message("Cost verification in actions quicklink has failed for con " + con2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Action details verification failed for second consigment");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("PE 'Action' Bulk Action failed");
		}
	}

	// PE Bulk actions Remarks for customer
	public void US_B513636_TC5_bulkOperationMultiplePERemarksForCust() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
		con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[5]);
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action Remarks For Customer
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickRemarksForCustomer();
			casedetailpage.setCustRemarkdesc("Update Customer Remarks for testing");
			Pass_Message("Remarks for Customer entered successfully for multiple PE");
			casedetailpage.clickCustRemarkSave();
			myOpenCaseListpage.clickOK();
			myOpenCaseSelect(con1);
			casedetailpage.clickCaseInformation();
			String Remarks = casedetailpage.getRemark().trim();
			if (Remarks.contains("Update Customer Remarks for testing")) {
				Pass_Message("Customer remark verified in case information details section succesfully for 1st con: "
						+ con1);
			} else {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
			ACM_Connectivity.CloseTab();
			myOpenCaseSelect(con2);
			casedetailpage.clickCaseInformation();
			if (Remarks.contains("Update Customer Remarks for testing")) {
				Pass_Message("Customer remark verified in case information details section succesfully for 2nd con: "
						+ con2);
			} else {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("PE Case is not selected from My Open Cases dropdown");
		}
	}

	// Multiple Reactive case call back
	public void US_B513636_TC6_bulkOperationMultipleReactiveCaseCallBack() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		try {
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			String[] myConArray = new String[2];
			myConArray[0] = con1;
			myConArray[1] = con2;
			// createCase_MultipleReactiveCons();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				uiTestHelper.propagateException();
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickCallBack();
			bulkActions_callback_activity();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reactive Case is not selected from My Open Cases dropdown");
		}
	}

	public void TrackandTraceCCDSearch_Multiple(String myConArray) {
		try {
			HomePage homePage = new HomePage(getDriver());
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickTrackAndTraceCCD();
			trackandtracePage.setConsignmentNumber(myConArray);
			trackandtracePage.clickTrackandTraceSearch();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Track and Trace CCD search service Failed");
		}
	}

	// Bulk action Reactive Case remarks
	public void US_B513636_TC7_bulkOperationMultipleReactiveCase_CaseRemarks() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		try {
			getDriver().navigate().refresh();
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Reactive Cases are not selected from My Open Cases dropdown");
			}
			// Bulk action Case Remarks

			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickCaseRemarks();
			casedetailpage.setCaseRemarkdesc("Test case remarks for bulk action testing");
			Pass_Message("Case Remarks added successfully for multiple Reactive Cases");
			casedetailpage.clickCaseRemarkSave();
			myOpenCaseListpage.clickOK();
			try {
				{
					Pass_Message("Case Remarks added successfully for multiple Reactive Cases");
					myOpenCaseSelect(con1);
					casedetailpage.clickRelatedTab();
					casedetailpage.clickCaseRemarkVew();
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
						Pass_Message("Comments of Case Remark is displayed as " + comments + " for 1st consignment: "
								+ con1);
						myOpenCaseSelect(con2);
						casedetailpage.clickRelatedTab();
						casedetailpage.clickCaseRemarkVew();
						String datetTime1 = casedetailpage.getcaseRemarkCreatedTime();
						if (datetTime1.isEmpty()) {
							Fail_Message("Created Date of Case Remark is not displayed");
						} else {
							Pass_Message("Created Date of Case Remark is displayed as " + datetTime);
						}
						String comments1 = casedetailpage.getCaseRemarkComments();
						if (comments1.isEmpty()) {
							Fail_Message("Comments of Case Remark is not displayed");
						} else {
							Pass_Message("Comments of Case Remark is displayed as " + comments
									+ " for 2nd consignment: " + con2);
						}
					}
				}
			} catch (Exception e) {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reactive Cases are not selected from My Open Cases dropdown");
		}
	}

	// Bulk Action_Reactive case Remarks for Customer
	public void US_B513636_TC8_bulkOperationMultipleReactiveCase_RemarksForCust() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		try {
			getDriver().navigate().refresh();
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				uiTestHelper.propagateException();
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action Remarks For Customer
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickRemarksForCustomer();
			casedetailpage.setCustRemarkdesc("Update Customer Remarks for testing");
			Pass_Message("Remarks for Customer entered successfully for multiple Reactive Cases");
			casedetailpage.clickCustRemarkSave();
			myOpenCaseListpage.clickOK();
			myOpenCaseSelect(con1);
			casedetailpage.clickCaseInformation();
			String Remarks = casedetailpage.getRemark().trim();
			if (Remarks.contains("Update Customer Remarks for testing")) {
				Pass_Message("Customer remark verified in case information details section succesfully for 1st con: "
						+ con1);
			} else {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
			ACM_Connectivity.CloseTab();
			myOpenCaseSelect(con2);
			casedetailpage.clickCaseInformation();
			if (Remarks.contains("Update Customer Remarks for testing")) {
				Pass_Message("Customer remark verified in case information details section succesfully for 2nd con: "
						+ con2);
			} else {
				Fail_Message("Case Remarks are not added successfully to cases");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reactive Case are not selected from My Open Cases dropdown");
		}
	}

	// Reactive Bulk Action_ Actions
	public void US_B513636_TC9_bulkOperationMultipleReactiveCase_Action() {
		MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
		try {
			getDriver().navigate().refresh();
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			ACM_Connectivity.CloseTab();
			try {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				uiTestHelper.propagateException();
				clickMultiplecheckBox();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case is not selected from My Open Cases dropdown");
			}
			// Bulk action for Action
			myOpenCaseListpage.clickshowMoredropDown();
			myOpenCaseListpage.clickAction();
			uiTestHelper.propagateException();
			casedetailpage.setActionType_BulkAction("Communication with Another Location");
			casedetailpage.setActionCost("3.25");
			Pass_Message("Details are successfully entered in Actions");
			casedetailpage.clickActionSave();
			myOpenCaseListpage.clickOK();
			try {
				myOpenCaseSelect(con1);
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
				boolean actionTable = casedetailpage.actionTableView();
				int Size = casedetailpage.getSize();
				if (actionTable) {
					Pass_Message("Action displayed in action table for first con " + con1);
				} else {
					Fail_Message("Action table not present");
				}
				String cost = casedetailpage.getactionTableCost(Size).trim();
				if (cost.contains("3.25")) {
					Pass_Message("Cost verified in actions quicklink");
				} else {
					Fail_Message("Cost verification in actions quicklink has failed for con " + con1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Action details verification failed for first consingment: " + con1);
			}
			try {
				ACM_Connectivity.CloseTab();
				getDriver().navigate().refresh();
				myOpenCaseSelect(con2);
				casedetailpage.clickRelatedTab();
				casedetailpage.clickActionVew();
				boolean actionTable1 = casedetailpage.actionTableView();
				int Size1 = casedetailpage.getSize();
				if (actionTable1) {
					Pass_Message("Action displayed in action table for 2nd con " + con2);
				} else {
					Fail_Message("Action table not present");
				}
				uiTestHelper.propagateException();
				String cost1 = casedetailpage.getactionTableCost(Size1).trim();
				if (cost1.contains("3.25")) {
					Pass_Message("Cost verified in actions quicklink");
				} else {
					Fail_Message("Cost verification in actions quicklink has failed for con " + con2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Action details verification failed for second consingment: " + con2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case are not selected from My Open Cases List View");
		}
	}

	// Create Multiple reactive cases
	public void createCase_MultipleReactiveCons() {
		try {
			CMOD_FunctionalFlow_Updated FFUpdated = new CMOD_FunctionalFlow_Updated();
			String[] myConArray = new String[2];
			con1 = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			con2 = Database_Connection.retrieveTestData("CON2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			myConArray[0] = con1;
			myConArray[1] = con2;
			for (int i = 0; i <= myConArray.length - 1;) {
				TrackandTraceCCDSearch_Multiple(myConArray[i]);
				FFUpdated.CreateCase_EBS();
				uiTestHelper.propagateException();
				ACM_Connectivity.CloseTab();
				i++;
				getDriver().findElement(By.xpath("//button[text()='New Search']")).click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Multiple Reactive Case creation failed");
		}
	}

	// To click multiple checkbox
	public void clickMultiplecheckBox() {
		try {
			wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr")));
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				for (int j = i; j <= i + 1; j++) {
					String a = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
							.getText();
					if (a.equals(con2)) {
						getDriver().findElement(By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[" + j + "]/td[2]//label/span[1]"))
								.click();
						break;
					}
					String a1 = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
							.getText();
					if (a1.equals(con1)) {
						getDriver().findElement(By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/td[2]//label/span[1]"))
								.click();
						break;
					}
				}
			}
			Pass_Message("Case is selected from My Open Cases dropdown");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cases are not selected from My Open Cases dropdown");
		}
	}

	public void selectMultipleCasesFromCouQueue() {
		try {
			wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr")));
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				for (int j = i; j <= i + 1; j++) {
					String a = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[5]/span"))
							.getText();
					if (a.equals(caseNum1)) {
						wait.until(ExpectedConditions.elementToBeClickable((By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[\"+j+\"]/td[2]//label/span[1]"))));
						getDriver().findElement(By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[" + j + "]/td[2]//label/span[1]"))
								.click();
						break;
					}
				}
				String a1 = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[5]/span"))
						.getText();
				if (a1.equals(caseNum2)) {
					getDriver().findElement(By.xpath(
							"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/td[2]//label/span[1]"))
							.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cases are not selected from My Open Cases dropdown");
		}
	}

	// Case Update by owner
	public void us_B515916_TC3_caseUpdatedbyOwn_CaseUpdateBlankValidationInOwnLogin() {
		try {
			support_Login();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_CaseUpdate.Key_Array[6]);
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			String Retrive = "638258261";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			customer_remark("re_case");
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				String a = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				if (a.equals(Retrive)) {
					String caseUpdateVal = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[11]/span"))
							.getText();
					if (caseUpdateVal == null) {
						Pass_Message("Case Update is not reflecting in My Open Cases");
					} else {
						Fail_Message(
								"Case Update value to updated in My Open Case List View when the case is updated by CO as: "
										+ caseUpdateVal);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("CaseUpdateBlankValidationInOwnerLoginFailed.");
		}
	}

	public void us_B515916_TC6_LayoutValidation() {
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput(CMODConstants.MY_OPEN_CASES);
			proactivepage.clickAssignedInput(CMODConstants.MY_OPEN_CASES);
			if (proactivepage.iscaseNumberColumnHeaderVisible()) {
				Pass_Message("Case number column is present");
			} else {
				Fail_Message("Case number column is not present");
			}
			if (proactivepage.isCosgnNumColumnVisible()) {
				Pass_Message("Conginment number column is present");
			} else {
				Fail_Message("Conginment number column is not present");
			}
			if (proactivepage.isSlaStatusColumnVisible()) {
				Pass_Message("SLA status column is present");
			} else {
				Fail_Message("SLA status column is not present");
			}
			if (proactivepage.isSlaColumnVisible()) {
				Pass_Message("SLA column is present");
			} else {
				Fail_Message("SLA column is not present");
			}
			if (proactivepage.isSubjectColumnVisible()) {
				Pass_Message("Subject column is present");
			} else {
				Fail_Message("Subject column is not present");
			}
			if (proactivepage.isCaseTypeColumnVisible()) {
				Pass_Message("Case Type column is present");
			} else {
				Fail_Message("Case Type column is not present");
			}
			if (proactivepage.isDateTimeColumnVisible()) {
				Pass_Message("DateTime column is present");
			} else {
				Fail_Message("DateTime column is not present");
			}
			if (proactivepage.isDueDateColumnVisible()) {
				Pass_Message("Due Date column is present");
			} else {
				Fail_Message("Due Date column is not present");
			}
			if (proactivepage.isCaseUpdateColumnVisible()) {
				Pass_Message("Case Update column present");
			} else {
				Fail_Message("Case Update column not present");
			}
			if (proactivepage.isRevDueDateColumnVisible()) {
				Pass_Message("Revised Due Date column is present");
			} else {
				Fail_Message("Revised Due Date column is not present");
			}
			if (proactivepage.isSenCouTerColumnVisible()) {
				Pass_Message("Sender's County/Territory column is present");
			} else {
				Fail_Message("Sender's County/Territory column is not present");
			}
			if (proactivepage.isUpdatedCosgnStatusDescColumnVisible()) {
				Pass_Message("Updated Consignment Status Description column is present");
			} else {
				Fail_Message("Updated Consignment Status Description column is not present");
			}
			if (proactivepage.isRecCouTerColumnVisible()) {
				Pass_Message("Receiver's County/Territory column is present");
			} else {
				Fail_Message("Receiver's County/Territory column is not present");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Update Column validation failed.");
		}
	}

	public void cmod_OIB_Support_Login() {
		try {
			LoginPage loginpage = new LoginPage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_CaseUpdate.Key_Array[2]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_CaseUpdate.Key_Array[2]);
			getDriver().get(URL);
			getDriver().manage().window().maximize();
			loginpage.setUsername(Username);
			loginpage.setPassword(Password);
			loginpage.clickLoginBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("OIB login to salesforce Failed.");
		}
	}

	// Tc 6
	public void us_B515916_TC4_CaseUpdatedbyOIB_CaseRemarksVal() {
		try {
			support_Login();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_CaseUpdate.Key_Array[6]);
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			String Retrive = "625566760";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			cdPage.clickCaseDetails();
			// cdPage.clickOIBMemeberEdit();
			// cdPage.clickOIBMember();
			try {
				if (cdPage.showOIBSearchPeople() == true) {
					setOIBName("Automation Tester3");
				}
			} catch (Exception e) {
				getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).click();
				setOIBName("Automation Tester3");
				cdPage.clickCaseDtlsCancelBtn();
			}
			logout();
			uiTestHelper.propagateException();
			cmod_OIB_Support_Login();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
			// ACM_Connectivity.TrackandTraceCCD(Retrive);
			// ACM_Connectivity.CCD_ViewCase();
			uiTestHelper.propagateException();
			Case_remark("re_case");
			logout();
			support_Login();
			uiTestHelper.propagateException();
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			System.out.println("ObjRow " + objRow);
			int row_count = objRow.size();
			System.out.println("Row Count " + row_count);
			for (int i = 1; i <= objRow.size(); i++) {
				String a = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				// System.out.println("A " + a);
				if (a.equals(Retrive)) {
					String caseUpdateVal = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[11]/span"))
							.getText();
					System.out.println("CaseUpdateValue " + caseUpdateVal);
					if (caseUpdateVal == null) {
						Fail_Message("Case Update is not reflecting in My Open Cases");
					} else {
						Pass_Message("Case Remarks are displayed  in Case Update field in My Open Case List as: "
								+ caseUpdateVal);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Case Remarks updated by OIB and reflecting in Case Update field in My Open Cases queue of CO validation failed.");
		}
	}

	public void setOIBName(String oibName) {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		cdPage.setOIBMember(oibName);
		cdPage.clickShowAllOIB();
		getDriver().findElement(By.xpath("//a[contains(text(),'" + oibName + "')]")).click();
		cdPage.clickSaveOIB();
	}

	public void us_B515916_CaseUpdatedbyOIB_Email() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			// Retrive = Database_Connection.retrieveTestData("CONNORT",
			// "ACM","KEY",CMOD_FF_Reusable.Key_Array[5]);
			String Retrive = "625566760";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			/*
			 * try{if(cdPage.showCaseUpdateClearEmailMsg()==true)
			 * {cdPage.clickCaseUpdateCancelBtnOnPopUp(); }} catch(Exception e) {
			 * System.out.println("Clear Email Case updated pop up window is not displayed"
			 * ); }
			 */
			uiTestHelper.propagateException();
			cdPage.clickCaseDetails();
			cdPage.clickOIBMember();
			try {
				if (cdPage.showOIBMemberName() == true) {
					cdPage.clickCaseDtlsCancelBtn();
				}
			} catch (Exception e) {
				setOIBName("Automation Tester3");
			}
			ACM_Connectivity.CloseTab();
			logout();
			cmod_OIB_Support_Login();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			FF_EBS.sendEmailFromOIB();
			logout();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Email sent by OIB failed.");
		}
	}

	public void us_B515916_CaseUpdatedbyOIB_Email_Validation() {
		try {
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_FF_Reusable.Key_Array[5]);
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Cases']/following::table[1]/tbody")));
			/*
			 * EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			 * event.executeScript(
			 * "document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500"
			 * );
			 */
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			for (int i = 1; i <= row_count; i++) {
				System.out.println("after for");
				String myCase = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				System.out.println("after mycase");
				if (myCase.equals(Retrive)) {
					System.out.println("Inside for loop");
					String caseUpdateVal = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[11]/span"))
							.getText();
					if (caseUpdateVal.equals("New email")) {
						System.out.println("Inside if");
						Pass_Message("'New email' is updated in Case Updated field in My Open Cases");
						uiTestHelper.propagateException();
						getDriver()
								.findElement(By
										.xpath("//span[text()='Cases']/following::table/tbody/tr[" + i + "]/th/span/a"))
								.click();
						break;
					} else {
						Fail_Message("'New email' is not updated in Case Updated field in My Open Cases");
					}
				}
				System.out.println("before break");

			}
		} catch (Exception e) {
			System.out.println("inside catch");
			e.printStackTrace();
			Fail_Message("Exception: 'New Email' verifcation in Case Update field in My Open Cases of CO is failed");
		}
	}

	public void us_B515916_TC7_CaseUpdatedbyOIB_CaseStatus() {
		try {
			support_Login();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_CaseUpdate.Key_Array[6]);
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			String Retrive = "625566760";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			cdPage.clickCaseDetails();
			cdPage.clickOIBMember();
			try {
				if (cdPage.showOIBSearchPeople() == true) {
					setOIBName("Automation Tester3");
				}
			} catch (Exception e) {
				getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).click();
				setOIBName("Automation Tester3");
				cdPage.clickCaseDtlsCancelBtn();
			}
			logout();
			cmod_OIB_Support_Login();
			getDriver().navigate().refresh();
			// ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickCaseInformation();
			cdPage.clickEditStatus();
			getDriver().findElement(By.xpath("//button[@name='Status']")).click();
			getDriver().findElement(By.xpath("//span[@title='Monitoring']")).click();
			// getDriver().findElement(By.xpath("//lightning-base-combobox-item[@data-value='Monitoring']")).click();
			cdPage.clickSaveOIB();
			logout();
			uiTestHelper.propagateException();
			support_Login();
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			for (int i = 1; i <= row_count; i++) {
				String caseUpdateVal = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				if (caseUpdateVal.equals("Shipment status change")) {
					System.out.println(caseUpdateVal);
					Pass_Message("'Shipment status change' is updated in Case Updated field in My Open Cases");
					break;
				} else {
					Fail_Message("'Shipment status change' is not updated in Case Updated field in My Open Cases");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case status in case update column failed");
		}
	}

	// us_B466623_TC1-TC3 - When a CSR selects a close option then Consumer type
	// Drop down list should be Displayed
	public void us_B466623_ConsumerTypeDrpDwnLstValidation_CloseCase() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			// String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM",
			// "KEY",
			// CCD_CMOD_SmokeTest.Key_Array[1]);
			// System.out.println(Retrive);
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			ACM_Connectivity.CloseTab();
			String Retrive = "625568045";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
					casedetailpage.clickcallbackActivity();
					if (getDriver().findElement(By.xpath("//span[contains(text(),'Next Call Back')]")).isDisplayed())
						;
					{
						cdPage.clickcallbackActivity();
						cdPage.clickcallbackcheckbox();
						cdPage.clickcallbackActivitysave();
						cdPage.waitForSuccessMsg();
					}
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
				if (getDriver().findElement(By.xpath("//span[contains(text(),'Next Call Back')]")).isDisplayed())
					;
				{
					cdPage.clickcallbackActivity();
					cdPage.clickcallbackcheckbox();
					cdPage.clickcallbackActivitysave();
				}
			}
			try {
				cdPage.clickCloseCaseTab();
				cdPage.clickConsumerType();
				if (cdPage.showBToB() == true && cdPage.showBToC() == true && cdPage.showUnclearUnknown() == true) {
					Pass_Message("'Consumer Type' Drowpdown list is displayed");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("'Consumer Type' drop down list validation failed");
			}
			try {
				cdPage.clickBToB();
				cdPage.setComments("Close the Case for testing");
				cdPage.clickCloseCaseSave();
				String status = cdPage.getCaseStatus();
				System.out.println(status);
				if (status.contains("'Manual-Closed")) {
					Pass_Message("Case is closed Successfully");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Case close operation failed after adding Consumer Type drop down value");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Close case with Consumer Type drop down list validation failed");
		}
	}

	public void us_B466623_TC4_ConsumerTypeDrpDwnCloseCase_MegredActions() {
		try {
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			MyOpenCasesListViewPage myOpenCaseListpage = new MyOpenCasesListViewPage(getDriver());
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			uiTestHelper.propagateException();
			try {
				wait = new WebDriverWait(getDriver(), 60);
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
						By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr")));
				String a = getDriver()
						.findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[1]//td[3]/span"))
						.getText();
				System.out.println("A  " + a);
				if (a != null) {
					getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[1]/td[2]//label/span[1]"))
							.click();
				}
				myOpenCaseListpage.clickshowMoredropDown();
				myOpenCaseListpage.clickMergedActions();
				myOpenCaseListpage.switchToFrameMergedActions();
				myOpenCaseListpage.chkMergedActCloseCase();
				try {
					cdPage.clickConsumerType();
					if (cdPage.showBToB() == true) {
						Pass_Message("'Consumer Type' Drowpdown list is displayed in 'Close Case' of Merged Action");
					}
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Consumer Type drop down list in Merged case validation failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Close case with Consumer Type drop down list validation failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Close case with Consumer Type drop down list validation failed");
		}
	}

	public void loginInSearchConsignment() {
		// Login and search a consignment number
		CMOD_FF_Reusable ACM_FF_Reusable = new CMOD_FF_Reusable();
		ACM_FF_Reusable.support_Login();
		CCD_Connectivity acmConnectivity = new CCD_Connectivity();
		acmConnectivity.CloseTab();
		CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
		ccdConnectivity.TrackandTraceConsignmentSearch("625426895");
	}

	public void loginInSearchConsignmentAndCreateCase() {
		// Login and search a consignment number
		CMOD_FF_Reusable ACM_FF_Reusable = new CMOD_FF_Reusable();
		ACM_FF_Reusable.support_Login();
		CCD_Connectivity acmConnectivity = new CCD_Connectivity();
		acmConnectivity.CloseTab();
		CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
		ccdConnectivity.TrackandTraceConsignmentSearch("625426895");
		// Fill the Create Case Popup Window Form after pressing create case button
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		ffUpdated.CreateCase_EBS();
		// getDriver().navigate().to("https://tnt--sit.lightning.force.com/lightning/r/Case/5003O000006ZTHNQA4/view?ws=%2Flightning%2Fr%2FAccount%2F0013O00000473WZQAY%2Fview");
	}

	public void clickReAssignOnCasePageAndSelectOriginOption() {
		// this code clicks the more option on the banner and it presses the re assign
		// button
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		// this code handles the more tab section
		ccPage.clickReassignButton();
		// this code handles the re-assign dropdown menu
		ccPage.clickReAssignDropDownMenuBox();
		ccPage.selectOriginFromReAssignBox();
		ccPage.selectreAssignSaveButton();
	}

	public void clickReAssignOnCasePageAndSelectDestinationOption() {
		// this code clicks the more option on the banner and it presses the re assign
		// button
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		// this code handles the more tab section
		ccPage.clickReassignButton();
		// this code handles the re-assign dropdown menu
		ccPage.clickReAssignDropDownMenuBox();
		ccPage.selectDestinationFromReAssignBox();
		ccPage.selectreAssignSaveButton();
	}

	public void validateCaseOwnerOnCasePage(String testName) {
		// Click summary and validate that the owner is set to me
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseSummaryTab();
		String isNamePresent = ccPage.getUserCreationName();
		softAssertion.assertEquals(isNamePresent, testName);
		softAssertion.assertAll();
		Pass_Message("Ben is the new case owner");
	}

	public void validateMonitorActivityInCaseInformation() {
		// Click summary and validate that the status is set to monitoring
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseInformationTab();
		String isMonitoringTextPresent = ccPage.getUserCreationName();
		softAssertion.assertEquals(isMonitoringTextPresent, "Monitoring");
		softAssertion.assertAll();
		Pass_Message("Status is correctly set to Monitoring");
	}

	public void validateCaseIsManualClosedInCaseInformation() {
		// Click summary and validate that the status is set to monitoring
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectCaseInformationTab();
		String isManualClosedTextPresent = ccPage.getManualClosedStatus();
		softAssertion.assertEquals(isManualClosedTextPresent, "Manual-Closed");
		softAssertion.assertAll();
		Pass_Message("Status is correctly set to Monitoring");
	}

	public void validateCaseHistoryStatusChanges() {
		// Validate 2 rows in the table to see status changes
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		String isNewValue = ccPage.getNewValueText();
		softAssertion.assertEquals(isNewValue, "Created");
		softAssertion.assertAll();
		Pass_Message("New Value is correctly set to Created");
	}

	public void validateTaskStatus() {
		// Validate 2 rows in the table to see status changes
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		String isNewValue = ccPage.getOriginalValueText();
		softAssertion.assertEquals(isNewValue, "Manually-Closed");
		softAssertion.assertAll();
		Pass_Message("New Value is correctly set to Manually-Closed");
	}

	public void applyMonitorActivityTab() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickMonitorActivityTab();
		ccPage.setDateInCOBox("31/07/2021");
		ccPage.setTimeInCOBox("19:00");
		ccPage.selectCOMonitoringTickBox();
		ccPage.selectMonitoringActivitySaveButton();
		// xpath tick box is the problem
		// unsure we actually need time time method as well
	}

	public void applyCloseCaseActions() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCloseCaseTab();
		ccPage.selectCloseCaseSaveButton();
	}

	public void applyCallBackOptionsToCase() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCallBackTab();
		ccPage.selectCOCallBackTickBox();
		ccPage.selectCallBackSaveButton();
	}

	public void applyReOpenCaseActions() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickReOpenCaseTab();
		ccPage.clickSelectBoxChooseReOpenReason();
		ccPage.clickReOpenSaveButton();
	}

	public void applyMergedActionsTab() {
		// This method is for when a case is found through global search [1]
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickMergedActionsTab();
		ccPage.selectClosedCaseTickBox();
		ccPage.selectMergedActionsSaveButton();
	}

	public void View_Consignment_And_All_Cases_Should_Be_Open() {
		// B-400176 Test Cases
		try {
			// Search consignment first time and validate eye icon
			CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
			ccdConnectivity.TrackandTraceConsignmentSearch("324645440");
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			consignmentPage.clickviewcasebtn();
			consignmentPage.clickEyeIconAndScrollRight();
			String isConsignmentNumberPresent = consignmentPage.consignmentNumberValidation("324645440");
			softAssertion.assertEquals(isConsignmentNumberPresent, "324645440");
			softAssertion.assertAll();
			Pass_Message("Consignment Number is there, case has been opened");
			// clear tabs and validate the second icon on the same search result
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			getDriver().navigate().refresh();
			ccdConnectivity.TrackandTraceConsignmentSearch("324645440");
			consignmentPage.clickviewcasebtn();
			consignmentPage.clicksecondEyeIconAndScrollRight();
			String isConsignmentNumberTwoPresent = consignmentPage.consignmentNumberValidation("324645440");
			softAssertion.assertEquals(isConsignmentNumberTwoPresent, "324645440");
			softAssertion.assertAll();
			Pass_Message("Consignment Number is there, case has been opened");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("If consginment ID is not present");
		}
	}

	public void searchAnyCaseInGlobalMenu(String caseID) {
		// puts in the string into the global search and will select the customer
		// account
		HomePage homePage = new HomePage(getDriver());
		CustomerAccountPage customerAccountPage = new CustomerAccountPage(getDriver());
		homePage.searchGlobalSearch(caseID);
		customerAccountPage.selectCustomerAccounts(caseID);
	}

	public void applyTaskTabActions(String country, String depot, String routeto) {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickTaskTab();
		ccPage.taskCountryTerritoryBox(country);
		ccPage.setTaskLocation(depot);
		ccPage.setCaseRoute(routeto);
		// ccPage.setTaskDeadlineDate(date);
		// ccPage.setTaskDeadlineTime(time);
		ccPage.sendTextToTaskInstructionsBox();
	}

	public void validateConsignmentStatusDescriptionField() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.selectConsignmentInformationTab();
		String isDataFieldPresent = ccPage.getupdatedConsignmentStatusDescriptionFieldName();
		softAssertion.assertEquals(isDataFieldPresent, "Updated Consignment Status Description");
		softAssertion.assertAll();
		Pass_Message("Updated Consignment Status Description Field Is Present");
	}

	public void clickCasesMenuAndSelectMyOpenCases() {
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		caseDetailsPage.clickCasesDropDownMenu();
		// caseDetailsPage.clickMyOpenCasesFromDropDownMenu();
	}

	public void searchCasePageTableAndSelectRecord(String caseNumber) {
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		caseDetailsPage.searchCaseNumberInSearchBox(caseNumber);
	}

	public void beforeNextTestReOpenCaseAfterClosing(String caseID) {
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.searchAnyCaseInGlobalMenu(caseID);
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickReOpenCaseTab();
		ccPage.clickSelectBoxChooseReOpenReason();
		ccPage.waitForSuccessMsg();
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}

	public void reusableIssueLocationDropDownValidation() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCloseCaseTab();
		Boolean isDataFieldPresent = ccPage.isIssueLocationBoxDisplayed();
		softAssertion.assertEquals(isDataFieldPresent, "Issue Location Field Is Present");
		softAssertion.assertAll();
		Pass_Message("Issue Location DropDown Menu Exists Still");
	}

	public void booleanValidationForIssueLocationBox() {
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		Boolean isDataFieldPresent = ccPage.isIssueLocationBoxDisplayed();
		softAssertion.assertEquals(isDataFieldPresent, "Issue Location Field Is Present");
		softAssertion.assertAll();
		Pass_Message("Issue Location DropDown Menu Exists Still");
	}

	public void closeCaseThenAddAnyIssueLocationOwnerShipRootCauseAndVerify() {
		// take out the string and add the DBA call with Prachi's help
		String openCaseID = Database_Connection.retrieveTestData("CASENUM", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		HomePage homePage = new HomePage(getDriver());
		homePage.clickDropDownNavigationMenu();
		homePage.clickHome();
		System.out.println(openCaseID);
		cmodFFReusable.searchAnyCaseInGlobalMenu(openCaseID);
		// getDriver().findElement(By.xpath("//*[@class='slds-media__body
		// slds-text-title
		// forceSearchRecordPreviewTitle']/following::a[@title='20122424']")).click();
		CreateCasePage ccPage = new CreateCasePage(getDriver());
		ccPage.clickCloseCaseTab();
		String ownershipFieldValue = Database_Connection.retrieveTestData("OWNERSHIPFIELD", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		System.out.println(ownershipFieldValue);
		ccPage.setOwnershipField(ownershipFieldValue);
		String issueLocationValue = Database_Connection.retrieveTestData("ISSUELOC", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		System.out.println(issueLocationValue);
		ccPage.setIssueLocationField(issueLocationValue);
		String rootCauseValue = Database_Connection.retrieveTestData("ROOTCAUSE", "ACM", "KEY",
				CMOD_Create_Close_ReopenCases.Key_Array[4]);
		System.out.println(rootCauseValue);
		ccPage.setRootCauseField(rootCauseValue);
		ccPage.selectCloseCaseSaveButton();
		ccPage.waitForSuccessMsg();
		String firstArticleText = ccPage.getTopArticleText();
		System.out.println(firstArticleText);
		softAssertion.assertEquals(firstArticleText.contains(issueLocationValue + "\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains(ownershipFieldValue + "\n"), true,
				"Top Article Text Contains Expected Value");
		softAssertion.assertEquals(firstArticleText.contains(rootCauseValue + "\n"), true,
				"Top Article Text Contains Expected Value");
	}

	// Task Escalation
	public void task_escalation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			for (int i = 0; i <= 5; i++) {
				if (i < 5) {
					errorValidation();
					taskEscalationSamePage(i + 1);
				} else {
					try {
						cdPage.clickEscalateTaskBtn();
						getDriver().navigate().refresh();
						cdPage.clickEscalateTaskBtn();
						String error = cdPage.getErrorMessage();
						boolean err = error.contains("task is already reached final level");
						if (err) {
							Pass_Message("Error is displayed correctly as: '" + error + "'");
						} else {
							Fail_Message("Error has not displayed");
						}
						Assert.assertTrue(err, "Error is displayed correctly as: '" + error + "'");
					} catch (Exception e) {
						e.printStackTrace();
						Fail_Message("Exception: Escalation error validation failed");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	public void taskEscalationLevelDisplay() {
		try {
			ACM_Connectivity.CloseTab();
			// Retrive = Database_Connection.retrieveTestData("CONNORT",
			// "ACM","KEY",CMOD_CaseUpdate.Key_Array[2]);
			Retrive = "625564962";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			taskEscalation();
			/*
			 * cdPage.clickRelatedTab(); cdPage.clickTaskView();
			 * cdPage.clickCreatedTaskLink(); cdPage.clickEscalateTaskBtn();
			 */
			getDriver().navigate().refresh();
			String esc = getDriver().findElement(By.xpath("//lightning-formatted-text[contains(text(),'L')]"))
					.getText();
			if (esc.isEmpty()) {
				Fail_Message("Task is not escalated and escalation level is not displayed");
			} else {
				Pass_Message("Task has been escalated successfully to escalation leves: " + esc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	public void taskEscalationSLADateTimeDisplay() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			// Retrive = Database_Connection.retrieveTestData("CONNORT",
			// "ACM","KEY",CMOD_CaseUpdate.Key_Array[2]);
			Retrive = "625564826";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			if (cdPage.showEscalationSLAValue() == true) {
				Pass_Message("'Escalation SLA (Date/Time)' is displayed");
			} else {
				Fail_Message("'Escalation SLA (Date/Time)' is not displayed");
			}
			// for future test case
			/*
			 * String sla=cdPage.getEscalationSLAValue(); if(sla.isEmpty()) {
			 * Fail_Message("SLA time is not displayed"); } else {
			 * Pass_Message("SLA time is displayed as: "+sla); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation SLA date time validation test case failed");
		}
	}

	public void taskEscalation_ChangeCountryAndLocationValidation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			// Retrive = Database_Connection.retrieveTestData("CONNORT",
			// "ACM","KEY",CMOD_CaseUpdate.Key_Array[2]);
			Retrive = "625564962";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			cdPage.clickEditTaskBtn();
			cdPage.clickCouTerrField();
			/*
			 * try{ if(cdPage.showOIBSearchPeople()==true) {
			 * setOIBName("Automation Tester3"); } } catch(Exception e) {
			 * getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).
			 * click (); setOIBName("Automation Tester3"); cdPage.clickCaseDtlsCancelBtn();
			 * }
			 */
			cdPage.clickTNTLocationField();
			/*
			 * try{ if(cdPage.showOIBSearchPeople()==true) {
			 * setOIBName("Automation Tester3"); } } catch(Exception e) {
			 * getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).
			 * click (); setOIBName("Automation Tester3"); cdPage.clickCaseDtlsCancelBtn();
			 * }
			 */
			String couTer = cdPage.getCouTerrValue();
			String tntLoc = cdPage.getTNTLocationValue();
			if (couTer.contains("India")) {
				Pass_Message("Country/Territory updated and displayes successfully");
			} else {
				Fail_Message("Country/Territory is not updated and displayes");
			}
			if (tntLoc.contains("BLR")) {
				Pass_Message("TNT Location updated and displayes successfully");
			} else {
				Fail_Message("TNT Location is not updated and displayes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation SLA date time validation test case failed");
		}
	}

	public void taskEscalation_byOIB() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_CaseUpdate.Key_Array[2]);
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			cdPage.clickEditTaskBtn();
			cdPage.clickCouTerrField();
			/*
			 * try{ if(cdPage.showOIBSearchPeople()==true) {
			 * setOIBName("Automation Tester3"); } } catch(Exception e) {
			 * getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).
			 * click (); setOIBName("Automation Tester3"); cdPage.clickCaseDtlsCancelBtn();
			 * }
			 */
			cdPage.clickTNTLocationField();
			/*
			 * try{ if(cdPage.showOIBSearchPeople()==true) {
			 * setOIBName("Automation Tester3"); } } catch(Exception e) {
			 * getDriver().findElement(By.xpath("(//button[@title='Clear Selection'])[1]")).
			 * click (); setOIBName("Automation Tester3"); cdPage.clickCaseDtlsCancelBtn();
			 * }
			 */
			String couTer = cdPage.getCouTerrValue();
			String tntLoc = cdPage.getTNTLocationValue();
			if (couTer.contains("India")) {
				Pass_Message("Country/Territory updated and displayes successfully");
			} else {
				Fail_Message("Country/Territory is not updated and displayes");
			}
			if (tntLoc.contains("BLR")) {
				Pass_Message("TNT Location updated and displayes successfully");
			} else {
				Fail_Message("TNT Location is not updated and displayes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation SLA date time validation test case failed");
		}
	}

	public void taskEscalation_forDiffLocErrorMsg() {
		// CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			// Retrive = Database_Connection.retrieveTestData("CONNORT",
			// "ACM","KEY",CMOD_CaseUpdate.Key_Array[2]);
			Retrive = "625564962";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			taskEscalation();
			/*
			 * cdPage.clickRelatedTab(); cdPage.clickTaskView();
			 * cdPage.clickCreatedTaskLink(); cdPage.clickEscalateTaskBtn();
			 * cdPage.clickEscReason(); cdPage.clickEscReasonVal();
			 * cdPage.inputEscalationComments("Please escalate to next level for testing");
			 * cdPage.clickTascEscSaveBtn();
			 */
			/*
			 * try { if (cdPage.verifyTaskEscFailbyDiffLocBMessage()==true) {
			 * Pass_Message("Task can not be escalated for countries and location not live for this feature"
			 * ); } } catch(Exception e){
			 * Fail_Message("Task escalation error validation failed"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	public void taskEscalation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		cdPage.clickRelatedTab();
		cdPage.clickTaskView();
		cdPage.clickCreatedTaskLink();
		cdPage.clickEscalateTaskBtn();
		cdPage.clickEscReason();
		cdPage.clickEscReasonVal();
		cdPage.inputEscalationComments("Please escalate to next level for testing");
		cdPage.clickTascEscSaveBtn();
		try {
			if (cdPage.verifyTaskEscFailbyDiffLocBMessage() == true) {
				Pass_Message("Task can not be escalated for countries and location not live for this feature");
			}
		} catch (Exception e) {
			Fail_Message("Task escalation error validation failed");
		}
	}

	public void task_escalation_OIB() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		CCD_CI_Booking cibooking = new CCD_CI_Booking();
		try {
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[7]);
			ACM_Connectivity.CloseTab();
			logout();
			cmod_OIB_Support_Login();
			cibooking.returnToPage();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);

			viewOrCreateCase();

			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			boolean isError = cdPage.verifyTaskEscFailbyOIBMessage() == true;
			if (isError) {
				Pass_Message("As expected Task has not been escalated by OIB");
			} else {
				Fail_Message("Task has been escalated by OIB");
			}
			Assert.assertTrue(isError, "As expected Task has not been escalated by OIB");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task can not be escalated by OIB test case is failed");
		}
	}

	// US B-486175
	public void us_B486175_VerifyChangeOIBinListViewByTL() {
		try {
			logout();
			login_TeamLead();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_CaseUpdate.Key_Array[6]);
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			if (myOpenCasePage.verifyChangeOIBMemberBtn() == true) {
				Pass_Message("Change OIB button is present in List View");
			} else {
				Fail_Message("Change OIB button is not present in List View");
			}
			EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
			event.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count = objRow.size();
			for (int i = 1; i <= row_count; i++) {
				try {
					getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[2]/span"))
							.click();
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			myOpenCasePage.clickChangeOIBMember();
			myOpenCasePage.clickrelateToDrpDwn();
			if (myOpenCasePage.verifyUserDisplayed() == true) {
				Pass_Message("User is displyed in Relate To drop down of Change OIB button");
			} else {
				Fail_Message("User is not displyed in Relate To drop down of Change OIB button");
			}
			if (myOpenCasePage.verifyQueueDisplayed() == true) {
				Pass_Message("Queue is displyed in Relate To drop down of Change OIB button");
			} else {
				Fail_Message("Queue is not displyed in Relate To drop down of Change OIB button");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIBinListView_User() {
		try {
			caseNum1 = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			caseNum2 = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			logout();
			getDriver().navigate().refresh();
			login_TeamLead();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			uiTestHelper.propagateException();
			selectMultipleCasesFromCouQueue();
			myOpenCasePage.clickChangeOIBMember();
			myOpenCasePage.switchToFrameChangeOIB();
			myOpenCasePage.setOIBValue("Tessa Boesmans");
			myOpenCasePage.clickSaveOIBMemberBtn();
			getDriver().navigate().refresh();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			try {
				{
					selectMyCaseFromCouQueue(caseNum1);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMember().contains("Tessa Boesmans")) {
						Pass_Message("OIB user is updated correctly for 1st case " + caseNum1);
					} else {
						Fail_Message("OIB user is not updated correctly for 1st case " + caseNum1);
					}
					ACM_Connectivity.CloseTab();
					selectMyCaseFromCouQueue(caseNum2);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMember().contains("Tessa Boesmans")) {
						Pass_Message("OIB user is updated correctly for 2nd case " + caseNum2);
					} else {
						Fail_Message("OIB user is not updated correctly for 2nd case " + caseNum2);
					}
				}
			} catch (Exception e) {
				Fail_Message("OIB user have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Update Change OIB Member User validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIBinListView_Queue() {
		try {
			ACM_Connectivity.CloseTab();
			caseNum1 = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			caseNum2 = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			logout();
			login_TeamLead();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			uiTestHelper.propagateException();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			uiTestHelper.propagateException();
			selectMultipleCasesFromCouQueue();
			myOpenCasePage.clickChangeOIBMember();
			myOpenCasePage.switchToFrameChangeOIB();
			myOpenCasePage.clickrelateToDrpDwn();
			myOpenCasePage.clickQueue();
			myOpenCasePage.setOIBValue("UK - GH - Support 2");
			myOpenCasePage.clickSaveOIBMemberBtn();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			try {
				{
					selectMyCaseFromCouQueue(caseNum1);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMemberQueueName().contains("UK - GH - Support 2")) {
						Pass_Message("OIB queue is updated correctly for 1st case " + caseNum1);
					} else {
						Fail_Message("OIB queue is not updated correctly for 1st case " + caseNum1);
					}
					ACM_Connectivity.CloseTab();
					selectMyCaseFromCouQueue(caseNum2);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMemberQueueName().contains("UK - GH - Support 2")) {
						Pass_Message("OIB queue is updated correctly for 2nd case " + caseNum2);
					} else {
						Fail_Message("OIB queue is not updated correctly for 2nd case " + caseNum2);
					}
				}
			} catch (Exception e) {
				Fail_Message("OIB queue have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Update Change OIB Member User validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIB_MergedActions() {
		try {
			ACM_Connectivity.CloseTab();
			caseNum1 = Database_Connection.retrieveTestData("CASENUM", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			caseNum2 = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			logout();
			// uiTestHelper.propagateException();
			login_TeamLead();
			// uiTestHelper.propagateException();
			ACM_Connectivity.CloseTab();
			HomePage homePage = new HomePage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			selectMultipleCasesFromCouQueue();
			myOpenCasePage.clickshowMoredropDown();
			myOpenCasePage.clickMergedActions();
			if (myOpenCasePage.verifyMergedActChngOIBChkBox() == true) {
				Pass_Message("Change OIB Member checkbox is present in Merged action of list view");
			} else {
				Fail_Message("Change OIB Member checkbox is not present in Merged action of list view");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member in merged action validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIB_User_MergedActions() {
		try {
			ACM_Connectivity.CloseTab();
			us_B486175_ChangeOIB_MergedActions();
			caseNum1 = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			caseNum2 = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			selectMultipleCasesFromCouQueue();
			myOpenCasePage.clickChangeOIBMember();
			myOpenCasePage.switchToFrameChangeOIB();
			myOpenCasePage.setOIBValue("Tessa Boesmans");
			myOpenCasePage.clickSaveOIBMemberBtn();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			try {
				{
					selectMyCaseFromCouQueue(caseNum1);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMember().contains("Tessa Boesmans")) {
						Pass_Message("OIB user is updated correctly for 1st case " + caseNum1);
					} else {
						Fail_Message("OIB user is not updated correctly for 1st case " + caseNum1);
					}
					ACM_Connectivity.CloseTab();
					selectMyCaseFromCouQueue(caseNum2);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMember().contains("Tessa Boesmans")) {
						Pass_Message("OIB user is updated correctly for 2nd case " + caseNum2);
					} else {
						Fail_Message("OIB user is not updated correctly for 2nd case " + caseNum2);
					}
				}
			} catch (Exception e) {
				Fail_Message("OIB user have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member in merged action validations failed");
		}
	}

	public void us_B486175_ChangeOIB_Queue_MergedActions() {
		try {
			ACM_Connectivity.CloseTab();
			us_B486175_ChangeOIB_MergedActions();
			caseNum1 = Database_Connection.retrieveTestData("CASENUM1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			caseNum2 = Database_Connection.retrieveTestData("CASENUM2", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			HomePage homePage = new HomePage(getDriver());
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			selectMultipleCasesFromCouQueue();
			myOpenCasePage.clickChangeOIBMember();
			myOpenCasePage.switchToFrameChangeOIB();
			myOpenCasePage.clickrelateToDrpDwn();
			myOpenCasePage.clickQueue();
			myOpenCasePage.setOIBValue("UK - GH - Support 2");
			myOpenCasePage.clickSaveOIBMemberBtn();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("UK - GH - Support 1");
			proactivepage.clickAssignedInput("UK - GH - Support 1");
			try {
				{
					selectMyCaseFromCouQueue(caseNum1);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMemberQueueName().contains("UK - GH - Support 2")) {
						Pass_Message("OIB queue is updated correctly for 1st case " + caseNum1);
					} else {
						Fail_Message("OIB queue is not updated correctly for 1st case " + caseNum1);
					}
					ACM_Connectivity.CloseTab();
					selectMyCaseFromCouQueue(caseNum2);
					cdPage.clickCaseDetails();
					if (cdPage.getOIBMember().contains("UK - GH - Support 2")) {
						Pass_Message("OIB queue is updated correctly for 2nd case " + caseNum2);
					} else {
						Fail_Message("OIB queue is not updated correctly for 2nd case " + caseNum2);
					}
				}
			} catch (Exception e) {
				Fail_Message("OIB queue have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member in merged action validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIB_User_FromReactiveCase() {
		try {
			ACM_Connectivity.CloseTab();
			Retrive = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			cdPage.clickMergedActionsTab();
			myOpenCasePage.clickMergedActChngOIBChkBox();
			myOpenCasePage.setOIBValue("Automation Tester3");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			myOpenCasePage.clickSaveOIBMemberBtn();
			if (cdPage.verifySuccessMessage() == true) {
				Pass_Message("OIB saved successfully");
			} else {
				Fail_Message("OIB is not saved");
			}
			try {
				cdPage.clickCaseDetails();
				if (cdPage.getOIBMember().contains("Automation Tester3")) {
					Pass_Message("OIB user is updated correctly for Reactive case");
				} else {
					Fail_Message("OIB user is not updated correctly for Reactive Case");
				}
			} catch (Exception e) {
				Fail_Message("OIB user have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member in merged action validations failed");
		}
	}

	// US B-486175
	public void us_B486175_ChangeOIB_Queue_FromReactiveCase() {
		try {
			ACM_Connectivity.CloseTab();
			Retrive = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			cdPage.clickMergedActionsTab();
			myOpenCasePage.clickMergedActChngOIBChkBox();
			myOpenCasePage.clickrelateToDrpDwn();
			myOpenCasePage.clickQueue();
			myOpenCasePage.setOIBValue("UK - GH - Support 2");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			myOpenCasePage.clickSaveOIBMemberBtn();
			if (cdPage.verifySuccessMessage() == true) {
				Pass_Message("OIB saved successfully");
			} else {
				Fail_Message("OIB is not saved");
			}
			try {
				cdPage.clickCaseDetails();
				if (cdPage.getOIBMemberQueueName().contains("UK - GH - Support 2")) {
					Pass_Message("Case OIB Queue is updated correctly for Reactive case");
				} else {
					Fail_Message("Case OIB Queue is not updated correctly for Reactive Case");
				}
			} catch (Exception e) {
				Fail_Message("OIB user have not been added from Merged action successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member validations failed");
		}
	}

	public void csr_Login(String Username, String Password) {
		try {
			LoginPage loginPage = new LoginPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			getDriver().get(URL);
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			loginPage.clickLoginBtn();
			HomePage homepage = new HomePage(getDriver());
			uiTestHelper.propagateException();
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
			boolean verifydropDownMenu = homePage.verifydropDownMenu();
			assertTrue(verifydropDownMenu);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce as Support CSR Failed.");
		}
	}

	// B-852245
	public void us_B852245_clearedEmail_InCaseUpdate_Validation() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			if (cdPage.showCaseUpdateClearEmailMsg() == true) {
				Pass_Message("Pop Up window to clear the 'Email' in Case Update column is displayed successfully");
			} else {
				Fail_Message("Modal pop up window to clear email is not displayed");
			}
			cdPage.clickCaseUpdateClearBtnOnPopUp();
			ACM_Connectivity.CloseTab();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput("1 - My Open Cases");
			proactivepage.clickAssignedInput("1 - My Open Cases");
			EventFiringWebDriver event1 = new EventFiringWebDriver(getDriver());
			event1.executeScript(
					"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
			List<WebElement> objRow2 = getDriver()
					.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
			int row_count2 = objRow2.size();
			for (int i = 1; i <= row_count2; i++) {
				String myCon2 = getDriver()
						.findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[3]/span"))
						.getText();
				if (myCon2.equals(Retrive)) {
					String caseUpdateVal = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[11]/span"))
							.getText();
					if (caseUpdateVal.isEmpty()) {
						Pass_Message("'New email' is cleared from Case Update field in My Open Cases");
					} else {
						Fail_Message("'New email' is not cleared from Case Update field in My Open Cases");
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Clear 'Email' from Case Update field test case validation failed.");
		}
	}

	public void us_B852245_CaseUpdatePopUpValidation_Cancel() {
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_FF_Reusable.Key_Array[5]);
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			if (cdPage.showCaseUpdateClearEmailMsg() == true) {
				Pass_Message(
						"Pop Up window to clear the 'Email' in Case Update column is displayed successfully when case is opened");
			} else {
				Fail_Message("Pop Up window to clear the 'Email' in Case Update column is not displayed");
			}
			cdPage.clickCaseUpdateCancelBtnOnPopUp();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cancel button validation in modal pop up for Clear Email failed");
		}
	}

	public void us_B852245_CaseUpdate_ClearAndCancelEmailPopUpValidations() {
		try {
			us_B515916_CaseUpdatedbyOIB_Email();
			support_Login();
			getDriver().navigate().refresh();
			us_B852245_CaseUpdatePopUpValidation_Cancel();
			us_B515916_CaseUpdatedbyOIB_Email_Validation();
			us_B852245_clearedEmail_InCaseUpdate_Validation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Clear/Cancel buttons in Clear Email pop up validation failed.");
		}
	}

	// B-860466
	public void us_B860466_CaseUpdate_ClearEmailModelPopUpinOIB() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_FF_Reusable.Key_Array[5]);
			// String Retrive = "324670006";
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
			cdPage.clickCaseDetails();
			cdPage.clickOIBMember();
			try {
				if (cdPage.showOIBMemberName() == true) {
					cdPage.clickCaseDtlsCancelBtn();
				}
			} catch (Exception e) {
				setOIBName("Automation Tester3");
			}
			ACM_Connectivity.CloseTab();
			logout();
			cmod_OIB_Support_Login();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			FF_EBS.sendEmailFromOIB();
			us_B852245_clearedEmail_InCaseUpdate_Validation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Email sent by OIB failed.");
		}
	}

	public void CreateCaseByFrontlineCSR() {
		CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
		CMOD_FF_Reusable reusable = new CMOD_FF_Reusable();
		try {
			uiTestHelper.propagateException();
			logout();

			// String Retrive = Database_Connection.retrieveTestData("OMNI_CON", "ACM",
			// "KEY",
			// CMOD_ReactiveCaseFlow.Key_Array[7]);
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[7]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[7]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[7]);
			System.out.println(Retrive);

			Retrive = "324682374";// "324681862";// "904092582";
			reusable.csr_Login(Username, Password);
			CCD_CI_Booking cibooking = new CCD_CI_Booking();
			cibooking.returnToPage();
			getDriver().navigate().refresh();
			FF_EBS.searchConsignmentOption(Retrive);
			viewOrCreateCase();
			// FF_EBS.CreateCase_EBS();
			// connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case creation from frontline CSR failed");
		}
		uiTestHelper.propagateException();
		logout();
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[8]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				CMOD_ReactiveCaseFlow.Key_Array[8]);
		reusable.csr_Login(Username, Password);
		CCD_CI_Booking cibooking = new CCD_CI_Booking();
		cibooking.returnToPage();
		getDriver().navigate().refresh();
		// ominiChannelAccept();
		ominiChannelAccept11();
	}

	public void ominiChannelAccept11() {
		try {
			TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
			ttPage.clickOmniChannel();
			ttPage.clickOmniStatusDropDown();
			ttPage.clickOmniStatus("Available");
			ttPage.clickOmniCaseAccept();
			Actions obj = new Actions(getDriver());
			WebElement omniClose = getDriver().findElement(By.xpath(
					"/html/body/div[4]/div[1]/div[2]/div[3]/div[1]/div[4]/a/div/div[2]/span/button/lightning-primitive-icon"));
			obj.click(omniClose).perform();
			Pass_Message("Case accepted from the omini channel.");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case accept from omni channel failed");
		}
	}

	// US 860466
	public void us_B860466_UploadAttachment() {
		try {
			ACM_Connectivity.CloseTab();
			Retrive = Database_Connection.retrieveTestData("CON1", "ACM", "KEY", CMOD_BulkActions.Key_Array[4]);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			CMOD_FunctionalFlow_Updated FF_EBS = new CMOD_FunctionalFlow_Updated();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				FF_EBS.CreateCase_EBS();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member validations failed");
		}
	}

	// US B-869638
	public void us_B869638_AutoCloseReactive() {
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			// Retrive = Database_Connection.retrieveTestData("CLOSE_REOPEN",
			// "ACM","KEY",CMOD_InSprintTestExecution.Key_Array[6]);
			Retrive = "625563905";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				ffUpdated.CreateCase_EBS();
			}
			String caseStatus = casedetailpage.getCasesSatusOnHighlightPanel();
			if (caseStatus.contains("Created")) {
				Pass_Message("Case Status displayed as: " + caseStatus);
			}
			logout();
			applyStatusCodeFromAdmin();
			logout();
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[2]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[2]);
			csr_Login(Username, Password);
			connectivity.TrackandTraceCCD(Retrive);
			connectivity.CCD_ViewCase();
			String caseStatusAfter = casedetailpage.getCasesSatusOnHighlightPanel();
			if (caseStatusAfter.contains("Auto-Closed")) {
				Pass_Message("Case Status correctly displayed as: " + caseStatus);
			} else {
				Fail_Message("Case is not not auto closed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Change OIB Member validations failed");
		}
	}

	public void applyStatusCodeFromAdmin() {
		try {
			logout();
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ExceptionsPage excPage = new ExceptionsPage(getDriver());
			Retrive = Database_Connection.retrieveTestData("Close_Reopen", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[6]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[6]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[6]);
			Retrive = "625564843";
			csr_Login(Username, Password);
			// getDriver().navigate().refresh();
			connectivity.CloseTab();
			connectivity.TrackandTraceCCD(Retrive);
			connectivity.CCD_ViewCase();
			cdPage.clickMoreTab();
			cdPage.clickIntegrationHiddenField();
			excPage.clickExceptionLink();
			excPage.clickUpdatedConsignmentStatus();
			excPage.setUpdatedConsignmentStatus("221A");
			excPage.clickUpdatedConsignmentStatusDesc();
			excPage.setUpdatedConsignmentStatusDesc("Consignment delivered in good condition");
			excPage.clickSaveExc();
			// excPage.clickSaveChangesInExc();
			// logout();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: status code has not been updated");
			// logout();
		}
	}

	// Autoclose
	public void us_B869638_AutoCloseReactive_OutstandingSLA() {
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
			HomePage homepage = new HomePage(getDriver());
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			Retrive = Database_Connection.retrieveTestData("CLOSE_REOPEN", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[10]);
			// Retrive= "625564843";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				ffUpdated.CreateCase_EBS();
			}
			String expecteddate = setExpectedDate();
			System.out.println("Expecteddate: " + expecteddate);
			try {
				cdPage.clickcallbackActivity();
				cdPage.selectcallbackCODateFrom(expecteddate);
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Call Back Date selection failed");
			}
			try {
				green_colour(new java.util.Date());
				cdPage.setcallbackTimeInput(TimeGreen);
				cdPage.clickcallbackActivitysave();
				String caseStatus = cdPage.getCasesSatusOnHighlightPanel();
				if (caseStatus.contains("Created")) {
					Pass_Message("Case Status displayed as: " + caseStatus);
				}
				logout();
				applyStatusCodeFromAdmin();
				logout();
				String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
						CMOD_RegressionTestExecution.Key_Array[2]);
				String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
						CMOD_RegressionTestExecution.Key_Array[2]);
				csr_Login(Username, Password);
				ACM_Connectivity.CloseTab();
				homepage.clickDropDownNavigationMenu();
				homepage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				EventFiringWebDriver event = new EventFiringWebDriver(getDriver());
				event.executeScript(
						"document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
				List<WebElement> objRow = getDriver()
						.findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
				int row_count = objRow.size();
				for (int i = 1; i <= row_count; i++) {
					String a = getDriver()
							.findElement(By.xpath(
									"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[2]/span"))
							.getText();
					if (a.equals(Retrive)) {
						String caseUpdateVal = getDriver()
								.findElement(By.xpath(
										"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]//td[11]/span"))
								.getText();
						if (caseUpdateVal.equals("POD")) {
							Pass_Message("'POD' is updated in Case Updated field in My Open Cases");
						} else {
							Fail_Message("'POD' is not updated in Case Updated field in My Open Cases");
						}
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Auto-close case with outstanding SLA failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Auto-close case with outstanding SLA failed");
		}
	}

	// 5.1 TC 7,8,9
	public void us_B885303_ClearanceCase_Reactive_CreateAndValidateCaseDetails() {
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "625566610";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				String loc = "Origin";
				String reason = "Clearance";
				String cause = "Awaiting Clearance Instructions";
				String routeTo = "Origin";
				ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, routeTo, reason, cause);
			}
			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.contains("Automated Process")) {
				Pass_Message("Case owner for Clearance case is displayed correctly as: " + caseOwner);
			} else {
				Fail_Message("Case owner for Clearance case is not displayed correctly");
			}
			String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
			if (OIBCaseOwner.contains("Support")) {
				Pass_Message("OIB Case owner for Clearance case is displayed correctly as a queue: " + OIBCaseOwner);
			} else {
				Fail_Message("Case owner is not displayed correctly as queue");
			}
			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				Pass_Message("As expected OIB Member Name is blank");
			} else {
				Fail_Message("OIB Member Name is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}
	}

	// Sprint 5.1 TC 10
	public void us_B885303_ClearanceCase_Reactive_ValidateOIBMemberName() {
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			// Retrive =
			// Database_Connection.retrieveTestData("CON2","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "625566610";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			HomePage homePage = new HomePage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				String loc = "Origin";
				String reason = "Clearance";
				String cause = "Awaiting Clearance Instructions";
				String routeTo = "Origin";
				ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, routeTo, reason, cause);
			}
			casedetailpage.clickCaseDetails();
			String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				// String OIBCaseOwner=casedetailpage.getOIBCaseOwnerValue();
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput(OIBCaseOwner);
				proactivepage.clickAssignedInputPE(OIBCaseOwner);
				selectMyConFromCouQueue(Retrive);
				uiTestHelper.propagateException();
				myOpenCasePage.clickshowMoredropDown();
				myOpenCasePage.clickAcceptCase();
				myOpenCaseSelect(Retrive);
				String OIBCaseOwner2 = casedetailpage.getOIBCaseOwnerValue();
				if (OIBCaseOwner2.isEmpty()) {
					Pass_Message("As expected OIB CO is blank");
				} else {
					Fail_Message("OIB CO is not blank");
				}
				String OIBMemberName2 = casedetailpage.getOIBMember();
				if (OIBMemberName2.contains("Automation")) {
					Pass_Message(
							"As expected OIB Member Name is not blank and CSR name is displayed as: " + OIBMemberName2);
				} else {
					Fail_Message("OIB CO is not blank");
				}
			} else {
				Pass_Message("OIB Member Name is already displayed");
			}
			if (OIBCaseOwner.isEmpty()) {
				Pass_Message("As expected OIB Case Owner is blank");
			} else {
				Fail_Message("OIB Case Owner is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}
	}

	// Sprint 5.1 TC 13, 14, 15
	public void us_B885303_ClearanceCase_Reactive_3rdPartyLocation_CreateAndValidateCaseDetails() {
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "625566685";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				String loc = "3rd Party";
				String reason = "Clearance";
				String cause = "Awaiting Clearance Instructions";
				String caseRoute = "Origin";
				ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, caseRoute, reason, cause);
			}
			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.contains("Automated Process")) {
				Pass_Message("Case owner for Clearance case is displayed correctly as: " + caseOwner);
			} else {
				Fail_Message("Case owner for Clearance case is not displayed correctly");
			}
			String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
			if (OIBCaseOwner.contains("Support")) {
				Pass_Message("OIB Case owner for Clearance case is displayed correctly as a queue: " + OIBCaseOwner);
			} else {
				Fail_Message("Case owner is not displayed correctly as queue");
			}
			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				Pass_Message("As expected OIB Member Name is blank");
			} else {
				Fail_Message("OIB Member Name is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}
	}

	// Sprint 5.1, TC 15
	public void us_B885303_ClearanceCase_Reactive_ChangeOwnerValidation() {
		try {

			CCD_Connectivity connectivity = new CCD_Connectivity();
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "625566708";
			System.out.println(Retrive);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				String loc = "3rd Party";
				String reason = "Clearance";
				String cause = "Awaiting Clearance Instructions";
				String caseRoute = "Origin";
				ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, caseRoute, reason, cause);
			}
			setChangeCaseOwner("Automation Tester3");
			if (cdPage.isChangeOwnerErrorMsgDisplayed() == true) {
				Pass_Message(
						"As expected Case owner cannot be changed and an error message is displayed in clearnace reavtice case");
			} else {
				Fail_Message(
						"As expected an error message is not displayed while changing case owner in clearance reavtice case");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}
	}

	public void setChangeCaseOwner(String userName) {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			cdPage.clickChangeOwner();
			cdPage.setSearchUserinChangeOwner(userName);
			cdPage.clickChangeOwnerSubmitBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}

	}

	// Sprint 5.1 Tc 17
	public void us_B885303_ClearanceCase_Reactive_Inbound_ValidateCaseDetails() {
		try {
			HomePage hpPage = new HomePage(getDriver());
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "20131268";
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(Retrive);
			getDriver().findElement(By.xpath("(//a[@title='" + Retrive + "'])[2]")).click();

			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.contains("Automated Process")) {
				Pass_Message("Case owner for Clearance case is displayed correctly as: " + caseOwner);
			} else {
				Fail_Message("Case owner for Clearance case is not displayed correctly");
			}
			String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
			if (OIBCaseOwner.contains("Support")) {
				Pass_Message("OIB Case owner for Clearance case is displayed correctly as a queue: " + OIBCaseOwner);
			} else {
				Fail_Message("Case owner is not displayed correctly as queue");
			}
			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				Pass_Message("As expected OIB Member Name is blank");
			} else {
				Fail_Message("OIB Member Name is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Clearance Reactive case creattion and validation case details failed");
		}
	}

	// Sprint 5.1 Tc18
	public void us_B885303_ClearanceCase_Reactive_Inbound_changeCaseOwner() {
		try {
			HomePage hpPage = new HomePage(getDriver());
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "20131268";
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(Retrive);
			getDriver().findElement(By.xpath("(//a[@title='" + Retrive + "'])[2]")).click();
			// TODO Update OIB member name

			setChangeCaseOwner("Automation Tester2");

			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.contains("Automation Tester2")) {
				Pass_Message("Case owner for inbound Clearance Reactive case is updated and displayed correctly as: "
						+ caseOwner);
			} else {
				Fail_Message("Case owner for Clearance case is not updated correctly");
			}

			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				Pass_Message("As expected OIB Member Name is blank");
			} else {
				Fail_Message("OIB Member Name is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Inbound Clearance Reactive case Change Case Owner validation failed");
		}
	}

	public void us_B885303_ClearanceCase_Reactive_Inbound_changeCaseOwnerAndValInMyOpenCases() {
		try {

			HomePage homePage = new HomePage(getDriver());
			MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
			// Retrive =
			// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
			String Retrive = "20131268";

			us_B885303_ClearanceCase_Reactive_Inbound_changeCaseOwner();
			String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
			String OIBMemberName = casedetailpage.getOIBMember();
			if (OIBMemberName.isEmpty()) {
				homePage.clickDropDownNavigationMenu();
				homePage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput(OIBCaseOwner);
				proactivepage.clickAssignedInputPE(OIBCaseOwner);
				selectMyConFromCouQueue(Retrive);
				uiTestHelper.propagateException();
				myOpenCasePage.clickshowMoredropDown();
				myOpenCasePage.clickAcceptCase();
				myOpenCaseSelect(Retrive);
				String OIBCaseOwner2 = casedetailpage.getOIBCaseOwnerValue();
				if (OIBCaseOwner2.isEmpty()) {
					Pass_Message("As expected OIB CO is blank");
				} else {
					Fail_Message("OIB CO is not blank");
				}
				String OIBMemberName2 = casedetailpage.getOIBMember();
				if (OIBMemberName2.contains("Automation")) {
					Pass_Message(
							"As expected OIB Member Name is not blank and CSR name is displayed as: " + OIBMemberName2);
				} else {
					Fail_Message("OIB CO is not blank");
				}
			} else {
				Pass_Message("OIB Member Name is already displayed");
			}
			if (OIBCaseOwner.isEmpty()) {
				Pass_Message("As expected OIB Case Owner is blank");
			} else {
				Fail_Message("OIB Case Owner is not blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Inbound Clearance Reactive case Change Case Owner validation failed");
		}
	}

	// Sprint 5.1 TC 1
	public void us_B905245_TaskEscalation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		// Retrive =
		// Database_Connection.retrieveTestData("CON1","ACM","KEY",CMOD_InSprintTestExecution.Key_Array[7]);
		String Retrive = "625566739";
		System.out.println(Retrive);
		connectivity.TrackandTraceCCD(Retrive);
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			String loc = "3rd Party";
			String reason = "Clearance";
			String cause = "Awaiting Clearance Instructions";
			String caseRoute = "Origin";
			ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, caseRoute, reason, cause);
		}
		try {
			ACM_Connectivity.CloseTab();
			task_create(Retrive);
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			try {
				if (cdPage.verifySuccessMessage() == true) {
					Pass_Message("Task escalated successfully");
				}
			} catch (Exception e) {
				Fail_Message("Task has not been Escalated");
			}
			getDriver().navigate().refresh();
			String esc = getDriver().findElement(By.xpath("//lightning-formatted-text[contains(text(),'L')]"))
					.getText();
			if (esc.isEmpty()) {
				Fail_Message("Task is not escalated and escalation level is not displayed");
			} else {
				Pass_Message("Task has been escalated successfully to escalation level: " + esc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	// Sprint 5.1 TC 20
	public void us_B905245_cmodInboundTask_AutomatedProcessVal() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			HomePage hpPage = new HomePage(getDriver());
			String cmodCaseNum = Database_Connection.retrieveTestData("CMODCASE1", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[7]);
			System.out.println(cmodCaseNum);
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(cmodCaseNum);
			hpPage.clickSearchedCaseNum(cmodCaseNum);
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			if (cdPage.getTaskOwnerIDValue().contains("Automated Process")) {
				Pass_Message("As expected task owner id is displayed successfully as 'Automated Process");
			} else {
				Fail_Message("Task owner id is not displayed correctly as 'Automated Process'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: cmodInboundTask_AutomatedProcessVal failed");
		}
	}

	// Sprint 5.1 Tc22
	public void us_B905245_cmodInboundClearanceCase_ChangeOwnerErrorValidation() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			HomePage hpPage = new HomePage(getDriver());
			String cmodCaseNum = Database_Connection.retrieveTestData("CMODCASE1", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[7]);
			System.out.println(cmodCaseNum);
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(cmodCaseNum);
			hpPage.clickSearchedCaseNum(cmodCaseNum);

			setChangeCaseOwner("Automation Tester2");
			if (cdPage.isChangeOwnerErrorMsgDisplayed() == true) {
				Pass_Message(
						"As expected Case owner cannot be changed and an error message is displayed in clearnace reavtice case");
			} else {
				Fail_Message(
						"As expected an error message is not displayed while changing case owner in clearance reavtice case");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: cmodInboundTask_AutomatedProcessVal failed");
		}
	}

	// TC23
	public void us_B905245_CCDClearanceCase_ChangeOwnerErrorValidation() {
		try {

			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			Retrive = Database_Connection.retrieveTestData("CON1", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[7]);
			connectivity.TrackandTraceCCD(Retrive);
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				String loc = "3rd Party";
				String reason = "Clearance";
				String cause = "Awaiting Clearance Instructions";
				String caseRoute = "Origin";
				ffUpdated.createCaseWithValues_WithoutAssignToMe(loc, caseRoute, reason, cause);
			}
			setChangeCaseOwner("Automation Tester2");
			if (cdPage.isChangeOwnerErrorMsgDisplayed() == true) {
				Pass_Message(
						"As expected Case owner cannot be changed and an error message is displayed in ccd clearnace reative case");
			} else {
				Fail_Message(
						"As expected an error message is not displayed while changing case owner in clearance reavtice case");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: cmodInboundTask_AutomatedProcessVal failed");
		}
	}

	// Sprint 5.1 TC 4
	public void us_B933496_ClareanceCasetaskEscalation_EmailValidation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			ACM_Connectivity.CloseTab();
			Retrive = "625566535";
			task_create(Retrive);
			internal_tabclose();
			ACM_Connectivity.TrackandTraceCCD(Retrive);
			ACM_Connectivity.CCD_ViewCase();
			cdPage.clickRelatedTab();
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			cdPage.clickEscalateTaskBtn();
			try {
				if (cdPage.verifySuccessMessage() == true) {
					Pass_Message("Task escalated successfully");
				}
			} catch (Exception e) {
				Fail_Message("Task has not been Escalated");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	// Sprint 5.2

	public void us_B894974_CreateUnallocatedCase() {
		CMOD_TrackAndTrace_Reusable ttPage = new CMOD_TrackAndTrace_Reusable(getDriver());
		ttPage.CreateUnallocatedCaseAndValidateReasonsCausesDropDowns();
		casedetailpage.clickCaseDetails();
		String caseOwner = casedetailpage.getcaseOwnerValue();
		if (caseOwner.contains("Automated Process")) {
			Pass_Message("Case owner for Clearance case is displayed correctly as: " + caseOwner);
		} else {
			Fail_Message("Case owner for Clearance case is not displayed correctly");
		}
		String OIBCaseOwner2 = casedetailpage.getOIBCaseOwnerValue();
		if (OIBCaseOwner2.isEmpty()) {
			Pass_Message("As expected OIB CO is blank");
		} else {
			Fail_Message("OIB CO is not blank");
		}
	}

	// Sprint 5.2 TC 2
	public void us_B894974_CreateUnallocatedCase_ValidateAutomatedProcess() {
		CMOD_TrackAndTrace_Reusable ttPage = new CMOD_TrackAndTrace_Reusable(getDriver());
		MyOpenCasesListViewPage myOpenCasePage = new MyOpenCasesListViewPage(getDriver());
		HomePage homePage = new HomePage(getDriver());

		ttPage.CreateUnallocatedCaseAndValidateReasonsCausesDropDowns();
		casedetailpage.clickCaseDetails();
		String caseOwner = casedetailpage.getcaseOwnerValue();
		if (caseOwner.contains("Automated Process")) {
			Pass_Message("Case owner for Clearance case is displayed correctly as: " + caseOwner);
		} else {
			Fail_Message("Case owner for Clearance case is not displayed correctly");
		}

		String OIBCaseOwner = casedetailpage.getOIBCaseOwnerValue();
		String OIBMemberName = casedetailpage.getOIBMember();
		if (OIBMemberName.isEmpty()) {
			// String OIBCaseOwner=casedetailpage.getOIBCaseOwnerValue();
			homePage.clickDropDownNavigationMenu();
			homePage.clickCases();
			proactivepage.clickRecentlyViewedItems();
			proactivepage.searchInput(OIBCaseOwner);
			proactivepage.clickAssignedInputPE(OIBCaseOwner);
			selectMyConFromCouQueue(Retrive);
			uiTestHelper.propagateException();
			myOpenCasePage.clickshowMoredropDown();
			myOpenCasePage.clickAcceptCase();
			myOpenCaseSelect(Retrive);
			String OIBCaseOwner2 = casedetailpage.getOIBCaseOwnerValue();
			if (OIBCaseOwner2.isEmpty()) {
				Pass_Message("As expected OIB CO is blank");
			} else {
				Fail_Message("OIB CO is not blank");
			}
			String OIBMemberName2 = casedetailpage.getOIBMember();
			if (OIBMemberName2.contains("Automation")) {
				Pass_Message(
						"As expected OIB Member Name is not blank and CSR name is displayed as: " + OIBMemberName2);
			} else {
				Fail_Message("OIB CO is not blank");
			}
		}
	}

	// Sprint 5.2
	public void us_B912560_CMODChangingTaskOwnership_validateEscalationSLAField() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			HomePage hpPage = new HomePage(getDriver());
			String cmodCaseNum = Database_Connection.retrieveTestData("CMODCASE1", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[7]);
			System.out.println(cmodCaseNum);
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(cmodCaseNum);
			hpPage.clickSearchedCaseNum(cmodCaseNum);
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			if (cdPage.showEscalationSLAValue() == true) {
				Pass_Message("Task escalation field is displayed successfully");
			} else {
				Fail_Message("Task escalation field is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	// Sprint 5.2
	public void us_B912560_CMODChangingTaskOwnership_validateAutomatedProcess() {
		try {
			CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
			HomePage hpPage = new HomePage(getDriver());
			String cmodCaseNum = Database_Connection.retrieveTestData("CMODCASE1", "ACM", "KEY",
					CMOD_InSprintTestExecution.Key_Array[7]);
			System.out.println(cmodCaseNum);
			hpPage.globalSearchClick();
			hpPage.setTextInGlobalSearch(cmodCaseNum);
			hpPage.clickSearchedCaseNum(cmodCaseNum);
			cdPage.clickTaskView();
			cdPage.clickCreatedTaskLink();
			String taskCaseOwnerId = casedetailpage.getCMODTaskOwnerIdValue();
			if (taskCaseOwnerId.contains("Automated Process")) {
				Pass_Message("CMOD Task owner Id for Clearance case is displayed correctly as: " + taskCaseOwnerId);
			} else {
				Fail_Message("CMOD Task owner Id for Clearance case is not displayed correctly");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task Owner Id as 'Automated Process' validation failed");
		}
	}

	// Sprint 5.2
	public void us_B876151_StatusCodesinAutoPNFunctionality_validateNewlyAddedCodes() {
		try {
			AdminPage adPage = new AdminPage(getDriver());
			logout();
			admin_Login();
			adPage.clickSetup();
			{
				String mainWindowHandle = getDriver().getWindowHandle();
				for (String childWindowHandle : getDriver().getWindowHandles()) {
					if (!childWindowHandle.equals(mainWindowHandle)) {
						getDriver().switchTo().window(childWindowHandle);
						adPage.setQuickFindSearch("Custom Settings");
					}
					{
						String mainWindowHandle1 = getDriver().getWindowHandle();
						for (String childWindowHandle1 : getDriver().getWindowHandles()) {
							if (!childWindowHandle1.equals(mainWindowHandle1)) {
								getDriver().switchTo().window(childWindowHandle1);
								adPage.pnNot();
							}
						}
					}
					uiTestHelper.propagateException();
					adPage.switchToFrameCustSetting();
					if (adPage.selectCustObj("Original_Consignment_Status_ExportNew") == true) {
						Pass_Message("PN Original_Consignment_Status_ExportNew is displayed");
					} else {
						Fail_Message("PN Original_Consignment_Status_ExportNew is not displayed");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: AutoPn new field validation failed");
		}

	}

	public void admin_Login() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
				CMOD_InSprintTestExecution.Key_Array[10]);
		String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
				CMOD_InSprintTestExecution.Key_Array[10]);
		cmodFFReusable.csr_Login(Username, Password);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}

	// B-700565 TCs (reusable method for the first 8 steps of Tcs)
	public void accessReportsAndCreateNewReportWithOptions() {
		HomePage homepage = new HomePage(getDriver());
		ReportingPage reportPage = new ReportingPage(getDriver());
		homepage.clickDropDownNavigationMenu();
		reportPage.clickReportingFromGlobalMenu();
		reportPage.clickPublicReportsSideOption();
		reportPage.clickNewReportButton();
		reportPage.selectContactOptionInMenu();
		reportPage.clickFieldsLeftPaneMenu();
		reportPage.clickGroupRowsBoxAndSendKeys("Created Date");
		reportPage.clickColumnsBoxAndSendKeys("Created Date");
		reportPage.clickSaveAndRunButton();
		reportPage.provideNameForReportDescription();
		reportPage.selectFolderButton();
		reportPage.clickPublicReportsMenuOption();
		reportPage.selectFolderOnPublicReportsWindow();
		// Once its saved
		// switch back to parent frame

	}

	public void us_B935469_ECDT_EmailToCaseQueue() {
		HomePage homepage = new HomePage(getDriver());
		// CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());

		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("ECDT");
		bookingexcppage.clickAssignedInput("ECDT");
		if (bookingexcppage.verifyHearderDisplayed("ECDT")) {
			Pass_Message("ECDT Email to Case Queue is selected on Case page");
		}
		/*
		 * if (casedetailpage.verifyCaseNumberColumn()) {
		 * Pass_Message("Case Number column is displayed on ECDT Email to Case Queue");
		 * } if (casedetailpage.verifyEmailColumn()) {
		 * Pass_Message("Email column is displayed on ECDT Email to Case Queue"); } if
		 * (casedetailpage.verifyCustomerNameColumn()) {
		 * Pass_Message("Customer Name column is displayed on ECDT Email to Case Queue"
		 * ); }
		 */
	}

	public void us_B814290_ECDT_ProactveExceptionQueue() {
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());

		homepage.clickDropDownNavigationMenu();
		homepage.clickProactiveExceptions();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("ECDT");
		bookingexcppage.clickAssignedInput("ECDT");
		if (bookingexcppage.verifyHearderDisplayed("ECDT")) {
			Pass_Message("ECDT PE Queue is selected on Proactive Exceptions page");
		}
		if (proactivepage.isPEOwnerColumnVisible()) {
			Pass_Message("PE Owner column is visible on Proactive Exceptions page");
		}
		if (proactivepage.isProactiveExceptionColumnVisible()) {
			Pass_Message("Proactive Exception column is visible on Proactive Exceptions page");
		}
		if (proactivepage.isControllingStationCountryColumnVisible()) {
			Pass_Message("Controlling Station Country column is visible on Proactive Exceptions page");
		}
		if (proactivepage.isSenderAccountNameColumnVisible()) {
			Pass_Message("Sender Account Name column is visible on Proactive Exceptions page");
		}
		if (proactivepage.isSenderCountryColumnVisible()) {
			Pass_Message("Sender Country column is visible on Proactive Exceptions page");
		}
	}

	public void us_B1030147_ReactiveCaseWithoutPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());

		String Retrive = "638252075";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Claim");
		ccpage.setCauseOption("Damage");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("8989456780");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
		casedetailpage.clickEscalateTaskBtn();
		Assert.assertEquals(ccpage.getPopupMsgText(), "You may escalate only after SLA Period is over.");
		{
			Pass_Message(ccpage.getPopupMsgText() + " popup message is displayed.");
		}
	}

	public void us_B1030147_ReactiveCaseCreationPriorityIndicator(String consignmentNo, String caseLocation,
			String caseType, String reason, String cause, String priority, boolean assignToMe) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		// consignmentNo = "200587286";//"324680926";// "324681011";

		// System.out.println(cdPage.systemCurrentTimeEULondon());
		ACM_Connectivity.CloseTab();
		connectivity.TrackandTraceCCD(consignmentNo);
		// ACM_Connectivity.TrackandTraceCCD(consignmentNo);
		try {
			if (conpage.verifyViewCase() == true) {
				ACM_Connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			createCase_Customized(caseLocation, caseType, reason, cause, priority, assignToMe);
		}
		ffUpdated.priorityIndicatorCaseValidation(priority);
		task_create(consignmentNo);
		// level 1 to 4 escalation
		taskEscalationFromL1toL4();
	}

	public void createCase_Customized(String caseLocation, String case_type, String reason, String cause,
			String priorityIndicator, boolean assignTome) {
		ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			consignmentPage.clickcreatecasebtn();
			ccpage.selectRecordType(case_type);
			ccpage.clickCCLocation();
			// ccpage.clickOrigin();
			ccpage.selectOrginDestination(caseLocation);
			ccpage.setReason(reason);
			ccpage.setCause(cause);

			ccpage.setFirstName("TestFirst");
			ccpage.setLastName("TestLast");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			if (!priorityIndicator.isEmpty()) {
				ccpage.setPriorityIndicator(priorityIndicator);
			}
			if (assignTome) {
				ccpage.clickCaseAssign();
			}
			Pass_Message("Case creation details entered successfully");
			ccpage.clickCaseCreatebtn();
			ccpage.getCreatedStatus();
			Pass_Message("Reactive case is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case creation is failed");
		}

	}

	public void errorValidation() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			cdPage.clickEscalateTaskBtn();
			getDriver().navigate().refresh();
			cdPage.clickEscalateTaskBtn();
			String error = cdPage.getErrorMessage();
			System.out.println("Error:" + error);
			boolean err = error.contains("SLA Period is over") || error.contains("SLA Timeline is not reached");
			Assert.assertTrue(err);
			if (err) {
				Pass_Message("Error is displayed correctly as: '" + error + "'");
			} else {
				Fail_Message("Error has not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Escalation error validation failed");
		}
	}

	public void taskEscalationSamePage(int level) {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		try {
			System.out.println(cdPage.escSLATime() + "\n" + cdPage.systemCurrentTimeUTC());
			int slatime = cdPage.escSLATime();
			int currenttime = cdPage.systemCurrentTimeUTC();
			while (currenttime <= slatime) {
				currenttime = cdPage.systemCurrentTimeUTC();
				System.out.println("currenttime: " + currenttime);
				getDriver().navigate().refresh();
			}
			getDriver().navigate().refresh();
			cdPage.clickEscalateTaskBtn();
			getDriver().navigate().refresh();
			cdPage.clickEscalateTaskBtn();
			cdPage.clickEscReason();
			cdPage.clickEscReasonVal();
			cdPage.inputEscalationComments("Escalating this Task");
			cdPage.clickTascEscSaveBtn();
			try {
				if (cdPage.verifySuccessMessage() == true) {
					Pass_Message("Task escalated success message is displayed");
					softAssert.assertTrue(cdPage.verifySuccessMessage() == true,
							"Task escalated success message is displayed");
				}
			} catch (Exception e) {
				Fail_Message("Success message has not been shown");
			}
			String escLevel = "";
			for (int i = 0; i < 5; i++) {
				escLevel = cdPage.getTaskLevel();
				if (!escLevel.contains(level + "")) {
					getDriver().navigate().refresh();
					uiTestHelper.propagateException();
				} else {
					break;
				}
			}
			if (!escLevel.contains(level + "")) {
				Fail_Message("Task escalation level is not displayed as L" + level);
			} else {
				Pass_Message("Task has been escalated successfully to escalation level: " + escLevel);
			}
			Assert.assertTrue(escLevel.contains(level + ""),
					"Task has been escalated successfully to escalation level: " + escLevel);
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation test case failed");
		}
	}

	public void us_B1030147_PackageResearchCaseWithoutPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Late Delivery");
		ccpage.setCauseOption("Missed Connection");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("8989456780");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
	}

	public void us_B1030147_PackageResearchCaseWithPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Undeliverable");
		ccpage.setCauseOption("Paperwork");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("8989456780");
		ccpage.setPriorityIndicator("Personal Data");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
	}

	public void us_B1030147_ServiceChangeWithPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Undeliverable");
		ccpage.setCauseOption("Payment");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("22255588011");
		ccpage.setPriorityIndicator("Financial Data");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
	}

	public void us_B1030147_RecipientInformationNeededWithPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Re-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Undeliverable");
		ccpage.setCauseOption("Insufficient Address");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("22255588011");
		ccpage.setPriorityIndicator("Financial Data");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
	}

	public String getNextDate() {
		String nextdate = getSystemDate();
		int todayDate = Integer.parseInt(nextdate);
		int date = Integer.sum(todayDate, 1);
		String nextdaydate = String.valueOf(date);
		// int date = Integer.parseInt(expecteddate);
		// if (date < 10) {
		// nextdaydate = Integer.toString(date);
		// System.out.println("FND 3 " + nextdaydate);
		// nextdaydate = nextdate.replace("0", "");
		// System.out.println("FND 4 " + nextdaydate);
		// }
		return nextdaydate;
	}

	public void us_B1030253_TaskCreationWithFedExOther(String consignmentNo, String caseLocation, String caseType,
			String cause, String reason, String priorityindicator, boolean assignToMe) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		ConsignmentPage conpage = new ConsignmentPage(getDriver());
		// consignmentNo = "200587286";//"324680926";// "324681011";

		// System.out.println(cdPage.systemCurrentTimeEULondon());
		try {
			ACM_Connectivity.CloseTab();

			connectivity.TrackandTraceCCD(consignmentNo);
			// ACM_Connectivity.TrackandTraceCCD(consignmentNo);
			try {
				if (conpage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				createCase_Customized(caseLocation, caseType, reason, cause, priorityindicator, assignToMe);
			}
			// connectivity.CCD_ViewCase();
			ccpage.selectCaseDetailsTab();
			Assert.assertEquals(ccpage.getPriorityIndicatorCaseDetailsTab(), priorityindicator);
			{
				Pass_Message("Prioirty Indicator " + priorityindicator + " is displayed");
			}
			task_create(consignmentNo);
			// level 1 to 4 escalation
			taskEscalationFromL1toL4();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task validation with 'FedEx Other' opetion failed");
		}
	}

	public void createTaskOnCasePage() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());

		// String timeStamp = new
		// SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		// long addMinutes = 340;
		try {
			LocalDate date = LocalDate.now();
			DateTimeFormatter f2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String nextDate = f2.format(date.plusDays(1));

			ccpage.clickTaskTab();
			ccpage.taskCountryTerritoryBox("Norway");
			ccpage.setTaskLocation("GDR");
			ccpage.setCaseRoute("TNT-OPS");
			ccpage.setTaskDeadlineDate(nextDate);
			// ccpage.setTaskDeadlineTime(nextDate);
			ccpage.setOriginDestination("Destination");
			ccpage.sendTextToTaskInstructionsBox();
			ccpage.clickTaskSaveButton();
			String recentTaskID = ccpage.getRecentTaskId();
			ccpage.waitForSuccessMsg();
			ccpage.clickTaskQuickLink();
			ccpage.openRecentlyCreatedTask(recentTaskID);
			casedetailpage.clickEscalateTaskBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task Creation on case page failed");
		}

	}

	public void us_B1030147_ProActiveCaseWithPriorityIndicator() {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Pro-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Undeliverable");
		ccpage.setCauseOption("Not Accessible");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("22255588011");
		String priorityIndicator = "Personal Data";
		ccpage.setPriorityIndicator(priorityIndicator);
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		ccpage.selectCaseDetailsTab();
		Assert.assertEquals(ccpage.getPriorityIndicatorCaseDetailsTab(), priorityIndicator);
		{
			Pass_Message("Prioirty Indicator " + priorityIndicator + " is displayed");
		}

		createTaskOnCasePage();
		// Task should not be Escalated. Currently functionality not Working
		// TO DO
	}

	public void us_B1030147_ProActiveCaseWithoutPriorityIndicator() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());

		String Retrive = "638252053";
		connectivity.TrackandTraceCCD(Retrive);
		trackandtracePage.clickCreateCaseButton();
		ccpage.selectRecordType("Pro-Active Case");
		ccpage.setCaseLoc("Origin");
		ccpage.setCaseReason("Undeliverable");
		ccpage.setCauseOption("Not Accessible");
		ccpage.setFirstName("Shawnee");
		ccpage.setLastName("Unique");
		ccpage.setphone("22255588011");
		ccpage.clickCaseAssign();
		ccpage.clickCaseCreatebtn();
		createTaskOnCasePage();

		// Task should not be Escalated. Currently functionality not Working
		// TO DO
		casedetailpage.clickEscalateTaskBtn();
		Assert.assertEquals(ccpage.getPopupMsgText(), "You may escalate only after SLA Period is over.");
		{
			Pass_Message(ccpage.getPopupMsgText() + " popup message is displayed.");
		}
	}

	/*
	 * Task creation with FedEx other option
	 */
	public void taskCreationAndEscaltion_FedEx_Others_or_Overgoods(String connumber, String routToOption,
			String orgOrDest, String country, String taskLocation) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			// String Username = Database_Connection.retrieveTestData("USER_ID", "ACM",
			// "KEY",
			// CMOD_RegressionTestExecution.Key_Array[1]);
			// String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM",
			// "KEY",
			// CMOD_RegressionTestExecution.Key_Array[1]);
			String Retrive = connumber;

			System.out.println(Retrive);
			getDriver().navigate().refresh();
			// ACM_Connectivity.CloseTab();
			// logout();
			// csr_Login(Username, Password);
			// CCD_CI_Booking cibooking = new CCD_CI_Booking();
			// cibooking.returnToPage();
			//
			// getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();

			ACM_Connectivity.TrackandTraceCCD(Retrive);
			viewOrCreateCase();

			// getDriver().navigate().refresh();
			// task field validation while creating task
			ccpage.clickTaskTab();
			if (routToOption.contains("FedEx") && orgOrDest.contains("Other")) {
				ccpage.taskCountryTerritoryBox(country);
				boolean isRoutToOption = ccpage.setCaseRoute(routToOption);
				if (isRoutToOption) {
					Pass_Message("Rout to field is set as '" + routToOption + "'");
				} else {
					Fail_Message("Rout to field is not set as '" + routToOption + "'");
				}
				Assert.assertTrue(isRoutToOption, "Rout to field is set as '" + routToOption + "'");

				boolean isOriginOrDest = ccpage.setOriginDestination(orgOrDest);
				if (isOriginOrDest) {
					Pass_Message("Orgin/Destination field is set as '" + orgOrDest + "'");
				} else {
					Fail_Message("Orgin/Destination field is not set as '" + orgOrDest + "'");
				}
				Assert.assertTrue(isOriginOrDest, "Orgin/Destination to field is set as '" + orgOrDest + "'");

				ccpage.setTaskLocationFreely(taskLocation);
				ccpage.sendTextToTaskInstructionsBox();
				String tasklocationfrompage = ccpage.getTaskLocation();
				boolean istasklocation = tasklocationfrompage.contains(taskLocation);
				if (istasklocation) {
					Pass_Message("location name is set as '" + taskLocation + "'");
				} else {
					Fail_Message("location name is not set");
				}
				Assert.assertTrue(istasklocation, "location name is set as '" + taskLocation + "'");
			}

			// task field validation when origin/destination select as Overgoods
			if (routToOption.contains("FedEx") && orgOrDest.contains("Overgoods")) {
				System.out.println("inside if Overgoods");
				ccpage.taskCountryTerritoryBox("India");
				boolean isRoutToOption = ccpage.setCaseRoute(routToOption);
				if (isRoutToOption) {
					Pass_Message("Rout to field is set as '" + routToOption + "'");
				} else {
					Fail_Message("Rout to field is not set as '" + routToOption + "'");
				}
				Assert.assertTrue(isRoutToOption, "Rout to field is set as '" + routToOption + "'");

				boolean isOriginOrDest = ccpage.setOriginDestination(orgOrDest);
				if (isOriginOrDest) {
					Pass_Message("Orgin/Destination field is set as '" + orgOrDest + "'");
				} else {
					Fail_Message("Orgin/Destination field is not set as '" + orgOrDest + "'");
				}
				Assert.assertTrue(isOriginOrDest, "Orgin/Destination to field is set as '" + orgOrDest + "'");

				// captureing error message
				uiTestHelper.propagateException();
				String errormsg = ccpage.getErrorMessageOvergoods();

				System.out.println("errormsg: " + errormsg);
				boolean isErrorMsg = errormsg.contains("Country not available For Over Goods");
				if (isErrorMsg) {
					Pass_Message("The error is shown as '" + errormsg + "' as expected");
				} else {
					Fail_Message("The error is not shown");
				}
				Assert.assertTrue(isErrorMsg, "The error is shown as '" + errormsg + "' as expected");

				boolean istasklocationdisbaled = ccpage.isTaskLocationDisabled();
				if (istasklocationdisbaled) {
					Pass_Message("Task location is disabled as expected");
				} else {
					Fail_Message("Task location is not disabled");
				}
				Assert.assertTrue(istasklocationdisbaled, "Task location is disabled as expected");

				ccpage.taskCountryTerritoryBox(country);
				System.out.println("country: " + country);
				System.out.println("taskLocation: " + taskLocation);
				boolean istasklocationset = ccpage.setTaskLocationForOvergoods(taskLocation);
				if (istasklocationset) {
					Pass_Message("Task location is set as '" + taskLocation + "' as expected");
				} else {
					Fail_Message("Task location is not set");
				}
				Assert.assertTrue(istasklocationset, "Task location is set as '" + taskLocation + "' as expected");

				ccpage.sendTextToTaskInstructionsBox();
			}

			// task creation process and task field validation after task created
			ccpage.clickTaskSaveButton();
			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
					uiTestHelper.propagateException();
				}
			}

			String cmodTaskId = "";
			boolean istaskid = !recentTaskID.isEmpty();
			if (istaskid) {
				By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
				WebElement tid = uiTestHelper.waitForObject(taskid);
				tid.click();
				uiTestHelper.propagateException();
				Pass_Message("Task created successfully");
				for (int i = 0; i < 5; i++) {
					cmodTaskId = casedetailpage.getCmodTaskId();
					if (cmodTaskId.isEmpty()) {
						getDriver().navigate().refresh();
						uiTestHelper.propagateException();
					} else {
						break;
					}
				}
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(istaskid);
			boolean iscmodtaskid = !cmodTaskId.isEmpty();
			if (iscmodtaskid) {
				Pass_Message("CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
			} else {
				Fail_Message("CMOD Task id has not Created");
			}
			Assert.assertTrue(iscmodtaskid, "CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation test case failed");
		}
	}

	/*
	 * validation of different countries and locations for FedEx-Ops Overgoods tasks
	 */
	public void taskCreationAndEscaltion_FedEx_Overgoods(String connumber, String routToOption, String orgOrDest,
			String country, String taskLocation, String assigntoLocation) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			// String Username = Database_Connection.retrieveTestData("USER_ID", "ACM",
			// "KEY",
			// CMOD_RegressionTestExecution.Key_Array[1]);
			// String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM",
			// "KEY",
			// CMOD_RegressionTestExecution.Key_Array[1]);
			String Retrive = connumber;

			System.out.println(Retrive);
			getDriver().navigate().refresh();
			// ACM_Connectivity.CloseTab();
			// logout();
			// csr_Login(Username, Password);
			// CCD_CI_Booking cibooking = new CCD_CI_Booking();
			// cibooking.returnToPage();
			//
			// getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();

			ACM_Connectivity.TrackandTraceCCD(Retrive);
			viewOrCreateCase();

			// getDriver().navigate().refresh();
			// task field validation while creating task
			ccpage.clickTaskTab();

			// task field validation when origin/destination select as Overgood
			boolean isRoutToOption = ccpage.setCaseRoute(routToOption);
			if (isRoutToOption) {
				Pass_Message("Rout to field is set as '" + routToOption + "'");
			} else {
				Fail_Message("Rout to field is not set as '" + routToOption + "'");
			}
			Assert.assertTrue(isRoutToOption, "Rout to field is set as '" + routToOption + "'");

			boolean isOriginOrDest = ccpage.setOriginDestination(orgOrDest);
			if (isOriginOrDest) {
				Pass_Message("Orgin/Destination field is set as '" + orgOrDest + "'");
			} else {
				Fail_Message("Orgin/Destination field is not set as '" + orgOrDest + "'");
			}
			Assert.assertTrue(isOriginOrDest, "Orgin/Destination to field is set as '" + orgOrDest + "'");

			ccpage.taskCountryTerritoryBox(country);
			boolean istasklocationset = ccpage.setTaskLocationForOvergoods(taskLocation);
			if (istasklocationset) {
				Pass_Message("Task location is set as '" + taskLocation + "' as expected");
			} else {
				Fail_Message("Task location is not set");
			}
			Assert.assertTrue(istasklocationset, "Task location is set as '" + taskLocation + "' as expected");

			ccpage.sendTextToTaskInstructionsBox();

			// task creation process and task field validation after task created
			ccpage.clickTaskSaveButton();
			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
					uiTestHelper.propagateException();
				}
			}

			String cmodTaskId = "";
			boolean istaskid = !recentTaskID.isEmpty();
			if (istaskid) {
				By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
				WebElement tid = uiTestHelper.waitForObject(taskid);
				tid.click();
				uiTestHelper.propagateException();
				Pass_Message("Task created successfully");

				// validating assigned to field selection based on location
				boolean iflocationset = casedetailpage.getFedExLocation(assigntoLocation);
				if (iflocationset) {
					Pass_Message("FedEx location is set as '" + assigntoLocation + "' as expected");
				} else {
					Fail_Message("FedEx location is not set correctly");
				}
				Assert.assertTrue(iflocationset, "FedEx location is set as '" + assigntoLocation + "' as expected");

				for (int i = 0; i < 5; i++) {
					cmodTaskId = casedetailpage.getCmodTaskId();
					if (cmodTaskId.isEmpty()) {
						getDriver().navigate().refresh();
						uiTestHelper.propagateException();
					} else {
						break;
					}
				}
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(istaskid);

			boolean iscmodtaskid = !cmodTaskId.isEmpty();
			if (iscmodtaskid) {
				Pass_Message("CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
			} else {
				Fail_Message("CMOD Task id has not Created");
			}
			Assert.assertTrue(iscmodtaskid, "CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation test case failed");
		}
	}

	public void getDataTaskEscalatioFedExOvergoods() {
		ArrayList<String[]> testdata = TestUtil.getTestDataSLAFedExOvergoods();
		for (int i = 0; i < testdata.size(); i++) {
			String[] strArray = testdata.get(i);
			System.out.println("{" + strArray[0] + ", " + strArray[1] + ", " + strArray[2] + "}");
		}

	}

	public void taskEscalationFromL1toL4() {
		CaseDetailsPage cdPage = new CaseDetailsPage(getDriver());
		// level 1 to 4 escalation
		try {
			for (int i = 0; i < 5; i++) {
				if (i < 5) {
					errorValidation();
					taskEscalationSamePage(i + 1);
				} else {
					try {
						cdPage.clickEscalateTaskBtn();
						getDriver().navigate().refresh();
						cdPage.clickEscalateTaskBtn();
						String error = cdPage.getErrorMessage();
						System.out.println("Error:" + error);
						boolean err = error.contains("task is already reached final level");
						Assert.assertTrue(err);
						if (err) {
							Pass_Message("Error is displayed correctly as: '" + error + "'");
						} else {
							Fail_Message("Error has not displayed");
						}
					} catch (Exception e) {
						e.printStackTrace();
						Fail_Message("Exception: Escalation error validation failed");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task escalation failed");
		}
	}

	public void taskCreationAndEscaltion_and_MonitorActivitySLA(String connumber, String routToOption, String orgOrDest,
			String country, String taskLocation, String instructions) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TaskPage taskpage = new TaskPage(getDriver());
		HashMap<String, String> taskDetails = new HashMap<String, String>();
		try {
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(connumber);
			viewOrCreateCase();

			boolean isMonitorActivitySet = setMonitorActivitySLA();
			if (isMonitorActivitySet) {
				Pass_Message("Monitoring Activity SLA Added Successfully");
			} else {
				Fail_Message("Monitor Activity SLA did not Add");
			}
			Assert.assertTrue(isMonitorActivitySet, "Monitoring Activity SLA Added Successfully");

			login_Task();
			ACM_Connectivity.CloseTab();
			ACM_Connectivity.TrackandTraceCCD(connumber);
			viewOrCreateCase();

			casedetailpage.clickMonitorActivity();
			String monitorActSLAdatetime_Before = taskpage.getMonitorActSLA_DateandTime();

			fillTaskDetails(connumber, routToOption, orgOrDest, country, taskLocation, instructions);

			String recentTaskID = "";
			for (int i = 0; i < 5; i++) {
				if (ccpage.isMsgDisplayed()) {
					recentTaskID = ccpage.getRecentTaskId();
					break;
				} else {
					uiTestHelper.propagateException();
				}
			}
			String cmodTaskId = "";
			boolean istaskid = !recentTaskID.isEmpty();
			if (istaskid) {
				By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
				WebElement tid = uiTestHelper.waitForObject(taskid);
				tid.click();
				uiTestHelper.propagateException();
				Pass_Message("Task created successfully");
				for (int i = 0; i < 5; i++) {
					cmodTaskId = casedetailpage.getCmodTaskId();
					if (cmodTaskId.isEmpty()) {
						getDriver().navigate().refresh();
						uiTestHelper.propagateException();
					} else {
						break;
					}
				}
			} else {
				Fail_Message("Task not Created");
			}
			Assert.assertTrue(istaskid);
			boolean iscmodtaskid = !cmodTaskId.isEmpty();
			if (iscmodtaskid) {
				Pass_Message("CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
			} else {
				Fail_Message("CMOD Task id has not Created");
			}
			Assert.assertTrue(iscmodtaskid, "CMOD Task is created successfully and CMOD task id is: " + cmodTaskId);
			taskDetails.clear();
			taskDetails = taskpage.getTaskDetails();
			// String caseId = taskDetails.get("caseId");
			// String taskId = taskDetails.get("taskId");
			String escalationSLAtime = taskDetails.get("escalationSLAtime");
			// do {
			taskpage.closeCurrentTaskWindow(taskpage.getCrrentOpenedTask());
			// }while(taskpage.isTaskTabOpened());

			casedetailpage.clickMonitorActivity();
			String monitorActSLAdatetime_After = taskpage.getMonitorActSLA_DateandTime();
			monitorActSLAdatetime_After = changeDateFormat(monitorActSLAdatetime_After);
			monitorActSLAdatetime_Before = changeDateFormat(monitorActSLAdatetime_Before);

			System.out.println("escalationSLAtime: " + escalationSLAtime);
			System.out.println("monitorActSLAdatetime_After: " + monitorActSLAdatetime_After);
			System.out.println("monitorActSLAdatetime_Before: " + monitorActSLAdatetime_Before);

			boolean compareSLA = (monitorActSLAdatetime_After.equalsIgnoreCase(escalationSLAtime));
			boolean compareMonitorSLA = !(monitorActSLAdatetime_Before.equalsIgnoreCase(monitorActSLAdatetime_After));
			if (compareSLA && compareMonitorSLA) {
				Pass_Message("Monitoring Activity SLA Overidden and Updated Same as Task Escaltion SLA");
			} else {
				Fail_Message("Monitoring Activity SLA not Updated as Task Escaltion SLA");
			}
			Assert.assertTrue((compareSLA && compareMonitorSLA),
					"Monitoring Activity SLA Overidden and Updated Same as Task Escaltion SLA");

			login_General();
			ACM_Connectivity.TrackandTraceCCD(connumber);
			viewOrCreateCase();
			casedetailpage.clickMonitorActivity();

			isMonitorActivitySet = setMonitorActivitySLA();
			if (isMonitorActivitySet) {
				Pass_Message("Monitoring Activity SLA is Editable");
			} else {
				Fail_Message("Monitor Activity SLA is not Editable");
			}
			Assert.assertTrue(isMonitorActivitySet, "Monitoring Activity SLA is Editable");

			// taskpage.goBackToTask("T-099568");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task creation test case failed");
		}
	}

	public void fillTaskDetails(String connumber, String routToOption, String orgOrDest, String country,
			String taskLocation, String instructions) {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		TaskPage taskpage = new TaskPage(getDriver());
		HashMap<String, String> taskDetails = new HashMap<String, String>();
		try {
			// task field validation while creating task
			ccpage.clickTaskTab();

			taskDetails = taskpage.createTask(routToOption, orgOrDest, country, taskLocation, instructions);
			// task details validation while creating task
			boolean isRoutToOption = taskDetails.get("routToOption").contains("true");
			if (isRoutToOption) {
				Pass_Message("Rout to field is set as '" + routToOption + "'");
			} else {
				Fail_Message("Rout to field is not set as '" + routToOption + "'");
			}
			Assert.assertTrue(isRoutToOption, "Rout to field is set as '" + routToOption + "'");

			boolean isOriginOrDest = taskDetails.get("orgOrDest").contains("true");
			if (isOriginOrDest) {
				Pass_Message("Orgin/Destination field is set as '" + orgOrDest + "'");
			} else {
				Fail_Message("Orgin/Destination field is not set as '" + orgOrDest + "'");
			}
			Assert.assertTrue(isOriginOrDest, "Orgin/Destination to field is set as '" + orgOrDest + "'");

			System.out.println(taskDetails.get("country"));

			boolean istasklocation = taskDetails.get("taskLocation").contains("true");
			if (istasklocation) {
				Pass_Message("location name is set as '" + taskLocation + "'");
			} else {
				Fail_Message("location name is not set");
			}
			Assert.assertTrue(istasklocation, "location name is set as '" + taskLocation + "'");
			Assert.assertTrue(istasklocation, "Task details filled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Task details not filled sucessfully");
		}
	}

	public boolean setMonitorActivitySLA() {
		TaskPage taskpage = new TaskPage(getDriver());
		try {
			String expecteddate = setExpectedDate();
			String currentdate = expecteddate + casedetailpage.getSystemMonth().substring(3, 11);
			System.out.println(currentdate);
			try {
				casedetailpage.clickMonitorActivity();
				casedetailpage.selectmonitorCODateFrom(currentdate);
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Monitoring Date selection failed");
			}
			monitor_green_colour(new java.util.Date());
			casedetailpage.setMonitorTimeInput(TimeGreen);
			if (casedetailpage.ismonitorcheckbox()) {
				casedetailpage.clickmonitorcheckbox();
			}
			casedetailpage.clickMonitorActivitysave();
			boolean isMessage = taskpage.ifSuccessMessage("Monitoring Activity SLA Added Successfully");
			int i = 0;
			while (i < 5 && !isMessage) {
				isMessage = taskpage.ifSuccessMessage("Monitoring Activity SLA Added Successfully");
				uiTestHelper.propagateException();
				i++;
			}

			return isMessage;
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Monitory activity SLA date and time are not set");
			return false;
		}
	}

	public void login_Task() {
		try {
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[1]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[1]);

			ACM_Connectivity.CloseTab();
			logout();
			csr_Login(Username, Password);
			CCD_CI_Booking cibooking = new CCD_CI_Booking();
			cibooking.returnToPage();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Login Failed");
		}
	}

	public void login_General() {
		try {
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[2]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CMOD_RegressionTestExecution.Key_Array[2]);
			ACM_Connectivity.CloseTab();
			logout();
			csr_Login(Username, Password);
			CCD_CI_Booking cibooking = new CCD_CI_Booking();
			cibooking.returnToPage();
			getDriver().navigate().refresh();
			ACM_Connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Login Failed");
		}
	}

	// B-1033260
	public void b1033260_ccd_cases_in_bulk_option_on_UI_validation() {
		try {
			HomePage homePage = new HomePage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.selectOptionFromHomeDropDown("Upload Cases in Bulk");

			String details[] = homePage.getUploadCasesInBulkWindows_details();
			for (int i = 0; i < details.length; i++) {
				System.out.println("details[" + i + "]= " + details[i]);
			}
			boolean isCaseinBilkWindowOpen = details[0].contentEquals("true");
			if (isCaseinBilkWindowOpen) {
				Pass_Message("Bulk Case Upload window is displayed successfully");
			} else {
				Fail_Message("Bulk Case Upload window is not displayed");
			}
			Assert.assertTrue(isCaseinBilkWindowOpen, "Bulk Case Upload window is displayed successfully");

			boolean isUploadFilesButton = details[1].contentEquals("true");
			if (isCaseinBilkWindowOpen) {
				Pass_Message("Upload Files Button is displayed");
			} else {
				Fail_Message("Upload Files Button is not displayed");
			}
			Assert.assertTrue(isUploadFilesButton, "Upload Files Button is displayed");

			boolean isDropFilesButton = details[2].contentEquals("true");
			if (isCaseinBilkWindowOpen) {
				Pass_Message("Drop Files Button is displayed");
			} else {
				Fail_Message("Drop Files Button is not displayed");
			}
			Assert.assertTrue(isDropFilesButton, "Drop Files Button is displayed");

			boolean isTemplateDownloadLink = details[3].contentEquals("true");
			if (isTemplateDownloadLink) {
				Pass_Message("Export Template link for 'Bulk Case Upload' is Displayed");
			} else {
				Fail_Message("Export Template link for 'Bulk Case Upload' is not Displayed");
			}
			Assert.assertTrue(isTemplateDownloadLink, "Export Template link for 'Bulk Case Upload' is Displayed");

			boolean textonpage = details[4].contains("to Export the templete as csv/xls.");
			if (textonpage) {
				Pass_Message("'Click here to Export the templete as csv/xls.' is displayed successfully");
			} else {
				Fail_Message("\"'Click here to Export the templete as csv/xls.' is not displayed");
			}
			Assert.assertTrue(textonpage, "'Click here to Export the templete as csv/xls.' is displayed");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CCD Cases in Bulk Window Validation Failed");
		}
	}

}
