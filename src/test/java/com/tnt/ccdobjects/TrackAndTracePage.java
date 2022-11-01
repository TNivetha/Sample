package com.tnt.ccdobjects;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Media;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;

public class TrackAndTracePage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By consignMentNumber = By.xpath("//input[@name='conNum']");
	By firstConsignmentNumberBox = By.xpath("(//label[contains(text(),'Consignment Number')]/following::input[1])[1]");
	By searchButton = By.xpath("//button[@title='Search']");
	By omnichannel = By.xpath("//div[@class='flexipageComponent']//span[contains(text(),'Omni-Channel')]");
	By omnichanneldropdown = By.xpath("//span[@title='Offline Status']/following::button[1]/lightning-primitive-icon");
	By omniStatusAvailable = By.xpath("//span[text()='Available']");
	By omniCaseAccept = By.xpath("//button[@title='Accept Case ']");
	By omniMinimizeButton = By.xpath("//h2[@title='Omni-Channel']/following::button[@title='Minimize']");

	By coldateFrom = By.xpath("//input[@name='collDateFrom']");
	By deldateFrom = By.xpath("//input[@name='delDateFrom']");
	By coldateTo = By.xpath("//input[@name='collDateTo']");
	By deldateTo = By.xpath("//input[@name='delDateTo']");
	By ipcustRef = By.xpath("//input[@name='custRef']");
	By errmsg = By.xpath("//div[@data-key='error']/button");
	By altcongnnum = By.xpath("//input[@name='altConNum']");
	By senCountrynm = By
			.xpath("//label[text()='Sender Country/Territory']/parent::lightning-input//input[@name='senderCountry']");
	By recCountrynm = By.xpath(
			"//label[text()='Receiver Country/Territory']/parent::lightning-input//input[@name='senderCountry']");
	By senAccNum = By.xpath("//input[@name='senderAccNum']");
	By recAccNum = By.xpath("//input[@name='receiverAccNum']");
	By senCompyNm = By.xpath("//input[@name='senderCompName']");
	By recCompyNm = By.xpath("//input[@name='receiverCompName']");
	By senPostCode = By.xpath("//input[@name='senderCompPostcode']");
	By recPostCode = By.xpath("//input[@name='receiverCompPostcode']");
	By senTownNm = By.xpath("//input[@name='senderCompTown']");
	By recTownNm = By.xpath("//input[@name='receiverCompTown']");
	By senDepot = By.xpath("//label[text()='Sender Depot']/parent::lightning-input//input[@name='depotName']");
	By recDepot = By.xpath("//label[text()='Receiver Depot']/parent::lightning-input//input[@name='depotName']");
	By ipCosgnNum = By.xpath("//input[@name='conNum']");
	By deliveryAreaOriginZoneCode = By.xpath("(//*[contains(text(),'Delivery Zone Code')])[1]/following::dd[1]");
	By deliveryAreaDestZoneCode = By.xpath("(//*[contains(text(),'Delivery Zone Code')])[2]/following::dd[1]");
	By deliveryAreaOriginDesc = By.xpath("(//*[contains(text(),'Delivery Zone Description')])[1]/following::dd[1]");
	By deliveryAreaDestDesc = By.xpath("(//*[contains(text(),'Delivery Zone Description')])[2]/following::dd[1]");
	By closeDeliveryArea = By.xpath("//button[@name='closedeliveryarea']");
	By invoiceandRating = By.xpath("//button[text()='Invoice & Rating']");

	By lstbtnConsgnNum = By.xpath("//div[@class=\"slds-scrollable_y\"]/table/tbody/tr[1]/th//span/div");
	By dropDownHistory = By.xpath("//th//span[text()='History']");
	By dropDownPieces = By.xpath("//th//span[text()='Pieces']");
	By dropDownDetails = By.xpath("//th//span[text()='Details']");
	By showMoreIcon = By.xpath("//button[@title='ShowMore']");
	By additionalInfoTitle = By.xpath("//b[contains(text(),'Additional Information')]");
	By userId = By.xpath("//b[text()='User-Id']/following::td[1]");
	By statusDepot = By.xpath("//b[text()='Status depot']/following::td[1]");
	By sendDateTime = By.xpath("//b[text()='Send date/time']/following::td[1]");
	By receiveDateTime = By.xpath("//b[text()='Received date/time']/following::td[1]");
	By roundNo = By.xpath("//b[text()='Round No.']/following::td[1]");
	By runsheetNo = By.xpath("//b[text()='Runsheet No.']/following::td[1]");
	By clearButton = By.xpath("//button[@class='slds-button slds-button_neutral']");
	By newSearchBtn = By.xpath("//button[text()='New Search']");
	By unallocateCasebtn = By.xpath("//button[contains(text(),'Create Unallocated Case')]");
	By additionalInfoCloseButton = By.xpath(
			"//*[contains(@id,'brandBand')]//div/c-trackntrace-container-comp/c-consignment-history-component/lightning-card/article/div/slot/div[3]/section/div/footer/button");
	By viewCaseButton = By.xpath("//button[contains(text(),'View Case')]");
	By createCaseButton = By.xpath("//button[contains(text(),'Create Case')]");
	By createCaseRedErrorMsg = By.xpath("//div[text()='ERROR:']/following::lightning-primitive-icon[1]");
	By caseNumberTableTitle = By.xpath("//th[@data-label='Case Number']");
	By existingCaseCauseErrorMessage = By.xpath("//div[@data-key='error']");
	By eyeIcon = By.xpath("//button[@title='Preview']");
	By consignmentErrorMessage = By
			.xpath("//span[@data-aura-class='forceActionsText' and contains(text(),'12 digit')]");
	By invalidFormatMsg = By.xpath("//span[@data-aura-class='forceActionsText' and contains(text(),'proper format')]");

	By cosignmentTable = By.xpath("//div[@class='dt-outer-container']//table/tbody");

	By consgnmentPieces = By.xpath("//button[text()='Consignment Pieces']");
	By consignmentHistory = By.xpath("//button[text()='Consignment History']");

	public TrackAndTracePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public By cognTbl() {
		By consgnTble = (By.xpath("//div[@class='dt-outer-container']//table/tbody"));
		return consgnTble;
	}

	public By clrBtn() {
		return clearButton;
	}

	public By txtconsnEquiry() {
		return By.xpath("//span[text()='Consignment Enquiry']");
	}

	public By btnNewSearch() {
		return By.xpath("//button[contains(text(),'New Search')]");
	}

	public By btnClear() {
		return By.xpath("//button[text()='Clear']");
	}

	// Consignment Number for Consi
	public By txtCosgnNum() {
		return By.xpath("//label[text()='Consignment Number']");
	}

	public By txtsenderinfo() {
		By txtsenderinfo = By.xpath("//span[text()='Sender Information']");
		return txtsenderinfo;
	}

	public By txtreciverinfo() {
		By txtsenderinfo = By.xpath("//span[text()='Receiver Information']");
		return txtsenderinfo;
	}

	public By calTable() {
		By calTble = By.xpath("//lightning-calendar//table/tbody");
		return calTble;
	}

	public By listCognTbl() {
		By consgnTble = By.xpath("//div[@class='dt-outer-container']//table/tbody/tr");
		return consgnTble;
	}

	public void lstbtnConsgn() {
		uiTestHelper.waitForObject(lstbtnConsgnNum).click();
	}

	// Select history form dropdown of list of consignment
	public void clkDropDownHistory() {
		uiTestHelper.waitForObject(dropDownHistory).click();
	}

	// Select Pieces form dropdown of list of consignment
	public void clkDropDownPieces() {
		uiTestHelper.waitForObject(dropDownPieces).click();
	}

	// Select Details form dropdown of list of consignment
	public void clkDropDownDetails() {
		uiTestHelper.waitForObject(dropDownDetails).click();
	}

	public void clkClearBtn() {
		WebElement btn = uiTestHelper.waitForObject(clrBtn());
		scrollIntoView(btn);
		btn.click();
	}

	public void clickCreateUnallocatedCaseBtn() {
		WebElement btn = uiTestHelper.waitForObject(unallocateCasebtn);
		btn.click();
	}

	public Boolean waitForErrorMessage() {
		return uiTestHelper.waitForObjectToBeInvisible(existingCaseCauseErrorMessage);
	}

	public void clickViewCaseButton() {
		uiTestHelper.waitForObject(viewCaseButton).click();
	}

	public void clickCreateCaseButton() {
		uiTestHelper.waitForObject(createCaseButton).click();
	}

	public Boolean areCaseNumbersDisplayed() {
		WebElement caseNumbers = uiTestHelper.waitForObject(caseNumberTableTitle);
		return caseNumbers.isDisplayed();
	}

	public Boolean verifyNewSearchDisplayed()  {
		try {
			// WebElement newSearch = uiTestHelper.waitForObject(btnNewSearch());
			By newSearchBtn = By.xpath("//button[contains(text(),'New Search')]");
			driver.findElement(newSearchBtn);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void clkNewSearch() {
		uiTestHelper.waitForObject(btnNewSearch()).click();
	}

	public void clickEyeIconInCaseShipmentTable() {
		uiTestHelper.waitForObject(eyeIcon);
		uiTestHelper.scrollIntoView(eyeIcon);
		uiTestHelper.waitForObject(eyeIcon).click();
	}

	public void scrollBtnNewSearch() {
		uiTestHelper.waitForObject(btnNewSearch());
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
				driver.findElement(btnNewSearch()));
	}

	public By consgnOverview() {
		By consgnOverview = By.xpath("//a[text()='CONSIGNMENT OVERVIEW']");
		return consgnOverview;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		WebElement conNumField = uiTestHelper.waitForObject(consignMentNumber);
		conNumField.clear();
		conNumField.sendKeys(consignmentNumber);
	}

	// firstConsignmentNumberBox
	public void setFirstConsignmentNumberBox(String consignmentNumber) {
		WebElement conNumField = uiTestHelper.waitForObject(firstConsignmentNumberBox);
		conNumField.clear();
		conNumField.click();
		conNumField.sendKeys(consignmentNumber);
	}

	public void javaScriptExecutorSendKeys() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement textBox = driver.findElement(firstConsignmentNumberBox);
		jse.executeScript("arguments[0].value='324646464'", textBox);
	}

	public void clickTrackandTraceSearch() {
		WebElement clickSearch = uiTestHelper.waitForObject(searchButton);
		scrollIntoView(clickSearch);
		clickSearch.click();
	}

	public void clickOmniChannel() {
		WebElement omniChannel = uiTestHelper.waitForObject(omnichannel);
		clickJS(omniChannel);
	}

	public void clickOmniStatusDropDown() {
		WebElement dropdown = uiTestHelper.waitForObject(omnichanneldropdown);
		dropdown.click();

	}

	public void clickOmniStatus(String omniStatus) {
		WebElement status = uiTestHelper.waitForObject(By.xpath("//span[text()='" + omniStatus + "']"));
		status.click();
	}

	public void clickOmniCaseAccept() {
		WebElement caseAccept = uiTestHelper.waitForObject(omniCaseAccept);
		caseAccept.click();
	}

	public void clickOmniMinimize() {
		WebElement minimize = uiTestHelper.waitForObject(omniMinimizeButton);
		minimize.click();
	}

	public void ipCosgnNum(String num) {
		WebElement connum = uiTestHelper.waitForObject(ipCosgnNum);
		connum.clear();
		connum.sendKeys(num);
	}

	public void clkColDateFrom() {
		uiTestHelper.waitForObject(coldateFrom).click();
	}

	// click Delivery Date From
	public void clkDelDateFrom() {
		uiTestHelper.waitForObject(deldateFrom).click();
	}

	public void clkColDateTo() {
		uiTestHelper.waitForObject(coldateTo).click();
	}

	// Click Delivery Date To
	public void clkDelDateTo() {
		uiTestHelper.waitForObject(deldateTo).click();
	}

	public By clkDate(String date) {
		By clkdate = By.xpath("//span[text()=" + date + "]");
		return clkdate;
	}

	public void custRef(String retrive) {
		uiTestHelper.waitForObject(ipcustRef).click();
		driver.findElement(ipcustRef).sendKeys(retrive);
	}

	public void clickerrorbtn() {

		try {
			WebElement ele = uiTestHelper.waitForObject(errmsg);
			ele.click();
		} catch (Exception e) {
			System.out.println("Not able to click error msg");
		}
	}

	public void entraltcongnnum(String Retrive) {

		uiTestHelper.waitForObject(altcongnnum).click();
		driver.findElement(altcongnnum).sendKeys(Retrive);
	}

	public void sltSenContry(StringBuilder country) {
		WebElement senderCountry = uiTestHelper.waitForObject(senCountrynm);
		senderCountry.sendKeys(country + " ");
		senderCountry.sendKeys(Keys.BACK_SPACE);
		WebElement senderCountryOption = uiTestHelper.waitForObject(By.xpath("//span[text()='" + country + "']"));
		senderCountryOption.click();
	}

	public String getDeliveryAreaOriginZone() {
		WebElement deliveryArea = uiTestHelper.waitForObject(deliveryAreaOriginZoneCode);
		return deliveryArea.getText();
	}

	// Delivery Area Info
	public String getDeliveryAreaDestinationZone() {
		WebElement deliveryArea = uiTestHelper.waitForObject(deliveryAreaDestZoneCode);
		return deliveryArea.getText();
	}

	public String getDeliveryAreaOriginZoneDesc() {
		WebElement deliveryArea = uiTestHelper.waitForObject(deliveryAreaOriginDesc);
		return deliveryArea.getText();
	}

	public String getDeliveryAreaDestinationZoneDesc() {
		WebElement deliveryArea = uiTestHelper.waitForObject(deliveryAreaDestDesc);
		return deliveryArea.getText();
	}

	public void clickDeliveryAreaInfo() {
		WebElement deliveryArea = uiTestHelper.waitForObject(closeDeliveryArea);
		deliveryArea.click();
	}

	// Invoice and Rating
	public void clickInvoiceRating() {
		WebElement invoicerate = uiTestHelper.waitForObject(invoiceandRating);
		invoicerate.click();
	}

	public void sltoptnCountrynm(StringBuilder optnCountry) {
		uiTestHelper.waitForObject(By.xpath("//span[text()='" + optnCountry + "']")).click();

	}

	public void sltRecContry(StringBuilder country) {
		WebElement sltRecContry = uiTestHelper.waitForObject(recCountrynm);
		sltRecContry.click();
		sltRecContry.sendKeys(country + " ");
		sltRecContry.sendKeys(Keys.BACK_SPACE);
	}

	// Sender account number
	public void ipSenAccuNum(String accNum) {
		uiTestHelper.waitForObject(senAccNum).click();
		driver.findElement(senAccNum).sendKeys(accNum);

	}

	// Reciver account number
	public void ipRecAccuNum(String accNum) {
		uiTestHelper.waitForObject(recAccNum).click();
		driver.findElement(recAccNum).sendKeys(accNum);

	}

	// Sender company name
	public void senCompyNm(String compynm) {
		uiTestHelper.waitForObject(senCompyNm).click();
		driver.findElement(senCompyNm).sendKeys(compynm);
	}

	// Recevier company name
	public void recCompyNm(String compynm) {
		uiTestHelper.waitForObject(recCompyNm).click();
		driver.findElement(recCompyNm).sendKeys(compynm);
	}

	// Sender Post Code
	public void senPostCode(String postcode) {
		uiTestHelper.waitForObject(senPostCode).click();
		driver.findElement(senPostCode).sendKeys(postcode);
	}

	// Reciver Post Code
	public void recPostCode(String postcode) {
		uiTestHelper.waitForObject(recPostCode).click();
		driver.findElement(recPostCode).sendKeys(postcode);
	}

	// Sender Town Name
	public void senTownNm(String townNm) {
		uiTestHelper.waitForObject(senTownNm).click();
		driver.findElement(senTownNm).sendKeys(townNm);
	}

	// Reciver Town Name
	public void recTownNm(String townNm) {
		uiTestHelper.waitForObject(recTownNm).click();
		driver.findElement(recTownNm).sendKeys(townNm);
	}

	// Sender Depot
	public void senDepot(String depot) {
		depot = depot.toUpperCase();
		uiTestHelper.waitForObject(senDepot).click();
		driver.findElement(senDepot).sendKeys(depot);
		WebElement depotopt = uiTestHelper.waitForObject(By.xpath("//span[contains(text(),'" + depot + "')]"));
		depotopt.click();

	}

	// Receiver Depot
	public void recDepot(String depot) {
		depot = depot.toUpperCase();
		uiTestHelper.waitForObject(recDepot).click();
		driver.findElement(recDepot).sendKeys(depot);
		WebElement depotopt = uiTestHelper.waitForObject(By.xpath("//span[contains(text(),'" + depot + "')]"));
		depotopt.click();
	}

	// show More Icon on the Consignment History
	public void clickShowMoreIcon() {
		WebElement ele = driver.findElement(By.xpath("//div[@class='dt-outer-container']/div"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollLeft += 500", ele);
		WebElement icon = uiTestHelper.waitForObject(showMoreIcon);
		icon.click();
	}

	// History Additional Info details
	public String getStatus() {
		WebElement histdetails = uiTestHelper.waitForObject(additionalInfoTitle);
		return histdetails.getText();
	}

	public String getUserid() {
		WebElement histdetails = uiTestHelper.waitForObject(userId);
		return histdetails.getText();
	}

	public String getStatusDepot() {
		WebElement histdetails = uiTestHelper.waitForObject(statusDepot);
		return histdetails.getText();
	}

	public String getSendDateTime() {
		WebElement histdetails = uiTestHelper.waitForObject(sendDateTime);
		return histdetails.getText();
	}

	public String getReceivedDateTime() {
		WebElement histdetails = uiTestHelper.waitForObject(receiveDateTime);
		return histdetails.getText();
	}

	public String getRoundNo() {
		WebElement histdetails = uiTestHelper.waitForObject(roundNo);
		return histdetails.getText();
	}

	public String getRunSheetNo() {
		WebElement histdetails = uiTestHelper.waitForObject(runsheetNo);
		return histdetails.getText();
	}

	public void acceptErrorMessageAfterCreateCase() {
		uiTestHelper.waitForObject(createCaseRedErrorMsg).click();
	}

	public boolean verifyConsignmentTable() {
		WebElement ele = uiTestHelper.waitForObject(cognTbl());
		return ele.isDisplayed();
	}

	public String getConsignmentInvalidFormatErrorMessage_for_lessthan12digit() {
		WebElement ele = uiTestHelper.waitForObject(consignmentErrorMessage);
		return ele.getText();
	}

	public String getConsignmentInvalidFormatErrorMessage_for_lessthan6digit() {
		WebElement ele = uiTestHelper.waitForObject(invalidFormatMsg);
		return ele.getText();
	}

	public boolean isConsignmentHistoryButton() {
		WebElement ele = uiTestHelper.waitForObject(consignmentHistory);
		return ele.isDisplayed();
	}

	public boolean isConsignmentPiecesButton() {
		WebElement ele = uiTestHelper.waitForObject(consgnmentPieces);
		return ele.isDisplayed();
	}

	public void clickConsignmentHistoryButton() {
		WebElement ele = uiTestHelper.waitForObject(consignmentHistory);
		ele.click();
	}

	public void clickConsignmentPiecesButton() {
		WebElement ele = uiTestHelper.waitForObject(consgnmentPieces);
		uiTestHelper.clickJS(ele);
	}

	public void getConsignmentPiecesDetails() {
		String table="//*[@class='cTntSearch']//lightning-datatable//table";
		List<WebElement> pieceTableRow=uiTestHelper.waitForObjects(By.xpath(table+"//tr"));
		String pieceNumber=null;
		HashMap<String, String> map=new HashMap<String, String>();
		
		for(int i=1;i<=pieceTableRow.size()-1;i++) {
			List<WebElement> pieceTableCol=uiTestHelper.waitForObjects(By.xpath(table+"//tbody/tr["+i+"]/td"));
			for(int j=1;j<=pieceTableCol.size()-2;j++) {
				int h=j+1;
			pieceNumber=uiTestHelper.waitForObject(By.xpath(table+"//tbody//tr["+i+"]//th//lightning-base-formatted-text")).getText();
			String headervalue=uiTestHelper.waitForObject(By.xpath(table+"/thead//th["+h+"]//a/span[2]")).getText();
			String pieceDetails=uiTestHelper.waitForObject(By.xpath(table+"//tbody//tr["+i+"]//td["+j+"]//lightning-base-formatted-text[1]")).getText();			
			map.put(headervalue, pieceDetails);
			}
			Driver.report_MapValues("All availbale Piece Details are below for the Piece Tracking Number "+pieceNumber+":",map);
		}		
	}
	
	public void getConsignmentPieceStatus()  {
		String table="//*[@class='cTntSearch']//lightning-datatable//table";
		List<WebElement> pieceStatus = uiTestHelper.waitForObjects(
				By.xpath(table+"//tbody//button[@name='View']"));
		HashMap<String, String> map=new HashMap<String, String>();
		//HashMap<String, Media> historyMap=new HashMap<String, Media>();
		for (int i = 1; i <= pieceStatus.size(); i++) {
			String trackingNumber=uiTestHelper.waitForObject(By.xpath(table+"//tbody//tr["+i+"]//th//lightning-base-formatted-text")).getText();					
			WebElement viewButton= uiTestHelper.waitForObject(
					By.xpath(table+"//tbody//tr["+i+"]//button[@name='View']"));
			viewButton.click();
			String Status=uiTestHelper.waitForObject(By.xpath("//b[text()='STATUS']/following::span[1]")).getText();
			WebElement closeWindow=uiTestHelper.waitForObject(By.xpath("//button[text()='Close']"));
			closeWindow.click();
			WebElement historyButton=uiTestHelper.waitForObject(By.xpath(table+"//tbody//tr["+i+"]//button[@name='History']"));
			historyButton.click();
			Driver.attachScreenShot("History of Tracking Number - "+trackingNumber+" is below",trackingNumber);
			WebElement historyWindow=uiTestHelper.waitForObject(By.xpath("//button[text()='Close']"));
			historyWindow.click();
			map.put(trackingNumber, Status);
			
		}
		Driver.report_MapValues("All availbale Piece Tracking Number with Piece Status are below :",map);
	}
	
	public void scrollPieceDetailsTable() {
		String table="//*[@class='cTntSearch']//lightning-datatable//table";
		List<WebElement> pieceTableRow=uiTestHelper.waitForObjects(By.xpath(table+"//tr"));
		int rowSize=pieceTableRow.size();
		if(rowSize>7) {
		uiTestHelper.scrollTable("div[class*=\"slds-scrollable_y\"]");
		}
	}

	// Scroll USing JavaScript Executor
	public void scrollIntoView(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", ele);
	}

	// Wait for objects
	public WebElement waitForObjectClickable(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		return element;
	}

	public void additionalInfoCloseBtnClick() {
		WebElement closeBtn = uiTestHelper.waitForObject(additionalInfoCloseButton);
		closeBtn.click();
	}

	// Click USing JavaScript Executor

	public void clickJS(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ele);
	}
}