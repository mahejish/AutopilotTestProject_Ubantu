package Pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentTest;

import GenricWraper.GenricMethods;

public class Page1 extends GenricMethods{
	public WebDriver driver;
	String excelfilepath;
	String excelTestCaseName;
	String excelSheetnumber;
	
	
	public Page1(WebDriver driver) {
		super(driver);
		this.driver=driver;
		try {
			prop= new Properties();
			
			FileInputStream fileInputStream= new FileInputStream("/home/qbuser/Documents/Workspace/DEMO_parallel/testPack/Locators/page1.properties");
		
			prop.load(fileInputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.excelfilepath=prop.getProperty("excelfilepath");
		this.excelTestCaseName=prop.getProperty("excelTestCaseName");
		this.excelSheetnumber=prop.getProperty("excelSheetnumber");
	}

	public ExtentTest page1_validation(ExtentTest logger,String testType) throws NumberFormatException, IOException {
		
		driver.get("https://www.autopilothq.com/");
		
		excelDataInitializer(this.excelfilepath+":"+this.excelSheetnumber+":"+this.excelTestCaseName);

		System.out.println(driver.getTitle());
		
		
		Actions actions= new Actions(driver);
		
		report("Test Started WebSite Loaded", "PASS",logger);
		if(elementIsPresent(prop.getProperty("frame2"))) {
			if(webelement(prop.getProperty("frame2")).isDisplayed()) {
				driver.switchTo().frame(webelement(prop.getProperty("frame2")));
				click(prop.getProperty("acceptBTN"));}
			}
		if(testType.equalsIgnoreCase("Mobile")) {
			click(prop.getProperty("hmbrgr_menu"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			click(prop.getProperty("freeTrail_Mob"));
		}else {
		click(prop.getProperty("freeTrail_Dsktp"));
		}
		
		
		report("Clicked on free trail link text and navigated to Free 30 Day trail Page", "PASS",logger);
		sendValue(prop.getProperty("firtsNameTXT_Box"), testDataValues.get("firstName"));
		sendValue(prop.getProperty("lastNameTXT_Box"), testDataValues.get("lastName"));
		sendValue(prop.getProperty("emailIdTXT_Box"), testDataValues.get("email_Id"));
		sendValue(prop.getProperty("passwordTXT_Box"), testDataValues.get("password"));
		report("Sucessfuly entered enterd details", "PASS",logger);
		
		
		if(elementIsPresent(prop.getProperty("frame2"))) {
			if(webelement(prop.getProperty("frame2")).isDisplayed()) {
				driver.switchTo().frame(webelement(prop.getProperty("frame2")));
				click(prop.getProperty("acceptBTN"));}
			}
		actions.moveToElement(webelement(prop.getProperty("tAndc_Check_Box"))).build().perform();
		click(prop.getProperty("tAndc_Check_Box"));
		
		report("Sucessfuly clicked on tAndc_Check_Box", "PASS",logger);
		click(prop.getProperty("startFree_BTN"));
		report("startFree_BTN is working  and user get navigated to next page", "PASS",logger);
		
		
		sendValue(prop.getProperty("companyTXT_Box"), testDataValues.get("companyWebsite"));
		report("Sucessfully navigated to next page  and entered companyWebsite ", "PASS",logger);
		click(prop.getProperty("nextBTN"));
		
		report("clicked on nextBTN & Sucessfully navigated to next page ", "PASS",logger);
		sendValue(prop.getProperty("companyWebsiteTXTBox"), testDataValues.get("firstName"));
		sendValue(prop.getProperty("LinkedInUsername"), testDataValues.get("lastName"));
		sendValue(prop.getProperty("intentToUse"), testDataValues.get("email_Id"));
		report("Sucessfully entered the details", "PASS",logger);
		if(elementIsPresent(prop.getProperty("frame2"))) {
			if(webelement(prop.getProperty("frame2")).isDisplayed()) {
				driver.switchTo().frame(webelement(prop.getProperty("frame2")));
				click(prop.getProperty("acceptBTN"));}
			}
		click(prop.getProperty("submit"));
		
		report("clicked on submit BTN. Sucessfully navigated to next page and entered the details ", "PASS",logger);
		if(elementIsPresent(prop.getProperty("frame2"))) {
			if(webelement(prop.getProperty("frame2")).isDisplayed()) {
				driver.switchTo().frame(webelement(prop.getProperty("frame2")));
				click(prop.getProperty("acceptBTN"));}
			}
		
		if(webelement(prop.getProperty("sucessMsg")).isDisplayed()) {
			report("SignUp reques submitesd and got the sucess msg ", "PASS",logger);
			click(prop.getProperty("goHomeBTN"));
			report("Home BTN is working fine.User get returned to home page ", "PASS",logger);
		}else {
			report("SignUp reques submitesd and got the sucess msg ", "FAIL",logger);
		}	
		
		
		
		return logger;
	}
}
