package Automatique;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import BaseDeDonnee.DataBase;
import BaseDeDonnee.Initial;
import BaseDeDonnee.Utile;

public class EtatParmtEnBoucle  extends Initial {

	@Test
	public void lesEtats () throws ClassNotFoundException, SQLException, IOException {

		DataBase database = new DataBase();
		database.setUpDataBase();
        Connection connecter = database.connectedC;
		
		// recupere codeSrt d'etats
				Statement stmtSRT = connecter.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//	ResultSet resulSRT = stmtSRT.executeQuery("select CODSRT  from tpspost.AFPTST where CODSRT in ('XESBEDETI','ACHGIDTINS','AFACDCSDH')");
				//ResultSet resulSRT = stmtSRT.executeQuery("select CODSRT from tpspost.AFPTST");
				ResultSet resulSRT = stmtSRT.executeQuery("select CODSRT  from tpspost.AFPTST where CODSRT in ('ACPTOD002','P_KEFP','CPSMNL10D')");
				
				while (resulSRT.next()) {

					String codSrt = resulSRT.getString("CODSRT");
				
					System.out.println("les etats :--->" + codSrt);

					String links = "http://192.168.0.197:7003/unipost/srt/fllnet.jsp?pathsrt=srtmdl&rng=";
					System.out.println("--------------------");

					driver.navigate().to(links + codSrt);
					Utile.waitTime(1000);
					
				
					 //WebElement interfaceSrt = driver.findElement(By.xpath("//table[@class='tableau_str']"));
					WebElement interfaceSrt =  driver.findElement(By.xpath("//table[@class='tableau_str']"));
	    	          //capture d'écran
	  			    File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  			      BufferedImage  fullScreen = ImageIO.read(screenshot);
	  			      Point location = interfaceSrt.getLocation();
	  			        int width = interfaceSrt.getSize().getWidth();
	  			        int height = interfaceSrt.getSize().getHeight();
	  			        BufferedImage captureSrt = fullScreen.getSubimage(location.getX(), location.getY(), width, height);
	  			        ImageIO.write(captureSrt, "png", screenshot);		
	  			        FileUtils.copyFile(screenshot, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\\\LesEtatsParmt\\"+codSrt+".PNG"));
	  			    
	  			  
					
                       }
					
}
}