package Portal;

import com.data.ExcelUtils;
import com.global.Global;

import com.po.HeladerasPage;
import com.po.IndexPage;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class FravegaTest extends Base {

	Global global = new Global(driver);

	@DataProvider
	public Object[][] fravegaCases(ITestContext context) throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray(
				super.getDataProviderDir() + context.getCurrentXmlTest().getParameter("dataProvider"),
				context.getCurrentXmlTest().getParameter("sheet"));
		return (testObjArray);
	}

	@Test(description = "1-Buscar-Filtrar producto- Fravega", dataProvider = "fravegaCases")
	public void searchAndFilter(String producto, String filtro, String marca) throws Exception {

		global.log().info("Inicia el caso de prueba");
		IndexPage index = new IndexPage(driver);
		HeladerasPage heladeras = new HeladerasPage(driver);

		// Acciones
		index.validateHomePage();
		index.searchItem(producto);
		heladeras.selectFilter(filtro);
		heladeras.selectBrand(marca);
		// Asersiones
		heladeras.obtainAndValidateProducts(marca, filtro);

	}

}
