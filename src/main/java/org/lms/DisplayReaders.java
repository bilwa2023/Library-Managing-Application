package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/displayreaders")
public class DisplayReaders extends HttpServlet {

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
        out.println("<html><head><meta charset='UTF-8'><title>Readers</title>");

        // Embedded CSS (Library Theme)
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 20px; }");
        out.println("h2 { color: #4b2e1a; text-align: center; margin-bottom: 30px; }");
        out.println("table { width: 100%; border-collapse: collapse; background-color: #fff9e6; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("th, td { padding: 12px 15px; border: 1px solid #8b5e3c; text-align: center; }");
        out.println("th { background-color: #8b5e3c; color: white; }");
        out.println("tr:nth-child(even) { background-color: #fdf6e3; }");
        out.println("tr:hover { background-color: #f2e2ba; }");
        out.println("button { padding: 7px 14px; background-color: #6b4226; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 0.9rem; }");
        out.println("button:hover { background-color: #4b2e1a; }");
        out.println("form { margin: 0; }");
        out.println("</style>");

        out.println("</head><body>");
        out.println("<h2>📘 Registered Readers</h2>");

        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Reader ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Email</th>");
        out.println("<th>Password</th>");
        out.println("<th>Contact</th>");
        out.println("<th>Join Date</th>");
        out.println("<th>History</th>");
        out.println("</tr>");

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM readers");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                out.println("<tr>");
                out.println("<td>" + set.getString(1) + "</td>");
                out.println("<td>" + set.getString(2) + "</td>");
                out.println("<td>" + set.getString(3) + "</td>");

                // Optionally hide password or mask it
                out.println("<td>••••••••</td>"); // OR: out.println("<td>" + set.getString(4) + "</td>");

                out.println("<td>" + set.getString(5) + "</td>");
                out.println("<td>" + set.getDate(6) + "</td>");

                out.println("<td>");
                out.println("<form action='readerborrowings' method='post'>");
                out.println("<input type='hidden' name='rid' value='" + set.getString(1) + "'>");
                out.println("<button>Borrowings</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error loading reader data.</p>");
        }

        out.println("</body></html>");
    }
}
