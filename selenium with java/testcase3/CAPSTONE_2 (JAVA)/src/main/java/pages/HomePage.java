package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private By productGrid = By.cssSelector("div.shelf-item");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void waitForHomepage() {
        System.out.println("⏳ Waiting for homepage to load...");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productGrid));
        System.out.println("✅ Homepage loaded successfully with products.");
    }
}
