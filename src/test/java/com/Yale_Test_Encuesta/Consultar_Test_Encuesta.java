package com.Yale_Test_Encuesta;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test_Encuesta {
	private WebDriver driver;
	Consult_Page consult_page;

	// elementos de la pagina Home
	By BtnPreguntas = By.xpath("/html/body/app/vertical-layout-1/div/div/fuse-sidebar/navbar/navbar-vertical-style-1/div[2]/div[2]/fuse-navigation/div/fuse-nav-vertical-group/div[2]/fuse-nav-vertical-item/a");

	// elementos de crear preguntas
	By BtnCrearPreguntas = By.id("new-pregunta");
	By TextIntPregunta = By.id("txt-create");
	By BtnCancelar = By.id("btn-cancel-create");
	By BtnGuardar = By.id("btn-save-create");
	By NotificacionVacio = By.xpath("/html/body/app/vertical-layout-1/div/div/div/div/content/app-preguntas-list/div/mat-paginator/div/div/div[1]/mat-form-field/div/div[1]/div");

	

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

		System.out.println("Navegador: " + browserType);
	}

	public String obtenerFechaFormateada(LocalDate fecha, String formato) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formato);
		return fecha.format(dtf);
	}

	@DataProvider(name = "Data_Preguntas")
	public Object[][] getData() {
		return new Object[][] { 
			    { "Como califica el envio" }, 
			    { "Como estuvo el tiempo de entrega" },
				{ "Nos recomendaria" },

		};

	}

	@Test
	public void Consultar_Pagina_Home() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(BtnPreguntas).click();
		assertTrue(driver.findElement(BtnCrearPreguntas).isDisplayed());

	}

	
	  @Test(dataProvider = "Data_Preguntas") 
	  public void Crear_Preguntas_Guardar(String Data) throws InterruptedException {
	  
	  driver.findElement(BtnCrearPreguntas).click();
	  assertTrue(driver.findElement(TextIntPregunta).isDisplayed());
	  driver.findElement(TextIntPregunta).sendKeys(Data);
	  driver.findElement(BtnGuardar).click(); 
	  WebDriverWait wait = new
	  WebDriverWait(driver, 10);
	  wait.until(ExpectedConditions.invisibilityOfElementLocated(BtnGuardar));
	  	  
	  String pageText = driver.findElement(By.tagName("body")).getText();
	  assertThat("Pregunta no creada", pageText, containsString(Data));
	  assertThat("Pregunta no queda activa", pageText, containsString("Si"));
	  LocalDate date = LocalDate.now(); 
	  String fecha = obtenerFechaFormateada(date,"dd-MM-yyyy");
      assertThat("Fecha no es correcta", pageText,containsString(fecha)); 
      }
	  
	  @Test public void Crear_Preguntas_Cancelar() throws InterruptedException {
	  
	  String Data = "Pregunta no Guardada";
	  driver.findElement(BtnCrearPreguntas).click();
	  assertTrue(driver.findElement(TextIntPregunta).isDisplayed());
	  driver.findElement(TextIntPregunta).sendKeys(Data);
	  driver.findElement(BtnCancelar).click();
	  Thread.sleep(2000); 
	  String pageText = driver.findElement(By.tagName("body")).getText();
	  assertFalse(pageText.contains(Data), "Texto  presente en la pagina");
	  
	  }
	  
	  @Test public void Crear_Preguntas_longitud255() throws InterruptedException {
	  
	  String Data =
	  "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor  desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original."
	  ; driver.findElement(BtnCrearPreguntas).click();
	  assertTrue(driver.findElement(TextIntPregunta).isDisplayed());
	  driver.findElement(TextIntPregunta).sendKeys(Data);
	  driver.findElement(BtnGuardar).click();
	  Thread.sleep(2000); 
	  String validacion  = "Texto ingresado supera los 255 caracteres"; String pageText =
	  driver.findElement(By.tagName("body")).getText();
	  assertThat("Texto no presente en la pagina", pageText, containsString(Data));
	  }
	  
	  @Test public void Crear_Preguntas_Vacia() throws InterruptedException {
	  
	  driver.findElement(BtnCrearPreguntas).click();
	  assertTrue(driver.findElement(TextIntPregunta).isDisplayed());
	  driver.findElement(TextIntPregunta).sendKeys("");
	  driver.findElement(BtnGuardar).click(); 
	  Thread.sleep(2000);
	  assertTrue(driver.findElement(NotificacionVacio).isDisplayed());
	  driver.findElement(BtnCancelar).click(); }
	  
	 
}
