package Automatique;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import BaseDeDonnee.DataBase;
import BaseDeDonnee.Initial;

public class testclient extends Initial{

String lienG = "http://192.168.0.197:7003/unipost/v4/ecrans/index.jsp?pack=packru&rpVrs=v4&etb=poste&rng=" ;
	
	
	@Test
	public void lesFormulaires () throws ClassNotFoundException, SQLException {
		
		DataBase datab = new DataBase();
	    datab.setUpDataBase();

		Connection connect = datab.connectedC;

	

		
		Statement stmt0 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resulRe0 = stmt0.executeQuery("select distinct codope from VALTST order by CODOPE");

	
		
		while (resulRe0.next()) {
			//les opérations de la colonne CODOPE : (R310...R315)
			String operation = resulRe0.getString("CODOPE");
			
			//System.out.println("lien Formulaire :---" + operation);
			
			/*int rows = resulRe.getRow();
			//System.out.println("ligne numero°  ---> " + rows ); */
			
			
			driver.navigate().to(lienG + operation); 
			
			Statement stmt1 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulRe1 = stmt1.executeQuery("select RUBOPE,VALOPE from valtst where CODOPE='"+operation+"' order by CODOPE ");
			
			Statement stmt2 = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resulRe2 = stmt2.executeQuery("select 't'||tabzon as IDOPE from t_zontrn where trnzon='"+operation+"' and saizon='1' order by tabzon");
			
			
			//ResultSet resulRe2 = stmt2.executeQuery("select VNAME from VLIST order by VNAME");
			// String valrub="";
			//System.out.println("aaaaaaaaaaaaaaaaaa");
			//connexion au formulaire
			  //while (resulRe2.next()) 
			
			       while (resulRe1.next()&&resulRe2.next()) {
			    	 
			        	//  String rub = resulRe1.getString("RUBOPE");
			            String valrub = resulRe1.getString("VALOPE");
			            
			            String valt=resulRe2.getString("IDOPE");
			     // Utile.threadTime(1000);
			   //   driver.findElement(By.id(valt)).sendKeys(valrub);
			       } 
			       
			       
				          /*System.out.println("lien Formulaire :---" + rub);
				          
				          String codrub =valt+"=" + rub +"="+valrub;
				          System.out.println("lien Formulaire :---" + codrub);*/
				          
				         
							//driver.findElement(By.id("t0")).sendKeys();
							//driver.findElement(By.id("t0")).sendKeys(valrub);
						/*if(valt.equals("t1")) {
							Select listeTypDoc = new Select(driver.findElement(By.id(valt)));
							listeTypDoc.selectByVisibleText("CIN");
						}else {*/
							
							//Utile.threadTime(2000);
						//}
				
			    	   
				
			      
			
		
			
			
			
		}
	
		
	
		
		//fermeture de la class	
	}

	
	
}
