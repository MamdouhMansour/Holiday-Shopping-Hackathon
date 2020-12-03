package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DesktopBrowserInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MainPage;

public class Base {

	private final int viewPortWidth = 1200;
	private final int viewPortHeight = 800;

	private EyesRunner runner = null;
	protected Configuration suiteConfig;
	protected Configuration suiteConfig1;
	protected Eyes eyes;
	protected Eyes eyes1;
	protected WebDriver webDriver;
	protected Configuration testConfig;
	protected Configuration testConfig1;

	protected MainPage mainPage;

	/**
	 * 
	 * @param url    the url that needed to navigate to
	 * @param driver created driver to open eyes with our webDriver
	 */
	public void navigate(String url, String versionName) {
		WebDriver driver;

		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			driver = eyes1.open(webDriver);
		} else {
			driver = eyes.open(webDriver);
		}

		// Visual checkpoint #1.
		mainPage = new MainPage(driver);
		driver.get(url);
	}

	/*
	 * Shared methods to be used in test
	 */
	// get property from configuration file
	public static String getProperty(String property) {
		Properties props = System.getProperties();
		try {
			props.load(new FileInputStream(new File("src/test/resources/Configuration/config.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props.getProperty(property);
	}

	public void setTestName(String testName, String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			testConfig1.setTestName(testName);
		} else {
			testConfig.setTestName(testName);
		}
	}

	public void setStepName(String stepName, String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			eyes1.addProperty("Step Name", stepName);
		} else {
			eyes.addProperty("Step Name", stepName);
		}
	}

	public void setMatchLevel(MatchLevel matchLevel, String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			eyes1.setMatchLevel(matchLevel);
		} else {
			eyes.setMatchLevel(matchLevel);
		}
	}

	// set environment name "dev, version 1, final production version"
	public void setVersionName(String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			eyes1.addProperty("Version Name", versionName);
		} else {
			eyes.addProperty("Version Name", versionName);
		}
	}

	public void validateWindow(String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			eyes1.checkWindow();
		} else {
			eyes.checkWindow();
		}
	}

	public void validateElement(By locator, String versionName) {
		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			eyes1.checkElement(locator);
		} else {
			eyes.checkElement(locator);
		}
	}

	@DataProvider(name = "versionData")
	public String[] versionToRun() {
		String[] url = null;
		url = new String[3];
		for (int i = 0; i < url.length; i++) {
			// get version url and concatenate it with its name
			url[i] = getProperty("V" + (i + 1)) + "+" + getProperty("EnvName" + (i + 1));
		}
		return url;
	}

	/*
	 * TestNg configuration methods
	 */
	@BeforeSuite
	public void beforeTestSuite() {

		runner = new VisualGridRunner(5);

		// Create a configuration object for final production environment, we will
		// use this when setting up each test
		suiteConfig = new Configuration();
		suiteConfig1 = new Configuration();

		// Set the various configuration values for final production version
		suiteConfig
				// 4. Add Visual Grid browser configurations
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.FIREFOX))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.SAFARI))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.EDGE_CHROMIUM))
				.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_X, ScreenOrientation.PORTRAIT))

				.setViewportSize(new RectangleSize(viewPortWidth, viewPortHeight))

				// set up default Eyes configuration values
				.setApiKey(getProperty("applitools.api.key")).setBatch(new BatchInfo(getProperty("batchName")))
				.setAppName(getProperty("appName"));

		suiteConfig1
				// 4. Add Visual Grid browser configurations
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME))

				.setViewportSize(new RectangleSize(viewPortWidth, viewPortHeight))

				// set up default Eyes configuration values
				.setApiKey(getProperty("applitools.api.key")).setBatch(new BatchInfo(getProperty("batchName")))
				.setAppName(getProperty("appName"));
	}

	@BeforeMethod
	public void beforeEachTest(ITestResult result) {
		// 1. Create the Eyes instance for the test and associate it with the runner
		eyes = new Eyes(runner);
		eyes1 = new Eyes(runner);

		// 2. Set the configuration values we set up in beforeTestSuite
		eyes.setConfiguration(suiteConfig);
		eyes1.setConfiguration(suiteConfig1);

		// 3. Create a WebDriver for the test
		WebDriverManager.chromedriver().setup();
		webDriver = new ChromeDriver();

		testConfig = eyes.getConfiguration();
		testConfig1 = eyes1.getConfiguration();
	}

	@AfterMethod
	public void afterEachTest(ITestResult result) {
		boolean testFailed = result.getStatus() == ITestResult.FAILURE;
		if (!testFailed) {
			// Close the Eyes instance, no need to wait for results, we'll get those at the
			// end in afterTestSuite
			eyes.closeAsync();
			eyes1.closeAsync();
		} else {
			// There was an exception so the test may be incomplete - abort the test
			eyes.abortAsync();
			eyes1.abortAsync();
		}
		webDriver.quit();
	}

	// get Environment Url & name
	public List<String> getVersionData(String versionData) {
		List<String> Env_Name_URL = Arrays.asList(versionData.split("\\+"));
		return Env_Name_URL;
	}

	@AfterSuite
	public void afterTestSuite(ITestContext testContext) {
		// Wait until the test results are available and retrieve them
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		for (TestResultContainer result : allTestResults) {
			handleTestResults(result);
		}
	}

	void handleTestResults(TestResultContainer summary) {
		Throwable ex = summary.getException();
		if (ex != null) {
			System.out.printf("System error occured while checking target.\n");
		}
		summary.getTestResults();
	}
}
