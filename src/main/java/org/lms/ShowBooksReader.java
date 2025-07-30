package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/showbooksreader")
public class ShowBooksReader extends HttpServlet {

    private Connection connection;

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
        jdbc();
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html; charset=UTF-8");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Book Inventory</title>");
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #fef8f0; padding: 30px; color: #333; }");
        out.println("h2 { color: #4b2e1a; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }");
        out.println("th { background-color: #e6d3c0; }");
        out.println("tr:nth-child(even) { background-color: #f9f3eb; }");
        out.println("button { background-color: #6b4226; color: white; border: none; padding: 8px 16px; border-radius: 5px; cursor: pointer; }");
        out.println("button:hover { background-color: #4b2e1a; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Our Book Inventory</h2>");

        out.println("<table>");
        out.println("<tr>"
                + "<th>Book Id</th>"
                + "<th>Book Name</th>"
                + "<th>Author</th>"
                + "<th>Edition</th>"
                + "<th>Publication</th>"
                + "<th>Category</th>"
                + "<th>Pages</th>"
                + "<th>Price</th>"
                + "<th>Issue</th>"
                + "</tr>");

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM books");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                out.println("<tr>"
                        + "<td>" + set.getString(1) + "</td>"
                        + "<td>" + set.getString(2) + "</td>"
                        + "<td>" + set.getString(3) + "</td>"
                        + "<td>" + set.getInt(4) + "</td>"
                        + "<td>" + set.getString(5) + "</td>"
                        + "<td>" + set.getString(6) + "</td>"
                        + "<td>" + set.getInt(7) + "</td>"
                        + "<td>" + set.getDouble(8) + "</td>"
                        + "<td>"
                        + "<form action='requestbook' method='post'>"
                        + "<input type='hidden' name='bookid' value='" + set.getString(1) + "'>"
                        + "<button>Request Book</button>"
                        + "</form>"
                        + "</td>"
                        + "</tr>");
            }
            out.println("</table>");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving books from the database.</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
