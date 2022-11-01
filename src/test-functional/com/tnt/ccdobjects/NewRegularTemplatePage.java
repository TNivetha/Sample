package com.tnt.ccdobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class NewRegularTemplatePage {
	WebDriver driver;
	UiTestHelper uiTestHelper;

	// Click on New Template
	By NewTemplate = By
			.xpath("//*[contains(text(),'Regular Booking Template')]/following::button[text()='New Template(s)']");

	// Click on View button
	By viewButton = By.xpath("//*[contains(text(),'Regular Booking Template')]/following::button[text()='View']");
	By regularbookingtext = By.xpath("//*[contains(text(),'Regular Booking Template')]");
	By tempViewButton = By.xpath(
			"//*[contains(text(),'Regular Booking Template')]/following::table/tbody/tr[1]/td[3]//button/lightning-primitive-icon");
	By verifyTempInformation = By
			.xpath("//div[contains(text(),'Regular Template')]/following::slot[@name='primaryField'][1]//span");
	By getTempID = By.xpath(
			"//*[contains(text(),'Regular Booking Template')]/following::table/tbody/tr[1]/td[2]//span/div/lightning-base-formatted-text");
	By editButton = By.xpath("//button[@name='Regular_Template__c.Edit']");
	By viewTemplateIcon=By.xpath("//button[@name='View Template']");
	
	// Caller Information
	By customerDescription = By.xpath("//*[(@name='customerDescription')]");
	
	//Collection address
	By collectionAddressTitle=By.xpath("//span[text()='Collection Address Details']");

	// Payment Terms
	By getContactName = By.xpath("//input[@name='contactName']");
	By getContactPhone = By.xpath("//input[@name='contactPhone']");
	By paymentTerms = By.xpath("//button[@name='paymentTerms']");
	By payingAccNumber = By.xpath("//input[@name='PaymentAccountNumber']");
	By selectCountry = By.xpath("//input[@name='senderCountry']");
	By receiverCountry=By.xpath("//label[text()='Country/Territory']/following::input[@name='senInputName'][1]");

	// Consignment information
	By driverInstructions = By.xpath("//input[@name='driverInstructions']");
	By selectContents = By.xpath("//*[text()='Contents']");
	By consignmentQuantity = By.xpath("//input[@name='quantity']");
	By consignmentWeight = By.xpath("//input[@name='totalWeight']");
	By addAnotherItem = By.xpath("//button[@title='Add another item']");
	By deleteConsignment = By.xpath("//button[@title='Delete']");

	// Requesting Contact Info
	By requestingsales = By.xpath("//*[contains(text(),'Requesting Sales Contact')]");
	By requestingcontactFullName = By.xpath("//input[@name='FullName']");
	By requestingcontactTelephoneNumber = By.xpath("//input[@name='TelephoneNumber']");

	// Authorising Contact Info
	By authorisingcontactFullName2 = By.xpath("//input[@name='FullName2']");
	By authorisingcontactTelephoneNumber2 = By.xpath("//input[@name='TelephoneNumber2']");

	// Add Schedules button
	By scheduleTitle=By.xpath("//span[text()='Schedule Information']");
	By addSchedulesbutton = By.xpath("//button[text()='Add Schedules']");
	By clickSavebutton = By.xpath("//button[@title='Save']");
	By selectScheduleforSaturday = By.xpath("(//span[text()='S']/preceding::span[1])[2]");

	// Add Schedules button while editing existing booking
	By clickAddSchedulesButton = By.xpath("(//div/lightning-button)[5]//button");
	By selectAddScheduleForNewSchedule = By.xpath("(//div/lightning-button)[3]//button");
	By readyTimenew = By.xpath("(//input[@name='readyTime'])");
	By toTimenew = By.xpath("(//input[@name='closeTime'])");
	By setUnavailableWindowStartTm = By.xpath("(//label[text()='Unavailable Window Start'])[1]/following::input[1]");
	By setUnavailableWindowEndTm = By.xpath("(//label[text()='Unavailable Window End'])[1]/following::input[1]");
	By clickSaveButtonAfterUnavailableTime = By.xpath("//button[text()='Save']");

	// Schedule information ready time and close time
	By readyTime = By.xpath("//input[@name='readyTime']");
	By closeTime = By.xpath("//input[@name='closeTime']");

	// Creating template
	By clickCancelBtnAfterTempCreation = By.xpath("//button[@name='Regular_Template__c.Cancel']");
	By displayCancelMessage = By.xpath(
			"//div/div/div/div[2]/section/c-cancel-regular-booking-l-w-c/lightning-card/article/div[2]/slot/div[1]/div/b");
	By clickYesOptionForCancellingTemplate = By.xpath("//button[text()='Yes']");
	By checkCancelledStatus = By.xpath("//p[text()='Status']/following::lightning-formatted-text[1]");
	By checkRedCrossIsDisplayed = By.xpath("//div[text()='Regular Template']/following::img[1]");

	// Unconfirmed held button
	By unconfirmedheldbtn = By.xpath("//*[contains(text(),'Unconfirmed Held')]");
	By checkStatus = By.xpath("//*[text()='Unconfirmed']/following::div/p[2]");

	// Cancel button
	By cancelbutton = By.xpath("//button[text()='Cancel']");

	By createTemplateButton = By.xpath("//button[text()='Create Template']");
	By errorMessage = By.xpath("//div[contains(text(),'Missing fields')]");
	By postCodetxt = By.xpath("//input[@name='collectionPostCode']");
	By towntxt = By.xpath("//input[@name='collectionTown']");
	By validateAddressButton = By.xpath("//button[@title='Search']");
	By addressValidateSuccessMsg = By.xpath("//*[contains(text(),'Success:')]");
	By validateContactButton = By.xpath("(//button[@title='Search'])[2]");

	By totalWeight = By.xpath("//input[@name='totalWeight']");
	By band = By.xpath("//input[@name='band']");
	By banddropdownIcon = By.xpath("//input[@name='band']/following::div[1]//lightning-primitive-icon");
	By totalWeightMandateIcon = By
			.xpath("//button[@title='Add another item']/following::lightning-input[1]/label/abbr[@title='required']");

	By confirmTemplateMsg = By.xpath("//span[contains(text(),'Regular Booking Template is created')]");

	public NewRegularTemplatePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	/**
	 * Click on new template button
	 */
	public void newTemplate() {
		WebElement newTemplatebtn = uiTestHelper.waitForObject(NewTemplate);
		uiTestHelper.clickJS(newTemplatebtn);
	}

	/**
	 * Method to click on view button
	 */
	public void viewbutton() {
		WebElement viewbtn = uiTestHelper.waitForObject(viewButton);
		uiTestHelper.scrollIntoView(viewbtn);
		uiTestHelper.clickJS(viewbtn);
	}

	/**
	 * to get total count of list items in view template
	 * 
	 * @return
	 */
	public int getViewTemplateDetails() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//*[contains(text(),'Regular Booking Template')]/following::table[1]/tbody/tr")));
		WebElement templateTable =uiTestHelper.waitForObject(By.xpath("//*[contains(text(),'Regular Booking Template')]/following::table[1]/tbody"));
		List<WebElement> objRow1 = templateTable.findElements(By.tagName("tr"));
		int row_count1 = objRow1.size();
		System.out.println("Row count in Valid Services table is " + row_count1);
		return row_count1;
	}

	public void viewTemplate(String templateID) {
		int rowCount = getViewTemplateDetails();
		for (int i = 1; i <= rowCount; i++) {
			String id = uiTestHelper.waitForObject(
					By.xpath("//*[contains(text(),'Regular Booking Template')]/following::table[1]/tbody/tr[" + i
							+ "]/td[2]//lightning-base-formatted-text")).getText();
			System.out.println(id+","+templateID);
			if (id.equalsIgnoreCase(templateID.trim())) {
				WebElement viewTemplate = uiTestHelper.waitForObject(
						By.xpath("//*[contains(text(),'Regular Booking Template')]/following::table[1]/tbody/tr[" + i
								+ "]/td[3]//button[@name='View Template']"));
				uiTestHelper.clickJS(viewTemplate);
				break;
			}
		}
	}
	
	public boolean verifyViewTemplateIcon() {
		WebElement template=uiTestHelper.waitForObject(viewTemplateIcon);
		return template.isDisplayed();
	}

	/**
	 * to select a particular template
	 */
	public void tempViewbutton() {
		WebElement tempviebtn = uiTestHelper.waitForObject(tempViewButton);
		uiTestHelper.scrollIntoView(tempviebtn);
		uiTestHelper.clickJS(tempviebtn);
	}

	/**
	 * to get template ID from view template page
	 */
	public String getParticularTemplateID() {
		WebElement vertempinfo = uiTestHelper.waitForObject(verifyTempInformation);
		String tempnumber = vertempinfo.getText();
		return tempnumber;

	}

	/**
	 * to get template ID from homepage
	 * 
	 * @return
	 */
	public String getTemplateID() {
		WebElement tempID = uiTestHelper.waitForObject(getTempID);
		String templateID = tempID.getText();
		return templateID;
	}

	/**
	 * Clicking on Edit button of a template
	 */
	public void clickTempEditButton() {
		WebElement tempeditbtn = uiTestHelper.waitForObjectToBeClickable(editButton);
		uiTestHelper.clickJS(tempeditbtn);
	}
	public boolean verifyTemplateEditButton() {
		WebElement tempeditbtn = uiTestHelper.waitForObject(editButton);
		return tempeditbtn.isDisplayed();
	}
	/**
	 * Clicking on Add schedules button for Editing existing regular booking
	 **/
	public void clickAddSchedulesButtonEditBookingfirst() {
		WebElement addschedulesbtn2 = uiTestHelper.waitForObject(clickAddSchedulesButton);
		uiTestHelper.scrollIntoView(addschedulesbtn2);
		uiTestHelper.clickJS(addschedulesbtn2);
	}

	/**
	 * Updating weight for existing regular booking
	 **/
	public void updateWeightForExistingTemplate(String weightupdt) {
		WebElement templateweight = uiTestHelper.waitForObject(totalWeight);
		templateweight.clear();
		templateweight.sendKeys(weightupdt);
	}

	/**
	 * entering caller information
	 */
	public void customerDescription(String desc) {
		WebElement Custname = uiTestHelper.waitForObject(customerDescription);
		Custname.sendKeys(desc);
	}

	public void setContactName(String contactnm) {
		WebElement contactname = uiTestHelper.waitForObject(getContactName);
		contactname.clear();
		contactname.sendKeys(contactnm);
	}

	public void setContactPhone(String contactph) {
		WebElement contactphone = uiTestHelper.waitForObject(getContactPhone);
		contactphone.clear();
		contactphone.sendKeys(contactph);
	}

	/**
	 * Selecting payment terms
	 * 
	 * @param callerofPayment
	 */
	public void paymentTerms(String callerofPayment) {
		WebElement input = uiTestHelper.waitForObjectToBeClickable(paymentTerms);
		uiTestHelper.clickJS(input);
		WebElement paymentterm = uiTestHelper.waitForObjectToBeClickable(
				By.xpath("//*[text()='Payment Terms']/following::div/lightning-base-combobox-item/span/span[text()='"
						+ callerofPayment + "']"));
		uiTestHelper.clickJS(paymentterm);
	}
	
	public boolean verifyPaymentTermsField() {
		WebElement ele=uiTestHelper.waitForObject(paymentTerms);
		return ele.isEnabled();
	}
	
	public boolean verifyAccNumberField() {
		WebElement ele=uiTestHelper.waitForObject(payingAccNumber);
		return ele.isEnabled();
	}

	public boolean verifyReceiverCountryField() {
		WebElement ele=uiTestHelper.waitForObject(receiverCountry);
		return ele.isEnabled();
	}


	/**
	 * getting account number
	 * 
	 * @param accountnr
	 */
	public void setAccountNumber(String accountnr) {
		WebElement accnr = uiTestHelper.waitForObject(payingAccNumber);
		accnr.clear();
		accnr.sendKeys(accountnr);
	}

	/**
	 * Selecting country
	 * 
	 * @param countrynm
	 */
	public void setCountry(String countrynm) {
		WebElement countryname = uiTestHelper.waitForObject(selectCountry);
		countryname.clear();
		countryname.sendKeys(countrynm);
		WebElement ctrname = uiTestHelper.waitForObject(By.xpath("//span[text()='" + countrynm + "']"));
		ctrname.click();
	}

	/**
	 * Clicking on Search button of a Contact
	 */
	public void validateSearchButton() {
		WebElement tempeditbtn = uiTestHelper.waitForObject(validateContactButton);
		tempeditbtn.click();
	}

	/**
	 * Setting driver instructions
	 * 
	 * @param driver
	 */
	public void setDriverInstructions(String driver) {
		WebElement instruct = uiTestHelper.waitForObject(driverInstructions);
		instruct.clear();
		instruct.sendKeys(driver);
	}

	/**
	 * Selecting contents
	 * 
	 * @param callerofContents
	 */
	public void setContents(String callerofContents) {
		WebElement content = uiTestHelper.waitForObject(selectContents);
		content.click();
		WebElement contentname = uiTestHelper.waitForObject(
				By.xpath("//*[text()='Contents']/following::div/lightning-base-combobox-item/span/span[text()='"
						+ callerofContents + "']"));
		contentname.click();
	}

	/**
	 * Setting consignment quantity
	 * 
	 * @param conquantity
	 */
	public void setConsignmentQuantity(String conquantity) {
		WebElement quantity = uiTestHelper.waitForObject(consignmentQuantity);
		quantity.clear();
		quantity.sendKeys(conquantity);
	}

	/**
	 * Setting consignment weight
	 * 
	 * @param conweight
	 */
	public void setConsignmentWeight(String conweight) {
		WebElement weight = uiTestHelper.waitForObject(consignmentWeight);
		weight.clear();
		weight.sendKeys(conweight);
	}

	/**
	 * Requesting sales contact
	 * 
	 * @param contactname
	 */
	public void setrequestingcontactFullName(String contactname) {
		WebElement name = uiTestHelper.waitForObject(requestingcontactFullName);
		name.clear();
		name.sendKeys(contactname);
	}

	public void setrequestingcontactTelephoneNumber(String contactphone) {
		WebElement phone = uiTestHelper.waitForObject(requestingcontactTelephoneNumber);
		phone.clear();
		phone.sendKeys(contactphone);
	}

	/**
	 * Authorising operation contact
	 * 
	 * @param contactname
	 */
	public void setauthorisingcontactFullName2(String contactname) {
		WebElement name = uiTestHelper.waitForObject(authorisingcontactFullName2);
		name.clear();
		name.sendKeys(contactname);
	}

	public void setauthorisingcontactTelephoneNumber2(String contactphone) {
		WebElement phone = uiTestHelper.waitForObject(authorisingcontactTelephoneNumber2);
		phone.clear();
		phone.sendKeys(contactphone);
	}

	/**
	 * Clicking on Add schedules button
	 */
	public void schedulesbutton() {
		WebElement addscheduleebtn = uiTestHelper.waitForObject(addSchedulesbutton);
		uiTestHelper.clickJS(addscheduleebtn);
		// addscheduleebtn.click();
	}

	/**
	 * Add ready time
	 * 
	 * @param rtime
	 */
	public void setReadyTime(String rtime) {
		WebElement readytime = uiTestHelper.waitForObject(readyTime);
		readytime.clear();
		readytime.sendKeys(rtime);
	}

	/**
	 * Add close time
	 * 
	 * @param ctime
	 */
	public void setCloseTime(String ctime) {
		WebElement closetime = uiTestHelper.waitForObject(closeTime);
		closetime.clear();
		closetime.sendKeys(ctime);
	}

	/**
	 * Method to add day
	 */
	public void selectScheduleforSaturday() {
		WebElement ele = uiTestHelper.waitForObject(selectScheduleforSaturday);
		uiTestHelper.clickJS(ele);
	}

	/**
	 * Method to click on Save button after adding day in schedule information
	 */
	public void addScheduleSavebutton() {
		WebElement clicksavebtn = uiTestHelper.waitForObjectToBeClickable(clickSavebutton);
		uiTestHelper.clickJS(clicksavebtn);
	}
	/**
	 * Method to check unconfirmed held button is enabled or not
	 * 
	 * @return
	 */
	public boolean isEnabledUnconfirmedHeldButton() {
		WebElement heldbtn = uiTestHelper.waitForObject(unconfirmedheldbtn);
		return heldbtn.isEnabled();
	}

	/**
	 * Unconfirmed held button
	 */
	public void clickUnconfirmedheldbutton() {
		WebElement heldbtn = uiTestHelper.waitForObject(unconfirmedheldbtn);
		heldbtn.click();
	}

	/**
	 * Method to check booking status
	 * 
	 * @return
	 */
	public String checkStatus() {
		WebElement status = uiTestHelper.waitForObject(checkStatus);
		return status.getText();
	}

	/**
	 * Method to check cancel button is enabled or not
	 * 
	 * @return
	 */
	public boolean isEnabledCancelButton() {
		WebElement cancelbtn = uiTestHelper.waitForObject(cancelbutton);
		return cancelbtn.isEnabled();
	}

	/**
	 * Cancel button
	 */
	public void clickCancelbutton() {
		WebElement cancelbtn = uiTestHelper.waitForObjectToBeClickable(cancelbutton);
		uiTestHelper.clickJS(cancelbtn);
	}

	/**
	 * Method to click on cancel button after template creation
	 */
	public void clickCancelBtnAfterTemplateCreated() {
		WebElement cancelbtn = uiTestHelper.waitForObject(clickCancelBtnAfterTempCreation);
		cancelbtn.click();
	}

	/**
	 * Method to check cancel message after clicking on cancel button
	 */
	public String checkMessageIsDisplayedAfterCancel() {
		WebElement status = uiTestHelper.waitForObject(displayCancelMessage);
		return status.getText();
	}

	/**
	 * Method to click on Yes for cancelling the regular booking
	 */
	public void clickYesButtonForTemplateCancelling() {
		WebElement yesbutton = uiTestHelper.waitForObject(clickYesOptionForCancellingTemplate);
		uiTestHelper.clickJS(yesbutton);
	}

	/**
	 * Method to check cancelled status
	 */
	public String checkBookingCancelledStatus() {
		WebElement cancelstatus = uiTestHelper.waitForObject(checkCancelledStatus);
		return cancelstatus.getText();
	}

	public boolean checkIfRedCrossIsDisplayed() {
		WebElement redcross = uiTestHelper.waitForObject(checkRedCrossIsDisplayed);
		return redcross.isDisplayed();
	}

	public boolean CheckImage() throws Exception {
		WebElement redcross = driver
				.findElement(By.xpath("//div[contains(text(),'Regular Template')]/parent::h1//img"));

		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				redcross);
		if (!ImagePresent) {
			System.out.println("Image not displayed.");
		} else {
			System.out.println("Image displayed.");
		}
		return ImagePresent;
	}

	// ------------------for editing the template TC 103 to 105

	// TC103
	public void scheduleforSaturday() {
		WebElement additionaldaycheckbox = uiTestHelper.waitForObject(selectScheduleforSaturday);
		uiTestHelper.scrollIntoView(driver.findElement(By.xpath("//*[contains(text(),' Caller Information')]")));
		additionaldaycheckbox.click();
	}

	public void addScheduleSaveBtnAfterAddingDay() {
		WebElement clicksavebtn = uiTestHelper.waitForObject(clickSavebutton);
		uiTestHelper.clickJS(clicksavebtn);
	}

	// TC104
	public void clickAddSchedulesButtonEditBookingsecond() {
		WebElement addschedule = uiTestHelper.waitForObject(selectAddScheduleForNewSchedule);
		uiTestHelper.clickJS(addschedule);
	}

	/**
	 * Add ready time
	 * 
	 * @param rtime
	 */
	public void setReadyTimenew(String rtimenew) {
		uiTestHelper.waitForObject(readyTimenew);
		int n = driver.findElements(readyTimenew).size();
		List<WebElement> allreadytime = driver.findElements(readyTimenew);
		allreadytime.get(n - 1).clear();
		allreadytime.get(n - 1).sendKeys(rtimenew);
	}

	/**
	 * Add close time
	 * 
	 * @param ctime
	 */
	public void setToTimenew(String totimenew) {
		uiTestHelper.waitForObject(toTimenew);
		int n = driver.findElements(toTimenew).size();
		List<WebElement> alltotime = driver.findElements(toTimenew);
		alltotime.get(n - 1).clear();
		alltotime.get(n - 1).sendKeys(totimenew);
	}

	public void addScheduleSavebuttonfornewschedule() {
		WebElement clicksavebtn = uiTestHelper.waitForObjectToBeClickable(clickSavebutton);
		clicksavebtn.click();
	}

	// 105
	public void setUnavailableWindowStartTime(String unavailablestarttime) {
		WebElement starttime = uiTestHelper.waitForObject(setUnavailableWindowStartTm);
		starttime.clear();
		starttime.sendKeys(unavailablestarttime);
	}

	public void setUnavailableWindowEndTime(String unavailableendttime) {
		WebElement endtime = uiTestHelper.waitForObject(setUnavailableWindowEndTm);
		endtime.clear();
		endtime.sendKeys(unavailableendttime);
	}

	public void addunavailableTimeSaveButton() {
		WebElement clicksavebtn = uiTestHelper.waitForObjectToBeClickable(clickSaveButtonAfterUnavailableTime);
		uiTestHelper.scrollIntoView(clicksavebtn);
		uiTestHelper.clickJS(clicksavebtn);
	}

	public void addItem() {
		WebElement item = uiTestHelper.waitForObjectToBeClickable(addAnotherItem);
		item.click();
	}

	public List<WebElement> getDeleteButtons() {
		List<WebElement> list = uiTestHelper.waitForObjects(deleteConsignment);
		return list;
	}

	/**
	 * Click on Create Template Button
	 */
	public void clickCreateTemplateButton() {
		WebElement createTemplateBtn = uiTestHelper.waitForObjectToBeClickable(createTemplateButton);
		uiTestHelper.scrollIntoView(createTemplateBtn);
		createTemplateBtn.click();
	}

	/**
	 * Method to return Error Message display on New Template
	 */
	public String getErrorMessage() {
		WebElement errorMsg = uiTestHelper.waitForObject(errorMessage);
		return errorMsg.getText();
	}

	/**
	 * Method to return Post code
	 * 
	 * @return Post code
	 */
	public String getPostCode() {
		WebElement postCodetext = uiTestHelper.waitForObject(postCodetxt);
		return postCodetext.getAttribute("value");
	}

	/**
	 * Method to clear Post code
	 */
	public void clearPostCode() {
		WebElement postCodetext = uiTestHelper.waitForObject(postCodetxt);
		postCodetext.clear();
	}

	/**
	 * Method to enter Post code
	 */
	public void setPostCode(String postCode) {
		WebElement postCodetext = uiTestHelper.waitForObject(postCodetxt);
		postCodetext.sendKeys(postCode);
	}

	/**
	 * Method to clear Town
	 */
	public void clearTown() {
		WebElement towntext = uiTestHelper.waitForObject(towntxt);
		towntext.clear();
	}

	/**
	 * Click Validate Address Button
	 */
	public void clickValidateAddressBtn() {
		WebElement validateAddressBtn = uiTestHelper.waitForObjectToBeClickable(validateAddressButton);
		validateAddressBtn.click();
	}

	/**
	 * Method to return Town
	 * 
	 * @return Town
	 */
	public String getTown() {
		WebElement towntext = uiTestHelper.waitForObject(towntxt);
		String town = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", towntext);
		return town;
	}

	/**
	 * Address Validate Successfully
	 * 
	 * @return return true if success message displayed
	 */
	public boolean isAddressValidateSuccessfully() {
		WebElement msg = uiTestHelper.waitForObject(addressValidateSuccessMsg);
		return msg.isDisplayed();
	}

	/**
	 * Method to enter Total Weight
	 */
	public void setTotalWeight(String totalWeightValue) {
		WebElement totalWeightTxt = uiTestHelper.waitForObject(totalWeight);
		totalWeightTxt.sendKeys(totalWeightValue);
	}

	/**
	 * Method to check Band drop down disable or enable
	 * 
	 * @return return true if success message displayed
	 */
	public boolean isBandEnabled() {
		WebElement bandDropDown = uiTestHelper.waitForObject(band);
		return bandDropDown.isEnabled();
	}

	/**
	 * Method to clear Total Weight
	 */
	public void clearTotalWeight() {
		WebElement totalWeightTxt = uiTestHelper.waitForObject(totalWeight);
		totalWeightTxt.clear();
	}

	/**
	 * Method to get Total Weight
	 */
	public String getTotalWeight() {
		WebElement totalWeightTxt = uiTestHelper.waitForObject(totalWeight);
		return totalWeightTxt.getAttribute("value");
	}

	/**
	 * Method to set Band drop down
	 */
	public void setBand(String bandVal) {
		WebElement bandDropDown = uiTestHelper.waitForObjectToBeClickable(banddropdownIcon);
		// bandDropDown.sendKeys("30.00 - 100.00");
		uiTestHelper.scrollIntoView(bandDropDown);
		uiTestHelper.clickJS(bandDropDown);
		WebElement bandDropDownOptions = uiTestHelper.waitForObjectToBeClickable(By.xpath(
				"//input[@name='band']/following::div/lightning-base-combobox-item[@data-value='" + bandVal + "']"));
		uiTestHelper.scrollIntoView(bandDropDownOptions);
		bandDropDownOptions.click();
	}

	// totalWeightMandateIcon
	public boolean isTotalWeightMandateIconDisplayed() {
		WebElement totalWeightMandateIconEle = uiTestHelper.waitForObject(totalWeightMandateIcon);
		return totalWeightMandateIconEle.isDisplayed();
	}

	public String getTemplateConfirmMsg() {
		WebElement confirm = uiTestHelper.waitForObject(confirmTemplateMsg);
		return confirm.getText();
	}
	public boolean verifyScheduleInfo() {
		WebElement info = uiTestHelper.waitForObject(scheduleTitle);
		return info.isDisplayed();
	}
	public boolean verifyTemplatePage() {
		WebElement info = uiTestHelper.waitForObject(collectionAddressTitle);
		return info.isDisplayed();
	}
}
