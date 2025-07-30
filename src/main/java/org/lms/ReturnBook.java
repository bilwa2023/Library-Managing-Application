package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/returnbook")
public class ReturnBook extends HttpServlet{
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
	public Date getDate() throws SQLException {
		Date date=null;
		jdbc();
		PreparedStatement ps=connection.prepareStatement("SELECT CURRENT_DATE()");
		ResultSet set= ps.executeQuery();
		if(set.next()) {
			date=set.getDate(1);
		}
		return date;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		jdbc();
		int issueno=Integer.parseInt(req.getParameter("issueno"));
		
		try {
			PreparedStatement ps=connection.prepareStatement("SELECT * FROM issuebook WHERE issueno=?");
			ps.setInt(1, issueno);
			ResultSet set=ps.executeQuery();
			String bid=null;
			String rid=null;
			
			if(set.next()) {
				bid=set.getString(2);
				rid=set.getString(4);
			}
			
			
			PreparedStatement psib=connection.prepareStatement("UPDATE issuebook SET status =?,returndate=? WHERE issueno=?");
			psib.setString(1, "returned");
			psib.setDate(2, getDate());
			psib.setInt(3, issueno);
			int res=psib.executeUpdate();
			
			
			PreparedStatement psir=connection.prepareStatement("UPDATE issuerequest SET status =?WHERE bookid=? AND readerid=?");
			psir.setString(1, "returned");
			psir.setString(2, bid);
			psir.setString(3, rid);
			int result=psir.executeUpdate();
			
			if(res==1&&result==1) {
				RequestDispatcher rd=req.getRequestDispatcher("readerhistory");
				req.setAttribute("returnmsg", "Book returned Successfully");
				rd.forward(req, resp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
