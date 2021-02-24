package com.global;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Global {
	protected WebDriver driver;
	public static final long TIMEWAIT = 30;

	public Global(WebDriver d) {
		this.driver = d;
	}

	public static Response getRequestResponse() {
		return requestResponse;
	}

	public static void setRequestResponse(Response requestResponse) {
		Global.requestResponse = requestResponse;
	}

	protected static Response requestResponse;

	public void waitForVisibility(WebElement e) {
		WebDriverWait wait = new WebDriverWait(driver, TIMEWAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void click(WebElement e, String msg) {
		waitForVisibility(e);
		log().info(msg);
		e.click();
	}

	public void sendKeys(WebElement e, String txt, String msg) {
		waitForVisibility(e);
		log().info(msg);
		e.sendKeys(txt);
	}

	public String getText(WebElement e) {
		waitForVisibility(e);
		return e.getText();
	}

	public String reemplazarEnXpath(String xpath, String palabraACambiar, String... args) {
		for (int i = 0; i < args.length; i++) {
			xpath = xpath.replaceFirst(palabraACambiar, args[i]);
		}
		return xpath;
	}

	public void sendRequest(String tipoPeticion, String serviceName, String baseUrl, String pathRequest,
			String bodyRequest) {

		log().info("Se invoca API tipo: " + tipoPeticion + " de nombre: " + serviceName);

		if (tipoPeticion.equalsIgnoreCase("POST")) {

			// Se invoca el servicio
			Response response = given().contentType(ContentType.JSON).when().body(bodyRequest)
					.post(baseUrl + pathRequest);

			setRequestResponse(response);

		} else if (tipoPeticion.equalsIgnoreCase("GET")) {

			// Se invoca el servicio
			Response response = given().contentType(ContentType.JSON).when().get(baseUrl + pathRequest);

			setRequestResponse(response);

		} else if (tipoPeticion.equalsIgnoreCase("DELETE")) {

			// Se invoca el servicio
			Response response = given().contentType(ContentType.JSON).when().body(bodyRequest)
					.delete(baseUrl + pathRequest);

			setRequestResponse(response);

		} else {

			// Se invoca el servicio
			Response response = given().contentType(ContentType.JSON).when().body(bodyRequest)
					.put(baseUrl + pathRequest);

			setRequestResponse(response);

		}
	}

	public Logger log() {
		return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}

}
