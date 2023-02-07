package BaseDeDonnee;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;





 public class DataBase {
	
	
		public Connection connectedC;

   
 	 public void setUpDataBase() throws ClassNotFoundException, SQLException {
	 
		String url = "jdbc:oracle:thin:@//192.168.0.195:1521/mpbank";
		String user = "tpspost";
		String password = "tpspost";
		
		
			Class.forName("oracle.jdbc.OracleDriver");
			Connection connected = DriverManager.getConnection(url, user, password);
			connectedC = connected ;
			
			System.out.println("Connection is successful with" + " " + connectedC);
			
	

	}
}
