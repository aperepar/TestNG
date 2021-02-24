package com.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.global.Global;

public class IndexPage extends Global {

	// Definir web elements a nivel de clase

	public IndexPage(WebDriver d) {
		super(d);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='SearchBar__LogoContainer-xgngsx-5 gmyMIL']//p[contains(.,'Ingresá tu código postal')]")
	private WebElement logoLocationContainerDesktop;

	@FindBy(xpath = "//input[@placeholder='Buscar productos']")
	private WebElement searchBar;

	@FindBy(xpath = "//button[@type='submit' and contains(.,'search Icon')]")
	private WebElement searchIcon;

	// Steps

	public Boolean logoContainerIsVisible() {
		waitForVisibility(logoLocationContainerDesktop);
		return logoLocationContainerDesktop.isDisplayed();
	}

	public Boolean searchBarIsVisible() {
		waitForVisibility(searchBar);
		return searchBar.isDisplayed();
	}

	public void validateHomePage() {

		Assert.assertTrue(logoContainerIsVisible(),
				"Valido que aparezca el logo de Frávega además del botón de localización.");
		Assert.assertTrue(searchBarIsVisible(), "Valido que aparezca la barra de búsqueda.");
		log().info("Se valida la correcta aparición de elementos del Home Page. ");

	}

	public void searchItem(String producto) {
		HeladerasPage heladeras = new HeladerasPage(driver);

		sendKeys(searchBar, producto, "Se busca producto: " + producto);
		click(searchIcon, "Se presiona botón de búsqueda.");
		Assert.assertTrue(heladeras.getCategoryTitleTxt().equals(producto));
		log().info("Se valida búsqueda por categoria.");
	}

}
