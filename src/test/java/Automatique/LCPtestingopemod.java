package Automatique;



import java.awt.AWTException;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;



import BaseDeDonnee.DataBase;
import BaseDeDonnee.Initial;
import BaseDeDonnee.Utile;


public class LCPtestingopemod extends Initial {
	
	String oper = "";
	String lienG = "http://192.168.0.197:7003/unipost/v6/ecrans/index.jsp?pack=packgcc&etb=poste&rng=" ;

	
	
	
	@Test(priority = 1)
	public void lesOperations () throws ClassNotFoundException, SQLException, IOException, AWTException {
		
		DataBase datab = new DataBase();
	    datab.setUpDataBase();

		Connection connect = datab.connectedC;
		// ajouter par si lassaad
		String codmaj="C";
		boolean btest=true;
		boolean bmsg=true;

		
		Statement stmt0 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		//ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST where codope like 'A%' and codope not in ('A023','A034','A036','A037','A038','A044','A046','A066') order by CODOPE");
		//ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST where codope like 'B%'  order by CODOPE");
		//ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST where codope like 'A075' order by CODOPE");
		//ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST");
		
		//ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VVALTST where codope like 'R%' and codope not in ('R020') order by CODOPE");
		ResultSet resulRe0 = stmt0.executeQuery("select * from VVALTST where codope like 'R174' order by CODOPE");
		
		
		
		
		//',,'A043','A045'
		while (resulRe0.next()) {
			//les opérations de la colonne CODOPE : (A0.....R3)
			String operation = resulRe0.getString("CODOPE");
			//oper = operation;
			codmaj="C";
			
			System.out.println("--///--CODE d'operations--///-- :--->" + operation);
						
			
			
			do {
				
				Statement stmt1 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet resulRe1 = stmt1.executeQuery("select TABOPE,RUBOPE,VAL2OPE,CARZON,VALLSTOPE,TYPLSTZON from vvaltst where CODOPE='"+operation+"' ");
				 //String msggggg="";
				
			driver.navigate().to(lienG + operation +"&CODMAJ="+codmaj); 
			System.out.println("********************************************");
			btest=true;
		
			bmsg=true;
			  
			  Utile.waitTime(1000);
			  
			  String msgErreur1="";
			       while (resulRe1.next()&& btest==true && bmsg==true) {
			   
			    	   
			    	   String valrub = null;
			    	   String valt=null;
			    	   
			    	 String testList = null;
			    
			    	 testList = resulRe1.getString("TYPLSTZON");
			    	 System.out.println("Tyle liste '1' ou '0' : ----------" + testList );
			    	   
			    	   if(!testList.equals("1")) {
			    		   valrub = resulRe1.getString("VAL2OPE");}
			    		   else  {
				    		   System.out.println("valeur du champs SI Type liste = 0 :  --------->" + valrub );
					    	
					    		   valrub = resulRe1.getString("VALLSTOPE");
					    		
					    	   System.out.println("valeur du champs SI Type liste = 1 : ---------->" + valrub );}
			    	
			    		   valt=resulRe1.getString("TABOPE");
			    		
			    		   
			            System.out.println("nom d'ID d'element: --> "+valt);
			            System.out.println("valeur si type de liste(TYPLSTZON) est '0'--> "+valrub);
			    	   
			            if (!resulRe1.getString("CARZON").equals("X")) {
			            	
			            
			            if (valrub==null) {
			            	valrub="";
			            		
			            	 System.out.println("valeur séquentiel "+valrub);
						   driver.findElement(By.id(valt)).sendKeys(Keys.TAB);
						   
						   
					   
						 
			            }else {
			            	
			            	 msgErreur1 = driver.findElement(By.id("divChg")).getText();
							  System.out.println("message d'erreur Result1---> : " + msgErreur1);
			         System.out.println("valeur du champs---:  " + valrub +"  avec l'ID d'element---: " +valt);
			         if (operation.equals("R011")) {
			        	 String xpath="//img[@onclick='calendrier("+valt+")']";
						    WebElement calend =   driver.findElement(By.xpath(xpath));
						    calend.click(); 
			         }
			         
			         driver.findElement(By.id(valt)).sendKeys(valrub);	
			         System.out.println("message d'erreur Result2---> : " + msgErreur1);
			         if(operation.equals("A009") && (valt.equals("t13")) ) {
			        	 driver.findElement(By.id(valt)).sendKeys(Keys.TAB);
						   driver.switchTo().alert().accept();
						  
					   }  
			   
			                      
					    
			            }
			      //Message d'erreur
			     // msgErreur1 = driver.findElement(By.id("divChg")).getText();
				  System.out.println("message d'erreur Result3---> : " + msgErreur1);
				  
				  
				//  if (msgErreur1.startsWith("ENREGISTREMENT EXISTE")){	
					 if (msgErreur1.startsWith("ENREGISTREMENT EXISTE")){	
					 btest=false;
					 codmaj="M";
					 System.out.println("****passer au mode Modification****");
					 
				  }else  {
					  if(!msgErreur1.trim().equals("")) {
						  
						  System.out.println("message d'erreur Result non bloquant---> : " + msgErreur1);
						  bmsg=false;
						  
					  }
					  
				  }
				  }
			             
				    	
			       } 
			       
			     
			     if(btest==true && bmsg==true ) {
			    	 
			    	
			    	 
			    	 Utile.waitTime(1000);
			    	 //capture d'écran
			    	// WebElement interfaceOPE = driver.findElement(By.xpath("//*[@id=\"MapOpe\"]/table")); 
			    WebElement interfaceOPE = driver.findElement(By.xpath("//*[@id=\"MapOpe\"]/table"));
			    	 
	  			     File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  			      BufferedImage  fullScreen = ImageIO.read(screenshot);
	  			      Point location = interfaceOPE.getLocation();
	  			        int width = interfaceOPE.getSize().getWidth();
	  			        int height = interfaceOPE.getSize().getHeight();
	  			        BufferedImage captureOPE = fullScreen.getSubimage(location.getX(), location.getY(), width, height);
	  			        ImageIO.write(captureOPE, "png", screenshot);		
	  			        FileUtils.copyFile(screenshot, new File("C:\\Users\\kenya\\Downloads\\Screenshot Project\\Clients\\"+operation+".PNG"));
			    	 
			    	 
			    	 
			    	 
			    
			    	   driver.findElement(By.id("b5")).click();
			    	   
			       } 
			      
			  
			       
			    	        
			       
			}while(btest==false);
			                
			    	            
		                
		
			}
	
		
		
		
		
		//fermeture de la class	
	}

	
	//@AfterMethod
	public void closeDriver() {
		Utile.waitTime(6000);
		driver.close();
	}
	
	}
