package com.tnt.ccdobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.tnt.commonutilities.UiTestHelper;

public class ContactPage {
	UiTestHelper uiTestHelper;
	WebDriver driver;

	public ContactPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	// xpaths
	By VerifyContactDropDown = By.xpath("//button[@title='Select a List View']//lightning-primitive-icon");
	By contactsDropDownMenuArrow = By.xpath("//button[@title='Select a List View']//lightning-icon");
	By allContactsMenuOption = By.xpath("//span[contains(text(),'All Contacts')]");
	By editButton = By.xpath("//button[@name='Edit']");
	By customerNameColumn = By.xpath("//span[contains(text(),'Customer Name')]");

	// edit customer info form
	By emailOptOut = By.xpath("//span[contains(text(),'Email Opt Out')]");
	By faxOptOut = By.xpath("//span[contains(text(),'Fax Opt Out')]");

	public boolean VerifyDefaultContactDropDownValue() {
		WebElement verifyDropdownValue = uiTestHelper.waitForObject(VerifyContactDropDown);
		return verifyDropdownValue.isDisplayed();
	}

	public void clickOnContact(String contactname) {
		WebElement searchbtn = uiTestHelper.waitForObject(By.xpath("//input[@name='Contact-search-input']"));
		searchbtn.sendKeys(Keys.CLEAR);
		searchbtn.sendKeys(contactname);
		searchbtn.sendKeys(Keys.ENTER);
		WebElement searchedResult = uiTestHelper
				.waitForObjectToBeClickable(By.xpath("//th/span/a[@title='" + contactname + "']"));
		uiTestHelper.clickJS(searchedResult);
	}

