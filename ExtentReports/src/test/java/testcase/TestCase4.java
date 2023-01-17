package testcase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase4 {
	static WebDriver driver = null;

	@DataProvider(name = "excel-data")

	public Object[][] excelDP() throws IOException {

		Object[][] arrObj = getExcelData("C:\\Users\\MohanSusha\\OneDrive\\Desktop\\Susha\\credentials.xlsx", "Sheet1");
		return arrObj;
	}

	public Object[][] getExcelData(String fileName, String sheetName) {

		Object[][] data = null;

		try

		{

			FileInputStream fis = new FileInputStream(fileName);

			XSSFWorkbook wb = new XSSFWorkbook(fis);

			XSSFSheet sh = wb.getSheet(sheetName);

			XSSFRow row = sh.getRow(0);

			int noOfRows = sh.getPhysicalNumberOfRows();

			int noOfCols = row.getLastCellNum();

			Cell cell;

			data = new String[noOfRows - 1][noOfCols];

			for (int i = 1; i < noOfRows; i++) {

				for (int j = 0; j < noOfCols; j++) {

					row = sh.getRow(i);

					cell = row.getCell(j);

					try {

						data[i - 1][j] = cell.getStringCellValue();

					} catch (Exception e) {

						data[i - 1][j] = ("" + cell.getNumericCellValue()).replace(".0", "").trim();
					}

				}

			}

		}

		catch (Exception e) {

			System.out.println("The exception is: " + e.getMessage());

		}

		return data;

	}

	@Test(dataProvider = "excel-data")
	public void login(String username, String password) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\MohanSusha\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://accounts.zoho.in/signin?servicename=ZohoHome&signupurl=https://www.zoho.com/signup.html");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@id='login_id']")).sendKeys(username);
		driver.findElement(By.xpath("(//button[@id='nextbtn'])[1]")).click();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@class='btn blue']/following::*[text()='Sign in']")).click();
		Thread.sleep(2000);
		System.out.println(username + " " + password);

	}

	@AfterSuite
	public void closeBrowser() throws InterruptedException {

		driver.close();
	}
}
