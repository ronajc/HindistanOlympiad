package com.ht_olympiad.testcases;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class registerStudent extends BaseClass{
	BaseClass BSpline = new BaseClass();
	
	@Test(enabled = false)
	public void registerAsStudent() throws InterruptedException
	{
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
		
		JavascriptExecutor jsExecutor = ((JavascriptExecutor)driver);
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(By.id("verify")));
		Thread.sleep(4000);
		
		BSpline.getOTP();
	}
}
