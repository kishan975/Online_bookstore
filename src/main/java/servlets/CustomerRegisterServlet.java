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
import com.bittercode.constant.ResponseCode;
import com.bittercode.constant.db.UsersDBConstants;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.UserService;
import com.bittercode.service.impl.UserServiceImpl;

@WebServlet("/register")   // ✅ IMPORTANT

public class CustomerRegisterServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

UserService userService = new UserServiceImpl();

// ✅ OPEN REGISTER PAGE
protected void doGet(HttpServletRequest req, HttpServletResponse res) 
        throws IOException, ServletException {

    RequestDispatcher rd = req.getRequestDispatcher("CustomerRegister.html");
    rd.forward(req, res);
}

// ✅ HANDLE FORM SUBMIT
protected void doPost(HttpServletRequest req, HttpServletResponse res) 
        throws IOException, ServletException {

    res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
    PrintWriter pw = res.getWriter();

    String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);
    String fName = req.getParameter(UsersDBConstants.COLUMN_FIRSTNAME);
    String lName = req.getParameter(UsersDBConstants.COLUMN_LASTNAME);
    String addr = req.getParameter(UsersDBConstants.COLUMN_ADDRESS);
    String phNo = req.getParameter(UsersDBConstants.COLUMN_PHONE);
    String mailId = req.getParameter(UsersDBConstants.COLUMN_MAILID);

    User user = new User();
    user.setEmailId(mailId);
    user.setFirstName(fName);
    user.setLastName(lName);
    user.setPassword(pWord);
    user.setPhone(Long.parseLong(phNo));
    user.setAddress(addr);

    try {

        String respCode = userService.register(UserRole.CUSTOMER, user);

        if (ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)) {

            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);

            pw.println("<table class='tab'><tr><td>User Registered Successfully</td></tr></table>");

        } else {

            RequestDispatcher rd = req.getRequestDispatcher("CustomerRegister.html");
            rd.include(req, res);

            pw.println("<table class='tab'><tr><td>" + respCode + "</td></tr></table>");
            pw.println("Sorry! Try again");

        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
