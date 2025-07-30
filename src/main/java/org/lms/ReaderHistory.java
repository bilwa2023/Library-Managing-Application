package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/readerhistory")
public class ReaderHistory extends HttpServlet {
	private Connection connection = null;

	public void jdbc() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement", "root", "root");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession();
		String rid = (String) session.getAttribute("rid");

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Borrow History</title>");
		out.println("<style>");
		out.println("body { font-family: 'Georgia', serif; background-color: #f5f2e7; padding: 30px; color: #333; }");
		out.println("h2 { color: #4b2e1a; text-align: center; }");
		out.println("table { width: 90%; margin: auto; border-collapse: collapse; background-color: #fffaf0; }");
		out.println("th, td { padding: 12px; text-align: center; border: 1px solid #ccc; }");
		out.println("th { background-color: #e0c097; color: #3e2723; }");
		out.println("button { background-color: #6b4226; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer; }");
		out.println("button:hover { background-color: #4b2e1a; }");
		out.println("h3 { color: green; text-align: center; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		// Optional message after return
		String returnmsg = (String) req.getAttribute("returnmmsg");
		if (returnmsg != null) {
			out.println("<h3>" + returnmsg + "</h3>");
		}

		out.println("<h2>Borrow History</h2>");
		out.println("<br><br>");

		jdbc();

		out.println("<table>");
		out.println("<tr>");
		out.println("<th>Book Id</th>");
		out.println("<th>Book Name</th>");
		out.println("<th>Issue Date</th>");
		out.println("<th>Return Date</th>");
		out.println("<th>Status</th>");
		out.println("<th>Return Book</th>");
		out.println("</tr>");

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM issuebook WHERE rid=?");
			ps.setString(1, rid);
			ResultSet set = ps.executeQuery();

			while (set.next()) {
				out.println("<tr>");
				out.println("<td>" + set.getString(2) + "</td>");
				out.println("<td>" + set.getString(3) + "</td>");
				out.println("<td>" + set.getDate(6) + "</td>");
				out.println("<td>" + set.getDate(7) + "</td>");
				out.println("<td>" + set.getString(8) + "</td>");
				out.println("<td>");
				out.println("<form action='confirmreturn' method='post'>");
				out.println("<input type='hidden' name='issueno' value='" + set.getInt(1) + "'>");
				out.println("<button>Return</button>");
				out.println("</form>");
				out.println("</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</body>");
		out.println("</html>");
	}
}
