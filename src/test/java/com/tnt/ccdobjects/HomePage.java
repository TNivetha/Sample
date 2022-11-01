package com.tnt.ccdobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class HomePage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By homepageVerification = By.xpath("//span[@title='CCD Customer Care Desktop']");
	By dropDownMenu = By.xpath("//button[@title='Show Navigation Menu']/lightning-primitive-icon");

	By trackAndTrace = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Track and Trace CCD')]");
	By proactiveExceptions = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Proactive Exceptions')]");
	By cases = By.xpath("//a[@data-label='Cases']//span[contains(text(),'Cases')]");
	By searchBox = By.xpath("//div[@class='forceSearchAssistantDialog']//input[@type='search']");
	By searchButton = By.xpath("//div[@data-aura-class='forceSearchAssistant']/button");
	By contactsGlobalDropDown = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Contacts')]");
	By searchBoxInput = By.xpath("//input[@placeholder='Search...']");

	By bookings = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Bookings')]");
	By homeDropdownMenuOption = By.xpath("//a[@data-itemid='Home']");

	By globalDropDown = By.xpath("//lightning-base-combobox[@class='slds-combobox_container']//input");
	By customerAccounts = By.xpath("(//span[@title='Customer Accounts'])[1]");
	By customerIdentification = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Customer Identification')]");
	By bookingException = By.xpath("//*[@id='navMenuList']//span[contains(text(),'Booking Exception')]");
	By caseSearchInput = By.xpath("//input[@name='Case-search-input']");
	By bookingExceptionSearchInput = By.xpath("//input[@name='Booking_Exception__c-search-input']");
	By caseSearch = By.xpath("//input[@name='Case-search-input']");
	// New Customer
	By newCustomer = By.xpath("//button[@title='NewCustomer']");
	By serachBookingInput = By.xpath("//input[@name='Booking__c-search-input']");
	By customerAccountDropDown = By.xpath("//a[@data-itemid='Customer_Account__c']");
	By searchCustomerAcc = By.xpath("//input[@placeholder='Search Customer Accounts and more...']");

	By refersh = By.xpath("//button[@name='refreshButton']");
	By clearButton = By.xpath("//button[@data-element-id='searchClear']/lightning-primitive-icon");
	By globalSearchButton = By.xpath("//button[@aria-label='Search']");
	By globalSearchInput = By.xpath("//input[@placeholder='Search...']");
	By returntoPage = By.xpath("//button[text()='Return to Page']");

	By displayCase = By.xpath("//div[@class='entityNameTitle slds-line-height--reset' and contains(text(),'Case')]");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public String getTitle() {
		WebElement title = uiTestHelper.waitForObject(homepageVerification);
		return title.getText();
	}

	public boolean verifyCaseDisplayed() throws Exception {
		try {
			By displayCase = By
					.xpath("//div[@class='entityNameTitle slds-line-height--reset' and contains(text(),'Case')]");
			boolean isCaseViewed = uiTestHelper.waitForObject(displayCase).isDisplayed();
			return isCaseViewed;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			By displayCase = By
					.xpath("(//div[@class='entityNameTitle slds-line-height--reset' and contains(text(),'Case')])[2]");
			boolean isCaseViewed = uiTestHelper.waitForObject(displayCase).isDisplayed();
			return isCaseViewed;
		}
	}

	// Set UserId on Login Page
	public boolean verifydropDownMenu() {
		WebElement userIdField = uiTestHelper.waitForObjectToBeClickable(dropDownMenu);
		return userIdField.isDisplayed();
	}

	public void clickDropDownNavigationMenu() {
		WebElement dropdownbutton = uiTestHelper.waitForObjectToBeClickable(dropDownMenu);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownbutton);
	}

	// to get the Track and Trace

	public void clickTrackAndTraceCCD() {
		WebElement trackandtrace = uiTestHelper.waitForObject(trackAndTrace);
		trackandtrace.click();
	}
	
	public void selectOptionFromHomeDropDown(String option) {
		By optionToSelect=By.xpath("//*[@id='navMenuList']//span[contains(text(),'"+option+"')]");
		uiTestHelper.waitForObjectToBeClickable(optionToSelect).click();
		
	}

	// To get the proactive Exceptions
	public void clickProactiveExceptions() {
		WebElement proactiveex = uiTestHelper.waitForObject(proactiveExceptions);
		proactiveex.click();
	}

	// to get the Cases
	public void clickCases() {
		WebElement casesSearch = uiTestHelper.waitForObjectToBeClickable(cases);
		uiTestHelper.scrollIntoView(casesSearch);
		// uiTestHelper.clickJS(casesSearch);
		casesSearch.click();
	}

	// Click Contacts from Dropdown Menu
	public void clickContacts() {
		WebElement contactsDropDown = uiTestHelper.waitForObject(contactsGlobalDropDown);
		contactsDropDown.click();
	}

	// to get the Customer Identication
	public void clickCustomerIdentification() {
		WebElement CI = uiTestHelper.waitForObject(customerIdentification);
		uiTestHelper.scrollIntoView(CI);
		uiTestHelper.clickJS(CI);
	}

	// to get the Booking
	public void clickBooking() {
		WebElement bookingsearch = uiTestHelper.waitForObject(bookings);
		uiTestHelper.clickJS(bookingsearch);
	}

	// get to homepage
	public void clickHome() {
		uiTestHelper.waitForObject(homeDropdownMenuOption).click();
	}

	// to get the Booking Exception
	public void clickBookingException() {
		WebElement bookingsearch = uiTestHelper.waitForObject(bookingException);
		uiTestHelper.scrollIntoView(bookingsearch);
		bookingsearch.click();
	}

	public void clickCustomerAccounts() {
		WebElement customerAcc = uiTestHelper.waitForObject(customerAccounts);
		uiTestHelper.scrollIntoView(customerAcc);
		customerAcc.click();
	}

	// search customer using Account Name
	public void searchBooking(String AccName) {
		globalSearchClick();
		WebElement customerAcc = uiTestHelper.waitForObjectToBeClickable(searchBox);
		uiTestHelper.waitforPresenceOfObject(searchBox);
		uiTestHelper.clickJS(customerAcc);
		customerAcc.sendKeys(AccName, Keys.ENTER);
	}

	// globalSearchBox
	public void searchGlobalSearch(String caseID) {
		globalSearchClick();
		WebElement searchInput = uiTestHelper.waitForObjectToBeClickable(searchBox);
		searchInput.click();
		searchInput.sendKeys(caseID, Keys.ENTER);
	}

	public void clickOnSearchedCase(String consignmentNo) {
		try {
		globalSearchClick();
		WebElement searchInput = uiTestHelper.waitForObjectToBeClickable(searchBox);
		searchInput.click();
		searchInput.sendKeys(consignmentNo);
		uiTestHelper.propagateException();
		uiTestHelper.actionClick(driver.findElement(By.xpath("(//div[@class='instant-results-list']//div[1])[1]")));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void clearBookingSearch() {
		WebElement ele = uiTestHelper.waitForObject(serachBookingInput);
		ele.clear();
	}

	// Select the Customer
	public void selectCustomerAccounts(String AccName) {
		WebElement acc = uiTestHelper.waitForObjectToBeClickable(By.xpath("//a[@title='" + AccName + "']"));
		uiTestHelper.clickJS(acc);
	}

	public void globalSearchWaitForPresenceThenClick() {
		WebElement searchbox = uiTestHelper.waitforPresenceOfObject(searchBox);
		searchbox.click();
		searchbox.sendKeys(Keys.ENTER);
	}

	public void inputGlobalSearch() {
		WebElement searchboxInput = uiTestHelper.waitforPresenceOfObject(searchBoxInput);
		searchboxInput.click();
		// searchboxInput.sendKeys(Keys.ENTER);
	}

	public void clickSearchedCaseNum(String CaseNumber) {
		WebElement caseNum = uiTestHelper.waitForObjectToBeClickable(By.xpath("//a[@title='" + CaseNumber + "']"));
		caseNum.click();
	}

	// search customer using Account Name
	public void searchCustomerAcc(String AccName) {
		globalSearchClick();
		WebElement customerAcc = uiTestHelper.waitForObject(searchCustomerAcc);
		customerAcc.click();
		customerAcc.sendKeys(AccName, Keys.ENTER);
	}

	public void globalSearchClick() {
		WebElement searchbox = uiTestHelper.waitForObjectToBeClickable(searchButton);
		uiTestHelper.clickJS(searchbox);
	}

	public void setTextInGlobalSearch(String str) {
		WebElement searchbox = uiTestHelper.waitForObject(searchBoxInput);
		searchbox.sendKeys(str);
		searchbox.sendKeys(Keys.ENTER);
	}

	public void setTextInGlobalSearch(Keys k) {
		WebElement searchbox = uiTestHelper.waitForObject(searchBox);
		searchbox.sendKeys(k);
	}

	public void caseSearch(String caseNumber) {
		WebElement input = uiTestHelper.waitForObject(caseSearchInput);
		input.clear();
		input.sendKeys(Keys.ENTER);
		input.sendKeys(caseNumber);
		input.sendKeys(Keys.ENTER);
	}

	public void clickCaseColumn() {
		By th = By.xpath("//th[@title='Case Number']/div");
		WebElement caseNumberColumn = driver.findElement(th);
		uiTestHelper.clickJS(caseNumberColumn);
	}

	public void bookingExceptionSearch(String bookingReferenceNumber) {
		WebElement input = uiTestHelper.waitForObject(bookingExceptionSearchInput);
		input.clear();
		input.sendKeys(bookingReferenceNumber);
	}

	public void refreshSearch() {
		WebElement button = uiTestHelper.waitForObject(refersh);
		button.click();
	}

	public void clearSearchList() {
		WebElement button = uiTestHelper.waitForObject(clearButton);
		uiTestHelper.clickJS(button);
	}

	public void searchBookingNumber(String bookingNumber) {
		WebElement button = uiTestHelper.waitForObject(serachBookingInput);
		button.clear();
		button.sendKeys(bookingNumber, Keys.ENTER);
	}

	public boolean verifyReturntoPage() {
		WebElement ele = uiTestHelper.waitForObjectwithException(returntoPage, 30);
		return ele.isDisplayed();
	}

	public void clickReturnToPage() {
		WebElement ele = uiTestHelper.waitForObjectwithException(returntoPage, 30);
		ele.click();
	}

	public void caseExceptionSearch(String bookingReferenceNumber) {
		WebElement input = uiTestHelper.waitForObject(caseSearchInput);
		input.clear();
		input.sendKeys(bookingReferenceNumber);
	}

	// customerAccountDropDown
	public void clickCustomerAccountsFromListBox() {
		WebElement customerAcc = uiTestHelper.waitforPresenceOfObject(customerAccountDropDown);
		customerAcc.click();
	}

	// Method for choosing the correct customer account as it references multiple
	// results with xPaths
	public void validateCustomerIdentity(String AccName) {
		By accName = By.xpath("//a[text()='Customer Accounts']/following::a[text()='" + AccName + "']");
		WebElement acc = uiTestHelper.waitforPresenceOfObject(accName);
		acc.click();
	}
	
	public String[] getUploadCasesInBulkWindows_details() {
		String caseInBulkDetails[]= new String[5];
		try {
			uiTestHelper.waitForObject(By.xpath("//h2/span[contains(text(),'Bulk Case Upload')]"));
			caseInBulkDetails[0]="true";
		}catch (org.openqa.selenium.NoSuchElementException e) {
			caseInBulkDetails[0]="false";
		}
		try {
			uiTestHelper.waitForObject(By.xpath("//span[contains(text(),'Upload Files')]"));
			caseInBulkDetails[1]="true";
		}catch (org.openqa.selenium.NoSuchElementException e) {
			caseInBulkDetails[1]="false";
		}
		try {
			uiTestHelper.waitForObject(By.xpath("//span[contains(text(),'Or drop files')]"));
			caseInBulkDetails[2]="true";
		}catch (org.openqa.selenium.NoSuchElementException e) {
			caseInBulkDetails[2]="false";
		}
		try {
			uiTestHelper.waitForObjectToBeClickable(By.xpath("//button[@title='Export Templete']"));
			caseInBulkDetails[3]="true";
		}catch (org.openqa.selenium.NoSuchElementException e) {
			caseInBulkDetails[3]="false";
		}
		try {
			String downloadText=uiTestHelper.waitForObject(By.xpath("//c-download-excel-templete")).getText();
			caseInBulkDetails[4]=downloadText;
		}catch (org.openqa.selenium.NoSuchElementException e) {
			caseInBulkDetails[4]="";
		}				
		return caseInBulkDetails;		
	}
	public void clickAppLauncher() {
		By app_launcher = By.xpath("//div[@role='navigation']/button");
		WebElement appLauncherButton = uiTestHelper.waitForObjectToBeClickable(app_launcher);
		appLauncherButton.click();
	}

	public void searchItem(String itemName) {
		By searchInput = By.xpath("//input[@placeholder='Search apps and items...']");
		WebElement searchInput_we = uiTestHelper.waitForObject(searchInput);
		searchInput_we.sendKeys(Keys.CLEAR);
		searchInput_we.sendKeys(itemName);
		By item = By.xpath("//a[@data-label='"+itemName+"']");
		WebElement item_link = uiTestHelper.waitForObjectToBeClickable(item);
		uiTestHelper.actionClick(item_link);
	}	
	
	public boolean isRedirectedToCCD() {
		By _CCD = By.xpath("//span[@title='CCD Customer Care Desktop']");
		try {
			WebElement ccd_desktop = driver.findElement(_CCD);
			ccd_desktop.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public boolean isRedirectedToCustomers() {
		By _customers = By.xpath("//div/span[contains(text(),'Customers')]");
		try {
			WebElement _customersPage = driver.findElement(_customers);
			_customersPage.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public boolean isRedirectedToCustomerAccounts() {
		By _customersAccounts = By.xpath("//div/span[contains(text(),'Customer Accounts')]");
		try {
			WebElement customersAccounts = driver.findElement(_customersAccounts);
			customersAccounts.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean isRedirectedToNewCustomer() {
		By _newcustomers = By.xpath("//h2[contains(text(),'New Customer: Customer')]");
		WebElement _newcustomersPage;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			_newcustomersPage = wait.until(ExpectedConditions.visibilityOfElementLocated(_newcustomers));
			_newcustomersPage = wait.until(ExpectedConditions.presenceOfElementLocated(_newcustomers));
			_newcustomersPage = driver.findElement(_newcustomers);
			_newcustomersPage.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean isRedirectedToContacts() {
		By _contacts = By.xpath("//div/span[contains(text(),'Contacts')]");
		try {
			WebElement contacts = driver.findElement(_contacts);
			contacts.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean isRedirectedToNewCustomerAccount() {
		By _newcustomers = By.xpath("//h2[contains(text(),'New Customer Account')]");
		try {
			WebElement _newcustomersPage = driver.findElement(_newcustomers);
			_newcustomersPage.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean isRedirectedToNewContact() {
		By _newcontact = By.xpath("//h2[contains(text(),'New Contact')]");
		try {
			WebElement contact_we = driver.findElement(_newcontact);
			contact_we.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public void clickNewCustomer() {
		By newCustomer = By.xpath("//div[@data-aura-class='forceObjectHomeDesktop']//li/a[@title='New']");
		WebElement newCustomerButton = uiTestHelper.waitForObjectToBeClickable(newCustomer);
		newCustomerButton.click();
	}
	public void clickNewButton() {
		By newCustomerAccount = By.xpath("//li/a[@title='New']");
		WebElement newCustomerAccountButton = uiTestHelper.waitForObjectToBeClickable(newCustomerAccount);
		newCustomerAccountButton.click();
	}

	public void clickNextButton() {
		By nextbutton = By.xpath("//div[@class='inlineFooter']//button[2]");
		WebElement nextbutton_we = uiTestHelper.waitForObjectToBeClickable(nextbutton);
		nextbutton_we.click();
	}

	public boolean isReferenceSystemField() {
		By referenceSystem_field = By.xpath("//label[contains(text(),'Reference System')]");
		try {
			WebElement referenceSystem_field_we = driver.findElement(referenceSystem_field);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);",referenceSystem_field_we);
			referenceSystem_field_we.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public void clcikRefSysDropDown() {
		By referenceSystem_dropdown = By.xpath("//label[contains(text(),'Reference System')]/following::button[1]");
		WebElement dropDownbutton_we = uiTestHelper.waitForObjectToBeClickable(referenceSystem_dropdown);
		dropDownbutton_we.click();
	}
	//CDB ECDC CCD at customer level
	public String getReferenceSystemList_customer() {
		By list = By.xpath(
				"//label[contains(text(),'Reference System')]/following::div//div[@role='listbox']/lightning-base-combobox-item");
		List<WebElement> ref_list = uiTestHelper.waitForObjects(list);
		String referencesystem_items = "";
		int row = ref_list.size();
		for (int i = 1; i <= row; i++) {
			WebElement ref_list_item = uiTestHelper.waitForObject(By.xpath(
					"//label[contains(text(),'Reference System')]/following::div//div[@role='listbox']/lightning-base-combobox-item["
							+ i + "]/span[2]/span"));
			if (i < 2) {
				referencesystem_items = referencesystem_items + ref_list_item.getText();
			}else if(i==(row-1)) {
				referencesystem_items = referencesystem_items + ", " + ref_list_item.getText()+" and";
			}else {
				referencesystem_items = referencesystem_items + ", " + ref_list_item.getText();
			}
		}
		return referencesystem_items;
	}
	public void clickCancelEdit() {
		By cancelbutton = By.xpath("//button[@name='CancelEdit']");
		WebElement cancelbutton_we = uiTestHelper.waitForObjectToBeClickable(cancelbutton);
		cancelbutton_we.click();
	}
	
	// to get all the available cases
		public void openRequiredListType(String listType) {
			By dropdownIcon = By.xpath("//button[@title='Select a List View']");
			By listTypeInput = By.xpath("//div/input[@role='combobox']");
			By listItem = By.xpath("//ul[@role='listbox']/li[1]/a");
			uiTestHelper.waitForObjectToBeClickable(dropdownIcon).click();
			uiTestHelper.waitForObject(listTypeInput).sendKeys(Keys.CLEAR);
			uiTestHelper.waitForObject(listTypeInput).sendKeys(listType);
			// uiTestHelper.waitForObjectToBeClickable(listTypeInput).sendKeys(Keys.ENTER);
			uiTestHelper.waitForObjectToBeClickable(listItem).click();
		}

		// sort the column in descending or ascending order
		public void sortTheColumn(String order, String columnName) {
			By orderType = By.xpath("//th[@title='" + columnName + "']/div/span");
			By column = By.xpath("//th[@title='" + columnName + "']/div");
			WebElement column_01 = uiTestHelper.waitForObjectToBeClickable(column);
			// uiTestHelper.actionClick(column_01);
			String ascORdesc = uiTestHelper.waitForObject(orderType).getText();
			System.out.println("ascORdesc: " + ascORdesc);
			if (!ascORdesc.contains(order)) {
				uiTestHelper.actionClick(column_01);
			}
		}

		// get the subject for the latest case from the list
		public String getSubject() throws Exception {
			By subjectColumn = By.xpath("//table[1]/tbody/tr[1]//div[@class='outputSubject']/div/a");
			String subjectValue = "";
			try {
				WebElement subject = driver.findElement(subjectColumn);
				subjectValue = subject.getText();
			} catch (Exception e) {
				subjectValue = "";
			}
			return subjectValue;
		}

		// click on latest case
		public void clickOnLatestCase() {
			By caseNumber = By.xpath("//table[1]/tbody/tr[1]/th/span");
			// uiTestHelper.waitForObjectToBeClickable(caseNumber).click();
			WebElement case_id = uiTestHelper.waitForObjectToBeClickable(caseNumber);
			uiTestHelper.actionClick(case_id);
			// case_id.click();
		}

		// check if email is clickable
		public String isEmailClickable(String email) {
			email = email.toLowerCase();
			By emailDetails_text = By.xpath("//table[1]/tbody/tr[1]/td//span[@title='" + email + "']");
			By emailDetails_link = By.xpath("//table[1]/tbody/tr[1]/td//a[contains(text(),'" + email + "')]");
			String linkORtext = "";
			try {
				WebElement element = driver.findElement(emailDetails_text);
				element.isDisplayed();
				linkORtext = "true";

			} catch (Exception e) {
				linkORtext = "false";
			}
			try {
				WebElement element = driver.findElement(emailDetails_link);
				element.isDisplayed();
				linkORtext = "false";

			} catch (Exception e) {
				linkORtext = linkORtext + "true";
			}
			return linkORtext;

		}

}
