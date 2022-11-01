package com.tnt.ccd;

import static org.junit.Assert.assertTrue;

//import oracle.net.aso.a;
//import oracle.net.jdbc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import oracle.net.aso.a;
//import oracle.net.jdbc.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.RatingInvoicePage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.ccdobjects.TransitTimePage;
import com.tnt.cmod.CMOD_FunctionalFlow_Updated;
import com.tnt.cmod.CMOD_ReactiveCaseFlow;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;

//import salesforce.List;
//import salesforce.WebElement;

public class CCD_Connectivity extends Driver {

	TrackAndTracePage ttp_obj;
	ConsignmentPage cp_obj;
	HomePage hp_obj;
	UiTestHelper uiTestHelper = new UiTestHelper();
	By componentError = By.xpath("//div[@class='msgHeader']//span[text()='A Component Error has occurred!']");
	By bookingFailiure = By.xpath("//div[@class='forceVisualMessageQueue']//div[contains(text(),'Error')]");
	By imadeError = By.xpath("//div[contains(text(),'ERROR')]/following::span[contains(text(),'IMADE_')]");
	By closeWindow = By.xpath("//div[@class='forceVisualMessageQueue']//button[@title='Close']");
	By generalEnquiryScreen=By.xpath("//h2[text()='GENERAL ENQUIRY']");
	By closeGEWindow=By.xpath("//button[@title='close']");
	By newSearchBtn=By.xpath("//div[2]/div/lightning-button[2]/button");
	
	public CCD_Connectivity() {
		ttp_obj = new TrackAndTracePage(getDriver());
		cp_obj = new ConsignmentPage(getDriver());
		hp_obj = new HomePage(getDriver());
	}

	String SenCou, SenComp, SenAcct, RecAcct, RecCou, RecComp, SenPostCode, RecPostCode, SenTown, RecTown;
	public String Retrive;

