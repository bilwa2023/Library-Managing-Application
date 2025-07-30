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
@WebServlet("/searchreader")
public class SearchReader  extends HttpServlet{
	private Connection connection;
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
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		jdbc();
		String readerid=req.getParameter("rid");
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT * FROM readers WHERE rid=? ");
			ps.setString(1, readerid);
			
			ResultSet set=ps.executeQuery();
			
			out.println("<table border=1px cellpadding=5px cellspacing=0px>"
					+ "<tr>"
					+ "<th>Reader Id</th>"
					+ "<th>Reader Name</th>"
					+ "<th>Email</th>"
					+ "<th>Password</th>"
					+ "<th>Contact</th>"
					+ "<th>Join Date</th>"
					+ "<th>Borrowings</th>"
					+ "</tr>");
			if(set.next()) {
				out.println(
						"<tr>"
						+ "<td>"+set.getString(1)+"</td>"
						+ "<td>"+set.getString(2)+"</td>"
						+ "<td>"+set.getString(3)+"</td>"
						+ "<td>"+set.getString(4)+"</td>"
						+ "<td>"+set.getString(5)+"</td>"
						+ "<td>"+set.getDate(6)+"</td>"
						+ "<td>"
						+ "<form action='readerborrowings' method='post'>"
						+ "<input type='hidden' name='rid' value='"+set.getString(1)+"'>"
						+ "<button>Borrowings</button>"
						+ "</form>"
						+ "</td>"
						+ "</tr>");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
