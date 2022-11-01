
package com.tnt.ccdobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

/**
 * Quote detail page after creating Quote
 */
public class QuoteDetailPage {

	WebDriver driver;
	UiTestHelper uiTestHelper = new UiTestHelper();
	By recordTypeAsQuote = By
			.xpath("(//div/p[contains(text(),'Record Type')])[1]//following::span[contains(text(),'Quote')][1]");
	By statusAsUnconverted = By.xpath(
			"(//div/p[contains(text(),'Status')])[1]//following::lightning-formatted-text[contains(text(),'Unconverted')]");
	By service = By.xpath("(//div/p[contains(text(),'Service')])[1]//following::lightning-formatted-text[1]");
	By collectionDate = By
			.xpath("(//div/p[contains(text(),'Collection Date')])[1]/following::lightning-formatted-text[1]");
	By convertedDate = By
			.xpath("(//span[contains(text(),'Converted Date')])[1]/following::lightning-formatted-text[1]");
	By convertToBookingButton = By.xpath("(//button[contains(text(),'Convert To Booking')])");

	By quoteSummaryBtn = By.xpath("//button[@name='Booking__c.Quote_Summary']");
	By quoteSummarySection = By.xpath("//b[contains(text(),'Quote Summary')]");
	By sendEmailButton = By.xpath("//button[contains(text(),'Send Email')]");

	By emailID = By.xpath("//lightning-formatted-email[@class='slds-border_bottom slds-form-element__static']");
	By sentDate = By.xpath("(//lightning-formatted-text[@class='slds-border_bottom slds-form-element__static'])[3]");
	By sentBy = By.xpath(
			"//*[contains(@id,'tab')]/slot/flexipage-component2/slot/c-field-set-record-form/lightning-card/article/div[2]/slot/div/lightning-record-form/lightning-record-edit-form/form/slot/div/div/div[3]/div[2]/lightning-output-field/div/lightning-formatted-lookup/a");
	// Additional Info Objects
	By additionalInfo = By.xpath("//a[@data-label='Additional Information']");
	By availableServicesGreenTick = By.xpath("//img[@src='/changemgmt/img/checkgreen.png']");
	By quotetoBookingStatus = By.xpath("(//p[text()='Status']/following::lightning-formatted-text[1])[2]");
	By availableServices = By.xpath("//span[@title='Available Services']");
	By emailSuccessMsg = By.xpath("(//*[contains(text(),'Email has been sent successfully')])");
	By availableServicesonQuoteSummary=By.xpath("//b[text()='Quote Summary']/following::span[@title='Available Services']");
	By services=By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr");
	By quoteExpiryDate=By.xpath("//p[@title='Quote Expiry Date']//following::lightning-formatted-text[1]");
	

	public QuoteDetailPage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Method to verify "Record Type" field value as "Quote"
	 * 
	 * @return Boolean: Is "Record Type" value display
	 */
	public boolean isRecordTypeAsQuote() {
		WebElement recordTypeValue = uiTestHelper.waitForObject(recordTypeAsQuote);
		return recordTypeValue.isDisplayed();
	}

	/**
	 * Method to verify Status field value as Unconverted
	 * 
	 * @return Boolean: Is "Status" value display
	 */
	public boolean isStatusAsUnconverted() {
		WebElement statusValue = uiTestHelper.waitForObject(statusAsUnconverted);
		return statusValue.isDisplayed();
	}

	/**
	 * Method to verify Service field displayed
	 * 
	 * @return Boolean: Is "Service" value display
	 */
	public boolean isServiceDisplayed() {
		WebElement serviceValue = uiTestHelper.waitForObject(service);
		return serviceValue.isDisplayed();
	}

	/**
	 * Method to return Collection Date
	 * 
	 * @return Date
	 */
	public String getCollectionDate() {
		WebElement collectionDt = uiTestHelper.waitForObject(collectionDate);
		return collectionDt.getText();
	}

