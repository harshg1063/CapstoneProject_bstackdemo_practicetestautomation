
package browserstackwebapp;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase1 extends BaseTest {

    @Test
    public void loginTest() throws InterruptedException {
        System.out.println("TestCase1 started");
        System.out.println("Navigating to website");
        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Enter username
        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        usernameField.sendKeys("student");
        System.out.println("Username entered");

        // Enter password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");
        System.out.println("Password entered");

        // Click Submit
        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();
        System.out.println("Submit button clicked");
        // Wait for success page
        wait.until(ExpectedConditions.urlContains("logged-in-successfully"));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("🔗 Redirected URL: " + currentUrl);

        Assert.assertTrue(
            currentUrl.toLowerCase().contains("logged-in-successfully"),
            " Login failed or incorrect redirect!"
        );

        // Verify success message
        WebElement successMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Logged In Successfully')]"))
        );
        Assert.assertTrue(successMessage.isDisplayed(), " Success message not visible!");

        System.out.println("TestCase1 completed");
    }
}
