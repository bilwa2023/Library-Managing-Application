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
@WebServlet("/searchbook")
public class SearchBook extends HttpServlet{
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
		String bookid=req.getParameter("bid");
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT * FROM books WHERE bid=? ");
			ps.setString(1, bookid);
			
			ResultSet set=ps.executeQuery();
			
			out.println("<table border=1px cellpadding=5px cellspacing=0px>"
					+ "<tr>"
					+ "<th>Book Id</th>"
					+ "<th>Book Name</th>"
					+ "<th>Author</th>"
					+ "<th>Edition</th>"
					+ "<th>Publication</th>"
					+ "<th>Category</th>"
					+ "<th>Pages</th>"
					+ "<th>Prices</th>"
					+ "<th>Units</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>");
			if(set.next()) {
				out.println(
						"<tr>"
						+ "<td>"+set.getString(1)+"</td>"
						+ "<td>"+set.getString(2)+"</td>"
						+ "<td>"+set.getString(3)+"</td>"
						+ "<td>"+set.getInt(4)+"</td>"
						+ "<td>"+set.getString(5)+"</td>"
						+ "<td>"+set.getString(6)+"</td>"
						+ "<td>"+set.getInt(7)+"</td>"
						+ "<td>"+set.getDouble(8)+"</td>"
						+ "<td>"+set.getInt(9)+"</td>"
						+ "<td>"
						+ "<form action='updatebook' method='post'>"
						+ "<input type='hidden' name='bookid' value='"+set.getString(1)+"'>"
						+ "<button>Update Book</button>"
						+ "</form>"
						+ "</td>"
						+ "<td>"
						+ "<form action='confirmremovebook' method='post'>"
						+ "<input type='hidden' name='bookid' value='"+set.getString(1)+"'>"
						+ "<button>Remove Book</button>"
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
