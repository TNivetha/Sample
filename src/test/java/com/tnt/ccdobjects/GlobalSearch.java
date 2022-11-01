package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class GlobalSearch {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By searchBox = By.xpath("//input[contains(@title,'Search')]");
	By searchedCustAccountTable = By.xpath("//*[contains(text(),'Account Name')]//ancestor::table");
	By firstRecordInCustAccTable = By
			.xpath("//*[contains(text(),'Account Name')]//ancestor::table/tbody/tr[1]/th/span/a");
	By searchedCustomersTable = By.xpath("(//*[contains(text(),'Customer Name')]//ancestor::table");

	By customerAccountSearch = By.xpath("//span[contains(text(),'Customer Accounts')]");
	By refineBy = By.xpath("//h3[contains(text(),'Refine By')]");
	By refineByAccName = By.xpath("(//span[contains(text(),'Account Name')])[1]/following::input[1]");
	By refineByFilter = By.xpath("//button[@title='Apply filter']/lightning-primitive-icon");
	By refineBySearch = By.xpath("//div[text()='Customer Accounts']/following::table[1]/tbody/tr[1]/th/span/a");
	By refineBySalesCountry = By.xpath("(//span[contains(text(),'TNT Sales Country')])[1]/following::input[1]");
	By refineBySearchCountry = By
			.xpath("//div[text()='Customer Accounts']/following::table[1]/tbody/tr[1]/td[2]/span/span");
	By refineByAccNum = By.xpath("(//span[contains(text(),'Account Number')])[1]/following::input[1]");
	By refineBySearchAccNum = By
			.xpath("//div[text()='Customer Accounts']/following::table[1]/tbody/tr[1]/td[3]/span/span");

	By stream = By.xpath("(//span[text()='Stream'])[1]/following::lightning-formatted-text[1]");

	public GlobalSearch(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	public WebElement globalSearchcustAccTable() {
		WebElement element = uiTestHelper.waitforPresenceOfObject(searchedCustAccountTable);
		return element;
	}

	public boolean searchResultCustAccVisible() throws InterruptedException {
		WebElement element = uiTestHelper.waitforPresenceOfObject(searchedCustAccountTable);
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void firstRecordInCustAccTableClk() {
		WebElement element = uiTestHelper.waitforPresenceOfObject(firstRecordInCustAccTable);
		element.click();
	}

	public WebElement globalSearchCustomersTable() {
		WebElement element = uiTestHelper.waitforPresenceOfObject(searchedCustomersTable);
		return element;
	}

	public boolean searchResultCustomersVisible() {
		WebElement element = uiTestHelper.waitforPresenceOfObject(searchedCustomersTable);
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void custAccSearchClick() {
		WebElement element = uiTestHelper.waitForObjectToBeClickable(customerAccountSearch);
		element.click();
	}

	// Check if refine by is present
	public boolean refineByPresent() {
		WebElement refine = uiTestHelper.waitForObject(refineBy);
		if (refine.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// account name webelement in Refine By
	public WebElement accNameRefineBy() {
		WebElement element = uiTestHelper.waitForObject(refineByAccName);
		return element;
	}

	// account name webelement in Refine By
	public WebElement filterRefineBy() {
		WebElement element = uiTestHelper.waitForObjectToBeClickable(refineByFilter);
		return element;
	}

	// account name webelement in Refine By
	public WebElement searchRefineBy() {
		WebElement element = uiTestHelper.waitForObject(refineBySearch);
		return element;
	}

	// account name webelement in Refine By
	public WebElement searchbox() {
		WebElement element = uiTestHelper.waitForObject(searchBox);
		return element;
	}

	// account name webelement in Refine By
	public WebElement salesCountryRefine() {
		WebElement element = uiTestHelper.waitForObject(refineBySalesCountry);
		return element;
	}

	// account name webelement in Refine By
	public WebElement searchCountryRefine() {
		WebElement element = uiTestHelper.waitForObject(refineBySearchCountry);
		return element;
	}

	// account name webelement in Refine By
	public WebElement accNumRefine() {
		WebElement element = uiTestHelper.waitForObject(refineByAccNum);
		return element;
	}

	// account name webelement in Refine By
	public WebElement searchAccNumRefine() {
		WebElement element = uiTestHelper.waitForObject(refineBySearchAccNum);
		return element;
	}

	// to get Customer Stream

	public String getStream() {
		WebElement ele = uiTestHelper.waitForObject(stream);
		return ele.getText();
	}

	// to click search Filter

	public void clickFilter() {
		WebElement search = filterRefineBy();
		uiTestHelper.clickJS(search);
	}
	public void selectReferenceNumber(String referenceNumber) {
		WebElement correctQuoteSearchTab = uiTestHelper.waitForObject(By.xpath(
				"//*[text()='Bookings']/following::table[1]/tbody/tr[1]/th/span/a[text()='" + referenceNumber + "']"));
		correctQuoteSearchTab.click();
	}
}
