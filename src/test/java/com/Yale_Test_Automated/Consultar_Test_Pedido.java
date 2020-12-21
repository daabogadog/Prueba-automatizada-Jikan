package com.Yale_Test_Automated;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test_Pedido {

	Consult_Page consult_page;
	private WebDriver driver;
	
	// elementos de la pagina de consultar pedido
	By TipoDocumento = By.name("tipo");
	By Documento = By.name("documento");
	By ConsultarEstado = By.name("op");
	By CampoVacio = By.xpath("/html/body/app-root/app-seguimiento/div/div[2]");

	// elementos de la pagina linea de tiempo
	By RealizarConsulta = By.id("back-submit");
	
	//Estados
	By Recibido = By.xpath("/html/body/app-root/app-respuesta/div[2]/div/header/section/div[2]/section/div/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div[1]/div[1]/div");
	By Facturado = By.xpath("/html/body/app-root/app-respuesta/div[2]/div/header/section/div[2]/section/div/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div[2]/div[1]/div");
	By Despachado = By.xpath("/html/body/app-root/app-respuesta/div[2]/div/header/section/div[2]/section/div/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div[3]/div[1]/div");	
	By En_tránsito  = By.xpath("/html/body/app-root/app-respuesta/div[2]/div/header/section/div[2]/section/div/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div[4]/div[1]/div");
	By Entregado= By.xpath("/html/body/app-root/app-respuesta/div[2]/div/header/section/div[2]/section/div/div/div/div/div/div/div/div[2]/div[1]/div/div/div/div[5]/div[1]/div");
	
	
	
	@BeforeClass
	@Parameters({"URL","BrowserType"})
	public void setUp(String url, String browserType) throws Exception {
		
		consult_page = new Consult_Page(driver);
				
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
	
	@Test
	public void Validar_Estados() throws InterruptedException {
		Thread.sleep(2000);
		Select listaTipoDocumento = new Select(driver.findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText("Número de Guía");

		if (driver.findElement(TipoDocumento).isDisplayed()) {
			driver.findElement(Documento).sendKeys("1");
			driver.findElement(ConsultarEstado).click();
			Thread.sleep(2000);
			
			assertTrue(driver.findElement(Recibido).isDisplayed());
			assertTrue(driver.findElement(Facturado).isDisplayed());
			assertTrue(driver.findElement(Despachado).isDisplayed());
			assertTrue(driver.findElement(En_tránsito).isDisplayed());
			assertTrue(driver.findElement(Entregado).isDisplayed());	
			
			driver.findElement(RealizarConsulta).click();
			Thread.sleep(2000);
		} else {
			System.out.print("No se puede encontrar elemento");
		}
	}
	
	@DataProvider(name = "Data_prueba")
	public Object[][] getData() {
		return new Object[][] { 
		    { "1", "Orden de venta (OV)" }, 
		    { "2", "Número de Guía"}
			

	};
	}
	
	@Test(dataProvider = "Data_prueba")
	public void Consultar(String data,String valor) throws InterruptedException {
		Thread.sleep(2000);
		Select listaTipoDocumento = new Select(driver.findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText(valor);

		if (driver.findElement(TipoDocumento).isDisplayed()) {
			driver.findElement(Documento).sendKeys(data);
			driver.findElement(ConsultarEstado).click();
			Thread.sleep(2000);
			assertTrue(driver.findElement(RealizarConsulta).isDisplayed());
			Color DespachadoActivo = Color.fromString(consult_page.findElement((Despachado)).getCssValue("color"));
			assert DespachadoActivo.asRgb().equals("rgb(0, 0, 0)");
			driver.findElement(RealizarConsulta).click();
			Thread.sleep(2000);
		} else {
			System.out.print("No se puede encontrar elemento");
		}

	}
}
