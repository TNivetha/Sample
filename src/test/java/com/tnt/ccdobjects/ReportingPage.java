package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;

public class ReportingPage extends Driver{
	UiTestHelper uiTestHelper;
	WebDriver driver; 
	
	By reportsMenuDropDownOption = By.xpath("//a[@data-itemid='Report']");
	By publicReportSideOption = By.xpath("//a[contains(text(),'Public Reports')]");
	By newReportButton = By.xpath("//div[@title='New Report']");
	By reportTypeContinueButton = By.xpath("//button[@class='slds-button slds-button_brand report-type-continue']");
	By reportingOptionContact = By.xpath("//a[text()='Contacts']/parent::li");
	By searchReportTypes = By.xpath("//input[@placeholder='Search Report Types...']");
	By reportBuilderIframe = By.xpath("//iframe[@title='Report Builder']"); 
	By fieldsLeftPaneMenu = By.xpath("//div[text()='Fields']/button"); 
	By groupRowsTextBox = By.xpath("//input[@placeholder='Add group...']"); 
	By columnsTextBox = By.xpath("//input[@placeholder='Add column...']");
	By saveAndRunButton = By.xpath("//button[text()='Save & Run']");
	By reportDescriptionPopUpField = By.xpath("//textarea[@id='reportDescription']"); 
	By selectReportButton = By.xpath("//button[@title='Select Folder']"); 
	By publicReportsMenuOption = By.xpath("//button[@title='Public Reports']"); 
	By selectFolderButton = By.xpath("//div[@class='modal-footer slds-modal__footer']//button[@title='Select Folder']");
	By newContactsReportTitle = By.xpath("//h1[contains(text(),'New Contacts Report')]");
	
	public ReportingPage(WebDriver driver) {
		uiTestHelper = new UiTestHelper();
		this.driver = driver;
		
	}
	public void clickReportingFromGlobalMenu() {
		WebElement reports = uiTestHelper.waitForObject(reportsMenuDropDownOption);
		reports.click();
	}
	public void clickPublicReportsSideOption() {
		WebElement publicReport = uiTestHelper.waitForObject(publicReportSideOption);
		publicReport.click();
	}
	public void clickNewReportButton() {
		WebElement newReportBtn = uiTestHelper.waitForObject(newReportButton);
		newReportBtn.click();
	}
	public void clickContinueButton() {
		WebElement continueBtn = uiTestHelper.waitForObject(reportTypeContinueButton);
		continueBtn.click();
	}
	public void selectContactOptionInMenu() {
		uiTestHelper.waitForObject(reportBuilderIframe);
		WebElement reportsIframe = uiTestHelper.waitForObject(reportBuilderIframe);
		driver.switchTo().frame(reportsIframe);
		WebElement contactOption = uiTestHelper.waitForObject(reportingOptionContact);
		contactOption.click();
		clickContinueButton();
	}
	public void clickFieldsLeftPaneMenu() {
		uiTestHelper.waitForObject(newContactsReportTitle);
		WebElement fieldsLeftPane = uiTestHelper.waitForObject(fieldsLeftPaneMenu);
		fieldsLeftPane.click();
	}
	public void clickGroupRowsBoxAndSendKeys(String filterOption) {
		//driver.switchTo().parentFrame();
		WebElement filterBox = uiTestHelper.waitForObject(groupRowsTextBox);
		filterBox.click();
		filterBox.sendKeys(filterOption, Keys.ENTER);
	}
	public void clickColumnsBoxAndSendKeys(String filterOption) {
		WebElement filterBox = uiTestHelper.waitForObject(columnsTextBox);
		filterBox.sendKeys(filterOption, Keys.ENTER);
	}
	public void clickSaveAndRunButton() {
		WebElement saveAndRunBtn = uiTestHelper.waitForObject(saveAndRunButton);
		saveAndRunBtn.click();
	}
	public void provideNameForReportDescription() {
		WebElement reportDescriptionBox = uiTestHelper.waitForObject(reportDescriptionPopUpField);
		reportDescriptionBox.click();
	}
	public void selectFolderButton() {
		WebElement selectFolder = uiTestHelper.waitForObject(selectReportButton);
		selectFolder.click();
	}
	public void clickPublicReportsMenuOption() {
		WebElement publicFolder = uiTestHelper.waitForObject(publicReportsMenuOption);
		publicFolder.click();
	}
	public void selectFolderOnPublicReportsWindow() {
		WebElement selectFolder = uiTestHelper.waitForObject(selectFolderButton);
		selectFolder.click();
	}
}
