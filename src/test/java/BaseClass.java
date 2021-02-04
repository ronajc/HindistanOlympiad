import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    WebDriver driver;

    @BeforeSuite
    public void setUp()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1, enabled = true)
    public void openWebsite()
    {
        driver.get("https://olympiad.innovationm.com");
        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Hindustan Olympiad 2020, Hindustan Olympiad Latest news in hindi, हिन्दुस्तान ओलंपियाड 2020");
    }

    @Test(priority = 2, dependsOnMethods = "openWebsite", enabled = true)
    public void registerSchool() throws InterruptedException, IOException {
        driver.get("https://olympiad.innovationm.com/register");
        Select stateDropdown = new Select(driver.findElement(By.id("populateState")));
        stateDropdown.selectByValue("11");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Select districtDropdown = new Select(driver.findElement(By.id("populateDistrict")));
        districtDropdown.selectByVisibleText("Agra");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Select schoolDropdown = new Select(driver.findElement(By.id("populateSchool")));
        schoolDropdown.selectByValue("600");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        TakesScreenshot ss = ((TakesScreenshot)driver);
        File file = ss.getScreenshotAs(OutputType.FILE);
        File destination = new File(System.getProperty("user.dir") + "/Screenshot/" + "Registration Page" + ".png");
        FileUtils.copyFile(file, destination);

    }

    @AfterSuite
    public void tearDown(){
        driver.quit();
    }
}
