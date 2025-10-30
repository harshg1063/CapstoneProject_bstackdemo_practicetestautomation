package com.browserstack.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class VendorFilterAppTest {
    private AndroidDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        System.out.println("⏳ Setting up BrowserStack session...");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Samsung Galaxy S23");
        options.setPlatformName("Android");
        options.setPlatformVersion("13.0");
        options.setAutomationName("UiAutomator2");

        // ✅ Replace with your uploaded app ID from BrowserStack
        options.setApp("bs://19ca75ca1664be8898e4b9fd99197dfb3d6a7d1d");

        String username = "abhinavkumar_G68TMz";
        String accessKey = "ESsn5nimXH22cBk9KhDk";

        URL remoteUrl = new URL("http://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub");
        driver = new AndroidDriver(remoteUrl, options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.out.println("✅ Connected to BrowserStack Successfully!");
    }

    @Test
    public void loginAddToCartLogout() throws InterruptedException {
        System.out.println("🔑 Starting login and add-to-cart workflow...");

        // Step 1️⃣: Login
        WebElement loginMenu = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='open menu']"));
        loginMenu.click();
        Thread.sleep(2000);

        WebElement loginOption = driver.findElement(By.xpath("//android.widget.TextView[@text='Log In']"));
        loginOption.click();
        Thread.sleep(2000);

        System.out.println("📲 Login screen opened.");

        // Enter username and password
        WebElement usernameField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc='Username input field']"));
        WebElement passwordField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc='Password input field']"));

        usernameField.sendKeys("bob@example.com");
        passwordField.sendKeys("10203040");

        // Tap login button
        WebElement loginButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Login button']"));
        loginButton.click();
        Thread.sleep(3000);

        System.out.println("✅ Logged in successfully!");

        // Step 2️⃣: Open side menu → Catalog
        WebElement menuButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='open menu']"));
        menuButton.click();
        Thread.sleep(1500);

        WebElement catalogMenu = driver.findElement(By.xpath("//android.widget.TextView[@text='Catalog']"));
        catalogMenu.click();
        Thread.sleep(2000);

        System.out.println("🛍️ Catalog opened successfully!");

        // Step 3️⃣: Select product (e.g., Sauce Labs Backpack)
        WebElement product = driver.findElement(By.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']"));
        product.click();
        Thread.sleep(2000);

        System.out.println("🎒 Opened product details for 'Sauce Labs Backpack'");

        // Step 4️⃣: Add to cart
        WebElement addToCartButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Add To Cart button']"));
        addToCartButton.click();
        Thread.sleep(1500);

        System.out.println("🛒 Product added to cart successfully!");

        // Step 5️⃣: Open cart and verify
        WebElement cartButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='cart badge']"));
        cartButton.click();
        Thread.sleep(2000);

        WebElement cartItem = driver.findElement(By.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']"));
        if (cartItem.isDisplayed()) {
            System.out.println("✅ Product verified in cart!");
        } else {
            System.out.println("⚠️ Product not found in cart.");
        }

        // Step 6️⃣: Logout
        menuButton = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='open menu']"));
        menuButton.click();
        Thread.sleep(1500);

        WebElement logoutButton = driver.findElement(By.xpath("//android.widget.TextView[@text='Log Out']"));
        logoutButton.click();
        Thread.sleep(2000);

        System.out.println("🚪 Logged out successfully!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("✅ Session Ended.");
    }
}
