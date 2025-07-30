package org.lms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/readerdashboard")
public class ReaderDashboard extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=UTF-8");

		HttpSession session = req.getSession();
		String name = (String) session.getAttribute("rname");

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Reader Dashboard</title>");
		out.println("<style>");
		out.println("body { font-family: 'Georgia', serif; background-color: #f5f2e7; padding: 30px; color: #333; }");
		out.println("h2 { color: #4b2e1a; text-align: center; }");
		out.println(".header, form { text-align: center; margin: 10px auto; }");
		out.println("button { background-color: #6b4226; color: white; border: none; padding: 10px 20px; border-radius: 6px; margin: 5px; cursor: pointer; font-size: 16px; }");
		out.println("button:hover { background-color: #4b2e1a; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		out.println("<h2>Welcome, " + name + "!</h2>");
		out.println("<div class='header'>");

		// Profile Button
		out.println("<form action='readerprofile' method='post'>");
		out.println("<button class='click'>Profile</button>");
		out.println("</form>");

		// Logout Button
		out.println("<form action='readerlogin.html' method='post'>");
		out.println("<button class='click'>Logout</button>");
		out.println("</form>");
		out.println("</div>");

		// Show Books Button
		out.println("<form action='showbooksreader' method='post'>");
		out.println("<button class='click'>Show Books</button>");
		out.println("</form>");

		// My List Button
		out.println("<form action='readerhistory' method='post'>");
		out.println("<button class='click'>My List</button>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}
}
