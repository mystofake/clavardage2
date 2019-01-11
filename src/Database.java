
import java.sql.*;

public class Database {

		private static User mainUser;
	
		
	
	   public static void createDB(Controler c) {
		   
		   
		   mainUser = c.mainUser;
		   String DB_URL = "jdbc:sqlite:Clavardage-"+ mainUser.getPseudo()+".db";

		   Connection conn = null;
		   Statement stmt = null;
		   try{

	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL);

	      //STEP 4: Execute a query
	      System.out.println("Creating database " + DB_URL + " ...");
	      stmt = conn.createStatement();
	      
	      


		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		}//end createDB

	   public static void write(Message m, boolean send)
	   {
		   Connection conn = null;
		   Statement stmt = null;
		   try{

			   String DB_URL = "jdbc:sqlite:Clavardage-"+ mainUser.getPseudo()+".db";
			   String tableName;

		      //Open a connection
		      conn = DriverManager.getConnection(DB_URL);

		      //Execute a query
		      stmt = conn.createStatement();
		      
		      //Cree la table correspondant au pseudo
		      	if(send)   // MainUser est la source
		      	{
		      		System.out.println(m.getUserDest().getPseudo());
		      		tableName =  m.getUserDest().getPseudo();
		      	}
		    	else // MainUser recoit le message
		    	{
		    		tableName =  m.getUserSrc().getPseudo();		      
		    	} 
		      String sql = "CREATE TABLE IF NOT EXISTS "+ tableName +"(TEMPS VARCHAR(20), PSEUDO VARCHAR(20),MESSAGE VARCHAR(150))";
		      
		      
		      stmt.executeUpdate(sql);
		      
		      String pdu = m.getPDU().replaceAll("\'", "\''"); // On remplace ' par '' pour pas avoir de soucis avec SQL 
		      
		      
		      
		      sql = "INSERT INTO " + tableName + " VALUES ('" + m.recupDate() + "' , '" + m.getUserSrc() + "' , '" + pdu + "')"; 
		      stmt.executeUpdate(sql);
		      
		      
		      
		   		}
		   catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
	   
	   }  
	   
	   
	   public static String getHistory(User userDest)
	   {
		   
		   Connection conn = null;
		   Statement stmt = null;
		   String historyText = "";
		   ResultSet res = null;
		   try{
			   
	

			   String DB_URL = "jdbc:sqlite:Clavardage-"+ mainUser.getPseudo()+".db";			   
			   
			   //Open a connection
			   conn = DriverManager.getConnection(DB_URL);
			   
		      //Execute a query
		      stmt = conn.createStatement();
		      
		      
		      String sql = "CREATE TABLE IF NOT EXISTS "+ userDest.getPseudo() +"(TEMPS VARCHAR(20), PSEUDO VARCHAR(20),MESSAGE VARCHAR(150))"; 
		      stmt.executeUpdate(sql);
		      
		 		      
		      sql = "SELECT * FROM "+ userDest.getPseudo();
		      
		      res = stmt.executeQuery(sql);
		      
		      
			    while (res.next()) {
			        String pseudo = res.getString("PSEUDO");
			        String message = res.getString("MESSAGE");
			        String date = res.getString("TEMPS");
			        //System.out.println("| " + date + "| " + pseudo + ": " + message + "\n");
			        historyText = historyText +  "| " + date + "| " + pseudo + ": " + message + "\n";
			    }

			   res.close();
	   		}
		   catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
	   	 
		   return historyText;
	   }
	   

	   
}

