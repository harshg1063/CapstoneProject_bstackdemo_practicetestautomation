package browserstackwebapp;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    public static final String USERNAME = "harshgupta_o1FpYc";
    public static final String ACCESS_KEY = "acc5QdEgzVymuaSj2C4G";

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {

        // Browser capabilities
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", "chrome");

        // BrowserStack options
        MutableCapabilities bstackOptions = new MutableCapabilities();
        bstackOptions.setCapability("osVersion", "14.0");
        bstackOptions.setCapability("deviceName", "Samsung Galaxy S23");
        bstackOptions.setCapability("realMobile", "true");
        bstackOptions.setCapability("projectName", "Android Web Automation");
        bstackOptions.setCapability("buildName", "PracticeTestAutomation Build");
        bstackOptions.setCapability("userName", USERNAME);
        bstackOptions.setCapability("accessKey", ACCESS_KEY);

        // Attach BrowserStack options to main capabilities
        caps.setCapability("bstack:options", bstackOptions);

        // Initialize driver
        driver = new RemoteWebDriver(new URL("http://hub.browserstack.com/wd/hub"), caps);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
