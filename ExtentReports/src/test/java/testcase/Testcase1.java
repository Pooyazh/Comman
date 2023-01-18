package testcase;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Testcase1 {
	
	@Test
	public void doLogin() {

		//test = extent.createTest("Login Test");
		System.out.println("Executing Login Test");
	}
//method for registration
	@Test
	public void doUserReg() {
		//this is for registration
		//test = extent.createTest("User Reg Test");
		Assert.fail("User Reg Test Failed");
	}

	@Test
	public void isSkip() {

		//test = extent.createTest("Skip Test");
		throw new SkipException("Skipping the test case");
	}

}
