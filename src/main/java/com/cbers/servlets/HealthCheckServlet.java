package com.cbers.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name = "HealthCheckServlet",
		urlPatterns = {"/health_check"}
		)
public class HealthCheckServlet extends HttpServlet {


	private static final long serialVersionUID = -7258182158267882519L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Hitting healthCheck API.");
		resp.setStatus(200);
		System.out.println("Returning from healthCheck API.");
	}


}
