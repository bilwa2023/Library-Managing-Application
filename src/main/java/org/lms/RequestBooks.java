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
@WebServlet("/requestbook")
public class RequestBooks extends HttpServlet{

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
		String bookid=req.getParameter("bookid");
		HttpSession session=req.getSession();
		String rid=(String) session.getAttribute("rid");
//		System.out.println(rid);
//		System.out.println(bookid);
		try {
			PreparedStatement pslno=connection.prepareStatement("SELECT MAX(slno) FROM issuerequest");
			ResultSet slset=pslno.executeQuery();
			int slno=0;
			if(slset.next()) {
				slno=slset.getInt(1)+1;
			}
			PreparedStatement psd=connection.prepareStatement("SELECT * FROM issuerequest WHERE bookid=? AND readerid=? AND status ='requested'" );
			psd.setString(1, bookid);
			psd.setString(2, rid);
			ResultSet res=psd.executeQuery();
			
			
			PreparedStatement psc=connection.prepareStatement("SELECT units FROM BOOKS WHERE bid=?");
			psc.setString(1, bookid);
			ResultSet set=psc.executeQuery();
			if(res.next()) {
				RequestDispatcher rd=req.getRequestDispatcher("showbooksreader");
				out.println("<h3>The book request already exists</h3>");
				rd.include(req, resp);
			}
			else {
				if(set.next()) {
					if(set.getInt(1)>=1) {
						PreparedStatement ps=connection.prepareStatement("INSERT INTO issuerequest VALUES(?,?,?,?)");
						ps.setInt(1, slno);
						ps.setString(2, bookid);
						ps.setString(3, rid);
						ps.setString(4,"requested");
						
						int result=ps.executeUpdate();
						
							if(result==1)
							{
								RequestDispatcher rd=req.getRequestDispatcher("showbooksreader");
								out.println("<h3>Book request Sent</h3>");
								connection.close();
								rd.include(req, resp);
							}
							else {
								RequestDispatcher rd=req.getRequestDispatcher("showbooksreader");
								out.println("<h3>Book request not sent..</h3>");
								rd.include(req, resp);
								
							}
						}
					else {					
						RequestDispatcher rd=req.getRequestDispatcher("showbooksreader");
						out.println("<h3>Book not available</h3>");
						rd.include(req, resp);
					}
				}
				else {
					RequestDispatcher rd=req.getRequestDispatcher("showbooksreader");
					out.println("<h3>Book not available</h3>");
					rd.include(req, resp);			
					}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("</body>");
		out.println("</html>");
	
	}
}
