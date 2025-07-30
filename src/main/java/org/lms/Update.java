package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/update")
public class Update extends HttpServlet{
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
		
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		jdbc();
		HttpSession session=req.getSession();
		String bookid= (String)session.getAttribute("bookid");
		String bname=req.getParameter("bname");
		String author=req.getParameter("author");
		int edition=Integer.parseInt(req.getParameter("edition"));
		String publication=req.getParameter("publication");
		String category=req.getParameter("category");
		int pages=Integer.parseInt(req.getParameter("pages"));
		double price=Double.parseDouble(req.getParameter("price"));
		int units=Integer.parseInt(req.getParameter("units"));
		System.out.println(bookid);
		
		try {
			
			
			PreparedStatement ps=connection.prepareStatement("UPDATE books SET bookname=?,author=?,edition=?,publication=?,category=?,pages=?,price=?,units=? WHERE bid=?");
			ps.setString(1, bname);
			ps.setString(2, author);
			ps.setInt(3, edition);
			ps.setString(4, publication);
			ps.setString(5, category);
			ps.setInt(6, pages);
			ps.setDouble(7, price);
			ps.setInt(8, units);
			ps.setString(9, bookid);
		
			int res=ps.executeUpdate();
			if(res==1) {
				RequestDispatcher rd= req.getRequestDispatcher("displayallbooks");
				rd.forward(req, resp);
			}
			else {
				RequestDispatcher rd= req.getRequestDispatcher("updatebook");
				out.println("<h3> Please try Again</h3>");
				rd.forward(req, resp);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
		
	
	}
}
