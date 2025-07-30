package org.lms;

import java.io.IOException;
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
@WebServlet("/approverequest")
public class ApproveRequest extends HttpServlet{
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
//		LocalDate today = LocalDate.now();
//		return today;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		jdbc();
		
		int slno=Integer.parseInt(req.getParameter("slno"));
		
//		System.out.println(slno);
		try {
			PreparedStatement ps=connection.prepareStatement("SELECT * FROM issuerequest WHERE slno=?");
			ps.setInt(1, slno);
			ResultSet set=ps.executeQuery();
			String bid=null;
			String rid=null;
			
			if(set.next()) {
				bid= set.getString(2);
				rid= set.getString(3);
			}
						
			
			
			//Fetch bookname to update in issuedbooks table
			PreparedStatement psbn=connection.prepareStatement("SELECT * FROM books WHERE bid=?");
			psbn.setString(1, bid);
			
			ResultSet books=psbn.executeQuery();
			String bname=null;
			int units=0;
			if(books.next()) {
				bname=books.getString(2);
				units=books.getInt(9);
			}
			
			//Fetch readername to update in issuedbooks table
			PreparedStatement psr=connection.prepareStatement("SELECT * FROM readers WHERE rid=?");
			psr.setString(1, rid);
			ResultSet readers=psr.executeQuery();
			String rname=null;
			if(readers.next()) {
				rname=readers.getString(2);
			}
			
			
			//Fetch the issue date
			Date today=getDate();
			
			//Issueno generate
			PreparedStatement issq=connection.prepareStatement("SELECT MAX(issueno) FROM issuebook");
			ResultSet issno=issq.executeQuery();
			int issueno=0;
			if(issno.next()) {
				issueno=issno.getInt(1);
			}
			
			//Insert into issued books table
			PreparedStatement psb=connection.prepareStatement("INSERT INTO issuebook VALUES(?,?,?,?,?,?,?,?)");
			psb.setInt(1, issueno+1);
			psb.setString(2, bid);
			psb.setString(3, bname);
			psb.setString(4, rid);
			psb.setString(5, rname);
			psb.setDate(6, today);
			psb.setDate(7, null);
			psb.setString(8, "issued");
			
			int insert=psb.executeUpdate();
//			System.out.println(insert);
			
			
			//To Set the status to Issued
			PreparedStatement psu=connection.prepareStatement("UPDATE issuerequest SET status='issued' WHERE slno=?");
			psu.setInt(1, slno);
			psu.executeUpdate();
			
			//Move to requests
			RequestDispatcher rd=req.getRequestDispatcher("issuerequests");
			connection.close();
			rd.forward(req, resp);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
