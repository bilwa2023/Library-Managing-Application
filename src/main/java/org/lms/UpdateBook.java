package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/updatebook")
public class UpdateBook extends HttpServlet{
	private Connection connection=null;

	public void jdbc() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement","root","root");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bookid=req.getParameter("bookid");
		jdbc();
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h3>Book Id: "+bookid+"</h3>");
		HttpSession session=req.getSession();
		session.setAttribute("bookid", bookid);
		System.out.println(bookid);
		
		try {
			PreparedStatement ps=connection.prepareStatement("SELECT * FROM books WHERE bid=?");
			ps.setString(1, bookid);
			ResultSet res=ps.executeQuery();
			if(res.next()) {
			
				out.println(" <fieldset>\r\n"
						+ "        <legend>Update Book Details</legend>\r\n"
						+ "        <form action='update' method=\"post\">\r\n"
						+ "        <table>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label>Book Name:</label></td>\r\n"
						+ "        	<td><input type=\"text\" value='"+res.getString(2)+"' name=\"bname\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label>Author:</label></td>\r\n"
						+ "        	<td><input type=\"text\" value='"+res.getString(3)+"' name=\"author\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Edition:</label></td>\r\n"
						+ "        	<td><input type=\"number\" value='"+res.getInt(4)+"' name=\"edition\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "       \r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Publication:</label></td>\r\n"
						+ "        	<td><input type=\"text\" value='"+res.getString(5)+"' name=\"publication\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Category:</label></td>\r\n"
						+ "        	<td><select name=\"category\">\r\n"
						+ "        		<option value=\"Self-help\">Self-help</option>\r\n"
						+ "        		<option value=\"Fictional\">Fictional</option>\r\n"
						+ "        		<option value=\"Comics\">Comics</option>\r\n"
						+ "        		<option value=\"Educational\">Educational</option>\r\n"
						+ "        		<option value=\"Short Stories\">Short Stories</option>\r\n"
						+ "        		<option value=\"Novel\">Novel</option>\r\n"
						+ "        		<option value=\"History\">History</option>\r\n"
						+ "        		\r\n"
						+ "        	</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Pages:</label></td>\r\n"
						+ "        	<td><input type=\"number\" value='"+res.getInt(7)+"' name=\"pages\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Price:</label></td>\r\n"
						+ "        	<td><input type=\"number\" value='"+res.getDouble(8)+"' name=\"price\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr> \r\n"
						+ "        	<td><label >Units:</label></td>\r\n"
						+ "        	<td><input type=\"number\" value='"+res.getInt(9)+"' name=\"units\" required></td>\r\n"
						+ "        </tr>\r\n"
						+ "       \r\n"
						+ "        \r\n"
						+ "        \r\n"
						+ "        <tr> \r\n"
						+ "        	<td><button>Update</button></td>\r\n"
						+ "        </tr>        \r\n"
						+ "        </table>      \r\n"
						+ "        </form>\r\n"
						+ "       \r\n"
						+ "        </fieldset>");
			}
			else {
				out.println("<h3> The book details could not be found please try again");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}
}
