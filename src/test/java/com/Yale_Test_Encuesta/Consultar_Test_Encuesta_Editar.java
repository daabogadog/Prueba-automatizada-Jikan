package com.Yale_Test_Encuesta;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Consultar_Test_Encuesta_Editar {
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
	By BtnGuardar = By.id("btn-save-create");
	// elementos para editar y borrar
	By BtnEditar = By.id("edit-pregunta");
	By TextEditar = By.id("txt-edit");
	By NotificacionVacio = By.xpath("/html/body/app/vertical-layout-1/div/div/div/div/content/app-preguntas-list/div/mat-paginator/div/div/div[1]/mat-form-field/div/div[1]/div");
	By listEstados = By.id("select-estado");
	By EstadosInativo = By.xpath("// span [text() ='Inactivo']");
	By EstadosActivo = By.xpath("// span [text() ='Activo']");
	
	
	// elementos para guardar y cancelar de editar
	By BtnGuardarEditar = By.id("btn-save-edit");
	By BtnCancelarEditar = By.id("btn-cancel-edit");
	
	// elementos eliminar
	By BtnBorrar = By.id("delete-pregunta");
	By BtnBorrarSi = By.id("btn-delete-si");
	By BtnBorrarNo = By.id("btn-delete-no");

	
	
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
		Thread.sleep(2000);
		driver.manage().window().maximize();
		driver.get(url);
		String User = "itoadmin"; 
		String Pass = "1T0s0ftw4r3*";
		driver.findElement(TextUser).sendKeys(User);
		driver.findElement(TextPass).sendKeys(Pass);
		driver.findElement(BtnLogIn).click();
		System.out.println("Navegador: " + browserType);
	}

	public String obtenerFechaFormateada(LocalDate fecha, String formato) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formato);
		return fecha.format(dtf);
	}

	@DataProvider(name = "Data_Preguntas")
	public Object[][] getData() {
		return new Object[][] { { "Como califica el envio" } };

	}
	

	@Test(dataProvider = "Data_Preguntas")
	public void Editar_Pregunta(String Data) throws InterruptedException {

		Thread.sleep(2000);
		driver.findElement(BtnPreguntas).click();
		driver.findElement(BtnCrearPreguntas).click();
		driver.findElement(TextIntPregunta).sendKeys(Data);
		driver.findElement(BtnGuardar).click();

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(BtnGuardar));
		Thread.sleep(4000);
		driver.findElement(BtnEditar).click();
		String DataEdit = "Como fue la atencion - Editada";
		driver.findElement(TextEditar).clear();
		driver.findElement(TextEditar).sendKeys(DataEdit);
		driver.findElement(BtnGuardarEditar).click();
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Pregunta no editada", pageText, containsString(DataEdit));
		Thread.sleep(4000);
	}
    /*
	@Test
	public void Editar_Pregunta_Vacia() throws InterruptedException {

		driver.findElement(BtnEditar).click();

		String Pregunta = driver.findElement(TextEditar).getAttribute("value");
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

		driver.findElement(TextEditar).sendKeys(del);
		driver.findElement(BtnGuardarEditar).click();

		assertTrue(driver.findElement(NotificacionVacio).isDisplayed());
		Thread.sleep(2000);
		driver.findElement(BtnCancelarEditar).click();

		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Pregunta no editada", pageText, containsString(Pregunta));
	}

	@Test
	public void Editar_Preguntas_Cancelar() throws InterruptedException {

		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		String Data = "Edicion no guardar";
		driver.findElement(BtnEditar).click();
		driver.findElement(TextEditar).sendKeys(del);
		driver.findElement(TextEditar).sendKeys(Data);
		driver.findElement(BtnCancelarEditar).click();
		Thread.sleep(2000);
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertFalse(pageText.contains(Data), "Texto  presente en la pagina");

	}

	@Test
	public void Editar_Preguntas_longitud255() throws InterruptedException {

		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		String Data = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor  desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original.";
		driver.findElement(BtnEditar).click();
		driver.findElement(TextEditar).sendKeys(del);
		driver.findElement(TextEditar).sendKeys(Data);
		driver.findElement(BtnGuardarEditar).click();
		Thread.sleep(2000);
		String validacion = "Texto ingresado supera los 255 caracteres";
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("Admite valores de 255 caracteres", pageText, containsString(Data));
		driver.findElement(BtnCancelarEditar).click();
		
	}

	@Test
	public void Editar_Preguntas_Activo() throws InterruptedException {
		driver.findElement(BtnEditar).click();
		driver.findElement(listEstados).click();
		driver.findElement(EstadosInativo).click();
	    driver.findElement(BtnGuardarEditar).click();
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("No cambia de estado", pageText, containsString("No"));
		Thread.sleep(2000);
		driver.findElement(BtnEditar).click();
		driver.findElement(listEstados).click();
		driver.findElement(EstadosActivo).click();
		driver.findElement(BtnGuardarEditar).click();
		pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("No cambia de estado", pageText, containsString("Si"));
		
	}
	@Test
	public void Editar_Preguntas_Activo_Cancelar() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(BtnEditar).click();
		driver.findElement(listEstados).click();
		driver.findElement(EstadosInativo).click();
	    driver.findElement(BtnCancelarEditar).click();
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("No cambia de estado", pageText, containsString("Si"));
		
		Thread.sleep(2000);
		driver.findElement(BtnEditar).click();
		driver.findElement(listEstados).click();
		driver.findElement(EstadosInativo).click();
		driver.findElement(BtnGuardarEditar).click();
		
		
		Thread.sleep(2000);
		driver.findElement(BtnEditar).click();
		driver.findElement(listEstados).click();
		driver.findElement(EstadosActivo).click();
		driver.findElement(BtnCancelarEditar).click();
		pageText = driver.findElement(By.tagName("body")).getText();
		assertThat("No cambia de estado", pageText, containsString("No"));
		
	}
	
	@Test
	public void Editar_Preguntas_Eliminar() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(BtnBorrar).click();
		assertTrue(driver.findElement(BtnBorrarNo).isDisplayed());
		assertTrue(driver.findElement(BtnBorrarSi).isDisplayed());
		driver.findElement(BtnBorrarNo).click();
		assertTrue(driver.findElement(BtnBorrar).isDisplayed());
				
		driver.findElement(BtnBorrar).click();
		driver.findElement(BtnBorrarSi).click();
		WebDriverWait wait = new
	    WebDriverWait(driver,10);
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(BtnBorrar));		
	    if(driver.findElements(BtnBorrar).size() != 0)
	    { assertTrue(true); }
	    

		
	}


	*/
	
}
