import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


// Chapter 5
public class Chapter05_Locators {
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/MSI/Documents/chromedriver-win64-129-2/chromedriver.exe");		
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:/Users/MSI/Downloads/chrome-win64 (1)/chrome-win64/chrome.exe");
        chromeOptions.addArguments("user-data-dir=C:/Users/MSI/AppData/Local/Google/Chrome for Testing/User Data");
        WebDriver driver = new ChromeDriver(chromeOptions);
        
        // implicit wait - if no wait, Selenium is so fast and can't locate error message cuz it haven't loaded, yet.
 		// max timeout should be 5 seconds
 		// This line waits for 5 seconds if sth does not show up. After 5 seconds it crashes.
 		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.get("https://rahulshettyacademy.com/locatorspractice");
		driver.findElement(By.id("inputUsername")).sendKeys("rahul");
		driver.findElement(By.name("inputPassword")).sendKeys("hello123");
		driver.findElement(By.className("signInBtn")).click();
		
		
		String errorMessage = driver.findElement(By.cssSelector("p.error")).getText();
		
		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());
		System.out.println(errorMessage);
		
		driver.findElement(By.linkText("Forgot your password?")).click();
		
		try {
			Thread.sleep(1000);  // pause the code for 1 second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("John Dick");
		driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys("johndick@hotmail.com");
		driver.findElement(By.xpath("//input[@type='text'][2]")).clear();
		driver.findElement(By.cssSelector("input[type='text']:nth-child(3)")).sendKeys("johndick@gmail.com");
		driver.findElement(By.xpath("//form/input[3]")).sendKeys("0(312) 741 29 56");
		driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
		
		String secondErrorMessage = driver.findElement(By.cssSelector("form p")).getText();
		
		System.out.println(secondErrorMessage);
		
	}
}