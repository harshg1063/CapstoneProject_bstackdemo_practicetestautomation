package com.browserstack.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WikipediaTest {

    private AppiumDriver driver;

    @BeforeClass
    public void setUp() throws Exception {

        // ✅ Optional: Fix SSL trust issues (Windows only)
        System.setProperty("javax.net.ssl.trustStoreType", "Windows-ROOT");

        // ✅ BrowserStack credentials
        String USERNAME = "abhinavkumar_G68TMz";
        String ACCESS_KEY = "ESsn5nimXH22cBk9KhDk";

        // ✅ BrowserStack-specific options
        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", USERNAME);
        bstackOptions.put("accessKey", ACCESS_KEY);
        bstackOptions.put("projectName", "Wikipedia Sample App");
        bstackOptions.put("buildName", "Appium Java Test Build");
        bstackOptions.put("sessionName", "Wikipedia Search Test");
        bstackOptions.put("deviceName", "Google Pixel 7");
        bstackOptions.put("osVersion", "13.0");

        // ✅ Appium options for Android
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("app", "bs://48c25d1575fc56e588e4bf3e4b97b459f0ad43eb");
        options.setCapability("bstack:options", bstackOptions);

        // ✅ Initialize the driver with the wrapped capabilities
        driver = new AndroidDriver(
                new URL("https://hub-cloud.browserstack.com/wd/hub"),
                options
        );

        System.out.println("✅ BrowserStack session started successfully!");
    }

    @Test
    public void testWikipediaSearch() throws InterruptedException {
        System.out.println("🔍 Starting Wikipedia search test...");

        Thread.sleep(5000);

        driver.findElement(By.id("org.wikipedia.alpha:id/search_container")).click();
        driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text")).sendKeys("BrowserStack");

        Thread.sleep(3000);

        boolean resultsDisplayed =
                driver.findElements(By.id("org.wikipedia.alpha:id/page_list_item_title")).size() > 0;

        if (resultsDisplayed) {
            System.out.println("✅ Search results displayed successfully!");
        } else {
            System.out.println("❌ No search results found!");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🔚 BrowserStack session closed.");
        }
    }
}
