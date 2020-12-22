package com.Yale_Test_Pedido;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Yale_Test_Encuesta.Consult_Page;

public class RevisarLinks {

	private WebDriver driver;
	Consult_Page consult_page;
	

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
	
	@Test
	public void VerificarLink() {
		assertTrue(consult_page.chechingPageLink(driver),"Existen link rotos");
	}
}