	/**
	 * Method to return Converted Date
	 * 
	 * @return date
	 */
	public String getConvertedDate() {
		WebElement collectionDt = uiTestHelper.waitForObject(convertedDate);
		return collectionDt.getText();
	}

	/**
	 * Method to click on Convert To Booking Button
	 */
	public void clickConvertToBookingBtn() {
		WebElement convertToBookingBtn = uiTestHelper.waitForObject(convertToBookingButton);
		convertToBookingBtn.click();
	}
	
	public boolean verifyConvertToBookingBtn() {
		WebElement convertToBookingBtn = uiTestHelper.waitForObject(convertToBookingButton);
		return convertToBookingBtn.isDisplayed();
	}

	/**
	 * Method to click on Quote Summary button
	 */
	public void clickQuoteSummaryButton() {
		WebElement convertToBookingBtn = uiTestHelper.waitForObject(quoteSummaryBtn);
		uiTestHelper.clickJS(convertToBookingBtn);
	}

	/**
	 * Method to click on Quote Summary button
	 */
	public boolean verifyQuoteSummaryButton() {
		WebElement convertToBookingBtn = uiTestHelper.waitForObjectToBeClickable(quoteSummaryBtn);
		return convertToBookingBtn.isEnabled();
	}

	/**
	 * Method to click on Quote Additional Info tab
	 */
	public void clickQuoteAdditionalInfoTab() {
		WebElement tab = uiTestHelper.waitForObject(additionalInfo);
		uiTestHelper.clickJS(tab);
	}

	/**
	 * Method to check Quote Summary section displayed after click on Quote summary
	 * button
	 * 
	 * @return Boolean
	 */
	public boolean isQuoteSummaryDisplayed() {
		WebElement quoteSummary = uiTestHelper.waitForObject(quoteSummarySection);
		return quoteSummary.isDisplayed();
	}

	/**
	 * Method to click on Send Email button
	 */
	public void clickSendEmail() {
		WebElement sendEmailBtn = uiTestHelper.waitForObject(sendEmailButton);
		sendEmailBtn.click();
	}

	public String getEmailId() {
		WebElement emailIdTxt = uiTestHelper.waitForObject(emailID);
		return emailIdTxt.getText();
	}

	/**
	 * Method to get Quote summary Sent Date
	 * 
	 * @return
	 */
	public String getQuoteSummarySentDate() {
		WebElement sentDateTxt = uiTestHelper.waitForObject(sentDate);
		return sentDateTxt.getText();
	}

	/**
	 * Method to return QuoteSummarySentBy
	 * 
	 * @return
	 */
	public String getQuoteSummarySentBy() {
		WebElement sentByTxt = uiTestHelper.waitForObject(sentBy);
		return sentByTxt.getText();
	}

	public boolean checkGreenTick(String serviceName) {
		WebElement tableRow = driver
				.findElement(By.xpath("//span[contains(text(),'Available Services')]/following::table[1]//span[text()='"
						+ serviceName + "']"));
		return tableRow.findElement(availableServicesGreenTick).isDisplayed();
	}
	
	public boolean isAvailableServicesDisplayed() {
		WebElement service = uiTestHelper.waitForObject(availableServicesonQuoteSummary);
		return service.isDisplayed();
		
	}
	
	
	
	

	public String getBookingStatus() {
		WebElement status = uiTestHelper.waitForObject(quotetoBookingStatus);
		return status.getText();
	}

