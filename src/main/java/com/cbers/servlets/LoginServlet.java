package com.cbers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cbers.models.UserModel;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.User;
import com.fasterxml.jackson.databind.ObjectMapper;


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
		String userAgent = req.getHeader("User-Agent");
		System.out.println("User Agent is: "+userAgent);
		try {
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			System.out.println("User email: " + email);
			System.out.println("User password: " + password);

			User user = null;
			Role role = null;
			if ((user = UserModel.getUser(email)) == null) {
				req.setAttribute("error", "User does not exist");
				String nextJSP = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				dispatcher.forward(req, resp);
				return;
			} 
			role = user.getRole();
			if (!user.getPassword().equals(password)) {
				req.setAttribute("error", "Password is incorrect");
				req.setAttribute("email", email);
				String nextJSP = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				dispatcher.forward(req, resp);
				return;
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
				if (!userAgent.toLowerCase().contains("android")){
					session.invalidate();
					req.setAttribute("error", "You are not authorized.");
					String nextJSP = "/index.jsp";
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
					dispatcher.forward(req, resp);
					return;
				} else {
					PrintWriter out = resp.getWriter();
					ObjectMapper objectMapper= new ObjectMapper();
					String jsonString = "";
					jsonString = objectMapper.writeValueAsString(user);
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					out.print(jsonString);
					out.flush();
					resp.setStatus(200);
					return;
				}
			default:
				req.setAttribute("error", "You are not authorized.");
				req.setAttribute("email", email);
				String nextJSP = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				dispatcher.forward(req, resp);
				return;
			}

			resp.sendRedirect(encodedURL);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doGet (HttpServletRequest req,
			HttpServletResponse resp)
					throws ServletException, IOException {

		resp.setContentType("text/html");

		if (req.getSession(false) == null || req.getSession(false).getAttribute("userName") == null) {
			req.setAttribute("error", "You are not logged in.");
			String nextJSP = "/index.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(req, resp);
			return;
		}

		if (req.getSession(false).getAttribute("userRole") != null ) {

			HttpSession session = req.getSession(false);
			Role role = Role.valueOf(session.getAttribute("userRole").toString());
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
				req.setAttribute("error", "You are not authorized.");
				String nextJSP = "/index.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				dispatcher.forward(req, resp);
				return;
			}

			resp.sendRedirect(encodedURL);
		} else {
			req.setAttribute("error", "You are not authorized.");
			String nextJSP = "/index.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
			dispatcher.forward(req, resp);
			return;
		}

	}
}