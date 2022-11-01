package com.tnt.ccdobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class MyOpenCasesListViewPage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By changeOwner=By.xpath("//div[@title='Change Owner']");
	By newCase=By.xpath("//div[@title='New']");
	By actions=By.xpath("//a[@title='Action']");
	By caseRemarks=By.xpath("//a[@title='Case Remark']");
	By showMoredropDown=By.xpath("//a[contains(@title,'more actions')]");
	By accept=By.xpath("//a[@title='Accept']");
	By remarksForCustomer=By.xpath("//a[@title='Remark for Customer']");
	By monitorActivity=By.xpath("//div[@title='Monitor Activity']");
	By callBack=By.xpath("//a[@title='Call Back']");
	By closeCase=By.xpath("//a[@title='Close Case']");
	By senderCaseContact=By.xpath("//a[@title='Sender Case Contact']");
	By receiverCaseContact=By.xpath("//a[@title='Receiver Case Contact']");
	By dedicatedCustAction=By.xpath("//a[@title='Dedicated Customer Action required']");
	By changeOIBMemeber= By.xpath("//div[@title='Change OIB Member']");
	By ok=By.xpath("//button[@title='OK']");
	
	//Merged Actions
	
	By mergedActions=By.xpath("//div[@title='Merged Action']");
	By iframeMergedAction=By.xpath("//iframe[@title='accessibility title']");
	By mergedActCloseCase=By.xpath("(//span[@class='slds-checkbox_faux'])[5]");
	By mergedActChngOIB=By.xpath("//input[@value='changeOIBMember']");
	
	//Change OIB Member
	By relateToDrpDwn= By.xpath("//button[@title='Toggle content action']");
	By user= By.xpath("//span[@title='User']");
	By queue= By.xpath("//span[@title='Queue']");
	By changeOIBFrame=By.xpath("//iframe[@title='accessibility title']");
	By searchValueInOIB=By.xpath("//input[@placeholder='Search...']");
	By saveOIB=By.xpath("//button[@title='Perform Action']");
  
	public MyOpenCasesListViewPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}
		public void clickChangeOwner() {
		WebElement changeOwnerButton=uiTestHelper.waitForObject(changeOwner);
		changeOwnerButton.click();
	}
	public void clicknewCase() {
		WebElement newCaseButton=uiTestHelper.waitForObject(newCase);
		newCaseButton.click(); 
	}
	public void clickChangeOIBMember() {
		WebElement changeOIBMemberButton=uiTestHelper.waitForObject(changeOIBMemeber);
		changeOIBMemberButton.click();
	}
	public boolean verifyChangeOIBMemberBtn() {
		WebElement showChangeOIBMemberBtn = uiTestHelper.waitForObject(changeOIBMemeber);
		return showChangeOIBMemberBtn.isDisplayed();
	}
	public boolean verifyMergedActChngOIBChkBox() {
		WebElement showmergedActChngOIBChkBox = uiTestHelper.waitForObject(mergedActChngOIB);
		return showmergedActChngOIBChkBox.isDisplayed();
	}
	public void clickMergedActChngOIBChkBox() {
		WebElement mergedActchangeOIBMemberChkBox=uiTestHelper.waitForObject(mergedActChngOIB);
		uiTestHelper.clickJS(mergedActchangeOIBMemberChkBox);
	}
	public void clickMergedActions() {
		WebElement mergedActionsButton=uiTestHelper.waitForObject(mergedActions);
		mergedActionsButton.click();
	}
	public void chkMergedActCloseCase() {
		WebElement chkMergedActCloseCase=uiTestHelper.waitForObject(mergedActCloseCase);
		chkMergedActCloseCase.click();
	}
	public void switchToFrameMergedActions() {
		uiTestHelper.waitForObject(iframeMergedAction);
		WebElement frame=uiTestHelper.waitForObject(iframeMergedAction);
		driver.switchTo().frame(frame);
	}
	
	public void clickAction() {
		WebElement actionsButton=uiTestHelper.waitForObject(actions);
		actionsButton.click();
	}
	public void clickshowMoredropDown() {
		WebElement showMoredropDownButton=uiTestHelper.waitForObject(showMoredropDown);
		showMoredropDownButton.click();
	}
	public void clickAcceptCase()	{
		WebElement acceptBtn=uiTestHelper.waitForObjectToBeClickable(accept);
		acceptBtn.click();
	}
	public void clickCaseRemarks() {
		WebElement caseRemarksButton=uiTestHelper.waitForObject(caseRemarks);
		caseRemarksButton.click();
	}
	public void clickRemarksForCustomer() {
		WebElement remarksForCustomerButton=uiTestHelper.waitForObject(remarksForCustomer);
		remarksForCustomerButton.click();
	}
	public void clickMonitorActivity() {
		WebElement monitorActivityButton=uiTestHelper.waitForObject(monitorActivity);
		monitorActivityButton.click();
	}
	public void clickCallBack() {
		WebElement callBackButton=uiTestHelper.waitForObject(callBack);
		callBackButton.click();
	}
	public void clickCloseCase() {
		WebElement closeCaseButton=uiTestHelper.waitForObject(closeCase);
		closeCaseButton.click();
	}
	public void clickSenderCaseContact() {
		WebElement senderCaseContactButton=uiTestHelper.waitForObject(senderCaseContact);
		senderCaseContactButton.click();
	}
	public void clickReceiverCaseContact() {
		WebElement receiverCaseContactButton=uiTestHelper.waitForObject(receiverCaseContact);
		receiverCaseContactButton.click();
	}
	public void clickDedicatedCustActiont() {
		WebElement dedicatedCustActionButton=uiTestHelper.waitForObject(dedicatedCustAction);
		dedicatedCustActionButton.click();
	}
	public void clickOK() {
		WebElement okButton=uiTestHelper.waitForObject(ok);
		okButton.click();
	}
	
	//Change OIB Member functionality
	
	public void clickrelateToDrpDwn() {
		WebElement relateToDrpDwnBtn=uiTestHelper.waitForObject(relateToDrpDwn);
		relateToDrpDwnBtn.click();
	}
	public void clickUser() {
		WebElement userButton=uiTestHelper.waitForObject(user);
		userButton.click();
	}
	public void clickQueue() {
		WebElement queueButton=uiTestHelper.waitForObject(queue);
		queueButton.click();
	}
	public boolean verifyUserDisplayed() {
		WebElement showUser = uiTestHelper.waitForObject(user);
		return showUser.isDisplayed();
	}
	public boolean verifyQueueDisplayed() {
		WebElement showqueue = uiTestHelper.waitForObject(queue);
		return showqueue.isDisplayed();
	}
	public void switchToFrameChangeOIB() {
		uiTestHelper.waitForObject(changeOIBFrame);
		WebElement frame=uiTestHelper.waitForObject(changeOIBFrame);
		driver.switchTo().frame(frame);
	}
	public void setOIBValue(String oibName) {
		WebElement oib = uiTestHelper.waitForObject(searchValueInOIB);
		oib.click();
		oib.sendKeys(oibName);
		driver.findElement(By.xpath("//span[@title='"+oibName+"']")).click();
	}
	public void clickSaveOIBMemberBtn() {
		WebElement saveBtn=uiTestHelper.waitForObject(saveOIB);
		saveBtn.click();
	}
}
