package com.tnt.ccdobjects;

import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.UiTestHelper;

import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class CreateCasePage {

	UiTestHelper uiTestHelper;
	WebDriver driver;
	By recordType = By.xpath("//button[@name='SelectRecordType']");
	By createcaseLocation = By.xpath("//button[@name='CaseLocation']");
	By caseRouteTo = By.xpath("//button[@name='RouteTo']");
	By createcaseReason = By.xpath("//button[@name='CaseReason']");
	By createcaseCause = By.xpath("//button[@name='Cause']");
	By contactfirstname = By.xpath("//input[@name='ContactFirstName']");
	By contactlastname = By.xpath("//input[@name='ContactLastName']");
	By contactPhone = By.xpath("//input[@name='ContactPhone']");
	By contactEmail = By.xpath("//input[@name='ContactEmail']");
	By createCase = By.xpath("(//button[contains(text(),'Create Case')])[2]");
	By createdStatus = By
			.xpath("(//p[contains(text(),'Status')]/following-sibling::p/slot/lightning-formatted-text)[1]");

	
	By originLocation = By.xpath("//lightning-base-combobox-item[@data-value='Origin']");
	By caseAssign = By.xpath("//input[@name='assignToMe']/following::span[1]");
	By assignToMeCheckbox = By.xpath("//input[@name='assignToMe']");

	// Unallocated Case Creation
	By caseOwnerLoc = By.xpath("//a[text()='--None--']");
	By caseContactDetailsTitle = By.xpath("//span[contains(text(),'Case Contact Details')]");
	By senderContactName = By.xpath("//input[@name='Sender_s_Contact_Name__c']");
	By receiverContactName = By.xpath("//input[@name='Receiver_s_Contact_Name__c']");
	By senderContactPhone = By.xpath("//span[text()='Sender Contact Phone']/following::input[1]");
	By receiverContactPhone = By.xpath("//span[text()='Receiver Contact Phone']/following::input[1]");
	By senderContactEmail = By.xpath("//span[text()='Sender Contact Email']/following::input[1]");
	By receiverContactEmail = By.xpath("//span[text()='Receiver Contact Email']/following::input[1]");
	By caseInfoTitle = By.xpath("//span[contains(text(),'Case Information')]");
	By saveUnallocatedCase = By.xpath("//*[text()='Save']");

	// Unallocated Case Creation
	By caseGroupBox = By.xpath("//button[@name='CaseGroup']");
	By caseReasonBox = By.xpath("//button[@name='CaseReason']");
	By causeBox = By.xpath("//button[@name='Cause']");
	By pcsTextBox = By.xpath("//input[@name='Pcss__c']");
	By totalWeightKGBox = By.xpath("//input[@name='Total_Weights__c']");
	By collectionDepot = By.xpath(
			"//label[contains(text(),'Collection Depot')]/following-sibling::div//lightning-base-combobox//input[@placeholder='Search Locations...'] ");
	By destinationDepot = By.xpath(
			"//label[contains(text(),'Destination Depot')]/following-sibling::div//lightning-base-combobox//input[@placeholder='Search Locations...'] ");
	By collectionDate = By.xpath("//input[@name='Collection_Date_Unallocated__c']");
	By moreButtonOnCreatedCasePage = By.xpath("(//div[@class='uiMenu']/following::a[text()='More'])[3]");
	// By reAssignButton =
	// By.xpath("//a[contains(text(),'More')]/preceding::span[contains(text(),'Re-assign')]");
	By reAssignBannerButton = By.xpath("//span[contains(text(),'Re-assign')]");
	By casePageTabsList = By.xpath(
			"//div[@class='LARGE uiTabset--base uiTabset--task uiTabset oneActionsComposer forceActionsContainer']//div[@role='tablist']");
	By reAssignToDropDownBox = By.xpath("//input[@name='ReassignTO']");
	By originDropDownMenuItem = By.xpath(
			"//input[@name='ReassignTO']/following::lightning-base-combobox-item[@data-value='Origin']//span[contains(text(),'Origin')]");
	By destinationDropDownMenuItem = By.xpath(
			"//input[@name='ReassignTO']/following::lightning-base-combobox-item[@data-value='Destination']//span[contains(text(),'Destination')]");
	By reAssignSaveButton = By.xpath("//button[contains(text(),'Save')]");

	public String caseReasonDropDown = "//input[@name='Case_Reason__c']/following::div[2]/lightning-base-combobox-item[";
	public String causeDropDown = "//input[@name='Cause__c']/following::div[2]/lightning-base-combobox-item[";

	// Unallocated Case 'Sender & Receiver Section'
	By senderContactNameUnallocated = By.xpath("//input[@name='Sender_s_Contact_Name__c']");
	By receiverContactNameUnallocated = By.xpath("//input[@name='Receiver_s_Contact_Name__c']");
	By senderStreet = By.xpath("//input[@name='Sender_s_Street__c']");
	By receiverStreet = By.xpath("//input[@name='Receiver_s_Street__c']");
	By senderPostCode = By.xpath("//input[@name='Sender_Postcode_Unallocated__c']");
	By receiverPostCode = By.xpath("//input[@name='Receiver_Postcode_Unallocated__c']");
	By senderTown = By.xpath("//input[@name='Sender_s_Town__c']");
	By receiverTown = By.xpath("//input[@name='Receiver_s_Town__c']");
	By sendersProvince = By.xpath("//input[@name='Sender_s_Province__c']");
	By sendersLookupBox = By.xpath("//label[contains(text(),\"Sender's Lookup\")]/following::input[1]");
	// By sendersCountryTerritoryBox = By.path("//label[contains(text(),\"Sender’s
	// Country/Territory\")]/following::input[1]");
	By sendersCountryTerritoryBox = By.xpath("(//input[@placeholder='Search Countries/Territories...'])[1]");

	By receiversCountryTerritoryBox = By
			.xpath("//label[contains(text(),\"Receiver's Country/Territory\")]/following::input[1]");

	// Alternative Collection & Delivery Info
	By collectionStreet = By.xpath("//input[@name='Collection_Streets__c']");
	By collectionTown = By.xpath("//input[@name='Collection_Towns__c']");
	By deliveryStreet = By.xpath("//input[@name='Delivery_Streets__c']");
	By deliveryTown = By.xpath("//input[@name='Delivery_Towns__c']");
	By createCaseButton = By.xpath("//footer[contains(@class,'footer')]//div//button[contains(text(),'Create Case')]");
	By noConsignmentPopUpOkBtn = By
			.xpath("//div[contains(@class, 'sldsboxcontent')]/following::footer//button[contains(text(),'OK')]");
	By statusCreatedMessage = By.xpath("//lightning-formatted-text[contains(text(),'Created')]");

	// case page tablist section
	By caseSumamryTab = By.xpath("//a[contains(text(),'Case Summary')]");
	By caseInformationTab = By.xpath("//a[contains(text(),'Case Information')]");
	By consignmentInformationTab = By.xpath("//a[contains(text(),'Consignment Information')]");
	By updatedConsignmentStatusDescriptionField = By
			.xpath("//span[contains(text(),'Updated Consignment Status Description')]");
	By closeCaseTab = By.xpath("//span[contains(text(),'Close Case')]");
	By caseOwnerName = By.xpath("//span[contains(text(),'Case Owner')]/following::a[1]");
	By moreOptions = By.xpath("(//div[@class='uiPopupTrigger']//following::a[text()='More'])[1]");
	By monitorActivityTab = By.xpath("//span[contains(text(),'Monitor Activity')]");
	By monitorActivityCODateBox = By.xpath("(//label[contains(text(),'Date')]/following-sibling::input)[1]");
	By monitorActivityCOTimeBox = By.xpath("(//label[contains(text(),'Time')]/following::input)[1]");
	By COTickBox = By.xpath("(//div[@class='uiInput uiInputCheckbox uiInput--default uiInput--checkbox']/input)[1]");
	By midPageTabsSaveButton = By.xpath("//div[@class='bottomBarLeft slds-col']/following-sibling::div//span");
	By statusDisplaysMonitoringText = By.xpath("//lightning-formatted-text[contains(text(),'Monitoring')]");
	By closeCaseSaveButton = By.xpath("//button[@title='Perform Action']");
	By topOfPageCaseStatusBox = By.xpath("//p/preceding::lightning-formatted-text[contains(text(),'Manual-Closed')]");
	By callBackTab = By.xpath("//a[@class='tabHeader']//span[contains(text(),'Call Back')]");
	By reOpenCaseTab = By.xpath("//span[contains(text(),'Re-Open case')]");
	By clickMergedActionsTab = By.xpath("//span[contains(text(),'Merged Actions')]");
	By reOpenSelectBox = By.xpath("//select[@name='input1']");
	By reOpenDropDownBoxReason = By.xpath("//option[contains(text(),\"Customer's Original Request Not Satisfied\")]");
	By reOpenCaseSaveButton = By.xpath("//button[contains(text(),'Save')]");

	// Task section on case page
	By taskTabHeader = By.xpath("//a[@class='tabHeader']//span[contains(text(),'Task')]");
	By taskCountryTerritoryBox = By.xpath("//input[@placeholder='Search Countries/Territories...']");
	By taskRouteToBox = By.xpath("//input[@name='RouteTo']");
	By taskOriginDestinationRoute = By.xpath("//button[@name='OriginDestination']");
	By taskLocationBox = By.xpath("//input[@name='Location']");
	By taskDeadlineDateBox = By.xpath("(//input[@name='datetime'])[1]");
	By taskTimeDeadlineBox = By.xpath("(//input[@name='datetime'])[2]");
	By taskInstructionTextBox = By.xpath("//textarea[@name='Description']");
	By taskSaveButton = By.xpath("//button[@title='Create']");

	By changeOwnerButton = By.xpath("//span[contains(text(),'Change Owner')]");
	By changeCaseOwnerSearchUserBox = By.xpath("//input[@placeholder='Search Users...']");
	By caseOwnerSubmitButton = By.xpath("//span[contains(text(),'Submit')]");
	By caseDropDownMenu = By.xpath(
			"//lightning-button-menu[@class='menu-button-item slds-dropdown-trigger slds-dropdown-trigger_click']");
	By greenSuccessMsgPopUp = By.xpath("//div[@data-key='success']");
	By popupMsgText = By.xpath("//span[@class='toastMessage forceActionsText']");
	By createdTaskId = By.xpath("//a[@class='forceActionLink']");
	By redErrorMsgPopup = By.xpath("//div[@data-key='error']");

	// Close Tab Actions
	By ownershipDropDownBox = By.xpath("//button[@name='Ownership__c']");
	By issueLocationSearchBox = By.xpath("//input[@placeholder='Search Locations...']");
	By rootCauseDropDownBox = By.xpath("//button[@name='Root_Cause__c']");

	// Case History Tab Section
	By caseHistoryQuickLink = By.xpath("//a[@class='flex-wrap-ie11']//span[contains(text(),'Case History')]");
	By rowOneOriginalValue = By.xpath(
			"((//div[@class='slds-cell-edit errorColumn slds-cell-error'])[1]/following::td[@role='gridcell']//span[text()='Manual-Closed'])[1]");
	By rowOneNewValue = By.xpath(
			"((//div[@class='slds-cell-edit errorColumn slds-cell-error'])[1]/following::td[@role='gridcell']//span[text()='Created'])[1]");
	// add save button on the task tab

	// Merged actions tab
	By closeCaseTickBox = By.xpath(
			"(//legend[contains(text(),'Select Case Actions')]/following::Div//span//following::span[@class='slds-checkbox_faux'])[5]");
	By mergedActionsSaveButton = By.xpath("//button[@title='Perform Action']");
	By caseTabCloseCaseTickBox = By.xpath(
			"//legend[contains(text(),'Select Case Actions')]/following::Div//span//following::span[contains(text(),'Close Case')]");

	// Case Details
	By caseDetailsTab = By.xpath("//a[contains(text(),'Case Details')]");
	By oibMemberNameEditPencil = By
			.xpath("(//span[contains(text(),'OIB Member Name')]/following::slot//button/lightning-primitive-icon)[1]");
	By oibMemberNameInputBox = By
			.xpath("//span[contains(text(),'OIB Member Name')]/following::input[@id='input-1047']");
	By caseDetailsSaveButton = By.xpath("//button[@type='submit']");
	By getPriorityIndicator = By.xpath("//div[7]/div/lightning-output-field/div/lightning-formatted-text");
	// span[contains(text(),'Priority
	// Indicator')]/following::lightning-formatted-text");

	// Case Information
	By oibCaseSubjectEditPencil = By
			.xpath("(//span[contains(text(),'OIB Case Subject')]/following::slot//button/lightning-primitive-icon)[1]");
	By oibCaseSubjectInputBox = By.xpath("//input[@name='OIB_Case_Subject__c']");

	// Related List Quick Links tab
	By taskQuickLink = By.xpath("//a[@class='flex-wrap-ie11']//span[contains(text(),'Task')]");
	By allUpdatesArticlePost = By.xpath("//span[text()='All Updates']/following::article[1]");
	By firstTaskTickBoxOption = By.xpath("//span[contains(text(),'Select item 1')]/preceding-sibling::span");
	By taskHyperlink = By.xpath("(//a[@data-refid='recordId'])[1]");

	// Case Related Task functions
	By escalationSLA = By.xpath("//span[contains(text(),'Escalation SLA (Date/Time)')]");

	// CreateCase
	By priorityIndicatorDropdown = By.xpath("//button[@name='PriorityIndicator']");
	
	//New Case Creation 
	By newButton=By.xpath("//a[@title='New']");
	By nextbuttonOnCaseSelection=By.xpath("//button//span[text()='Next']");
	By dueDate=By.xpath("(//span[text()='Case Detail']/following::span[text()='Due Date'])[1]/following::input[1]");
	By invoiceInput=By.xpath("(//span[text()='Invoice'])[1]/following::input[1]");
	By timeRemining=By.xpath("(//span[text()='Time Remaining'])[1]/following::input[1]");
	By dueDatewithTime=By.xpath("(//span[text()='Due Date Date/Time'])[1]/following::input[1]");
	By caseGroupDropdown=By.xpath("//span[text()='Case Group']/following::a[1]");
	By caseReasonDropdown=By.xpath("(//span[text()='Case Reason']/following::a[1])[2]");
	By cause=By.xpath("//span[text()='Cause']/following::a[1]");
	By caseSaveBtn=By.xpath("//button[@title='Save']/span");
	

	public CreateCasePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	// Set Location Case Creation Page
	public void clickCCLocation() {
		WebElement locationbtn = uiTestHelper.waitForObject(createcaseLocation);
		// locationbtn.click();
		uiTestHelper.actionClick(locationbtn);
	}

	/*
	 * Create the Case By giving the below details -->firstname, -->lastname,
	 * -->phone, -->email, -->Origin, -->Case Reason and Case Cause
	 */

	public void clickCCReason() {
		WebElement reasonbtn = uiTestHelper.waitForObject(createcaseReason);
		reasonbtn.click();
	}

	public void clickCCCause() {
		WebElement causebtn = uiTestHelper.waitForObject(createcaseCause);
		causebtn.click();
	}

	public void setFirstName(String firstName) {
		WebElement firstname = uiTestHelper.waitForObject(contactfirstname);
		firstname.sendKeys(firstName);
	}

	public void setLastName(String lastName) {
		WebElement lastname = uiTestHelper.waitForObject(contactlastname);
		lastname.sendKeys(lastName);
	}

	public void setphone(String phone) {
		WebElement phoneno = uiTestHelper.waitForObject(contactPhone);
		phoneno.sendKeys(phone);
	}

	public void setEmail(String emailId) {
		WebElement email = uiTestHelper.waitForObject(contactEmail);
		email.sendKeys(emailId);
	}

	public void clickCaseHistoryQuickLink() {
		uiTestHelper.waitForObject(caseHistoryQuickLink).click();
	}

	public void clickTaskQuickLink() {
		WebElement clickTaskQuickLink = uiTestHelper.waitForObject(taskQuickLink);
		uiTestHelper.scrollIntoView(clickTaskQuickLink);
		uiTestHelper.clickJS(clickTaskQuickLink);
	}

	public void clickFirstTaskTickBox() {
		uiTestHelper.waitForObject(firstTaskTickBoxOption).click();
	}

	public void clickTaskHyperLink() {
		uiTestHelper.waitForObject(taskHyperlink).click();
	}

	public void clickCaseCreatebtn() {
		WebElement createcase = uiTestHelper.waitForObjectToBeClickable(createCase);
		createcase.click();
	}

	public void clickRouteToMenu() {
		uiTestHelper.clickLightningDropDownMenu(taskRouteToBox, "RouteTo", "TNT-CS");
		// uiTestHelper.clickJSByObjects(taskRouteToBox);
		// WebElement select =
		// uiTestHelper.waitForObject(By.xpath("//input[@name='RouteTo']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='TNT-CS']"));
		// select.click();
	}

	public void waitForSuccessMsg() {
		uiTestHelper.waitForObjectToBeInvisible(greenSuccessMsgPopUp);
	}

	public void setOwnershipField(String ownershipMenuValue) {
		WebElement ownershipDropDown = uiTestHelper.waitForObject(ownershipDropDownBox);
		ownershipDropDown.click();
		WebElement ownershipDropDownValue = uiTestHelper.waitForObject(By.xpath(
				"//*[@name='Ownership__c']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ ownershipMenuValue + "']"));
		ownershipDropDownValue.click();
	}

	public void setRootCauseField(String rootCauseMenuValue) {
		WebElement rootCauseDropDown = uiTestHelper.waitForObject(rootCauseDropDownBox);
		rootCauseDropDown.click();
		WebElement rootCauseDropDownValue = uiTestHelper.waitForObject(By.xpath(
				"//*[@name='Root_Cause__c']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ rootCauseMenuValue + "']"));
		rootCauseDropDownValue.click();
	}

	public void clickTaskSaveButton() {
		WebElement ele = uiTestHelper.waitForObject(taskSaveButton);
		uiTestHelper.clickJS(ele);
	}

	public void setIssueLocationField(String issueLocationDepot) {
		WebElement issueDepotBox = uiTestHelper.waitForObjectToBeClickable(issueLocationSearchBox);
		issueDepotBox.sendKeys(issueLocationDepot);
		WebElement issueDropDownDepot = uiTestHelper.waitForObject(
				By.xpath("//lightning-base-combobox-formatted-text[@title='" + issueLocationDepot + "']"));
		issueDropDownDepot.click();
	}

	public Boolean isEscalationSlaFieldDisplayed() {
		WebElement escSLA = uiTestHelper.waitForObject(escalationSLA);
		return escSLA.isDisplayed();
	}

	public void clickOrigin() {
		//WebElement locationbtn = uiTestHelper.waitForObjectToBeClickable(createcaseLocation);
		WebElement setloc = uiTestHelper.waitForObject(originLocation);
		uiTestHelper.actionClick(setloc);
		// setloc.click();
	}

	public boolean selectOrginDestination(String orgORdest) {
		try {
			uiTestHelper.waitForObjectToBeClickable(createcaseLocation).click();
			WebElement orginDestinationfield = uiTestHelper
					.waitForObjectToBeClickable(By.xpath("//lightning-base-combobox-item[@data-value='"+orgORdest+"']"));
			uiTestHelper.actionClick(orginDestinationfield);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void setCaseLoc(String loc) {
		clickCCLocation();
		WebElement setloc = uiTestHelper
				.waitForObjectToBeClickable(By.xpath("//lightning-base-combobox-item[@data-value='" + loc + "']"));
		setloc.click();
	}

	public boolean setCaseRoute(String caseRoute){
		try {
			WebElement routeBtn = uiTestHelper.waitForObjectToBeClickable(caseRouteTo);
			routeBtn.click();
			WebElement setRoute = uiTestHelper.waitForObjectToBeClickable(
					By.xpath("//lightning-base-combobox-item[@data-value='" + caseRoute + "']"));
			uiTestHelper.actionClick(setRoute);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void setReason(String Reason) {
		WebElement setreason = uiTestHelper.waitForObject(createcaseReason);
		setreason.click();
		char setChar = Reason.charAt(0);
		// System.out.print(setChar);
		String s = Character.toString(setChar);
		Actions act = new Actions(driver);
		act.keyDown(Keys.CONTROL).sendKeys(s).keyUp(Keys.CONTROL).perform();
		WebElement reason = uiTestHelper.waitForObjectToBeClickable(By.xpath("//button[@name='CaseReason']/following::lightning-base-combobox-item[@data-value='" + Reason + "']"));
		reason.click();

		/*
		 * if(driver.findElement(By.xpath("//span[text()='"+Reason+"']")).isDisplayed())
		 * {
		 * 
		 * driver.findElement(By.xpath("//span[text()='"+Reason+"']")).click(); } else {
		 * //WebElement menuItem =
		 * driver.findElement((By.xpath("//span[text()='"+Reason+"']")));
		 * 
		 * uiTestHelper.scrollIntoView(By.xpath("//span[text()='"+Reason+"']"));
		 * driver.findElement(By.xpath("//span[text()='"+Reason+"']")).click(); }
		 */

		/*
		 * try {
		 * if(driver.findElement(By.xpath("//span[text()='"+Reason+"']")).isDisplayed())
		 * {
		 * 
		 * driver.findElement(By.xpath("//span[text()='"+Reason+"']")).click(); }
		 * 
		 * }catch(Exception e) {
		 * uiTestHelper.scrollIntoView(By.xpath("//span[text()='"+Reason+"']"));
		 * driver.findElement(By.xpath("//span[text()='"+Reason+"']")).click(); }
		 */

	}

	public void setCause(String caseCause) {
		WebElement setcause = uiTestHelper.waitForObject(createcaseCause);
		setcause.click();
		WebElement cause = uiTestHelper.waitForObject(By.xpath("//span[text()='" + caseCause + "']"));
		uiTestHelper.scrollIntoView(cause);
		cause.click();

	}

	public void clickCaseAssign() {
		WebElement assign = uiTestHelper.waitForObject(caseAssign);
		assign.click();
	}

	// Set Location Unallocated Case Creation Page
	public void clickCaseOwnerLoc(String Location) {
		WebElement locationbtn = uiTestHelper.waitForObject(caseOwnerLoc);
		locationbtn.click();
		WebElement loc = uiTestHelper.waitForObject(By.xpath("//*[@title='" + Location + "']"));
		loc.click();
	}

	public void scrolltoContactDetails() {
		WebElement scroll = uiTestHelper.waitForObject(caseContactDetailsTitle);
		uiTestHelper.scrollIntoView(scroll);
	}

	public void setSenderContactName(String ContactName) {
		WebElement contact = uiTestHelper.waitForObject(senderContactName);
		contact.click();
		contact.sendKeys(ContactName);
	}

	public void setReceiverContactName(String ContactName) {
		WebElement contact = uiTestHelper.waitForObject(receiverContactName);
		contact.click();
		contact.sendKeys(ContactName);
	}

	public void setSenderContactPhone(String ContactPhone) {
		WebElement contact = uiTestHelper.waitForObject(senderContactPhone);
		contact.click();
		contact.sendKeys(ContactPhone);
	}

	public void setReceiverContactPhone(String ContactPhone) {
		WebElement contact = uiTestHelper.waitForObject(receiverContactPhone);
		contact.click();
		contact.sendKeys(ContactPhone);
	}

	public void setSenderContactEmail(String ContactEmail) {
		WebElement contact = uiTestHelper.waitForObject(senderContactEmail);
		contact.click();
		contact.sendKeys(ContactEmail);
	}

	public void setReceiverContactEmail(String ContactEmail) {
		WebElement contact = uiTestHelper.waitForObject(receiverContactEmail);
		contact.click();
		contact.sendKeys(ContactEmail);
	}

	public void setNewCaseOwner() {
		WebElement newOwner = uiTestHelper.waitForObject(changeCaseOwnerSearchUserBox);
		newOwner.sendKeys("Ben Grimstead");
	}

	public void clickCaseOwnerSubmitButton() {
		uiTestHelper.waitForObject(caseOwnerSubmitButton).click();
	}

	public void scrolltoCaseInformation() {
		WebElement scroll = uiTestHelper.waitForObject(caseInfoTitle);
		uiTestHelper.scrollIntoView(scroll);
	}

	public void createdStatus() {
		WebElement status = uiTestHelper.waitForObject(createdStatus);
		uiTestHelper.scrollIntoView(status);
	}

	public String getCreatedStatus() throws Exception {
		boolean temp = false;
		try {
			By statusField = By
					.xpath("(//p[contains(text(),'Status')]/following-sibling::p/slot/lightning-formatted-text)[1]");
			// WebElement statusfield
			temp = driver.findElement(statusField).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			temp = false;
		}
		if (temp) {
			By statusField = By
					.xpath("(//p[contains(text(),'Status')]/following-sibling::p/slot/lightning-formatted-text)[1]");
			WebElement statusfield = driver.findElement(statusField);
			return statusfield.getText();
		} else {
			By statusField = By
					.xpath("(//p[contains(text(),'Status')]/following-sibling::p/slot/lightning-formatted-text)[3]");
			WebElement statusfield = driver.findElement(statusField);
			return statusfield.getText();
		}
	}

	public String getPriorityValue() {
		By priority = By.xpath("//p[@title='Priority Indicator']/following::p/slot/lightning-formatted-text");
		WebElement priorityvalue = driver.findElement(priority);
		return priorityvalue.getText();
	}

	public void setCaseGroup(String group) {
		// caseGroupBoxBtn = uiTestHelper.waitForObject(caseGroupBox);
		uiTestHelper.waitForObject(caseGroupBox);
		uiTestHelper.scrollIntoView(caseGroupBox);
		uiTestHelper.clickJSByObjects(caseGroupBox);
		WebElement groupselect = uiTestHelper.waitForObject(By.xpath(
				"//input[@name='Case_Group__c']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ group + "']"));
		groupselect.click();
	}

	public void setCaseReason(String reason) {
		uiTestHelper.clickJSByObjects(caseReasonBox);
		WebElement reasonselect = uiTestHelper.waitForObject(By.xpath(
				"//button[@name='CaseReason']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ reason + "']"));
		reasonselect.click();
		//// input[@name='Case_Reason__c']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
	}

	// causeBox
	public void setCauseOption(String cause) {
		uiTestHelper.clickJSByObjects(causeBox);
		WebElement reasonselect = uiTestHelper.waitForObject(By.xpath(
				"//button[@name='Cause']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ cause + "']"));
		reasonselect.click();
		//// input[@name='Cause__c']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='
	}

	public void setCauseSetReasonSetGroupDropDowns(By by, String rootInputName, String itemValue) {
		uiTestHelper.clickJSByObjects(by);
		WebElement selectItem = uiTestHelper.waitForObject(By.xpath("//input[@name='" + rootInputName
				+ "']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='" + itemValue
				+ "']"));
		selectItem.click();
	}

	public void clickSaveUnallocatedCase() {
		WebElement savecase = uiTestHelper.waitForObject(saveUnallocatedCase);
		savecase.click();
	}

	public void setDateInCOBox(String date) {
		WebElement dateBox = uiTestHelper.waitForObject(monitorActivityCODateBox);
		dateBox.sendKeys(date);
	}

	public void setTimeInCOBox(String time) {
		WebElement dateBox = uiTestHelper.waitForObject(monitorActivityCOTimeBox);
		dateBox.sendKeys(time);
	}

	public void clickChangeOnwerButton() {
		// Depending on screen resolution its hidden in a dropwdown menu on the page, so
		// this handles both scenarios
		uiTestHelper.waitForObject(caseDropDownMenu).click();
		WebElement changeOwner = uiTestHelper.waitForObject(changeOwnerButton);
		uiTestHelper.clickJS(changeOwner);
	}

	public void clickMoreButtonOnCreatedCasePage() {
		uiTestHelper.clickJSByObjects(moreButtonOnCreatedCasePage);
	}

	public void clickReAssignButtonOnCreatedCasePage() {
		uiTestHelper.clickJSByObjects(reAssignBannerButton);
	}

	public void clickReAssignDropDownMenuBox() {
		uiTestHelper.waitForObject(reAssignToDropDownBox).click();
	}

	public void selectOriginFromReAssignBox() {
		uiTestHelper.waitForObject(originDropDownMenuItem).click();
	}

	public void selectDestinationFromReAssignBox() {
		uiTestHelper.waitForObject(destinationDropDownMenuItem).click();
	}

	public void selectCOMonitoringTickBox() {
		WebElement ele = uiTestHelper.waitForObject(COTickBox);
		uiTestHelper.clickJS(ele);
	}

	public void selectCOCallBackTickBox() {
		WebElement ele = uiTestHelper.waitForObject(COTickBox);
		uiTestHelper.clickJS(ele);
	}

	public void selectMonitoringActivitySaveButton() {
		WebElement ele = uiTestHelper.waitForObject(midPageTabsSaveButton);
		uiTestHelper.clickJS(ele);
	}

	public void selectCallBackSaveButton() {
		WebElement ele = uiTestHelper.waitForObject(midPageTabsSaveButton);
		uiTestHelper.clickJS(ele);
	}

	public void clickMonitorActivityTab() {
		WebElement ele = uiTestHelper.waitForObject(monitorActivityTab);
		uiTestHelper.clickJS(ele);
	}

	public void clickCallBackTab() {
		WebElement ele = uiTestHelper.waitForObject(callBackTab);
		uiTestHelper.clickJS(ele);
	}

	public void clickCloseCaseTab() {
		WebElement ele = uiTestHelper.waitForObject(closeCaseTab);
		uiTestHelper.clickJS(ele);
	}

	public void clickReOpenCaseTab() {
		WebElement ele = uiTestHelper.waitForObject(reOpenCaseTab);
		uiTestHelper.clickJS(ele);
	}

	public void clickMergedActionsTab() {
		WebElement ele = uiTestHelper.waitForObject(clickMergedActionsTab);
		uiTestHelper.clickJS(ele);
	}

	public void clickReOpenSaveButton() {
		WebElement ele = uiTestHelper.waitForObject(reOpenCaseSaveButton);
		uiTestHelper.clickJS(ele);
	}

	public void clickTaskTab() {
		WebElement ele = uiTestHelper.waitForObject(taskTabHeader);
		uiTestHelper.clickJS(ele);
	}

	public void selectDestinationFromRouteToBox() {
		uiTestHelper.clickLightningDropDownMenu(taskOriginDestinationRoute, "OriginDestination", "Destination");
	}

	public void clickSelectBoxChooseReOpenReason() {
		WebElement selectBox = uiTestHelper.waitForObject(reOpenSelectBox);
		Select select = new Select(selectBox);
		select.selectByVisibleText("Customer's Original Request Not Satisfied");
	}

	public void selectCloseCaseSaveButton() {
		uiTestHelper.waitForObject(closeCaseSaveButton).click();
	}

	public void selectClosedCaseTickBox() {
		uiTestHelper.waitForObject(closeCaseTickBox).click();
	}

	public void caseTabClosedCaseTickBox() {
		WebElement tickBox = uiTestHelper.waitForObject(caseTabCloseCaseTickBox);
		uiTestHelper.clickJS(tickBox);
	}

	public void switchToIFrameAndClickClosedActionsAndValidate() {
		WebElement saleforceIFrame = driver.findElement(By.xpath("//iframe[@title='accessibility title']"));
		driver.switchTo().frame(saleforceIFrame);
		caseTabClosedCaseTickBox();
		setOwnershipField("Lost");
		CMOD_FF_Reusable cmodFFReusable = new CMOD_FF_Reusable();
		cmodFFReusable.booleanValidationForIssueLocationBox();
		setIssueLocationField("LIS");
		setRootCauseField("No tracking");
		selectMergedActionsSaveButton();
		driver.switchTo().parentFrame();
	}

	public void switchToIFrameAndClickClosedActions() {
		WebElement saleforceIFrame = driver.findElement(By.xpath("//iframe[@title='accessibility title']"));
		driver.switchTo().frame(saleforceIFrame);
		caseTabClosedCaseTickBox();
		selectMergedActionsSaveButton();
		driver.switchTo().parentFrame();
	}

	public void switchToIFrameClickClosedActionsAndSelectThreeDropDowns()  {
		WebElement saleforceIFrame = driver.findElement(By.xpath("//iframe[@title='accessibility title']"));
		driver.switchTo().frame(saleforceIFrame);
		caseTabClosedCaseTickBox();
		setOwnershipField("Lost");
		setIssueLocationField("LIS");
		setRootCauseField("No tracking");
		selectMergedActionsSaveButton();
		driver.switchTo().parentFrame();
	}

	public void selectMergedActionsSaveButton() {
		uiTestHelper.waitForObject(mergedActionsSaveButton).click();
	}

	public void selectCaseSummaryTab() {
		WebElement caseTab = uiTestHelper.waitForObject(caseSumamryTab);
		uiTestHelper.clickJS(caseTab);
	}

	public void selectCaseInformationTab() {
		uiTestHelper.waitForObject(caseInformationTab).click();
	}

	public void selectCaseDetailsTab() {
		uiTestHelper.waitForObject(caseDetailsTab).click();
	}

	public void selectOIBNameEditerPencil() {
		uiTestHelper.waitForObject(oibMemberNameEditPencil).click();
	}

	public void selectOIBNameBoxSendKeysAndSave(String oibMemberName) {
		WebElement oibBox = uiTestHelper.waitForObject(oibMemberNameInputBox);
		oibBox.click();
		oibBox.sendKeys(oibMemberName);
		uiTestHelper.waitForObject(caseDetailsSaveButton).click();
	}

	public void selectOIBCaseSubjectBoxSendKeysAndSave(String oibCaseSubjectText) {
		WebElement oibBox = uiTestHelper.waitForObject(oibCaseSubjectInputBox);
		oibBox.click();
		oibBox.sendKeys(oibCaseSubjectText);
		uiTestHelper.waitForObject(caseDetailsSaveButton).click();
	}

	public void selectConsignmentInformationTab() {
		uiTestHelper.waitForObject(consignmentInformationTab).click();
	}

	public void selectreAssignSaveButton() {
		uiTestHelper.waitForObject(reAssignSaveButton).click();
	}

	public String getConsignmentStatusDescription() {
		WebElement info = uiTestHelper.waitForObject(caseOwnerName);
		return info.getText();
	}

	public String getUserCreationName() {
		WebElement info = uiTestHelper.waitForObject(caseOwnerName);
		return info.getText();
	}

	public String getupdatedConsignmentStatusDescriptionFieldName() {
		WebElement info = uiTestHelper.waitForObject(updatedConsignmentStatusDescriptionField);
		return info.getText();
	}

	public Boolean isIssueLocationBoxDisplayed() {
		WebElement issueLocation = uiTestHelper.waitForObject(issueLocationSearchBox);
		return issueLocation.isDisplayed();
	}

	public String getOriginalValueText() {
		WebElement info = uiTestHelper.waitForObject(rowOneOriginalValue);
		return info.getText();
	}

	public String getNewValueText() {
		WebElement info = uiTestHelper.waitForObject(rowOneNewValue);
		return info.getText();
	}

	public String getManualClosedStatus() {
		WebElement info = uiTestHelper.waitForObject(topOfPageCaseStatusBox);
		return info.getText();
	}

	public String getMonitoringStatus() {
		WebElement info = uiTestHelper.waitForObject(statusDisplaysMonitoringText);
		return info.getText();
	}

	public void clickReassignButton() {
		// Need a dynamic wait here as the application takes awhile to spit out the case
		// before being able to run the next section
		// and the code is running faster than that so it fails
		try {
			WebElement ele = uiTestHelper.waitForObject(moreOptions);
			uiTestHelper.clickJS(ele);

		} catch (TimeoutException e) {
			// no catch needed - if more tab doesnt exist it will click reassign button
		}
		clickReAssignButtonOnCreatedCasePage();
	}

	public void clickOptions(String options) {
		clickReassignButton();
		WebElement ele = uiTestHelper.waitForObject(
				By.xpath("//div[@class='uiPopupTrigger']//following::ul[1]/li/a[@title='" + options + "']"));
		uiTestHelper.clickJS(ele);
	}

	public void setCollecionDate(String time) {
		WebElement collectDate = uiTestHelper.waitForObject(collectionDate);
		collectDate.sendKeys(time);
	}

	public boolean ifCountryFieldisFilled() {
		try {
			// driver.findElement(By.xpath("//label['Country/Territory']/following::span['Countries__c']/input"));
			WebElement clearbtn = driver.findElement(By.xpath("//button[@title='Clear Selection']"));
			uiTestHelper.clickJS(clearbtn);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void taskCountryTerritoryBox(String country)  {
		ifCountryFieldisFilled();
		WebElement taskLookupBox = uiTestHelper.waitForObject(taskCountryTerritoryBox);
		taskLookupBox.sendKeys(country);
		WebElement dropdownCountry = uiTestHelper.waitForObject(By
				.xpath("//label[text()='Country/Territory']/following::lightning-base-combobox-formatted-text[@title='"
						+ country + "']"));
		uiTestHelper.clickJS(dropdownCountry);
		taskLookupBox.sendKeys(Keys.TAB);
	}

	public void sendTextToTaskInstructionsBox() {
		WebElement taskInstructionBox = uiTestHelper.waitForObject(taskInstructionTextBox);
		uiTestHelper.scrollIntoView(taskInstructionBox);
		taskInstructionBox.sendKeys("TestG");
		taskInstructionBox.sendKeys(Keys.TAB);
	}

	public void setTaskDeadlineDate(String date) {
		WebElement collectDate = uiTestHelper.waitForObject(taskDeadlineDateBox);
		collectDate.sendKeys(date);
	}

	public void setTaskDeadlineTime(String time) {
		WebElement collectDate = uiTestHelper.waitForObject(taskTimeDeadlineBox);
		uiTestHelper.clickJS(collectDate);
		collectDate.clear();
		collectDate.sendKeys(time);
	}

	public boolean isTaskLocationDisabled()  {
		try {
			WebElement tasklocationinput = uiTestHelper.waitForObject(By.xpath("//button[@name='locationFedOps']"));
			return !tasklocationinput.isEnabled();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public void setTaskLocationFreely(String depot) {
		WebElement depotfield = uiTestHelper.waitForObject(By.xpath("//input[@name='location']"));
		depotfield.sendKeys(depot);
	}

	public void setTaskLocation(String depot) {
		WebElement depotfield = uiTestHelper.waitForObject(taskLocationBox);
		depotfield.sendKeys(depot);
		// depotfield.sendKeys(Keys.TAB);
		WebElement depotMenuOption = uiTestHelper
				.waitForObject(By.xpath("//div[@role='option']/span/span[text()='" + depot + "']"));
		uiTestHelper.clickJS(depotMenuOption);
	}

	public boolean setTaskLocationForOvergoods(String location) throws Exception {
		try {
			WebElement tasklocationbutton = uiTestHelper.waitForObject(By.xpath("//button[@name='locationFedOps']"));
			uiTestHelper.clickJS(tasklocationbutton);
			tasklocationbutton.sendKeys(location);
			// tasklocationbutton.sendKeys(location.charAt(0)+"");
			WebElement colldepot = uiTestHelper.waitForObject(By.xpath(
					"//button[@name='locationFedOps']/following::div[@role='listbox'][1]/lightning-base-combobox-item//span[@title='"
							+ location + "']"));
			colldepot.click();
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	// button[@name='locationFedOps']/following::div[@role='listbox'][1]/lightning-base-combobox-item//span[@title='Overgoods
	// - Canada']
	public String getTaskLocation() {
		WebElement depotfield = uiTestHelper.waitForObject(By.xpath("//input[@name='location']"));
		return depotfield.getAttribute("value");
	}

	public void setDestinationDepot(String desDepot) {
		WebElement desDepotBox = uiTestHelper.waitForObject(destinationDepot);
		desDepotBox.sendKeys(desDepot);
		WebElement colldepot = uiTestHelper
				.waitForObject(By.xpath("//lightning-base-combobox-formatted-text[@title='" + desDepot + "']"));
		colldepot.click();
	}

	public void setCollectionDepot(String colDepot) {
		WebElement colDepotBox = uiTestHelper.waitForObject(collectionDepot);
		colDepotBox.sendKeys(colDepot);
		WebElement colldepot = uiTestHelper
				.waitForObject(By.xpath("//lightning-base-combobox-formatted-text[@title='" + colDepot + "']"));
		colldepot.click();
	}

	public void setPcsValue(String pcsAmount) {
		WebElement pcsBox = uiTestHelper.waitForObject(pcsTextBox);
		pcsBox.sendKeys(pcsAmount);
	}

	public void setTotalWeightValue(String weightKG) {
		WebElement pcsBox = uiTestHelper.waitForObject(totalWeightKGBox);
		pcsBox.sendKeys(weightKG);
	}

	// Sender & Receiver Information
	public void senderInformationBlock(String Name, String Street, String postCode, String Town, String Province) {
		WebElement nameBox = uiTestHelper.waitForObject(senderContactName);
		nameBox.sendKeys(Name);
		WebElement streetBox = uiTestHelper.waitForObject(senderStreet);
		streetBox.sendKeys(Street);
		WebElement postcodeBox = uiTestHelper.waitForObject(senderPostCode);
		postcodeBox.sendKeys(postCode);
		WebElement townBox = uiTestHelper.waitForObject(senderTown);
		townBox.sendKeys(Town);
		WebElement provinceBox = uiTestHelper.waitForObject(sendersProvince);
		provinceBox.sendKeys(Province);
	}

	public void receiverInformationBlock(String Name, String Street, String postCode, String Town) {
		WebElement nameBox = uiTestHelper.waitForObject(receiverContactName);
		nameBox.sendKeys(Name);
		WebElement streetBox = uiTestHelper.waitForObject(receiverStreet);
		streetBox.sendKeys(Street);
		WebElement postcodeBox = uiTestHelper.waitForObject(receiverPostCode);
		postcodeBox.sendKeys(postCode);
		WebElement townBox = uiTestHelper.waitForObject(receiverTown);
		townBox.sendKeys(Town);
	}

	public void sendersLookUpAndCountryFormBoxes(String customerID) {
		WebElement senLookupBox = uiTestHelper.waitForObject(sendersLookupBox);
		senLookupBox.sendKeys(customerID);
		WebElement colldepot = uiTestHelper
				.waitForObject(By.xpath("//lightning-base-combobox-formatted-text[@title='" + customerID + "']"));
		colldepot.click();

	}

	public void sendersCountryTerritoryBox(String country) {
		WebElement senLookupBox = uiTestHelper.waitForObject(sendersCountryTerritoryBox);
		senLookupBox.sendKeys(country);
		WebElement dropdownCountry = uiTestHelper
				.waitForObject(By.xpath("(//lightning-base-combobox-formatted-text[@title='" + country + "'])[2]"));
		dropdownCountry.click();
	}

	public void receiversCountryTerritoryBox(String country) {
		WebElement recCountryBox = uiTestHelper.waitForObject(receiversCountryTerritoryBox);
		recCountryBox.sendKeys(country);
		WebElement dropdownCountry = uiTestHelper
				.waitForObject(By.xpath("(//lightning-base-combobox-formatted-text[@title='" + country + "'])[3]"));
		dropdownCountry.click();
	}

	public void collectionTownAndStreet(String Street, String Town) {
		WebElement collectionStreetBox = uiTestHelper.waitForObject(collectionStreet);
		collectionStreetBox.sendKeys(Street);
		WebElement collectionTownBox = uiTestHelper.waitForObject(collectionTown);
		collectionTownBox.sendKeys(Town);
	}

	public void deliveryTownAndStreet(String Street, String Town) {
		WebElement collectionStreetBox = uiTestHelper.waitForObject(deliveryStreet);
		collectionStreetBox.sendKeys(Street);
		WebElement collectionTownBox = uiTestHelper.waitForObject(deliveryTown);
		collectionTownBox.sendKeys(Town);
	}

	public void clickCreateCaseOnUnallocatedForm() {
		uiTestHelper.waitForObject(createCaseButton).click();
	}

	public void noConsignmentNumberPopUpWindowOkButton() {
		uiTestHelper.waitForObjectToBeClickable(noConsignmentPopUpOkBtn).click();
	}

	public Boolean checkStatusIsCreated() {
		WebElement statusMessage = uiTestHelper.waitForObject(statusCreatedMessage);
		return statusMessage.isDisplayed();
	}

	/**
	 * Method to check Assign to Me checkbox visibles
	 *
	 * @return
	 */
	public boolean isAssignToMeChkVisible() {
		WebElement assign = uiTestHelper.waitForObject(caseAssign);
		return assign.isDisplayed();
	}

	/**
	 * Method to check Assign to Me checkbox visible
	 *
	 * @return
	 */
	public boolean isAssignToMeChecked() {
		WebElement assignToMechk = uiTestHelper.waitForObject(caseAssign);
		assignToMechk.click();
		WebElement assignToMecheckBox = uiTestHelper.waitForObject(assignToMeCheckbox);
		return assignToMecheckBox.isSelected();
	}

	public String getCaseReason() {

		WebElement reason = uiTestHelper.waitForObject(caseReasonBox);
		return reason.getAttribute("value");
	}

	public String getCaseCause() {

		WebElement reason = uiTestHelper.waitForObject(causeBox);
		return reason.getAttribute("value");
	}

	public void clickCaseReason() {
		uiTestHelper.clickJSByObjects(caseReasonBox);
	}

	public void clickCaseCause() {
		uiTestHelper.clickJSByObjects(causeBox);
	}

	public int getCaseReasonDropDownSize() {
		List<WebElement> caseReasonDropDown = uiTestHelper.waitForObjects(
				By.xpath("//input[@name='Case_Reason__c']/following::div[2]/lightning-base-combobox-item"));
		int reasonDropDownSize = caseReasonDropDown.size();
		return reasonDropDownSize;
	}

	public int getCaseCauseDropDownSize() {
		List<WebElement> causeDropDown = uiTestHelper
				.waitForObjects(By.xpath("//input[@name='Cause__c']/following::div[2]/lightning-base-combobox-item"));
		int causeDropDownSize = causeDropDown.size();
		return causeDropDownSize;
	}

	public String getTopArticleText() {
		WebElement article1 = uiTestHelper.waitForObject(allUpdatesArticlePost);
		return article1.getText();
	}

	public void clickPriorityIndicatorDropdown() {
		WebElement priorityDropdown = uiTestHelper.waitForObject(priorityIndicatorDropdown);
		priorityDropdown.click();
	}

	public String[] getPriorityIndicatorListItmes() {
		clickPriorityIndicatorDropdown();
		WebElement myTable = driver.findElement(By.xpath("//label[text()='Priority Indicator']/following::div[5]"));
		List<WebElement> objRow = myTable.findElements(By.tagName("lightning-base-combobox-item"));
		int row_count = objRow.size();
		String[] pi_list = new String[row_count - 1];
		for (int i = 2; i <= row_count; i++) {
			String listitemname = driver.findElement(
					By.xpath("//label[text()='Priority Indicator']/following::div/lightning-base-combobox-item[" + i
							+ "]/span/span"))
					.getText();
			System.out.println(listitemname);
			pi_list[i - 2] = listitemname;

		}

		return pi_list;
	}

	public void setPriorityIndicator(String Priority) {
		clickPriorityIndicatorDropdown();
		WebElement priority = uiTestHelper.waitForObject(By.xpath("//*[@title='" + Priority + "']"));
		priority.click();
	}

	public String getPopupMsgText() {
		WebElement successMsgTxt = uiTestHelper.waitForObject(popupMsgText);
		return successMsgTxt.getText();
	}

	public boolean isMsgDisplayed() {
		try {
			WebElement errormessage = driver.findElement(By.xpath("//a[@class='forceActionLink']"));
			return errormessage.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public String getRecentTaskId() {
		WebElement recentTaskId = uiTestHelper.waitForObject(createdTaskId);
		return recentTaskId.getText();
	}

	public void openRecentlyCreatedTask(String recentTaskID) {
		WebElement clickRecentTask = uiTestHelper.waitForObject(By.xpath("//a[@title='" + recentTaskID + "']"));
		clickRecentTask.click();
	}

	public boolean isOrginDestinationDisabled() {
		try {
			WebElement isOrginDestinationDisabled = driver.findElement(By.xpath("//button[@name='OriginDestination']"));
			return !isOrginDestinationDisabled.isEnabled();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean setOriginDestination(String originDestination){
		try {
			WebElement clickTaskOriginDestination = uiTestHelper.waitForObject(taskOriginDestinationRoute);
			clickTaskOriginDestination.click();
			WebElement selectTaskOriginDestination = uiTestHelper.waitForObject(By.xpath(
					"//button[@name='OriginDestination']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
							+ originDestination + "']"));
			selectTaskOriginDestination.click();
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public String getErrorMessageOvergoods() throws Exception {
		try {
			WebElement errormessage = driver
					.findElement(By.xpath("//div[@class='forceVisualMessageQueue']/div/div/div/div/div/span"));
			return errormessage.getText();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return "";
		}
	}

	public void selectRecordType(String caseType) {
		WebElement clickRecordType = uiTestHelper.waitForObject(recordType);
		clickRecordType.click();
		WebElement selectRecordType = uiTestHelper.waitForObject(By.xpath(
				"//button[@name='SelectRecordType']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
						+ caseType + "']"));
		selectRecordType.click();
	}

	public String getPriorityIndicatorCaseDetailsTab() {
		WebElement priorityIndicator = uiTestHelper.waitForObject(getPriorityIndicator);
		return priorityIndicator.getText();
	}

	public void waitForErrorMsg() {
		uiTestHelper.waitForObjectToBeInvisible(redErrorMsgPopup);
	}
	
	public void newCaseBtn() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(newButton);
	ele.click();
	}
	
	public void selectCaseType(String caseType) {
		WebElement ele=uiTestHelper.waitForObject(By.xpath("//span[contains(text(),'"+caseType+"')]/preceding::span[1]"));
		uiTestHelper.clickJS(ele);
		
	}
	
	public void enterDueDate() {
		WebElement ele=uiTestHelper.waitForObject(dueDate);
		ele.sendKeys(uiTestHelper.getTomorrowsDate());
	}
	
	public void enterInvoice(String invoice) {
		WebElement ele=uiTestHelper.waitForObject(invoiceInput);
		uiTestHelper.scrollIntoView(ele);
		ele.sendKeys(invoice);
	}
	
	public void enterTimeRemaining() {
		WebElement ele=uiTestHelper.waitForObject(timeRemining);
		uiTestHelper.scrollIntoView(ele);
		ele.sendKeys(uiTestHelper.getSystemDate());
	}
	public void enterDueDatewithTimeRemaining() {
		WebElement ele=uiTestHelper.waitForObject(dueDatewithTime);
		uiTestHelper.scrollIntoView(ele);
		ele.sendKeys(uiTestHelper.getTomorrowsDate());
	}
	public void nextButton() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(nextbuttonOnCaseSelection);
		uiTestHelper.clickJS(ele);
	}
	
	public void selectCaseGroupDropDown() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(caseGroupDropdown);
		uiTestHelper.clickJS(ele);
		
	}
	public void selectCaseReasonDropDown() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(caseReasonDropdown);
		uiTestHelper.clickJS(ele);
		
	}
	public void selectCaseCauseDropDown() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(cause);
		uiTestHelper.clickJS(ele);
		
	}
	public void selectCaseGroup(String caseGroup) {
		WebElement ele=uiTestHelper.waitForObject(By.xpath("//span[text()='Case Group']/following::li/a[@title='"+caseGroup+"']"));
		uiTestHelper.scrollIntoView(ele);
		uiTestHelper.clickJS(ele);
	}
	public void selectCaseReason(String caseReason) {
		WebElement ele=uiTestHelper.waitForObject(By.xpath("//span[text()='Case Reason']/following::li/a[@title='"+caseReason+"']"));
		uiTestHelper.scrollIntoView(ele);
		uiTestHelper.clickJS(ele);
	}
	public void selectCaseCause(String caseCause) {
		WebElement ele=uiTestHelper.waitForObject(By.xpath("//span[text()='Cause']/following::li/a[@title='"+caseCause+"']"));
		uiTestHelper.scrollIntoView(ele);
		uiTestHelper.clickJS(ele);
	}
	public void saveCase() {
		WebElement ele=uiTestHelper.waitForObjectToBeClickable(caseSaveBtn);
		uiTestHelper.clickJS(ele);
		
	}
	
	

}
