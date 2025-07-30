package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/librarianprofile")
public class LibrarianProfile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int id = (int) session.getAttribute("lid");
		String name = (String) session.getAttribute("lname");
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");

		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Librarian Profile</title>");
		out.println("<style>");
		out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 30px; color: #333; }");
		out.println("h2 { color: #4b2e1a; text-align: center; margin-bottom: 20px; }");
		out.println("form { background-color: #fff8ee; padding: 20px; border-radius: 10px; max-width: 500px; margin: 20px auto; box-shadow: 0px 0px 10px #ccc; }");
		out.println("input { width: 90%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 5px; }");
		out.println("button { background-color: #6b4226; color: white; border: none; padding: 10px 15px; margin-right: 10px; border-radius: 5px; cursor: pointer; }");
		out.println("button:hover { background-color: #4b2e1a; }");
		out.println("h4 { color: green; text-align: center; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		String msg = (String) req.getAttribute("msg");
		if (msg != null) {
			out.println("<h4>" + msg + "</h4>");
		}

		out.println("<h2>Librarian Profile</h2>");
		out.println("<form action='updatelibrarian' method='post'>");
		out.println("<p><strong>ID:</strong> " + id + "</p>");
		out.println("<label>Name:</label><br>");
		out.println("<input type='text' name='name' value='" + name + "' required><br>");
		out.println("<label>Email:</label><br>");
		out.println("<input type='email' name='email' value='" + email + "' required><br>");
		out.println("<label>Password:</label><br>");
		out.println("<input type='password' name='password' value='" + password + "' required><br>");
		out.println("<button type='submit'>Update</button>");
		out.println("</form>");

		out.println("<form action='deletelibrarian' method='post'>");
		out.println("<button type='submit'>Delete Profile</button>");
		out.println("</form>");

		out.println("<form action='librariandashboard' method='post'>");
		out.println("<button type='submit'>Return to Dashboard</button>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}
}
