package com.tnt.ccdobjects;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class BookingPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By bookinglistTable = By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[2]/th/span/a");
	By bookingsearch = By.xpath("//input[@name='Booking__c-search-input']");

	// Caller Info
	By callerInfo = By.xpath("//span[@class='slds-radio_faux']");
	By callerName = By.xpath("//input[@name='callerName']");
	By callerPhone = By.xpath("//input[@name='callerPhone']");
	By callerEmail = By.xpath("//input[@name='callerEmail']");
	By paymentTerms = By.xpath("//legend[contains(text(),'Payment Terms')]");
	By billingNumber = By.xpath("//input[@name='billingAccNum']");
	By sameascallerinfosenderinput = By
			.xpath("(//*[contains(text(),\"Collection Contact Details\")]/following::input)[1]");
	By sameascallerinforeceiverinput = By
			.xpath("(//*[contains(text(),\"Delivery Contact Details\")]/following::input)[1]");
	By billingAccountSearchRecord = By.xpath(
			"//h2[text()='Please Select Billing Account Number']/following::table[1]//tr[1]//button[text()='Select']");
	By callerRegion = By.xpath(
			"(//span[text()=' Caller Information']/following::label[text()='Region Code']/following::input[1])[1]");
	By telephoneHelpMessage = By.xpath("//*[text()='Your entry does not match the allowed pattern.']");

	// Customer Instruction
	By confirmCustInstruction = By.xpath("//*[text()='Customer Instruction']/following::button[text()='Confirm']");
	By badDebtorStatus = By.xpath("//h1[contains(text(),'BAD DEBTOR STATUS: ')]");
	By badDebtorConfirm = By.xpath("//button[contains(text(),'Confirm')]");
	By CustInstruction = By.xpath("//*[text()='Customer Instruction']");
	By custInstructionMsg = By.xpath("//*[text()='Customer Instruction']/following::h1[1]");

	// Delivery Objects
	By deliveryCountry = By.xpath("(//input[@name='senderCountry'])[1]");
	By delPostalCode = By.xpath("//input[@name='deliveryPostCode']");
	By deliveryTown = By.xpath("//input[@name='deliveryTown']");
	By deliveryCustomerName = By.xpath("//input[@name='deliveryCustomerName']");
	By deliveryAddress = By.xpath("//input[@name='DeliverylineAddress1']");
	By deliveryValidate = By.xpath("//button[@title='Validate']");
	By removeCollectionCountry = By
			.xpath("(//h1[text()='Collection Address']/following::button[@title='Remove selected option'])[1]");
	By removeDeliveryCountry = By
			.xpath("//h1[text()='Delivery Address']/following::button[@title='Remove selected option']");
	By deliveryAddress2 = By.xpath("//input[@name='DeliverylineAddress2']");
	By deliveryAddress3 = By.xpath("//input[@name='DeliverylineAddress3']");
	By deliveryProvince = By.xpath("//input[@name='deliveryProvince']");
	By deliveryRegion = By.xpath(
			"//span[text()='Delivery Contact Details']/following::label[text()='Region Code']/following::input[1]");
	By deliverySaveAddress = By.xpath("//h1[text()='Delivery Address']/following::button[text()='Save Address']");
	By deliverySelectAddress = By.xpath("//h1[text()='Delivery Address']/following::button[text()='Select Address']");

	// Contact Info
	By contactName = By.xpath("//input[@name='conName']");
	By contactPhone = By.xpath("//input[@name='conPhone']");
	By contactEmail = By.xpath("//input[@name='conEmail']");
	By delConName = By.xpath("//input[@name='DelName']");
	By delConPhone = By.xpath("//input[@name='DelPhone']");
	By delConEmail = By.xpath("//input[@name='DelEmail']");

	// Collection Address Objects

	By collectionCountry = By.xpath("//input[@name='senderCountry']");
	By collcountrylist = By.xpath(
			"//h1[contains(text(),'Collection Address')]/following::c-country-search-component//lightning-input/following::div[1]");
	By collectionPostalCode = By.xpath("//input[@name='collectionPostCode']");
	By collectionTown = By.xpath("//input[@name='collectionTown']");
	By collcustomerName = By.xpath("//input[@name='collectionCustomerName']");
	By collectionAddress = By.xpath("//input[@name='CollectionlineAddress1']");
	By collectionValidate = By.xpath("//h1[text()='Collection Address']/following::button[@title='Search']");
	By valdateButton = By.xpath("(//button[@title='Search'])[1]");
	By bookingInfoTitle = By.xpath("//a[contains(text(),'Booking Information')]");
	By editCollectionCountry = By.xpath("(//input[@name='senInputName'])[1]");
	By bookinginfoTitleOnEdit = By.xpath("(//a[contains(text(),'Booking Information')])[2]");
	By collectionAddress2 = By.xpath("//input[@name='CollectionlineAddress2']");
	By collectionAddress3 = By.xpath("//input[@name='CollectionlineAddress3']");
	By collectionProvince = By.xpath("//input[@name='collectionProvince']");
	By collectionRegion = By.xpath(
			"//span[text()='Collection Contact Details']/following::label[text()='Region Code']/following::input[1]");
	By collectionVATNumber = By.xpath("//input[@name='collectionVatNum']");
	By deliveryVATNumber = By.xpath("//input[@name='delVatNum']");
	By collectionSaveAddress = By
			.xpath("//h1[text()='Collection Address']/following::button[text()='Save Address'][1]");
	By collectionSelectAddress = By
			.xpath("//h1[text()='Collection Address']/following::button[text()='Select Address'][1]");

	// Error Message
	By errorMsg = By.xpath("//div[@class='forceVisualMessageQueue']//*[contains(text(),'Error')]");
	By errorMsgContent = By
			.xpath("//div[@class='forceVisualMessageQueue']//*[contains(text(),'Error')]/following::span[1]");
	By successMsg = By.xpath("(//*[contains(text(),'Success:')])[1]");
	By addressValidation = By.xpath("(//*[contains(text(),'Address is validated')])");

	// Collection Contact detail
	By sameAsCollerInformationChkBox = By
			.xpath("//span[text()='Collection Contact Details']/following::span[@class='slds-checkbox_faux'][1]");
	By sameAsCollerInformationReceiverChkBox = By
			.xpath("//span[text()='Delivery Contact Details']/following::span[@class='slds-checkbox_faux'][1]");
	// Next button which navigate from Booking information tab to Goods Information
	// tab
	By nextButton = By.xpath("(//button[@title='Next'])[1]");

	By bookingInfoTitleByClone = By.xpath("(//a[@data-label='Booking Information'])[2]");
	By callerInfoSenderButton = By.xpath("(//span[contains(text(),'Sender')])[2]");
	By paymentTermsReceiverButton = By.xpath("(//span[contains(text(),'Receiver')])[3]");
	By callerInfoCountryTextBox = By.xpath("(//input[@name='senderCountry'])[1]");
	By callerInfoSearchButton = By.xpath("(//button[@title='Search'])[1]");

	// Recent Bookings page
	By bookingStatusOnListView = By.xpath("(//span[@title='BK'])[1]");
	By editBookingTitle = By.xpath("//span[@title='Edit Booking']");
	By cloneBookingTitle = By.xpath("//span[@title='Clone Booking']");

	// Cutoff
	By cutoffButton = By.xpath("//button[@title='Cut Off']");
	By cutoffTimeTilte = By.xpath("//h2[text()='CutOff Time']");
	By closeCutOffBtn = By.xpath("//h2[text()='CutOff Time']/preceding::button[1]");
	By cutoffFrame=By.id("iframeScrtWidget");

	public BookingPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public boolean verifyBookingList() {
		WebElement list = uiTestHelper.waitForObject(bookinglistTable);
		return list.isDisplayed();
	}

	public void globalSearchBookingNumber(String bookingNumber) {
		WebElement search = uiTestHelper.waitForObject(bookingsearch);
		search.clear();
		search.sendKeys(bookingNumber, Keys.ENTER);
	}

	// Caller Information
	public List<WebElement> getCallerInfoButtons() {
		List<WebElement> list = uiTestHelper.waitForObjects(callerInfo);
		return list;
	}

	public void callerInfo_SISP() {
		List<WebElement> list = uiTestHelper.waitForObjects(callerInfo);
		uiTestHelper.clickJS(list.get(0));
		uiTestHelper.clickJS(list.get(2));
	}

	public void callerInfo_SIRP() {
		List<WebElement> list = uiTestHelper.waitForObjects(callerInfo);
		list.get(0).click();
		list.get(3).click();
	}

	public void callerInfo_RIRP() {
		List<WebElement> list = uiTestHelper.waitForObjects(callerInfo);
		list.get(1).click();
		list.get(3).click();
	}

	public void confirmCustomerInstruction() {
		WebElement confirm = uiTestHelper.waitForObject(confirmCustInstruction);
		confirm.click();
	}

	public boolean verifyCustomerInstruction() {
		WebElement confirm = uiTestHelper.waitForObject(CustInstruction);
		return confirm.isDisplayed();
	}

	public String getCustomerInstruction() {
		WebElement confirm = uiTestHelper.waitForObject(custInstructionMsg);
		return confirm.getText();
	}

	public boolean verifyPaymentTerms() {
		WebElement title = uiTestHelper.waitForObject(paymentTerms);
		return title.isDisplayed();
	}

	public boolean verifyBillingAccNumber() {
		WebElement callerinfo = uiTestHelper.waitForObject(billingNumber);
		return callerinfo.isEnabled();
	}

	public void setBillingAccNumber(String billingAccountNumber) {
		WebElement accnum = uiTestHelper.waitForObject(billingNumber);
		accnum.clear();
		uiTestHelper.clickJS(accnum);
		accnum.sendKeys(billingAccountNumber);
	}

	public String getBillingAccountNumber() {
		WebElement accnum = uiTestHelper.waitForObject(billingNumber);
		return accnum.getAttribute("value");
	}

	// billingAccountSearchRecord
	public void clickBillingAccountSearchRecordPopup() {
		WebElement searchRecord = uiTestHelper.waitForObject(billingAccountSearchRecord);
		searchRecord.click();
	}

	public void clickPaymentTermsReceiverButton() {
		WebElement paymentTermsResender = uiTestHelper.waitForObject(paymentTermsReceiverButton);
		paymentTermsResender.click();
	}

	public void setCallerInfoCountry(String countryName) {
		WebElement countryTextBox = uiTestHelper.waitForObject(callerInfoCountryTextBox);
		countryTextBox.click();
		countryTextBox.sendKeys(countryName, Keys.ENTER);
		WebElement callerInfoCountryTextBox = uiTestHelper
				.waitForObject(By.xpath("(//span[contains(text(),'" + countryName + "')])[2]"));
		callerInfoCountryTextBox.click();
	}

	public void clickCallerInfoSearchButton() {
		WebElement callInfoButton = uiTestHelper.waitForObject(callerInfoSearchButton);
		callInfoButton.click();
	}

	public void setCountry(String Country) {
		WebElement territory = uiTestHelper.waitForObject(collectionCountry);
		uiTestHelper.clickJS(territory);
		territory.sendKeys(Country);
		WebElement countrylist = uiTestHelper.waitForObject(
				By.xpath("//label[text()='Country/Territory']/following::span[text()='" + Country + "']"));
		countrylist.click();
	}

	public void setCallerName(String contactname) {
		WebElement name = uiTestHelper.waitForObject(callerName);
		name.clear();
		name.sendKeys(contactname);
	}

	public void setCallerPhone(String contactphone) {
		WebElement phone = uiTestHelper.waitForObject(callerPhone);
		phone.clear();
		phone.sendKeys(contactphone);
		phone.sendKeys(Keys.TAB);
	}

	public void setCallerEmail(String contactemail) {
		WebElement email = uiTestHelper.waitForObject(callerEmail);
		email.clear();
		email.sendKeys(contactemail);
	}

	public String getCallerName() {
		WebElement name = uiTestHelper.waitForObject(callerName);
		return name.getAttribute("value");
	}

	public String getCallerPhone() {
		WebElement phone = uiTestHelper.waitForObject(callerPhone);
		return phone.getAttribute("value");
	}

	public String getCallerEmail() {
		WebElement email = uiTestHelper.waitForObject(callerEmail);
		return email.getAttribute("value");
	}

	public boolean verifyBadDebtorStatus() {
		WebElement status = uiTestHelper.waitForObject(badDebtorStatus);
		return status.isDisplayed();
	}

	public void clickConfirm() {
		WebElement status = uiTestHelper.waitForObject(badDebtorConfirm);
		status.click();
	}

	// Delivery Address
	public void setDeliveryCountry(String DeliveryCountry) {
		WebElement country = uiTestHelper.waitForObject(deliveryCountry);
		country.clear();
		country.sendKeys(DeliveryCountry);
		WebElement countrylist = uiTestHelper.waitForObject(By.xpath("//span[text()='" + DeliveryCountry + "']"));
		uiTestHelper.clickJS(countrylist);
	}

	public void setDeliveryPostal(String PostalCode) {
		WebElement postal = uiTestHelper.waitForObject(delPostalCode);
		postal.clear();
		postal.sendKeys(PostalCode);
	}

	public String getDeliveryPostalMasc() {
		WebElement postal = uiTestHelper.waitForObject(delPostalCode);
		postal.clear();
		return postal.getAttribute("placeholder");
	}

	public void setDeliveryTown(String Town) {
		WebElement town = uiTestHelper.waitForObject(deliveryTown);
		town.clear();
		town.sendKeys(Town);
	}

	public void setDelCustomerName(String CustomerName) {
		WebElement custname = uiTestHelper.waitForObject(deliveryCustomerName);
		custname.clear();
		custname.sendKeys(CustomerName);
	}

	public void setDeliveryAddress(String DeliveryAddress) {
		WebElement delAddress = uiTestHelper.waitForObject(deliveryAddress);
		delAddress.clear();
		delAddress.sendKeys(DeliveryAddress);
	}

	public String getDeliveryAddress() {
		WebElement delAddress = uiTestHelper.waitForObject(deliveryAddress);
		return delAddress.getAttribute("value");
	}

	public void deliveryValidatebtn() {
		WebElement validate = uiTestHelper.waitForObject(deliveryValidate);
		uiTestHelper.clickJS(validate);
	}

	public boolean verifyDelCountry() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryCountry);
		return delbtn.isDisplayed();
	}

	public boolean verifyDelPostal() {
		WebElement delbtn = uiTestHelper.waitForObject(delPostalCode);
		return delbtn.isDisplayed();
	}

	public boolean verifyDelCustomerName() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryCustomerName);
		return delbtn.isDisplayed();
	}

	public boolean verifyDelTown() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryTown);
		return delbtn.isDisplayed();
	}

	public boolean verifyDeAddress() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress);
		return delbtn.isDisplayed();
	}

	public boolean verifyDeliverySaveAddress() {
		WebElement ele = uiTestHelper.waitForObject(deliverySaveAddress);
		return ele.isDisplayed();
	}

	public boolean verifyDeliverySelectAddress() {
		WebElement ele = uiTestHelper.waitForObject(deliverySelectAddress);
		return ele.isDisplayed();
	}

	public void clickDeliverySaveAddress() {
		WebElement ele = uiTestHelper.waitForObject(deliverySaveAddress);
		uiTestHelper.clickJS(ele);
	}

	public void clickDeliverySelectAddress() {
		WebElement ele = uiTestHelper.waitForObject(deliverySelectAddress);
		uiTestHelper.clickJS(ele);
	}

	public boolean verifyDeliveryValidateBtn() {
		WebElement Deliverybtn = uiTestHelper.waitForObject(deliveryValidate);
		return Deliverybtn.isEnabled();
	}

	public void clickRemoveDelCountry() {
		WebElement remove = uiTestHelper.waitForObject(removeDeliveryCountry);
		uiTestHelper.scrollIntoView(remove);
		uiTestHelper.clickJS(remove);
	}

	public void clickRemoveCollectionCountry() {
		WebElement remove = uiTestHelper.waitForObject(removeCollectionCountry);
		uiTestHelper.scrollIntoView(remove);
		uiTestHelper.clickJS(remove);
	}

	public boolean verifyCollectionRemoveAddressMark() {
		WebElement remove = uiTestHelper.waitForObject(removeCollectionCountry);
		return remove.isDisplayed();
	}

	public boolean verifyDeliveryRemoveAddressMark() {
		WebElement remove = uiTestHelper.waitForObject(removeDeliveryCountry);
		return remove.isDisplayed();
	}

	public boolean verifyDelAddress2() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress2);
		return delbtn.isDisplayed();
	}

	public void setDeliveryAddressLine2(String addressLine2) {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress2);
		delbtn.sendKeys(addressLine2);
	}

	public String getDeliveryAddressLine2() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress2);
		return delbtn.getAttribute("value");
	}

	public boolean verifyDelAddress3() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress3);
		return delbtn.isDisplayed();
	}

	public void setDeliveryAddressLine3(String addressLine3) {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress3);
		delbtn.sendKeys(addressLine3);
	}

	public String getDeliveryAddressLine3() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryAddress3);
		return delbtn.getAttribute("value");
	}

	public boolean validateDelProvince() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryProvince);
		return delbtn.isDisplayed();
	}

	public boolean validateDelProvinceEnabled() {
		WebElement delbtn = uiTestHelper.waitForObject(deliveryProvince);
		return delbtn.isEnabled();
	}

	public String getDeliveryProvince() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionProvince);
		return delbtn.getAttribute("value");
	}

	public String getDeliveryCustomerName() {
		WebElement delAddress = uiTestHelper.waitForObject(deliveryCustomerName);
		return delAddress.getAttribute("value");
	}

	public boolean isValidateButtonEnabled() {
		WebElement searchBtn = uiTestHelper.waitForObject(deliveryValidate);
		return searchBtn.isEnabled();
	}

	public String getDeliveryPostCode() {
		WebElement delPostalCodeTxt = uiTestHelper.waitForObject(delPostalCode);
		return delPostalCodeTxt.getAttribute("value");
	}

	public String getCollectionPostCode() {
		WebElement collPostalCodeTxt = uiTestHelper.waitForObject(collectionPostalCode);
		return collPostalCodeTxt.getAttribute("value");
	}

	// Collection Address
	public void setCollectionCountry(String CollectionCountry) {
		WebElement country = uiTestHelper.waitForObject(collectionCountry);
		uiTestHelper.clickJS(country);
		country.clear();
		country.sendKeys(CollectionCountry);
		WebElement countrylist = uiTestHelper.waitForObject(collcountrylist);
		countrylist.click();
	}

	public void setCollectionTown(String Town) {
		WebElement town = uiTestHelper.waitForObject(collectionTown);
		town.clear();
		town.sendKeys(Town);
	}

	public void setCollectionCustomerName(String CustomerName) {
		WebElement custname = uiTestHelper.waitForObject(collcustomerName);
		custname.clear();
		custname.sendKeys(CustomerName);
	}

	public boolean verifyCollectionSaveAddress() {
		WebElement ele = uiTestHelper.waitForObject(collectionSaveAddress);
		return ele.isDisplayed();
	}

	public boolean verifyCollectionSelectAddress() {
		WebElement ele = uiTestHelper.waitForObject(collectionSelectAddress);
		return ele.isDisplayed();
	}

	public void clickCollectionSaveAddress() {
		WebElement ele = uiTestHelper.waitForObject(collectionSaveAddress);
		uiTestHelper.clickJS(ele);
	}

	public void clickCollectionSelectAddress() {
		WebElement ele = uiTestHelper.waitForObject(collectionSelectAddress);
		uiTestHelper.clickJS(ele);
	}

	public String getCollectionPostalMasc() {
		WebElement postal = uiTestHelper.waitForObject(collectionPostalCode);
		postal.clear();
		return postal.getAttribute("placeholder");
	}

	// callerInfoSenderButton
	public void clickCallerInfoSenderButton() {
		WebElement callInfoSender = uiTestHelper.waitForObject(callerInfoSenderButton);
		callInfoSender.click();
	}

	public void setCollectionAddress(String CollectionAddress) {
		WebElement Address = uiTestHelper.waitForObject(collectionAddress);
		Address.clear();
		Address.sendKeys(CollectionAddress);
	}

	public void clickCollectionValidatebtn() {
		WebElement validate = uiTestHelper.waitForObject(collectionValidate);
		uiTestHelper.clickJS(validate);
	}

	public void clickValidate() {
		WebElement validate = uiTestHelper.waitForObject(valdateButton);
		uiTestHelper.clickJS(validate);
	}

	public void deleteCollectionPostal() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionPostalCode);
		Collectionbtn.sendKeys(Keys.CONTROL, Keys.chord("1"));
		Collectionbtn.sendKeys(Keys.BACK_SPACE);
	}

	public void setCollectionPostal(String postalcode) {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionPostalCode);
		Collectionbtn.clear();
		Collectionbtn.sendKeys(postalcode);
	}

	public void setCollectionPostalWithOutClear(String postalcode) {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionPostalCode);
		Collectionbtn.sendKeys(postalcode);
	}

	public boolean verifyCollectionValidateBtn() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionValidate);
		return Collectionbtn.isEnabled();
	}

	public boolean verifyCollectionCountry() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionCountry);
		return Collectionbtn.isDisplayed();
	}

	public boolean verifyCollectionPostal() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionPostalCode);
		return Collectionbtn.isDisplayed();
	}

	public boolean verifyCollectionCustomerName() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collcustomerName);
		return Collectionbtn.isDisplayed();
	}

	public boolean verifyCollectionTown() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionTown);
		return Collectionbtn.isDisplayed();
	}

	public boolean verifyCollectionAddress() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(collectionAddress);
		return Collectionbtn.isDisplayed();
	}

	public void editCollectionCountry() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(editCollectionCountry);
		Collectionbtn.click();
	}

	public boolean verifyEditonCountry() {
		WebElement Collectionbtn = uiTestHelper.waitForObject(editCollectionCountry);
		return Collectionbtn.isDisplayed();
	}

	public boolean verifyCollectionAddress2() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionAddress2);
		return delbtn.isDisplayed();
	}

	public void setCollectionAddressLine2(String addressLine2) {
		WebElement delbtn = uiTestHelper.waitForObject(collectionAddress2);
		delbtn.sendKeys(addressLine2);
	}

	public boolean validateCollectionProvince() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionProvince);
		return delbtn.isDisplayed();
	}

	public boolean validateCollectionProvinceEnabled() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionProvince);
		return delbtn.isEnabled();
	}

	public String getCollectionProvince() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionProvince);
		return delbtn.getAttribute("value");
	}

	public String getCollectionCustomerName() {
		WebElement delAddress = uiTestHelper.waitForObject(collcustomerName);
		return delAddress.getAttribute("value");
	}

	public String getCollectionAddress() {
		WebElement delAddress = uiTestHelper.waitForObject(collectionAddress);
		return delAddress.getAttribute("value");
	}

	public String getCollectionAddressLine2() {
		WebElement delAddress = uiTestHelper.waitForObject(collectionAddress2);
		return delAddress.getAttribute("value");
	}

	public boolean verifyCollectionAddress3() {
		WebElement delbtn = uiTestHelper.waitForObject(collectionAddress3);
		return delbtn.isDisplayed();
	}

	public void setCollectionAddressLine3(String addressLine3) {
		WebElement delbtn = uiTestHelper.waitForObject(collectionAddress3);
		delbtn.sendKeys(addressLine3);
	}

	public String getCollectionAddressLine3() {
		WebElement delAddress = uiTestHelper.waitForObject(collectionAddress3);
		return delAddress.getAttribute("value");
	}

	// Contact Information
	public void setContactName(String contactname) {
		WebElement name = uiTestHelper.waitForObject(contactName);
		name.clear();
		name.sendKeys(contactname);
	}

	public void setContactPhone(String contactphone) {
		WebElement phone = uiTestHelper.waitForObject(contactPhone);
		phone.clear();
		phone.sendKeys(contactphone);
		phone.sendKeys(Keys.TAB);
	}

	public void setContactEmail(String contactemail) {
		WebElement email = uiTestHelper.waitForObject(contactEmail);
		email.clear();
		email.sendKeys(contactemail);
	}

	public void setRegion(String region) {
		WebElement phone = uiTestHelper.waitForObject(collectionRegion);
		phone.clear();
		phone.sendKeys(region);
	}

	public String getContactName() {
		WebElement name = uiTestHelper.waitForObject(contactName);
		return name.getAttribute("value");
	}

	public String getContactPhone() {
		WebElement phone = uiTestHelper.waitForObject(contactPhone);
		return phone.getAttribute("value");
	}

	public String getContactEmail() {
		WebElement email = uiTestHelper.waitForObject(contactEmail);
		return email.getAttribute("value");
	}

	public void clickBookingnextbtn() {
		WebElement nextbtn = uiTestHelper.waitForObject(nextButton);
		uiTestHelper.scrollIntoView(nextbtn);
		uiTestHelper.clickJS(nextbtn);
	}

	public boolean isNextButtonEnabled() {
		WebElement nextBtn = uiTestHelper.waitForObject(nextButton);
		return nextBtn.isEnabled();
	}

	public boolean verifyBookingTitle() {
		WebElement title = uiTestHelper.waitForObject(bookingInfoTitle);
		return title.isDisplayed();
	}

	public boolean verifyBookingTitleonEdit() {
		WebElement title = uiTestHelper.waitForObject(bookinginfoTitleOnEdit);
		return title.isDisplayed();
	}

	public void setInputSACIDeliveryContactName(String name) {
		WebElement getContactName = uiTestHelper.waitForObject(delConName);
		getContactName.clear();
		getContactName.sendKeys(name);
	}

	public void setInputSACIDeliveryTelephone(String phone) {
		WebElement getContactTelephone = uiTestHelper.waitForObject(delConPhone);
		getContactTelephone.clear();
		getContactTelephone.sendKeys(phone);
		getContactTelephone.sendKeys(Keys.TAB);
	}

	public void setInputSACIDeliveryEmail(String email) {
		WebElement getContactEmail = uiTestHelper.waitForObject(delConEmail);
		getContactEmail.clear();
		getContactEmail.sendKeys(email);
	}

	/**
	 * method to get the caller contact name from booking "same as caller
	 * information" field
	 * 
	 * @return
	 */
	public String getInputSACIDeliveryContactName() {
		WebElement getContactName = uiTestHelper.waitForObject(delConName);
		return getContactName.getAttribute("value");
	}

	/**
	 * method to get the caller telephone from booking "same as caller information"
	 * field
	 * 
	 * @return
	 */
	public String getInputSACIDeliveryTelephone() {
		WebElement getContactTelephone = uiTestHelper.waitForObject(delConPhone);
		return getContactTelephone.getAttribute("value");
	}

	/**
	 * method to get the caller email from booking "same as caller information"
	 * field
	 * 
	 * @return
	 */
	public String getInputSACIDeliveryEmail() {
		WebElement getContactEmail = uiTestHelper.waitForObject(delConEmail);
		return getContactEmail.getAttribute("value");
	}

	public void getBookingNumfromList(String bookingNumber) {
		WebElement myTable = driver.findElement(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		for (int i = 1; i <= row_count; i++) {
			String tempBookNum = driver
					.findElement(By
							.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th/span"))
					.getText();
			if (bookingNumber.contains(tempBookNum)) {
				Actions ob = new Actions(driver);
				WebElement chkBox = driver.findElement(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th/span"));
				ob.click(chkBox).perform();
				break;
			}
		}
	}

	public boolean isCallerNameEmpty() {
		WebElement callerNm = uiTestHelper.waitForObject(callerName);
		return callerNm.getText().isEmpty();
	}

	public boolean isCallerPhoneEmpty() {
		WebElement callerPhoneNo = uiTestHelper.waitForObject(callerPhone);
		return callerPhoneNo.getText().isEmpty();
	}

	public boolean isCallerEmailEmpty() {
		WebElement callerEmailId = uiTestHelper.waitForObject(callerEmail);
		return callerEmailId.getText().isEmpty();
	}

	/**
	 * Method to click on "Same As Coller Information" Check Box for Sender
	 */
	public void clickSameAsCallerInfo() {
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(sameAsCollerInformationChkBox);
		uiTestHelper.scrollIntoView(sameAsCollerInformationCheckBox);
		sameAsCollerInformationCheckBox.click();
	}

	/**
	 * Method to check if "same as caller information" field for sender is displayed
	 * 
	 * @return
	 */
	public boolean checkSenderSameAsCallerInfoIsDisplayed() {
		// scrollDownToSameAsCollerInfo();
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(sameAsCollerInformationChkBox);
		return sameAsCollerInformationCheckBox.isDisplayed();
	}

	/**
	 * Method to check if "same as caller information" field for sender is displayed
	 * 
	 * @return
	 */
	public boolean checkSenderSameAsCallerInfoIsEnabled() {
		WebElement sameascallerinput1 = uiTestHelper.waitForObject(sameascallerinfosenderinput);
		return sameascallerinput1.isSelected();
	}

	/**
	 * Method to click on "Same As Coller Information" Check Box for Receiver
	 */
	public void clickReceiverSameAsCallerInfo() {
		// scrollDownToSameAsCollerInfo();
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(sameAsCollerInformationReceiverChkBox);
		uiTestHelper.scrollIntoView(sameAsCollerInformationCheckBox);
		sameAsCollerInformationCheckBox.click();
	}

	public void scrollDownToSameAsCollerInfo() {
		uiTestHelper.scrollIntoView(driver.findElement(sameAsCollerInformationChkBox));
	}

	/**
	 * Method to check if "same as caller information" field for receiver is enabled
	 * 
	 * @return
	 */
	public boolean checkReceiverSameAsCallerInfoIsEnabled() {
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(sameascallerinforeceiverinput);
		return sameAsCollerInformationCheckBox.isSelected();
	}

	/**
	 * Method to check if "same as caller information" field for reciever is
	 * displayed
	 * 
	 * @return
	 */
	public boolean checkReceiverSameAsCallerInfoIsDisplayed() {
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(sameAsCollerInformationReceiverChkBox);
		return sameAsCollerInformationCheckBox.isDisplayed();
	}

	public boolean verifyBookingPageTitleByClone() {
		WebElement clone = uiTestHelper.waitForObject(bookingInfoTitleByClone);
		return clone.isDisplayed();
	}

	public boolean verifyBookingNextBtnByClone() {
		WebElement clone = uiTestHelper.waitForObject(nextButton);
		return clone.isEnabled();
	}

	public boolean verifyUseroptions(String userOption) {

		WebElement ele = uiTestHelper.waitForObject(By.xpath(
				"//h2[text()='Please Select Billing Account Number']/following::table[1]//tr[1]/th//a//span[@title='"
						+ userOption + "']"));
		return ele.isDisplayed();
	}

	// Error Message
	public boolean errorMsg() {
		WebElement msg = uiTestHelper.waitForObject(errorMsg);
		return msg.isDisplayed();
	}

	public String geterrorMsg() {
		WebElement msg = uiTestHelper.waitForObject(errorMsgContent);
		return msg.getText();
	}

	public boolean successMsg() {
		WebElement msg = uiTestHelper.waitForObject(successMsg);
		return msg.isDisplayed();
	}

	public boolean successMsgonAddress() {
		WebElement msg = uiTestHelper.waitForObjectwithSec(addressValidation, 90);
		return msg.isDisplayed();
	}

	public String getCallerRegion() {
		WebElement region = uiTestHelper.waitForObject(callerRegion);
		return region.getAttribute("value");
	}

	public String getCollectionRegion() {
		WebElement region = uiTestHelper.waitForObject(collectionRegion);
		return region.getAttribute("value");
	}

	public String getDeliveryRegion() {
		WebElement region = uiTestHelper.waitForObject(deliveryRegion);
		return region.getAttribute("value");
	}

	public boolean verifyCallerRegion() {
		WebElement region = uiTestHelper.waitForObject(callerRegion);
		return region.isDisplayed();
	}

	public boolean verifyCollectionRegion() {
		WebElement region = uiTestHelper.waitForObject(collectionRegion);
		return region.isDisplayed();
	}

	public boolean verifyDeliveryRegion() {
		WebElement region = uiTestHelper.waitForObject(deliveryRegion);
		return region.isDisplayed();
	}

	public boolean verifyCollectionVATNumber() {
		WebElement vatNumber = uiTestHelper.waitForObject(collectionVATNumber);
		return vatNumber.isDisplayed();
	}

	public boolean verifyDeliveryVATNumber() {
		WebElement vatNumber = uiTestHelper.waitForObject(deliveryVATNumber);
		return vatNumber.isDisplayed();
	}

	public void clearCollectionVATNumber() {
		WebElement vatNumber = uiTestHelper.waitForObject(collectionVATNumber);
		vatNumber.clear();
	}

	public void clearDeliveryVATNumber() {
		WebElement vatNumber = uiTestHelper.waitForObject(deliveryVATNumber);
		vatNumber.clear();
	}

	public void enterCollectionVATNumber(String collectionVATNumberValue) {
		WebElement collectionVATNumberTextbox = uiTestHelper.waitForObject(collectionVATNumber);
		uiTestHelper.scrollIntoView(collectionVATNumberTextbox);
		collectionVATNumberTextbox.click();
		collectionVATNumberTextbox.sendKeys(collectionVATNumberValue);
		collectionVATNumberTextbox.sendKeys(Keys.TAB);
	}

	public void enterDeliveryVATNumber(String deliveryVATNumberValue) {
		WebElement deliveryVATNumberTextbox = uiTestHelper.waitForObject(deliveryVATNumber);
		uiTestHelper.scrollIntoView(deliveryVATNumberTextbox);
		deliveryVATNumberTextbox.click();
		deliveryVATNumberTextbox.sendKeys(deliveryVATNumberValue);
		deliveryVATNumberTextbox.sendKeys(Keys.TAB);
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

	public boolean verifyTelephoneHelpMessage() {
		WebElement telephoneHelpMessageText = uiTestHelper.waitForObject(telephoneHelpMessage);
		uiTestHelper.scrollIntoView(telephoneHelpMessageText);
		return telephoneHelpMessageText.isDisplayed();
	}

	/**
	 * public String getCollectionPostalCodePlaceholder() { WebElement
	 * collectionPostalCodeTextbox =
	 * uiTestHelper.waitForObject(collectionPostalCode);
	 * uiTestHelper.scrollIntoView(collectionPostalCodeTextbox); return
	 * collectionPostalCodeTextbox.getAttribute("value"); }
	 **/

	public void collectionAddressBookSelection(String postCode) {
		By tableRow = By.xpath("//h2[text()='Please Select Collection Address Book ']/following::table//tbody/tr");
		List<WebElement> addressbook = uiTestHelper.waitForObjects(tableRow);
		int row = addressbook.size();
		for (int r = 1; r <= row; r++) {
			WebElement postalcode = uiTestHelper.waitForObject(
					By.xpath("//h2[text()='Please Select Collection Address Book ']/following::table//tbody/tr[" + r
							+ "]//td[@data-label='PostCode']//lightning-base-formatted-text"));
			String code = postalcode.getText();
			if (code.equals(postCode)) {
				WebElement address = uiTestHelper.waitForObject(
						By.xpath("//h2[text()='Please Select Collection Address Book ']/following::table//tbody/tr[" + r
								+ "]" + "//button[@name='Select']"));
				uiTestHelper.clickJS(address);
				break;
			}
		}
	}

	public void deliveryAddressBookSelection(String postCode) {
		By tableRow = By.xpath("//h2[text()='Please Select Delivery Address Book ']/following::table//tbody/tr");
		List<WebElement> addressbook = uiTestHelper.waitForObjects(tableRow);
		int row = addressbook.size();
		for (int r = 1; r <= row; r++) {
			WebElement postalcode = uiTestHelper.waitForObject(
					By.xpath("//h2[text()='Please Select Delivery Address Book ']/following::table//tbody/tr[" + r
							+ "]//td[@data-label='PostCode']//lightning-base-formatted-text"));
			String code = postalcode.getText();
			if (code.equals(postCode)) {
				WebElement address = uiTestHelper.waitForObject(
						By.xpath("//h2[text()='Please Select Delivery Address Book ']/following::table//tbody/tr[" + r
								+ "]" + "//button[@name='Select']"));
				uiTestHelper.clickJS(address);
				break;
			}
		}
	}

	public boolean verifyEditBookingTitle() {
		WebElement title = uiTestHelper.waitForObjectwithSec(editBookingTitle, 2000);
		return title.isDisplayed();
	}

	public boolean verifyCloneBookingTitle() {
		WebElement title = uiTestHelper.waitForObjectwithSec(cloneBookingTitle, 2000);
		return title.isDisplayed();
	}
	public boolean verifyCutoffButton() {
		WebElement cutOffTimeBtn = uiTestHelper.waitForObjectwithSec(cutoffButton, 50);
		return cutOffTimeBtn.isDisplayed();
	}
	public void clickCutoff() {
		WebElement cutOffTimeBtn = uiTestHelper.waitForObjectwithSec(cutoffButton, 50);
		cutOffTimeBtn.click();
	}

	public void getCutoffTime() throws Exception {
		//List<WebElement> cutofftimetableColumn=driver.findElements(By.xpath("//h2[text()='CutOff Time']/following::lightning-datatable//table/thead//th"));
		List<WebElement> cutofftimetableColumn=uiTestHelper.waitForObjects(By.xpath("//h2[text()='CutOff Time']/following::lightning-datatable//table/thead//th"));
		List<WebElement> cutofftimetableRow=uiTestHelper.waitForObjects(By.xpath("//h2[text()='CutOff Time']/following::lightning-datatable//table/tbody/tr"));
		//List<WebElement> cutOffType = uiTestHelper.waitForObjects(By.xpath("//h2[text()='CutOff Time']/following::table[1]/tbody//th"));
		int rowCount=cutofftimetableRow.size();
		int colCount=cutofftimetableColumn.size();
		//int cutofftypeCount=cutOffType.size(); 
		HashMap<String, String> map=new HashMap<String,String>();	
		System.out.println(rowCount+ " "+colCount);
		for(int j=1;j<=colCount;j++) {		
			WebElement headerValue = uiTestHelper.waitForObject(By.xpath("//h2[text()='CutOff Time']/following::lightning-datatable//table/thead/tr[1]/th["+j+"]//span[2]"));
			String header=headerValue.getText();	
			if(j==1) {
				WebElement cutoffType=uiTestHelper.waitForObject(By.xpath("//h2[text()='CutOff Time']/following::table[1]/tbody/tr["+j+"]/th[1]//lightning-base-formatted-text"));
				map.put(header, cutoffType.getText());
			}else {
			for(int i=1;i<=rowCount;i++) {
				WebElement rowValue = uiTestHelper.waitForObject(By.xpath("//h2[text()='CutOff Time']/following::table[1]/tbody/tr["+i+"]/td["+j+"]//lightning-base-formatted-text"));
				String data=rowValue.getText();
				System.out.println();
				map.put(header,data);
			}
			
		}
		}
		Driver.report_MapValues("All availbale Cutoff time options are below :",map);
	}
}