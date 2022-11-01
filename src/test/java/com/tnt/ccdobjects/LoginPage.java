package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class LoginPage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By username = By.xpath("//*[@id='username']");
	By password = By.xpath("//*[@id='password']");
	By loginBtn = By.xpath("//*[@id='Login']");
	By instructionNextButton = By.xpath("//input[@type='button']");
	By finishButton = By.xpath("//input[@value='Finish']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper = new UiTestHelper();
	}

	// Set UserId on Login Page
	public void setUsername(String userId) {
		WebElement userIdField = uiTestHelper.waitForObject(username);
		userIdField.sendKeys(userId);
	}

	// Set Password on Login Page
	public void setPassword(String pwd) {
		WebElement passwordField = uiTestHelper.waitForObject(password);
		passwordField.sendKeys(pwd);
	}

	// Click login button on Login Page
	public void clickLoginBtn() {
		WebElement loginButton = uiTestHelper.waitForObjectToBeClickable(loginBtn);
		loginButton.click();
	}

	// Click login button on Login Page
	public void clickInstructionConfirmButton() {
		WebElement nextButton = uiTestHelper.waitForObjectToBeClickable(instructionNextButton);
		nextButton.click();
	}

	// Click login button on Login Page
	public void clickFinishButton() {
		WebElement finishbtn = uiTestHelper.waitForObjectToBeClickable(finishButton);
		finishbtn.click();
	}
	
	// Click Next button if present
    public void clickNextORFinishBtn(String buttonName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            WebElement nextButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='"+buttonName+"']")));
            uiTestHelper.actionClick(nextButton);
        } catch (Exception e) {
       }
    }

}