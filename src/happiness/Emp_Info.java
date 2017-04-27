package happiness;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Emp_Info {

	private static ArrayList<String> st2=new ArrayList<String>(); 
	private static ArrayList<String> st3=new ArrayList<String>(); 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Emp_Info e=new Emp_Info();
		//getting email addresses from database
		e.viewdetails();
		//getting url from database
		e.geturls();
	}


	public ArrayList<String> viewdetails(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/happiness_index_database", "root", "root");
			Statement st=null;
			if(con!=null){
				st=con.createStatement();
				ResultSet rs= st.executeQuery("select * from employee_info");
				String st1=new String();

				while(rs.next()){
					st1=rs.getString(3)+"/"+rs.getString(4);
					st2.add(st1);//adding values to arraylist
				}
				System.out.println(st2);
			}

			else{
				System.out.println("not connected");
			}
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st2;
	}

	public ArrayList<String> geturls(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/happiness_index_database", "root", "root");
			Statement st=null;
			if(con!=null){
				System.out.println("connected");
				st=con.createStatement();
				ResultSet rs= st.executeQuery("select * from urls");
				String st4=new String();
				while(rs.next()){
					st4=rs.getString(2);//getting second column value from database
					st3.add(st4);//adding value to arraylist
				}
			}
			else{
				System.out.println("not connected");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return st3;
	}

}




