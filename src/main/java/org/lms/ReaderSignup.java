package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class ReaderSignup extends HttpServlet{
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
	public int getId() throws SQLException {
		jdbc();
		int id=0;
		PreparedStatement ps=connection.prepareStatement("SELECT MAX(rid) FROM readerid");
		ResultSet set=ps.executeQuery();
		if(set.next()) {
			id=set.getInt(1);
		}
		return id;
	}
	public void setId() throws SQLException {
		jdbc();
		int id=getId();
		PreparedStatement ps=connection.prepareStatement("UPDATE readerid SET rid=? WHERE rid=?");
		ps.setInt(1, id+1);
		ps.setInt(2, id);
		ps.executeUpdate();
	}
	public Date getDate() throws SQLException {
		Date date=null;
		jdbc();
		PreparedStatement ps=connection.prepareStatement("SELECT CURRENT_DATE()");
		ResultSet set= ps.executeQuery();
		if(set.next()) {
			date=set.getDate(1);
		}
		return date;
//		LocalDate today = LocalDate.now();
//		return today;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out= resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String contact=req.getParameter("contact");
		try {
			String id="REA"+getId();
			Date date=getDate();
			PreparedStatement ps=connection.prepareStatement("INSERT INTO readers VALUES(?,?,?,?,?,?)");
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, contact);
			ps.setDate(6, date);
			
			int result=ps.executeUpdate();
			
			if(result==1) {
				setId();
				RequestDispatcher rd=req.getRequestDispatcher("readerlogin.html");
				out.println("<h2>Registered successfully</h2>");
				rd.include(req, resp);
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("signup.html");
				out.println("<h2>Registration failed</h2>");
				rd.include(req, resp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("</body>");
		out.println("</html>");
		
	}
}
