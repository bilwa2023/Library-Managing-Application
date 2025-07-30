package org.lms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/updatereader")
public class UpdateReader extends HttpServlet{
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

		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String contact=req.getParameter("contact");
		HttpSession session=req.getSession();
		String id=(String)session.getAttribute("rid");
		
//		System.out.println(id);
		jdbc();
		try {
			PreparedStatement ps=connection.prepareStatement("UPDATE readers SET rname=?,email=?,password=?,contact=? where rid=?");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, contact);
			ps.setString(5, id);
			
			int result=ps.executeUpdate();
			if(result==1) {
				session.setAttribute("msg", "Reader data updated..");
				session.setAttribute("rname", name);
				session.setAttribute("email", email);
				session.setAttribute("password",password);
				session.setAttribute("contact", contact);
				RequestDispatcher rd=req.getRequestDispatcher("readerprofile");
				rd.forward(req, resp);
			}
			else {
				session.setAttribute("msg", "Reader data couldnot be updated..");
				RequestDispatcher rd=req.getRequestDispatcher("readerprofile");
				rd.forward(req, resp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
