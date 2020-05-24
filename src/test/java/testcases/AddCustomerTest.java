 package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtilities;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtilities.class ,dataProvider = "dp")
	public void addCustomerTest(String firstName,String lastName, String postCode, String alertText, String runMode) throws Exception
	{
		
		if(!runMode.equalsIgnoreCase("Y"))
		{
			test.log(LogStatus.INFO,"Skipping entry: " +firstName+" as run mode is not Y if you wish to run the Test set it as 'Y'" );
			throw new SkipException("Skipping entry: " +firstName+" as run mode is not Y if you wish to run the Test set it as 'Y'");
			
		}
		//driver.get("http://www.way2automation.com/angularjs-protractor/banking/#/manager");
		//Thread.sleep(2000);
		log.debug("Entering Add Customer Method");
		//driver.findElement(By.cssSelector(OR.getProperty("addCustTest"))).click();
		
		//driver.findElement(By.cssSelector(OR.getProperty("fName"))).sendKeys(firstName);
		type("fName_CSS", firstName);
		
		//driver.findElement(By.cssSelector(OR.getProperty("lName"))).sendKeys(lastName);
		type("lName_XPATH", lastName);
		//driver.findElement(By.cssSelector(OR.getProperty("postCode"))).sendKeys(postCode);
		type("postCode_CSS", postCode);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(OR.getProperty("btnAddC_CSS"))).click();
		Thread.sleep(2000);
		Alert alert =wait.until(ExpectedConditions.alertIsPresent());
		String alertMsg = alert.getText();
		System.out.println(alertMsg);
		
		alert.accept();
		System.out.println(alertText);
		Assert.assertTrue(alertMsg.contains(alertText));
		
		
		log.debug(alertMsg);
		Reporter.log(alertMsg);
		//Assert.fail();
	}
	
	@DataProvider
	public Object[][] getData()
	
	{
		String sheetName="AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][]data = new Object[rows-1][cols];
		for(int i=2;i<=rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				data[i-2][j]= excel.getCellData(sheetName, j, i);
			}
		}
		
		return data;
		
	}

}
