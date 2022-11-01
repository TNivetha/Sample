package com.tnt.ccdobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class BookingRecordPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By bookingCancel = By.xpath("//button[contains(text(),'Cancel Booking')]");
	By editBooking = By.xpath("(//button[@name='Booking__c.Edit'])");
	By editCurrentBooking = By.xpath("(//button[@name='Booking__c.Edit'])[2]");

	// Booking Summary
	By bookingSummary = By.xpath("//button[@name='Booking__c.Booking_Summary']");
	By emailAddressInput = By.xpath("//label[text()='Email Address']/following::button[1]");
	By otherEmailAddress = By.xpath("//*[text()='Email Address']/following::span[@title='Other']");
	By otherEmailAddressInput = By.xpath("//label[text()='Other Email']/following::input[1]");
	By sendEmail = By.xpath("//button[contains(text(),'Send Email')]");
	By emailErrorMsg = By.xpath("//div[contains(text(),'Please enter a valid email')]");
	By collectionEmailDropDown = By
			.xpath("//label[text()='Email Address']/following::div[@role='listbox']/lightning-base-combobox-item[2]");
	By callerEmail = By
			.xpath("//label[text()='Email Address']/following::div[@role='listbox']/lightning-base-combobox-item[1]");
	By sendOtherEmailString = By.xpath("//input[@placeholder='please enter an email adress..']");

	// Booking Audit
	By auditBooking = By.xpath("(//a[@data-label='Booking Audit'])");
	// Cancel Booking
	By cancelBookingScreen = By.xpath("//slot[@name='title']//h2[contains(text(),'Cancel Booking')]");
	By callerofCancel = By.xpath("//input[@name='Cancelled Caller']");
	By cancelReasonField = By.xpath("//button[@name='progress']");
	By saveCancelBooking = By.xpath("//button[contains(text(),'Save')]");
	By cancelReason = By.xpath("//lightning-base-combobox-item[@data-value='Booking no longer required']");

	// Booking history
	By bookingHistory = By.xpath("//a[text()='Booking History']");
	By refreshBookingHistory = By.xpath("//button[text()='Booking History']");
	By bookingCancelStatus = By.xpath("//lightning-formatted-text[contains(text(),'CN')]");
	By bookingCancelReason = By.xpath("//lightning-formatted-text[contains(text(),'Booking no longer required')]");
	By emailSentTo = By.xpath("//span[text()='Booking Summary - Sent To']/following::lightning-formatted-text[1]");
	By emailSentBy = By.xpath("//span[text()='Booking Summary - Sent By']/following::lightning-formatted-lookup[1]/a");
	By emailSentDate = By.xpath("//span[text()='Booking Summary - Sent Date']/following::lightning-formatted-text[1]");
	By bookingStatusUpdationMsg = By.xpath("(//*[contains(text(),'Booking status has been Updated')])");
	
	// Booking Info
	By bookinginformation = By.xpath("//a[text()='Booking Information']");
	By callerInfo = By.xpath("//span[contains(text(),'Caller Information')]/following::div[1]");
	By paymentTerms = By.xpath("//span[contains(text(),'Payment Terms')]/following::div[1]");
	By callerName = By.xpath("//span[contains(text(),'Caller Name')]");
	By callerInformation = By.xpath("(//span[contains(text(),'Caller Information')])[3]");
	By receiverName = By.xpath("(//span[contains(text(),'Receiver Name')])[2]");
	By callerPhone = By.xpath("//span[text()='Caller Phone']/following::lightning-formatted-phone[1]");
	By callerPostcode = By.xpath("//span[text()='Collection Postcode']/following::lightning-formatted-text[1]");
	By callerTown = By.xpath("(//span[text()='Collection City'])[2]/following::lightning-formatted-text[1]");
	By callerAddress = By.xpath("//span[text()='Collection Address']/following::lightning-formatted-text[1]");
	By callerCountry = By
			.xpath("(//span[text()='Collection Country/Territory'])[1]/following::lightning-formatted-lookup[2]");
	By billingAccountNumber = By
			.xpath("//span[text()='Billing Account Number']/following::lightning-formatted-text[1]");
	By payingcustomerAccount = By.xpath("//span[text()='Customer Account']/following::a[1]");

	By collectionVATNumber = By.xpath("//span[text()='Collection Vat Number']/following::lightning-formatted-text[1]");
	By deliveryVATNumber = By.xpath("//span[text()='Delivery Vat Number']/following::lightning-formatted-text[1]");
	

	// Goods Info Objects
	By goodsInformation = By.xpath("//a[text()='Goods Information']");
	By goodscontent = By.xpath("//span[contains(text(),'Contents')]/following::lightning-formatted-text[1]");
	By goodsdescription = By.xpath("//span[contains(text(),'Description')]/following::lightning-formatted-text[1]");

	// ConsignmentInfo Objects
	By moretab = By.xpath("//button[text()='More']");
	By consignmentinfo = By.xpath("(//span[contains(text(),'Consignment Information')])[1]");
	By lineitems = By.xpath("//span[@title='Line Items']");
	By lineItemTable = By.xpath("(//span[@title='Line Items']/following::table)[1]/tbody/tr[1]/th//a");
	By consignmentType = By.xpath("(//span[contains(text(),'Type')])[3]/following::lightning-formatted-text[1]");
	By quantity = By.xpath("//span[contains(text(),'Quantity')]/following::lightning-formatted-number[1]");
	By length = By.xpath("//span[contains(text(),'Length')]/following::lightning-formatted-number[1]");
	By height = By.xpath("//span[contains(text(),'Height')]/following::lightning-formatted-number[1]");
	By width = By.xpath("//span[contains(text(),'Width')]/following::lightning-formatted-number[1]");
	By weight = By.xpath("//span[contains(text(),'Weight')]/following::lightning-formatted-number[1]");
	By lineItemActiveImage = By.xpath("//img[@alt='Not Deleted']");
	By measurement = By.xpath("//span[text()='Measurement']/following::lightning-formatted-text[1]");

	// Additional Info Objects
	By additionalInfo = By.xpath("(//span[contains(text(),'Additional Information')])[1]");
	By collectionDate = By
			.xpath("(//span[contains(text(),'Collection Date')])[2]/following::lightning-formatted-text[1]");
	By lob = By.xpath("//span[contains(text(),'Line of Business')]/following::lightning-formatted-text[1]");
	By services = By.xpath("(//span[contains(text(),'Service')])[3]/following::lightning-formatted-text[1]");
	By legacyOrderId = By.xpath("//span[contains(text(),'Legacy Order Id')]/following::lightning-formatted-text[1]");
	By convertedFrom = By.xpath("//span[contains(text(),'Converted From')]/following::a");
	By collectionDt = By
			.xpath("(//span[contains(text(),'Collection Date')])[3]/following::lightning-formatted-text[1]");
	By cashAmount = By.xpath("//span[text()='Cash Order Amount']/following::lightning-formatted-text[1]");
	By consignmentNo = By.xpath("//span[text()='Consignment Number']/following::lightning-formatted-text[1]");
	By totalPrice = By.xpath("//span[text()='Total Price']/following::lightning-formatted-text[1]");

	// More option
	By moreOption = By.xpath("(//button[@title='More Tabs']//lightning-primitive-icon)[3]");

	// Booking Remarks
	By bookingRemarkbtn = By.xpath("//button[@name='Booking__c.Booking_remarks']");
	By remarkTextArea = By.xpath("//span[text()='Booking Remarks']/following::textarea");
	By remarkSaveBtn = By.xpath("//span[text()='Booking Remarks']/following::span[text()='Save']");
	By remarkTab = By.xpath("//a[@data-label='Booking Remarks']");
	By remarkTable = By.xpath("//span[contains(text(),'Booking Remarks')]/following::table/tbody/tr");
	By successMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']");
	By errorMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='error']");

	// Consignment Note
	By consignmentNoteBtn = By.xpath("//button[@name='Booking__c.Consignment_Note']");
	By emailDropDown = By.xpath("//label[text()='Email Address']/following::div[1]");
	By consignmentNoteCreation = By
			.xpath("//b[text()='Consignment Note']/following::button[text()='Consignment Note']");
	By otherDropDown = By.xpath("//lightning-base-combobox-item//span/span[text()='Other']");
	By otherInput = By.xpath("//label[text()='Other Email']/following::input[1]");
	By accountEmailAddress = By
			.xpath("(//label[text()='Email Address']/following::lightning-base-combobox-item//span/span)[1]");
	By cancelbtnonConsignmentNote = By.xpath("//button[text()='Cancel']");
	By consignmentLanguage = By.xpath("//label[text()='Select Language']/following::button[@name='language']");

	// SpecialService

	By specialServiceToggle = By.xpath("//input[@name='IsSpecialService__c']/following::span[1]");
	By contactName = By.xpath("(//span[text()='Contact Name'])[2]/following::lightning-formatted-text[1]");
	By town = By.xpath("(//span[text()='Town'])/following::lightning-formatted-text[1]");
	By telephone = By.xpath("(//span[text()='Telephone'])[2]/following::lightning-formatted-phone[1]");
	By postcode = By.xpath("(//span[text()='Postcode'])/following::lightning-formatted-text[1]");
	By targetCountry = By.xpath("(//span[text()='Target Country'])/following::lightning-formatted-text[1]");
	By callBackDate = By.xpath("(//span[text()='Callback Date'])/following::lightning-formatted-text[1]");
	By callbackBefore = By.xpath("(//span[text()='Callback Before'])/following::lightning-formatted-text[1]");
	By additionalInformation = By
			.xpath("(//span[text()='Additional Information'])/following::lightning-formatted-text[1]");
	By specialServiceEditMsg = By.xpath("(//*[@data-aura-class='forceToastMessage' and @data-key='error']//span)[2]");
	By bookingStatus = By.xpath("//p[text()='Status']/following::lightning-formatted-text[1]");
	By senderReceiver = By.xpath("//span[text()='Sender/Receiver']/following::lightning-formatted-text[1]");

	// Booking Clone
	By cloneButton = By.xpath("//button[@name='Booking__c.Clone']");
	By preSetEmailSentTo = By
			.xpath("//span[text()='Booking Summary - Sent To']/following::lightning-formatted-text[1]");

	// Booking Exception
	By bookingException = By.xpath("//a[text()='Booking Exception History']");
	By bookingExceptionTable = By.xpath("//*[@data-label='Booking Exception Number']//span//a//span");
	By customerAccount = By.xpath("//span[text()='Customer Account']");
	By service = By.xpath("(//span[text()='Service'])[2]");
	By productOptions = By.xpath("//span[text()='Product options']");
	By custAccName = By.xpath("//span[text()='Customer Account']/following::lightning-formatted-lookup[1]");
	By serviceName = By.xpath("(//span[text()='Service'])[2]/following::lightning-formatted-text[1]");
	By productOptionName = By.xpath("//span[text()='Product options']/following::lightning-formatted-text[1]");
	By bookingDescription = By.xpath(
			"//span[@title='Exception Description']/following::td[@data-label='Exception Description']//lst-formatted-text[1]");
	By bookingExceptionView = By.xpath("//*[@data-label='Booking Exception Number']//span//a");
	By exceptionCode = By.xpath(
			"//span[@title='Exception Code']/following::td[@data-label='Exception Code']//lightning-formatted-text[1]");

	public BookingRecordPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public boolean verifyEditBooking() {
		WebElement button = uiTestHelper.waitForObject(editBooking);
		return button.isEnabled();
	}

	public void clickEditBooking() {
		WebElement button = uiTestHelper.waitForObject(editBooking);
		button.click();
	}

	public void editCurrentBooking() {
		WebElement button = uiTestHelper.waitForObject(editCurrentBooking);
		button.click();
	}

	// Booking Summary
	public void clickBookingSummary() {
		WebElement button = uiTestHelper.waitForObject(bookingSummary);
		uiTestHelper.clickJS(button);
	}

	public String getEmailAddress() {
		WebElement textarea = uiTestHelper.waitForObject(emailAddressInput);
		return textarea.getAttribute("value");
	}

	public void clickEmailField() {
		WebElement button = uiTestHelper.waitForObject(emailAddressInput);
		button.click();
	}

	public void clickOther() {
		WebElement button = uiTestHelper.waitForObject(otherEmailAddress);
		uiTestHelper.clickJS(button);
	}

	public void setEmailAddress(String emailAddress) {
		WebElement input = uiTestHelper.waitForObject(otherEmailAddressInput);
		input.clear();
		input.sendKeys(emailAddress);
	}

	public boolean verifyEmailFormat() {
		WebElement button = uiTestHelper.waitForObject(emailErrorMsg);
		return button.isDisplayed();
	}

	// Booking History
	public void clickBookingHistory() {
		WebElement button = uiTestHelper.waitForObjectToBeClickable(bookingHistory);
		button.click();
	}
	
	public boolean successMsgonBookingHistory() {
		WebElement msg = uiTestHelper.waitForObject(bookingStatusUpdationMsg);
		return msg.isDisplayed();
	}

	public void clickSendEmail() {
		WebElement button = uiTestHelper.waitForObject(sendEmail);
		button.click();
	}

	public void clickCollectionEmail() {
		WebElement button = uiTestHelper.waitForObject(collectionEmailDropDown);
		button.click();
	}

	public String getCollectionEmail() {
		WebElement button = uiTestHelper.waitForObject(collectionEmailDropDown);
		return button.getAttribute("data-value");
	}

	public void clickCallerEmail() {
		WebElement button = uiTestHelper.waitForObject(callerEmail);
		button.click();
	}

	public String getCallerEmail() {
		WebElement button = uiTestHelper.waitForObject(callerEmail);
		return button.getAttribute("data-value");
	}

	public String getPreSetEmailSentTo() {
		WebElement info = uiTestHelper.waitForObject(preSetEmailSentTo);
		return info.getText();
	}

	// Booking Audit
	public void clickBookingAudit() {
		WebElement button = uiTestHelper.waitForObject(auditBooking);
		uiTestHelper.clickJS(button);
	}

	public String getOriginalValue(String fieldName) {
		By originalValue = By.xpath("//table[1]/tbody/tr//*[contains(text(),'" + fieldName
				+ "')]/following::td[@data-label='Original Value'][1]//lightning-base-formatted-text");
		WebElement value = uiTestHelper.waitForObject(originalValue);
		return value.getText();
	}

	public String getUpdatedValue(String fieldName) {
		By updatedValue = By.xpath("//table[1]/tbody/tr//*[contains(text(),'" + fieldName
				+ "')]/following::td[@data-label='New Value'][1]//lightning-base-formatted-text");
		WebElement value = uiTestHelper.waitForObject(updatedValue);
		return value.getText();
	}

	// Cancel Booking
	public void clickCancelBooking() {
		WebElement button = uiTestHelper.waitForObject(bookingCancel);
		button.click();
	}

	public boolean verifyCancelBookingbtn() {
		WebElement button = uiTestHelper.waitForObject(bookingCancel);
		return button.isDisplayed();
	}

	public boolean verifyCancelBookingScreen() {
		WebElement button = uiTestHelper.waitForObject(cancelBookingScreen);
		return button.isDisplayed();
	}

	public void setCallerofCancel(String Caller) {
		WebElement button = uiTestHelper.waitForObject(callerofCancel);
		button.sendKeys(Caller);
	}

	public void saveCancelBooking() {
		WebElement button = uiTestHelper.waitForObject(saveCancelBooking);
		button.click();
	}

	public void setCancelReason(String Reason) {
		By cancelReason = By.xpath("//lightning-base-combobox-item[@data-value='" + Reason + "']");
		WebElement reasonField = uiTestHelper.waitForObject(cancelReasonField);
		uiTestHelper.clickJS(reasonField);
		WebElement reason = uiTestHelper.waitForObject(cancelReason);
		uiTestHelper.clickJS(reason);
	}

	// Booking History
	public void clickBookingHistroy() {
		WebElement button = uiTestHelper.waitForObjectToBeClickable(bookingHistory);
		uiTestHelper.clickJS(button);
	}

	/*public void refreshBookingHistroy() {
		WebElement button = uiTestHelper.waitForObjectToBeClickable(refreshBookingHistory);
		uiTestHelper.clickJS(button);
	}*/

	public boolean verifyCancelBookingStatus() {
		WebElement button = uiTestHelper.waitForObject(bookingCancelStatus);
		return button.isDisplayed();
	}

	public boolean verifyCancelReason() {
		WebElement button = uiTestHelper.waitForObject(bookingCancelReason);
		return button.isDisplayed();
	}

	public String getEmailSentTo() {
		WebElement info = uiTestHelper.waitForObject(emailSentTo);
		return info.getText();
	}

	public String getEmailSentBy() {
		WebElement info = uiTestHelper.waitForObject(emailSentBy);
		return info.getText();
	}

	public String getEmailSentDate() {
		WebElement info = uiTestHelper.waitForObject(emailSentDate);
		return info.getText();
	}

	// Booking Information
	public void clickBookingInfo() {
		WebElement info = uiTestHelper.waitForObject(bookinginformation);
		info.click();
	}

	public String getCallerInfo() {
		WebElement info = uiTestHelper.waitForObject(callerInfo);
		return info.getText();
	}

	public String getPaymentTerms() {
		WebElement info = uiTestHelper.waitForObject(paymentTerms);
		return info.getText();
	}

	public String getCallerName() {
		WebElement info = uiTestHelper.waitForObject(callerName);
		return info.getText();
	}

	public String getCallerPhone() {
		WebElement info = uiTestHelper.waitForObject(callerPhone);
		return info.getText();
	}

	public String getCallerPostal() {
		WebElement info = uiTestHelper.waitForObject(callerPostcode);
		return info.getText();
	}

	public String getCallerTown() {
		WebElement info = uiTestHelper.waitForObject(callerTown);
		return info.getText();
	}

	public String getCallerCountry() {
		WebElement info = uiTestHelper.waitForObject(callerCountry);
		return info.getText();
	}

	// Goods Info
	public void clickGoodsInfo() {
		WebElement info = uiTestHelper.waitForObject(goodsInformation);
		info.click();
	}

	public String getGoodsContent() {
		WebElement info = uiTestHelper.waitForObject(goodscontent);
		return info.getText();
	}

	public String getGoodsDescription() {
		WebElement info = uiTestHelper.waitForObject(goodsdescription);
		return info.getText();
	}

	// ConsignmentInfo
	public void clickMoretab() {
		WebElement info = uiTestHelper.waitForObjectToBeClickable(moretab);
		if (info.isEnabled()) {
			uiTestHelper.clickJS(info);
		}
	}

	public void clickConsignmentInfo() {
		WebElement info = uiTestHelper.waitForObject(consignmentinfo);
		info.click();
	}

	public void clickLineItems() {
		WebElement items = uiTestHelper.waitForObject(lineitems);
		uiTestHelper.clickJS(items);
	}

	public void clickLineItemLink() {
		WebElement items = uiTestHelper.waitForObject(lineItemTable);
		uiTestHelper.clickJS(items);
	}

	public String getMeasurement() {
		WebElement dim = uiTestHelper.waitForObject(measurement);
		return dim.getText();
	}

	public String getConsignmentType() {
		WebElement coninfo = uiTestHelper.waitForObject(consignmentType);
		return coninfo.getText();
	}

	public String getQuantity() {
		WebElement coninfo = uiTestHelper.waitForObject(quantity);
		return coninfo.getText();
	}

	public String getLength() {
		WebElement coninfo = uiTestHelper.waitForObject(length);
		return coninfo.getAttribute("value");
	}

	public String getWidth() {
		WebElement coninfo = uiTestHelper.waitForObject(width);
		return coninfo.getAttribute("value");
	}

	public String getHeight() {
		WebElement coninfo = uiTestHelper.waitForObject(height);
		return coninfo.getAttribute("value");
	}

	public String getWeight() {
		WebElement coninfo = uiTestHelper.waitForObject(weight);
		return coninfo.getAttribute("value");
	}

	public List<WebElement> getLineItems() {
		List<WebElement> lineitems = uiTestHelper.waitForObjects(lineItemActiveImage);
		return lineitems;
	}

	// Additional Info
	public void clickAdditionalInfo() {
		WebElement info = uiTestHelper.waitForObject(additionalInfo);
		info.click();
	}

	public String getCollectionDate() {
		WebElement additionalInfo = uiTestHelper.waitForObject(collectionDate);
		return additionalInfo.getText();
	}

	public String getLob() {
		WebElement additionalInfo = uiTestHelper.waitForObject(lob);
		return additionalInfo.getText();
	}

	public String getService() {
		WebElement additionalInfo = uiTestHelper.waitForObject(services);
		return additionalInfo.getText();
	}

	public String getLegacyOrderId() {
		WebElement additionalInfo = uiTestHelper.waitForObject(legacyOrderId);
		return additionalInfo.getText();
	}

	public String getCashAmount() {
		WebElement additionalInfo = uiTestHelper.waitForObject(cashAmount);
		return additionalInfo.getText();
	}

	public String getConsignmentNo() {
		WebElement additionalInfo = uiTestHelper.waitForObject(consignmentNo);
		return additionalInfo.getText();
	}

	public String getTotalPrice() {
		WebElement additionalInfo = uiTestHelper.waitForObject(totalPrice);
		return additionalInfo.getText();
	}

	// Booking Remark
	public void clickBookingRemark() {
		WebElement remark = uiTestHelper.waitForObject(bookingRemarkbtn);
		remark.click();
	}
	
	public boolean verifyBookingRemark() {
		WebElement remark = uiTestHelper.waitForObject(bookingRemarkbtn);
		return remark.isDisplayed();
	}

	public void setRemarks(String bookingRemark) {
		WebElement remark = uiTestHelper.waitForObject(remarkTextArea);
		remark.sendKeys(bookingRemark);
	}

	public void saveBookingRemark() {
		WebElement remark = uiTestHelper.waitForObject(remarkSaveBtn);
		remark.click();
	}

	public int getRemarkTableSize() {
		List<WebElement> remark = uiTestHelper.waitForObjects(remarkTable);
		return remark.size();
	}

	public String getRemarkCreatedDate(int Size) {
		WebElement ele = uiTestHelper
				.waitForObject(By.xpath("//span[contains(text(),'Booking Remarks')]/following::table/tbody/tr[" + Size
						+ "]/td[1]//lightning-formatted-text"));
		return ele.getText();
	}

	public String getCSRNameLoc(int Size) {
		WebElement ele = uiTestHelper
				.waitForObject(By.xpath("//span[contains(text(),'Booking Remarks')]/following::table/tbody/tr[" + Size
						+ "]/td[2]//lightning-base-formatted-text"));
		return ele.getText();
	}

	public void clickRemarkTab() {
		WebElement remark = uiTestHelper.waitForObject(remarkTab);
		uiTestHelper.clickJS(remark);
	}

	public boolean verifySuccessMessage() {
		WebElement msg = uiTestHelper.waitForObject(successMessage);
		return msg.isDisplayed();
	}

	public boolean verifyErrorMessage() {
		WebElement msg = uiTestHelper.waitForObject(errorMessage);
		return msg.isDisplayed();
	}

	// Consignment Note
	public void clickConsignmentNote() {
		WebElement connote = uiTestHelper.waitForObject(consignmentNoteBtn);
		connote.click();
	}

	public void clickEmailAddressDropDown() {
		WebElement connote = uiTestHelper.waitForObject(emailDropDown);
		connote.click();
	}

	public int getCountofEmaillist() {
		List<WebElement> connote = uiTestHelper.waitForObjects(
				By.xpath("//b[text()='Consignment Note']/following::lightning-base-combobox-item//span/span"));
		return connote.size();
	}

	public void setOtherEmailAddress(String emailAddress) {
		WebElement emailadd = uiTestHelper.waitForObject(otherDropDown);
		emailadd.click();
		WebElement setemail = uiTestHelper.waitForObject(otherInput);
		setemail.sendKeys(emailAddress);
	}

	public void sendOtherEmailAddressToBox(String emailAddress) {
		WebElement input = uiTestHelper.waitForObject(sendOtherEmailString);
		input.clear();
		input.sendKeys(emailAddress);
	}

	public void clickConsignmentNoteCreationBtn() {
		WebElement connote = uiTestHelper.waitForObjectToBeClickable(consignmentNoteCreation);
		connote.sendKeys(Keys.ENTER);
	}

	public void setAccountEmailAddress() {
		WebElement emailadd = uiTestHelper.waitForObject(accountEmailAddress);
		emailadd.click();
	}

	public String getEmailAddressofConNote() {
		WebElement emailadd = uiTestHelper.waitForObject(accountEmailAddress);
		return emailadd.getAttribute("title");
	}

	public void clickCancelonConsignmentNote() {
		WebElement ele = uiTestHelper.waitForObject(cancelbtnonConsignmentNote);
		ele.click();
	}

	public boolean verifyConsignmentNoteCreationBtn() {
		WebElement connote = uiTestHelper.waitForObject(consignmentNoteCreation);
		return connote.isEnabled();
	}

	public void selectLanguage(String language) {
		WebElement ele = uiTestHelper.waitForObjectToBeClickable(consignmentLanguage);
		uiTestHelper.clickJS(ele);
		WebElement lang = uiTestHelper
				.waitForObject(By.xpath("//button[@name='language']/following::*[@data-value='" + language + "']"));
		uiTestHelper.scrollIntoView(lang);
		lang.click();
	}

	public String getLanguage() {
		WebElement ele = uiTestHelper.waitForObjectToBeClickable(consignmentLanguage);
		return ele.getAttribute("value");
	}

	public void sendEmail() {
		WebElement ele = uiTestHelper.waitForObjectToBeClickable(sendEmail);
		ele.click();
	}
	// Booking Exception History Tab

	public void clickBookingExceptionTab() {
		WebElement ele = uiTestHelper.waitForObject(bookingException);
		ele.click();
	}

	public boolean verifyExceptionDescription() {
		WebElement ele = uiTestHelper.waitForObject(bookingDescription);
		return ele.isDisplayed();
	}

	public String getExceptionDescription() {
		WebElement ele = uiTestHelper.waitForObject(bookingDescription);
		return ele.getText();
	}

	public String getExceptionCode() {
		WebElement ele = uiTestHelper.waitForObject(exceptionCode);
		return ele.getText();
	}

	public void clickBookingExceptionTable() {
		WebElement ele = uiTestHelper.waitForObject(bookingExceptionTable);
		ele.click();
	}

	public boolean verifyCustomerAccount() {
		WebElement bookingException = uiTestHelper.waitForObject(customerAccount);
		return bookingException.isDisplayed();
	}

	public boolean verifyProductOption() {
		WebElement bookingException = uiTestHelper.waitForObject(productOptions);
		return bookingException.isDisplayed();
	}

	public boolean verifyService() {
		WebElement bookingException = uiTestHelper.waitForObject(service);
		return bookingException.isDisplayed();
	}

	public String getCustomerName() {
		WebElement bookingException = uiTestHelper.waitForObject(custAccName);
		return bookingException.getText();
	}

	public String getServiceName() {
		WebElement bookingException = uiTestHelper.waitForObject(serviceName);
		return bookingException.getText();
	}

	public String getProductOption() {
		WebElement bookingException = uiTestHelper.waitForObject(productOptionName);
		return bookingException.getText();
	}

	/**
	 * Verify caller information displayed
	 * 
	 * @return : Boolean
	 */
	public boolean isCallerInformationDisplayed() {
		WebElement callerinfo = uiTestHelper.waitForObject(callerInformation);
		return callerinfo.isDisplayed();

	}

	/**
	 * Verify Caller Name displayed
	 * 
	 * @return : Boolean
	 */
	public boolean isCallerNameDisplay() {
		WebElement callerNm = uiTestHelper.waitForObject(callerName);
		return callerNm.isDisplayed();
	}

	/**
	 * Verify Receiver Name displayed
	 * 
	 * @return : Boolean
	 */
	public boolean isReceiverNameDisplay() {
		WebElement receiverNm = uiTestHelper.waitForObject(receiverName);
		return receiverNm.isDisplayed();
	}

	/**
	 * Click on More option to select additional tab
	 */
	public void clickMoreOption() {
		WebElement clickMoreOption = uiTestHelper.waitForObject(moreOption);
		clickMoreOption.click();
	}

	/**
	 * get Converted Quote Number
	 * 
	 * @return Quote Number
	 */
	public String getConvertedFrom() {
		WebElement quoteNumber = uiTestHelper.waitForObject(convertedFrom);
		return quoteNumber.getText();

	}

	/**
	 * get collection Date
	 * 
	 * @return Collection Date
	 */
	public String getCollectionDateVal() {
		WebElement collectionDate = uiTestHelper.waitForObject(collectionDt);
		return collectionDate.getText();

	}

	/**
	 * Method to return Collection Date displayed
	 * 
	 * @return Boolean: True or False
	 */
	public boolean isCollectionDateDisplayed() {
		WebElement collectionDate = uiTestHelper.waitForObject(collectionDt);
		return collectionDate.isDisplayed();
	}

	public boolean isSpecialServiceDispalyed() {
		WebElement spclService = uiTestHelper.waitForObject(specialServiceToggle);
		return spclService.isDisplayed();
	}

	public String getContactName() {
		WebElement spclService = uiTestHelper.waitForObject(contactName);
		return spclService.getText();
	}

	public String getTelephoneNumber() {
		WebElement spclService = uiTestHelper.waitForObject(telephone);
		return spclService.getText();
	}

	public String getTargetCountry() {
		WebElement spclService = uiTestHelper.waitForObject(targetCountry);
		return spclService.getText();
	}

	public String getTown() {
		WebElement spclService = uiTestHelper.waitForObject(town);
		return spclService.getText();
	}

	public String getPostalcode() {
		WebElement spclService = uiTestHelper.waitForObject(postcode);
		return spclService.getText();
	}

	public String getCallbackDate() {
		WebElement spclService = uiTestHelper.waitForObject(callBackDate);
		return spclService.getText();
	}

	public String getSenderorReceiver() {
		WebElement spclService = uiTestHelper.waitForObject(senderReceiver);
		return spclService.getText();
	}

	public String getCallbackBefore() {
		WebElement spclService = uiTestHelper.waitForObject(callbackBefore);
		return spclService.getText();
	}

	public String getAdditionalInfo() {
		WebElement spclService = uiTestHelper.waitForObject(additionalInformation);
		return spclService.getText();
	}

	public String getBookingStatus() {
		WebElement status = uiTestHelper.waitForObject(bookingStatus);
		return status.getText();
	}

	public String getSpecialServiceErrorMsg() {
		WebElement status = uiTestHelper.waitForObject(specialServiceEditMsg);
		return status.getText();
	}

	public void clickCloneBooking() {
		WebElement clone = uiTestHelper.waitForObject(cloneButton);
		clone.click();
	}

	public boolean verifyBillingAccountNumber() {
		WebElement accNumber = uiTestHelper.waitForObject(billingAccountNumber);
		return accNumber.isDisplayed();
	}

	public String getBillingAccountNumber() {
		WebElement accName = uiTestHelper.waitForObject(billingAccountNumber);
		return accName.getText();
	}

	public boolean verifyCustomerAccountName() {
		WebElement accName = uiTestHelper.waitForObject(payingcustomerAccount);
		return accName.isDisplayed();
	}

	public String getCustomerAccountName() {
		WebElement accName = uiTestHelper.waitForObject(payingcustomerAccount);
		return accName.getText();
	}

	// bookingTabsList
	public Boolean waitForBookingTabsList() {
		WebElement tabs = uiTestHelper.waitForObjectToBeClickable(bookingHistory);
		return true;
	}

	// greenPopUpSuccessMsg - run this test to prove it starts with 1 and ends with
	// 0
	public Boolean waitForSuccessMessage() {
		uiTestHelper.waitForObject(successMessage);
		Boolean greenTick = uiTestHelper.invisibilityOfElementLocated(successMessage);
		return greenTick;
	}

	public String getCollectionVATNumber() {
		WebElement collectionVATNumberTextbox = uiTestHelper.waitForObject(collectionVATNumber);
		uiTestHelper.scrollIntoView(collectionVATNumberTextbox);
		return collectionVATNumberTextbox.getAttribute("value");
	}

	public String getDeliveryVATNumber() {
		WebElement deliveryVATNumberTextbox = uiTestHelper.waitForObject(deliveryVATNumber);
		uiTestHelper.scrollIntoView(deliveryVATNumberTextbox);
		return deliveryVATNumberTextbox.getAttribute("value");
	}
}
