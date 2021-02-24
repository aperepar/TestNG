package Portal;

import com.po.ApiRestPage;
import org.testng.annotations.Test;

public class ApiRestFravegaTest {

	String cerveceria = "lagunitas";

	@Test(description = "2-Test Backend - Fravega")
	public void apiRestTest() throws Exception {

		System.out.println("Inicia el caso de prueba");
		ApiRestPage api = new ApiRestPage(null);

		// Acciones
		api.cerveceriaFilter(cerveceria);

	}

}
