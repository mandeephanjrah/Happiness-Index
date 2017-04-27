
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class Url_parameter_servlet
 */
public class Url_parameter_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Url_parameter_servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String query = request.getQueryString();
		String username = request.getParameter("username");
		String status = request.getParameter("status");
		String date = request.getParameter("date");
		String team = request.getParameter("team");
		System.out.println("username: " + username);
		System.out.println(status);
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/happiness_index_database", "root", "root");
			st = con.createStatement();
			String qry = "select email,date from happiness_index_database.chartdata where email='" + username
					+ "'and date='" + date + "'";
			System.out.println(qry);
			rs = st.executeQuery(qry);
			boolean flg = rs.next();
			System.out.println("rs flag ===> " + flg);
			if (!flg) {
				System.out.println("inside if ===> ");
				out.print("Your response has been recorded for the day!");
				PreparedStatement ps = (PreparedStatement) con
						.prepareStatement("insert into chartdata(email,date,response,team)values(?,?,?,?)");
				ps.setString(1, username);
				ps.setString(2, date);
				ps.setString(3, status);
				ps.setString(4, team);
				int i = ps.executeUpdate();
				if (i == 1) {
					System.out.println("record inserted");

				} else {
					System.out.println("record not inserted");
				}

			} else {
				System.out.println("already exists");
				out.print("Your response is already recorded for the day!");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				rs.close();
				st.close();
				con.close();

			} catch (SQLException sqlee) {
				sqlee.printStackTrace();
			}
		}

	}

	/*
	 * /**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}