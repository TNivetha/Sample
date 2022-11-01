package com.tnt.ccd;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;

@Listeners(com.tnt.commonutilities.ListnerImplementation.class)
public class CCD_Booking extends Driver {

	long elapsedTime = 0;
	long startTime = 0;
	String environment=null;
	CCD_CI_Booking ciBooking = new CCD_CI_Booking();

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}	}

	@BeforeClass(alwaysRun = true)
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
	public void login(String env){	
			ciBooking.ci_login(env);		
		
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);

	}
	
	
	@Test(groups = { "booking", "SISP" })
	public void BK_SISP_Flow(){

		ciBooking.BK_SISP_Flow();

	}

	@Test(groups = { "booking", "SISP_EditBooking" })
	public void BK_SISP_EditBooking(){

		ciBooking.edit_SISP_Booking();

	}

	@Test(groups = { "booking", "SIRP" })
	public void BK_SIRP_Flow(){

		ciBooking.BK_SIRP_Flow();

	}

	@Test(groups = { "booking", "RIRP" })
	public void BK_RIRP_Flow(){

		ciBooking.BK_RIRP_Flow();

	}

	@Test(groups = { "booking", "callerinfo_buttons" })
	public void TC1_BK_US602384_Verify_CallerInfo_Sen_Rec_Btns(){

		ciBooking.BK_US602384_Verify_CallerInfo_Sen_Rec_Btns();

	}

	@Test(groups = { "booking", "payment_buttons" })
	public void TC2_BK_US602387_Verify_PayTerms_Sen_Rec_Btns(){

		ciBooking.BK_US602387_Verify_PayTerms_Sen_Rec_Btns();

	}

	@Test(groups = { "booking", "bad_debtor" })
	public void TC3_BK_US611155_Verify_BAD_Debtor_Status(){

		ciBooking.BK_US611155_Verify_BAD_Debtor_Status();

	}

	// Commanted as it is covered in the Bad Debtor Status testcase
	/*
	 * @Test(priority =5, enabled = false) public void
	 * TC4_BK_US611155_Verify_BAD_Debtor_Confirm_Booking(){
	 * ACM_ciBooking ciBooking = new ACM_ciBooking();
	 * ciBooking.BK_US611155_Verify_BAD_Debtor_Confirm_Booking(); ACM_Connectivity
	 * ACM_Connectivity= new ACM_Connectivity(); } catch(Exception e) { } }
	 */
	@Test(groups = { "booking", "validate_collectionAddress" })
	public void TC5_BK_US603130_ValidateBtn_CollAddress(){

		ciBooking.BK_US603130_ValidateBtn_CollAddress();

	}

	@Test(groups = { "booking", "update_collectionAddress" })
	public void TC6_BK_US603132_Update_CollAdd_ValidateBtn(){

		ciBooking.BK_US603132_Update_CollAdd_ValidateBtn();

	}

	@Test(groups = { "booking", "validate_UK_deliveryAddress" })
	public void TC7_BK_US603134_DelAdd_UK_ValidateBtn(){

		ciBooking.BK_US603134_DelAdd_UK_ValidateBtn();

	}

	@Test(groups = { "booking", "validate_NonUK_deliveryAddress" })
	public void TC8_BK_US603134_DelAdd_NonUK_ValidateBtn(){

		ciBooking.BK_US603134_DelAdd_NonUK_ValidateBtn();

	}

	@Test(groups = { "booking", "update_collectionDetails" })
	public void TC9_BK_US603134_Update_CollectionDetails(){

		ciBooking.BK_US603134_Update_CollectionDetails();

	}

	@Test(groups = { "booking", "update_goodsDescription", "goodsInformation" })
	public void TC10_BK_US603138_Update_GoodsDesc(){

		ciBooking.BK_US603138_Update_GoodsDesc();

	}

	@Test(groups = { "booking", "goodsDefaultCurrency", "goodsInformation" })
	public void TC11_BK_US603140_GoodsInfo_DefaultCCY(){

		ciBooking.BK_US603140_GoodsInfo_DefaultCCY();

	}

	@Test(groups = { "booking", "goodscurrency", "goodsInformation" })
	public void TC12_BK_US603140_GoodsInfo_DifferentCCY(){

		ciBooking.BK_US603140_GoodsInfo_DifferentCCY();

	}

	@Test(groups = { "booking", "goodsinfo_blankamount", "goodsInformation" })
	public void TC13_BK_US603140_GoodsInfo_BlankAmt(){

		ciBooking.BK_US603140_GoodsInfo_BlankAmt();

	}

	@Test(groups = { "booking", "dangerous_goods", "goodsInformation" })
	public void TC14_BK_US603142_GoodsInfo_DangGoods_NO(){

		ciBooking.BK_US603142_GoodsInfo_DangGoods_NO();

	}

	@Test(groups = { "booking", "goods_content", "goodsInformation" })
	public void TC15_BK_US603156_GoodsInfo_Contents_Docs(){

		ciBooking.BK_US603156_GoodsInfo_Contents_Docs();

	}

	@Test(groups = { "booking", "goods_content", "goodsInformation" })
	public void TC16_BK_US603156_GoodsInfo_Contents_NonDocs(){

		ciBooking.BK_US603156_GoodsInfo_Contents_NonDocs();

	}

	@Test(groups = { "booking", "goods_content", "goodsInformation" })
	public void TC17_BK_US603156_GoodsInfo_Contents_Mixed(){

		ciBooking.BK_US603156_GoodsInfo_Contents_Mixed();

	}

	@Test(groups = { "booking", "multiple_consignment", "consignmentInformation" })
	public void TC18_BK_603158_Book_MultipleItems(){

		ciBooking.BK_603158_Book_MultipleItems();

	}

	@Test(groups = { "booking", "envelope_with_Dims", "consignmentInformation" })
	public void TC19_BK_Type_Envelop_Dimns_Disappear(){

		ciBooking.BK_Type_Envelop_Dimns_Disappear();

	}

	@Test(groups = { "booking", "box", "consignmentInformation" })
	public void TC20_BK_Select_Type_Box(){

		ciBooking.BK_Select_Type_Box();

	}

	@Test(groups = { "booking", "pallet", "consignmentInformation" })
	public void TC21_BK_Select_Type_Pallet(){

		ciBooking.BK_Select_Type_Pallet();

	}

	@Test(groups = { "booking", "overlimit_consignments", "consignmentInformation" })
	public void TC22_BK_Book_MultipleItems_Overlimits(){

		ciBooking.BK_Book_MultipleItems_Overlimits();

	}

	@Test(groups = { "booking", "10_consignments_SameWeightsandDims", "consignmentInformation" })
	public void TC23_BK_603159_Book_10Items(){

		ciBooking.BK_603159_Book_10Items_SameWeightsAndDims();

	}

	@Test(groups = { "booking", "10_consignments_DiffWeightsandDims", "consignmentInformation" })
	public void TC24_BK_603160_Book_10Items_DiffWightsAndDims(){

		ciBooking.BK_603160_Book_10Items_DiffWightsAndDims();

	}

	@Test(groups = { "booking", "delete_consignments", "consignmentInformation" })
	public void TC25_BK_603160_Book_10Items_Reduce5Items(){

		ciBooking.BK_603159_Book_10Items_Reduce5Items();

	}

	@Test(groups = { "booking", "quantity100_consignments", "consignmentInformation" })
	public void TC26_BK_603160_Book_100Items_Error(){

		ciBooking.BK_603160_Book_100Items_Error();

	}

	@Test(groups = { "booking", "quantity0_consignments", "consignmentInformation" })
	public void TC27_BK_603160_Book_0Items_Error(){

		ciBooking.BK_603160_Book_0Items_Error();

	}

	@Test(groups = { "booking", "customerRefNumber", "goodsInformation" })
	public void TC28_BK_605035_Update_CustRefNum(){

		ciBooking.BK_605035_Update_CustRefNum();

	}

	@Test(groups = { "booking", "alphaNumber", "goodsInformation" })
	public void TC29_BK_605036_Update_CustRefNum_AlphaNum(){

		ciBooking.BK_605036_Update_CustRefNum_AlphaNum();

	}

	@Test(groups = { "booking", "update_collectionDate", "additionalInformation" })
	public void TC30_BK_605041_Update_CollDate_OnAdditionInfoPage(){

		ciBooking.BK_605041_Update_CollDate_OnAdditionInfoPage();

	}

	@Test(groups = { "booking", "update_collectionInstruction", "additionalInformation" })
	public void TC28_BK_US605028a_Update_Collection_Instruction(){

		ciBooking.BK_US605028a_Update_Collection_Instruction();

	}

	@Test(groups = { "booking", "update_deliveryInstruction", "additionalInformation" })
	public void BK_US605028d_Update_DelDriver_Instruction(){

		ciBooking.BK_US605028d_Update_DelDriver_Instruction();

	}

	@Test(groups = { "booking", "collectionInstruction_charlen", "additionalInformation" })
	public void BK_Update_CollDriver_Inst_charsLength(){

		ciBooking.BK_Update_CollDriver_Inst_charsLength();

	}

	@Test(groups = { "booking", "deliveryInstruction_charlen", "additionalInformation" })
	public void BK_US605028c_Update_DelDriver_Inst_Morethan50chars(){

		ciBooking.BK_US605028c_Update_DelDriver_Inst_Morethan50chars();

	}

	@Test(groups = { "booking", "service_validation", "additionalInformation" })
	public void BK_US605036_Validate_AvailableServicesDisplayed(){

		ciBooking.BK_US605036_Validate_AvailableServicesDisplayed();

	}

	@Test(groups = { "booking", "update_collectiontime", "additionalInformation" })
	public void BK_US605041_UpdateCollTime(){

		ciBooking.BK_US605041_UpdateCollTime();

	}

	@Test(groups = { "booking", "update_unavailabletime", "additionalInformation" })
	public void BK_US605041_Update_Close_Unavilable_Time(){

		ciBooking.BK_US605041_Update_Close_Unavilable_Time();

	}

	@Test(groups = { "booking", "validate_viewsummarybutton", "additionalInformation" })
	public void BK_US605048_Verify_ViewSummaryBtn(){

		ciBooking.BK_US605048_Verify_ViewSummaryBtn();

	}

	@Test(groups = { "booking", "reterivebooking" })
	public void BK_GS_RetriveBooking(){

		ciBooking.BK_GS_RetriveBooking();

	}

	@Test(groups = { "booking", "bookinglist_validation" })
	public void BK_BookingObject_ListView(){

		ciBooking.BK_BookingObject_ListView();

	}

	@Test(groups = { "booking", "cancel_booking" })
	public void BK_SISP_CancelBooking(){

		ciBooking.BK_CancelBooking_SISP();

	}

	@Test(groups = { "booking", "booking_summary" })
	public void BK_BookingSummary(){

		ciBooking.BK_BookingSummary();

	}

	@Test(groups = { "booking", "SISP_dangerous_goods" })
	public void BK_SISP_DG_Flow(){

		ciBooking.BK_SISP_DG_Flow();

	}

	@Test(groups = { "booking", "newcustomer_booking" })
	public void BK_NewCustomerBooking(){

		ciBooking.BK_NewCustomerBooking();

	}

	@Test(groups = { "booking", "auditpage_validation" })
	public void BK_AuditPage_Validation(){

		ciBooking.BK_BookingAuditPageValidation_EditBooking();

	}
	// <----Backlog Testcases---->

	@Test(groups = { "booking", "dangerous_goods" })
	public void BK_DG_US_391179_VerifyDG_Section(){

		ciBooking.BK_DG_VerifyDG_Section();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void BK_DG_US_391179_VerifyDG_Error(){

		ciBooking.BK_DG_VerifyDG_Error();

	}

	// Check to Sayali
	@Test(groups = { "booking", "dangerous_goods" })

	public void BK_DG_US_391179_VerifyDG_TypesofGoodsandUNNumber(){

		ciBooking.BK_DG_VerifyDG_TypesofGoodsandUNNumber();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void BK_DG_US_391179_VerifyFullyRegulatedDangerousGoods(){

		ciBooking.BK_DG_VerifyFullyRegulatedDangerousGoods();

	}

	@Test(groups = { "booking", "booking_remark" })
	public void BK_US_391196_VerifyBookingRemark(){

		ciBooking.BK_VerifyBookingRemark();

	}

	@Test(groups = { "booking", "product_option_price" })
	public void b521838_TC112_BK_product_Option_Price_Add(){

		ciBooking.bK_Product_Option_Price_Add_SISP();

	}

	@Test(groups = { "booking", "product_option_price" })
	public void b521838_TC113_BK_product_Option_Price_Remove(){

		ciBooking.bK_Product_Option_Price_Remove_SISP();

	}

	@Test(groups = { "booking", "bk_consignmentNote" })
	public void BK_US_404812_verifyConSignmentNoCreation_SISP(){

		ciBooking.BK_verifyConSignmentNoCreation_SISP();

	}

	@Test(groups = { "booking", "product_option_price" })
	public void b521838_TC114_BK_product_Option_Price_Multiple_Add(){

		ciBooking.bK_Product_Option_Price_Multiple_Add_SISP();

	}

	@Test(groups = { "booking", "edit_consignmentNote" })
	public void BK_US_404812_VerifyConsignmentNumber_Edit_SISPFlow(){

		ciBooking.BK_VerifyConsignmentNumber_Edit_SISPFlow();

	}

	@Test(groups = { "booking", "product_option_price" })
	public void b521838_TC116_BK_Edit_Product_Option_Price(){

		ciBooking.bK_Edit_Product_Option_Price();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void BK_US_391185_VerifyDGPricebreakdown_Edit_SISPFlow(){

		ciBooking.BK_VerifyDGPricebreakdown_Edit_SISPFlow();

	}

	@Test(groups = { "booking", "product_options" })
	public void BK_US_391201_VerifyAllProductOptions(){

		ciBooking.BK_VerifyAllProductOptions();

	}

	/*
	 * @Test(groups = { "booking", "product_options" }) public void
	 * BK_US_391201_VerifyProductOptions_CSImportServiceEnabled_RIRPFlow() throws
	 * Exception {
	 * 
	 * ciBooking.BK_VerifyProductOptions_CSImportServiceEnabled_RIRPFlow();
	 * 
	 * }
	 */

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC121_BK_DG_Reg_Special_Service_Indicator_Check(){

		ciBooking.bK_DG_REG_Special_Service_Ind_SISP();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC122_BK_DG_Add_Three_Non_Reg_DG_With_Approved_Account(){

		ciBooking.bK_DG_Add_Three_Non_Reg_DG_With_Approved_Account();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC118_BK_Non_Regulated_DG_and_Non_DG_Approved_Account_SISP_DG_Flow(){

		ciBooking.bK_Non_Reg_DG_and_Non_Approved_DG_Acct_SISP_DG_Flow();

	}

	@Test(groups = { "booking", "callerinfo_validation" })
	public void b527199_TC74_to_TC77_BK_Same_As_Caller_Info_Field_Validation(){

		ciBooking.bK_Same_As_Caller_Field_Validation_SISP();
		ciBooking.bK_Same_As_Caller_Field_Validation_SIRP();

	}

	@Test(groups = { "booking", "specialservice_booking" })
	public void BK_US_477641_CreateSpecialServiceBooking_SISPFlow(){

		ciBooking.BK_CreateSpecialServiceBooking_SISPFlow();

	}

	@Test(groups = { "booking", "callerinfo_validation" })
	public void b527199_TC78_BK_Same_As_Caller_Info_Field_Validation(){

		ciBooking.bK_Same_As_Caller_Field_Validation_RIRP();

	}

	@Test(groups = { "booking", "cash_amount" })
	public void BK_US_391235_UpdateCashAmount_Edit_SISPFlow(){

		ciBooking.BK_UpdateCashAmount_Edit_SISPFlow();

	}

	@Test(groups = { "booking", "specialservice_booking" })
	public void BK_US_477641_ValidateSpecialServiceDetails(){

		ciBooking.BK_ValidateSpecialServiceDetails();

	}

	@Test(groups = { "booking", "specialservice_booking" })
	public void BK_US_477641_ValidateSpecialServiceStatus(){

		ciBooking.BK_ValidateSpecialServiceStatus();

	}

	@Test(groups = { "booking", "specialservice_booking" })
	public void BK_US_477641_EditSpecialServiceBooking_Error(){

		ciBooking.BK_EditSpecialServiceBooking_Error();

	}

	@Test(groups = { "booking", "specialservice_booking" })
	public void BK_US_477641_VerifySpecialServiceContact_Address_MirroredFrom_Caller(){

		ciBooking.BK_VerifySpecialServiceContact_Address_MirroredFrom_Caller();

	}

	@Test(groups = { "booking", "cash_amount" })
	public void BK_US_391235_VerifyCaseOrderIndiactor_CashAmount_with_SISPFlow(){

		ciBooking.BK_VerifyCaseOrderIndiactor_CashAmount_with_SISPFlow();

	}

	@Test(groups = { "booking", "booking_hold" })
	public void BK_US_371328_verifyBookingwithHoldStatus(){

		ciBooking.BK_verifyBookingwithHoldStatus();

	}

	@Test(groups = { "booking", "specialservice_with_hold_booking" })
	public void BK_US_371328_verifySpecialServiceBooking_withHoldStatus(){

		ciBooking.BK_verifySpecialServiceBooking_withHoldStatus();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void bk_DG_US_391182_VerifyErrorDisplay_UnapprovedAccount_Ship_DangerousGoods(){

		ciBooking.bk_DG_US_391182_VerifyErrorDisplay_UnapprovedAccount_Ship_DangerousGoods();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void bk_DG_US_371379_VerifyDGApprovedCustomerAccountStatus(){

		ciBooking.bk_DG_US_371379_VerifyDGApprovedCustomerAccountStatus();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void bk_DG_US_371379_VerifyDebtorStatusDGApprovedRegularOrderBlank(){

		ciBooking.bk_DG_US_371379_VerifyDebtorStatusDGApprovedRegularOrderBlank();

	}

	@Test(groups = { "booking", "booking_clone" })
	public void BK_US_371330_verifyBookingClone(){

		ciBooking.BK_verifyBookingClone();

	}

	@Test(groups = { "booking", "stackable_goods" })
	public void BK_US_371348_verifyStackableGoods(){

		ciBooking.BK_verifyStackableGoods();

	}

	@Test(groups = { "booking", "contactdetails_validation" })
	public void BK_US_371589_verifyContactDetails_against_CallerInfo(){

		ciBooking.BK_verifyContactDetails_against_CallerInfo();

	}

	@Test(groups = { "booking", "dangerous_goods_air" })
	public void BK_DG_US_391179_VerifyAir_DangerousGoods(){

		ciBooking.BK_DG_VerifyAir_DangerousGoods();

	}

	@Test(groups = { "booking", "dimension_with_decimal" })
	public void BK_US_493415_verifyConsignmentdimension_with_decimalValues(){

		ciBooking.BK_verifyConsignmentdimension_with_decimalValues();

	}

	@Test(groups = { "booking", "booking_summary_email" })
	public void BK_US_495671_verifyCollectionEmail_with_bookingSummary(){

		ciBooking.BK_verifyCollectionEmail_with_bookingSummary();

	}

	@Test(groups = { "booking", "booking_summary_email" })
	public void BK_US_495671_verifyCallerEmail_with_bookingSummary(){

		ciBooking.BK_verifyCallerEmail_with_bookingSummary();

	}

	@Test(groups = { "booking", "booking_summary_email" })
	public void BK_US_495671_verifyOtherEmail_with_bookingSummary(){

		ciBooking.BK_verifyOtherEmail_with_bookingSummary();

	}

	@Test(groups = { "booking", "goodsinfo_doc_envelope" })
	public void BK_US_512149_verifyGoodsInfoDocument_with_envelopConsignment(){

		ciBooking.BK_verifyGoodsInfoDocument_with_envelopConsignment();

	}

	@Test(groups = { "booking", "specialservice_newcustomer" })
	public void BK_US_512158_verifySpecialServiceCollectionAddress_with_NewCustomer_SISPFlow(){

		ciBooking.BK_verifySpecialServiceCollectionAddress_with_NewCustomer_SISPFlow();

	}

	@Test(groups = { "booking", "consignmentnote_email" })
	public void BK_US_495671_verifyCallerEmail_with_consignmentNote(){

		ciBooking.BK_verifyCallerEmail_with_consignmentNote();

	}

	@Test(groups = { "booking", "consignmentnote_email" })
	public void BK_US_495671_verifyCollectionEmail_with_consignmentNote(){

		ciBooking.BK_verifyCollectionEmail_with_consignmentNote();

	}

	@Test(groups = { "booking", "consignmentnote_email" })
	public void BK_US_495671_verifyOtherEmail_with_consignmentNote(){

		ciBooking.BK_verifyOtherEmail_with_consignmentNote();

	}

	@Test(groups = { "booking", "consignmentnote_email0" })
	public void BK_US_477634_defaultEmail_with_consignmentNote(){

		ciBooking.BK_defaultEmail_with_consignmentNote();

	}

	@Test(groups = { "booking", "consignmentnote" })
	public void BK_US_477634_verifyConsignmentCreationButton_with_consignmentNote(){

		ciBooking.verifyOtherEmaildata_with_consignmentNote();

	}

	@Test(groups = { "booking", "price_with_noservices" })
	public void BK_US_504473_verifyNoServices_with_price(){

		ciBooking.verifyNoServices_with_price();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC120_BK_DG_Price_Drop_Down_validation(){

		ciBooking.bK_DG_Price_Break_Down_SISP();

	}

	@Test(groups = { "booking", "product_options" })

	public void BK_US_391201_VerifyProductOptions_enable5Options_Error(){

		ciBooking.BK_VerifyProductOptions_enable5Options_Error();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC119_BK_DG_Add_Non_Reg_DG_With_Non_Approved_Account(){

		ciBooking.bK_DG_Non_Reg_DG_With_Non_Approved_Account();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void b544549_TC123_BK_Non_Reg_And_Reg_DG_With_Non_DG_Approved_Account_SISP_Flow(){

		ciBooking.bK_DG_One_Fully_REG_DG_And_Two_Non_Reg_DG_SISP();

	}

	@Test(groups = { "booking", "postal_validation" })
	public void bk_CI_TC38_Booking_Validation_of_TownName_Field_for_Multiple_Postal_Match_SISP(){

		ciBooking.booking_TownAndMultipltePostalCode_Validation_SISP();

	}

	@Test(groups = { "booking", "postal_validation" })
	public void validatePostCodeAutopopulateForTownWithSinglePostcodeInBooking(){

		ciBooking.validatePostCodeAutopopulateForTownWithSinglePostcodeInBooking();
	}

	@Test(groups = { "booking", "selfbaughttime" })
	public void bk_Booking_SelfBaughtTime_Validation_SISP(){

		ciBooking.booking_SelfBaughtTime_Validation_SISP();

	}

	@Test(groups = { "booking", "booking_summary_email" })
	public void bk_Validate_two_Email_Ids_on_bookingSummary(){

		ciBooking.validate_User_Can_Add_Two_Email_Ids_And_Exist_In_Summary_Tab();

	}

	@Test(groups = { "booking", "booking_summary_email" })
	public void bk_Validate_invalid_Email_Ids_on_bookingSummary(){

		ciBooking.validate_invalid_Email_Ids_on_bookingSummary();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_bookingPage(){

		ciBooking.validate_Services_when_edits_on_bookingPage();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_goodsInformationPage(){

		ciBooking.validate_Services_when_edits_on_goodsInformationPage();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_additionalInformationPage(){

		ciBooking.validate_Services_when_edits_on_additionalInformationPage();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_collection_and_Delivery_Address(){

		ciBooking.validate_Services_when_edits_on_collection_and_Delivery_Address();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_enhanced_liability_indicator(){

		ciBooking.validate_Services_when_edits_on_enhanced_liability_indicator();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_quantity(){

		ciBooking.validate_Services_when_edits_on_quantity();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_weight(){

		ciBooking.validate_Services_when_edits_on_weight();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_remove_Consignment_items(){

		ciBooking.validate_Services_when_remove_Consignment_items();

	}

	@Test(groups = { "booking", "service_validation" })
	public void bk_Validate_Services_when_edits_on_Collection_date(){

		ciBooking.validate_Services_when_edits_on_Collection_date();

	}

	@Test(groups = { "booking", "accountNumber_validation" })
	public void bk_Verify_Account_Number_Without_Entering_Zeros_In_Booking_Screen(){

		ciBooking.Verify_Account_Number_Without_Entering_Zeros_In_Booking_Screen();

	}

	@Test(groups = { "booking", "accountNumber_validation" })
	public void bk_Verify_user_presented_options_when_Entering_Account_Number_Zeros_In_Booking_Screen()
			throws Exception {

		ciBooking.verify_user_presented_options_when_Entering_Account_Number_Zeros_In_Booking_Screen();

	}

	@Test(groups = { "booking", "accountNumber_validation" })
	public void bk_Verify_delivery_Address_when_Entering_Account_Number_Zeros_In_Booking_Screen(){

		ciBooking.verify_delivery_Address_when_Entering_Account_Number_Zeros_In_Booking_Screen();

	}

	@Test(groups = { "booking", "bkemail_language" })
	public void bk_bookingSummary_emailLanguage_Validation(){

		ciBooking.bk_bookingSummary_emailLanguage_Validation();

	}

	@Test(groups = { "booking", "email_language" })
	public void bk_consignmentNote_emailLanguage_Validation(){

		ciBooking.bk_consignmentNote_emailLanguage_Validation();

	}

	@Test(groups = { "booking", "address_verification" })
	public void verifyBooking_Adressline2_and_Province(){

		ciBooking.verifyBooking_Adressline2_and_Province();

	}

	@Test(groups = { "booking", "address_verification" })
	public void bk_verifyCharcterLength_for_CustomerName_AddressLines(){

		ciBooking.verifybooking_CharcterLength_for_CustomerName_AddressLines();

	}

	@Test(groups = { "booking", "address_verification" })
	public void verifyCloneBooking_Adressline2_and_Province(){

		ciBooking.verifyCloneBooking_Address_Province();

	}

	@Test(groups = { "booking", "address_verification" })
	public void verifyCloneBooking_with_exceedCharacters_of_Address_and_CustomerName(){

		ciBooking.verifyCloneBooking_with_exceedCharacters_of_Address_and_CustomerName();

	}

	@Test(groups = { "booking", "address_verification2" })
	public void verifyBooking_AddressLine2_while_chracterExceeded_on_AddressLine1(){

		ciBooking.verifyBooking_AddressLine2_while_chracterExceeded_on_AddressLine1();

	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDGMessage_when_duplicateUNNumber(){

		ciBooking.verifyDGMessage_when_duplicateUNNumber();
	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDGErrorMessage_with_UNNumber(){

		ciBooking.verifyDGErrorMessage_with_UNNumber();
	}

	@Test(groups = { "booking", "packaging_group" })
	public void verifyDifferent_Packaging_Group_for_goods(){

		ciBooking.verifyDifferent_Packaging_Group_for_goods();
	}

	@Test(groups = { "booking", "quantity_with_deciamlvalues" })
	public void verifyGoodsQuantity_with_decimalValues(){

		ciBooking.verifyGoodsQuantity_with_decimalValues();
	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDangerousGoods_Limitation(){

		ciBooking.verifyDangerousGoods_Limitation();
	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDangerousGoods_with_consignmentNoteEmail(){

		ciBooking.verifyDangerousGoods_with_consignmentNoteEmail();
	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDangerousGoods_while_clone_the_booking(){

		ciBooking.verifyDangerousGoods_while_clone_the_booking();
	}

	@Test(groups = { "booking", "dangerous_goods" })
	public void verifyDangerousGoods_while_edit_the_booking(){

		ciBooking.verifyDangerousGoods_while_edit_the_booking();
	}

	@Test(groups = { "booking", "ackwardFreight" })
	public void verifyException_Details_when_AwkwardFreight_not_approved_for_Booking(){

		ciBooking.verifyException_Details_when_AwkwardFreight_not_approved_for_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking(){
		ciBooking.verifyIndividualpieceweight_alert_when_exceeded_weight_for_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyDimension_alert_when_exceeded_dimension_updated_for_the_Booking(){
		ciBooking.verifyDimension_alert_when_exceeded_dimension_updated_for_the_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyConsignmentweight_alert_when_exceeded_totalWeight_for_Booking(){
		ciBooking.verifyConsignmentweight_alert_when_exceeded_totalWeight_for_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyConsignmentweight_alert_when_exceeded_totalWeight_while_clone_the_Booking(){
		ciBooking.verifyConsignmentweight_alert_when_exceeded_totalWeight_while_clone_the_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyDimension_alert_when_updated_anyDimension_while_edit_the_Booking(){
		ciBooking.verifyDimension_alert_when_updated_anyDimension_while_edit_the_Booking();
	}

	@Test(groups = { "booking", "alert_message" })
	public void verifyIndividualpieceweight_alert_when_exceeded_weight_while_edit_the_Booking(){
		ciBooking.verifyIndividualpieceweight_alert_when_exceeded_weight_while_edit_the_Booking();
	}

	@Test(groups = { "booking", "currency_validation" })
	public void validateCurrency_for_the_Booking(){
		ciBooking.booking_currency_validation();
	}

	@Test(groups = { "booking", "currency_validation" })
	public void validateCurrency_while_clone_the_Booking(){
		ciBooking.clone_booking_currency_validation();
	}

	@Test(groups = { "booking", "currency_validation" })
	public void validateCurrency_while_edit_the_Booking(){
		ciBooking.edit_booking_currency_validation();
	}

	@Test(groups = { "booking", "product_option_domesticRIRP" })
	public void verifyProductOptions_without_CSImportService_domesticRIRPFlow(){
		ciBooking.verifyProductOptions_without_CSImportService_domesticRIRPFlow();
	}

	@Test(groups = { "booking", "product_option_RIRP" })
	public void verifyProductOptions_without_CSImportService_RIRPFlow_booking(){
		ciBooking.verifyProductOptions_without_CSImportService_RIRPFlow_booking();
	}

	@Test(groups = { "booking", "product_option_RIRP_edit" })
	public void verifyProductOptions_without_CSImportService_RIRPFlow_while_editing_the_booking(){
		ciBooking.verifyProductOptions_without_CSImportService_RIRPFlow_while_editing_the_booking();
	}

	@Test(groups = { "booking", "product_option_domesticRIRP_edit" })
	public void verifyProductOptions_without_CSImportService_domesticRIRPFlow_while_editing_the_booking()
			throws Exception {
		ciBooking.verifyProductOptions_without_CSImportService_domesticRIRPFlow_while_editing_the_booking();
	}

	@Test(groups = { "booking", "No_Alert_Message_SIRP" })
	public void verifyAlertMessage_with_SIRPBooking_when_blank_migrationDate(){
		ciBooking.verifyAlertMessage_with_SIRPBooking_when_blank_migrationDate();
	}

	@Test(groups = { "booking", "Alert_Message_SIRP" })
	public void verifyAlertMessage_with_SIRPBooking_when_only_migrationDate(){
		ciBooking.verifyAlertMessage_with_SIRPBooking_when_only_migrationDate();
	}

	@Test(groups = { "booking", "bk_payingAccountNumber" })
	public void verifyPayingAccountNumber_with_SIRPBooking(){
		ciBooking.verifyPayingAccountNumber_with_SIRPBooking();
	}

	@Test(groups = { "booking", "bk_italy_collection" })
	public void verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPBooking(){
		ciBooking.verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPBooking();
	}

	@Test(groups = { "booking", "bk_italy_delivery" })
	public void verifyTelephoneNumber_on_delivery_for_Italy_SIRPBooking(){
		ciBooking.verifyTelephoneNumber_on_delivery_for_Italy_SIRPBooking();
	}

	@Test(groups = { "booking", "clonebk_italy_collection" })
	public void verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPCloneBooking(){
		ciBooking.verifyTelephoneNumber_on_Collection_and_Caller_for_Italy_SISPCloneBooking();
	}

	@Test(groups = { "booking", "clonebk_italy_delivery" })
	public void verifyTelephoneNumber_on_deliveryAddress_for_Italy_SIRPCloneBooking(){
		ciBooking.verifyTelephoneNumber_on_deliveryAddress_for_Italy_SIRPCloneBooking();
	}

	@Test(groups = { "booking", "email_specialservice_SISP" })
	public void verifyEmail_SISPBooking_with_SpecialServices(){
		ciBooking.verifyEmail_SISPBooking_with_SpecialServices();
	}

	@Test(groups = { "booking", "email_specialservice_SIRP" })
	public void verifyEmail_SIRPBooking_with_SpecialServices(){
		ciBooking.verifyEmail_SIRPBooking_with_SpecialServices();
	}

	@Test(groups = { "booking", "email_specialservice_RIRP" })
	public void verifyEmail_RIRPBooking_with_SpecialServices(){
		ciBooking.verifyEmail_RIRPBooking_with_SpecialServices();
	}

	@Test(groups = { "booking", "postalcode_masc_booking" })
	public void verifyPostcode_masc_for_booking(){
		ciBooking.verifyPostcode_masc_for_booking();
	}

	@Test(groups = { "booking", "postalcode_masc_for_country_without_postal" })
	public void verifyPostcode_masc_for_country_without_postal(){
		ciBooking.verifyPostcode_masc_for_country_without_postal();
	}

	@Test(groups = { "booking", "postalcode_masc_clonebooking" })
	public void verifyPostcode_masc_for_clonebooking(){
		ciBooking.verifyPostcode_masc_for_clonebooking();
	}

	@Test(groups = { "booking", "postalcode_masc_editbooking" })
	public void verifyPostcode_masc_for_editbooking(){
		ciBooking.verifyPostcode_masc_for_editbooking();
	}

	@Test(groups = { "booking", "bk_contactCharcters_limit" })
	public void verifyContactCharacters_for_booking(){
		ciBooking.verifyContactCharacters_for_booking();
	}

	@Test(groups = { "booking", "editbk_contactCharcters_limit" })
	public void verifyContactCharacters_while_editbooking(){
		ciBooking.verifyContactCharacters_while_editbooking();
	}

	@Test(groups = { "booking", "clonebk_contactCharcters_limit" })
	public void verifyContactCharacters_while_clonebooking(){
		ciBooking.verifyContactCharacters_while_clonebooking();
	}

	@Test(groups = { "booking", "bk_vatnumber_SISP" })
	public void verifyVATNumberOnBookingsPage_SISP(){
		ciBooking.verifyVATNumberOnBookingsPage_SISP();
	}

	@Test(groups = { "booking", "bk_vatnumber_SIRP" })
	public void verifyVATNumberOnBookingsPage_SIRP(){
		ciBooking.verifyVATNumberOnBookingsPage_SIRP();
	}

	@Test(groups = { "booking", "bk_vatnumber_RIRP" })
	public void verifyVATNumberOnBookingsPage_RIRP(){
		ciBooking.verifyVATNumberOnBookingsPage_RIRP();
	}

	@Test(groups = { "booking", "clone_bk_vatnumber" })
	public void verifyVATNumberOnBookingsPage_while_cloneBooking(){
		ciBooking.verifyVATNumberOnBookingsPage_while_cloneBooking();
	}

	@Test(groups = { "booking", "get_times_for_booking" })
	public void getTimingOnAdditionalInformationPage(){
		ciBooking.getTimingOnAdditionalInformationPage();
	}

	@Test(groups = { "booking", "telephone_number_booking" })
	public void verifyTelephoneNumberFieldForBooking(){
		ciBooking.verifyTelephoneNumberFieldForBooking();
	}

	@Test(groups = { "booking", "telephone_number_editBooking" })
	public void verifyTelephoneNumberFieldForEditBooking(){
		ciBooking.verifyTelephoneNumberFieldForEditBooking();
	}

	@Test(groups = { "booking", "telephone_number_cloneBooking" })
	public void verifyTelephoneNumberFieldForCloneBooking(){
		ciBooking.verifyTelephoneNumberFieldForCloneBooking();
	}

	@Test(groups = { "booking", "awkwardFreight_pallet" })
	public void verifyAwkwardFreightPallet(){
		ciBooking.verifyAwkwardFreightPallet();
	}

	@Test(groups = { "booking", "awkwardFreight_envelope" })
	public void verifyAwkwardFreightEnvelope(){
		ciBooking.verifyAwkwardFreightEnvelope();
	}

	@Test(groups = { "booking", "additionalOptions" })
	public void verifyAdditionalOptions_onAdditionalInformation(){
		ciBooking.verifyAdditionalOptions_onAdditionalInformation();
	}

	@Test(groups = { "booking", "euCountry_Amount_Validation" })
	public void verifyAmountField_for_different_EU_while_booking(){
		ciBooking.verifyAmountField_for_different_EU_while_booking();
	}

	@Test(groups = { "booking", "euCountry_Amount_Validation" })
	public void verifyAmountField_for_same_EU_while_booking(){
		ciBooking.verifyAmountField_for_same_EU_while_booking();
	}

	@Test(groups = { "booking", "clearAddress_booking" })
	public void clearAddressBooking_while_booking(){
		ciBooking.clearAddressBooking_while_booking();
	}

	@Test(groups = { "booking", "clearAddress_editbooking" })
	public void clearAddressBooking_while_editbooking(){
		ciBooking.clearAddressBooking_while_editbooking();
	}

	@Test(groups = { "booking", "clearAddress_clonebooking" })
	public void clearAddressBooking_while_clonebooking(){
		ciBooking.clearAddressBooking_while_clonebooking();
	}

	@Test(groups = { "booking", "addressBookSelection_on_booking" })
	public void verifySaveandSelectAddressButton_on_Booking(){
		ciBooking.verifySaveandSelectAddressButton_on_Booking();
	}

	@Test(groups = { "booking", "addressBookSelection_on_editbooking" })
	public void verifySaveandSelectAddressButton_on_editbooking(){
		ciBooking.verifySaveandSelectAddressButton_on_editbooking();
	}

	@Test(groups = { "booking", "addressBookSelection_on_clonebooking" })
	public void verifySaveandSelectAddressButton_on_clonebooking(){
		ciBooking.verifySaveandSelectAddressButton_on_clonebooking();
	}

	@Test(groups = { "booking", "dimension_measurement" })
	public void validateDimensionMeasurement_for_booking(){
		ciBooking.validateDimensionMeasurement_for_booking();
	}

	@Test(groups = { "booking", "clone_consignmentNote" })
	public void BK_VerifyConsignmentNumber_clone_SISPFlow(){

		ciBooking.BK_VerifyConsignmentNumber_clone_SISPFlow();

	}

	@Test(groups = { "booking", "booking_history" })
	public void BK_verifyHistoryPage_SISPFlow(){

		ciBooking.BK_verifyHistoryPage_SISPFlow();
	}

	@Test(groups = { "booking", "editbooking_collectionInstruction_charlen", "additionalInformation" })
	public void BK_editBooking_CollDriver_Inst_charsLength(){

		ciBooking.BK_editBooking_CollDriver_Inst_charsLength();

	}

	@Test(groups = { "booking", "clonebooking_collectionInstruction_charlen", "additionalInformation" })
	public void BK_cloneBooking_CollDriver_Inst_charsLength(){

		ciBooking.BK_cloneBooking_CollDriver_Inst_charsLength();

	}

	@Test(groups = { "booking", "osc_errormessage", "bookingInformation" })
	public void booking_ValidateonOSCErrorMessage(){

		ciBooking.booking_ValidateonOSCErrorMessage();

	}

	@Test(groups = { "booking", "cutoff", "bookingInformation" })
	public void cutoffValidation_on_bookinginfoPage(){

		ciBooking.cutoffValidation_on_bookinginfoPage();

	}
	
	@Test(groups = { "booking", "expressAndSpecialbutton", "additionalInformation" })
	public void expressAndSpecialServiceButtonValidation_for_booking(){

		ciBooking.expressAndSpecialServiceButtonValidation_for_booking();

	}
	@Test(groups = { "booking", "expressAndSpecialbutton_editBooking", "aditionalInformation" })
	public void expressAndSpecialServiceButtonValidation_for_editbooking(){

		ciBooking.expressAndSpecialServiceButtonValidation_for_editbooking();

	}
	@Test(groups = { "booking", "connoteUNNumber", "DGbooking" })
	public void verifyconsignmentnoteUNNumber_DGBooking(){
		ciBooking.verifyconsignmentnoteUNNumber_with_DGBooking();
	}
	
	@Test(groups = { "booking", "SpSThirdParty" })
	public void verifySpSThirdPartyBooking(){
		ciBooking.verifySpSThirdPartyBooking();
	}
	
	@Test(groups = { "booking", "WeightAndDimsMaintained" })
	public void verifyWeightAndDims_maintained_consignmentInfoPage_for_Booking(){
		ciBooking.verifyWeightAndDims_maintained_consignmentInfoPage_for_Booking();
	}
	
	@Test(groups = { "booking", "caseClearance" })
	public void verifyCaseClearance_for_allCaseTypes(){
		ciBooking.verifyCaseClearance_for_allCaseTypes();
	}
	@Test(groups = { "DPT", "reportLink" })
	public void validation_ofDPTReportLink_in_CCD() {
		ciBooking.validation_ofDPTReportLink_in_CCD();
	}
	
	

	@AfterMethod(alwaysRun = true)
	public void closeTab(ITestResult result, Method method){
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.verifyComponentError();
		CCD_Connectivity.verifyBookingUpdateorCreationError();
		CCD_Connectivity.CloseTab();
	}

	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		Test_Initializer.After_Method(result);
		extent.flush();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		// driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

		Test_Initializer.AfterSuite();
	}

}
