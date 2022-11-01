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

public class ConsignmentInfoPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By consignmentInfoTitle = By.xpath("//h2/span[text()='Consignment Information']");
	By addAnotherItem = By.xpath("//button[@title='Add another item']");
	By additionalInfo = By.xpath("(//button[@title='Next'])[3]");
	By box = By.xpath("//lightning-base-combobox-item[@data-value='box']");
	By pallet = By.xpath("//lightning-base-combobox-item[@data-value='pallet']");
	By envelope = By.xpath("//lightning-base-combobox-item[@data-value='envelope']");
	By verifyEnvelope = By.xpath("//input[@name='contentsOfEnvelope']");
	By verifyBox = By.xpath("//input[@placeholder='Box']");
	By deleteConsignment = By.xpath("//button[@title='Delete']");
	By stackable = By.xpath("//span[text()='Stackable']/following::input[@name='true']");
	By advisoryMsg = By.xpath("(//span[text()='Stackable'])[2]/following::div[2]");
	By stackableToggle = By.xpath("(//span[text()='Stackable'])[2]/following::span[2]");
	By dimension = By.xpath("(//button[@name='[object Object]'])[1]");
	By consignmentNote = By
			.xpath("//label//input[@name='Consignment Note']/following::span[@class='slds-checkbox_faux'][1]");

	// Error Msg
	By errorinAdding10Items = By.xpath("//div[contains(text(),'Maximum number of package lines reached')]");
	By lengthExceedMsg = By.xpath("//div[contains(text(),'Length exceeds max.')]");
	By widthExceedMsg = By.xpath("//div[contains(text(),'Width exceeds max.')]");
	By weigthExceedMsg = By.xpath("//div[contains(text(),'The number is too high.')]");
	By heightExceedMsg = By.xpath("//div[contains(text(),'Height exceeds max.')]");
	By totalVolumeExceedMsg = By
			.xpath("//div[contains(text(),'Total Volume exceeds restrictions for selected service')]");
	By totalWeigthExceedMsg = By.xpath("//div[contains(text(),'You can only ship boxes up to 70 kg in weight.')]");
	By quantityExceedMsg = By
			.xpath("//div[contains(text(),'The maximum number of packages in a single shipment is 99.')]");
	By quantityMinErrorMsg = By
			.xpath("//div[contains(text(),'The minimum number of packages in a single shipment is 1.')]");

	public String quantityInput = "(//input[@name='quantity'])[1]";
	public String lengthInput = "(//label[text()='Length']/following::input[1])[1]";
	public String widthInput = "(//label[text()='Width']/following::input[1])[1]";
	public String heightInput = "(//label[text()='Height']/following::input[1])[1]";
	public String weightInput = "(//label[text()='Weight (kg)']/following::input[1])[1]";
	public String palletheightInput = "(//label[text()='Height ']/following::input[1])[1]";

	public ConsignmentInfoPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public void addItem() {
		WebElement item = uiTestHelper.waitForObjectToBeClickable(addAnotherItem);
		uiTestHelper.clickJS(item);
	}

	public void clickConsignmentInfoNextBtn() {
		WebElement next = uiTestHelper.waitForObject(additionalInfo);
		// next.click();
		next.sendKeys(Keys.ENTER);
	}

	public void clickTypeBox() {
		WebElement type = uiTestHelper.waitForObject(box);
		uiTestHelper.clickJS(type);
	}

	public void clickTypePallet() {
		WebElement type = uiTestHelper.waitForObject(pallet);
		uiTestHelper.clickJS(type);
	}

	public void clickTypeEnvelope() {
		WebElement type = uiTestHelper.waitForObject(envelope);
		uiTestHelper.clickJS(type);
	}

	public boolean verifyTypeEnvelope() {
		WebElement env = uiTestHelper.waitForObject(verifyEnvelope);
		return env.isDisplayed();
	}

	public boolean verifyTypeOther() {
		WebElement env = uiTestHelper.waitForObject(verifyBox);
		return env.isDisplayed();
	}

	public boolean verifyAddItemBtn() {
		WebElement item = uiTestHelper.waitForObject(addAnotherItem);
		return item.isEnabled();
	}

	public List<WebElement> getDeleteButtons() {
		List<WebElement> list = uiTestHelper.waitForObjects(deleteConsignment);
		return list;
	}

	public void clickStackableToggle() {
		WebElement toggle = uiTestHelper.waitForObject(stackableToggle);
		toggle.click();
	}

	public boolean verifyAdvisoryMessage() {
		WebElement toggle = uiTestHelper.waitForObject(advisoryMsg);
		return toggle.isDisplayed();
	}

	public boolean verifyStackableToggleByDefault() {
		WebElement toggle = uiTestHelper.waitForObject(stackable);
		return toggle.isDisplayed();
	}

	public String getAdvisoryMessage() {
		WebElement msg = uiTestHelper.waitForObject(advisoryMsg);
		return msg.getText();
	}

	// Verify Error Msg's
	public boolean verifyErrorMsgAdding10Items() {
		WebElement item = uiTestHelper.waitForObject(errorinAdding10Items);
		return item.isDisplayed();
	}

	public boolean verifyLengthExceeds() {
		WebElement item = uiTestHelper.waitForObject(lengthExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyWeightExceeds() {
		WebElement item = uiTestHelper.waitForObject(weigthExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyWidthExceeds() {
		WebElement item = uiTestHelper.waitForObject(widthExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyHeightExceeds() {
		WebElement item = uiTestHelper.waitForObject(heightExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyTotalVolumeExceeds() {
		WebElement item = uiTestHelper.waitForObject(totalVolumeExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyTotalWeightExceeds() {
		WebElement item = uiTestHelper.waitForObject(totalWeigthExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyQuantityExceeds() {
		WebElement item = uiTestHelper.waitForObject(quantityExceedMsg);
		return item.isDisplayed();
	}

	public boolean verifyQuantityMinimum() {
		WebElement item = uiTestHelper.waitForObject(quantityMinErrorMsg);
		return item.isDisplayed();
	}

	public boolean verifyConsignmentInfoTitle() {
		WebElement item = uiTestHelper.waitForObject(consignmentInfoTitle);
		return item.isEnabled();
	}

	public String getDimension() {
		WebElement dim = uiTestHelper.waitForObject(dimension);
		return dim.getAttribute("data-value");
	}

	public void setDimension(String dimMeasurement, int index) {
		WebElement dim = uiTestHelper.waitForObject(dimension);
		uiTestHelper.clickJS(dim);
		WebElement dim1 = uiTestHelper.waitForObject(
				By.xpath("//lightning-base-combobox-item[@data-value='" + dimMeasurement + "'][" + index + "]"));
		dim1.click();
	}

	public void clickConsignmentNote() {
		WebElement productopt = uiTestHelper.waitForObjectToBeClickable(consignmentNote);
		uiTestHelper.clickJS(productopt);
	}

	public boolean verifyConnoteIndicator() {
		WebElement productopt = uiTestHelper.waitForObject(consignmentNote);
		return productopt.isDisplayed();
	}
	
	public String getQuantity() {
		return quantityInput;
	}
	public String getLength() {
		return lengthInput;
	}
	public String getWidth() {
		return widthInput;
	}
	public String getHeight() {
		return heightInput;
	}
	public String getWeight() {
		return weightInput;
	}
}
