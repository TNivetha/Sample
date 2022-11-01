package com.tnt.ccdobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tnt.commonutilities.UiTestHelper;

public class TransitTimePage {
	WebDriver driver;
	UiTestHelper uiTestHelper;
	By transitTimePageTitle=By.xpath("//div[@id='tableDiv']/div/h3/font");
	By iframeTransitTime=By.xpath("//div[@class='oneAlohaPage']//iframe");
	By childFrameTransitTime=By.xpath("(//iframe[@title='accessibility title'])[2]");
	By multipleService=By.xpath("//font[contains(text(),'Multiple OSC Service Locations found')]");
	By origin=By.xpath("//input[@name='origin']");
	By destination=By.xpath("//input[@name='destination']");
	By viewTransitTimeLine=By.xpath("//input[@value='View Transit TimeLine']");
	By transitTimeOrgDestTitle=By.xpath("//font[contains(text(),'Transit Timeline')]");
	
	
	public TransitTimePage(WebDriver driver) {
		this.driver = driver;
		uiTestHelper=new UiTestHelper();
	}
	
	public void switchToFrameTT() {
		uiTestHelper.waitForObject(iframeTransitTime);
		WebElement frame=uiTestHelper.waitForObject(childFrameTransitTime);
		driver.switchTo().frame(frame);
	}
	public String getTransitTimeTitle() {
		WebElement title=uiTestHelper.waitForObjectwithException(transitTimePageTitle,30);
		return title.getText();
	}
	
	public boolean verifyMultiServiceLoc() {
		WebElement tt=uiTestHelper.waitForObject(multipleService);
		return tt.isDisplayed();
	}
	public void clickOrigin() {
		WebElement tt=uiTestHelper.waitForObject(origin);
		tt.click();
	}
	public void clickDestination() {
		WebElement tt=uiTestHelper.waitForObject(destination);
		tt.click();
	}
	public void viewTransitTimeline() {
		WebElement tt=uiTestHelper.waitForObject(viewTransitTimeLine);
		tt.click();
	}
	public boolean getTransitTimeMultiService() {
		WebElement title=uiTestHelper.waitForObject(transitTimeOrgDestTitle);
		return title.isDisplayed();
	} 
}
