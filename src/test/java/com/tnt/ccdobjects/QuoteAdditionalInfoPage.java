package com.tnt.ccdobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is the Additional Information page while creating New Quote
 */

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class QuoteAdditionalInfoPage {

	WebDriver driver;
	UiTestHelper uiTestHelper;
	By validServicesButton = By.xpath("//button[@title='Valid Services']");
	By closeButton = By.xpath("//button[contains(text(),'Close')]");
	By saveQuoteButtonn = By.xpath("//button[contains(text(),'Save & Quote')]");
	By closeQuoteButton = By.xpath("(//span[contains(text(),'Close')])[6]");
	By enhancedIndiactor = By.xpath("(//*[contains(@id,'toggle-description')])[2]");
	By cashOrderIndiactor = By.xpath("(//*[contains(@id,'toggle-description')])[4]");
	By collectionDate = By.xpath("//input[@name='collectionDate']");

	By errorMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='error']");
	By bookingTime = By.xpath(
			"//*[contains(text(),'Selected Service')]/following::table[1]//lightning-primitive-cell-factory[@data-label='Booking']//lightning-base-formatted-text");
	By collectionAreaTime = By.xpath(
			"//*[contains(text(),'Selected Service')]/following::table[1]//lightning-primitive-cell-factory[@data-label='In Collection Area']//lightning-base-formatted-text");
	By selfboughtTime = By.xpath(
			"//*[contains(text(),'Selected Service')]/following::table[1]//lightning-primitive-cell-factory[@data-label='Self-Brought']//lightning-base-formatted-text");
	By quoteSuccessMsg = By
			.xpath("//span[contains(text(),'Quote is created successfully. Quote Reference Number is: ')]");
	
	By recalculatePrice=By.xpath("//lightning-card//lightning-button/button[text()='Recalculate Price']");

	public QuoteAdditionalInfoPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	/**
	 * Method to click Valid Services button
	 */
	public void clickValidServices() {
		WebElement validServices = uiTestHelper.waitForObject(validServicesButton);
		uiTestHelper.clickJS(validServices);
	}

	/**
	 * select Valid service
	 */


	// Valid Service Selection
	public void getValidServices() {
		WebElement selectService = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[@data-row-key-value='EX200']//button[text()='Select']"));
		uiTestHelper.clickJS(selectService);
	}

	public void selectService() {
		WebElement selectService = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//button[text()='Select']"));
		uiTestHelper.scrollIntoView(selectService);
		uiTestHelper.clickJS(selectService);
	}

	/**
	 * Method to click on close button after views service details
	 */
	public void clickCloseButton() {
		WebElement closeBtn = uiTestHelper.waitForObject(closeButton);
		uiTestHelper.scrollIntoView(closeBtn);
		closeBtn.click();
	}

	/**
	 * Click on Save and Quote button
	 */
	public void clickSaveQuoteBtn() {
		WebElement saveQuoteBtn = uiTestHelper.waitForObject(saveQuoteButtonn);
		uiTestHelper.scrollIntoView(saveQuoteBtn);
		saveQuoteBtn.click();

	}

	/**
	 * Method to click on Close button
	 */
	public void closeQuotePage() {

		WebElement validServices = uiTestHelper.waitForObject(closeQuoteButton);
		uiTestHelper.clickJS(validServices);
	}

	/**
	 * Method to verify on Cash Order Indicator
	 */
	public boolean verifyCashOrderIndiactor() {

		WebElement validServices = uiTestHelper.waitForObject(cashOrderIndiactor);
		return validServices.isEnabled();
	}

	/**
	 * Method to enter Collection date
	 *
	 * @param date
	 */
	public void enterCollectionDate(String Date) {
		WebElement date = uiTestHelper.waitForObject(collectionDate);
		//date.click();
		uiTestHelper.clickJS(date);
		date.clear();
		date.sendKeys(Date);

	}

	public boolean verifyErrorMessage() {
		WebElement msg = uiTestHelper.waitForObject(errorMessage);
		return msg.isDisplayed();
	}

	public String getBackDate(String format) throws ParseException {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, -2);
		Date backdate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String exp_date = sdf.format(backdate);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	public String getFutureDate(String format) throws ParseException {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, 2);
		Date backdate = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String exp_date = sdf.format(backdate);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	// quote to booking: special services
	public String getServiceNameQuote() {
		WebElement specialservice1 = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[1]/td[2]//lightning-base-formatted-text[1]"));
		return specialservice1.getText();
	}

	public String getServiceNameQtoBK() {
		WebElement specialservice1 = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[1]/td[2]//lightning-base-formatted-text[1]"));
		return specialservice1.getText();
	}

	public boolean verifyBookingBeforeTime() {
		WebElement ele = uiTestHelper.waitForObject(bookingTime);
		return ele.isDisplayed();
	}

	public boolean verifyCollectionAreaTime() {
		WebElement ele = uiTestHelper.waitForObject(collectionAreaTime);
		return ele.isDisplayed();
	}

	public boolean verifySelfBoughtTime() {
		WebElement ele = uiTestHelper.waitForObject(selfboughtTime);
		return ele.isDisplayed();
	}

	public String getBookingBeforeTime() {
		WebElement ele = uiTestHelper.waitForObject(bookingTime);
		return ele.getText();
	}

	public String getCollectionAreaTime() {
		WebElement ele = uiTestHelper.waitForObject(collectionAreaTime);
		return ele.getText();
	}

	public String getSelfBoughtTime() {
		WebElement ele = uiTestHelper.waitForObject(selfboughtTime);
		return ele.getText();
	}
	public boolean verifyQuoteMsg() {
		WebElement ele=uiTestHelper.waitForObject(quoteSuccessMsg);
		return ele.isDisplayed();
	}
	public String getQuoteMsg() {
		WebElement ele=uiTestHelper.waitForObject(quoteSuccessMsg);
		return ele.getText();
	}
	public void selectOptions(String service) {
		WebElement selectProduct = uiTestHelper.waitForObject(By.xpath("//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[@data-row-key-value='"+service+"']//button[@name='selectpo']"));
		uiTestHelper.clickJS(selectProduct);
	}
	public boolean verifyRecalculatePrice() {
		WebElement price = uiTestHelper.waitForObjectToBeClickable(recalculatePrice);
		return price.isDisplayed();
	}
	public void clickRecalculatePrice() {
		WebElement price = uiTestHelper.waitForObjectToBeClickable(recalculatePrice);
		uiTestHelper.clickJS(price);
	}
}
