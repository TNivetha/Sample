package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

/**
 * This Page will display after click on General Enquiry button after selecting
 * Customer Account
 * 
 * @author W125KDI
 *
 */

public class GeneralEnquiryPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By generalEnquiryTitle=By.xpath("//h2[text()='GENERAL ENQUIRY']");
	By recipientDropdownIcon=By.xpath("//button[@name='Recipient__c']/following::div[1]//lightning-primitive-icon"); 
	By recipientDropdown=By.xpath("//button[@name='Recipient__c']");
	By activityDropdow=By.xpath("//button[@name='Activity__c']");
	By activityDropdownIcon=By.xpath("//button[@name='Activity__c']/following::div[1]//lightning-primitive-icon");
	By businessLocationDropdown=By.xpath("//input[@name='Activity__c']");
	By callbackDate=By.xpath("//input[@name='Date']");
	By callbackBeforeTime=By.xpath("//input[@name='CallbackBefore']");
	By additionalInformation=By.xpath("//textarea[@name='AdditionalInformation']");
	By confirmBtn = By.xpath("//button[contains(text(),'Confirm')]");
	By cancelBtn = By.xpath("//button[contains(text(),'Cancel')]");
	By businessLocationDropDownIcon = By.xpath("//button[@name='BusinessLocation']/following::div[1]//lightning-primitive-icon");
	By successMessage = By.xpath("//span[contains(text(),'General Enquiry record')]");
	By contactName=By.xpath("//input[@name='ContactName']");
	By telephone=By.xpath("//input[@name='Telephone']");
	By postcode=By.xpath("//input[@name='PostCode']");
	By selectedCountry=By.xpath("//button[@name='selectedcountry']");
	By packaging=By.xpath("//button[@name='packaging__c']");
	By activityNotAvailableMessage = By.xpath("//div[contains(text(),'Activity not available')]");
	By consignmentNumber = By.xpath("//input[@name='ConsignmentNumber']");
	By generalEnquiryNumber = By.xpath("//span[contains(text(),'General Enquiry Name')]/following::slot[1]/lightning-formatted-text");
	
	public GeneralEnquiryPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}
	
	/**
	 * Method to set Recipient drop down 
	 */
	public void setRecipient(String recipientVal) {
		WebElement recipientDropdown=uiTestHelper.waitForObject(recipientDropdownIcon);
		uiTestHelper.scrollIntoView(recipientDropdown);
		uiTestHelper.clickJS(recipientDropdown);
		WebElement recipientDropdownOptions = uiTestHelper.waitForObject(By.xpath("//button[@name='Recipient__c']/following::div/lightning-base-combobox-item[@data-value='"+recipientVal+"']"));
		uiTestHelper.scrollIntoView(recipientDropdownOptions);
		recipientDropdownOptions.click();
	}
	
	public boolean verifyPackaging() {
		WebElement packagedata=uiTestHelper.waitForObject(packaging);
		return packagedata.isDisplayed();
	}
	/**
	 * Method to set packaging drop down 
	 */
	public void setPackaging(String packageGroup) {
		WebElement packagingDropdown=uiTestHelper.waitForObject(packaging);
		uiTestHelper.scrollIntoView(packagingDropdown);
		uiTestHelper.clickJS(packagingDropdown);
		WebElement packagingDropdownOptions = uiTestHelper.waitForObject(By.xpath("//button[@name='packaging__c']/following::div/lightning-base-combobox-item[@data-value='"+packageGroup+"']"));
		uiTestHelper.scrollIntoView(packagingDropdownOptions);
		packagingDropdownOptions.click();
	}
	/**
	 * Method to set Country drop down 
	 */
	public void setCountry(String country) {
		WebElement countryDropdown=uiTestHelper.waitForObject(selectedCountry);
		uiTestHelper.scrollIntoView(countryDropdown);
		uiTestHelper.clickJS(countryDropdown);
		WebElement countryDropdownOptions = uiTestHelper.waitForObject(By.xpath("//button[@name='selectedcountry']/following::div/lightning-base-combobox-item//span[@title='"+country+"']"));
		uiTestHelper.scrollIntoView(countryDropdownOptions);
		countryDropdownOptions.click();
	}
	/**
	 * Method to set Postalcode drop down 
	 */
	public void setPostal(String postal) {
		WebElement details=uiTestHelper.waitForObject(postcode);
		details.sendKeys(postal);
	}
	/**
	 * Method to set telephone drop down 
	 */
	public void setTelephone(String telephoneNumber) {
		WebElement details=uiTestHelper.waitForObject(telephone);
		details.sendKeys(telephoneNumber);
	}
	/**
	 * Method to set telephone drop down 
	 */
	public void setContactName(String contactname) {
		WebElement details=uiTestHelper.waitForObject(contactName);
		details.sendKeys(contactname);
	}
	/**
	 * Method to get Recipient drop down value
	 */
	public String getRecipient() {
		WebElement recipientDropdownEle = uiTestHelper.waitForObject(recipientDropdown);
		return recipientDropdownEle.getAttribute("data-value");
	}

	/**
	 * Method to select Activity drop down value
	 * @param activityVal
	 */
	public void setActivity(String activityVal) {
		WebElement activityDropdown=uiTestHelper.waitForObject(activityDropdownIcon);
		uiTestHelper.scrollIntoView(activityDropdown);
		uiTestHelper.clickJS(activityDropdown);
		WebElement activityValDropdownOptions = uiTestHelper.waitForObject(By.xpath("//button[@name='Activity__c']/following::div/lightning-base-combobox-item[@data-value='"+activityVal+"']"));
		uiTestHelper.scrollIntoView(activityValDropdownOptions);
		activityValDropdownOptions.click();
	}	
	
	/**
	 * Method to get Activity drop down value
	 */
	public String getActivity() {
		WebElement activityDropdownEle = uiTestHelper.waitForObject(activityDropdow);
		return activityDropdownEle.getAttribute("data-value");
	}

	/**
	 * Method to enter Callback Date
	 * @param callback Date
	 */
	public void enterCallbackDate(String callbackDt) {
		WebElement callbackDateTxt = uiTestHelper.waitForObject(callbackDate);
		callbackDateTxt.sendKeys(callbackDt);
	}
	
	/**
	 * Method to enter Callback Before Time
	 * @param callback Time
	 */
	public void enterCallbackBeforeTime(String callbackTime) {
		WebElement callbackBeforeTimeTxt = uiTestHelper.waitForObject(callbackBeforeTime);
		callbackBeforeTimeTxt.sendKeys(callbackTime);
	}
	
	/**
	 * Method to enter additional Information
	 * @param additionalInformationVal
	 */
	public void enterAdditionalInformation(String additionalInformationVal) {
		WebElement additionalInformationTxt = uiTestHelper.waitForObject(additionalInformation);
		uiTestHelper.scrollIntoView(additionalInformationTxt);
		additionalInformationTxt.sendKeys(additionalInformationVal);
	}
	
	/**
	 * Method to click on Confirm button
	 */
	public void clickConfirmButton() {
		uiTestHelper.waitForObject(confirmBtn).click();
	}
	
	/**
	 * Method to click on Cancel button
	 */
	public void clickConcelButton() {
		uiTestHelper.waitForObject(cancelBtn).click();
	}
	
	/**
	 * Method to select Business Location drop down value
	 * @param businessLocationVal
	 */
	public void setBusinessLocation(String businessLocationVal) {
		WebElement businessLocationDropdown=uiTestHelper.waitForObject(businessLocationDropDownIcon);
		uiTestHelper.scrollIntoView(businessLocationDropdown);
		uiTestHelper.clickJS(businessLocationDropdown);
		WebElement businessLocationDropdownOptions = uiTestHelper.waitForObject(By.xpath("//button[@name='BusinessLocation']/following::div/lightning-base-combobox-item[@data-value='"+businessLocationVal+"']"));
		uiTestHelper.scrollIntoView(businessLocationDropdownOptions);
		businessLocationDropdownOptions.click();
	}
	/**
	 * Method to check General Enquiry Success Message displayed
	 * @return Boolean
	 */
	public boolean isGeneralEnqsuiryCreated() {
		WebElement successMsgEle = uiTestHelper.waitForObject(successMessage);
		return successMsgEle.isDisplayed();
	}
	
	/**
	 * Methpd to get General Enquiry Success Message
	 * @return General Enquiry Success Message
	 */
	public String getSuccessMessage() {
		WebElement successMsgEle = uiTestHelper.waitForObject(successMessage);
		return successMsgEle.getText();
	}
	
	public boolean verifyActivityNotAvailable() {
		WebElement activityMsg = uiTestHelper.waitForObject(activityNotAvailableMessage);
		uiTestHelper.scrollIntoView(activityMsg);
		return activityMsg.isDisplayed();
	}
	
	public String getAddInfoText() {
		WebElement addInfoText = uiTestHelper.waitForObject(additionalInformation);
		return addInfoText.getAttribute("value");		
	}
	
	public boolean verifyConsignmentNumberField() {
		WebElement consignmentNumberField = uiTestHelper.waitForObject(consignmentNumber);
		return consignmentNumberField.isDisplayed();
	}
	
	public String getGeneralEnquiryNumber() {
		WebElement generalEnquiryNum = uiTestHelper.waitForObject(generalEnquiryNumber);
		uiTestHelper.scrollIntoView(generalEnquiryNum);
		return generalEnquiryNum.getAttribute("value");
	}
	
	public void openGeneralEnquiry(String geNum) {
		WebElement genEnqNum = uiTestHelper.waitForObjectToBeClickable(By.xpath("//a[@title='"+ geNum +"']"));
		genEnqNum.click();		
	}
	public boolean verifyGeneralEnquiryTitle() {
		WebElement title = uiTestHelper.waitForObject(generalEnquiryTitle);
		return title.isDisplayed();
	}
	
}
