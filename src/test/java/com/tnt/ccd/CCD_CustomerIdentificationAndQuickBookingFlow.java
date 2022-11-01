package com.tnt.ccd;

import java.rmi.server.UID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.tnt.ccdobjects.AdditionalInfoPage;
import com.tnt.ccdobjects.BookingPage;
import com.tnt.ccdobjects.BookingRecordPage;
import com.tnt.ccdobjects.ConsignmentInfoPage;
import com.tnt.ccdobjects.CustomerAccountPage;
import com.tnt.ccdobjects.CustomerIdentificationPage;
import com.tnt.ccdobjects.GoodsInfoPage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.LoginPage;
import com.tnt.ccdobjects.QuickBookingPage;
import com.tnt.ccdobjects.QuoteAdditionalInfoPage;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

/**
 * File used for business logic for Booking Flow
 *
 */

public class CCD_CustomerIdentificationAndQuickBookingFlow extends Driver {
	LoginPage login;
	HomePage homePage;
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	UiTestHelper uiTestHelper = new UiTestHelper();
	public static String bookNum;

	public void bK_CustIdentification_Tab() throws Exception {
		try {
			// homepage.clickCustomerIdentification();
			CI_verifyCustomerEnquiryPage();
			Pass_Message("User is successfully navigated to customer identification page");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: customer identification tab validation failed");
			getDriver().navigate().refresh();

		}
	}

	public void checkBookingRefandDepot() throws Exception {
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setBookingReference("123456");
			cipage.setDepot("123");
			uiTestHelper.propagateException();
			if (cipage.isDepotField()) {
				Pass_Message("Depot field is enabled");
			} else {
				Fail_Message("Depot field is not enabled");
			}

			if (cipage.isBookingRefField()) {
				Pass_Message("Booking Reference field is enabled");
			} else {
				Fail_Message("Booking Reference field is not enabled");
			}

			if (!cipage.isQuoteRefField()) {
				Pass_Message("Quote reference field is not enabled");
			} else {
				Fail_Message("Quote reference field is enabled");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Depot and Booking Refrence field are not enabled");
			getDriver().navigate().refresh();
		}
	}

	public void checkQuoteRefandDepot() throws Exception {
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setQuoteRef("123456");
			if (cipage.isDepotField()) {
				Fail_Message("Depot field is enabled");
			} else {
				Pass_Message("Depot field is not enabled");
			}

			if (cipage.isQuoteRefField()) {
				Pass_Message("Quote reference field is enabled");
			} else {
				Fail_Message("Quote reference field is not enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Depot and Quote Refrence field are not enabled");
			getDriver().navigate().refresh();
		}
	}

