package com.cbers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.PatientStatus;
import com.cbers.models.Role;

@WebServlet(
		name = "PatientStatusServlet",
		urlPatterns = {"/patientStatus"}
		)
public class PatientStatusServlet extends CbersServlet {


	private static final long serialVersionUID = -7258182158267882519L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request > ["+req+"], Session > ["+req.getSession(false)+"]");
		if (req.getSession(false) == null || req.getSession(false).getAttribute("userName") == null) {
			System.out.println("User not logged in, redirect to login.");
			goToLogin(req, resp);
			return;
		}
		
		if (req.getSession(false).getAttribute("userRole") == null || 
				!req.getSession(false).getAttribute("userRole").equals(Role.DOCTOR.toString())) {
			System.out.println("User not a DOCTOR, redirect to login.");
			unAuthorizedAccess(req, resp);
			return;
		}

		loadPatientStatus(req, resp);
	}


	private void loadPatientStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PatientStatus> result = new ArrayList<>();

		forwardListPatientStatus(req, resp, result);

	}

	private void forwardListPatientStatus(HttpServletRequest req, HttpServletResponse resp, List<PatientStatus> patientStatusList)
			throws ServletException, IOException {
		String nextJSP = "/list-patient-status.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("patientStatusList", patientStatusList);
		System.out.println("Forwarding patientStatus..."+patientStatusList);
		dispatcher.forward(req, resp);
	}

}
