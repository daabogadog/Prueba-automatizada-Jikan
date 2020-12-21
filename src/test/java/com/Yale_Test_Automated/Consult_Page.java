package com.Yale_Test_Automated;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Consult_Page extends Base {

	// elementos de la pagina de consultar pedido
	By TipoDocumento = By.name("tipo");
	By Documento = By.name("documento");
	By ConsultarEstado = By.name("op");
	By CampoVacio = By.xpath("/html/body/app-root/app-seguimiento/div/div[2]/div");
	By NoInformacion = By.xpath("/html/body/app-root/app-seguimiento/div/div[2]");

	// elementos de la pagina linea de tiempo
	By RealizarConsulta = By.id("back-submit");

	public Consult_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void Consultar(String data) throws InterruptedException {

		Thread.sleep(2000);
		Select listaTipoDocumento = new Select(findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText("Factura electrónica (FE)");

		if (isDisplayed(TipoDocumento)) {
			type(data, Documento);
			click(ConsultarEstado);
			Thread.sleep(2000);
		} else {
			System.out.print("No se puede encontrar elemento");
		}

	}

	public boolean isHomePageDisplayed() {
		return isDisplayed(RealizarConsulta);
	}

	public void DocumentoVacio() throws InterruptedException {

		Thread.sleep(2000);
		Select listaTipoDocumento = new Select(findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText("Factura electrónica (FE)");

		if (isDisplayed(TipoDocumento)) {
			click(ConsultarEstado);
		} else {
			System.out.print("No se puede encontrar elemento");
		}

	}

	public boolean isHomePageDisplayed_vacio() {
		return isDisplayed(CampoVacio);
	}

	public void DocumentoSinRegistro() throws InterruptedException {

		Select listaTipoDocumento = new Select(findElement(TipoDocumento));
		listaTipoDocumento.selectByVisibleText("Orden de venta (OV)");

		if (isDisplayed(TipoDocumento)) {
			type("1", Documento);
			click(ConsultarEstado);
			Thread.sleep(2000);
		} else {
			System.out.print("No se puede encontrar elemento");
		}

	}

	public boolean isHomePageDisplayed_No_Valido() {
		return isDisplayed(NoInformacion);
	}

	//private WebDriver driver;	
	
	public boolean chechingPageLink(WebDriver driver) {
	  List<WebElement> Links = driver.findElements(By.tagName("a"));
	  String url="";
	  List<String> brokenLinks = new ArrayList<String>();
	  List<String> OkLinks = new ArrayList<String>();
	  
	  HttpURLConnection httpConection = null;
	  int responseCode=200;
	  Iterator<WebElement> it = Links.iterator();
	  
	  while (it.hasNext()) {
		  url = it.next().getAttribute("href");
		  if(url==null || url.isEmpty()) {
			  System.out.println(url+"url no configurada o vacia");
			  continue;
		  }
		  try {
			  httpConection = (HttpURLConnection)(new URL(url).openConnection());
			  httpConection.setRequestMethod("HEAD");
			  httpConection.connect();
			  responseCode = httpConection.getResponseCode();
			  
			  if(responseCode>400) {
				  System.out.println("Error Link: -- "+ url);
				  brokenLinks.add(url);
			  }else {
				  System.out.println("Valido Link: -- "+ url);
				  OkLinks.add(url);
			  }
			  
			  
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	System.out.println("Links Validos: -- "+OkLinks.size());
	System.out.println("Links Invalidos: -- "+brokenLinks.size());
	
	if (brokenLinks.size()>0) {
		System.out.println("**** ERROR ------------------- lINK ROTOS");
		for (int i =0;i <brokenLinks.size();i++) {
			System.out.println(brokenLinks.get(i));	
		}
		return false;
	} else {
		return true;
	}
	
	}

}