	public HashedMap<String, String> getServiceswithDeliveryDate() {
		List<WebElement> table = uiTestHelper.waitForObjects(
				By.xpath("//span[@title='Available Services']/following::table[1]//tbody/tr"));
		int Size = table.size();
		System.out.println(Size);
		HashedMap<String, String> map = new HashedMap<String, String>();
		String service = null, deliveryDate = null;
		for (int i = 1; i <= Size; i++) {
			service = driver
					.findElement(By.xpath(
							"(//span[@title='Available Services']/following::table[1]//tbody/tr//a//span)[" + i + "]"))
					.getText();
			deliveryDate = driver.findElement(By.xpath(
					"(//span[@title='Available Services']/following::table[1]//tbody/tr//td[@data-label='Delivery Date']//lightning-formatted-text)["
							+ i + "]"))
					.getText();
			//System.out.println(service+ " "+deliveryDate);
			map.put(service, deliveryDate);
		}
		return map;
	}

	// Success Message
	public boolean verifyEmailsuccessMsg() {
		WebElement msg = uiTestHelper.waitForObject(emailSuccessMsg);
		return msg.isDisplayed();
	}
	
	public void getAllServicesForQuote() {
		List<WebElement> serviceList=uiTestHelper.waitForObjects(services);
		HashMap<String,String> map=new HashMap<String,String>();
		int serviceSize=serviceList.size();
		for(int i=1;i<=serviceSize;i++) {
			WebElement service=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//lightning-base-formatted-text"));
			String availableService=service.getText();
			map.put("Service["+i+"]", availableService);
			}
		Driver.report_MapValues("All available Services: ", map);
	}
	
	public void getSelectedService(String Message) {
		List<WebElement> serviceList=uiTestHelper.waitForObjects(services);
		HashMap<String,String> map=new HashMap<String,String>();
		int serviceSize=serviceList.size();
		for(int i=1;i<=serviceSize;i++) {
			String defaultService=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]")).getAttribute("aria-selected");
			System.out.println(defaultService);
			if(defaultService.equals("true")) {
			WebElement service=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//lightning-base-formatted-text"));
			String availableService=service.getText();
			map.put("", availableService);
			}
			
		}
		Driver.report_MapValues(Message, map);
	}
	
	public void selectServiceonQuoteSummary(String serviceName){
		List<WebElement> serviceList=uiTestHelper.waitForObjects(services);
		HashMap<String,String> map=new HashMap<String,String>();
		int serviceSize=serviceList.size();
		for(int i=1;i<=serviceSize;i++) {
			String defaultService=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]")).getAttribute("aria-selected");
			System.out.println(defaultService);
			if(defaultService.equals("false")) {
				WebElement service=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//lightning-base-formatted-text"));
				String unselectedService=service.getText();
				if(unselectedService.equalsIgnoreCase(serviceName)) {
					WebElement serviceTickBox=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//label//span[1]"));
					uiTestHelper.scrollIntoView(serviceTickBox);
					serviceTickBox.click();
				}
				}
	}
}
	public void deselectServiceonQuoteSummary(String serviceName){
		List<WebElement> serviceList=uiTestHelper.waitForObjects(services);
		HashMap<String,String> map=new HashMap<String,String>();
		int serviceSize=serviceList.size();
		for(int i=1;i<=serviceSize;i++) {
			String defaultService=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]")).getAttribute("aria-selected");
			System.out.println(defaultService);
			if(defaultService.equals("true")) {
				WebElement service=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//lightning-base-formatted-text"));
				String unselectedService=service.getText();
				if(unselectedService.equalsIgnoreCase(serviceName)) {
					WebElement serviceTickBox=uiTestHelper.waitForObject(By.xpath("//b[text()='Quote Summary']/following::table/tbody//tr["+i+"]//label//span[1]"));
					uiTestHelper.scrollIntoView(serviceTickBox);
					serviceTickBox.click();
				}
				}
	}
}
	public boolean isQuoteExpiryDateEnabled() {
		WebElement qedate=uiTestHelper.waitForObject(quoteExpiryDate);
		return qedate.isDisplayed();
	}
	public String getQuoteExpiryDate() {
		WebElement qedate=uiTestHelper.waitForObject(quoteExpiryDate);
		return qedate.getText();
	}
}
