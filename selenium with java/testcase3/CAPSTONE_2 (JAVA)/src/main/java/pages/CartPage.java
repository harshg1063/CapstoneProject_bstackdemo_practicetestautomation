package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {

    private By productItems = By.cssSelector("div.shelf-item");
    private By addToCartButtons = By.cssSelector("div.shelf-item__buy-btn");
    private By cartToggle = By.xpath("//div[contains(@class,'float-cart__toggle')]");
    private By cartContainer = By.xpath("//div[contains(@class,'float-cart') and contains(@class,'open')]");
    private By cartCloseButton = By.cssSelector("div.float-cart__close-btn");
    private By cartItems = By.cssSelector("div.float-cart__shelf-container div.shelf-item");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // 🔹 Add multiple items to cart
    public void addMultipleItems(int count) {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
            List<WebElement> buttons = driver.findElements(addToCartButtons);
            System.out.println("✅ Found " + buttons.size() + " 'Add to cart' buttons.");

            for (int i = 0; i < count && i < buttons.size(); i++) {
                WebElement btn = buttons.get(i);
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                System.out.println("🛒 Added item " + (i + 1) + " to cart.");
                Thread.sleep(1000);
            }
            System.out.println("✅ Added " + count + " products to cart successfully.");

        } catch (Exception e) {
            System.err.println("⚠️ Error adding products: " + e.getMessage());
        }
    }

    // 🔹 Verify cart contents
    public int verifyCartItems() {
        try {
            System.out.println("🧾 Checking cart state...");

            // Scroll up to make sure floating cart is visible
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
            Thread.sleep(1000);

            // If cart is already open, no need to click toggle
            boolean cartAlreadyOpen = driver.findElements(cartContainer).size() > 0;
            if (!cartAlreadyOpen) {
                WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(cartToggle));
                toggle.click();
                System.out.println("🛍️ Cart opened successfully.");
            } else {
                System.out.println("🛍️ Cart is already open — skipping toggle click.");
            }

            // Wait for cart items to load
            List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
            System.out.println("🛒 Cart contains " + items.size() + " item(s).");

            // Optional: close the cart for next test
            if (driver.findElements(cartCloseButton).size() > 0) {
                driver.findElement(cartCloseButton).click();
                System.out.println("❎ Closed cart after verification.");
            }

            return items.size();

        } catch (Exception e) {
            System.err.println("❌ Failed to verify cart: " + e.getMessage());
            return 0;
        }
    }
}
