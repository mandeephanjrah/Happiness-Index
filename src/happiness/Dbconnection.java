package happiness;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {
	 public Connection getmysqlConnection()
	 {
	  Connection con=null;
	  String url="jdbc:mysql://127.0.0.1:3306/happiness_index_database";
	  String user="root";
	  String password="root";
	  try
	  {
	   Class.forName("com.mysql.jdbc.Driver");
	   con=DriverManager.getConnection(url, user, password);
	   
	   if(con!=null)
	   {
	    System.out.println("Connected");
	   }
	   else
	   {
	    System.out.println("Not connected");
	   }
	  }
	  catch (ClassNotFoundException e) 
	  {
	   e.printStackTrace();
	  }
	  catch(SQLException e)
	  {
	   e.printStackTrace();
	  }
	  
	  return con;
	  
	 }

}
