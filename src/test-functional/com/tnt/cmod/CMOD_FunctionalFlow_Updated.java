
package com.tnt.cmod;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccdobjects.CaseDetailsPage;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.ccdobjects.CreateCasePage;
import com.tnt.ccdobjects.HomePage;
import com.tnt.ccdobjects.ProactiveExceptionsPage;
import com.tnt.ccdobjects.TrackAndTracePage;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class CMOD_FunctionalFlow_Updated extends Driver {
	// Commit on 13-Nov-20
	String globalSearch, caseNo;
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	String casenumber, akash, TimeGreen, TimeAmber, Timered, queuename, exp_date, Retrive;
	String Country;
	UiTestHelper uiTestHelper=new UiTestHelper();

	public static String myCon;

	public void CreateCase_EBS() {
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.clickCCLocation();
			uiTestHelper.propagateException();
			ccpage.clickOrigin();
			ccpage.setReason("Late Delivery");
			ccpage.setCause("Missed Connection");
			// ccpage.setReason("Undeliverable");
			// ccpage.setCause("Insufficient Address");
			ccpage.setFirstName("TestFirst");
			ccpage.setLastName("TestLast");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			ccpage.clickCaseAssign();
			Pass_Message("Case creation details entered successfully");
			ccpage.clickCaseCreatebtn();
			uiTestHelper.propagateException();
			// ccpage.createdStatus();
			String status = ccpage.getCreatedStatus();
			Assert.assertEquals("Created", status);
			Pass_Message("Reactive case is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case creation test case is failed");
		}
	}

	public void createCase_WithoutAssignToMe() {
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			HomePage hp = new HomePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			// ccpage.clickCCLocation();
			uiTestHelper.propagateException();
			// ccpage.clickOrigin();
			ccpage.setReason("Missing");
			ccpage.setCause("Missing Contents");
			ccpage.setFirstName("Test");
			ccpage.setLastName("Test");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			Pass_Message("Case creation details entered successfully");
			uiTestHelper.propagateException();
			ccpage.clickCaseCreatebtn();
			ccpage.createdStatus();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive case is created successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Reactive Case creation test case is failed");
		}
	}

	public void createCaseWithValues_WithoutAssignToMe(String loc, String caseRoute, String reason, String cause)
			{
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			HomePage hp = new HomePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.setCaseLoc(loc);
			ccpage.setCaseRoute(caseRoute);
			ccpage.setReason(reason);
			ccpage.setCause(cause);
			ccpage.setFirstName("Test");
			ccpage.setLastName("Test");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			Pass_Message("Case creation details entered successfully");
			uiTestHelper.propagateException();
			ccpage.clickCaseCreatebtn();
			ccpage.createdStatus();
			boolean val = hp.verifyCaseDisplayed();
			Assert.assertEquals(val, true);
			{
				Pass_Message("Reactive case is created successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Reactive Case creation test case is failed");
		}
	}

	public void Email_EBS(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		String Retrive = null;
		if (caseType.contains("re_case")) {
			Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[7]);
		} else {
			Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY", CMOD_PECaseFlow.Key_Array[8]);
		}
		System.out.println("Consignment Number: " + Retrive);
		// Retrive = "638257793";// "324679456"
		connectivity.CloseTab();

		ffUpdated.searchConsignmentOption(Retrive);
		Support.viewOrCreateCase();
		uiTestHelper.propagateException();
		try {
			casedetailpage.clickConsignmentInfo();
			casedetailpage.clickDestinationDepot();
			casedetailpage.clickAdditionalInfo();
			String emailTo = casedetailpage.getEmail();
			System.out.println("Mail TO: " + emailTo);
			Support.internal_tabclose();
			casedetailpage.clickEmailTab();
			casedetailpage.clickCompose();
			casedetailpage.setEmailTo(emailTo);
			casedetailpage.setEmailSubject("Request for Information/Action");
			casedetailpage.clickTemplate();
			System.out.println("Template clicked");
			casedetailpage.insertTemplatebtn();
			casedetailpage.selectTemplateType("All Classic Templates");
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
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email service failed");
		}
		// Escalations
		// try {
		// casedetailpage.clickRelatedTab();
		// uiTestHelper.propagateException();
		// casedetailpage.clickEscalation();
		// int Size=casedetailpage.getEscalationTableSize();
		// casedetailpage.clickescalationTable(Size,"Request for Information/Action");
		// casedetailpage.clickescalatebtn();
		// boolean escVal= casedetailpage.verifyEmailEscalationMessage();
		// Assert.assertEquals(escVal, true);
		// {
		// Pass_Message("Email cannnot be escalated and an error message is displayed
		// successfully");
		// }
		// Support.internal_tabclose();
		// connectivity.CloseTab();
		// }
		// catch(Exception e)
		// {
		// Support.internal_tabclose();
		// connectivity.CloseTab();
		// e.printStackTrace();
		// Fail_Message("Exception: Email escalation failed");
		// }
	}

	public void sendEmailFromOIB() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FF_Reusable Support = new CMOD_FF_Reusable();
		try {
			casedetailpage.clickConsignmentInfo();
			casedetailpage.clickCollectionDepot();
			casedetailpage.clickAdditionalInfo();
			String emailTo = casedetailpage.getEmail();
			System.out.println("Mail TO: " + emailTo);
			Support.internal_tabclose();
			casedetailpage.clickEmailTab();
			casedetailpage.clickCompose();
			// uiTestHelper.propagateException();
			casedetailpage.setEmailTo(emailTo);
			casedetailpage.setEmailSubject("Test Email");
			casedetailpage.switchToParentFrameEmailBody();
			casedetailpage.switchToFrameEmailBody();

			casedetailpage.setEmailbody("Test email for validation");
			getDriver().switchTo().defaultContent();
			// uiTestHelper.propagateException();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,450)");
			casedetailpage.clickSendBtn();
			String confirmMessage = casedetailpage.getEmailConfirmMessage();
			if (confirmMessage.isEmpty()) {
				Fail_Message("Email is not Sent Successfully");
			} else {
				Pass_Message("Email sent successfully" + confirmMessage);
			}
			getDriver().navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Email service failed");
		}

		Support.internal_tabclose();
	}

	public void RFI_EBS() {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity conn = new CCD_Connectivity();
		CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
		HomePage homepage = new HomePage(getDriver());
		ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		// String Retrive = Database_Connection.retrieveTestData("CONNORT",
		// "ACM","KEY",CMOD_ReactiveCaseFlow.Key_Array[7]);
		String Retrive = "625564517";
		connectivity.TrackandTraceCCD(Retrive);

		ConsignmentPage conpage = new ConsignmentPage(getDriver());

		try {
			if (conpage.verifyViewCase() == true) {
				connectivity.CCD_ViewCase();
			}
		} catch (Exception e) {
			ffUpdated.CreateCase_EBS();
		}

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
			if (casedetailpage.verifySuccessMessage() == false) {
				Fail_Message("RFI Not Created");
			} else {
				Pass_Message("RFI created");
				casedetailpage.clickRelatedTab();
				casedetailpage.clickEscalation();
				int Size=casedetailpage.getEscalationTableSize();
				System.out.println("Size: "+Size);
				casedetailpage.clickescalationTable(Size,"RFI");
				ResponderName=casedetailpage.getresponderName();
				System.out.println("Responder Name: "+ResponderName);
				casedetailpage.clickescalatebtn();
				Pass_Message("Can not be escalated as the current SLA has not breached");
			}
	
			//Open RFI
			reuse.logout();
			reuse.OIB_Support_Login();
			conn.CloseTab();
			String consignmentNo = conn.TrackandTraceCCDSearch();
			System.out.println("Consignment No: " + consignmentNo);
			reuse.RFI_OIBPEAccept(ResponderName, consignmentNo);
			// To select case from My Open cases
			try {
				conn.CloseTab();
				homepage.clickDropDownNavigationMenu();
				homepage.clickCases();
				proactivepage.clickRecentlyViewedItems();
				proactivepage.searchInput("1 - My Open Cases");
				proactivepage.clickAssignedInput("1 - My Open Cases");
				EventFiringWebDriver event=new EventFiringWebDriver(getDriver());
				event.executeScript("document.querySelector('div[data-aura-class=\"uiScroller\"][class*=\"uiScroller\"]').scrollTop = 1500");
				//To select case from MyOpenCase table

				WebElement myTable = getDriver().findElement(By.xpath("//span[text()='Cases']/following::table[1]/tbody"));
				List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
				int row_count = objRow.size();
				System.out.println("Row count in MyOpenCases table is " + row_count);
				for (int i = 1; i <= row_count; i++) {
					String a = getDriver()
							.findElement(By
									.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"))
							.getText();
					if (a.equals(caseNo)) {
						WebElement myCon = getDriver().findElement(
								By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr[" + i + "]/th/span/a"));
						myCon.click();
						break;
					}
				}
				Pass_Message("Case selected from cases tab");
			} catch (Exception e) {
				e.printStackTrace();
				Fail_Message("Case selection from My Open Cases failed");
			}
			reuse.RFI_AnswerandClose();
			/*
			 * reuse.logout(); ACM_FF_Reusable Support = new ACM_FF_Reusable();
			 * Support.support_Login(); conn.CloseTab(); conn.TrackandTraceCCDSearch();
			 * conn.CCD_ViewCase();
			 */
			boolean rfiUnreadAnswer = casedetailpage.getRFINoteImage();
			if (rfiUnreadAnswer) {
				Pass_Message("CO got the RFI message successfully");
			} else {
				Fail_Message("CO have not get the RFI message successfully");
			}
			// Support.internal_tabclose();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: RFI service failed");
		}
	}

	public void searchConsignmentOption(String conNumber) {
		CCD_Connectivity connectivity = new CCD_Connectivity();
		TrackAndTracePage ttPage = new TrackAndTracePage(getDriver());
		try {
			if (ttPage.verifyNewSearchDisplayed() == true) {
				connectivity.searchConWithNewSearchBtn(conNumber);
			} else {
				connectivity.TrackandTraceCCD(conNumber);
			}
		} catch (Exception e) {
			connectivity.TrackandTraceCCD(conNumber);
		}
	}

	public void casedetails_caseebs() {
		// Case Details Page validations
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
		try {
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[7]);
			connectivity.CloseTab();
			searchConsignmentOption(Retrive);
			reuse.viewOrCreateCase();
			getDriver().navigate().refresh();
			casedetailpage.clickCaseDetails();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.isEmpty()) {
				Fail_Message("Case owner is not displayed correctly");
			} else {
				Pass_Message("Case owner displayed as: " + caseOwner);
			}
			String caseOwnerCountry = casedetailpage.getcaseOwnerCountry();
			if (caseOwnerCountry.isEmpty()) {
				Fail_Message("Case owner Country is not displayed correctly");
			} else {
				Pass_Message("Case owner displayed as: " + caseOwnerCountry);
			}
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
			String custdialOwner = casedetailpage.getCustodialOwner();
			// Assert.assertNull(custdialOwner);
			if (custdialOwner.isEmpty()) {
				Fail_Message("Custodial Owner is blank");
			} else {
				Pass_Message("CustodialOwner id is displayed as : " + custdialOwner);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Details Page is not validated successfully");
		}
		// ******************************************************************
		// Case Information Page validations
		try {
			casedetailpage.clickCaseInformation();
			uiTestHelper.propagateException();
			String casetype = casedetailpage.getcaseType();
			System.out.println("CaseType" + casetype);
			if (casetype.contains("Reactive")) {
				Pass_Message("Case Type displayed is 'Reactive case' ");
			} else {
				Fail_Message("Case Type is not displayed correctly");
			}
			// String casestatus=casedetailpage.getReactiveCaseStatus();
			/*
			 * if (casestatus.isEmpty()==false) {
			 * Pass_Message("Case status displayed is '"+casestatus+"' "); } else {
			 * Fail_Message("Case status is not displayed correctly"); }
			 */
			String caseGroupt = casedetailpage.getcaseGroup();
			if (caseGroupt.contains("Shipment")) {
				Pass_Message("Case Group displayed is correctly as: " + caseGroupt);
			} else {
				Fail_Message("Case Group is not displayed correctly");
			}
			String caseReason = casedetailpage.getcaseReason();
			if (caseReason.contains("Late Delivery"))// "Undeliverable");
			{
				Pass_Message("Case Reason displayed correctly as: " + caseReason);
			} else {
				Fail_Message("Case Reason is not displayed correctly");
			}
			String cause = casedetailpage.getcaseCause();
			if (cause.contains("Missed Connection")) // Driver Delayed//Insufficient Address
			{
				Pass_Message("Cause displayed correctly as: " + cause);
			} else {
				Fail_Message("Cause is not displayed correctly");
			}
			String sendContName = casedetailpage.getcontactName();
			if (sendContName.contains("TestFirst TestLast")) {
				Pass_Message("Sender Contact Name displayed correctly as: " + sendContName);
			} else {
				Fail_Message("Sender Contact Name is not displayed correctly");
			}
			String sendPhone = casedetailpage.getcontactPhone();
			if (sendPhone.contains("0123456789")) {
				Pass_Message("Sender Contact Phone displayed correctly as: " + sendPhone);
			} else {
				Fail_Message("Sender Contact Phone is not displayed correctly");
			}
			String email = casedetailpage.getContactEmail();
			if (email.contains("test@fedex.com")) {
				Pass_Message("Sender Contact email displayed correctly as: " + email);
			} else {
				Fail_Message("Sender Contact email is not displayed correctly");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Information Page is not validated successfully");
		}
		// ***********************************************************
		// Consignment Information Page validations
		try {
			casedetailpage.clickConsignmentInfo();
			String consignmentNumner = casedetailpage.getconsignMentNo();
			if (consignmentNumner.isEmpty()) {
				Fail_Message("Consignment Number is not displayed");
			} else {
				Pass_Message("Consignment Number is displayed correctly as: " + consignmentNumner);
			}
			String collDepot = casedetailpage.getCollectionDepot();
			if (collDepot.isEmpty()) {
				Fail_Message("Collection Depot is not displayed");
			} else {
				Pass_Message("Collection Depot is displayed correctly as: " + collDepot);
			}
			String destDepot = casedetailpage.getDestinationDepot();
			if (destDepot.isEmpty()) {
				Fail_Message("Destination Depot is not displayed");
			} else {
				Pass_Message("Destination Depot is displayed correctly as: " + destDepot);
			}
			uiTestHelper.propagateException();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Consignment Information Page is not validated successfully");
		}
		// *****************************************************
		// Sender & Receiver Information Page validations
		try {
			uiTestHelper.propagateException();
			casedetailpage.clickMoreTab();
			casedetailpage.clickSenderAndReceiverInfo();
			String SenderAccNo = casedetailpage.getsenderAccountNo();
			if (!SenderAccNo.isEmpty()) {
				Pass_Message("Sender Account Number is displayed as: " + SenderAccNo);
			}
			// else
			// {
			// Fail_Message("Sender Account Number is not displayed");
			// }
			String SenderContactName = casedetailpage.getsenderContact();
			if (SenderContactName.isEmpty()) {
				Fail_Message("Sender Contact Name is not displayed");
			} else {
				Pass_Message("Sender's Contact Name is displayed as: " + SenderContactName);
			}
			String senderCountry = casedetailpage.getsenderCountry();
			if (senderCountry.isEmpty()) {
				Fail_Message("Sender's Country is not displayed");
			} else {
				Pass_Message("Sender's country is displayed as: " + senderCountry);
			}
			String receiverContactName = casedetailpage.getreceiverContact();
			if (receiverContactName.isEmpty()) {
				Fail_Message("Receiver's Contact Name is not displayed");
			} else {
				Pass_Message("Receiver's Contact Name is displayed as: " + receiverContactName);
			}
			String receiverCountry = casedetailpage.getRcReceiverCountry();
			if (receiverCountry.isEmpty()) {
				Fail_Message("Receiver's Country is not displayed");
			} else {
				Pass_Message("Receiver's country is displayed as: " + receiverCountry);
			}
			casedetailpage.clickRelatedTab();
			connectivity.CloseTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Sender & Receiver Information Page is not validated successfully");
		}
	}

	// Case details for PE
	public void casedetails_caseebs_PE() {
		// Case Details Page validations
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		HomePage homepage = new HomePage(getDriver());
		//ProactiveExceptionsPage proactivepage = new ProactiveExceptionsPage(getDriver());
		try {
			CCD_Connectivity connectivity = new CCD_Connectivity();
			String peCon = "20166914";// "20168336";
			connectivity.CloseTab();
			homepage.clickDropDownNavigationMenu();
			homepage.clickCases();
			// proactivepage.clickRecentlyViewedItems();
			// proactivepage.peSearchInput("Recently Viewed Cases");
			homepage.caseSearch(peCon);
			homepage.caseSearch(peCon);
			uiTestHelper.propagateException();
			// homepage.clickCaseColumn();
			homepage.clickSearchedCaseNum(peCon);
			casedetailpage.clickCaseDetails();
			uiTestHelper.propagateException();
			String caseOwner = casedetailpage.getcaseOwnerValue();
			if (caseOwner.isEmpty()) {
				Fail_Message("Case owner is not displayed correctly");
			} else {
				Pass_Message("Case owner displayed as: " + caseOwner);
			}
			String caseOwnerCountry = casedetailpage.getcaseOwnerCountry();
			if (caseOwnerCountry.isEmpty()) {
				Fail_Message("Case owner Country is not displayed correctly");
			} else {
				Pass_Message("Case owner Country displayed as: " + caseOwnerCountry);
			}
			String cmodId = casedetailpage.getcmodID();
			if (cmodId.isEmpty()) {
				Fail_Message("CMOD is blank");
			} else {
				Pass_Message("CMOD id is displayed as : " + cmodId);
			}
			String shipmentdirection = casedetailpage.getShipmentDirection();
			if (shipmentdirection.isEmpty()) {
				Fail_Message("Shipment Direction is blank");
			} else {
				Pass_Message("Shipment Direction id is displayed as : " + shipmentdirection);
			}
			String custdialOwner = casedetailpage.getCustodialOwner();
			if (custdialOwner.isEmpty()) {
				Fail_Message("Custodial Owner is blank");
			} else {
				Pass_Message("Custodial Owner id is displayed as : " + custdialOwner);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Details Page is not validated successfully");
		}
		// ******************************************************************
		// Case Information Page validations
		try {
			casedetailpage.clickCaseInformation();
			String casetype = casedetailpage.getproActivecaseType();
			System.out.println("CaseType" + casetype);
			if (!casetype.isEmpty()) {
				Pass_Message("Case Type displayed is 'Proactive case' " + casetype);
			} else {
				Fail_Message("Case Type is not displayed correctly");
			}
			String casestatus=casedetailpage.getCaseStatus();
			if (!casestatus.isEmpty())
			{
				Pass_Message("Case status displayed is 'Created' "+casestatus);
			}
			else
			{
				Fail_Message("Case status is not displayed correctly");
			}
			String caseGroupt = casedetailpage.getcaseGroup();
			if (!caseGroupt.isEmpty()) {
				Pass_Message("Case Group displayed is correctly as: " + caseGroupt);
			} else {
				Fail_Message("Case Group is not displayed correctly");
			}
			String caseReason = casedetailpage.getcaseReason();
			if (!caseReason.isEmpty()) {
				Pass_Message("Case Reason displayed correctly as: " + caseReason);
			} else {
				Fail_Message("Case Reason is not displayed correctly");
			}
			String cause = casedetailpage.getcaseCause();
			if (!cause.isEmpty()) {
				Pass_Message("Cause displayed correctly as: " + cause);
			} else {
				Fail_Message("Cause is not displayed correctly");
			}
			String sendContName = casedetailpage.getcontactName();
			if (!sendContName.isEmpty()) {
				Pass_Message("Sender Contact Name displayed correctly as: " + sendContName);
			} else {
				Fail_Message("Sender Contact Name is not displayed correctly");
			}
			String sendPhone = casedetailpage.getcontactPhone();
			if (!sendPhone.isEmpty()) {
				Pass_Message("Sender Contact Phone displayed correctly as: " + sendPhone);
			} else {
				Fail_Message("Sender Contact Phone is not displayed correctly");
			}
			String email = casedetailpage.getContactEmail();
			if (!email.isEmpty()) {
				Pass_Message("Sender Contact email displayed correctly as: " + email);
			}
			// else
			// {
			// Fail_Message("Sender Contact email is not displayed correctly");
			// }
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case Information Page is not validated successfully");
		}
		// ***********************************************************
		// Consignment Information Page validations
		try {
			casedetailpage.clickConsignmentInfo();
			String consignmentNumner = casedetailpage.getconsignMentNo();
			if (consignmentNumner.isEmpty()) {
				Fail_Message("Consignment Number is not displayed");
			} else {
				Pass_Message("Consignment Number is displayed correctly as: " + consignmentNumner);
			}
			String collDepot = casedetailpage.getCollectionDepot();
			if (collDepot.isEmpty()) {
				Fail_Message("Collection Depot is not displayed");
			} else {
				Pass_Message("Collection Depot is displayed correctly as: " + collDepot);
			}
			String destDepot = casedetailpage.getDestinationDepot();
			if (destDepot.isEmpty()) {
				Fail_Message("Destination Depot is not displayed");
			} else {
				Pass_Message("Destination Depot is displayed correctly as: " + destDepot);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Consignment Information Page is not validated successfully");
		}
		// *****************************************************
		// Sender & Receiver Information Page validations
		try {
			casedetailpage.clickPEMoreTab();
			casedetailpage.clickSenderAndReceiverInfo();
			String SenderAccNo = casedetailpage.getPEsenderAccountNo();
			if (!SenderAccNo.isEmpty()) {
				Pass_Message("Sender Account Number is displayed as: " + SenderAccNo);
			}
			// else
			// {
			//
			// Fail_Message("Sender Account Number is not displayed");
			// }
			String SenderContactName = casedetailpage.getsenderContact();
			if (SenderContactName.isEmpty()) {
				Fail_Message("Sender Contact Name is not displayed");
			} else {
				Pass_Message("Sender's Contact Name is displayed as: " + SenderContactName);
			}
			String peSenderCountry = casedetailpage.getPeSenderCountry();
			if (peSenderCountry.isEmpty()) {
				Fail_Message("Sender's Country is not displayed");
			} else {
				Pass_Message("Sender's country is displayed as: " + peSenderCountry);
			}
			String receiverAccount = casedetailpage.getPEreceiverAccountNo();
			if (!receiverAccount.isEmpty()) {
				Pass_Message("Receiver's Account Number is displayed as: " + SenderAccNo);
			}
			// else
			// {
			// Fail_Message("Receiver's Account Number is not displayed");
			// }
			String receiverContactName = casedetailpage.getreceiverContact();
			if (receiverContactName.isEmpty()) {
				Fail_Message("Receiver's Contact Name is not displayed");
			} else {
				Pass_Message("Receiver's Contact Name is displayed as: " + receiverContactName);
			}

			String receiverCountry = casedetailpage.getreceiverCountry();
			if (receiverCountry.isEmpty()) {
				Fail_Message("Receiver's Country is not displayed");
			} else {
				Pass_Message("Receiver's country is displayed as: " + receiverCountry);
			}
			casedetailpage.clickRelatedTab();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Sender & Receiver Information Page is not validated successfully");
		}
	}

	public void getvalues() {
		myCon = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CMOD_ReactiveCaseFlow.Key_Array[7]);
		try {

			System.out.println("Con in FF Updated" + Retrive);
		} catch (Exception e) {
			System.out.println("Failed");
		}
	}

	public void createCase_PE() {
		try {
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			CreateCasePage ccpage = new CreateCasePage(getDriver());
			consignmentPage.clickcreatecasebtn();
			ccpage.selectRecordType("Pro-Active Case");
			ccpage.clickCCLocation();
			uiTestHelper.propagateException();
			ccpage.clickOrigin();
			ccpage.setReason("Undeliverable");
			ccpage.setCause("Insufficient Address");
			ccpage.setFirstName("Test");
			ccpage.setLastName("Test");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			ccpage.clickCaseAssign();
			Pass_Message("Case creation details entered successfully");
			uiTestHelper.propagateException();
			ccpage.clickCaseCreatebtn();
			ccpage.createdStatus();
			Pass_Message("Reactive case is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case creation test case is failed");
		}
	}

	/*
	 * B-592940 TC01 to 03
	 */
	public void b592940_CaseSummaryTab_UpdatedConsignmentStatus_Description() {
		// Case Details Page validations
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CCD_Connectivity connectivity = new CCD_Connectivity();
		CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
		try {
			String Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
					CMOD_ReactiveCaseFlow.Key_Array[8]);
			System.out.println(Retrive);

			connectivity.CloseTab();
			searchConsignmentOption("324681840");
			reuse.viewOrCreateCase();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			casedetailpage.clickCaseSummary();
			boolean isUpdateConsStatusDesc = casedetailpage.isUpdatedConStatusDescDisplayed();
			if (isUpdateConsStatusDesc) {
				Pass_Message(
						"Case Summary Tab: Updated Consignment Status Description field is displayed successfully");
			} else {
				Fail_Message("Case Summary Tab: Updated Consignment Status Description field is not displayed");
			}
			Assert.assertTrue(isUpdateConsStatusDesc);
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: case summary: update consignment status field verification failed");
		}
	}

	public void b703296_RootCauseAnalysis_NonMmandatoryChange_and_Enhancement(String caseType) {
		CaseDetailsPage casedetailpage = new CaseDetailsPage(getDriver());
		CMOD_FunctionalFlow_Updated ffUpdated = new CMOD_FunctionalFlow_Updated();
		CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
		try {
			if (caseType.contains("re_case")) {
				Retrive = Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY",
						CMOD_ReactiveCaseFlow.Key_Array[7]);
			} else {
				Retrive = Database_Connection.retrieveTestData("CONSIGNMENT", "ACM", "KEY",
						CMOD_PECaseFlow.Key_Array[8]);
			}
			Retrive = "324682120";
			ACM_Connectivity.CloseTab();
			ffUpdated.searchConsignmentOption(Retrive);
			reuse.viewOrCreateCase();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			casedetailpage.clickcallbackActivity();
			if (!casedetailpage.iscallbackcheckbox()) {
				casedetailpage.clickcallbackcheckbox();
			}
			casedetailpage.clickcallbackActivitysave();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			casedetailpage.clickCloseCaseTab();
			
			casedetailpage.setComments("Closed Case");
			casedetailpage.clickCloseCaseSave();
			uiTestHelper.propagateException();
			getDriver().navigate().refresh();
			String status = casedetailpage.getCaseStatus();
			System.out.println("status: " + status);
			boolean isStatus = status.equalsIgnoreCase("Manual-Closed");
			if (isStatus) {
				Pass_Message("Case is closed Successfully");
			} else {
				Fail_Message("Case is not closed");
			}
			//Assert.assertTrue(isStatus, "Case is closed Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case close operation failed");
		}
	}
	/*
	 * this covers TC01, TC02, TC04
	 */
	public void b716112_TC01_PiorityIndicatorInCases(String consignmentNo,String caseLocation, String caseType, String reason, String cause, String priority,boolean assignToMe) {
		ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
		CMOD_FF_Reusable reuse = new CMOD_FF_Reusable();
		try {
			String Retrieve =Database_Connection.retrieveTestData("CONNORT","ACM","KEY",CMOD_ReactiveCaseFlow.Key_Array[7]);
			Retrieve = "309869254";	
			ACM_Connectivity.CloseTab();
			
			searchConsignmentOption(Retrieve);
			try {
				if (consignmentPage.verifyViewCase() == true) {
					ACM_Connectivity.CCD_ViewCase();
				}
			} catch (Exception e) {
				reuse.createCase_Customized(caseLocation,caseType, reason, cause, priority,assignToMe);
			}
			
			//reuse.createCaseWithPriority(caseType, reason, cause, priority);
			priorityIndicatorCaseValidation(priority);
			
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case creation with priority is failed");
		}
		
	}
	public void priorityIndicatorCaseValidation(String priority) {
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			uiTestHelper.propagateException();
			boolean isstatus=ccpage.getCreatedStatus().contains("Created");
			if(isstatus) {
				Pass_Message("Reactive case is created successfully");
			}else {
				Fail_Message("Reactive case is not created");
			}
			Assert.assertTrue("Reactive case is created successfully", isstatus);	
			
			boolean ispriorityvalue=ccpage.getPriorityValue().equalsIgnoreCase(priority);
			if(ispriorityvalue) {
				Pass_Message("Prioirty Indicator as '" + priority + "' is displayed in case status tab");
			}else {
				Fail_Message("Prioirty indicator displayed incorrect in case status tab");
			}
			Assert.assertTrue("Prioirty Indicator as '" + priority + "' is displayed in case status tab ",ispriorityvalue);
			
			ccpage.selectCaseDetailsTab();
			boolean priorityIndicator=ccpage.getPriorityIndicatorCaseDetailsTab().equalsIgnoreCase(priority);
			if(priorityIndicator) {
				Pass_Message("Prioirty Indicator as '" + priority + "' is displayed");
			}else {
				Fail_Message("Prioirty indicator displayed incorrect in case details");
			}
			Assert.assertTrue("Prioirty Indicator as '" + priority + "' is displayed",priorityIndicator);
		}catch(Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case validation for priority indicator failed");
		}
	}
	public void b716112_TC02_PiorityIndicatorListVerification(String consignmentNo, String caseType, String reason, String cause, String priority) {
		ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
		CreateCasePage ccpage = new CreateCasePage(getDriver());
		try {
			String Retrieve =Database_Connection.retrieveTestData("CONNORT","ACM","KEY",CMOD_ReactiveCaseFlow.Key_Array[7]);
			Retrieve = "309869254";	
			ACM_Connectivity.CloseTab();
			
			searchConsignmentOption(Retrieve);
			
			consignmentPage.clickcreatecasebtn();
			
			ccpage.selectRecordType(caseType);
			
			ccpage.clickCCLocation();
			uiTestHelper.propagateException();
			ccpage.clickOrigin();
			
			ccpage.setReason(reason);
			ccpage.setCause(cause);

			ccpage.setFirstName("TestFirst");
			ccpage.setLastName("TestLast");
			ccpage.setphone("0123456789");
			ccpage.setEmail("test@fedex.com");
			//ccpage.clickPriorityIndicatorDropdown();
			//validate priority indicator lis items
			String[] pi_items=ccpage.getPriorityIndicatorListItmes();
			String listItems="";
			for(int i=0;i<pi_items.length;i++) {
				listItems+=pi_items[i]+" | ";
			}
			
			String[] listitemstocompare= {"Aircraft on ground (AOG)","Bids/Proposal",
					"Display booths/anything associated with the display booth","Payroll",
					"Plant/vehicle down","Wedding Items","Animal semen","Financial Data",
					"Personal Data","Bill of lading","Legal document","Priority timed option (PTO)",
					"Expedite in 'Case details'"};
			
			String itemtovalidate="";
			boolean islistitem=false;
			int itemmatched=0;
			for(int i=0;i<listitemstocompare.length;i++) {
				itemtovalidate=listitemstocompare[i];
				islistitem=listItems.contains(itemtovalidate);
				if(islistitem) {
					Pass_Message("Prioirty Indicator as '" + itemtovalidate + "' is present in list");
					itemmatched++;
				}else {
					Fail_Message("Prioirty Indicator as '" + itemtovalidate + "' is not present in list");
				}				
				//Assert.assertTrue("Prioirty Indicator as '" + itemtovalidate + "' is present in list",islistitem);										
			}
			boolean areallitemspresent=itemmatched<(listitemstocompare.length-1);
			if(areallitemspresent) {
				Pass_Message("All options present in 'Prioirty Indicator' list");
			}else {
				Fail_Message("All options are not present in 'Prioirty Indicator' list");
			}
			Assert.assertTrue("All options present in 'Prioirty Indicator' list",areallitemspresent);										
			
			//ccpage.setPriorityIndicator(priority);
			
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Exception: Case creation with priority is failed");
		}
	}
}