package Automatique;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import BaseDeDonnee.DataBase;
import BaseDeDonnee.Initial;
import BaseDeDonnee.Utile;

public class parametresBoucle extends Initial {

	String etatSrt = "";

	@Test
	public void lesEtats () throws ClassNotFoundException, SQLException, IOException {

		DataBase database = new DataBase();
		database.setUpDataBase();

		Connection connect = database.connectedC;

		// recupere code d'etats
		Statement stmt0 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resulRe0 = stmt0.executeQuery("select distinct AFP from valefs order by AFP");
		// select distinct codope from VALTST order by CODOPE

		while (resulRe0.next()) {

			String Srt = resulRe0.getString("AFP");
			etatSrt = Srt;
			System.out.println("les etats :--->" + Srt);

			// http://192.168.0.197:7003/unipost/srt/fllnetp.jsp?rng=RPAY&srv=FLLP
			String links = "http://192.168.0.197:7003/unipost/srt/fllnetp.jsp?rng=" + Srt + "&srv=FLLP";
			System.out.println("-------------------------------------");

			driver.navigate().to(links);
			Utile.waitTime(2000);

			// recupere codafpID
			Statement stmt1 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulRe1 = stmt1
					.executeQuery("select distinct CODAFP from valefs where AFP='" + Srt + "' order by AFP");

			// recupere les valeurs VALAFP
			Statement stmt2 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulRe2 = stmt2
					.executeQuery("select CODAFP,VALAFP from valefs where AFP='" + Srt + "' order by AFP");

			while (resulRe1.next() && resulRe2.next()) {

				String codAfpId = resulRe1.getString("CODAFP");
				System.out.println("les id des champs -----> " + codAfpId);
				System.out.println("---------------------------------");

				Utile.waitTime(1000);

				String valAfp = resulRe2.getString("VALAFP");
				String coAfp = resulRe2.getString("CODAFP");
				System.out.println("l'id--" + coAfp + "+les valeurs -------> " + valAfp);

				driver.findElement(By.id(codAfpId)).sendKeys(valAfp);

				String mainOnglet = driver.getWindowHandle();
				System.out.println("tab est etat mainOnglet-----------:" + mainOnglet);
				System.out.println("====================================");
			}
			// capture l'etat
			WebElement cadreEtat = driver.findElement(By.xpath("//*[@id=\"frmAfp\"]/table"));

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullScreen = ImageIO.read(screenshot);

			// Find location of the webelement logo on the page
			Point location = cadreEtat.getLocation();

			// Find width and height of the located element logo
			int width = cadreEtat.getSize().getWidth();
			int height = cadreEtat.getSize().getHeight();

			// cropping the full image to get only the logo screenshot
			BufferedImage captureImage = fullScreen.getSubimage(location.getX(), location.getY(), width, height);
			ImageIO.write(captureImage, "png", screenshot);


			Date currentdate = new Date();
			String dateActuel = currentdate.toString().replace(" ", "-").replace(":", "-");
			FileUtils.copyFile(screenshot, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\LesEtats\\"
					+ etatSrt + '+' + dateActuel + ".PNG"));
		

			// Valider l'Eta
			
			  Set<String> windows = driver.getWindowHandles();
		         Iterator <String> it = windows.iterator();
		         String pere = it.next();
	         driver.findElement(By.id("ValiderAFP")).click();
	
	       
	         String enfant = it.next();
	         driver.switchTo().window(enfant);
	
			
			
		WebElement cadreResultatEtat = driver.findElement(By.xpath("//*[@id=\"divTab\"]/table"));
			File screenshotTesultatEtat = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullScreenResultEtat = ImageIO.read(screenshotTesultatEtat);
			Point locationResultEtat = cadreResultatEtat.getLocation();
			int widthResult = cadreResultatEtat.getSize().getWidth();
			int heightResult = cadreResultatEtat.getSize().getHeight();
         	BufferedImage captureImageResult = fullScreenResultEtat.getSubimage(locationResultEtat.getX(),
					locationResultEtat.getY(), widthResult, heightResult);
			ImageIO.write(captureImageResult, "png", screenshotTesultatEtat);
			FileUtils.copyFile(screenshotTesultatEtat,
					new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\LesEtats\\" + etatSrt + '+' + "resultat"
							+ '+' + dateActuel + ".PNG")); 

			// String titre = driver.getWindowHandle();
			// System.out.println("tab est ici apres valider-----------:" + onglets);

			// Pour ouvrir ds nouvel onglet & nvl windowx
			// driver.switchTo().newWindow(WindowType.TAB);
			// driver.switchTo().newWindow(WindowType.WINDOW);
		}

	}
}
