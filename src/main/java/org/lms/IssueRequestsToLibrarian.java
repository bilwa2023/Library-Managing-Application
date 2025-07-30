package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/issuerequests")
public class IssueRequestsToLibrarian extends HttpServlet {

    private Connection connection = null;

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
        out.println("<html><head><meta charset='UTF-8'><title>Issue Requests</title>");

        // Embedded CSS
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 20px; }");
        out.println("h2 { color: #4b2e1a; text-align: center; margin-bottom: 30px; }");
        out.println("table { width: 100%; border-collapse: collapse; background-color: #fff9e6; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("th, td { padding: 12px 15px; border: 1px solid #8b5e3c; text-align: center; }");
        out.println("th { background-color: #8b5e3c; color: white; }");
        out.println("tr:nth-child(even) { background-color: #fdf6e3; }");
        out.println("tr:hover { background-color: #f2e2ba; }");
        out.println("button { padding: 6px 12px; background-color: #6b4226; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 0.9rem; }");
        out.println("button:hover { background-color: #4b2e1a; }");
        out.println("form { margin: 0; }");
        out.println("</style>");

        out.println("</head><body>");
        out.println("<h2>📄 Book Issue Requests</h2>");

        try {
            int slno = 1;

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM issuerequest WHERE status = 'requested'");
            ResultSet set = ps.executeQuery();

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Sl No.</th>");
            out.println("<th>Book ID</th>");
            out.println("<th>Reader ID</th>");
            out.println("<th>Approve</th>");
            out.println("<th>Reject</th>");
            out.println("</tr>");

            while (set.next()) {
                out.println("<tr>");

                out.println("<td>" + slno + "</td>");

                // Book ID link
                out.println("<td>");
                out.println("<form action='searchbook' method='post'>");
                out.println("<input type='hidden' name='bid' value='" + set.getString(2) + "'>");
                out.println("<button>" + set.getString(2) + "</button>");
                out.println("</form>");
                out.println("</td>");

                // Reader ID link
                out.println("<td>");
                out.println("<form action='searchreader' method='post'>");
                out.println("<input type='hidden' name='rid' value='" + set.getString(3) + "'>");
                out.println("<button>" + set.getString(3) + "</button>");
                out.println("</form>");
                out.println("</td>");

                // Approve
                out.println("<td>");
                out.println("<form action='approverequest' method='post'>");
                out.println("<input type='hidden' name='slno' value='" + set.getInt(1) + "'>");
                out.println("<button>Approve</button>");
                out.println("</form>");
                out.println("</td>");

                // Reject
                out.println("<td>");
                out.println("<form action='rejectrequest' method='post'>");
                out.println("<input type='hidden' name='slno' value='" + set.getInt(1) + "'>");
                out.println("<button>Reject</button>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
                slno++;
            }

            out.println("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving issue requests.</p>");
        }

        out.println("</body></html>");
    }
}
