package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/confirmreturn")
public class ConfirmReturnBook extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		int issueno=Integer.parseInt(req.getParameter("issueno"));
		
		
		out.println("<h3> Are you sure you want to return the book!!</h3>");
		out.println("<form action='returnbook' method='post'>"
				+ "<input type='hidden' name='issueno' value='"+issueno+"'>"
				+ "<button>Confirm</button>"
				+ "</form>");
		out.println("<form action='readerhistory' method='post'>"
				+ "<button>Cancel</button>"
				+ "</form>");
		
		
		out.println("</body>");
		out.println("</html>");
	}
}
