package appli.fashion.test;

import org.testng.annotations.Test;

import com.applitools.eyes.MatchLevel;

import base.Base;

public class AppliFashioTests extends Base {

	@Test(dataProvider = "versionData")
	public void validateMainPage(String versionData) {

		// get Env. url & name by split data provided
		String url = getVersionData(versionData).get(0);
		String versionName = getVersionData(versionData).get(1);

		// Update the Eyes configuration with test specific values
		setTestName(getProperty("Test_1_Name"), versionName);
		setStepName(getProperty("Step_1_Name"), versionName);
		setVersionName(versionName);

		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			// 2. Set the configuration values we set up in beforeTestSuite
			eyes1.setConfiguration(testConfig1);
		} else {
			eyes.setConfiguration(testConfig);
		}
		setMatchLevel(MatchLevel.STRICT, versionName);

		// Navigates to main page
		navigate(url, versionName);

		validateWindow(versionName);
	}

	@Test(dataProvider = "versionData")
	public void filterByColor(String versionData) {
		// get Env. url & name by split data provided
		String url = getVersionData(versionData).get(0);
		String versionName = getVersionData(versionData).get(1);

		// Update the Eyes configuration with test specific values
		setTestName(getProperty("Test_2_Name"), versionName);
		setStepName(getProperty("Step_2_Name"), versionName);
		setVersionName(versionName);

		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			// 2. Set the configuration values we set up in beforeTestSuite
			eyes1.setConfiguration(testConfig1);
		} else {
			eyes.setConfiguration(testConfig);
		}
		setMatchLevel(MatchLevel.STRICT, versionName);

		// Navigates to main page
		navigate(url, versionName);

		mainPage.filterWithBlackColor();
		mainPage.clickOnFilterButton();
		validateElement(mainPage.findProductFilteredResults(), versionName);
	}

	@Test(dataProvider = "versionData")
	public void checkAppliAirNightPage(String environmentData) {
		// get Env. url & name by split data provided
		String url = getVersionData(environmentData).get(0);
		String versionName = getVersionData(environmentData).get(1);

		// Update the Eyes configuration with test specific values
		setTestName(getProperty("Test_3_Name"), versionName);
		setStepName(getProperty("Step_3_Name"), versionName);
		setVersionName(versionName);

		if (versionName.equalsIgnoreCase(getProperty("EnvName1"))
				|| versionName.equalsIgnoreCase(getProperty("EnvName2"))) {
			// 2. Set the configuration values we set up in beforeTestSuite
			eyes1.setConfiguration(testConfig1);
		} else {
			eyes.setConfiguration(testConfig);
		}
		setMatchLevel(MatchLevel.STRICT, versionName);

		// Navigates to main page
		navigate(url, versionName);

		mainPage.clickOnAppliAirNight();
		validateWindow(versionName);
	}

}
