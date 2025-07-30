package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/readerprofile")
public class ReaderProfile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("rid");
		String name = (String) session.getAttribute("rname");
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");
		String contact = (String) session.getAttribute("contact");
		Date joindate = (Date) session.getAttribute("joindate");

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=UTF-8");

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Reader Profile</title>");
		out.println("<style>");
		out.println("body { font-family: 'Georgia', serif; background-color: #fef8f0; padding: 30px; color: #333; }");
		out.println("h4 { color: #4b2e1a; }");
		out.println("form { margin: 20px 0; }");
		out.println("input { padding: 8px; margin: 5px 0; width: 300px; border-radius: 5px; border: 1px solid #ccc; }");
		out.println("button { background-color: #6b4226; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; margin-right: 10px; }");
		out.println("button:hover { background-color: #4b2e1a; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");

		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			out.println("<h4 style='color: green;'>" + msg + "</h4>");
			session.removeAttribute("msg");
		}

		out.println("<h2>Reader Profile</h2>");
		out.println("<h4>ID: " + id + "</h4>");
		out.println("<h4>Join Date: " + joindate + "</h4>");

		out.println("<form action='updatereader' method='post'>");
		out.println("Name:<br><input type='text' name='name' value='" + name + "'><br>");
		out.println("Email:<br><input type='email' name='email' value='" + email + "'><br>");
		out.println("Password:<br><input type='password' name='password' value='" + password + "'><br>");
		out.println("Contact:<br><input type='text' name='contact' value='" + contact + "'><br><br>");
		out.println("<button type='submit'>Update</button>");
		out.println("</form>");

		out.println("<form action='deletereader' method='post'>");
		out.println("<button type='submit'>Delete Profile</button>");
		out.println("</form>");

		out.println("<form action='readerdashboard' method='post'>");
		out.println("<button type='submit'>Return to Dashboard</button>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}
}
