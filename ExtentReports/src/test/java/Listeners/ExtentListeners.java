package Listeners;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;


//This is the listener class for extent report
public class ExtentListeners implements ITestListener {

	static Date d = new Date();
	static ExtentTest test=null;
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	private static ExtentReports extent = ExtentManager
			.createInstance(System.getProperty("user.dir") + "\\reports\\" + fileName);
	
	/*
	 * ExtentLoggerReporter logger = new ExtentLoggerReporter("user/build/name/");
	 * ExtentReporter extent = new ExtentReports(); extent.attachReporter(logger);
	 */
	

	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		
		
	test = extent
				.createTest(result.getTestClass().getName() + "     @TestCase : " + result.getMethod().getMethodName());
		
		
		testReport.set(test);

	}

	public void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);

	}

	public void onTestFailure(ITestResult result) {

		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get()
				.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
						+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
						+ " \n");

		try {

			ExtentManager.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName).build());
		} catch (IOException e) {

		} catch (WebDriverException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		String failureLogg = "TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);

	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

		if (extent != null) {

			extent.flush();
		}

	}
	public ExtentTest getTestInstance() {
		return test;
	}
}
