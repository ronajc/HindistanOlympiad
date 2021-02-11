package com.ht_olympiad.testcases;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ht_olympiad.main.baseClass;

public class registerSchool extends baseClass{
	
	@Test(enabled = false)
	public void verifyWebsite()
	{
		if(driver.getTitle().contains("Hindustan"))
		{
			Assert.assertTrue(true);
		}
	}

	@Test(priority = 1, enabled = true)
	public void registerasSchool() throws InterruptedException, IOException {
		driver.get("https://olympiad.innovationm.com/register");
		Select stateDropdown = new Select(driver.findElement(By.id("populateState")));
		stateDropdown.selectByValue("11");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		Select districtDropdown = new Select(driver.findElement(By.id("populateDistrict")));
		districtDropdown.selectByVisibleText("Agra");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		Select schoolDropdown = new Select(driver.findElement(By.id("populateSchool")));
		schoolDropdown.selectByVisibleText("Other");
		driver.findElement(By.id("inputSchool")).sendKeys(RandomStringUtils.randomAlphabetic(5)+" public school");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		WebElement username = driver.findElement(By.id("user_name"));
		username.clear();
		username.sendKeys(RandomStringUtils.randomAlphanumeric(8));
		WebElement password = driver.findElement(By.id("school_password"));
		password.clear();
		password.sendKeys("Vishal@123");
		driver.findElement(By.id("school_conf_password")).sendKeys("Vishal@123");
		driver.findElement(By.id("school_email")).sendKeys(RandomStringUtils.randomAlphabetic(5)+"@yopmail.com");
		driver.findElement(By.id("school_phone")).sendKeys("8894962429");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElement(By.id("city")).sendKeys("Noida");
		driver.findElement(By.id("address")).sendKeys("abc colony");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		WebElement verifybtn = driver.findElement(By.id("verify"));
		String mainwindowhandle = driver.getWindowHandle();

		JavascriptExecutor jsExecutor = ((JavascriptExecutor)driver);
		jsExecutor.executeScript("window.scrollBy(0,600)", "");
		jsExecutor.executeScript("arguments[0].click()", verifybtn);
		Thread.sleep(4000);

		String otp = getOTP();
		StringBuffer otp1 = new StringBuffer(otp);
		String one = otp1.substring(0);
		System.out.println(one);


		Set<String> allwindowhandle = driver.getWindowHandles();
		Iterator<String> iterator = allwindowhandle.iterator();

		while(iterator.hasNext())
		{
			String childwindow = iterator.next();
			if(!mainwindowhandle.equalsIgnoreCase(childwindow)) {
				driver.switchTo().window(childwindow);
			}
		}		
		driver.findElement(By.xpath("//*[@id=\"digit_one\"]")).sendKeys(one);
		Thread.sleep(3000);
		driver.findElement(By.className("verify-btn")).click();
		Thread.sleep(4000);
		if(driver.getCurrentUrl().contains("olympiad"))
		{
			Assert.assertTrue(true);
			System.out.println("Registered as School successfully");
		}
		else {
			{
				Assert.assertFalse(false);
				captureScreenshot(driver, "Register As School");
				
			}
		}
	}
}
