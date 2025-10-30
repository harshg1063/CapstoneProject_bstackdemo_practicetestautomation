package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Locators
    private final String url = "https://bstackdemo.com/";
    private final By signInButton = By.id("signin");
    private final By usernameDropdown = By.cssSelector("div#username div.css-1hwfws3");
    private final By passwordDropdown = By.cssSelector("div#password div.css-1hwfws3");
    private final By loginButton = By.id("login-btn");
    private final By productsContainer = By.className("shelf-item");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Perform login on BrowserStack Demo
    public void login(String username, String password) {
        try {
            driver.get(url);
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
            System.out.println("🟢 Clicked Sign In button.");

            // Wait for dropdowns to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameDropdown));
            Thread.sleep(1000);

            // Select username
            WebElement usernameDrop = driver.findElement(usernameDropdown);
            usernameDrop.click();
            WebElement usernameOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@id,'react-select-2-option-0-0') or text()='" + username + "']")
                )
            );
            new Actions(driver).moveToElement(usernameOption).click().perform();
            System.out.println("✅ Selected username: " + username);

            // Select password
            WebElement passwordDrop = driver.findElement(passwordDropdown);
            passwordDrop.click();
            WebElement passwordOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@id,'react-select-3-option-0-0') or text()='" + password + "']")
                )
            );
            new Actions(driver).moveToElement(passwordOption).click().perform();
            System.out.println("✅ Selected password.");

            // Click login
            wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
            System.out.println("🚀 Clicked Login button.");

            // Wait for product grid (homepage)
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsContainer));
            System.out.println("✅ Logged in successfully as " + username);

        } catch (Exception e) {
            System.err.println("❌ Login failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
