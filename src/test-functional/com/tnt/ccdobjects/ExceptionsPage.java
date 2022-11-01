package com.tnt.ccdobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class ExceptionsPage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By exceptionLink=By.xpath("//a[contains(text(),'EXC-')]");
	By updatedConsignmentStatus=By.xpath("//button[@title='Edit Updated Consignment Status']");
	By conStatusDesc= By.xpath("//button[@title='Edit Updated Consignment Status Description']");
	By saveExc=By.xpath("//button[@name='SaveEdit']");
	By saveChangesinExc=By.xpath("//button[@class='slds-button slds-button_brand saveBtn']");
	By inputConsignmentStatus=By.xpath("//input[@name='Current_Consignment_Status__c']");
	By inputConStatusDesc= By.xpath("//input[@name='Current_Consignment_Status_Description__c']");
	
	public ExceptionsPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}
	public void clickExceptionLink() {
		WebElement clickExceptionLink=uiTestHelper.waitForObjectToBeClickable(exceptionLink);
		uiTestHelper.actionClick(clickExceptionLink);
	}

	public void clickUpdatedConsignmentStatus() {
		WebElement updatedConsgn = uiTestHelper.waitForObjectToBeClickable(updatedConsignmentStatus);
		uiTestHelper.scrollIntoView(updatedConsgn);
		uiTestHelper.clickJS(updatedConsgn);
	}
	public void setUpdatedConsignmentStatus(String statusCode) {
		WebElement inputUpdatedConsignment = uiTestHelper.waitForObject(inputConsignmentStatus);
		inputUpdatedConsignment.clear();
		inputUpdatedConsignment.sendKeys(statusCode);
		inputUpdatedConsignment.sendKeys(Keys.ENTER);
	}
	public void clickUpdatedConsignmentStatusDesc() {
		WebElement updatedConsgnDesc = uiTestHelper.waitForObjectToBeClickable(conStatusDesc);
		uiTestHelper.scrollIntoView(updatedConsgnDesc);
		uiTestHelper.clickJS(updatedConsgnDesc);
	}
	public void setUpdatedConsignmentStatusDesc(String statusCode) {
		WebElement inputUpdatedConsignmentdesc = uiTestHelper.waitForObject(inputConStatusDesc);
		inputUpdatedConsignmentdesc.clear();
		inputUpdatedConsignmentdesc.sendKeys(statusCode);
		inputUpdatedConsignmentdesc.sendKeys(Keys.ENTER);
	}
	public void clickSaveExc() {
		WebElement saveExcBtn=uiTestHelper.waitForObjectToBeClickable(saveExc);
		uiTestHelper.clickJS(saveExcBtn);
	}
	public void clickSaveChangesInExc() {
		WebElement saveChangesExcBtn=uiTestHelper.waitForObjectToBeClickable(saveChangesinExc);
		uiTestHelper.clickJS(saveChangesExcBtn);
	}
}
