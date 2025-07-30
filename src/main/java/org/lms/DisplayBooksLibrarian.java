package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/displayallbooks")
public class DisplayBooksLibrarian extends HttpServlet {

    private Connection connection;

    public void jdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        jdbc();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>All Books</title>");

        // Embedded CSS
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 20px; }");
        out.println("h2 { color: #4b2e1a; text-align: center; margin-bottom: 30px; }");
        out.println("table { width: 100%; border-collapse: collapse; background-color: #fff9e6; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("th, td { padding: 12px 15px; border: 1px solid #8b5e3c; text-align: center; }");
        out.println("th { background-color: #8b5e3c; color: white; }");
        out.println("tr:nth-child(even) { background-color: #fdf6e3; }");
        out.println("tr:hover { background-color: #f2e2ba; }");
        out.println("button { padding: 7px 15px; background-color: #6b4226; color: white; border: none; border-radius: 5px; cursor: pointer; }");
        out.println("button:hover { background-color: #4b2e1a; }");
        out.println("form { margin: 0; }");
        out.println(".message { text-align: center; color: green; margin-bottom: 20px; font-size: 1.1rem; }");
        out.println("</style>");

        out.println("</head><body>");
        out.println("<h2>📚 Our Book Inventory</h2>");

        // Show remove message if present
        String removebookmessage = (String) req.getAttribute("removebook");
        if (removebookmessage != null) {
            out.println("<div class='message'>" + removebookmessage + "</div>");
        }

        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Book ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Author</th>");
        out.println("<th>Edition</th>");
        out.println("<th>Publication</th>");
        out.println("<th>Category</th>");
        out.println("<th>Pages</th>");
        out.println("<th>Price</th>");
        out.println("<th>Units</th>");
        out.println("<th>Update</th>");
        out.println("<th>Remove</th>");
        out.println("</tr>");

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM books");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                out.println("<tr>");
                out.println("<td>" + set.getString(1) + "</td>");
                out.println("<td>" + set.getString(2) + "</td>");
                out.println("<td>" + set.getString(3) + "</td>");
                out.println("<td>" + set.getInt(4) + "</td>");
                out.println("<td>" + set.getString(5) + "</td>");
                out.println("<td>" + set.getString(6) + "</td>");
                out.println("<td>" + set.getInt(7) + "</td>");
                out.println("<td>₹" + set.getDouble(8) + "</td>");
                out.println("<td>" + set.getInt(9) + "</td>");

                // Update button
                out.println("<td><form action='updatebook' method='post'>");
                out.println("<input type='hidden' name='bookid' value='" + set.getString(1) + "'>");
                out.println("<button>Update</button>");
                out.println("</form></td>");

                // Remove button
                out.println("<td><form action='confirmremovebook' method='post'>");
                out.println("<input type='hidden' name='bookid' value='" + set.getString(1) + "'>");
                out.println("<button>Remove</button>");
                out.println("</form></td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error fetching books from database.</p>");
        }

        out.println("</body></html>");
    }
}
