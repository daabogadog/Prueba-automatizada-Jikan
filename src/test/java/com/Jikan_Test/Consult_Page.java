package com.Jikan_Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Consult_Page extends Base {

	// elementos del landing page
	By Caja_de_busqueda = By.id("search_query");
	By Boton_GO = By.id("search");
	By Requesting = By.id("search_query_url");
	By Request_cached = By.id("request_cached");
	By Time_taken = By.id("request_time_taken");
	By Sponsors = By.xpath("/html/body/section[8]/div[1]/div/a");
	// elementos de la notificacion
	By RealizarConsulta = By.id("back-submit");

	public Consult_Page(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	//Metodo para consultar Anime
	public void Consult(WebDriver driver, String data) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Caja_de_busqueda));

		type(data, Caja_de_busqueda);
		click(Boton_GO);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Requesting));

	}
 
	//Metodo para verificar elemento presente en pantalla
	public boolean isHomePageDisplayed() {

		if (isDisplayed(Request_cached) && isDisplayed(Requesting) && isDisplayed(Time_taken)) {
			
				clear(Caja_de_busqueda);
				return true;				
		}

		else
			return false;
	}
	
	public boolean Request_true() {
       
		if (istext(Request_cached)!="false") {
			return true;				
		}
		else			
			return false;
	}
	
	
	
    //Metodo para verificar color de un elemento
	public Color ColorsElement(WebDriver driver) {

		Color ValidarColor = Color.fromString(driver.findElement((Request_cached)).getCssValue("color"));
		return ValidarColor;
	}

	
     //Metodo para verificar todos los link de una pagina
	public boolean chechingPageLink(WebDriver driver) {
		List<WebElement> Links = driver.findElements(By.tagName("a"));
		String url = "";
		List<String> brokenLinks = new ArrayList<String>();
		List<String> OkLinks = new ArrayList<String>();

		HttpURLConnection httpConection = null;
		int responseCode = 200;
		Iterator<WebElement> it = Links.iterator();

		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println(url + "url no configurada o vacia");
				continue;
			}
			try {
				httpConection = (HttpURLConnection) (new URL(url).openConnection());
				httpConection.setRequestMethod("HEAD");
				httpConection.connect();
				responseCode = httpConection.getResponseCode();

				if (responseCode > 400) {
					System.out.println("Error Link: -- " + url);
					brokenLinks.add(url);
				} else {
					System.out.println("Valido Link: -- " + url);
					OkLinks.add(url);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Links Validos: -- " + OkLinks.size());
		System.out.println("Links Invalidos: -- " + brokenLinks.size());

		if (brokenLinks.size() > 0) {
			System.out.println("**** ERROR ------------------- lINK ROTOS");
			for (int i = 0; i < brokenLinks.size(); i++) {
				System.out.println(brokenLinks.get(i));
			}
			return false;
		} else {
			return true;
		}

	}

}
