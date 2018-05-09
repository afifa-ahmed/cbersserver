package com.cbers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cbers.models.UserModel;


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6242852158491002057L;

	@Override
    protected void doPost (HttpServletRequest req,
                        HttpServletResponse resp)
                        throws ServletException, IOException {

        resp.setContentType("text/html");

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            System.out.println("User email: " + email);
            System.out.println("User password: " + password);

            if (!UserModel.validUser(email, password)) {
            	resp.setStatus(401);
            	return;
            }
            
            HttpSession session = req.getSession(true);
            session.setAttribute("user", email);
            session.setAttribute("userRole", UserModel.getRole(password));
            System.out.println("User Role Is: " + session.getAttribute("userRole"));
            
            String encodedURL = resp.encodeRedirectURL("/cbers/emergencyMonitor");
            
            resp.sendRedirect(encodedURL);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}