	public void depotFieldValidation() throws Exception {
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		try {
			int temp = 0;
			Pass_Message("User is successfully logged in to salesforce as CSR");
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.validateDepotField();
			temp = temp + cipage.validateDepotField();
			cipage.setDepot("1234");
			String depotvalue = cipage.getDepotFieldValue();
			if (depotvalue.length() == 3) {
				Pass_Message("Max value for depot is restricted to 3 character");
				temp = temp + 1;
			} else {
				Fail_Message("Max value for depot is not restricted to 3 character");
			}
			if (temp == 3) {
				Pass_Message("Min and max value for depot is restricted to 3 character");
			} else {
				Fail_Message("Min and max value for depot is not restricted to 3 character");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Depot field validation failed");
			getDriver().navigate().refresh();
		}
	}

	public void BookingRefFieldValidation() throws Exception {
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		try {
			int temp = 0;
			Pass_Message("User is successfully logged in to salesforce as CSR");
			customerIdentification_dropDown();
			cipage.clickClearButton();
			temp = cipage.validateBookingRefField();
			// temp=temp+cipage.validateBookingRefField();
			System.out.println("temp: " + temp);
			cipage.setBookingReference("87654321");
			String depotvalue = cipage.getBookRefFieldValue();
			if (depotvalue.length() == 6) {
				Pass_Message("Max value for depot is restricted to 6 character");
				temp = temp + 1;
			} else {
				Fail_Message("Max value for depot is not restricted to 6 character");
			}
			if (temp == 6) {
				Pass_Message("Min and max value for depot is restricted to 6 character");
			} else {
				Fail_Message("Min and max value for depot is not restricted to 6 character");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Depot field validation failed");
			getDriver().navigate().refresh();
		}
	}

	public void bk_CI_NewCustomerBookingPage() throws Exception {
		BookingPage bkpage = new BookingPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		CCD_CI_Booking ciBooking = new CCD_CI_Booking();
		GoodsInfoPage goodspage = new GoodsInfoPage(getDriver());
		try {

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
				Pass_Message("Collection Details are updated successfully");
			}
			bkpage.setDelCustomerName(Deliv_Cust_Name);
			bkpage.setDeliveryAddress(Deliv_Add);
			bkpage.setDeliveryCountry(Deliv_Country);
			bkpage.setDeliveryPostal(Deliv_PostCode);
			bkpage.setDeliveryTown(Deliv_Town);
			bkpage.deliveryValidatebtn();
			if (bkpage.successMsgonAddress()) {
				Pass_Message("Delivery Details are updated successfully");
				ciBooking.setCollectionContactDetails("Nivetha","8940732594","nivetha.thirunavukarasu.osv@fedex.com");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: CI Booking SISP flow failed");
		}
	}

	public void bk_CI_NewCustomerBooking() throws Exception {
		CCD_CI_Booking ciBooking = new CCD_CI_Booking();
		bk_CI_NewCustomerBookingPage();
		ciBooking.BK_GoodsInfoPage();
		ciBooking.BK_ConInfo_Page();
		ciBooking.BK_AdditionalInfo_Page();
	}

	/**
	 * method to to verify and validate packaging group field
	 *
	 * @throws Exception
	 */
	public void validatePackagingGroupField() throws Exception {
		CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
		try {
			GoodsInfoPage goodspage = new GoodsInfoPage(getDriver());
			Pass_Message("User is successfully logged in to salesforce as CSR");
			bk_CI_NewCustomerBookingPage();
			goodspage.clickGoodsNonDoccontent();
			goodspage.setGoodsDesc("Test Desc");
			goodspage.setGoodsValue("20");
			goodspage.clickDangerousGoodsInd();
			Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page");
			goodspage.setUNNumber("3010");
			goodspage.searchDangerousGoods();
			boolean isTable = goodspage.verifyDangeoursGoodsTable();
			Assert.assertTrue(isTable);
			if (isTable) {
				goodspage.selectDangerousGoods();
			}
			if (goodspage.verifyDGPackagingGroup() == true) {
				Pass_Message("Packaging Group of Dangerous Goods displayed in the Goods Information Page");
			} else {
				Fail_Message("Packaging Group of Dangerous Goods Not displayed in the Goods Information Page");
			}
			if (!goodspage.getPacakagingGroup().isEmpty()) {
				Pass_Message_withoutScreenCapture(
						"Packaging group value dipalayed with " + goodspage.getPacakagingGroup());
			} else {
				Fail_Message("Packaging group value not dipalayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Packaging group field not validated");

		}
	}

	/**
	 * method to create booking with 3DG and with DryIce check box checked
	 *
	 * @throws Exception
	 */
	/*
	 * public void create_Booking_with_DryIceField_Checked_and_3DG_Selected() throws
	 * Exception { CustomerIdentificationPage cipage = new
	 * CustomerIdentificationPage(getDriver()); GoodsInfoPage goodsinfopage = new
	 * GoodsInfoPage(getDriver()); CCD_CI_Booking acmcibooking = new CCD_CI_Booking();
	 * 
	 * try { String dgname = "";
	 * Pass_Message("User is successfully logged in to salesforce as CSR");
	 * bk_CI_NewCustomerBooking(); cipage.clickDangerousGoodsYes();
	 * Pass_Message("Selected Dangerous Goods toggle to Yes in Goods Information Page"
	 * ); dgname = "Biological substance, Category B UN3373"; //
	 * goodsinfopage.setDangerousGoods(dgname); if (cipage.isDryIceFieldEnabled1())
	 * { Pass_Message("Dry Ice check box is enabled"); } else {
	 * Fail_Message("Dry Ice check box is disabled"); } cipage.checkDryIceField1();
	 * if (cipage.isDryIceFieldSelected1()) {
	 * Pass_Message("Dry Ice check box is checked"); } else {
	 * Fail_Message("Dry Ice check box is not checked"); }
	 * goodsinfopage.clickAddAnotherDGBtn(); dgname =
	 * "Genetically modified organisms UN3245";
	 * goodsinfopage.setAnotherDangerousGoods1(dgname); Pass_Message(dgname +
	 * " Dangerous Goods is Selected"); if (cipage.isDryIceFieldEnabled2()) {
	 * Pass_Message("Dry Ice check box is enabled"); } else {
	 * Fail_Message("Dry Ice check box is disabled"); } cipage.checkDryIceField2();
	 * if (cipage.isDryIceFieldSelected2()) {
	 * Pass_Message("Dry Ice check box is checked"); } else {
	 * Fail_Message("Dry Ice check box is not checked"); }
	 * 
	 * goodsinfopage.clickAddAnotherDGBtn1();
	 * 
	 * dgname = "Biological substance, Category B UN3373";
	 * goodsinfopage.setAnotherDangerousGoods2(dgname); Pass_Message(dgname +
	 * " selected in Dangerous Goods"); if (cipage.isDryIceFieldEnabled3()) {
	 * Pass_Message("Dry Ice check box is enabled"); } else {
	 * Fail_Message("Dry Ice check box is disabled"); } cipage.checkDryIceField3();
	 * if (cipage.isDryIceFieldSelected3()) {
	 * Pass_Message("Dry Ice check box is checked"); } else {
	 * Fail_Message("Dry Ice check box is not checked"); }
	 * goodsinfopage.clickGoodsInfoNextBtn(); acmcibooking.BK_ConInfo_Page();
	 * acmcibooking.BK_AdditionalInfo_Page();
	 * 
	 * addinfopage.getValidServices(); Pass_Message("Valid Service is selected");
	 * continueToBooking();
	 * 
	 * acmcibooking.BK_BookingPagesValidation(); System.out.println("");
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * Fail_Message("Booking with DG and Dry Ice failed");
	 * 
	 * } }
	 * 
	 */ /**
		 * TC39, TC40, TC42 and TC43 Quick booking flow
		 *
		 * @throws Exception
		 */
	public void bK_QuickBooking_Flow() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(getDriver());
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(getDriver());
		try {
			bk_NavigateToBookingPage();
			select_QuickBooking();
			quickbooking_CollectionContactDetailsValidation();
			qk_EnhanceLiabilityFieldValidation();
			goodsinfopage.clickEnhancedLiability();
			uiTestHelper.scrolldown("300");
			goodsinfopage.clickGoodsInfoNextBtn();
			qickBooking_ConInfo_Page();
			quickBooking_AdditionalInfo_Page();
			uiTestHelper.scrolldown("300");
			quickbookingpage.clickQuickBookingSubmitBtn();
			uiTestHelper.propagateException();
			try {
				String book = addinfopage.getBookingConfirmMsg();
				bookNum = book.substring(75, 85);
				System.out.println(bookNum);
				Pass_Message("Legacy number for quick booking is generated: " + bookNum);
			} catch (Exception e) {
				// TODO: handle exception
				Fail_Message("Booking Failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quick booking creation failed");
		}
	}

	/**
	 * TC41 Collection contact details validation
	 *
	 * @throws Exception
	 */
	public void bK_CollectContactDetailsValidation() throws Exception {
		try {
			bk_NavigateToBookingPage();
			select_QuickBooking();
			quickbooking_CollectionContactDetailsValidation();
			editCollectionContactDetails();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Special service indicator is not validated");
		}
	}

	/**
	 * TC45 valid services option validation
	 *
	 * @throws Exception
	 */
	public void qickBooking_ValidServices_Tab_Validation() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		BookingPage bookingpage = new BookingPage(getDriver());
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(getDriver());
		try {
			bk_NavigateToBookingPage();
			select_QuickBooking();
			uiTestHelper.scrolldown("300");
			bookingpage.clickBookingnextbtn();
			goodsinfopage.clickGoodsInfoNextBtn();
			qickBooking_ConInfo_Page();

			if (!quickbookingpage.isValidService()) {
				Pass_Message("Valid service option is not present for quick booking");
			} else {
				Fail_Message("Valid service option is present for quick booking");
			}
			quickbookingpage.clickradiobtnquickbooking();
			Assert.assertEquals(quickbookingpage.isValidService(), true);
			if (quickbookingpage.isValidService()) {
				Pass_Message("Valid service option is present for full booking");
			} else {
				Fail_Message("Valid service option is not present for full booking");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Special service indicator is not validated");
		}
	}

	/**
	 * TC46 Method to validate special service functionality for quick booking
	 *
	 * @throws Exception
	 */
	public void quickBooking_SpecialServiceValidation() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(getDriver());
		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			bk_NavigateToBookingPage();
			select_QuickBooking();
			quickbooking_CollectionContactDetailsValidation();
			qk_EnhanceLiabilityFieldValidation();
			goodsinfopage.clickEnhancedLiability();
			uiTestHelper.scrolldown("300");
			goodsinfopage.clickGoodsInfoNextBtn();
			qickBooking_ConInfo_Page();
			quickBooking_AdditionalInfo_Page();
			uiTestHelper.scrolldown("300");
			quickBooking_SpecialService_Tab_Validation();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: special service validation failed ");
		}
	}

	/**
	 * TC47 Method to check edit functionality for quick booking
	 *
	 * @throws Exception
	 */
	public void editQuickBooking() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			bK_QuickBooking_Flow();
			ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
			Assert.assertEquals(bkdetailspage.verifyEditBooking(), true);
			bkdetailspage.clickEditBooking();
			Pass_Message("User is able to click edit booking button");
			quickbookingpage.clickradiobtnquickbooking();
			uiTestHelper.propagateException();
			Assert.assertEquals(quickbookingpage.iscallerreceiverbtn(), true);
			if (quickbookingpage.iscallerreceiverbtn() == true) {
				Pass_Message("Quick booking is disabled");
				Pass_Message("Caller receiver button is displayed");
			} else {
				Fail_Message("Caller receiver button is not displayed");
			}
			if (quickbookingpage.ispaymentreceiverbtn() == true) {
				Pass_Message("Payment receiver button is displayed");
			} else {
				Fail_Message("Payment receiver button is not displayed");
			}
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Edit for quick booking is failed ");
		}
	}

	/**
	 * TC48 and TC49 Method to check cancel quick booking functionality
	 *
	 * @throws Exception
	 */
	public void cancelQuickBooking() throws Exception {
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		String callercancelled = "TEST", reason = "Duplicate Booking";
		try {
			Pass_Message("User is successfully logged in to salesforce as CSR");
			bK_QuickBooking_Flow();
			ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
			Assert.assertEquals(bkdetailspage.verifyCancelBookingbtn(), true);
			bkdetailspage.clickCancelBooking();
			Pass_Message("User is able to click cancel booking button");
			bkdetailspage.setCallerofCancel(callercancelled);
			uiTestHelper.propagateException();
			bkdetailspage.setCancelReason(reason);
			Pass_Message("User is able to fill cancelaation details");
			uiTestHelper.scrolldown("300");
			bkdetailspage.saveCancelBooking();
			try {
				if (bkdetailspage.verifySuccessMessage()) {
					Pass_Message("Quick Booking is Cancelled");
				} else {
					Fail_Message("Quick Booking Cancellation Failed");
				}
			} catch (Exception e) {
				Fail_Message("Quick Booking Cancellation Failed");
			}
			bkdetailspage.clickBookingHistroy();
			Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
			uiTestHelper.propagateException();
			bkdetailspage.getBookingStatus();
			if (bkdetailspage.getBookingStatus().equals("CN")) {
				Pass_Message("Quick booking status is updated as CN (cancelled) successfully");
				Pass_Message("Quick booking is cancelled successfully");
			} else {
				Fail_Message("Quick booking status is not updated to CN");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quick booking cancellation is failed ");
			getDriver().navigate().refresh();
		}
	}

	public void bk_NavigateToBookingPage() throws Exception {
		HomePage homepage = new HomePage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage bookingPage = new BookingPage(getDriver());
		try {
			String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			homepage.clickDropDownNavigationMenu();
			homepage.clickBooking();
			homepage.searchBooking(AcctName);
			custaccpage.selectCustomerAccounts(AcctName);
			Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer account Page not Displayed");
			;
			if (custaccpage.verifyCustomerAccountPage() == true) {
				custaccpage.clickContactRadiobtn();
				custaccpage.clickNewBooking();
			}
			Assert.assertEquals(bookingPage.verifyBookingTitle(), true, "New Booking page not Opened");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to booking page failed");

		}
	}

	public void select_QuickBooking() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		quickbookingpage.clickradiobtnquickbooking();
		Assert.assertEquals(quickbookingpage.verifyquickbooking(), true);
		try {
			if (quickbookingpage.iscallerreceiverbtn() == false) {
				Pass_Message("Caller receiver button is disabled for quick booking");
			} else {
				Fail_Message("Caller receiver button is not disabled for quick booking");
			}
			if (quickbookingpage.ispaymentreceiverbtn() == false) {
				Pass_Message("Payment receiver button is disabled for quick booking");
			} else {
				Fail_Message("Payment receiver button is not disabled for quick booking");
			}
			Assert.assertEquals(quickbookingpage.iscallersenderbtnenabled(), true);
			if (quickbookingpage.iscallersenderbtnenabled()) {
				Pass_Message("caller sender button is enabled for quick booking");
			} else {
				Fail_Message("caller sender button is disabled for quick booking");
			}
			Assert.assertEquals(quickbookingpage.ispaymentsenderbtnenabled(), true);
			if (quickbookingpage.ispaymentsenderbtnenabled()) {
				Pass_Message("Payment sender button is enabled for quick booking");
			} else {
				Fail_Message("Payment Sender button is disabled for quick booking");
			}
			uiTestHelper.scrolldown("300");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Quick booking SISP selection failed");

		}
	}

	public void quickbooking_CollectionContactDetailsValidation() throws Exception {
		try {
			SoftAssert softAssert = new SoftAssert();
			BookingPage bookingpage = new BookingPage(getDriver());
			QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
			String contactname = bookingpage.getCallerName();
			String contacttelephone = bookingpage.getCallerPhone();
			String contactemail = bookingpage.getCallerEmail();
			String sacicontactname = bookingpage.getContactName();
			String sacicontacttelephone = bookingpage.getContactPhone();
			String sacicontactemail = bookingpage.getContactEmail();
			if (!quickbookingpage.getBillingAccountNumber().equals("")) {
				Pass_Message("Billing account number is autopopulated");
			} else {
				Fail_Message("Billing account number is not autopopulated");
			}
			uiTestHelper.scrolldown("600");
			softAssert.assertEquals(contactname, sacicontactname);
			if (contactname.equals(sacicontactname)) {
				Pass_Message("Contact name is autopopulated");
			} else {
				Fail_Message("Contact name is autopopulated");
			}
			softAssert.assertEquals(contacttelephone, sacicontacttelephone);
			if (contacttelephone.equals(sacicontacttelephone)) {
				Pass_Message("Contact telephone is autopopulated");
			} else {
				Fail_Message("Contact telephone is autopopulated");
			}
			softAssert.assertEquals(contactemail, contactemail);
			if (contactemail.equals(sacicontactemail)) {
				Pass_Message("Contact email is autopopulated");
			} else {
				Fail_Message("Contact email is autopopulated");
			}
			softAssert.assertEquals(quickbookingpage.IsSenderSameAsCallerInfoSelected(), true);
			if (quickbookingpage.IsSenderSameAsCallerInfoSelected()) {
				Pass_Message("Same as caller info check box is checked");
			} else {
				Fail_Message("Same as caller info check box is not checked");
			}
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: collection contact details are not validated");
		}
	}

	public void editCollectionContactDetails() throws Exception {
		BookingPage bookingpage = new BookingPage(getDriver());
		SoftAssert softAssert = new SoftAssert();
		try {
			String conName = "SHAAD AHMAD";
			String conPhone = "1234567890";
			String conEmail = "SHAADTEST@gmail.com";
			bookingpage.setContactName(conName);
			bookingpage.setContactPhone(conPhone);
			bookingpage.setContactEmail(conEmail);
			String sacicontactname = bookingpage.getContactName();
			String sacicontacttelephone = bookingpage.getContactPhone();
			String sacicontactemail = bookingpage.getContactEmail();
			softAssert.assertEquals(sacicontactname, conName);
			if (sacicontactname.equals(conName)) {
				Pass_Message("contact name is updated");
			} else {
				Fail_Message("contact name is not editable");
			}
			softAssert.assertEquals(sacicontacttelephone, conPhone);
			if (sacicontacttelephone.equals(conPhone)) {
				Pass_Message("contact telephone is updated");
			} else {
				Fail_Message("contact telephone is not editable");
			}
			softAssert.assertEquals(sacicontactemail, conEmail);
			if (sacicontactemail.equals(conEmail)) {
				Pass_Message("contact Email is updated");
			} else {
				Fail_Message("contact Email is not editable");
			}
			softAssert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: User is not able to edit collection contact details");
		}
	}

	public void qk_EnhanceLiabilityFieldValidation() throws Exception {
		BookingPage bookingpage = new BookingPage(getDriver());
		GoodsInfoPage goodspage = new GoodsInfoPage(getDriver());
		try {
			bookingpage.clickBookingnextbtn();
			Assert.assertEquals(goodspage.verifyGoodsDescTitle(), true, "Goods Info Page not Opened");
			goodspage.clickEnhancedLiability();
			Assert.assertEquals(goodspage.verifyEnhancedLiabilityField(), true);
			if (goodspage.verifyEnhancedLiabilityField()) {
				Pass_Message("Enhanced Liability check box is checked");
				Pass_Message("User is able to check the Enhanced Liability");
			} else {
				Fail_Message("User is able to check the Enhanced Liability");
			}
			String amount = "100";
			goodspage.setEnhancedLiabilityInput(amount);

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Enhanced liability option is not validated");
		}
	}

	// con info paGE
	public void qickBooking_ConInfo_Page() throws Exception {
		ConsignmentInfoPage coninfopage = new ConsignmentInfoPage(getDriver());
		UiTestHelper uiTestHelper = new UiTestHelper();
		Assert.assertEquals(coninfopage.verifyConsignmentInfoTitle(), true, "Consignment Info Page not Opened");
		try {
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("(//input[@name='quantity'])[1]", "1");
			hashmap.put("(//label[text()='Length']/following::input[1])[1]", "10");
			hashmap.put("(//label[text()='Width']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Height']/following::input[1])[1]", "12");
			hashmap.put("(//label[text()='Weight (kg)']/following::input[1])[1]", "12");
			uiTestHelper.clickJSByObjects(By.xpath("(//button[@name='[object Object]'])[2]"));
			uiTestHelper.clickJSByObjects(By.xpath("//lightning-base-combobox-item[@data-value='box']"));

			for (HashMap.Entry<String, String> entry : hashmap.entrySet()) {
				uiTestHelper.waitForObject(By.xpath(entry.getKey())).sendKeys(entry.getValue());
			}
			Pass_Message("User is able to fill consignment informatsion details");
			coninfopage.clickConsignmentInfoNextBtn();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Navigation to Additional Information page failed");
		}
	}

	// Additional Info page
	public void quickBooking_AdditionalInfo_Page() throws Exception {
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(getDriver());
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		Assert.assertEquals(addinfopage.verifyTitle(), true, "Additional Info Page not Opened");
		try {
			if (!quickbookingpage.isValidService()) {
				Pass_Message("Valid service option is not present for quick booking");
			} else {
				Fail_Message("Valid service option is present for quick booking");
			}
			quickBooking_Update_CollDate_OnAdditionInfoPage();

			uiTestHelper.scrolldown("300");

			if (!quickbookingpage.getFromCollWindowTime().equalsIgnoreCase("")) {
				Pass_Message("From time in 'Preferred Collection Window' is autopopulated");
			} else {
				Fail_Message("From time in 'Preferred Collection Window' is autopopulated");
			}
			if (!quickbookingpage.getToCollWindowTime().equalsIgnoreCase("")) {
				Pass_Message("To time in 'Preferred Collection Window' is autopopulated");
			} else {
				Fail_Message("To time in 'Preferred Collection Window' is autopopulated");
			}
			// Select From time
			addinfopage.setFromCollWindowTimeInput("17:30");
			// Select To time
			addinfopage.setToCollWindowTimeInput("18:30");
			// Select unavailable from time
			addinfopage.setFromUnavailTimeInput("17:30");
			// Select unavailable To time
			addinfopage.setToUnavailTimeInput("18:30");
			addinfopage.setCollectionInstruction("Test instruction for collection driver");
			Pass_Message("Details entered successfully on Additional Information Page");
			uiTestHelper.scrolldown("300");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Details not entered on Additional Information Page");
			getDriver().navigate().refresh();
		}
	}

	public void quickBooking_Update_CollDate_OnAdditionInfoPage() throws Exception {
		try {
			QuoteAdditionalInfoPage adtnlinfopage = new QuoteAdditionalInfoPage(getDriver());
			AdditionalInfoPage additionalInfoPage = new AdditionalInfoPage(getDriver());
			String expecteddate = additionalInfoPage.getSystemDateinFormat("dd-MMM-yyyy");
			adtnlinfopage.enterCollectionDate(expecteddate);
			Pass_Message("Collection date is selected");

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Collection date update failed");
		}

	}

	public void quickBooking_SpecialService_Tab_Validation() throws Exception {
		try {
			AdditionalInfoPage addinfopage = new AdditionalInfoPage(getDriver());
			QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
			uiTestHelper.scrolldown("300");
			addinfopage.enableSpecialService();
			uiTestHelper.scrolldown("300");
			String expecteddate = addinfopage.getSystemDateinFormat("dd-MMM-yyyy");
			addinfopage.setCallbackDate(expecteddate);
			String time = addinfopage.getSystemTime();
			addinfopage.setcallbackTimeInput(time);
			addinfopage.setAddtionalInfoOfSpecialService("Additional info");
			uiTestHelper.scrolldown("700");
			addinfopage.confirmSpecialService();
			Assert.assertEquals(quickbookingpage.isSpsServiceConfirmMsg(), "Special service details have been saved.");
			if (quickbookingpage.isSpsServiceConfirmMsg().equals("Special service details have been saved.")) {
				Pass_Message(quickbookingpage.isSpsServiceConfirmMsg());
			} else {
				Fail_Message("Special service details have not been saved.");
			}
			quickbookingpage.clickQuickBookingSubmitBtn();
			String book = addinfopage.getBookingConfirmMsg();
			bookNum = book.substring(75, 85);
			Assert.assertNotNull(bookNum, "Special Service - Quick Booking not Created");
			Pass_Message("Legacy number for Special Service quick booking is generated: " + bookNum);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception:Special service selection failed");
			getDriver().navigate().refresh();

		}
	}

	public void continueToBooking() throws Exception {

		AdditionalInfoPage addinfopage = new AdditionalInfoPage(getDriver());
		uiTestHelper.scrolldown("300");
		addinfopage.clickGetPrice();
		uiTestHelper.scrolldown("300");
		addinfopage.setCollectionInstruction("Test instruction for collection driver");
		addinfopage.setDeliveryInstruction("Test instruction for delivery driver");
		Pass_Message("Details entered successfully on Additional Information Page");
		uiTestHelper.scrolldown("300");
		addinfopage.clickViewSummary();
		addinfopage.clickConfirmBooking();
	}

	public void quickBookingPagesValidation() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		String bookremarks = "123456";
		try {
			bK_QuickBooking_Flow();
			ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
			Assert.assertEquals(bkdetailspage.verifyBookingRemark(), true, "Booking Remark Button not Displayed");
			bkdetailspage.clickBookingRemark();
			bkdetailspage.setRemarks(bookremarks);
			bkdetailspage.saveBookingRemark();
			Assert.assertEquals(bkdetailspage.verifySuccessMessage(), true, "Booking Remark not Saved");
			if (bkdetailspage.verifySuccessMessage()) {
				Pass_Message("Booking remarks saved");
			}
			bkdetailspage.clickRemarkTab();
			if (!quickbookingpage.getBookingRemarksNumber().isEmpty()) {
				Pass_Message("Booking remark is created and Validated");
			} else {
				Fail_Message("Booking remark is not created");
			}
			if (quickbookingpage.verifyBookingException()) {
				Pass_Message("'Booking Exception History' tab is present");
				quickbookingpage.clickBookingException();
			} else {
				Fail_Message("'Booking Exception History' tab is not present");
			}
			if (quickbookingpage.verifyBookingAudit()) {
				Pass_Message("'Booking Audit' tab is present");
				quickbookingpage.clickBookingAudit();
			} else {
				Fail_Message("'Booking Audit' tab is not present");
			}
			bkdetailspage.clickBookingHistroy();
			Pass_Message("'Booking History' tab is present");
			Assert.assertEquals(bkdetailspage.successMsgonBookingHistory(), true);
			Pass_Message("Booking history is Updated");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exeption: Booking Pages Valdations failed");
		}
	}

	public void customerIdentification_dropDown() throws Exception {
		try {
			HomePage homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerIdentification();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer Identification option is not found");
		}
	}

	public void CI_verifyCustomerEnquiryPage() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			customerIdentification_dropDown();
			Assert.assertEquals(cipage.verifyCustomerEnquiryPage(), true);
			if (cipage.verifyCustomerEnquiryPage()) {
				Pass_Message("Customer Enquiry Page Dispalyed Successfully");
			} else {
				Fail_Message("Customer Enquiry Page is Not Dispalyed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer Enquiry Page is Not Verified");
		}
	}

	public void verifySearchResult_CustNameandCountry() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String customerName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setCustomerName(customerName);
			cipage.setCountry(country);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifySearchResultTable(), true, "Search Result table not Displayed");
			if (cipage.verifySearchResultTable()) {
				Pass_Message("Search Result displayed as per the Customer Name and Country");
			} else {
				Fail_Message("Search Result Not displayed as per the Customer Name and Country");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer details Not displayed");
		}
	}

	public void verifySearchResult_AccountNoandCountry() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String accountNumber = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setAccountNumber(accountNumber);
			cipage.setCountry(country);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifySearchResultTable(), true, "Search Result table not Displayed");
			if (cipage.verifySearchResultTable()) {
				Pass_Message("Search Result displayed as per the Accoutnt Number and Country");
			} else {
				Fail_Message("Search Result Not displayed as per the Account Number and Country");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer details Not displayed");
		}
	}

	public void verifySearchResult_PostalCodeandCountry() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String postal_Code = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setPostCode(postal_Code);
			cipage.setCountry(country);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifySearchResultTable(), true, "Search Result table not Displayed");
			if (cipage.verifySearchResultTable()) {
				Pass_Message("Search Result displayed as per the Postal Code and Country");
			} else {
				Fail_Message("Search Result Not displayed as per the Postal Code and Country");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer details Not displayed");
		}
	}

	public void verifySearchResult_TelephoneNumberandCountry() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String telephone_Number = Database_Connection.retrieveTestData("SENDERPHONE", "ACM", "KEY",
					CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setPhoneNumber(telephone_Number);
			cipage.setCountry(country);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifySearchResultTable(), true, "Search Result table not Displayed");
			if (cipage.verifySearchResultTable()) {
				Pass_Message("Search Result displayed as per the Telephone Number and Country");
			} else {
				Fail_Message("Search Result Not displayed as per the Telephone Number and Country");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer details Not displayed");
		}
	}

	public void verifySearchResult_ReferenceNumberandCountry() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String reference_Number = Database_Connection.retrieveTestData("BOOKINGNUM", "ACM", "KEY",
					CCD_CI.Key_Array[5]);
			String num[] = reference_Number.split("-");
			String depot = num[0];
			String bookingReference = num[1];
			String country = Database_Connection.retrieveTestData("SEN_COU", "ACM", "KEY", CCD_CI.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setDepot(depot);
			cipage.setBookingReference(bookingReference);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifySearchResultTable(), true, "Search Result table not Displayed");
			if (cipage.verifySearchResultTable()) {
				Pass_Message("Search Result displayed as per the Reference Number and Country");
			} else {
				Fail_Message("Search Result Not displayed as per the Reference Number and Country");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer details Not displayed");
		}
	}

	public void verifySearchResult_with_AccountDetailsPage() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			CustomerAccountPage capage = new CustomerAccountPage(getDriver());
			verifySearchResult_CustNameandCountry();
			cipage.clickSearchResult();
			if (capage.verifyCustomerAccountPage()) {
				Pass_Message("Customer Account Page is Displayed Successfully");
			} else {
				Fail_Message("Customer Account Page not Displayed Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Customer Account details Not displayed");
		}
	}

	public void verifySearchResult_with_invalidData() throws Exception {
		try {
			CustomerIdentificationPage cipage = new CustomerIdentificationPage(getDriver());
			String postal_Code = Database_Connection.retrieveTestData("SEN_POSTCODE", "ACM", "KEY",
					CCD_CI.Key_Array[5]);
			String country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY", CCD_Booking.Key_Array[5]);
			customerIdentification_dropDown();
			cipage.clickClearButton();
			cipage.setPostCode(postal_Code);
			cipage.setCountry(country);
			cipage.clickSearchButton();
			Assert.assertEquals(cipage.verifyErroronResult(), true);
			if (cipage.verifyErroronResult()) {
				Pass_Message("No Result Found is displayed for Invalid Data");
			} else {
				Fail_Message("Invalid Test data not vrerified successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Invalid Test data not Verified");
		}
	}

	public void validate_Alert_Message_And_Check_Past_Migration_Date_And_Future_Overlap_Date() throws Exception {
		// Declare Customer Account as String - Australia Account
		String customerName = Database_Connection.retrieveTestData("DESTNAME", "ACM", "KEY", CCD_CI.Key_Array[6]);
		String customerAccount = Database_Connection.retrieveTestData("REC_ACCT", "ACM", "KEY", CCD_CI.Key_Array[6]);
		try {
			// Go to Home_page and pass in customer account
			HomePage homepage = new HomePage(getDriver());
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerAccountsFromListBox();
			homepage.searchCustomerAcc(customerAccount);
			homepage.validateCustomerIdentity(customerName);
			// Validate migration date
			CustomerIdentificationPage customerIdentificationPage = new CustomerIdentificationPage(getDriver());
			String migrationDateString = customerIdentificationPage.getMigrationDate();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date todaysDate = new Date();
			Date migrationDate = format.parse(migrationDateString);
			softAssertion.assertTrue(migrationDate.before(todaysDate), "Migration date should be in the past");

			// Validate overLap Date
			String overLapDateString = customerIdentificationPage.getOverLapDate();
			Date overLapDate = format.parse(overLapDateString);
			softAssertion.assertTrue(overLapDate.before(todaysDate), "Overlap date should be in the past");
			softAssertion.assertAll();

		} catch (Exception e) {
			Fail_Message("Alert message not verified with Past Migration and Future Overlap date");
		}
	}

	public void validate_Alert_Message_And_Check_Past_Migration_Date_And_Overlap_Date() throws Exception {
		try {
			// Declare Hong Kong Account
			String customerName = Database_Connection.retrieveTestData("SENDERNAME", "ACM", "KEY", CCD_CI.Key_Array[6]);
			String customerAccount = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY",
					CCD_CI.Key_Array[6]);

			// Go to Home_page and pass in customer account
			HomePage homepage = new HomePage(getDriver());
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerAccountsFromListBox();
			homepage.searchCustomerAcc(customerAccount);
			homepage.validateCustomerIdentity(customerName);

			// Validate migration date
			CustomerIdentificationPage customerIdentificationPage = new CustomerIdentificationPage(getDriver());
			String migrationDateString = customerIdentificationPage.getMigrationDate();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			Date todaysDate = new Date();
			Date migrationDate = format.parse(migrationDateString);
			softAssertion.assertTrue(migrationDate.before(todaysDate), "Migration date should be in the past");

			String overLapDateString = customerIdentificationPage.getOverLapDate();
			Date overLapDate = format.parse(overLapDateString);
			softAssertion.assertTrue(overLapDate.after(todaysDate), "Overlap date should be in the future");
			softAssertion.assertAll();

		} catch (Exception e) {
			Fail_Message("Alert message not verified with Past Migration and Overlap date");
		}
	}

	public void validate_Account_and_See_No_Alert_Message_And_No_Date() throws Exception {
		try {
			// Declare Portugal Account
			String customerName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
			String customerAccount = Database_Connection.retrieveTestData("SEN_ACCT", "ACM", "KEY",
					CCD_CI.Key_Array[5]);
			System.out.println(customerName);
			// Go to Home_page and pass in customer account
			HomePage homepage = new HomePage(getDriver());
			uiTestHelper.propagateException();
			homepage.clickDropDownNavigationMenu();
			homepage.clickCustomerAccountsFromListBox();
			homepage.searchCustomerAcc(customerAccount);
			homepage.validateCustomerIdentity(customerName);

			// Validate No Dates for customer account searched
			CustomerIdentificationPage customerIdentificationPage = new CustomerIdentificationPage(getDriver());
			Boolean noMigrationResult = customerIdentificationPage.isMigrationDateDisplayed();
			softAssertion.assertFalse(noMigrationResult, "Migration Date is Visable");

			Boolean noOverLapResult = customerIdentificationPage.isOverLapDateDisplayed();
			softAssertion.assertFalse(noOverLapResult, "Overlap Date is Visable");
			Pass_Message("Account Validated with No Alert Message and migration/Overlap Date");
			softAssertion.assertAll();

		} catch (Exception e) {
			Fail_Message("Alert message not verified with out Migration and Overlap date");
		}
	}

	public void getTimingConvertQuickBookingToBooking() throws Exception {
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		AdditionalInfoPage addinfopage = new AdditionalInfoPage(getDriver());
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(getDriver());
		CCD_CustomerIdentificationAndQuickBookingFlow ciflow = new CCD_CustomerIdentificationAndQuickBookingFlow();
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		BookingRecordPage bkdetailspage = new BookingRecordPage(getDriver());
		ConsignmentInfoPage consignmentInfoPage = new ConsignmentInfoPage(getDriver());
		BookingPage bookingpage = new BookingPage(getDriver());
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Cust_Name = Database_Connection.retrieveTestData("DELIV_CUST_NAME", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Add = Database_Connection.retrieveTestData("DELIV_ADD", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_Town = Database_Connection.retrieveTestData("DELIV_TOWN", "ACM", "KEY", CCD_Booking.Key_Array[6]);
		String Deliv_PostCode = Database_Connection.retrieveTestData("DELIV_POSTCD", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		try {
			bk_NavigateToBookingPage();
			select_QuickBooking();
			quickbooking_CollectionContactDetailsValidation();
			qk_EnhanceLiabilityFieldValidation();
			goodsinfopage.clickEnhancedLiability();
			uiTestHelper.scrolldown("300");
			goodsinfopage.clickGoodsInfoNextBtn();
			qickBooking_ConInfo_Page();
			ciflow.quickBooking_AdditionalInfo_Page();
			uiTestHelper.scrolldown("300");
			quickbookingpage.clickQuickBookingSubmitBtn();
			uiTestHelper.propagateException();
			try {
				String book = addinfopage.getBookingConfirmMsg();
				bookNum = book.substring(75, 85);
				System.out.println(bookNum);
				Pass_Message("Legacy number for quick booking is generated: " + bookNum);
			} catch (Exception e) {
				// TODO: handle exception
				Fail_Message("Booking Failed");
			}

			ci_booking.BK_getRecentBookingfrom_BookingList(bookNum);
			bkdetailspage.clickEditBooking();
			Pass_Message("User is able to click edit booking button");
			quickbookingpage.clickradiobtnquickbooking();
			uiTestHelper.propagateException();
			bookingpage.setDeliveryCountry(Deliv_Country);
			bookingpage.setDeliveryPostal(Deliv_PostCode);
			bookingpage.setDeliveryTown(Deliv_Town);
			bookingpage.setDelCustomerName(Deliv_Cust_Name);
			bookingpage.setDeliveryAddress(Deliv_Add);
			bookingpage.deliveryValidatebtn();
			if (bookingpage.successMsgonAddress()) {
				Assert.assertEquals(bookingpage.successMsgonAddress(), true);
				Pass_Message("Delivery Address is Validated");
			}

			bookingpage.clickBookingnextbtn();
			ci_booking.BK_GoodsInfoPage();
			consignmentInfoPage.clickConsignmentInfoNextBtn();
			ci_booking.verifyTimingsOn_AdditionalInfo_Page_ForEditBooking();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Quick booking creation failed");
		}
	}

	public void verifyPostcode_masc_for_quickBooking() throws Exception {
		CCD_CI_Booking ci_booking = new CCD_CI_Booking();
		QuickBookingPage quickbookingpage = new QuickBookingPage(getDriver());
		CustomerAccountPage custaccpage = new CustomerAccountPage(getDriver());
		BookingPage bookingpage = new BookingPage(getDriver());
		String AcctName = Database_Connection.retrieveTestData("ACCT_NAME", "ACM", "KEY", CCD_CI.Key_Array[5]);
		String Deliv_Country = Database_Connection.retrieveTestData("DELIV_COU", "ACM", "KEY",
				CCD_Booking.Key_Array[6]);
		ci_booking.bookingSelectionOnHomepage(AcctName);
		custaccpage.selectCustomerAccounts(AcctName);
		Assert.assertEquals(custaccpage.verifyCustomerAccountPage(), true, "Customer Account page not Displayed");
		if (custaccpage.verifyCustomerAccountPage() == true) {
			custaccpage.clickContactRadiobtn();
			custaccpage.clickNewBooking();
		}
		Assert.assertEquals(bookingpage.verifyBookingTitle(), true, "Booking Page not Displayed");
		quickbookingpage.clickradiobtnquickbooking();
		Assert.assertEquals(quickbookingpage.verifyquickbooking(), true);
		uiTestHelper.scrolldown("300");
		bookingpage.setDeliveryCountry(Deliv_Country);
		Assert.assertEquals(bookingpage.getDeliveryPostalMasc(), "NNNN");
		Pass_Message("Delivery Postal masc Enabled for Booking");
		Assert.assertEquals(bookingpage.getCollectionPostalMasc(), "NNNN-NNN");
		Pass_Message("Collection Postal masc Enabled for Booking");
	}

	public void BK_quickBooking_CollDriver_Inst_charsLength() throws Exception {
		AdditionalInfoPage adtnlinfopage = new AdditionalInfoPage(getDriver());
		GoodsInfoPage goodsinfopage = new GoodsInfoPage(getDriver());
		bk_NavigateToBookingPage();
		select_QuickBooking();
		quickbooking_CollectionContactDetailsValidation();
		qk_EnhanceLiabilityFieldValidation();
		goodsinfopage.clickEnhancedLiability();
		uiTestHelper.scrolldown("300");
		goodsinfopage.clickGoodsInfoNextBtn();
		qickBooking_ConInfo_Page();
		quickBooking_Update_CollDate_OnAdditionInfoPage();
		uiTestHelper.scrolldown("300");
		adtnlinfopage.setCollectionInstruction(
				"Please enter more than eighty chars in collection driver instruction in the Additioal Information Page of Booking");
		int collectionDriverInstlen = adtnlinfopage.getCollectionInstruction().length();
		System.out.println(collectionDriverInstlen);
		Assert.assertEquals(collectionDriverInstlen, 80);
		Pass_Message("Collection Instruction Length matched to IMADE Charcter Limit");
	}

}
