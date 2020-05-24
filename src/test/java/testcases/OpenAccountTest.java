 package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtilities;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtilities.class,dataProvider = "dp")
	public void openAccountTest(String customer,String currency) throws Exception
	{
		click("openaccount_CSS");
		select("customer_CSS", customer);
		Thread.sleep(1000);
		select("currency_CSS", currency);
		Thread.sleep(1000);
		click("process_XPATH");
		Alert alert =wait.until(ExpectedConditions.alertIsPresent());
		String alertMsg = alert.getText();
		System.out.println(alertMsg);
		alert.accept();
		//System.out.println(alertText);
		//Assert.assertTrue(alertMsg.contains(alertText));
		log.debug(alertMsg);
		Reporter.log(alertMsg);
		test.log(LogStatus.INFO,alertMsg);
		
		
	}
	
	

}
