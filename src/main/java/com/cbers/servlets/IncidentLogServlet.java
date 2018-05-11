package com.cbers.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.IncidentModel;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.IncidentLog;

@WebServlet(
		name = "IncidentLogServlet",
		urlPatterns = {"/incidentLog"}
		)
public class IncidentLogServlet extends CbersServlet {


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
			param = Long.parseLong(req.getParameter("incident_id"));
		} catch (NumberFormatException | NullPointerException e) {
			e.printStackTrace();
			resp.sendError(400, "Invalid Request");
			return;
		}

		List<IncidentLog> patientIncidents = IncidentModel.getAllIncidents(param);
		loadIncidentLogs(req, resp, patientIncidents);
	}


	private void loadIncidentLogs(HttpServletRequest req, HttpServletResponse resp, List<IncidentLog> incidentLogs) 
			throws ServletException, IOException {

		String nextJSP = "/list-patient-incident-log.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("incidentLogs", incidentLogs);
		System.out.println("Forwarding patientIncidentLogs..."+incidentLogs);
		dispatcher.forward(req, resp);
	}

}
