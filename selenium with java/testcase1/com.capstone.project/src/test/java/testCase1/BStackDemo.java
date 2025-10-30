package testCase1;

import java.time.Duration;
import java.util.List;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BStackDemo extends BaseTest {
	WebDriverWait wait;
    @Test
    public void testingFavorites() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
        WebElement signIn = driver.findElement(By.cssSelector(".UserNav_root__343id"));
        signIn.click();
        Thread.sleep(2000);
//----------------------------username--------------------------------------------------        
        WebElement usernameDropdown = driver.findElement(By.cssSelector("#username div[class*='css-1hwfws3']"));
        usernameDropdown.click();
        List<WebElement> usernames = driver.findElements(By.cssSelector("div[id^='react-select-2-option']"));
        System.out.println("Username Options: ");
        for (WebElement option : usernames) {
            System.out.println(option.getText());
        }
        boolean userSelected = false;
        for (int i = 0; i < usernames.size(); i++) {
            try {
                WebElement freshOption = driver.findElements(By.cssSelector("div[id^='react-select-2-option']")).get(i);
                if (freshOption.getText().trim().equalsIgnoreCase("demouser")) {
                    freshOption.click();
                    userSelected = true;
                    break;
                }
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                usernames = driver.findElements(By.cssSelector("div[id^='react-select-2-option']"));
                i--; 
            }
        }
        Thread.sleep(1000);
//------------------------password------------------------------------------
        
        WebElement passwordDropdown = driver.findElement(By.cssSelector("#password div[class*='css-1hwfws3']"));
        passwordDropdown.click();
        Thread.sleep(1000);
        List<WebElement> passwordOptions = driver.findElements(By.cssSelector("div[id^='react-select-3-option']"));
        System.out.println("Password Options: ");
        for (WebElement option : passwordOptions) {
            System.out.println(option.getText());
        }

        boolean passSelected = false;
        for (int i = 0; i < passwordOptions.size(); i++) {
            try {
                WebElement freshOption = driver.findElements(By.cssSelector("div[id^='react-select-3-option']")).get(i);
                if (freshOption.getText().trim().equalsIgnoreCase("testingisfun99")) {
                    freshOption.click();
                    passSelected = true;
                    break;
                }
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                passwordOptions = driver.findElements(By.cssSelector("div[id^='react-select-3-option']"));
                i--;
            }
        }
//------------------------------login button---------------------------------------        
        WebElement loginBtn = driver.findElement(By.id("login-btn"));
        loginBtn.click();

        Thread.sleep(2000);
//------------------------------total Products------------------------------------------------------------
        List<WebElement> products = driver.findElements(By.cssSelector(".shelf-item"));
        int totalProducts = products.size();
        System.out.println("Total Products Found: " + totalProducts);
        System.out.println("Available Products: ");
        for (WebElement product : products) {
            String productName = product.findElement(By.cssSelector(".shelf-item__title")).getText();
            String productPrice = product.findElement(By.cssSelector(".val")).getText();
           
            System.out.println( productName + " - Price: " + productPrice);
        }

        Thread.sleep(2000);
    
//-----------------------------Add To Favourates-----------------------------------------

        List<WebElement> favButtons = driver.findElements(By.cssSelector("button[aria-label='delete']"));
        int count = Math.min(4, favButtons.size());

        for (int i = 0; i < count; i++) {
            favButtons.get(i).click();
            Thread.sleep(4000);
        }

        System.out.println(count + "products added to favourites successfully!");

//-----------------------navigate to favorates-----------------------

WebElement favourites = wait.until(ExpectedConditions.elementToBeClickable(By.id("favourites")));
favourites.click();

System.out.println("navigated to Favourites successfully!");
Thread.sleep(3000);

//----------------------- verify Favourites -----------------------
List<WebElement> favProducts = driver.findElements(By.cssSelector(".shelf-item__title"));
System.out.println("products displayed in Favourites are: ");
for (WebElement favProduct : favProducts) {
 System.out.println("->" + favProduct.getText());
}

int favCount = favProducts.size();
if (favCount >= 4) {
 System.out.println("all added products appeared in favourites");
} else {
 System.out.println("expected 4 favourites, but found only " + favCount);
}

// --------------------Check name, image, and price-----------------------
List<WebElement> favItems = driver.findElements(By.cssSelector(".shelf-item"));
for (WebElement item : favItems) {
    String name = item.findElement(By.cssSelector(".shelf-item__title")).getText();
    String price = item.findElement(By.cssSelector(".val")).getText();
    WebElement image = item.findElement(By.cssSelector(".shelf-item__thumb img"));
    boolean imgVisible = image.isDisplayed();

    System.out.println("\n " + name);
    System.out.println("price: " + price);
    System.out.println("image visible: " + imgVisible);

    if (!name.isEmpty() && !price.isEmpty() && imgVisible)
        System.out.println("details displayed correctly for " + name);
    else
        System.out.println("missing detail for " + name);
}

Thread.sleep(2000);

//------------------Remove one product-------------------------------------------
List<WebElement> favBefore = driver.findElements(By.cssSelector(".shelf-item"));
int beforeCount = favBefore.size();

if (beforeCount > 0) {
 System.out.println("Products before removing: " + beforeCount);

 favBefore.get(0).findElement(By.cssSelector("button[aria-label='delete']")).click();
 Thread.sleep(2000);

 List<WebElement> favAfter = driver.findElements(By.cssSelector(".shelf-item"));
 int afterCount = favAfter.size();

 if (afterCount == beforeCount - 1) {
     System.out.println("product removed successfully." + beforeCount + " → " + afterCount);
 } else {
     System.out.println("product not removed correctly. Current count: " + afterCount);
 }

 System.out.println("\nProducts left in favourites:");
 for (WebElement item : favAfter) {
     String name = item.findElement(By.cssSelector(".shelf-item__title")).getText();
     String price = item.findElement(By.cssSelector(".val")).getText();
     System.out.println(name + " - " + price);
 }

} else {
 System.out.println("no favourites found to remove");
}


    }
}
