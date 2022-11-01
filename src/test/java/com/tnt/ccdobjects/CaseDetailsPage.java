package com.tnt.ccdobjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class CaseDetailsPage {
	UiTestHelper uiTestHelper;
	WebDriver driver;
	// case summary
	By casesummarytab = By.xpath("//a[@data-label='Case Summary']");
	By updateConDesc = By.xpath("//span[contains(text(),'Updated Consignment Status Description')]");
	// case Details tab Object
	By casedetailstab = By.xpath("//a[@data-label='Case Details']");
	By caseOwner = By.xpath("(//span[contains(text(),'Case Owner')])[1]/following::lightning-formatted-lookup[1]");
	By oibCaseOwner = By.xpath("(//span[contains(text(),'OIB Case Owner')])[1]/following::lightning-formatted-text");
	By caseOIB = By.xpath("(//span[contains(text(),'OIB Member Name')])[1]/following::lightning-formatted-lookup[1]");
	By caseOwnerOIB = By.xpath("//span[contains(text(),'OIB Case Owner')]/following::div[1]/lightning-formatted-text");
	By caseOIBMemUpdate = By
			.xpath("(//span[contains(text(),'OIB Member Name')])[1]/following::lightning-primitive-icon[1]");
	By oibMemberSearch = By.xpath("(//input[contains(@aria-controls,'dropdown-element')])[3]");
	By oibSearch = By.xpath("//input[@placeholder='Search People...']");
	By oibName = By.xpath("//input[contains(@placeholder,'Automation')]");
	By oibShowAll = By.xpath("//span[contains(@title,'Show All Results')]");
	By oibCaseSubject = By.xpath("//span[contains(text(),'OIB Case Subject')]");
	By caseDtlsCancelBtn = By.xpath("//button[@name='cancel']");
	By cmodCaseIDNumber = By.xpath("//lightning-formatted-text[contains(text(),'C-')]");

	By oibSubmit = By.xpath("//button[@name='submit']");
	By caseOwnerCountry = By
			.xpath("//span[contains(text(),'CO – Country/Territory')]/following::lightning-formatted-lookup[1]");
	By cmodcaseid = By.xpath("//span[contains(text(),'CMOD Case Id')]/following::lightning-formatted-text[1]");
	By shipmentDirection = By
			.xpath("//span[contains(text(),'Shipment Direction')]/following::lightning-formatted-text[1]");
	By custodialOwner = By.xpath("//span[contains(text(),'Custodial Owner')]/following::lightning-formatted-text[1]");
	// Case Information tab Objects
	By caseinformationtab = By.xpath("//a[@data-label='Case Information']");
	By proactiveCase = By
			.xpath("//span[contains(text(),'Case Type')]/following::lightning-formatted-text[text()='Proactive']");
	By caseType = By.xpath("//span[contains(text(),'Case Type')]/following::lightning-formatted-text[1]");

	By reactiveCaseStatus = By.xpath("(//span[text()='Status'])[1]/following::lightning-formatted-text[1]");
	By status = By.xpath("(//span[text()='Status'])/following::lightning-formatted-text[2]");

	By statusOnHighlightPanel = By.xpath("(//P[text()='Status'])[1]/following::lightning-formatted-text[1]");
	By statusEdit = By.xpath("(//span[text()='Status'])[1]/following::lightning-primitive-icon[2]");

	By contactName = By.xpath("//span[contains(text(),'Sender Contact Name')]/following::lightning-formatted-text[1]");
	By contactPhone = By
			.xpath("//span[contains(text(),'Sender Contact Phone')]/following::lightning-formatted-phone[1]");
	By contactemail = By
			.xpath("//span[contains(text(),'Sender Contact Email')]/following::lightning-formatted-email[1]");
	By casegroup = By.xpath("//span[contains(text(),'Case Group')]/following::lightning-formatted-text[1]");
	By casereason = By.xpath("//slot[@name='tabs']//span[text()='Case Reason']/following::lightning-formatted-text[1]");
	By casereasonwithCmod = By
			.xpath("(//span[contains(text(),'Case Reason')]/following::lightning-formatted-text[1])[2]");
	By cause = By.xpath("//span[contains(text(),'Cause')]/following::lightning-formatted-text[1]");
	By causewithCmod = By.xpath("(//span[contains(text(),'Cause')]/following::lightning-formatted-text[1])[2]");
	By custRemark = By
			.xpath("//span[text()='Dedicated Report - Remarks for Customer']/following::lightning-formatted-text[1]");

	// Case ConsignmentInfo tab Objects
	By caseconsignmentinfotab = By.xpath("//a[@data-label='Consignment Information']");
	By consignmentno = By
			.xpath("//slot[@name='tabs']//span[text()='Consignment #']/following::lightning-formatted-text[1]");

	By collectiondepot = By
			.xpath("//span[contains(text(),'Collection Depot')]/following::lightning-formatted-lookup[1]");
	By destinationdepot = By
			.xpath("//span[contains(text(),'Destination Depot')]/following::lightning-formatted-lookup[1]");
	By destDepotLink = By
			.xpath("//span[contains(text(),'Destination Depot')]/following::lightning-formatted-lookup[1]/a");
	By originDepotLink = By.xpath("//span[contains(text(),'Origin Depot')]/following::lightning-formatted-lookup[1]/a");
	By originCaseChkBox = By.xpath("//input[@name='Origin_Case__c']/following::span[1]");
	By destCaseChkBox = By.xpath("//input[@name='Destination_Case__c']/following::span[1]");

	// Case Sender and Receiver tab Objects
	By pemoreTabs = By.xpath("//button[@title='More Tabs' and (text()='More')]");
	By moreTabs = By.xpath("(//button[@title='More Tabs'])[2]");

	By peSenderAccountNo = By
			.xpath(" (//span[contains(text(),'Sender’s Account Number')]/following::lightning-formatted-text[1])");
	By peReceiverAccountNo = By
			.xpath("//span[contains(text(),'Sender’s Account Number')]/following::div[3]/lightning-formatted-text[1]");
	By senderandreceiverinfotab = By.xpath("//span[contains(text(),'Sender & Receiver Information')]");
	By senderAccountNo = By
			.xpath("//span[contains(text(),'Sender’s Account Number')]/following::lightning-formatted-text[1]");
	By sendercontactName = By
			.xpath("(//span[contains(text(),'Contact Name')])[3]/following::lightning-formatted-text[1]");
	By receivercontactName = By
			.xpath("(//span[contains(text(),'Contact Name')])[4]/following::lightning-formatted-text[1]");

	By senderCountry = By
			.xpath("//span[contains(text(),'Sender’s Country/Territory')]/following::lightning-formatted-lookup[1]");
	By peSenderCountry = By
			.xpath("(//span[contains(text(),'Sender’s Country/Territory')])/following::lightning-formatted-lookup[1]");
	By receiverCountry = By
			.xpath("//span[contains(text(),'Sender’s Country/Territory')]/following::lightning-formatted-lookup[2]");

	// Case Related tab Object
	By relatedtab = By.xpath("//a[text()='Related']");
	// By actionsView = By.xpath("(//h2[text()='Related List Quick
	// Links']/following::span[contains(text(),'Actions')][1]");
	By actionsView = By.xpath("//h2[text()='Related List Quick Links']/following::a[1]");
	By caseRemarView = By.xpath("//a/slot/span[contains(text(),'Case Remarks')]");
	// h2[text()='Related List Quick Links']/following::span[contains(text(),'Case
	// Remarks')][1]");
	By taskView = By.xpath("//h2[text()='Related List Quick Links']/following::span[contains(text(),'Tasks')][1]");
	// By escalationView = By.xpath("//h2[text()='Related List Quick
	// Links']/following::a[4]/span");
	By escalationView = By.xpath("//h2[text()='Related List Quick Links']/following::a[4]");
	// Case Monitor tab Objects
	By monitorActivity = By.xpath("//span[@class='title' and text()='Monitor Activity']");
	By monitorCODate = By
			.xpath("(//span[contains(text(),'Monitor Activity')]/following::label[text()='Date']/following::a[1])[1]");
	By monitorTimeInput = By.xpath(
			"(//span[contains(text(),'Monitor Activity')]/following::label[text()='Time']/following::input[1])[1]");
	By sladatetime = By.xpath("//p[contains(text(),'SLA')]/following::lightning-formatted-text[1]");
	By monitorTime = By.xpath("(//label[contains(text(),'Time')])[1]/following::a[1]");
	By saveMonitorActivity = By.xpath("//button[@type='submit']");
	By greengif = By.xpath("//img[@src='/img/samples/light_green.gif']");
	By redgif = By.xpath("//img[@src='/img/samples/light_red.gif']");
	By yellowgif = By.xpath("//img[@src='/img/samples/light_yellow.gif']");
	By monitorcheckbox = By.xpath("//span[contains(text(),'CO - Monitoring Completed')]/following::input[1]");
	By monitorORcallback = By.xpath("//p[contains(text(),'SLA Status')]/following::span[1]");
	// Case Callback tab Objects
	By callbacktab = By.xpath("//span[@class='title' and text()='Call Back']");
	By callbackdate = By
			.xpath("(//label[contains(text(),'Date')]/following::input[@class=' input']/following::a[1])[1]");
	By callbackTime = By.xpath("//span[contains(text(),'CO - Next Call Back')]/following::input[2]");
	By callbacksave = By.xpath("//*[contains(@id,':0')]/div/div[3]/div/div/div[2]/div[2]/button");
	By callbackcheckbox = By.xpath("//span[contains(text(),'CO - Call Back Completed')]/following::input[1]");

	// Task
	By taskTab = By.xpath("//span[@class='title' and contains(text(), 'Task')]");
	By taskCountry = By.xpath("(//label[contains(text(),'Country/Territory')])[3]/following::input[1]");
	By countryLookup = By.xpath("//div[@class='pullToRefresh']/following::table/tbody/tr/td[1]/a");
	By routeTo = By.xpath("//input[@name='RouteTo']");
	By routetoTNTops = By.xpath("//lightning-base-combobox-item[@data-value='TNT-CS']");
	By taskInstruction = By.xpath("//textarea[@name='Description']");
	By taskdate = By.xpath("(//input[@name='datetime'])[1]");
	By tasktime = By.xpath("(//div[@class='input-has-icon input-has-icon--right']/input)[1]"); // ("(//input[@name='datetime'])[2]");
	By taskLocation = By.xpath("//input[@name='Location']");
	By createTask = By.xpath("//textarea[@name='Description']/following::button[text()='Save']");
	By taskTableList = By.xpath("//h1[text()='Tasks']/following::table[1]/tbody/tr");
	By cmodTaskId = By.xpath("//p[text()='CMOD Task Id']/following::lightning-formatted-text[1]");
	By createdTaskLink = By.xpath("//a[contains(text(),'T-')]");
	By escalateTask = By.xpath("//button[@name='Task__c.Escalate']");
	By escalationSLA = By
			.xpath("//span[contains(text(),'Escalation SLA (Date/Time)')]/following::lightning-formatted-text[1]");
	By editTask = By.xpath("//button[@name='Task__c.Edit']");
	By couTerritory = By.xpath("//span[contains(text(),'Country/Territory')]/following::lightning-formatted-text[1]");
	By tntLocation = By.xpath("//span[contains(text(),'TNT Location)]/following::lightning-formatted-text[1]");
	By escReason = By.xpath("//button[@name='Escalation_Reason__c']");
	By escReasonVal = By.xpath("//lightning-base-combobox-item[@data-value='Need Help']");
	By escComments = By.xpath("//textArea[@name='Escalation_comments__c']");
	By taskEscSave = By.xpath("//button[@name='Save']");
	By taskEscCancel = By.xpath("//button[@name='Cancel']");
	By taskOwnerId = By.xpath("(//span[contains(text(),'Owner ID')])[1]/following::lightning-formatted-lookup");
	By taskOwnerIdValue = By.xpath("//span[contains(text(),'Owner ID')]/following::lightning-formatted-lookup");
	By closeTaskTab = By.xpath("//button[@title='Close Tasks']");
	By closeRecentlyOpenedTask = By.xpath("//span[contains(text(),'Close T-')]");
	By taskTableSize = By.xpath("//table/tbody/tr");

	// Action tab objects
	By actionTab = By.xpath("//span[@class='title' and (text()='Action')]");
	By actionTypeDropDown = By.xpath("(//div[@class='uiPopupTrigger']/following::a[contains(text(),'--None--')])[1]");
	By actionCost = By.xpath("//span[text()='Cost']/following::input[1]");
	By actionSave = By.xpath("//span[text()='Cost']/following::span[text()='Save'][1]");
	By actionTable = By.xpath("//h1[@title='Actions']/following::table[1]/tbody");
	By actionTableList = By.xpath("//h1[@title='Actions']/following::table[1]/tbody/tr");
	By actionCurrency = By.xpath("(//div[@class='uiPopupTrigger']/following::a[contains(text(),'EUR - Euro')])[1]");
	By actionsClose = By.xpath("//button[@title='Close Actions']");

	// Customer Remark
	By customerRemarkTab = By.xpath("//span[@class='title' and (text()='Remark for Customer')]");
	By custremarkDescription = By.xpath("//span[text()='New Customer Remark']/following::textarea[1]");
	By custRemarkSave = By.xpath("//span[text()='New Customer Remark']/following::span[text()='Save']");

	// case Remark
	By caseRemarkTab = By.xpath("//a[@title='Case Remark']/span[text()='Case Remark']");// span[@class='title' and
																						// (text()='Case Remark')]");
	By caseremarkDescription = By.xpath("//span[text()='Body']/following::textarea[1]");
	By caseRemarkSave = By.xpath("//span[text()='Body']/following::button/span[text()='Save']");
	By remarkTableList = By.xpath("//h1[@title='Case Remarks']/following::table[1]/tbody/tr");
	By closeCaseRemarkTab = By.xpath("//button[@title='Close Case Remarks']");

	// Email Tab
	By additionalInfo = By.xpath("//a[@data-label='Additional Information']");
	// By emailLink = By.xpath("(//a[contains(@href,'dummy')])[2]");
	By emailLink = By
			.xpath("//span[text()='Email']/following::lightning-formatted-rich-text/span/a[contains(@href,'dummy')]");
	By emailTab = By.xpath("//span[@class='title' and (text()='Email')]");
	By emailCompose = By.xpath("//button[@title='Compose']");
	By emailTO = By.xpath("(//div[@class='standardField'])[1]//ul/li/input");
	By emailtemplate = By.xpath("(//div[@class='cuf-Icon'])[3]//lightning-primitive-icon");
	By insertTemplatebtn = By.xpath("//a[@title='Insert a template...']");
	By templateSearch = By.xpath("//span[@title='Search templates...']/following::input[1]");
	By genericTemplate = By.xpath(
			"//div[@class='runtime_sales_emailtemplateuiTemplateInsertDialog']//table/tbody/tr/th//button[text()='Generic']");
	By emailSend = By.xpath("//span[text()='Send']");
	By emailConfirmMsg = By.xpath("(//*[contains(text(),'Email sent')])[2]");
	By emailSubject = By.xpath("(//span[text()='Subject'])[1]/following::input[1]");
	By emailBody = By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']");
	By iframeEmailBody = By.xpath("//iframe[@title='Email Body']");
	By iframeParentEmailBody = By.xpath("//iframe[@title='CK Editor Container']");
	// Escalations
	By escalationTable = By.xpath("//h1[@title='Escalations']/following::table[1]/tbody/tr/th[1]/span/a");
	By escalate = By.xpath("//button[@name='Email_Reminder__c.Escalate']");
	By esactionLevel = By.xpath("//span[text()='Escalation Level']/following::lightning-formatted-number[1]");
	By closeEscalationsTab = By.xpath("//button[@title='Close Escalations']");
	By closeInfoActionTab = By.xpath("//span[contains(text(),'Close Request for Information/Action')]");

	// Merged Action tab
	By mergedAction = By.xpath("//span[text()='Merged Actions']");

	// RFI Tab Objects
	By rfiTab = By.xpath("//span[text()='RFI']");
	By rfiSubject = By.xpath("//span[text()='Subject']/following::input[1]");
	By rfiSendTo = By.xpath("//span[text()='Send to']/following::a[1]");
	By rfiSendToDestination = By.xpath("//a[@title='Destination']");
	By rfiQuestion = By.xpath("//span[text()='Question']/following::textarea[1]");
	By rfiSave = By.xpath("(//span[text()='Question']/following::span[text()='Save'])");
	By caseNo = By.xpath("(//span[@title='Case']/following::span[1])[1]");
	By rfiImage = By.xpath("//p[text()='RFI Status']/following::img[1]");
	By rfiTableSize = By.xpath("//h1[@title='Escalations']/following::table[1]/tbody/tr/th[1]/span/a");
	By respondername = By.xpath("//p[contains(text(),'Name')]/following::lightning-formatted-text[1]");
	By rfiAnswerEdit = By.xpath("//span[text()='Answer']/following::lightning-primitive-icon[1]");
	By rfiAnswer = By.xpath("//textarea[@name='Answer__c']");
	By rfiAnswerSave = By.xpath("//button[@name='submit']");
	By rfiNote = By.xpath("//img[@src='/resource/RFI_Status_Images/UnreadMessage.png']");

	// Case Dropdown Menu Page
	By caseDropDownMenuArrow = By
			.xpath("//button[@title='Select List View']//lightning-icon[@role='button']//lightning-primitive-icon");
	By myOpenCasesMenuOption = By.xpath("//span[contains(text(),'1 - My Open Cases')]");
	By caseTableSearchBox = By.xpath("//input[@name='Case-search-input']");
	By caseTableTickBox = By.xpath("(//span[@class='slds-checkbox--faux'])[2]");
	By mergedActionButton = By.xpath("//div[contains(text(),'Merged Action')]");
	By ukATDedicatedOne = By.xpath("//span[contains(text(),' - GH - Dedicated 1')]");
	By caseDropDownSearchBox = By.xpath("//input[@type='text']");
	By showMoreActionsDropDownMenu = By.xpath("//a[@title='Show 11 more actions']");
	By showMoreActionsAcceptButton = By.xpath("//div[@title='Accept']");
	By caseUpdatedTableText = By.xpath("//force-list-view-manager-status-info//*[contains(text(),'Updated')]");

	// CloseCase
	By closeTab = By.xpath("//span[text()='Close Case']");
	By commentsMsg = By.xpath("//textArea[@name='Comments']");
	By commentsSave = By.xpath("//button[@title='Perform Action']");
	By caseStatus = By.xpath("//p[@title='Status']/following::lightning-formatted-text[1]");
	By consumerType = By.xpath("//button[@name='Consumer_Type__c']");
	By bToC = By.xpath("//lightning-base-combobox-item[@data-value='BC – Business to Consumer']");
	By bToB = By.xpath("//lightning-base-combobox-item[@data-value='BB – Business to Business']");
	By unclearUnknown = By.xpath("//lightning-base-combobox-item[@data-value='Unclear / Unknown']");
	// SuccessMessage
	By successMessage = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']");
	By successMessage_CB = By.xpath("//*[@data-aura-class='forceActionsText' and contains(text(),'was saved')]");
	// error message for task escalation
	By error1 = By.xpath("//div[@class='toastContent slds-notify__content']//span");

	// Error Masg for Task escalation by OIB
	By taskEscErrorMessage = By.xpath("//span[contains(text(),'Task owner only escalate')]");
	By emailEscErrorMessage = By.xpath("//span[contains(text(),'Cannot be escalated')]");
	By taskEscDiffLocErrorMessage = By
			.xpath("//span[contains(text(),'It is not yet possible to escalate a Task to this location')]");

	// Transit Time
	By tabDropDown = By
			.xpath("//ul[@class='slds-button-group-list']//lightning-button-menu/button/lightning-primitive-icon");
	By transitTime = By.xpath("//span[contains(text(),'Transit Time')]");

	// Change Case Owner
	By changeOwner = By.xpath("//a[@name='ChangeOwnerOne']");
	By searchUser = By.xpath("//input[@placeholder='Search Users...']");
	By ChangeCaseOwnerPage = By.xpath("//h2[contains(text(),'Change Case Owner')]");
	By changeOwnerSubmit = By.xpath("//button[@title='Submit']");
	By ChangeCaseOwnerErrorMsg = By
			.xpath("//span[contains(text(),'Case can only be reassigned within your own Country/Territory/Function')]");

	// Invoice & rating
	By invoiceRatingbtn = By.xpath("//button[text()='Invoice & Rating']");

	By caseHeading = By.xpath("//img[@title='Case']/following::div[contains(text(),'Case')]");
	By caseMoreTab = By.xpath("(//a[@title='Post']/following::a[contains(text(),'More')])[1]");
	By reassign = By.xpath("//a[@title='Re-assign' and text()= 'Re-assign']");
	By reassignToDropDown = By.xpath("//input[@type='text' and @name='ReassignTO']");
	By reassignToDowndownValues = By
			.xpath("//input[@type='text' and @name='ReassignTO']/following::lightning-base-combobox-item/span[2]/span");
	By reassignToSaveBtn = By.xpath("//button[@title='Update' and text() = 'Save']");

	// CaseUpdate
	By caseUpdateClearMsg = By.xpath("//b[text()=\"The recent update on this Case is 'New Email'\"]");
	By caseUpdateClearBtnOnPopUp = By.xpath("//button[@title='OK']");
	By caseUpdateCancelBtnOnPopUp = By.xpath("//button[@title='Cancel']");

	// Integration Hidden
	By integrationHiddenField = By.xpath("//span[contains(text(),'Integration Fields - HIDDEN')]");

	// Case Columns
	By slaStatusColumn = By.xpath("//span[@title='SLA Status']");
	By customerAccountColumn = By.xpath("//span[@title='Customer Account']");
	By exceptionCodeColumn = By.xpath("//span[@title='Exception Code']");

	By rcReceiverCountry = By
			.xpath("//span[contains(text(),'Sender’s Country/Territory')]/following::lightning-formatted-lookup[2]/a");
	By oibEdit = By.xpath("//button[@title='Edit: OIB_Member__c']");
	By customerNameColumn = By.xpath("//span[text()='Customer Name']");

	By caseCreatedStatus = By.xpath("//lightning-formatted-text[contains(text(),'created')]");
	By caseCreatedIdentifier = By
			.xpath("(//span[@title='Case']/following::span[1])[1]/following::lightning-primitive-icon[1]");

	public CaseDetailsPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	// case summary tab
	public void clickCaseSummary() {
		WebElement casesummary = uiTestHelper.waitForObjectToBeClickable(casesummarytab);
		casesummary.click();
	}

	public Boolean isUpdatedConStatusDescDisplayed() {
		try {
			updateConDesc = By.xpath("//span[contains(text(),'Updated Consignment Status Description')]");
			WebElement updatecondesc = driver.findElement(updateConDesc);
			return updatecondesc.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public String getCountry(String requiredcountry) {
		String[] isoCountries = Locale.getISOCountries();
		String code = "";
		for (String country : isoCountries) {
			Locale locale = new Locale("", country);
			// String iso=locale.getISO3Country();
			// String code=locale.getCountry();
			String name = locale.getDisplayCountry();
			if (name.contains(requiredcountry)) {
				code = locale.getCountry();
				if (code.equals("GB"))
					code = "UK";
				break;
			}
		}
		return code;
	}

	// Case Details Tab
	public void clickCaseDetails() {
		WebElement casedetail = uiTestHelper.waitForObjectToBeClickable(casedetailstab);
		casedetail.click();
	}

	public Boolean isCmodCaseIdDisplayed() {
		WebElement cmodCaseID = uiTestHelper.waitForObject(cmodCaseIDNumber);
		boolean cmodCaseIdIsDisplayed = cmodCaseID.isDisplayed();
		return cmodCaseIdIsDisplayed;
	}

	public String getcaseOwnerValue() {
		WebElement COField = uiTestHelper.waitForObject(caseOwner);
		String COValue = COField.getText();
		return COValue;
	}

	public String getCMODTaskOwnerIdValue() {
		WebElement cmodCOField = uiTestHelper.waitForObject(taskOwnerIdValue);
		String taskCOid = cmodCOField.getText();
		return taskCOid;
	}

	// test
	public String getOIBCaseOwnerValue() {
		WebElement OIBCOField = uiTestHelper.waitForObject(oibCaseOwner);
		String OIBCOValue = OIBCOField.getText();
		return OIBCOValue;
	}

	public void sendCaseOIB(String oibName) {
		WebElement oibMember = uiTestHelper.waitForObject(caseOIB);
		oibMember.sendKeys(oibName);
	}

	public String getcaseOwnerCountry() {
		WebElement COCountryField = uiTestHelper.waitForObject(caseOwnerCountry);
		String COCountryValue = COCountryField.getText();
		return COCountryValue;
	}

	public String getcmodID() {
		WebElement cmodcaseidField = uiTestHelper.waitForObject(cmodcaseid);
		String cmodcaseid = cmodcaseidField.getText();
		return cmodcaseid;
	}

	public String getShipmentDirection() {
		WebElement shipDirField = uiTestHelper.waitForObject(shipmentDirection);
		String shipDirValue = shipDirField.getText();
		return shipDirValue;
	}

	public String getCustodialOwner() {
		WebElement custodialOwnerField = uiTestHelper.waitForObject(custodialOwner);
		String custodialOwnerValue = custodialOwnerField.getText();
		return custodialOwnerValue;
	}

	public String getOIBMember() {
		WebElement oibField = uiTestHelper.waitForObject(caseOIB);
		String OIBValue = oibField.getText();
		return OIBValue;
	}

	public String getOIBMemberQueueName() {
		WebElement caseOwnerOIBValue = uiTestHelper.waitForObject(caseOwnerOIB);
		String caseOwnerOIBVal = caseOwnerOIBValue.getText();
		return caseOwnerOIBVal;
	}

	public void setOIBMember(String oibName) {
		WebElement custodialOwnerField = uiTestHelper.waitForObject(oibMemberSearch);
		uiTestHelper.clickJS(custodialOwnerField);
		custodialOwnerField.sendKeys(oibName);
	}

	public void clickShowAllOIB() {
		WebElement showAll = uiTestHelper.waitForObject(oibShowAll);
		uiTestHelper.clickJS(showAll);
	}

	public void clickSaveOIB() {
		WebElement submitBtn = uiTestHelper.waitForObject(oibSubmit);
		uiTestHelper.clickJS(submitBtn);

	}

	public void clickCaseDtlsCancelBtn() {
		WebElement cancelBtn = uiTestHelper.waitForObject(caseDtlsCancelBtn);
		uiTestHelper.clickJS(cancelBtn);
	}

	public boolean showOIBSearchPeople() {
		WebElement showSearchPeople = uiTestHelper.waitForObject(oibSearch);
		boolean showSearchPeopleDisplayed = showSearchPeople.isDisplayed();
		return showSearchPeopleDisplayed;
	}

	public Boolean checkOIBCaseStatusIsPresent() {
		WebElement caseSubjectField = uiTestHelper.waitForObject(oibCaseSubject);
		return caseSubjectField.isDisplayed();
	}

	public boolean showOIBMemberName() {
		WebElement showoib = uiTestHelper.waitForObject(oibName);
		boolean showOib = showoib.isDisplayed();
		return showOib;
	}

	// Case Information Tab
	public void clickCaseInformation() {
		WebElement caseinfo = uiTestHelper.waitForObjectToBeClickable(caseinformationtab);
		uiTestHelper.clickJS(caseinfo);
	}

	public void clickEditStatus() {
		WebElement clickEditStatus = uiTestHelper.waitForObject(statusEdit);
		uiTestHelper.clickJS(clickEditStatus);
	}

	public String getcaseType() {
		WebElement casetypeField = uiTestHelper.waitForObject(caseType);
		String CasetypeValue = casetypeField.getText();
		return CasetypeValue;
	}

	public String getproActivecaseType() {
		WebElement casetypeField = uiTestHelper.waitForObject(proactiveCase);
		String CasetypeValue = casetypeField.getText();
		return CasetypeValue;
	}

	public String getCaseStatus() {
		WebElement casestatusField = uiTestHelper.waitForObject(caseStatus);
		String casestatusValue = casestatusField.getText();
		return casestatusValue;
	}

	public String getCasesSatusOnHighlightPanel() {
		WebElement casestatus = uiTestHelper.waitForObject(statusOnHighlightPanel);
		String casestatusVal = casestatus.getText();
		return casestatusVal;
	}

	public String getcontactName() {
		WebElement contactNameField = uiTestHelper.waitForObject(contactName);
		String conName = contactNameField.getText();
		return conName;
	}

	public String getcontactPhone() {
		WebElement contactPhoneField = uiTestHelper.waitForObject(contactPhone);
		String conPhoneValue = contactPhoneField.getText();
		return conPhoneValue;
	}

	public String getContactEmail() {
		WebElement contactemailField = uiTestHelper.waitForObject(contactemail);
		String contactemailValue = contactemailField.getText();
		return contactemailValue;
	}

	public String getcaseGroup() {
		WebElement casegroupField = uiTestHelper.waitForObject(casegroup);
		String casegroupValue = casegroupField.getText();
		return casegroupValue;
	}

	public String getRemark() {
		WebElement remark = uiTestHelper.waitForObject(custRemark);
		String customerRemark = remark.getText();
		return customerRemark;
	}

	public String getcaseReason() {
		WebElement casereasonField = uiTestHelper.waitForObject(casereason);
		String casereasonValue = casereasonField.getText();
		return casereasonValue;
	}

	public String getcaseReasonWithCmod() {
		WebElement casereasonField = uiTestHelper.waitForObject(casereasonwithCmod);
		String casereasonValue = casereasonField.getText();
		return casereasonValue;
	}

	public String getcaseCause() {
		WebElement causeField = uiTestHelper.waitForObject(cause);
		String causeFieldValue = causeField.getText();
		return causeFieldValue;
	}

	public String getcaseCausewithCmod() {
		uiTestHelper.scrollIntoView(causewithCmod);
		WebElement causeField = uiTestHelper.waitForObject(causewithCmod);
		String causeFieldValue = causeField.getText();
		return causeFieldValue;
	}

	public void clickOIBMember() {
		WebElement oibMember = uiTestHelper.waitForObject(caseOIBMemUpdate);
		uiTestHelper.clickJS(oibMember);
	}

	public void clickCOMember() {
		WebElement updateCO = uiTestHelper.waitForObject(caseOwner);
		uiTestHelper.clickJS(updateCO);
	}

	// Consignment Information Tab
	public void clickConsignmentInfo() {
		WebElement consignmentinfo = uiTestHelper.waitForObject(caseconsignmentinfotab);
		uiTestHelper.clickJS(consignmentinfo);
	}

	public String getconsignMentNo() {
		WebElement consignmentNoField = uiTestHelper.waitForObject(consignmentno);
		String conNumValue = consignmentNoField.getText();
		return conNumValue;
	}

	public String getCollectionDepot() {
		WebElement colldepotField = uiTestHelper.waitForObject(collectiondepot);
		String colldepotValue = colldepotField.getText();
		return colldepotValue;
	}

	public void clickCollectionDepot() {
		WebElement originDepot = uiTestHelper.waitForObject(originDepotLink);
		uiTestHelper.clickJS(originDepot);
	}

	public boolean verifyOriginCaseChkBoxIsChecked() {

		WebElement orgChkBx = uiTestHelper.waitForObjectToBeClickable(originCaseChkBox);
		return orgChkBx.isEnabled();
	}

	public boolean verifyDestinationCaseChkBoxIsChecked() {

		WebElement destChkBx = uiTestHelper.waitForObjectToBeClickable(destCaseChkBox);
		uiTestHelper.scrollIntoView(destChkBx);
		return destChkBx.isEnabled();
	}

	public String getDestinationDepot() {
		WebElement destinationDepotField = uiTestHelper.waitForObject(destinationdepot);
		String destdepotvalue = destinationDepotField.getText();
		return destdepotvalue;
	}

	public void clickDestinationDepot() {
		WebElement destinationDepot = uiTestHelper.waitForObject(destDepotLink);
		uiTestHelper.clickJS(destinationDepot);
	}

	// Sender and Receiver Tab
	public void clickPEMoreTab() {

		WebElement moretab = uiTestHelper.waitForObject(pemoreTabs);

		moretab.click();
	}

	public void clickMoreTab() throws Exception {
		By moreTabs;
		try {
			moreTabs = By.xpath("(//button[@title='More Tabs'])[2]");
			WebElement moretab = driver.findElement(moreTabs);
			moretab.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			moreTabs = By.xpath("//button[@title='More Tabs']");
			WebElement moretab = driver.findElement(moreTabs);
			moretab.click();
		}
	}

	public void clickSenderAndReceiverInfo() {
		WebElement sendandReceiverinfo = uiTestHelper.waitForObject(senderandreceiverinfotab);
		sendandReceiverinfo.click();
	}

	public String getsenderAccountNo() {
		WebElement senderAccNoField = uiTestHelper.waitForObject(senderAccountNo);
		String senAccNoValue = senderAccNoField.getText();
		return senAccNoValue;
	}

	public String getPEsenderAccountNo() {
		WebElement senderAccNoField = uiTestHelper.waitForObject(peSenderAccountNo);
		String senAccNoValue = senderAccNoField.getText();
		return senAccNoValue;
	}

	public String getPEreceiverAccountNo() {
		WebElement senderAccNoField = uiTestHelper.waitForObject(peReceiverAccountNo);
		String senAccNoValue = senderAccNoField.getText();
		return senAccNoValue;
	}

	public String getConsignmentNumber(String consignmentID) {
		WebElement validateCons = uiTestHelper
				.waitForObject(By.xpath("//span[contains(text(),'" + consignmentID + "')]"));
		String consignmentNumber = validateCons.getText();
		return consignmentNumber;
	}

	public String getsenderContact() {
		WebElement senderConatctField = uiTestHelper.waitForObject(sendercontactName);
		String senconValue = senderConatctField.getText();
		return senconValue;
	}

	public String getsenderCountry() {
		WebElement senderCountryField = uiTestHelper.waitForObject(senderCountry);
		String sencountryValue = senderCountryField.getText();
		return sencountryValue;
	}

	public String getPeSenderCountry() {
		WebElement peSenderCountryField = uiTestHelper.waitForObject(peSenderCountry);
		String sencountryValue = peSenderCountryField.getText();
		return sencountryValue;
	}

	public String getreceiverContact() {
		WebElement receiverConatctField = uiTestHelper.waitForObject(receivercontactName);
		String senconValue = receiverConatctField.getText();
		return senconValue;
	}

	public String getreceiverCountry() {
		WebElement receiverCountryField = uiTestHelper.waitForObject(receiverCountry);
		String sencountryValue = receiverCountryField.getText();
		return sencountryValue;
	}

	// Related Tab
	public void clickRelatedTab() {

		WebElement relatedTab = uiTestHelper.waitForObjectToBeClickable(relatedtab);
		clickJS(relatedTab);
	}

	public boolean verifyRelatedTab() {

		WebElement relatedTab = uiTestHelper.waitForObjectToBeClickable(relatedtab);
		return relatedTab.isDisplayed();
	}

	public void clickActionVew() {
		WebElement actView = uiTestHelper.waitForObject(actionsView);
		Actions act = new Actions(driver);
		act.click(actView).perform();
		// clickJS(actView);

	}

	public void clickCaseRemarkVew() {
		WebElement casermrkview = uiTestHelper.waitForObject(caseRemarView);
		uiTestHelper.clickJS(casermrkview);
	}

	public void clickTaskView() {
		WebElement taskview = uiTestHelper.waitForObject(taskView);
		uiTestHelper.clickJS(taskview);
	}
	// Monitor Activity

	public void clickMonitorActivity() {
		WebElement monitorActivitytab = uiTestHelper.waitForObjectToBeClickable(monitorActivity);
		uiTestHelper.clickJS(monitorActivitytab);
	}

	public void selectmonitorCODateFrom(String expectDate) {
//		WebElement dateFrom = uiTestHelper.waitForObject(monitorCODate);
//		uiTestHelper.clickJS(dateFrom);
//		By calBtn=By.xpath("//button[@title='Select a date for Date']");
//		WebElement dateFrom=uiTestHelper.waitForObject(calBtn);
//		uiTestHelper.clickJS(dateFrom);
//		By date = By.xpath("//table[@class='calGrid']/tbody/tr/td//span[contains(text(),'" + expectDate + "')]");
//		WebElement fromDate = uiTestHelper.waitForObject(date);
//		uiTestHelper.clickJS(fromDate);

		By dateField = By.xpath("(//input[@name='CO_Internal_Activity_SLA__c'])[1]");
		WebElement fromDate = uiTestHelper.waitForObject(dateField);
		fromDate.clear();
		fromDate.sendKeys(expectDate);
	}

	public String isMonitoring() {
		WebElement monitoring = uiTestHelper.waitForObject(monitorORcallback);
		monitoring.getText();
		return monitoring.getText();
	}

	public void clickMonitorTime() {
		WebElement monitortime = uiTestHelper.waitForObject(monitorTime);
		uiTestHelper.clickJS(monitortime);
	}

	public void setMonitorTimeInput(String time) {
		WebElement monitortimeField = uiTestHelper.waitForObject(monitorTimeInput);
		uiTestHelper.clickJS(monitortimeField);
		String b = Keys.BACK_SPACE.toString();
		monitortimeField.sendKeys(b + b + b + b + b + time);
		monitortimeField.sendKeys(Keys.TAB);
		// monitortimeField.sendKeys(time);
	}

	public void clickMonitorActivitysave() {
		WebElement activitysave = uiTestHelper.waitForObject(saveMonitorActivity);
		uiTestHelper.clickJS(activitysave);
	}

	public String getSLADateTime() {
		WebElement sladateTime = uiTestHelper.waitForObject(sladatetime);
		String datetime = sladateTime.getText();
		return datetime;
	}

//	public boolean getgreenGIF() {
//		WebElement greenimage = uiTestHelper.waitForObject(greengif);
//		boolean imagedisplayed = greenimage.isDisplayed();
//		return imagedisplayed;
//	}

	public boolean getgreenGIF() throws Exception {
		try {
			driver.findElement(greengif);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean getyellowGIF() throws Exception {
		try {
			driver.findElement(yellowgif);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean getredGIF() {
		WebElement image = uiTestHelper.waitForObject(redgif);
		boolean imagedisplayed = image.isDisplayed();
		return imagedisplayed;
	}

//	public boolean getyellowGIF() {
//		WebElement image = uiTestHelper.waitForObject(yellowgif);
//		boolean imagedisplayed = image.isDisplayed();
//		return imagedisplayed;
//	}

	public void clickmonitorcheckbox() {
		// WebElement checkbox = uiTestHelper.waitForObject(monitorcheckbox);
		WebElement checkbox = driver
				.findElement(By.xpath("//span[contains(text(),'CO - Monitoring Completed')]/following::input[1]"));
		// checkbox.click();
		uiTestHelper.clickJS(checkbox);
	}

	public boolean ismonitorcheckbox() {
		// WebElement checkbox = uiTestHelper.waitForObject(monitorcheckbox);
		WebElement checkbox = driver
				.findElement(By.xpath("//span[contains(text(),'CO - Monitoring Completed')]/following::input[1]"));
		return checkbox.isSelected();
	}
	// callback Activity

	public void clickcallbackActivity() {
		WebElement callbackActivitytab = uiTestHelper.waitForObjectToBeClickable(callbacktab);
		uiTestHelper.clickJS(callbackActivitytab);
	}

	public void selectcallbackCODateFrom(String expectDate) {
//		WebElement dateFrom = uiTestHelper.waitForObject(callbackdate);
//		uiTestHelper.clickJS(dateFrom);
//		By date = By.xpath("//table[@class='calGrid']/tbody/tr/td//span[contains(text(),'" + expectDate + "')]");
//		WebElement fromDate = uiTestHelper.waitForObject(date);
//		uiTestHelper.clickJS(fromDate);
		By dateField = By.xpath("//span[contains(text(),'CO - Next Call Back')]/following::input[1]");
		WebElement fromDate = uiTestHelper.waitForObject(dateField);
		fromDate.clear();
		fromDate.sendKeys(expectDate);
	}

	// bulkActions X104OKL 9-8-21
	public void selectcallbackCODateFromBulkActions(String expectDate) {
		WebElement dateFrom = uiTestHelper.waitForObject(callbackdate);
		uiTestHelper.clickJS(dateFrom);
		driver.findElement(By.xpath("//a[@title='Go to next month']")).click();
		By date = By.xpath("//table[@class='calGrid']/tbody/tr/td//span[contains(text(),'" + expectDate + "')]");
		WebElement fromDate = uiTestHelper.waitForObject(date);
		uiTestHelper.clickJS(fromDate);
	}

	public void setcallbackTimeInput(String time) {
		WebElement callbacktimeField = uiTestHelper.waitForObject(callbackTime);
		uiTestHelper.clickJS(callbacktimeField);
		callbacktimeField.clear();
		String b = Keys.BACK_SPACE.toString();
		callbacktimeField.sendKeys(b + b + b + b + b + time);
		callbacktimeField.sendKeys(Keys.TAB);
		// callbacktimeField.sendKeys(time);
	}

	public void clickcallbackActivitysave() {
		WebElement activitysave = uiTestHelper.waitForObject(callbacksave);
		uiTestHelper.clickJS(activitysave);
	}

	public void clickcallbackcheckbox() {
		WebElement checkbox = uiTestHelper.waitForObject(callbackcheckbox);
		uiTestHelper.clickJS(checkbox);
	}

	public boolean iscallbackcheckbox() {
		WebElement checkbox = uiTestHelper.waitForObject(callbackcheckbox);
		return checkbox.isSelected();
	}

	// Task

	public void clicktaskTab() {
		WebElement tasktab = uiTestHelper.waitForObject(taskTab);
		uiTestHelper.clickJS(tasktab);
	}

	public int getTaskTableSize1() {
		List<WebElement> taskTableSize = uiTestHelper.waitForObjects(taskTableList);
		int size = taskTableSize.size();
		clicktaskTable1(size);
		return size;
	}

	public void clicktaskTable1(int Size) {
		By tasktableid = By.xpath("//h1[text()='Tasks']/following::table[1]/tbody/tr[" + Size + "]/th/span/a");
		WebElement taskid = uiTestHelper.waitForObject(tasktableid);
		uiTestHelper.scrollIntoView(taskid);
		taskid.click();
	}

	public void clickCreatedTaskLink() {
		WebElement createdTask = uiTestHelper.waitForObject(createdTaskLink);
		uiTestHelper.clickJS(createdTask);
	}

	public void clickCreatedTaskLink1(int recenttask) {
		By latestTask = By.xpath("//h1[text()='Tasks']/following::table[1]/tbody/tr[" + recenttask + "]/th/span");
		WebElement createdTask = uiTestHelper.waitForObject(latestTask);
		uiTestHelper.clickJS(createdTask);
	}

	public String getTaskOwnerIDValue() {
		WebElement taskOwnerOField = uiTestHelper.waitForObject(taskOwnerId);
		String taskOwnerOVa = taskOwnerOField.getText();
		return taskOwnerOVa;
	}

	public boolean showCreatedTask() {
		WebElement showtask = uiTestHelper.waitForObject(createdTaskLink);
		boolean showtasklinkDisplayed = showtask.isDisplayed();
		return showtasklinkDisplayed;
	}

	public void clickEscalateTaskBtn() {
		WebElement escBtn = uiTestHelper.waitForObject(escalateTask);
		uiTestHelper.clickJS(escBtn);
	}

	public void clickEditTaskBtn() {
		WebElement editBtn = uiTestHelper.waitForObject(editTask);
		uiTestHelper.clickJS(editBtn);
	}

	public void clickCouTerrField() {
		WebElement CouTerrField = uiTestHelper.waitForObject(couTerritory);
		uiTestHelper.clickJS(CouTerrField);
	}

	public void clickTNTLocationField() {
		WebElement tntLocationField = uiTestHelper.waitForObject(tntLocation);
		uiTestHelper.clickJS(tntLocationField);
	}

	public String getCouTerrValue() {
		WebElement couTer = uiTestHelper.waitForObject(couTerritory);
		String couTerValue = couTer.getText();
		return couTerValue;
	}

	public String getTNTLocationValue() {
		WebElement tntLoc = uiTestHelper.waitForObject(tntLocation);
		String tntLocAValue = tntLoc.getText();
		return tntLocAValue;
	}

	public void settaskCountry(String country) {
		WebElement taskcountry = uiTestHelper.waitForObject(taskCountry);
		taskcountry.click();
		// taskcountry.clear();
		taskcountry.sendKeys(country);
		taskcountry.sendKeys(Keys.ENTER);
		driver.findElement(
				By.xpath("//*[@title='" + country + "']/following::span[1]/lightning-base-combobox-formatted-text"))
				.click();
	}

	public String getEscalationSLAValue() {
		WebElement escSLA = uiTestHelper.waitForObject(escalationSLA);
		String escSLAValue = escSLA.getText();
		return escSLAValue;
	}

	public boolean showEscalationSLAValue() {
		WebElement showtaskesc = uiTestHelper.waitForObject(escalationSLA);
		boolean showtaskEscDisplayed = showtaskesc.isDisplayed();
		return showtaskEscDisplayed;
	}

	public void clickEscReason() {
		WebElement escReasonCombobox = uiTestHelper.waitForObject(escReason);
		clickJS(escReasonCombobox);
	}

	public int escSLATime() {
		By escSLAtime = By
				.xpath("//span[contains(text(),'Escalation SLA (Date/Time)')]/following::lightning-formatted-text[1]");
		WebElement escSLAtime1 = uiTestHelper.waitForObject(escSLAtime);
		String slaTime = escSLAtime1.getText();
		if (slaTime.length() > 16) {
			return Integer.parseInt(((slaTime.substring(9, slaTime.length() - 3)).replace(":", "").replace(" ", "")));
		} else {
			return Integer.parseInt(((slaTime.substring(11, slaTime.length())).replace(":", "").replace(" ", "")));
		}

	}

	public int systemCurrentTimeEUMadrid() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		String slaTime = df.format(date);
		return Integer.parseInt(((slaTime.substring(11, slaTime.length())).replace(":", "")));
	}

	public int systemCurrentTimeUTC() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		String slaTime = df.format(date);
		return Integer.parseInt(((slaTime.substring(11, slaTime.length())).replace(":", "")));
	}

	public void clickEscReasonVal() {
		WebElement escReasonComboboxVal = uiTestHelper.waitForObject(escReasonVal);
		// clickJS(escReasonComboboxVal);
		escReasonComboboxVal.click();
	}

	public void inputEscalationComments(String comments) {
		WebElement escComm = uiTestHelper.waitForObject(escComments);
		// clickJS(escComm);
		escComm.sendKeys(Keys.CLEAR);
		escComm.sendKeys(comments);
		escComm.sendKeys(Keys.TAB);
	}

	public void clickTascEscSaveBtn() {
		WebElement escSave = uiTestHelper.waitForObject(taskEscSave);
		clickJS(escSave);
	}

	public void clickTascEscCancelBtn() throws Exception {
		try {
			By taskEscCancel = By.xpath("//button[@name='Cancel']");
			WebElement escCancel = driver.findElement(taskEscCancel);
			clickJS(escCancel);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Exception: " + e);
		}
	}

	// caseTableSearchBox
	public void searchCaseNumberInSearchBox(String caseNumber) {
		WebElement searchResult = uiTestHelper.waitForObjectToBeClickable(caseTableSearchBox);
		searchResult.click();
		searchResult.sendKeys(caseNumber);
		searchResult.sendKeys(Keys.ENTER);
	}

	public void selectCaseTickBoxInTable(String caseID) {
		uiTestHelper
				.waitForObjectToBeClickable(By.xpath("//a[@title='" + caseID + "']/preceding::td//span//span//label"))
				.click();
	}

	public void clickMergedActionButton() {
		uiTestHelper.waitForObject(mergedActionButton).click();
	}

	public void clickrouteTO() {
		WebElement routeto = uiTestHelper.waitForObject(routeTo);
		uiTestHelper.clickJS(routeto);
	}

	public void clickroutetoTNTops() {
		WebElement routetotntops = uiTestHelper.waitForObject(routetoTNTops);
		uiTestHelper.clickJS(routetotntops);
	}

	public void setTaskInstruction(String desc) {
		WebElement taskintro = uiTestHelper.waitForObject(taskInstruction);
		uiTestHelper.clickJS(taskintro);
		taskintro.sendKeys(desc);
	}

	public void settaskdate(String date) {
		WebElement task = uiTestHelper.waitForObject(taskdate);
		uiTestHelper.clickJS(task);
		task.sendKeys(date);
	}

	public void settasktime(String time) {
		WebElement task = uiTestHelper.waitForObject(tasktime);
		uiTestHelper.clickJS(task);
		task.clear();
		task.sendKeys(time);
	}

	public void settaskLocation(String location) {
		WebElement taskloc = uiTestHelper.waitForObject(taskLocation);
		uiTestHelper.clickJS(taskloc);
		taskloc.sendKeys(location);
		WebElement loc = uiTestHelper.waitForObject(
				By.xpath("//span[@class='slds-listbox__option-text slds-listbox__option-text_entity' and (text()='"
						+ location + "')]"));
		loc.click();
	}

	public void clicktaskCreate() {
		WebElement taskcreate = uiTestHelper.waitForObject(createTask);
		uiTestHelper.clickJS(taskcreate);
	}

	public void clickCasesDropDownMenu() {
		WebElement menu = uiTestHelper.waitForObject(caseDropDownMenuArrow);
		uiTestHelper.clickJS(menu);
	}

	public void clickMyOpenCasesFromDropDownMenu() {
		WebElement menuItem = uiTestHelper.waitForObject(myOpenCasesMenuOption);
		uiTestHelper.clickJS(menuItem);
	}

	public void clickUKATDedicatedOneOption() {
		WebElement menuItem = uiTestHelper.waitForObject(ukATDedicatedOne);
		uiTestHelper.clickJS(menuItem);
	}

	public void selectCasesListViewAndSendKeys() {
		WebElement menuItem = uiTestHelper.waitForObject(caseDropDownSearchBox);
		menuItem.click();
		menuItem.sendKeys("UK - GH - Dedicated 1");
		By searchBarResult = By.xpath("//mark[contains(text(),'UK - GH - Dedicated 1')]");
		WebElement ukGHDedicatedOne = uiTestHelper.waitForObject(searchBarResult);
		ukGHDedicatedOne.click();
	}

	/*
	 * public void waitForCaseTableToLoad() {
	 * 
	 * Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	 * .withTimeout(30,TimeUnit.SECONDS) .pollingEvery(5,TimeUnit.SECONDS)
	 * .ignoring(NoSuchElementException.class);
	 * 
	 * WebElement waitForText = wait.until(new Function<WebDriver, WebElement>() {
	 * public WebElement apply(WebDriver driver) { return
	 * driver.findElement(caseUpdatedTableText); } }); }
	 */
	public void selectInboundACMTaskFromTable(String caseNum) {
		By tableSearchConsingment = By.xpath("//a[@title='" + caseNum + "']/preceding::td//span//span//label");
		WebElement searchResult = uiTestHelper.waitForObject(tableSearchConsingment);
		searchResult.click();
	}

	public void clickMoreActionsDropDownMenu() {
		WebElement menuItem = uiTestHelper.waitForObject(showMoreActionsDropDownMenu);
		uiTestHelper.clickJS(menuItem);
	}

	public void clickAcceptMenuItem() {
		WebElement menuItem = uiTestHelper.waitForObject(showMoreActionsAcceptButton);
		uiTestHelper.clickJS(menuItem);
	}

	public void clickCountryLookup() {
		WebElement countrylookup = uiTestHelper.waitForObject(countryLookup);
		countrylookup.click();
	}

	public int getTaskTableSize() {
		List<WebElement> taskTableSize = uiTestHelper.waitForObjects(taskTableList);
		int size = taskTableSize.size();
		return size;
	}

	public void clicktaskTable(int Size) {
		By tasktableid = By.xpath("//h1[text()='Tasks']/following::table[1]/tbody/tr['" + Size + "']/th/span/a");
		WebElement taskid = uiTestHelper.waitForObject(tasktableid);
		uiTestHelper.scrollIntoView(taskid);
		taskid.click();
	}

	public String getCmodTaskId() throws Exception {
		try {
			By cmodTaskId1 = By.xpath("//p[text()='CMOD Task Id']/following::lightning-formatted-text[1]");
			WebElement cmodtaskid = driver.findElement(cmodTaskId1);
			// WebElement cmodtaskid = uiTestHelper.waitForObject(cmodTaskId1);
			String id = cmodtaskid.getText();
			return id;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return "";
		}

	}

	public boolean getFedExLocation(String fxLocation) {
		try {
			driver.findElement(By.xpath(
					"//span[contains(text(),'FedEx Location')]/following::lightning-formatted-text[contains(text(),'"
							+ fxLocation + "')]"));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	// Email
	public void clickAdditionalInfo() {
		WebElement additionalinfo = uiTestHelper.waitForObject(additionalInfo);
		additionalinfo.click();
	}

	public String getEmail() {
		WebElement additionalinfo = uiTestHelper.waitForObject(emailLink);
		String emailtext = additionalinfo.getText();
		return emailtext;
	}

	public void clickEmailTab() {
		WebElement emailtab = uiTestHelper.waitForObject(emailTab);
		uiTestHelper.clickJS(emailtab);
	}

	public void clickCompose() {
		WebElement emailcompose = uiTestHelper.waitForObject(emailCompose);
		uiTestHelper.clickJS(emailcompose);
	}

	public void setEmailTo(String mailid) {
		WebElement emailto = uiTestHelper.waitForObject(emailTO);
		emailto.sendKeys(mailid);
	}

	public void clickTemplate() {
		WebElement template = uiTestHelper.waitForObject(emailtemplate);
		uiTestHelper.scrollIntoView(template);
		template.click();
	}

	public void insertTemplatebtn() {
		WebElement inserttemplate = uiTestHelper.waitForObject(insertTemplatebtn);
		uiTestHelper.scrollIntoView(inserttemplate);
		inserttemplate.click();
	}

	public void selectTemplate(String templateName) {
		WebElement selectTemplate = uiTestHelper.waitForObject(templateSearch);
		selectTemplate.sendKeys(templateName, Keys.ENTER);
	}

	public void selectTemplateType(String templateType) {
		Select templatetype = new Select(
				driver.findElement(By.xpath("//span[@title='Templates']/following::select[1]")));
		templatetype.selectByVisibleText(templateType);
	}

	public void clickGenericTemplate() {
		WebElement clickGenericTemplate = uiTestHelper.waitForObject(genericTemplate);
		uiTestHelper.clickJS(clickGenericTemplate);
	}

	public void clickSendBtn() {
		WebElement sendbtn = uiTestHelper.waitForObjectToBeClickable(emailSend);
		uiTestHelper.clickJS(sendbtn);
	}

	public void setEmailSubject(String mailSubject) {
		WebElement emailSub = uiTestHelper.waitForObject(emailSubject);
		emailSub.clear();
		emailSub.sendKeys(mailSubject);
	}

	public void switchToFrameEmailBody() {
		uiTestHelper.waitForObject(iframeEmailBody);
		WebElement frame = uiTestHelper.waitForObject(iframeEmailBody);
		driver.switchTo().frame(frame);
	}

	public void switchToParentFrameEmailBody() {
		uiTestHelper.waitForObject(iframeParentEmailBody);
		WebElement pframe = uiTestHelper.waitForObject(iframeParentEmailBody);
		driver.switchTo().frame(pframe);
	}

	public void setEmailbody(String emailbody) {
		WebElement inputEmailbody = uiTestHelper.waitForObject(emailBody);
		inputEmailbody.sendKeys(emailbody);
	}

	public String getEmailConfirmMessage() {
		WebElement confirmMsg = uiTestHelper.waitForObject(emailConfirmMsg);
		String emailConfirmMessage = confirmMsg.getText();
		return emailConfirmMessage;
	}

	// Close Case
	public void clickCloseCaseTab() {
		WebElement closecase = uiTestHelper.waitForObject(closeTab);
		uiTestHelper.clickJS(closecase);
	}

	public void clickConsumerType() {
		WebElement consumerTypeDrpDwn = uiTestHelper.waitForObject(consumerType);
		uiTestHelper.clickJS(consumerTypeDrpDwn);
	}

	public void clickBToB() {
		WebElement clickBToB = uiTestHelper.waitForObject(bToB);
		uiTestHelper.clickJS(clickBToB);
	}

	public boolean showBToB() {
		WebElement showBToB = uiTestHelper.waitForObject(bToB);
		boolean showBToBDisplayed = showBToB.isDisplayed();
		return showBToBDisplayed;
	}

	public boolean showBToC() {
		WebElement showBToC = uiTestHelper.waitForObject(bToC);
		boolean showBToCDisplayed = showBToC.isDisplayed();
		return showBToCDisplayed;
	}

	public boolean showUnclearUnknown() {
		WebElement showUnclearUnknown = uiTestHelper.waitForObject(unclearUnknown);
		boolean showUnclearUnknownDisplayed = showUnclearUnknown.isDisplayed();
		return showUnclearUnknownDisplayed;
	}

	public void clickCloseCaseSave() {
		WebElement save = uiTestHelper.waitForObject(commentsSave);
		uiTestHelper.clickJS(save);
	}

	public void setComments(String Comments) {
		WebElement commentsInput = uiTestHelper.waitForObject(commentsMsg);
		commentsInput.sendKeys(Comments);
	}

	// RFI
	public void clickRFITab() {
		WebElement rfitab = uiTestHelper.waitForObjectToBeClickable(rfiTab);
		uiTestHelper.clickJS(rfitab);
	}

	public void clickRFISendTobtn() {
		WebElement rfisendto = uiTestHelper.waitForObject(rfiSendTo);
		uiTestHelper.clickJS(rfisendto);
	}

	public void setRFISendTo() {
		WebElement rfisendtodest = uiTestHelper.waitForObject(rfiSendToDestination);
		uiTestHelper.scrollIntoView(rfisendtodest);
		rfisendtodest.click();
	}

	public void setRFISubject(String Subject) {
		WebElement rfisubject = uiTestHelper.waitForObject(rfiSubject);
		rfisubject.sendKeys(Subject);
	}

	public void setRFIQuestion(String Question) {
		WebElement rfiquestion = uiTestHelper.waitForObject(rfiQuestion);
		rfiquestion.sendKeys(Question);
	}

	public void clickRFISave() {
		WebElement rfisave = uiTestHelper.waitForObject(rfiSave);
		uiTestHelper.clickJS(rfisave);
	}

	public String getCaseNo() {
		WebElement caseno = uiTestHelper.waitForObject(caseNo);
		String caseNumber = caseno.getText();
		return caseNumber;
	}

	public boolean getRFIImage() {
		WebElement rfiimage = uiTestHelper.waitForObject(rfiImage);
		boolean img = rfiimage.isDisplayed();
		return img;
	}

	public void clickRFIAnswerEdit() {
		WebElement edit = uiTestHelper.waitForObject(rfiAnswerEdit);
		uiTestHelper.clickJS(edit);
	}

	public void setRFIAnswer(String Answer) {
		WebElement answer = uiTestHelper.waitForObject(rfiAnswer);
		answer.sendKeys(Answer);
	}

	public void clickRFIAnswerSubmit() {
		WebElement submit = uiTestHelper.waitForObject(rfiAnswerSave);
		uiTestHelper.clickJS(submit);
	}

	public boolean getRFINoteImage() {
		WebElement rfiimage = uiTestHelper.waitForObject(rfiNote);
		boolean img = rfiimage.isDisplayed();
		return img;

	}

	// Escalation
	public void clickEscalation() {
		WebElement escalation = uiTestHelper.waitForObjectToBeClickable(escalationView);
		uiTestHelper.clickJS(escalation);
	}

	public void clickEscalationTableLink() {
		WebElement esctable = uiTestHelper.waitForObject(escalationTable);
		uiTestHelper.clickJS(esctable);
	}

	public void clickescalatebtn() {
		WebElement esctable = uiTestHelper.waitForObject(escalate);
		uiTestHelper.clickJS(esctable);
	}

	public String getescalationLevel() {
		WebElement esclevel = uiTestHelper.waitForObject(esactionLevel);
		String escalationLevel = esclevel.getText();
		return escalationLevel;
	}

	public int getEscalationTableSize() {
		List<WebElement> TableSize = uiTestHelper.waitForObjects(escalationTable);
		int size = TableSize.size();
		return size;
	}

	public void clickescalationTable(int Size, String Text) {
		By tableid = By.xpath("//h1[@title='Escalations']/following::table[1]/tbody/tr['" + Size
				+ "']/th[1]/span/a[contains(text(),'" + Text + "')]");
		WebElement id = uiTestHelper.waitForObject(tableid);
		uiTestHelper.scrollIntoView(id);
		id.click();
	}

	public String getresponderName() {
		WebElement responder = uiTestHelper.waitForObject(respondername);
		String responderName = responder.getText();
		return responderName;
	}

	// Case Remark
	public void clickCaseRemarkTab() {
		WebElement CaseRemark = uiTestHelper.waitForObject(caseRemarkTab);
		uiTestHelper.clickJS(CaseRemark);
	}

	public void clickCaseRemarkSave() {

		WebElement CaseRemarksave = uiTestHelper.waitForObject(caseRemarkSave);
		doubleClick(CaseRemarksave);
	}

	public void setCaseRemarkdesc(String desc) {
		WebElement CaseRemarkdesc = uiTestHelper.waitForObject(caseremarkDescription);
		CaseRemarkdesc.clear();
		Actions act1 = new Actions(driver);
		act1.moveToElement(CaseRemarkdesc).doubleClick().sendKeys("Testing case remarks").build().perform();
	}

	public String getcaseRemarkCreatedTime() {
		List<WebElement> tableSize = uiTestHelper.waitForObjects(remarkTableList);
		int size = tableSize.size();
		WebElement casermktime = uiTestHelper.waitForObject(
				By.xpath("//h1[@title='Case Remarks']/following::table[1]/tbody/tr['" + size + "']/td[2]/span/span"));
		String time = casermktime.getText();
		return time;
	}

	public String getCaseRemarkComments() {
		List<WebElement> tableSize = uiTestHelper.waitForObjects(remarkTableList);
		int size = tableSize.size();
		WebElement casermktime = uiTestHelper.waitForObject(
				By.xpath("//h1[@title='Case Remarks']/following::table[1]/tbody/tr['" + size + "']/td[3]/span/span"));
		String time = casermktime.getText();
		return time;
	}

	// Customer Remark
	public void clickCustRemarkTab() {
		WebElement custRemark = uiTestHelper.waitForObject(customerRemarkTab);
		uiTestHelper.clickJS(custRemark);
	}

	public void clickCustRemarkSave() {
		WebElement custRemarksave = uiTestHelper.waitForObject(custRemarkSave);
		uiTestHelper.clickJS(custRemarksave);
	}

	public void setCustRemarkdesc(String desc) {
		WebElement custRemarkdesc = uiTestHelper.waitForObject(custremarkDescription);
		custRemarkdesc.sendKeys(desc);
		custRemarkdesc.sendKeys(Keys.TAB);
	}

	// Action
	public void clickActionTab() {
		WebElement acttab = uiTestHelper.waitForObject(actionTab);
		uiTestHelper.clickJS(acttab);
	}

	public void setActionType(String actionType) {
		WebElement actiontypeField = uiTestHelper.waitForObject(actionTypeDropDown);
		uiTestHelper.clickJS(actiontypeField);
		By actionlist = By.xpath("//div[@class='select-options']/following::ul[@role='presentation'][6]/li/a[text()='"
				+ actionType + "']");
		WebElement actList = uiTestHelper.waitForObject(actionlist);
		uiTestHelper.clickJS(actList);
	}

	public void setActionType_BulkAction(String actionType) {
		WebElement actiontypeField = uiTestHelper.waitForObject(actionTypeDropDown);
		uiTestHelper.clickJS(actiontypeField);
		By actionlist = By.xpath("//a[@title='" + actionType + "']");
		WebElement actList = uiTestHelper.waitForObject(actionlist);
		uiTestHelper.clickJS(actList);
	}

	public void clickActionSave() {
		WebElement actsave = uiTestHelper.waitForObject(actionSave);
		uiTestHelper.clickJS(actsave);
	}

	public void setActionCost(String Cost) {
		WebElement actcost = uiTestHelper.waitForObject(actionCost);
		actcost.sendKeys(Cost);
		actcost.sendKeys(Keys.TAB);
	}

	public boolean actionTableView() {
		WebElement actTable = uiTestHelper.waitForObject(actionTable);
		boolean table = actTable.isDisplayed();
		return table;
	}

	public int getSize() {
		List<WebElement> actTableSize = uiTestHelper.waitForObjects(actionTableList);
		int size = actTableSize.size();
		return size;
	}

	public String getactionTableCost(int Size) {
		By actionTableCost = By
				.xpath("//h1[@title='Actions']/following::table[1]/tbody/tr[" + Size + "]/td[3]/span/span");
		// "//h1[@title='Actions']/following::table[1]/tbody/tr[" + Size +
		// "]/td[3]/span/span");
		WebElement actTableCost = uiTestHelper.waitForObject(actionTableCost);
		uiTestHelper.scrollIntoView(actTableCost);
		String cost = actTableCost.getText();
		return cost;
	}

	public boolean verifySuccessMessage() {
		try {
			By successMsg = By.xpath("//*[@data-aura-class='forceToastMessage' and @data-key='success']");
			WebElement msg = driver.findElement(successMsg);
			return msg.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public String getErrorMessage() {
		WebElement Errmsg = uiTestHelper.waitForObject(error1);
		return Errmsg.getText();
	}

	// verify task level
	public String getTaskLevel() throws Exception {
		try {
			By tasklevelxpath = By
					.xpath("//span[contains(text(),'Escalation Level')]/following::div[1]/lightning-formatted-text");
			WebElement tasklevel = driver.findElement(tasklevelxpath);
			String level = tasklevel.getText();
			return level;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return "";
		}

	}

	public boolean verifySuccessMessage_CB_Mon() {
		WebElement msg = uiTestHelper.waitForObject(successMessage_CB);
		return msg.isDisplayed();
	}

	public boolean verifyTaskEscFailbyOIBMessage() {
		WebElement errMsg = uiTestHelper.waitForObject(taskEscErrorMessage);
		return errMsg.isDisplayed();
	}

	public boolean verifyTaskEscFailbyDiffLocBMessage() {
		WebElement errMsgEscFailed = uiTestHelper.waitForObject(taskEscDiffLocErrorMessage);
		return errMsgEscFailed.isDisplayed();
	}

	public boolean verifyEmailEscalationMessage() {
		WebElement errMsg = uiTestHelper.waitForObject(emailEscErrorMessage);
		return errMsg.isDisplayed();
	}

	public void clickTransitTime() {
		WebElement dropdown = uiTestHelper.waitForObjectToBeClickable(tabDropDown);
		uiTestHelper.clickJS(dropdown);
		WebElement transittime = uiTestHelper.waitForObjectToBeClickable(transitTime);
		uiTestHelper.clickJS(transittime);
	}

	public void clickChangeOwner() {
		WebElement dropdown = uiTestHelper.waitForObject(tabDropDown);
		dropdown.click();
		WebElement changeOwnerBtn = uiTestHelper.waitForObject(changeOwner);
		changeOwnerBtn.click();
	}

	public void setSearchUserinChangeOwner(String userName) {
		WebElement changeOwnerPage = uiTestHelper.waitForObject(ChangeCaseOwnerPage);
		WebElement searchUserInput = uiTestHelper.waitForObject(searchUser);
		uiTestHelper.clickJS(searchUserInput);
		searchUserInput.sendKeys(userName);
		WebElement inputUser = uiTestHelper.waitForObject((By.xpath("//mark[contains(text(),'Automation')]")));
		inputUser.click();
	}

	public void clickChangeOwnerSubmitBtn() {
		WebElement submitBtn = uiTestHelper.waitForObjectToBeClickable(changeOwnerSubmit);
		uiTestHelper.clickJS(submitBtn);
	}

	public Boolean isChangeOwnerErrorMsgDisplayed() {
		WebElement ChangeCaseOwnerErrorMsgDisp = uiTestHelper.waitForObject(ChangeCaseOwnerErrorMsg);
		boolean isChangeOwnerErrorMsgDisplayed = ChangeCaseOwnerErrorMsgDisp.isDisplayed();
		return isChangeOwnerErrorMsgDisplayed;
	}

	public void clickInvoiceRating() {
		WebElement button = uiTestHelper.waitForObject(invoiceRatingbtn);
		button.click();
	}

	public boolean verifyInvoiceRating() {
		WebElement button = uiTestHelper.waitForObject(invoiceRatingbtn);
		return button.isDisplayed();
	}

	// Scroll USing JavaScript Executor
	public void scrollIntoView(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", ele);
	}

	// Click USing JavaScript Executor
	public void clickJS(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ele);
	}

	public void doubleClick(WebElement ele) {
		Actions act = new Actions(driver);

		// Double click on element

		act.doubleClick(ele).perform();
	}

	public String getSystemDate(String format) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String exp_date = sdf.format(currentDateTime);
		calendar.setTime(sdf.parse(exp_date));
		calendar.add(Calendar.DATE, 1); // number of days to add
		exp_date = sdf.format(calendar.getTime());
		System.out.println("Expected Date:" + exp_date);
		return exp_date;
	}

	public String getSystemTime() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));

		cal.add(Calendar.MINUTE, 40);

		return dateFormat.format(cal.getTime());
	}

	public String getSystemMonth() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("DD/MM/yyyy");
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}

	public boolean isCaseDisplayed() {
		WebElement caseDetailPage = uiTestHelper.waitForObject(caseHeading);
		return caseDetailPage.isDisplayed();
	}

	public void clickCaseMoreTab() {
		WebElement caseMoreTabelement = uiTestHelper.waitForObject(caseMoreTab);
		uiTestHelper.clickJS(caseMoreTabelement);
	}

	public boolean isReassignDisplayed() {
		WebElement reassignTab = uiTestHelper.waitForObject(reassign);
		return reassignTab.isDisplayed();
	}

	public void clickReassign() {
		WebElement reassignTab = uiTestHelper.waitForObject(reassign);
		uiTestHelper.clickJS(reassignTab);
	}

	public List<WebElement> clickReassignDropdown() {
		WebElement reassignToDropDownElement = uiTestHelper.waitForObject(reassignToDropDown);
		reassignToDropDownElement.click();
		List<WebElement> reassignDropdownVals = driver.findElements(reassignToDowndownValues);
		return reassignDropdownVals;
	}

	/**
	 * Method to select the reassignTo dropdown value
	 * 
	 * @param reassignToDropdownVal
	 */
	public void selectReassignTo(String reassignToDropdownVal) {
		WebElement reassignToDropdpwnEle = uiTestHelper.waitForObject(By.xpath(
				"//input[@type='text' and @name='ReassignTO']/following::lightning-base-combobox-item/span[2]/span[@title='"
						+ reassignToDropdownVal + "'][1]"));
		reassignToDropdpwnEle.click();
	}

	public void clickReassignToSaveBtn() {
		WebElement reassignToSaveButton = uiTestHelper.waitForObject(reassignToSaveBtn);
		reassignToSaveButton.click();
	}

	// Merged Action Options
	public void clickMergedActionsTab() {
		WebElement mergedActBtn = uiTestHelper.waitForObject(mergedAction);
		uiTestHelper.clickJS(mergedActBtn);
	}

	// Case Update
	public boolean showCaseUpdateClearEmailMsg() {

		WebElement showcaseUpdateClearEmailMsg = uiTestHelper.waitForObject(caseUpdateClearMsg);
		boolean showcaseUpdateClearEmailMsgDisplayed = showcaseUpdateClearEmailMsg.isDisplayed();
		return showcaseUpdateClearEmailMsgDisplayed;
	}

	public void clickCaseUpdateClearBtnOnPopUp() {
		WebElement clearBtn = uiTestHelper.waitForObject(caseUpdateClearBtnOnPopUp);
		uiTestHelper.clickJS(clearBtn);
	}

	public void clickCaseUpdateCancelBtnOnPopUp() {
		WebElement cancelBtn = uiTestHelper.waitForObject(caseUpdateCancelBtnOnPopUp);
		uiTestHelper.clickJS(cancelBtn);
	}

	// integrationHiddenField
	public void clickIntegrationHiddenField() {
		WebElement integrationHiddenFieldBtn = uiTestHelper.waitForObjectToBeClickable(integrationHiddenField);
		clickJS(integrationHiddenFieldBtn);
	}

	public boolean verifyExceptionCodeColumn() {
		WebElement ele = uiTestHelper.waitForObject(exceptionCodeColumn);
		return ele.isDisplayed();
	}

	public boolean verifyCustomerAccountColumn() {
		WebElement ele = uiTestHelper.waitForObject(customerAccountColumn);
		return ele.isDisplayed();
	}

	public boolean verifySlaStatus() {
		WebElement ele = uiTestHelper.waitForObject(slaStatusColumn);
		return ele.isDisplayed();
	}

	public boolean verifyCustomerNameColumn() {
		WebElement ele = uiTestHelper.waitForObject(customerNameColumn);
		return ele.isDisplayed();
	}

	public void clickActionCurrency() {
		WebElement currency = uiTestHelper.waitForObject(actionCurrency);
		uiTestHelper.clickJS(currency);
	}

	public void waitForSuccessMsg() {
		uiTestHelper.waitForObjectToBeInvisible(successMessage);
	}

	public String getReactiveCaseStatus() {
		WebElement casestatusField = uiTestHelper.waitForObject(reactiveCaseStatus);
		String casestatusValue = casestatusField.getText();
		return casestatusValue;
	}

	public String getRcReceiverCountry() {
		WebElement receiverCountryField = uiTestHelper.waitForObject(rcReceiverCountry);
		String sencountryValue = receiverCountryField.getText();
		return sencountryValue;
	}

	public void clickOIBMemeberEdit() {
		WebElement oibMemberEdit = uiTestHelper.waitForObject(oibEdit);
		uiTestHelper.clickJS(oibMemberEdit);
	}

	public void closeActionsTab() {
		WebElement closeActionsTab = uiTestHelper.waitForObject(actionsClose);
		uiTestHelper.clickJS(closeActionsTab);
	}

	public void closeCaseRemarksTab() {
		WebElement closeCaseRemarksTab = uiTestHelper.waitForObject(closeCaseRemarkTab);
		uiTestHelper.clickJS(closeCaseRemarksTab);
	}

	public void closeOpenedTask() {
		WebElement closeOpenedTaskTab = uiTestHelper.waitForObject(closeRecentlyOpenedTask);
		uiTestHelper.clickJS(closeOpenedTaskTab);
	}

	public void closeTaskTab() {
		WebElement closeTasksTab = uiTestHelper.waitForObject(closeTaskTab);
		uiTestHelper.clickJS(closeTasksTab);
	}

	public void closeRequestForInfoActionTab() {
		WebElement closeInfActionTab = uiTestHelper.waitForObject(closeInfoActionTab);
		uiTestHelper.clickJS(closeInfActionTab);
	}

	public void closeEscalationsTab() {
		WebElement closeEscalationTab = uiTestHelper.waitForObject(closeEscalationsTab);
		uiTestHelper.clickJS(closeEscalationTab);
	}

	public boolean verifyCaseCreated() {
		waitForSuccessMsg();
		WebElement ele = uiTestHelper.waitForObject(caseCreatedIdentifier);
		return ele.isDisplayed();
	}

	public String getCaseNumber() {
		WebElement ele = uiTestHelper.waitForObject(caseNo);
		return ele.getText();

	}

	public String isEmailClickable_ContactHeader(String email) {
		email = email.toLowerCase();
		By emailDetails_text = By.xpath("//slot/lightning-formatted-text[contains(text(),'" + email + "')]");
		String email_xpath = "(//lightning-formatted-lookup/a[contains(text()(//a/slot/slot/span[contains(text(),'"
				+ email + "')])";
		boolean temp = false;
		for (int i = 0; i < 4; i++) {
			try {
				By emailDetails_link = By.xpath(email_xpath + "[" + i + "");
				uiTestHelper.waitForObjectToBeClickable(emailDetails_link);
				temp = true;
				break;
			} catch (Exception e) {
				temp = false;
			}
		}
		String linkORtext = "";
		if (temp) {
			linkORtext = "false";
		}
		try {
			uiTestHelper.waitForObjectToBeClickable(emailDetails_text);
			linkORtext = linkORtext + true;
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

	public String isEmailClickable_CaseDetails(String email) {
		email = email.toLowerCase();
		WebElement element;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		By emailDetails_text = By.xpath("//lightning-formatted-text[contains(text(),'" + email + "')]");
		By emailDetails_link = By.xpath("//lightning-formatted-lookup/a[contains(text(),'" + email + "')]");
		String linkORtext = "";
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailDetails_text));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(emailDetails_text));
			element.isDisplayed();
			linkORtext = "true";

		} catch (Exception e) {
			linkORtext = "false";
		}
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailDetails_link));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(emailDetails_link));
			element.isDisplayed();
			linkORtext = "false";

		} catch (Exception e) {
			linkORtext = linkORtext + "true";
		}
		return linkORtext;
	}

	public void clickCustomerContactName() {
		By contactname = By.xpath("//p[@title='Contact Name']/following::p[1]//div//div");
		WebElement contactname_a = uiTestHelper.waitForObject(contactname);
		uiTestHelper.actionClick(contactname_a);

	}

	public String getCustomerContactName() {
		By contactname_field = By.xpath("//p[@title='Contact Name']/following::p[1]//div//div//a//span");
		boolean temp = false;
		try {
			WebElement contactname_a = driver.findElement(contactname_field);
			temp = true;
		} catch (Exception e) {
			temp = false;
			return null;
		}
		if (temp) {
			WebElement contactname_a = uiTestHelper.waitForObject(contactname_field);
			return contactname_a.getText();
		}
		return null;
	}

}
