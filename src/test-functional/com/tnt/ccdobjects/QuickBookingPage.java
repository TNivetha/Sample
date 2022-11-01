package com.tnt.ccdobjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class QuickBookingPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;

	By radiobtnquickbooking = By.xpath(
			"//span[text()='Quick Booking']/following::span[@class='slds-checkbox_faux'][1]");
	By btncallerreceiver = By
			.xpath("(//input[@name='callerInfoValue']/following::span[text()='Receiver'][1])[1]");
	By btnpaymentreceiver = By
			.xpath("(//input[@name='radioGroup1']/following::span[text()='Receiver'][1])[1]");
	By btnpaymentsender = By
			.xpath("(//input[@name='radioGroup1']/following::span[text()='Sender'][1])[1]");
	By btncallersender = By.xpath(
			"(//input[@name='callerInfoValue']/following::span[text()='Sender'][1])[1]");
	By inputbillingaccountnum = By.xpath(
			"//label[text()='Billing Account Number']/following::input[1]");

	// Contact details
	By sameAsCollerInformationReceiverChkBox = By
			.xpath("//span[text()='Delivery Contact Details']/following::span[@class='slds-checkbox_faux'][1]");
	By checkboxsameascallercollectionconatct = By
			.xpath("//span[text()='Collection Contact Details']/following::span[@class='slds-checkbox_faux'][1]");
	By inputsameascallercollectionconatct = By.xpath(
			"(//*[contains(text(),'Collection Contact Details')]/following::input)[1]");

	// Next button which navigate from Booking information tab to Goods Information
	// tab
	By nextButton = By.xpath("(//button[@title='Next'])[1]");

	// Add info page
	By validServices = By.xpath("//button[@title='Valid Services']");
	By fromCollWindowTime = By.xpath("//input[@name='loggedInUserTime']");
	By toCollWindowTime = By.xpath("//input[@name='collectionToTime']");
	By quickbookingsubmitbtn = By.xpath("//button[text()='Quick Booking']");
	By quickbookingsubmitbtn1=By.xpath("//slot/c-additional-information-component/div[6]/lightning-button[2]/button");
	By spsserviceconfirmmsg=By.xpath("//slot/c-additional-information-component/div[5]");
	
	
	// booking remarks tab
	By bookingremarksnumber=By.xpath("//span[contains(@title,'Booking Remarks')]/following::table[1]/tbody/tr[1]/th//a//span");
	
	//booking exception history tab
	By bkexceptionhistorytab=By.xpath("//a[@data-label='Booking Exception History']");
	By bookingaudittab=By.xpath("//a[@data-label='Booking Audit']");
	
	
	public QuickBookingPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}

	// quick booking button
	public void clickradiobtnquickbooking() {
		WebElement quickbk = uiTestHelper.waitForObject(radiobtnquickbooking);
		uiTestHelper.clickJS(quickbk);

	}
	
	public boolean verifyquickbooking() {
		WebElement quickbk = uiTestHelper.waitForObject(radiobtnquickbooking);
		return quickbk.isEnabled();

	}

	// caller receiver button
	public boolean iscallerreceiverbtn() {
		try {
			driver.findElement(btncallerreceiver);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	// payment receiver button
	public boolean ispaymentreceiverbtn() {
		try {
			driver.findElement(btnpaymentreceiver);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean iscallersenderbtnenabled() {
		WebElement callsenderbtn = uiTestHelper.waitForObject(btncallersender);
		return callsenderbtn.isEnabled();
	}

	public boolean ispaymentsenderbtnenabled() {
		WebElement callsenderbtn = uiTestHelper.waitForObject(btnpaymentsender);
		return callsenderbtn.isEnabled();
	}

	
	/**
	 * Method to click on "Same As Caller Information" Check Box for sender
	 */
	public void clickSenderSameAsCallerInfo() {
		// scrollDownToSameAsCollerInfo();
		WebElement sameAsCollerInformationCheckBox = uiTestHelper.waitForObject(checkboxsameascallercollectionconatct);
		uiTestHelper.scrollIntoView(sameAsCollerInformationCheckBox);
		sameAsCollerInformationCheckBox.click();
	}

	public boolean IsSenderSameAsCallerInfoSelected() {
		WebElement sameAsCollerInformationCheckBox1 = uiTestHelper.waitForObject(inputsameascallercollectionconatct);
		return sameAsCollerInformationCheckBox1.isEnabled();
	}

	public String getBillingAccountNumber() {
		WebElement billaccountnum = uiTestHelper.waitForObject(inputbillingaccountnum);
		return billaccountnum.getAttribute("value");
	}

	public boolean isValidService() {
		try {
			driver.findElement(validServices);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getFromCollWindowTime() {
		WebElement fromcollwindowtime = uiTestHelper.waitForObject(fromCollWindowTime);
		return fromcollwindowtime.getAttribute("value");
	}

	public String getToCollWindowTime() {
		WebElement tocollwindowtime = uiTestHelper.waitForObject(toCollWindowTime);
		return tocollwindowtime.getAttribute("value");
	}

	public boolean isQuickBookingSubmitBtn() {
		try {
			driver.findElement(quickbookingsubmitbtn);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public void clickQuickBookingSubmitBtn() {
		if(isQuickBookingSubmitBtn()) {
			WebElement quickbookingsubmitbtn1 = uiTestHelper.waitForObject(quickbookingsubmitbtn);
			uiTestHelper.scrollIntoView(quickbookingsubmitbtn1);
			quickbookingsubmitbtn1.click();
		}else {
			WebElement quickbookingsubmit = uiTestHelper.waitForObject(quickbookingsubmitbtn1);
			uiTestHelper.scrollIntoView(quickbookingsubmit);
			quickbookingsubmit.click();
		}
		
	}
	public String getBookingRemarksNumber() {
		WebElement bookingnumber = uiTestHelper.waitForObject(bookingremarksnumber);
		return bookingnumber.getText();
	}
	
	public String isSpsServiceConfirmMsg() {
		try {
			driver.findElement(spsserviceconfirmmsg);
			String message=driver.findElement(spsserviceconfirmmsg).getText();
			return message;
		} catch (NoSuchElementException e) {
			return "null";
		}
	}
	
	public void clickBookingException() {
		WebElement ele=uiTestHelper.waitForObject(bkexceptionhistorytab);
		ele.click();
	}
	public boolean verifyBookingException() {
		WebElement ele=uiTestHelper.waitForObject(bkexceptionhistorytab);
		return ele.isDisplayed();
	}
	public void clickBookingAudit() {
		WebElement ele=uiTestHelper.waitForObject(bookingaudittab);
		ele.click();
	}
	public boolean verifyBookingAudit() {
		WebElement ele=uiTestHelper.waitForObject(bookingaudittab);
		return ele.isDisplayed();
	}
	public void clickValidServices() {
		WebElement service=uiTestHelper.waitForObject(validServices);
		uiTestHelper.clickJS(service);
	}
	public String getSelfBaughtTime() {
		/*Date currentdate=new Date(System.currentTimeMillis());
		TimeZone timezone=TimeZone.getDefault();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat simpledf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		simpledf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		System.out.println(simpledf.format(calendar.getTime()));
		System.out.println(TimeZone.getDefault());
		System.out.println(simpledf.format(calendar.getTime()));
		
		System.out.println("currenttime: "+currenttime);
		System.out.println("timezone: "+timezone);*/
		
		DateTimeFormatter dateformat=DateTimeFormatter.ofPattern(("yyyy-MM-dd"));
		LocalDateTime date=LocalDateTime.now();		
		String currdate=dateformat.format(date);
		System.out.println("crrent date "+currdate);
		WebDriverWait wait=new WebDriverWait(driver, 60);		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Services Available')]/following::table[1]")));
		WebElement selfbaughttime1 = driver.findElement(By.xpath("//*[contains(text(),'Services Available')]/following::table[1]/tbody"));
		
		List<WebElement> objRow1= selfbaughttime1.findElements(By.tagName("tr"));
		int row_count1 = objRow1.size();
		String trueandfalse="";
		for (int i=1; i<=row_count1; i++)
		{
			WebElement mySelect=driver.findElement(By.xpath("//*[contains(text(),'Services Available')]/following::table[1]/tbody/tr["+i+"]/td[5]//div/lightning-base-formatted-text"));			
			String selfbaughttime=mySelect.getText();
			if(selfbaughttime.contains(currdate)) {
				trueandfalse=trueandfalse+" true";
			}else {
				trueandfalse=trueandfalse+" false";
			}
			
		}return trueandfalse;
	}

	public int getPostalCodeFromMultiplePostCode() {
		WebDriverWait wait=new WebDriverWait(driver, 60);		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[text()='Address Details']/following::table[1]/tbody")));
		WebElement setofpostalcodes = driver.findElement(By.xpath("//h2[text()='Address Details']/following::table[1]/tbody"));
		
		List<WebElement> objRow1= setofpostalcodes.findElements(By.tagName("tr"));
		int row_count1 = objRow1.size();
		return row_count1;
	}
	
	By postcodeselectionbutton=By.xpath("//h2[text()='Address Details']/following::table[1]//button[@name='Select']");
	public void selectPostcode() {
		WebElement postacodebtn=uiTestHelper.waitForObject(postcodeselectionbutton);
		uiTestHelper.clickJS(postacodebtn);
	}
	
	
}
