package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.constant.BookStoreConstants;
import com.bittercode.constant.db.UsersDBConstants;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.UserService;
import com.bittercode.service.impl.UserServiceImpl;

@WebServlet("/login")
public class CustomerLoginServlet extends HttpServlet {

private static final long serialVersionUID = 1L; // ✅ good practice

UserService authService = new UserServiceImpl();

// ✅ Handles clicking "Login" (GET request)
protected void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws IOException, ServletException {

    RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
    rd.forward(req, res);
}

// ✅ Handles form submission (POST request)
protected void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws IOException, ServletException {

    res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
    PrintWriter pw = res.getWriter();

    String uName = req.getParameter(UsersDBConstants.COLUMN_USERNAME);
    String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);

    User user = authService.login(UserRole.CUSTOMER, uName, pWord, req.getSession());

    try {

        if (user != null) {

            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);

            pw.println("<div id='topmid'><h1>Welcome to Online <br>Book Store</h1></div>"
                    + "<br>"
                    + "<table class='tab'>"
                    + "<tr><td><p>Welcome " + user.getFirstName() + ", Happy Learning !!</p></td></tr>"
                    + "</table>");

        } else {

            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);

            pw.println("<table class='tab'><tr><td>Incorrect UserName or Password</td></tr></table>");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
