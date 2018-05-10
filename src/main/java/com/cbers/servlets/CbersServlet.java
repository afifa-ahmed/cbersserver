package com.cbers.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class CbersServlet extends HttpServlet {


	private static final long serialVersionUID = -7258182158267882519L;


	public void goToLogin(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nextJSP = "/index.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}
	
	public void unAuthorizedAccess(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session != null)
			session.invalidate();
		req.getRequestDispatcher("/index.jsp").forward(req,resp);
	}


}
