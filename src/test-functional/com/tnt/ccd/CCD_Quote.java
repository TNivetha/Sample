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
public class CCD_Quote extends Driver {
	// Just testing for test
	long elapsedTime = 0;
	long startTime = 0;
	CCD_CI_Booking ciQuote = new CCD_CI_Booking();
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			Test_Initializer.BeforeSuite(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public void login() throws Exception {
		ciQuote.ci_login();
	}

	@BeforeMethod(alwaysRun = true)
	public void Before_method(Method method) {
		Test_Initializer.Before_Method(method);
	}

	@Test(priority = 1, groups = { "quote", "quote-Sender" })
	public void ACM_QuoteFlow_Sender() throws Exception {

		ciQuote.Q_QuoteFlow();
		ciQuote.Q_QuoteDetails_Validation();
		ciQuote.Q_ConvertToBookingSender();
		ciQuote.Q_QuoteToBooking_DetailsValidation();
	}

	@Test(priority = 2, groups = { "quote", "quote-Receiver" })
	public void ACM_QuoteFlow_Receiver() throws Exception {

		ciQuote.Q_QuoteFlow_Receiver();
		ciQuote.Q_QuoteDetails_Validation();
		ciQuote.Q_ConvertToBookingReceiver();
		ciQuote.Q_QuoteToBooking_DetailsValidation();
	}

	@Test(groups = { "quote", "collection_address" })
	public void Q_IncorrectCollAddError() throws Exception {

		ciQuote.Q_IncorrectCollAddError();
	}

	@Test(groups = { "quote", "delivery_address" })
	public void Q_IncorrectDelAddError() throws Exception {

		ciQuote.Q_IncorrectDelAddErrorAndValidateError();
	}

	@Test(groups = { "quote", "acc_without_contact" })
	public void Q_AccountWithoutContact() throws Exception {

		ciQuote.Q_AccountWithoutContact();
	}

	@Test(groups = { "quote", "blank_custname" })
	public void Q_BlankCustName_FirstAddLine() throws Exception {

		ciQuote.Q_BlankCustName_FirstAddLine();
	}

	@Test(groups = { "quote", "non_document" })
	public void Q_CreateQuote_NonDocument() throws Exception {

		ciQuote.Q_CreateQuote_NonDocument();
	}

	@Test(groups = { "quote", "document" })
	public void Q_CreateQuote_Document() throws Exception {

		ciQuote.Q_CreateQuote_Document();
	}

	@Test(groups = { "quote", "mixed_content" })
	public void Q_CreateQuote_Mixed() throws Exception {

		ciQuote.Q_CreateQuote_Mixed();
	}

	@Test(groups = { "quote", "dangerous_goods" })
	public void Q_DangerousGoods_NO() throws Exception {

		ciQuote.Q_GoodsInfo_DangGoods_NO();
	}

	@Test(groups = { "quote", "blank_amount_error" })
	public void Q_Validate_BlankAmtError() throws Exception {

		ciQuote.Q_Validate_BlankAmtError();
	}

	@Test(groups = { "quote", "zero_amount" })
	public void Q_CreateQuote_ZeroAmt() throws Exception {

		ciQuote.Q_CreateQuote_ZeroAmt();
	}

	@Test(groups = { "quote", "anyDescription" })
	public void Q_CreateQuote_AnyDesc() throws Exception {

		ciQuote.Q_CreateQuote_AnyDesc();
	}

	@Test(groups = { "quote", "blankDescription" })
	public void Q_CreateQuote_BlankDesc() throws Exception {

		ciQuote.Q_CreateQuote_BlankDesc();
	}

	@Test(groups = { "quote", "content_type" })
	public void Q_ConType_Box_Pallet_Envelope() throws Exception {

		ciQuote.Q_ConType_Box_Pallet_Envelope();
	}

	@Test(groups = { "quote", "consignment_min_max_error" })
	public void Q_OverLimit_0Items_ErrorValidation() throws Exception {

		ciQuote.Q_OverLimit_0Items_ErrorValidation();
	}

	@Test(groups = { "quote", "quantity" })
	public void Q_ExcQty_ErrorValidation() throws Exception {

		ciQuote.Q_ExcQty_ErrorValidation();
	}

	@Test(groups = { "quote", "consignmentType" })
	public void Q_CreateQuote_DiffConsignmnentsType() throws Exception {

		ciQuote.Q_CreateQuote_DiffConsignmnentsType();
	}

	@Test(groups = { "quote", "weight_and_dim" })
	public void Q_CreateQuote_10Items_SameWeightsAndDims() throws Exception {

		ciQuote.Q_CreateQuote_10Items_SameWeightsAndDims();
	}

	@Test(groups = { "quote", "weight_and_dim" })
	public void Q_Calc_TotalWtAndDimValue() throws Exception {

		ciQuote.Q_Calc_TotalWtAndDimValue();
	}

	@Test(groups = { "quote", "service_validation" })
	public void Q_Verify_ValidServices() throws Exception {

		ciQuote.Q_Verify_ValidServices();
	}

	@Test(groups = { "quote3", "past_collection_date" })
	public void Q_Verify_CollectionDate_with_BackDated() throws Exception {

		ciQuote.Q_Verify_BackDatedCollDate();
	}

	@Test(groups = { "quote3", "future_collection_date" })
	public void Q_Verify_CollectionDate_with_FutureDated() throws Exception {

		ciQuote.Q_Verify_FutureDatedCollDate();
	}

	@Test(groups = { "quote3", "consignment_number" })
	public void Q_US_404812_VerifyConsignmentNumber() throws Exception {

		ciQuote.Q_VerifyConsignmentNumber();
	}

	@Test(groups = { "quote3", "product_options" })
	public void Q_US_371295_VerifyAllProductOptions() throws Exception {

		ciQuote.Q_VerifyAllProductOptions();
	}

	@Test(groups = { "quote3", "product_options" })
	public void Q_US_371295_VerifyProductOptions_enable5Options_Error() throws Exception {

		ciQuote.Q_VerifyProductOptions_enable5Options_Error();
	}

	@Test(groups = { "quote3", "contact_and_collection" })
	public void Q_US_371589_VerifyContactDetails_against_CollectionDetails() throws Exception {

		ciQuote.Q_VerifyContactDetails_against_CollectionDetails();
	}

	@Test(groups = { "quote3", "postal_code" })
	public void validatePostCodeAutopopulateForTownWithSinglePostcode() throws Exception {

		ciQuote.validatePostCodeAutopopulateForTownWithSinglePostcodeInQuote();

	}

	@Test(groups = { "quote3", "product_options" })
	public void verifyCreateQuoteWith4ProductOptions() throws Exception {

		ciQuote.verifyCreateQuoteWith4ProductOptions();
	}

	@Test(groups = { "quote3", "product_options" })
	public void verifyCreateQuoteWith1DGAnd3ProductOptions() throws Exception {

		ciQuote.verifyCreateQuoteWith1DGAnd3ProductOptions();
	}

	@Test(groups = { "quote3", "price_validation" })
	public void b521838_TC117_Q_Price_Break_Down_validation() throws Exception {

		ciQuote.q_QuoteFlow_Price_Break_Down_Receiver();
	}

	@Test(groups = { "quote3" })
	public void Check_Green_Tick_For_Available_Services_For_Quote() throws Exception {

		ciQuote.Check_Green_Tick_For_Available_Services_For_Quote();
	}

	@Test(groups = { "quote3", "postal_code" })
	public void Validate_Multiple_Areas_for_One_PostCode_Sender_Quote() throws Exception {

		ciQuote.Q_Validate_Multiple_Areas_for_One_PostCode_Sender_Quote();
	}

	@Test(groups = { "quote3", "postal_code" })
	public void Validate_Multiple_Postal_Codes_For_One_Town_Sender_Quote() throws Exception {

		ciQuote.Q_Validate_Multiple_Postal_Codes_For_One_Town_Sender_Quote();
	}

	@Test(groups = { "quote3", "quote_summary" })
	public void bk_Validate_two_Email_Ids_on_quoteSummary() throws Exception {

		ciQuote.validate_User_Can_Add_Two_Email_Ids_in_quoteSummary_And_Exist_In_Summary_Tab();
	}

	@Test(groups = { "quote3", "quote_summary" })
	public void bk_Validate_invalid_Email_Ids_on_quoteSummary() throws Exception {

		ciQuote.validate_invalid_Email_Ids_on_quoteSummary();
	}

	@Test(groups = { "quote3", "price_validation" })
	public void b523095_TC01_B594125_TC01_ACM_QuoteToBooking_SameDayQuote_WithoutRecalculatingThePrice()
			throws Exception {

		ciQuote.Q_ConvertToBookingSender_SameDayQuote();
	}

	@Test(groups = { "quote2", "address2" })
	public void verifyQuote_Adressline2_and_Province() throws Exception {

		ciQuote.verifyQuote_Adressline2_and_Province();
	}

	@Test(groups = { "quote2", "address" })
	public void verifyQuote_CharcterLength_for_CustomerName_AddressLines() throws Exception {

		ciQuote.verifyQuote_CharcterLength_for_CustomerName_AddressLines();
	}

	@Test(groups = { "quote2", "product_options" })
	public void verifyProductOptions_while_convert_Quote_to_Booking() throws Exception {

		ciQuote.verifyProductOptions_while_convert_Quote_to_Booking();
	}

	@Test(groups = { "quote2", "dangerous_goods" })
	public void verifyDangerousGoods_while_convertQuote_to_the_booking() throws Exception {

		ciQuote.verifyDangerousGoods_while_convertQuote_to_the_booking();
	}

	@Test(groups = { "quote2", "service_selection" })
	public void verifyServiceSelection_while_convertQuote_to_the_booking() throws Exception {

		ciQuote.verifyServiceSelection_while_convertQuote_to_the_booking();
	}

	@Test(groups = { "quote2", "routing_information" })
	public void verifyDetailedRoutingInfo_while_convert_Quote_to_the_booking() throws Exception {

		ciQuote.verifyDetailedRoutingInfo_while_convert_Quote_to_the_booking();
	}

	@Test(groups = { "quote2", "currency_validation" })
	public void validateCurrency_for_the_quote() throws Exception {
		ciQuote.quote_currency_validation();
	}
	@Test(groups = { "quote2", "delivery_date"})
	public void validateDeliveryDate_with_quoteSummary() throws Exception {
		ciQuote.validateDeliveryDate_with_quoteSummary();
	}
	@Test(groups = { "quote2", "quote_payingAccountNumber" })
	public void verifyPayingAccountNumber_with_SIRPQuote() throws Exception {
		ciQuote.verifyPayingAccountNumber_with_SIRPQuote();
	}
	@Test(groups = { "quote2", "quote_italy_collection" })
	public void verifyTelephoneNumber_on_deliveryAddress_for_Italy_QuoteSender() throws Exception {
		ciQuote.verifyTelephoneNumber_on_caller_and_Collection_for_Italy_QuoteSender();
	}
	@Test(groups = { "quote2", "quote_italy_delivery" })
	public void verifyTelephoneNumber_on_deliveryAddress_for_Italy_QuoteReceiver() throws Exception {
		ciQuote.verifyTelephoneNumber_on_deliveryAddress_for_Italy_QuoteReceiver();
	}
	@Test(groups = { "quote2", "bookingtime_quotes" })
	public void verifyBooking_collectionArea_and_selfbought_times_in_Quote() throws Exception {
		ciQuote.verifyBooking_collectionArea_and_selfbought_times_in_Quote();
	}
	@Test(groups = { "quote2", "quote_contactCharcters_limit" })
	public void verifyContactCharacters_for_quote() throws Exception {
		ciQuote.verifyContactCharacters_for_Quote();
	}
	@Test(groups = { "booking", "postalcode_masc_quote" })
	public void verifyPostcode_masc_for_quote() throws Exception {
		ciQuote.verifyPostcode_masc_for_quote();
	}
	@Test(groups = { "quote2", "clearAddress_quote" })
	public void clearAddressBooking_for_Quote() throws Exception {
		ciQuote.clearAddressBooking_for_Quote();
	}
	@Test(groups = { "quote2", "euCountry_Amount_Validation" })
	public void verifyAmountField_for_different_EU_while_quote() throws Exception {
		ciQuote.verifyAmountField_for_different_EU_while_quote();
	}
	@Test(groups = { "quote2", "euCountry_Amount_Validation" })
	public void verifyAmountField_for_same_EU_while_quote() throws Exception {
		ciQuote.verifyAmountField_for_same_EU_while_quote();
	}

	@Test(groups = { "quote2", "get_times_for_quote" })
	public void getTimingOnAdditionalInformationPage_while_creatingQuote() throws Exception {
		ciQuote.getTimingOnAdditionalInformationPage_while_creatingQuote();	
	}
	
	@Test(groups = { "quote2", "get_times_convertQuoteToBooking" })
	public void getTimingAfterConvertingQuoteToBooking() throws Exception {
		ciQuote.getTimingAfterConvertingQuoteToBooking();	
	}
	
	@Test(groups = { "quote2", "telephone_number_quote" })
	public void verifyTelephoneNumberFieldForQuote () throws Exception {
		ciQuote.verifyTelephoneNumberFieldForQuote();	
	}
	
	@Test(groups = { "booking", "telephone_number_convertQuoteToBooking" })
	public void verifyTelephoneNumberField_while_convertingQuoteToBooking() throws Exception {
		ciQuote.verifyTelephoneNumberField_while_convertingQuoteToBooking();	
	}
	
	@Test(groups = { "booking", "defaultPreferredCollectionTime_convertQuoteToBooking" })
	public void defaultPreferredCollectionTime_convertQuoteToBooking() throws Exception {
		ciQuote.verifyDefaultPreferredCollectionTime_while_convertQuoteToBooking();	
	}
	
	@AfterMethod(alwaysRun = true)
	public void After_Method(ITestResult result) {
		long stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		// Test_Initializer.After_Method(result, Key_Array[0], "Salesforce", "Advanced
		// Case Management", "NA", "Customer Experience", "NA", "NA", elapsedTime);
		extent.flush();
	}

	@AfterMethod(alwaysRun = true)
	public void closeTab() throws Exception {
		CCD_Connectivity CCD_Connectivity = new CCD_Connectivity();
		CCD_Connectivity.verifyComponentError();
		CCD_Connectivity.verifyBookingUpdateorCreationError();
		CCD_Connectivity.CloseTab();
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		//driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		try {
			Test_Initializer.AfterSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
