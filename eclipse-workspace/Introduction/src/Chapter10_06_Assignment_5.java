import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chapter10_06_Assignment_5 {
	public static void main(String[] args) {
		// ESTABLISH WEBDRIVER
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/MSI/Documents/chromedriver-win64-129-2/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("C:/Users/MSI/Downloads/chrome-win64 (1)/chrome-win64/chrome.exe");
		chromeOptions.addArguments("user-data-dir=C:/Users/MSI/AppData/Local/Google/Chrome for Testing/User Data");
		WebDriver driver = new ChromeDriver(chromeOptions);
		// Choose one of them and comment out the other to test.
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// GO TO THE WEBSITE for testing
		driver.get("https://the-internet.herokuapp.com/");
		driver.manage().window().maximize();

		// Click "Click Here" to open a new window
		driver.findElement(By.cssSelector("a[href='/nested_frames']")).click();
		// RAHULS SOLUTION:

		// COUNT THE FRAMES in the parent frameset and print it out
		int frameCount = driver.findElements(By.tagName("frame")).size();
		System.out.println("# of frames in the first frameset: " + frameCount);

		// SELECT FRAME to do operations. There two alternatives:
		// First option:
		// driver.switchTo().frame(0);
		// Second option:
		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name='frame-top']")));

		// COUNT THE FRAMES int the child frameset and print it out
		int frameCount2 = driver.findElements(By.tagName("frame")).size();
		System.out.println("# of frames in the second frameset: " + frameCount2);

		// driver.switchTo().frame(1);
		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name='frame-middle']")));

		String text = driver.findElement(By.id("content")).getText();
		System.out.println("Text in the middle frame is: " + text);

		// At the end, we should quit the frame:
		driver.switchTo().defaultContent();

		// RAHUL'S SOLUTION to the assignment is pretty straight!
		driver.findElement(By.linkText("Nested Frames")).click();
		driver.switchTo().frame("frame-top");
		driver.switchTo().frame("frame-middle");
		System.out.println(driver.findElement(By.id("content")).getText());

	}

}
