package listeners;

import java.io.IOException;

import org.testng.ITestContext;
//import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtilities;
public class CustomListeners extends TestBase implements ITestListener {

	public Boolean canTestRun = true;

	

	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtilities.captureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" Failed with exception"+result.getThrowable());
		test.log(LogStatus.FAIL,test.addScreenCapture(TestUtilities.filePath));
		repo.endTest(test);
		repo.flush();//this is important else report will not be generated
		System.out.println(result);
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\"href="+TestUtilities.ssPath+">Screenshot</a>");// this code is for hyperlink
		Reporter.log("<br>");
		Reporter.log("<br>");
		//this code is for small image to shop up which is clickable
		Reporter.log("<a target=\"_blank\"href="+TestUtilities.ssName+"><img src ="+TestUtilities.filePath+" heigth=200 width=200></img></a>");

	
	}

	@Override
	public void onTestStart(ITestResult result) {
		test = repo.startTest(result.getName().toUpperCase());
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		if(!TestUtilities.isTestRunnable(result.getName(), excel))
		{
			canTestRun = false;
			throw new SkipException("Skipping test: "+result.getName()+" as run mode is not Y if you wish to run the Test set it as 'Y'");
			
		}
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//System.out.println("printing for onsuccess method value of result.getname is "+result.getName());
		//System.out.println("printing for onsuccess method value of result.getTestname is "+result.getTestName());
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" is PASS");
		repo.endTest(test);
		repo.flush();//this is important else report will not be generated
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP,result.getName()+" is skippied as the Runnable flag is " + canTestRun);
		repo.endTest(test);
		repo.flush();//this is important else report will not be generated
		
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
}
