package com.Yale_Test_Encuesta;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test_Encuesta_Paginacion {
	private WebDriver driver;
	Consult_Page consult_page;

	// Elementos del Login
	By TextUser = By.id("username");
	By TextPass = By.id("password");
	By BtnLogIn = By.id("kc-login");
	
	// elementos de la pagina Home
	By BtnPreguntas = By.xpath(
			"/html/body/app/vertical-layout-1/div/div/fuse-sidebar/navbar/navbar-vertical-style-1/div[2]/div[2]/fuse-navigation/div/fuse-nav-vertical-group/div[2]/fuse-nav-vertical-item/a");

	// elementos de crear preguntas
	By BtnCrearPreguntas = By.id("new-pregunta");
	By TextIntPregunta = By.id("txt-create");
	By BtnCancelar = By.id("btn-cancel-create");
	By BtnGuardar = By.id("btn-save-create");
	By NotificacionVacio = By.xpath(
			"/html/body/app/vertical-layout-1/div/div/div/div/content/app-preguntas-list/div/mat-paginator/div/div/div[1]/mat-form-field/div/div[1]/div");

	// elementos paginacion
	By listaPaginacion = By.xpath(
			"//*[@id=\"container-3\"]/content/app-preguntas-list/div/mat-paginator/div/div/div[1]/mat-form-field/div/div[1]/div");
	By listaPaginacion5 = By.xpath("//*[@id=\"mat-option-0\"]/span");
	By listaPaginacion10 = By.xpath("//*[@id=\"mat-option-1\"]/span");
	By listaPaginacion20 = By.xpath("//*[@id=\"mat-option-2\"]/span");

	@BeforeClass
	@Parameters({ "URL_encuesta", "BrowserType" })
	public void setUp(String url, String browserType) throws Exception {

		consult_page = new Consult_Page(driver);

		if (browserType.equalsIgnoreCase("Chrome")) {
			driver = consult_page.chromeDriverConnection();
		} else if (browserType.equalsIgnoreCase("Firefox")) {
			driver = consult_page.FirefoxDriverConnection();
		} else if (browserType.equalsIgnoreCase("Internet Explorer")) {
			driver = consult_page.IEDriverConnection();
		}
		driver.manage().window().maximize();
		driver.get(url);
		String User = "itoadmin"; 
		String Pass = "1T0s0ftw4r3*";
		driver.findElement(TextUser).sendKeys(User);
		driver.findElement(TextPass).sendKeys(Pass);
		driver.findElement(BtnLogIn).click();
		System.out.println("Navegador: " + browserType);
	}

	

	@DataProvider(name = "Data_Paginacion")
	public Object[][] getData_paginacion() {
		return new Object[][] { { "100" }, { "200" }, { "300" }, { "400" }, { "500" }, { "600" }, { "700" }, { "800" },
				{ "900" }, { "1000" }, { "2000" }, { "3000" }, { "4000" }, { "5000" }, { "6000" }, { "7000" },
				{ "8000" }, { "9000" }, { "10000" }, { "11000" },

		};
	}

	
	
	@Test(dataProvider = "Data_Paginacion")
	public void Control_Data_Paginacion(String Data) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(BtnPreguntas).click();
		driver.findElement(BtnCrearPreguntas).click();
		driver.findElement(TextIntPregunta).sendKeys(Data);
		driver.findElement(BtnGuardar).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(BtnGuardar));

	}

	@Test
	public void Control_Paginacion() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Paginacion por 5
		driver.findElement(listaPaginacion).click();
		driver.findElement(listaPaginacion5).click();
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Paginacion errada", pageText, containsString("500"));

		// Paginacion por 10
		driver.findElement(listaPaginacion).click();
		driver.findElement(listaPaginacion10).click();

		// Buscar elemento por texto de enlace y almacenarlo en la variable "Element"
		WebElement Element = driver.findElement(listaPaginacion);

		// Esto desplazará la página hasta que se encuentre el elemento
		js.executeScript("arguments[0].scrollIntoView();", Element);

		String pageText2 = driver.findElement(By.tagName("body")).getText();
		assertThat("Paginacion errada", pageText2, containsString("1000"));

		// Paginacion por 20
		driver.findElement(listaPaginacion).click();
		driver.findElement(listaPaginacion20).click();

		// Buscar elemento por texto de enlace y almacenarlo en la variable "Element"
		Element = driver.findElement(listaPaginacion);

		// Esto desplazará la página hasta que se encuentre el elemento
		js.executeScript("arguments[0].scrollIntoView();", Element);

		String pageText3 = driver.findElement(By.tagName("body")).getText();
		assertThat("Paginacion errada", pageText3, containsString("11000"));

	}

}
