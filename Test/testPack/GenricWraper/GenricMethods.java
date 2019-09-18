package GenricWraper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenricMethods {

	public WebDriver driver;
	public static Properties prop;
	public ExtentReports extent;
	public ExtentTest logger;
	public static HashMap<String,String> testDataValues;
	
	public GenricMethods(WebDriver driver) {
		this.driver= driver;
	}
	
	
	/**
	 * Function excelDataInitializer : Used to store excel test datas
     * @author Jishnu Mahesh
     */
	public static void excelDataInitializer(String excelDetails) throws NumberFormatException, IOException {
			
			HashMap<String,String> testDatas= new HashMap<String,String>();
			File file= new File(excelDetails.split(":")[0].trim());
			String sheetNumber=excelDetails.split(":")[1].trim();
			String testCaseName= excelDetails.split(":")[2].trim();
			
			
			Sheet sheet= SpreadSheet.createFromFile(file).getSheet(Integer.parseInt(sheetNumber));
			int nColIndex =0;
			int rowIndex=0;
			MutableCell cell = null;
			for( int nRowIndex = 1;nRowIndex <=sheet.getRowCount(); nRowIndex++)
	        {
				cell = sheet.getCellAt(nColIndex, nRowIndex);
				if(cell.getValue().equals(testCaseName)) {
					rowIndex=nRowIndex;
					break;
				}
	        }
			MutableCell cell1 = null;
			MutableCell cell2 = null;
			for( nColIndex = 1;nColIndex < sheet.getColumnCount(); nColIndex++)
	        {
				cell1 = sheet.getCellAt(nColIndex, 0);
				cell2 = sheet.getCellAt(nColIndex, rowIndex);
				testDatas.put(cell1.getValue()+"", cell2.getValue()+"");
				
	        }
			
			testDataValues= testDatas;
			sheet=null;
		}
	/**
	 * Function extentReportSetUp : Used for extend report pre- setup
     * @author Jishnu Mahesh
     */	
	public void extentReportSetUp(String reportName) {
		extent=new ExtentReports("/home/qbuser/Documents/Workspace/Test/test-Output",true);
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-Report/Reports/"+reportName+".html", true);
		System.out.println(System.getProperty("user.dir"));
		extent.addSystemInfo("Host Name", "SoftwareTesting");
		extent.addSystemInfo("Environment", "QAtest");
		extent.addSystemInfo("User Name", "JishnuMahesh");

		extent.loadConfig(new File("/home/qbuser/Documents/Workspace/Test/extent-config.xml"));
	}
	/**
	 * Function extentReportSetUp : Used for killing extend report instances
     * @author Jishnu Mahesh
     */	
	public void reportTearDwn() {
		
		extent.endTest(logger);
		extent.flush();
		//extent.close();
		
	}
	/**
	 * Function extentReportSetUp : Used for taking screenshots ,which are used for generating extend report
     * @author Jishnu Mahesh
     */	
	public String getScreenshot() throws IOException {
		String dateName=new SimpleDateFormat("yyyyMMddhhmmssss").format(new Date());
		File source=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destination="/home/qbuser/Documents/Workspace/DEMO_parallel/test-Report/Screenshots/"+dateName+".png";
		FileUtils.copyDirectory(source, new File(destination));
		
		return destination;
		
		
	}
	/**
	 * Function extentReportSetUp : Used for logging PASS or FAIL into report along with screenshots and description 
     * @author Jishnu Mahesh
     */	
	public ExtentTest  report(String msg,String status,ExtentTest logger) throws IOException {
		String screenshot=getScreenshot();
		if(status.equalsIgnoreCase("PASS")) {
			logger.log(LogStatus.PASS, msg+logger.addScreenCapture(screenshot));
		}else {
			logger.log(LogStatus.FAIL, msg+logger.addScreenCapture(screenshot));
		}
		return logger;	
	}
	
	/**
	 * Function click : Help us to click on a webelement  
     * @author Jishnu Mahesh
     */
	public void click(String locatorProp) {
		
		String locator=locatorProp.split("===")[0];
		String addrs=locatorProp.split("===")[1];
		if(locator.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(addrs)).click();
		}else if(locator.equalsIgnoreCase("name")){
			driver.findElement(By.name(addrs)).click();
		}else if(locator.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorProp)).click();
		}
		
		
	}
	/**
	 * Function sendValue : Help us to enter value into a text box 
     * @author Jishnu Mahesh
     */
	public void sendValue(String locatorProp,String value) {
		
		String locator=locatorProp.split("===")[0];
		String addrs=locatorProp.split("===")[1];
		if(locator.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(addrs)).sendKeys(value);
		}else if(locator.equalsIgnoreCase("name")){
			driver.findElement(By.name(addrs)).sendKeys(value);
		}else if(locator.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorProp)).sendKeys(value);
		}
		
		
	}
	/**
	 * Function click : Return the corresponding webelement 
     * @author Jishnu Mahesh
     */
	public WebElement webelement(String locatorProp) {
		WebElement ele = null;
		String locator=locatorProp.split("===")[0];
		String addrs=locatorProp.split("===")[1];
		if(locator.equalsIgnoreCase("xpath")) {
			ele= driver.findElement(By.xpath(addrs));
		}else if(locator.equalsIgnoreCase("name")){
			ele=  driver.findElement(By.name(addrs));
		}else if(locator.equalsIgnoreCase("id")){
			ele=  driver.findElement(By.id(addrs));
		}
		
		return ele;
	}
	
	/**
	 * Function elementIsPresent : Return True?False ==> webelement is present in the DOM or not
     * @author Jishnu Mahesh
     */
	public boolean elementIsPresent(String locatorProp) {
		boolean flag = false ;
		String locator=locatorProp.split("===")[0];
		String addrs=locatorProp.split("===")[1];
		if(locator.equalsIgnoreCase("xpath")) {
			flag= (driver.findElements(By.xpath(addrs)).size()!=0);
		}else if(locator.equalsIgnoreCase("name")){
			flag= (driver.findElements(By.name(addrs)).size()!=0);
		}else if(locator.equalsIgnoreCase("id")){
			flag= (driver.findElements(By.id(addrs)).size()!=0);
		}
		
		return flag;
	}
	
	
	
	/**
	 * Function click : Return the corresponding webelement 
     * @author Jishnu Mahesh
     */
	public void waitForSeconds(int time) {
		driver.manage().timeouts().implicitlyWait(time,TimeUnit.SECONDS);
	}

	
}
