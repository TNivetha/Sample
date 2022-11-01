package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class RatingInvoicePage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By invoiceSummary=By.xpath("//span[contains(text(),'Invoice Summary')]");
	By invoiceClosebtn=By.xpath("//button[contains(text(),'Close')]");
	By iframeRatingInvoice=By.xpath("//div[@class='oneAlohaPage']//iframe");
	By invoiceSequance=By.xpath("(//div[@class='slds-scrollable_y'])[1]/table/tbody/tr[1]/th//span//lightning-base-formatted-text");
	By invoiceNumber=By.xpath("(//div[@class='slds-scrollable_y'])[1]/table/tbody/tr[1]/td[2]//span//lightning-base-formatted-text");
	By ratingDetails=By.xpath("//span[contains(text(),'Rating Details')]");
	By rateCard=By.xpath("//b[contains(text(),'Ratecard Version code')]/following::dd[1]");
	By invoiceCostTable=By.xpath("//span[contains(text(),'Invoice Cost BreakDown Details')]/following::div[@class='slds-card__body']//table/tbody");


	public RatingInvoicePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}
	public void switchToFrameIR() {
		WebElement frame=uiTestHelper.waitForObject(iframeRatingInvoice);
		driver.switchTo().frame(frame);
	}
	public boolean getInvoiceSummary() {
		WebElement deliveryArea=uiTestHelper.waitForObject(invoiceSummary);
		return deliveryArea.isDisplayed();
	}
	public void clickInvoiceRatingClosebtn() {
		WebElement invoicerate=uiTestHelper.waitForObject(invoiceClosebtn);
		invoicerate.click();
	}
	public String getInvoiceSequance() {
		WebElement invoice=uiTestHelper.waitForObject(invoiceSequance);
		return invoice.getText();
	}
	public String getInvoiceNumber() {
		WebElement invoice=uiTestHelper.waitForObject(invoiceNumber);
		return invoice.getText();
	}

	public String getRatingDetails() {
		WebElement rating=uiTestHelper.waitForObject(ratingDetails);
		return rating.getText();
	}
	public String getRatingCard() {
		WebElement rating=uiTestHelper.waitForObject(rateCard);
		return rating.getText();
	}
	public WebElement getInvoiceCostTable() {
		WebElement table=uiTestHelper.waitForObject(invoiceCostTable);
		return table;
	}

}
