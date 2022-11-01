package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class CustomerIdentificationPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By customerEnquiry=By.xpath("//h2/span[text()='Customer Enquiry']");
	By accountNumberInput=By.xpath("//input[@name='accountNum']");
	By customerNameInput=By.xpath("//input[@name='customername']");
	By postcodeInput=By.xpath("//input[@name='postcode']");
	By countryDropDown=By.xpath("//button[@name='country']");
	By telephoneNumberInput=By.xpath("//input[@name='telNumber']");
	By depotInput=By.xpath("//input[@name='legacyDepotid']");
	By bookingRefInput=By.xpath("//input[@name='legacyBookingid']");
	By quoteRefInput=By.xpath("//input[@name='quoteId']");
	By searchButton=By.xpath("//button[text()='Search']");
	By clearButton=By.xpath("//button[text()='Clear']");
	By errorMsg=By.xpath("//div[text()='NO RESULTS FOUND']");
	By searchResultTable=By.xpath("//div[@class='slds-scrollable_y']/table");
	By bookingRadioButton=By.xpath("//span[text()='Booking ']");
	By migrationDate=By.xpath("//p[contains(text(),'Migration Date')]/following-sibling::p//lightning-formatted-text");
	By overlapDate=By.xpath("//p[contains(text(),'Overlap Date')]/following-sibling::p//lightning-formatted-text");
	By newCustomer = By.xpath("//button[@title='NewCustomer']");
	By generalEnquiry=By.xpath("//button[@title='GeneralEnquiry']");
	 
	public CustomerIdentificationPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	public boolean verifyCustomerEnquiryPage() {
		WebElement ciPage=uiTestHelper.waitForObject(customerEnquiry);
		return ciPage.isDisplayed();
	}
	public void setAccountNumber(String accountNumber) {
		WebElement ciOptions=uiTestHelper.waitForObject(accountNumberInput);
		ciOptions.clear();
		ciOptions.sendKeys(accountNumber);
	}
	public void setCustomerName(String customerName) {
		WebElement ciOptions=uiTestHelper.waitForObject(customerNameInput);
		ciOptions.clear();
		ciOptions.sendKeys(customerName);
	}
	public void setPostCode(String postCode) {
		WebElement ciOptions=uiTestHelper.waitForObject(postcodeInput);
		ciOptions.clear();
		ciOptions.sendKeys(postCode);
	}
	public void setPhoneNumber(String telephoneNumber) {
		WebElement ciOptions=uiTestHelper.waitForObject(telephoneNumberInput);
		ciOptions.clear();
		ciOptions.sendKeys(telephoneNumber);
	}
	public void setDepot(String depot) {
		WebElement ciOptions=uiTestHelper.waitForObject(depotInput);
		ciOptions.clear();
		ciOptions.sendKeys(depot);
	}
	public void setBookingReference(String bookingRef) {
		WebElement ciOptions=uiTestHelper.waitForObject(bookingRefInput);
		ciOptions.clear();
		ciOptions.sendKeys(bookingRef);
	}
	public void setQuoteRef(String quoteRef) {
		WebElement ciOptions=uiTestHelper.waitForObject(quoteRefInput);
		ciOptions.clear();
		ciOptions.sendKeys(quoteRef);
	}
	public void setCountry(String countryName) {
		WebElement ciOptions=uiTestHelper.waitForObject(countryDropDown);
		ciOptions.click();
		WebElement country=uiTestHelper.waitForObject(By.xpath("//button[@name='country']/following::lightning-base-combobox-item[@data-value='"+countryName+"']"));
		uiTestHelper.scrollIntoView(country);
		country.click();
	}
	public void clickSearchButton() {
		WebElement btn=uiTestHelper.waitForObject(searchButton);
		btn.click();
	}
	public void clickClearButton() {
		WebElement btn=uiTestHelper.waitForObject(clearButton);
		btn.click();
	}
	public boolean verifySearchResultTable() {
		WebElement table=uiTestHelper.waitForObject(searchResultTable);
		return table.isDisplayed();
	}
	public boolean verifyErroronResult() {
		WebElement table=uiTestHelper.waitForObject(errorMsg);
		return table.isDisplayed();
	}
	public void clickSearchResult() {
		WebElement result=uiTestHelper.waitForObject(By.xpath("//div[@class='slds-scrollable_y']/table/tbody/tr[1]/th//lightning-formatted-url/a"));
		uiTestHelper.clickJS(result);
	}
	
	public void clickBookingRadioButton() {
		WebElement btn=uiTestHelper.waitForObjectToBeClickable(bookingRadioButton);
		uiTestHelper.clickJS(btn);
	}
	
	public void clickNewCustomer() {
		WebElement button = uiTestHelper.waitForObjectToBeClickable(newCustomer);
		button.click();
	}
	
	public void clickGeneralEnquiry() {
		WebElement button = uiTestHelper.waitForObjectToBeClickable(generalEnquiry);
		button.click();
	}
	
	//-------------------------------------------------------------------------
	By depotvalue=By.xpath("//lightning-card[1]/article/div[2]/slot/div[2]/div/div[6]/lightning-input/div");
	By depotminerror=By.xpath("//lightning-card[1]/article/div[2]/slot/div[2]/div/div[6]/lightning-input/div[2]");
	By bookingreferror=By.xpath("//lightning-card[1]/article/div[2]/slot/div[2]/div/div[8]/lightning-input/div[2]");
	/**
	 * customer identification : new customer : booking with dangerous goods
	 */
	By cidgbtn=By.xpath("//lightning-card[2]/article/div[2]/slot/div/div/lightning-input/div//span[2]//span[1]");
	By packaginggroupfield=By.xpath("//article/div[2]/slot/ul/div/div/div[6]/div/lightning-combobox/label");
	By packaginggroupvalue=By.xpath("//lightning-card[2]/article/div[2]/slot/ul/div/div/div[6]/div/lightning-combobox/div/lightning-base-combobox/div/div[1]//input");
	By checkboxdryIce1=By.xpath("//lightning-card[2]/article/div[2]/slot/ul/div/div/div[3]/lightning-input/div/span/label");
	By checkboxdryIce2=By.xpath("//lightning-card[2]/article/div[2]/slot/ul[2]/div/div/div[3]/lightning-input/div/span/label");
	By checkboxdryIce3=By.xpath("//lightning-card[2]/article/div[2]/slot/ul[3]/div/div/div[3]/lightning-input/div/span/label");
	By ischeckboxdryice1=By.xpath("//lightning-card[2]/article/div[2]/slot/ul/div/div/div[3]/lightning-input/div/span/input");
	By ischeckboxdryice2=By.xpath("//lightning-card[2]/article/div[2]/slot/ul[2]/div/div/div[3]/lightning-input/div/span/input");
	By ischeckboxdryice3=By.xpath("//lightning-card[2]/article/div[2]/slot/ul[3]/div/div/div[3]/lightning-input/div/span/input");

	public boolean isDepotField() {
		WebElement ciOptions=uiTestHelper.waitForObject(depotInput);
		return ciOptions.isEnabled();
	} 
	public String getDepotFieldValue() {
		WebElement depotf=uiTestHelper.waitForObject(depotInput);
		return depotf.getAttribute("value");
	}	
	public int validateDepotField() {
		int valnum=0;
		WebElement depotf=uiTestHelper.waitForObject(depotInput);
		depotf.clear();
		depotf.sendKeys("12");
		clickSearchButton();
		valnum=valnum+getErrorStatus(depotminerror);
		depotf.clear();
		depotf.sendKeys("1");
		clickSearchButton();
		valnum=valnum+getErrorStatus(depotminerror);
		
		return valnum;
	}
	public int getErrorStatus(By errxpath) {
		WebElement errordepot=uiTestHelper.waitForObject(errxpath);
		String err=errordepot.getText();
		if(err.equalsIgnoreCase("Your entry is too short.")) {
			System.out.println("Error is shown");
			return 1;
		}else {
			System.out.println("No error is shown");
			return 0;
		}		
	}
	public int validateBookingRefField() throws InterruptedException {
		int valnum=0; 
		String bookrefid="";
		WebElement depotf=uiTestHelper.waitForObject(bookingRefInput);
		for(int i=1;i<=5;i++) {
			depotf.clear();
			bookrefid=bookrefid+i;
			depotf.sendKeys(bookrefid);
			clickSearchButton();
			valnum=valnum+getErrorStatus(bookingreferror);
		}					
		return valnum;
	} 
	public String getBookRefFieldValue() {
		WebElement bookf=uiTestHelper.waitForObject(bookingRefInput);
		return bookf.getAttribute("value");
	}
	/**
	 * selection of DG
	 */
	public void clickDangerousGoodsYes() {
		WebElement indicator=uiTestHelper.waitForObject(cidgbtn);
		indicator.click();
	}
	public boolean ispackaginggroupdisplayed() {
		WebElement packaginggroup=uiTestHelper.waitForObject(packaginggroupfield);
		packaginggroup.isDisplayed();
		return true;
	}
	public String getpackaginggroupvalue() {
		WebElement packaginggroup=uiTestHelper.waitForObject(packaginggroupvalue);
		packaginggroup.getAttribute("value");
		return packaginggroup.getAttribute("value");
	}
	public boolean isDryIceFieldEnabled1() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce1);
		dryicefield.isEnabled();
		return dryicefield.isEnabled();
	}
	public void checkDryIceField1() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce1);
		dryicefield.click();
	}
	public boolean isDryIceFieldSelected1() {
		WebElement dryicefield=uiTestHelper.waitForObject(ischeckboxdryice1);
		dryicefield.isSelected();
		return dryicefield.isSelected();
	}
	public boolean isDryIceFieldEnabled2() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce2);
		dryicefield.isEnabled();
		return dryicefield.isEnabled();
	}
	public void checkDryIceField2() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce2);
		dryicefield.click();
	}
	public boolean isDryIceFieldSelected2() {
		WebElement dryicefield=uiTestHelper.waitForObject(ischeckboxdryice2);
		dryicefield.isSelected();
		return dryicefield.isSelected();
	}
	public boolean isDryIceFieldEnabled3() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce3);
		dryicefield.isEnabled();
		return dryicefield.isEnabled();
	}
	public void checkDryIceField3() {
		WebElement dryicefield=uiTestHelper.waitForObject(checkboxdryIce3);
		dryicefield.click();
	}
	public boolean isDryIceFieldSelected3() {
		WebElement dryicefield=uiTestHelper.waitForObject(ischeckboxdryice3);
		dryicefield.isSelected();
		return dryicefield.isSelected();
	}
	public String getMigrationDate() {
		WebElement getMigrationDate = uiTestHelper.waitForObject(migrationDate);
		return getMigrationDate.getText();
	}
	public String getOverLapDate() {
		WebElement getOverlapDate = uiTestHelper.waitForObject(overlapDate);
		return getOverlapDate.getText();
	}
	public Boolean isMigrationDateDisplayed() {
		WebElement noMigrationDate = driver.findElement(migrationDate);			
		return noMigrationDate.isDisplayed();
	}
	public Boolean isOverLapDateDisplayed() {
		WebElement noOverLapDate = driver.findElement(overlapDate);
		return noOverLapDate.isDisplayed();
	}
	public boolean isBookingRefField() {
		WebElement ciOptions=uiTestHelper.waitForObject(bookingRefInput);
		return ciOptions.isEnabled();		
	}
	public boolean isQuoteRefField() {
		WebElement quotef=uiTestHelper.waitForObject(quoteRefInput);
		return quotef.isEnabled();
	}
}