	public boolean hasContactOpened(String contactname) {
		try {
			WebElement conactNameField = driver
					.findElement(By.xpath("//h1//div/span[contains(text(),'" + contactname + "')]"));
			boolean isContactDisplayed = conactNameField.isDisplayed();
			return isContactDisplayed;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCreateCaseButton() {
		By createCaseButton = By.xpath("//button[@name='Contact.Create_Case']");
		try {
			WebElement createcasebutton = driver.findElement(createCaseButton);
			boolean isCreateCaseButton = createcasebutton.isDisplayed();
			uiTestHelper.clickJS(uiTestHelper.waitForObjectToBeClickable(createCaseButton));
			return isCreateCaseButton;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCreateCasePopUp() {
		try {
			WebElement createCasePopUp = driver.findElement(By.xpath("//span[contains(text(),'Email to Case')]"));
			boolean isCreateCasePopUp = createCasePopUp.isDisplayed();
			return isCreateCasePopUp;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isFieldPresent(String fieldname) {
		try {
			WebElement field = driver.findElement(By.xpath(
					"//span[contains(text(),'Email to Case')]/following::label[contains(text(),'" + fieldname + "')]"));
			boolean isField = field.isDisplayed();
			return isField;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isButtonPresent(String fieldname) {
		try {
			WebElement button = driver.findElement(
					By.xpath("//span[contains(text(),'Email to Case')]/following::button[(contains(text(),'" + fieldname
							+ "'))]"));
			boolean isButton = button.isDisplayed();
			return isButton;
		} catch (Exception e) {
			return false;
		}
	}

	public String verifyFieldsonPopUp() {
		String fieldsOnPopUp = "";
		if (isFieldPresent("Case Queue"))
			fieldsOnPopUp += "Case Queue ";
		if (isFieldPresent("Subject"))
			fieldsOnPopUp += "Subject ";
		if (isFieldPresent("Case Country"))
			fieldsOnPopUp += "Case Country ";
		if (isButtonPresent("Save"))
			fieldsOnPopUp += "Save ";
		if (isButtonPresent("Cancel"))
			fieldsOnPopUp += "Cancel ";
		return fieldsOnPopUp;
	}

	public boolean isSubjectMandatory() {
		try {
			String popUpScreenPath = "//span[contains(text(),'Email to Case')]/following::";
			WebElement subjectInput = uiTestHelper.waitForObject(By.xpath(popUpScreenPath + "input[1]"));
			subjectInput.sendKeys(Keys.CLEAR);
			subjectInput.sendKeys(Keys.ENTER);
			WebElement errormessage = driver
					.findElement(By.xpath(popUpScreenPath + "div[contains(text(),'Complete this field.')]"));
			boolean iserrormessage = errormessage.isDisplayed();
			return iserrormessage;
		} catch (Exception e) {
			return false;
		}
	}

	public void createCase(String caseQueue, String subject, String caseCountry) {
		String popUpScreenPath = "//span[contains(text(),'Email to Case')]/following::";
		WebElement casequeueDropDown = uiTestHelper.waitForObjectToBeClickable(By.xpath(popUpScreenPath + "select[1]"));
		uiTestHelper.actionClick(casequeueDropDown);
		casequeueDropDown.sendKeys(caseQueue);

		uiTestHelper.waitForObjectToBeClickable(
				By.xpath(popUpScreenPath + "select[1]/option[contains(text(),'" + caseQueue + "')]")).click();

		casequeueDropDown.sendKeys(Keys.TAB);
		WebElement subjectInput = uiTestHelper.waitForObject(By.xpath(popUpScreenPath + "input[1]"));
		subjectInput.sendKeys(Keys.CLEAR);
		subjectInput.sendKeys(subject);
		subjectInput.sendKeys(Keys.ENTER);

		WebElement caseCountryDropDown = uiTestHelper
				.waitForObjectToBeClickable(By.xpath(popUpScreenPath + "select[2]"));
		uiTestHelper.actionClick(caseCountryDropDown);
		caseCountryDropDown.sendKeys(caseCountry);

		uiTestHelper.waitForObjectToBeClickable(
				By.xpath(popUpScreenPath + "select[2]/option[contains(text(),'" + caseCountry + "')]"));
		caseCountryDropDown.sendKeys(Keys.TAB);

		WebElement button = uiTestHelper.waitForObjectToBeClickable(
				By.xpath("//span[contains(text(),'Email to Case')]/following::button[(contains(text(),'Save'))]"));
		button.click();
	}

	public boolean isCasePageDisplayed(String subject) {
		try {
			WebElement caseName = uiTestHelper
					.waitForObject(By.xpath("//lightning-formatted-text[contains(text(),'" + subject + "')]"));
			boolean isCaseName = caseName.isDisplayed();
			return isCaseName;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean getSuccessMessage(String successMessage) {
		try {
			WebElement successMSG = uiTestHelper
					.waitForObjectwithSec(By.xpath("//*[contains(text(),'" + successMessage + "')]"), 10000);
			boolean isCasePanel = successMSG.isDisplayed();
			return isCasePanel;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCasesPanel() {
		try {
			WebElement CasesField = driver.findElement(By.xpath("//h2/span[contains(text(),'Cases')]"));
			boolean isCasePanel = CasesField.isDisplayed();
			return isCasePanel;
		} catch (Exception e) {
			return false;
		}
	}

	public int getCasesCount() {
		try {
			WebElement myTable = driver
					.findElement(By.xpath("//h2/span[contains(text(),'Cases')]/following::table[1]/tbody"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			return row_count;
		} catch (Exception e) {
			return 0;
		}
	}

	public String[] getCaseDetailsByRowNumber(int rownum) {
		String[] caseDetails = new String[4];
		String tablexPath = "//h2/span[contains(text(),'Cases')]/following::table[1]/tbody/tr[" + rownum + "]";
		caseDetails[0] = uiTestHelper.waitForObject(By.xpath(tablexPath + "/th//a[1]")).getText();
		caseDetails[1] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[1]//span[1]//lightning-base-formatted-text[1]")).getText();
		caseDetails[2] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[2]//span[1]//lightning-base-formatted-text[1]")).getText();
		caseDetails[3] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[3]//span[1]//lightning-formatted-date-time[1]")).getText();
		return caseDetails;
	}

	public String[] getCaseDetailsBySubject(String subject) {
		String[] caseDetails = new String[4];
		String tablexPath = "";
		int casecount = getCasesCount();
		for (int i = 1; i <= casecount; i++) {
			tablexPath = "//h2/span[contains(text(),'Cases')]/following::table[1]/tbody/tr[" + i + "]";
			String casenameTemp = "";
			try {
				WebElement myTable = driver
						.findElement(By.xpath(tablexPath + "/td[1]//span[1]//lightning-base-formatted-text[1]"));
				casenameTemp = myTable.getText();
			} catch (Exception e) {
				casenameTemp = "";
			}
			if (casenameTemp.equalsIgnoreCase(subject)) {
				break;
			}
		}
		caseDetails[0] = uiTestHelper.waitForObject(By.xpath(tablexPath + "/th//a[1]")).getText();
		caseDetails[1] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[1]//span[1]//lightning-base-formatted-text[1]")).getText();
		caseDetails[2] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[2]//span[1]//lightning-base-formatted-text[1]")).getText();
		caseDetails[3] = uiTestHelper
				.waitForObject(By.xpath(tablexPath + "/td[3]//span[1]//lightning-formatted-date-time[1]")).getText();
		return caseDetails;
	}

	public void clickContactDropDownMenu() {
		WebElement menu = uiTestHelper.waitForObjectToBeClickable(contactsDropDownMenuArrow);
		uiTestHelper.clickJS(menu);
	}

	public void clickAllContactsFromDropDownMenu() {
		WebElement menuItem = uiTestHelper.waitForObject(allContactsMenuOption);
		menuItem.click();
	}

	public void selectContactNameInTable(String name) {
		// wait for the table then click on the name
		// uiTestHelper.waitForObject(customerNameColumn);
		uiTestHelper.waitForObjectToBeClickable(By.xpath("//span//a[@title='" + name + "']")).click();

	}

	public void selectContactNameInTable() {
		// public void selectContactNameInTable(String name) {
		// wait for the table then click on the name
		// uiTestHelper.waitForObject(customerNameColumn);
		// uiTestHelper.waitForObjectToBeClickable(By.xpath("//span//a[@title='"+name+"']")).click();
		// uiTestHelper.waitForObjectToBeClickable(By.xpath("//span//a[@title='--
		// YUE']")).click();
		uiTestHelper.waitForObjectToBeClickable(By.xpath("//*[text()='.Herr Kurapka']")).click();
	}

	public void clickEditButton() {
		uiTestHelper.waitForObject(editButton).click();
	}

	public Boolean isEmailOptOutDisplayed() {
		WebElement emailOptOutSection = uiTestHelper.waitForObject(emailOptOut);
		// scroll into view maybe?
		return emailOptOutSection.isDisplayed();
	}

	public Boolean isFaxOptOutDisplayed() {
		WebElement faxOptOutSection = uiTestHelper.waitForObject(faxOptOut);
		return faxOptOutSection.isDisplayed();
	}

	public String isEmailClickable_ContactHeader(String email) {
		email = email.toLowerCase();
		By emailDetails_text = By.xpath("//div/p[@title='Email']/following::slot/lightning-formatted-text");
		String email_xpath = "(//div/p[@title='Email']/following::lightning-formatted-lookup[1]/a[contains(text(),'"
				+ email + "')])";
		boolean temp = false;
		for (int i = 0; i < 4; i++) {
			try {
				By emailDetails_link = By.xpath(email_xpath + "[" + i + "]");
				driver.findElement(emailDetails_link).isDisplayed();
				temp = true;
				break;
			} catch (Exception e) {
				temp = false;
			}
		}
		String linkORtext = "";
		if (temp) {
			linkORtext = "link";
		} else {
			linkORtext = "text";
		}
		try {
			WebElement emailid_wb = uiTestHelper.waitForObject(emailDetails_text);
			String emailid = emailid_wb.getText();
			if (!emailid.isEmpty()) {
				linkORtext = linkORtext + "text";
			} else {
				linkORtext = linkORtext + "link";
			}

		} catch (Exception e) {
			linkORtext = linkORtext + false;
		}
		return linkORtext;
	}

	public String isEmailClickable_ContactDetails(String email) {
		email = email.toLowerCase();
		By emailDetails_text = By.xpath("//div/lightning-formatted-text[contains(text(),'" + email + "')]");
		By emailDetails_link = By.xpath("//lightning-formatted-lookup/a[contains(text(),'" + email + "')]");
		String linkORtext = "";
		try {
			WebElement element = driver.findElement(emailDetails_text);
			element.isDisplayed();
			linkORtext = "true";

		} catch (Exception e) {
			linkORtext = "false";
		}
		try {
			WebElement element = driver.findElement(emailDetails_link);
			element.isDisplayed();
			linkORtext = "false";

		} catch (Exception e) {
			linkORtext = linkORtext + "true";
		}
		return linkORtext;
	}

	public String getSalutation() {
		By salutation_ = By.xpath("//span[contains(text(),'Salutation')]/following::div[1]/lightning-formatted-text");
		WebElement saluation_we = uiTestHelper.waitForObject(salutation_);
		return saluation_we.getText();
	}

	public String getReferenceSystem_Contact() {// span[contains(text(),'Reference
												// System')]/following::div[1]/span/slot/lightning-formatted-text
		By ReferenceSystem_ = By
				.xpath("//span[contains(text(),'Reference System')]/following::div[1]/lightning-formatted-text");
		WebElement ReferenceSystem = uiTestHelper.waitForObject(ReferenceSystem_);
		return ReferenceSystem.getText();
	}

	public String getReferenceSystem_Customer() {
		By ReferenceSystem_ = By.xpath(
				"//span[contains(text(),'Reference System')]/following::div[1]/span/slot/lightning-formatted-text");
		WebElement ReferenceSystem = uiTestHelper.waitForObject(ReferenceSystem_);
		return ReferenceSystem.getText();
	}

	public String getAndClickCustomerName() {
		By customername_field = By.xpath("(//p[@title='Customer Name']/following::p[1]//div//div//a//span)[2]");
		boolean temp = false;
		try {
			WebElement customername = driver.findElement(customername_field);
			temp = true;
		} catch (Exception e) {
			temp = false;
			return null;
		}
		if (temp) {
			WebElement customername = uiTestHelper.waitForObject(customername_field);
			String cust_name = customername.getText();
			customername.click();
			return cust_name;
		}
		return null;
	}
}
