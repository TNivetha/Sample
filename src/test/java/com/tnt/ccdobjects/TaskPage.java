package com.tnt.ccdobjects;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.tnt.commonutilities.Driver;
import com.tnt.commonutilities.UiTestHelper;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class TaskPage extends Driver{

	UiTestHelper uiTestHelper;
	WebDriver driver;

	// Task section on case page
	By taskTabHeader = By.xpath("//a[@class='tabHeader']//span[contains(text(),'Task')]");
	By taskCountryTerritoryBox = By.xpath("//input[@placeholder='Search Countries/Territories...']");
	By taskRouteToBox = By.xpath("//button[@name='RouteTo']");
	By taskOriginDestinationRoute = By.xpath("//button[@name='OriginDestination']");
	By taskLocationBox = By.xpath("//input[@name='Location']");
	By taskDeadlineDateBox = By.xpath("(//input[@name='datetime'])[1]");
	By taskTimeDeadlineBox = By.xpath("(//input[@name='datetime'])[2]");
	By taskInstructionTextBox = By.xpath("//textarea[@name='Description']");
	By taskSaveButton = By.xpath("//button[@title='Create']");

	By taskId = By.xpath("//h1/div[contains(text(),'Task')]/following::slot[1]/lightning-formatted-text");
	By cmodTaskId = By.xpath("//p[@title='CMOD Task Id']/following::p[1]/slot/lightning-formatted-text");
	By cmodTaskId2 = By.xpath("//span[contains(text(),'CMOD Task Id')]/following::div[1]/lightning-formatted-text");
	By instructions = By.xpath("//span[contains(text(),'Instruction')]/following::div[1]/lightning-formatted-text");
	By status = By.xpath("//span[contains(text(),'Status')]/following::div[1]/lightning-formatted-text");
	By countryORteritory = By
			.xpath("//span[contains(text(),'Country/Territory')]/following::div[1]/lightning-formatted-lookup/a");
	By caseId= By.xpath("//span[contains(text(),'Case Id')]/following::div[1]/lightning-formatted-lookup/a");
	By TNTlocation = By.xpath("//span[contains(text(),'TNT Location')]/following::div[1]/lightning-formatted-lookup/a");
	By FedExlocation = By.xpath("//span[contains(text(),'FedEx Location')]/following::div[1]/lightning-formatted-text");
	By taskCreationTime = By
			.xpath("//span[contains(text(),'Task Creation Date/Time')]/following::div[1]/lightning-formatted-text");
	By routTo = By
			.xpath("//span[contains(text(),'Task Creation Date/Time')]/following::div[1]/lightning-formatted-text");
	By escalationLevel = By
			.xpath("//span[contains(text(),'Escalation Level')]/following::div[1]/lightning-formatted-text");
	By urgency = By.xpath("//span[contains(text(),'Urgency')]/following::div[1]/lightning-formatted-text");
	By escalationSLAtime = By
			.xpath("//span[contains(text(),'Escalation SLA (Date/Time)')]/following::div[1]/lightning-formatted-text");
	By escalationErrorMessage = By
			.xpath("//span[contains(text(),'Escalation SLA (Date/Time)')]/following::div[1]/lightning-formatted-text");
	By ownerId = By.xpath("//span[contains(text(),'Owner ID')]/following::div[1]/lightning-formatted-lookup");

	//monitor activity SLA date and time
	By monitorActSLAdate=By.xpath("(//input[@name='CO_Internal_Activity_SLA__c'])[1]");
	By monitorActSLAtime=By.xpath("(//input[@name='CO_Internal_Activity_SLA__c'])[2]");
	
	
	HashMap<String, String> taskDetails = new HashMap<String, String>();
	
	
	
	public TaskPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}
	
	/*
	 * This method is used to add getLogger() for Passed test cases in extent report with
	 * screenshot
	 */
	public  void Pass_Message(String Message)  {
		getLogger().pass(Message);
		//getLogger().log(Status.INFO, "", MediaEntityBuilder.createScreenCaptureFromPath(addscreenshot()).build());
	}
	public  static void report_MapValues(String Message,HashMap map)  {
		getLogger().pass(Message);
		getLogger().pass(MarkupHelper.createOrderedList(map).getMarkup());
	}
	
	/*
	 * This method is used to add getLogger() for Passed test cases in extent report with
	 * screenshot
	 */
	public  void Pass_Message_withoutScreenCapture(String Message){
		getLogger().pass(Message);
	}

	/*
	 * This method is used to add getLogger() for Failed test cases in extent report with
	 * screenshot
	 */
	public static void Fail_Message(String Message)  {
		getLogger().fail(Message);
	}

	public HashMap<String, String> createTask(String routToOption, String orgOrDest, String country, String taskLocation,
			String instructions) throws Exception{
		//taskDetails = null;

		try {
			WebElement routeBtn = uiTestHelper.waitForObjectToBeClickable(taskRouteToBox);
			routeBtn.click();
			WebElement setRoute = uiTestHelper.waitForObjectToBeClickable(
					By.xpath("//lightning-base-combobox-item[@data-value='" + routToOption + "']"));
			uiTestHelper.actionClick(setRoute);
			taskDetails.put("routToOption", "true");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			taskDetails.put("routToOption", "false");
		}

		try {
			uiTestHelper.waitForObjectToBeClickable(taskOriginDestinationRoute).click();
			uiTestHelper.waitForObjectToBeClickable(By.xpath(
					"//button[@name='OriginDestination']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='"
							+ orgOrDest + "']")).click();
			taskDetails.put("orgOrDest", "true");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			taskDetails.put("orgOrDest", "false");
		}
		try {
			ifCountryFieldisFilled();
			WebElement taskLookupBox = uiTestHelper.waitForObject(taskCountryTerritoryBox);
			taskLookupBox.sendKeys(country);
			WebElement dropdownCountry = uiTestHelper.waitForObjectToBeClickable(By
					.xpath("//label[text()='Country/Territory']/following::lightning-base-combobox-formatted-text[@title='"
							+ country + "']"));
			uiTestHelper.clickJS(dropdownCountry);
			taskLookupBox.sendKeys(Keys.TAB);
			taskDetails.put("country", "true");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			taskDetails.put("country", "false");
		}
		
		if(routToOption.contains("FedEx")) {
			try {
				WebElement tasklocationbutton = uiTestHelper.waitForObject(By.xpath("//button[@name='locationFedOps']"));
				uiTestHelper.clickJS(tasklocationbutton);
				tasklocationbutton.sendKeys(taskLocation);
				WebElement colldepot = uiTestHelper.waitForObjectToBeClickable(By.xpath(
						"//button[@name='locationFedOps']/following::div[@role='listbox'][1]/lightning-base-combobox-item//span[@title='"
								+ taskLocation + "']"));
				colldepot.click();
				taskDetails.put("taskLocation", "true");
			} catch (org.openqa.selenium.NoSuchElementException e) {
				taskDetails.put("taskLocation", "false");
			}
		}else {
			try {
				WebElement depotfield = uiTestHelper.waitForObject(taskLocationBox);
				depotfield.sendKeys(taskLocation);
				// depotfield.sendKeys(Keys.TAB);
				WebElement depotMenuOption = uiTestHelper.waitForObjectToBeClickable(By.xpath("//div[@role='option']/span/span[text()='" + taskLocation + "']"));
				uiTestHelper.clickJS(depotMenuOption);
				taskDetails.put("taskLocation", "true");
			} catch (org.openqa.selenium.NoSuchElementException e) {
				taskDetails.put("taskLocation", "false");
			}
		}		
		
		try {
			WebElement taskInstructionBox = uiTestHelper.waitForObject(taskInstructionTextBox);
			uiTestHelper.scrollIntoView(taskInstructionBox);
			taskInstructionBox.sendKeys("TestG");
			taskInstructionBox.sendKeys(Keys.TAB);
			taskDetails.put("instructions", "true");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			taskDetails.put("instructions", "false");
		}
		
		WebElement saveBtn = uiTestHelper.waitForObject(taskSaveButton);
		uiTestHelper.clickJS(saveBtn);
		
		return taskDetails;

	}
	public boolean ifCountryFieldisFilled() throws Exception{
		try {
		//	driver.findElement(By.xpath("//label['Country/Territory']/following::span['Countries__c']/input"));	
			WebElement clearbtn=driver.findElement(By.xpath("//button[@title='Clear Selection']"));
			uiTestHelper.clickJS(clearbtn);
		return true;
		}catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	public void clickOnTaskCreatedTask() {
		By taskid = By.xpath("//div[@class='forceVisualMessageQueue']/div/div//a");
		WebElement tid = uiTestHelper.waitForObject(taskid);
		tid.click();		
	}
	public HashMap<String, String> getTaskDetails() throws Exception{
		//taskDetails = null;
		try {		
		taskDetails.put("taskId", uiTestHelper.waitForObject(taskId).getText());
		taskDetails.put("cmodTaskId", uiTestHelper.waitForObject(cmodTaskId).getText());
		taskDetails.put("cmodTaskId2",uiTestHelper.waitForObject(cmodTaskId2).getText());
		taskDetails.put("instructions",uiTestHelper.waitForObject(instructions).getText());
		taskDetails.put("status",uiTestHelper.waitForObject(status).getText());
		taskDetails.put("countryORteritory",uiTestHelper.waitForObject(countryORteritory).getText());
		taskDetails.put("caseId",uiTestHelper.waitForObject(caseId).getText());
		taskDetails.put("TNTlocation",uiTestHelper.waitForObject(TNTlocation).getText());
		taskDetails.put("FedExlocation",uiTestHelper.waitForObject(FedExlocation).getText());
		taskDetails.put("taskCreationTime",uiTestHelper.waitForObject(taskCreationTime).getText());
		taskDetails.put("routTo",uiTestHelper.waitForObject(routTo).getText());
		taskDetails.put("escalationLevel",uiTestHelper.waitForObject(escalationLevel).getText());
		taskDetails.put("urgency",uiTestHelper.waitForObject(urgency).getText());
		taskDetails.put("escalationSLAtime",uiTestHelper.waitForObject(escalationSLAtime).getText());
		taskDetails.put("escalationErrorMessage",uiTestHelper.waitForObject(escalationErrorMessage).getText());
		taskDetails.put("ownerId",uiTestHelper.waitForObject(ownerId).getText());
		}catch(Exception e) {
			e.printStackTrace();
			Fail_Message("");			
		}

		return taskDetails;
	}

	public String getMonitorActSLA_DateandTime() {
		String dateANDtime = "";
		try {
			dateANDtime=uiTestHelper.waitForObject(monitorActSLAdate).getAttribute("value");
			dateANDtime=dateANDtime+" "+uiTestHelper.waitForObject(monitorActSLAtime).getAttribute("value");
			return dateANDtime;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return "";
		}

	}
	public String getCrrentOpenedTask() {
		return driver.findElement(taskId).getText();
	}
	public boolean isTaskTabOpened() {
		try {			
			return driver.findElement(taskId).isDisplayed();
		}catch(Exception e) {
			return false;
		}
	}
	public boolean closeCurrentTaskWindow(String taskid) {
		try {			
			uiTestHelper.clickJS(uiTestHelper.waitForObject(By.xpath("//button[@title='Close "+taskid+"']")));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean goBackToTask(String taskid) {
		try {
			uiTestHelper.waitForObjectToBeClickable(By.xpath("//li/a[@title='" + taskid + "']")).click();
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	public boolean ifSuccessMessage(String message) {
		try {
			uiTestHelper.waitForObject(By.xpath("//div[@class='forceVisualMessageQueue']/div/div//span[contains(text(),'"+message+"')]"));
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
}
