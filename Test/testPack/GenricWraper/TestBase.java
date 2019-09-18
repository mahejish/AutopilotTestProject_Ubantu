package GenricWraper;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;

public class TestBase {
	
	public ExtentReports extent;
	public ExtentTest logger;
	
	public WebDriver driver;
	public WebDriver driverInitializer(String deviceName,String udid,String platformName,String platformVersion,String browserName) throws MalformedURLException {//,String noReset
		DesiredCapabilities cap= new DesiredCapabilities();
		cap.setCapability("deviceName", deviceName);//Galaxy Tab A (2017)
		cap.setCapability("udid", udid);
		cap.setCapability("platformName", platformName);
		cap.setCapability("platformVersion", platformVersion);
		cap.setCapability("browserName", "Chrome");
//		cap.setCapability("noReset", true);
		
			URL url= new URL("http://127.0.0.1:4723/wd/hub");
			driver= new AppiumDriver(url,cap);

		    
		
		return driver;
	}
	public WebDriver driverInitializer(String browserName) {
		if(browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "/home/qbuser/Documents/chromedriver");
			driver= new ChromeDriver();
			
			
		}else if(browserName.equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.gecko.driver", "/home/qbuser/Documents/geckodriver");
			driver= new FirefoxDriver();
			
		}
		driver.manage().deleteAllCookies();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		return driver;
	}
	
	/**
	 * Function extentReportSetUp : Used for extend report pre- setup
     * @author Jishnu Mahesh
     */	
	public void extentReportSetUp(String reportName) {
		extent=new ExtentReports("/home/qbuser/Documents/Workspace/Test/test-Output",true);
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-Report/Reports/"+reportName+".html", true);

		extent.addSystemInfo("Host Name", "SoftwareTesting");
		extent.addSystemInfo("Environment", "QAtest");
		extent.addSystemInfo("User Name", "JishnuMahesh");

		extent.loadConfig(new File("/home/qbuser/Documents/Workspace/DEMO_parallel/testPack/ExtentConfig/extent-config.xml"));
	}
	/**
	 * Function extentReportSetUp : Used for logging PASS or FAIL into report along with screenshots and description 
     * @author Jishnu Mahesh
     */	
	public void report(String msg,String status) throws IOException {
		String screenshot=new GenricMethods(driver).getScreenshot();
		if(status.equalsIgnoreCase("PASS")) {
			logger.log(LogStatus.PASS, msg+logger.addScreenCapture(screenshot));
		}else {
			logger.log(LogStatus.FAIL, msg+logger.addScreenCapture(screenshot));
		}
			
	}
}
