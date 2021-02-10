package com.ht_olympiad.testcases;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xdgf.usermodel.section.geometry.MoveTo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.sql.*;
import java.awt.Dialog.ModalityType;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseClass {
	WebDriver driver;
	public static Logger logger;

	@BeforeSuite(enabled = true)
	public void setUp()
	{
		logger = Logger.getLogger("HT_Olympiad");
		PropertyConfigurator.configure("log4j.properties");

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@BeforeTest(enabled = true)
	public void openWebsite()
	{
		driver.get("https://olympiad.innovationm.com");
		logger.info("Opening website");
		if(driver.getTitle().contains("Hindustan"))
		{
			Assert.assertTrue(true);
		}
	}

	@Test(priority = 1, enabled = true)
	public void registerSchool() throws InterruptedException, IOException {
		driver.get("https://olympiad.innovationm.com/register");
		Select stateDropdown = new Select(driver.findElement(By.id("populateState")));
		stateDropdown.selectByValue("11");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		Select districtDropdown = new Select(driver.findElement(By.id("populateDistrict")));
		districtDropdown.selectByVisibleText("Agra");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		Select schoolDropdown = new Select(driver.findElement(By.id("populateSchool")));
		//		schoolDropdown.selectByValue("601");
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

		TakesScreenshot ss = ((TakesScreenshot)driver);
		File source = ss.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"/Screenshot"+"Registration page"+".png");
		FileUtils.copyFile(source, destination);
		logger.info("SS taken");

		//		WebDriverWait wait2 = new WebDriverWait(driver, 10);
		//		wait2.until(ExpectedConditions.elementToBeClickable(By.id("verify")));

		WebElement verifybtn = driver.findElement(By.id("verify"));
		String mainwindowhandle = driver.getWindowHandle();
		//		Actions actions = new Actions(driver);
		//		actions.moveToElement(verifybtn).build().perform();

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
//		driver.findElement(By.id("digit_two")).sendKeys(two);
//		Thread.sleep(3000);
//		driver.findElement(By.id("digit_three")).sendKeys(three);
//		Thread.sleep(3000);
//		driver.findElement(By.id("digit_four")).sendKeys(four);
//		Thread.sleep(3000);
//		driver.findElement(By.id("digit_five")).sendKeys(five);
//		Thread.sleep(3000);
//		driver.findElement(By.id("digit_six")).sendKeys(six);
		driver.findElement(By.className("verify-btn")).click();
		Thread.sleep(3000);
		
		TakesScreenshot takesScreenshot = ((TakesScreenshot)driver);
		File sourFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File desFile = new File(System.getProperty("user.dir")+"/Screenshot"+ "Registration success verification SS"+".png");
		FileUtils.copyFile(sourFile, desFile);
	}
	
	@AfterTest(enabled = true)
	public String getOTP() {
		String otpString = "";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://ht-codeathon.cmhlyd83nflz.ap-south-1.rds.amazonaws.com:3306/ht_olympiad", "ht-user", "HT@wdse21");
			Statement st = con.createStatement();
			logger.info("Connection established");
			ResultSet rs = st.executeQuery("select otp from ht_olympiad.otps order by otps.created_at desc limit 1");
			while(rs.next())
			{
				otpString += rs.getString("otp");
				System.out.println(otpString);
			}
			con.close();
		}
		catch (Exception e) {
			System.out.println(e+"failed to establish connection");
		}
		return otpString;	
	}

	@AfterSuite(enabled = true)
	public void tearDown(){
		driver.quit();
	}
}
