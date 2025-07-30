package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
@WebServlet("/readerlogin")
public class ReaderLogin extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out= resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement","root","root");
			
			PreparedStatement ps=connection.prepareStatement("SELECT * FROM readers where email=?");
			ps.setString(1, email);
			ResultSet set=ps.executeQuery();
			if(set.next()) {
				if(password.equals(set.getString(4))) {
					HttpSession session=req.getSession();
					session.setAttribute("rid", set.getString(1));
					session.setAttribute("rname", set.getString(2));
					session.setAttribute("email", set.getString(3));
					session.setAttribute("password", set.getString(4));
					session.setAttribute("contact", set.getString(5));
					session.setAttribute("joindate", set.getDate(6));
					 RequestDispatcher rd=req.getRequestDispatcher("readerdashboard");
					 rd.forward(req, resp);
				}
				else {
					RequestDispatcher rd=req.getRequestDispatcher("readerlogin.html");
					out.println("<h2>Incorrect Password .Try again..</h2>");
					rd.include(req, resp);
				}
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("readerlogin.html");
				out.println("<h2>Email not found try with different email..</h2>");
				rd.include(req, resp);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("</body>");
		out.println("</html>");
		
	}
}
