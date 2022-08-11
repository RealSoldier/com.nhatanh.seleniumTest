package com.nhatanh.test01;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.internal.runners.statements.ExpectException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.AbstractParallelWorker.Arguments;

public class ClickDocTest {
	private WebDriver driver;
	String url = "https://int.clickdoc.de/cd-de/";
	
	@BeforeTest
	public void testSetup() {
		System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to(url);
		driver.manage().window().maximize();
		WebElement cookie = driver.findElement(By.xpath("/html/body/app-root/div[2]/app-consent-gdpr-container/app-modal-wrapper/div/div[2]/div[3]/button[1]"));
		if (cookie.isDisplayed()) {
			cookie.click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	@Test(priority = 0)
	private void userAnmelden() throws InterruptedException {
		//Step1: click on profil from navbar
		WebElement profil = driver.findElement(By.xpath("/html/body/app-root/div[2]/div[1]/app-header/div/div/div/div[2]/ul/li[4]/a/span[2]"));
		profil.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Step2: switch view to login window 
		WebElement loginWindow = driver.findElement(By.id("iframeDialog"));
		driver.switchTo().frame(loginWindow);
		
		//Step3: finding and entering into email and password elements
		WebElement userEmail = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-0")));
		WebElement password = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-1")));
		userEmail.sendKeys("qa_candidate_cgm@mailinator.com");
		password.sendKeys("1234,56Qq");
		
		//Step4: click on Anmelden Button
		WebElement anmeldenButton = driver.findElement(By.xpath("//*[@id=\"body\"]/app-root/div/div/main/app-login/form/div[2]/div/div/div/button[1]"));
		anmeldenButton.click();
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	@Test(priority = 1)
	private void count_E_Rezepte() throws InterruptedException {
		//Step5: go back to main window of app
		driver.switchTo().defaultContent();
		//finding and using javascript to switch to E-Rezept from navbar
		WebElement e_rezept = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/div[2]/div[1]/app-header/div/div/div/div[2]/ul/li[2]/a/span[1]")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", e_rezept);
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Step6: count the number of e-receipts available
		int count_number_of_e_rezept = driver.findElements(By.cssSelector(".prescription.ng-star-inserted")).size();
		//int count_number_of_e_rezept = driver.findElements(By.xpath("/html/body/app-root/div[2]/div[1]/div/app-erezept-erezept-guard-resolver/div/app-prescription-active-list/div/div[2]/app-prescription-list")).size();
		System.out.println("Die Zahl aktueller E-Rezepte: " + count_number_of_e_rezept); //it return to 1 receipt because there are 2 receipts displayed on the app, but those 2 are duplicates
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
