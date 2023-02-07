package BaseDeDonnee;





import org.openqa.selenium.WebDriver;




public class Utile {
	  
	static WebDriver driver;
	  

	static public void waitTime(int ms) {
	
			
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
	
}
