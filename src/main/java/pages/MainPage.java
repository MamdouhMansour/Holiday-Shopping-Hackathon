package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

	private WebDriver driver;

	private By blackColorCheckBox = By.id("LABEL__containerc__104");
	private By filterBtn = By.id("filterBtn");
	private By filterResultGrid = By.id("product_grid");
	private By Appli_Air_X_Night = By.id("product_1");

	public MainPage(WebDriver driver) {
		this.driver = driver;
	}

	public void filterWithBlackColor() {
		driver.findElement(blackColorCheckBox).click();
	}

	public void clickOnFilterButton() {
		driver.findElement(filterBtn).click();
	}

	public By findProductFilteredResults() {
		return filterResultGrid;
	}

	public void clickOnAppliAirNight() {
		driver.findElement(Appli_Air_X_Night).click();
	}

}
