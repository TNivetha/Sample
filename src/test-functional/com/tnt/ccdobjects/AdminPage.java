package com.tnt.ccdobjects;
import com.tnt.cmod.CMOD_FF_Reusable;
import com.tnt.commonutilities.UiTestHelper;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
public class AdminPage {
	String alphabet;
	UiTestHelper uiTestHelper;
	WebDriver driver;
	By setup = By.xpath("(//div[@class='headerTrigger  tooltip-trigger uiTooltip'])[2]");
	By customiframe = By.xpath("//div[@class='content iframe-parent']");
	By CustSettiniFrame= By.xpath("//*[@id='setupComponent']/div[2]/div/div/force-aloha-page/div/iframe");
	 

	//By setup = By.xpath("(//span[@role='tooltip' ])[5]");
	By quickFind=By.xpath("(//input[@type='search'])[2]");
	
	By pnNotificationQL= By.xpath("//a[contains(text(),'PN Notification Display Name')]");
	public AdminPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}
	public void clickSetup() {
		WebElement setupbtn = uiTestHelper.waitForObjectToBeClickable(setup);
		uiTestHelper.clickJS(setupbtn);
		Actions act= new Actions(driver);
		WebElement st=driver.findElement(By.xpath("//li[@id='related_setup_app_home']"));
		act.click(st).perform();
	}
	public void clickQuickFind() {
		//WebElement clickQF = uiTestHelper.waitForObjectToBeClickable(quickFind);
		WebElement clickQF = uiTestHelper.waitforPresenceOfObject(quickFind);
		clickQF.click();
	}
	public void setQuickFindSearch(String searchItem) {
		WebElement search = uiTestHelper.waitforPresenceOfObject(quickFind);
		search.click();
		search.sendKeys(searchItem);
		/*Actions act1=new Actions(driver);
		act1.moveToElement(search).doubleClick().sendKeys("Test").build().perform();*/
		driver.findElement(By.xpath("//mark[contains(text(),'"+searchItem+"')]")).click();
	}
	public void pnNot() {
		WebElement pnNot = uiTestHelper.waitForObjectToBeClickable(pnNotificationQL);
		JavascriptExecutor jse1 = (JavascriptExecutor) driver;
		jse1.executeScript("window.scrollBy(0,900)");
		uiTestHelper.scrollIntoView(pnNot);
		pnNot.click();
	}
	public void verifyPNNotification() {
		//div[@class="content iframe-parent"]
		WebElement msg = uiTestHelper.waitForObject(By.xpath("//a[contains(text(),'Original_Consignment_Status_ExportNew')]"));
		
		 msg.isDisplayed();
	}
	public void switchToFrameCustSetting() {
		uiTestHelper.waitForObject(CustSettiniFrame);
		uiTestHelper.waitforPresenceOfObject(CustSettiniFrame);
		WebElement frame=uiTestHelper.waitForObject(CustSettiniFrame);
		driver.switchTo().frame(frame);
	}
	public boolean selectCustObj(String custObj)
	{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement myTable = driver.findElement(By.xpath(
				"//div[@id='CS_Defn_View:customFields_body']/table/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		System.out.println("Row count in MyOpenCases table is " + row_count);
		for (int i = 2; i < row_count; i++) {
			System.out.println(i);
			String a = driver.findElement(By.xpath("//div[@id='CS_Defn_View:customFields_body']/table/tbody/tr["+i+"]/th/div/a")).getText();
			if(a.contains(custObj))
			{
				System.out.println("Original_Consignment_Status_ExportNew is displayed");
				break;
			}
	}
		return true;
			
		}
		/*
		public boolean custObj(String CustObj) {
			WebElement actTable = uiTestHelper.waitForObject(actionTable);
			boolean table = actTable.isDisplayed();
		
		return table;

		WebElement myTable = driver.findElement(By.xpath(
				"//*[@id='CS_Defn_View:customFields_body']/table/tbody"));
		List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
		int row_count = objRow.size();
		System.out.println("Row count in MyOpenCases table is " + row_count);
		for (int i = 1; i <= row_count; i++) {
		
		
			String a = driver.findElement(By.xpath("//*[@id='CS_Defn_View:customFields_body']/table/tbody/tr["+i+"]/th/div/a")).getText();
			System.out.println("Case no in caseselect is " + a);
			if(a.equals(CustObj))
			{
				
				
			}
		
		
		}*/
		/*public boolean isAssignToMeChecked() {
		WebElement assignToMechk = uiTestHelper.waitForObject(caseAssign);
		assignToMechk.click();
		WebElement assignToMecheckBox = uiTestHelper.waitForObject(assignToMeCheckbox);
		return assignToMecheckBox.isSelected();
	}*/
		/*public void selectCustFiledLabel(String CustObj)
		{
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement myTable = driver.findElement(By.xpath(
					"//*[@id='CS_Defn_List:theTemplate:panelGrid']/tbody/tr/td/div[5]/div/div[2]/table"));
			List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
			int row_count = objRow.size();
			System.out.println("Row count in MyOpenCases table is " + row_count);

			for (int i = 1; i <= row_count; i++) {
				String a = driver.findElement(By.xpath("//*[@id='CS_Defn_List:theTemplate:panelGrid']/tbody/tr/td/div[5]/div/div[2]/table/tbody/tr["+i+"]/th/a")).getText();
				System.out.println("Case no in caseselect is " + a);

				if (a.equals(CustObj)) {
					WebElement myObj = driver.findElement(By.xpath("//*[@id='CS_Defn_List:theTemplate:panelGrid']/tbody/tr/td/div[5]/div/div[2]/table/tbody/tr["+i+"]/th/a"));
					uiTestHelper.clickJS(myObj);
					break;
				}
			}*/

		/*public boolean custFieldLabelDisplay(String custField) {

				WebElement myTable = driver.findElement(By.xpath(
						"//*[@id='CS_Defn_View:customFields_body']/table/tbody"));
				List<WebElement> objRow = myTable.findElements(By.tagName("tr"));
				int row_count = objRow.size();
				System.out.println("Row count in MyOpenCases table is " + row_count);

				for (int i = 1; i <= row_count; i++) {
					String a = driver.findElement(By.xpath("//*[@id='CS_Defn_View:customFields_body']/table/tbody/tr["+i+"]/th/div/a")).getText();
					System.out.println("Case no in caseselect is " + a);

					if (a.equals(custField)) {
						WebElement myObj = driver.findElement(By.xpath("//*[@id='CS_Defn_View:customFields_body']/table/tbody/tr["+i+"]/th/div/a"));
						//uiTestHelper.clickJS(myObj);
						break;
					}
			WebElement assignToMechk = uiTestHelper.waitForObject(caseAssign);
			assignToMechk.click();
			WebElement assignToMecheckBox = uiTestHelper.waitForObject(assignToMeCheckbox);
			return assignToMecheckBox.isSelected();
		}*/

	}
