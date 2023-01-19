package testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Listeners.ExtentListeners;

public class TestCase2 extends ExtentListeners {
	public static ExtentTest test;
	//public static ExtentReports extent;
	public static WebDriver driver;
	public String driverPath = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe";

	@BeforeMethod
	public void startBrowser() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		driver.close();

	}

	@Test(priority = 1)
	public void doSearch() throws InterruptedException {
		test=getTestInstance();
		test.log(Status.INFO,"Initating the test");
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		driver.findElement(By.name("q")).sendKeys("selenium");
		driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).click();
		System.out.println("Title is :" + driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Sselenium - Google Search");
		Thread.sleep(2000);

	}

	@Test(priority = 2)
	public void doImFeelingLucky() throws InterruptedException {
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[2]")).click();
		Thread.sleep(2000);

	}
	
	  @Test(priority=3) 
	  public void isSkip() {
	  
	  throw new SkipException("Skipping the test case"); }
	 
}
