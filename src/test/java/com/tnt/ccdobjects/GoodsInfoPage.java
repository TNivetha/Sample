package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class GoodsInfoPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By goodsContent = By.xpath("//button[@name='goodsContents']");
	By contentDoc = By.xpath("//span[@title='Documents']");
	By contentNonDoc = By.xpath("//span[@title='Non-Documents']");
	By contentMixed = By.xpath("//span[@title='Mixed']");
	By goodsValue = By.xpath("//input[@name='goodsValue']");
	By goodsDescription = By.xpath("//input[@name='goodsDesc']");
	By consignmentPage = By.xpath("(//button[@title='Next'])[2]");
	By custReference = By.xpath("//input[@name='custReference']");
	By defaultCurrency = By.xpath("//input[@name='userCurrency']");
	By cupCurrency = By.xpath("//span[@title='CUP']");
	By goodsDescText = By.xpath("//span[contains(text(),'Goods Description')]");
	By typeofDangGood = By.xpath("//label[text()='Types of Dangerous Goods']");
	By errorMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='error']");

	// <-----------DG Goods------------------------>
	// Indicator of DG
	By dangGoodsNO = By.xpath("//span[@class='slds-checkbox_faux_container']");
	By dangGoodsYes = By
			.xpath("(//span[contains(text(),'Dangerous Goods')])[3]/following::span[@class='slds-checkbox_faux']");

	// Fields of DG
	By unnumberField = By.xpath("//input[@name='unapinumber']");
	By searchDangerousGoods = By.xpath("//input[@name='unapinumber']/following::button[@title='Search']");
	By typeofDG = By.xpath("//input[@name='typeOfDg']");
	By dgUOM = By.xpath("//input[@name='unitofmeasurement']");
	By packagingGroup = By.xpath("//input[@name='PackageGroup']");
	By deleteGoods = By.xpath("//button[@title='Delete']");
	By unNumber=By.xpath("//input[@name='unNumber']");
	By optionCode=By.xpath("//button[@name='optionCode']");

	// Error Message Validation
	By dgDuplicateUNNumber = By.xpath("//*[contains(text(),'UN number already selected')]");
	By goodShipmsg = By.xpath("//*[contains(text(),'Unable to ship this type of Dangerous good on the network')]");
	By incorrectUNNumberMsg = By.xpath("//*[contains(text(),'No record found for given search criteria')]");
	By errorMsgUnapprovedAccountDGShipment = By.xpath(
			"//div[text()='This account is not approved to ship this type of dangerous goods. Special Service has been enabled.']");
	By airOnlyDGMsg = By.xpath("//div[text()='This type can be shipped via Air only']");

	// Enhanced Liability Indicator
	By enhancedLiability = By.xpath("//input[@name='enhancedLiability']/following::span[2]");
	By enhancedLiabilityInput = By.xpath("//input[@name='enhancedLiability']/following::input[1]");

	public GoodsInfoPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	// Goods Content

	public void clickGoodsDoccontent() {
		WebElement content = uiTestHelper.waitForObject(goodsContent);
		uiTestHelper.clickJS(content);
		WebElement doc = uiTestHelper.waitForObject(contentDoc);
		uiTestHelper.clickJS(doc);
	}

	public String getGoodsContent() {
		WebElement ccy = uiTestHelper.waitForObject(goodsContent);
		return ccy.getAttribute("value");
	}

	public void clickGoodsNonDoccontent() {
		WebElement content = uiTestHelper.waitForObject(goodsContent);
		uiTestHelper.clickJS(content);
		WebElement nondoc = uiTestHelper.waitForObject(contentNonDoc);
		uiTestHelper.clickJS(nondoc);
	}

	public void clickGoodsMixedcontent() {
		WebElement content = uiTestHelper.waitForObject(goodsContent);
		uiTestHelper.clickJS(content);
		WebElement mixed = uiTestHelper.waitForObject(contentMixed);
		uiTestHelper.clickJS(mixed);
	}

	// Goods amount

	public void setGoodsValue(String GoodsValue) {
		WebElement value = uiTestHelper.waitForObject(goodsValue);
		value.clear();
		value.sendKeys(GoodsValue);
	}

	// Goods Description

	public void setGoodsDesc(String Description) {
		WebElement desc = uiTestHelper.waitForObject(goodsDescription);
		desc.clear();
		desc.sendKeys(Description);
	}

	// Customer Reference

	public void setCustomerReference(String reference) {
		WebElement ref = uiTestHelper.waitForObject(custReference);
		ref.clear();
		ref.sendKeys(reference);
	}

	// Currency

	public String getDefaultCurrency() {
		WebElement ccy = uiTestHelper.waitForObject(defaultCurrency);
		return ccy.getAttribute("value");
	}

	public void setCurrency(String currency) {
		WebElement ccy = uiTestHelper.waitForObject(defaultCurrency);
		ccy.click();
		WebElement cupccy = uiTestHelper.waitForObject(By.xpath("//span[@title='"+currency+"']"));
		uiTestHelper.scrollIntoView(cupccy);
		cupccy.click();
	}

	// Dangerous Goods

	public boolean verifyDangerousGoodsNo() {
		WebElement indicator = uiTestHelper.waitForObject(dangGoodsNO);
		return indicator.isEnabled();
	}

	public void clickDangerousGoodsYes() {
		WebElement indicator = uiTestHelper.waitForObject(dangGoodsYes);
		indicator.click();
	}

	public void clickDangerousGoodsInd() {
		WebElement indicator = uiTestHelper.waitForObject(dangGoodsNO);
		indicator.click();
	}

	public boolean verifyGoodsDescTitle() {
		WebElement title = uiTestHelper.waitForObject(goodsDescText);
		return title.isDisplayed();
	}

	public void setUNNumber(String UNNumber) {
		WebElement dgflow = uiTestHelper.waitForObject(unnumberField);
		dgflow.sendKeys(UNNumber);
	}

	public void selectDangerousGoods() {
		WebElement table = uiTestHelper.waitForObject(
				By.xpath("//h2[text()='Select Dangerous Goods']/following::table/tbody//button[@name='Select']"));
		table.click();
	}

	public void selectPackagingGroup(String packagingGroup) {
		WebElement table = uiTestHelper.waitForObject(By.xpath(
				"//h2[text()='Select Dangerous Goods']/following::table/tbody/tr/td//lightning-base-formatted-text[text()='"
						+ packagingGroup + "']//following::button[@name='Select'][1]"));
		table.click();
	}

	public void deleteDangerousGoods() {
		WebElement dgsec = uiTestHelper.waitForObject(deleteGoods);
		dgsec.click();
	}

	public boolean verifyDangerousGoodsDelete() {
		WebElement dgsec = uiTestHelper.waitForObject(deleteGoods);
		return dgsec.isDisplayed();
	}

	public boolean verifyDangeoursGoodsTable() {
		boolean istable = false;
		try {
			WebElement table = uiTestHelper.waitForObjectwithSec(
					By.xpath("//h2[text()='Select Dangerous Goods']/following::table/tbody/tr"),90);
			istable = table.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Dangerous Goods Selected");
			istable = true;
		}
		return istable;
	}

	public void setQuantity(String dgQuantity, int i) {
		WebElement value = uiTestHelper.waitForObject(By.xpath("(//input[@name='Quantity'])[" + i + "]"));
		value.clear();
		value.sendKeys(dgQuantity);
	}
	
	public String  getQuantity(int i) {
		WebElement value = uiTestHelper.waitForObject(By.xpath("(//input[@name='Quantity'])[" + i + "]"));
		return value.getAttribute("value");
	}

	public void clickdgOptions(int i) {
		WebElement dg = uiTestHelper.waitForObject(By.xpath("(//button[@name='optionCode'])[" + i + "]"));
		uiTestHelper.clickJS(dg);
	}

	public void setDangerousGoodsOptions(String dangerousgoods, int i) {
		clickdgOptions(i);
		WebElement dgoption = uiTestHelper.waitForObject(
				By.xpath("(//*[@name='optionCode']/following::lightning-base-combobox-item//span/span[text()='"
						+ dangerousgoods + "'])[" + i + "]"));
		dgoption.click();
	}
	public String getDangerousGoodsOptions( int i) {
		WebElement dgoption = uiTestHelper.waitForObject(optionCode);
		return dgoption.getAttribute("value");
	}

	public String getDGFullDescription() {
		WebElement dgSection = uiTestHelper.waitForObject(typeofDG);
		return dgSection.getAttribute("value");
	}

	public String getUOM() {
		WebElement dgSection = uiTestHelper.waitForObject(dgUOM);
		return dgSection.getAttribute("value");
	}

	public String getPacakagingGroup() {
		WebElement dgSection = uiTestHelper.waitForObject(packagingGroup);
		return dgSection.getAttribute("value");

	}
	
	public String getUNNumber() {
		WebElement dgSection = uiTestHelper.waitForObject(unNumber);
		return dgSection.getAttribute("value");

	}


	public boolean verifyDuplicateUNnumberMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(dgDuplicateUNNumber);
		return dgSection.isDisplayed();

	}

	public String getDuplicateUNnumberMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(dgDuplicateUNNumber);
		return dgSection.getText();

	}

	public boolean verifyUNnumberShipMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(goodShipmsg);
		return dgSection.isDisplayed();

	}

	public String getUNnumberShipMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(goodShipmsg);
		return dgSection.getText();

	}

	public boolean verifyIncorrectUNnumberMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(incorrectUNNumberMsg);
		return dgSection.isDisplayed();

	}

	public String getIncorrectUNnumberMsg() {
		WebElement dgSection = uiTestHelper.waitForObject(incorrectUNNumberMsg);
		return dgSection.getText();

	}

	public boolean verifyDGFullDesc() {
		WebElement dgSection = uiTestHelper.waitForObject(typeofDG);
		return dgSection.isDisplayed();
	}

	public boolean verifyDGUOM() {
		WebElement dgSection = uiTestHelper.waitForObject(dgUOM);
		return dgSection.isDisplayed();
	}

	public boolean verifyDGPackagingGroup() {
		WebElement dgSection = uiTestHelper.waitForObject(packagingGroup);
		return dgSection.isDisplayed();
	}

	public boolean verifyTypeofDangGood() {
		WebElement dgflow = uiTestHelper.waitForObject(typeofDangGood);
		return dgflow.isDisplayed();
	}

	public boolean verifyUNNumberDangGood() {
		WebElement dgflow = uiTestHelper.waitForObject(unnumberField);
		return dgflow.isDisplayed();
	}

	public boolean verifyUNNumberField() {
		WebElement dgflow = uiTestHelper.waitForObject(unnumberField);
		return dgflow.isEnabled();
	}

	public boolean verifyErrorMessage() {
		WebElement msg = uiTestHelper.waitForObject(errorMessage);
		return msg.isDisplayed();
	}

	public void searchDangerousGoods() {
		WebElement goodssearch = uiTestHelper.waitForObject(searchDangerousGoods);
		goodssearch.click();
	}

	public void clickEnhancedLiability() {
		WebElement productopt = uiTestHelper.waitForObject(enhancedLiability);
		productopt.click();
	}

	public boolean verifyEnhancedLiabilityIndicator() {
		WebElement productopt = uiTestHelper.waitForObject(enhancedLiability);
		return productopt.isDisplayed();
	}

	public boolean verifyEnhancedLiabilityField() {

		WebElement validServices = uiTestHelper.waitForObject(enhancedLiability);
		return validServices.isEnabled();

	}

	public void setEnhancedLiabilityInput(String elinput) {
		WebElement productopt = uiTestHelper.waitForObject(enhancedLiabilityInput);
		productopt.clear();
		productopt.sendKeys(elinput);
	}

	// regulate goods select button
	public void clickAirOnlyDG() {
		WebElement dg = uiTestHelper.waitForObject(airOnlyDGMsg);
		dg.click();
	}

	public String getAirDGMsg() {
		WebElement dg = uiTestHelper.waitForObject(airOnlyDGMsg);
		return dg.getText();
	}

	public boolean verifyAirOnlyDGMsg() {
		WebElement dg = uiTestHelper.waitForObject(airOnlyDGMsg);
		return dg.isDisplayed();
	}

	public boolean verifyDGUnapprovedAccountErrorMsg() {
		WebElement unapprovedAccountDGShipmentErrorMsg = uiTestHelper.waitForObject(errorMsgUnapprovedAccountDGShipment);
		System.out.println("Err Msg = " + unapprovedAccountDGShipmentErrorMsg.getText());
		return unapprovedAccountDGShipmentErrorMsg.isDisplayed();
	}

	public String getDGUnApprovedShipmentErrMsg() {
		WebElement unapprovedAccountDGShipmentErrorMsg = uiTestHelper.waitForObject(errorMsgUnapprovedAccountDGShipment);
		return unapprovedAccountDGShipmentErrorMsg.getText();

	}

	public void clickGoodsInfoNextBtn() {
		WebElement next = uiTestHelper.waitForObject(consignmentPage);
		next.sendKeys(Keys.ENTER);
		// next.click();
	}
}
