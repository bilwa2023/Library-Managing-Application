package org.lms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/librariandashboard")
public class LibrarianDashboard extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("lname");
        String addbookMsg = (String) session.getAttribute("addbooks");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Librarian Dashboard</title>");

        // Embedded CSS
        out.println("<style>");
        out.println("body { font-family: 'Georgia', serif; background-color: #f3efe0; padding: 40px; text-align: center; }");
        out.println("h2 { color: #4b2e1a; margin-bottom: 30px; }");
        out.println("form { margin: 10px auto; }");
        out.println(".click {"
                + "padding: 10px 20px;"
                + "background-color: #6b4226;"
                + "color: white;"
                + "border: none;"
                + "border-radius: 6px;"
                + "cursor: pointer;"
                + "font-size: 1rem;"
                + "width: 250px;"
                + "margin-top: 8px;"
                + "box-shadow: 0 2px 6px rgba(0,0,0,0.2);"
                + "}");
        out.println(".click:hover { background-color: #4b2e1a; }");
        out.println(".header { display: flex; justify-content: center; gap: 20px; margin-bottom: 30px; }");
        out.println("</style>");

        out.println("</head><body>");

        // Greeting or book message
        if (addbookMsg != null) {
            out.println("<h2>" + addbookMsg + "</h2>");
            session.removeAttribute("addbooks");
        } else {
            out.println("<h2>Welcome, " + name + "</h2>");
        }

        // Profile and logout buttons
        out.println("<div class='header'>");
        out.println("<form action='librarianprofile' method='post'>");
        out.println("<button class='click'>Profile</button>");
        out.println("</form>");
        out.println("<form action='librarianlogin.html' method='post'>");
        out.println("<button class='click'>Logout</button>");
        out.println("</form>");
        out.println("</div>");

        // Dashboard action buttons
        out.println("<form action='addbook.html' method='post'>");
        out.println("<button class='click'>Add Book</button>");
        out.println("</form>");

        out.println("<form action='searchbook.html' method='post'>");
        out.println("<button class='click'>Search Book</button>");
        out.println("</form>");

        out.println("<form action='displayborrowedbooks' method='post'>");
        out.println("<button class='click'>Display Borrowed Books</button>");
        out.println("</form>");

        out.println("<form action='issuerequests' method='post'>");
        out.println("<button class='click'>Issue Requests</button>");
        out.println("</form>");

        out.println("<form action='displayallbooks' method='post'>");
        out.println("<button class='click'>Display All Books</button>");
        out.println("</form>");

        out.println("<form action='displayreaders' method='post'>");
        out.println("<button class='click'>Display Readers</button>");
        out.println("</form>");

        out.println("</body></html>");
    }
}
