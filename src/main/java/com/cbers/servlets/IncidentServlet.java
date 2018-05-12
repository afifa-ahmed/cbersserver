package com.cbers.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.IncidentModel;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.Incident;

@WebServlet(
		name = "IncidentServlet",
		urlPatterns = {"/incident"}
		)
public class IncidentServlet extends CbersServlet {


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

		List<Incident> patientIncidents = IncidentModel.getPatientIncidents(param);
		loadIncidents(req, resp, patientIncidents);
	}


	private void loadIncidents(HttpServletRequest req, HttpServletResponse resp, List<Incident> patientIncidents) 
			throws ServletException, IOException {

		String nextJSP = "/list-patient-incident.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("patientIncidentList", patientIncidents);
		System.out.println("Forwarding patientIncidents..."+patientIncidents);
		dispatcher.forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request > ["+req+"], Session > ["+req.getSession(false)+"]");
		if (req.getSession(false) == null || req.getSession(false).getAttribute("userName") == null) {
			System.out.println("User not logged in, redirect to login.");
			goToLogin(req, resp);
			return;
		}

		if (req.getSession(false).getAttribute("userRole") == null || 
				!req.getSession(false).getAttribute("userRole").equals(Role.DOCTOR.toString())) {
			System.out.println("User not a doctor, redirect to login.");
			unAuthorizedAccess(req, resp);
			return;
		}

		String action = req.getParameter("action");
		if (!"create".equalsIgnoreCase(action) && !"update".equalsIgnoreCase(action) && !"close".equalsIgnoreCase(action)) {
			resp.setStatus(400);
			return;
		}
		switch (action.toLowerCase()) {
		case "create":
			createIncident(req, resp);
			break;
		case "update":
			updateIncident(req, resp);
			break;    
		case "close":
			closeIncident(req, resp);
			break;    
		}

	}

	private void createIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long patient_id = Long.parseLong(req.getParameter("patient_id"));
		String incident_detail = req.getParameter("incident");
		String solution = req.getParameter("solution");

		Incident incident = new Incident(patient_id, incident_detail, solution);
		long incidentId = 0;
		try {
			incidentId = IncidentModel.addIncident(incident);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//		List<Incident> incidentList = IncidentModel.getPatientIncidents(patient_id);
		String message = null;
		if (incidentId > 0 ) {
			message = "The new advice has been successfully created.";
		} else if (incidentId == -2 ) {
			message = "Error: An open advice already exists, please close it before adding new.";
		} else {
			message = "Error: Incident not created!!";
		}
		req.getSession().setAttribute("incidentCreateError", message);
		resp.sendRedirect(resp.encodeRedirectURL("/cbers/patientStatus"));

		// TODO HOW TO UPDATE ANDROID APP
	}

	private void updateIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long incident_id = Long.parseLong(req.getParameter("incident_id"));
		long patient_id = Long.parseLong(req.getParameter("patient_id"));
		String incident_detail = req.getParameter("incident");
		String solution = req.getParameter("solution");

		boolean success = false;
		try {
			success = IncidentModel.updateIncident(incident_id, incident_detail, solution);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success) {
			message = "The Incident has been successfully updated.";
		} else {
			message = "Error: Incident not updated!!";
		}
		List<Incident> incidentList = IncidentModel.getPatientIncidents(patient_id);;
		req.setAttribute("idIncident", incident_id);
		req.setAttribute("message", message);
		loadIncidents(req, resp, incidentList);

		// TODO HOW TO UPDATE ANDROID APP
	}

	private void closeIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long incident_id = Long.parseLong(req.getParameter("incident_id"));
		long patient_id = Long.parseLong(req.getParameter("patient_id"));
		String closing_comment = req.getParameter("closing_comment");

		boolean success = false;
		try {
			success = IncidentModel.closeIncident(incident_id, closing_comment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success) {
			message = "The Incident has been successfully closed.";
		} else {
			message = "Error: Incident not closed!!";
		}
		List<Incident> incidentList = IncidentModel.getPatientIncidents(patient_id);;
		req.setAttribute("idIncident", incident_id);
		req.setAttribute("message", message);
		loadIncidents(req, resp, incidentList);

		// TODO HOW TO UPDATE ANDROID APP
	}

}
