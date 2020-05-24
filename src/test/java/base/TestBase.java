package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
//import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Excel_Reader_New;
import utilities.ExtentManager;
import utilities.TestUtilities;

import org.apache.logging.log4j.*;

public class TestBase {
	/*
	 * All initialisation goes here
	 * WebDriver  -- done
	 * properties-- done
	 * reports
	 * logs -- log4j , log4j.properties, logger class
	 * DB
	 * Excel
	 * Mail
	 * 
	 */
	public static WebDriver driver = null;
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis ;
	public static Logger log = LogManager.getLogger(TestBase.class.getName());
	public static Excel_Reader_New excel = new Excel_Reader_New(System.getProperty("user.dir") +"\\src\\test\\resources\\excelSheet\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports repo = ExtentManager.getInstance();
	public static ExtentTest test;
	public static SoftAssert sa = new SoftAssert();
	public static WebElement dropdown;
	public String extra;
	//We want that this method is called before any Test Case in the the Suite
	//so we will set it under the TESTNG annotation
	
	public static void verifyText(String expected,String actual) throws IOException
	{
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable e) {
			TestUtilities.captureScreenShot();
			Reporter.log("Verification Failure :"+e.getMessage());
			Reporter.log("<a target=\"_blank\"href="+TestUtilities.ssPath+">Screenshot</a>");// this code is for hyperlink
			Reporter.log("<br>");
			Reporter.log("<br>");
			//this code is for small image to shop up which is clickable
			Reporter.log("<a target=\"_blank\"href="+TestUtilities.ssName+"><img src ="+TestUtilities.filePath+" heigth=200 width=200></img></a>");
			
			test.log(LogStatus.FAIL," Failed with exception"+e.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtilities.filePath));
		}
		
	}
	public void click(String locator)
	{
		if(locator.endsWith("_CSS"))
		{
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on --"+locator);
		
	}
	public void type(String locator,String value)
	{
		if(locator.endsWith("_CSS"))
		{
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			test.log(LogStatus.INFO,"using xpath locator technique");
		}
		test.log(LogStatus.INFO, "Typing in --"+locator+" And sending value "+value);
	}
	public void select(String locator, String value)
	{
		if(locator.endsWith("_CSS"))
		{
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		Select s = new Select(dropdown);
		s.selectByVisibleText(value);
		
		}
		else if(locator.endsWith("_XPATH"))
		{
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
			Select s = new Select(dropdown);
			s.selectByVisibleText(value);
			test.log(LogStatus.INFO,"using xpath locator technique");
		}
		test.log(LogStatus.INFO, "Selecting from Dropdown:-"+locator+" And  value "+value);
		
	}
	@BeforeSuite
	public void setUp()
	{
		
		try {
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			config.load(fis);
			log.debug("Config file loaded");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		 try {
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\OR.properties");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			OR.load(fis);
			log.debug("Object Repository file loaded");  
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		
		if(config.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			//C:\Users\sps\Eclipse-2020-Workspace\ZFramework_DataDriven
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe" );
			 driver = new ChromeDriver();
		}
		else if(config.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			//C:\Users\sps\Eclipse-2020-Workspace\ZFramework_DataDriven
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe" );
			driver = new FirefoxDriver();
		}
		else if(config.getProperty("browser").equalsIgnoreCase("ie"))
		{
			//C:\Users\sps\Eclipse-2020-Workspace\ZFramework_DataDriven
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe" );
			driver = new InternetExplorerDriver();
		}
		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to"+ config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		wait=new WebDriverWait(driver, 5);
		
	}
	
	public boolean isElementVisible(By by)
	{
		try
		{
			driver.findElement(by);
			return true;
			
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
	}
	
	@AfterSuite
	public void tearDown() throws Exception
	{
		if(driver!=null)
		{
			Thread.sleep(2000);
			log.debug("tear down initiated quitting browser"); 
		driver.quit();
		
		}
	}

}
