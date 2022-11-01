package com.tnt.ccd;
import java.lang.reflect.Method;
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
import org.testng.annotations.Parameters;

import com.tnt.ccdobjects.BookingPage;
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
public class CCD_WeightAndDimsValidation extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();
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
		if ((!Keys.isEmpty()) || (!(Keys==null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeClass(alwaysRun = true)
	@Parameters({"env"})
	public void login(String env) throws Exception {	
		ciBooking.ci_login(env);		
		
	}
	@BeforeMethod
	public void Before_method(Method method) throws Exception {
		Test_Initializer.Before_Method(method);
	}
	@DataProvider
	public Object[][] getLoginData() {
		Object data[][] = TestUtil.getTestData("SISP_DimsData");
		return data;
	}
	@DataProvider
	public Object[][] SIRP_Data() {
		Object data[][] = TestUtil.getTestData("SIRP_DimsData");
		return data;
	}
	@DataProvider
	public Object[][] RIRP_Data() {
		Object data[][] = TestUtil.getTestData("RIRP_DimsData"); 
		return data;
	}
	@org.testng.annotations.Test(priority = 1, dataProvider = "getLoginData", enabled = false)
	public void WeightAndDimsValidations_SISP(String TC, String Country, String username, String password,
			String acctname, String delcou, String del_pc, String del_town, String del_custname, String del_add,
			String e_len, String e_wid, String e_ht, String e_wt, String len, String wid, String ht, String wt,
			String pe_len, String pe_wid, String pe_ht, String pe_wt, String p_len, String p_wid, String p_ht,
			String p_wt, String envex_wt, String env_wt) throws Exception {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage BookingPage = new BookingPage(getDriver());
		try {
			System.out.println(TC);
			ciBooking.bookingSelectionOnHomepage(acctname);
			uiTestHelper.propagateException();
			custaccpage.selectCustomerAccounts(acctname);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewBooking();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ciBooking Radion button is not selected");
		}
		try {
			BookingPage.callerInfo_SISP();
			BookingPage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			uiTestHelper.propagateException();
			BookingPage.setDeliveryCountry(delcou);
			BookingPage.setDeliveryPostal(del_pc);
			uiTestHelper.propagateException();
			BookingPage.setDeliveryTown(del_town);
			BookingPage.setDelCustomerName(del_custname);
			BookingPage.setDeliveryAddress(del_add);
			BookingPage.deliveryValidatebtn();
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			BookingPage.setContactName("Arjun Rampal");
			BookingPage.setContactPhone("8940732594");
			BookingPage.setContactEmail("Arjun_Rampal@gmail.com");
			Pass_Message("Details entered successfully in ciBooking Information Page");
			BookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Page is not navigated to Good Information Page successfully");
		}
		// Goods info page
		try {
			ciBooking.BK_GoodsInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details have not been entered successfully on Goods info page");
		}
		// Con Info Page
		// Box
		consignmentWeightandDimValidation(TC, Country, e_len, e_wid, e_ht, e_wt, "box");
		// Pallet
		consignmentWeightandDimValidation(TC, Country, pe_len, pe_wid, pe_ht, pe_wt, "pallet");
		// Envelope
		consignmentWeightandDimValidationEnvelope(TC, Country, envex_wt, "envelope");
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}
	// SIRP Flow
	@org.testng.annotations.Test(priority = 2, dataProvider = "SIRP_Data", enabled = false)
	public void WeightAndDimsValidations_SIRP(String TC, String Country, String billAcctNum, String cou1,
			String acctname, String delcou, String del_pc, String del_town, String del_custname, String del_add,
			String e_len, String e_wid, String e_ht, String e_wt, String len, String wid, String ht, String wt,
			String pe_len, String pe_wid, String pe_ht, String pe_wt, String p_len, String p_wid, String p_ht,
			String p_wt, String envex_wt, String env_wt) throws Exception {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage BookingPage = new BookingPage(getDriver());
		try {
			System.out.println(TC);
			ciBooking.bookingSelectionOnHomepage(acctname);
			uiTestHelper.propagateException();
			custaccpage.selectCustomerAccounts(acctname);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewBooking();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ciBooking Radion button is not selected");
		}
		try {
			BookingPage.callerInfo_SIRP();
			BookingPage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			uiTestHelper.propagateException();
			BookingPage.setBillingAccNumber(billAcctNum);
			BookingPage.setCountry(cou1);
			BookingPage.clickValidate();
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			BookingPage.setContactName("Arjun Rampal");
			BookingPage.setContactPhone("+91 4545898985");
			BookingPage.setContactEmail("Arjun_Rampal@gmail.com");
			Pass_Message("Details entered successfully in ciBooking Information Page");
			BookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Page is not navigated to Good Information Page successfully");
		}
		// Goods info page
		try {
			ciBooking.BK_GoodsInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details have not been entered successfully on Goods info page");
		}
		// Con Info Page
		// Box
		consignmentWeightandDimValidation(TC, Country, e_len, e_wid, e_ht, e_wt, "box");
		// Pallet
		consignmentWeightandDimValidation(TC, Country, pe_len, pe_wid, pe_ht, pe_wt, "pallet");
		// Envelope
		consignmentWeightandDimValidationEnvelope(TC, Country, envex_wt, "envelope");
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}
	// RIRP Flow
	@org.testng.annotations.Test(priority = 3, dataProvider = "RIRP_Data", enabled = false)
	public void WeightAndDimsValidations_RIRP(String TC, String Country, String acctname, String collcou,
			String coll_pc, String coll_town, String coll_custname, String coll_add, String e_len, String e_wid,
			String e_ht, String e_wt, String len, String wid, String ht, String wt, String pe_len, String pe_wid,
			String pe_ht, String pe_wt, String p_len, String p_wid, String p_ht, String p_wt, String envex_wt,
			String env_wt) throws Exception {
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage BookingPage = new BookingPage(getDriver());
		try {
			System.out.println(TC);
			ciBooking.bookingSelectionOnHomepage(acctname);
			uiTestHelper.propagateException();
			custaccpage.selectCustomerAccounts(acctname);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewBooking();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ciBooking Radion button is not selected");
		}
		try {
			BookingPage.callerInfo_RIRP();
			BookingPage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			uiTestHelper.propagateException();
			BookingPage.setCollectionCountry(collcou);
			BookingPage.setCollectionPostal(coll_pc);
			uiTestHelper.propagateException();
			BookingPage.setCollectionTown(coll_town);
			BookingPage.setCollectionCustomerName(coll_custname);
			BookingPage.setCollectionAddress(coll_add);
			BookingPage.clickCollectionValidatebtn();
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			BookingPage.setContactName("Arjun Rampal");
			BookingPage.setContactPhone("+91 4545898985");
			BookingPage.setContactEmail("Arjun_Rampal@gmail.com");
			Pass_Message("Details entered successfully in ciBooking Information Page");
			BookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Page is not navigated to Good Information Page successfully");
		}
		// Goods info page
		try {
			ciBooking.BK_GoodsInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details have not been entered successfully on Goods info page");
		}
		// Con Info Page
		// Box
		consignmentWeightandDimValidation(TC, Country, e_len, e_wid, e_ht, e_wt, "box");
		// Pallet
		consignmentWeightandDimValidation(TC, Country, pe_len, pe_wid, pe_ht, pe_wt, "pallet");
		// Envelope
		consignmentWeightandDimValidationEnvelope(TC, Country, envex_wt, "envelope");
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}
	/*@org.testng.annotations.Test(priority = 2, dataProvider = "SIRP_Data", enabled = false)
	public void WeightAndDimsValidations_SIRP(String TC11, String Country, String billAcctNum, String cou1,
			String acctname, String delcou, String del_pc, String del_town, String del_custname, String del_add,
			String e_len, String e_wid, String e_ht, String e_wt, String len, String wid, String ht, String wt,
			String pe_len, String pe_wid, String pe_ht, String pe_wt, String p_len, String p_wid, String p_ht,
			String p_wt, String envex_wt, String env_wt) throws Exception {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage BookingPage = new BookingPage(getDriver());
		try {
			ciBooking.bookingSelectionOnHomepage(acctname);
			uiTestHelper.propagateException();
			homepage.selectCustomerAccounts(acctname);
			uiTestHelper.propagateException();
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewBooking();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("ciBooking Radion button is not selected");
		}
		try {
			BookingPage.callerInfo_SIRP();
			BookingPage.confirmCustomerInstruction();
			uiTestHelper.scrolldown("300");
			uiTestHelper.propagateException();
			BookingPage.setBillingAccNumber(billAcctNum);
			BookingPage.setCountry(cou1);
			BookingPage.clickValidate();
			uiTestHelper.propagateException();
			uiTestHelper.scrolldown("300");
			BookingPage.setContactName("Arjun Rampal");
			BookingPage.setContactPhone("+91 4545898985");
			BookingPage.setContactEmail("Arjun_Rampal@gmail.com");
			Pass_Message("Details entered successfully in ciBooking Information Page");
			BookingPage.clickBookingnextbtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Page is not navigated to Good Information Page successfully");
		}
		// Goods info page
		try {
			ciBooking.BK_GoodsInfoPage();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Details have not been entered successfully on Goods info page");
		}
		// Con Info Page
		// Box
		consignmentWeightandDimValidation(TC, Country, e_len, e_wid, e_ht, e_wt, "box");
		// Pallet
		consignmentWeightandDimValidation(TC, Country, pe_len, pe_wid, pe_ht, pe_wt, "pallet");
		// Envelope
		consignmentWeightandDimValidationEnvelope(TC, Country, envex_wt, "envelope");
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
	}
*/
	public void consignmentWeightandDimValidation(String testCase, String Country, String length, String width,
			String height, String weight, String consignmentType) {
		try {
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(getDriver());
			getDriver().findElement(By.xpath("//input[@name='[object Object]']")).click();
			getDriver().findElement(By.xpath("//lightning-base-combobox-item[@data-value='" + consignmentType + "']"))
					.click();
			WebElement quantity = getDriver().findElement(By.xpath("(//input[@name='quantity'])[1]"));
			quantity.clear();
			quantity.sendKeys("1");
			getDriver().findElement(By.xpath("(//label[text()='Length (cm)']/following::input[1])[1]")).sendKeys(length,
					Keys.TAB);
			if (coninfopage.verifyLengthExceeds() == true) {
				Pass_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Length for "
						+ consignmentType + " is displayed correctly");
			} else {
				Fail_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Length for "
						+ consignmentType + "  is not displayed correctly");
			}
			getDriver().findElement(By.xpath("(//label[text()='Width (cm)']/following::input[1])[1]")).sendKeys(width,
					Keys.TAB);
			if (coninfopage.verifyWidthExceeds() == true) {
				Pass_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Width for "
						+ consignmentType + " is displayed correctly");
			} else {
				Fail_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Width for "
						+ consignmentType + " is not displayed correctly");
			}
			getDriver().findElement(By.xpath("(//label[text()='Height (cm)']/following::input[1])[1]")).sendKeys(height,
					Keys.TAB);
			if (coninfopage.verifyHeightExceeds() == true) {
				Pass_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Height for "
						+ consignmentType + " is displayed correctly");
			} else {
				Fail_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Height for "
						+ consignmentType + " is not displayed correctly");
			}
			getDriver().findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[1]")).sendKeys(weight,
					Keys.TAB);
			if (coninfopage.verifyWeightExceeds() == true) {
				Pass_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Weight for "
						+ consignmentType + " is displayed correctly");
			} else {
				Fail_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Weight for "
						+ consignmentType + " is displayed correctly");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void consignmentWeightandDimValidationEnvelope(String testCase, String Country, String weight,
			String consignmentType) {
		try {
			ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(getDriver());
			getDriver().findElement(By.xpath("//input[@name='[object Object]']")).click();
			getDriver().findElement(By.xpath("//lightning-base-combobox-item[@data-value='" + consignmentType + "']"))
					.click();
			WebElement quantity = getDriver().findElement(By.xpath("(//input[@name='quantity'])[1]"));
			quantity.clear();
			quantity.sendKeys("1");
			getDriver().findElement(By.xpath("(//label[text()='Weight (kg)']/following::input[1])[1]")).sendKeys(weight,
					Keys.TAB);
			if (coninfopage.verifyWeightExceeds() == true) {
				Pass_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Weight for "
						+ consignmentType + " is displayed correctly");
			} else {
				Fail_Message(testCase + ": " + Country + ": " + "Error msg for maximum allowed Weight for "
						+ consignmentType + " is displayed correctly");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
	public void closeTab() throws Exception {
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
