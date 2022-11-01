package com.tnt.ccdobjects;

import java.util.List;

/**
 * This is New Quote page where user create quotes
 */

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class QuotePage {

	WebDriver driver;
	UiTestHelper uiTestHelper;

	// By callerInfoSenderBtn =
	By callerInfoSenderBtn = By.xpath("(//label[@class='slds-radio_button__label']/span[text()='Sender'])[1]");
	By callerInfoReceiverBtn = By.xpath("(//label[@class='slds-radio_button__label']/span[text()='Receiver'])[1]");
	By searchBtnCaller = By.xpath("(//button[contains(text(),'Search')])[1]");
	By quoteInfoTitle=By.xpath("//a[contains(text(),'Quote Information')]");
	// Error message
	By addressErrorMessage = By
			.xpath("//div[@class='forceVisualMessageQueue']/div/div/lightning-icon/span[contains(text(),'error')]");

	By testUserPTContactName = By.xpath("(//a[contains(text(),'TESTUSERPT')])[1]");

	// Quote Validation table
	By addressDetailsTable = By.xpath("//h2[text()='Address Details']/following::table[1]/tbody/tr");
	By addressDetailData = By
			.xpath("//h2[text()='Address Details']/following::table[1]/tbody/tr[1]/td//button[@name='Select']");

	// After Quote Completion
	By quoteSummaryBtn = By.xpath("//button[@name='Booking__c.Quote_Summary']");
	By quoteHistoryTab = By.xpath("//a[contains(text(),'Quote History')]");
	By quoteInformationTab = By.xpath("//a[contains(text(),'Quote Information')]");
	By preSetEmailSentTo = By.xpath("//span[text()='Quote Summary - Sent To']/following::lightning-formatted-text[1]");
	

	public QuotePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	/**
	 * Method to click on Sender Button from Caller Information
	 */
	public void clickSenderButton() {
		// TODO Auto-generated method stub

		WebElement senderButton = uiTestHelper.waitForObject(callerInfoSenderBtn);
		senderButton.click();
	}

	// Method to click on Receiver button
	public void clickReceiverButton() {
		WebElement receiverButton = uiTestHelper.waitForObject(callerInfoReceiverBtn);
		receiverButton.click();

	}

	public boolean errorDisplayed() {
		WebElement searchBtn = uiTestHelper.waitForObject(addressErrorMessage);
		return searchBtn.isDisplayed();
	}

	public boolean isSearchButtonEnable() {
		WebElement searchBtn = uiTestHelper.waitForObject(searchBtnCaller);
		return searchBtn.isEnabled();

	}

	public int getAddressDetailsTableSize() {
		List<WebElement> taskTableSize = uiTestHelper.waitForObjects(addressDetailsTable);
		int size = taskTableSize.size();
		return size;
	}

	public void selectAddressDetail() {
		WebElement addressdetail = uiTestHelper.waitForObject(addressDetailData);
		addressdetail.click();
	}

	public void clickQuoteSummaryButton() {
		WebElement quoteSummary = uiTestHelper.waitForObject(quoteSummaryBtn);
		uiTestHelper.clickJS(quoteSummary);
	}
	
	public boolean verifyQuoteSummaryButton() {
		WebElement quoteSummary = uiTestHelper.waitForObject(quoteSummaryBtn);
		return quoteSummary.isDisplayed();
	}

	public void clickQuoteHistoryTab() {
		WebElement quoteHistory = uiTestHelper.waitForObject(quoteHistoryTab);
		quoteHistory.click();
	}

	public String getPreSetEmailSentTo() {
		WebElement info = uiTestHelper.waitForObject(preSetEmailSentTo);
		return info.getText();
	}
	
	public boolean verifyEmailSentTo() {
		WebElement info = uiTestHelper.waitForObject(preSetEmailSentTo);
		return info.isDisplayed();
	}
	
	public boolean verifyQuoteInfoTitle() {
		WebElement title = uiTestHelper.waitForObject(quoteInfoTitle);
		return title.isDisplayed();
	}
	
	

}
