package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class VendorFilterPage extends BasePage {

    private By vendorButtons = By.cssSelector("span.checkmark");
    private By productGrid = By.cssSelector("div.shelf-item");

    public VendorFilterPage(WebDriver driver) {
        super(driver);
    }

    public void applyVendorFilter(String vendor) {
        try {
            WebElement filter = driver.findElement(By.xpath("//label[contains(.,'" + vendor + "')]"));
            filter.click();
            System.out.println("🔍 Vendor filter applied: " + vendor);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productGrid));
        } catch (Exception e) {
            System.err.println("❌ Failed to apply vendor filter: " + e.getMessage());
        }
    }

    public int countFilteredProducts() {
        List<WebElement> items = driver.findElements(productGrid);
        System.out.println("🧾 Found " + items.size() + " product(s) after filter.");
        return items.size();
    }
}
