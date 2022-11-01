package com.tnt.ccd;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tnt.ccdobjects.AdditionalInfoPage;
import com.tnt.ccdobjects.BookingExceptionsPage;
import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.BookingRecordPage;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentInfoPage;
import com.tnt.ccdobjects.GoodsInfoPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.LogoutPage;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class CCD_CI_BookingException extends Driver {
	LoginPage login;
	HomePage homepage;
	public static String bookingReferenceNumber, bookNum;
	public static String caseNo;
	UiTestHelper uiTestHelper = new UiTestHelper();
	boolean isOntheQueue =false;
	public void ci_login(){
		try {
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY", CCD_CI.Key_Array[5]);
			getDriver().get(URL);
			System.out.println("Usename: " + Password);
			getDriver().manage().window().maximize();
			login = new LoginPage(getDriver());
			login.setUsername(Username);
			login.setPassword(Password);
			login.clickLoginBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void acceptBookingException(){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			uiTestHelper.propagateException();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
			uiTestHelper.propagateException();
			selectException(1, "Booking Exception - PT");
			uiTestHelper.propagateException();
			bookingexcppage.clickAcceptException();
			if (bookingexcppage.verifyExceptionAccept()) {
				Pass_Message("Booking exception accepted for the Booking Reference Number : " + bookingReferenceNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public String selectException(int countOfExeption, String collectionCountry) {
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		for (int i = 1; i <= countOfExeption; i++) {
			WebElement myCon = uiTestHelper.waitForObject(By.xpath("(//span[text()='" + collectionCountry
					+ "']/following::table[1]/tbody/tr[" + i + "])[1]/td[2]//label/span[1]"));
			bookingReferenceNumber = getDriver().findElement(By.xpath("(//span[text()='" + collectionCountry
					+ "']/following::table[1]/tbody/tr[" + i + "])[1]/td[3]/span/span[1]")).getText();
			System.out.println(bookingReferenceNumber);
			myCon.click();
		}
		return bookingReferenceNumber;
	}

	public void openBookingExceptionCase(String bookingReferenceNumber){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickCases();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchInput("My Open Booking Exception Case");
			bookingexcppage.clickAssignedInput("My Open Booking Exception Case");
			uiTestHelper.propagateException();
			homepage.caseExceptionSearch(bookingReferenceNumber);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 20);
			WebElement myTable = getDriver().findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= row_count; i++) {
				String a = getDriver()
						.findElement(By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/td[3]/span/span[1]"))
						.getText();
				System.out.println(a);
				System.out.println(bookingReferenceNumber);
				if (a.equals(bookingReferenceNumber)) {
					WebElement myCon = getDriver().findElement(
							By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
					uiTestHelper.clickJS(myCon);
					break;
				}
			}
			caseNo = bookingexcppage.getCaseNumber();
			System.out.println("Case Number: " + caseNo);
			Pass_Message("Booking Exception converted to Case and the Case Number is : " + caseNo);
			if (!caseNo.isEmpty()) {
				bookingexcppage.clickBookingExceptionDetailsTab();
				String caseOwner = bookingexcppage.getCaseOwner();
				if (caseOwner.contains("Swati")) {
					Pass_Message_withoutScreenCapture("Agent for Booking Exception record and Case Owner is Same."
							+ caseOwner + " is a Agent for both");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void acceptandOpenBookingExceptionCase(){
		try {
			acceptBookingException();
			openBookingExceptionCase(bookingReferenceNumber);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void bookingException_Email(){
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		try {
			casedetailpage.clickEmailTab();
			casedetailpage.clickCompose();
			uiTestHelper.propagateException();
			casedetailpage.setEmailTo("nivetha.thirunavukarasu.osv@fedex.com");
			casedetailpage.setEmailSubject("Request for Information/Action");
			casedetailpage.clickTemplate();
			casedetailpage.insertTemplatebtn();
			bookingexcppage.selectTemplateType("All Classic Templates");
			casedetailpage.selectTemplate("Generic");
			uiTestHelper.propagateException();
			casedetailpage.clickGenericTemplate();
			Pass_Message("Generic template inserted");
			uiTestHelper.propagateException();
			casedetailpage.clickSendBtn();
			String confirmMessage = casedetailpage.getEmailConfirmMessage();
			if (confirmMessage.isEmpty()) {
				Fail_Message("Email is not Sent Successfully");
			} else {
				Pass_Message("Email sent successfully" + confirmMessage);
			}
			getDriver().navigate().refresh();
			casedetailpage.clickRelatedTab();
			bookingexcppage.clickEmailView();
			int Size = bookingexcppage.getEmailTableSize();
			bookingexcppage.clickemailTable(Size, "Request for Information");
			bookingexcppage.clickEmailDetailsTab();
			String emailStatus = bookingexcppage.getEmailStatus();
			Pass_Message("Email Status is : " + emailStatus);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email service failed");
		}
	}

	public void bookingException_caseRemark(){
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		try {
			casedetailpage.clickCaseRemarkTab();
			casedetailpage.setCaseRemarkdesc("Update Case Remarks for testing");
			casedetailpage.clickCaseRemarkSave();
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']")));
			if (casedetailpage.verifySuccessMessage() == true) {
				casedetailpage.clickRelatedTab();
				casedetailpage.clickCaseRemarkVew();
			}
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
				Pass_Message("Comments of Case Remark is displayed as " + comments);
			}
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case remark verification failed");
		}
	}

	public void bookingException_CallBackActivity(){
		try {
			CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
			Support.callback_activity("re_case");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Call Back Activity service failed");
		}
	}

	public void bookingException_RFI(){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
			CCD_Connectivity conn = new CCD_Connectivity();
			CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
			HomePage homepage = new HomePage(getDriver());
			String ResponderName = null;
			casedetailpage.clickRFITab();
			casedetailpage.setRFISubject("Test RFI");
			caseNo = casedetailpage.getCaseNo();
			System.out.println("CaseNumber: " + caseNo);
			casedetailpage.setRFIQuestion("RFI Question");
			casedetailpage.clickRFISave();
			if (casedetailpage.verifySuccessMessage() == false) {
				Fail_Message("RFI Not Created");
			} else {
				Pass_Message("RFI created");
				casedetailpage.clickRelatedTab();
				bookingexcppage.clickRFIView();
				int Size = bookingexcppage.getRFITableSize();
				System.out.println("Size: " + Size);
				uiTestHelper.propagateException();
				bookingexcppage.clickrfiTable(Size, "RFI");
			}
			uiTestHelper.propagateException();
			// Open RFI
			reuse.logout();
			OIB_Support_Login();
			uiTestHelper.propagateException();
			conn.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			homepage = new HomePage(getDriver());
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - DK");
			uiTestHelper.propagateException();
			String rfi_bookingReference = bookingReferenceNumber + "_" + "RFI";
			homepage.bookingExceptionSearch(rfi_bookingReference);
			uiTestHelper.propagateException();
			WebElement myCon = uiTestHelper.waitForObject(By.xpath(
					"(//span[text()='Booking Exception - DK']/following::table[1]/tbody/tr[1])[1]/td[2]//label/span[1]"));
			myCon.click();
			bookingexcppage.clickAcceptException();
			reuse.logout();
			ci_login();
			reuse.internal_tabclose();
			boolean rfistatus = bookingexcppage.verifyRfiStatus();
			if (rfistatus == true) {
				Pass_Message("Rfi Completed Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case selection from My Open Cases failed");
		}
	}

	public void bookingException_CloseCase(){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			bookingexcppage.clickCloseCaseButton();
			bookingexcppage.setCloseCaseComments("Booking Exception is Closed");
			bookingexcppage.saveCloseCaseButton();
			String status = bookingexcppage.getCaseStatus();
			if (status.contains("'Manual-Closed")) {
				Pass_Message("Case is closed Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed");
		}
	}

	public void bookingException_VerifyClosedCase(){
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			homepage = new HomePage(getDriver());
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickCases();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchInput("My Open Booking Exception Case");
			bookingexcppage.clickAssignedInput("My Open Booking Exception Case");
			uiTestHelper.propagateException();
			boolean caseResult = caseSearch(caseNo);
			System.out.println(caseResult);
			if (!caseResult) {
				Pass_Message("Closed Case Removed From My Open Booking Exception Case");
			}
			bookingexcppage.clickMyOpenBookingExceptionItems();
			bookingexcppage.searchInput("My Closed Booking Exception Case");
			bookingexcppage.clickAssignedInput("My Closed Booking Exception Case");
			uiTestHelper.propagateException();
			boolean caseResult1 = caseSearch(caseNo);
			System.out.println(caseResult1);
			if (!caseResult1) {
				Pass_Message("Closed Case Removed From My Open Booking Exception Case");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed");
		}
	}

	public void release_BookingException(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		BookingPage bkpage = new BookingPage(getDriver());
		GoodsInfoPage goodspage = new GoodsInfoPage(getDriver());
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(getDriver());
		AdditionalInfoPage adtnlpage = new AdditionalInfoPage(getDriver());
		try {
			getDriver().navigate().refresh();
			acceptBookingException();
			openBookingExceptionCase(bookingReferenceNumber);
			bookingexcppage.clickViewBooking();
			bkdetailspage.clickEditBooking();
			uiTestHelper.propagateException();
			bkpage.setCallerName("Nivetha");
			bkpage.setCallerPhone("8940732594");
			bkpage.clickBookingnextbtn();
			goodspage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			adtnlpage.getValidServices();
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			adtnlpage.clickGetPrice();
			uiTestHelper.scrolldown("300");
			adtnlpage.clickViewSummary();
			uiTestHelper.propagateException();
			adtnlpage.clickReleaseBookingException();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Booking Exception has been Released");
				if (bookingexcppage.getCaseStatus().equals("Auto-Closed")) {
					Pass_Message("Booking Exception release is Verified");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Relaese Booking Exception operation failed");
		}
	}

	// IOB Support Login
	public void OIB_Support_Login(){
		try {
			LoginPage loginpage = new LoginPage(getDriver());
			getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CCD_BookingException.Key_Array[3]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CCD_BookingException.Key_Array[3]);
			/*
			 * String Username = "nivetha.thirunavukarasu.osv@fedex.com.sit"; String
			 * Password = "Fedex789";
			 */
			getDriver().get(URL);
			getDriver().manage().window().maximize();
			loginpage.setUsername(Username);
			System.out.println(Username);
			loginpage.setPassword(Password);
			System.out.println(Password);
			loginpage.clickLoginBtn();
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public boolean caseSearch(String caseNumber)  {
		HomePage homePage = new HomePage(getDriver());
		boolean iscaseNo = false;
		homePage.caseSearch(caseNumber);
		int size = 0;
		do {
			uiTestHelper.propagateException();
			homePage.refreshSearch();
			size++;
		} while (size <= 15);
		List<WebElement> objRow = getDriver().findElements(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr"));
		int row_count = objRow.size();
		System.out.println("Row count in MyOpenCases table is " + row_count);
		getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		for (int i = 1; i <= objRow.size(); i++) {
			String a = getDriver()
					.findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th//a"))
					.getText();
			if (a.equals(caseNumber)) {
				iscaseNo = true;
				WebElement ele = getDriver()
						.findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th//a"));
				uiTestHelper.clickJS(ele);
				break;
			} else {
				iscaseNo = false;
			}
		}
		return iscaseNo;
	}

	public void verifyholdBooking_with_bookingException_history_tab(){
		CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		// TODO Auto-generated method stub
		try {
			BK_createBookingwithHoldStatus();
			acmcibooking.BK_getRecentBookingfrom_BookingList(acmcibooking.bookNum);
			bkdetailspage.clickBookingExceptionTab();
			bkdetailspage.clickBookingExceptionTable();
			try {
				if (bkdetailspage.verifyCustomerAccount() && bkdetailspage.verifyProductOption()
						&& bkdetailspage.verifyService()) {
					Pass_Message("Below details are displayed in the Booking Exception History Tab of the Booking :");
					Pass_Message_withoutScreenCapture("Customer Account Name: " + bkdetailspage.getCustomerName());
					Pass_Message_withoutScreenCapture("Product Option Name: " + bkdetailspage.getProductOption());
					Pass_Message_withoutScreenCapture("Service Name: " + bkdetailspage.getServiceName());
				}
			} catch (Exception e) {
				Fail_Message("Booking Exception Details are missing");
			}
		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}
	}

	public void veifyHLDBooking_for_dedicatedAccount(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		try {
			BK_createHLDBookingExceptionforDedicatedAccount();
			con.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
			bookNum = bookNum.replaceAll("\"", "");
			System.out.println(bookNum);
			if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
				homepage.bookingExceptionSearch(bookNum);
				int size = 0;
				do {
					uiTestHelper.propagateException();
					homepage.refreshSearch();
					size++;
				} while (size <= 20);
				WebDriverWait wait = new WebDriverWait(getDriver(), 60);
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr")));
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
				if (isOntheQueue) {
					Pass_Message("HLD Booking exception dispalyed on the Booking Exception - PT - Dedicated Queue");
				} else {
					Fail_Message("HLD Booking exception Not dispalyed on the Booking Exception - PT - Dedicated Queue");
				}

			}
			homepage.clearSearchList();
		} catch (Exception e) {
			Fail_Message("Verification for Hold Booking Exception with Dedicated Account is failed");
		}
	}

	public void BK_createBookingwithHoldStatus(){
		CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(getDriver());
		try {
			acmcibooking.BK_BookingPage_SISP();
			acmcibooking.BK_GoodsInfoPage();
			acmcibooking.BK_ConInfo_Page();
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			adtnlinfopage.enableHold();
			adtnlinfopage.clickViewSummary();
			adtnlinfopage.clickConfirmBooking();
			wait = new WebDriverWait(getDriver(), 60);
			try {
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
				String book = adtnlinfopage.getBookingConfirmMsg();
				bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
				if (getDriver().findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
					Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);
				} else {
					Fail_Message("Booking failed");

				}
			} catch (Exception e) {
				Fail_Message("Booking Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking with Hold Status not created Successfully");
		}

	}

	public void BK_createHLDBookingExceptionforDedicatedAccount(){
		CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(getDriver());

		try {
			acmcibooking.BK_SISP_BookingPage_DedicatedAccount();
			acmcibooking.BK_GoodsInfoPage();
			acmcibooking.BK_ConInfo_Page();
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			adtnlinfopage.enableHold();
			adtnlinfopage.clickViewSummary();
			adtnlinfopage.clickConfirmBooking();
			try {
				String book = adtnlinfopage.getBookingConfirmMsg();
				bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
				Assert.assertEquals(adtnlinfopage.verifyBookingConfirmMsg(), true);
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);
			} catch (Exception e) {
				Fail_Message("Booking Failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking with Hold Status not created Successfully");
		}

	}

	public void BK_createDBCBookingExceptionforDedicatedAccount(){
		CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(getDriver());
		try {
			acmcibooking.BK_RIRP_with_Dedicated_Account();
			acmcibooking.BK_GoodsInfoPage();
			acmcibooking.BK_ConInfo_Page();
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			adtnlinfopage.clickViewSummary();
			adtnlinfopage.clickConfirmBooking();
			wait = new WebDriverWait(getDriver(), 60);
			try {
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

				String book = adtnlinfopage.getBookingConfirmMsg();
				bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
				if (getDriver().findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
					Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

				} else {
					Fail_Message("Booking failed");
				}
			} catch (Exception e) {
				Fail_Message("Booking Failed");
				adtnlinfopage.clickCancel();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking with Hold Status not created Successfully");
		}

	}

	public void veifyDBCBooking_for_dedicatedAccount(String env){
		HomePage homepage = new HomePage(getDriver());
		CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		try {
			BK_createDBCBookingExceptionforDedicatedAccount();
			con.CloseTab();
			logout();
			acmcibooking.login_DKUser();
			acmcibooking.returnToPage();
			con.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			uiTestHelper.propagateException();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - DK");
			bookNum = bookNum.replaceAll("\"", "");
			System.out.println(bookNum);
			if (bookingexcppage.verifyHearderDisplayed("Booking Exception - DK")) {
				homepage.bookingExceptionSearch(bookNum);
				int size = 0;
				do {
					uiTestHelper.propagateException();
					homepage.refreshSearch();
					size++;
				} while (size <= 20);
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
				if (isOntheQueue) {
					Pass_Message("DBC Booking exception dispalyed on the Booking Exception - DK Queue");
				} else {
					Fail_Message("DBC Booking exception Not dispalyed on the Booking Exception - DK Queue");
				}
			}
			logout();
			acmcibooking.login_PTUser();
			acmcibooking.returnToPage();
		} catch (Exception e) {
			Fail_Message("Verification for DBC Booking Exception with Dedicated Account is failed");
			acmcibooking.ci_login(env);

		}
	}

	public void logout(){
		LogoutPage logout = new LogoutPage(getDriver());
		try {
			logout.clickUser();
			uiTestHelper.propagateException();
			if (logout.verifyLogout()) {
				logout.clickLogout();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Logout Failed");
		}
	}

	public void verifyholdBooking_with_bookingException_fields(){

		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		try {
			BK_createBookingwithHoldStatus();
			con.CloseTab();
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			// getDriver().navigate().refresh();
			System.out.println(bookNum);
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
			bookNum = bookNum.replaceAll("\"", "");
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr")));
			try {
				if (bookingexcppage.verifyHearderDisplayed("Service")
						&& bookingexcppage.verifyHearderDisplayed("Product Options")
						&& bookingexcppage.verifyHearderDisplayed("Customer Account Name")) {
					Pass_Message("Exception headers are displayed on the Table");
				}
			} catch (Exception e) {
				Fail_Message("Booking Exception Details are missing in Booking Exception Table");
			}
			bookingexcppage.viewBookingException(bookNum);
			try {
				if (bookingexcppage.verifyCustomerAccount() && bookingexcppage.verifyProductOption()
						&& bookingexcppage.verifyService()) {
					Pass_Message("Below details are displayed in the Booking Exception History Tab of the Booking :");
					Pass_Message_withoutScreenCapture("Customer Account Name: " + bookingexcppage.getCustomerName());
					Pass_Message_withoutScreenCapture("Product Option Name: " + bookingexcppage.getProductOption());
					Pass_Message_withoutScreenCapture("Service Name: " + bookingexcppage.getServiceName());
				}
			} catch (Exception e) {
				Fail_Message("Booking Exception Details are missing in History Tab");
			}

		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}

	}

	public void clearBookingException(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		BK_createBookingwithHoldStatus();
		con.CloseTab();
		uiTestHelper.propagateException();
		homepage.clickDropDownNavigationMenu();
		System.out.println("BookNum: " + bookNum);
		bookNum = bookNum.replaceAll("\"", "");
		bookingReferenceNumber = bookNum;
		System.out.println("Booking re fo: " + bookingReferenceNumber);
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isOntheQueue) {
				Pass_Message("HLD Booking exception dispalyed on the Booking Exception - PT Queue");
			} else {
				Fail_Message("HLD Booking exception Not dispalyed on the Booking Exception - PT Queue");
			}
			selectException(1, "Booking Exception - PT");
			uiTestHelper.propagateException();
			bookingexcppage.clickAcceptException();
		}
		openBookingExceptionCase(bookingReferenceNumber);
		bookingexcppage.clickReleaseException();
		if (bookingexcppage.verifyRfiStatus()) {
			String Status = bookingexcppage.getExceptionStatus();
			System.out.println(Status);
			assertEquals(Status, "Auto-Closed");
			Pass_Message("Booking Exception is cleared and Status is : " + Status);
		}
		if (bookingexcppage.verifyReleaseException() == false) {
			Pass_Message("Booking exception is cleared and Release Button is removed on the page");
		}

	}

	public void booking_to_bookingExceptionCases(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		try {
			BK_createBookingwithHoldStatus();
			con.CloseTab();
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			// getDriver().navigate().refresh();
			System.out.println(bookNum);
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
			bookNum = bookNum.replaceAll("\"", "");
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 20);
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr")));
			uiTestHelper.propagateException();
			 isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			if (isOntheQueue) {
				Pass_Message("HLD Booking exception dispalyed on the Booking Exception - PT Queue");
			} else {
				Fail_Message("HLD Booking exception Not dispalyed on the Booking Exception - PT Queue");
			}
			selectException(2, "Booking Exception - PT");
			uiTestHelper.propagateException();
			bookingexcppage.clickAcceptException();
			uiTestHelper.propagateException();
		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}
	}

	public void verifyCaseUpdateColumn(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		try {
			booking_to_bookingExceptionCases();
			homepage.clickDropDownNavigationMenu();
			homepage.clickCases();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchInput("My Open Booking Exception Case");
			bookingexcppage.clickAssignedInput("My Open Booking Exception Case");
			uiTestHelper.propagateException();
			if (bookingexcppage.verifyHeaderDisplayed("Case Update")) {
				Pass_Message("Case Update Column is displayed and Validated on the My Open Booking Cases");
			}
		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}
	}

	public void verifyCaseRemark_bookingException(){

		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		try {
			BK_createBookingwithHoldStatus();
			con.CloseTab();
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			// getDriver().navigate().refresh();
			System.out.println(bookNum);
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchInput("Booking Exception - PT");
			bookNum = bookNum.replaceAll("\"", "");
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr")));
			uiTestHelper.propagateException();
			 isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			if (isOntheQueue) {
				Pass_Message("HLD Booking exception dispalyed on the Booking Exception - PT Queue");
			} else {
				Fail_Message("HLD Booking exception Not dispalyed on the Booking Exception - PT Queue");
			}
			selectException(2, "Booking Exception - PT");
			uiTestHelper.propagateException();
			bookingexcppage.clickAcceptException();
			openBookingExceptionCase(bookNum);

		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}
	}

	public void verifySupervisorColumns_on_MyTeamOpenBookingException(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("My Team Open Booking Exception Cases");
		bookingexcppage.clickAssignedInput("My Team Open Booking Exception Cases");
		if (bookingexcppage.verifyHeaderDisplayed("Last Modified Date")) {
			Pass_Message("Last Modified Date Column is displayed and Validated on the My Team Open Booking Cases");
		}
		if (bookingexcppage.verifyHeaderDisplayed("Owner Name")) {
			Pass_Message("Owner Name Column is displayed and Validated on the My Team Open Booking Cases");
		}
	}

	public void verifySupervisorColumns_on_MyTeamClosedBookingException(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("My Team Closed Booking Exception Cases");
		bookingexcppage.clickAssignedInput("My Team Closed Booking Exception Cases");
		if (bookingexcppage.verifyHeaderDisplayed("Last Modified Date")) {
			Pass_Message("Last Modified Date Column is displayed and Validated on the My Team Closed Booking Cases");
		}
		if (bookingexcppage.verifyHeaderDisplayed("Owner Name")) {
			Pass_Message("Owner Name Column is displayed and Validated on the My Team Closed Booking Cases");
		}
	}

	public void verifyBookingDescriptionField_on_bookingExceptionQueue(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity ccd_Connectivity = new CCD_Connectivity();
		BK_createBookingwithHoldStatus();
		ccd_Connectivity.CloseTab();
		homepage.clickDropDownNavigationMenu();
		homepage.clickBookingException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("Booking Exception - PT");
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		if (bookingexcppage.verifyHearderDisplayed("Exception Description")) {
			Pass_Message("Exception Description - displayed in the Exception Queue");
		} else {
			Fail_Message("Exception Description - not displayed in the Exception Queue");
		}
	}

	public void verifyBookingDescriptionField_on_bookingHistoryTab(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		BK_createBookingwithHoldStatus();
		uiTestHelper.propagateException();
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		if (bookingRecordPage.verifyExceptionDescription()) {
			Pass_Message("Exception Description - displayed in the Exception History Tab");
		} else {
			Fail_Message("Exception Description - not displayed in the Exception History Tab");
		}
		bookingRecordPage.clickBookingExceptionTable();
		if (bookingexcppage.verifyBookingDescription()) {
			Pass_Message("Exception Description - displayed in the Exception History Record");
		} else {
			Fail_Message("Exception Description - not displayed in the Exception History Record");
		}
	}

	public void verifyBookingDescriptionField_on_Cases(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		try {
			booking_to_bookingExceptionCases();
			openBookingExceptionCase(bookNum);
			bookingexcppage.clickBookingExceptionDetailsTab();
			if (bookingexcppage.verifyExceptionDescription()) {
				Pass_Message("Booking Exception Description displayed on the Cases Screen");
			} else {
				Fail_Message("Booking Exception Description not displayed on the Cases Screen");
			}
		} catch (Exception e) {
			Fail_Message("Booking Exception details verification is failed");
		}
	}

	public void verifyReleaseBooking_while_edit_holdBooking(){
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		CCD_Connectivity con = new CCD_Connectivity();
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(getDriver());
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(getDriver());
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(getDriver());
		BookingPage bookingPage = new BookingPage(getDriver());
		ci_booking.BK_SISP_Flow_without_Validation();
		bookingRecordPage.clickEditBooking();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("700");
		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("1000");
		if (additionalInfoPage.verifyHoldOption()) {
			additionalInfoPage.enableHold();
		}
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		uiTestHelper.propagateException();
		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		Assert.assertEquals(additionalInfoPage.verifyUpdatedBookingConfirmMsg(), true);
		Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingHistory();
		con.CloseTab();
		verifyBookingExceptionontheQueue(bookNum);
		bookingexcppage.clickReleaseException();
		Assert.assertEquals(bookingexcppage.verifyCaseReopen(), true);
		Pass_Message("Hold Booking exception got released");
	}

	public void verifyBookingExceptionontheQueue(String bookNum){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		homepage.clickDropDownNavigationMenu();
		
		System.out.println("BookNum: " + bookNum);
		bookNum = bookNum.replaceAll("\"", "");
		bookingReferenceNumber = bookNum;
		System.out.println("Booking re fo: " + bookingReferenceNumber);
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				 isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectException(2, "Booking Exception - PT");
		bookingexcppage.clickAcceptException();
		uiTestHelper.propagateException();
		openBookingExceptionCase(bookNum);
	}

	public void verifyBookingxceptionOnQueueforReceiverCountry(String bookNum){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		CCD_Connectivity con = new CCD_Connectivity();
		logout();
		ci_booking.login_DKUser();
		ci_booking.returnToPage();
		con.CloseTab();
		homepage.clickDropDownNavigationMenu();
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - DK");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - DK")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				 isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectException(1, "Booking Exception - DK");
		bookingexcppage.clickAcceptException();
		uiTestHelper.propagateException();
		openBookingExceptionCase(bookNum);
	}

	public void verifyRelatedTab_when_anyCaseOpens(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		CCD_Connectivity ccd_Connectivity = new CCD_Connectivity();
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		BK_createHLDBookingExceptionforDedicatedAccount();
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		bookingRecordPage.clickBookingExceptionTable();
		ccd_Connectivity.CloseTab();
		homepage.clickDropDownNavigationMenu();
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookNum = bookNum.replaceAll("\"", "");
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT - Dedicated");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT - Dedicated")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				 isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectException(1, "Booking Exception - PT - Dedicated");
		bookingexcppage.clickAcceptException();
		uiTestHelper.propagateException();
		openBookingExceptionCase(bookNum);
		Assert.assertEquals(caseDetailsPage.verifyRelatedTab(), true);
		Pass_Message("Related Tab displayed as Default Tab when case get Opens");
	}

	public void verifyOIBRelease_hidden_from_OIBCaseView(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		CCD_Connectivity ccd_Connectivity = new CCD_Connectivity();
		HomePage homepage = new HomePage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		BK_createHLDBookingExceptionforDedicatedAccount();
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		bookingRecordPage.clickBookingExceptionTable();
		ccd_Connectivity.CloseTab();
		homepage.clickDropDownNavigationMenu();
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookNum = bookNum.replaceAll("\"", "");
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT - Dedicated");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT - Dedicated")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectException(1, "Booking Exception - PT - Dedicated");
		bookingexcppage.clickAcceptException();
		uiTestHelper.propagateException();
		openBookingExceptionCase(bookNum);
		bookingException_RFI();
	}

	public void verifyBookingException_Hyperlinks(){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());

		String bookExcepNum = bookingexcppage.getBookingExceptionID();
		System.out.println("Value is  " + bookExcepNum + "  end");
		uiTestHelper.clickJSByObjects(By.xpath("//span[text()='" + bookExcepNum + "']"));
		Assert.assertTrue(bookingexcppage.verifyBookingExceptionTab());
		{
			Pass_Message(
					"Booking Exception hyperlink is clicked and Booking Exception tab is displayed with Exception details");
		}
		if (bookingexcppage.getCaseOwner() != null) {
			String ownerName = bookingexcppage.getCaseOwner();
			Pass_Message("Booking Exception Owner name " + ownerName + " is displayed");
		}
	}

	public void verifyColumnsOnOpenBookingExceptionCasePage(){
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		CCD_CI_Booking ciBooking=new CCD_CI_Booking();
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		homepage = new HomePage(getDriver());

		ciBooking.verifyAwkwardFreightEnvelope();
		ACM_Connectivity.CloseTab();

		acceptBookingExceptionInQueue(ciBooking.bookNum);
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("My Open Booking Exception Case");
		bookingexcppage.clickAssignedInput("My Open Booking Exception Case");
		if (bookingexcppage.verifyHearderDisplayed("My Open Booking Exception Case")) {
			Pass_Message("My Open Booking Exception Case is opened on Case page");
		}

		Assert.assertTrue(casedetailpage.verifyCustomerAccountColumn());
		{
			Pass_Message("Customer Account column is displayed on My Open Booking Exception Case page");
		}
		Assert.assertTrue(casedetailpage.verifyExceptionCodeColumn());
		{
			Pass_Message("Exception Code column is displayed on My Open Booking Exception Case page");
		}
		Assert.assertTrue(casedetailpage.verifySlaStatus());
		{
			Pass_Message("SLA Status column is displayed on My Open Booking Exception Case page");
		}
	}

	public void acceptAwkwardBookingException(){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			uiTestHelper.propagateException();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Recently Viewed");
			uiTestHelper.propagateException();
			selectException(1, "Denmark");
			uiTestHelper.propagateException();
			bookingexcppage.clickAcceptException();
			uiTestHelper.propagateException();
			if (bookingexcppage.verifyExceptionAccept()) {
				Pass_Message("Booking exception accepted for the Booking Reference Number : " + bookingReferenceNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void acceptBookingExceptionInQueue(String bookNum){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		homepage.clickDropDownNavigationMenu();
		System.out.println("BookNum: " + bookNum);
		bookNum = bookNum.replaceAll("\"", "");
		bookingReferenceNumber = bookNum;
		System.out.println("Booking re fo: " + bookingReferenceNumber);

		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
			homepage.bookingExceptionSearch(bookNum);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			try {
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectException(1, "Booking Exception - PT");
		bookingexcppage.clickAcceptException();
		uiTestHelper.propagateException();
	}

	public void verifyColumnsOnClosedBookingExceptionCasePage(){
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		homepage = new HomePage(getDriver());

		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();

		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchInput("My Closed Booking Exception Case");
		bookingexcppage.clickAssignedInput("My Closed Booking Exception Case");
		if (bookingexcppage.verifyHearderDisplayed("My Closed Booking Exception Case")) {
			Pass_Message("My Closed Booking Exception Case is opened on Case page");
		}

		Assert.assertTrue(casedetailpage.verifyCustomerAccountColumn());
		{
			Pass_Message("Customer Account column is displayed on My Closed Booking Exception Case page");
		}
		Assert.assertTrue(casedetailpage.verifyExceptionCodeColumn());
		{
			Pass_Message("Exception Code column is displayed on My Closed Booking Exception Case page");
		}
		Assert.assertTrue(casedetailpage.verifySlaStatus());
		{
			Pass_Message("SLA Status column is displayed on My Closed Booking Exception Case page");
		}
	}

	public void verifyReopenFunctionality_on_bookingException(){
		BookingExceptionsPage bookingExceptionsPage = new BookingExceptionsPage(getDriver());
		CMOD_FF_Reusable bookingExpActivity = new CMOD_FF_Reusable();
		CCD_Connectivity con = new CCD_Connectivity();
		CCD_CI_Booking ciBooking=new CCD_CI_Booking();
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());

		BK_createBookingwithHoldStatus();
		// ccdBookingFlow.verifyAwkwardFreightEnvelope();
		con.CloseTab();
		uiTestHelper.propagateException();

		// Two Booking Exceptions
		verifyBookingExceptionontheQueue(bookNum);
		uiTestHelper.propagateException();

		// openBookingExceptionCases(bookNum);
		// caseDetailsPage.selectCaseTickBoxInTable(caseNo);
		bookingExceptionsPage.verifyReleaseException();
		bookingExceptionsPage.clickReleaseException();
		Assert.assertEquals(bookingExceptionsPage.verifyCaseReopen(), true);
		{
			Pass_Message("Case Reopened");
		}
		con.CloseTab();
		homepage = new HomePage(getDriver());
		uiTestHelper.propagateException();
		getDriver().navigate().refresh();
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		bookingexcppage.clickRecentlyViewedItems();
		bookingExceptionsPage.searchInput("My Closed Booking Exception Case");
		bookingExceptionsPage.clickAssignedInput("My Closed Booking Exception Case");
		uiTestHelper.propagateException();
		boolean caseResult1 = false;
		caseResult1 = caseSearch(caseNo);
		if (!caseResult1) {
			Pass_Message("Closed Case opened");
		}

		Assert.assertTrue(bookingExceptionsPage.verifyCaseReopen());
		{
			Pass_Message("Reopen button is displayed My Closed Booking Exception Cases");
		}

		bookingException_Email();
		bookingException_CallBackActivity();
		bookingExpActivity.internal_tabclose();
		bookingException_caseRemark();
		bookingExpActivity.internal_tabclose();
		bookingException_RFI();
	}

	public void openBookingExceptionCases(String bookingReferenceNumber){
		try {
			BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
			homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickCases();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchInput("My Open Booking Exception Case");
			bookingexcppage.clickAssignedInput("My Open Booking Exception Case");
			uiTestHelper.propagateException();
			homepage.caseExceptionSearch(bookingReferenceNumber);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 20);
			WebElement myTable = getDriver().findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);
			for (int i = 1; i <= row_count; i++) {
				String a = getDriver()
						.findElement(By.xpath(
								"//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/td[3]/span/span[1]"))
						.getText();
				System.out.println(a);
				System.out.println(bookingReferenceNumber);
				if (a.equals(bookingReferenceNumber)) {
					uiTestHelper.propagateException();
					WebElement myCon = getDriver().findElement(
							By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
					uiTestHelper.propagateException();
					uiTestHelper.clickJS(myCon);
					break;
				}
			}
			caseNo = bookingexcppage.getCaseNumber();
			System.out.println("Case Number: " + caseNo);
			Pass_Message("Booking Exception converted to Case and the Case Number is : " + caseNo);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void verifyReleaseBooking_while_edit_ackwardFreightBooking(){
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		CCD_Connectivity con = new CCD_Connectivity();
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(getDriver());
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(getDriver());
		BookingPage bookingPage = new BookingPage(getDriver());
		ci_booking.BK_SISP_Flow_without_Validation();
		bookingRecordPage.clickEditBooking();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("700");
		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		ci_booking.validationOnDims_in_consignmentinfopage();
		uiTestHelper.scrolldown("300");
		additionalInfoPage.clickValidServices();
		if (additionalInfoPage.verifyGetPrice()) {
			additionalInfoPage.clickGetPrice();
		}
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("1000");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		uiTestHelper.propagateException();
		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		Assert.assertEquals(additionalInfoPage.verifyUpdatedBookingConfirmMsg(), true);
		Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		Assert.assertEquals(bookingRecordPage.getExceptionDescription(), "Awkward Freight");
		Pass_Message(bookingRecordPage.getExceptionDescription() + " Exception created in Exception History");
		con.CloseTab();
		verifyBookingExceptionontheQueue(bookNum);
		bookingexcppage.clickReleaseException();
		Assert.assertEquals(bookingexcppage.verifyCaseReopen(), true);
		Pass_Message("Hold Booking exception got released");
	}

	public void verifyReleaseBooking_while_edit_dbcFreightBooking(){
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		CCD_Connectivity con = new CCD_Connectivity();
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		BookingRecordPage bookingRecordPage = new BookingRecordPage(getDriver());
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(getDriver());
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(getDriver());
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(getDriver());
		BookingPage bookingpage = new BookingPage(getDriver());
		ci_booking.BK_RIRP_Flow();
		ci_booking.BK_getRecentBookingfrom_BookingList(ci_booking.bookNum);
		bookingRecordPage.clickEditBooking();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("700");
		bookingpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		additionalInfoPage.clickValidServices();
		if (additionalInfoPage.verifyGetPrice()) {
			additionalInfoPage.clickGetPrice();
		}
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("1000");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		uiTestHelper.propagateException();
		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		Assert.assertEquals(additionalInfoPage.verifyUpdatedBookingConfirmMsg(), true);
		Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		Assert.assertEquals(bookingRecordPage.getExceptionCode(), "DBC");
		Pass_Message(bookingRecordPage.getExceptionCode() + " Exception created in Exception History");
		con.CloseTab();
		uiTestHelper.propagateException();
		verifyBookingxceptionOnQueueforReceiverCountry(bookNum);
		bookingexcppage.clickReleaseException();
		Assert.assertEquals(bookingexcppage.verifyCaseReopen(), true);
		Pass_Message("DBC Booking exception got released");
	}

	public void verifyBookingException_with_CustomerAccountNumber(){
		CCD_Connectivity con = new CCD_Connectivity();
		String accountNumber = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY", CCD_CI.Key_Array[5]);
		System.out.println(accountNumber);
		BK_createBookingwithHoldStatus();
		con.CloseTab();
		verifyCustomerAccountonBookingException(bookNum, accountNumber);
		// getDriver().navigate().refresh();

	}
	
	public void verifyBookingException_ListView_CustomerAccountNumber_and_Name(){
		HomePage homepage = new HomePage(getDriver());
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		CCD_Connectivity con = new CCD_Connectivity();
		String accountNumber = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY", CCD_CI.Key_Array[5]);
		System.out.println(accountNumber);
		BK_createBookingwithHoldStatus();
		con.CloseTab();
		uiTestHelper.propagateException();
		homepage.clickDropDownNavigationMenu();
		homepage.clickBookingException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		Assert.assertEquals(bookingexcppage.verifyHeaderDisplayed("Customer Account Number"), true);
		if (bookingexcppage.verifyHeaderDisplayed("Customer Account Number")) {
			Pass_Message("Customer Account Number field included in Booking exception Queue");

		}
		Assert.assertEquals(bookingexcppage.verifyHeaderDisplayed("Customer Name"), true);
		if (bookingexcppage.verifyHeaderDisplayed("Customer Name")) {
			Pass_Message("Customer Name field included in Booking exception Queue");

		}
	}

	public void verifyCustomerAccountonBookingException(String bookNum, String accountNmber){
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		homepage.clickDropDownNavigationMenu();
	
		System.out.println("BookNum: " + bookNum);
		bookNum = bookNum.replaceAll("\"", "");
		bookingReferenceNumber = bookNum;
		System.out.println("Booking re fo: " + bookingReferenceNumber);
		homepage.clickBookingException();
		uiTestHelper.propagateException();
		bookingexcppage.clickRecentlyViewedItems();
		bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
		if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
			homepage.bookingExceptionSearch(accountNmber);
			int size = 0;
			do {
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				size++;
			} while (size <= 15);
			
			try {
				isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertEquals(isOntheQueue, true);
			if (isOntheQueue) {
				Pass_Message("Created Booking exception --" + bookNum + " with Account Number --" + accountNmber
						+ " is on the Queue");
			}
		}
	}
	
	public void verifyDPTBookingExceptionCase() {
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		homepage.searchGlobalSearch("566487316");
		
		
	}
}
