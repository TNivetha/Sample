package com.tnt.ccdobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class CustomerAccountPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By customerAccContact = By.xpath("(//div[@class='slds-card__body']//table)[1]/tbody/tr[1]/td[1]/input");
	By newBooking = By.xpath("//button[@name='Booking']");
	By newQuote = By.xpath("//button[@name='Quote']");
	By newContact = By.xpath("//button[contains(text(),'New Contact')]");
	By phoneNumber = By.xpath("//table/tbody/tr[1]/td[@data-label='Phone']");
	By email = By.xpath("//table/tbody/tr[1]/td[@data-label='Country'][2]");
	By extension = By.xpath("//table/tbody/tr[1]/td[@data-label='Country'][1]");
	By customerName = By.xpath("(//span[contains(text(),'Customer')])/parent::lightning-output-field//a");
	By accountNumber = By
			.xpath("(//span[contains(text(),'Account Number')])[2]/following::lightning-formatted-text[1]");
	By address = By.xpath("//span[contains(text(),'Customer Address')]/following::lightning-formatted-rich-text/span");
	By title = By.xpath("//h1/div[text()='Customer Account']");
	By customerAccountDetailsFields = By.xpath("//span[@class='slds-form-element__label']");
	By accountNameHeader = By
			.xpath("//a[@data-tab-value='customTab']/following::span[contains(text(),'Account Name')]");
	By dedicatedAccHeader = By.xpath("(//span[contains(text(),'Dedicated Account')])[1]");
	By dedicatedTeamHeader = By.xpath("//span[contains(text(),'Dedicated Team')]");
	By migrationDateHeader = By.xpath("//p[@title='Migration Date']");
	By overlapDateHeader = By.xpath("//p[@title='Overlap Date']");
	By proactiveException=By.xpath("//a[contains(text(),'Proactive Exceptions')]");
	By caseDisplayed=By.xpath("(//a[@data-refid='recordId'])[1]");
	By caseRecord=By.xpath("(//a[@data-refid='recordId'])[2]");
	By caseSearch=By.xpath("(//a[text()='Cases']/following::table[@data-aura-class='uiVirtualDataTable']//tr)[2]/th//a");

	By dabtorStatusLabel = By.xpath("//p[@title='Debtor Satus']");
	By dgApprovedStatusChkbox = By.xpath("//input[@name='Dangerous_Goods_Approved__c']");
	By regularOrderCheckBox = By.xpath("//input[@name='Regular_Order__c']");
	By dabtorStatusVal = By.xpath("(//p[@title='Debtor Satus'])/following::lightning-formatted-text[1]");
	By generalEnquirybutton = By.xpath("//button[@name='GeneralEnquiry']");
	By customerAccountTitle = By.xpath("//a[text()='Customer Accounts']");

	public CustomerAccountPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public List<WebElement> customerAccDetailsFields() {
		uiTestHelper.waitforPresenceOfObject(customerAccountDetailsFields);
		List<WebElement> elements = driver.findElements(customerAccountDetailsFields);
		return elements;
	}

	public WebElement accountNameHeaderField() {
		WebElement element = uiTestHelper.waitForObject(accountNameHeader);
		return element;
	}

	public WebElement dedicatedAccHeaderField() {
		WebElement element = uiTestHelper.waitForObject(dedicatedAccHeader);
		return element;
	}

	public WebElement dedicatedTeamHeaderField() {
		WebElement element = uiTestHelper.waitForObject(dedicatedTeamHeader);
		return element;
	}

	public WebElement migrationDateHeaderField() {
		WebElement element = uiTestHelper.waitForObject(migrationDateHeader);
		return element;
	}

	public WebElement overlapDateHeaderField() {
		WebElement element = uiTestHelper.waitForObject(overlapDateHeader);
		return element;
	}

	public void selectCustomerAccounts(String AccName) {
		//if(verifyCustomerAccountsPage()) {
		WebElement acc = uiTestHelper.waitForObjectToBeClickable(By.xpath("//a[text()='Customer Accounts']/following::a[@title='" + AccName + "']"));
		uiTestHelper.clickJS(acc);
		//}
	}
	
	public void selectCaseNumber() {
		//if(verifyCustomerAccountsPage()) {
		WebElement caseNo = uiTestHelper.waitForObjectToBeClickable(By.xpath("//a[text()='Cases']//following::table[1]//tbody//td//span//span[text()='Damaged Shipment']/preceding::tr[1]//th//span/a"));
		uiTestHelper.clickJS(caseNo);
		//}
	}

	public boolean verifyCustomerAccountPage() {
		WebElement page = uiTestHelper.waitForObject(title);
		return page.isDisplayed();
	}

	public boolean verifyCustomerAccountsPage() {
		WebElement page = uiTestHelper.waitForObject(customerAccountTitle);
		return page.isDisplayed();
	}

	public void clickContactRadiobtn() {
		WebElement radio = uiTestHelper.waitForObject(customerAccContact);
		uiTestHelper.clickJS(radio);
	}

	public void clickNewBooking() {
		WebElement booking = uiTestHelper.waitForObject(newBooking);
		uiTestHelper.clickJS(booking);
	}

	public WebElement newBookingBtn() {
		WebElement booking = uiTestHelper.waitForObject(newBooking);
		return booking;
	}

	public WebElement newQuoteBtn() {
		WebElement quote = uiTestHelper.waitForObject(newQuote);
		return quote;
	}

	public void clickNewQuote() {
		WebElement quoteBtn = uiTestHelper.waitForObject(newQuote);
		uiTestHelper.clickJS(quoteBtn);
	}

	public WebElement newContactBtn() {
		WebElement contact = uiTestHelper.waitForObject(newContact);
		return contact;
	}

	public WebElement phoneNumberField() {
		WebElement phone = uiTestHelper.waitForObject(phoneNumber);
		return phone;
	}

	public WebElement emailField() {
		WebElement emailid = uiTestHelper.waitForObject(email);
		return emailid;
	}

	// to get extension field webelement
	public WebElement extensionField() {
		WebElement ext = uiTestHelper.waitForObject(extension);
		return ext;
	}

	// to get customer name webelement
	public WebElement customername() {
		WebElement name = uiTestHelper.waitForObject(customerName);
		return name;
	}

	// to get account number webelement
	public WebElement accountNum() {
		WebElement account = uiTestHelper.waitForObject(accountNumber);
		return account;
	}

	// to get address webelement
	public WebElement addressDetail() {
		WebElement add = uiTestHelper.waitForObject(address);
		return add;
	}

	// to verify proactive exceptions displayed
	public boolean verifyProactiveExceptions() {
		WebElement pe = uiTestHelper.waitForObject(proactiveException);
		return pe.isDisplayed();
	}
	
	public boolean verifyCaseDisplayed() {
		WebElement CaseDisp=uiTestHelper.waitForObject(caseDisplayed);
		return CaseDisp.isDisplayed();
	}
	public void clickCaseRecord() {
		WebElement caseSearch=uiTestHelper.waitForObjectToBeClickable(caseRecord);
		caseSearch.click();
	}

	// click the case for the Customer Account
	public void clickCase() {
		WebElement search = uiTestHelper.waitForObject(caseSearch);
		search.click();
	}

	/**
	 * Method to check Dabtor Status Label Displayed
	 * 
	 * @return true if Dabtor Status Label Displayed else false
	 */
	public boolean isDabtorStatusLabelDisplayed() {
		WebElement dabtorStatusField = uiTestHelper.waitForObject(dabtorStatusLabel);
		return dabtorStatusField.isDisplayed();
	}

	/**
	 * Method to check DG Approved Status Checkbox checked
	 * 
	 * @return true of DG Approved Status indicator checked box checked else false
	 */
	public boolean isDGApprovedIndicatorChecked() {
		WebElement dgApprovedStatusCheckbox = uiTestHelper.waitForObject(dgApprovedStatusChkbox);
		return dgApprovedStatusCheckbox.isSelected();
	}

	/**
	 * Method to check Regular Order Indicator Checked
	 * 
	 * @return true if Regular Order Indicator Checked else false
	 */
	public boolean isRegularOrderIndicatorChecked() {
		WebElement regularOrderChkBox = uiTestHelper.waitForObject(regularOrderCheckBox);
		return regularOrderChkBox.isSelected();
	}

	/**
	 * Method to read Dabtor status
	 * 
	 * @return
	 */
	public String getDabtorStatus() {
		WebElement dabtorStatusValue = uiTestHelper.waitForObject(dabtorStatusVal);
		return dabtorStatusValue.getText();
	}

	/**
	 * Method to Click on General Enquiry Button
	 */
	public void clickGeneralEnquiryButton() {
		WebElement generalEnquiryBtn = uiTestHelper.waitForObject(generalEnquirybutton);
		uiTestHelper.clickJS(generalEnquiryBtn);
	}
}
