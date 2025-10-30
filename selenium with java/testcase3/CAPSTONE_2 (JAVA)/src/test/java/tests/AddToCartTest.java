package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddToCartTest {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    CartPage cartPage;

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        loginPage.login("demouser", "testingisfun99");
        homePage.waitForHomepage();
        cartPage.addMultipleItems(3);
        int count = cartPage.verifyCartItems();
        Assertions.assertTrue(count >= 3, "Cart should contain at least 3 items.");
    }

    @AfterAll
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
