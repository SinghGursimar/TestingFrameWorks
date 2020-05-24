package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import base.TestBase;

public class BMLoginTest extends TestBase {
	public static Logger logg = LogManager.getLogger(BMLoginTest.class.getName());
@Test
public void BankManagerLoginTest() throws Exception
{	logg.debug("inside login test method");
	//driver.findElement(By.cssSelector(OR.getProperty("bmlbtn"))).click();
	verifyText("abc", "xyz");
	click("bmlbtn_CSS");
	Thread.sleep(1000);
	logg.debug("Login link clicked ");
	Assert.assertTrue(isElementVisible(By.cssSelector(OR.getProperty("addCustTest_CSS"))));
	WebElement element = driver.findElement(By.cssSelector(OR.getProperty("addCustTest_CSS")));
	System.out.println(element.isDisplayed());
	element.click();
	Thread.sleep(1000);
	logg.debug("Login is succesfull");
	Reporter.log("Login is succesfull");
	//Assert.fail();
}


}
