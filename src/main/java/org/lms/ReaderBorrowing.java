package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/readerborrowings")
public class ReaderBorrowing extends HttpServlet {
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
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		HttpSession session = req.getSession();
		String rid = (String) session.getAttribute("rid");
		System.out.println(rid);
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Borrow History</title>");

		// Embedded CSS for theme consistency
		out.println("<style>");
		out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 30px; }");
		out.println("h2 { color: #4b2e1a; text-align: center; margin-bottom: 30px; }");
		out.println("table { width: 90%; margin: auto; border-collapse: collapse; background-color: #fff8ee; }");
		out.println("th, td { padding: 12px 15px; text-align: center; border: 1px solid #bfa890; }");
		out.println("th { background-color: #6b4226; color: white; }");
		out.println("tr:nth-child(even) { background-color: #f9f4ea; }");
		out.println("form { display: inline; }");
		out.println("button { background-color: #6b4226; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer; }");
		out.println("button:hover { background-color: #4b2e1a; }");
		out.println("</style>");

		out.println("</head>");
		out.println("<body>");
		out.println("<h2>Borrow History</h2>");

		jdbc();

		out.println("<table>");
		out.println("<tr>"
				+ "<th>Book Id</th>"
				+ "<th>Book Name</th>"
				+ "<th>Issue Date</th>"
				+ "<th>Return Date</th>"
				+ "<th>Status</th>"
				+ "</tr>");

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM issuebook WHERE rid=?");
			ps.setString(1, rid);
			
			ResultSet set = ps.executeQuery();

			while (set.next()) {
				out.println("<tr>"
						+ "<td>"
						+ "<form action='searchbook?bid=" + set.getString(2) + "' method='post'>"
						+ "<button>" + set.getString(2) + "</button>"
						+ "</form>"
						+ "</td>"
						+ "<td>" + set.getString(3) + "</td>"
						+ "<td>" + set.getDate(6) + "</td>"
						+ "<td>" + set.getDate(7) + "</td>"
						+ "<td>" + set.getString(8) + "</td>"
						+ "</tr>");
			}

			connection.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}
