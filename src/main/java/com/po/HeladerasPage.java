package com.po;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.global.Global;

public class HeladerasPage extends Global {

	// Definir web elements a nivel de clase

	public HeladerasPage(WebDriver d) {
		super(d);
		PageFactory.initElements(driver, this);
	}

	@FindBy(name = "categoryTitle")
	private WebElement categoryTitle;

	@FindBy(xpath = "//li[@name='appliedFilters']//span[@data-testid='tag-pill']")
	private WebElement appliedFilters;

	@FindBy(xpath = "//li//a[@cursor='default']")
	private WebElement breadcrumbBtn;

	@FindBy(xpath = "//li[@title='Next Page']")
	private WebElement nextPageBtn;

	private String subCategoryButton = "//h4[@name='subcategoryAggregation' and contains (.,'textoButton')]";
	private String brandSelected = "//a[contains(.,'category')]//span[@class='quantity']";
	private String brandButton = "//ul[@name='categoriesAggregation']//li[@name='brandAggregation' and contains (., 'textoButton')]";
	private String category = "category";
	private String textoButton = "textoButton";

	// Steps

	public String getCategoryTitleTxt() {
		return getText(categoryTitle);
	}

	public String getAppliedFiltersTxt() {
		return getText(appliedFilters);
	}

	public String getBreadcrumbBtnTxt() {
		return getText(breadcrumbBtn);
	}

	public void selectFilter(String filtro) {

		String xpathButton = reemplazarEnXpath(subCategoryButton, textoButton, filtro);
		driver.findElement(By.xpath(xpathButton)).click();
		log().info("Se selecciona filtro: " + filtro);

	}

	public void selectBrand(String marca) {

		String xpathButton = reemplazarEnXpath(brandButton, textoButton, marca);
		driver.findElement(By.xpath(xpathButton)).click();
		log().info("Se filtra por la marca: " + marca);
		Assert.assertTrue(getAppliedFiltersTxt().equalsIgnoreCase(marca));
		log().info("Se valida búsqueda por categoria.");
	}

	public void obtainAndValidateProducts(String marca, String filtro) {

		// Se arma xpath con la marca seleccionada y se obtiene la cantidad
		String xpathObtainBrandSelectedQuantity = reemplazarEnXpath(brandSelected, category, marca);
		String totalQuantity = driver.findElement(By.xpath(xpathObtainBrandSelectedQuantity)).getText();

		// Se recorre grilla de productos paginada, se guardan en una lista y se valida cantidad
		List<WebElement> elements = driver.findElements(
				By.xpath("//article[@class='PieceLayout-orsj2a-0 PieceLayout__ResponsiveLayout-orsj2a-3 GKcLt']"));

		List<String> names = new ArrayList<String>();

		for (WebElement element : elements) {
			names.add(element.getText());
		}

		String nextButtonClass = driver.findElement(By.xpath("//li[@title='Next Page']")).getAttribute("aria-disabled");

		int count = 0;

		while (!nextButtonClass.contains("true")) {
			click(nextPageBtn, "Se presiona botón de paginado. ");

			elements = driver.findElements(
					By.xpath("//article[@class='PieceLayout-orsj2a-0 PieceLayout__ResponsiveLayout-orsj2a-3 GKcLt']"));

			for (WebElement element : elements) {
				names.add(element.getText());
			}
			nextButtonClass = driver.findElement(By.xpath("//li[@title='Next Page']")).getAttribute("aria-disabled");

		}

		for (int i = 0; i < names.size(); i++) {

			Assert.assertTrue(names.get(count).contains(marca));
			count++;
		}

		log().info("Se valida que los productos listados contengan la marca: " + marca);

		int totalNames = names.size();

		log().info("el numero total de productos es de: " + totalNames);
		Assert.assertTrue(totalQuantity.contains(String.valueOf(totalNames)));
		log().info(
				"Se valida que la cantidad de elementos de la lista coincida con el número obtenido de la categoria seleccionada.");
		Assert.assertTrue(getBreadcrumbBtnTxt().equalsIgnoreCase(filtro));
		log().info("Se valida breadcrumb: " + filtro);

	}

}
