package Tests;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mongodb.MapReduceCommand.OutputType;
import com.relevantcodes.extentreports.ExtentTest;

import GenricWraper.TestBase;
import Pages.Page1;



public class Test1 extends TestBase{
	public WebDriver driver;
	String browserName;
	String reportName;
	
	ExtentTest logger;
	
	
	String deviceName;
	String udid;
	String platformName;
	String platformVersion;

	String testType;

	
@Parameters({"testType","deviceName","udid","platformName","platformVersion","browserName","reportName"})
public Test1(String testType,String deviceName,String udid,String platformName,String platformVersion,String browserName,String reportName) throws MalformedURLException{
	super();
	this.testType=testType;
	if(testType.equals("Mobile")) {
		
		this.deviceName=deviceName;
		this.udid=udid;
		this.platformName=platformName;
		this.platformVersion=platformVersion;
		this.browserName=browserName;
		this.reportName=reportName;

			
		this.driver=driverInitializer(this.deviceName, this.udid, this.platformName, this.platformVersion, this.browserName);//, this.noReset

		
	}else {	
	
		this.browserName=browserName;
		this.reportName=reportName;
			
		this.driver=driverInitializer(this.browserName);
	}
		
	}
	
	@Test
	public void test1() throws Exception {
		extentReportSetUp(this.reportName);
		logger=extent.startTest("Test1");
	try {
		logger=	new Page1(driver)
		.page1_validation(logger,testType);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	driver.close();
	
	
	extent.endTest(logger);
	extent.flush();
	}

}
