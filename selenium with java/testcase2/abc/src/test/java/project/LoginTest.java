package project;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends base {

    JavascriptExecutor js;

    @Test
    public void verifyVendorFilterFunctionality() {
        try {
            js = (JavascriptExecutor) driver;

            performLogin();

            // Wait for all products to load
            List<WebElement> allProductsBeforeFilter = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".shelf-item"))
            );
            System.out.println("Total products before filter: " + allProductsBeforeFilter.size());

            // Apply Apple filter
            applyVendorFilter("Apple");
            verifyProductsByVendor("Apple");

            // Apply Samsung filter
            applyVendorFilter("Samsung");
            verifyProductsByVendor("Samsung");

            // Unselect filters instead of clear button
            unselectVendorFilter("Apple");
            unselectVendorFilter("Samsung");

            // Wait for all products to reload
            List<WebElement> allProductsAfterUnselect = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".shelf-item"))
            );
            System.out.println("Products visible after unselecting filters: " + allProductsAfterUnselect.size());

            Assert.assertTrue(allProductsAfterUnselect.size() >= allProductsBeforeFilter.size(),
                    "After unselecting filters, all products should be visible again.");

            System.out.println("\nVendor Filter Validation Successful!");

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void performLogin() {
        try {
            System.out.println("Attempting to log in...");
            WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".UserNav_root__343id")));
            signInBtn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-btn")));

            // Username dropdown
            WebElement usernameDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='username']//div[contains(@class,'css-1hwfws3')]")));
            usernameDropdown.click();
            WebElement usernameOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='demouser']")));
            usernameOption.click();

            // Password dropdown
            WebElement passwordDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='password']//div[contains(@class,'css-1hwfws3')]")));
            passwordDropdown.click();
            WebElement passwordOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='testingisfun99']")));
            passwordOption.click();

            // Login button
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn")));
            loginBtn.click();

            // Wait for product grid to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.shelf-item")));
            System.out.println("Logged in successfully!");
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void applyVendorFilter(String vendor) {
        try {
            WebElement vendorElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(text(),'" + vendor + "')]")));

            scrollToElement(vendorElement);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(vendorElement)).click();
            } catch (ElementClickInterceptedException ex) {
                js.executeScript("arguments[0].click();", vendorElement);
            }

            Thread.sleep(2000);
            List<WebElement> filteredProducts = driver.findElements(By.cssSelector(".shelf-item"));

            System.out.println("\nApplied vendor filter: " + vendor);
            System.out.println("Total products after applying " + vendor + " filter: " + filteredProducts.size());

        } catch (Exception e) {
            System.out.println("Failed to apply vendor filter: " + vendor + " - " + e.getMessage());
        }
    }

    private void verifyProductsByVendor(String expectedVendor) {
        try {
            List<WebElement> vendorNames = driver.findElements(By.cssSelector("p.shelf-item__vendor"));
            for (WebElement vendorElement : vendorNames) {
                String vendor = vendorElement.getText().trim();
                Assert.assertTrue(vendor.equalsIgnoreCase(expectedVendor),
                        "Product vendor mismatch: Expected " + expectedVendor + " but found " + vendor);
            }

        } catch (Exception e) {
            System.out.println("Verification failed for vendor: " + expectedVendor + " - " + e.getMessage());
        }
    }

    private void unselectVendorFilter(String vendor) {
        try {
            WebElement vendorElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(text(),'" + vendor + "')]")));

            scrollToElement(vendorElement);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(vendorElement)).click();
            } catch (ElementClickInterceptedException ex) {
                js.executeScript("arguments[0].click();", vendorElement);
            }

            Thread.sleep(2000);
            System.out.println("Unselected vendor filter: " + vendor);
        } catch (Exception e) {
            System.out.println("Failed to unselect vendor filter: " + vendor + " - " + e.getMessage());
        }
    }

    private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
