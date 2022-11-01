package com.tnt.ccdobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.Test_Initializer;
import com.tnt.commonutilities.UiTestHelper;

public class AdditionalInfoPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By validServices = By.xpath("//button[@title='Valid Services']");
	By specialServices = By.xpath("//button[@title='Special']");
	By additionalInfo = By.xpath("(//button[@title='Next'])[3]");
	By getprice = By.xpath("//lightning-card//lightning-button/button[text()='Get Price']");
	By collectionDate = By.xpath("//input[@name='collectionDate']");
	By collectionDriverInstruction = By.xpath("//textarea[@name='colDriverInstructions']");
	By deliveryDriverInstruction = By.xpath("//textarea[@name='delDriverInstructions']");
	By fromCollWindowTime = By.xpath("//input[@name='loggedInUserTime']");
	By toCollWindowTime = By.xpath("//input[@name='collectionToTime']");
	By fromUnavailTime = By.xpath("//input[@name='avlFromTime']");
	By toUnavailTime = By.xpath("//input[@name='avlToTime']");
	By viewSummary = By.xpath("//button[contains(text(),'View Summary')]");
	By bookingSummary = By.xpath("//b[contains(text(),'BOOKING SUMMARY')]");
	By expresscost = By.xpath("//b[contains(text(),'BOOKING SUMMARY')]/following::h1/lightning-formatted-number");
	By exclVat = By.xpath("//b[contains(text(),'BOOKING SUMMARY')]/following::h2/lightning-formatted-number");
	By vat = By.xpath("//b[contains(text(),'BOOKING SUMMARY')]/following::h3/lightning-formatted-number");
	By totalvat = By.xpath("//b[contains(text(),'BOOKING SUMMARY')]/following::h4/lightning-formatted-number");
	By cancelViewSummary = By.xpath("//button[contains(text(),'Cancel')]");
	By addtionalOptions = By.xpath("//span[contains(text(),'Additional Options')]");
	By confirmBooking = By.xpath("//button[contains(text(),'Confirm Booking')]");
	By confirmBookingMsg = By.xpath("//span[contains(text(),'Booking is created')]");
	By emailBtn = By.xpath("//button[contains(text(),'Send Email')]");
	By updateBooking = By.xpath("//button[contains(text(),'Update Booking')]");
	By confirmUpdatedBookingMsg = By.xpath("//span[contains(text(),'Booking is updated')]");
	By serviceTable = By.xpath(
			"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table/tbody");

	By bookingReferenceNo = By
			.xpath("//span[contains(text(),'Booking is created successfully. Booking Reference Number is:')]");
	By cashOrderIndiactor = By
			.xpath("//span[text()='Cash Order']/following::span[contains(@id,'toggle-description')][1]");
	By cashAmount = By.xpath("//input[@name='cashAmount']");
	By specialService = By.xpath("//span[text()='Special Service']");
	By specialServiceToggle = By
			.xpath("//input[@name='enableSpecServ']/following::span[@class='slds-checkbox_faux'][1]");
	By additionalInfoofspclservice = By.xpath("//textarea[@name='AdditionalInformation']");
	By callbackDate = By.xpath("//input[@name='Date']");
	By callTime = By.xpath("//input[@name='CallbackBefore']");
	By confirmSpecialService = By.xpath("//button[text()='Confirm']");
	By spclserviceConfirmMsg = By.xpath("//div[text()='Special service details have been saved.']");
	By targetCountry = By.xpath("//label[text()='Target Country']/following::input[@name='senInputName']");
	By targetCountryInput=By.xpath("//label[text()='Target Country']/following::input[@name='senderCountry']");
	By targetCountryRemoveOption = By.xpath("//label[text()='Target Country']/following::button[1]");
	By targetcountrylist = By.xpath(
			"(//label[text()='Target Country']/following::c-lookup-result-component/div)[1]");
	By businessLocation=By.xpath("//input[@name='Location']");

	// Product options
	By productOptionTitle = By.xpath("//div[text()='Product Option']");
	By errorMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='error']");
	By holdToggle = By.xpath("//input[@name='isHold']/following::span[2]");
	By holdOption = By.xpath("//input[@name='isHold']");
	By productOptionError = By.xpath("//*[contains(text(),'Select at most 3 options')]");
	By priorityOption = By.xpath("//span[@title='Priority']");
	By selectionArrow = By.xpath("//button[@title='Move selection to Selected']/lightning-primitive-icon[1]");
	By deselectionArrow = By.xpath("//button[@title='Move selection to Available']/lightning-primitive-icon[1]");
	By saturdayDeliveryOption = By.xpath("//span[@title='Saturday Delivery']");
	By latePickupOption = By.xpath("//span[@title='Late Pickup']");
	By csImportedService = By.xpath("//span[@title='CS Arranged Import Services']");
	By productOptSubmit = By.xpath("//button[@title='Submit']");
	By productOptCancel = By.xpath("//button[@title='Cancel']");

	By addtionalInfoPageTitle = By.xpath("//h2/span[text()='Collection Date']");
	By pricenotRetrievable = By.xpath("//div[contains(text(),'Price not retrievable')]");

	By releaseBookingException = By.xpath("//button[contains(text(),'Release Booking Exception')]");

	By ackwardFrieghtApproval = By
			.xpath("//span[text()='Has awkward freight been approved']/following::span[@class='slds-checkbox_faux']");

	// Detail info
	By detailButton = By.xpath(
			"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//button[@name='detailInfo']");
	By routingInformation = By.xpath("//span[contains(text(),'Routing Information')]");
	By unselectButton = By.xpath(
			"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//button[@name='unselect']");
	// Alert Messgae

	By awkwardFreightAlert = By.xpath("//div[contains(text(),'AWKWARD FREIGHT ALERT:')]");
	By pieceWeightAlert = By.xpath("//div[contains(text(),'Piece Weight')]");
	By consignmentWeightAlert = By.xpath("//div[contains(text(),'Consignment')]");

	// Currency
	By currency = By.xpath("(//input[@name='userCurrency'])[2]");

	// Services Available
	By selectService = By.xpath("//button[@name='select'][1]");
	By inCollectionAreaValue = By.xpath("//td[@data-label='In Collection Area']");
	By bookBeforeValue = By.xpath("//td[@data-label='Booking']");
	By selfBroughtValue = By.xpath("//td[@data-label='Self-Brought']");

	// Additional options
	By customerDiscount = By
			.xpath("//b[contains(text(),'Customer Discount')]/following::lightning-formatted-number[1]");
	By totalWeight = By.xpath("//b[contains(text(),'Total Weight')]/following::td[1]");

	// Additional Info on Booking Record
	By expectedDueDate = By.xpath("//span[contains(text(),'Expected Due Date')]");
	By lobofSpecialServie = By.xpath(
			"//*[contains(text(),'Services Available')]/following::slot[2]//table/tbody//td//lightning-base-formatted-text[text()='Time Critical Int']");
	By lobofExpressService = By.xpath(
			"//*[contains(text(),'Services Available')]/following::slot[2]//table/tbody//td//lightning-base-formatted-text[text()='Int Express']");

	public AdditionalInfoPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	public boolean verifyTitle() {
		WebElement ele = uiTestHelper.waitForObject(addtionalInfoPageTitle);
		return ele.isDisplayed();
	}

	/**
	 * method to click the get price button
	 */
	public void clickGetPrice() {
		WebElement price = uiTestHelper.waitForObjectToBeClickable(getprice);
		uiTestHelper.clickJS(price);
	}

	public boolean verifyGetPrice() {
		WebElement price = uiTestHelper.waitForObjectToBeClickable(getprice);
		return price.isDisplayed();
	}

	public void setCollectionInstruction(String collInstruction) {
		WebElement instruction = uiTestHelper.waitForObject(collectionDriverInstruction);
		instruction.clear();
		instruction.sendKeys(collInstruction);
	}

	public void setDeliveryInstruction(String delInstruction) {
		WebElement instruction = uiTestHelper.waitForObject(deliveryDriverInstruction);
		instruction.clear();
		instruction.sendKeys(delInstruction);
	}

	public void setFromCollWindowTimeInput(String time) {
		WebElement timeField = uiTestHelper.waitForObject(fromCollWindowTime);
		uiTestHelper.clickJS(timeField);
		String b = Keys.BACK_SPACE.toString();
		timeField.sendKeys(b + b + b + b + b + time);
		timeField.sendKeys(Keys.TAB);
	}

	public void setToCollWindowTimeInput(String time) {
		WebElement timeField = uiTestHelper.waitForObject(toCollWindowTime);
		uiTestHelper.clickJS(timeField);
		String b = Keys.BACK_SPACE.toString();
		timeField.sendKeys(b + b + b + b + b + time);
		timeField.sendKeys(Keys.TAB);
	}

	public void setFromUnavailTimeInput(String time) {
		WebElement timeField = uiTestHelper.waitForObject(fromUnavailTime);
		uiTestHelper.clickJS(timeField);
		timeField.sendKeys(time);
		timeField.sendKeys(Keys.TAB);
	}

	public void setToUnavailTimeInput(String time) {
		WebElement timeField = uiTestHelper.waitForObject(toUnavailTime);
		uiTestHelper.clickJS(timeField);
		timeField.sendKeys(time, Keys.TAB);
	}

	public void clickViewSummary() {
		WebElement summery = uiTestHelper.waitForObject(viewSummary);
		uiTestHelper.clickJS(summery);
	}

	public boolean verifyViewSummary() {
		WebElement summery = uiTestHelper.waitForObject(viewSummary);
		return summery.isDisplayed();
	}

	public boolean verifyViewSummaryquick() {
		WebElement summery = uiTestHelper.waitForObjectQuick(viewSummary);
		return summery.isDisplayed();
	}

	public void clickUpdateBooking() {
		WebElement booking = uiTestHelper.waitForObject(updateBooking);
		booking.click();
	}

	public boolean getBookingSummary() {
		WebElement summary = uiTestHelper.waitForObject(bookingSummary);
		return summary.isDisplayed();
	}

	public String getexpressCost() {
		WebElement cost = uiTestHelper.waitForObject(expresscost);
		return cost.getText();
	}

	public String getexclVAT() {
		WebElement cost = uiTestHelper.waitForObject(exclVat);
		return cost.getText();
	}

	public String getVAT() {
		WebElement cost = uiTestHelper.waitForObject(vat);
		return cost.getText();
	}

	public String getTotalVAT() {
		WebElement cost = uiTestHelper.waitForObject(totalvat);
		return cost.getText();
	}

	public void clickCancel() {
		WebElement cancelbtn = uiTestHelper.waitForObject(cancelViewSummary);
		cancelbtn.click();
	}

	public boolean getAdditionalOptions() {
		WebElement options = uiTestHelper.waitForObject(addtionalOptions);
		return options.isDisplayed();
	}

	public void clickConfirmBooking() {
		WebElement confirm = uiTestHelper.waitForObject(confirmBooking);
		uiTestHelper.clickJS(confirm);
	}

	public String getBookingConfirmMsg() {
		WebElement confirm = uiTestHelper.waitForObject(confirmBookingMsg);
		return confirm.getText();
	}

	public String getUpdatedBookingConfirmMsg() {
		WebElement confirm = uiTestHelper.waitForObject(confirmUpdatedBookingMsg);
		return confirm.getText();
	}

	public void setCollectionDate(String expectDate) {
		WebElement date = uiTestHelper.waitForObject(collectionDate);
		date.click();
		By expectdate = By.xpath("//lightning-calendar//table/tbody/tr/td//*[contains(text(),'" + expectDate + "')]");
		WebElement collDate = uiTestHelper.waitForObject(expectdate);
		uiTestHelper.clickJS(collDate);
	}

	public boolean getEmailBtn() {
		WebElement emailbutton = uiTestHelper.waitForObject(emailBtn);
		return emailbutton.isEnabled();
	}

	public String getCollectionInstruction() {
		WebElement instruction = uiTestHelper.waitForObject(collectionDriverInstruction);
		return instruction.getAttribute("value");
	}

	public String getDeliveryInstruction() {
		WebElement instruction = uiTestHelper.waitForObject(deliveryDriverInstruction);
		return instruction.getAttribute("value");
	}

	/**
	 * Method to verify on Cash Order Indicator
	 */
	public boolean verifyCashOrderIndiactor() {

		WebElement validServices = uiTestHelper.waitForObject(cashOrderIndiactor);
		return validServices.isDisplayed();
	}

	public void clickCashOrderIndicator() {
		WebElement indicator = uiTestHelper.waitForObject(cashOrderIndiactor);
		indicator.click();
	}

	public void setCashAmount(String amount) {
		WebElement amt = uiTestHelper.waitForObject(cashAmount);
		amt.clear();
		amt.sendKeys(amount);
	}

	public void clickValidServices() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(validServices);
		uiTestHelper.clickJS(service);
	}

	public boolean isExpressButtonDisplayed() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(validServices);
		return service.isDisplayed();
	}

	public boolean isSpecialButtonDisplayed() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(specialServices);
		return service.isDisplayed();
	}

	public boolean isSpecialButtonEnabled() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(specialServices);
		return service.isEnabled();
	}

	public boolean isExpressButtonEnabled() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(validServices);
		return service.isEnabled();
	}

	public void clickSpecialServices() {
		WebElement service = uiTestHelper.waitForObjectToBeClickable(specialServices);
		uiTestHelper.clickJS(service);
	}

	/**
	 * Method to select valid service in Booking Additional Information page when
	 * converted Quote into Booking
	 */

	// Valid Service Selection
	public void getValidServices() {
		WebElement selectService = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[@data-row-key-value='EX200']//button[text()='Select']"));
		uiTestHelper.clickJS(selectService);
	}

	public void getValidSelectedService() {
		WebElement selectService = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//button[text()='Select']"));
		uiTestHelper.clickJS(selectService);
	}

	public void deSelectService() {
		WebElement selectService = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//button[text()='Unselect']"));
		uiTestHelper.clickJS(selectService);
	}

	public boolean verifyPriceOnTable() {
		WebElement pricedetail = uiTestHelper.waitForObject(
				By.xpath("//span[text()='Services Available']/following::table[1]//span[@title='Price']"));
		return pricedetail.isDisplayed();
	}

	public String getPriceOnTable() {
		WebElement price = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table/tbody/tr[1]/td[7]//lightning-formatted-number[1]"));
		return price.getText();
	}

	public void clickDetailinSelectService() {
		WebElement servicedtl = uiTestHelper
				.waitForObject(By.xpath("//div[@class='slds-scrollable_y']/table//button[text()='Detail']"));
		uiTestHelper.clickJS(servicedtl);
	}

	public boolean verifyPriceOnSelectedServiceTable() {
		WebElement pricedetail = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Selected Service')]/following::slot[2]//div[@class='slds-scrollable_y']/table//span[text()='Price']"));
		return pricedetail.isDisplayed();
	}

	public String getPrice(String priceType) {
		WebElement ele = uiTestHelper.waitForObject(
				By.xpath("//h2//span[text()='Additional Options']/following::table[1]/tbody//td/b[text()='" + priceType
						+ "']/following::td[1]/lightning-formatted-number[1]"));
		return ele.getText();
	}

	public boolean verifySpecialService() {
		WebElement spclService = uiTestHelper.waitForObject(specialService);
		return spclService.isDisplayed();
	}

	public void enableSpecialService() {
		WebElement spclService = uiTestHelper.waitForObject(specialServiceToggle);
		spclService.click();
	}

	public void setAddtionalInfoOfSpecialService(String additionalInformation) {
		WebElement spclService = uiTestHelper.waitForObject(additionalInfoofspclservice);
		spclService.sendKeys(additionalInformation);
	}

	public void setCallbackDate(String date) {
		WebElement spclService = uiTestHelper.waitForObject(callbackDate);
		spclService.sendKeys(date);

	}

	public void setcallbackTimeInput(String time) {
		WebElement callbacktimeField = uiTestHelper.waitForObject(callTime);
		uiTestHelper.clickJS(callbacktimeField);
		// String b = Keys.BACK_SPACE.toString();
		callbacktimeField.sendKeys(time, Keys.TAB);
	}

	public String getTargetCountry() {
		WebElement spclService = uiTestHelper.waitForObject(targetCountry);
		return spclService.getAttribute("value");
	}

	public void removeDefaultTargetCountryOption() {
		WebElement closeOption = uiTestHelper.waitForObject(targetCountryRemoveOption);
		closeOption.click();
	}

	public void setTargetCountry(String countryName) {
		WebElement country = uiTestHelper.waitForObject(targetCountryInput);
		uiTestHelper.clickJS(country);
		country.clear();
		country.sendKeys(countryName);
		WebElement countrylist = uiTestHelper.waitForObject(targetcountrylist);
		countrylist.click();
		 
	}
	
	public void setBusinessLocation(String BusinessLocation) {
		WebElement location = uiTestHelper.waitForObject(businessLocation);
		uiTestHelper.clickJS(location);
		location.clear();
		location.sendKeys(BusinessLocation);
		WebElement loclist=uiTestHelper.waitForObject(By.xpath("(//input[@name='Location']//following::c-lookup-result-component/div)//span[text()='"+BusinessLocation+"']"));
		loclist.click();
	}

	public void confirmSpecialService() {
		WebElement spclservice = uiTestHelper.waitForObject(confirmSpecialService);
		spclservice.click();
	}

	public boolean verifySepcialServiceApplied() {
		WebElement spclservice = uiTestHelper.waitForObject(spclserviceConfirmMsg);
		return spclservice.isDisplayed();
	}

	public boolean verifyErrorMessage() {
		WebElement msg = uiTestHelper.waitForObject(errorMessage);
		return msg.isDisplayed();
	}

	public void selectProductOption() {
		WebElement productopt = uiTestHelper.waitForObject(selectionArrow);
		uiTestHelper.clickJS(productopt);
	}

	public void deselectProductOption() {
		WebElement productopt = uiTestHelper.waitForObject(deselectionArrow);
		uiTestHelper.clickJS(productopt);
	}

	public void enableProductOption(String productOption) {
		WebElement productopt = uiTestHelper.waitForObject(
				By.xpath("//div[@class='slds-dueling-list__options']//span[contains(@title,'" + productOption + "')]"));
		productopt.click();
		selectProductOption();
	}

	public void disableProductOption(String productOption) {
		WebElement productopt = uiTestHelper.waitForObject(
				By.xpath("//div[@class='slds-dueling-list__options']//span[contains(@title,'" + productOption + "')]"));
		productopt.click();
		deselectProductOption();
	}

	public boolean verifyPriority() {
		WebElement productopt = uiTestHelper.waitForObject(priorityOption);
		return productopt.isDisplayed();
	}

	public boolean verifySaturdayDelivery() {
		WebElement productopt = uiTestHelper.waitForObject(saturdayDeliveryOption);
		return productopt.isDisplayed();
	}

	public boolean verifyCSImportService() {
		WebElement productopt = uiTestHelper.waitForObject(csImportedService);
		return productopt.isDisplayed();
	}

	public boolean verifyProductOptionsSelected(String productOption) {
		WebElement productopt = uiTestHelper.waitForObject(By.xpath(
				"//div[text()='Select Product Options']/following::span[text()='Selected']/following::span[@title='"
						+ productOption + "']"));
		// uiTestHelper.scrollIntoView(productopt);
		return productopt.isDisplayed();
	}

	public boolean verifyProductOptionsAvailable(String productOption) {
		WebElement productopt = uiTestHelper.waitForObject(By.xpath(
				"//div[text()='Select Product Options']/following::span[text()='Available']/following::div[1]//span[contains(@title,"
						+ productOption + "']"));
		uiTestHelper.scrollIntoView(productopt);
		return productopt.isDisplayed();
	}

	public boolean verifyLatePickup() {
		WebElement productopt = uiTestHelper.waitForObject(latePickupOption);
		return productopt.isDisplayed();
	}

	public boolean VerifyProductSelectionError() {
		WebElement status = uiTestHelper.waitForObject(productOptionError);
		return status.isDisplayed();
	}

	public String getProductSelectionErrorMsg() {
		WebElement status = uiTestHelper.waitForObject(productOptionError);
		return status.getText();
	}

	public void enableHold() {
		WebElement option = uiTestHelper.waitForObject(holdToggle);
		uiTestHelper.clickJS(option);
	}

	public boolean verifyHoldOption() {
		WebElement productopt = uiTestHelper.waitForObject(holdOption);
		return productopt.isEnabled();
	}

	public boolean verifyPriceBreakDownDetails(String details) {
		WebElement ele = uiTestHelper.waitForObject(
				By.xpath("//*[contains(text(),'Additional Option')]/following::table/tbody/tr[@data-row-key-value='"
						+ details + "']"));
		return ele.isDisplayed();
	}

	public String getPriceBreakDownDetails(String details) {
		WebElement ele = uiTestHelper.waitForObject(
				By.xpath("//*[contains(text(),'Additional Option')]/following::table/tbody/tr[@data-row-key-value='"
						+ details + "']"));
		return ele.getText();
	}

	/**
	 * method to get price break down details
	 */
	public String PriceBreakDownDetails(String productOptionName) {
		String descriptionName = "";
		WebElement valPriceBreakDownTable = uiTestHelper
				.waitForObject(By.xpath("//*[contains(text(),'Additional Option')]/following::table[2]//tbody"));
		List<WebElement> objRow1 = valPriceBreakDownTable.findElements(By.tagName("tr"));
		int row_count1 = objRow1.size();
		/**
		 * storing multiple product option price
		 *//*
			 * for (int j = 1; j <= row_count1; j++) { WebElement priceBreakDownDesc =
			 * uiTestHelper.waitForObject( By.
			 * xpath("//*[contains(text(),'Additional Option')]/following::table[2]//tbody/tr["
			 * + j + "]/th")); productOptionName = productOptionName + " " +
			 * (priceBreakDownDesc.getText()); }
			 */
		for (int i = 1; i <= row_count1; i++) {
			WebElement priceBreakDownDesc = uiTestHelper
					.waitForObject(By.xpath("//*[contains(text(),'Additional Option')]/following::table[2]//tbody/tr["
							+ i + "]/th//lightning-base-formatted-text[1]"));
			descriptionName = priceBreakDownDesc.getText();
			if (descriptionName.equalsIgnoreCase(productOptionName)) {
				descriptionName = priceBreakDownDesc.getText();
				break;
			} else {
				descriptionName = null;
			}
		}
		return descriptionName;

	}

	public void approveAckwardFrieght() {
		WebElement ele = uiTestHelper.waitForObject(ackwardFrieghtApproval);
		ele.click();
	}

	/**
	 * Method to click on Detail button to see service details
	 */
	public void clickDetailnfoBtn() {
		WebElement detailBtn = uiTestHelper.waitForObject(detailButton);
		uiTestHelper.clickJS(detailBtn);

	}

	public void clickUnselectService() {
		WebElement detailBtn = uiTestHelper.waitForObject(unselectButton);
		detailBtn.click();

	}

	public boolean verifyDetailButton() {
		WebElement detailBtn = uiTestHelper.waitForObject(detailButton);
		return detailBtn.isDisplayed();

	}

	public boolean verifyDetailedRoutingInformation() {
		WebElement detailinfo = uiTestHelper.waitForObject(routingInformation);
		return detailinfo.isDisplayed();
	}

	public String getSystemDate()  {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String exp_date = sdf.format(currentDateTime);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	public String getSystemDateinFormat(String format)  {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String exp_date = sdf.format(currentDateTime);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	public void clickReleaseBookingException() {
		WebElement additionalinfo = uiTestHelper.waitForObject(releaseBookingException);
		additionalinfo.click();
	}

	public String getSystemTime()  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		// get current date time with Calendar()
		Date cal = new Date(System.currentTimeMillis());
		System.out.println(dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}

	// Awkward Frieght Alert msg
	public boolean verifyAWKFriegtAlert() {
		WebElement alertMsg = uiTestHelper.waitForObject(awkwardFreightAlert);
		return alertMsg.isDisplayed();
	}

	public String getAWKFriegtAlert() {
		WebElement alertMsg = uiTestHelper.waitForObject(awkwardFreightAlert);
		return alertMsg.getText();
	}

	public boolean verifyPieceWeightAlert() {
		WebElement alertMsg = uiTestHelper.waitForObject(pieceWeightAlert);
		return alertMsg.isDisplayed();
	}

	public String getPieceWeightAlertMsg() {
		WebElement alertMsg = uiTestHelper.waitForObject(pieceWeightAlert);
		return alertMsg.getText();
	}

	public boolean verifyConsignemntAlert() {
		WebElement alertMsg = uiTestHelper.waitForObject(consignmentWeightAlert);
		return alertMsg.isDisplayed();
	}

	public String getConsignmentWeightAlertMsg() {
		WebElement alertMsg = uiTestHelper.waitForObject(consignmentWeightAlert);
		return alertMsg.getText();
	}

	// Currency

	public String getDefaultCurrency() {
		WebElement ccy = uiTestHelper.waitForObject(currency);
		return ccy.getAttribute("value");
	}

	public void setCurrency(String userCurrency) {
		WebElement ccy = uiTestHelper.waitForObject(currency);
		ccy.click();
		WebElement cupccy = uiTestHelper.waitForObject(By.xpath("//span[@title='" + userCurrency + "']"));
		uiTestHelper.scrollIntoView(cupccy);
		cupccy.click();
	}

	public String currencyOnServiceTable(String userCurrency) {
		WebElement ccy = uiTestHelper.waitForObject(By.xpath(
				"//span[text()='Services Available']/following::table[1]//lightning-formatted-number[contains(text(),'"
						+ userCurrency + "')]"));
		String currency = ccy.getText();
		return currency;
	}

	/**
	 * verifying Booking is created successfully. Booking Reference Number is
	 * display on screen
	 *
	 * @return
	 */
	public boolean isBookingReferenceNoCreated() {
		WebElement bookingReferenceNumber = uiTestHelper.waitForObject(bookingReferenceNo);
		return bookingReferenceNumber.isDisplayed();
	}

	public boolean isPriceNotRetrievable() {
		WebElement additionalinfo = uiTestHelper.waitForObject(pricenotRetrievable);
		return additionalinfo.isDisplayed();
	}

	public String getNoServiceErrorMsg() {
		WebElement additionalinfo = uiTestHelper.waitForObject(pricenotRetrievable);
		return additionalinfo.getText();
	}

	public boolean verifyBookingConfirmMsg() {
		WebElement confirm = uiTestHelper.waitForObject(confirmBookingMsg);
		return confirm.isDisplayed();
	}

	public boolean verifyUpdatedBookingConfirmMsg() {
		WebElement confirm = uiTestHelper.waitForObject(confirmUpdatedBookingMsg);
		return confirm.isDisplayed();
	}

	public void clickSelectBtn() {
		WebElement selectButton = uiTestHelper.waitForObject(selectService);
		uiTestHelper.scrollIntoView(selectButton);
		selectButton.click();
	}

	public boolean verifyInCollectionAreaDateTime() {
		WebElement inCollectionAreaDateTime = uiTestHelper.waitForObject(inCollectionAreaValue);
		return inCollectionAreaDateTime.isDisplayed();
	}

	public String getInCollectionAreaDateTimeValue() {
		WebElement inCollectionAreaDateTimeValue = uiTestHelper.waitForObject(inCollectionAreaValue);
		return inCollectionAreaDateTimeValue.getText();
	}

	public boolean verifyBookBeforeDateTime() {
		WebElement bookBeforeDateTime = uiTestHelper.waitForObject(bookBeforeValue);
		return bookBeforeDateTime.isDisplayed();
	}

	public String getBookBeforeDateTimeValue() {
		WebElement bookBeforeDateTimeValue = uiTestHelper.waitForObject(bookBeforeValue);
		return bookBeforeDateTimeValue.getText();
	}

	public boolean verifySelfBroughtDateTime() {
		WebElement selfBroughtDateTime = uiTestHelper.waitForObject(selfBroughtValue);
		return selfBroughtDateTime.isDisplayed();
	}

	public String getSelfBroughtDateTimeValue() {
		WebElement selfBroughtDateTimeValue = uiTestHelper.waitForObject(selfBroughtValue);
		return selfBroughtDateTimeValue.getText();
	}

	public String getCollectionDate() {
		WebElement getCollectionDateValue = uiTestHelper.waitForObject(collectionDate);
		uiTestHelper.scrollIntoView(getCollectionDateValue);
		return getCollectionDateValue.getAttribute("value");
	}

	public String getCustomerDiscount() {
		WebElement getCustomerDiscount = uiTestHelper.waitForObject(customerDiscount);
		uiTestHelper.scrollIntoView(getCustomerDiscount);
		return getCustomerDiscount.getText();
	}

	public String getTotalWeight() {
		WebElement getTotalWeight = uiTestHelper.waitForObject(totalWeight);
		uiTestHelper.scrollIntoView(getTotalWeight);
		return getTotalWeight.getText();
	}

	public String getFromPreferredCollectionTime() {
		WebElement getFromPreferredCollectionTime = uiTestHelper.waitForObject(fromCollWindowTime);
		uiTestHelper.scrollIntoView(getFromPreferredCollectionTime);
		return getFromPreferredCollectionTime.getAttribute("value");
	}

	public boolean verifyExpectedDueDate() {
		WebElement ele = uiTestHelper.waitForObject(expectedDueDate);
		uiTestHelper.scrollIntoView(ele);
		return ele.isDisplayed();
	}

	public void selectOptions(String service) {
		WebElement selectProduct = uiTestHelper.waitForObject(By.xpath(
				"//*[contains(text(),'Services Available')]/following::slot[2]//div[@class='slds-scrollable_y']/table//tr[@data-row-key-value='"
						+ service + "']//button[@name='selectpo']"));
		uiTestHelper.clickJS(selectProduct);
	}

	public void selectedServiceOptions() {
		WebElement selectProduct = uiTestHelper.waitForObject(By.xpath("//button[@name='selectpo']"));
		uiTestHelper.clickJS(selectProduct);
	}

	public boolean verifyProductOptionTable() {
		WebElement ele = uiTestHelper.waitForObject(productOptionTitle);
		return ele.isDisplayed();
	}

	public void getAllavailableProductOptions() {
		List<WebElement> proOpt = uiTestHelper
				.waitForObjects(By.xpath("//span[text()='Available']/following::ul[1]/li//span/span"));
		int size = proOpt.size();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 1; i <= size; i++) {
			WebElement ele = uiTestHelper
					.waitForObject(By.xpath("(//span[text()='Available']/following::ul[1]/li//span/span)[" + i + "]"));
			String optavailable = ele.getAttribute("title");
			map.put(i, optavailable);
		}
		Driver.report_MapValues("All availbale product Options are below :", map);

	}

	public boolean getAvailableProductOptions(String productOption) {
		boolean productOpt = false;
		List<WebElement> proOpt = uiTestHelper
				.waitForObjects(By.xpath("//span[text()='Available']/following::ul[1]/li//span/span"));
		int size = proOpt.size();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 1; i <= size; i++) {
			WebElement ele = uiTestHelper
					.waitForObject(By.xpath("(//span[text()='Available']/following::ul[1]/li//span/span)[" + i + "]"));
			String optavailable = ele.getAttribute("title");
			map.put(i, optavailable);
			if (optavailable.contains(productOption)) {
				productOpt = true;
			}

		}
		return productOpt;
	}

	public void enableProductOptions(String productOption) {
		boolean option = getAvailableProductOptions(productOption);
		if (option == true) {
			enableProductOption(productOption);
		} else {
			Test_Initializer.Fail_Message("Product Option Not Found");
		}
	}

	public boolean getSelectedProductOptions(String productOption) {
		boolean productOpt = false;
		List<WebElement> proOpt = uiTestHelper
				.waitForObjects(By.xpath("//span[text()='Selected']/following::ul[1]/li//span/span"));
		int size = proOpt.size();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 1; i <= size; i++) {
			WebElement ele = uiTestHelper
					.waitForObject(By.xpath("(//span[text()='Selected']/following::ul[1]/li//span/span)[" + i + "]"));
			String optavailable = ele.getAttribute("title");
			map.put(i, optavailable);
			if (optavailable.contains(productOption)) {
				productOpt = true;
			}

		}
		return productOpt;
	}

	public void disableProductOptions(String productOption) {
		boolean option = getSelectedProductOptions(productOption);
		if (option == true) {
			disableProductOption(productOption);
		} else {
			Test_Initializer.Fail_Message("Product Option Not Found");
		}
	}

	public void submitProductOption() {
		WebElement ele = uiTestHelper.waitForObject(productOptSubmit);
		ele.click();
	}

	public void cancelProductOption() {
		WebElement ele = uiTestHelper.waitForObject(productOptCancel);
		ele.click();
	}

	public WebElement serviceTable() {
		WebElement ele = uiTestHelper.waitForObject(serviceTable);
		return ele;
	}

	public void getAllAvaliableServices(String serviceName) {
		List<WebElement> servicesList = uiTestHelper.waitForObjects(
				By.xpath("//*[contains(text(),'Services Available')]/following::table//td[@data-label='Service']"));
		int size = servicesList.size();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 1; i <= size; i++) {
			WebElement ele = uiTestHelper.waitForObject(By.xpath(
					"(//*[contains(text(),'Services Available')]/following::table//td[@data-label='Service']//lightning-base-formatted-text)["
							+ i + "]"));
			String serviceAvailable = ele.getText();
			map.put(i, serviceAvailable);
		}
		Driver.report_MapValues("All availbale " + serviceName + " Services are below :", map);
	}

	public boolean isLOBofSpecialService() {
		WebElement serviceTable = uiTestHelper.waitForObjectwithSec(lobofSpecialServie, 90);
		return serviceTable.isDisplayed();
	}

	public boolean isLOBofExpressService() {
		WebElement serviceTable = uiTestHelper.waitForObjectwithSec(lobofExpressService, 90);
		return serviceTable.isDisplayed();
	}
}
