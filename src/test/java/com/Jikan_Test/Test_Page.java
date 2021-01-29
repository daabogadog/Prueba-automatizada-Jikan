package com.Jikan_Test;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.junit.After;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class Test_Page {

	private WebDriver driver;
	Consult_Page consult_page;

	
	@BeforeClass
	@Parameters({ "URL", "BrowserType" })
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

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
	

	@DataProvider(name = "Anime")
	public Object[][] User() {
		return new Object[][] { 
			    { "Naruto"}, 
			    { "Pokemon"},
				{ "Death note"},

		};

	}

	@Test(dataProvider = "Anime")
	public void Validar_Busqueda_Anime(String Data) throws InterruptedException {
		
		consult_page.Consult(driver, Data);
		Thread.sleep(2000);
		assertTrue(consult_page.isHomePageDisplayed());
		assertTrue(consult_page.Request_true());
		Color ValidarColor = consult_page.ColorsElement(driver);
		assert ValidarColor.asRgb().equals("rgb(0, 255, 0)");	
		
	}
		
	
	@Test
	public void Validar_Link_Rotos() throws InterruptedException {
		
		Thread.sleep(2000);
		assertTrue(consult_page.chechingPageLink(driver),"Existen link rotos");
		
	}
	

}
