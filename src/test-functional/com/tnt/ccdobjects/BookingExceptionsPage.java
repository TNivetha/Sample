package com.tnt.ccdobjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class BookingExceptionsPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By recentlyViewed = By.xpath("//span[text()='Recently Viewed']");
	By searchItems = By.xpath("(//div[@data-aura-class='forceVirtualAutocompleteMenu']//input)");
	By exceptionTable = By.xpath("//span[text()='Booking Exception']/following::table[1]/tbody/tr");
	By acceptException = By.xpath("(//a[@class='forceActionLink'])[2]/div");
	By myOpenCases = By.xpath("//mark[text()='1 - My Open Cases']");
	By searchCasesInput = By.xpath("//input[@name='Case-search-input']");
	By caseOwner = By.xpath("//span[text()='Case Owner']/following::lightning-formatted-lookup[1]");
	By caseNumber = By.xpath("//div[text()='Case']/following::span[1]");
	By emailView = By.xpath("//h2[text()='Related List Quick Links']/following::span[contains(text(),'Emails')][1]");
	By emailTable = By.xpath("//h1[@title='Emails']/following::table[1]/tbody/tr/th[1]/span/a");
	By detailsofEmailTab = By.xpath("//span[(@class='title' and text()='Details')]");
	By emailStatus = By.xpath("//span[text()='Email Status']/following::span[2]");
	By rfiView = By
			.xpath("//h2[text()='Related List Quick Links']/following::span[contains(text(),'Booking RFIs')][1]");
	By rfiTable = By.xpath("//h1[@title='Booking RFIs']/following::table[1]/tbody/tr/th[1]/span/a");
	By closeCaseButton = By.xpath("//button[@name='Case.Close_Case_BE']");
	By closeCaseComments = By.xpath("//h2[text()='Close Case']/following::textarea[1]");
	By saveCloseCaseComments = By.xpath("//span[text()='Internal Comments']/following::button/span[text()='Save']");
	By caseStatus = By.xpath("(//p[@title='Status']/following::lightning-formatted-text[1])[2]");
	By caseTableList = By.xpath("//span[text()='Cases']/following::table[1]/tbody/tr");
	By myopenBookingExceptionCase = By.xpath("//span[text()='My Open Booking Exception Case']");
	By viewBooking = By.xpath("//button[@name='Case.View_Booking']");
	By rfigif = By.xpath("//img[@src='/img/samples/flag_green.gif']");

	By releaseCaseStatus = By.xpath("(//p[@title='Status']/following::lightning-formatted-text[1])[3]");
	By statusAfterClearException=By.xpath("(//p[@title='Status']/following::lightning-formatted-text[1])");
	By bookingReferenceNumber = By
			.xpath("//span[text()='Booking Reference Number']/following::lightning-formatted-text[1]");

	// Booking Exception Details
	By customerAccount = By.xpath("(//span[text()='Customer Account'])[2]");
	By service = By.xpath("(//span[text()='Service'])[2]");
	By productOptions = By.xpath("(//span[text()='Product options'])[2]");
	By custAccName = By.xpath("(//span[text()='Customer Account'])[2]/following::lightning-formatted-lookup[1]");
	By serviceName = By.xpath("(//span[text()='Service'])[2]/following::lightning-formatted-text[1]");
	By productOptionName = By.xpath("(//span[text()='Product options'])[2]/following::lightning-formatted-text[1]");
	By bookingDescription= By.xpath("//lightning-output-field/span[text()='Exception Description']/following::lightning-formatted-text[1]");
	By bookingExceptionDetails=By.xpath("//a[@data-label='Booking Exception Details']");
	By exceptionDescription=By.xpath("//lightning-primitive-cell-factory[@data-label='Exception Description']//lightning-formatted-text[1]");
	By releaseButton=By.xpath("//button[@name='Case.Release']");

	By bookingExceptionNumber = By.xpath("//span[@title='Booking Exception Number']/following::span[@id='window']");
	By bookingExceptionTab = By.xpath("//a[@data-label='Booking Exception']");
	By reopen=By.xpath("//button[@name='Case.Reopen']");
	
	public BookingExceptionsPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	public void clickRecentlyViewedItems() {
		WebElement recentview = uiTestHelper.waitForObjectToBeClickable(recentlyViewed);
		recentview.click();
	}

	public void clickMyOpenBookingExceptionItems() {
		WebElement recentview = uiTestHelper.waitForObjectToBeClickable(myopenBookingExceptionCase);
		recentview.click();
	}

	public void searchInput(String input) {
		WebElement inputSearch = uiTestHelper.waitForObject(searchItems);
		inputSearch.clear();
		inputSearch.sendKeys(input);
	}

	public void searchCollectionCountryException(String countryoccException) {
		WebElement inputSearch = uiTestHelper.waitForObject(By.xpath("//span[text()='" + countryoccException + "']"));
		inputSearch.click();
	}

	public void searchCase(String consignmentNo) {
		WebElement button = uiTestHelper.waitForObject(searchCasesInput);
		button.sendKeys(consignmentNo);
		uiTestHelper.clickJS(button);
	}

	public void clickAssignedInput(String input) {
		By inputSelect = By.xpath("//mark[text()='" + input + "']");
		WebElement in = uiTestHelper.waitForObject(inputSelect);
		//in.click();
		uiTestHelper.clickJS(in);
	}

	public int getExceptionTableSize() {
		List<WebElement> TableSize = uiTestHelper.waitForObjects(exceptionTable);
		int size = TableSize.size();
		return size;
	}

	public void clickAcceptException() {
		WebElement accept = uiTestHelper.waitForObjectToBeClickable(acceptException);
		uiTestHelper.clickJS(accept);
	}
	
	public boolean verifyExceptionAccept() {
		WebElement accept = uiTestHelper.waitForObjectToBeClickable(By.xpath("(//span[text()='Select List View'])[1]"));
		return accept.isDisplayed();
	}

	public void selectTemplateType(String templateType) {
		Select templatetype = new Select(
				driver.findElement(By.xpath("//span[@title='Templates']/following::select[1]")));
		templatetype.selectByVisibleText(templateType);
	}

	public String getCaseOwner() {
		WebElement casedetails = uiTestHelper.waitForObject(caseOwner);
		uiTestHelper.scrollIntoView(casedetails);
		return casedetails.getText();
	}

	public String getCaseNumber() {
		WebElement casedetails = uiTestHelper.waitForObject(caseNumber);
		return casedetails.getText();
	}

	// Email
	public void clickEmailView() {
		WebElement mailView = uiTestHelper.waitForObject(emailView);
		uiTestHelper.clickJS(mailView);
	}

	public int getEmailTableSize() {
		List<WebElement> TableSize = uiTestHelper.waitForObjects(emailTable);
		int size = TableSize.size();
		return size;
	}

	public void clickemailTable(int Size, String Text) {
		By tableid = By.xpath("//h1[@title='Emails']/following::table[1]/tbody/tr['" + Size
				+ "']/th[1]/span/a[contains(text(),'" + Text + "')]");
		WebElement id = uiTestHelper.waitForObject(tableid);
		uiTestHelper.scrollIntoView(id);
		id.click();
	}

	public void clickEmailDetailsTab() {
		WebElement mailView = uiTestHelper.waitForObject(detailsofEmailTab);
		uiTestHelper.clickJS(mailView);
	}

	public String getEmailStatus() {
		WebElement status = uiTestHelper.waitForObject(emailStatus);
		return status.getText();
	}

	// RFI
	public void clickRFIView() {
		WebElement rfi = uiTestHelper.waitForObject(rfiView);
		uiTestHelper.clickJS(rfi);
	}

	public int getRFITableSize() {
		List<WebElement> TableSize = uiTestHelper.waitForObjects(rfiTable);
		int size = TableSize.size();
		return size;
	}

	public void clickrfiTable(int Size, String Text) {
		By tableid = By.xpath("//h1[@title='Booking RFIs']/following::table[1]/tbody/tr['" + Size
				+ "']/th[1]/span/a[contains(text(),'" + Text + "')]");
		WebElement id = uiTestHelper.waitForObject(tableid);
		uiTestHelper.scrollIntoView(id);
		id.click();
	}

	public boolean verifyRfiStatus() {
		WebElement rfi = uiTestHelper.waitForObject(rfigif);
		return rfi.isDisplayed();
	}

	// Close Case
	public void clickCloseCaseButton() {
		WebElement caseClose = uiTestHelper.waitForObject(closeCaseButton);
		uiTestHelper.clickJS(caseClose);
	}

	public void setCloseCaseComments(String comments) {
		WebElement caseClose = uiTestHelper.waitForObject(closeCaseComments);
		caseClose.sendKeys(comments);
	}

	public void saveCloseCaseButton() {
		WebElement caseClose = uiTestHelper.waitForObject(saveCloseCaseComments);
		uiTestHelper.clickJS(caseClose);
	}

	public String getCaseStatus() {
		WebElement casedetails = uiTestHelper.waitForObject(caseStatus);
		return casedetails.getText();
	}

	// caseTable
	public boolean verifyCaseTableCount() {
		WebElement casedetails = uiTestHelper.waitForObject(caseTableList);
		return casedetails.isDisplayed();
	}

	public void clickViewBooking() {
		WebElement casedetails = uiTestHelper.waitForObject(viewBooking);
		casedetails.click();
	}

	public String getReleaseCaseStatus() {
		WebElement casedetails = uiTestHelper.waitForObject(releaseCaseStatus);
		return casedetails.getText();
	}

	// ViewBooking
	public void viewBookingException(String bookNum) throws InterruptedException {
		WebElement myTable = driver.findElement(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		for (int i = 1; i <= row_count; i++) {
			Thread.sleep(1000);
			String tempBookNum = driver.findElement(By.xpath(
					"(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/td[3]/span/span[1]"))
					.getText();
			System.out.println(tempBookNum + "  " + bookNum);
			if (bookNum.contains(tempBookNum)) {
				Actions ob = new Actions(driver);
				WebElement chkBox = driver.findElement(
						By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/th//a"));
				ob.click(chkBox).perform();
				break;
			}
		}
	}
	
	public int getTableSize() {
		List<WebElement> table=uiTestHelper.waitForObjects(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody/tr"));
		return table.size();
	}
	public boolean verifyBookingOntheQueue(String bookNum) throws Exception {
		boolean isOntheQueue = false;
		WebElement myTable = uiTestHelper.waitForObject(By.xpath("(//div[@class='slds-template__container']//table)[1]/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		for (int i = 1; i <= row_count; i++) {
			Thread.sleep(1000);
			String tempBookNum = uiTestHelper.waitForObject(By.xpath(
					"(//div[@class='slds-template__container']//table)[1]/tbody/tr[" + i + "]/td[3]/span/span[1]"))
							.getText();
			System.out.println(tempBookNum + "  " + bookNum);
			if (bookNum.contains(tempBookNum)) {
				isOntheQueue = true;
				break;
			}
		}
		return isOntheQueue;
	}

	public boolean verifyHearderDisplayed(String headerName) {
		WebElement ele = uiTestHelper.waitForObject(By.xpath("//h1//span[text()='" + headerName + "']"));
		return ele.isDisplayed();
	}

	// Booking Exception Details
	public boolean verifyCustomerAccount() {
		WebElement bookingException = uiTestHelper.waitForObject(customerAccount);
		return bookingException.isDisplayed();
	}

	public boolean verifyProductOption() {
		WebElement bookingException = uiTestHelper.waitForObject(productOptions);
		return bookingException.isDisplayed();
	}

	public boolean verifyService() {
		WebElement bookingException = uiTestHelper.waitForObject(service);
		return bookingException.isDisplayed();
	}

	public String getCustomerName() {
		WebElement bookingException = uiTestHelper.waitForObject(custAccName);
		return bookingException.getText();
	}

	public String getServiceName() {
		WebElement bookingException = uiTestHelper.waitForObject(serviceName);
		return bookingException.getText();
	}

	public String getProductOption() {
		WebElement bookingException = uiTestHelper.waitForObject(productOptionName);
		return bookingException.getText();
	}

	public boolean viewExceptionheader(String tableHeader) {
		boolean isOnHeader = false;
		WebElement myTable = driver
				.findElement(By.xpath("(//div[@class='slds-template__container']//table)[1]/thead/tr"));
		List<WebElement> objRow = myTable.findElements(By.tagName("th"));
		int row_count = objRow.size();
		for (int i = 1; i <= row_count; i++) {
			String header = driver
					.findElement(By.xpath(
							"(//div[@class='slds-template__container']//table)[1]/thead/tr/th[" + i + "]//a/span[2]"))
					.getText();
			System.out.println(header + "  " + tableHeader);
			if (tableHeader.contains(header)) {
				isOnHeader = true;
				break;
			}
		}
		return isOnHeader;
	}
	
	public void clickReleaseException() {
		WebElement button = uiTestHelper.waitForObject(releaseButton);
		uiTestHelper.scrollIntoView(button);
		uiTestHelper.clickJS(button);
		//button.click();
	}
	
	public boolean verifyReleaseException() {
		boolean clearButton=false;
		try {
		WebElement button = uiTestHelper.waitForObject(releaseButton);
		button.isDisplayed();
		clearButton=true;;
		}catch(NoSuchElementException e) {
			clearButton=false;
		}
		return clearButton;
	}
	public String getExceptionStatus() {
		WebElement bookingException = uiTestHelper.waitForObject(statusAfterClearException);
		return bookingException.getText();
	}
	public boolean verifyHeaderDisplayed(String headerName) {
		WebElement ele = uiTestHelper.waitForObject(By.xpath("//table//th//a//span[@title='"+ headerName + "']"));
		return ele.isDisplayed();
	}
	public boolean verifyBookingDescription(){
		WebElement ele = uiTestHelper.waitForObject(bookingDescription);
		return ele.isDisplayed();
	}
	public void clickBookingExceptionDetailsTab() {
		WebElement ele = uiTestHelper.waitForObject(bookingExceptionDetails);
		ele.click();
	}
	public boolean verifyExceptionDescription(){
		WebElement ele = uiTestHelper.waitForObject(exceptionDescription);
		return ele.isDisplayed();
	}
	public String getBookingRefNumber() {
		WebElement bookingexcpdetails = uiTestHelper.waitForObject(bookingReferenceNumber);
		return bookingexcpdetails.getText();
	}
	
	public String getBookingExceptionID() {
		WebElement bookingExceptionID = uiTestHelper.waitForObject(bookingExceptionNumber);
		return bookingExceptionID.getText();
	}
	
	public boolean verifyBookingExceptionTab() {
		WebElement ele = uiTestHelper.waitForObject(bookingExceptionTab);
		return ele.isDisplayed();
	}
	
	public boolean verifyCaseReopen() {
		WebElement ele = uiTestHelper.waitForObject(reopen);
		return ele.isDisplayed();
	}

	public WebElement waitForObject(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return element;
	}
	public void clickJS(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ele);
	}

}
