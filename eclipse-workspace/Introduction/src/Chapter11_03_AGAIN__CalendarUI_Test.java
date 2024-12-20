import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;

public class Chapter11_03_AGAIN__CalendarUI_Test {

	public static void main(String[] args) {

		// ESTABLISH WEBDRIVER
		System.setProperty("webdriver.chrome.driver", "C:/Users/MSI/Documents/chromedriver-win64-130/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("C:/Users/MSI/Downloads/chrome-win64/chrome-win64/chrome.exe");
		WebDriver driver = new ChromeDriver(chromeOptions);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();

		// GO TO THE WEBSITE for testing
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

		// Date to select
		String month = "6";
		String day = "15";
		String year = "2007";

		// First click to the calendar UI to select year
		driver.findElement(By.cssSelector(".react-date-picker__inputGroup")).click();
		driver.findElement(By.cssSelector("button[class='react-calendar__navigation__label']")).click();
		driver.findElement(By.cssSelector("button[class='react-calendar__navigation__label']")).click();

		// GET YEAR
		String currentYear = "0";
		while (!currentYear.equals(year)) {
			// Get all year elements from calendar UI
			List<WebElement> years = driver
					.findElements(By.cssSelector("div[class='react-calendar__viewContainer'] button"));

			// Check whether to go back or forward based on the first year's text
			int displayedFirstYear = Integer.parseInt(years.get(0).getText());
			int targetYear = Integer.parseInt(year);

			// Iterate over the year elements to find the target year
			for (WebElement yearElement : years) {
				currentYear = yearElement.getText();
				if (currentYear.equals(year)) {
					yearElement.click();
					break; // exit the for loop
				}
			}
			if (currentYear.equals(year)) {
		        break; // Ensures the `while` loop is exited after clicking the year
		    }

			// Navigate back or forward
			String navigationButtonXPath = (displayedFirstYear > targetYear) ? "//button[contains(text(),'‹')]"
					: "//button[contains(text(),'›')]";
			driver.findElement(By.xpath(navigationButtonXPath)).click();
		}

		// GET MONTH
		List<WebElement> months = driver
				.findElements(By.cssSelector("div[class='react-calendar__viewContainer'] button"));
		
		months.get(Integer.parseInt(month) - 1).click();

		// GET DAY
		List<WebElement> days = driver.findElements(By.cssSelector(
				".react-calendar__month-view__days__day:not(.react-calendar__month-view__days__day--neighboringMonth)"));

		for (WebElement we : days) {
			if (we.getText().equalsIgnoreCase(day)) {
				we.click();
			}
		}
	}
}
