package BaseDeDonnee;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.BeforeSuite;





public class Initial {

	public static WebDriver driver = null; 
	
	
	String urlMp = "http://192.168.0.197:7003/unipost/v6/ecrans/index.jsp?etb=poste&pack=packccp";
	String urlAuthentification = "192.168.0.197:7003/unipost/v6/ecrans/index.jsp?etb=poste&pack=packccp";
	
	
	@BeforeSuite
	public void initialize() throws IOException, InterruptedException { 
		  
	
		System.setProperty("webdriver.chrome.driver", "C:\\selenium webdriver\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
	
		
		
		driver = new ChromeDriver();
	
		driver.get(urlMp);
		driver.manage().window().maximize();
		
		driver.get("http://17001:17001" + "@" + urlAuthentification);
		
		
    } 
	
}
