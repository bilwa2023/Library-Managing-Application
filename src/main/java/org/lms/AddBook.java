package org.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/addbook")
public class AddBook extends HttpServlet {

    private Connection connection;

    public void jdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        int id = 0;
        jdbc();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MAX(bid) FROM bookid");
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void setId() throws SQLException {
        jdbc();
        int id = getId();
        PreparedStatement ps = connection.prepareStatement("UPDATE bookid SET bid=? WHERE bid=?");
        ps.setInt(1, id + 1);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        jdbc();

        String id = "BK" + getId();
        String bname = req.getParameter("name");
        String author = req.getParameter("author");
        int edition = Integer.parseInt(req.getParameter("edition"));
        String publication = req.getParameter("publication");
        String category = req.getParameter("category");
        int pages = Integer.parseInt(req.getParameter("pages"));
        double price = Double.parseDouble(req.getParameter("price"));
        int units = Integer.parseInt(req.getParameter("units"));

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO books VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, bname);
            ps.setString(3, author);
            ps.setInt(4, edition);
            ps.setString(5, publication);
            ps.setString(6, category);
            ps.setInt(7, pages);
            ps.setDouble(8, price);
            ps.setInt(9, units);

            int res = ps.executeUpdate();

            if (res == 1) {
                setId();
                HttpSession session = req.getSession();
                session.setAttribute("addbooks", "Book added successfully");
                RequestDispatcher rd = req.getRequestDispatcher("librariandashboard");
                connection.close();
                ps.close();
                rd.forward(req, resp);
            } else {
                showErrorPage(resp, "Book could not be added.");
                connection.close();
                ps.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorPage(resp, "An error occurred while adding the book.");
        }
    }

    private void showErrorPage(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Add Book Error</title>");
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; display: flex; justify-content: center; align-items: center; height: 100vh; }");
        out.println(".error-box { background-color: #fff9e6; border: 2px solid #8b5e3c; border-radius: 10px; padding: 30px 40px; box-shadow: 0 0 15px rgba(0,0,0,0.1); text-align: center; width: 450px; }");
        out.println("h2 { color: #a83232; }");
        out.println("a { display: inline-block; margin-top: 20px; text-decoration: none; color: #6b4226; font-style: italic; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='error-box'>");
        out.println("<h2>" + message + "</h2>");
        out.println("<a href='addbook.html'>← Back to Add Book</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
