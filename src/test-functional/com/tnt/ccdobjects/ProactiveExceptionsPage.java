package com.tnt.ccdobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class ProactiveExceptionsPage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By recentlyViewed=By.xpath("//span[text()='Recently Viewed']");
	By searchItems=By.xpath("//div[@class='container']//input");
	By peSearchItems=By.xpath("(//div[@class='container']//input)[3]");
	By exceptionTable=By.xpath("//span[text()='Proactive Exceptions']/following::table[1]/tbody/tr");
	By acceptException=By.xpath("//div[text()='Accept']");
	By myOpenCases=By.xpath("//mark[text()='1 - My Open Cases']");
	By searchCasesInput=By.xpath("//input[@name='Case-search-input']");
	//X104OKL Date:30-Jul-21
	By peQueueSearchInput=By.xpath("//input[@class='slds-input default input uiInput uiInputTextForAutocomplete uiInput--default uiInput--input']");
	By caseUpdateColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Case Update']");
	By caseNumberColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Case Number']");
	By cosgnNumberColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Consignment #']");
	By slaStatusColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='SLA Status']");
	By slaColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='SLA']");
	By subjectColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Subject']");
	By caseTypeColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Case Type']");
	By dateTimeColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Date/Time Opened']");
	By dueDateColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Due Date']");
	By revDueDateColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Revised Due Date']");
	By senCouTerColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Sender's Country/Territory']");
	By recCouTerColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title=\"Receiver's Country/Territory\"]");
	//By recCouTerColumnHeader=By.xpath("//span[contains(text(),'Receiver') and contains(text(),'Country/Territory')]");
	//By recCouTerColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th//span[contains(text(),'Receiver') and contains(text(),'Country/Territory')]");
	By updatedCosgnStatusDescColumnHeader=By.xpath("//span[text()='Cases']/following::table[1]/thead/tr/th[@title='Updated Consignment Status Description']");
	By peOwnerColumnHeader=By.xpath("//span[text()='PE Owner']");
	By proactiveExceptionColumnHeader=By.xpath("//span[text()='Proactive Exception']");
	By controllingStationCountryColumnHeader=By.xpath("//span[text()='Controlling Station Country']");
	By senderCountryColumnHeader=By.xpath("//span[text()='Sender's Country']");
	By senderAccountNameColumnHeader=By.xpath("//span[@class='slds-truncate' and contains(text(), 'Sender's Account Name')]");
	
	public ProactiveExceptionsPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}
	public void clickRecentlyViewedItems() {
		WebElement recentview=uiTestHelper.waitForObjectToBeClickable(recentlyViewed);
		uiTestHelper.clickJS(recentview);
	}
	public void searchInput(String input) {
		WebElement inputSearch=uiTestHelper.waitForObject(searchItems);
		inputSearch.sendKeys(input);
	}
	public void peSearchInput(String input) {
		WebElement inputSearch=uiTestHelper.waitForObject(peSearchItems);
		inputSearch.sendKeys(input);
	}
	public void searchCase(String consignmentNo) {
		WebElement button=uiTestHelper.waitForObject(searchCasesInput);
		button.sendKeys(consignmentNo);
		uiTestHelper.actionDoubleClick(button);
	}
	public void clickAssignedInput(String input) {
		By inputSelect=By.xpath("//mark[text()='"+input+"']");
		WebElement in=uiTestHelper.waitForObject(inputSelect);
		in.click();
	}
	
	public int getExceptionTableSize() {
		List<WebElement> TableSize=uiTestHelper.waitForObjects(exceptionTable);
		int size=TableSize.size();
		return size;
	}
	public void clickAcceptException() {
		WebElement accept=uiTestHelper.waitForObject(acceptException);
		accept.click();
	}
	//X104OKL Date:30-Jul-21
	public void searchPEQueue(String inputQueueName) {
		WebElement inputSearchPEQueue=uiTestHelper.waitForObject(peQueueSearchInput);
		inputSearchPEQueue.sendKeys(inputQueueName);
	}
	public void clickAssignedInputPE(String inputQueueName) {
		By inputSelect=By.xpath("//mark[text()='"+inputQueueName+"']");
		WebElement in=uiTestHelper.waitForObject(inputSelect);
		in.click();
	}
	public void inputMyCaseQueue(String queueName) {
		WebElement inputMyCases=uiTestHelper.waitForObject(peQueueSearchInput);
		inputMyCases.sendKeys(queueName);
	}
	
	public void clickMyOpenCases() {
		WebElement myCases=uiTestHelper.waitForObject(myOpenCases);
		myCases.click();
	}
	/**
	 * Method to check Case Update column visible
	 * @return
	 */
	public boolean iscaseNumberColumnHeaderVisible() {
		WebElement caseNemberCol=uiTestHelper.waitForObject(caseNumberColumnHeader);
		return caseNemberCol.isDisplayed();
	}

	public boolean isCosgnNumColumnVisible() {
		WebElement cosgnNumCol=uiTestHelper.waitForObject(cosgnNumberColumnHeader);
		return cosgnNumCol.isDisplayed();
	}
	public boolean isSlaStatusColumnVisible() {
		WebElement slaStatusCol=uiTestHelper.waitForObject(slaStatusColumnHeader);
		return slaStatusCol.isDisplayed();
	}
	public boolean isSlaColumnVisible() {
		WebElement slaCol=uiTestHelper.waitForObject(slaColumnHeader);
		return slaCol.isDisplayed();
	}
	public boolean isSubjectColumnVisible() {
		WebElement subjectCol=uiTestHelper.waitForObject(subjectColumnHeader);
		return subjectCol.isDisplayed();
	}
	public boolean isCaseTypeColumnVisible() {
		WebElement caseTypeCol=uiTestHelper.waitForObject(caseTypeColumnHeader);
		return caseTypeCol.isDisplayed();
	}
	public boolean isDateTimeColumnVisible() {
		WebElement dateTimeCol=uiTestHelper.waitForObject(dateTimeColumnHeader);
		return dateTimeCol.isDisplayed();
	}
	public boolean isDueDateColumnVisible() {
		WebElement dueDateCol=uiTestHelper.waitForObject(dueDateColumnHeader);
		return dueDateCol.isDisplayed();
	}
	public boolean isCaseUpdateColumnVisible() {
		WebElement caseUpdateColumnEle=uiTestHelper.waitForObjectwithException(caseUpdateColumnHeader,5);
		return caseUpdateColumnEle.isDisplayed();
	}
	public boolean isRevDueDateColumnVisible() {
		WebElement ele = driver.findElement(By.xpath("//div[@class='uiScroller scroller-wrapper scroll-bidirectional native']"));
		uiTestHelper.clickJS(ele) ;
		WebElement revDueDateCol=uiTestHelper.waitForObject(revDueDateColumnHeader);
		return revDueDateCol.isDisplayed();
	}
	public boolean isSenCouTerColumnVisible() {
		WebElement senCouTerCol=uiTestHelper.waitForObject(senCouTerColumnHeader);
		//uiTestHelper.scrollIntoView(senCouTerCol);
		return senCouTerCol.isDisplayed();
	}
	public boolean isRecCouTerColumnVisible() {
		WebElement recCouTerCol=uiTestHelper.waitForObject(recCouTerColumnHeader);
		return recCouTerCol.isDisplayed();
	}
	public boolean isUpdatedCosgnStatusDescColumnVisible() {
		WebElement updatedCosgnStatusDescCol=uiTestHelper.waitForObject(updatedCosgnStatusDescColumnHeader);
		return updatedCosgnStatusDescCol.isDisplayed();
	}
	public boolean isPEOwnerColumnVisible() {
		WebElement peOwnerColumn=uiTestHelper.waitForObject(peOwnerColumnHeader);
		return peOwnerColumn.isDisplayed();
	}
	public boolean isProactiveExceptionColumnVisible() {
		WebElement proactiveExceptionColumn=uiTestHelper.waitForObject(proactiveExceptionColumnHeader);
		return proactiveExceptionColumn.isDisplayed();
	}
	public boolean isControllingStationCountryColumnVisible() {
		WebElement controllingStationCountryColumn=uiTestHelper.waitForObject(controllingStationCountryColumnHeader);
		return controllingStationCountryColumn.isDisplayed();
	}
	public boolean isSenderAccountNameColumnVisible() {
		WebElement senderAccountNameColumn=uiTestHelper.waitForObject(senderAccountNameColumnHeader);
		return senderAccountNameColumn.isDisplayed();
	}
	public boolean isSenderCountryColumnVisible() {
		WebElement senderCountryColumn=uiTestHelper.waitForObject(senderCountryColumnHeader);
		return senderCountryColumn.isDisplayed();
	}
	
}
