import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Chapter34_02_autoIT {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		// set chrome download path
		String downloadPath = System.getProperty("user.dir");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);

		// set chrome driver
		System.setProperty("webdriver.chrome.driver", "C:/Users/MSI/Documents/chromedriver-win64-130/chromedriver.exe");

		// Set ChromeOptions
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("C:/Users/MSI/Documents/chrome-win64/chrome-win64/chrome.exe"); // Custom binary v130
		chromeOptions.setExperimentalOption("prefs", chromePrefs); // download directory preferences
		
		// Launch driver
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		// set up wait and get to website for test
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://www.ilovepdf.com/pdf_to_jpg");
		driver.findElement(By.id("pickfiles")).click();
		Thread.sleep(1000);
		
		// upload .pdf file and download the converted file
		Runtime.getRuntime().exec("C:\\Users\\MSI\\Documents\\check\\fileUpload.exe");
		driver.findElement(By.id("processTaskTextBtn")).click();
		driver.findElement(By.cssSelector(".downloader__btn.active")).click();
		
		// if in your local, you can use below code. However, it is not generic and not recommended.
		// File f = new File("C:\\Users\\MSI\\Downloads\\Appointment_page-0001.jpg");
		// if(f.exists())  System.out.println("file found!");
		
		// verify the downloaded file
		File f = new File(downloadPath + "/Appointment_page-0001.jpg");
		Thread.sleep(2500); // without this, file could not be located by f.exists()...
		if (f.exists()){
			Assert.assertTrue(f.exists());
			if (f.delete())		System.out.println("file deleted");
			// f.delete() returns true if the file is successfully deleted. 
		}				
	}
}