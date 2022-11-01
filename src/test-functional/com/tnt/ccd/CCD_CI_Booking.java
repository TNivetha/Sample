package com.tnt.ccd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.tnt.ccdobjects.AdditionalInfoPage;
import com.tnt.ccdobjects.BookingExceptionsPage;
import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.BookingRecordPage;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentInfoPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.CustomerIdentificationPage;
import com.tnt.ccdobjects.GlobalSearch;
import com.tnt.ccdobjects.GoodsInfoPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.QuickBookingPage;
import com.tnt.ccdobjects.QuoteAdditionalInfoPage;
import com.tnt.ccdobjects.QuoteDetailPage;
import com.tnt.ccdobjects.QuotePage;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class CCD_CI_Booking extends Driver {
	LoginPage login;
	HomePage homePage;
	String globalSearch, AcctName;
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	public static String Country, quoteNum, bookNum, price, quoteNumber, consignmentNumber, serviceNameQuote,
			serviceNameBK;
	public String BookingId = "";
	UiTestHelper uiTestHelper = new UiTestHelper();
	SoftAssert softAssert = new SoftAssert();
	String URL, Username, Password = null;

	public void bookingSelectionOnHomepage(String AcctName) {
		SoftAssert softAssert = new SoftAssert();
		try {
			HomePage homePage = new HomePage(driver);
			softAssert.assertEquals(homePage.verifydropDownMenu(), true);
			homePage.clickDropDownNavigationMenu();
			homePage.clickBooking();
			homePage.searchBooking(AcctName);
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Booking is not selected on the HomePage");
		}
	}

	public void newBookingonCustomerAccPage(String accountName) {
		BookingPage bkpage = new BookingPage(driver);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		custaccpage.selectCustomerAccounts(accountName);
		Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewBooking();
		}
		Assert.assertEquals(bkpage.verifyBookingTitle(), true, "New Booking page not Opened");
	}

	public void newQuoteonCustomerAccPage(String accountName) {
		QuotePage quotePage = new QuotePage(driver);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		custaccpage.selectCustomerAccounts(accountName);
		Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		Assert.assertEquals(quotePage.verifyQuoteInfoTitle(), true, "New Quote page not Opened");
	}

	public void setDeliveryAddress(String country, String postal, String town, String customerName, String address) {
		SoftAssert softAssert = new SoftAssert();
		BookingPage bookingPage = new BookingPage(driver);
		bookingPage.setDeliveryCountry(country);
		bookingPage.setDeliveryPostal(postal);
		bookingPage.setDeliveryTown(town);
		bookingPage.setDelCustomerName(customerName);
		bookingPage.setDeliveryAddress(address);
		bookingPage.deliveryValidatebtn();
		softAssert.assertEquals(bookingPage.successMsgonAddress(), true);
		Pass_Message("Delivery Address is Validated");
	}

	public void setCollectionAddress(String country, String postal, String town, String customerName, String address) {
		SoftAssert softAssert = new SoftAssert();
		BookingPage bookingPage = new BookingPage(driver);
		bookingPage.setCollectionAddress(country);
		bookingPage.setCollectionPostal(postal);
		bookingPage.setCollectionTown(town);
		bookingPage.setCollectionCustomerName(customerName);
		bookingPage.setCollectionAddress(address);
		bookingPage.clickCollectionValidatebtn();
		softAssert.assertEquals(bookingPage.successMsgonAddress(), true);
		Pass_Message("Collection Address is Validated");
	}

	public void setCallerDetails(String name, String contact, String emailAddress) {
		BookingPage bookingPage = new BookingPage(driver);
		bookingPage.setCallerName(name);
		bookingPage.setCallerPhone(contact);
		bookingPage.setCallerEmail(emailAddress);
		Pass_Message("Collection details entered");
	}

	public void setCollectionContactDetails(String name, String contact, String emailAddress) {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			bookingpage.setContactName(name);
			bookingpage.setContactPhone(contact);
			bookingpage.setContactEmail(emailAddress);
			bookingpage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contact details are not updated");

		}
	}

	public void ci_login(String env) {
		try {
			System.out.println("Environment: " + env);
			if (env.equals("UAT")) {
				URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY", CCD_Quote.Key_Array[9]);
				Username = Database_Connection.retrieveTestData("ACM_USERID", "GLOBALDATA", "KEY",
						CCD_Quote.Key_Array[9]);
				Password = Database_Connection.retrieveTestData("ACM_PASSWORD", "GLOBALDATA", "KEY",
						CCD_Quote.Key_Array[9]);
			} else {
				URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY", CCD_Booking.Key_Array[0]);
				Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY", CCD_CI.Key_Array[5]);
				Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY", CCD_CI.Key_Array[5]);
			}
			System.out.println("URL: " + URL);
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			login = new LoginPage(driver);

			// Assert.assertEquals(URL, driver.getCurrentUrl(), "Actual page URL is not same
			// as expected");
			login.setUsername(Username);
			login.setPassword(Password);
			login.clickLoginBtn();
			// login.clickInstructionConfirmButton();
			// login.clickFinishButton();
			driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.MINUTES);
			HomePage homepage = new HomePage(driver);
			String actualTitle = homepage.getTitle();
			Assert.assertEquals(actualTitle, "CCD Customer Care Desktop");
			CCD_Connectivity acm_connectivity = new CCD_Connectivity();
			acm_connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void login_SLUser() {
		try {
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY", CCD_CI.Key_Array[5]);
			driver.get(URL);
			login = new LoginPage(driver);
			driver.manage().window().maximize();
			Assert.assertEquals(URL, driver.getCurrentUrl(), "Actual page URL is not same as expected");
			login.setUsername(Username);
			login.setPassword(Password);
			login.clickLoginBtn();
			HomePage homepage = new HomePage(driver);
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle, "CCD Home Page not Opened");
			CCD_Connectivity acm_connectivity = new CCD_Connectivity();
			acm_connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void login_DKUser() {
		try {
			LoginPage loginPage = new LoginPage(driver);
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);

			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY",
					CCD_ProductOptions.Key_Array[2]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY",
					CCD_ProductOptions.Key_Array[2]);
			driver.get(URL);
			driver.manage().window().maximize();
			Assert.assertEquals(URL, driver.getCurrentUrl(), "Actual page URL is not same as expected");
			loginPage.setUsername(Username);
			loginPage.setPassword(Password);
			loginPage.clickLoginBtn();
			HomePage homepage = new HomePage(driver);
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Frontline CSR Login to salesforce Failed.");
		}
	}

	public void login_PTUser() {
		try {
			String URL = Database_Connection.retrieveTestData("ACM_LINK", "GLOBALDATA", "KEY",
					CCD_CMOD_SmokeTest.Key_Array[0]);
			String Username = Database_Connection.retrieveTestData("USER_ID", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Password = Database_Connection.retrieveTestData("PASSWORD", "ACM", "KEY", CCD_CI.Key_Array[5]);
			driver.get(URL);
			driver.manage().window().maximize();
			Assert.assertEquals(URL, driver.getCurrentUrl(), "Actual page URL is not same as expected");
			login = new LoginPage(driver);
			login.setUsername(Username);
			login.setPassword(Password);
			login.clickLoginBtn();
			HomePage homepage = new HomePage(driver);
			String actualTitle = homepage.getTitle();
			String expectedTitle = "CCD Customer Care Desktop";
			Assert.assertEquals(actualTitle, expectedTitle);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Login to salesforce Failed.");
		}
	}

	public void returnToPage() {
		HomePage homepage = new HomePage(driver);
		try {
			if (homepage.verifyReturntoPage()) {
				homepage.clickReturnToPage();
			}
		} catch (Exception e) {
			driver.navigate().refresh();
			Pass_Message("Session not Ended");
		}
	}

	public void ci_GS_FirstName() {
		try {
			ACM_Connectivity.CloseTab();
			String firstName = Database_Connection.retrieveTestData("FIRST_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String fullName = Database_Connection.retrieveTestData("CUST_FULLNAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(firstName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			if (globalSearch.searchResultCustAccVisible()) {
				List<WebElement> rows = globalSearch.globalSearchcustAccTable().findElements(By.xpath("tbody/tr"));
				boolean result = true;
				for (int i = 0; i < rows.size(); i++) {
					String firstNameVisible = rows.get(i).findElement(By.xpath("th//a")).getText().toString();
					String[] firstname = firstNameVisible.trim().split(" ");
					if (fullName.contains(firstname[0])) {
						result = true;
					} else {
						result = false;
						break;
					}
				}
				if (result) {
					Pass_Message("Customer with Last name as " + firstName + " displayed successfully");
				} else {
					Fail_Message("Customer with Last name as " + firstName + " NOT displayed");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer search with first name is failed");
			ACM_Connectivity.CloseTab();
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_GS_LastName() {
		try {

			String lastName = Database_Connection.retrieveTestData("LAST_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String fullName = Database_Connection.retrieveTestData("CUST_FULLNAME", "ACM", "KEY", CCD_CI.Key_Array[5]);

			setValueInGlobalSearch(lastName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			if (globalSearch.searchResultCustAccVisible()) {
				List<WebElement> objRow = globalSearch.globalSearchcustAccTable().findElements(By.xpath("tbody/tr"));
				System.out.println(objRow.get(0).getText());
				int row_count = objRow.size();
				boolean result = true;
				for (int i = 0; i < row_count; i++) {
					String lastNameVisible = objRow.get(i).findElement(By.xpath("th//a")).getText().toString();
					String[] lastname = lastNameVisible.trim().split(" ");
					if (fullName.contains(lastname[1])) {
						result = true;
					} else {
						result = false;
						break;
					}
				}
				if (result) {
					Pass_Message("Customer with Last name as " + lastName + " displayed successfully");
				} else {
					Fail_Message("Customer with Last name as " + lastName + " NOT displayed");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer search with last name is failed");
			ACM_Connectivity.CloseTab();
		}
		ACM_Connectivity.CloseTab();
	}

	public void setValueInGlobalSearch(String str) {
		homePage = new HomePage(driver);
		homePage.searchBooking(str);
	}

	public void CI_GS_CustAcct() {
		try {
			String fullName = Database_Connection.retrieveTestData("CUST_FULLNAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(fullName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.firstRecordInCustAccTableClk();
			CustomerAccountPage custAccPage = new CustomerAccountPage(driver);
			List<WebElement> list = custAccPage.customerAccDetailsFields();

			String InactiveFlag = list.get(1).getText();
			if (InactiveFlag.isEmpty()) {
				Fail_Message("In active Flag not displayed");
			} else {
				Pass_Message("In active Flag is displayed");
			}
			String acct_name = list.get(2).getText();
			if (acct_name.isEmpty()) {
				Fail_Message("acct_name field is not displayed");
			} else {
				Pass_Message("acct_name field is displayed");
			}
			String ded_acct = list.get(3).getText();
			if (ded_acct.isEmpty()) {
				Fail_Message("ded_acct field is not displayed");
			} else {
				Pass_Message("ded_acct field is displayed");
			}
			String acct_sou_sys = list.get(4).getText();
			if (acct_sou_sys.isEmpty()) {
				Fail_Message("acct_sou_sys field is not displayed");
			} else {
				Pass_Message("acct_sou_sys field is displayed");
			}
			String ded_team = list.get(5).getText();
			if (ded_team.isEmpty()) {
				Fail_Message("ded_team field is not displayed");
			} else {
				Pass_Message("ded_team field is displayed");
			}
			String acct_num = list.get(6).getText();
			if (acct_num.isEmpty()) {
				Fail_Message("acct_num field is not displayed");
			} else {
				Pass_Message("acct_num field is displayed");
			}
			String emp_userid = list.get(7).getText();
			if (emp_userid.isEmpty()) {
				Fail_Message("emp_userid field is not displayed");
			} else {
				Pass_Message("emp_userid field is displayed");
			}
			String acct_int_id = list.get(8).getText();
			if (acct_int_id.isEmpty()) {
				Fail_Message("acct_int_id field is not displayed");
			} else {
				Pass_Message("acct_int_id field is displayed");
			}
			String cust_status = list.get(9).getText();
			if (cust_status.isEmpty()) {
				Fail_Message("cust_status field is not displayed");
			} else {
				Pass_Message("cust_status field is displayed");
			}
			String cust_add = list.get(10).getText();
			if (cust_add.isEmpty()) {
				Fail_Message("cust_add field is not displayed");
			} else {
				Pass_Message("cust_add field is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer account details are not displayed correctly");
		}
		ACM_Connectivity.CloseTab();
	}

	public void CI_GS_Header() {
		try {
			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);

			setValueInGlobalSearch(acctName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.firstRecordInCustAccTableClk();

			CustomerAccountPage customerAccPage = new CustomerAccountPage(driver);
			String Acct_Name = customerAccPage.accountNameHeaderField().getText();
			if (Acct_Name.isEmpty()) {
				Fail_Message("Account Name field is not displayed");
			} else {
				Pass_Message("Account Name field is displayed");
			}

			String Ded_acct = customerAccPage.dedicatedAccHeaderField().getText();
			if (Ded_acct.isEmpty()) {
				Fail_Message("Dedicated Account field is not displayed");
			} else {
				Pass_Message("Dedicated Account field is displayed");
			}

			String Ded_team = customerAccPage.dedicatedTeamHeaderField().getText();
			if (Ded_team.isEmpty()) {
				Fail_Message("Dedicated Team field is not displayed");
			} else {
				Pass_Message("Dedicated Team field is displayed");
			}

			String Migration_date = customerAccPage.migrationDateHeaderField().getText();
			if (Migration_date.isEmpty()) {
				Fail_Message("Migration Date field is not displayed");
			} else {
				Pass_Message("Migration Date field is displayed");
			}
			String Overlap_date = customerAccPage.overlapDateHeaderField().getText();
			if (Overlap_date.isEmpty()) {
				Fail_Message("Overlap Date field is not displayed");
			} else {
				Pass_Message("Overlap Date field is displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Header through Global Search is not displayed correctly");
		}
		ACM_Connectivity.CloseTab();
	}

	public void CI_GS_VerifyBtns() {
		try {
			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);

			setValueInGlobalSearch(acctName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.firstRecordInCustAccTableClk();
			CustomerAccountPage custAccPage = new CustomerAccountPage(driver);
			WebElement newBooking = custAccPage.newBookingBtn();
			if (newBooking.isEnabled()) {
				Pass_Message("New Booking button is enabled when customer is searched from Global search");
			} else {
				Fail_Message("New Booking button is not enabled");
			}

			WebElement newQuote = custAccPage.newQuoteBtn();
			if (newQuote.isEnabled()) {
				Pass_Message("New Quote button is enabled when customer is searched from Global search");
			} else {
				Fail_Message("New Quote button is not enabled");
			}

			WebElement newContact = custAccPage.newContactBtn();
			if (newContact.isEnabled()) {
				Pass_Message("New Contact button is enabled when customer is searched from Global search");
			} else {
				Fail_Message("New Contact button is not enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message(
					"Buttons on Customer Account Details when searched through Global Search are not displayed correctly");
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_GS_VerifyContactDtls() {
		try {
			String fullName = Database_Connection.retrieveTestData("CUST_FULLNAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(fullName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.firstRecordInCustAccTableClk();
			CustomerAccountPage custAccPage = new CustomerAccountPage(driver);

			String phNum = custAccPage.phoneNumberField().getText();
			if (phNum.isEmpty()) {
				Fail_Message("Phone Num in search result is not displayed");
			} else {
				Pass_Message("Phone Num " + phNum + " in search result is displayed");
			}

			String email = custAccPage.emailField().getText();
			if (email.isEmpty()) {
				Fail_Message("Email in search result is not displayed");
			} else {
				Pass_Message("Email " + email + " in search result is displayed");
			}

			String extn = custAccPage.extensionField().getText();
			if (extn.isEmpty()) {
				Fail_Message("Extension in search result is not displayed");
			} else {
				Pass_Message("Extension " + extn + " is displayed on Contact Details page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer contact details are not displayed correctly when searched from Global Search");
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_GS_AcctNum() {
		try {
			String country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String acctNum = Database_Connection.retrieveTestData("ACCTNUM", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(acctNum);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			if (globalSearch.searchResultCustAccVisible()) {
				List<WebElement> objRow = globalSearch.globalSearchcustAccTable().findElements(By.xpath("tbody/tr"));
				int row_count = objRow.size();
				boolean result = true;
				for (int i = 0; i < row_count; i++) {
					String accountNum = objRow.get(i).findElement(By.xpath("td[3]")).getText().toString().trim();
					String countryfield = objRow.get(i).findElement(By.xpath("td[2]")).getText().toString().trim();
					if ((accountNum.contains(acctNum)) && (countryfield.contains(country))) {
						result = true;
					} else {
						result = false;
						break;
					}
				}
				if (result) {
					Pass_Message("Customer with account number as " + acctNum + " displayed successfully");
				} else {
					Fail_Message("Customer with account number as " + acctNum + " NOT displayed");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account not displayed");
		}
		ACM_Connectivity.CloseTab();
	}

	public void CI_GS_AcctNum_Details() {
		try {
			Country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String AcctNum = Database_Connection.retrieveTestData("ACCTNUM", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String ADD_LINE1 = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String City = Database_Connection.retrieveTestData("CITY", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String POST_CODE = Database_Connection.retrieveTestData("POST_CODE", "ACM", "KEY", CCD_CI.Key_Array[5]);

			setValueInGlobalSearch(AcctNum);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.firstRecordInCustAccTableClk();
			uiTestHelper.propagateException();
			CustomerAccountPage custAccPage = new CustomerAccountPage(driver);

			String CustName = custAccPage.customername().getText().trim();
			if (CustName.isEmpty()) {
				Fail_Message("Customer Name field is not displayed");
			} else {
				Pass_Message("Customer Name field is displayed");
			}

			String ActNum_app = custAccPage.accountNum().getText().trim();
			if (ActNum_app.equals(AcctNum)) {
				Pass_Message("Account number displayed is correct");
			} else {
				Fail_Message("Account number displayed is not correct");
			}

			String Address_app = custAccPage.addressDetail().getText();
			if (Address_app.contains(ADD_LINE1))
				;
			{
				Pass_Message("Add line 1 '" + ADD_LINE1 + "' is displayed");
			}
			if (Address_app.contains(City))
				;
			{
				Pass_Message("City '" + City + "' is displayed");
			}
			if (Address_app.contains(POST_CODE))
				;
			{
				Pass_Message("POST_CODE '" + POST_CODE + "' is displayed");
			}
			if (Address_app.contains(Country))
				;
			{
				Pass_Message("Country '" + Country + "' is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account not displayed");
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_GS_VerifyStream() {
		try {
			Country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String contName = Database_Connection.retrieveTestData("CONT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			// System.out.println(contName);
			setValueInGlobalSearch(contName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			// globalSearch.firstRecordInCustAccTableClk();

			// Need to discuss with team *****************************
			driver.findElement(By
					.xpath("//a[text()='Contacts']/following::table[1]/tbody//a[contains(text(),'" + contName + "')]"))
					.click();

			String CustServ = globalSearch.getStream();
			System.out.println(CustServ);
			if (CustServ.equals("Customer Service")) {
				Pass_Message("Customer Srvice Stream displayed correctly in contact section");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Stream displayed is not correct");
			driver.findElement(By.xpath("//input[contains(@title,'Search')]")).clear();
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_LeftSearch_AcctName() {
		try {
			// String acctName=Database_Connection.retrieveTestData("FIRST_NAME", "ACM",
			// "KEY", ACM_CI.Key_Array[5]);
			// String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
			// "KEY", ACM_CI.Key_Array[5]);
			String acctName = "Tatasky AccountName";
			Country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String acctNum = Database_Connection.retrieveTestData("ACCTNUM", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(acctNum);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.custAccSearchClick();
			uiTestHelper.propagateException();
			if (globalSearch.refineByPresent()) {
				globalSearch.accNameRefineBy().sendKeys(acctName);
				uiTestHelper.propagateException();
				globalSearch.clickFilter();
				String serachResult = globalSearch.searchRefineBy().getText();
				if (serachResult.equalsIgnoreCase(AcctName))
					;
				{
					Pass_Message(
							"Search by Account Name filter is working as expected and search result list with account name as '"
									+ AcctName + "' is displayed successfully");
				}
				globalSearch.searchbox().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Search is not completed by Account Name filter criteria");
			driver.findElement(By.xpath("//input[contains(@title,'Search')]")).clear();
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_LeftSearch_SalesCountry() {
		try {
			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(acctName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.custAccSearchClick();
			uiTestHelper.propagateException();
			if (globalSearch.refineByPresent()) {
				globalSearch.salesCountryRefine().sendKeys(country);
				uiTestHelper.propagateException();
				globalSearch.clickFilter();
				String serachResult = globalSearch.searchCountryRefine().getText();
				if (serachResult.equalsIgnoreCase(country))
					;
				{
					Pass_Message(
							"Search by Sales Country filter is working as expected and search result list with Sales Country as '"
									+ country + "' is displayed successfully");
				}
				globalSearch.searchbox().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Search is not completed by Sales Country filter criteria");
			driver.findElement(By.xpath("//input[contains(@title,'Search')]")).clear();
		}
		ACM_Connectivity.CloseTab();
	}

	public void ci_LeftSearch_AcctNum() {
		try {
			String acctName = Database_Connection.retrieveTestData("FIRST_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			// String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
			// "KEY", ACM_CI.Key_Array[5]);
			Country = Database_Connection.retrieveTestData("CUST_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String acctNum = Database_Connection.retrieveTestData("ACCTNUM", "ACM", "KEY", CCD_CI.Key_Array[5]);
			setValueInGlobalSearch(acctName);
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.custAccSearchClick();
			uiTestHelper.propagateException();
			if (globalSearch.refineByPresent()) {
				globalSearch.accNumRefine().sendKeys(acctNum);
				uiTestHelper.propagateException();
				globalSearch.clickFilter();
				String serachResult = globalSearch.searchAccNumRefine().getText();
				if (serachResult.equalsIgnoreCase(acctNum))
					;
				{
					Pass_Message(
							"Search by Account Number filter is working as expected and search result list with Account Number as '"
									+ acctNum + "' is displayed successfully");
				}
				globalSearch.searchbox().clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Search is not completed by Account Number filter criteria");
			driver.close();
		}
		ACM_Connectivity.CloseTab();
	}

	public void BK_US602384_Verify_CallerInfo_Sen_Rec_Btns() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			List<WebElement> list = bkpage.getCallerInfoButtons();
			if (list.get(0).isEnabled()) {
				Pass_Message("Sender button is enabled");
			} else {
				Fail_Message("Sender button is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Sender button on Caller Information section is not working as expected");
		}
	}

	public void BK_US611155_Verify_BAD_Debtor_Status() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = "PORTUGAL PMC";
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bkpage.callerInfo_SISP();
			if (bkpage.verifyBadDebtorStatus() == true) {
				Pass_Message("Pop window to show Bad Debtor status is displayed");
			} else {
				Fail_Message("Pop window to show Bad Debtor status is not displayed");
			}
			BK_US611155_Verify_BAD_Debtor_Confirm_Booking();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Bad Debtor Status is not verified");
		}
	}

	public void BK_US602387_Verify_PayTerms_Sen_Rec_Btns() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//legend[contains(text(),'Payment Terms')]")));
			if (bkpage.verifyPaymentTerms() == true) {
				Pass_Message("Payment Terms section is displayed");
			} else {
				Fail_Message("Payment Terms section is not displayed");
			}

			List<WebElement> list = bkpage.getCallerInfoButtons();
			if (list.get(2).isEnabled()) {
				Pass_Message("Sender button is enabled");
			} else {
				Fail_Message("Sender button is disabled");
			}
			if (list.get(3).isEnabled()) {
				Pass_Message("Receiver button is enabled");
			} else {
				Fail_Message("Receiver button is disabled");
			}
			list.get(2).click();
			uiTestHelper.scrolldown("300");
			if (bkpage.verifyBillingAccNumber() == true) {
				Pass_Message("Bill number is displayed successfully");
			} else {
				Fail_Message("Bill number is not displayed");
			}
			if (bkpage.verifyCollectionCustomerName() == true) {
				Pass_Message("collection CustomerName as is displayed successfully");
			} else {
				Fail_Message("collection CustomerName is not displayed");
			}
			if (bkpage.verifyCollectionAddress()) {
				Pass_Message("collection Address as is displayed");
			} else {
				Fail_Message("collection Address is not displayed");
			}
			if (bkpage.verifyCollectionTown() == true) {
				Pass_Message("collection Town as is displayed");
			} else {
				Fail_Message("collection Town is not displayed");
			}
			if (bkpage.verifyCollectionPostal() == true) {
				Pass_Message("collection PostCode as is displayed");
			} else {
				Fail_Message("collection PostCode is not displayed");
			}
			if (bkpage.verifyCollectionCountry() == true) {
				Pass_Message("collection Country as is displayed");
			} else {
				Fail_Message("collection Country is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Sender button on Caller Information section is not working as expected");
		}
	}

	public void BK_US603130_ValidateBtn_CollAddress() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//legend[contains(text(),'Payment Terms')]")));
			if (bkpage.verifyPaymentTerms() == true) {
				Pass_Message("Payment Terms section is displayed");
			} else {
				Fail_Message("Payment Terms section is not displayed");
			}

			List<WebElement> list = bkpage.getCallerInfoButtons();
			list.get(0).click();
			list.get(2).click();
			// bkpage.confirmCustomerInstruction();
			if (list.get(3).isSelected()) {
				Fail_Message("Receiver button is not greyed out and is selected");
			} else {
				Pass_Message("Receiver button is greyed out and is not selected");
			}
			uiTestHelper.scrolldown("300");
			BK_Validate_CollAdd();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Sender button on Caller Information section is not working as expected");
		}
	}

	public void BK_US611155_Verify_BAD_Debtor_Confirm_Booking() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			bkpage.clickConfirm();
			Pass_Message("On clicking on 'Confirm' button pop up is dissappeared and user is able to proceed");
			uiTestHelper.scrolldown("300");
			if (bkpage.successMsgonAddress()) {
				Pass_Message("Collection address validated successfully");
				if (bkpage.verifyCollectionValidateBtn() == true) {
					Fail_Message("Validate button is still Enabled");
				} else {
					Pass_Message("Collection Address Validated and User able to proceed the Booking");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking is not confirmed with Bad Debtor Status");
		}
	}

	public void BK_US603132_Update_CollAdd_ValidateBtn() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//legend[contains(text(),'Payment Terms')]")));
			if (bkpage.verifyPaymentTerms() == true) {
				Pass_Message("Payment Terms section is displayed");
			} else {
				Fail_Message("Payment Terms section is not displayed");
			}

			List<WebElement> list = bkpage.getCallerInfoButtons();
			list.get(0).click();
			list.get(2).click();
			// bkpage.confirmCustomerInstruction();
			bkpage.deleteCollectionPostal();

			if (bkpage.verifyCollectionValidateBtn() == true) {
				Pass_Message("Validate button is Enabled");
			} else {
				Fail_Message("Validate button is disabled");
			}
			bkpage.clickCollectionValidatebtn();
			if (bkpage.errorMsg() == true) {
				Pass_Message("Collection address is not validated successfully");
			}
			bkpage.setCollectionPostalWithOutClear("1");
			bkpage.clickCollectionValidatebtn();
			if (bkpage.successMsgonAddress() == true) {
				Pass_Message("Collection address validated successfully");
			}
			if (bkpage.verifyCollectionValidateBtn() == true) {
				Fail_Message("Validate button is still Enabled");
			} else {
				Pass_Message("Validate button is disabled");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Collection address can not be validated");
		}
	}

	public void BK_Validate_CollAdd() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			uiTestHelper.scrolldown("300");
			bkpage.clickCollectionValidatebtn();
			if (bkpage.successMsgonAddress() == true) {
				Pass_Message("Collection address validated successfully");
				if (bkpage.verifyCollectionValidateBtn() == true) {
					Fail_Message("Validate button is still Enabled");
				} else {
					Pass_Message("Validate button is disabled");
				}
			}
			if (bkpage.verifyBillingAccNumber() == true) {
				Pass_Message("Bill number is displayed successfully");
			} else {
				Fail_Message("Bill number is not displayed");
			}
			if (bkpage.verifyCollectionCustomerName() == true) {
				Pass_Message("collection CustomerName as is displayed successfully");
			} else {
				Fail_Message("collection CustomerName is not displayed");
			}
			if (bkpage.verifyCollectionAddress()) {
				Pass_Message("collection Address as is displayed");
			} else {
				Fail_Message("collection Address is not displayed");
			}
			if (bkpage.verifyCollectionTown() == true) {
				Pass_Message("collection Town as is displayed");
			} else {
				Fail_Message("collection Town is not displayed");
			}
			if (bkpage.verifyCollectionPostal() == true) {
				Pass_Message("collection PostCode as is displayed");
			} else {
				Fail_Message("collection PostCode is not displayed");
			}
			if (bkpage.verifyCollectionCountry() == true) {
				Pass_Message("collection Country as is displayed");
			} else {
				Fail_Message("collection Country is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Collection Address can not be validated successfully");
		}
	}

	public void BK_Validate_DelAdd() {
		try {
			BookingPage bookingpage = new BookingPage(driver);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)");
			bookingpage.setDeliveryCountry("Portugal");
			bookingpage.setDeliveryPostal("1000-001");
			bookingpage.setDeliveryTown("Lisboa");
			bookingpage.setDelCustomerName("Rohit Soni");
			bookingpage.setDeliveryAddress("202 River Dr");
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Delivery address not Validated");
				Pass_Message("Delivery Address is Validated");
			}

			if (bookingpage.verifyDeliveryValidateBtn()) {
				Fail_Message("Validate button is still Enabled");
			} else {
				Pass_Message("Validate button is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Delivery Address can not be validated successfully");
		}
	}

	public void BK_US603134_DelAdd_UK_ValidateBtn() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			List<WebElement> sisp = bkpage.getCallerInfoButtons();
			sisp.get(0).click();
			sisp.get(2).click();
			// bkpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			if (bkpage.verifyDeliveryValidateBtn() == true) {
				Fail_Message("Validate button is enabled and delivery address have not been validated");
			} else {
				Pass_Message("Validate button is disabled and Delivery address has been validated");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Delivery Address validation negative test case could not be executed as expected");
		}
	}

	public void BK_US603134_DelAdd_NonUK_ValidateBtn() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			List<WebElement> sisp = bkpage.getCallerInfoButtons();
			sisp.get(0).click();
			sisp.get(2).click();
			// bkpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			setDeliveryAddress("Portugal", "1000-001", "Lisboa", "Akash Batra", "21 River Woods");
			if (bkpage.verifyDeliveryValidateBtn() == true) {
				Fail_Message("Validate button is still Enabled");
			} else {
				Pass_Message("Validate button is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Delivery Address can not be validated successfully");
		}
	}

	public void BK_US603134_Update_CollectionDetails() {
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			uiTestHelper.scrolldown("700");
			setCallerDetails("Arjun Rampal", "+91 4545898985", "Arjun_Rampal@gmail.com");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Collection Details could not be updated successfully");
		}
	}

	public void BK_US603138_Update_GoodsDesc() {
		try {
			BK_Nav_GoodsInformation_page();
			setGoodsInformation("Document", "Cloths", "18", "121212");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("CSR is unable to update Goods Information section");
		}
	}

	public void BK_US603140_GoodsInfo_DefaultCCY() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			driver.findElement(By.xpath("//input[@name='userCurrency']"));
			String ccy1 = goodsPage.getDefaultCurrency();
			if (ccy1.equals("EUR")) {
				Pass_Message("Default Currency as '" + ccy1 + "' is popuated");
			} else {
				Fail_Message("Default Currency as '" + ccy1 + "' is not popuated");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Default CCY has not been validated");
		}
	}

	public void BK_US603140_GoodsInfo_DifferentCCY() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.setCurrency("CUP");
			Pass_Message("Different CCY have been selected successfully");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("User is not able to select different CCY");
		}
	}

	public void BK_US603140_GoodsInfo_BlankAmt() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Document", "Cloths", "18", "55555");
			goodsPage.clickGoodsInfoNextBtn();
			Pass_Message("User is able to enter other details by keeping Amount field blank");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Blank amount field can not be validated as expected");
		}
	}

	public void BK_US603142_GoodsInfo_DangGoods_NO() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			if (goodsPage.verifyDangerousGoodsNo() == true) {
				Pass_Message("Dangerous Good indicator is set to NO");
			} else {
				Fail_Message("Dangerous Good indicator is set to Yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Dangerous Good indicator has not been validated");
		}
	}

	public void BK_US603156_GoodsInfo_Contents_Docs() {
		try {

			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.clickGoodsDoccontent();
			String content = goodsPage.getGoodsContent();
			if (content.equals("Documents")) {
				Pass_Message("Contents as 'Document' have been selected");
			} else {
				Fail_Message("Contents as 'Document' have not been selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("DContents as 'Document' can not be selected");
		}

	}

	public void BK_US603156_GoodsInfo_Contents_NonDocs() {
		try {

			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.clickGoodsNonDoccontent();
			String content = goodsPage.getGoodsContent();
			if (content.equalsIgnoreCase("Non-Documents")) {
				Pass_Message("Contents as 'Non-Documents' have been selected");
			} else {
				Fail_Message("Contents as 'Non-Documents' have not been selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contents as 'Non-Document' can not be selected");
		}

	}

	public void BK_US603156_GoodsInfo_Contents_Mixed() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.clickGoodsMixedcontent();
			String content = goodsPage.getGoodsContent();
			if (content.equals("Mixed")) {
				Pass_Message("Contents as 'Mixed' have been selected");
			} else {
				Fail_Message("Contents as 'Mixed have not been selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contents as 'Mixed' can not be selected");
		}

	}

	public void BK_603158_Book_MultipleItems() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[2]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[2]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[2]", "21");
			hashmap.put("(//label[text()='Weight']/following::input[1])[2]", "28");
			for (int i = 0; i <= 1 - i; i++) {
				coninfopage.addItem();
			}
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			coninfopage.clickTypeBox();
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[2]")).click();
			uiTestHelper.waitForObject(By.xpath("(//lightning-base-combobox-item[@data-value='pallet'])[2]")).click();
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Multiple items have been added successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Multiple items has not been added succesfully");
		}

	}

	public void BK_Type_Envelop_Dimns_Disappear() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			coninfopage.clickTypeEnvelope();
			if (coninfopage.verifyTypeEnvelope() == true) {
				Pass_Message("On selecting type as 'Enevelope' Dimensions sections is disappeared");
			} else {
				Fail_Message("Dimensions sections is still displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Fields in content type Evelope could not be validated successfully");
		}

	}

	public void BK_Select_Type_Box() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			coninfopage.clickTypeBox();
			if (coninfopage.verifyTypeOther() == true) {
				Pass_Message("Content Type as Box selected successfully");
			} else {
				Fail_Message("Content Type as Box has not been selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Content Type as Box has not been selected");
		}

	}

	public void BK_Select_Type_Pallet() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			coninfopage.clickTypePallet();
			if (coninfopage.verifyTypeOther() == true) {
				Pass_Message("Content Type as Pallet selected successfully");
			} else {
				Fail_Message("Content Type as Pallet has not been selected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exeception: Content Type as Pallet has not been selected");
		}

	}

	// navigation
	public void BK_Nav_CustomerAccount_page() {
		try {
			CustomerAccountPage customerAccountPage = new CustomerAccountPage(driver);
			HomePage homepage = new HomePage(driver);
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			homepage.searchBooking(AcctName);
			customerAccountPage.selectCustomerAccounts(AcctName);
			Assert.assertEquals(customerAccountPage.verifyCustomerAccountPage(), true,
					"Customer account Page not Displayed");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Cuistomr Account page failed");
		}

	}

	public void BK_Nav_ConsignmentInformation_page() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	public void BK_Nav_GoodsInformation_page() {
		BK_BookingPage_SISP();
	}

	public void BK_Nav_AdditionalInformation_page() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "3");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "120");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "120");
			hashmap.put("(//label[text()='Weight']/following::input[1])[1]", "90");
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			coninfopage.clickTypeBox();
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (

		Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");
		}
	}

	public void BK_Book_MultipleItems_Overlimits() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			for (int i = 0; i <= 1 - i; i++) {
				coninfopage.addItem();
			}
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			driver.findElement(By.xpath("(//label[text()='Length']/following::input[1])[2]")).sendKeys("20", Keys.TAB);

			driver.findElement(By.xpath("(//label[text()='Width']/following::input[1])[2]")).sendKeys("90", Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Height']/following::input[1])[2]")).sendKeys("21", Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[2]")).sendKeys("71",
					Keys.TAB);
			if (coninfopage.verifyWeightExceeds() == true) {
				Pass_Message("Error for threshold value for Weight is displayed");
			} else {
				Fail_Message("Error for threshold value for Weight is not displayed");
			}
			if (coninfopage.verifyTotalWeightExceeds() == true) {
				Pass_Message(
						"Error message as 'You can only ship boxes up to 70 kg in weight. Change the package type to 'Pallet' to continue' is displayed successfully");
			} else {
				Fail_Message(
						"Error message as 'You can only ship boxes up to 70 kg in weight. Change the package type to 'Pallet' to continue' is displayed successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Threshhold value for Multiple items has not been validated succesfully");
		}

	}

	public void BK_603160_Book_10Items_DiffWightsAndDims() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[2]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[2]", "20");
			hashmap.put("(//label[text()='Height']/following::input[1])[2]", "20");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "20");
			hashmap.put("(//input[@name='quantity'])[3]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[3]", "3");
			hashmap.put("(//label[text()='Width']/following::input[1])[3]", "50");
			hashmap.put("(//label[text()='Height']/following::input[1])[3]", "50");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[3]", "50");
			hashmap.put("(//input[@name='quantity'])[4]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[4]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[4]", "40");
			hashmap.put("(//label[text()='Height']/following::input[1])[4]", "40");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[4]", "28");
			hashmap.put("(//input[@name='quantity'])[5]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[5]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[5]", "60");
			hashmap.put("(//label[text()='Height']/following::input[1])[5]", "60");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[5]", "28");
			hashmap.put("(//input[@name='quantity'])[6]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[6]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[6]", "25");
			hashmap.put("(//label[text()='Height']/following::input[1])[6]", "25");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[6]", "28");
			hashmap.put("(//input[@name='quantity'])[7]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[7]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[7]", "6");
			hashmap.put("(//label[text()='Height']/following::input[1])[7]", "35");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[7]", "40");
			hashmap.put("(//input[@name='quantity'])[8]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[8]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[8]", "45");
			hashmap.put("(//label[text()='Height']/following::input[1])[8]", "65");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[8]", "20");
			hashmap.put("(//input[@name='quantity'])[9]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[9]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[9]", "20");
			hashmap.put("(//label[text()='Height']/following::input[1])[9]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[9]", "18");
			for (int i = 0; i <= 8; i++) {
				coninfopage.addItem();
				uiTestHelper.scrolldown("300");
			}
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
				uiTestHelper.scrolldown("300");
			}
			if (coninfopage.verifyAddItemBtn() == true) {
				Fail_Message("'Add another item' button is still enabled");
			} else {
				Pass_Message("As expecetd 'Add another item' button is disabled");
			}
			uiTestHelper.scrollUp("-700");
			if (coninfopage.verifyErrorMsgAdding10Items() == true) {
				Pass_Message(
						"After adding 10 items with different weights and Dimensions an error msg :'Maximum number of package lines reached' is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Multiple items has not been added succesfully");
		}

	}

	public void BK_603159_Book_10Items_SameWeightsAndDims() {
		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			int i, j;
			for (j = 1; j < 10; j++) {
				for (i = 1; i < 51; i++) {
					System.out.println(j);
					List<WebElement> val = driver.findElements(By.xpath("//input[@inputmode='decimal']"));
					System.out.println(i);
					val.get(i).sendKeys("1");
					i = i + 1;
					System.out.println(i);
					val.get(i).sendKeys("2");
					i = i + 1;
					System.out.println(i);
					val.get(i).sendKeys("12");
					i = i + 1;
					System.out.println(i);
					val.get(i).sendKeys("21");
					i = i + 1;
					System.out.println(i);
					val.get(i).sendKeys("28");
					System.out.println(i);
					if (j < 10) {
						coninfopage.addItem();
						j++;
					} else {
						j++;
					}
					uiTestHelper.scrolldown("300");
				}
				if (coninfopage.verifyAddItemBtn() == true) {
					Fail_Message("Add another item button is still enabled");
				} else {
					Pass_Message("Add another item button is disabled");
				}
			}
			uiTestHelper.scrollUp("-700");
			if (coninfopage.verifyErrorMsgAdding10Items() == true) {
				Pass_Message(
						"After adding 10 items an error msg :'Maximum number of package lines reached' is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("10 items has not been added succesfully");
		}

	}

	public void BK_603159_Book_10Items_Reduce5Items() {
		try {
			BK_603159_Book_10Items_SameWeightsAndDims();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			uiTestHelper.scrolldown("300");
			List<WebElement> deleteBtn = coninfopage.getDeleteButtons();
			deleteBtn.get(0).click();
			deleteBtn.get(1).click();
			deleteBtn.get(2).click();
			deleteBtn.get(3).click();
			deleteBtn.get(4).click();
			Pass_Message("5 Items have been deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("5 Items can not be reduced");
		}

	}

	public void BK_603160_Book_100Items_Error() {
		try {
			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "100");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			if (coninfopage.verifyQuantityExceeds() == true) {
				Pass_Message(
						"Correct error message 'The maximum number of packages in a single shipment is 99' is displayed below shipemtn box");
			} else {
				Fail_Message("Correct error message is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("99 Items can not be added");
		}

	}

	public void BK_603160_Book_0Items_Error() {
		try {
			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			driver.findElement(By.xpath("(//input[@name='quantity'])[1]")).sendKeys("0", Keys.TAB);
			;
			if (coninfopage.verifyQuantityMinimum() == true) {
				Pass_Message(
						"Correct error message 'The minimum number of packages in a single shipment is 1' is displayed below shipemtn box");
			} else {
				Fail_Message("Correct error message is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("5 Items can not be reduced");
		}
	}

	public void BK_605035_Update_CustRefNum() {
		try {
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.setCustomerReference("33333");
			Pass_Message("Customer Reference number updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer Reference number is not be updated");
		}
	}

	public void BK_605036_Update_CustRefNum_AlphaNum() {
		try {
			// closetab();
			BK_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.setCustomerReference("Alpha333");
			Pass_Message("Alpha numeric Customer Reference number updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Alpha numeric Customer Reference number is not be updated");
		}
	}

	public void BK_605041_Update_CollDate_OnAdditionInfoPage() {
		try {
			BK_Nav_AdditionalInformation_page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			String expecteddate = adtnlinfopage.getSystemDate();
			System.out.println("expected date: " + expecteddate);
			int date = Integer.parseInt(expecteddate);
			System.out.println("Date: " + date);
			if (date < 10) {
				expecteddate = Integer.toString(date);
				expecteddate = expecteddate.replace("0", "");
			} else {
				expecteddate = Integer.toString(date);
			}
			System.out.println("Expected Date: " + expecteddate);
			adtnlinfopage.setCollectionDate(expecteddate);
			Pass_Message("Collection date is selected");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Collection date update failed");
		}

	}

	// Booking Page for SISP Flow
	public void BK_BookingPage_SISP() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String name = "nivetha";
		String contact = "8940732593";
		String emailAddress = "nivetha.thirunavukarasu.osv@fedex.com";

		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		setCallerDetails(name, contact, emailAddress);
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		uiTestHelper.scrolldown("700");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}

	public void setCollectionContactDetailswithRegion() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			bookingpage.setContactName("Sayali Patil");
			if (bookingpage.verifyCollectionRegion()) {
				Pass_Message("Collection Address Telephone field splited in to" + " two fields for Italy Account");
				bookingpage.setRegion("7981");
			}
			bookingpage.setContactPhone("677474");
			bookingpage.setContactEmail("sayali.patil.osv@fedex.com");
			Pass_Message("Details entered successfully in Booking Information Page");
			bookingpage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contact details are not updated");

		}
	}

	public void BK_SISP_Flow_without_Validation() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");

		}
	}

	public void BK_SISP_Flow() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			// BK_BookingPagesValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");
		}
	}

	// Dangerous Goods
	public void BK_SISP_DG_Flow() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage_DG();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");
		}
	}

	// Goods info paGE
	public void BK_GoodsInfoPage() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
		goodsPage.clickGoodsInfoNextBtn();
	}

	public void setGoodsInformation(String goodsContentType, String goodsDescription, String goodsValue,
			String customerRef) {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		Assert.assertEquals(goodsPage.verifyGoodsDescTitle(), true);
		switch (goodsContentType) {
		case "Non-Document":
			goodsPage.clickGoodsNonDoccontent();
		case "Document":
			goodsPage.clickGoodsDoccontent();
		case "Mixed":
			goodsPage.clickGoodsMixedcontent();
		}
		goodsPage.setGoodsDesc(goodsDescription);
		goodsPage.setGoodsValue(goodsValue);
		goodsPage.setCustomerReference(customerRef);
		Pass_Message("Details entered successfully in Goods Information Page");
	}

	// Goods info paGE
	public void BK_GoodsInfoPage_with_CustomerReference() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
		Pass_Message("Details entered successfully in Goods Information Page");
		goodsPage.clickGoodsInfoNextBtn();
	}

	// Dangerous Goods
	public void BK_GoodsInfoPage_DG() {
		try {
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			setDGGoods("3091", "2", "Lithium Battery", 1);
			goodsPage.clickGoodsInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	public void setDGGoods(String UNNumber, String quantity, String dgOption, int count) {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		Assert.assertTrue(goodsPage.verifyUNNumberDangGood());
		if (goodsPage.verifyUNNumberDangGood()) {
			goodsPage.setUNNumber(UNNumber);
			Pass_Message("UN Number Field is Displayed and User is updated the UNNumber");
		}
		goodsPage.searchDangerousGoods();
		boolean isTable = goodsPage.verifyDangeoursGoodsTable();
		Assert.assertTrue(isTable);
		if (isTable) {
			goodsPage.selectDangerousGoods();
			Pass_Message("Dangerous good is selected from the table");
		} else {
			Fail_Message("Dangerous good is not selected from the table");
		}
		goodsPage.setQuantity(quantity, count);
		goodsPage.setDangerousGoodsOptions(dgOption, count);
		uiTestHelper.scrolldown("300");
	}

	// con info paGE
	public void BK_ConInfo_Page() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true);
		try {
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "10");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "12");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[2]", "200");
			hashmap.put("(//label[text()='Width']/following::input[1])[2]", "120");
			hashmap.put("(//label[text()='Height ']/following::input[1])[1]", "120");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "200");
			coninfopage.addItem();
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
			uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='box']"));
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[4]"));
			uiTestHelper.clickJSByObjects(By.xpath("(//lightning-base-combobox-item[@data-value='pallet'])[2]"));
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Quantity , Weight and Dimensions are updated on the Consignment Information Page");
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");

		}
	}

	// Additional Info page
	public void BK_AdditionalInfo_Page() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		Assert.assertEquals(addinfopage.verifyGetPrice(), true, "Get Price button Not Displayed");
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		Assert.assertEquals(addinfopage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		uiTestHelper.scrolldown("700");
		// Select From time
		addinfopage.setFromCollWindowTimeInput("17:30");
		// Select To time
		addinfopage.setToCollWindowTimeInput("18:30");
		addinfopage.setFromUnavailTimeInput("18:15");
		addinfopage.setToUnavailTimeInput("18:30");
		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();
		if (addinfopage.getBookingSummary() == true) {
			Pass_Message_withoutScreenCapture("Booking Summary is displayed successfully");
		} else {
			Fail_Message("Booking Summary is not displayed");
		}
		String expCost = addinfopage.getexpressCost();
		if (expCost.isEmpty()) {
			Fail_Message("Express shipping cost is not displayed");
		} else {
			Pass_Message_withoutScreenCapture("Express shipping cost is displayed successfully as: " + expCost);
		}
		String exclVat = addinfopage.getexclVAT();
		if (exclVat.isEmpty()) {
			Fail_Message("Excl. VAT cost is not displayed");
		} else {
			Pass_Message_withoutScreenCapture("Excl. VAT cost is displayed successfully as: " + exclVat);
		}
		String Vat = addinfopage.getVAT();
		if (Vat.isEmpty()) {
			Fail_Message("VAT cost is not displayed");
		} else {
			Pass_Message_withoutScreenCapture("VAT cost is displayed successfully as: " + Vat);

		}
		String totalCost = addinfopage.getTotalVAT();
		if (totalCost.isEmpty()) {
			Fail_Message("Total cost is not displayed");

		} else {
			Pass_Message_withoutScreenCapture("Total cost is displayed successfully as: " + totalCost);

		}
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}
		Assert.assertNotNull(bookNum, "SISP Booking not Created");
	}

	// Additional Infor page forSIRP
	public void BK_AdditionalInfo_Page_SIRP() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
		addinfopage.clickValidServices();
		/*
		 * Assert.assertEquals(addinfopage.verifyGetPrice(), true,
		 * "Get Price button Not Displayed"); if (addinfopage.verifyGetPrice()) {
		 * addinfopage.clickGetPrice(); }
		 * Assert.assertEquals(addinfopage.verifyPriceOnTable(), true,
		 * "Price Not Displayed for Services"); addinfopage.verifyPriceOnTable();
		 */
		addinfopage.getValidServices();
		Pass_Message("Valid Service is selected");
		uiTestHelper.scrolldown("300");
		// addinfopage.clickGetPrice();
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		uiTestHelper.scrolldown("600");
		uiTestHelper.propagateException();
		// Select From time
		addinfopage.setFromCollWindowTimeInput("17:30");
		// Select To time
		addinfopage.setToCollWindowTimeInput("18:30");
		// Select unavailable from time
		addinfopage.setFromUnavailTimeInput("18:15");
		addinfopage.setToUnavailTimeInput("18:30");
		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("300");
		addinfopage.clickViewSummary();
		Assert.assertEquals(addinfopage.getBookingSummary(), true, "Booking Summary window not Displayed");
		boolean bookingSummery = addinfopage.getBookingSummary();
		if (bookingSummery == true) {
			Pass_Message("Booking Summary is displayed successfully");
		} else {
			Fail_Message("Booking Summary is not displayed");
		}
		/*
		 * String expCost = addinfopage.getexpressCost(); if (expCost.isEmpty()) {
		 * Fail_Message("Express shipping cost is not displayed"); } else {
		 * Pass_Message("Express shipping cost is displayed successfully as: " +
		 * expCost);
		 *
		 * } String exclVat = addinfopage.getexclVAT(); if (exclVat.isEmpty()) {
		 * Fail_Message("Excl. VAT cost is not displayed"); } else {
		 * Pass_Message("Excl. VAT cost is displayed successfully as: " + exclVat); }
		 * String Vat = addinfopage.getVAT(); if (Vat.isEmpty()) {
		 * Fail_Message("VAT cost is not displayed"); } else {
		 * Pass_Message("VAT cost is displayed successfully as: " + Vat);
		 *
		 * } String totalCost = addinfopage.getTotalVAT(); if (totalCost.isEmpty()) {
		 * Fail_Message("Total cost is not displayed");
		 *
		 * } else { Pass_Message("Total cost is displayed successfully as: " +
		 * totalCost);
		 *
		 * } addinfopage.clickCancel();
		 *
		 * boolean title = addinfopage.getAdditionalOptions(); if (title = true) {
		 * Pass_Message("Cancel button is working fine"); }
		 * uiTestHelper.scrolldown("300"); // uiTestHelper.propagateException();
		 * addinfopage.clickViewSummary();
		 */
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}
		Assert.assertNotNull(bookNum, "SIRP Booking Not Created");
	}

	// SIRP Flow
	public void BK_SIRP_Flow() {
		try {
			BK_BookingPage_SIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page_SIRP();

		} catch (Exception e) {
			e.printStackTrace();
			driver.navigate().refresh();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	// Booking Page for SIRP Flow
	public void BK_BookingPage_SIRP() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SIRP();
			uiTestHelper.scrolldown("300");
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
				// bookingpage.confirmCustomerInstruction();
				Pass_Message("Collection Address is Validated");
			}
			receiverDetails_on_SIRP("879000767", "Denmark");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			Pass_Message("Details entered successfully in Booking Information Page");
		} catch (Exception e) {
			e.printStackTrace();
			driver.navigate().refresh();
			Fail_Message("Navigation to Goods Information Page failed");

		}
	}

	public void receiverDetails_on_SIRP(String billingAccoutNumber, String receiverCountry) {
		BookingPage bookingpage = new BookingPage(driver);
		bookingpage.setBillingAccNumber(billingAccoutNumber);
		bookingpage.setCountry(receiverCountry);
		bookingpage.clickValidate();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Delivery address not Validated");
			Pass_Message("Delivery Address is validated");
			uiTestHelper.scrolldown("300");
		}
	}

	// Booking Page for RIRP flow
	public void BK_BookingPage_RIRP() {
		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("REC_ACCTNAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_RIRP();
			// bookingpage.confirmCustomerInstruction();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Delivery address not Validated");
				Pass_Message("Delivery Address is Validated");
			}
			uiTestHelper.scrolldown("300");
			setCollectionAddress("Denmark", "1552", "Copenhagen", "Megha Joshi", "89 Vester Voldgade");
			uiTestHelper.scrolldown("300");
			bookingpage.setInputSACIDeliveryEmail("nivetha.thirunavukarasu.osv@fedex.com");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			Pass_Message("Details entered successfully in Booking Information Page");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	// RIRP Flow
	public void BK_RIRP_Flow() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: RIRP booking failed");
			driver.navigate().refresh();

		}
	}

	// TC28_BK_US605028a_Update_Collection_Instruction()
	public void BK_US605028a_Update_Collection_Instruction() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				adtnlinfopage.getValidServices();
				Pass_Message("Valid Service is selected");
				uiTestHelper.scrolldown("300");
				adtnlinfopage.setCollectionInstruction("Collect the Consignment from Goods Area");
				Pass_Message("Collection Driver instructions updated successfully");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Update to Collection Driver Instruction failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	public void BK_Update_CollDriver_Inst_charsLength() {
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		try {
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			adtnlinfopage.getValidServices();
			Pass_Message("Valid Service is selected");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.setCollectionInstruction(
					"Please enter more than eighty chars in collection driver instruction in the Additioal Information Page of Booking");
			int collectionDriverInstlen = adtnlinfopage.getCollectionInstruction().length();
			System.out.println(collectionDriverInstlen);
			Assert.assertEquals(collectionDriverInstlen, 80);
			Pass_Message("Collection Instruction Length matched to IMADE Charcter Limit");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: 80 chars field length test case failed");
		}
	}

	public void BK_US605028c_Update_DelDriver_Inst_Morethan50chars() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				adtnlinfopage.getValidServices();
				Pass_Message("Valid Service is selected");
				uiTestHelper.scrolldown("300");
				adtnlinfopage.setDeliveryInstruction("Please enter more than 50 chars in coll driver inst");
				if (adtnlinfopage.getDeliveryInstruction()
						.equals("Please enter more than 50 chars in delv driver ins")) {
					Pass_Message("User is able to enter only 50 chars in Delivery Driver field");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: 50 chars field length fro Delivery driver instruction field test case failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: 50 chars field length test case failed");
		}
	}

	// update del driver instructions
	public void BK_US605028d_Update_DelDriver_Instruction() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				adtnlinfopage.getValidServices();
				Pass_Message("Valid Service is selected");
				uiTestHelper.scrolldown("300");
				adtnlinfopage.setDeliveryInstruction("Deliver the Consignment to Goods Area");
				Pass_Message("Delivery Driver instructions updated successfully");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Update to Delivery Driver Instruction failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Update to Delivery Driver Instruction failed");
		}
	}

	public void BK_US605036_Validate_AvailableServicesDisplayed() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			try {
				WebElement valServices = driver.findElement(By.xpath("//button[@title='Valid Services']"));
				valServices.click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table")));
				WebElement valServiceTable1 = driver.findElement(By.xpath(
						"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table/tbody"));
				List<WebElement> objRow1 = valServiceTable1.findElements(By.tagName("tr"));
				int row_count1 = objRow1.size();
				System.out.println("Row count in Valid Services table is " + row_count1);
				for (int i = 1; i <= row_count1; i++) {
					WebElement mySelect = driver.findElement(By.xpath(
							"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table/tbody/tr["
									+ i + "]//button[text()='Select']"));
					if (mySelect.isEnabled()) {
						Pass_Message(
								"Services Available section is displayed with the services listed based on the information entered in all the tabs");
						break;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Booking failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	// update collection date
	public void BK_US605041_UpdateCollTime() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				adtnlinfopage.getValidServices();
				uiTestHelper.scrolldown("300");
				// Select From time
				try {
					adtnlinfopage.setFromCollWindowTimeInput("17:30");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				// Select To time
				try {
					adtnlinfopage.setToCollWindowTimeInput("18:30");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				// Select To time
				try {
					adtnlinfopage.setToCollWindowTimeInput("19:30");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				Pass_Message("Collection time is updated successfully");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Booking failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	// updated Close time
	public void BK_US605041_Update_Close_Unavilable_Time() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				adtnlinfopage.getValidServices();
				uiTestHelper.scrolldown("300");
				// Select From time
				try {
					adtnlinfopage.setFromCollWindowTimeInput("17:30");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				// Select To time
				try {
					adtnlinfopage.setToCollWindowTimeInput("18:00");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				// Select unavailable from time
				try {

					adtnlinfopage.setFromUnavailTimeInput("18:15");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				// Select unavailable To time
				try {
					adtnlinfopage.setToUnavailTimeInput("18:30");
				} catch (Exception e) {
					e.printStackTrace();
					Fail_Message("Time not selected");
				}
				Pass_Message("Closing/Unavailable time updated successfully");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Booking failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	// Email button verfiication
	public void BK_US605044_Verify_EmailBtnPresent() {
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		try {
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			adtnlinfopage.getValidServices();
			Pass_Message("Valid Service is selected");
			uiTestHelper.scrolldown("300");
			if (adtnlinfopage.getEmailBtn() == true) {
				Fail_Message("Send Email button is enabled");
			} else {
				Pass_Message("'Send Email' button is present and is disabled on Additional Information Page");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Presence of Email button verification failed");
		}
	}

	// verfiy view summary btn
	public void BK_US605048_Verify_ViewSummaryBtn() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			Pass_Message("Valid Service is selected");
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			adtnlinfopage.setCollectionInstruction("Test instruction for collection driver");
			adtnlinfopage.setDeliveryInstruction("Test instruction for delivery driver");
			uiTestHelper.scrolldown("700");
			// Select From time
			try {
				adtnlinfopage.setFromCollWindowTimeInput("17:30");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Time not selected");
			}
			// Select To time
			try {
				adtnlinfopage.setToCollWindowTimeInput("18:00");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Time not selected");
			}
			// Select unavailable from time
			try {

				adtnlinfopage.setFromUnavailTimeInput("18:15");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Time not selected");
			}
			// Select unavailable To time
			try {
				adtnlinfopage.setToUnavailTimeInput("18:30");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Time not selected");
			}

			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickViewSummary();

			boolean bookingSummery = adtnlinfopage.getBookingSummary();
			if (bookingSummery = true) {
				Pass_Message("Booking Summary is displayed successfully");
			} else {
				Fail_Message("Booking Summary is not displayed");
			}
			String expCost = adtnlinfopage.getexpressCost();
			if (expCost.isEmpty()) {
				Fail_Message("Express shipping cost is not displayed");
			} else {
				Pass_Message("Express shipping cost is displayed successfully as: " + expCost);

			}
			String exclVat = adtnlinfopage.getexclVAT();
			if (exclVat.isEmpty()) {
				Fail_Message("Excl. VAT cost is not displayed");
			} else {
				Pass_Message("Excl. VAT cost is displayed successfully as: " + exclVat);
			}
			String Vat = adtnlinfopage.getVAT();
			if (Vat.isEmpty()) {
				Fail_Message("VAT cost is not displayed");
			} else {
				Pass_Message("VAT cost is displayed successfully as: " + Vat);

			}
			String totalCost = adtnlinfopage.getTotalVAT();
			if (totalCost.isEmpty()) {
				Fail_Message("Total cost is not displayed");

			} else {
				Pass_Message("Total cost is displayed successfully as: " + totalCost);

			}
			adtnlinfopage.clickCancel();
			boolean title = adtnlinfopage.getAdditionalOptions();
			if (title = true) {
				Pass_Message("Cancel button is working fine");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: View Summary button verification failed");
		}
	}

	public void BK_GS_RetriveBooking() {
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.CloseTab();
			String bookingid = "LIS-214198";
			HomePage homepage = new HomePage(driver);
			BookingPage bkpage = new BookingPage(driver);
			homepage.searchBooking(bookingid);
			By bookingsearch = By
					.xpath("//a[text()='Bookings']/following::table[1]//a[contains(text(),'" + bookingid + "')]");
			driver.findElement(bookingsearch).click();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//a[contains(text(),'Booking Information')]")));
			if (bkpage.verifyBookingTitle() == true) {
				Pass_Message("Booking is searched successfully from Global Search");
			} else
				Fail_Message("Booking is not searched from Global Search");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Reteive Booking from Global Search failed");
		}
	}

	public void BK_BookingObject_ListView() {
		try {
			HomePage homepage = new HomePage(driver);
			BookingPage bkpage = new BookingPage(driver);
			CCD_Connectivity con = new CCD_Connectivity();
			con.CloseTab();
			driver.navigate().refresh();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			uiTestHelper.propagateException();
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody")));
			if (bkpage.verifyBookingList() == true) {
				Pass_Message("Booking is searched successfully from Booking object");
			} else
				Fail_Message("Booking is not searched from Booking object");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Reteive Booking from Booking object Search failed");
		}
	}

	// Quote sc1
	public void Q_IncorrectCollAddError() {
		try {
			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_Cust_Name = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CCD_Quote.Key_Array[7]);
			String coll_Add = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_Town = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_PostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_Quote.Key_Array[6]);
			String coll_Country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingpage = new BookingPage(driver);
			bookingSelectionOnHomepage(acctName);
			newQuoteonCustomerAccPage(acctName);
			quotePage.clickReceiverButton();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Delivery address not Validated");
				Pass_Message("Delivery Details is Updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			setCollectionAddress(coll_Country, coll_PostCode, coll_Town, coll_Cust_Name, coll_Add);
			Assert.assertEquals(quotePage.errorDisplayed(), true, "Error not diplayed for invalid postal");
			// System.out.println("err_msg: "+err_msg);
			Pass_Message("Null error is diplayed");
			if (quotePage.isSearchButtonEnable()) {
				Pass_Message("Collection address is not validated and Search button is still enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Incorrect Collection address validation failed");
		}
	}

	// Quote - Delivery address
	public void Q_IncorrectDelAddErrorAndValidateError() {
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingPage = new BookingPage(driver);
			bookingSelectionOnHomepage(AcctName);
			newQuoteonCustomerAccPage(AcctName);
			quotePage.clickSenderButton();
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Updated Successfully");
			}
			setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
			Assert.assertEquals(quotePage.errorDisplayed(), true,
					"Error not Displyed when we have Incorrect Delivery Postal");
			quotePage.errorDisplayed();
			Pass_Message("Null error is diplayed");
			if (bookingPage.isValidateButtonEnabled()) {
				Pass_Message("As expected Delivery address is not validated and Search button is still enabled");
			}
			bookingPage.clickBookingnextbtn();
			if (bookingPage.isNextButtonEnabled()) {
				Pass_Message("Without validating address page can not be moved to Next screen");
			} else {
				Fail_Message("Without validating address page has been moved to Next screen");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Incorrect Delivery address validation failed");

		}
	}

	// Account without Contact
	public void Q_AccountWithoutContact() {
		try {
			BookingPage bookingpage = new BookingPage(driver);
			String AcctName = "TESTUSERPT";
			bookingSelectionOnHomepage(AcctName);
			newQuoteonCustomerAccPage(AcctName);
			if (bookingpage.isCallerNameEmpty()) {
				Pass_Message("Contact name is blank");
			}

			if (bookingpage.isCallerPhoneEmpty()) {
				Pass_Message("Contact phone number is blank");
			}

			if (bookingpage.isCallerEmailEmpty()) {
				Pass_Message("Contact Email ID is blank");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account without contact address validation failed");
		}
	}

	// blank cust name 1st line
	public void Q_BlankCustName_FirstAddLine() {
		String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		bookingSelectionOnHomepage(acctName);
		newQuoteonCustomerAccPage(acctName);
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Updated Successfully");
		}
		setDeliveryAddress(deliv_Country, Deliv_PostCode, deliv_Town, "", "");
		uiTestHelper.scrolldown("300");// TODO
		if (bookingpage.isNextButtonEnabled()) {
			Pass_Message("Without entering Customer Name and Address 1st line page can be moved to Next screen");
		} else {
			Fail_Message("Without entering Customer Name and Address 1st line page can not be moved to Next screen");
		}
		bookingpage.clickBookingnextbtn();

	}

	// create Quote non document
	public void Q_CreateQuote_NonDocument() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote creation failed");

			driver.navigate().refresh();

		}
	}

	// create quote document
	public void Q_CreateQuote_Document() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		Q_QuoteInfoPage();
		setGoodsInformation("Document", "TestDesc", "20", "12345");
		goodsPage.clickGoodsInfoNextBtn();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
	}

	// create quote mixed
	public void Q_CreateQuote_Mixed() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		try {
			Q_QuoteInfoPage();
			setGoodsInformation("Mixed", "TestDesc", "20", "12345");
			Pass_Message("Details entered successfully in Goods Information Page");
			goodsPage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote creation failed");
			driver.navigate().refresh();

		}
	}

	// Dangerous good indicator set to NO in Quote
	public void Q_GoodsInfo_DangGoods_NO() {
		Q_Nav_GoodsInformation_page();
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		Assert.assertEquals(goodsPage.verifyDangerousGoodsNo(), true);
		if (goodsPage.verifyDangerousGoodsNo() == true) {
			Pass_Message("Dangerous Good indicator is set to NO");
		} else {
			Fail_Message("Dangerous Good indicator is set to Yes");
		}
	}

	// Quote_good infor page
	public void Q_Nav_GoodsInformation_page() {

		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", ACM_CI.Key_Array[5]);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		bookingSelectionOnHomepage(AcctName);
		newQuoteonCustomerAccPage(AcctName);
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Updated Successfully");
		}
		// quotePage.clickCustomerInstrConfirmBtn();
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		bookingpage.clickBookingnextbtn();
		Assert.assertEquals(goodsInfoPage.verifyGoodsDescTitle(), true);

	}

	// Blank amount error
	public void Q_Validate_BlankAmtError() {
		try {
			Q_Nav_GoodsInformation_page();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			goodsPage.clickGoodsInfoNextBtn();
			if (goodsPage.verifyGoodsDescTitle() == true) {
				Pass_Message("Page can not be moved to next by keeping Amount field blank");
			} else {
				Fail_Message("Page has been moved to next by keeping Amount field blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Dangerous Good indicator has not been validated");
		}
	}

	// Zero Amount quote
	public void Q_CreateQuote_ZeroAmt() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", ACM_CI.Key_Array[5]);
		Q_QuoteInfoPage();
		setGoodsInformation("Document", "TestDesc", "0", "12345");
		Pass_Message("Details entered successfully in Goods Information Page");
		goodsPage.clickGoodsInfoNextBtn();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
	}

	// Create Quote for any desc enetered
	public void Q_CreateQuote_AnyDesc() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		Q_QuoteInfoPage();
		setGoodsInformation("Mixed", "Test Any Description", "20", "12345");
		Pass_Message("Details entered successfully in Goods Information Page");
		goodsPage.clickGoodsInfoNextBtn();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
	}

	// Blank desc
	public void Q_CreateQuote_BlankDesc() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		try {
			Q_QuoteInfoPage();
			setGoodsInformation("Document", "", "20", "12345");
			Pass_Message("Details entered successfully in Goods Information Page");
			goodsPage.clickGoodsInfoNextBtn();
			if (goodsPage.verifyGoodsDescTitle() == true) {
				Pass_Message("Page can not be moved to next by keeping Goods Description field blank");
			} else {
				Fail_Message("Page can be moved to next by keeping Goods Description field blank");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote failed");
		}

	}

	// Complete quote flow
	public void getRecentlyCreatedQuote() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
			Q_getRecentQuotefrom_BookingList(quoteNum);
		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote creation failed");
			driver.navigate().refresh();

		}
	}

	// Complete quote flow
	public void Q_QuoteFlow() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
		}

		catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote creation failed");
		}
	}

	// Complete quote flow by Receiver
	public void Q_QuoteFlow_Receiver() {
		try {
			Q_QuoteInfoPage_Receiver();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ExceptionQuote creation failed failed");

		}
	}

	// Quote Details validation
	public void Q_QuoteDetails_Validation() {
		try {
			Q_getRecentQuotefrom_BookingList(quoteNum);

			Pass_Message("Quote selected from additional information tab");// Quote selected from Booking recent view
			// list
			// Quote detail page after creating Quote
			QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
			try {
				if (quoteDetailPage.isRecordTypeAsQuote()) {
					Pass_Message("Record Type is displayed as 'Quote'");
				}
				if (quoteDetailPage.isStatusAsUnconverted()) {
					Pass_Message("Status is displayed as 'Unconverted'");
				}
				if (quoteDetailPage.isServiceDisplayed()) {
					Pass_Message("Service is displayed successfully");
				}
				String collDate = quoteDetailPage.getCollectionDate();
				if (collDate.isEmpty()) {
					Fail_Message("Collectione date is not displayed");
				} else {
					Pass_Message("Collection date is displayed successfully as: " + collDate);
				}
				String convDate = quoteDetailPage.getConvertedDate();

				if (convDate.isEmpty()) {
					Pass_Message("Converted date is not displayed");
				} else {
					Fail_Message("Converted date is displayed successfully as: " + convDate);
				}
			} catch (Exception e) {
				Fail_Message("Header in Booking Details page is not displayed properly");
			}
			// Q_ConvertToBooking();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote details validation failed");
		}
	}

	// Convert to Booking Sender
	public void Q_ConvertToBookingSender() {
		try {
			QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
			Assert.assertEquals(quoteDetailPage.verifyConvertToBookingBtn(), true,
					"Booking conversion Button not Displayed");
			quoteDetailPage.clickConvertToBookingBtn();
			BookingPage bookingPage = new BookingPage(driver);
			if (bookingPage.verifyBookingTitle()) {
				bookingPage.clickSameAsCallerInfo();
				Pass_Message("Details entered successfully in Booking Information Page");
				bookingPage.clickBookingnextbtn();
			}
			GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
			goodsInfoPage.clickGoodsInfoNextBtn();
			ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			Q_AdditionalInfo_Page();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Convert to Booking failed details validation failed");

		}
	}

	// Convert to Booking Receiver
	public void Q_ConvertToBookingReceiver() {
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		Assert.assertEquals(quoteDetailPage.verifyConvertToBookingBtn(), true,
				"Booking conversion Button not Displayed");
		quoteDetailPage.clickConvertToBookingBtn();
		BookingPage bookingPage = new BookingPage(driver);
		uiTestHelper.propagateException();
		if (bookingPage.verifyBookingTitle()) {
			bookingPage.setContactName("Arjun Rampal");
			bookingPage.setContactPhone("8940732594");
			bookingPage.setContactEmail("Arjun_Rampal@gmail.com");
			bookingPage.clickReceiverSameAsCallerInfo();
			Pass_Message("Details entered successfully in Booking Information Page");
			bookingPage.clickBookingnextbtn();
		}
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		goodsInfoPage.clickGoodsInfoNextBtn();
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		Q_AdditionalInfo_Page();
	}

	// Booking Additional information page in quote flow when converted quote to
	// Booking
	public void Q_AdditionalInfo_Page() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		Assert.assertEquals(additionalInfoPage.verifyTitle(), true, "Additional Information Page not Opened");
		additionalInfoPage.clickValidServices();
		Assert.assertEquals(additionalInfoPage.verifyGetPrice(), true, "Get Price button Not Displayed");
		if (additionalInfoPage.verifyGetPrice()) {
			additionalInfoPage.clickGetPrice();
		}
		Assert.assertEquals(additionalInfoPage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("700");
		uiTestHelper.scrolldown("300");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		try {
			String book = additionalInfoPage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			uiTestHelper.propagateException();
			if (additionalInfoPage.isBookingReferenceNoCreated()) {
				Pass_Message("Quote to Booking converted successfully and booking number is: " + bookNum);
			} else {
				Fail_Message("Quote to Booking conversion failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Quote is not converted to Booking");
		}
		Assert.assertNotNull(bookNum, "Quote to Booking conversion is not Successfull");
	}

	public void Q_AdditionalInfoPage_with_productOptions() {
		try {
			selectThreeQuoteProductOptions();
			Q_AdditionalInfo_Page();
		} catch (Exception e) {
			Fail_Message("Additional page information not updated while converting quote to Booking");
		}
	}

	// Quote Booking Page
	public void Q_QuoteInfoPage() {
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		bookingSelectionOnHomepage(AcctName);
		newQuoteonCustomerAccPage(AcctName);
		setCallerDetails(Deliv_Cust_Name, "8940732594", "nivetha.thirunavukarasu.osv@fedex.com");
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Updated Successfully");
		}
		// quotePage.clickCustomerInstrConfirmBtn();
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		;
		uiTestHelper.scrolldown("500");
		bookingpage.clickBookingnextbtn();
	}

	// Quote Info Page by Receiver
	public void Q_QuoteInfoPage_Receiver() {
		try {

			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_Cust_Name = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CCD_Quote.Key_Array[7]);
			String coll_Add = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_Town = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_PostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_Quote.Key_Array[7]);
			String coll_Country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_Quote.Key_Array[7]);

			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingPage = new BookingPage(driver);
			bookingSelectionOnHomepage(acctName);
			newQuoteonCustomerAccPage(acctName);
			quotePage.clickReceiverButton();
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Delivery address not Validated");
				Pass_Message("Delivery Address is Updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			setCollectionAddress(coll_Country, coll_PostCode, coll_Town, coll_Cust_Name, coll_Add);
			uiTestHelper.scrolldown("300");
			bookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Page validation failed");
		}
	}

	// Quote Additional info page
	public void Q_AdditionalInfoPage() {

		QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
		AdditionalInfoPage additionalinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(additionalinfopage.verifyTitle(), true, "Additional Information Page not Opened");
		quoteAdditionalInfoPage.clickValidServices();
		uiTestHelper.scrolldown("200");
		additionalinfopage.verifyPriceOnTable();
		Assert.assertEquals(additionalinfopage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
		quoteAdditionalInfoPage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		uiTestHelper.scrolldown("300");
		additionalinfopage.clickDetailnfoBtn();
		uiTestHelper.scrolldown("300");
		quoteAdditionalInfoPage.clickCloseButton();
		uiTestHelper.scrolldown("300");
		quoteAdditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteAdditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteAdditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);

	}

	// Box Type
	public void Q_ConType_Box_Pallet_Envelope() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			Q_diffConsignment();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Consignment selection type validation failed");
		}
	}

	// Quote Over limit error validation
	public void Q_OverLimit_0Items_ErrorValidation() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			driver.findElement(By.xpath("(//input[@name='quantity'])[1]")).sendKeys("0", Keys.TAB);
			if (coninfopage.verifyQuantityMinimum() == true) {
				Pass_Message(
						"Correct error message 'The minimum number of packages in a single shipment is 1' is displayed below shipemtn box");
			} else {
				Fail_Message("Correct error message is not displayed");
			}
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[2]", "800");
			hashmap.put("(//label[text()='Width']/following::input[1])[2]", "900");
			hashmap.put("(//label[text()='Height']/following::input[1])[2]", "2100");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "71");
			coninfopage.addItem();
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}

			if (coninfopage.verifyWeightExceeds() == true) {
				Pass_Message("Error for threshold value for Weight is displayed");
			} else {
				Fail_Message("Error for threshold value for Weight is not displayed");
			}
			/*
			 * if (coninfopage.verifyTotalWeightExceeds() == true) { Pass_Message(
			 * "Error message as 'Total weight exceeds restrictions for selected service' is displayed successfully"
			 * ); } else { Fail_Message(
			 * "Error message as 'Total weight exceeds restrictions for selected service' is displayed successfully"
			 * ); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Consignment selection as Box type failed");
		}
	}

	// Exceeding qty error validation
	public void Q_ExcQty_ErrorValidation() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "100");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			if (coninfopage.verifyQuantityExceeds() == true) {
				Pass_Message(
						"Correct error message 'The maximum number of packages in a single shipment is 99' is displayed below shipemtn box");
			} else {
				Fail_Message("Correct error message is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Exceeded quantity error validation failed");
		}
	}

	// Create Quote for different types of consignmnet
	public void Q_CreateQuote_DiffConsignmnentsType() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			Q_diffConsignment();
			Q_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ExceptionQuote creation failed failed");
		}
	}

	// Create Quote of 10 diff items
	public void Q_CreateQuote_10Items_SameWeightsAndDims() {
		try {

			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			int i, j;
			for (j = 1; j < 10; j++) {
				for (i = 1; i < 51; i++) {
					List<WebElement> val = driver.findElements(By.xpath("//input[@inputmode='decimal']"));
					val.get(i).sendKeys("1");
					i = i + 1;
					val.get(i).sendKeys("2");
					i = i + 1;
					val.get(i).sendKeys("12");
					i = i + 1;
					val.get(i).sendKeys("21");
					i = i + 1;
					val.get(i).sendKeys("28");
					System.out.println(i);
					if (j < 10) {
						coninfopage.addItem();
						j++;
					} else {
						j++;
					}
					uiTestHelper.scrolldown("300");
				}
				if (coninfopage.verifyAddItemBtn() == true) {
					Fail_Message("Add another item button is still enabled");
				} else {
					Pass_Message("Add another item button is disabled");
				}
			}
			uiTestHelper.scrollUp("-1000");
			if (coninfopage.verifyErrorMsgAdding10Items() == true) {
				Pass_Message(
						"After adding 10 items an error msg :'Maximum number of package lines reached' is displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("10 items has not been added succesfully");
		}

	}

	public void Q_diffConsignment() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);

		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true, "Consignment Info Page not Displayed");
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("(//input[@name='quantity'])[1]", "1");
		hashmap.put("(//label[text()='Length']/following::input[1])[1]", "2");
		hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
		hashmap.put("(//label[text()='Height']/following::input[1])[1]", "21");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
		hashmap.put("(//input[@name='quantity'])[2]", "1");
		hashmap.put("(//label[text()='Length']/following::input[1])[2]", "2");
		hashmap.put("(//label[text()='Width']/following::input[1])[2]", "12");
		hashmap.put("(//label[text()='Height ']/following::input[1])[1]", "21");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "28");
		hashmap.put("(//input[@name='quantity'])[3]", "1");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[3]", "28");
		int i = 1;
		for (i = i + 1; i <= 3; i++) {
			coninfopage.addItem();
		}
		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
		coninfopage.clickTypeBox();
		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[4]"));
		uiTestHelper.clickJSByObjects(By.xpath("(//lightning-base-combobox-item[@data-value='pallet'])[2]"));
		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[6]"));
		uiTestHelper.clickJSByObjects(By.xpath("(//lightning-base-combobox-item[@data-value='envelope'])[3]"));
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		coninfopage.clickConsignmentInfoNextBtn();

	}

	// Calculate total weight and dims
	public void Q_Calc_TotalWtAndDimValue() {
		QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
		AdditionalInfoPage additionalinfopage = new AdditionalInfoPage(driver);
		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		Q_diffConsignment();
		Assert.assertEquals(additionalinfopage.verifyTitle(), true, "Additional Information Page not Opened");
		quoteAdditionalInfoPage.clickValidServices();
		quoteAdditionalInfoPage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		uiTestHelper.scrolldown("600");
		// Consignment information
		List<WebElement> totVal = uiTestHelper
				.waitForObjects(By.xpath("//div/div[@class='slds-p-left_small']//following::div"));
		String totQty = totVal.get(0).getText();
		String totVol = totVal.get(1).getText();
		String totWt = totVal.get(2).getText();
		if (totQty.contains("3")) {
			Pass_Message("Total Quantity is displayed correctly as: " + totQty);
		}

		if (totVol.contains("0.001 m3")) {
			Pass_Message("Total Volume is displayed correctly as: " + totVol);
		}

		if (totWt.contains("84.00 ")) {
			Pass_Message("Total Weight is displayed correctly as: " + totWt);
		}
	}

	public void Q_Verify_ValidServices() {

		Q_Nav_GoodsInformation_page();
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		setGoodsInformation("Non-Document", "TestDesc", "20", "12345");
		if (goodsPage.verifyEnhancedLiabilityField() == true) {
			Pass_Message("Enhanced liablity indicator is set to No");
		} else {
			Fail_Message("Enhanced liablity indicator is set to Yes");
		}
		goodsPage.clickGoodsInfoNextBtn();
		BK_ConInfo_Page();
		QuoteAdditionalInfoPage adtnlinfopage = new QuoteAdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
		// Current date collection date validation
		try {
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Collection current date validation failed");
		}
		// To verify if services are available with price

		adtnlinfopage.selectService();
		// To validate Enhanced Liability and Case Order indicator set to NO
		uiTestHelper.scrolldown("300");
		if (adtnlinfopage.verifyCashOrderIndiactor() == true) {
			Pass_Message("Case Order indicator is set to No");
		} else {
			Fail_Message("Case Order indicator is set to Yes");
		}
	}

	// back coll date
	public void Q_Verify_BackDatedCollDate() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			QuoteAdditionalInfoPage adtnlinfopage = new QuoteAdditionalInfoPage(driver);
			String date = adtnlinfopage.getBackDate("dd-MMM-yyyy");
			System.out.println("BackDated: " + date);
			adtnlinfopage.enterCollectionDate(date);
			adtnlinfopage.clickValidServices();
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Value must be')]")));
			if (driver.findElement(By.xpath("//div[contains(text(),'Value must be')]")).isDisplayed()) {
				Pass_Message("Warning message for back dated collection date is displayed successfully");
			} else {
				Fail_Message("Warning message for back dated collection date is not displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Back dated coll date Valid Service validation failed");
		}
	}

	// futre date validation
	public void Q_Verify_FutureDatedCollDate() {
		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			QuoteAdditionalInfoPage adtnlinfopage = new QuoteAdditionalInfoPage(driver);
			AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
			String date = adtnlinfopage.getFutureDate("dd-MMM-yyyy");
			System.out.println("Future Dated: " + date);
			adtnlinfopage.enterCollectionDate(date);
			// uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			uiTestHelper.scrolldown("300");

			WebElement valServiceTable1 = additionalInfoPage.serviceTable();
			List<WebElement> objRow1 = valServiceTable1.findElements(By.tagName("tr"));
			int row_count1 = objRow1.size();
			System.out.println("Row count in Valid Services table is " + row_count1);
			for (int i = 1; i <= row_count1; i++) {

				WebElement mySelect = uiTestHelper
						.waitForObject(By.xpath("//*[contains(text(),'Services Available')]/follo"
								+ "wing::slot[2]//div[@class='slds-scrollable_y']/table/tbody//button[text()='Select']"));
				if (mySelect.isEnabled()) {
					Pass_Message("Valid Service is available for current date");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Valid Service for future collection date validation failed");
		}
	}

	// Quote to booking validation
	public void Q_QuoteToBooking_DetailsValidation() {
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		try {
			if (bookingRecordPage.isCallerInformationDisplayed()) {
			} else {
				Fail_Message("Caller Information is not displayed");
			}

			if (bookingRecordPage.isCallerNameDisplay()) {
			} else {
				Fail_Message("Caller Name is not displayed");
			}
			if (bookingRecordPage.isReceiverNameDisplay()) {
			} else {
				Fail_Message("Receiver Name is not displayed");
			}
			Pass_Message("Details on Booking information page displayed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote to Booking details validation failed");
		}

		try {
			bookingRecordPage.clickMoreOption();
			bookingRecordPage.clickAdditionalInfo();
			quoteNumber = bookingRecordPage.getConvertedFrom();
			String collDate = bookingRecordPage.getCollectionDateVal();
			if (bookingRecordPage.isCollectionDateDisplayed()) {
				Pass_Message("Collection Date is displayed successfully as: " + collDate);
			} else {
				Fail_Message("Collection Date is not displayed");
			}
			Assert.assertNotNull(quoteNumber, "Quote number not Present");
			if (quoteNumber.isEmpty()) {
				Fail_Message("Quote num not present");

			} else {
				Pass_Message("Q num is displayed on additional info page: " + quoteNumber);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Additional Information Page for quote to booking validation failed");

		}
	}

	// Edit booking for SISP flow from List view
	public void BK_EditBookingBtnVal_ListView_SISP() {
		HomePage homepage = new HomePage(driver);
		BookingPage bkpage = new BookingPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			String bookingNum = Database_Connection.retrieveTestData("BOOKINGNUM", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			System.out.println("Booking Num: " + bookingNum);
			// String bookingNum= "LIS-214024";
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			// to select respective quote
			uiTestHelper.propagateException();
			homepage.searchBookingNumber(bookingNum);
			bkpage.getBookingNumfromList(bookingNum);
			if (bkdetailspage.verifyEditBooking() == true) {
				Pass_Message("Edit button is Enabled");
			} else {
				Fail_Message("Edit button is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:SISP Edit Booking functionality failed");
		}
	}

	public void BK_EditBooking_SISP() {
		HomePage homepage = new HomePage(driver);
		BookingPage bkpage = new BookingPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			ACM_Connectivity.CloseTab();
			String bookingNum = bookNum;
			// String bookingNum = Database_Connection.retrieveTestData("BK_EDIT", "ACM",
			// "KEY", ACM_Booking.Key_Array[6]);
			System.out.println("Booking Number: " + bookingNum);
			BookingDrpDown();
			// to select respective quote
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody")));
			homepage.searchBookingNumber(bookingNum);
			uiTestHelper.propagateException();
			bkpage.getBookingNumfromList(bookingNum);
			bkdetailspage.clickEditBooking();
			List<WebElement> callerinfo = bkpage.getCallerInfoButtons();
			if (callerinfo.get(0).isSelected()) {
				Fail_Message("Sender as Caller Information is not locked");
			} else {
				Pass_Message("Sender as Caller Information is locked");
			}
			if (callerinfo.get(2).isSelected()) {
				Fail_Message("Sender as Payment Terms is not locked");
			} else {
				Pass_Message("Sender as Payment Terms is locked");
			}
			if (bkpage.verifyBillingAccNumber() == true) {
				Fail_Message("Billing acct number is not locked");
			} else {
				Pass_Message("Billing acct number is locked");
			}
			try {
				bkpage.editCollectionCountry();
				if (bkpage.verifyEditonCountry() == true) {
					Pass_Message("Coll Cou is not locked");
				} else {
					Fail_Message("Coll Cou is locked");
				}
			} catch (Exception e) {
				Pass_Message("Coll Cou is locked");
			}
			bkpage.clickRemoveDelCountry();
			if (bkpage.verifyDelCountry() == true) {
				Pass_Message("Del Cou is not locked");
			} else {
				Fail_Message("Del Cou is locked");
			}
			// update booking page
			bkpage.setDeliveryCountry("United Kingdom");
			bkpage.setDeliveryPostal("MK1 1AA");
			bkpage.setDeliveryTown("Milton Keynes");
			bkpage.setDelCustomerName("Test Cust");
			bkpage.setDeliveryAddress("Address Line Updated");
			bkpage.deliveryValidatebtn();
			if (bkpage.successMsgonAddress()) {
				Pass_Message("Delivery details are updated Successfully");
			}
			bkpage.setCollectionPostal("1000-100");
			bkpage.setCollectionTown("Lisboa");
			bkpage.setCollectionCustomerName("Test ABC CNAME6 UPDATED");
			bkpage.setCollectionAddress("1TEST ADDR LINE6 updated");
			bkpage.clickCollectionValidatebtn();
			if (bkpage.successMsgonAddress()) {
				Pass_Message("Collection details are updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			bkpage.setContactName("TEST UPDATED");
			bkpage.setContactPhone("2228855525");
			bkpage.setContactEmail("testupdate@gmail.com");
			Pass_Message("Updated details entered successfully in Booking Information Page");
			uiTestHelper.propagateException();
			bkpage.clickBookingnextbtn();
			BK_GoodsInfoPage_EditBooking();
			BK_ConInfo_Page_EditBooking();
			BK_AdditionalInfoPage_EditBooking();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:SISP Edit Booking functionality failed");
			// driver.navigate(). refresh();

		}
	}

	public void edit_SISP_Booking() {
		BK_EditBooking_SISP();
		CCD_Connectivity connectivity = new CCD_Connectivity();
		connectivity.CloseTab();
		HomePage homePage = new HomePage(driver);
		homePage.clearBookingSearch();

	}

	public void BK_GoodsInfoPage_EditBooking() {
		try {
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "Handle with care", "40", "12345");
			goodsPage.clickGoodsInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	public void BK_ConInfo_Page_EditBooking() {
		try {
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			List<WebElement> deleteItem = coninfopage.getDeleteButtons();
			deleteItem.get(0).click();
			Pass_Message("Line Item deleted successfully");
			uiTestHelper.propagateException();
			coninfopage.clickConsignmentInfoNextBtn();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	// Addition info Edit booking page
	public String BK_AdditionalInfoPage_EditBooking() {
		try {
			AdditionalInfoPage adtnlpage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			adtnlpage.clickValidServices();
			Pass_Message("Valid Service is selected");
			if (adtnlpage.verifyGetPrice()) {
				adtnlpage.clickGetPrice();
			}
			adtnlpage.verifyPriceOnTable();
			adtnlpage.getValidServices();
			uiTestHelper.scrolldown("300");
			adtnlpage.clickViewSummary();
			String expCost = adtnlpage.getexpressCost();
			if (expCost.isEmpty()) {
				Fail_Message("Express shipping cost is not displayed");
			} else {
				Pass_Message("Express shipping cost is displayed successfully as: " + expCost);
			}
			String exclVat = adtnlpage.getexclVAT();
			if (exclVat.isEmpty()) {
				Fail_Message("Excl. VAT cost is not displayed");
			} else {
				Pass_Message("Excl. VAT cost is displayed successfully as: " + exclVat);
			}
			String Vat = adtnlpage.getVAT();
			if (Vat.isEmpty()) {
				Fail_Message("VAT cost is not displayed");
			} else {
				Pass_Message("VAT cost is displayed successfully as: " + Vat);

			}
			String totalCost = adtnlpage.getTotalVAT();
			if (totalCost.isEmpty()) {
				Fail_Message("Total cost is not displayed");
			} else {
				Pass_Message("Total cost is displayed successfully as: " + totalCost);
			}
			try {
				adtnlpage.clickUpdateBooking();
				String book = adtnlpage.getUpdatedBookingConfirmMsg();
				bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
				if (adtnlpage.verifyUpdatedBookingConfirmMsg()) {
					Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
				} else {
					Fail_Message("Update Booking failed");
				}
			} catch (NoSuchElementException e) {
				// TODO: handle exception
				Fail_Message("Booking Updation Failed");
				adtnlpage.clickCancel();
				if (adtnlpage.verifyErrorMessage()) {
					CCD_Connectivity conn = new CCD_Connectivity();
					conn.CloseTab();
					Assert.assertNotNull(bookNum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Update Booking failed");
			// driver.navigate().refresh();
			// driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
		}
		return bookNum;
	}

	// Add line item before
	public void BK_LineItem_BeforeDelete() {
		try {
			BookingRecordPage bkdetailpage = new BookingRecordPage(driver);
			bkdetailpage.clickMoretab();
			bkdetailpage.clickConsignmentInfo();
			List<WebElement> lineItem = bkdetailpage.getLineItems();
			if (lineItem.get(0).isDisplayed()) {
				Pass_Message("First Line item is displayed successfully");
			} else {
				Fail_Message("First Line item is not displayed successfully");
			}
			if (lineItem.get(1).isDisplayed()) {
				Pass_Message("Second Line item is displayed successfully");
			} else {
				Fail_Message("Second Line item is not displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Add Line item validation failed");
		}
	}

	// Add line item after deletion
	public void BK_LineItem_AfterDelete() {
		try {
			BookingRecordPage bkdetailpage = new BookingRecordPage(driver);
			bkdetailpage.clickMoretab();
			bkdetailpage.clickConsignmentInfo();
			List<WebElement> lineItem = bkdetailpage.getLineItems();
			if (lineItem.get(0).isDisplayed()) {
				Pass_Message("First Line item is displayed successfully");
			} else {
				Fail_Message("First Line item is not displayed successfully");
			}
			if (lineItem.get(1).isDisplayed()) {
				Fail_Message("Second Line item indicator as 'Deleted' is Not displayed successfully");
			} else {
				Pass_Message("Second Line item indicator as 'Deleted' is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Add Line item validation failed");
		}
	}

	// Cancel Booking
	public void BK_CancelBooking_SISP() {
		try {
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			/*
			 * String bookingNum = Database_Connection.retrieveTestData("BK_CAN", "ACM",
			 * "KEY", ACM_Booking.Key_Array[6]);
			 * System.out.println("Booking No:"+bookingNum);
			 */
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			try {
				if (bkdetailspage.verifyCancelBookingbtn() == true) {
					Pass_Message("Cancel Booking button is displayed successfully in the header");
					bkdetailspage.clickCancelBooking();
					uiTestHelper.propagateException();
					if (bkdetailspage.verifyCancelBookingScreen() == true) {
						Pass_Message("Cancel Booking screen is displayed successfully");
					}
					bkdetailspage.setCallerofCancel("Prachi");
					bkdetailspage.setCancelReason("Booking no longer required");
					uiTestHelper.scrolldown("300");
					bkdetailspage.saveCancelBooking();
					uiTestHelper.propagateException();
					bkdetailspage.clickBookingHistroy();
					Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
					if (bkdetailspage.verifyCancelBookingStatus() == true) {
						Pass_Message("Booking has been cancelled successfully and status displayed as CN");
					} else {
						Fail_Message("Status has not been correctly displayed as 'CN'");
					}
					if (bkdetailspage.verifyCancelReason() == true) {
						Pass_Message("'Reason for Cancel' is correctly displayed as 'Booking no longer required'");
					} else {
						Pass_Message("'Reason for Cancel' is not displayed as 'Booking no longer required'");
					}
				}
			} catch (Exception e) {
				Fail_Message("Cancel Booking button is not displayed successfully in the header");

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:SISP Cancel Booking functionality failed");
			driver.navigate().refresh();

		}
	}

	// Booking Summary
	public void BK_BookingSummary() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			HomePage homepage = new HomePage(driver);
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickBookingSummary();
			/*
			 * String emailVal=bkdetailspage.getEmailAddress(); if(emailVal.isEmpty()) {
			 * Fail_Message("Email value is not prepopulated"); } else {
			 * Pass_Message("Email value is prepopulated and its value is: "+emailVal); }
			 */
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			bkdetailspage.setEmailAddress("prachi.sharma");
			if (bkdetailspage.verifyEmailFormat() == true) {
				Pass_Message("Email field is mandatory");
			} else {
				Fail_Message("Email field is not mandatory");
			}
			bkdetailspage.setEmailAddress("prachi.sharma.osv@fedex.com");
			Pass_Message("Email value has been entered successfully");
			uiTestHelper.propagateException();
			bkdetailspage.clickSendEmail();
			Pass_Message("Email has been sent successfully");
			if (bkdetailspage.verifySuccessMessage()) {
				uiTestHelper.propagateException();
				bkdetailspage.clickBookingHistroy();
				Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
			}
			String sentTo = bkdetailspage.getEmailSentTo();
			if (sentTo.contains("prachi.sharma.osv@fedex.com")) {
				Pass_Message("'Booking Summary - Sent To' value is displayed successfully on Booking History page");
			} else {
				Fail_Message("'Booking Summary - Sent To' value is not displayed successfully on Booking History page");
			}
			String sentBy = bkdetailspage.getEmailSentBy();
			if (sentBy.isEmpty()) {
				Fail_Message("'Booking Summary - Sent By' value is not displayed successfully on Booking History page");
			} else {
				Pass_Message("'Booking Summary - Sent By' value is displayed successfully on Booking History page as: "
						+ sentBy);
			}
			String sentDate = bkdetailspage.getEmailSentTo();
			if (sentDate.isEmpty()) {
				Fail_Message(
						"'Booking Summary - Sent Date' value is not displayed successfully on Booking History page");
			} else {
				Pass_Message(
						"'Booking Summary - Sent Date' value is displayed successfully on Booking History page as: "
								+ sentDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Summary validations failed");
		}
	}

	// Booking dropdown
	public void BookingDrpDown() {
		try {

			if (driver.findElements(By.xpath("(//span[contains(text(),'Bookings')])[3]")).size() > 0) {

			} else {
				HomePage homepage = new HomePage(driver);
				homepage.clickDropDownNavigationMenu();
				homepage.clickBooking();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking listviewPage can not be displayed");

		}
	}

	public void BK_NewCustomerBooking() {
		try {
			uiTestHelper.propagateException();
			// Values are being fetched from CI_Booking row in database for respective
			// column
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Coll_CustName = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Coll_Add = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			String Coll_Town = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			String Coll_PostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Coll_Country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			BookingPage bkpage = new BookingPage(driver);
			HomePage homepage = new HomePage(driver);
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(driver);
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerIdentification();
			cipage.clickNewCustomer();
			cipage.clickBookingRadioButton();
			bkpage.setCallerName("Prachi Sharma");
			bkpage.setCallerPhone("987564321");
			bkpage.setCallerEmail("test@gmail.com");
			List<WebElement> list = bkpage.getCallerInfoButtons();
			list.get(0).click();
			list.get(1).click();
			bkpage.setCollectionCountry(Coll_Country);
			bkpage.setCollectionPostal(Coll_PostCode);
			bkpage.setCollectionTown(Coll_Town);
			bkpage.setCollectionCustomerName(Coll_CustName);
			bkpage.setCollectionAddress(Coll_Add);
			bkpage.clickCollectionValidatebtn();
			if (bkpage.successMsgonAddress()) {
				Assert.assertEquals(bkpage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Validated");
			}
			bkpage.setDelCustomerName(Deliv_Cust_Name);
			bkpage.setDeliveryAddress(Deliv_Add);
			bkpage.setDeliveryCountry(Deliv_Country);
			bkpage.setDeliveryPostal(Deliv_PostCode);
			bkpage.setDeliveryTown(Deliv_Town);
			bkpage.deliveryValidatebtn();
			if (bkpage.successMsgonAddress()) {
				Assert.assertEquals(bkpage.successMsgonAddress(), true, "Delivery address not Validated");
				Pass_Message("Delivery Address is Validated");
				uiTestHelper.scrolldown("300");
				setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			}
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");
			driver.navigate().refresh();
		}
	}

	// Audit Booking Page for Edit Booking validations
	public void BK_BookingAuditPageValidation_EditBooking() {
		try {
			BookingPage bkpage = new BookingPage(driver);
			HomePage homepage = new HomePage(driver);
			BookingRecordPage bkdetailpage = new BookingRecordPage(driver);
			// String bookingNum = Database_Connection.retrieveTestData("BK_EDIT", "ACM",
			// "KEY", ACM_Booking.Key_Array[6]);
			BK_EditBooking_SISP();
			uiTestHelper.propagateException();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailpage.clickBookingInfo();
			bkdetailpage.clickBookingAudit();
			String emailOriginalValue = bkdetailpage.getOriginalValue("Email");
			String emailUpdatedValue = bkdetailpage.getUpdatedValue("Email");
			if (!emailOriginalValue.isEmpty()) {
				Pass_Message("Email values displayed are: Original- " + emailOriginalValue + " and Updated value- "
						+ emailUpdatedValue);
			}
			List<WebElement> tablerow = driver
					.findElements(By.xpath("//h2/span[contains(text(),'Audit History')]/following::table[1]/tbody/tr"));
			int rowCount = tablerow.size();
			System.out.println("rowCount: " + rowCount);
			for (int i = 0; i <= rowCount; i++) {
				WebElement field = driver.findElement(
						By.xpath("//h2/span[contains(text(),'Audit History')]/following::table[1]/tbody/tr['" + i
								+ "']//td[1]//lightning-base-formatted-text"));
				String fieldName = field.getText().trim();
				System.out.println(fieldName);
				if (fieldName.equalsIgnoreCase("Collection Contact Email")) {
					String editedDateTime = driver.findElement(
							By.xpath("//h2/span[contains(text(),'Audit History')]/following::table[1]/tbody/tr[" + i
									+ "]/th[@data-label='Audit Date']/following::lightning-formatted-date-time[1]"))
							.getText();
					Pass_Message("Date and Time of edited record is displayed succesfully " + editedDateTime);
					System.out.println("Date and Time: " + editedDateTime);
					break;
				}

			}
			String collAddOriginalValue = bkdetailpage.getOriginalValue("Collection Postcode");
			String collAddUpdatedValue = bkdetailpage.getUpdatedValue("Collection Postcode");
			if (!collAddOriginalValue.isEmpty()) {
				Pass_Message("PostCode of Collection address values displayed are: Original- " + collAddOriginalValue
						+ " and Updated value- " + collAddUpdatedValue);
			}
			String delAddOriginalValue = bkdetailpage.getOriginalValue("Delivery Postcode");
			String delAddUpdatedValue = bkdetailpage.getUpdatedValue("Delivery Postcode");
			if (!delAddOriginalValue.isEmpty()) {
				Pass_Message("PostCode of Delivery Country values displayed are: Original- " + delAddOriginalValue
						+ " and Updated value- " + delAddUpdatedValue);
			}
			String consignmentOriginal = bkdetailpage.getOriginalValue("Is Deleted");
			String consignmentUpdated = bkdetailpage.getUpdatedValue("Is Deleted");
			if (!consignmentOriginal.isEmpty()) {
				Pass_Message("Consignement Line Item Deleted: Original- " + consignmentOriginal + " and Updated value- "
						+ consignmentUpdated);
			}

			Pass_Message(
					"Edited values on Collection&Delivery Address And Consignment Information is displated successfully on Audit Booking tab");
			CCD_Connectivity connectivity = new CCD_Connectivity();
			connectivity.CloseTab();
			HomePage homePage = new HomePage(driver);
			homePage.clearBookingSearch();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Original and updated valued validation on Audit Page failed");
		}

	}

	public void BK_VerifyCaseOrderIndiactor_CashAmount_with_SISPFlow() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			addinfopage.getValidServices();
			Pass_Message("Valid Service is selected");
			uiTestHelper.scrolldown("300");
			verifyCashOrderIndiactor_and_CashAmount("500");
			addinfopage.clickGetPrice();
			addinfopage.setCollectionInstruction("Test instruction for collection driver");
			addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cash Order Indicator not Verified in the Addtional Information Page");
		}
	}

	public void BK_UpdateCashAmount_Edit_SISPFlow() {
		try {
			BK_VerifyCaseOrderIndiactor_CashAmount_with_SISPFlow();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BookingPage bkpage = new BookingPage(driver);
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			uiTestHelper.scrolldown("300");
			bkpage.clickSameAsCallerInfo();
			bkpage.clickBookingnextbtn();
			goodsPage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			addinfopage.setCashAmount("600");
			Pass_Message("Cash Amount Updated Successfully");
			addinfopage.clickGetPrice();
			addinfopage.setCollectionInstruction("Test instruction for collection driver");
			addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickUpdateBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getUpdatedBookingConfirmMsg();
			bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			if (bkdetailspage.getCashAmount().trim().contains("600")) {
				Pass_Message("Updated cash Amount reflected successfully in the Additional Information Tab");
			} else {
				Fail_Message("Updated Cash Amount not Reflected in the Additional Information Tab");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Updated Cash Amount verified in the Addtional Information Tab");
		}
	}

	public void verifyCashOrderIndiactor_and_CashAmount(String cashAmount) {
		try {
			AdditionalInfoPage adtnlpage = new AdditionalInfoPage(driver);
			if (adtnlpage.verifyCashOrderIndiactor() == true) {
				adtnlpage.clickCashOrderIndicator();
				adtnlpage.setCashAmount(cashAmount);
				Pass_Message(
						"Case Order Toggle button selected and Cash Amount " + cashAmount + " Updated for the Booking");
			} else {
				Fail_Message("Case Order Toggle button not dispalyed in the Additional Information Tab");

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cash Order Indicator not Verified in the Addtional Information Page");
		}
	}

	public void createConsignmentNumber() {
		try {
			BookingRecordPage bkrecordpage = new BookingRecordPage(driver);
			bkrecordpage.clickConsignmentNote();
			Assert.assertEquals(bkrecordpage.verifySuccessMessage(), true);
			if (bkrecordpage.verifySuccessMessage() == true) {
				Pass_Message("Consignment No Created Successfully");
			} else {
				Fail_Message("Consignment No Not created");
			}
			bkrecordpage.clickCancelonConsignmentNote();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No not created and it not send in Email");
		}
	}

	public void sendConsignmentNoinEmail() {
		try {
			BookingRecordPage bkrecordpage = new BookingRecordPage(driver);
			bkrecordpage.clickConsignmentNote();
			String emailAddress = null;
			bkrecordpage.clickEmailAddressDropDown();
			uiTestHelper.propagateException();
			int emailList = bkrecordpage.getCountofEmaillist();
			System.out.println("EmailList: " + emailList);
			if (emailList >= 2) {
				bkrecordpage.clickEmailAddressDropDown();
				bkrecordpage.clickConsignmentNoteCreationBtn();
			} else {
				bkrecordpage.setOtherEmailAddress("nivetha.thirunavukarasu.osv@fedex.com");
				bkrecordpage.clickConsignmentNoteCreationBtn();
			}
			if (bkrecordpage.verifySuccessMessage() == true) {
				Pass_Message("Consignment No Created and send it to the Email Address: " + emailAddress);
			} else {
				Fail_Message("Consignment No Not created");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No not send in Email");
		}
	}

	public String verifyConsignmentNumber_AdditionalInfoTab() {
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			bkdetailspage.clickGoodsInfo();
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			if (!bkdetailspage.getConsignmentNo().isEmpty()) {
				Pass_Message("Consignment No Created and Consignment Number is : " + bkdetailspage.getConsignmentNo());
				consignmentNumber = bkdetailspage.getConsignmentNo();
			} else {
				Fail_Message("Consignment No not Created");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No not verified");
		}

		Assert.assertNotNull(bkdetailspage.getConsignmentNo(), "Consignment Number not created for booking");
		return bkdetailspage.getConsignmentNo();
	}

	public void BK_getRecentBookingfrom_BookingList(String bookingNumber) {
		// to select respective quote
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		WebElement myTable = uiTestHelper
				.waitForObject(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		driver.navigate().refresh();
		for (int i = 1; i <= row_count; i++) {
			String tempBookNum = uiTestHelper
					.waitForObject(
							By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th//a"))
					.getAttribute("title");
			if (bookingNumber.contains(tempBookNum)) {
				WebElement chkBox = uiTestHelper.waitForObject(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th//a"));
				uiTestHelper.clickJS(chkBox);
				break;
			}
		}
	}

	public String BK_verifyConSignmentNoCreation_SISP() {
		String consignmentNo = null;
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			createConsignmentNumber();
			consignmentNo = verifyConsignmentNumber_AdditionalInfoTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No not Verified for SISP Booking");
		}
		return consignmentNo;
	}

	public void Q_VerifyConsignmentNumber() {
		try {
			Q_QuoteFlow();
			Q_QuoteDetails_Validation();
			Q_ConvertToBookingSender();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			createConsignmentNumber();
			verifyConsignmentNumber_AdditionalInfoTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No not verified for Quote-Sender with SISP Booking");
		}
	}

	public void BK_VerifyConsignmentNumber_clone_SISPFlow() {
		try {
			consignmentNumber = BK_verifyConSignmentNoCreation_SISP();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BookingPage bkpage = new BookingPage(driver);
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickCloneBooking();
			uiTestHelper.scrolldown("300");
			bkpage.clickSameAsCallerInfo();
			uiTestHelper.propagateException();
			bkpage.clickBookingnextbtn();
			goodsPage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			String conNumber = bkdetailspage.getConsignmentNo();
			if (consignmentNumber.equals(conNumber)) {
				Pass_Message(
						"Consignment Number: " + conNumber + " is remains same when we edit the booking: " + conNumber);
			} else {
				Fail_Message("Consignment Number is not same when we edit the booking");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No is not verified when we Edit the Booking");
		}
	}

	public void BK_VerifyDGPricebreakdown_Edit_SISPFlow() {
		try {
			BK_SISP_DG_Flow();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BookingPage bkpage = new BookingPage(driver);
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);

			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			if (bkpage.verifyBookingTitle()) {
				uiTestHelper.scrolldown("300");
				uiTestHelper.propagateException();
				bkpage.clickBookingnextbtn();
			}
			goodsPage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();

			uiTestHelper.scrolldown("700");
			if (addinfopage.verifyPriceBreakDownDetails("EXCEPTED LITHIUM BATTERIES")) {
				Pass_Message("DG Price details are displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("DG price breakdown details is not verified when we Edit the Booking");
		}
	}

	public void BK_CreateSpecialServiceBooking_SISPFlow() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			enableSpecialServiceBooking_on_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Booking Not Created");
		}
	}

	public void updatespecialServiceBookingDetails() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		addinfopage.enableSpecialService();
		uiTestHelper.scrolldown("700");
		String expecteddate = addinfopage.getSystemDateinFormat("dd-MMM-yyyy");
		addinfopage.setCallbackDate(expecteddate);
		String time = addinfopage.getSystemTime();
		addinfopage.setcallbackTimeInput(time);
		addinfopage.setAddtionalInfoOfSpecialService("Additional info");
		uiTestHelper.scrolldown("700");

	}

	public void updateTargetCountry_and_BusinessLocation_on_specialServiceBooking(String targetCountry,
			String BusinessLocation) {
		String defaultTargetCountry = null;
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		defaultTargetCountry = addinfopage.getTargetCountry();
		Assert.assertNotNull(defaultTargetCountry);
		Pass_Message_with_screenCapture("Default Target Country: " + defaultTargetCountry);
		addinfopage.removeDefaultTargetCountryOption();
		addinfopage.setTargetCountry(targetCountry);
		String TargetCountry = addinfopage.getTargetCountry();
		Pass_Message("Changed Target Country: " + TargetCountry);
		Assert.assertNotEquals(defaultTargetCountry, TargetCountry);
		addinfopage.setBusinessLocation(BusinessLocation);
		Pass_Message_with_screenCapture("Business Location: " + BusinessLocation);
		Pass_Message("Able to Update Target Country and Business Location");
	}

	public void enableSpecialServiceBooking_on_AdditionalInfoPage() {
		try {
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			Assert.assertEquals(addinfopage.verifyGetPrice(), true, "Get Price button Not Displayed");
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			Assert.assertEquals(addinfopage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			if (addinfopage.verifySpecialService() == true) {
				Pass_Message("Special Service Option Dispalyed in the Addtional information page of Booking");
			} else {
				Fail_Message("Special Service Option Not Dispalyed in the Addtional information page of Booking");
			}
			updatespecialServiceBookingDetails();
			addinfopage.confirmSpecialService();
			Assert.assertTrue(addinfopage.verifySepcialServiceApplied());
			if (addinfopage.verifySepcialServiceApplied()) {
				Pass_Message("Special Service applied for the Booking");
			} else {
				Fail_Message("Special Service not applied for the Booking");
			}
			uiTestHelper.scrolldown("700");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			Assert.assertNotNull(bookNum);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Booking Not Created");
		}
	}

	public void BK_ValidateSpecialServiceDetails() {
		try {

			BK_CreateSpecialServiceBooking_SISPFlow();
			BK_SpecialServiceDetailsValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details not validated in the Booking Record Page for Special Service");
		}
	}

	public void BK_SpecialServiceDetailsValidation() {
		try {
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickGoodsInfo();
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			uiTestHelper.scrolldown("300");
			if (bkdetailspage.isSpecialServiceDispalyed()) {
				Pass_Message("Special Service is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service is not Saved in the Additional Information Tab");
			}
			if (!bkdetailspage.getContactName().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Contact Name: " + bkdetailspage.getContactName()
						+ " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Contact Name is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getTelephoneNumber().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Telephone Number: "
						+ bkdetailspage.getTelephoneNumber() + " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Telephone Number is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getTargetCountry().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Target Country: "
						+ bkdetailspage.getTargetCountry() + " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Target Country is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getPostalcode().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Postal Code: " + bkdetailspage.getPostalcode()
						+ " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Postal Code is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getTown().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Town: " + bkdetailspage.getTown()
						+ " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Town is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getCallbackBefore().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Callback Before: "
						+ bkdetailspage.getCallbackBefore() + " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Callback Before is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getCallbackDate().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Callback Date: " + bkdetailspage.getCallbackDate()
						+ " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message("Special Service -Callback Date is not dispalyed in the Additional Information Tab");
			}
			if (!bkdetailspage.getAdditionalInfo().isEmpty()) {
				Pass_Message_withoutScreenCapture("Special Service - Additional Information: "
						+ bkdetailspage.getAdditionalInfo() + " is Displayed in the Additional Information Tab");

			} else {

				Fail_Message(
						"Special Service -Additional Information is not dispalyed in the Additional Information Tab");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details not dispalyed in the Booking Record Page for Special Service");
		}
	}

	public void BK_ValidateSpecialServiceStatus() {
		try {

			BK_CreateSpecialServiceBooking_SISPFlow();
			// driver.navigate().refresh();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			String bookingStatus = bkdetailspage.getBookingStatus();
			if (bookingStatus.contains("SP")) {
				Pass_Message("Special Service Booking Status Validated Successfully and status is: " + bookingStatus);
			} else {
				Fail_Message("Special Service Booking Status Not Validated");
			}
			uiTestHelper.propagateException();
			bkdetailspage.clickBookingHistroy();
			Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
			BK_getRecentBookingfrom_BookingList(bookNum);
			String bookingStatusofSpecialService = bkdetailspage.getBookingStatus();
			System.out.println(bookingStatusofSpecialService);
			if (bookingStatusofSpecialService.contains("733")) {
				Pass_Message("Special Service Booking Status Validated Successfully and status is: "
						+ bookingStatusofSpecialService);
			} else {
				Fail_Message("Special Service Booking Status Not Validated");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking Staus of Special Service Not Validated");
		}
	}

	public void BK_EditSpecialServiceBooking_Error() {
		try {

			BK_CreateSpecialServiceBooking_SISPFlow();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickEditBooking();
			if (bkdetailspage.verifyErrorMessage()) {
				Pass_Message("Special Service Booking is Not Editable with Error msg: "
						+ bkdetailspage.getSpecialServiceErrorMsg());
			} else {
				Fail_Message("Special Service Booking is Editable");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Booking is Editable");
		}
	}

	public void BK_VerifySpecialServiceContact_Address_MirroredFrom_Caller() {
		try {
			BK_CreateSpecialServiceBooking_SISPFlow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingInfo();
			String callerName = bkdetailspage.getCallerName();
			String callerInfo = bkdetailspage.getCallerInfo();
			String callerPhone = bkdetailspage.getCallerPhone();
			String callerPostal = bkdetailspage.getCallerPostal();
			String callerTown = bkdetailspage.getCallerTown();
			String callerCountry = bkdetailspage.getCallerCountry();
			bkdetailspage.clickBookingInfo();
			uiTestHelper.propagateException();
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			if (bkdetailspage.getSenderorReceiver().equalsIgnoreCase(callerInfo)) {
				Pass_Message_withoutScreenCapture("Sender/Receiver Info: " + bkdetailspage.getSenderorReceiver()
						+ " Mirrored from caller Info: " + callerInfo);
			}
			if (bkdetailspage.getContactName().equalsIgnoreCase(callerName)) {
				Pass_Message_withoutScreenCapture("Contact Name: " + bkdetailspage.getContactName()
						+ " Mirrored from caller Name: " + callerName);
			}
			if (bkdetailspage.getTelephoneNumber().equalsIgnoreCase(callerPhone)) {
				Pass_Message_withoutScreenCapture("Contact Phone: " + bkdetailspage.getTelephoneNumber()
						+ " Mirrored from caller Phone: " + callerPhone);
			}
			if (bkdetailspage.getTargetCountry().equalsIgnoreCase(callerCountry)) {
				Pass_Message_withoutScreenCapture("Target Country: " + bkdetailspage.getTargetCountry()
						+ " Mirrored from caller Country: " + callerCountry);
			}
			if (bkdetailspage.getPostalcode().equalsIgnoreCase(callerPostal)) {
				Pass_Message_withoutScreenCapture("Postal Code: " + bkdetailspage.getPostalcode()
						+ " Mirrored from caller Postal: " + callerPostal);
			}
			if (bkdetailspage.getTown().equalsIgnoreCase(callerTown)) {
				Pass_Message_withoutScreenCapture(
						"Town: " + bkdetailspage.getTown() + " Mirrored from caller Town: " + callerTown);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Contact and Address Details not Mirrored from Caller Information");
		}
	}

	public void BK_VerifyAllProductOptions() {
		try {
			BK_BookingPage_SIRP();
			verifyAllProductOptions();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("All product options not displayed in the respective Page");
		}
	}

	public void BK_VerifyProductOptions_enable5Options_Error() {
		try {
			BK_BookingPage_SIRP();
			verifyProductOptions_enable5Options_Error();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.VerifyProductSelectionError(), true);
			if (addinfopage.VerifyProductSelectionError()) {
				Pass_Message(
						"Getting Error When we select greater than 3 Product Options for the Booking and the Error Messgae is: "
								+ addinfopage.getProductSelectionErrorMsg());
			} else {
				Fail_Message("Able to select the 5 product options and conitnue with the Booking");
			}
			addinfopage.submitProductOption();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Product option Error not verified for the Booking");
		}
	}

	public void Q_VerifyProductOptions_enable5Options_Error() {
		try {
			Q_QuoteInfoPage();
			verifyProductOptions_enable5Options_Error();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.VerifyProductSelectionError(), true);
			if (addinfopage.VerifyProductSelectionError()) {
				Pass_Message(
						"Getting Error When we select greater than 4 Product Options for the Quote and the Msg is: "
								+ addinfopage.getProductSelectionErrorMsg());
			} else {
				Fail_Message("Able to select the 5 product options and conitnue with the Quote");
			}
			addinfopage.cancelProductOption();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Product option Error not verified for the Quote");
		}
	}

	public void Q_VerifyAllProductOptions() {
		try {
			Q_QuoteInfoPage();
			verifyAllProductOptions();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("All product options not displayed in the respective Page");
		}
	}

	public void verifyProductOptions_enable5Options_Error() {
		String[] product_options = { "Late Pickup", "Saturday Delivery", "Hazardous Goods", "Formal Clearance" };
		try {
			GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			if (goodsinfopage.verifyEnhancedLiabilityIndicator()) {
				Pass_Message("Enhanced Liability Indicator is displayed in the Goods Info Page");
				goodsinfopage.clickEnhancedLiability();
				goodsinfopage.setEnhancedLiabilityInput("20");
			} else {
				Fail_Message("Enhanced Liability Indicator is not displayed in the Goods Info Page");
			}
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional info page not Displayed");
			addinfopage.clickValidServices();
			addinfopage.selectOptions("EX200");
			Assert.assertEquals(addinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			if (addinfopage.verifyProductOptionTable()) {
				addinfopage.getAllavailableProductOptions();
			}
			try {
				for (int i = 0; i <= product_options.length - 1; i++) {
					addinfopage.enableProductOptions(product_options[i]);
				}
			} catch (Exception e) {
				Fail_Message("Not able to add Product Option!");
				addinfopage.cancelProductOption();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("All product options not displayed in the respective Page");
		}
	}

	public void verifyAllProductOptions() {
		try {
			GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			if (goodsinfopage.verifyEnhancedLiabilityIndicator()) {
				Pass_Message("Enhanced Liability Indicator is displayed in the Goods Info Page");
			} else {
				Fail_Message("Enhanced Liability Indicator is not displayed in the Goods Info Page");
			}
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional info page not Displayed");
			addinfopage.clickValidServices();
			addinfopage.selectOptions("EX200");
			Assert.assertEquals(addinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			if (addinfopage.verifyProductOptionTable()) {
				addinfopage.getAllavailableProductOptions();
			}
			addinfopage.cancelProductOption();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("All product options not displayed in the respective Page");
		}
	}

	public void BK_VerifyProductOptions_CSImportServiceEnabled_RIRPFlow() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			if (adtnlinfopage.verifyProductOptionsSelected("CS Arranged Import Services")) {
				Pass_Message("CS Import Service Option Enabled for RIRP booking by Default");
			} else {
				Fail_Message("CS Import Service Option is Disabled for RIRP booking by default");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("CS Import Service option not displayed in the respective Page");
		}
	}

	public void BK_verifyBookingwithHoldStatus() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			Assert.assertEquals(addinfopage.verifyGetPrice(), true, "Get Price button Not Displayed");
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			Assert.assertEquals(addinfopage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			addinfopage.enableHold();
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			try {
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

				String book = addinfopage.getBookingConfirmMsg();
				bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
				if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
					Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

				} else {
					Fail_Message("Booking failed");

				}
			} catch (Exception e) {
				// TODO: handle exception
				Fail_Message("Booking Failed");
				CCD_Connectivity conn = new CCD_Connectivity();
				conn.CloseTab();
			}
			Assert.assertNotNull(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingHistroy();
			Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
			BK_getRecentBookingfrom_BookingList(bookNum);
			String bookingStatus = bkdetailspage.getBookingStatus();
			Assert.assertEquals(bookingStatus, "HLD");
			if (bookingStatus.contains("HLD")) {
				Pass_Message("Booking is in Hold and status is: " + bookingStatus);
			} else {
				Fail_Message("Booking with Hold Status Not Validated");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			Fail_Message("Booking with Hold Status not created Successfully");
		}

	}

	public void BK_verifySpecialServiceBooking_withHoldStatus() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			adtnlinfopage.enableSpecialService();
			if (adtnlinfopage.verifyHoldOption() == false) {
				Pass_Message("Hold option disabled for Special Service Booking");
			} else {
				Fail_Message("Error: Hold option Enabled for Special Service Booking");

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Able to create Special Service Booking with Hold Status");
		}
	}

	// SISP Pages validations

	public void BK_BookingPagesValidation() {
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		BookingDrpDown();
		// to select respective quote
		BK_getRecentBookingfrom_BookingList(bookNum);
		try {

			// booking Information page
			try {
				uiTestHelper.propagateException();
				bkdetailspage.clickBookingInfo();
				String callerInfo = bkdetailspage.getCallerInfo();
				if (callerInfo.equals("Sender")) {
					Pass_Message("Caller Information is displayed successfully as Sender");
				} else {
					Fail_Message("Caller Information is not displayed");
				}
				String paymentTerms = bkdetailspage.getPaymentTerms();
				if (paymentTerms.equals("Sender")) {
					Pass_Message("Payment Terms is displayed successfully as Sender");
				} else {
					Fail_Message("Payment Terms is not displayed");
				}

				String callerName = bkdetailspage.getCallerName();
				if (callerName.isEmpty()) {
					Fail_Message("Caller name is not displayed successfully");

				} else {
					Pass_Message("Caller name is displayed successfully");
				}

			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Booking Information Page validation failed");
			}
			// Good Information
			try {
				uiTestHelper.propagateException();
				bkdetailspage.clickGoodsInfo();
				String content = bkdetailspage.getGoodsContent();
				// String content=conTent.getText();
				if (content.isEmpty()) {
					Fail_Message("Description is not displayed");
				} else {
					Pass_Message("Contents is displayed successfully as: " + content);
				}
				String desc = bkdetailspage.getGoodsDescription();
				if (desc.isEmpty()) {
					Fail_Message("Description is not displayed");
				} else {
					Pass_Message("Description is displayed successfully as: " + desc);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Good Information Page validation failed");
			}

			// Consignment Information
			try {
				bkdetailspage.clickMoretab();
				bkdetailspage.clickConsignmentInfo();
				uiTestHelper.propagateException();
				bkdetailspage.clickLineItemLink();
				String type = bkdetailspage.getConsignmentType();
				if (type.isEmpty()) {
					Fail_Message("Con Type is not dsplayed");
				} else {
					Pass_Message("Con type is displayed successfully as: " + type);
				}
				String quantity = bkdetailspage.getQuantity();
				if (quantity.isEmpty()) {
					Fail_Message("Quantity is not displayed");
				} else {
					Pass_Message("Quantity is displayed successfully as: " + quantity);
				}

				String length = bkdetailspage.getLength();
				if (length.isEmpty()) {
					Fail_Message("Length is not displayed");
				} else {
					Pass_Message("Length is displayed successfully as: " + length + "cm");
				}
				String width = bkdetailspage.getWidth();
				if (width.isEmpty()) {
					Fail_Message("Width is not displayed successfully");
				} else {
					Pass_Message("Width is displayed successfully: " + width + "cm");
				}
				String height = bkdetailspage.getHeight();
				if (height.isEmpty()) {
					Fail_Message("Height is not displayed successfully");
				} else {
					Pass_Message("Height is displayed successfully: " + height + "cm");
				}
				String weight = bkdetailspage.getWeight();
				if (weight.isEmpty()) {
					Fail_Message("Weight is not displayed successfully");
				} else {
					Pass_Message("Weight is displayed successfully: " + weight + "Kg");
				}

				CMOD_FF_Reusable Reusable = new CMOD_FF_Reusable();
				Reusable.internal_tabclose();
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Consignment Information Page validation failed");
			}

			// Additional Information
			try {
				bkdetailspage.clickMoretab();
				bkdetailspage.clickAdditionalInfo();
				String collDate = bkdetailspage.getCollectionDate();
				if (collDate.isEmpty()) {
					Fail_Message("Collection Date is not displayed");
				} else {
					Pass_Message("Collection Date is displayed successfully as: " + collDate);
				}
				String lob = bkdetailspage.getLob();
				if (lob.isEmpty()) {
					Fail_Message("Line of Business is not displayed");
				} else {
					Pass_Message("Line of Business is displayed successfully as: " + lob);
				}
				String service = bkdetailspage.getService();
				if (service.isEmpty()) {
					Fail_Message("Service is not displayed successfully");
				} else {
					Pass_Message("Service is displayed successfully as: " + service);
				}
				String legOrderID = bkdetailspage.getLegacyOrderId();
				if (legOrderID.isEmpty()) {
					Fail_Message("Legacy Order Id is not displayed successfully");
				} else {
					Pass_Message("Legacy Order Id is displayed successfully as : " + legOrderID);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Additional Information Page validation failed");

			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exeption: Booking Pages Valdations failed");
		}
	}

	// Quote Summary
	public void Q_QuoteSummary() {
		try {
			uiTestHelper.propagateException();
			String quoteNumSumm = Database_Connection.retrieveTestData("Q_SUMM", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			quoteNumSumm = "Q-006090";
			System.out.println("quoteNumSumm = " + quoteNumSumm);
			BookingDrpDown();

			// to select respective quote
			WebElement myTable = driver
					.findElement(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			for (int i = 1; i <= row_count; i++) {
				String tempQuoteNum1 = driver
						.findElement(By.xpath(
								"(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th/span"))
						.getText();
				System.out.println("tem p q num " + tempQuoteNum1);
				if (quoteNumSumm.contains(tempQuoteNum1)) {
					Actions ob = new Actions(driver);
					WebElement chkBox = driver.findElement(By.xpath(
							"(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th/span/a"));
					ob.click(chkBox).perform();
					break;
				}
			}

			QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
			uiTestHelper.propagateException();
			quoteDetailPage.clickQuoteSummaryButton();

			if (quoteDetailPage.isQuoteSummaryDisplayed()) {
				Pass_Message("Quote Summary Screen is displayed");
			} else {
				Fail_Message("Quote Summary screen is not displayed");
			}
			uiTestHelper.propagateException();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			quoteDetailPage.clickSendEmail();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.navigate().refresh();

			String email = quoteDetailPage.getEmailId();

			if (email.isEmpty()) {
				Fail_Message("Quote Summary - Sent To is not displayed");
			} else {
				Pass_Message("Quote Summary - Sent To is displayed as: " + email);
			}

			String sentDate = quoteDetailPage.getQuoteSummarySentDate();

			if (sentDate.isEmpty()) {
				Fail_Message("Quote Summary - Sent Date is not displayed");
			} else {
				Pass_Message("Quote Summary - Sent Date is displayed as: " + sentDate);
			}

			String sentBy = quoteDetailPage.getQuoteSummarySentBy();

			if (sentBy.isEmpty()) {
				Fail_Message("Quote Summary - Sent By is not displayed");
			} else {
				Pass_Message("Quote Summary - Sent By is displayed as: " + sentBy);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Summary validation failed");
		}
	}

	public void BK_DG_VerifyDG_Section() {

		try {
			BK_BookingPage_SISP();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			setDGGoods("3010", "2", "Lithium Battery", 1);
			if (goodsPage.verifyDGFullDesc() == true) {
				Pass_Message("Dangerous Goods - Full Description displayed in the Goods Information Page"
						+ goodsPage.getDGFullDescription());
			} else {
				Fail_Message("Dangerous Goods - Full Description Not displayed in the Goods Information Page");
			}
			if (goodsPage.verifyDGPackagingGroup() == true) {
				Pass_Message("Packaging Group of Dangerous Goods displayed in the Goods Information Page"
						+ goodsPage.getPacakagingGroup());
			} else {
				Fail_Message("Packaging Group of Dangerous Goods Not displayed in the Goods Information Page");
			}
			if (goodsPage.verifyDGUOM() == true) {
				Pass_Message("Unit of Measurement of Dangerous Goods displayed in the Goods Information Page"
						+ goodsPage.getUOM());
			} else {
				Fail_Message("Unit of Measurement of Dangerous Goods Not displayed in the Goods Information Page");
			}
			goodsPage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Dangerous Goods Section Not Displayed Successfully");
		}

	}

	public void BK_DG_VerifyDG_Error() {
		try {
			BK_BookingPage_SISP();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page");
			goodsPage.clickGoodsInfoNextBtn();
			if (goodsPage.verifyErrorMessage() == true) {
				Pass_Message("Error Message Dispalyed as Mandatory Fields of DG needs to be entered by the CSR ");
			} else {
				Fail_Message("Error Message is not Dispalyed even CSR not entered the Mandatory fields of DG");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("DG error Message Not Verified");
		}

	}

	public void BK_DG_VerifyDG_TypesofGoodsandUNNumber() {

		try {
			BK_BookingPage_SISP();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page");
			// goodsPage.clickTypeOfDangGood();
			String dgoptions = "//*[@name='typeOfDg']/following::lightning-base-combobox-item";
			List<WebElement> DGlist = driver.findElements(By.xpath(dgoptions));
			List<WebElement> dgOption = driver.findElements(By.xpath(dgoptions + "/span[@class='slds-media__body']"));
			int listsize = DGlist.size();
			for (int i = 0; i < listsize; i++) {
				if (i > 0) {
					// goodsPage.clickTypeOfDangGood();
				}
				WebElement option = DGlist.get(i);
				option.click();
				/*
				 * //if (goodsPage.verifyUNNumberDangGoodInput() == true) {
				 * Pass_Message("UN Number dispalyed for the Dangerous Good Option " +
				 * dgOption.get(i).getText() + " in Goods Information Page"); } else {
				 * Fail_Message("UN Number of DG good Options are not populated"); } }
				 *
				 */ }
		} catch (

		Exception e) {
			e.printStackTrace();
			Fail_Message("DG Section is Not Verified with Type of DG with Respective UN Number");
		}

	}

	public void BK_DG_VerifyFullyRegulatedDangerousGoods() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			setGoodsInformation("Non-Document", "TestDescription", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			setDGGoods("2002", "2", "Lithium Battery", 1);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Fully Regulated Dangerous Goods Not Verified");
		}
	}

	public void BK_VerifyBookingRemark() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BK_BookingRemark();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking Remark not Verified in the Record Page");
		}
	}

	public void BK_BookingRemark() {
		try {
			BookingRecordPage bkrecordpage = new BookingRecordPage(driver);
			bkrecordpage.clickBookingRemark();
			bkrecordpage.setRemarks("Test Booking Remarks");
			bkrecordpage.saveBookingRemark();
			uiTestHelper.propagateException();
			if (bkrecordpage.verifySuccessMessage() == true) {
				bkrecordpage.clickRemarkTab();
				int size = bkrecordpage.getRemarkTableSize();
				String csrNameLoc[] = bkrecordpage.getCSRNameLoc(size).split("/");
				String CSRname = csrNameLoc[0];
				String CSRLoc = csrNameLoc[1];
				String remarkCreatedDateTime = bkrecordpage.getRemarkCreatedDate(size);
				Pass_Message("Booking Remark Created Successfully and Remark Created CSR Name: " + CSRname
						+ " CSR Location: " + CSRLoc + " Created Date Time: " + remarkCreatedDateTime);
			} else {
				Fail_Message("Booking Remark Not Created");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking Remark not Verified in the Record Page");
		}
	}

	public void BK_verifyBookingClone() {
		try {
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BookingPage bkpage = new BookingPage(driver);
			GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
			ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
			AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickCloneBooking();
			if (bkpage.verifyBookingPageTitleByClone()) {
				Pass_Message("Navigated to Booking Page after Clicking Clone");
				uiTestHelper.scrolldown("700");
				if (bkpage.verifyBookingNextBtnByClone()) {
					Pass_Message("Booking Details are auto Populated by clone");
				} else {
					Fail_Message("Booking Details are not auto Populated by clone");
				}
			} else {
				Fail_Message("Booking Page is not displayed after clicking Clone");
			}
			bkpage.clickBookingnextbtn();
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("600");
			additionalInfoPage.clickViewSummary();
			additionalInfoPage.clickConfirmBooking();
			String book = additionalInfoPage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Clone Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking Clone failed");

			}
			Assert.assertNotNull(bookNum, "SISP Clone Booking not Created");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invalid Test data not Verified");
		}
	}

	public void BK_verifyStackableGoods() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			if (coninfopage.verifyStackableToggleByDefault()) {
				Pass_Message("Stackable is enabled By default");
				coninfopage.clickStackableToggle();
				if (coninfopage.verifyAdvisoryMessage()) {
					Pass_Message("Advisory Message is displayed and Message is: " + coninfopage.getAdvisoryMessage());
				} else {
					Fail_Message("Advisory Message is not Displayed after clicking Stackable");
				}
			} else {
				Fail_Message("Stackable is not enabled By default");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invalid Test data not Verified");
		}
	}

	public void Q_VerifyContactDetails_against_CollectionDetails() {
		try {
			Q_QuoteFlow();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
			quoteDetailPage.clickConvertToBookingBtn();
			BookingPage bookingPage = new BookingPage(driver);
			Assert.assertEquals(bookingPage.verifyBookingTitle(), true);
			if (bookingPage.verifyBookingTitle()) {
				String callerName = bookingPage.getCallerName();
				String callerEmail = bookingPage.getCallerEmail();
				String callerPhone = bookingPage.getCallerPhone();
				uiTestHelper.scrolldown("300");
				bookingPage.clickSameAsCallerInfo();
				if (callerName.equals(bookingPage.getContactName()) && callerEmail.equals(bookingPage.getContactEmail())
						&& callerPhone.equals(bookingPage.getContactPhone())) {
					Pass_Message("Contact details Matched with Caller Information");
				} else {
					Fail_Message("Contact details Not Matched with Caller Information");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contact details not verified against Collection details of Caller for the Quote");
		}
	}

	public void Q_getRecentQuotefrom_BookingList(String quoteNum) {
		// to select respective quote

		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		driver.navigate().refresh();
		WebElement myTable = uiTestHelper
				.waitForObject(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		// driver.navigate().refresh();
		System.out.println(quoteNum);
		for (int i = 1; i <= row_count; i++) {
			String tempBookNum = uiTestHelper
					.waitForObject(
							By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th//a"))
					.getAttribute("title");
			System.out.println(tempBookNum);
			if (quoteNum.contains(tempBookNum)) {
				Actions ob = new Actions(driver);
				WebElement chkBox = uiTestHelper.waitForObject(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th//a"));
				ob.click(chkBox).perform();
				break;
			}
		}

	}

	public void BK_verifyContactDetails_against_CallerInfo() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			String callerName = bookingpage.getCallerName();
			String callerEmail = bookingpage.getCallerEmail();
			String callerPhone = bookingpage.getCallerPhone();
			uiTestHelper.scrolldown("300");
			setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
			uiTestHelper.scrolldown("700");
			bookingpage.clickSameAsCallerInfo();
			System.out.println(bookingpage.getContactName());
			if (callerName.equals(bookingpage.getContactName()) && callerEmail.equals(bookingpage.getContactEmail())
					&& callerPhone.equals(bookingpage.getContactPhone())) {
				Pass_Message("Contact details Matched with Caller Information");
			} else {
				Fail_Message("Contact details Not Matched with Caller Information");
			}
			bookingpage.setContactName("Arjun");
			System.out.println(bookingpage.getContactName());
			if (!callerName.equals(bookingpage.getContactName())) {
				Pass_Message("Contact details is Editable after clicking Same as Caller info Checkbox");
			} else {
				Fail_Message("Contact details is not Editable after clicking Same as Caller info Checkbox");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Contact details not verified against Collection details of Caller for the Quote");
		}
	}

	public void BK_DG_VerifyAir_DangerousGoods() {
		try {
			BK_BookingPage_SISP();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Non-Document", "TestDesc", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page");
			// goodsPage.setDangerousGoods("Fully Regulated Dangerous Goods");
			goodsPage.setUNNumber("1012");
			// goodsPage.searchRegulateGoods();
			goodsPage.clickAirOnlyDG();
			if (goodsPage.verifyAirOnlyDGMsg() == true) {
				Pass_Message("Verified Air Only DG with the Message: " + goodsPage.getAirDGMsg());
			} else {
				Fail_Message("Not Verified Air Type DG");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified Air Type DG");
		}
	}

	public void BK_verifyCollectionEmail_with_bookingSummary() {
		try {
			BK_RIRP_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingSummary();
			bkdetailspage.clickEmailField();
			String collectionEmail = bkdetailspage.getCollectionEmail();
			System.out.println(collectionEmail);
			bkdetailspage.clickCollectionEmail();
			bkdetailspage.clickSendEmail();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Email Sent to: " + collectionEmail + " and the confirm message is: ");
			} else {
				Fail_Message("Email not send to: " + collectionEmail + " from Booking Summary");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified Collection Email with Booking Summary");
		}
	}

	public void BK_verifyCallerEmail_with_bookingSummary() {
		try {
			BK_RIRP_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingSummary();
			bkdetailspage.clickEmailField();
			String callerEmail = bkdetailspage.getCallerEmail();
			System.out.println(callerEmail);
			bkdetailspage.clickCallerEmail();
			bkdetailspage.clickSendEmail();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Email Sent to: " + callerEmail + " and the confirm message is: ");
			} else {
				Fail_Message("Email not send to: " + callerEmail + " from Booking Summary");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified callerEmail Email with Booking Summary");
		}
	}

	public void BK_verifyOtherEmail_with_bookingSummary() {
		try {
			BK_RIRP_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingSummary();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			String emailAddress = "prachi.sharma.osv@fedex.com";
			System.out.println(emailAddress);
			bkdetailspage.setEmailAddress(emailAddress);
			bkdetailspage.clickSendEmail();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Email Sent to: " + emailAddress + " and the confirm message is: ");
			} else {
				Fail_Message("Email not send to: " + emailAddress + " from Booking Summary");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the Other Email with Booking Summary");
		}
	}

	public void BK_verifyCollectionEmail_with_consignmentNote() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			String collectionEmail = null;
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Created Successfully");
			} else {
				Fail_Message("Consignment Number is Not Created Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			collectionEmail = bkdetailspage.getCollectionEmail();
			System.out.println(collectionEmail);
			bkdetailspage.clickCollectionEmail();
			verifyConsignementEmailforBooking(collectionEmail);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the Collection Email with Consignment Note");
		}
	}

	public void BK_verifyConsignmentdimension_with_decimalValues() {
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "10.10");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12.12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "12.12");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "12.10");
			uiTestHelper.waitForObject(By.xpath("(//button[@name='[object Object]'])[1]")).click();
			uiTestHelper.waitForObject(By.xpath("//lightning-base-combobox-item[@data-value='box']")).click();
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Dimension Details are entered with decimal values in Consignment Information Page");
			coninfopage.clickConsignmentInfoNextBtn();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			if (adtnlinfopage.verifyTitle()) {
				Pass_Message("Decimal Values for Dimensions of Consignment are accepted");
			} else {
				Fail_Message("Decimal Values for Dimensions of Consignment are not accepted");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Details with Decimal values are not verified");
		}
	}

	public void BK_verifyGoodsInfoDocument_with_envelopConsignment() {
		try {
			BK_BookingPage_SISP();
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			setGoodsInformation("Document", "TestDesc", "20", "12345");
			goodsPage.clickGoodsInfoNextBtn();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			if (coninfopage.verifyTypeEnvelope()) {
				Pass_Message(
						"Goods Type -Document is selected then Consignment Type - Envelope is selected By Default");
			} else {
				Fail_Message(
						"Goods Type -Document is selected then Consignment Type - Envelope is not selected By Default");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Goods Info of type -Document with Consignment Envelope is not verified");
		}
	}

	public void BK_verifySpecialServiceCollectionAddress_with_NewCustomer_SISPFlow() {
		try {
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			String Coll_CustName = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Coll_Add = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			String Coll_Town = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			String Coll_PostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Coll_Country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			BK_bookingPage_createNewCustomerSISPBooking(Coll_CustName, Coll_Add, Coll_Town, Coll_PostCode,
					Coll_Country);
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			enableSpecialServiceBooking_on_AdditionalInfoPage();
			BookingDrpDown();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			uiTestHelper.scrolldown("300");
			if (bkdetailspage.getTargetCountry().equalsIgnoreCase(Coll_Country)) {
				Pass_Message_withoutScreenCapture("Target Country: " + bkdetailspage.getTargetCountry()
						+ " Mirrored from caller Country: " + Coll_Country);
			}
			if (bkdetailspage.getPostalcode().equalsIgnoreCase(Coll_PostCode)) {
				Pass_Message_withoutScreenCapture("Postal Code: " + bkdetailspage.getPostalcode()
						+ " Mirrored from caller Postal: " + Coll_PostCode);
			}
			if (bkdetailspage.getTown().equalsIgnoreCase(Coll_Town)) {
				Pass_Message_withoutScreenCapture(
						"Town: " + bkdetailspage.getTown() + " Mirrored from caller Town: " + Coll_Town);
			}
			Pass_Message(
					"Special Service Contact and Address Details Mirrored from Caller Information for Non Account-SISP Booking");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Contact and Address Details not Mirrored from Caller Information");
		}
	}

	public void BK_bookingPage_createNewCustomerSISPBooking(String Coll_CustName, String Coll_Add, String Coll_Town,
			String Coll_PostCode, String Coll_Country) {
		try {
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			BookingPage bkpage = new BookingPage(driver);
			HomePage homepage = new HomePage(driver);
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(driver);
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerIdentification();
			cipage.clickNewCustomer();
			cipage.clickBookingRadioButton();
			bkpage.setCallerName("Prachi Sharma");
			bkpage.setCallerPhone("987564321");
			bkpage.setCallerEmail("test@gmail.com");
			List<WebElement> list = bkpage.getCallerInfoButtons();
			list.get(0).click();
			list.get(1).click();
			bkpage.setCollectionCountry(Coll_Country);
			bkpage.setCollectionPostal(Coll_PostCode);
			bkpage.setCollectionTown(Coll_Town);
			bkpage.setCollectionCustomerName(Coll_CustName);
			bkpage.setCollectionAddress(Coll_Add);
			bkpage.clickCollectionValidatebtn();
			uiTestHelper.propagateException();
			bkpage.setDelCustomerName(Deliv_Cust_Name);
			bkpage.setDeliveryAddress(Deliv_Add);
			bkpage.setDeliveryCountry(Deliv_Country);
			bkpage.setDeliveryPostal(Deliv_PostCode);
			bkpage.setDeliveryTown(Deliv_Town);
			bkpage.deliveryValidatebtn();
			uiTestHelper.propagateException();
			bkpage.setContactName("Arjun Rampal");
			bkpage.setContactPhone("+91 4545898985");
			bkpage.setContactEmail("Arjun_Rampal@gmail.com");
			Pass_Message("Details entered successfully in Booking Information Page for New Customer Booking");
			bkpage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking Page of New Customer is not Worked");
		}
	}

	public void BK_verifyCallerEmail_with_consignmentNote() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			String callerEmail = null;
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Creadted Successfully");
			} else {
				Fail_Message("Consignment Number is Not Creadted Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			callerEmail = bkdetailspage.getCallerEmail();
			System.out.println(callerEmail);
			bkdetailspage.clickCallerEmail();
			verifyConsignementEmailforBooking(callerEmail);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the caller Email with Consignment Note");
		}

	}

	public void verifyConsignementEmailforBooking(String email) {
		try {
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.selectLanguage("French");
			bkdetailspage.clickConsignmentNoteCreationBtn();
			try {
				if (bkdetailspage.verifySuccessMessage()) {
					Pass_Message("Email Sent to: " + email + " and the confirm message is: Email Sent Successfully!");
				} else {
					Fail_Message("Email not send to: " + email + " from Booking Summary");
				}
			} catch (NoSuchElementException e) {
				// TODO: handle exception
				Fail_Message("Email is not sent to respective email address: " + email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment Email not verified");
		}
	}

	public void BK_verifyOtherEmail_with_consignmentNote() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			String emailAddress = "nivetha.thirunavukarasu.osv@fedex.com";
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Creadted Successfully");
			} else {
				Fail_Message("Consignment Number is Not Creadted Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			System.out.println(emailAddress);
			uiTestHelper.propagateException();
			bkdetailspage.setEmailAddress(emailAddress);
			verifyConsignementEmailforBooking(emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the Other Email with Consignment Note");
		}

	}

	public void BK_defaultEmail_with_consignmentNote() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Creadted Successfully");
			} else {
				Fail_Message("Consignment Number is Not Creadted Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			uiTestHelper.propagateException();
			String emailAddress = bkdetailspage.getEmailAddress();
			System.out.println(emailAddress);
			String callerEmail = "nivetha.thirunavukarasu.osv@fedex.com";
			if (emailAddress.equalsIgnoreCase(callerEmail)) {
				Pass_Message("Collection Mail is default mail address in Consignment Note Screen and "
						+ "Collection Email address is : " + emailAddress);
			} else {
				Fail_Message("Collection Mail is default mail address in Consignment Note Screen");
			}
			bkdetailspage.clickCancelonConsignmentNote();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the default Email with Consignment Note");
		}
	}

	/**
	 * booking summary Email Language verification
	 */
	public void bk_bookingSummary_emailLanguage_Validation() {
		try {
			String language = "French";
			BK_SISP_Flow_without_Validation();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickBookingSummary();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			String emailAddress = "nivetha.thirunavukarasu.osv@fedex.com";
			System.out.println(emailAddress);
			bkdetailspage.setEmailAddress(emailAddress);
			bkdetailspage.selectLanguage(language);
			Pass_Message("User is able to select email language as " + language);
			bkdetailspage.clickSendEmail();
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Email Sent to: " + emailAddress + " with the Specified Language");
			} else {
				Fail_Message("Email not send to: " + emailAddress + " from Booking Summary");
			}
			driver.get("https://outlook.office365.com");
			WebElement email = driver.findElement(By.name("loginfmt"));
			email.sendKeys(emailAddress);
			driver.findElement(By.xpath("//input[@type='submit']")).click();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Summary validations failed");
		}
	}

	public void bk_consignmentNote_emailLanguage_Validation() {
		try {
			String language = "French";
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			String emailAddress = "nivetha.thirunavukarasu.osv@fedex.com";
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Creadted Successfully");
			} else {
				Fail_Message("Consignment Number is Not Creadted Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			System.out.println(emailAddress);
			uiTestHelper.propagateException();
			bkdetailspage.setEmailAddress(emailAddress);
			bkdetailspage.selectLanguage("French");
			Pass_Message("User is able to select email language as " + language);
			bkdetailspage.clickConsignmentNoteCreationBtn();
			try {
				if (bkdetailspage.verifySuccessMessage()) {
					Pass_Message("Email Sent to: " + emailAddress + " with the selected language");
				} else {
					Fail_Message("Email not send to: " + emailAddress + " from Booking Summary");
				}
			} catch (NoSuchElementException e) {
				// TODO: handle exception
				Fail_Message("Email is not sent to respective email address: " + emailAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the Other Email with Consignment Note");
		}

	}

	public void verifyOtherEmaildata_with_consignmentNote() {
		try {
			BK_BookingPage_RIRP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page_with_consignmentNote();
			BK_AdditionalInfo_Page();
			BK_getRecentBookingfrom_BookingList(bookNum);
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			uiTestHelper.propagateException();
			if (bkdetailspage.verifyConsignmentNoteCreationBtn()) {
				Fail_Message("Consignment Note Email send button is Enabled");
			} else {
				Pass_Message("Consignment Note Email send button is Disbaled");
			}
			bkdetailspage.clickCancelonConsignmentNote();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the Consignment Note Email Button");
		}
	}

	public void verifyNoServices_with_price() {
		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Town = "STOCKHOLM";
			String Deliv_PostCode = "100 05";
			String Deliv_Country = "Sweden";
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
			uiTestHelper.scrolldown("300");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			bookingpage.clickBookingnextbtn();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (adtnlinfopage.isPriceNotRetrievable()) {
				Pass_Message("Price Not Retrievable for the Service selected for the Booking and the Msg is: "
						+ adtnlinfopage.getNoServiceErrorMsg());
			} else {
				Fail_Message("Price is Retrievable for the Service selected for the Booking");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Not Verified the no services with Price for the Booking");
		}
	}

	public void Q_Validate_Multiple_Areas_for_One_PostCode_Sender_Quote() {
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String Deliv_Country = "United Kingdom";
			String Deliv_postal = "MK1 1AA";
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingPage = new BookingPage(driver);
			bookingSelectionOnHomepage(AcctName);
			newQuoteonCustomerAccPage(AcctName);
			quotePage.clickSenderButton();
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Updated Successfully");
			}
			setDeliveryAddress(Deliv_Country, Deliv_postal, Deliv_postal, Deliv_Cust_Name, Deliv_Add);
			int size = quotePage.getAddressDetailsTableSize();
			Pass_Message("Address Details are displayed based on the Delivery Country and Postal Code");
			if (size < 0) {
				Fail_Message("Address Details are not displayed based on the Delivery Country and Postal Code");
			} else {
				quotePage.selectAddressDetail();
			}
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			bookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Page validation failed");
		}
	}

	public void Q_Validate_Multiple_Postal_Codes_For_One_Town_Sender_Quote() {
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String Deliv_Country = "United Kingdom";
			String Deliv_Postal = "Milton Keynes";
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingPage = new BookingPage(driver);
			bookingSelectionOnHomepage(AcctName);
			newQuoteonCustomerAccPage(AcctName);
			quotePage.clickSenderButton();
			if (bookingPage.successMsgonAddress()) {
				Assert.assertEquals(bookingPage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Updated Successfully");
			}
			bookingPage.setDeliveryCountry(Deliv_Country);
			bookingPage.setDeliveryTown(Deliv_Postal);
			bookingPage.deliveryValidatebtn();
			int size = quotePage.getAddressDetailsTableSize();
			Pass_Message("Address Details are displayed based on the Delivery Country and Town");
			if (size < 0) {
				Fail_Message("Address Details are not displayed based on the Delivery Country and Town");
			} else {
				quotePage.selectAddressDetail();
			}
			bookingPage.setDelCustomerName(Deliv_Cust_Name);
			bookingPage.setDeliveryAddress(Deliv_Add);
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			bookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Page validation failed");
		}
	}

	public void Check_Green_Tick_For_Available_Services_For_Quote() {
		try {
			CCD_CI_Booking ci_quote = new CCD_CI_Booking();
			// Call the global search method and pass the quote in and press enter
			String referenceNumber = "Q-018874";
			ci_quote.setValueInGlobalSearch(referenceNumber);

			// Select reference number item link from the search
			GlobalSearch globalSearch = new GlobalSearch(driver);
			globalSearch.selectReferenceNumber(referenceNumber);

			// Click the Additional Info Tab on the search screen
			QuoteDetailPage quotedetailspage = new QuoteDetailPage(driver);
			quotedetailspage.clickQuoteAdditionalInfoTab();
			uiTestHelper.scrolldown("700");
			// Validate the Green tick is on the service
			boolean isGreenTickPresent = quotedetailspage.checkGreenTick("Express");
			Assert.assertTrue(isGreenTickPresent, "Check Green Tick is Present");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Page validation failed");
		}
	}

	/**
	 * Agent is able to convert a quote to booking on the same day without
	 * recalculating the price
	 *
	 * @
	 */
	public void Q_ConvertToBookingSender_SameDayQuote() {
		try {
			BookingPage bookingPage = new BookingPage(driver);
			QuoteDetailPage quotedetailspage = new QuoteDetailPage(driver);
			Pass_Message("User has logged in  to salesforce application as CSR");
			Q_QuoteInfoPage();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage_SpecialServices();
			Pass_Message("User is navigated to quote booking page");
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quotedetailspage.clickConvertToBookingBtn();
			Pass_Message("User is able to select 'convert to booking'");
			uiTestHelper.propagateException();
			bookingPage.clickSameAsCallerInfo();
			Pass_Message("Details entered successfully in Booking Information Page");
			bookingPage.clickBookingnextbtn();
			GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
			goodsInfoPage.clickGoodsInfoNextBtn();
			ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			Q_To_BK_AdditionalInfoPage_SameDayQuote();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Convert to Booking failed details validation failed");

		}
	}

	// Quote Additional info page
	public void Q_AdditionalInfoPage_SpecialServices() {
		try {
			try {
				QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
				AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
				uiTestHelper.scrolldown("300");
				quoteAdditionalInfoPage.clickValidServices();
				quoteAdditionalInfoPage.getValidServices();
				serviceNameQuote = quoteAdditionalInfoPage.getServiceNameQuote();
				additionalInfoPage.clickDetailnfoBtn();
				uiTestHelper.scrolldown("300");
				quoteAdditionalInfoPage.clickCloseButton();
				uiTestHelper.scrolldown("300");
				quoteAdditionalInfoPage.clickSaveQuoteBtn();
				wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
						"//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]")));
				if (driver.findElement(By
						.xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]"))
						.isDisplayed()) {
					Pass_Message("Quote is created successfully");
				} else {
					Fail_Message("Quote is Not created successfully");
				}
				String quote = driver.findElement(By
						.xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]"))
						.getText();
				quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
				uiTestHelper.propagateException();
				System.out.println("Quote Num is " + quoteNum);
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Quote failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ExceptionQuote creation failed failed");
		}
	}

	// Same Day Quote: Quote to Booking: Additional info page
	public void Q_To_BK_AdditionalInfoPage_SameDayQuote() {
		try {
			try {
				QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
				QuoteDetailPage quotedetailpage = new QuoteDetailPage(driver);
				AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
				serviceNameBK = quoteAdditionalInfoPage.getServiceNameQtoBK();
				if (serviceNameBK.equals(serviceNameQuote)) {
					Pass_Message("Special service is auto selected");
				} else {
					Fail_Message("Special service is auto selected");
				}
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickViewSummary();
				adtnlinfopage.clickConfirmBooking();
				uiTestHelper.propagateException();
				try {
					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

					String book = adtnlinfopage.getBookingConfirmMsg();
					bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
					if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
						Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

					} else {
						Fail_Message("Booking failed");

					}
				} catch (Exception e) {
					// TODO: handle exception
					Fail_Message("Booking Failed");
					adtnlinfopage.clickCancel();
					if (adtnlinfopage.verifyErrorMessage()) {
						driver.findElement(By.xpath("(//button[@title='Close'])[2]")).click();
						CCD_Connectivity conn = new CCD_Connectivity();
						conn.CloseTab();
						Assert.assertNotNull(bookNum);
					}
				}
				Pass_Message("Quote is converted to booking with booking id " + bookNum);
				Pass_Message("Status has changed to " + quotedetailpage.getBookingStatus());
				Pass_Message("User is able to convert same day quote to booking without recalculating the price");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Quote to booking failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote to booking failed");
		}
	}

	// Booking Page for SISP with Dedicated Account
	public void BK_SISP_BookingPage_DedicatedAccount() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		// String AcctName = "PORTUGAL PMC";
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}
	// Booking Page for RIRP flow with Dedicated Account

	public void BK_RIRP_with_Dedicated_Account() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			// String AcctName = Database_Connection.retrieveTestData("REC_ACCTNAME", "ACM",
			// "KEY", ACM_Booking.Key_Array[6]);
			String AcctName = "PORTUGAL PMC";
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_RIRP();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Delivery Contact details are Updated");
			}
			bookingpage.clickConfirm();
			uiTestHelper.scrolldown("300");
			setCollectionAddress("Denmark", "1552", "Copenhagen", "Megha Joshi", "89 Vester Voldgade");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			Pass_Message("Details entered successfully in Booking Information Page");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	// Booking Page for RIRP flow
	public void BK_BookingPage_domesticRIRP() {
		try {
			BK_BookingPage_RIRP();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");
		}
	}

	public String BK_ConInform_Page() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true);

		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("(//input[@name='quantity'])[1]", "1");
		hashmap.put("(//label[text()='Length']/following::input[1])[1]", "10");
		hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
		hashmap.put("(//label[text()='Height']/following::input[1])[1]", "12");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "120");
		hashmap.put("(//input[@name='quantity'])[2]", "1");
		hashmap.put("(//label[text()='Length']/following::input[1])[2]", "200");
		hashmap.put("(//label[text()='Width']/following::input[1])[2]", "120");
		hashmap.put("(//label[text()='Height ']/following::input[1])[1]", "120");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "200");
		for (int i = 1; i <= 2; i++) {
			coninfopage.addItem();
		}
		uiTestHelper.propagateException();
		driver.findElement(By.xpath("(//button[@name='[object Object]'])[2]")).click();
		driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='box']")).click();
		driver.findElement(By.xpath("(//button[@name='[object Object]'])[4]")).click();
		driver.findElement(By.xpath("(//lightning-base-combobox-item[@data-value='pallet'])[2]")).click();
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		Pass_Message("Quantity , Weight and Dimensions are updated on the Consignment Information Page");

		Float quantity1Weight = Float.parseFloat(driver
				.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[1]")).getAttribute("value"));
		Float quantity2Weight = Float.parseFloat(driver
				.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[2]")).getAttribute("value"));

		Float expectedWeight = Float.sum(quantity1Weight, quantity2Weight);
		String expectedTotalWeight = String.valueOf(expectedWeight);
		coninfopage.clickConsignmentInfoNextBtn();
		return expectedTotalWeight;
	}

	public void BK_ConInfo_Page_with_consignmentNote() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true);
		try {
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "10");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "12");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[2]", "200");
			hashmap.put("(//label[text()='Width']/following::input[1])[2]", "120");
			hashmap.put("(//label[text()='Height ']/following::input[1])[1]", "120");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[2]", "200");
			coninfopage.addItem();
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
			uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='box']"));
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[4]"));
			uiTestHelper.clickJSByObjects(By.xpath("(//lightning-base-combobox-item[@data-value='pallet'])[2]"));
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Quantity , Weight and Dimensions are updated on the Consignment Information Page");
			if (coninfopage.verifyConnoteIndicator()) {
				Assert.assertEquals(coninfopage.verifyConnoteIndicator(), true);
				coninfopage.clickConsignmentNote();
			}
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");

		}
	}

	public void BK_VerifyConsignmentNumber_Edit_SISPFlow() {
		try {
			consignmentNumber = BK_verifyConSignmentNoCreation_SISP();
			BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
			BookingPage bkpage = new BookingPage(driver);
			GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			uiTestHelper.scrolldown("300");
			bkpage.clickSameAsCallerInfo();
			uiTestHelper.propagateException();
			bkpage.clickBookingnextbtn();
			goodsPage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			addinfopage.clickViewSummary();
			addinfopage.clickUpdateBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getUpdatedBookingConfirmMsg();
			bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickMoretab();
			bkdetailspage.clickAdditionalInfo();
			String conNumber = bkdetailspage.getConsignmentNo();
			if (consignmentNumber.equals(conNumber)) {
				Pass_Message(
						"Consignment Number: " + conNumber + " is remains same when we edit the booking: " + conNumber);
			} else {
				Fail_Message("Consignment Number is not same when we edit the booking");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Consignment No is not verified when we Edit the Booking");
		}
	}

	public void BK_editBooking_CollDriver_Inst_charsLength() {
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentinfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		bookingpage.clickBookingnextbtn();
		goodsPage.clickGoodsInfoNextBtn();
		consignmentinfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		verifyCollectionDriverInstruction_charcterLength();

	}

	public void BK_cloneBooking_CollDriver_Inst_charsLength() {
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentinfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickCloneBooking();
		uiTestHelper.propagateException();
		bookingpage.clickBookingnextbtn();
		goodsPage.clickGoodsInfoNextBtn();
		consignmentinfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		verifyCollectionDriverInstruction_charcterLength();
	}

	public void verifyCollectionDriverInstruction_charcterLength() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		adtnlinfopage.setCollectionInstruction(
				"Please enter more than eighty chars in collection driver instruction in the Additioal Information Page of Booking");
		int collectionDriverInstlen = adtnlinfopage.getCollectionInstruction().length();
		System.out.println(collectionDriverInstlen);
		Assert.assertEquals(collectionDriverInstlen, 80);
		Pass_Message("Collection Instruction Length matched to IMADE Charcter Limit");
	}

	public void booking_ValidateonOSCErrorMessage() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String postal = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.callerInfo_SISP();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Validated");
		}
		bookingpage.setCollectionPostal(postal);
		bookingpage.clickCollectionValidatebtn();
		Assert.assertTrue(bookingpage.errorMsg());
		Pass_Message("OSC error Message displayed with " + bookingpage.geterrorMsg());
		// bookingpage.confirmCustomerInstruction();
		driver.navigate().refresh();
		bookingpage.callerInfo_SISP();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Validated");
		}
		bookingpage.setCollectionPostal("1000-163");
		bookingpage.setCollectionTown(town);
		bookingpage.clickCollectionValidatebtn();
		Assert.assertTrue(bookingpage.errorMsg());
		Pass_Message("OSC error Message displayed with " + bookingpage.geterrorMsg());
		driver.navigate().refresh();
	}

	public void enableSpecialServiceBooking_for_SpSThirdParty_AdditionalInfoPage() {
		try {
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Information Page not Opened");
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			Assert.assertEquals(addinfopage.verifyGetPrice(), true, "Get Price button Not Displayed");
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			Assert.assertEquals(addinfopage.verifyPriceOnTable(), true, "Price Not Displayed for Services");
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			if (addinfopage.verifySpecialService() == true) {
				Pass_Message("Special Service Option Dispalyed in the Addtional information page of Booking");
			} else {
				Fail_Message("Special Service Option Not Dispalyed in the Addtional information page of Booking");
			}
			updatespecialServiceBookingDetails();
			updateTargetCountry_and_BusinessLocation_on_specialServiceBooking("Sweden", "LISBON");
			addinfopage.confirmSpecialService();
			Assert.assertTrue(addinfopage.verifySepcialServiceApplied());
			if (addinfopage.verifySepcialServiceApplied()) {
				Pass_Message("Special Service applied for the Booking");
			} else {
				Fail_Message("Special Service not applied for the Booking");
			}
			uiTestHelper.scrolldown("700");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			Assert.assertNotNull(bookNum);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Special Service Booking Not Created");
		}
	}

	public void BK_ConInfo_Page_with_weightAndDim(String quantity, String Length, String Width, String Height,
			String Weight) {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(consignmentInfoPage.verifyConsignmentInfoTitle(), true);
		HashMap<String, String> hashmap = new HashMap<String, String>();

		hashmap.put(consignmentInfoPage.quantityInput, quantity);
		hashmap.put(consignmentInfoPage.lengthInput, Length);
		hashmap.put(consignmentInfoPage.widthInput, Width);
		hashmap.put(consignmentInfoPage.heightInput, Height);
		hashmap.put(consignmentInfoPage.weightInput, Weight);

		String dimsDefaultMeasurement = consignmentInfoPage.getDimension();
		Assert.assertEquals(dimsDefaultMeasurement, "CM");
		if (dimsDefaultMeasurement.equals("CM")) {
			Pass_Message("Default Dimension Measurement is displayed as " + dimsDefaultMeasurement);
		}

		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
		uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='box']"));
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		Pass_Message_with_screenCapture("Default Weight and Dims are below:");
		consignmentInfoPage.setDimension("Inches", 1);
		compareWeightAndDimAfterChangingConsignmentType(quantity, Length, Width, Height, Weight, "Dimension");
		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
		uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='pallet']"));
		compareWeightAfterChangingConsignmentTypeToPallet(quantity, Length, Width, Height, Weight,
				"ConsignmentType to Pallet");
		uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
		uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='envelope']"));
		compareWeightAfterChangingConsignmentTypeToEnvelope(quantity, Weight);
		consignmentInfoPage.clickConsignmentInfoNextBtn();
	}

	public void compareWeightAndDimAfterChangingConsignmentType(String quantity, String Length, String Width,
			String Height, String Weight, String changingType) {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		System.out
				.println(uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.quantityInput)).getAttribute("value"));
		boolean quantityVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.quantityInput))
				.getAttribute("value").equals(quantity);
		boolean lengthVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.lengthInput)).getAttribute("value")
				.equals(Length.concat(".00"));
		boolean widthVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.widthInput)).getAttribute("value")
				.equals(Width.concat(".00"));
		boolean heightVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.heightInput)).getAttribute("value")
				.equals(Height.concat(".00"));
		boolean weightVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.weightInput)).getAttribute("value")
				.equals(Weight.concat(".00"));
		Assert.assertEquals(quantityVal, true);
		Assert.assertEquals(lengthVal, true);
		Assert.assertEquals(widthVal, true);
		Assert.assertEquals(heightVal, true);
		Assert.assertEquals(weightVal, true);
		Pass_Message_with_screenCapture("Weight and Dims are Maintained after changing " + changingType);
	}

	public void compareWeightAfterChangingConsignmentTypeToPallet(String quantity, String Length, String Width,
			String Height, String Weight, String changingType) {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		boolean quantityVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.quantityInput))
				.getAttribute("value").equals(quantity);
		boolean lengthVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.lengthInput)).getAttribute("value")
				.equals(Length.concat(".00"));
		boolean widthVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.widthInput)).getAttribute("value")
				.equals(Width.concat(".00"));
		boolean heightVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.palletheightInput))
				.getAttribute("value").equals(Height.concat(".00"));
		boolean weightVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.weightInput)).getAttribute("value")
				.equals(Weight.concat(".00"));
		Assert.assertEquals(quantityVal, true);
		Assert.assertEquals(lengthVal, true);
		Assert.assertEquals(widthVal, true);
		Assert.assertEquals(heightVal, true);
		Assert.assertEquals(weightVal, true);
		Pass_Message_with_screenCapture("Weight and Dims are Maintained after changing Conignment Type to Pallet");
		;
	}

	public void compareWeightAfterChangingConsignmentTypeToEnvelope(String quantity, String Weight) {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		boolean quantityVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.quantityInput))
				.getAttribute("value").equals(quantity);
		boolean weightVal = uiTestHelper.waitForObject(By.xpath(consignmentInfoPage.weightInput)).getAttribute("value")
				.equals(Weight.concat(".00"));
		Assert.assertEquals(quantityVal, true);
		Assert.assertEquals(weightVal, true);
		Pass_Message_with_screenCapture("Weight is Maintained after changing ConsignmentType to Envelope");
	}

	public void bK_Product_Option_Price_Add_SISP() {

		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			bK_AdditionalInfo_Page_Product_Option_Price_Add();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();

		}
	}

	public void bK_AdditionalInfo_Page_Product_Option_Price_Add() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		String product_option = "Saturday Delivery";
		try {
			adtnlinfopage.clickValidServices();
			Pass_Message("Service as Express is selected");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.selectOptions("EX200");
			Assert.assertEquals(adtnlinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			if (adtnlinfopage.verifyProductOptionTable()) {
				adtnlinfopage.getAllavailableProductOptions();
			}
			adtnlinfopage.enableProductOptions(product_option);
			adtnlinfopage.submitProductOption();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			String productOptionDesc = adtnlinfopage.PriceBreakDownDetails(product_option);
			if (productOptionDesc.equalsIgnoreCase(product_option)) {
				Pass_Message("Product option price is added");
			} else {
				Fail_Message("Product option price is not added");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Product option price is not added ");
		}
	}

	/**
	 * B521838 TC113 Product Option Price validation
	 *
	 * @
	 */

	public void bK_Product_Option_Price_Remove_SISP() {

		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			bK_AdditionalInfo_Page_Product_Option_Price_Remove();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with price flow failed");
			driver.navigate().refresh();

		}
	}

	/**
	 * method to remove product option price
	 *
	 * @
	 */
	public void bK_AdditionalInfo_Page_Product_Option_Price_Remove() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		String product_option = "Saturday Delivery";
		try {
			adtnlinfopage.clickValidServices();
			Pass_Message("Service as Express is selected");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.selectOptions("EX200");
			Assert.assertEquals(adtnlinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			if (adtnlinfopage.verifyProductOptionTable()) {
				adtnlinfopage.getAllavailableProductOptions();
			}
			adtnlinfopage.enableProductOptions(product_option);
			adtnlinfopage.submitProductOption();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			String productOptionDesc = adtnlinfopage.PriceBreakDownDetails(product_option);
			if (productOptionDesc.equalsIgnoreCase(product_option)) {
				Pass_Message("Product option price is added");
			} else {
				Fail_Message("Product option price is not added");
			}
			uiTestHelper.scrollUp("-700");
			adtnlinfopage.selectedServiceOptions();
			Assert.assertEquals(adtnlinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			adtnlinfopage.verifyProductOptionTable();
			adtnlinfopage.disableProductOptions(product_option);
			adtnlinfopage.submitProductOption();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnSelectedServiceTable();
			adtnlinfopage.getValidSelectedService();
			uiTestHelper.scrolldown("300");
			try {
				WebElement ele = driver.findElement(By.xpath(
						"//*[contains(text(),'Additional Option')]/following::table/tbody/tr[@data-row-key-value='"
								+ product_option + "']"));
			} catch (Exception e) {
				Pass_Message("Product option Successfully Removed");
			}
			uiTestHelper.scrolldown("300");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Product option price is not added");
		}
	}

	/**
	 * B521838 TC114 Product Option Price validation: Multiple product option
	 * selection
	 *
	 * @
	 */

	public void bK_Product_Option_Price_Multiple_Add_SISP() {

		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			bK_AdditionalInfo_Page_Product_Option_Price_Multiple_Add();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with multple product option price SISP flow failed");
		}
	}

	/**
	 * Multiple product option price selection process
	 *
	 * @
	 */
	public void bK_AdditionalInfo_Page_Product_Option_Price_Multiple_Add() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		String[] product_options = { "Priority", "Late Pickup", "CS Arranged Import Services", "Saturday Delivery" };
		try {
			adtnlinfopage.clickValidServices();
			Pass_Message("Service as Express is selected");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.selectOptions("EX200");
			Assert.assertEquals(adtnlinfopage.verifyProductOptionTable(), true, "Product Options Displayed");
			if (adtnlinfopage.verifyProductOptionTable()) {
				adtnlinfopage.getAllavailableProductOptions();
			}
			for (int i = 0; i <= product_options.length - 1; i++) {
				adtnlinfopage.enableProductOptions(product_options[i]);
			}
			adtnlinfopage.submitProductOption();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			String productOptionDesc = adtnlinfopage.PriceBreakDownDetails("PRIORITY");
			String productOptionDesc1 = adtnlinfopage.PriceBreakDownDetails("SATURDAY DELIVERY");
			String productOptionDesc2 = adtnlinfopage.PriceBreakDownDetails("CS ARRANGED IMPORT SERVICE");
			if (productOptionDesc.contains("PRIORITY") && productOptionDesc1.contains("SATURDAY DELIVERY")
					&& productOptionDesc2.contains("CS ARRANGED IMPORT SERVICE")) {
				Pass_Message("All product options are diplayed with Price");
			} else {
				Fail_Message("All product options are not diplayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Multiple product options selection failed");
		}
	}

	/**
	 * B521838 TC116 Edit Booking: Product option price validation
	 *
	 * @
	 */

	public void bK_Edit_Product_Option_Price() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			bK_AdditionalInfo_Page_Product_Option_Price_Multiple_Add();
			uiTestHelper.scrolldown("300");
			additionalInfoPage.clickViewSummary();
			additionalInfoPage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = additionalInfoPage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");
			}
			Assert.assertNotNull(bookNum, "SISP Booking not Created");
			BK_getRecentBookingfrom_BookingList(bookNum);
			bK_Edit_AdditionalInfo_Page_Product_Option_Price();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with multple product option price SISP flow failed");
		}
	}

	/**
	 * B521838 TC116 product option price check while editing booking
	 *
	 * @
	 */
	public void bK_Edit_AdditionalInfo_Page_Product_Option_Price() {
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			bkdetailspage.clickEditBooking();
			uiTestHelper.scrolldown("700");
			bookingPage.clickBookingnextbtn();
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			bK_Edit_AddInfo_Page_Product_Options_check();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Product option price validation failed");
		}
	}

	public void bK_Edit_AddInfo_Page_Product_Options_check() {

		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		try {
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			uiTestHelper.scrolldown("300");
			String productOptionDesc = adtnlinfopage.PriceBreakDownDetails("PRIORITY");
			String productOptionDesc1 = adtnlinfopage.PriceBreakDownDetails("SATURDAY DELIVERY");
			String productOptionDesc2 = adtnlinfopage.PriceBreakDownDetails("CS ARRANGED IMPORT SERVICE");
			if (productOptionDesc.contains("PRIORITY") && productOptionDesc1.contains("SATURDAY DELIVERY")
					&& productOptionDesc2.contains("CS ARRANGED IMPORT SERVICE")) {
				Pass_Message("All product options are diplayed with Price");
			} else {
				Fail_Message("All product options are not diplayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Product option price validation failed");
		}
	}

	/*
	 * Price: Price break down quote flow by Receiver
	 */
	public void q_QuoteFlow_Price_Break_Down_Receiver() {

		try {
			Q_QuoteInfoPage_Receiver();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			q_AdditionalInfoPagePrice();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote creation failed failed");

		}
	}

	public void q_AdditionalInfoPagePrice() {

		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
		try {

			try {
				uiTestHelper.scrolldown("300");
				adtnlinfopage.clickValidServices();
				quoteAdditionalInfoPage.getValidServices();
				adtnlinfopage.clickDetailnfoBtn();
				uiTestHelper.scrolldown("300");
				/*
				 * quoteAdditionalInfoPage.clickCloseButton();
				 * quoteuiTestHelper.scrolldown("300");
				 * quoteAdditionalInfoPage.clickSaveQuoteBtn();
				 * wait.until(ExpectedConditions.presenceOfElementLocated(By.
				 * xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]"
				 * ))); if(driver.findElement(By.
				 * xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]"
				 * )).isDisplayed()) { Pass_Message("Quote is created successfully"); } else {
				 * Fail_Message("Quote creation failed"); } String quote= driver.findElement(By.
				 * xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]"
				 * )).getText(); quoteNum=
				 * quote.replace("Quote is created successfully. Quote Reference Number is: ",
				 * ""); uiTestHelper.propagateException(); quoteAdditionalInfoPage.closeQuotePage();
				 * driver.navigate().refresh(); uiTestHelper.propagateException();
				 */
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Exception: Quote failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ExceptionQuote creation failed failed");
		}
	}

	/**
	 * B544549_TC120_BK_DG: Non Regulated Dangerous Goods: Price Break Down
	 * validation
	 *
	 * @
	 */

	public void bK_DG_Price_Break_Down_SISP() {

		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);

		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage_DG();
			BK_ConInfo_Page();
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
			uiTestHelper.scrolldown("700");
			if (adtnlinfopage.verifyPriceBreakDownDetails("EXCEPTED LITHIUM BATTERIES")) {
				Pass_Message("DG Price details are displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();

		}
	}

	public void bK_DG_AdditionalInfo_Page_Price_Break_Down() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		try {
			uiTestHelper.scrolldown("700");
			String productOptionDesc = adtnlinfopage.PriceBreakDownDetails("EXCEPTED LITHIUM BATTERIES");
			if (productOptionDesc.contains("EXCEPTED LITHIUM BATTERIES")) {
				Pass_Message("Dangerous goods are diplayed");
			} else {
				Fail_Message("Dangerous goods are not diplayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Product option price is not added ");
		}
	}

	/**
	 * B544549_TC121 Regulated and No Regulated Dangerous Goods: Special Service
	 * indicator validation
	 *
	 * @
	 */
	public void bK_DG_REG_Special_Service_Ind_SISP() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);

		try {
			BK_DG_VerifyFullyRegulatedDangerousGoods();
			goodsinfopage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			enableSpecialServiceBooking_on_AdditionalInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();
		}
	}

	/**
	 * B544549_TC122 Regulated and No Regulated Dangerous Goods: Special Service
	 * B544549_TC120_BK_DG Indicator validation
	 */
	public void bK_DG_Add_Three_Non_Reg_DG_With_Approved_Account() {

		try {
			BK_BookingPage_SISP();
			BK_Goodsinfopage_with_three_Non_regulated_DG();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
		}
	}

	public void BK_Goodsinfopage_with_three_Non_regulated_DG() {
		GoodsInfoPage goodsPage = new GoodsInfoPage(driver);
		try {
			setGoodsInformation("Non-Document", "TestDesc", "20", "12345");
			goodsPage.clickDangerousGoodsYes();
			setDGGoods("3010", "2", "Lithium Battery", 1);
			setDGGoods("3091", "2", "Lithium Battery", 2);
			setDGGoods("3480", "2", "Lithium Battery", 3);
			goodsPage.clickGoodsInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Three Non-Regulated Goods not selected");
		}
	}

	/**
	 * TC118 Non Regulated DG and Non approved customer
	 *
	 * @
	 */

	public void bK_Non_Reg_DG_and_Non_Approved_DG_Acct_SISP_DG_Flow() {

		try {
			bK_BookingPage_Non_Approved_DG_Account_SISP();
			BK_Goodsinfopage_with_three_Non_regulated_DG();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: SISP flow failed");

		}
	}

	/**
	 * B527199 TC74 to TC78: "Same as caller information" check box validation
	 *
	 * @
	 */
	public void bK_Same_As_Caller_Field_Validation_SISP() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			uiTestHelper.scrolldown("300");
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisplayed();
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisplayed();
			uiTestHelper.scrollUp("-700");
			bookingpage.callerInfo_SISP();
			uiTestHelper.scrolldown("300");
			bookingpage.clickSameAsCallerInfo();
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsEnabled();
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisabled();
			bookingpage.clickSameAsCallerInfo();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();
		}
	}

	public void bK_Same_As_Caller_Field_Validation_RIRP() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			uiTestHelper.scrolldown("300");
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisplayed();
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisplayed();
			uiTestHelper.scrollUp("-700");
			bookingpage.callerInfo_RIRP();
			uiTestHelper.scrolldown("300");
			bookingpage.clickReceiverSameAsCallerInfo();
			String contactname = bookingpage.getCallerName();
			String contacttelephone = bookingpage.getCallerPhone();
			String contactemail = bookingpage.getCallerEmail();
			/**
			 * "same as caller information" tab's value
			 */
			String sacicontactname = bookingpage.getInputSACIDeliveryContactName();
			String sacicontacttelephone = bookingpage.getInputSACIDeliveryTelephone();
			String sacicontactemail = bookingpage.getInputSACIDeliveryEmail();

			if (contactname.equals(sacicontactname)) {
				Pass_Message("contact name is autopopulated");
			} else {
				Fail_Message("contact name is autopopulated");
			}
			if (contacttelephone.equals(sacicontacttelephone)) {
				Pass_Message("contact telephone is autopopulated");
			} else {
				Fail_Message("contact telephone is autopopulated");
			}
			if (contactemail.equals(sacicontactemail)) {
				Pass_Message("contact email is autopopulated");
			} else {
				Fail_Message("contact email is autopopulated");
			}
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsEnabled();
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisabled();
			bookingpage.clickReceiverSameAsCallerInfo();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();
		}
	}

	public void bK_Same_As_Caller_Field_Validation_SIRP() {

		BookingPage bookingpage = new BookingPage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			uiTestHelper.scrolldown("300");
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisplayed();
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisplayed();
			uiTestHelper.scrollUp("-700");
			bookingpage.callerInfo_SIRP();
			uiTestHelper.scrolldown("300");
			bookingpage.clickSameAsCallerInfo();
			bK_BookingPage_SameAsCallerField_Sender_Contact_IsEnabled();
			bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisabled();
			bookingpage.clickSameAsCallerInfo();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking with product option price SISP flow failed");
			driver.navigate().refresh();
		}
	}

	/**
	 * To fill delivery address details
	 *
	 * @
	 */
	public void bK_BookingPage_Fill_Delivery_Adress() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(Deliv_Cust_Name);
			bookingpage.setDeliveryAddress(Deliv_Add);
			bookingpage.deliveryValidatebtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Booking Information page failed");
		}
	}

	/**
	 * "Same as caller information-delivery contact details" field Check
	 *
	 * @
	 */
	public void bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisplayed() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (bookingpage.checkReceiverSameAsCallerInfoIsDisplayed()) {
				Pass_Message("'Same as caller information' in delivery contact details is displayed");
			} else {
				Fail_Message("'Same as caller information' in delivery contact details is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in delivery contact details is not displayed");
		}
	}

	public void bK_BookingPage_SameAsCallerField_Delivery_Contact_IsEnabled() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (bookingpage.checkReceiverSameAsCallerInfoIsEnabled()) {
				Pass_Message("'Same as caller information' in delivery contact details is enabled");
			} else {
				Fail_Message("'Same as caller information' in delivery contact details is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in delivery contact details is disabled");
		}
	}

	public void bK_BookingPage_SameAsCallerField_Delivery_Contact_IsDisabled() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (!bookingpage.checkReceiverSameAsCallerInfoIsEnabled()) {
				Pass_Message("'Same as caller information' in delivery contact details is Disabled");
			} else {
				Fail_Message("'Same as caller information' in delivery contact details is enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in delivery contact details is enabled");
		}
	}

	/**
	 * "Same as caller information-sender contact details" field Check
	 *
	 * @
	 */
	public void bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisplayed() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (bookingpage.checkSenderSameAsCallerInfoIsDisplayed()) {
				Pass_Message("'Same as caller information' in sender contact details is displayed");
			} else {
				Fail_Message("'Same as caller information' in sender contact details is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in sender contact details is not displayed");
		}
	}

	public void bK_BookingPage_SameAsCallerField_Sender_Contact_IsEnabled() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (bookingpage.checkSenderSameAsCallerInfoIsEnabled()) {
				Pass_Message("'Same as caller information' in sender contact details is enabled");
			} else {
				Fail_Message("'Same as caller information' in sender contact details is disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in sender contact details is disabled");
		}
	}

	public void bK_BookingPage_SameAsCallerField_Sender_Contact_IsDisabled() {
		BookingPage bookingpage = new BookingPage(driver);
		try {
			if (!bookingpage.checkSenderSameAsCallerInfoIsEnabled()) {
				Pass_Message("'Same as caller information' in sender contact details is disabled");
			} else {
				Fail_Message("'Same as caller information' in sender contact details is enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("'Same as caller information' in sender contact details is disabled");
		}
	}

	/**
	 * Unapproved CSR account can't ship the DangerousGoods (DG) - Application show
	 * error message
	 *
	 * @
	 */
	public void bk_DG_US_391182_VerifyErrorDisplay_UnapprovedAccount_Ship_DangerousGoods() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		try {
			bK_BookingPage_Non_Approved_DG_Account_SISP();
			setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
			goodsinfopage.clickDangerousGoodsYes();
			Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page");
			goodsinfopage.setUNNumber("1020");
			goodsinfopage.searchDangerousGoods();
			if (goodsinfopage.verifyDGUnapprovedAccountErrorMsg()) {
				Pass_Message("Display error message as : " + goodsinfopage.getDGUnApprovedShipmentErrMsg());
			} else {
				Fail_Message("Error message is not displayed when Unapproved account create DG shipment");
			}
			uiTestHelper.scrolldown("300");
			goodsinfopage.clickGoodsInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Unapproved account created Dangerous Goods shipment");
		}

	}

	/**
	 * Method to validate Dabtor status, DG approved Indicator status and regular
	 * order indicator for DG approved Customer account
	 *
	 * @
	 */
	public void bk_DG_US_371379_VerifyDGApprovedCustomerAccountStatus() {

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		HomePage homepage = new HomePage(driver);
		// String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", ACM_CI.Key_Array[5]);
		String acctName = "PORTUGAL PMC";
		homepage.clickDropDownNavigationMenu();
		homepage.clickBooking();
		homepage.searchBooking(acctName);
		custaccpage.selectCustomerAccounts(acctName);

		if (custaccpage.isDabtorStatusLabelDisplayed())
			Pass_Message("Dabtor Status field displayed on screen");
		else
			Fail_Message("Dabtor Status field is not displayed on screen");

		if (!custaccpage.getDabtorStatus().isEmpty())
			Pass_Message("Dabtor Status as " + custaccpage.getDabtorStatus());
		else
			Fail_Message("Dabtor Status is blank");

		if (custaccpage.isDGApprovedIndicatorChecked())
			Pass_Message("Dangerous Goods Apporved indicator checkbox checked");
		else
			Fail_Message("Dangerous Goods Apporved indicator checkbox unchecked");

		if (custaccpage.isRegularOrderIndicatorChecked())
			Pass_Message("Regular Order indicator checkbox checked");
		else
			Fail_Message("Regular Order indicator checkbox unchecked");
	}

	/**
	 * Verify CSR account doesnot have dabtor status,DG approved status indicator
	 * blank and Regular Order indicator blank
	 *
	 * @param acctName @
	 */
	public void bk_DG_US_371379_VerifyDebtorStatusDGApprovedRegularOrderBlank() {

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		HomePage homepage = new HomePage(driver);
		String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		homepage.clickDropDownNavigationMenu();
		homepage.clickBooking();
		homepage.searchBooking(acctName);
		custaccpage.selectCustomerAccounts(acctName);

		if (custaccpage.getDabtorStatus().isEmpty())
			Pass_Message("Dabtor Status is blank " + custaccpage.getDabtorStatus());
		else
			Fail_Message("Dabtor Status is not blank");

		if (!custaccpage.isDGApprovedIndicatorChecked())
			Pass_Message("Dangerous Goods Apporved indicator checkbox unchecked");
		else
			Fail_Message("Dangerous Goods Apporved indicator checkbox checked");

		if (!custaccpage.isRegularOrderIndicatorChecked())
			Pass_Message("Regular Order indicator checkbox unchecked");
		else
			Fail_Message("Regular Order indicator checkbox checked");
	}

	/**
	 * B544549_TC119 Regulated and No Regulated Dangerous Goods: Special Service
	 *
	 * @
	 */
	public void bK_DG_Non_Reg_DG_With_Non_Approved_Account() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);

		try {
			bK_BookingPage_Non_Approved_DG_Account_SISP();
			setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
			goodsinfopage.clickDangerousGoodsYes();
			setDGGoods("3091", "2", "Lithium Battery", 1);
			goodsinfopage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			enableSpecialServiceBooking_on_AdditionalInfoPage();
			// acm_ci_bk.BK_BookingPagesValidation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Special service indicator is not validated");
			driver.navigate().refresh();
		}
	}

	/**
	 * B544549_TC123 Fully regulated DG and with 2 non regulated DG
	 *
	 * @
	 */
	public void bK_DG_One_Fully_REG_DG_And_Two_Non_Reg_DG_SISP() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);

		try {
			BK_DG_VerifyFullyRegulatedDangerousGoods();
			setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
			goodsinfopage.clickDangerousGoodsYes();
			setDGGoods("3010", "2", "Lithium Battery", 2);
			setDGGoods("3091", "2", "Lithium Battery", 3);
			goodsinfopage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			enableSpecialServiceBooking_on_AdditionalInfoPage();
			BK_BookingPagesValidation_GoodsInfo();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Goods information are not validated");
			driver.navigate().refresh();

		}
	}

	/**
	 * method to validate goods information page after booking is created
	 *
	 * @
	 */
	public void BK_BookingPagesValidation_GoodsInfo() {
		CCD_Connectivity con = new CCD_Connectivity();

		uiTestHelper.propagateException();
		con.CloseTab();
		driver.navigate().refresh();
		try {
			// priceinfopage.clickGoodsInfo();
			// String dgdetails = priceinfopage.getDGDetails();
			// String dgselected = dgname1 + " ; " + dgname2 + " ; " + dgname3;
			//
			// if (dgdetails.equalsIgnoreCase(dgselected)) {
			// Pass_Message(
			// "Goods information tab is validated with all the DGs info and UN with semi
			// colon seperated values");
			// } else {
			// Fail_Message("Goods information tab is not validated");
			// }

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Good Information Page validation failed");
		}
	}

	/**
	 * Booking Page: method to search non approved DG account and select service as
	 * SISP
	 *
	 * @
	 */
	public void bK_BookingPage_Non_Approved_DG_Account_SISP() {

		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
			setCollectionContactDetails("Nivetha", "8940735692", "nivetha.thirunavukarasu.osv@fedex.com");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	/**
	 * validate PostCode Autopopulate when Town has single Postcode for Quote Sender
	 * flow
	 *
	 * @
	 */
	public void validatePostCodeAutopopulateForTownWithSinglePostcodeInQuote() {

		BookingPage bookingpage = new BookingPage(driver);

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		QuotePage quotePage = new QuotePage(driver);
		try {

			String acctName = getDGApprovedAccountFromDB();
			String delivTown = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivCountry = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivCustName = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivAddress = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(acctName);
			custaccpage.selectCustomerAccounts(acctName);
			Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
			Assert.assertEquals(quotePage.verifyQuoteInfoTitle(), true, "New Quote page not Opened");
			quotePage.clickSenderButton();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
				Pass_Message("Collection Address is Updated Successfully");
			}
			bookingpage.setDeliveryCountry(delivCountry);
			bookingpage.setDeliveryTown(delivTown);
			bookingpage.setDelCustomerName(delivCustName);
			bookingpage.setDeliveryPostal(delivAddress);
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Delivery address not Validated");
				if (!bookingpage.getDeliveryPostCode().isEmpty())
					Pass_Message("Postcode autopopulated when Town has only one postcode as : "
							+ bookingpage.getDeliveryPostCode());
				else
					Fail_Message("Postcode not autopopulated when Town has only one postcode");
			}
		} catch (Exception e) {
			Fail_Message("Quote flow validation failed");
		}
	}

	/**
	 * Get Non approved DG customer account from Test DB
	 *
	 * @return Customer Account name
	 */
	public String getNonDGApprovedAccountFromDB() {
		return Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
	}

	/**
	 * Get Non approved DG customer account from Test DB
	 *
	 * @return Customer Account name
	 */
	public String getDGApprovedAccountFromDB() {
		return Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
	}

	/**
	 * validate PostCode Autopopulate when Town has single Postcode for Booking SISP
	 * flow
	 *
	 * @
	 */
	public void validatePostCodeAutopopulateForTownWithSinglePostcodeInBooking() {

		BookingPage bookingpage = new BookingPage(driver);
		try {
			String acctName = getDGApprovedAccountFromDB();
			String delivCustName = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivAddress = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivTown = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String delivCountry = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(acctName);
			newBookingonCustomerAccPage(acctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			setDeliveryAddress(delivCountry, delivCustName, delivTown, delivCustName, delivAddress);
			if (!bookingpage.getDeliveryPostCode().isEmpty())
				Pass_Message("Postcode autopopulated when Town has only one postcode as : "
						+ bookingpage.getDeliveryPostCode());
			else
				Fail_Message("Postcode not autopopulated when Town has only one postcode");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Booking flow validation failed");
		}
	}

	/**
	 * Method to Create Quote With 4 Product Options
	 *
	 * @
	 */
	public void verifyCreateQuoteWith4ProductOptions() {

		Q_QuoteInfoPage_Receiver();
		verifyProductOptions_enable5Options_Error();
		Q_AdditionalInfoPage();

	}

	public void verifyCreateQuoteWith1DGAnd3ProductOptions() {

		QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		Q_QuoteInfoPage_Receiver();
		BK_GoodsInfoPage_DG();
		BK_ConInfo_Page();
		Assert.assertEquals(additionalInfoPage.verifyTitle(), true, "Additional Information Page not Opened");
		quoteAdditionalInfoPage.clickValidServices();
		quoteAdditionalInfoPage.selectOptions("EX200");
		Assert.assertEquals(additionalInfoPage.verifyProductOptionTable(), true, "Product Options Displayed");
		if (additionalInfoPage.verifyProductOptionTable()) {
			additionalInfoPage.getAllavailableProductOptions();
		}
		selectThreeQuoteProductOptions();
		quoteAdditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteAdditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteAdditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);

	}

	/*
	 * Method to select product options in Additional Information screen in Quote
	 * flow
	 */
	public void selectThreeQuoteProductOptions() {
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		additionalInfoPage.enableProductOptions("Late Pickup");
		additionalInfoPage.enableProductOptions("Saturday Delivery");
		additionalInfoPage.enableProductOptions("Priority");
		additionalInfoPage.submitProductOption();

	}

	public void booking_SelfBaughtTime_Validation_SISP() {

		QuickBookingPage quickbookingpage = new QuickBookingPage(driver);

		try {
			Pass_Message("User is successfully able to log in to salesforce as CSR");
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			quickbookingpage.clickValidServices();
			Pass_Message("User is able to click on 'Valid Services'");
			if (!quickbookingpage.getSelfBaughtTime().contains("false")) {
				Pass_Message("Self baught time is displayed in valid services");
				Pass_Message("Self baught time contains current date");
				System.out.println(quickbookingpage.getSelfBaughtTime());
			} else {
				Fail_Message("Self baught time does not contain current date");
				System.out.println(quickbookingpage.getSelfBaughtTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:Self Baught Time is not validated");
			driver.navigate().refresh();

		}
	}

	public void booking_TownAndMultipltePostalCode_Validation_SISP() {
		BookingPage bookingpage = new BookingPage(driver);

		CCD_CustomerIdentificationAndQuickBookingFlow ciflow = new CCD_CustomerIdentificationAndQuickBookingFlow();
		QuickBookingPage quickbookingpage = new QuickBookingPage(driver);

		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			System.out.println("Deliv_Country: " + Deliv_Country);
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);

			ciflow.bk_NavigateToBookingPage();
			bookingpage.callerInfo_SISP();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("collection Address is updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(Deliv_Cust_Name);
			bookingpage.setDeliveryAddress(Deliv_Add);
			bookingpage.deliveryValidatebtn();
			if (quickbookingpage.getPostalCodeFromMultiplePostCode() > 1) {
				Pass_Message("Multiple postal codes are shown");
				quickbookingpage.selectPostcode();
				if (!bookingpage.getDeliveryPostCode().isEmpty()) {
					Pass_Message("Postal code selected from multiple post code options");
				}
			} else {
				Fail_Message("Multiple postal codes validation failed");
			}
			uiTestHelper.scrolldown("300");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			Pass_Message("Details entered successfully in Booking Information Page");
			// bookingpage.clickBookingnextbtn();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Multiple Postal code from town name functionality is not validated");
		}
	}

	public void validate_User_Can_Add_Two_Email_Ids_And_Exist_In_Summary_Tab() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		try {
			// Click the booking summary after booking has been created
			BK_SISP_Flow_without_Validation();
			bkdetailspage.clickBookingSummary();

			// Select 'Other' Option and add 2 email addresses
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			String emailAddresses = "ben.grimstead@fedex.com" + "," + "nivetha.thirunavukarasu.osv@fedex.com";
			bkdetailspage.sendOtherEmailAddressToBox(emailAddresses);
			uiTestHelper.propagateException();
			bkdetailspage.clickSendEmail();

			// add a wait in here - this wait controls the green message before next step
			bkdetailspage.waitForSuccessMessage();

			bkdetailspage.clickBookingHistroy();
			String isEmailSent = bkdetailspage.getEmailSentTo();
			softAssertion.assertEquals(isEmailSent,
					"ben.grimstead@fedex.com" + "," + "nivetha.thirunavukarasu.osv@fedex.com");
			softAssertion.assertAll();
			Pass_Message("Email accepts 2 correct emails by comma to seperate email ids");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("2 correct email ids entered but were not seperated by comma");
		}
	}

	public void validate_invalid_Email_Ids_on_bookingSummary() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			// Click the booking summary after booking has been created
			BK_SISP_Flow_without_Validation();
			bkdetailspage.clickBookingSummary();

			// Select 'Other' Option and add 2 email addresses
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			String emailAddresses = "ben.grimstead@fedex.com" + "," + "jhkgbb@fedex.com";
			bkdetailspage.sendOtherEmailAddressToBox(emailAddresses);
			bkdetailspage.clickSendEmail();

			// add a wait in here - this wait controls the green message before next step
			bkdetailspage.waitForSuccessMessage();

			// validate booking history has confirmed the email as incorrect
			bkdetailspage.clickBookingHistroy();
			String isEmailSent = bkdetailspage.getEmailSentTo();
			softAssertion.assertEquals(isEmailSent, "ben.grimstead@fedex.com" + "," + "jhkgbb@fedex.com");
			softAssertion.assertAll();
			Pass_Message("Email result displays acutal vs expected, and only accepts expected");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invalid email address should not be accepted");
		}

	}

	public void validate_User_Can_Add_Two_Email_Ids_in_quoteSummary_And_Exist_In_Summary_Tab() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		QuotePage quotePage = new QuotePage(driver);
		try {
			// Create a Quote
			getRecentlyCreatedQuote();
			Assert.assertEquals(quotePage.verifyQuoteSummaryButton(), true);
			// Click summary - add email IDs to other section - validate
			quotePage.clickQuoteSummaryButton();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			//
			String emailAddresses = "ben.grimstead@fedex.com" + "," + "nivetha.thirunavukarasu.osv@fedex.com";
			bkdetailspage.sendOtherEmailAddressToBox(emailAddresses);
			bkdetailspage.clickSendEmail();
			// add a wait in here - this wait controls the green message before next step
			bkdetailspage.waitForSuccessMessage();

			// to see the email IDs, you have to reload the page
			quotePage.clickQuoteHistoryTab();
			// driver.navigate().refresh();
			if (quotePage.verifyEmailSentTo()) {
				String isEmailSent = quotePage.getPreSetEmailSentTo();
				softAssertion.assertEquals(isEmailSent,
						"ben.grimstead@fedex.com" + "," + "nivetha.thirunavukarasu.osv@fedex.com");
				softAssertion.assertAll();
				Pass_Message("Email accepts 2 correct emails by comma to seperate email ids - " + isEmailSent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("2 correct email ids entered but were not seperated by comma");
		}
	}

	public void validate_invalid_Email_Ids_on_quoteSummary() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		QuotePage quotePage = new QuotePage(driver);

		try {
			// Create a Quote
			getRecentlyCreatedQuote();
			Assert.assertEquals(quotePage.verifyQuoteSummaryButton(), true);
			quotePage.clickQuoteSummaryButton();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			String emailAddresses = "ben.grimstead@fedex.com" + "," + "test.osv@fedex.com";
			bkdetailspage.sendOtherEmailAddressToBox(emailAddresses);
			bkdetailspage.clickSendEmail();

			// add a wait in here - this wait controls the green message before next step
			bkdetailspage.waitForSuccessMessage();
			// to see the email IDs, you have to reload the page
			quotePage.clickQuoteHistoryTab();
			// driver.navigate().refresh();
			if (quotePage.verifyEmailSentTo()) {
				String isEmailSent = quotePage.getPreSetEmailSentTo();
				softAssertion.assertEquals(isEmailSent, "ben.grimstead@fedex.com" + "," + "test.osv@fedex.com");
				softAssertion.assertAll();
				Pass_Message("Email result displays for invalid Ids - " + isEmailSent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invalid email address should not be accepted");
		}
	}

	public void validate_Services_when_edits_on_bookingPage() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.setCallerName("Nivetha");
			bookingpage.setCallerPhone("8942563585");
			bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
			Pass_Message("Caller Information Updated for the Booking");
			uiTestHelper.scrolldown("700");
			bookingpage.setContactName("Deepti");
			bookingpage.setContactPhone("9752645581");
			bookingpage.setContactEmail("deepti.khopade.osv@fedex.com");
			bookingpage.setInputSACIDeliveryContactName("Sayali");
			bookingpage.setInputSACIDeliveryTelephone("8574858585");
			bookingpage.setInputSACIDeliveryEmail("sayali.patil.osv@fedex.com");
			Pass_Message("Collection and Delivery Contact has been Updated for the booking");
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		Assert.assertEquals(adtnlinfopage.verifyViewSummary(), true, "Service Needs to be Revalidated");
		if (adtnlinfopage.verifyViewSummary()) {
			adtnlinfopage.clickViewSummary();
		} else {
			Fail_Message("Service needs to be revalidated when we edit Caller, Collection and Delivery Contact"
					+ " on the Booking information Page of the booking");
		}
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message(
					"Service is not Changed when we edit the Caller, Collection and Delivery Contact on Booking page"
							+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public String updateBooking() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		adtnlinfopage.clickUpdateBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = adtnlinfopage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		} else {
			Fail_Message("Update Booking failed");
		}
		Assert.assertNotNull(bookNum, "Booking updation Failed");
		return bookNum;
	}

	public void validate_Services_when_edits_on_goodsInformationPage() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		if (bookingpage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("700");
			bookingpage.clickBookingnextbtn();
		}
		setGoodsInformation("Document", "Goods Desc", "10", "Alpha123");
		Pass_Message("Goods Description and Customer Reference has been Updated for the Booking");
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		Assert.assertEquals(adtnlinfopage.verifyViewSummary(), true, "Service Needs to be revalidated");
		if (adtnlinfopage.verifyViewSummary()) {
			adtnlinfopage.clickViewSummary();
		} else {
			Fail_Message("Service needs to be revalidated when we edit the Goods Description and Customer "
					+ " Reference on the Goods information Page of the booking");
		}
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message(
					"Service is not Changed when we edit the goods description and Customer Reference on Goods Information page"
							+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public void validate_Services_when_edits_on_additionalInformationPage() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			uiTestHelper.scrolldown("700");
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		adtnlinfopage.setCollectionInstruction("Updated Collection Instruction");
		adtnlinfopage.setDeliveryInstruction("Updated Delivery Instruction");
		uiTestHelper.scrolldown("300");
		adtnlinfopage.setFromCollWindowTimeInput("16:30");
		adtnlinfopage.setToCollWindowTimeInput("18:15");
		Pass_Message("Driver Instruction and Collecton Window Updated for the Booking");
		Assert.assertEquals(adtnlinfopage.verifyViewSummary(), true, "Service Needs to be revalidated");
		if (adtnlinfopage.verifyViewSummary()) {
			adtnlinfopage.clickViewSummary();
		} else {
			Fail_Message(
					"Service needs to be revalidated when we edit the Collection/Delivery instruction and Collection "
							+ " window on the Additional information Page of the booking");
		}
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message(
					"Service is not Changed when we edit the Collection/Delivery instruction and Collection Window on Additional Information page"
							+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public void validate_Services_when_edits_on_collection_and_Delivery_Address() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			uiTestHelper.scrolldown("700");
			bookingpage.setCollectionPostal("2480-031");
			bookingpage.setCollectionTown("ALVADOS");
			bookingpage.setCollectionCustomerName("Test ABC CNAME6 UPDATED");
			bookingpage.setCollectionAddress("1TEST ADDR LINE6 updated");
			bookingpage.clickCollectionValidatebtn();
			uiTestHelper.propagateException();
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the Collection and Delivery address is changed on Booking Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message(
					"Valid Service is revalidated when the Collection and Delivery address is changed on Booking Information page");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message(
					"Service is revalidated when we edit the Collection and Delivery address on Booking Information page"
							+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
		CCD_Connectivity con = new CCD_Connectivity();
		con.CloseTab();
	}

	public void validate_Services_when_edits_on_enhanced_liability_indicator() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		if (bookingpage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickEnhancedLiability();
		goodsinfopage.setEnhancedLiabilityInput("100");
		Pass_Message("Enhanced Liability is Enabled");
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the enhanced liability is changed on Goods Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message(
					"Valid Service is revalidated when the enhanced liability is changed on Goods Information page");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message("Service is revalidated when we edit enhanced liability on Goods Inormation page"
					+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public void validate_Services_when_edits_on_quantity() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("(//input[@name='quantity'])[1]", "3");
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			driver.findElement(By.xpath(entry.getKey())).clear();
			driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		Pass_Message("Quantity has been updated on the Consignment Information Page");
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the Quantity is changed on Consignment Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message("Valid Service is revalidated when the Quantity is changed on Consignment Information page");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message("Service is revalidated when we edit Quantity on Consignment Inormation page"
					+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public void validate_Services_when_edits_on_weight() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "30");
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			driver.findElement(By.xpath(entry.getKey())).clear();
			driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		Pass_Message("Weight has been updated on the Consignment Information Page");
		coninfopage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the Weight is changed on Consignment Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message("Valid Service is revalidated when the Weight is changed on Consignment Information page");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message("Service is revalidated when we edit Quantity on Consignment Inormation page"
					+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}
	}

	public void validate_Services_when_remove_Consignment_items() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		BK_ConInfo_Page_EditBooking();
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the Weight is changed on Consignment Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message("Valid Service is revalidated when the Weight is changed on Consignment Information page");
			uiTestHelper.scrolldown("300");
			adtnlinfopage.clickValidServices();
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message("Service is revalidated when we edit Weight on Consignment Inormation page"
					+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}

	}

	public void validate_Services_when_edits_on_Collection_date() {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(driver);
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		QuoteAdditionalInfoPage quoteAdditionalInfoPage = new QuoteAdditionalInfoPage(driver);

		BK_SISP_Flow_without_Validation();
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.clickBookingnextbtn();
		}
		goodsinfopage.clickGoodsInfoNextBtn();
		coninfopage.clickConsignmentInfoNextBtn();
		String date = adtnlinfopage.getSystemDateinFormat("dd-MMM-yyyy");
		quoteAdditionalInfoPage.enterCollectionDate(date);
		Pass_Message("Collection date is updated on the Additional information page");
		uiTestHelper.scrolldown("700");
		try {
			if (adtnlinfopage.verifyViewSummaryquick()) {
				Fail_Message(
						"Not able to re-validate the service when the Collection date is changed on Additional Information page");
				ACM_Connectivity.CloseTab();
			}
		} catch (Exception e) {
			Pass_Message(
					"Valid Service is revalidated when the Collection date is changed on Additional Information page");
			adtnlinfopage.clickValidServices();
			uiTestHelper.scrolldown("300");
			if (adtnlinfopage.verifyGetPrice()) {
				adtnlinfopage.clickGetPrice();
			}
			adtnlinfopage.verifyPriceOnTable();
			adtnlinfopage.getValidServices();
		}
		uiTestHelper.scrolldown("700");
		adtnlinfopage.clickViewSummary();
		String bookingNumber = updateBooking();
		if (!bookingNumber.isEmpty()) {
			Pass_Message("Service is revalidated when we edit Collection date on Additional Inormation page"
					+ " for the Booking - " + bookingNumber);
		} else {
			Fail_Message("Booking service is not validated for the Updated booking id - " + bookingNumber);
		}

	}

	public void verifyAccountNumber_without_entering_Zeros() {
		BookingPage bookingpage = new BookingPage(driver);

		// Iteration 2.3 test cases B-488891

		// Declare account, click drop-down menu, search account, pass in the details
		// and select matching account
		String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[6]);
		bookingSelectionOnHomepage(acctName);

		newBookingonCustomerAccPage(acctName);
		// Set booking to sender, receiver and pass in billing account number with no
		// zero values, select country
		bookingpage.callerInfo_SIRP();
		String billingAccNumber = "651";
		bookingpage.verifyBillingAccNumber();
		receiverDetails_on_SIRP(billingAccNumber, "Portugal");

	}

	public void Verify_Account_Number_Without_Entering_Zeros_In_Booking_Screen() {

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		// Iteration 2.3 test cases B-488891
		try {

			verifyAccountNumber_without_entering_Zeros();
			Pass_Message("Multiple options for the Account Number with Country is Displayed");
			bookingpage.clickBillingAccountSearchRecordPopup();
			// Select and account and verify the billing account number
			bkdetailspage.waitForSuccessMessage();
			String accountNumberResult = bookingpage.getBillingAccountNumber();
			softAssertion.assertEquals(accountNumberResult, "000000651");
			softAssertion.assertAll();
			Pass_Message("Account number has been validated and contains preceeding zeros");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account number shows no zeros, re-validate");
		}
	}

	public void verify_user_presented_options_when_Entering_Account_Number_Zeros_In_Booking_Screen() {
		// Iteration 2.3 test cases B-488891

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);

		try {
			verifyAccountNumber_without_entering_Zeros();
			boolean name = bookingpage.verifyUseroptions("Name");
			boolean accountNumber = bookingpage.verifyUseroptions("Account Number");
			boolean status = bookingpage.verifyUseroptions("Status");
			boolean country = bookingpage.verifyUseroptions("Country");
			if (name && accountNumber && status && country) {
				Pass_Message(
						"User Options (Name, Account Number, Status and Country) are displayed when we having multiple address for Billing Account Number");
			} else {
				Fail_Message(
						"User Options (Name, Account Number, Status and Country) are not displayed when we having multiple address for Billing Account Number");
			}
			bookingpage.clickBillingAccountSearchRecordPopup();
			// Select and account and verify the billing account number
			bkdetailspage.waitForSuccessMessage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account number shows no zeros, re-validate");
		}
	}

	public void verify_delivery_Address_when_Entering_Account_Number_Zeros_In_Booking_Screen() {
		// Iteration 2.3 test cases B-488891
		BookingPage bookingpage = new BookingPage(driver);
		try {
			verifyAccountNumber_without_entering_Zeros();
			bookingpage.clickBillingAccountSearchRecordPopup();
			BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
			bookingRecordPage.waitForSuccessMessage();
			// Select and account and verify the delivery address
			uiTestHelper.scrolldown("300");
			String delAddress = bookingpage.getDeliveryAddress();
			if (!delAddress.isEmpty()) {
				Pass_Message("Delivery Address is Auto filled and the address is : " + delAddress);
			} else {
				Fail_Message("Delivery Address is not auto filled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Account number shows no zeros, re-validate");
		}
	}

	public void verifyCollectionAddress_and_province() {
		BookingPage bookingpage = new BookingPage(driver);
		if (bookingpage.verifyCollectionAddress2()) {
			Pass_Message_withoutScreenCapture("Collection Address Line 1 Splited into two lines is Displayed");
		} else {
			Fail_Message("Collection Address Line 1 not splited into two lines is not Displayed");
		}
		Assert.assertEquals(bookingpage.verifyCollectionAddress2(), true,
				"Collection Address line 1 not splitted into two lines");
		if (bookingpage.verifyCollectionAddress3()) {
			Pass_Message_withoutScreenCapture("Collection Address Line 2 is Displayed");
		} else {
			Fail_Message("Collection Address Line 2 is not Displayed");
		}
		Assert.assertEquals(bookingpage.verifyCollectionAddress3(), true, "Collection Address line 2 is not Displayed");
		if (bookingpage.validateCollectionProvince()) {
			if (!bookingpage.validateCollectionProvinceEnabled()) {
				Pass_Message("Collection Province is displayed and the province is auto-filled with :"
						+ bookingpage.getCollectionProvince());
			} else {
				Fail_Message("Collection Province needs to be entered");
			}
		} else {
			Fail_Message("Collection Province is not displayed in the booking");
		}
		Assert.assertEquals(bookingpage.validateCollectionProvince(), true, "Collection Province not Displayed");

	}

	public void verifyDeliveryAddress_and_Province() {
		BookingPage bookingpage = new BookingPage(driver);
		if (bookingpage.validateDelProvince()) {
			if (!bookingpage.validateDelProvinceEnabled()) {
				Pass_Message("Delivery Province is displayed and the province is auto-filled with :"
						+ bookingpage.getDeliveryProvince());
			} else {
				Fail_Message("Delivery Province needs to be entered");
			}
		} else {
			Fail_Message("Delivery Province is not displayed in the booking");
		}
		Assert.assertEquals(bookingpage.validateDelProvince(), true, "Delivery Province not Displayed");
		if (bookingpage.verifyDelAddress2()) {
			Pass_Message_withoutScreenCapture("Delivery Address Line 1 Splited into two lines is Displayed");
		} else {
			Fail_Message("Delivery Address Line 1 not splited into two lines is not Displayed");
		}
		Assert.assertEquals(bookingpage.verifyDelAddress2(), true,
				"Delivery Address line 1 not splitted into two lines");
		if (bookingpage.verifyDelAddress3()) {
			Pass_Message_withoutScreenCapture("Delivery Address Line 2 is Displayed");
		} else {
			Fail_Message("Delivery Address Line 2 is not Displayed");
		}
		Assert.assertEquals(bookingpage.verifyDelAddress3(), true, "Delivery Address line 2 is not Displayed");

	}

	public void verifyBooking_Adressline2_and_Province() {

		BookingPage bookingpage = new BookingPage(driver);

		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String Deliv_Country = "United Kingdom";
			String Deliv_Postal = "MK1 1AA";
			String Town = "Milton Keynes";
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			verifyCollectionAddress_and_province();
			setDeliveryAddress(Deliv_Country, Deliv_Postal, Town, Deliv_Cust_Name, Deliv_Add);
			verifyDeliveryAddress_and_Province();
			if (bookingpage.successMsgonAddress()) {
				setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Goods Information page failed");

		}
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
	}

	public void verifybooking_CharcterLength_for_CustomerName_AddressLines() {

		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
			// "KEY", ACM_Booking.Key_Array[6]);
			// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
			String collection_Cust_Name = "Collection Customer to verify Customer Name with Sixty Charcters";
			String collection_Add = "Nybrogade 2, 1203 Copenhagen Street";
			String Deliv_Cust_Name = "Delivery Customer to verify Customer Name with Sixty Charcters";
			String Deliv_Add = "Nybrogade 2, 1203 Copenhagen Street";
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("700");
			bookingpage.setCollectionCustomerName(collection_Cust_Name);
			bookingpage.setCollectionAddress(collection_Add);
			bookingpage.setCollectionAddressLine2(collection_Add);
			bookingpage.setCollectionAddressLine3(collection_Add);
			bookingpage.clickCollectionValidatebtn();
			verifyCharcterLength_for_collectionCustomerName_and_addresslines(collection_Add);
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(Deliv_Cust_Name);
			bookingpage.setDeliveryAddress(Deliv_Add);
			bookingpage.setDeliveryAddressLine2(Deliv_Add);
			bookingpage.setDeliveryAddressLine3(Deliv_Add);
			verifyCharcterLength_for_deliveryCustomerName_and_addresslines(Deliv_Add);
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Goods Information page failed");

		}
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
	}

	public void verifyQuote_Adressline2_and_Province() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		try {
			Q_QuoteFlow();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			uiTestHelper.propagateException();
			verifyCollectionAddress_and_province();
			verifyDeliveryAddress_and_Province();
			bookingpage.clickSameAsCallerInfo();
			Pass_Message("Details entered successfully in Booking Information Page while converting from Quote");
			bookingpage.clickBookingnextbtn();
			goodsinfopage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			Q_AdditionalInfo_Page();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Convert to Booking failed details validation failed");

		}
	}

	public void verifyQuote_CharcterLength_for_CustomerName_AddressLines() {
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);

		try {
			String collection_Cust_Name = "Collection Customer to verify Customer Name with Sixty Charcters";
			String collection_Add = "Nybrogade 2, 1203 Copenhagen Street";
			String Deliv_Cust_Name = "Delivery Customer to verify Customer Name with Sixty Charcters";
			String Deliv_Add = "Nybrogade 2, 1203 Copenhagen Street";
			quotepage_with_exceedCharacters_of_Address_and_CustomerName(collection_Cust_Name, collection_Add,
					Deliv_Cust_Name, Deliv_Add);
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			BookingPage bookingPage = new BookingPage(driver);
			uiTestHelper.propagateException();
			verifyCharcterLength_for_collectionCustomerName_and_addresslines(collection_Add);
			verifyCharcterLength_for_deliveryCustomerName_and_addresslines(Deliv_Add);
			bookingPage.clickSameAsCallerInfo();
			Pass_Message("Details entered successfully in Booking Information Page while converting from Quote");
			bookingPage.clickBookingnextbtn();
			GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
			goodsInfoPage.clickGoodsInfoNextBtn();
			ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			Q_AdditionalInfo_Page();

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Convert to Booking failed details validation failed");

		}
	}

	// Quote Booking Page
	public void quotepage_with_exceedCharacters_of_Address_and_CustomerName(String collection_Cust_Name,
			String collection_Add, String deliv_Cust_Name, String deliv_Add) {

		BookingPage bookingpage = new BookingPage(driver);

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		QuotePage quotePage = new QuotePage(driver);
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			custaccpage.selectCustomerAccounts(AcctName);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewQuote();
			}
			quotePage.clickSenderButton();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection details are updated successfully");
			}
			bookingpage.setCollectionCustomerName(collection_Cust_Name);
			bookingpage.setCollectionAddress(collection_Add);
			bookingpage.setCollectionAddressLine2(collection_Add);
			bookingpage.setCollectionAddressLine3(collection_Add);
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(deliv_Cust_Name);
			bookingpage.setDeliveryAddress(deliv_Add);
			bookingpage.setDeliveryAddressLine2(deliv_Add);
			bookingpage.setDeliveryAddressLine3(deliv_Add);
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Delivery details are updated successfully");
			}
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("500");
			bookingpage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Page validation failed");
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
	}

	public void bookingpage_with_exceedCharacters_of_Address_and_CustomerName(String collection_Cust_Name,
			String collection_Add, String deliv_Cust_Name, String deliv_Add) {

		BookingPage bookingpage = new BookingPage(driver);

		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection details are updated successfully");
			}
			// bookingpage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("700");
			bookingpage.setCollectionCustomerName(collection_Cust_Name);
			bookingpage.setCollectionAddress(collection_Add);
			bookingpage.setCollectionAddressLine2(collection_Add);
			bookingpage.setCollectionAddressLine3(collection_Add);
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(deliv_Cust_Name);
			bookingpage.setDeliveryAddress(deliv_Add);
			bookingpage.setDeliveryAddressLine2(deliv_Add);
			bookingpage.setCollectionAddressLine3(deliv_Add);
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Delivery details are updated successfully");
				setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Page validation failed");
		}
	}

	public void verifyCloneBooking_Address_Province() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			BK_SISP_Flow_without_Validation();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickCloneBooking();
			if (bookingpage.verifyBookingPageTitleByClone()) {
				Pass_Message("Navigated to Booking Page after Clicking Clone");
				uiTestHelper.scrolldown("700");
				if (bookingpage.verifyBookingNextBtnByClone()) {
					Pass_Message("Booking Details are auto Populated by clone");
				} else {
					Fail_Message("Booking Details are not auto Populated by clone");
				}
			} else {
				Fail_Message("Booking Page is not displayed after clicking Clone");
			}
			verifyCollectionAddress_and_province();
			verifyDeliveryAddress_and_Province();
			bookingpage.clickBookingnextbtn();
			goodsinfopage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			BK_AdditionalInfo_Page();
			if (!bookNum.isEmpty()) {
				Pass_Message("Address and Province verified while cloning the booking and booking id is: " + bookNum);

			} else {
				Fail_Message("Address and province not verified for Booking clone");
			}
		} catch (Exception e) {
			Fail_Message("Address and province is not verified While the cloning the booking");
		}
	}

	public void verifyCharcterLength_for_collectionCustomerName_and_addresslines(String collection_Add) {
		BookingPage bookingpage = new BookingPage(driver);
		String collCustomerName = bookingpage.getCollectionCustomerName();
		String collName = collCustomerName.substring(0, 60);
		if (collName.equals(collCustomerName)) {
			Pass_Message("Collection Customer Name with 60 Characters are displayed");
		} else {
			Fail_Message("Collection Cutomer Name will allow more than 60 Characters,"
					+ " but it should be < 60 or equal to 60 Charcters");
		}
		Assert.assertEquals(collCustomerName, collName, "Collection Customer Name not met with 60 Charcter Length");
		String collfirstAddressLine = bookingpage.getCollectionAddress();
		String colladdressLine1 = collection_Add.substring(0, 30);
		if (colladdressLine1.equals(collfirstAddressLine)) {
			Pass_Message("Collection Address Line 1 with 30 Characters are displayed");
		} else {
			Fail_Message("Collection Address Line 1 will allow more than 30 Characters,"
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(colladdressLine1, collfirstAddressLine,
				"Collection Address line 1 not met with 30 Charcter Length");
		String collfirstAddressLine12 = bookingpage.getCollectionAddressLine2();
		String colladdressLine12 = collection_Add.substring(0, 30);
		if (colladdressLine12.equals(collfirstAddressLine12)) {
			Pass_Message("Collection Address Line 1 splitted and it with 30 Characters are displayed");
		} else {
			Fail_Message("Collection Address Line 1 splitted line will allow more than 30 Characters,"
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(collfirstAddressLine12, colladdressLine12,
				"Collection Address line1 splitted line not met with 30 Charcter Length");
		String collsecondAddressLine = bookingpage.getCollectionAddressLine3();
		String colladdressLine2 = collection_Add.substring(0, 30);
		if (colladdressLine2.equals(collsecondAddressLine)) {
			Pass_Message("Collection Address Line 2 with 30 Characters are displayed");
		} else {
			Fail_Message("Collection Address Line 2 will allow more than 30 Characters, "
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(colladdressLine2, collsecondAddressLine,
				"Collection Address line 2 not met with 30 Charcter Length");
	}

	public void verifyCharcterLength_for_deliveryCustomerName_and_addresslines(String Deliv_Add) {
		BookingPage bookingpage = new BookingPage(driver);
		String delCustomerName = bookingpage.getDeliveryCustomerName();
		String delName = delCustomerName.substring(0, 60);
		if (delName.equals(delCustomerName)) {
			Pass_Message("Delivery Customer Name with 60 Characters are displayed");
		} else {
			Fail_Message("Delivery Cutomer Name will allow more than 60 Characters,"
					+ " but it should be < 60 or equal to 60 Charcters");
		}
		Assert.assertEquals(delName, delCustomerName, "Delivery Customer Name not met with 60 Charcter Length");
		String delfirstAddressLine = bookingpage.getDeliveryAddress();
		String deladdressLine1 = Deliv_Add.substring(0, 30);
		if (deladdressLine1.equals(delfirstAddressLine)) {
			Pass_Message("Delivery Address Line 1 with 30 Characters are displayed");
		} else {
			Fail_Message("Delivery Address Line 1 will allow more than 30 Characters,"
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(delfirstAddressLine, deladdressLine1,
				"Delivery Address line 1 not met with 30 Charcter Length");
		String delfirstAddressLine12 = bookingpage.getDeliveryAddressLine2();
		String deladdressLine12 = Deliv_Add.substring(0, 30);
		if (deladdressLine12.equals(delfirstAddressLine12)) {
			Pass_Message("Delivery Address Line 1 splitted and it with 30 Characters are displayed");
		} else {
			Fail_Message("Delivery Address Line 1 splitted line will allow more than 30 Characters,"
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(delfirstAddressLine12, deladdressLine12,
				"Delivery Address line1 splitted line not met with 30 Charcter Length");
		String delsecondAddressLine = bookingpage.getDeliveryAddressLine3();
		String deladdressLine3 = Deliv_Add.substring(0, 30);
		if (deladdressLine3.equals(delsecondAddressLine)) {
			Pass_Message("Delivery Address Line 2 with 30 Characters are displayed");
		} else {
			Fail_Message("Delivery Address Line 2 will allow more than 30 Characters, "
					+ " but it should be < 30 or equal to 30 Charcters");
		}
		Assert.assertEquals(deladdressLine3, delsecondAddressLine,
				"Delivery Address line 2 not met with 30 Charcter Length");
	}

	public void verifyCloneBooking_with_exceedCharacters_of_Address_and_CustomerName() {
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(driver);
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			String collection_Cust_Name = "Collection Customer to verify Customer Name with Sixty Charcters";
			String collection_Add = "Nybrogade 2, 1203 Copenhagen Street";
			String Deliv_Cust_Name = "Delivery Customer to verify Customer Name with Sixty Charcters";
			String Deliv_Add = "Nybrogade 2, 1203 Copenhagen Street";
			bookingpage_with_exceedCharacters_of_Address_and_CustomerName(collection_Cust_Name, collection_Add,
					Deliv_Cust_Name, Deliv_Add);
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();

			System.out.println(bookNum);
			BK_getRecentBookingfrom_BookingList(bookNum);

			bkdetailspage.clickCloneBooking();
			if (bookingpage.verifyBookingPageTitleByClone()) {
				Pass_Message("Navigated to Booking Page after Clicking Clone");
			}
			verifyCharcterLength_for_collectionCustomerName_and_addresslines(collection_Add);
			verifyCharcterLength_for_deliveryCustomerName_and_addresslines(Deliv_Add);
			uiTestHelper.scrolldown("700");
			bookingpage.clickBookingnextbtn();
			goodsinfopage.clickGoodsInfoNextBtn();
			coninfopage.clickConsignmentInfoNextBtn();
			BK_AdditionalInfo_Page();
			Assert.assertEquals(bookNum.isEmpty(), true, "Clone Booking not Created");
			if (!bookNum.isEmpty()) {
				Pass_Message(
						"Address and Customer Name Characters are verified while cloning the booking and booking id is: "
								+ bookNum);
			} else {
				Fail_Message("Address and Customer Name Characters are not verified for Booking clone");
			}

		} catch (Exception e) {
			Fail_Message("Address and Customer Name fields are not verified While the cloning the booking");
		}
	}

	public void verifyCharcterlength_for_addresslines_when_exceedcharcterLimit_on_addressLine1(
			String firstAddressLine) {
		BookingPage bookingpage = new BookingPage(driver);
		String addressLine1 = bookingpage.getCollectionAddress();
		String addresslineFirst = firstAddressLine.substring(0, 30);
		if (addresslineFirst.equals(addressLine1)) {
			Pass_Message("First Address Line updated with 30 Characters are displayed"
					+ "When we have more than 30 characters of Address Line 1");
		} else {
			Fail_Message("First Address Line updated with 30 Characters are displayed");
		}
		Assert.assertEquals(addresslineFirst, addressLine1, "Address Line 1 not met 30 Charcters Limit");
		String addressLine12 = bookingpage.getCollectionAddressLine2();
		String addresslineFirst12 = firstAddressLine.substring(30, 60);
		if (addressLine12.equals(addresslineFirst12)) {
			Pass_Message("Splitted First Address Line updated with 30 Characters are displayed"
					+ "When we have more than 30 characters of Address Line 1");
		} else {
			Fail_Message("Splitted First Address Line updated with 30 Characters are displayed");
		}
		Assert.assertEquals(addressLine12, addresslineFirst12, "Splitted Address Line 1 not met 30 Charcters Limit");
		String addressLine2 = bookingpage.getCollectionAddressLine3();
		String addresslineSecond = firstAddressLine.substring(60, 90);
		if (addresslineSecond.equals(addressLine2)) {
			Pass_Message("Second Address Line updated with remaining 30 Characters of address Line 1 "
					+ "are displayed When we have more" + "than 30 characters of Address Line 1");
		} else {
			Fail_Message("Second Address Line updated with 30 Characters are displayed");
		}
		Assert.assertEquals(addressLine2, addresslineSecond,
				"Second Address Line 1 not met 30 Charcters Limit when Address Line 1 exceeds 30 charcter");

	}

	public void verifyBooking_AddressLine2_while_chracterExceeded_on_AddressLine1() {

		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = "PORTUGAL PMC";
		String firstAddressLine = "R.DAS CARDOSAS , 656 to test the character limit of"
				+ " the address lines to test the new implementation based on the new user story for this fix";
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.callerInfo_SISP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection details are updated successfully");
		}
		bookingpage.clickConfirm();
		verifyCharcterlength_for_addresslines_when_exceedcharcterLimit_on_addressLine1(firstAddressLine);
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		if (!bookNum.isEmpty()) {
			Pass_Message(
					"Address 2 is verified when we have exceed charcters of Address Line 1 with Account and booking id is: "
							+ bookNum);
		} else {
			Fail_Message("Address 2 is not verified when we have exceed charcters of Address Line 1 with Account");
		}
	}

	public void verifyDGMessage_when_duplicateUNNumber() {

		GoodsInfoPage goodspage = new GoodsInfoPage(driver);
		BK_BookingPage_SISP();
		setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
		goodspage.clickDangerousGoodsYes();
		setDGGoods("3010", "2", "Lithium Battery", 1);
		goodspage.setUNNumber("3010");
		goodspage.searchDangerousGoods();
		if (goodspage.verifyErrorMessage()) {
			if (goodspage.verifyDuplicateUNnumberMsg()) {
				Pass_Message("Error displayed when we have Duplicate UN Number given. "
						+ goodspage.getDuplicateUNnumberMsg());
			} else {
				Fail_Message("Error not displayed when we have Duplicate UN Number given. ");
			}
		} else {
			Fail_Message("Message not diplayed when we have duplicate UN Number");
		}

	}

	public void verifyDGErrorMessage_with_UNNumber() {

		GoodsInfoPage goodspage = new GoodsInfoPage(driver);
		BK_BookingPage_SISP();
		setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
		goodspage.clickDangerousGoodsYes();
		Assert.assertTrue(goodspage.verifyUNNumberDangGood());
		if (goodspage.verifyUNNumberDangGood()) {
			goodspage.setUNNumber("3090");
			Pass_Message("UN Number Field is Displayed and User is updated the UNNumber");
		}
		goodspage.searchDangerousGoods();
		if (goodspage.verifyErrorMessage()) {
			if (goodspage.verifyUNnumberShipMsg()) {
				Pass_Message("Error displayed when we are not able to ship the goods and the Message is "
						+ goodspage.getUNnumberShipMsg());
			} else {
				Fail_Message("Error not displayed when we are not able to ship the goods");
			}
		} else {
			Fail_Message("Message not diplayed when we are not able to ship the goods");
		}
		goodspage.setUNNumber("0000");
		goodspage.searchDangerousGoods();
		if (goodspage.verifyErrorMessage()) {
			if (goodspage.verifyIncorrectUNnumberMsg()) {
				Pass_Message("Error displayed when we have incorrect UN Number given. "
						+ goodspage.getIncorrectUNnumberMsg());
			} else {
				Fail_Message("Error not displayed when we have incorrect UN Number given. ");
			}
		} else {
			Fail_Message("Message not diplayed when we have incorrect UN Number");
		}

	}

	public void verifyDifferent_Packaging_Group_for_goods() {

		GoodsInfoPage goodspage = new GoodsInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
			goodspage.clickDangerousGoodsYes();
			Assert.assertTrue(goodspage.verifyUNNumberDangGood());
			goodspage.setUNNumber("3010");
			goodspage.searchDangerousGoods();
			if (goodspage.verifyDangeoursGoodsTable()) {
				goodspage.selectPackagingGroup("II");
				Pass_Message("Packaging Group selected and PG is : " + goodspage.getPacakagingGroup());
			}
			goodspage.deleteDangerousGoods();
			if (goodspage.verifyUNNumberDangGood()) {
				goodspage.setUNNumber("3010");
				goodspage.searchDangerousGoods();
			}
			if (goodspage.verifyDangeoursGoodsTable()) {
				goodspage.selectPackagingGroup("III");
				Pass_Message("Packaging Group selected and PG is : " + goodspage.getPacakagingGroup());
			}
		} catch (Exception e) {
			Fail_Message("Packaging group is not verified for Goods");
		}
	}

	public void verifyGoodsQuantity_with_decimalValues() {

		GoodsInfoPage goodspage = new GoodsInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			setGoodsInformation("Non-Document", "Test Desc", "20", "12345");
			goodspage.clickDangerousGoodsYes();
			setDGGoods("3010", "2.5", "Lithium Battery", 1);
			goodspage.clickGoodsInfoNextBtn();
			BK_ConInfo_Page();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			Fail_Message("Quantity with decimal values is not verified");
		}
	}

	public void verifyDangerousGoods_Limitation() {

		GoodsInfoPage goodspage = new GoodsInfoPage(driver);
		try {
			BK_BookingPage_SISP();
			setGoodsInformation("Non-Document", "Test Desc", "20", "1");
			goodspage.clickDangerousGoodsYes();
			setDGGoods("3010", "2", "Lithium Battery", 1);
			setDGGoods("1021", "2", "Lithium Battery", 2);
			setDGGoods("3020", "2", "Lithium Battery", 3);
			if (!goodspage.verifyUNNumberField()) {
				Pass_Message("Dangerous Goods limitation is 3");
			} else {
				Fail_Message("Dangerous Goods Limitation is more than 3");
			}
		} catch (Exception e) {
			Fail_Message("Dangerous Goods Limitation is not verified");
		}
	}

	public void verifyDangerousGoods_with_consignmentNoteEmail() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			BK_SISP_DG_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickConsignmentNote();
			String emailAddress = "nivetha.thirunavukarasu.osv@fedex.com";
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Consignment Number is Creadted Successfully");
			} else {
				Fail_Message("Consignment Number is Not Creadted Successfully");
			}
			bkdetailspage.clickCancelonConsignmentNote();
			bkdetailspage.clickConsignmentNote();
			bkdetailspage.clickEmailField();
			bkdetailspage.clickOther();
			System.out.println(emailAddress);
			uiTestHelper.propagateException();
			bkdetailspage.setEmailAddress(emailAddress);
			verifyConsignementEmailforBooking(emailAddress);
		} catch (Exception e) {
			Fail_Message("Email not sent with Consignment Note for Dangerous Goods Shipment");
		}
	}

	public void verifyProductOptions_while_convert_Quote_to_Booking() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		try {
			Q_QuoteFlow();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			uiTestHelper.propagateException();
			bookingPage.clickSameAsCallerInfo();
			Pass_Message("Details entered successfully in Booking Information Page");
			bookingPage.clickBookingnextbtn();
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			Q_AdditionalInfoPage_with_productOptions();
		} catch (Exception e) {
			Fail_Message("Email not sent with Consignment Note for Dangerous Goods Shipment");
		}

	}

	public void verifyDangerousGoods_while_clone_the_booking() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);

		try {
			BK_SISP_DG_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickCloneBooking();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.propagateException();
				uiTestHelper.scrolldown("700");
				bookingPage.clickSameAsCallerInfo();
				bookingPage.clickBookingnextbtn();
			}
			System.out.println(goodsInfoPage.getUNNumber());
			if (goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message(
						"Clone Booking having the exsisting Dangerous Goods: " + goodsInfoPage.getDGFullDescription());
			}
			goodsInfoPage.setQuantity("1", 1);
			goodsInfoPage.setDangerousGoodsOptions("Dry Ice", 1);
			System.out.println(goodsInfoPage.getQuantity(1) + " " + goodsInfoPage.getDangerousGoodsOptions(1));
			if (goodsInfoPage.getQuantity(1).contains("1")
					&& goodsInfoPage.getDangerousGoodsOptions(1).equals("Dry Ice")) {
				Pass_Message("Quantity and DG options are editable");
			}
			Assert.assertTrue(goodsInfoPage.verifyUNNumberDangGood());
			if (goodsInfoPage.verifyUNNumberDangGood()) {
				goodsInfoPage.setUNNumber("3091");
			}
			goodsInfoPage.searchDangerousGoods();
			boolean isTable = goodsInfoPage.verifyDangeoursGoodsTable();
			Assert.assertTrue(isTable);
			if (isTable) {
				goodsInfoPage.selectDangerousGoods();
			} else {
				Fail_Message("Dangerous good is not selected from the table");
			}
			goodsInfoPage.setQuantity("2", 2);
			goodsInfoPage.setDangerousGoodsOptions("Lithium Battery", 2);
			uiTestHelper.scrolldown("300");
			goodsInfoPage.deleteDangerousGoods();
			if (!goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message("Dangerous Good with UN Number : 3010 is deleted");
			}
			goodsInfoPage.clickDangerousGoodsInd();
			try {
				goodsInfoPage.verifyUNNumberField();
			} catch (Exception e) {
				Pass_Message("Dangerous Goods indicator changed to No");
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			BK_AdditionalInfo_Page();
		} catch (Exception e) {
			Fail_Message("Dangerous Goods details not verified while Cloning the Booking");
		}

	}

	public void verifyDangerousGoods_while_edit_the_booking() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);

		try {
			BK_SISP_DG_Flow();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.propagateException();
				uiTestHelper.scrolldown("700");
				bookingPage.clickBookingnextbtn();
			}
			System.out.println(goodsInfoPage.getUNNumber());
			if (goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message(
						"Booking Edit having the exsisting Dangerous Goods: " + goodsInfoPage.getDGFullDescription());
			}
			goodsInfoPage.setQuantity("1", 1);
			goodsInfoPage.setDangerousGoodsOptions("Dry Ice", 1);
			System.out.println(goodsInfoPage.getQuantity(1) + " " + goodsInfoPage.getDangerousGoodsOptions(1));
			if (goodsInfoPage.getQuantity(1).contains("1")
					&& goodsInfoPage.getDangerousGoodsOptions(1).equals("Dry Ice")) {
				Pass_Message("Quantity and DG options are editable while editing the Booking");
			}
			Assert.assertTrue(goodsInfoPage.verifyUNNumberDangGood());
			if (goodsInfoPage.verifyUNNumberDangGood()) {
				goodsInfoPage.setUNNumber("3091");
			}
			goodsInfoPage.searchDangerousGoods();
			boolean isTable = goodsInfoPage.verifyDangeoursGoodsTable();
			Assert.assertTrue(isTable);
			if (isTable) {
				goodsInfoPage.selectDangerousGoods();
			} else {
				Fail_Message("Dangerous good is not selected from the table");
			}
			goodsInfoPage.setQuantity("2", 2);
			goodsInfoPage.setDangerousGoodsOptions("Lithium Battery", 2);
			uiTestHelper.scrolldown("300");
			goodsInfoPage.deleteDangerousGoods();
			if (!goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message("Dangerous Good with UN Number : 3010 is deleted while editing the booking");
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			BK_AdditionalInfoPage_EditBooking();

		} catch (Exception e) {
			Fail_Message("Dangerous Goods details not verified while Editing the Booking");
		}

	}

	public void verifyDangerousGoods_while_convertQuote_to_the_booking() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);

		try {
			Quote_DG_Sender();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.scrolldown("700");
				uiTestHelper.propagateException();
				bookingPage.clickSameAsCallerInfo();
				bookingPage.clickBookingnextbtn();
			}
			System.out.println(goodsInfoPage.getUNNumber());
			if (goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message("Quote Conversion to  Booking having the exsisting Dangerous Goods: "
						+ goodsInfoPage.getDGFullDescription());
			}
			goodsInfoPage.setQuantity("1", 1);
			goodsInfoPage.setDangerousGoodsOptions("Dry Ice", 1);
			System.out.println(goodsInfoPage.getQuantity(1) + " " + goodsInfoPage.getDangerousGoodsOptions(1));
			if (goodsInfoPage.getQuantity(1).contains("1")
					&& goodsInfoPage.getDangerousGoodsOptions(1).equals("Dry Ice")) {
				Pass_Message("Quantity and DG options are editable while converting quote to Booking");
			}
			Assert.assertTrue(goodsInfoPage.verifyUNNumberDangGood());
			if (goodsInfoPage.verifyUNNumberDangGood()) {
				goodsInfoPage.setUNNumber("3091");
			}
			goodsInfoPage.searchDangerousGoods();
			boolean isTable = goodsInfoPage.verifyDangeoursGoodsTable();
			Assert.assertTrue(isTable);
			if (isTable) {
				goodsInfoPage.selectDangerousGoods();
			} else {
				Fail_Message("Dangerous good is not selected from the table");
			}
			goodsInfoPage.setQuantity("2", 2);
			goodsInfoPage.setDangerousGoodsOptions("Lithium Battery", 2);
			uiTestHelper.scrolldown("300");
			goodsInfoPage.deleteDangerousGoods();
			if (!goodsInfoPage.getUNNumber().equals("3010")) {
				Pass_Message("Dangerous Good with UN Number : 3010 is deleted while converting quote to Booking");
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			Q_AdditionalInfo_Page();

		} catch (Exception e) {
			Fail_Message("Dangerous Goods details not verified while converting quote the Booking");
		}

	}

	public void Quote_DG_Sender() {

		try {
			Q_QuoteInfoPage();
			BK_GoodsInfoPage_DG();
			BK_ConInfo_Page();
			Q_AdditionalInfoPage();

		} catch (Exception e) {
			Fail_Message("Quote Flow failed with DG");
		}
	}

	public void verifyServiceSelection_while_convertQuote_to_the_booking() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		try {
			Q_QuoteFlow();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.scrolldown("700");
				uiTestHelper.propagateException();
				bookingPage.clickSameAsCallerInfo();
				bookingPage.clickBookingnextbtn();
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			additionalInfoPage.enableProductOption("Priority");
			additionalInfoPage.enableProductOption("Late Pickup");
			additionalInfoPage.enableProductOption("CS Arranged Import Services");
			additionalInfoPage.enableProductOption("Saturday Delivery");
			if (additionalInfoPage.verifyProductOptionsSelected("Priority")
					&& additionalInfoPage.verifyProductOptionsSelected("Late Pickup")
					&& additionalInfoPage.verifyProductOptionsSelected("CS Arranged Import Services")
					&& additionalInfoPage.verifyProductOptionsSelected("Saturday Delivery")) {
				Pass_Message("All Product Options are selected while converting quote to Booking");
			} else {
				Fail_Message("Product Options are not selected");
			}
			additionalInfoPage.disableProductOption("Late Pickup");
			additionalInfoPage.disableProductOption("CS Arranged Import Services");
			additionalInfoPage.disableProductOption("Saturday Delivery");
			additionalInfoPage.clickValidServices();
			if (additionalInfoPage.verifyGetPrice()) {
				additionalInfoPage.clickGetPrice();
			}
			if (additionalInfoPage.verifyPriceOnTable()) {
				Pass_Message("Prices are displayed on the Service Table");
			} else {
				Fail_Message("Price not displayed for all the Services");
			}
			additionalInfoPage.getValidServices();
			uiTestHelper.scrolldown("300");
			String price = additionalInfoPage.getPrice("Customer Rate");
			additionalInfoPage.deSelectService();
			additionalInfoPage.clickGetPrice();
			String priceBeforeSelectingService = additionalInfoPage.getPriceOnTable();
			if (priceBeforeSelectingService.equals(price) && additionalInfoPage.verifyPriceOnTable()) {
				Pass_Message_withoutScreenCapture(
						"Price is maintained and Visible when we deselect the selected service");
			}
			additionalInfoPage.getValidServices();
			uiTestHelper.scrolldown("700");
			additionalInfoPage.clickViewSummary();
			additionalInfoPage.clickConfirmBooking();
			String book = additionalInfoPage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			uiTestHelper.propagateException();
			if (additionalInfoPage.isBookingReferenceNoCreated()) {
				Pass_Message("Quote to Booking converted successfully and booking number is: " + bookNum);
			} else {
				Fail_Message("Quote to Booking conversion failed");
			}
		} catch (Exception e) {
			Fail_Message("Service Selection details not verified while converting quote the Booking");
		}

	}

	public void verifyDetailedRoutingInfo_while_convert_Quote_to_the_booking() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		try {
			Q_QuoteFlow();
			Q_getRecentQuotefrom_BookingList(quoteNum);
			quoteDetailPage.clickConvertToBookingBtn();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.scrolldown("700");
				uiTestHelper.propagateException();
				bookingPage.clickSameAsCallerInfo();
				bookingPage.clickBookingnextbtn();
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			additionalInfoPage.enableProductOption("Priority");
			additionalInfoPage.clickValidServices();
			uiTestHelper.scrolldown("700");
			additionalInfoPage.getValidServices();
			if (additionalInfoPage.verifyDetailButton()) {
				Pass_Message_withoutScreenCapture("Detail Button is Displayed");
			}
			additionalInfoPage.clickDetailnfoBtn();
			uiTestHelper.scrolldown("300");
			if (additionalInfoPage.verifyDetailedRoutingInformation()) {
				Pass_Message("Routing Information is Displayed");
			}
			additionalInfoPage.clickUnselectService();
			additionalInfoPage.getValidServices();
			uiTestHelper.scrolldown("700");
			additionalInfoPage.clickViewSummary();
			additionalInfoPage.clickConfirmBooking();
			String book = additionalInfoPage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			uiTestHelper.propagateException();
			if (additionalInfoPage.isBookingReferenceNoCreated()) {
				Pass_Message("Quote to Booking converted successfully and booking number is: " + bookNum);
			} else {
				Fail_Message("Quote to Booking conversion failed");
			}
		} catch (Exception e) {
			Fail_Message("Routing Information details not verified while converting quote the Booking");
		}

	}

	public void verifyException_Details_when_AwkwardFreight_not_approved_for_Booking() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		CCD_Connectivity connectivity = new CCD_Connectivity();
		HomePage homepage = new HomePage(driver);
		BookingExceptionsPage bookingexcppage = new BookingExceptionsPage(driver);
		try {
			BK_BookingPage_SISP();
			BK_GoodsInfoPage();
			BK_ConInfo_Page();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			Pass_Message_withoutScreenCapture("Valid Service is selected");
			uiTestHelper.scrolldown("300");
			addinfopage.setCollectionInstruction("Test instruction for collection driver");
			addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
			connectivity.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickBookingException();
			bookingexcppage.clickRecentlyViewedItems();
			bookingexcppage.searchCollectionCountryException("Booking Exception - PT");
			bookNum = bookNum.replaceAll("\"", "");
			System.out.println(bookNum);
			if (bookingexcppage.verifyHearderDisplayed("Booking Exception - PT")) {
				homepage.bookingExceptionSearch(bookNum);
				uiTestHelper.propagateException();
				homepage.refreshSearch();
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr")));
				boolean isOntheQueue = bookingexcppage.verifyBookingOntheQueue(bookNum);
				if (isOntheQueue) {
					Pass_Message("HLD Booking exception dispalyed on the Booking Exception - PT - Dedicated Queue");
				} else {
					Fail_Message("HLD Booking exception Not dispalyed on the Booking Exception - PT - Dedicated Queue");
				}

			}
			homepage.clearSearchList();
		} catch (Exception e) {
			Fail_Message("Exception not verified when Awkward Freight not approved for Booking");
		}

	}

	public void verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking() {

		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		try {
			BK_Book_MultipleItems_Overlimits();
			uiTestHelper.scrolldown("300");
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyPieceWeightAlert()) {
					Pass_Message("Individual Piece weight exceeded alert displayed with the Message: "
							+ addinfopage.getPieceWeightAlertMsg());
				}
			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.approveAckwardFrieght();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			Fail_Message("Alert Message not verified when Individual weight exceeded for Booking");
		}
	}

	public void verifyDimension_alert_when_exceeded_dimension_updated_for_the_Booking() {

		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		try {
			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "1");
			for (int i = 0; i <= 1 - i; i++) {
				coninfopage.addItem();
			}
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			driver.findElement(By.xpath("(//label[text()='Length (cm)']/following::input[1])[2]")).sendKeys("800",
					Keys.TAB);

			driver.findElement(By.xpath("(//label[text()='Width (cm)']/following::input[1])[2]")).sendKeys("900",
					Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Height (cm)']/following::input[1])[2]")).sendKeys("2100",
					Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[2]")).sendKeys("60",
					Keys.TAB);
			uiTestHelper.scrolldown("300");
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyPieceWeightAlert()) {
					Pass_Message("Dimension exceeded alert displayed with the Message: "
							+ addinfopage.getPieceWeightAlertMsg());
				}
			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.approveAckwardFrieght();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

			verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking();
		} catch (Exception e) {
			Fail_Message("Alert Message not verified when dimesion exceeded for the Booking");
		}
	}

	public void verifyIndividualpieceweight_alert_when_exceeded_weight_while_edit_the_Booking() {

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			if (bookingPage.verifyBookingTitleonEdit()) {
				uiTestHelper.propagateException();
				uiTestHelper.scrolldown("700");
				bookingPage.clickBookingnextbtn();
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			driver.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[1]")).clear();
			driver.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[1]")).sendKeys("71");
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyPieceWeightAlert()) {
					Pass_Message(
							"Individual Piece weight exceeded alert displayed while edit the Booking  with the Message: "
									+ addinfopage.getPieceWeightAlertMsg());
				}
			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickUpdateBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getUpdatedBookingConfirmMsg();
			bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Edit Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			Fail_Message("Alert Message not verified when Individual weight exceeded for edit Booking");
		}
	}

	public void BK_Book_MultipleItems_totalWeight_exceeded() {

		try {

			BK_Nav_ConsignmentInformation_page();
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[1]", "2");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[1]", "21");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "28");
			hashmap.put("(//input[@name='quantity'])[2]", "10");
			for (int i = 0; i <= 1 - i; i++) {
				coninfopage.addItem();
			}
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			driver.findElement(By.xpath("(//label[text()='Length (cm)']/following::input[1])[2]")).sendKeys("20",
					Keys.TAB);

			driver.findElement(By.xpath("(//label[text()='Width (cm)']/following::input[1])[2]")).sendKeys("20",
					Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Height (cm)']/following::input[1])[2]")).sendKeys("21",
					Keys.TAB);
			driver.findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[2]")).sendKeys("60",
					Keys.TAB);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Threshhold value for total weight has not been validated succesfully");
		}

	}

	public void verifyConsignmentweight_alert_when_exceeded_totalWeight_for_Booking() {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		try {
			BK_Book_MultipleItems_totalWeight_exceeded();
			uiTestHelper.scrolldown("300");
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyConsignemntAlert()) {
					Pass_Message("Consignment weight exceeded alert displayed with the Message: "
							+ addinfopage.getConsignmentWeightAlertMsg());
				}
			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.approveAckwardFrieght();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			Fail_Message("Alert Message not verified when Consignment weight exceeded for Booking");
		}
	}

	public void verifyConsignmentweight_alert_when_exceeded_totalWeight_while_clone_the_Booking() {

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			verifyConsignmentweight_alert_when_exceeded_totalWeight_for_Booking();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickCloneBooking();
			if (bookingPage.verifyBookingTitle()) {
				uiTestHelper.propagateException();
				uiTestHelper.scrolldown("700");
				bookingPage.clickBookingnextbtn();
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyConsignemntAlert()) {
					Pass_Message(
							"Consignment weight exceeded alert displayed while cloning the Booking with the Message: "
									+ addinfopage.getConsignmentWeightAlertMsg());
				}

			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickConfirmBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Clone Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			Fail_Message("Alert Message not verified when Consignment weight exceeded for clone Booking");
		}
	}

	public void verifyDimension_alert_when_updated_anyDimension_while_edit_the_Booking() {

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		try {
			verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking();
			BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			if (bookingPage.verifyBookingTitleonEdit()) {
				uiTestHelper.propagateException();
				uiTestHelper.scrolldown("700");
				bookingPage.clickBookingnextbtn();
			}
			goodsInfoPage.clickGoodsInfoNextBtn();
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//label[text()='Length (cm)']/following::input[1])[1]", "700");
			hashmap.put("(//label[text()='Width (cm)']/following::input[1])[1]", "800");
			hashmap.put("(//label[text()='Height (cm)']/following::input[1])[1]", "2000");
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				driver.findElement(By.xpath(entry.getKey())).clear();
				driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			uiTestHelper.scrolldown("300");
			addinfopage.clickValidServices();
			if (addinfopage.verifyGetPrice()) {
				addinfopage.clickGetPrice();
			}
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			uiTestHelper.scrolldown("300");
			if (addinfopage.verifyAWKFriegtAlert()) {
				if (addinfopage.verifyPieceWeightAlert()) {
					Pass_Message("Dimension exceeded alert displayed while editing the Booking with the Message: "
							+ addinfopage.getPieceWeightAlertMsg());
				}
			}
			uiTestHelper.scrolldown("700");
			uiTestHelper.scrolldown("300");
			addinfopage.clickViewSummary();
			addinfopage.clickUpdateBooking();
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getUpdatedBookingConfirmMsg();
			bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Edit Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}

		} catch (Exception e) {
			Fail_Message("Alert Message not verified when Dimension exceeded for edit Booking");
		}
	}

	public void Nav_AdditionalInfoPage_with_Different_countryName(String acctNumber, String acctName) {
		BookingPage bookingpage = new BookingPage(driver);
		bookingSelectionOnHomepage(acctNumber);
		newBookingonCustomerAccPage(acctName);
		bookingpage.callerInfo_SISP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress("United Kingdom", "MK1 1AA", "BLETCHLEY", "Cust", "park town");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
	}

	public void Nav_Quote_AdditionalInfoPage_with_Different_countryName(String acctNumber, String acctName) {
		BookingPage bookingpage = new BookingPage(driver);
		QuotePage quotepage = new QuotePage(driver);
		bookingSelectionOnHomepage(acctNumber);
		newQuoteonCustomerAccPage(acctName);
		quotepage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress("United Kingdom", "MK1 1AA", "BLETCHLEY", "Cust", "park town");
		bookingpage.clickBookingnextbtn();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();

	}

	public void update_AdditionalInfoPage_confirmBooking() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		uiTestHelper.scrolldown("300");
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}

	}

	public void update_AdditionalInfoPage_updateBooking() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		uiTestHelper.scrolldown("300");
		addinfopage.clickViewSummary();
		addinfopage.clickUpdateBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}

	}

	public void verifyProductOptions_specific_to_country_Switzerland() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Nav_AdditionalInfoPage_with_Different_countryName("002901298", "TEST 4");
		addinfopage.enableProductOption("Satellite Clearance");
		if (addinfopage.verifyProductOptionsSelected("Satellite Clearance")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
	}

	public void verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Switzerland() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bkpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);

		Nav_AdditionalInfoPage_with_Different_countryName("002901298", "TEST 4");
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickCloneBooking();
		if (bkpage.verifyBookingPageTitleByClone()) {
			Pass_Message("Navigated to Booking Page after Clicking Clone");
			uiTestHelper.scrolldown("700");
		}
		bkpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		addinfopage.enableProductOption("Satellite Clearance");
		if (addinfopage.verifyProductOptionsSelected("Satellite Clearance")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_updateBooking();
	}

	public void verifyProductOptions_while_edit_the_Booking_when_specific_to_country_Switzerland() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bkpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);

		Nav_AdditionalInfoPage_with_Different_countryName("002901298", "TEST 4");
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bkpage.verifyBookingTitleonEdit()) {
			uiTestHelper.scrolldown("700");
			bkpage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		addinfopage.enableProductOption("Satellite Clearance");
		if (addinfopage.verifyProductOptionsSelected("Satellite Clearance")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_updateBooking();
	}

	public void verifyProductOptions_while_create_Quote_when_specific_to_country_Switzerland() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);

		Nav_Quote_AdditionalInfoPage_with_Different_countryName("002901298", "TEST 4");
		addinfopage.enableProductOption("Satellite Clearance");
		if (addinfopage.verifyProductOptionsSelected("Satellite Clearance")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
	}

	public void verifyProductOptions_specific_to_country_Slovenia() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		CCD_Connectivity connectivity = new CCD_Connectivity();
		Nav_AdditionalInfoPage_with_Different_countryName("879000158", "test");
		addinfopage.enableProductOption("Discount");
		if (addinfopage.verifyProductOptionsSelected("Discount")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		connectivity.CloseTab();
	}

	public void verifyProductOptions_while_clone_the_Booking_when_specific_to_country_Slovenia() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bkpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		CCD_Connectivity connectivity = new CCD_Connectivity();

		Nav_AdditionalInfoPage_with_Different_countryName("879000158", "test");
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickCloneBooking();
		if (bkpage.verifyBookingPageTitleByClone()) {
			Pass_Message("Navigated to Booking Page after Clicking Clone");
			uiTestHelper.scrolldown("700");
		}
		bkpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		addinfopage.enableProductOption("Discount");
		if (addinfopage.verifyProductOptionsSelected("Discount")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		connectivity.CloseTab();
	}

	public void verifyProductOptions_while_edit_the_Booking_when_specific_to_country_slovenia() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bkpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		CCD_Connectivity connectivity = new CCD_Connectivity();

		Nav_AdditionalInfoPage_with_Different_countryName("879000158", "test");
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bkpage.verifyBookingTitleonEdit()) {
			uiTestHelper.scrolldown("700");
			bkpage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		addinfopage.enableProductOption("Discount");
		if (addinfopage.verifyProductOptionsSelected("Discount")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_updateBooking();
		connectivity.CloseTab();
	}

	public void verifyProductOptions_while_create_Quote_when_specific_to_country_Slovenia() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		CCD_Connectivity connectivity = new CCD_Connectivity();
		Nav_Quote_AdditionalInfoPage_with_Different_countryName("879000158", "test");
		addinfopage.enableProductOption("Discount");
		if (addinfopage.verifyProductOptionsSelected("Discount")) {
			Pass_Message("Automotive and Satelite Clearance are enabled for Switzerland Country for the Booking");
		}
		uiTestHelper.scrolldown("300");
		update_AdditionalInfoPage_confirmBooking();
		connectivity.CloseTab();

	}

	public void verifyProductOptions_specific_to_country_Poland() {
		Nav_AdditionalInfoPage_with_Different_countryName("003001100", "TEST POLAND");

	}

	public void currencyUpdate_on_AdditionalInfoPage() {
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		additionalInfoPage.clickValidServices();
		if (additionalInfoPage.verifyGetPrice()) {
			additionalInfoPage.clickGetPrice();
			uiTestHelper.scrolldown("700");
		}
		additionalInfoPage.verifyPriceOnTable();
		String defaultCurrency = additionalInfoPage.getDefaultCurrency();
		if (defaultCurrency.equals("EUR")) {
			defaultCurrency = "U+20AC";
		}
		System.out.println(defaultCurrency);
		String currency = additionalInfoPage.currencyOnServiceTable("U+20AC");
		System.out.println(currency);
		if (currency.contains(defaultCurrency)) {
			Pass_Message("Default Currency EUR reflected on the Service Table");
		}
		additionalInfoPage.setCurrency("CUP");
		additionalInfoPage.clickGetPrice();
		additionalInfoPage.verifyPriceOnTable();
		String currencyInput = additionalInfoPage.getDefaultCurrency();
		Assert.assertEquals(currencyInput, "CUP");
		Pass_Message("Currency -CUP is selected from the list");
		String inputCurrencyOntheTable = additionalInfoPage.currencyOnServiceTable("CUP");
		if (inputCurrencyOntheTable.contains(currencyInput)) {
			Pass_Message("Selected Currency - CUP reflected on the Service Table");
		}

	}

	public void booking_with_currency() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		currencyUpdate_on_AdditionalInfoPage();
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");
		}

	}

	public void booking_currency_validation() {

		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		booking_with_currency();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickMoretab();
		bkdetailspage.clickAdditionalInfo();
		String price = bkdetailspage.getTotalPrice();
		if (price.contains("CUP")) {
			Pass_Message("Selected Currency - CUP reflected on the Booking Record Page");
		}
	}

	public void clone_booking_currency_validation() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		booking_with_currency();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickCloneBooking();
		if (bookingPage.verifyBookingTitle()) {
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("700");
			bookingPage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		String userCurrency = additionalInfoPage.getDefaultCurrency();
		if (userCurrency.equals("CUP")) {
			Pass_Message("Selected Currency - CUP reflected on the booking clone");
		}
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Clone Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");
		}
	}

	public void edit_booking_currency_validation() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		booking_with_currency();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickEditBooking();
		if (bookingPage.verifyBookingTitle()) {
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("700");
			bookingPage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("300");
		String userCurrency = additionalInfoPage.getDefaultCurrency();
		if (userCurrency.equals("CUP")) {
			Pass_Message("Selected Currency - CUP reflected on the booking -edit");
		}
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");
		}
	}

	public void quote_currency_validation() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		currencyUpdate_on_AdditionalInfoPage();
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getBookingConfirmMsg();
		quoteNum = book.replace("Quote is created successfully. Quote Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Quote is completed successfully and Quote reference number is: " + quoteNum);

		} else {
			Fail_Message("Quote creation failed");
		}
		Q_getRecentQuotefrom_BookingList(quoteNum);
		bkdetailspage.clickMoretab();
		bkdetailspage.clickAdditionalInfo();
		String price = bkdetailspage.getTotalPrice();
		if (price.contains("CUP")) {
			Pass_Message("Selected Currency - CUP reflected on the quote Record Page");
		}
	}

	public void verifyProductOptions_without_CSImportService_RIRPFlow_booking() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_BookingPage_RIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		if (additionalInfoPage.verifyProductOptionsSelected("CS Arranged Import Services")) {
			Pass_Message("CS Import Service Option Enabled for RIRP booking by Default");
		} else {
			Fail_Message("CS Import Service Option is Disabled for RIRP booking by default");
		}
		additionalInfoPage.disableProductOption("CS Arranged Import Services");
		update_AdditionalInfoPage_confirmBooking();
	}

	public void verifyProductOptions_without_CSImportService_domesticRIRPFlow() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_BookingPage_domesticRIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		if (additionalInfoPage.verifyProductOptionsAvailable("CS Arranged Import Services")) {
			Pass_Message("CS Import Service Option not Enabled for Domestic RIRP booking by Default");
		} else {
			Fail_Message("CS Import Service Option is Enabled for Domestic RIRP booking by default");
		}
		update_AdditionalInfoPage_confirmBooking();
	}

	public void verifyProductOptions_without_CSImportService_RIRPFlow_while_editing_the_booking() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		BK_BookingPage_RIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickEditBooking();
		if (bookingPage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("700");
			bookingPage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		if (additionalInfoPage.verifyProductOptionsSelected("CS Arranged Import Services")) {
			Pass_Message("CS Import Service Option Enabled for RIRP booking by Default");
			additionalInfoPage.disableProductOption("CS Arranged Import Services");
		} else {
			Fail_Message("CS Import Service Option is Disabled for RIRP booking by default");
		}
		update_AdditionalInfoPage_updateBooking();
	}

	public void verifyProductOptions_without_CSImportService_domesticRIRPFlow_while_editing_the_booking() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BookingRecordPage bkdetailspage = new BookingRecordPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		BK_BookingPage_domesticRIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bkdetailspage.clickEditBooking();
		if (bookingPage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("700");
			bookingPage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		if (additionalInfoPage.verifyProductOptionsAvailable("CS Arranged Import Services")) {
			Pass_Message("CS Import Service Option not Enabled for Domestic RIRP booking by Default");
		} else {
			Fail_Message("CS Import Service Option is Enabled for Domestic RIRP booking by default");
		}
		update_AdditionalInfoPage_updateBooking();
	}

	public void bookingPage_collectionAddress_with_SIRP() {

		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SIRP();
			// bookingpage.confirmCustomerInstruction();
			// uiTestHelper.scrolldown("300");
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection Address is Validated");
			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("SIRP booking Collection Address is not validated");
		}

	}

	public void verifyAlertMessage_with_SIRPBooking_when_blank_migrationDate() {

		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		bookingPage_collectionAddress_with_SIRP();
		bookingpage.setBillingAccNumber("001570331");
		bookingpage.setCountry("United Kingdom");
		bookingpage.clickValidate();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is validated");
			uiTestHelper.scrolldown("300");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		}
		if (goodsInfoPage.verifyGoodsDescTitle()) {
			Pass_Message_withoutScreenCapture("Alert Message pop up not displayed  the reciever is the payer and is "
					+ "in the process of migrating to FedEx Migration Date is blank ");
		}
	}

	public void verifyAlertMessage_with_SIRPBooking_when_only_migrationDate() {
		BookingPage bookingpage = new BookingPage(driver);
		bookingPage_collectionAddress_with_SIRP();
		bookingpage.setBillingAccNumber("000000651");
		bookingpage.setCountry("Portugal");
		bookingpage.clickValidate();
		if (bookingpage.verifyCustomerInstruction()) {

			Pass_Message("Alert pop - up displayed where the reciever is the payer and "
					+ "is in the process of migrating to " + "FedEx if Migration date is populated and overlap"
					+ " date has not passed agent : -->" + bookingpage.getCustomerInstruction());
		}
		bookingpage.confirmCustomerInstruction();
	}

	public void validateDeliveryDate_with_quoteSummary() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		Q_QuoteFlow();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		uiTestHelper.propagateException();
		if (quoteDetailPage.verifyQuoteSummaryButton()) {
			quoteDetailPage.clickQuoteSummaryButton();
		}
		quoteDetailPage.clickSendEmail();
		if (quoteDetailPage.verifyEmailsuccessMsg()) {
			Pass_Message("Email Sent");
		}

		quoteDetailPage.clickQuoteAdditionalInfoTab();
		HashedMap<String, String> map = quoteDetailPage.getServiceswithDeliveryDate();
		for (Entry<String, String> entry : map.entrySet()) {
			Pass_Message_withoutScreenCapture(
					"Service :-->" + entry.getKey() + " DeliveryDate :--> " + entry.getValue());
		}

	}

	public void verifyPayingAccountNumber_with_SIRPBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_SIRP_Flow();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingInfo();
		if (bookingRecordPage.verifyBillingAccountNumber()) {
			Assert.assertNotNull(bookingRecordPage.getBillingAccountNumber());
			Pass_Message("Billing Account Number " + bookingRecordPage.getBillingAccountNumber()
					+ " Displayed Under Booking Info for SIRP Flow");
		}
		if (bookingRecordPage.verifyCustomerAccountName()) {
			Assert.assertNotNull(bookingRecordPage.getCustomerAccountName());
			Pass_Message("Billing Account Name " + bookingRecordPage.getCustomerAccountName()
					+ " Displayed Under Booking Info for SIRP Flow");
		}
	}

	public void verifyPayingAccountNumber_with_SIRPQuote() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		Q_QuoteFlow_Receiver();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		Q_ConvertToBookingReceiver();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingInfo();
		if (bookingRecordPage.verifyBillingAccountNumber()) {
			Assert.assertNotNull(bookingRecordPage.getBillingAccountNumber());
			Pass_Message("Billing Account Number " + bookingRecordPage.getBillingAccountNumber()
					+ " Displayed Under Booking Info for SIRP Flow");
		}
		if (bookingRecordPage.verifyCustomerAccountName()) {
			Assert.assertNotNull(bookingRecordPage.getCustomerAccountName());
			Pass_Message("Billing Account Name " + bookingRecordPage.getCustomerAccountName()
					+ " Displayed Under Booking Info for SIRP Flow");
		}
		bookingRecordPage.clickMoretab();
		bookingRecordPage.clickAdditionalInfo();
		Assert.assertNotNull(bookingRecordPage.getLegacyOrderId());
		Pass_Message("Booking Number " + bookingRecordPage.getLegacyOrderId()
				+ " Displayed Under Additional Info for SIRP Flow");
	}

	public void validateTelephone_field_on_delivery_address_for_ItalyAccount() {

		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
			String billingAccountNumber = Database_Connection.retrieveTestData("REC_ACCT", "ACM", "KEY",
					CCD_Booking.Key_Array[8]);
			String country = Database_Connection.retrieveTestData("REC_COU", "ACM", "KEY", CCD_Booking.Key_Array[8]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SIRP();
			// bookingpage.confirmCustomerInstruction();
			// uiTestHelper.scrolldown("300");
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection Address is Validated");
			}
			bookingpage.setBillingAccNumber(billingAccountNumber);
			bookingpage.setCountry(country);
			bookingpage.clickValidate();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Delivery Address is validated");
				uiTestHelper.scrolldown("300");
				if (bookingpage.verifyDeliveryRegion()) {
					Pass_Message(
							"Delivery Telephone Number Splited in to two fields for Italy Account as Telephone and Region fields");
				}
				setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
			}
			Pass_Message("Details entered successfully in Booking Information Page");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Goods Information Page failed");

		}
	}

	public void validateTelephone_field_on_caller_and_Collection_for_ItalyAccount() {
		BookingPage bookingpage = new BookingPage(driver);
		try {

			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[8]);
			// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
			// "KEY", CCD_CI.Key_Array[6]);
			// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[6]);
			bookingSelectionOnHomepage(AcctName);
			newBookingonCustomerAccPage(AcctName);
			bookingpage.callerInfo_SISP();
			// bookingpage.confirmCustomerInstruction();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection Address is Validated");
			}
			if (bookingpage.verifyCallerRegion()) {
				Pass_Message("Telephone Number Splited in to two fields for" + " Italy Account as : Region--> "
						+ bookingpage.getCallerRegion() + ", " + "Telephone -->" + bookingpage.getCallerPhone());
			}
			uiTestHelper.scrolldown("300");
			setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
			setCollectionContactDetailswithRegion();
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("SISP booking Collection Address is not validated");
		}
	}

	public void verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		validateTelephone_field_on_caller_and_Collection_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		if (bookingPage.verifyBookingTitle()) {
			Pass_Message("Booking  - " + bookNum + "Edited Successfully");
		}
	}

	public void verifyTelephoneNumber_on_delivery_for_Italy_SIRPBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		validateTelephone_field_on_delivery_address_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page_SIRP();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		if (bookingPage.verifyBookingTitle()) {
			Pass_Message("Booking  - " + bookNum + "Edited Successfully");
		}
	}

	public void verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPCloneBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		validateTelephone_field_on_caller_and_Collection_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		uiTestHelper.propagateException();
		if (bookingPage.verifyBookingTitle()) {
			Pass_Message("Booking  - " + bookNum + "Cloned Successfully");
		}
		if (bookingPage.verifyCallerRegion()) {
			Pass_Message("Telephone Number Splited in to two fields for" + " Italy Account as : Region--> "
					+ bookingPage.getCallerRegion() + ", " + "Telephone -->" + bookingPage.getCallerPhone()
					+ " while cloned the booking");
		}
		uiTestHelper.scrolldown("300");
		if (bookingPage.verifyCollectionRegion()) {
			Pass_Message("Collection Address Telephone field splited in to" + " two fields for Italy Account as -"
					+ bookingPage.getCollectionRegion() + " while cloned the booking");
			bookingPage.clickSameAsCallerInfo();
			bookingPage.clickBookingnextbtn();
		}
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Clone Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}
	}

	public void verifyTelephoneNumber_on_deliveryAddress_for_Italy_SIRPCloneBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		validateTelephone_field_on_delivery_address_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page_SIRP();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		uiTestHelper.propagateException();
		if (bookingPage.verifyBookingTitle()) {
			Pass_Message("Booking  - " + bookNum + "Cloned Successfully");
		}
		uiTestHelper.scrolldown("300");
		if (bookingPage.verifyDeliveryRegion()) {
			Pass_Message("Delivery Telephone Number Splited in to two fields for Italy Account"
					+ " as Telephone and Region fields while clone the booking");
		}
		bookingPage.clickSameAsCallerInfo();
		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = additionalInfoPage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Clone Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}
	}

	public void quote_validateTelephone_field_on_caller_and_Collection_for_ItalyAccount() {

		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Quote.Key_Array[8]);
			String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);
			String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
					CCD_Booking.Key_Array[5]);

			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingpage = new BookingPage(driver);
			CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
			bookingSelectionOnHomepage(AcctName);
			custaccpage.selectCustomerAccounts(AcctName);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewQuote();
			}
			quotePage.clickSenderButton();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Collection Address is Updated Successfully");
			}
			// quotePage.clickCustomerInstrConfirmBtn();
			if (bookingpage.verifyCallerRegion()) {
				Pass_Message("Telephone Number Splited in to two fields for" + " Italy Account as : Region--> "
						+ bookingpage.getCallerRegion() + ", " + "Telephone -->" + bookingpage.getCallerPhone());
			}
			uiTestHelper.scrolldown("300");
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(Deliv_Cust_Name);
			bookingpage.setDeliveryAddress(Deliv_Add);
			bookingpage.deliveryValidatebtn();
			uiTestHelper.propagateException();
			if (bookingpage.successMsgonAddress()) {
				Pass_Message("Delivery Address is Updated Successfully");
				uiTestHelper.scrolldown("300");
				bookingpage.clickBookingnextbtn();

			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Booking Page validation failed");
		}
	}

	public void verifyTelephoneNumber_on_caller_and_Collection_for_Italy_QuoteSender() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		quote_validateTelephone_field_on_caller_and_Collection_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		if (bookingPage.verifyCallerRegion()) {
			Pass_Message("Telephone Number Splited in to two fields for" + " Italy Account as : Region--> "
					+ bookingPage.getCallerRegion() + ", " + "Telephone -->" + bookingPage.getCallerPhone()
					+ "while converting quote to Booking");
		}
		bookingPage.clickSameAsCallerInfo();
		Pass_Message("Details entered successfully in Booking Information Page");
		if (bookingPage.verifyCollectionRegion()) {
			Pass_Message("Collection Address Telephone field splited in to"
					+ " two fields for Italy Account and collection region is :" + bookingPage.getCollectionRegion()
					+ "while converting quote to Booking");
		}
		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		Q_AdditionalInfo_Page();

	}

	public void quote_validateTelephone_field_on_deliveryAddress_for_ItalyAccount() {

		try {
			String acctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Quote.Key_Array[7]);
			String coll_Cust_Name = Database_Connection.retrieveTestData("SEN_COMP", "ACM", "KEY",
					CCD_Quote.Key_Array[8]);
			String coll_Add = Database_Connection.retrieveTestData("ADD_LINE1", "ACM", "KEY", CCD_Quote.Key_Array[8]);
			String coll_Town = Database_Connection.retrieveTestData("SEN_TOWN", "ACM", "KEY", CCD_Quote.Key_Array[8]);
			String coll_PostCode = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_Quote.Key_Array[8]);
			String coll_Country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_Quote.Key_Array[8]);

			QuotePage quotePage = new QuotePage(driver);
			BookingPage bookingPage = new BookingPage(driver);
			CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
			bookingSelectionOnHomepage(acctName);
			custaccpage.selectCustomerAccounts(acctName);
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewQuote();
			}
			quotePage.clickReceiverButton();
			if (bookingPage.successMsgonAddress()) {
				Pass_Message("Delivery Address is Updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			System.out.println(coll_Country);
			bookingPage.setCollectionCountry(coll_Country);
			bookingPage.setCollectionPostal(coll_PostCode);
			bookingPage.setCollectionTown(coll_Town);
			bookingPage.setCollectionCustomerName(coll_Cust_Name);
			bookingPage.setCollectionAddress(coll_Add);
			bookingPage.clickCollectionValidatebtn();
			uiTestHelper.propagateException();
			if (bookingPage.successMsgonAddress()) {
				Pass_Message("Collection Address is Updated Successfully");
			}
			uiTestHelper.scrolldown("300");
			bookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quote Page validation failed");
		}
	}

	public void verifyTelephoneNumber_on_deliveryAddress_for_Italy_QuoteReceiver() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		quote_validateTelephone_field_on_deliveryAddress_for_ItalyAccount();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		if (bookingPage.verifyDeliveryRegion()) {
			Pass_Message("Delivery Telephone Number Splited in to two fields for" + " Italy Account as : Region, "
					+ "Telephone while converting quote to Booking");
		}
		bookingPage.clickSameAsCallerInfo();
		Pass_Message("Details entered successfully in Booking Information Page");
		if (bookingPage.verifyCollectionRegion()) {
			Pass_Message("Collection Address Telephone field splited in to"
					+ " two fields for Italy Account and collection region is :" + bookingPage.getCollectionRegion()
					+ "while converting quote to Booking");
		}
		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		Q_AdditionalInfo_Page();
	}

	public void verifyEmail_SISPBooking_with_SpecialServices() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		enableSpecialServiceBooking_on_AdditionalInfoPage();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingSummary();
		if (bookingRecordPage.getEmailAddress().equalsIgnoreCase("nivetha.thirunavukarasu.osv@fedex.com")) {
			if (bookingRecordPage.getLanguage().equals("English")) {
				Pass_Message("SISP with Special Service booking email sent to Collection"
						+ " Country email with Language -" + bookingRecordPage.getLanguage());
			}
		}
		bookingRecordPage.sendEmail();
	}

	public void verifyEmail_SIRPBooking_with_SpecialServices() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_BookingPage_SIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		enableSpecialServiceBooking_on_AdditionalInfoPage();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingSummary();
		if (bookingRecordPage.getEmailAddress().equalsIgnoreCase("nivetha.thirunavukarasu.osv@fedex.com")) {
			if (bookingRecordPage.getLanguage().equals("English")) {
				Pass_Message("SISP with Special Service booking email sent to Collection"
						+ " Country email with Language -" + bookingRecordPage.getLanguage());
			}
		}
		bookingRecordPage.sendEmail();
	}

	public void verifyEmail_RIRPBooking_with_SpecialServices() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_BookingPage_RIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		enableSpecialServiceBooking_on_AdditionalInfoPage();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingSummary();
		if (bookingRecordPage.getEmailAddress().equalsIgnoreCase("nivetha.thirunavukarasu.osv@fedex.com")) {
			if (bookingRecordPage.getLanguage().equals("English")) {
				Pass_Message("SISP with Special Service booking email sent to Collection"
						+ " Country email with Language -" + bookingRecordPage.getLanguage());
			}
		}
		bookingRecordPage.sendEmail();
	}

	public void verifyPostcode_masc_for_booking() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "NNNN");
		Pass_Message("Delivery Postal masc Enabled for Booking");
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "NNNN-NNN");
		Pass_Message("Collection Postal masc Enabled for Booking");
	}

	public void verifyPostcode_masc_for_country_without_postal() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Country = "Afghanistan";
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		if (bookingpage.verifyCollectionRemoveAddressMark()) {
			bookingpage.clickRemoveCollectionCountry();
		}
		bookingpage.setCollectionCountry("Hong Kong");
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "");
		Pass_Message("Collection Postal masc left blank for Country dont have Masc");
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "");
		Pass_Message("Delivery Postal masc left blank for Country dont have Masc");

	}

	public void verifyPostcode_masc_for_quote() {

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuotePage quotePage = new QuotePage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		quotePage.clickSenderButton();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "NNNN");
		Pass_Message("Delivery Postal masc Enabled for Booking");
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "NNNN-NNN");
		Pass_Message("Collection Postal masc Enabled for Booking");
	}

	public void verifyPostcode_masc_for_clonebooking() {

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_SISP_Flow_without_Validation();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		bookingpage.verifyCloneBookingTitle();
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "NNNN-NNN");
		Pass_Message("Collection Postal masc Enabled when cloning Booking");
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "NNNN");
		Pass_Message("Delivery Postal masc Enabled when cloning Booking");
	}

	public void verifyPostcode_masc_for_editbooking() {

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_SISP_Flow_without_Validation();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		bookingpage.verifyEditBookingTitle();
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "NNNN");
		Pass_Message("Delivery Postal masc Enabled for Booking");
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "NNNN-NNN");
		Pass_Message("Collection Postal masc Enabled for Booking");
	}

	public void verifyBooking_collectionArea_and_selfbought_times_in_Quote() {

		QuoteAdditionalInfoPage quoteadditionalInfoPage = new QuoteAdditionalInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		uiTestHelper.scrolldown("700");
		additionalInfoPage.clickValidServices();
		additionalInfoPage.getValidServices();
		uiTestHelper.scrolldown("700");
		Assert.assertEquals(quoteadditionalInfoPage.verifyBookingBeforeTime(), true);
		Pass_Message("Booking Before Time: " + quoteadditionalInfoPage.getBookingBeforeTime());
		Assert.assertEquals(quoteadditionalInfoPage.verifyCollectionAreaTime(), true);
		Pass_Message("Collection Area Time: " + quoteadditionalInfoPage.getCollectionAreaTime());
		Assert.assertEquals(quoteadditionalInfoPage.verifySelfBoughtTime(), true);
		Pass_Message("Self Bought Time: " + quoteadditionalInfoPage.getSelfBoughtTime());
		quoteadditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteadditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteadditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);
	}

	public void contactCharcters_limit_Verification() {
		BookingPage bookingpage = new BookingPage(driver);
		String contactName = "ContactName Charcter Verification";
		String CollectionContactName = "Collection Contact Name Verification";
		String DeliveryContactName = "Delivery Contact Name Verification";
		uiTestHelper.scrollUp("-500");
		bookingpage.setCallerName(contactName);
		Assert.assertEquals(contactName.substring(0, 30), bookingpage.getCallerName());
		Pass_Message("Contact Name Charcter Length limit up to 30 Charcter");
		uiTestHelper.scrolldown("700");
		bookingpage.setContactName(CollectionContactName);
		bookingpage.setInputSACIDeliveryContactName(DeliveryContactName);
		Assert.assertEquals(CollectionContactName.substring(0, 30), bookingpage.getContactName());
		Pass_Message("Collection Contact Name Charcter Length limit up to 30 Charcter");
		Assert.assertEquals(DeliveryContactName.substring(0, 30), bookingpage.getInputSACIDeliveryContactName());
		Pass_Message("Delivery Contact Name Charcter Length limit up to 30 Charcter");
		bookingpage.setContactPhone("8940732594");
		bookingpage.setContactEmail("sayali.patil.osv@fedex.com");
	}

	public void verifyContactCharacters_for_booking() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		if (bookingpage.isNextButtonEnabled()) {
			contactCharcters_limit_Verification();
		}
		bookingpage.clickBookingnextbtn();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
	}

	public void verifyContactCharacters_while_editbooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			contactCharcters_limit_Verification();
		}
		bookingpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		uiTestHelper.scrolldown("1000");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		if (additionalInfoPage.verifyUpdatedBookingConfirmMsg()) {
			Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		} else {
			Fail_Message("Update Booking failed");
		}
	}

	public void verifyContactCharacters_while_clonebooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_verifyBookingClone();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingPageTitleByClone()) {
			contactCharcters_limit_Verification();
		}
		bookingpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();

		uiTestHelper.scrolldown("1000");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickConfirmBooking();
		String book = additionalInfoPage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (additionalInfoPage.verifyBookingConfirmMsg()) {
			Pass_Message("Booking is Cloned successfully and Booking reference number is: " + bookNum);
		} else {
			Fail_Message("Clone Booking failed");
		}
	}

	public void verifyContactCharacters_for_Quote() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);

		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Updated Successfully");
		}
		String contactName = "ContactName Charcter Verification";
		bookingpage.setCallerName(contactName);
		Assert.assertEquals(contactName.substring(0, 30), bookingpage.getCallerName());
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		bookingpage.setDeliveryPostal(Deliv_PostCode);
		bookingpage.setDeliveryTown(Deliv_Town);
		bookingpage.setDelCustomerName(Deliv_Cust_Name);
		bookingpage.setDeliveryAddress(Deliv_Add);
		bookingpage.deliveryValidatebtn();
		uiTestHelper.propagateException();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Updated Successfully");
			uiTestHelper.scrolldown("300");
			bookingpage.clickBookingnextbtn();

		}
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		String CollectionContactName = "Collection Contact Name Verification";
		String DeliveryContactName = "Delivery Contact Name Verification";
		Pass_Message("Contact Name Charcter Length limit up to 30 Charcter");
		uiTestHelper.scrolldown("700");
		bookingpage.setContactName(CollectionContactName);
		bookingpage.setInputSACIDeliveryContactName(DeliveryContactName);
		Assert.assertEquals(CollectionContactName.substring(0, 30), bookingpage.getContactName());
		Pass_Message("Collection Contact Name Charcter Length limit up to 30 Charcter");
		Assert.assertEquals(DeliveryContactName.substring(0, 30), bookingpage.getInputSACIDeliveryContactName());
		Pass_Message("Delivery Contact Name Charcter Length limit up to 30 Charcter");
		bookingpage.setContactPhone("8940732594");
		bookingpage.setContactEmail("sayali.patil.osv@fedex.com");
		bookingpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		Q_AdditionalInfo_Page();
	}

	public void verifyVATNumberOnBookingsPage_SISP() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		String collectionVATNumber = null, deliveryVATNumber = null;
		vatNumberValidationOnBookingPage_SISP();

		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);

		bookingRecordPage.clickEditBooking();

		if (bookingPage.verifyCollectionVATNumber()) {
			collectionVATNumber = enterCollectionVATNumber();
		}
		if (bookingPage.verifyDeliveryVATNumber()) {
			deliveryVATNumber = enterDeliveryVATNumber();
		}

		BK_getRecentBookingfrom_BookingList(bookNum);

		String currentCollectionVATNumber = bookingRecordPage.getCollectionVATNumber();
		Assert.assertEquals(collectionVATNumber, currentCollectionVATNumber);
		Pass_Message("Collection VAT Number is " + currentCollectionVATNumber);

		String currentDeliveryVATNumber = bookingRecordPage.getDeliveryVATNumber();
		Assert.assertEquals(deliveryVATNumber, currentDeliveryVATNumber);
		Pass_Message("Delivery VAT Number is " + currentDeliveryVATNumber);

	}

	public void vatNumberValidationOnBookingPage_SISP() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);

		Assert.assertEquals(bookingpage.verifyCollectionVATNumber(), true);
		Pass_Message("Collection VAT Number field exists");

		Assert.assertEquals(bookingpage.verifyDeliveryVATNumber(), true);
		Pass_Message("Delivery VAT Number field exists");

		bookingpage.clearCollectionVATNumber();
		bookingpage.clearDeliveryVATNumber();

		uiTestHelper.scrolldown("300");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}

	public String enterCollectionVATNumber() {
		BookingPage bookingpage = new BookingPage(driver);
		String collectionVATNumber = "45678";
		bookingpage.enterCollectionVATNumber(collectionVATNumber);
		System.out.println(bookingpage.getCollectionVATNumber());
		return collectionVATNumber;
	}

	public String enterDeliveryVATNumber() {
		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);

		String deliveryVATNumber = "12345";
		bookingpage.enterDeliveryVATNumber(deliveryVATNumber);
		System.out.println(bookingpage.getDeliveryVATNumber());

		bookingpage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();

		uiTestHelper.scrolldown("1000");
		additionalInfoPage.clickViewSummary();
		additionalInfoPage.clickUpdateBooking();
		String book = additionalInfoPage.getUpdatedBookingConfirmMsg();
		bookNum = book.replace("Booking is updated successfully. Booking Reference Number is: ", "");
		if (additionalInfoPage.verifyUpdatedBookingConfirmMsg()) {
			Pass_Message("Booking is updated successfully and Booking reference number is: " + bookNum);
		} else {
			Fail_Message("Update Booking failed");
		}
		return deliveryVATNumber;
	}

	public void verifyVATNumberOnBookingsPage_RIRP() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		vatNumberValidationOnBookingPage_RIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		// bookingRecordPage.clickBookingInfo();
		bookingRecordPage.clickEditBooking();
		if (bookingPage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			verifyVATNumberOnEditBooking();
		}
	}

	public void verifyVATNumberOnEditBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		String collectionVATNumber = enterCollectionVATNumber();
		String deliveryVATNumber = enterDeliveryVATNumber();

		BK_getRecentBookingfrom_BookingList(bookNum);

		String currentCollectionVATNumber = bookingRecordPage.getCollectionVATNumber();
		Assert.assertEquals(collectionVATNumber, currentCollectionVATNumber);
		Pass_Message("Collection VAT Number is " + currentCollectionVATNumber);

		String currentDeliveryVATNumber = bookingRecordPage.getDeliveryVATNumber();
		Assert.assertEquals(deliveryVATNumber, currentDeliveryVATNumber);
		Pass_Message("Delivery VAT Number is " + currentDeliveryVATNumber);
	}

	public void vatNumberValidationOnBookingPage_RIRP() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);

		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		bookingpage.callerInfo_RIRP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Validated");
		}
		setCollectionAddress("Denmark", "1552", "Copenhagen", "Megha Joshi", "89 Vester Voldgade");
		bookingpage.setInputSACIDeliveryEmail("nivetha.thirunavukarasu.osv@fedex.com");
		Assert.assertEquals(bookingpage.verifyCollectionVATNumber(), true);
		Pass_Message("Collection VAT Number field exists");
		Assert.assertEquals(bookingpage.verifyDeliveryVATNumber(), true);
		Pass_Message("Delivery VAT Number field exists");
		bookingpage.clearCollectionVATNumber();
		bookingpage.clearDeliveryVATNumber();
		uiTestHelper.scrolldown("300");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}

	public void verifyVATNumberOnBookingsPage_SIRP() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		vatNumberValidationOnBookingPage_SIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page_SIRP();
		BK_getRecentBookingfrom_BookingList(bookNum);

		bookingRecordPage.clickEditBooking();
		if (bookingPage.verifyBookingTitleonEdit()) {
			uiTestHelper.propagateException();
			verifyVATNumberOnEditBooking();
		}
	}

	public void vatNumberValidationOnBookingPage_SIRP() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);

		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);

		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SIRP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Validated");
		}
		receiverDetails_on_SIRP("000000651", "Portugal");
		bookingpage.confirmCustomerInstruction();
		Pass_Message("Details entered successfully in Booking Information Page");
		Assert.assertEquals(bookingpage.verifyCollectionVATNumber(), true);
		Pass_Message("Collection VAT Number field exists");

		Assert.assertEquals(bookingpage.verifyDeliveryVATNumber(), true);
		Pass_Message("Delivery VAT Number field exists");

		bookingpage.clearCollectionVATNumber();
		bookingpage.clearDeliveryVATNumber();

		uiTestHelper.scrolldown("300");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}

	public void verifyVATNumberOnBookingsPage_while_cloneBooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);

		Q_ConvertToBookingReceiver();
		vatNumberValidationOnBookingPage_RIRP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);

		bookingRecordPage.clickCloneBooking();
		uiTestHelper.propagateException();
		if (bookingPage.verifyBookingPageTitleByClone()) {
			Pass_Message("Navigated to Booking Page after Clicking Clone");
		}

		uiTestHelper.propagateException();
		verifyVATNumberOnEditBooking();
		uiTestHelper.propagateException();
		additionalInfoPage.clickConfirmBooking();
	}

	public void getTimingOnAdditionalInformationPage() {

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		verifyTimingsOn_AdditionalInfo_Page_ForBooking();
	}

	// Additional Info page
	public void verifyTimingsOn_AdditionalInfo_Page_ForBooking() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true);

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		uiTestHelper.propagateException();
		verifyTimes();
		uiTestHelper.scrolldown("700");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		uiTestHelper.scrolldown("700");
		addinfopage.setFromCollWindowTimeInput("17:30");

		addinfopage.setToCollWindowTimeInput("18:30");

		addinfopage.setFromUnavailTimeInput("18:15");

		addinfopage.setToUnavailTimeInput("18:30");

		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();

		boolean bookingSummery = addinfopage.getBookingSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Booking Failed");
		}
		System.out.println(bookNum);
		Assert.assertNotNull(bookNum);
	}

	public void verifyTimes() {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);

		// Get Collection Date and convert into yyyy-MM-dd format
		String collectionDate = addinfopage.getCollectionDate();
		Pass_Message_withoutScreenCapture("Collection Date is " + collectionDate);

		SimpleDateFormat collectionDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date convertedCollectionDate = null;
		try {
			convertedCollectionDate = collectionDateFormatter.parse(collectionDate);
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd");
		String stringCollectionDate = dateToString.format(convertedCollectionDate);
		Pass_Message_withoutScreenCapture("Collection Date in yyyy-MM-dd format is " + stringCollectionDate);

		if (addinfopage.verifyInCollectionAreaDateTime()) {
			Pass_Message("Date and Time for In Collection Area field exists");
		}

		// Get In Collection Area Date Time and convert into yyyy-MM-dd format
		String inCollectionAreaValue = addinfopage.getInCollectionAreaDateTimeValue();
		SimpleDateFormat collectionAreaToString = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date convertedInCollectionAreaValue = null;
		try {
			convertedInCollectionAreaValue = collectionAreaToString.parse(inCollectionAreaValue);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		String stringInCollectionAreaValue = dateToString.format(convertedInCollectionAreaValue);
		Pass_Message_withoutScreenCapture(
				"In Collection Area Date in yyyy-MM-dd format is " + stringInCollectionAreaValue);

		// Compare Collection date and In Collection Area Date
		Assert.assertEquals(stringCollectionDate, stringInCollectionAreaValue);
		Pass_Message_withoutScreenCapture("Collection Date and In Collection Area Date are matching ");

		if (addinfopage.verifyBookBeforeDateTime()) {
			Pass_Message("Date and Time for Book Before field exists");
		}

		// Get Booking Date Time and convert into yyyy-MM-dd format
		String bookBefore = addinfopage.getBookBeforeDateTimeValue();
		String bookBeforeDate = bookBefore.substring(20, 30);

		SimpleDateFormat bookBeforeToString = new SimpleDateFormat("dd/MM/yyyy");
		Date convertedBookBeforeTime = null;
		try {
			convertedBookBeforeTime = bookBeforeToString.parse(bookBeforeDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringBookBeforeTime = dateToString.format(convertedBookBeforeTime);
		Pass_Message_withoutScreenCapture("Book Before Date in yyyy-MM-dd format is " + stringBookBeforeTime);

		// Compare Collection date and Book Before Date
		Assert.assertEquals(stringCollectionDate, stringBookBeforeTime);
		Pass_Message_withoutScreenCapture("Collection Date and Book Before Date are matching ");

		if (addinfopage.verifySelfBroughtDateTime()) {
			Pass_Message("Date and Time for In Self Brought field exists");
		}

		// Get Self Brought Date Time and convert into yyyy-MM-dd format
		String selfBrought = addinfopage.getSelfBroughtDateTimeValue();
		SimpleDateFormat selfBroughtToString = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date convertedSelfBroughtTime = null;
		try {
			convertedSelfBroughtTime = selfBroughtToString.parse(selfBrought);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String stringSelfBroughtTime = dateToString.format(convertedSelfBroughtTime);
		Pass_Message_withoutScreenCapture("Self Brought Date in yyyy-MM-dd format is " + stringSelfBroughtTime);

		// Compare Collection date and Self Brought Date
		Assert.assertEquals(stringCollectionDate, stringSelfBroughtTime);
		Pass_Message_withoutScreenCapture("Collection Date and Self Brought Date are matching ");
	}

	public void getTimingOnAdditionalInformationPage_while_creatingQuote() {

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		verifyTimingsOn_AdditionalInfo_Page_ForQuotes();
	}

	// Additional Info page
	public void verifyTimingsOn_AdditionalInfo_Page_ForQuotes() {
		try {
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
			QuoteAdditionalInfoPage quoteadditionalInfoPage = new QuoteAdditionalInfoPage(driver);
			Assert.assertEquals(addinfopage.verifyTitle(), true);
			uiTestHelper.propagateException();
			addinfopage.clickValidServices();
			addinfopage.verifyPriceOnTable();
			addinfopage.getValidServices();
			Pass_Message_withoutScreenCapture("Valid Service is selected");

			uiTestHelper.propagateException();
			verifyTimes();
			quoteadditionalInfoPage.clickSaveQuoteBtn();
			uiTestHelper.propagateException();
			Assert.assertEquals(quoteadditionalInfoPage.verifyQuoteMsg(), true);
			Pass_Message("Quote is created successfully");
			String quote = quoteadditionalInfoPage.getQuoteMsg();
			quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
			Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:Quote failed");
		}
	}

	public void getTimingAfterConvertingQuoteToBooking() {
		BookingPage bookingPage = new BookingPage(driver);

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		verifyTimingsOn_AdditionalInfo_Page_ForQuotes();

		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("300");

		uiTestHelper.propagateException();
		bookingPage.callerInfo_RIRP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setCollectionCountry("Denmark");
		bookingpage.setCollectionPostal("1552");
		bookingpage.setCollectionTown("Copenhagen");
		bookingpage.setCollectionCustomerName("Megha Joshi");
		bookingpage.setCollectionAddress("89 Vester Voldgade");
		bookingpage.clickCollectionValidatebtn();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is validated");
			uiTestHelper.scrolldown("300");
			bookingpage.setInputSACIDeliveryEmail("nivetha.thirunavukarasu.osv@fedex.com");
			setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		}
		Pass_Message("Details entered successfully in Booking Information Page");

		uiTestHelper.propagateException();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		verifyTimingsOn_AdditionalInfo_Page_QuoteToBooking();
	}

	public void verifyTimingsOn_AdditionalInfo_Page_ForEditBooking() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true);

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();

		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}

		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");

		uiTestHelper.propagateException();
		verifyTimes();

		uiTestHelper.scrolldown("700");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		uiTestHelper.scrolldown("700");

		addinfopage.setFromCollWindowTimeInput("17:30");

		addinfopage.setToCollWindowTimeInput("18:30");

		addinfopage.setFromUnavailTimeInput("18:15");

		addinfopage.setToUnavailTimeInput("18:30");

		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();

		boolean bookingSummery = addinfopage.getBookingSummary();
		addinfopage.clickUpdateBooking();
		wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Booking Failed");
		}
		System.out.println(bookNum);
		Assert.assertNotNull(bookNum);
	}

	public void verifyTimingsOn_AdditionalInfo_Page_QuoteToBooking() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true);

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();

		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}

		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");

		uiTestHelper.propagateException();
		verifyTimes();

		uiTestHelper.scrolldown("700");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		uiTestHelper.scrolldown("700");
		addinfopage.setFromCollWindowTimeInput("17:30");
		addinfopage.setToCollWindowTimeInput("18:30");
		uiTestHelper.propagateException();
		addinfopage.setFromUnavailTimeInput("18:15");
		addinfopage.setToUnavailTimeInput("18:30");
		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();

		boolean bookingSummery = addinfopage.getBookingSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

			} else {
				Fail_Message("Booking failed");

			}
		} catch (Exception e) {
			// TODO: handle exception
			Fail_Message("Booking Failed");
			try {
				// addinfopage.clickCancel();
				if (addinfopage.verifyErrorMessage()) {
					driver.findElement(By.xpath("(//button[@title='Close'])[2]")).click();
					CCD_Connectivity conn = new CCD_Connectivity();
					conn.CloseTab();
					Assert.assertNotNull(bookNum);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println(bookNum);
	}

	public void verifyTelephoneNumberFieldForBooking() {
		BookingPage bookingpage = new BookingPage(driver);

		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);

		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		bookingpage.callerInfo_SISP();

		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}

		callerPhoneNumberValidations();
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		uiTestHelper.scrolldown("300");
		collectionPhoneNumberValidations();
		deliveryPhoneNumberValidations();
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();

	}

	public void callerPhoneNumberValidations() {
		BookingPage bookingPage = new BookingPage(driver);
		// Alphanumeric Phone number
		bookingPage.setCallerPhone("call@12345");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for Alphanumeric phone number on Caller Telephone is displayed");
		}

		// Continuous Phone number
		bookingPage.setCallerPhone("555222888");
		// Phone number with Spaces
		bookingPage.setCallerPhone("+55522 27722");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for phone number with space on Caller Telephone is displayed");
		}

		// Phone number starting with +
		bookingPage.setCallerPhone("+5552229900");

		// Phone number ending with +
		bookingPage.setCallerPhone("5552223311+");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for phone number ending with + on Caller Telephone is displayed");
		}
		bookingPage.setCallerPhone("5552228800");
	}

	public void collectionPhoneNumberValidations() {
		BookingPage bookingPage = new BookingPage(driver);
		// Alphanumeric Phone number
		bookingPage.setContactPhone("coll@12345");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for Alphanumeric phone number on Collection Telephone is displayed");
		}

		// Continuous Phone number
		bookingPage.setContactPhone("555222777");

		// Phone number with Spaces
		bookingPage.setContactPhone("+55522 28855");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for phone number with space on Collection Telephone is displayed");
		}

		// Phone number starting with +
		bookingPage.setContactPhone("+5552225500");

		// Phone number ending with +
		bookingPage.setContactPhone("5552228855+");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message(
					"Validation Error message for phone number ending with + on Collection Telephone is displayed");
		}

		bookingPage.setContactPhone("+5552225500");
	}

	public void deliveryPhoneNumberValidations() {
		BookingPage bookingPage = new BookingPage(driver);
		// Alphanumeric Phone number
		bookingPage.setInputSACIDeliveryTelephone("deli@12345");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for Alphanumeric phone number on Delivery Telephone is displayed");
		}

		// Continuous Phone number
		bookingPage.setInputSACIDeliveryTelephone("555222555");

		// Phone number with Spaces
		bookingPage.setInputSACIDeliveryTelephone("+55522 27766");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for phone number with space on Delivery Telephone is displayed");
		}

		// Phone number starting with +
		bookingPage.setInputSACIDeliveryTelephone("+5552224400");

		// Phone number ending with +
		bookingPage.setInputSACIDeliveryTelephone("5552229922+");
		if (bookingPage.verifyTelephoneHelpMessage()) {
			Assert.assertEquals(bookingPage.verifyTelephoneHelpMessage(), true);
			Pass_Message("Validation Error message for Telephone number ending with is displayed");
		}

		bookingPage.setInputSACIDeliveryTelephone("+5552226600");
	}

	public void verifyTelephoneNumberFieldForQuote() {
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		HomePage homePage = new HomePage(driver);
		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		QuoteAdditionalInfoPage quoteadditionalInfoPage = new QuoteAdditionalInfoPage(driver);

		bookingSelectionOnHomepage(AcctName);
		homePage.selectCustomerAccounts(AcctName);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Updated Successfully");
		}

		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		bookingpage.setDeliveryPostal(Deliv_PostCode);
		bookingpage.setDeliveryTown(Deliv_Town);
		bookingpage.setDelCustomerName(Deliv_Cust_Name);
		bookingpage.setDeliveryAddress(Deliv_Add);
		bookingpage.deliveryValidatebtn();
		uiTestHelper.propagateException();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Updated Successfully");
			uiTestHelper.scrolldown("300");
		}

		callerPhoneNumberValidations();
		bookingpage.clickBookingnextbtn();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");

		uiTestHelper.propagateException();
		quoteadditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteadditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteadditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);
	}

	public void verifyTelephoneNumberFieldForEditBooking() {

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);

		BK_SISP_Flow();
		BK_getRecentBookingfrom_BookingList(bookNum);

		bookingRecordPage.clickEditBooking();
		if (bookingPage.verifyBookingTitleonEdit()) {
			Pass_Message("Booking  - " + bookNum + "Edited Successfully");
		}

		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		callerPhoneNumberValidations();
		uiTestHelper.scrolldown("300");
		collectionPhoneNumberValidations();
		deliveryPhoneNumberValidations();

		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		BK_AdditionalInfoPage_EditBooking();
	}

	public void verifyTelephoneNumberFieldForCloneBooking() {

		BookingPage bookingpage = new BookingPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingPage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);

		BK_SISP_Flow();
		BK_getRecentBookingfrom_BookingList(bookNum);

		bookingRecordPage.clickCloneBooking();
		if (bookingPage.verifyBookingPageTitleByClone()) {
			Pass_Message("Navigated to Booking Page after Clicking Clone");
		}

		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		uiTestHelper.propagateException();
		callerPhoneNumberValidations();
		uiTestHelper.scrolldown("300");
		collectionPhoneNumberValidations();
		deliveryPhoneNumberValidations();

		bookingPage.clickBookingnextbtn();
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		BK_AdditionalInfo_Page();
	}

	public void verifyTelephoneNumberField_while_convertingQuoteToBooking() {
		BookingPage bookingPage = new BookingPage(driver);

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		QuoteAdditionalInfoPage quoteadditionalInfoPage = new QuoteAdditionalInfoPage(driver);

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");

		uiTestHelper.propagateException();
		quoteadditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteadditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteadditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);

		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("300");

		uiTestHelper.propagateException();
		bookingPage.callerInfo_RIRP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setCollectionCountry("Denmark");
		bookingpage.setCollectionPostal("1552");
		bookingpage.setCollectionTown("Copenhagen");
		bookingpage.setCollectionCustomerName("Megha Joshi");
		bookingpage.setCollectionAddress("89 Vester Voldgade");
		bookingpage.clickCollectionValidatebtn();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is validated");
			uiTestHelper.scrolldown("300");
			bookingpage.setInputSACIDeliveryEmail("nivetha.thirunavukarasu.osv@fedex.com");
		}
		Pass_Message("Details entered successfully in Booking Information Page");

		uiTestHelper.propagateException();
		callerPhoneNumberValidations();
		uiTestHelper.scrolldown("300");
		collectionPhoneNumberValidations();
		deliveryPhoneNumberValidations();
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");

		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();
		BK_AdditionalInfo_Page();
	}

	public void verifyAwkwardFreightPallet() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		uiTestHelper.propagateException();
		BK_ConInfo_Pallet();

		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		uiTestHelper.scrolldown("700");
		Assert.assertTrue(addinfopage.verifyAWKFriegtAlert());
		{
			Pass_Message("Awkward Freight alert is displayed");
		}
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");
		}

		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		Assert.assertTrue(bookingRecordPage.verifyExceptionDescription());
		{
			Pass_Message("Booking is created with Awkward Freight Exception");
		}
	}

	// con info paGE
	public void BK_ConInfo_Pallet() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true);
		try {
			HashMap<String, String> hashmap = new HashMap<String, String>();

			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "240");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "120");
			hashmap.put("(//label[text()='Height ']/following::input[1])[1]", "180");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "500");

			uiTestHelper.propagateException();
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
			uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='pallet']"));
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Quantity , Weight and Dimensions are updated on the Consignment Information Page for Pallet");
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");
		}
	}

	public void BK_ConInfo_Envelope() {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(driver);
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true);
		try {
			HashMap<String, String> hashmap = new HashMap<String, String>();

			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "550");

			uiTestHelper.propagateException();
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
			uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='envelope']"));
			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("Quantity and Weight are updated on the Consignment Information Page for Envelope");
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");
		}
	}

	public void verifyAwkwardFreightEnvelope() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		uiTestHelper.propagateException();
		BK_ConInfo_Envelope();
		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		uiTestHelper.scrolldown("700");
		Assert.assertTrue(addinfopage.verifyAWKFriegtAlert());
		{
			Pass_Message("Awkward Freight alert is displayed");
		}
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");
		}

		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		Assert.assertTrue(bookingRecordPage.verifyExceptionDescription());
		{
			Pass_Message("Booking is created with Awkward Freight Exception");
		}
	}

	public void verifyAdditionalOptions_onAdditionalInformation() {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		String expectedTotalWeight = BK_ConInform_Page();
		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();

		String custDiscountValue = addinfopage.getCustomerDiscount();
		Assert.assertEquals(custDiscountValue, "0%");
		{
			Pass_Message("Customer Discount is blank, 0% is displayed");
		}

		String actualTotalWeight = addinfopage.getTotalWeight();
		Assert.assertTrue(actualTotalWeight.contains(expectedTotalWeight));
		{
			Pass_Message("Total Weight is addition of Individual Consignment weight ");
		}

		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);
		} else {
			Fail_Message("Booking failed");
		}

		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickMoretab();
		bookingRecordPage.clickAdditionalInfo();
		if (addinfopage.verifyExpectedDueDate()) {
			Pass_Message("Expected Due Date field is displayed");
		}
	}

	public void verifyDefaultPreferredCollectionTime_while_convertQuoteToBooking() {
		BookingPage bookingPage = new BookingPage(driver);

		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		QuoteAdditionalInfoPage quoteadditionalInfoPage = new QuoteAdditionalInfoPage(driver);

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();

		uiTestHelper.propagateException();
		addinfopage.clickValidServices();
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		uiTestHelper.propagateException();
		quoteadditionalInfoPage.clickSaveQuoteBtn();
		uiTestHelper.propagateException();
		Assert.assertEquals(quoteadditionalInfoPage.verifyQuoteMsg(), true);
		Pass_Message("Quote is created successfully");
		String quote = quoteadditionalInfoPage.getQuoteMsg();
		quoteNum = quote.replace("Quote is created successfully. Quote Reference Number is: ", "");
		Pass_Message_withoutScreenCapture("Quote Num is " + quoteNum);

		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("300");
		uiTestHelper.propagateException();
		bookingPage.callerInfo_RIRP();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setCollectionCountry("Denmark");
		bookingpage.setCollectionPostal("1552");
		bookingpage.setCollectionTown("Copenhagen");
		bookingpage.setCollectionCustomerName("Megha Joshi");
		bookingpage.setCollectionAddress("89 Vester Voldgade");
		bookingpage.clickCollectionValidatebtn();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is validated");
			uiTestHelper.scrolldown("300");
			bookingpage.setInputSACIDeliveryEmail("nivetha.thirunavukarasu.osv@fedex.com");
		}
		Pass_Message("Details entered successfully in Booking Information Page");

		uiTestHelper.propagateException();
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		goodsInfoPage.clickGoodsInfoNextBtn();
		consignmentInfoPage.clickConsignmentInfoNextBtn();

		uiTestHelper.propagateException();
		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		uiTestHelper.scrolldown("700");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");

		uiTestHelper.scrolldown("700");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = new Date();
		if (dateFormat.format(date).contains(addinfopage.getFromPreferredCollectionTime())) {
			Pass_Message("From Preferred Collection Time is defaulted to Current time");
		} else {
			Pass_Message("From Preferred Collection Time is different from Current time");
		}
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));
			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
			if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
				Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);
			} else {
				Fail_Message("Booking failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:Booking failed");
		}
	}

	public void clearAddressBooking_while_booking() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", CCD_CI.Key_Array[6]);
		// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		verifyClearAddressFunction();
	}

	public void verifyClearAddressFunction() {
		BookingPage bookingpage = new BookingPage(driver);
		if (bookingpage.verifyCollectionRemoveAddressMark()) {
			bookingpage.clickRemoveCollectionCountry();
			Assert.assertEquals(bookingpage.getCollectionPostCode(), "");
			Pass_Message("Collection Address has been cleared");
		}
		if (bookingpage.verifyDeliveryRemoveAddressMark()) {
			bookingpage.clickRemoveDelCountry();
			Assert.assertEquals(bookingpage.getDeliveryPostCode(), "");
			Pass_Message("Delivery Address has been cleared");
		}
	}

	public void clearAddressBooking_while_editbooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingTitleonEdit()) {
			verifyClearAddressFunction();
		}

	}

	public void clearAddressBooking_while_clonebooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		BK_verifyBookingClone();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		uiTestHelper.propagateException();
		if (bookingpage.verifyBookingPageTitleByClone()) {
			verifyClearAddressFunction();
		}
	}

	public void clearAddressBooking_for_Quote() {

		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);

		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Updated Successfully");
		}
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		bookingpage.setDeliveryPostal(Deliv_PostCode);
		bookingpage.setDeliveryTown(Deliv_Town);
		bookingpage.setDelCustomerName(Deliv_Cust_Name);
		bookingpage.setDeliveryAddress(Deliv_Add);
		bookingpage.deliveryValidatebtn();
		uiTestHelper.propagateException();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Updated Successfully");
		}
		verifyClearAddressFunction();
	}

	public void selectCollectionAddressBook(String postcode) {
		BookingPage bookingpage = new BookingPage(driver);
		if (bookingpage.verifyCollectionSaveAddress()) {
			bookingpage.clickCollectionSaveAddress();
			Pass_Message("Collection Address has been Saved");
			if (bookingpage.verifyCollectionSelectAddress()) {
				bookingpage.clickCollectionSelectAddress();
				Pass_Message("Popup opened to Select Collection Address");
				bookingpage.collectionAddressBookSelection(postcode);
				Pass_Message_withoutScreenCapture("Saved Collection Address selected");
			}
		}

	}

	public void selectDeliveryAddressBook(String Deliv_PostCode) {
		BookingPage bookingpage = new BookingPage(driver);
		if (bookingpage.verifyDeliverySaveAddress()) {
			bookingpage.clickDeliverySaveAddress();
			Pass_Message("Delivery Address has been Saved");
			if (bookingpage.verifyDeliverySelectAddress()) {
				bookingpage.clickDeliverySelectAddress();
				Pass_Message("Popup opened to Select Delivery Address");
				bookingpage.deliveryAddressBookSelection(Deliv_PostCode);
				Pass_Message_withoutScreenCapture("Saved Delivery Address selected");
			}
		}
	}

	public void verifySaveandSelectAddressButton_on_Quote() {

		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[5]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[5]);

		QuotePage quotePage = new QuotePage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewQuote();
		}
		quotePage.clickSenderButton();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Collection Address is Updated Successfully");
		}
		uiTestHelper.scrolldown("300");
		selectCollectionAddressBook("5771");
		bookingpage.setDeliveryCountry(Deliv_Country);
		bookingpage.setDeliveryPostal(Deliv_PostCode);
		bookingpage.setDeliveryTown(Deliv_Town);
		bookingpage.setDelCustomerName(Deliv_Cust_Name);
		bookingpage.setDeliveryAddress(Deliv_Add);
		bookingpage.deliveryValidatebtn();
		uiTestHelper.propagateException();
		if (bookingpage.successMsgonAddress()) {
			Pass_Message("Delivery Address is Updated Successfully");
		}
		selectDeliveryAddressBook(Deliv_PostCode);
		uiTestHelper.scrolldown("300");
		bookingpage.clickBookingnextbtn();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Q_getRecentQuotefrom_BookingList(quoteNum);
		quoteDetailPage.clickConvertToBookingBtn();
		uiTestHelper.propagateException();
	}

	public void verifySaveandSelectAddressButton_on_Booking() {
		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", CCD_CI.Key_Array[6]);
		// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}
		selectCollectionAddressBook("1000-163");
		uiTestHelper.scrolldown("300");
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		selectDeliveryAddressBook(Deliv_PostCode);
		uiTestHelper.scrolldown("300");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");

	}

	public void verifySaveandSelectAddressButton_on_editbooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		verifySaveandSelectAddressButton_on_Booking();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		if (bookingpage.verifyBookingTitleonEdit()) {
			selectCollectionAddressBook("1000-163");
			selectDeliveryAddressBook("5771");
		}

	}

	public void verifySaveandSelectAddressButton_on_clonebooking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		BK_verifyBookingClone();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickCloneBooking();
		if (bookingpage.verifyBookingPageTitleByClone()) {
			selectCollectionAddressBook("1000-163");
			selectDeliveryAddressBook("5771");
		}
	}

	public void GoodsInfoPage_without_Amount_for_EUCountries() {
		try {
			GoodsInfoPage goodspage = new GoodsInfoPage(driver);
			setGoodsInformation("Document", "Test Desc", "", "12345");
			Pass_Message("Details entered successfully in Goods Information Page");
			goodspage.clickGoodsInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Consignment Information page failed");

		}
	}

	public void bookingPage_for_sameEUCountry(String bookingorQuote) {

		CustomerAccountPage custaccpage = new CustomerAccountPage(driver);
		BookingPage bookingpage = new BookingPage(driver);
		QuotePage quotepage = new QuotePage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", CCD_CI.Key_Array[6]);
		// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
		bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		uiTestHelper.propagateException();
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			if (bookingorQuote.equals("Booking")) {
				custaccpage.clickNewBooking();
				bookingpage.callerInfo_SISP();
			} else {
				custaccpage.clickNewQuote();
				quotepage.clickSenderButton();
			}
		}
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true);
			Pass_Message("Collection Address is Validated");
		}
		uiTestHelper.scrolldown("300");
		setDeliveryAddress("Denmark", "1552", "Copenhagen", "Megha Joshi", "1552");
	}

	public void verifyAmountField_for_different_EU_while_booking() {

		BK_BookingPage_SISP();
		GoodsInfoPage_without_Amount_for_EUCountries();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		Assert.assertNotNull(bookNum);
		Pass_Message("With out Goods Value able to create booking for Different EU Coutries");
	}

	public void verifyAmountField_for_same_EU_while_booking() {

		bookingPage_for_sameEUCountry("Booking");
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
		GoodsInfoPage_without_Amount_for_EUCountries();
		BK_ConInfo_Page();
		BK_AdditionalInfo_Page();
		Assert.assertNotNull(bookNum);
		Pass_Message("With out Goods Value able to create booking for Same EU Coutries");
	}

	public void verifyAmountField_for_different_EU_while_quote() {

		Q_QuoteInfoPage();
		GoodsInfoPage_without_Amount_for_EUCountries();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Assert.assertNotNull(quoteNum);
		Pass_Message("With out Goods Value able to create quote for Different EU Coutries");
	}

	public void verifyAmountField_for_same_EU_while_quote() {

		bookingPage_for_sameEUCountry("Quote");
		GoodsInfoPage_without_Amount_for_EUCountries();
		BK_ConInfo_Page();
		Q_AdditionalInfoPage();
		Assert.assertNotNull(quoteNum);
		Pass_Message("With out Goods Value able to create quote for Same EU Coutries");
	}

	public void validationOnDims_in_consignmentinfopage() {
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("(//input[@name='quantity'])[1]", "3");
		hashmap.put("(//label[text()='Length']/following::input[1])[1]", "200");
		hashmap.put("(//label[text()='Width']/following::input[1])[1]", "200");
		hashmap.put("(//label[text()='Height']/following::input[1])[1]", "200");
		hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "120");
		String dimsDefaultMeasurement = consignmentInfoPage.getDimension();
		Assert.assertEquals(dimsDefaultMeasurement, "CM");
		if (dimsDefaultMeasurement.equals("CM")) {
			Pass_Message("Default Dimension Measurement is displayed as " + dimsDefaultMeasurement);
		}
		consignmentInfoPage.setDimension("Inches", 1);
		driver.findElement(By.xpath("(//button[@name='[object Object]'])[2]")).click();
		driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='box']")).click();
		for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
			driver.findElement(By.xpath(entry.getKey())).clear();
			driver.findElement(By.xpath(entry.getKey())).sendKeys(entry.getValue());
		}
		consignmentInfoPage.clickConsignmentInfoNextBtn();
	}

	public void validateDimensionMeasurement_for_booking() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		validationOnDims_in_consignmentinfopage();
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(driver);
		Assert.assertEquals(addinfopage.verifyTitle(), true);
		uiTestHelper.scrolldown("300");
		addinfopage.clickValidServices();
		if (addinfopage.verifyGetPrice()) {
			addinfopage.clickGetPrice();
		}
		addinfopage.verifyPriceOnTable();
		addinfopage.getValidServices();
		Pass_Message_withoutScreenCapture("Valid Service is selected");
		Assert.assertEquals(addinfopage.verifyAWKFriegtAlert(), true);
		if (addinfopage.verifyAWKFriegtAlert()) {
			Pass_Message(
					"AwkFreigt alert displayed when Dimension selected as Inches:  " + addinfopage.getAWKFriegtAlert());
		}
		uiTestHelper.scrolldown("700");
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='toastMessage forceActionsText']")));

		String book = addinfopage.getBookingConfirmMsg();
		bookNum = book.replace("Booking is created successfully. Booking Reference Number is: ", "");
		if (driver.findElement(By.xpath("//span[@class='toastMessage forceActionsText']")).isDisplayed()) {
			Pass_Message("Booking is completed successfully and Booking reference number is: " + bookNum);

		} else {
			Fail_Message("Booking failed");

		}
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingExceptionTab();
		Assert.assertEquals(bookingRecordPage.getExceptionDescription(), "Awkward Freight");
		Pass_Message(bookingRecordPage.getExceptionDescription()
				+ " Exception created in Exception History when Dimension mentioned with Inches");
		bookingRecordPage.clickMoretab();
		bookingRecordPage.clickConsignmentInfo();
		bookingRecordPage.clickLineItemLink();
		Assert.assertEquals(bookingRecordPage.getMeasurement(), "Inches");
		Pass_Message("Dims Measurement updated correctly on the Booking Record Page with Measurement-->"
				+ bookingRecordPage.getMeasurement());

	}

	public void BK_verifyHistoryPage_SISPFlow() {

		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		BK_SISP_Flow_without_Validation();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickBookingHistory();
	}

	public void cutoffValidation_on_bookinginfoPage() {

		BookingPage bookingpage = new BookingPage(driver);
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		// String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM",
		// "KEY", CCD_CI.Key_Array[6]);
		// AcctName="TEST ABC CNAME6 UNIQUE";//Non approved DG account
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		bookingSelectionOnHomepage(AcctName);
		newBookingonCustomerAccPage(AcctName);
		bookingpage.setCallerEmail("nivetha.thirunavukarasu.osv@fedex.com");
		bookingpage.callerInfo_SISP();
		// bookingpage.confirmCustomerInstruction();
		if (bookingpage.successMsgonAddress()) {
			Assert.assertEquals(bookingpage.successMsgonAddress(), true, "Collection address not Validated");
			Pass_Message("Collection Address is Validated");
		}
		// uiTestHelper.scrolldown("300");
		Assert.assertEquals(bookingpage.verifyCutoffButton(), true, "Cutoff button not displayed");
		if (bookingpage.verifyCutoffButton()) {
			bookingpage.clickCutoff();
		}
		setDeliveryAddress(Deliv_Country, Deliv_PostCode, Deliv_Town, Deliv_Cust_Name, Deliv_Add);
		setCollectionContactDetails("Nivetha", "894678362", "nivetha.thirunavukarasu.osv@fedex.com");
	}

	public void expressAndSpecialServiceButtonValidation_for_booking() {

		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		Assert.assertEquals(additionalInfoPage.verifyTitle(), true);
		uiTestHelper.scrolldown("300");
		validationOnExpressService();
		validationOnSpecialService();

	}

	public void validationOnExpressService() {
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		Assert.assertEquals(additionalInfoPage.isExpressButtonDisplayed(), true);
		if (additionalInfoPage.isExpressButtonDisplayed()) {
			additionalInfoPage.clickValidServices();
			Pass_Message("Express button is Displayed to get Express Services");
			additionalInfoPage.isLOBofExpressService();
			additionalInfoPage.getAllAvaliableServices("Express");
		}
		Assert.assertEquals(additionalInfoPage.isSpecialButtonEnabled(), true);
		Pass_Message("Special button is enabled when we have selected the Express Services");
	}

	public void validationOnSpecialService() {
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		Assert.assertEquals(additionalInfoPage.isSpecialButtonDisplayed(), true);
		if (additionalInfoPage.isSpecialButtonDisplayed()) {
			additionalInfoPage.clickSpecialServices();
			Pass_Message("Special button is Displayed to get Special Services");
			additionalInfoPage.isLOBofSpecialService();
			additionalInfoPage.getAllAvaliableServices("Special");
		}
		Assert.assertEquals(additionalInfoPage.isExpressButtonEnabled(), true);
		Pass_Message("Express button is enabled when we have selected the Special Services");
	}

	public void expressAndSpecialServiceButtonValidation_for_editbooking() {

		BookingPage bookingpage = new BookingPage(driver);
		GoodsInfoPage goodsInfoPage = new GoodsInfoPage(driver);
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(driver);
		BK_SISP_Flow_without_Validation();
		BK_getRecentBookingfrom_BookingList(bookNum);
		bookingRecordPage.clickEditBooking();
		if (bookingpage.verifyBookingTitleonEdit()) {
			bookingpage.clickBookingnextbtn();
			goodsInfoPage.clickGoodsInfoNextBtn();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
		}
		Assert.assertEquals(additionalInfoPage.verifyTitle(), true);
		uiTestHelper.scrolldown("300");
		validationOnExpressService();
		validationOnSpecialService();
	}

	public void verifyconsignmentnoteUNNumber_with_DGBooking() {

		BK_BookingPage_SISP();
		BK_GoodsInfoPage_DG();
		BK_ConInfo_Page_with_consignmentNote();
		BK_AdditionalInfo_Page();
	}

	public void serviceSelection_onQuoteSummary() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		String email_address = "nivetha.thirunavukarasu.osv@fedex.com";
		Q_QuoteFlow();
		quoteDetailPage.clickQuoteSummaryButton();
		bookingRecordPage.clickEmailField();
		bookingRecordPage.clickOther();
		bookingRecordPage.setEmailAddress(email_address);
		quoteDetailPage.getAllServicesForQuote();
		quoteDetailPage.getSelectedService("Default Service while Quote Creation on Quote Summary : ");
		quoteDetailPage.selectServiceonQuoteSummary("Economy Express");
		quoteDetailPage.getSelectedService("Service List After Service Selection on Quote Summary : ");
		quoteDetailPage.clickSendEmail();
		Assert.assertEquals(quoteDetailPage.verifyEmailsuccessMsg(), true);
		Pass_Message("Quote with Selected services sent through email - " + email_address);
	}

	public void validation_ofDPTReportLink_in_CCD() {

		HomePage homepage = new HomePage(driver);
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		homepage.caseSearch("573515891");

	}

	public void validateErrorMessagewhileserviceSelection_onQuoteSummary() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		BookingRecordPage bookingRecordPage = new BookingRecordPage(driver);
		Q_QuoteFlow();
		quoteDetailPage.clickQuoteSummaryButton();
		quoteDetailPage.getAllServicesForQuote();
		quoteDetailPage.getSelectedService("Default Service while Quote Creation on Quote Summary : ");
		quoteDetailPage.deselectServiceonQuoteSummary("Express");
		quoteDetailPage.clickSendEmail();
		// Assert.assertEquals(quoteDetailPage.verifyEmailsuccessMsg(),true);
		Pass_Message("");
	}

	public void validateQuoteExpiration_before_bookingConversion() {

		QuoteDetailPage quoteDetailPage = new QuoteDetailPage(driver);
		Q_QuoteFlow();
		Assert.assertEquals(quoteDetailPage.isQuoteExpiryDateEnabled(), true);
		if (quoteDetailPage.isQuoteExpiryDateEnabled()) {
			Pass_Message_with_screenCapture(
					"Quote Expiry Date is Dispalyed and date is : " + quoteDetailPage.getQuoteExpiryDate());
		}
	}

	public void verifySpSThirdPartyBooking() {

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page();
		enableSpecialServiceBooking_for_SpSThirdParty_AdditionalInfoPage();
	}

	public void verifyWeightAndDims_maintained_consignmentInfoPage_for_Booking() {

		BK_BookingPage_SISP();
		BK_GoodsInfoPage();
		BK_ConInfo_Page_with_weightAndDim("1", "200", "200", "200", "70");

	}

	public void verifyWeightAndDims_maintained_consignmentInfoPage_for_Quote() {

		Q_QuoteInfoPage();
		BK_GoodsInfoPage();
		BK_ConInfo_Page_with_weightAndDim("1", "200", "200", "200", "70");

	}

	public void verifyCaseClearance_for_allCaseTypes() {
		CCD_Connectivity ccd_Connectivity = new CCD_Connectivity();
		HomePage homepage = new HomePage(driver);
		homepage.clickDropDownNavigationMenu();
		homepage.clickCases();
		createCase("Re-Active Case", "CPW");
		ccd_Connectivity.CloseTab();
		createCase("Pro-Active Case", "CAG");
		ccd_Connectivity.CloseTab();
		createCase("Unallocated Case", "CPW");
	}

	public void createCase(String caseType, String caseCause) {
		CreateCasePage createCasePage = new CreateCasePage(driver);
		CaseDetailsPage caseDetailsPage = new CaseDetailsPage(driver);
		createCasePage.newCaseBtn();
		createCasePage.selectCaseType(caseType);
		createCasePage.nextButton();
		createCasePage.enterDueDate();
		createCasePage.enterInvoice("123456789");
		createCasePage.enterTimeRemaining();
		if (caseType.equalsIgnoreCase("Re-Active Case")) {
			createCasePage.enterDueDatewithTimeRemaining();
		}
		createCasePage.selectCaseGroupDropDown();
		createCasePage.selectCaseGroup("Shipment");
		createCasePage.selectCaseReasonDropDown();
		createCasePage.selectCaseReason("Clearance");
		createCasePage.selectCaseCauseDropDown();
		createCasePage.selectCaseCause(caseCause);
		createCasePage.saveCase();
		Assert.assertEquals(caseDetailsPage.verifyCaseCreated(), true);
		String caseNumber = caseDetailsPage.getCaseNo();
		Pass_Message(caseType + " has been created with the case number : " + caseNumber);
		caseDetailsPage.clickCaseDetails();
		caseDetailsPage.clickCaseInformation();
		uiTestHelper.scrolldown("500");
		Assert.assertNotNull(caseDetailsPage.getcaseType());
		// Pass_Message_with_screenCapture(caseType +" Information is below: ");
	}

	
}
