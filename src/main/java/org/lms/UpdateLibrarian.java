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
@WebServlet("/updatelibrarian")
public class UpdateLibrarian extends HttpServlet{
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
		jdbc();
		try {
			PreparedStatement ps=connection.prepareStatement("UPDATE admin SET aname=?,email=?,password=?");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			
			int result=ps.executeUpdate();
			if(result==1) {
				
				HttpSession session=req.getSession();
				session.setAttribute("lname", name);
				session.setAttribute("email", email);
				session.setAttribute("password", password);
				System.out.println(session.getAttribute("lname"));
				req.setAttribute("msg", "Librarian data updated..");
				RequestDispatcher rd=req.getRequestDispatcher("librarianprofile");
				rd.forward(req, resp);
			}
			else {
				req.setAttribute("msg", "Librarian data couldnot be updated..");
				RequestDispatcher rd=req.getRequestDispatcher("librarianprofile");
				rd.forward(req, resp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
