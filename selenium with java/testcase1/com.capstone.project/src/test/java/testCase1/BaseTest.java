package testCase1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;


public class BaseTest {
	WebDriver driver;
	@Before
	public void openurl() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://bstackdemo.com/");
		driver.manage().window().maximize();
		
		System.out.println("Before Title of the page: "+ driver.getTitle());
		System.out.println("Before URL of the page: "+ driver.getCurrentUrl());
	}
	
	@After
	public void closeUrl() {
		
		System.out.println("After Title of the page: "+ driver.getTitle());
		System.out.println("After URL of the page: "+ driver.getCurrentUrl());
		driver.quit();
		
	}


}
