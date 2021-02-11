package com.ht_olympiad.main;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.ht_olympiad.utilities.readConfig;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;

public class baseClass {
	
	readConfig rconfig = new readConfig();
	public String baseURL = rconfig.getURL();
	public static WebDriver driver;
	public static Logger logger;

	@BeforeSuite
//	@Parameters("browser")
	public void setUp() throws InterruptedException
	{
		logger = Logger.getLogger("HT_Olympiad");
		PropertyConfigurator.configure("log4j.properties");

//		if(browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
//		}
//		else if(browser.equals("firefox")) {
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//		}	
		driver.get(baseURL);
		logger.info("Opening website");
		Thread.sleep(3000);
	}

	@AfterSuite
	public void tearDown(){
		driver.quit();
	}

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
			}
			con.close();
		}
		catch (Exception e) {
			System.out.println(e+"failed to establish connection");
		}
		return otpString;	
	}

	public void captureScreenshot(WebDriver driver, String name) throws IOException {
		TakesScreenshot tScreenshot = ((TakesScreenshot)driver);
		File source = tScreenshot.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir") + "/Screenshot/" + name + ".png");
		FileUtils.copyFile(source, destination);
		System.out.println("Took screenshot for " +name+" failure");
	}
}
