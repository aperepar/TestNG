package com.po;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;

import com.global.Global;

import io.restassured.path.json.JsonPath;

public class ApiRestPage extends Global {

	public ApiRestPage(WebDriver d) {
		super(d);
	}

	// Definir web elements a nivel de clase

	private String errorMessageApi = "El servicio no respondió satisfactoriamente, retornó código: ";

	public void cerveceriaFilter(String cerveceria) throws Exception {

		// Se consume servicio para filtrar las cervezerias por nombre.
		sendRequest("GET", "Obtener lista cervecerias", "https://api.openbrewerydb.org/breweries/",
				"autocomplete?query=" + cerveceria, "");

		// Se obtiene la respuesta
		int statusCode = getRequestResponse().getStatusCode();

		// Asersiones
		if (statusCode == 200) {
			log().info("El servicio respondió satisfactoriamente.");
		} else {
			log().info(errorMessageApi + statusCode);
			throw new Exception(errorMessageApi + statusCode);
		}

		// Se obtiene la respuesta
		JsonPath resp = getRequestResponse().jsonPath();
		String getId = resp.getString("id[0]");

		// Se consume servicio para filtrar las cervezerias por Id.
		sendRequest("GET", "Filtrar cerveceria por Id", "https://api.openbrewerydb.org/breweries/", getId, "");

		// Asersiones
		if (statusCode == 200) {
			log().info("El servicio respondió satisfactoriamente.");
		} else {
			log().info(errorMessageApi + statusCode);
			throw new Exception(errorMessageApi + statusCode);
		}

		// Se obtiene la respuesta
		JsonPath resp2 = getRequestResponse().jsonPath();
		String id = resp2.getString("id");
		String name = resp2.getString("name");
		String street = resp2.getString("street");
		String phone = resp2.getString("phone");

		// Asersiones
		Assert.assertEquals(id, "761", "Recibe el campo id correcto en la respuesta.");
		Assert.assertEquals(name, "Lagunitas Brewing Co", "Recibe el campo name correcto en la respuesta.");
		Assert.assertEquals(street, "1280 N McDowell Blvd", "Recibe el campo street correcto en la respuesta.");
		Assert.assertEquals(phone, "7077694495", "Recibe el campo phone correcto en la respuesta.");

		log().info("Se valida cerveceria con id: " + id + ", name: " + name + ", street: " + street + " y phone: "
				+ phone);

	}

}
