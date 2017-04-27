package happiness;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Report1")
public class Report1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int vhappy,qhappy,nhappy,nahappy,vhappy1;
	String Sdate;
	String Edate, Team;
	private static ArrayList<String> res=new ArrayList<String>(); 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Report1() {
		super();
		// TODO Auto-generated constructor stub


	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<style>.center {text-align: center;color: white;}.h1 { color: black;align: center;}</style>");
		out.println("<style> body {background-color:#E3EDFB;}</style>");
		out.println("<style> #div1 {margin-bottom:200px;}</style>");
		out.println("<style> #div2 { float: left;position: relative;Left: 30%;}</style>");
		out.println("<style> #button {margin-left:10px;padding:10px;color:white;background-color:3A81E0;}</style>");
		out.println("<body>"); 
		out.println("<div id=div1 class=form-group>");
		out.println("<form action=./Report1 method=get>");
		out.println(" <label id=date>Date:</label>");
		out.println(" <input type=date id=myDate name=sdate value=2017-01-01>");
		out.println(" <label id=l1>to</label>");
		out.println(" <input type=date id=myDate name=edate value=2017-01-31>");
		out.println("<select name=team>");
		// out.println("<option value=all>All</option>");	
		out.println("<option value=COE>COE</option>");	
		out.println("<option value=Chevin>Chevin</option>");
		out.println("<option value=Mendix>Mendix</option>");
		out.println("</select>");
		out.println("<input id=button type=submit value=Update>");
		out.println("</div>");
		out.println("<div id=div2 class=form-group>");
		out.print("<img src='http://localhost:8080/HappinessIndex/LineChart1'>");
		out.println("</div>");
		//getting parameter from url
		Sdate=request.getParameter("sdate");
		Edate=request.getParameter("edate");
		Team=request.getParameter("team");
		try{
			int totalrowCount;
			if(Sdate!=null && Edate!=null)
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/happiness_index_database", "root", "root");
				PreparedStatement ps=con.prepareStatement("select Count(*) from chartdata where date BETWEEN '" + Sdate
						+ "' and '" + Edate+ "' and team=  '" + Team +"'");
				ResultSet rs1=ps.executeQuery();
				rs1.next();
				totalrowCount = rs1.getInt(1);
				PreparedStatement ps1=con.prepareStatement("select * from chartdata where date BETWEEN '" + Sdate
						+ "' and '" + Edate+"' and team= '" + Team +"'");
				ResultSet result=ps1.executeQuery();

				res.clear();
				if(totalrowCount!=0){
					String st1=new String();
					while(result.next()){ 
						st1=result.getString(3)+"/"+result.getString(4);
						res.add(st1);
					}
				}
				//sending total row count to linechart1
				HttpSession ses = request.getSession(true);
				ses.setAttribute("totalrowcount",totalrowCount);

			}
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getdates(){
		return res;
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	  

	}
}




