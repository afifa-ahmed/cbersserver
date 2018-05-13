package com.cbers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.PatientStatusModel;
import com.cbers.models.enums.ColorCode;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.PatientLog;

@WebServlet(
		name = "PatientStatusLogServlet",
		urlPatterns = {"/patientStatusLog"}
		)
public class PatientStatusLogServlet extends CbersServlet {


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

		long param = 0;
		try {
			param = Long.parseLong(req.getParameter("patient_id"));
		} catch (NumberFormatException | NullPointerException e) {
			e.printStackTrace();
			resp.sendError(400, "Invalid Request");
			return;
		}

		req.setAttribute("patient_id", param);
		Map<String, List<PatientLog>> patientStatusLogs = PatientStatusModel.getPatientStatusLogs(param);
		loadPatientStatusLogs(req, resp, patientStatusLogs);
	}


	private void loadPatientStatusLogs(HttpServletRequest req, HttpServletResponse resp, Map<String, List<PatientLog>> patientStatusLogs) 
			throws ServletException, IOException {
		List<PatientLog> allPatient = new ArrayList<>();
		allPatient.addAll( patientStatusLogs.get(ColorCode.RED.toString()));
		allPatient.addAll( patientStatusLogs.get(ColorCode.ORANGE.toString()));
		allPatient.addAll( patientStatusLogs.get(ColorCode.GREEN.toString()));

		String nextJSP = "/list-patient-status-log.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("redPatients", patientStatusLogs.get(ColorCode.RED.toString()));
		req.setAttribute("orangePatients", patientStatusLogs.get(ColorCode.ORANGE.toString()));
		req.setAttribute("greenPatients", patientStatusLogs.get(ColorCode.GREEN.toString()));
		req.setAttribute("allPatients", allPatient);
		System.out.println("Forwarding patientStatusLogs..."+patientStatusLogs);
		dispatcher.forward(req, resp);
	}

}
