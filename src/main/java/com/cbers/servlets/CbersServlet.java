package com.cbers.servlets;

import java.io.IOException;
import java.util.Base64;

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
	
	public static boolean isUserAuthenticated(String authString){
		try {
			// Header is in the format "Basic 5tyc0uiDat4", extract data before decoding it back to original string
			String[] authParts = authString.split("\\s+");
			String authInfo = authParts[1];

			// Decode the data back to original string
			byte[] decodedBytes = Base64.getDecoder().decode(authInfo);
			String decodedAuth = new String(decodedBytes);
			System.out.println(decodedAuth);

			// your validation code goes here....
			String auth[] = decodedAuth.split(":");
			return auth.length == 2 && "user".equals(auth[0]) && "@llow3d".equals(auth[1]) ;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


}
