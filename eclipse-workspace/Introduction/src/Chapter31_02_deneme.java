import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Chapter31_02_deneme {

	public static void main(String[] args) throws IOException, InterruptedException {
		// set chrome driver
		System.setProperty("webdriver.chrome.driver", "C:/Users/MSI/Documents/chromedriver-win64-130/chromedriver.exe");

		// Set ChromeOptions
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("C:/Users/MSI/Documents/chrome-win64/chrome-win64/chrome.exe"); // Custom binary v130

		// Launch driver
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		// set up wait and get to website for test
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

		// variables: -----------------------------------------------------
		String fruitName = "Apple";
		String updatedValue = "603";
		String fileName = "C:\\Users\\MSI\\Downloads\\download.xlsx";
		System.out.println(fileName);

		// DOWNLOAD -----------------------------------------------------
		driver.findElement(By.id("downloadButton")).click();
		Thread.sleep(3000);

		// EDIT -------------------------------------------------
		// Load the Excel file
		FileInputStream file = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

		// Identify the columns
		int fruitNameCol = -1;
		int priceCol = -1;

		Row headerRow = sheet.getRow(0); // Header row
		for (Cell cell : headerRow) {
			String header = cell.getStringCellValue();
			if (header.equalsIgnoreCase("fruit_name")) {
				fruitNameCol = cell.getColumnIndex(); // get the column index of fruitName
			} else if (header.equalsIgnoreCase("price")) {
				priceCol = cell.getColumnIndex(); // get the column index of price
			}
		}

		// Find the row with the specified fruit name and update the price
		for (Row row : sheet) {
			Cell fruitCell = row.getCell(fruitNameCol);
			if (fruitCell != null && fruitCell.getStringCellValue().equalsIgnoreCase(fruitName)) {
				Cell priceCell = row.getCell(priceCol);
				priceCell.setCellValue(updatedValue);
				break;
			}
		}

		// Write changes back to the file
		file.close(); // Close the input stream before overwriting the file
		FileOutputStream outFile = new FileOutputStream(new File(fileName));
		workbook.write(outFile);
		outFile.close();
		workbook.close();

		// UPLOAD -----------------------------------------------------
		WebElement upload = driver.findElement(By.id("fileinput"));
		upload.sendKeys("C:\\Users\\MSI\\Downloads\\download.xlsx");

		// wait for success message to show up and wait for disappear
		By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
		String toastText = driver.findElement(toastLocator).getText();
		System.out.println(toastText);
		Assert.assertEquals("Updated Excel Data Successfully.", toastText);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
		
		// VERIFY -----------------------------------------------------
		// normal code without stream
		/*
		 * String actualPrice = "0"; List<WebElement> fruits =
		 * driver.findElements(By.xpath("//div[@data-column-id=\"2\"]")); for
		 * (WebElement fruit : fruits) { if
		 * (fruit.getText().equalsIgnoreCase(fruitName)) { actualPrice =
		 * fruit.findElement(By.xpath("following-sibling::*[2]")).getText(); } }
		 */
		
		// with stream it is more elegant! 
		String actualPrice = driver.findElements(By.xpath("//div[@data-column-id='2']"))
		        .stream()
		        .filter(fruit -> fruit.getText().equalsIgnoreCase(fruitName))
		        .map(fruit -> fruit.findElement(By.xpath("following-sibling::*[2]")).getText())
		        .findFirst()
		        .orElse("0");
		
		System.out.println(actualPrice);
		Assert.assertEquals(updatedValue, actualPrice);

	}
}
