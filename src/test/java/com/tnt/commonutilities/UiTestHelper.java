package com.tnt.commonutilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class UiTestHelper extends Driver {
	// This Class is here to hold all WebDriver Waits and any JavaScript Executor
	// Methods in one centralized place
	// Please add to here as you use new ones instead of copy and pasting them
	// around
	
	

	public void clickLightningDropDownMenu(By input, String inputNameTag, String listItem) {
		// Method that can be re-used for multiple lightning aria-active descendant
		// combobox
		waitForObject(input);
		clickJSByObjects(input);
		WebElement select = waitForObject(By.xpath("//input[@name='" + inputNameTag
				+ "']/following::div[@role='listbox'][1]/lightning-base-combobox-item[@data-value='" + listItem
				+ "']"));
		select.click();
	}

	public void waitForSuccessMessageToDisappear(By object) {
		waitForObjectToBeInvisible(object);
	}
	public String getSystemDate()  {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDateTime = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String exp_date = sdf.format(currentDateTime);
		System.out.println("excpected Date:" + exp_date);
		return exp_date;
	}

	public String getTomorrowsDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return simpleDateFormat.format(dt);
	}

	public WebElement waitForObjectwithSec(By by, int seconds) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
		return element;
	}

	public WebElement waitForObject(By by) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
		return element;
	}

	public List<WebElement> waitForObjects(By by) {
		List<WebElement> element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 90);
			//element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			e.printStackTrace();
			//Fail_Message("Element not Found: " + by);
			
		}
		Assert.assertNotNull(element);
		return element;
	}

	public WebElement waitForObjectToBeClickable(By by) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			element = wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
		return element;
	}

	public Boolean waitForObjectToBeInvisible(By by) {
		Boolean element = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			element = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("Element not Found: " + by);
		}
		Assert.assertNotEquals(element,false);
		return element;
	}

	public void scrollIntoView(By by) {
		WebElement element = null;
		try {

			element = getDriver().findElement(by);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
	}

	// Scroll USing JavaScript Executor
	public void scrollIntoView(WebElement ele) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].scrollIntoView();", ele);
		} catch (Exception e) {
			Fail_Message("Element not Found: " + ele);
		}
		Assert.assertNotNull(ele);

	}

	public void clickJS(WebElement ele) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].click();", ele);
		} catch (Exception e) {
			Fail_Message("Element not Found: " + ele);
		}
		Assert.assertNotNull(ele);
	}

	public void clickJSByObjects(By by) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].click();", getDriver().findElement(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(by);
	}

	public void sendKeysJS(By by, String sendChar) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("arguments[0].value='" + sendChar + "'", getDriver().findElement(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(by);
	}

	// Method for wait for presence of element
	public WebElement waitforPresenceOfObject(By by) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
		return element;
	}

	public WebElement waitForObjectQuick(By by) {
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 3);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotNull(element);
		return element;
	}

	public Boolean invisibilityOfElementLocated(By by) {
		Boolean element = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 60);
			element = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			Fail_Message("Element not Found: " + by);
		}
		Assert.assertNotEquals(element,false);
		return element;
	}

	// Scroll Down
	public void scrolldown(String scrollLimit) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("window.scrollBy(0," + scrollLimit + ")");
	}

	// Scroll Up
	public void scrollUp(String scrollLimit) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("window.scrollBy(0," + scrollLimit + ")");
	}
	//Double Click using Action Class
	public void actionDoubleClick(WebElement ele) {
		Actions action = new Actions(getDriver());
		action.doubleClick(ele).build().perform();
	}
	// Click using Action Class
	public void actionClick(WebElement ele) {
		Actions action = new Actions(getDriver());
		action.click(ele).perform();
	}	
	
	public WebElement waitForObjectwithException(By by,int seconds) {
		WebElement element=null;
			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return element;
	}
	
	public void scrollTable(String ele) {
		EventFiringWebDriver event=new EventFiringWebDriver(getDriver());
		event.executeScript("document.querySelector('"+ele+"').scrollTop = 1500");
	}
	public void propagateException() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
