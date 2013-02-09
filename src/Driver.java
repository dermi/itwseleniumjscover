import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Driver {

	public static void main(String args[])
	{
		//retrieve chromedriver from my disk
	    File file = new File("C:\\Users\\ucabd_000\\workspace\\ApplicationSeleniumTest\\chromedriver.exe");
	    System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
		
	    //set desired capabilities to run Chrome Driver
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        List<String> arguments = Arrays.asList("--disable-web-security","--allow-file-access-from-files"); 
        capabilities.setCapability("chrome.switches", arguments);
        
        WebDriver driver = new ChromeDriver(capabilities);
        JavascriptExecutor js = (JavascriptExecutor)driver;
       
        // Load JSCover test harness
        driver.get("http://localhost:8080/jscoverage.html?/index.html");
        
        
        // Pages in iframe maintain separate DOM
        // so webdriver needs to "switch" to the browserIframe to interact
        driver.switchTo().frame(driver.findElement(By.id("browserIframe")));
        
         
        WebDriverWait wait = new WebDriverWait(driver, 10);
        
        // wait until "Browse Apps" is clikable
        //WebElement browseAppsEl = wait.until(ExpectedConditions.elementToBeClickable(By.id("br")));
       // WebElement browseAppsEl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='br']")));
        WebElement browseAppsEl = driver.findElement(By.id("br"));
        browseAppsEl.click();
        
        
        //WebElement faqEl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='faq']")));
        //faqEl.click();

        // wait until Geography category is clikable
        WebElement geographyCategoryEl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='3']")));
        //WebElement geographyCategoryEl = driver.findElement(By.xpath("//*[@id='3']"));
        geographyCategoryEl.click();
        
        WebElement appElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app-pane']/div[1]/a/img")));
        appElement.click();
        
        // Switch back to the main document
        driver.switchTo().defaultContent();
        
        
        // First select the tab by running the relevant script
        js.executeScript("jscoverage_selectTab('summaryTab');");
        // Then update the coverage information
        js.executeScript("jscoverage_recalculateSummaryTab();");
        
        // Finally read the total coverage
        WebElement total = driver.findElement(By.xpath("//span[starts-with(@id, 'summaryTotal')]"));
        System.out.println("Total coverage:" + total.getText());
        
        // quit the test
        //driver.quit();
     
        
	}
}
