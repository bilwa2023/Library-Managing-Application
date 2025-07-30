package org.lms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/confirmremovebook")
public class ConfirmRemoveBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String bid = req.getParameter("bookid");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Confirm Deletion</title>");

        // CSS styles start here
        out.println("<style>");
        
        // Body styling
        out.println("body {");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    font-family: 'Georgia', serif;");
        out.println("    background-color: #f3efe0; /* Parchment-style background */");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    height: 100vh;");
        out.println("}");

        // Container box
        out.println(".box {");
        out.println("    background-color: #fff9e6;");
        out.println("    padding: 30px;");
        out.println("    border: 2px solid #8b5e3c;");
        out.println("    border-radius: 10px;");
        out.println("    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);");
        out.println("    text-align: center;");
        out.println("    width: 400px;");
        out.println("}");

        // Header text
        out.println("h2 {");
        out.println("    color: #4b2e1a;");
        out.println("    font-size: 1.3rem;");
        out.println("}");

        // Form buttons layout
        out.println("form {");
        out.println("    display: inline-block;");
        out.println("    margin: 10px;");
        out.println("}");

        // Button base style
        out.println("button {");
        out.println("    padding: 10px 20px;");
        out.println("    border: none;");
        out.println("    border-radius: 5px;");
        out.println("    font-size: 1rem;");
        out.println("    cursor: pointer;");
        out.println("    transition: background-color 0.3s ease;");
        out.println("}");

        // Confirm button
        out.println(".confirm {");
        out.println("    background-color: #a83232; /* Red for danger */");
        out.println("    color: white;");
        out.println("}");
        out.println(".confirm:hover {");
        out.println("    background-color: #812121;");
        out.println("}");

        // Cancel button
        out.println(".cancel {");
        out.println("    background-color: #6b4226;");
        out.println("    color: white;");
        out.println("}");
        out.println(".cancel:hover {");
        out.println("    background-color: #4b2e1a;");
        out.println("}");

        out.println("</style>");
        // CSS ends here

        out.println("</head>");
        out.println("<body>");
        out.println("<div class='box'>");

        out.println("<h2>Are you sure you want to delete this book?</h2>");

        // Confirm delete form
        out.println("<form action='removebook' method='post'>");
        out.println("<input type='hidden' name='bookid' value='" + bid + "'>");
        out.println("<button class='confirm'>Confirm</button>");
        out.println("</form>");

        // Cancel form
        out.println("<form action='displayallbooks' method='post'>");
        out.println("<button class='cancel'>Cancel</button>");
        out.println("</form>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
