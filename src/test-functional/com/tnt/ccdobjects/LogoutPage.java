package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class LogoutPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By userImage=By.xpath("//img[@class='icon noicon']/parent::span");
	By logoutButton=By.xpath("//a[contains(text(),'Log Out')]");
	By userName= By.xpath("//a[@class='profile-link-label']");
	By logout=By.xpath("//div[@class='profile-card-indent']//div[@class='profile-card-toplinks']//a[text()='Log Out']");
	
	public LogoutPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper(); 
	}
	public void clickUser() {
		WebElement userimg=uiTestHelper.waitForObject(userImage);
		uiTestHelper.clickJS(userimg);
	}
	public void clickLogout() {
		WebElement log=uiTestHelper.waitForObjectToBeClickable(logout);
		//log.click();
		uiTestHelper.clickJS(log);
	}
	
	public boolean verifyLogout() {
		WebElement logOut=uiTestHelper.waitForObjectToBeClickable(logout);
		return logOut.isEnabled();
	}

	//Get loggedin user name
	public String getLoggedinUserName() {
		WebElement loggedinUserName=uiTestHelper.waitForObject(userName);
		return loggedinUserName.getText();
	}
}