	public void Login_To_Salesforce(){
		try {
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[4]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[4]);
			Driver.getDriver().get(URL);
			// Driver.getDriver().manage().window().maximize();
			LoginPage loginpage = new LoginPage(getDriver());
			HomePage homepage = new HomePage(getDriver());
			loginpage.setUsername(Username);
			loginpage.setPassword(Password);
			loginpage.clickLoginBtn();
			boolean verifydropDownMenu = homepage.verifydropDownMenu();
			if (verifydropDownMenu == true) {
				Pass_Message("Logged in to salesforce Successfully.");

			} else {
				Fail_Message("Login to salesforce Failed.");
			}
		} catch (Exception e) {

			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");

		}

	}

	public void CloseTab() {
		Actions action = new Actions(getDriver());
		UiTestHelper uiTestHelper = new UiTestHelper();
		try {
			List<WebElement> si = Driver.getDriver().findElements(By.xpath(
					"//div[@class='navexWorkspaceManager']//button[@type='button' and contains(@title,'Close')]"));
			System.out.println("Tab Size:" + si.size());
			int count = si.size();
			if (count == 0) {
				System.out.println("We dont have any Opened tabs to Close");
			} else {
				while (count > 0) {
					WebElement close = Driver.getDriver().findElement(By.xpath(
							"//div[@class='navexWorkspaceManager']//button[@type='button' and contains(@title,'Close')]"));
					action.moveToElement(close).click(close).build().perform();
					uiTestHelper.propagateException();
					count--;
				}
			}
		} catch (Exception e) {

		}
	}

	// Method to close tab
	public int cmod_CloseTab(){

		Boolean flag = false;
		BookingPage bkpage = new BookingPage(getDriver());
		Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Driver.getDriver().switchTo().defaultContent();
		List<WebElement> si = Driver.getDriver().findElements(By.xpath("(//button[contains(@title,'Close')])"));

		int count = (si.size());

		while (count > 1) {
			try {
				WebElement close = si.get(count - 1);
				close.click();
				waitForObjectToDisappear(close);
			} catch (ElementNotInteractableException e) {
				// new unallocated window will not close / coded in an additional try catch to
				// handle this behaviour

				By cancelButton = By
						.xpath("//footer[contains(@class,'footer')]//div//button[contains(text(),'Cancel')]");

				if (Driver.getDriver().findElements(cancelButton).size() > 0) {
					Driver.getDriver().findElement(cancelButton).click();
					count = cmod_CloseTab();
				}
			}
			count--;
		}
		return count;
	}

	public void casetable() {
		try {

			Actions ob = new Actions(getDriver());
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement recentlyViewed = Driver.getDriver().findElement(By.xpath(
					"//span[text()='Recently Viewed' and @class='triggerLinkText selectedListView uiOutputText']"));
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", recentlyViewed);

			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<WebElement> myOpenCases = Driver.getDriver().findElements(By.xpath("//input[@role='combobox']"));

			ob.click(myOpenCases.get(1)).perform();
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			Driver.getDriver().findElement(By.xpath("//input[@role='combobox']")).sendKeys("1 - My Open Cases");

			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[contains(text(),'1 - My Open Cases')]")));
			WebElement opc = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'1 - My Open Cases')]"));
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			ob.click(opc).perform();

			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// Track and Trace Search
	public String TrackandTraceCCDSearch(){
		// CloseTab();
		try {
			HomePage homePage = new HomePage(getDriver());
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			System.out.println("Consignment Number: " + Retrive);
			homePage.clickDropDownNavigationMenu();
			homePage.clickTrackAndTraceCCD();
			trackandtracePage.setConsignmentNumber(Retrive);
			trackandtracePage.clickTrackandTraceSearch();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Track and Trace CCD search service Failed");
		}
		return Retrive;
	}

	// View Case button
	public void CCD_ViewCase(){
		// CloseTab();
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			HomePage hp = new HomePage(getDriver());
			consignmentPage.clickviewcasebtn();
			uiTestHelper.propagateException();
			consignmentPage.clickViewCaseIcon();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			Pass_Message("Case is viewed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("View Case button Failed");
		}
	}

	public void deliveryAreaInfoService_in_Consignment_Details(){
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CloseTab();
			// Verification for Delivery area page is not present
			/*-->324679891*/
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[6]);
			TrackandTraceCCD(Retrive);
			Assert.assertEquals(consignmentPage.verifyDeliveryAreaButton(), true, "Delivery area button not Displayed");
			if (consignmentPage.verifyDeliveryAreaButton()) {
				cp_obj.clkBtnDeliveryArea();
			}
			String originZonecode = ttp_obj.getDeliveryAreaOriginZone();
			Pass_Message("Origin zoncode is  " + originZonecode);
			String originZoneDescription = ttp_obj.getDeliveryAreaOriginZoneDesc();
			Pass_Message("Origin zone Description is  " + originZoneDescription);
			String destZonecode = ttp_obj.getDeliveryAreaDestinationZone();
			Pass_Message("Destination zoncode is  " + destZonecode);
			String destZoneDescription = ttp_obj.getDeliveryAreaDestinationZoneDesc();
			Pass_Message("Destination zon is  " + destZoneDescription);
			Pass_Message("Delivery area information verified.");
			ttp_obj.clickDeliveryAreaInfo();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Delivery area information verification failed");
			Driver.getDriver().findElement(By.xpath("//button[@name='closedeliveryarea']")).click();
		}

	}

	public void trackandTrace_PartialConsignmentSearch(){
		try {
			String PartialCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[6]);
			System.out.println(PartialCon);
			TrackandTraceDrpDown();
			String Mystring = new String(PartialCon);
			System.out.println("substring(0,6):" + Mystring.substring(0, 6));
			String Partial = Mystring.substring(0, 6);
			ttp_obj.ipCosgnNum(Partial);
			ttp_obj.clickTrackandTraceSearch();
			verifyIMADEError();
			Assert.assertEquals(ttp_obj.verifyConsignmentTable(), true);
			Pass_Message("Consignment Search Table displayed for the Consignemnt : " + PartialCon);
			String table = "//div[@class='dt-outer-container']//table//";
			WebElement myTable = Driver.getDriver().findElement(ttp_obj.cognTbl());
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			System.out.println(row_count);
			Pass_Message("List search result displayed");
			boolean flag = false;
			int navigate = 0;
			for (int i = 1; i <= row_count; i++) {
				String a = getDriver().findElement(By.xpath(table + "tr[" + i + "]/td[1]//span/div/lightning-base-formatted-text"))
						.getText();
				System.out.println("In for " + a);
				if (navigate >= 14) {
					JavascriptExecutor jse1 = (JavascriptExecutor) getDriver();
					jse1.executeScript("window.scrollBy(0,300)");
					// Driver.getDriver().findElement(By.xpath("//input[@value='Next']")).click();
				}

				if (a.equals(PartialCon.trim())) {
					Driver.getDriver().findElement(By.xpath(table + "tr[" + i + "]/th//div[1]//button/lightning-primitive-icon"))
							.click();
					Pass_Message("Dropdown in Track and Trace search list is displayed");
					ttp_obj.clkDropDownDetails();
					flag = true;
					break;
				} else {
					navigate++;
				}

			}
			Assert.assertEquals(flag, true, "Consignment Number Displayed on the Result table");
		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment not found in list search");
		}

	}

	public void consignment_RatingAndInvoicing_from_CasePage(){
		try {
			String InvCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[2]);
			System.out.println(InvCon);
			RatingInvoicePage ripage = new RatingInvoicePage(getDriver());
			HomePage homepage = new HomePage(getDriver());
			CustomerAccountPage capage = new CustomerAccountPage(getDriver());
			CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
			TrackandTraceDrpDown();
			/*
			 * ttp_obj.ipCosgnNum(InvCon); ttp_obj.clickTrackandTraceSearch();
			 */
			homepage.globalSearchClick();
			homepage.setTextInGlobalSearch(InvCon);
			capage.clickCase();
			Assert.assertEquals(casedetailpage.verifyInvoiceRating(), true, "Invoice Rating Page Not Displayed");
			casedetailpage.clickInvoiceRating();
			ripage.switchToFrameIR();
			String Sequence = ripage.getInvoiceSequance();
			Assert.assertNotNull(Sequence, "Invoice Sequent not Displayed");
			if (!Sequence.isEmpty()) {
				Pass_Message("Invoice Sequence is  " + Sequence);
			} else {
				Fail_Message("Invoice Sequence is not displayed");
			}
			String Number = ripage.getInvoiceNumber();
			Assert.assertNotNull(Number, "Invoice Number not Displayed");
			if (!Number.isEmpty()) {
				Pass_Message("Invoice Number is  " + Number);
			} else {
				Fail_Message("Invoice Number is not displayed");
			}
			/*
			 * WebElement RatingTable = Driver.getDriver().findElement(By.xpath(
			 * "//*[contains(@id,'brandBand')]/div/c-trackntrace-container-comp/c-consignment-detail-component/c-invoice-rating-detail-component/div/lightning-card[2]/article"
			 * )); List<WebElement> RatingRow = RatingTable.findElements(By.tagName("tr"));
			 * int Rrow_count = RatingRow.size();
			 */
			String RatingDetails = ripage.getRatingDetails();
			Assert.assertNotNull(RatingDetails, "RatingDetails not Displayed");
			if (RatingDetails.isEmpty()) {
				Fail_Message("Rating details not visible");
			} else {
				Pass_Message("Rating details displayed, value is  " + RatingDetails);
			}
			String RateCard = ripage.getRatingCard();
			Assert.assertNotNull(RateCard, "RatingDetails - RateCard not Displayed");
			if (RateCard.isEmpty()) {
				Fail_Message("Rating details not visible");
			} else {
				Pass_Message("Rating details displayed, value is  " + RateCard);
			}

			WebElement InvoiceCostTable = ripage.getInvoiceCostTable();
			List<WebElement> InvoiceCostRow = InvoiceCostTable.findElements(By.tagName("tr"));
			int Costrow_count = InvoiceCostRow.size();
			System.out.println(Costrow_count);
			if (Costrow_count >= 1) {
				Pass_Message("InvoiceCost details verified");
			} else {
				Fail_Message("InvoiceCost details not  verified");
			}
			// ripage.clickInvoiceRatingClosebtn();
			Pass_Message("Invoice & Rating screen Validated successfully");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("RatingInvoicing service failed");
		}

	}

	public void trackandTrace_ConsignmentSearch(){
		try {

			CloseTab();
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[6]);
			TrackandTraceCCD(Retrive);
			String ConNum = cp_obj.getConsignmentNo();

			if (ConNum.indexOf(":") > 0) {
				ConNum = ConNum.substring(ConNum.indexOf(":") + 1).trim();
			}
			Pass_Message("Consignment number" + ConNum + " is displayed at the top right corner");

			System.out.println("Con num is " + ConNum);
			System.out.println("Con num is retrive " + Retrive);
			Assert.assertEquals(ConNum, Retrive, "Reterive Search Failed");
			if (ConNum.contains(Retrive)) {
				Pass_Message("Retrive search service Passed.");

			} else {
				Fail_Message("Retrive search service failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Retrive search service Failed");
		}
	}

	// Search by 9 digit consignment no
	public void TTSearch_9DigitCon(){
		try {
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			Retrive = "324642441";
			System.out.println("Consignment Num: " + Retrive);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			ttp_obj.ipCosgnNum(Retrive);
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElement(cp_obj.txttopCongnNum()).isDisplayed()) {
				Pass_Message("9 digit consignment searched successfully");
				TrackandTraceNewSearchBtn();
			} else {
				Fail_Message("9 digit consignment has not been searched successfully");
				ttp_obj.clickerrorbtn();
			}
			Driver.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("9 digit consignment search failed");
		}

	}

	// Seach 6 digit consignment no
	public void TTSearch_6DigitsCon(){
		try {
			String PartialCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[1]);
			PartialCon = "324642441";// TODO need to update test DB
			CloseTab();
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			String Mystring = new String(PartialCon);
			System.out.println("substring(0,6):" + Mystring.substring(0, 6));
			String Partial = Mystring.substring(0, 6);
			ttp_obj.ipCosgnNum(Partial);
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully " + Partial);

			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully " + Partial);
					Pass_Message("Consignment searched successfully");
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("6 digit Consignment not found");
			// Assert.fail();
		}

	}

	// Select collection date and customer refernce
	public void TTSearch_CustReference(){
		try {
			// Retrive = Database_Connection.retrieveTestData("CUSTREF", "ACM", "KEY",
			// ACM_Functional_Flows.Key_Array[1]);
			Retrive = "12345";
			TrackandTraceDrpDown();
			// TTSearch_ColDate();
			ttp_obj.custRef(Retrive);
			Pass_Message("Customer reference number entered successfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with Customer Refernce" + Retrive);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.cognTbl());
				if (CustRef.size() > 0) {
					System.out
							.println("Consigment searched successfully with Customer Reference number as :" + Retrive);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched successfully with given criteria");
		}

	}

	// Select delivery date and alternate consginment number
	public void TTSearch_AltConNumDelDate(){
		try {
			Retrive = Database_Connection.retrieveTestData("ALTCONNUM", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			Retrive = "22";// TODO need to update test data DB
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.entraltcongnnum(Retrive);
			Pass_Message("Alternate Consignment number entered successfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message(
						"Consignment cannot be searched successfully with alternate consignment number: " + Retrive);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out
							.println("Consigment searched successfully with alternate consginment number :" + Retrive);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched successfully");
		}

	}

	// Select Delivery date and customer reference
	public void TTSearch_CustReferenceDelDate(){
		try {
			// Retrive = Database_Connection.retrieveTestData("CUSTREF", "ACM", "KEY",
			// ACM_Functional_Flows.Key_Array[1]);
			Retrive = "12345";
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.custRef(Retrive);
			Pass_Message("Customer reference number entered successfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date and Customer Refernce"
						+ Retrive);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {

					System.out.println(
							"Consigment searched successfully with delivery date and Customer Reference number as :"
									+ Retrive);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consigment not searched with delivery date and Customer Reference number as :" + Retrive);
		}

	}

	// Select delivery date ,sender account name and country
	public void TTSearch_SendAcctAndCountryDelDate(){
		try {
			SenAcct = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.ipSenAccuNum(SenAcct);
			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				if (Driver.getDriver().findElements(ttp_obj.consgnOverview()).isEmpty()) {
					Fail_Message(
							"Consignment cannot be searched successfully with delivery date, sender account number: "
									+ SenAcct + " and Country: " + SenCou);
					ttp_obj.clickerrorbtn();
				} else {
					System.out.println(
							"Consignment cannot be searched successfully with delivery date sender account number: "
									+ SenAcct + " and Country: " + SenCou);
				}
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println(
							"Consignment cannot be searched successfully with delivery date sender account number: "
									+ SenAcct + " and Country: " + SenCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}

	}

	// Select Receiver account number and country with delivery date
	public void TTSearch_RecAcctAndCountryDelDate(){
		try {
			RecAcct = Database_Connection.retrieveTestData("REC_ACCT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.ipRecAccuNum("324635710");

			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,receiver account number: "
						+ RecAcct + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with delivery date , recevier account number:"
							+ RecAcct + " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched with delivery date , recevier account number and Country");
		}

	}

	// Sender company , country , Delivery date
	public void TTSearch_SenCompAndCountryDelDate(){
		try {
			SenComp = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// sTTSearch_DelDate();

			ttp_obj.senCompyNm(SenComp);

			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				System.out
						.println("Consignment cannot be searched successfully with delivery date ,sender Company Name: "
								+ SenComp + " and Country: " + SenCou);
				Fail_Message("Consignment has not been searched");
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
			TTSearch_ClearBtn();
		}

	}

	// Select receiver company name and Country with delivery date
	public void TTSearch_RecCompAndCountryDelDate(){
		try {
			RecComp = Database_Connection.retrieveTestData("REC_COMP", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = "Portugal"; // TODO update Test data DB
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();

			// ttp_obj.recCompyNm(RecComp);
			ttp_obj.recCompyNm(convertStringToCamelCase(RecComp));

			selRecCountry(RecCou);

			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,Receiver Company name: "
						+ RecComp + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
		}

	}

	// Select sender postal code,country and delivery date
	public void TTSearch_SenPostCodeAndCountryDelDate(){
		try {
			SenPostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.senPostCode(SenPostCode);

			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,sender postcode: "
						+ SenPostCode + " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
		}

	}

	// Select Delivery date,Receiver Postcode and Country
	public void TTSearch_RecPostCodeAndCountryDelDate(){
		try {
			RecPostCode = Database_Connection.retrieveTestData("REC_POSTCODE", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.recPostCode(RecPostCode);

			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,receiver postcode: "
						+ RecPostCode + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
		}

	}

	// select sender town and country name with delivery date
	public void TTSearch_SenTownAndCountryDelDate(){
		try {
			SenTown = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.senTownNm(SenTown);
			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,sender post code: "
						+ SenPostCode + " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with delivery date , sender post code:"
							+ SenPostCode + " and Country: " + SenCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}

	}

	// Select receiver town and country name with delivery date
	public void TTSearch_RecTownAndCountryDelDate(){
		try {
			RecTown = Database_Connection.retrieveTestData("REC_TOWN", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			// TrackandTraceDrpDown();
			// TTSearch_DelDate();
			ttp_obj.recTownNm(RecTown);
			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,receiver town: " + RecTown
						+ " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with delivery date , receiver town:" + RecTown
							+ " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}
	}

	// Select sender&receiver depot with delivery date
	public void TTSearch_SenAndRecDepotDelDate(){
		try {
			String RecDepot = Database_Connection.retrieveTestData("REC_DEPOT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			String SenDepot = Database_Connection.retrieveTestData("SEN_DEPOT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_DelDate();
			ttp_obj.senDepot(SenDepot);
			ttp_obj.recDepot(RecDepot);

			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with delivery date ,receiver town: " + RecTown
						+ " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with delivery date , receiver town:" + RecTown
							+ " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}

	}

	// Select collection date from trace&track CCD
	public void TTSearch_ColDate(){
		try {
			ttp_obj.clkColDateFrom();
			WebElement dateWidget = Driver.getDriver().findElement(ttp_obj.calTable());
			List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
			String exp_date = "2";
			for (WebElement cell : columns) {
				if (cell.getText().equals(exp_date)) {
					System.out.println("Date picked from: " + cell.getText());
					cell.findElement(ttp_obj.clkDate(exp_date)).click();
					Pass_Message("Collection From Date selected");
					break;
				}
			}
			ttp_obj.clkColDateTo();
			WebElement dateWidget1 = Driver.getDriver().findElement(ttp_obj.calTable());
			List<WebElement> columns1 = dateWidget1.findElements(By.tagName("td"));
			String exp_date1 = "5";
			for (WebElement cell : columns1) {
				if (cell.getText().equals(exp_date1)) {
					System.out.println("Date picked to: " + cell.getText());
					cell.findElement(ttp_obj.clkDate(exp_date1)).click();
					Pass_Message("Collection To Date selected");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Date cannot be searched");
		}

	}

	// Enter delivery date
	public void TTSearch_DelDate(){
		try {
			ttp_obj.clkDelDateFrom();
			// delivery date from xpath
			WebElement dateWidget = Driver.getDriver().findElement(ttp_obj.calTable());
			List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
			String exp_date = "4";
			for (WebElement cell : columns) {
				// Select 13th Date
				if (cell.getText().equals(exp_date)) {
					System.out.println("Date picked from: " + cell.getText());

					cell.findElement(ttp_obj.clkDate(exp_date)).click();
					Pass_Message("Delivery From Date is selected");
					break;
				}
			}
			ttp_obj.clkDelDateTo();
			WebElement dateWidget1 = Driver.getDriver().findElement(ttp_obj.calTable());
			List<WebElement> columns1 = dateWidget1.findElements(By.tagName("td"));
			String exp_date1 = "5";
			for (WebElement cell : columns1) {

				if (cell.getText().equals(exp_date1)) {
					System.out.println("Date picked to: " + cell.getText());
					cell.findElement(ttp_obj.clkDate(exp_date1)).click();
					Pass_Message("Delivery To Date selected");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Date cannot be searched");
		}

	}

	// Select alternate consignment number and collection date
	public void TTSearch_AltConNumColDate(){
		try {
			Retrive = Database_Connection.retrieveTestData("ALTCONNUM", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			Retrive = "22";// TODO need to update test data DB
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.entraltcongnnum(Retrive);
			Pass_Message("Alternate Consignment number entered successfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message(
						"Consignment cannot be searched successfully with consignment alternate number: " + Retrive);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println(
							"Consignment searched successfully with alternate consignment number as :" + Retrive);
					System.out.println("Total number of cosignment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched with alternate cosignment" + Retrive);

		}

	}

	// Select sender account number, country name and collection date
	public void TTSearch_SendAcctAndCountryColDate(){
		try {
			SenAcct = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.ipSenAccuNum(SenAcct);
			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,sender account number: "
						+ SenAcct + " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with collection date , sender account number:"
							+ SenAcct + " and Country: " + SenCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched with collection date,sender account number: " + SenAcct
					+ " Country: " + SenCou);
		}

	}

	// Select Receiver account Name ,country and Collection date
	public void TTSearch_RecAcctAndCountryColDate(){
		try {
			RecAcct = Database_Connection.retrieveTestData("REC_ACCT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.ipRecAccuNum(RecAcct);
			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message(
						"Consignment cannot be searched successfully with collection date ,receiver account number: "
								+ RecAcct + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out
							.println("Consigment searched successfully with collection date , recevier account number:"
									+ RecAcct + " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}
	}

	// Select collection date, sender company name and country
	public void TTSearch_SenCompAndCountryColDate(){
		try {
			SenComp = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.senCompyNm(SenComp);
			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,Sender Company Name: "
						+ SenComp + " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();

			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
			TTSearch_ClearBtn();
		}

	}

	// Select collection date,sender company post code and country
	public void TTSearch_SenPostCodeAndCountryColDate(){
		try {
			SenPostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.senPostCode(SenPostCode);

			selSenderCountry(SenCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,sender postcode: "
						+ SenPostCode + " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with collection date , sender postcode:"
							+ SenPostCode + " and Country: " + SenCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();

		}

	}

	// Select recevier post code ,contry and collection date
	public void TTSearch_RecPostCodeAndCountryColDate(){
		try {
			RecPostCode = Database_Connection.retrieveTestData("REC_POSTCODE", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.recPostCode(RecPostCode);
			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,receiver post code: "
						+ RecPostCode + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
		}

	}

	// Select sender company Town,country and collection date
	public void TTSearch_SenTownAndCountryColDate(){
		try {
			SenTown = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			SenCou = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();

			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.senTownNm(SenTown);

			selSenderCountry(SenCou);

			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,sender town: " + SenTown
						+ " and Country: " + SenCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					Pass_Message("Consignment searched successfully");
					ttp_obj.scrollBtnNewSearch();
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
			ttp_obj.clickerrorbtn();
		}

	}

	// Select collection date,recevier town and country
	public void TTSearch_RecTownAndCountryColDate(){
		try {
			RecTown = Database_Connection.retrieveTestData("REC_TOWN", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecCou = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			RecTown = "New Delhi";// TODO need to update Test DB
			RecCou = "India";// TODO need to update Test DB
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();
			ttp_obj.recTownNm(RecTown);
			selRecCountry(RecCou);
			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,receiver town: "
						+ RecTown + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with collection date , receiver town:"
							+ RecTown + " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}

	}

	// Select Collection date with sender and receiver depot
	public void TTSearch_SenAndRecDepotColDate(){
		try {
			String RecDepot = Database_Connection.retrieveTestData("REC_DEPOT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			String SenDepot = Database_Connection.retrieveTestData("SEN_DEPOT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[1]);
			TrackandTraceDrpDown();
			TTSearch_ClearBtn();
			// TTSearch_ColDate();

			ttp_obj.senDepot(SenDepot);
			ttp_obj.recDepot(RecDepot);

			Pass_Message("Details entered succesfully");
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully with collection date ,receiver town: "
						+ RecTown + " and Country: " + RecCou);
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				if (CustRef.size() > 0) {
					System.out.println("Consigment searched successfully with collection date , receiver town:"
							+ RecTown + " and Country: " + RecCou);
					System.out.println("Total number of cosigment are :" + CustRef.size());
					Pass_Message("Consignment searched successfully");
					TTSearch_NewSearchBtn();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment cannot be searched");
		}

	}

	public void TrackandTraceDrpDown(){
		try {

			Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (Driver.getDriver().findElements(ttp_obj.txtCosgnNum()).size() > 0) {
				System.out.println("Search Page is already open");
			} else {
				hp_obj.clickDropDownNavigationMenu();
				hp_obj.clickTrackAndTraceCCD();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Track and Trace Page can not be displayed");
		}

	}

	// Trace and Track new Search button
	public void TrackandTraceNewSearchBtn(){
		try {

			if (Driver.getDriver().findElement(By.xpath("//button[text()='New Search']")).isDisplayed()){

				ttp_obj.clkNewSearch();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("New search can not be done");
		}

	}

	public void TTSearch_CreateUnallocatedCase(){
		try {
			TrackandTraceDrpDown();
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			ttp_obj.ipCosgnNum("123456789");
			ttp_obj.clickTrackandTraceSearch();
			Pass_Message("Invalid consignment number error message displayed");
			Driver.getDriver().findElement(By.xpath("//div[text()='ERROR:']/following::lightning-primitive-icon[1]")).click();
			ttp_obj.clickCreateUnallocatedCaseBtn();
			// ccpage.clickCaseOwnerLoc("HG5");
			ccpage.scrolltoContactDetails();
			ccpage.setSenderContactName("Alex Martin");
			ccpage.setReceiverContactName("Tom Jerry");
			ccpage.setSenderContactPhone("98989898989");
			ccpage.setReceiverContactPhone("22222222222");
			ccpage.setSenderContactEmail("sender@test.com");
			ccpage.setReceiverContactEmail("rec@test.com");
			ccpage.scrolltoCaseInformation();
			ccpage.setCaseGroup("Account");
			ccpage.setCaseReason("Account status enquiry");
			ccpage.clickSaveUnallocatedCase();
			Pass_Message("Unallocated Case Created successfully");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Unallocated Case has not been created ");

		}
	}

	// View consignment no at top
	public void TTSearch_ViewConNumAtTop(){
		try {
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[1]);
			Retrive = "324642441";
			TrackandTraceDrpDown();
			ttp_obj.ipCosgnNum(Retrive);
			ttp_obj.clickTrackandTraceSearch();
			validateConsignmentNoDisplayOnTop(Retrive);
			/*
			 * String conNum =
			 * Driver.getDriver().findElement(cp_obj.topCosginmentNum(Retrive)).getText(); if
			 * (conNum.isEmpty()) {
			 * Fail_Message("Consignment number is not displayed at top corner");
			 * ttp_obj.clickerrorbtn(); } else { System.out.println("Consignment number " +
			 * conNum + " is displayed at the top right corner");
			 * Pass_Message("Consignment number " + conNum +
			 * " is displayed at the top right corner"); TTSearch_NewSearchBtn(); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment number is not displayed at the top right corner");
		}

	}

	// new tcs
	public void TTSearch_NewSearchBtn(){

		try {
			ttp_obj.scrollBtnNewSearch();
			ttp_obj.clkNewSearch();
			Pass_Message("New Search button is working fine");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("New Search button not working");
		}

	}

	public void TTSearch_ClearBtn(){

		try {
			ttp_obj.clkClearBtn();

			JavascriptExecutor jse2 = (JavascriptExecutor) getDriver();
			jse2.executeScript("arguments[0].scrollIntoView();", Driver.getDriver().findElement(ttp_obj.txtconsnEquiry()));
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Clear button not working");
		}

	}

	public void TTSearch_Verify_ClearAndNewSearchBtn(){
		try {
			TrackandTraceCCDSearch();

			if (Driver.getDriver().findElement(ttp_obj.btnNewSearch()).isDisplayed()) {
				new WebDriverWait(getDriver(), 10).until(ExpectedConditions.elementToBeClickable(ttp_obj.btnNewSearch()));
				Pass_Message("New Search button is working fine");
				ttp_obj.clkNewSearch();
			}
			if (Driver.getDriver().findElement(ttp_obj.btnClear()).isDisplayed()) {
				new WebDriverWait(getDriver(), 10).until(ExpectedConditions.elementToBeClickable(ttp_obj.btnClear()));
				Pass_Message("Clear button is working fine");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Clear or New Search button not working");
		}

	}

	public void TTSearch_ConDtlBtn(){
		try {
			TrackandTraceDrpDown();
			if (Driver.getDriver().findElement(ttp_obj.txtCosgnNum()).isDisplayed()) {
				TrackandTraceCCDSearch();
			}
			if (Driver.getDriver().findElement(cp_obj.overviewCosgn()).isDisplayed()) {
				System.out.println("Consginment History" + Driver.getDriver().findElement(cp_obj.viewbtnHistory()).isEnabled());
				System.out.println("Consginment Pieces" + Driver.getDriver().findElement(cp_obj.viewbtnPieces()).isEnabled());
				System.out.println("Consginment View Case" + Driver.getDriver().findElement(cp_obj.viewcase()).isEnabled());
				Pass_Message("Consignment History and Consignment Pieces button are enabled");
				TTSearch_NewSearchBtn();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment History and Consignment Pieces button are not visible");
		}
	}

	public void TTSearch_ConHistBtn(){
		try {
			TTSearch_ConHistFrmDropdown();
			if (cp_obj.verifyConsignmentDetailsBtn() == true && cp_obj.verifyConsignmentPiecesBtn() == true) {
				Pass_Message("Consignment Details and Consignment Pieces button are visible in the History");
			} else {
				Fail_Message("Consignment Details and Consignment Pieces button are not visible");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details and Consignment Pieces button are not visible");
		}
	}

	public void TTSearch_ConPiecesBtn(){
		try {
			TTSearch_ConPiecesFromDropdown();
			if (cp_obj.verifyConsignmentDetailsBtn() == true && cp_obj.verifyConsignmentHistoryBtn() == true) {
				Pass_Message("Consignment Details and Consignment Pieces button are visible in the History");
			} else {
				Fail_Message("Consignment Details and Consignment Pieces button are not visible");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details and Consignment History button are not visible");
		}
	}

	// This method might be use in future
	/*
	 * public void TTSearch_ViewCase(){ try { if
	 * (Driver.getDriver().findElement(By.xpath("//*[contains(text(),'View Case')]")).
	 * isDisplayed()) { System.out.println("View Case Button is displayed");
	 * Pass_Message("Validation of View Case Button is done"); }
	 *
	 * }
	 *
	 * catch (Exception e) { e.printStackTrace();
	 * Fail_Message("Consignment Detail button not working");
	 *
	 * }
	 *
	 * }
	 */

	// TTSearch_InvfromConDtlPage
	public void TTSearch_InvfromConDtlPage(){
		try {
			String InvCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[2]);
			InvCon = "322478715";// TODO need to add into DB
			// TrackandTraceDrpDown();
			// ttp_obj.ipCosgnNum(InvCon);
			// ttp_obj.clickTrackandTraceSearch();
			TrackandTraceConsignmentSearch(InvCon);
			RatingInvoicePage ripage = new RatingInvoicePage(getDriver());
			ttp_obj.clickInvoiceRating();
			Driver.getDriver().switchTo().frame(Driver.getDriver().findElement(By.xpath("//iframe[@title='Invoices']")));
			if (ripage.getInvoiceSummary() == true) {
				Pass_Message("Invoice Summary is displayed");
			} else {
				Fail_Message("Invoice Summary is not displayed");
			}
			ripage.clickInvoiceRatingClosebtn();
			if (ripage.getInvoiceSummary() == true) {
				Fail_Message("Close button on Invoice Summary is not working");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invoice & Rating function not working as expected ");

		}

	}

	public String TrackandTraceConsignmentSearch(String consignmentNo){
		try {
			HomePage homePage = new HomePage(getDriver());
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickTrackAndTraceCCD();
			trackandtracePage.setConsignmentNumber(consignmentNo);
			trackandtracePage.clickTrackandTraceSearch();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Track and Trace CCD search service Failed");
		}
		return Retrive;

	}

	public void TTSearch_ConDtlfrmDropdown(){
		try {
			String PartialCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[1]);
			PartialCon = "324642441";// "625214999";
			TrackandTraceDrpDown();
			String Mystring = new String(PartialCon);
			System.out.println("substring(0,6):" + Mystring.substring(0, 6));
			String Partial = Mystring.substring(0, 6);
			ttp_obj.ipCosgnNum(Partial);
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				Fail_Message("Consignment cannot be searched successfully ");
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				System.out.println("Total consignment :" + CustRef.size());
				ttp_obj.lstbtnConsgn();
				Pass_Message("Details is displayed in dropdown");
				ttp_obj.clkDropDownDetails();
				Pass_Message("Consignment Details screen is displayed");

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details is not present in dropdown");
		}
	}

	public void TTSearch_ConHistDtlsPage_VerifySections(){
		try {
			TTSearch_ConHistFrmDropdown();
			if (Driver.getDriver().findElement(cp_obj.statusCosgnHistory()).isDisplayed()
					&& Driver.getDriver().findElement(cp_obj.statusHistoryCosgnHistory()).isDisplayed()) {
				System.out.println("Consigment History for status and status History is enable");
			}
			if (Driver.getDriver().findElement(cp_obj.srcCosgnHistory()).isDisplayed()
					&& Driver.getDriver().findElement(cp_obj.rmkNotesCosgnHistory()).isDisplayed()) {
				System.out.println("Consigment History for Source tab and remark/Notes is enable");
			}
			TTSearch_NewSearchBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details and Consignment Pieces button are not visible");
		}
	}

	public void TTSearch_ConPiecesFromDropdown(){
		try {
			String PartialCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[1]);
			PartialCon = "324642441";// TODO need to update test DB
			TrackandTraceDrpDown();
			String Mystring = new String(PartialCon);
			System.out.println("substring(0,6):" + Mystring.substring(0, 6));
			String Partial = Mystring.substring(0, 6);
			ttp_obj.ipCosgnNum(Partial);
			ttp_obj.clickTrackandTraceSearch();
			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				System.out.println("Consignment cannot be searched successfully ");
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				System.out.println("Total consignment :" + CustRef.size());
				// For tempory using only first consignment number

				ttp_obj.lstbtnConsgn();
				ttp_obj.clkDropDownPieces();
				Pass_Message("Pieces is displayed in dropdown");
				if (Driver.getDriver().findElement(cp_obj.piecesNoConsgn()).isDisplayed()
						&& Driver.getDriver().findElement(cp_obj.pieceStatusCosgn()).isDisplayed())
					Pass_Message("Consigment Piece No and Piece Status is disable");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details and Consignment History button are not visible");
		}
	}

	public void TTSearch_ConDtlsPage_VerifySections(){

		try {
			TrackandTraceCCDSearch();
			cp_obj.clickOverviewTab();
			if (cp_obj.verifyCustomerReference() == true) {
				Pass_Message("Consignment Overview section is displayed");
			} else {
				Fail_Message("Consignmnet Overview section is not displayed");
			}
			cp_obj.clickSenderReceiverAddressTab();
			if (cp_obj.verifySenderAddress() == true) {
				Pass_Message("Sender/Receiver Address section is displayed");
			} else {
				Fail_Message("Sender/Receiver Address section is not displayed");
			}
			cp_obj.clickCollectionDeliveryAddressTab();
			if (cp_obj.verifyCollectionAddress() == true) {
				Pass_Message("COLLECTION/DELIVERY ADDRESS section is displayed");
			} else {
				Fail_Message("COLLECTION/DELIVERY ADDRESS section is not displayed");
			}
			cp_obj.clickGoodsWeightandDimTab();
			if (cp_obj.verifyGoodsType() == true) {
				Pass_Message("GOODS,WEIGHTS AND DIMENSIONS section is displayed");
			} else {
				Fail_Message("GOODS,WEIGHTS AND DIMENSIONS section is not displayed");
			}
			cp_obj.clickCustomsControlledTab();
			if (cp_obj.verifyCertificateOrigin() == true) {
				Pass_Message("CUSTOMS CONTROLLED section is displayed");
			} else {
				Fail_Message("CUSTOMS CONTROLLED section is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("CONSIGNMENT OVERVIEW sections are not displayed correctly");
		}
	}

	public void TTSearch_ConHistFrmDropdown(){
		try {
			String PartialCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[1]);
			PartialCon = "324642441";
			TrackandTraceDrpDown();
			String Mystring = new String(PartialCon);
			System.out.println("substring(0,6):" + Mystring.substring(0, 6));
			String Partial = Mystring.substring(0, 6);
			System.out.println(Partial);
			ttp_obj.ipCosgnNum(Partial);
			ttp_obj.clickTrackandTraceSearch();

			if (Driver.getDriver().findElements(ttp_obj.cognTbl()).isEmpty()) {
				System.out.println("Consignment cannot be searched successfully ");
				ttp_obj.clickerrorbtn();
			} else {
				List<WebElement> CustRef = Driver.getDriver().findElements(ttp_obj.listCognTbl());
				System.out.println("Total consignment :" + CustRef.size());
				ttp_obj.lstbtnConsgn();
				ttp_obj.clkDropDownHistory();
				Pass_Message("History is displayed in dropdown");
				if (Driver.getDriver().findElement(cp_obj.dateTimeCosgnHistory()).isDisplayed()
						&& Driver.getDriver().findElement(cp_obj.locatnCosgnHistory()).isDisplayed())
					Pass_Message("Consigment History DateTime and Location is disable");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Detail could not be verfied");

		}

	}

	public void TTSearch_ConHist_StatusCodeDtls(){
		try {

			TTSearch_ConHistFrmDropdown();
			ttp_obj.clickShowMoreIcon();

			String AddInfo = ttp_obj.getStatus();
			if (AddInfo.isEmpty()) {
				Fail_Message("Additional Information of Status code is not displayed");
			} else {
				Pass_Message("Additional Information of Status code is displayed as" + AddInfo);
			}

			String userId = ttp_obj.getUserid();
			if (userId.isEmpty()) {
				Fail_Message("User Id not diaplyed");
			} else {
				Pass_Message("User Id displayed correctly as " + userId);
			}
			String depot = ttp_obj.getStatusDepot();
			if (depot.isEmpty()) {
				Fail_Message("Status depot not diaplyed");
			} else {
				Pass_Message("Status depot displayed correctly as " + depot);
			}
			String Send_date_time = ttp_obj.getSendDateTime();
			if (Send_date_time.isEmpty()) {
				Fail_Message("Send_date_time not diaplyed");
			} else {
				Pass_Message("Send_date_time displayed correctly as " + Send_date_time);
			}

			String Rec_date_time = ttp_obj.getReceivedDateTime();
			if (Rec_date_time.isEmpty()) {
				Fail_Message("Rec_date_time not diaplyed");
			} else {
				Pass_Message("Rec_date_time displayed correctly as " + Rec_date_time);
			}
			String Round_No = ttp_obj.getRoundNo();
			if (Round_No.isEmpty()) {
				Fail_Message("Round_No not diaplyed");
			} else {
				Pass_Message("Round_No displayed correctly as " + Round_No);
			}
			String Runsheet_No = ttp_obj.getRunSheetNo();
			if (Runsheet_No.isEmpty()) {
				Fail_Message("Runsheet_No not diaplyed");
			} else {
				Pass_Message("Runsheet_No displayed correctly as " + Runsheet_No);
			}
			// Driver.getDriver().findElement(By.xpath("//*[contains(@id,'brandBand')]/div/c-trackntrace-container-comp/c-consignment-history-component/lightning-card/article/div/slot/div[3]/section/div/footer/button")).click();
			ttp_obj.additionalInfoCloseBtnClick();
			Pass_Message("Additional Information window is closed");
		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Additional Information of Status code is not displayed");

		}

	}

	public void TTSearch_SearchListColSeq(){
		try {
			// TrackandTraceNewSearchBtn();
			TTSearch_ClearBtn();
			TTSearch_CustReference();
			List<String> expectedResult = new ArrayList<String>();
			expectedResult.add("Next");
			expectedResult.add("Consignment Number");
			expectedResult.add("Customer Reference");
			expectedResult.add("Collected Date");
			expectedResult.add("Sender Account Name");
			expectedResult.add("Sender Town");
			expectedResult.add("Sender Country/Territory");
			expectedResult.add("Receiver Account Name");
			expectedResult.add("Receiver Town");
			expectedResult.add("Receiver Country/Territory");
			expectedResult.add("Line of Business");
			expectedResult.add("POD");

			List<String> actualResult = new ArrayList<String>();
			List<WebElement> table = Driver.getDriver().findElements(cp_obj.listConsignment());
			for (int j = 0; j < table.size(); j++) {
				actualResult.add(table.get(j).getText());
				if (expectedResult.equals(actualResult)) {
					Pass_Message(
							"Sequence of Columns correctly displayed as: Next, Consignment Active, Customer, Collected, Sender Account Name, Sender Town, Sender Country/Territory, Receiver Account Name, Receiver Town, Receiver Country/Territory, Line of Business, POD");
				} else {
					Fail_Message("Sequnce of Colums arenot correct or not present");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Search list is not displayed");

		}

	}

	public void TTSearch_CreateCaseErrorforPE(){
		try {
			// String PECon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
			// ACM.Key_Array[1]);
			String PECon = "325635861";
			ttp_obj.ipCosgnNum(PECon);
			Pass_Message("PE Consignment entered successfully");
			ttp_obj.clickTrackandTraceSearch();
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			cp_obj.clickcreatecasebtn();
			ccpage.clickCCLocation();
			ccpage.clickOrigin();
			Driver.getDriver().findElement(By.xpath("//footer[@class='slds-modal__footer']/button[1]")).click();
			if (Driver.getDriver().findElement(By.xpath(
					"//span[contains(text(),'Please be aware that there is already a  Exception created for this consignment')]"))
					.isDisplayed()) {

				Pass_Message(
						"Error message for already an Exception created for this consignment is displayed succesfully");
			} else {
				Fail_Message("Error scenario for case creation for existing PE is failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Scenario for Case creation error for existing PE is not working as expecetd");
		}

	}

	public void TTSearch_Verify_LateststatusCodeatTop(){
		try {
			TTSearch_ConHistFrmDropdown();
			String latestCode = Driver.getDriver().findElement(By.xpath("//table/tbody/tr[1]/td[2]//lightning-base-formatted-text"))
					.getText();
			if (latestCode.isEmpty()) {
				Fail_Message("Latest Code is not displayed at the top");
			}
			Pass_Message("Latest Code is displayed in the top row" + latestCode);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exeception: Latest status code is not displayed in the top");
		}

	}

	public void proactiveException_Service(){
		try {
			String peCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[4]);
			// System.out.println(peCon);
			hp_obj.globalSearchClick();
			hp_obj.setTextInGlobalSearch(peCon);
			CustomerAccountPage capage = new CustomerAccountPage(getDriver());
			boolean pe = capage.verifyProactiveExceptions();
			Assert.assertEquals(pe, true);
			{
				Pass_Message("Proactive Exceptions are displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Proactive Exceptions are not displayed");
		}
	}

	public void consignment_TransitTime_from_CasePage(){
		try {

			CloseTab();
			String TransitCon = Database_Connection.retrieveTestData("CONSIGNMENTNO", "ACM", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[6]);

			CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
			TransitTimePage ttpage = new TransitTimePage(getDriver());
			CCD_Connectivity connectivity = new CCD_Connectivity();
			CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
			ConsignmentPage conpage = new ConsignmentPage(getDriver());
			TrackandTraceCCD(TransitCon);
			try {
				if (conpage.verifyViewCase() == true) {
					connectivity.CCD_ViewCase();
				}

			} catch (Exception e) {
				ffUpdated.CreateCase_EBS();

			}
			uiTestHelper.propagateException();
			casedetailpage.clickTransitTime();
			try {
				ttpage.switchToFrameTT();
				String TransitPage = ttpage.getTransitTimeTitle();
				Assert.assertTrue(TransitPage.contains("Transit Timeline"), "Transit TimeLine Page Displayed");
				if (TransitPage.contains("Transit Timeline")) {
					Pass_Message("Transit Time Page is displayed");
				}

			} catch (Exception e) {
				ttpage.switchToFrameTT();
				Assert.assertTrue(ttpage.verifyMultiServiceLoc(), "Multiple Service Loc Displayed");
				if (ttpage.verifyMultiServiceLoc() == true) {
					ttpage.clickOrigin();
					ttpage.clickDestination();
					Pass_Message("Origin and Destination selected on transit time screen");
					ttpage.viewTransitTimeline();
					boolean TransitPageOrgDest = ttpage.getTransitTimeMultiService();
					Assert.assertTrue(TransitPageOrgDest, "Transit Time Page is not Displayed");
					if (TransitPageOrgDest == true) {
						Pass_Message("Transit Time Page is displayed");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Transit Time service failed");
			CloseTab();
		}

	}

	// Select country from sender information
	public void selSenderCountry(String country) {

		String[] strArray = country.split(" ");
		StringBuilder SenCou = new StringBuilder();
		for (String ctrStr : strArray) {
			SenCou.append(ctrStr.substring(0, 1).toUpperCase() + ctrStr.substring(1).toLowerCase()).append(" ");
		}
		SenCou.deleteCharAt(SenCou.lastIndexOf(" ")).toString();
		ttp_obj.sltSenContry(SenCou);
		// ttp_obj.sltoptnCountrynm(SenCou);
	}

	// Select country from receiver information
	public void selRecCountry(String country) {
		String[] strArray = country.split(" ");
		StringBuilder recCou = new StringBuilder();
		for (String ctrStr : strArray) {
			recCou.append(ctrStr.substring(0, 1).toUpperCase() + ctrStr.substring(1).toLowerCase()).append(" ");
		}
		recCou.deleteCharAt(recCou.lastIndexOf(" ")).toString();
		ttp_obj.sltRecContry(recCou);
		ttp_obj.sltoptnCountrynm(recCou);
	}

	public String convertStringToCamelCase(String convertStr) {
		String[] strArray = convertStr.split(" ");
		StringBuilder recCou = new StringBuilder();
		for (String ctrStr : strArray) {
			recCou.append(ctrStr.substring(0, 1).toUpperCase() + ctrStr.substring(1).toLowerCase()).append(" ");
		}
		return recCou.deleteCharAt(recCou.lastIndexOf(" ")).toString();
	}

	public void validateConsignmentNoDisplayOnTop(String consignmentNo){
		// String conNum =
		// Driver.getDriver().findElement(cp_obj.topCosginmentNum(consignmentNo)).getText();
		String conNum = cp_obj.getTopCosginmentNumber(consignmentNo);
		if (conNum.isEmpty()) {
			Fail_Message("Consignment number is not displayed at top corner");
			ttp_obj.clickerrorbtn();
		} else {
			System.out.println("Consignment number " + conNum + " is displayed at the top right corner");
			Pass_Message("Consignment number " + conNum + " is displayed at the top right corner");
			TTSearch_NewSearchBtn();
		}
	}

	public String TrackandTraceCCD(String Retrive){
		try {
			HomePage homePage = new HomePage(getDriver());
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			homePage.clickDropDownNavigationMenu();
			homePage.clickTrackAndTraceCCD();
			trackandtracePage.setConsignmentNumber(Retrive);
			trackandtracePage.clickTrackandTraceSearch();
			uiTestHelper.scrollUp("500");
		} catch (Exception e) {
			/*
			 * e.printStackTrace(); TrackAndTracePage trackandtracePage = new
			 * TrackAndTracePage(getDriver()); trackandtracePage.clkNewSearch();
			 * trackandtracePage.setConsignmentNumber(Retrive);
			 * 
			 * trackandtracePage.clickTrackandTraceSearch();
			 */
			// Fail_Message("Track and Trace CCD search service Failed");

		}
		return Retrive;
	}
	public boolean isNewSearchBtn() {
		return false;
	}

	public String searchConWithNewSearchBtn(String Retrive){
		try {
			TrackAndTracePage trackandtracePage = new TrackAndTracePage(getDriver());
			trackandtracePage.clkNewSearch();
			trackandtracePage.setConsignmentNumber(Retrive);
			trackandtracePage.clickTrackandTraceSearch();

		} catch (Exception e) {
			e.printStackTrace();
			// Fail_Message("Track and Trace CCD 'New Search' service Failed");
		}
		return Retrive;
	}

	public void waitForObjectToDisappear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void waitForObjectToDisappearBy(By by) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public void waitForObjectTobeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void verifyComponentError(){
		By closeWindow = By.xpath("//button[@title='Close this window']");
		try {
			WebElement ele = Driver.getDriver().findElement(componentError);
			if (ele.isDisplayed()) {
				Test_Initializer.Fail_Message("Component Error has Occured!");
				WebElement close = uiTestHelper.waitForObject(closeWindow);
				uiTestHelper.clickJS(close);
			}
		} catch (Exception e) {
			System.out.println("Component Error Not Occured!");
		}
	}

	public String getBookingUpdateFailiureMessage() {
		WebElement ele = Driver.getDriver().findElement(bookingFailiure);
		return ele.getText();
	}

	public void verifyBookingUpdateorCreationError() {

		try {
			WebElement ele = Driver.getDriver().findElement(bookingFailiure);
			if (ele.isDisplayed()) {
				Test_Initializer.Fail_Message(getBookingUpdateFailiureMessage());
				WebElement close = uiTestHelper.waitForObject(closeWindow);
				uiTestHelper.clickJS(close);
				Driver.getDriver().navigate().refresh();
			}
		} catch (Exception e) {
			System.out.println("Booking Update Failiure Not Occured!");
		}
	}

	public String getImadeErrorMessage() {
		WebElement ele = Driver.getDriver().findElement(imadeError);
		return ele.getText();
	}

	public void verifyIMADEError() {
		try {
			WebElement ele = Driver.getDriver().findElement(imadeError);
			if (ele.isDisplayed()) {
				Test_Initializer.Fail_Message(getImadeErrorMessage());
				WebElement close = uiTestHelper.waitForObject(closeWindow);
				uiTestHelper.clickJS(close);
				}
		} catch (Exception e) {
			System.out.println("IMADE Failiure Not Occured!");
		}
	}
	
	public void verifyGenralEnquiryScreen() {
		try {
			WebElement ele = Driver.getDriver().findElement(generalEnquiryScreen);
			if (ele.isDisplayed()) {
				WebElement close = Driver.getDriver().findElement(closeGEWindow);
				uiTestHelper.clickJS(close);			
			}
		} catch (Exception e) {
			System.out.println("GE Screen Not Opened!");
		}
	}
	
	public String trackAndTrace_Validation_on_ConsignmentNo(){
		ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
		CloseTab();
		//String consignmentNumber = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
				//CCD_CMOD_SmokeTest.Key_Array[6]);
		String consignmentNumber="777206039975";
		TrackandTraceCCD(consignmentNumber);
		String ConNum = cp_obj.getConsignmentNo();
		System.out.println(ConNum);
		if (ConNum.indexOf(":") > 0) {
			ConNum = ConNum.substring(ConNum.indexOf(":") + 1).trim();
		}
		Assert.assertEquals(ConNum, consignmentNumber, "Reterive Search Failed");
		return ConNum;
}
	public void trackAndTrace_ValidationonerrorMessage_when_input_wrong_ConsignmentNo() {
		String consignmentNumber="777206039975";
		String invalidFormatMsg=null;
		String Mystring = new String(consignmentNumber);
		System.out.println("substring(0,10):" + Mystring.substring(0, 10)); 
		String Partial = Mystring.substring(0, 10); 
		TrackandTraceCCD(Partial);
		invalidFormatMsg=ttp_obj.getConsignmentInvalidFormatErrorMessage_for_lessthan12digit();
		Pass_Message("Invalid Format Message when we have 10 to 11 digit Consignment Number: "+invalidFormatMsg);
		System.out.println("substring(0,5):" + Mystring.substring(0, 5)); 
		String partialString = Mystring.substring(0, 5); 
		TrackandTraceCCD(partialString);
		invalidFormatMsg=ttp_obj.getConsignmentInvalidFormatErrorMessage_for_lessthan6digit();
		Pass_Message("Invalid Format Message when we have 1 to 5 digit Consignment Number: "+invalidFormatMsg);
	}
	public void trackAndTrace_ValidationOnMasterandChildTrackingNo(){
		SoftAssert softAssert=new SoftAssert();
		String MasterTrackingNumber=trackAndTrace_Validation_on_ConsignmentNo();
		Pass_Message("MasterTrackingNumber is :"+MasterTrackingNumber);
		softAssert.assertEquals(ttp_obj.isConsignmentHistoryButton(), true,"Consignment History button not Dispayed");
		Pass_Message("Consignment History Button is Displayed");
		softAssert.assertEquals(ttp_obj.isConsignmentPiecesButton(), true,"Consignment Pieces button not Dispayed");
		Pass_Message("Consignment Pieces Button is Displayed");
		uiTestHelper.scrollUp("-500");
		ttp_obj.clickConsignmentPiecesButton();
		ttp_obj.scrollPieceDetailsTable();
		ttp_obj.getConsignmentPiecesDetails();
		ttp_obj.getConsignmentPieceStatus();
		
		
		
	}
}