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
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import BaseDeDonnee.DataBase;
import BaseDeDonnee.Initial;
import BaseDeDonnee.Utile;


public class ClientBoucle extends Initial {
	
	String ope = "";
	String lienG = "http://192.168.0.197:7003/unipost/v6/ecrans/index.jsp?pack=packgcc&etb=poste&rng=" ;
	
	
	@Test
	public void lesOperations () throws ClassNotFoundException, SQLException, IOException {
		
		DataBase datab = new DataBase();
	    datab.setUpDataBase();

		Connection connect = datab.connectedC;

	

		//les opérations de la colonne CODOPE : (R310...R315)
		Statement stmt0 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST where codope like 'R3%' and codope not like 'R300'  order by CODOPE");

	
		
		while (resulRe0.next()) {
			
			String operation = resulRe0.getString("CODOPE");
			ope = operation;	
			System.out.println("lien Formulaire des operations :--->" + operation);
			
			
			driver.navigate().to(lienG + operation); 
			System.out.println("--------------------------------------");
			//Requete : recupere nom du champs + valeur correspond
			Statement stmt1 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//ResultSet resulRe1 = stmt1.executeQuery("select RUBOPE,VAL2OPE from vvaltst where CODOPE='"+operation+"' and codope not like 'R300'  order by TABOPE ");
			ResultSet resulRe1 = stmt1.executeQuery("select TABOPE,RUBOPE,VAL2OPE from vvaltst where CODOPE='"+operation+"' and codope not like 'R300'  order by TABOPE ");
			
			
			//Requete : recupere l'id 
			Statement stmt2 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulRe2 = stmt2.executeQuery("select TABOPE  from vvaltst where CODOPE='"+operation+"' and codope not like 'R300'  order by TABOPE");
			//ResultSet resulRe2 = stmt2.executeQuery("select 't'||tabzon as IDOPE from t_zontrn where trnzon='"+operation+"' and saizon='1' order by tabzon");
			
		/*	//Récupere localisation Bouton "Valider"
			Statement stmtV = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulReV = stmtV.executeQuery("select 'b'||tabzon as IDOPE from t_zontrn where trnzon='"+operation+"' and saizon='1' and tabzon ='5'  order by tabzon");
            */
			
		   /* String msgErreur0 = driver.findElement(By.id("divChg")).getText();
			System.out.println("msg erreur Result0---> : " + msgErreur0);
			 
			 //capture erreur des champs
			 File screen0 =  driver.findElement(By.id("divChg")).getScreenshotAs(OutputType.FILE);
			 Files.copy(screen0, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\Clients\\"+msgErreur0+".png"));
			  */
			 
			
			       while (resulRe1.next() && resulRe2.next()) {
			    	   String valOPE = resulRe1.getString("VAL2OPE");
			            String valTid = resulRe2.getString("TABOPE");
			          
		
			      driver.findElement(By.id(valTid)).sendKeys(valOPE);	
			      driver.findElement(By.id(valTid)).sendKeys(Keys.TAB);
			      //Message d'erreur
			      String msgErreur1 = driver.findElement(By.id("divChg")).getText();
				  System.out.println("message d'erreur Result1---> : " + msgErreur1);
				  
			             if (msgErreur1.equals("ENREGISTREMENT EXISTE")) {
			            	 Utile.waitTime(1000);
					  driver.navigate().to(lienG + operation + "&CODMAJ=M");
					  
					  System.out.println("--------passer en mode modification--------");
					
					  driver.findElement(By.id(valTid)).sendKeys(valOPE);	
					 // String valope =  valOPE;
					   //  driver.findElement(By.id(valTid)).sendKeys(valope);
					      if(valOPE.equals(null)) {
						    
						           driver.findElement(By.id(valTid)).sendKeys("");
						           driver.findElement(By.id(valTid)).sendKeys(Keys.TAB);
						           //driver.findElement(By.id(valTid)).sendKeys("");
					                 }
					
					   
					 
				  } 
			             
			            // driver.findElement(By.id(valTid)).sendKeys(valOPE);	
						  // driver.findElement(By.id(valTid)).sendKeys(Keys.TAB);
				 //  driver.findElement(By.id("b5")).click();
			       }
			       
			  
				/*  File screen =  driver.findElement(By.id("divChg")).getScreenshotAs(OutputType.FILE);
				  Files.copy(screen, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\Clients\\"+msgErreur1+".png")); 
			   
			       WebElement interfaceOpe = driver.findElement(By.xpath("//*[@id=\"MapOpe\"]/table"));
   	         
	    	          //capture d'écran(capturé formulaire d'opération)
	  			      File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  			      BufferedImage  fullScreen = ImageIO.read(screenshot);
	  			  
	  			        Point location = interfaceOpe.getLocation();	  		  
	  			        int width = interfaceOpe.getSize().getWidth();
	  			        int height = interfaceOpe.getSize().getHeight();
	  			
	  			        BufferedImage captureOpe = fullScreen.getSubimage(location.getX(), location.getY(),width, height);
	  			        ImageIO.write(captureOpe, "png", screenshot);
	  			        
	  			        //Save cropped Image at destination location physically.
	  			        FileUtils.copyFile(screenshot, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\Clients\\"+ope+".PNG"));*/
	    	            
	   	      
			       //valider l'opérations
	  			      
	  			        
	  			       
			               //  while (resulReV.next()) {
			                  //  String IDOPEvalider = resulReV.getString("TABOPE");
                              //  driver.findElement(By.id(IDOPEvalider)).click();
			    	            //driver.findElement(By.id(IDOPEvalider)).click();
	                  
	                           
	                            
			    	            // }
	                    
			    	            
			         
			                
			    	            
			                 
		
		}
		
		//fermeture de la class	
	
	}
	}
