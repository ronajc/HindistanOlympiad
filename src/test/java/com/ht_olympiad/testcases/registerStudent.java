package com.ht_olympiad.testcases;

import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.ht_olympiad.main.baseClass;

public class registerStudent extends baseClass{
	
	@Test(priority = 2,enabled = true)
	public void registerAsStudent() throws InterruptedException
	{
		logger.info("Opening student registration page");
		driver.get("https://olympiad.innovationm.com/student-register");
		WebElement studentname = driver.findElement(By.id("student_name"));
		studentname.clear();
		studentname.sendKeys("Vishal Thakur");
		WebElement studentEmail = driver.findElement(By.id("student_email"));
		studentEmail.clear();
		studentEmail.sendKeys(RandomStringUtils.randomAlphabetic(6)+"@yopmail.com");
		WebElement studentPhone = driver.findElement(By.id("student_phone"));
		studentPhone.clear();
		studentPhone.sendKeys("99"+RandomStringUtils.randomNumeric(8));
		WebElement userName = driver.findElement(By.id("user_name"));
		userName.clear();
		userName.sendKeys(RandomStringUtils.randomAlphanumeric(8));
		WebElement password = driver.findElement(By.id("student_password"));
		password.clear();
		password.sendKeys("Vishal@123");
		WebElement rePassword = driver.findElement(By.id("student_conf_password"));
		rePassword.clear();
		rePassword.sendKeys("Vishal@123");
		
		String mainwindowhandle = driver.getWindowHandle();
		
		JavascriptExecutor jsExecutor = ((JavascriptExecutor)driver);
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(By.id("verify")));
		Thread.sleep(4000);
		
		getOTP();
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
		Thread.sleep(3000);
		logger.info("OTP verified");
		
		driver.switchTo().window(mainwindowhandle);
		logger.info("filling school details and class info");
	}
}
