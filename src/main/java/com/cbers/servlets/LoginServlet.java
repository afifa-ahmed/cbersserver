package com.cbers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cbers.models.Role;
import com.cbers.models.User;
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

			User user = null;
			Role role = null;
			if ((user = UserModel.getUser(email)) == null) {
				resp.setStatus(401);
				return;
			} else {
				role = user.getRole();
				if (!user.getPassword().equals(password) || !role.equals(Role.ADMIN)) {
					resp.setStatus(401);
					return;
				}
			}

			HttpSession session = req.getSession(true);
			session.setAttribute("userName", email);
			session.setAttribute("userRole", role.toString());
			System.out.println("User Role Is: " + session.getAttribute("userRole"));

			String encodedURL = "";

			switch (role) {
			case ADMIN:
				encodedURL = resp.encodeRedirectURL("/cbers/user");
				break;
			case DOCTOR:
				encodedURL = resp.encodeRedirectURL("/cbers/patientStatus");
				break;
			case PATIENT:
				resp.setStatus(200);
				return;
			default:
				resp.setStatus(401);
				return;
			}

			resp.sendRedirect(encodedURL);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}