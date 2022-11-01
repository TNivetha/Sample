package com.tnt.cmod;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.tnt.ccd.CCD_Connectivity;
import com.tnt.ccd.CCD_CMOD_SmokeTest;
import com.tnt.ccdobjects.ConsignmentPage;
import com.tnt.commonutilities.CMODConstants;
import com.tnt.commonutilities.Database_Connection;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
public class CMOD_TrackAndTrace_Search extends Driver {
	long elapsedTime = 0;
	long startTime = 0;
	CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@BeforeClass(alwaysRun=true)
	public void beforeClass() {
		String Keys = Test_Initializer.BeforeClass(this.getClass().getSimpleName());
		if ((!Keys.isEmpty()) || (!(Keys == null))) {
			Key_Array = Keys.split(",");
			for (int i = 0; i < Key_Array.length; i++) {
				System.out.println(Key_Array[i]);
			}
		}
	}
	@BeforeClass(alwaysRun=true)
	public void login() {
		CMOD_FF_Reusable ACM_FF_Reusable = new CMOD_FF_Reusable();
		ACM_FF_Reusable.support_Login();
		CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		softAssertion.assertAll();
	}
	@BeforeMethod(alwaysRun=true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}
	@AfterMethod(alwaysRun=true)
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		extent.flush();
	}
	/*
	 * Pre-requisite for Track and Trace suite execution: 1. Create a consignment
	 * with Customer Reference number as 99999 from ODE. 2. Update Alternate
	 * consignmnet number in DB as 12345 in any consignment 3. Update col and del
	 * date 4. Update Invoice Con 5. Update PE con 6. Update Case creation con in
	 * Frontline login in database
	 *
	 */
	@Test(groups= {"ttSearch","CustReferenceColDate"})
	public void TC1_TTSearch_CustReferenceColDate() {
			// Verify collection date and customer reference
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_CustReference();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","newSearchBtn"})
	public void TC2_TTSearch_NewSearchBtn() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_NewSearchBtn();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","CustReferenceDelDate"})
	public void TC3_TTSearch_CustReferenceDelDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_CustReferenceDelDate();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","ClearBtn"})
	public void TC4_TTSearch_TTSearch_BackBtn() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_ClearBtn();
			softAssertion.assertAll();
	}
	// Verify alternate consignment number and collection date
	@Test(groups= {"ttSearch","AltConNumColDate"})
	public void TC5_TTSearch_AltConNumColDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_AltConNumColDate();
			softAssertion.assertAll();
	}
	// Verify delivery date and alternate consginment number
	@Test(groups= {"ttSearch","AltConNumDelDate"})
	public void TC6_TTSearch_AltConNumDelDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_AltConNumDelDate();
			softAssertion.assertAll();
	}
	// Verify sender account number, country name and collection date
	@Test(groups= {"ttSearch","sendAcctAndCountryColDate"})
	public void TC7_TTSearch_SendAcctAndCountryColDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_SendAcctAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify delivery date ,sender account name and country
	@Test(groups= {"ttSearch","SendAcctAndCountryDelDate"})
	public void TC8_TTSearch_SendAcctAndCountryDelDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_SendAcctAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify Receiver account Name,country and Collection date
	@Test(groups= {"ttSearch","RecAcctAndCountryColDate"})
	public void TC9_TTSearch_RecAcctAndCountryColDate() {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.TTSearch_RecAcctAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify Receiver account number and country with delivery date
	@Test(groups= {"ttSearch","RecAcctAndCountryDelvDate"})
	public void TC10_TTSearch_RecAcctAndCountryDelDate() {
			ACM_Connectivity.TTSearch_RecAcctAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify collection date, sender company name and country
	@Test(groups= {"ttSearch","SenCompAndCountryColDate"})
	public void TC11_TTSearch_SenCompAndCountryColDate() {
			ACM_Connectivity.TTSearch_SenCompAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify delivery data,sender company name and country
	@Test(groups= {"ttSearch","SenCompAndCountryDelvDate"})
	public void TC12_TTSearch_SenCompAndCountryDelDate() {
			ACM_Connectivity.TTSearch_SenCompAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify collection date, sender company name and country
	@Test(groups= {"ttSearch","SenCompAndCountryColDate"})
	public void TC13_TTSearch_SenCompAndCountryColDate() {
			ACM_Connectivity.TTSearch_SenCompAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify receiver company name and Country with delivery date
	@Test(groups= {"ttSearch","RecCompAndCountryDelvDate"})
	public void TC14_TTSearch_RecCompAndCountryDelDate() {
			ACM_Connectivity.TTSearch_RecCompAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify collection date,sender company post code and country
	@Test(groups= {"ttSearch","SenPostCodeAndCountryColDate"})
	public void TC_15_TTSearch_SenPostCodeAndCountryColDate() {
			ACM_Connectivity.TTSearch_SenPostCodeAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify sender postal code,country and delivery date
	@Test(groups= {"ttSearch","SenPostCodeAndCountryDelvDate"})
	public void TC_16_TTSearch_SenPostCodeAndCountryDelDate() {
			ACM_Connectivity.TTSearch_SenPostCodeAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify recevier post code ,country and collection date
	@Test(groups= {"ttSearch","RecPostCodeAndCountryColDate"})
	public void TC_17_TTSearch_RecPostCodeAndCountryColDate() {
			ACM_Connectivity.TTSearch_RecPostCodeAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify Delivery date,Receiver Postcode and Country
	@Test(groups= {"ttSearch","recPostCodeAndCountryDelDate"})
	public void TC_18_TTSearch_RecPostCodeAndCountryDelDate() {
			ACM_Connectivity.TTSearch_RecPostCodeAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify sender company Town,country and collection date
	@Test(groups= {"ttSearch","senTownAndCountryColDate"})
	public void TC19_TTSearch_SenTownAndCountryColDate() {
			ACM_Connectivity.TTSearch_SenTownAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify Sender town name and country with delivery date
	@Test(groups= {"ttSearch","senTownAndCountryDelDate"})
	public void TC20_TTSearch_SenTownAndCountryDelDate() {
			ACM_Connectivity.TTSearch_SenTownAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify Receiver town name and country with Collection date
	@Test(groups= {"ttSearch","recTownAndCountryColDate"})
	public void TC21_TTSearch_RecTownAndCountryColDate() {
			ACM_Connectivity.TTSearch_RecTownAndCountryColDate();
			softAssertion.assertAll();
	}
	// Verify Recevier town name and country with delivery date
	@Test(groups= {"ttSearch","recTownAndCountryDelDate"})
	public void TC22_TTSearch_RecTownAndCountryDelDate() {
			ACM_Connectivity.TTSearch_RecTownAndCountryDelDate();
			softAssertion.assertAll();
	}
	// Verify sender and receiver depot and collection date
	@Test(groups= {"ttSearch","SenAndRecDepotColDate"})
	public void TC23_TTSearch_SenAndRecDepotColDate() {
			ACM_Connectivity.TTSearch_SenAndRecDepotColDate();
			softAssertion.assertAll();
	}
	// Verify sender and receiver depot and delivery date
	@Test(groups= {"ttSearch","senAndRecDepotDelvDate"})
	public void TC24_TTSearch_SenAndRecDepotDelDate() {
			ACM_Connectivity.TTSearch_SenAndRecDepotDelDate();
			softAssertion.assertAll();
	}
	// enter 9 digit of consignnment
	@Test(groups= {"ttSearch","9DigitCon"})
	public void TC25_TTSearch_9DigitCon() {
			ACM_Connectivity.TTSearch_9DigitCon();
			softAssertion.assertAll();
	}
	// View consignment number at top
	@Test(groups= {"ttSearch","viewConatTop"})
	public void TC26_TTSearch_ViewConatTop() {
			ACM_Connectivity.TTSearch_ViewConNumAtTop();
			softAssertion.assertAll();
	}
	// Verify page
	@Test(groups= {"ttSearch","CreateCaseBtn"})
	public void TC27_TTSearch_ConDtlsPage_VerifySections() {
			ACM_Connectivity.TTSearch_ConDtlsPage_VerifySections();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","ConDtlsPage_VerifySections"})
	public void TC28_TTSearch_CreateCaseBtn() {
		try {
			CCD_Connectivity acmConnectivity = new CCD_Connectivity();
			CMOD_FF_Reusable reusableFunctions=new CMOD_FF_Reusable();
			String consignmentNo=Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CCD_CMOD_SmokeTest.Key_Array[3]);
			consignmentNo= "324646932";//TODO add test data into test DB
			acmConnectivity.CloseTab();
			acmConnectivity.TrackandTraceConsignmentSearch(consignmentNo);
			reusableFunctions.CCD_CreateCase();
			acmConnectivity.CloseTab();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Create case button is not working");
		}
	}
	// Verify view button
	@Test(groups= {"ttSearch","viewCaseBtn"})
	public void TC29_TTSearch_ViewCaseBtn() {
			ACM_Connectivity.TrackandTraceCCDSearch();
			ACM_Connectivity.CCD_ViewCase();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","6DigitsCon"})
	public void TC30_TTSearch_6DigitsCon() {
			ACM_Connectivity.TTSearch_6DigitsCon();
			ACM_Connectivity.TTSearch_NewSearchBtn();
	}
	// Verify all coloumns present in consignment list
	@Test(groups= {"ttSearch","SearchListColSeq"})
	public void TC31_TTSearch_SearchListColSeq() {
			ACM_Connectivity.TTSearch_SearchListColSeq();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","ConDtlfrmDropdown"})
	public void TC32_TTSearch_ConDtlfrmDropdown() {
			ACM_Connectivity.TTSearch_ConDtlfrmDropdown();
			softAssertion.assertAll();
	}
	// Verify the consignemnet Page
	@Test(groups= {"ttSearch","VerifySections_ConDtlsPage"})
	public void TC33_TTSearch_VerifySections_ConDtlsPage() {
			ACM_Connectivity.TTSearch_ConDtlsPage_VerifySections();
			softAssertion.assertAll();
	}
	// Verify Buttons on Consignment Page
	@Test(groups= {"ttSearch","BtnOnConDtlPage"})
	public void TC34_TTSearch_BtnOnConDtlPage() throws Exception
	{
			ACM_Connectivity.TTSearch_ConDtlBtn();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","ConHistFrmDropdown"})
	public void TC35_TTSearch_ConHistFrmDropdown() throws Exception
	{
			ACM_Connectivity.TTSearch_ConHistFrmDropdown();
			ACM_Connectivity.TrackandTraceNewSearchBtn();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","ConHistDtlsPage_VerifySections"})
	public void TC36_TTSearch_ConHistDtlsPage_VerifySections() throws Exception
	{
			ACM_Connectivity.TTSearch_ConHistDtlsPage_VerifySections();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","BtnOnConHistPage"})
	public void TC37_TTSearch_BtnOnConHistPage() throws Exception
	{
			ACM_Connectivity.TTSearch_ConHistBtn();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","LateststatusCodeatTop"})
	public void TC38_TTSearch_Verify_LateststatusCodeatTop() {
			ACM_Connectivity.TTSearch_Verify_LateststatusCodeatTop();
			softAssertion.assertAll();
	}
	// TODO consignment Additional Information validation
	@Test(groups= {"ttSearch","ConHist_StatusCodeDtls"})
	public void TC39_TTSearch_ConHist_StatusCodeDtls() throws Exception
	{
			ACM_Connectivity.TTSearch_ConHist_StatusCodeDtls();
			softAssertion.assertAll();
	}
	// Verify Consignment piece by dropdown
	@Test(groups= {"ttSearch","ConPiecesFromDropdown"})
	public void TC40_TTSearch_ConPiecesFromDropdown() {
			ACM_Connectivity.TTSearch_ConPiecesFromDropdown();
			softAssertion.assertAll();
	}
	// Verify consigment Pieces button
	@Test(groups= {"ttSearch","ConPiecesBtn"})
	public void TC41_TTSearch_BtnOnConPcsPage() {
			ACM_Connectivity.TTSearch_ConPiecesBtn();
			softAssertion.assertAll();
	}
	// Verify Clear and New serch button
	@Test(groups= {"ttSearch","Clear_And_NewSearch_Btns"})
	public void TC42_TTSearch_Verify_Clear_And_NewSearch_Btns() throws Exception
	{
			ACM_Connectivity.TTSearch_Verify_ClearAndNewSearchBtn();
			softAssertion.assertAll();
		}
	@Test(groups= {"ttSearch","InvfromConDtlPage"})
	public void TC43_TTSearch_InvfromConDtlPage() throws Exception
	{
			ACM_Connectivity.TTSearch_InvfromConDtlPage();
			ACM_Connectivity.CloseTab();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","DeliveryAreaInfo"})
	public void TC44_TTSearch_CCD_DeliveryAreaInfo() throws Exception
	{
		try {
			CCD_Connectivity ACM_Connectivity = new CCD_Connectivity();
			ACM_Connectivity.deliveryAreaInfoService_in_Consignment_Details();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Delivery area information have not been displayed");
		}
	}
	@Test(groups= {"ttSearch","CreateCaseErrorforPE"})
	public void TC45_TTSearch_CreateCaseErrorforPE() throws Exception
	{
			ACM_Connectivity.TTSearch_CreateCaseErrorforPE();
			softAssertion.assertAll();
	}
	@Test(groups= {"ttSearch","CreateUnallocatedCase"})
	public void TC46_TTSearch_CreateUnallocatedCase() throws Exception
	{
			ACM_Connectivity.TTSearch_CreateUnallocatedCase();
			softAssertion.assertAll();
	}
	@Test(groups= {"viewConsignment","caseOnShipment"})
	public void TC47_View_Consignment_To_Validate_Cases_On_Shipment() {
		// B-400176 Test Cases
		try {
			CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
			ccdConnectivity.TrackandTraceConsignmentSearch("324646464");
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			consignmentPage.clickviewcasebtn();
			String areColumnsPresent = consignmentPage.getCasesShipmentText();
			softAssertion.assertEquals(areColumnsPresent, "Cases on this shipment");
			softAssertion.assertAll();
			Pass_Message("The pop-up has entry rows");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Cases On Shipment shows no data");
		}
	}
	@Test(groups= {"viewConsignment","validateColmns"})
	public void TC48_View_Consignment_To_Validate_Columns() {
		// B-400176 Test Cases
		try {
			CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
			ccdConnectivity.TrackandTraceConsignmentSearch("324646464");
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			consignmentPage.clickviewcasebtn();
			String areColumnsPresent = consignmentPage.caseNumberColValidation();
			softAssertion.assertEquals(areColumnsPresent, "Case Number");
			softAssertion.assertAll();
			Pass_Message("Cases on Shipment has popped up and is present on the screen");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("If there are no columns to view - error");
		}
	}
	@Test(groups= {"viewConsignment","eyeIcon"})
	public void TC49_View_Consignment_And_Click_Eye_Icon() {
		// B-400176 Test Cases
		try {
			CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
			ccdConnectivity.TrackandTraceConsignmentSearch("324646464");
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			consignmentPage.clickviewcasebtn();
			consignmentPage.clickEyeIconAndScrollRight();
			String isConsignmentNumberPresent = consignmentPage.consignmentNumberValidation("324646464");
			softAssertion.assertEquals(isConsignmentNumberPresent, "324646464");
			softAssertion.assertAll();
			Pass_Message("Consignment Number is there, case has been opened");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Case has not opened, no consignment number to validate");
		}
	}
	@Test(groups= {"viewConsignment","multipleCases"})
	public void TC50_View_Consignment_And_Validate_Shipment_Has_Multiple_Cases() {
		// B-400176 Test Cases
		try { // 324645440
			CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
			ccdConnectivity.TrackandTraceConsignmentSearch("324645440");
			ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
			consignmentPage.clickviewcasebtn();
			consignmentPage.checkCaseShipmentTableRows();
			Pass_Message("2 or more cases available in the table rows");
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("If no cases present - error");
		}
	}
	@Test(groups= {"viewConsignment","allCasesOpen"})
	public void View_Consignment_And_All_Cases_Should_Be_Open() throws Exception{
		//B-400176 Test Cases  
	try
	{
		//Search consignment first time and validate eye icon
		CCD_Connectivity ccdConnectivity = new CCD_Connectivity();
		ccdConnectivity.TrackandTraceConsignmentSearch("324645440");
		ConsignmentPage consignmentPage = new ConsignmentPage(getDriver());
		consignmentPage.clickviewcasebtn();
		consignmentPage.clickEyeIconAndScrollRight();
		String isConsignmentNumberPresent = consignmentPage.consignmentNumberValidation("324645440");
		softAssertion.assertEquals(isConsignmentNumberPresent, "324645440");
		softAssertion.assertAll();
		Pass_Message("Consignment Number is there, case has been opened");	
		
		//clear tabs and validate the second icon on the same search result
		CCD_Connectivity ACM_Connectivity= new CCD_Connectivity();
		ACM_Connectivity.CloseTab();
		
		getDriver().navigate().refresh();

		ccdConnectivity.TrackandTraceConsignmentSearch("324645440");
		consignmentPage.clickviewcasebtn();
		consignmentPage.clicksecondEyeIconAndScrollRight();
		String isConsignmentNumberTwoPresent = consignmentPage.consignmentNumberValidation("324645440");
		softAssertion.assertEquals(isConsignmentNumberTwoPresent, "324645440");
		softAssertion.assertAll();
		Pass_Message("Consignment Number is there, case has been opened");	
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Fail_Message("If consginment ID is not present");
		}
  }
	public void TC51_View_Consignment_And_All_Cases_Should_Be_Open() {
		// B-400176 Test Cases
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.View_Consignment_And_All_Cases_Should_Be_Open();	
	}
	@Test(groups= {"viewConsignment","ReassignToDestination"})
	public void TC48_TTSearch_ReassignToDestination() {
		try {
			CCD_Connectivity acmConnectivity = new CCD_Connectivity();
			CMOD_FF_Reusable reusableFunctions=new CMOD_FF_Reusable();
			String consignmentNo=Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CCD_CMOD_SmokeTest.Key_Array[3]);
			consignmentNo= "226096253";//TODO add test data into test DB (GB to UK)
			acmConnectivity.CloseTab();
			acmConnectivity.TrackandTraceConsignmentSearch(consignmentNo);
			reusableFunctions.CCD_CreateCase();
			// reusableFunctions.validateCaseOwner();
			reusableFunctions.validateReassign();
			reusableFunctions.selectReassignTo(CMODConstants.REASSIGN_TO_DESTINATION);
			acmConnectivity.CloseTab();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reassign case to Destination functionality is not working");
		}
	}
	@Test(groups= {"viewConsignment","ReassignToOrigin"})
	public void TC48_TTSearch_ReassignToOrigin() {
		try {
			CCD_Connectivity acmConnectivity = new CCD_Connectivity();
			CMOD_FF_Reusable reusableFunctions=new CMOD_FF_Reusable();
			String consignmentNo=Database_Connection.retrieveTestData("CONNORT", "ACM", "KEY", CCD_CMOD_SmokeTest.Key_Array[3]);
			consignmentNo= "226096264";//TODO add test data into test DB (GB to UK)
			acmConnectivity.CloseTab();
			acmConnectivity.TrackandTraceConsignmentSearch(consignmentNo);
			reusableFunctions.CCD_CreateCase();
			// reusableFunctions.validateCaseOwner();
			reusableFunctions.validateReassign();
			reusableFunctions.selectReassignTo(CMODConstants.REASSIGN_TO_ORIGIN);
			acmConnectivity.CloseTab();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reassign case to Destination functionality is not working");
		}
	}
	@Test(groups= {"createCase","causeDrpDown"})
	public void TC49_CreateUnallocatedCaseAndValidateCausesDropDown() {
		try {
			CMOD_TrackAndTrace_Reusable CmodTTR = new CMOD_TrackAndTrace_Reusable(getDriver());
			CmodTTR.checkUnallocattedCaseReason_with_cause_while_creatingCase();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			Fail_Message("Reassign case to Destination functionality is not working");
		}
	}
	@Test(groups= {"createCase","ReassignToOrigin"})
	public void createCaseThenValidateReAssignOriginOption() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.loginInSearchConsignmentAndCreateCase();
		cmodFFReusable.clickReAssignOnCasePageAndSelectOriginOption();
		cmodFFReusable.validateCaseOwnerOnCasePage("Nivetha Thirunavukarasu");
	}
	@Test(groups= {"createCase","ValidateReAssignDestinationOption"})
	public void createCaseThenValidateReAssignDestinationOption() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.loginInSearchConsignmentAndCreateCase();
		cmodFFReusable.clickReAssignOnCasePageAndSelectDestinationOption();
		cmodFFReusable.validateCaseOwnerOnCasePage("Nivetha Thirunavukarasu");
	}
	@Test(groups= {"createCase","MonitoringActivityStatus"})
	public void createCaseApplyMonitoringActivityStatus() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.loginInSearchConsignmentAndCreateCase();
		cmodFFReusable.applyMonitorActivityTab();
		cmodFFReusable.validateMonitorActivityInCaseInformation();
	}
	@Test(groups= {"createCase","applyClosedStatus"})
	public void createCaseApplyClosedStatus() {
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.loginInSearchConsignmentAndCreateCase();
		cmodFFReusable.applyCallBackOptionsToCase();
		cmodFFReusable.applyCloseCaseActions();
		cmodFFReusable.validateCaseIsManualClosedInCaseInformation();
	}
	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
