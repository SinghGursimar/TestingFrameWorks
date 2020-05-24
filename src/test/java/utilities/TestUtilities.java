package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;
import org.apache.commons.io.FileUtils;

import base.TestBase;

public class TestUtilities extends TestBase {
	public static String ssPath;
	public static String ssName;
	public static String filePath;
	public static void captureScreenShot() throws IOException
	{
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		ssName = "error.jpg";
		Date d = new Date();
		String s =d.toString().replace(" ", "_").replace(":","_");
		ssName = "error"+"_"+s+".jgp";
		 ssPath = (System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\");
		 filePath=(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+ssName);
		
		System.out.println("file path is " + ssPath);
		File screenshotName = new File(ssPath+ssName);
		
		FileUtils.copyFile(screenshotFile, screenshotName);
		
	}
	@DataProvider(name = "dp")
	public Object[][] getData(Method m)
	
	{
		//Method is from refelction API
		String sheetName=m.getName();
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
	
	public static boolean isTestRunnable(String TestName,Excel_Reader_New excel)
	{
		String sheetName = "test_suite";
		String test;
		int rowCount = excel.getRowCount(sheetName);
		for(int i=2;i<=rowCount;i++)
		{
			test = excel.getCellData(sheetName, 0, i);
		//	System.out.println("from is test runnable method Test name is "+test);
			if(test.equalsIgnoreCase(TestName))
			{
				String runMode =  excel.getCellData(sheetName, 1, i);
			//	System.out.println("from is test runnable method Testmode name is "+runMode);
				if(runMode.equalsIgnoreCase("Y"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
				
		}	return false;// this is if it goes into the for loof and does not find Y or N and finds any other value
	}
		
	

	
}
