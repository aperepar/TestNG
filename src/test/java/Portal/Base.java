package Portal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class Base {
	WebDriver driver;

	private String seleniumUrl;
	private String driverPath;
	private String projectDir;
	private String dataProviderDir;
	private String driversDir;

	public Base() {
		this.projectDir = System.getProperty("user.dir");

		this.dataProviderDir = projectDir + "//src//test//dataprovider//";
		this.driversDir = projectDir + "//src//test//resources//webDrivers//";
	}

	public String getProjectDir() {
		return projectDir;
	}

	public String getDataProviderDir() {
		return dataProviderDir;
	}

	public String getDriversDir() {
		return driversDir;
	}

	@BeforeMethod(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) {

		seleniumUrl = context.getCurrentXmlTest().getParameter("selenium.url");
		driverPath = driversDir + context.getCurrentXmlTest().getParameter("selenium.driver");

		try {
			if (driverPath.contains("geckodriver")) {
				System.setProperty("webdriver.gecko.driver", driverPath);

				FirefoxOptions ffOpt = new FirefoxOptions();
				ffOpt.setCapability("marionette", true);
				driver = new FirefoxDriver(ffOpt);
				driver.manage().window().maximize();

			} else {
				System.setProperty("webdriver.chrome.driver", driverPath);
				// Initialize browser
				driver = new ChromeDriver();
				driver.manage().window().maximize();

				// Accede a cada dimensión individualmente
				/*
				 * int width = driver.manage().window().getSize().getWidth();
				 * int height = driver.manage().window().getSize().getHeight();
				 * log.info(width); log.info(height);
				 */

			}

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} catch (Exception e) {
			throw new IllegalStateException("Can't start selenium browser", e);
		}
		driver.get(seleniumUrl);

	}

	@AfterMethod(alwaysRun = true)
	public void setupAfterSuite() {
		driver.quit();
	}

}
