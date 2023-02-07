package Automatique;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import BaseDeDonnee.Initial;

public class creationCMsgErreurStatique extends Initial {

	@Test
	public void lesEtatsparametrer () {
		String links = "http://192.168.0.197:7003/unipost/v6/ecrans/index.jsp?pack=packgcc&etb=poste&rng=R310";
		driver.navigate().to(links);
	WebElement champ1 =driver.findElement(By.id("t0"));
	String msgErreur = driver.findElement(By.id("divChg")).getText();
	System.out.println("message d'erreur Champs---> : " + msgErreur);
	champ1.sendKeys("098765432");
	champ1.sendKeys(Keys.TAB);
	
	
	
	String msgErreur1 = driver.findElement(By.id("divChg")).getText();
		System.out.println("Message d'erreur General---> : " + msgErreur1);
	}
	
}
