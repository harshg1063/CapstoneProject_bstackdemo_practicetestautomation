package browserstackwebapp;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase2 extends BaseTest {

    @Test
    public void logoutTest() throws InterruptedException {
        System.out.println("TestCase2 started");
        System.out.println("Navigating to website");
        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Login first
        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        usernameField.sendKeys("student");
        System.out.println("Username entered");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");
        System.out.println("Password entered");

        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();
        System.out.println("Submit button clicked");
        // Wait for success page
        wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
        System.out.println(" Logged in successfully!");

        // Click Logout button
        WebElement logoutBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log out']"))
        );
        logoutBtn.click();

        // Verify redirect back to login page
        wait.until(ExpectedConditions.urlContains("practice-test-login"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("practice-test-login"), " Logout failed!");

        System.out.println("TestCase2 completed");
    }
}
