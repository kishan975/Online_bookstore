package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.model.UserRole;
import com.bittercode.util.StoreUtil;
//Http Servlet extended class for showing the about information
public class AboutServlet extends HttpServlet {

    private static final String ABOUT_SECTION = "<div style='min-height:70vh; display:flex; align-items:center; "
            + "justify-content:center; background:linear-gradient(135deg, #1f2937, #111827 55%, #0f172a); "
            + "color:#fff; text-align:center; padding:40px 20px;'>"
            + "<div style='background:rgba(0,0,0,0.45); padding:36px 28px; border-radius:18px; "
            + "box-shadow:0 12px 30px rgba(0,0,0,0.28); max-width:700px; width:100%;'>"
            + "<h1 style='margin:0; font-size:48px; letter-spacing:2px;'>About Us</h1>"
            + "<p style='margin:22px 0 0; font-size:34px; font-weight:700;'>created by Kishan and Harsh</p>"
            + "</div></div>";

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        //If the store is logged in as customer or seller show about info
        if (StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            pw.println(ABOUT_SECTION);

        } else if (StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            pw.println(ABOUT_SECTION);

        } else {
            //If the user is not logged in, ask to login first
            //Proceed only if logged in or forword to login page
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
        }

    }

}
