package testcase;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCase3 {

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
	public void search(String username, String password) {

		System.out.println(username + " " + password);

	}

}
