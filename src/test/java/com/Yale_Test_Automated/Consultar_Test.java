package com.Yale_Test_Automated;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test {

	private WebDriver driver;
	Consult_Page consult_page;
    
	// elementos de la pagina de consultar pedido
	By TipoDocumento = By.name("tipo");
	By Documento = By.name("documento");
	By ConsultarEstado = By.name("op");
	By CampoVacio = By.xpath("/html/body/app-root/app-seguimiento/div/div[2]");

	// elementos de la pagina linea de tiempo
	By RealizarConsulta = By.id("back-submit");

	@BeforeClass
	@Parameters({"URL","BrowserType"})
	public void setUp(String url, String browserType) throws Exception {
		consult_page = new Consult_Page(driver);
		//driver = consult_page.chromeDriverConnection();
		//consult_page.visit("http://localhost:4200/");
		
		if (browserType.equalsIgnoreCase("Chrome")) {
			driver = consult_page.chromeDriverConnection();
		}else if(browserType.equalsIgnoreCase("Firefox")){
			driver = consult_page.FirefoxDriverConnection();
		}else if(browserType.equalsIgnoreCase("Internet Explorer")) {
			driver = consult_page.IEDriverConnection();
		}
		driver.manage().window().maximize();
		driver.get(url);
		
		System.out.println("Navegador: "+browserType);
	}

	@DataProvider(name = "Data_prueba")
	public Object[][] getData() {
		Object[][] data = new Object[2][1];
		data[0][0] = "1";data[1][0] = "2";
		
		return data;
	
	}

	@Test(dataProvider = "Data_prueba")
	public void Consultar(String data) throws InterruptedException {
		Thread.sleep(2000);
		System.out.println("Mensaje"+data);
		Select listaTipoDocumento = new Select(driver.findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText("Factura convencional (FC)");

		if (driver.findElement(TipoDocumento).isDisplayed()) {
			driver.findElement(Documento).sendKeys(data);
			driver.findElement(ConsultarEstado).click();
			Thread.sleep(2000);
			assertTrue(driver.findElement(RealizarConsulta).isDisplayed());
			driver.findElement(RealizarConsulta).click();
			Thread.sleep(2000);
		} else {
			System.out.print("No se puede encontrar elemento");
		}

	}
 
	@Test
	public void DocumentoVacio() throws InterruptedException {
		consult_page.DocumentoVacio();
		assertTrue(consult_page.isHomePageDisplayed_vacio()); 
	}
	
	/*
	@Test
	public void DocumentoNoRegistro() throws InterruptedException {
		consult_page.DocumentoSinRegistro();;
		assertTrue(consult_page.isHomePageDisplayed_No_Valido()); 
	}*/
		
	
	
	@AfterClass
	public void afterClass() {
		// driver.quit();
	}

}
