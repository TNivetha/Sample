package com.tnt.ccdobjects;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class ConsignmentPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By createcase = By.xpath("//button[contains(text(),'Create Case')]");
	By viewcase = By.xpath("//button[contains(text(),'View Case')]");
	By collectionandDeliveryAddresstab = By.xpath("//a[text()='COLLECTION/DELIVERY ADDRESS']");
	By collectionAddressTitle = By.xpath("//b[text()= 'COLLECTION ADDRESS']");
	By goodsWeightandDimTab = By.xpath("//a[text()='GOODS,WEIGHTS AND DIMENSIONS']");
	By goodsType = By.xpath("//b[text()= 'Goods Type']");
	By customsControlledTab = By.xpath("//a[text()='CUSTOMS CONTROLLED']");
	By certificateofOrigin = By.xpath("//b[text()= 'Certificate Of Origin']");
	By senderReceiverTab = By.xpath("//a[text()='SENDER/RECEIVER ADDRESS']");
	By senderAddressTitle = By.xpath("//b[text()='SENDER ADDRESS']");
	By consignmentOverviewTab = By.xpath("//a[text()='CONSIGNMENT OVERVIEW']");
	By custReference = By.xpath("//b[text()='Customer Reference']");
	By collectionDate = By.xpath("//b[text()='Collection Date']/following::dd[1]");
	By deliveryDate = By.xpath("//b[text()='Delivery Date']/following::dd[1]");
	By consignmentDetailsBtn = By.xpath("//button[text()='Consignment Details']");
	By consignmentPiecesBtn = By.xpath("//button[text()='Consignment Pieces']");
	By caseViewIcon = By.xpath("//button[@name='View']");
	By consignmentNo = By.xpath("//*[@class='cTntSearch']//b[contains(text(),'Consignment Number :')]");
	By casesOnShipmentPopUp = By.xpath("//b[contains(text(),'Cases on this shipment')]");
	By caseNumberColumnheader = By.xpath("//span[@title='Case Number']");
	By eyeIcon = By.xpath("//button[@name='View']");
	By horizontalScrollBar = By.xpath("//div[@class='slds-table_header-fixed_container slds-scrollable_x']");
	By multiEyeIconOne = By.xpath("(//button[@name='View'])[1]");
	By multiEyeIconTwo = By.xpath("(//button[@name='View'])[2]");
	By casesShipmentTable = By.xpath("//div[@class='slds-modal__content slds-p-around_medium']//table");
	By assignToMeTickBox = By.xpath("//span[contains(text(),'Assign to me')]/preceding-sibling::span");
	By deliveryAreaInfo=By.xpath("//button[text()='Delivery Area Information']");

	public ConsignmentPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public By viewbtnHistory() {
		return By.xpath("//button[text()='Consignment History']");
	}

	public By viewbtnPieces() {
		return By.xpath("//button[text()='Consignment Pieces']");

	}

	public By viewcase() {
		return viewcase;
	}

	public By createcase() {
		return createcase;
	}

	// casesOnShipmentPopUp
	public String getCasesShipmentText() {
		WebElement info = uiTestHelper.waitforPresenceOfObject(casesOnShipmentPopUp);
		return info.getText();
	}

	// caseNumberColumnheader
	public String caseNumberColValidation() {
		WebElement info = uiTestHelper.waitforPresenceOfObject(caseNumberColumnheader);
		return info.getText();
	}
	// By consignmentNumberProof=

	public String consignmentNumberValidation(String consignmentNumber) {
		WebElement consNumber = uiTestHelper
				.waitforPresenceOfObject(By.xpath("//span[contains(text(),'" + consignmentNumber + "')]"));
		return consNumber.getText();
	}

	// Piece Button
	public By pieceBtnCongn() {
		return By.xpath("//button[text()='Consignment Pieces']");
	}

	// Overview consignment tab
	public By overviewCosgn() {
		return By.xpath("//a[@id='overviewTab__item']");
	}

	// DateTime for Consignment History Page
	public By dateTimeCosgnHistory() {
		return By.xpath("//span[text()='Date and Time']");
	}

	// Status From Consignment History Page
	public By statusCosgnHistory() {
		return By.xpath("//span[text()='Status']");
	}

	// Status History for Consignment History Page
	public By statusHistoryCosgnHistory() {
		return By.xpath("//span[text()='Status Description']");
	}

	// Source From Consignment History Page
	public By srcCosgnHistory() {
		return By.xpath("//span[text()='Source']");
	}

	// Remark/Notes for Consignment History Page
	public By rmkNotesCosgnHistory() {
		return By.xpath("//span[text()='Remarks/Notes']");
	}

	// Table pieces no text from Consignment Pieces Page
	public By piecesNoConsgn() {
		return By.xpath("//th//span[text()='Piece No']");
	}

	// Location From Consignment History
	public By pieceStatusCosgn() {
		return By.xpath("//th//span[text()='Piece Status']");
	}

	// Location From Consignment History
	public By locatnCosgnHistory() {
		return By.xpath("//span[text()='Location']");
	}

	public By txttopCongnNum() {
		return By.xpath("//b[contains(text(),'Consignment Number : ')]");
	}

	public By topCosginmentNum(String num) {
		By csgnNum = By.xpath("//b[text()='Consignment Number : ' and text()='" + num + "']");
		return csgnNum;
	}

	public String getTopCosginmentNumber(String consignemntNo) {
		WebElement topCosginmentNumber = uiTestHelper.waitForObject(topCosginmentNum(consignemntNo));
		return topCosginmentNumber.getText();
	}

	public By consignmentNo() {
		By congnmntNo = By.xpath("//p[@title='Consignment Number']");
		return congnmntNo;
	}

	public By listConsignment() {
		By tblCosgn = By.xpath("//div[@class='dt-outer-container']//table/thead//th//div/span/span");
		return tblCosgn;
	}

	// Delivery area information
	public boolean verifyDeliveryAreaButton() {
		WebElement btn = uiTestHelper.waitForObjectwithSec(deliveryAreaInfo,90);
		return btn.isDisplayed();
	}

	// Set UserId on Login Page
	public void clickcreatecasebtn() {
		WebElement createcasebtn = uiTestHelper.waitForObject(createcase);
		createcasebtn.click();
	}

	public void clickviewcasebtn() {
		WebElement clkcasebtn = uiTestHelper.waitforPresenceOfObject(viewcase);
		uiTestHelper.clickJS(clkcasebtn);
	}

	public boolean verifyViewCase() {
		WebElement clkcasebtn = uiTestHelper.waitForObjectwithException(viewcase,5);
		return clkcasebtn.isDisplayed();
	}

	public boolean verifyCreateCase() {
		WebElement clkcasebtn = uiTestHelper.waitForObject(createcase);
		return clkcasebtn.isDisplayed();
	}

	public void clickViewCaseIcon() {
		WebElement ele = driver.findElement(By.xpath("//div[@class='dt-outer-container']/div"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollLeft += 700", ele);
		WebElement viewcase = uiTestHelper.waitForObject(caseViewIcon);
		viewcase.click();
	}

	public void clkBtnHistory() {
		uiTestHelper.waitForObject(viewbtnHistory()).click();
	}

	public void clkBtnPiecesCongn() {
		uiTestHelper.waitForObject(viewbtnPieces()).click();
	}

	public void clkBtnDeliveryArea() {
		WebElement btn = uiTestHelper.waitForObject(deliveryAreaInfo);
		uiTestHelper.clickJS(btn);
	}

	public void clickOverviewTab() {
		WebElement condtls = uiTestHelper.waitForObject(consignmentOverviewTab);
		condtls.click();
	}

	public void clickSenderReceiverAddressTab() {
		WebElement condtls = uiTestHelper.waitForObject(senderReceiverTab);
		condtls.click();
	}

	public void clickGoodsWeightandDimTab() {
		WebElement condtls = uiTestHelper.waitForObject(goodsWeightandDimTab);
		condtls.click();
	}

	public void clickCollectionDeliveryAddressTab() {
		WebElement condtls = uiTestHelper.waitForObject(collectionandDeliveryAddresstab);
		condtls.click();
	}

	public void clickCustomsControlledTab() {
		WebElement condtls = uiTestHelper.waitForObject(customsControlledTab);
		condtls.click();
	}

	public boolean verifyCustomerReference() {
		WebElement condtls = uiTestHelper.waitForObject(custReference);
		return condtls.isDisplayed();
	}

	public boolean verifySenderAddress() {
		WebElement condtls = uiTestHelper.waitForObject(senderAddressTitle);
		return condtls.isDisplayed();
	}

	public boolean verifyCollectionAddress() {
		WebElement condtls = uiTestHelper.waitForObject(collectionAddressTitle);
		return condtls.isDisplayed();
	}

	public boolean verifyGoodsType() {
		WebElement condtls = uiTestHelper.waitForObject(goodsType);
		return condtls.isDisplayed();
	}

	public boolean verifyCertificateOrigin() {
		WebElement condtls = uiTestHelper.waitForObject(certificateofOrigin);
		return condtls.isDisplayed();
	}

	public boolean verifyConsignmentDetailsBtn() {
		WebElement condtls = uiTestHelper.waitForObject(consignmentDetailsBtn);
		return condtls.isDisplayed();
	}

	public boolean verifyConsignmentPiecesBtn() {
		WebElement condtls = uiTestHelper.waitForObject(consignmentPiecesBtn);
		return condtls.isDisplayed();
	}

	public boolean verifyConsignmentHistoryBtn() {
		WebElement condtls = uiTestHelper.waitForObject(viewbtnHistory());
		return condtls.isDisplayed();
	}

	public String getConsignmentNo() {
		WebElement ele = uiTestHelper.waitForObject(consignmentNo);
		return ele.getText();
	}

	// assignToMeTickBox
	public void clickAssignToMeBox() {
		uiTestHelper.waitForObject(assignToMeTickBox).click();
	}

	public void clickEyeIconAndScrollRight() {
		WebElement horizontalScroll = uiTestHelper.waitForObject(horizontalScrollBar);
		horizontalScroll.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollLeft +=500", horizontalScroll);
		WebElement ele = driver.findElement(eyeIcon);
		ele.click();
	}

	public void clicksecondEyeIconAndScrollRight() {
		WebElement horizontalScroll = uiTestHelper.waitForObject(horizontalScrollBar);
		horizontalScroll.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollLeft +=500", horizontalScroll);
		WebElement ele = driver.findElement(multiEyeIconTwo);
		ele.click();
	}

	public void checkCaseShipmentTableRows() {
		WebElement shipmentTable = uiTestHelper.waitForObject(casesShipmentTable);
		List<WebElement> tableRows = shipmentTable.findElements(By.xpath("//tbody//tr"));

		assertEquals(2, tableRows.size());
	}

}

