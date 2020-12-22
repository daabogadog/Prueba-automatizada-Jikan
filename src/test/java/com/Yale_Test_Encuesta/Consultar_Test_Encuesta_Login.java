package com.Yale_Test_Encuesta;

import static org.testng.Assert.assertTrue;

import java.awt.TexturePaint;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test_Encuesta_Login {

	private WebDriver driver;
	Consult_Page consult_page;

	// Elementos del Login
	By TextUser = By.id("username");
	By TextPass = By.id("password");
	By BtnLogIn = By.id("kc-login");
	By Alerta = By.xpath("//*[@id=\"kc-content-wrapper\"]/div[1]");

	// elementos de la pagina Home
	By BtnPreguntas = By.xpath(
			"/html/body/app/vertical-layout-1/div/div/fuse-sidebar/navbar/navbar-vertical-style-1/div[2]/div[2]/fuse-navigation/div/fuse-nav-vertical-group/div[2]/fuse-nav-vertical-item/a");

	// elementos logout
	By UserLogin = By.xpath("//*[@id=\"container-3\"]/toolbar/mat-toolbar/div/div[2]/button[1]/span[1]/div");
	By Userlogout = By.xpath("//*[@id=\"mat-menu-panel-0\"]/div/button[3]");
   
	
	@BeforeClass
	@Parameters({ "URL_encuesta", "BrowserType" })
	public void setUp(String url, String browserType) throws Exception {

		consult_page = new Consult_Page(driver);

		if (browserType.equalsIgnoreCase("Chrome")) {
			driver = consult_page.chromeDriverConnection();
		} else if (browserType.equalsIgnoreCase("Firefox")) {
			driver = consult_page.FirefoxDriverConnection();
		} else if (browserType.equalsIgnoreCase("Edge")) {
			driver = consult_page.IEDriverConnection();
		}
		
		driver.manage().window().maximize();
		driver.get(url);
		Thread.sleep(2000);
		System.out.println("Navegador: " + browserType);
	}

	

	@DataProvider(name = "User_pass")
	public Object[][] User() {
		return new Object[][] { 
			    { "1", "itoadmin", "1T0s0ftw4r3" }, 
			    { "1", "ito", "1T0s0ftw4r3*" },
				{ "1", "", "1T0s0ftw4r3*" }, 
				{ "1", "itoadmin", "" }, 
				{ "1", "", "" },
				{ "2", "itoadmin", "1T0s0ftw4r3*" }

		};

	}

	@Test(dataProvider = "User_pass")
	public void Login(String date, String User, String Pass) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(TextUser));

		
		if (date == "1") {
			driver.findElement(TextUser).clear();
			driver.findElement(TextPass).clear();
			driver.findElement(TextUser).sendKeys(User);
			driver.findElement(TextPass).sendKeys(Pass);
			driver.findElement(BtnLogIn).click();
			assertTrue(driver.findElement(Alerta).isDisplayed());
		} else {
			driver.findElement(TextUser).clear();
			driver.findElement(TextPass).clear();
			driver.findElement(TextUser).sendKeys(User);
			driver.findElement(TextPass).sendKeys(Pass);
			driver.findElement(BtnLogIn).click();
		    wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(BtnPreguntas));
			assertTrue(driver.findElement(BtnPreguntas).isDisplayed());
			
		}
	}
	
	@Test
	public void Logout() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(BtnPreguntas));
		driver.findElement(UserLogin ).click();
		driver.findElement(Userlogout).click();
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(TextUser));
		assertTrue(driver.findElement(TextUser).isDisplayed());
		
	}
	
	
}
