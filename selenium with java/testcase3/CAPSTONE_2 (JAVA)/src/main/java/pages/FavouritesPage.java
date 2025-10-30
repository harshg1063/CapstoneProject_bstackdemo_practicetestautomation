package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class FavouritesPage extends BasePage {

    private By productItems = By.cssSelector("div.shelf-item");
    private By heartIcons = By.xpath("//div[contains(@class,'heart')]");
    private By favouriteTab = By.xpath("//*[text()='Favourites' or contains(.,'Favourites')]");
    private By closeCart = By.xpath("//div[contains(@class,'float-cart__close-btn')]");

    public FavouritesPage(WebDriver driver) {
        super(driver);
        wait.withTimeout(Duration.ofSeconds(30));
    }

    public void addToFavourites() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
        sleep(2000);

        try {
            WebElement close = driver.findElement(closeCart);
            if (close.isDisplayed()) {
                close.click();
                System.out.println("🛒 Closed open cart overlay.");
            }
        } catch (Exception ignored) {}

        List<WebElement> hearts = driver.findElements(heartIcons);
        if (hearts.isEmpty()) {
            System.out.println("⚠️ No favourite icons found initially. Retrying after 3 seconds...");
            sleep(3000);
            hearts = driver.findElements(heartIcons);
        }

        if (hearts.isEmpty()) {
            System.out.println("❌ Still no favourite icons found. Please verify locator.");
            return;
        }

        System.out.println("✅ Found " + hearts.size() + " favourite icons.");

        for (int i = 0; i < Math.min(3, hearts.size()); i++) {
            try {
                WebElement heart = hearts.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", heart);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", heart);
                System.out.println("❤️ Added product " + (i + 1) + " to favourites.");
                sleep(1000);
            } catch (Exception e) {
                System.out.println("⚠️ Error clicking heart #" + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    public void verifyFavourites() {
        WebElement favTab = wait.until(ExpectedConditions.elementToBeClickable(favouriteTab));
        favTab.click();
        System.out.println("🧭 Navigated to Favourites tab.");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
        List<WebElement> favItems = driver.findElements(productItems);

        if (favItems.size() > 0)
            System.out.println("✅ Verified " + favItems.size() + " item(s) in favourites.");
        else
            System.out.println("❌ No favourites found!");
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }
}
