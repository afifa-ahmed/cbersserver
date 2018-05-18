package com.cbers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.FireBaseModel;
import com.cbers.models.IncidentModel;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.CbersResponse;
import com.cbers.models.pojos.Incident;
import com.cbers.utils.FireBaseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

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

		String patient_id = req.getParameter("patient_id");
		String incident_id = req.getParameter("incident_id");

		long param = 0;
		List<Incident> patientIncidents = new ArrayList<>();
		if (patient_id == null && incident_id == null) {
			resp.sendError(400, "Invalid Request");
			return;
		} else if (patient_id != null) {
			try {
				param = Long.parseLong(req.getParameter("patient_id"));
				patientIncidents = IncidentModel.getPatientIncidents(param);
			} catch (NumberFormatException | NullPointerException e) {
				e.printStackTrace();
				resp.sendError(400, "Invalid Request");
				return;
			}
		} else {
			try {
				param = Long.parseLong(req.getParameter("incident_id"));
				patientIncidents = IncidentModel.getPatientHistoryFromIncidents(param);
			} catch (NumberFormatException | NullPointerException e) {
				e.printStackTrace();
				resp.sendError(400, "Invalid Request");
				return;
			}
		}

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

		String userAgent = req.getHeader("User-Agent");
		System.out.println("User Agent is: "+userAgent);

		if (userAgent.toLowerCase().contains("android")) {

			PrintWriter out = resp.getWriter();
			ObjectMapper objectMapper= new ObjectMapper();
			String jsonString = "";
			String authString = req.getHeader("Authorization");
			if (!isUserAuthenticated(authString)) {
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Unauthorized Access."));
				resp.setStatus(401);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(jsonString);
				out.flush();
				return;
			}

			String action = req.getParameter("action");
			if (!"query".equalsIgnoreCase(action)) {
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Action missing in request"));
				resp.setStatus(401);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(jsonString);
				out.flush();
				return;
			}

			long id = Long.parseLong(req.getParameter("patient_id"));
			String patient_query = req.getParameter("patient_query");

			int result = -4;
			try {
				result = IncidentModel.insertQuery(id, patient_query.replaceAll("'", ""));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			switch (result) {
			case -1: case -4:
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Something went wrong in posting query."));
				resp.setStatus(500);
				break;
			case -2:
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Sorry, you have to wait till doctor replies your last query."));
				resp.setStatus(400);
				break;
			case -3:
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "More than one open incident found."));
				resp.setStatus(400);
				break;
			case 2:
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Success", "Query Posted Successfully."));
				resp.setStatus(200);
				break;
			}

			System.out.println("Sending Response: " +jsonString);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(jsonString);
			out.flush();
			return;
		} else {
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
			if (!"create".equalsIgnoreCase(action) && !"update".equalsIgnoreCase(action) 
					&& !"close".equalsIgnoreCase(action) && !"reply".equalsIgnoreCase(action) ) {
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
			case "reply":
				replyQuery(req, resp);
				break;    
			}
		}

	}

	private void replyQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long incident_id = Long.parseLong(req.getParameter("incident_id"));
		long incident_log_id = Long.parseLong(req.getParameter("incident_log_id"));
		String query_reply = req.getParameter("query_reply");

		int success = 0;
		try {
			success = IncidentModel.updateReply(incident_id, incident_log_id, query_reply.replaceAll("'", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success == 2) {
			message = "The query has been successfully replied.";
		} else {
			message = "Error: Reply not posted!!";
		}
		req.getSession().setAttribute("queryReplyError", message);
		resp.sendRedirect(resp.encodeRedirectURL("/cbers/incidentLog?incident_id="+incident_id));

		if (FireBaseMessage.SEND_MESSAGE)
			try {
				FireBaseMessage.send(FireBaseModel.getTokenFromIncident(incident_id), "Doctor replied to your query.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FCM Push Failed in replyQuery.");
			}
	}


	private void createIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long patient_id = Long.parseLong(req.getParameter("patient_id"));
		String incident_detail = req.getParameter("incident");
		String solution = req.getParameter("solution");

		Incident incident = new Incident(patient_id, incident_detail.replaceAll("'", ""), solution.replaceAll("'", ""));
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

		if (FireBaseMessage.SEND_MESSAGE)
			try {
				FireBaseMessage.send(FireBaseModel.getTokenFromIncident(patient_id), "Doctor logged an incident.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FCM Push Failed in createIncident.");
			}
	}

	private void updateIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long incident_id = Long.parseLong(req.getParameter("incident_id"));
		String incident_detail = req.getParameter("incident");
		String solution = req.getParameter("solution");

		boolean success = false;
		try {
			success = IncidentModel.updateIncident(incident_id, incident_detail.replaceAll("'", ""), solution.replaceAll("'", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success) {
			message = "The Incident has been successfully updated.";
		} else {
			message = "Error: Incident not updated!!";
		}
		req.getSession().setAttribute("incidentUpdateError", message);
		resp.sendRedirect(resp.encodeRedirectURL("/cbers/incidentLog?incident_id="+incident_id));

		if (FireBaseMessage.SEND_MESSAGE)
			try {
				FireBaseMessage.send(FireBaseModel.getTokenFromIncident(incident_id), "Doctor updated an incident.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FCM Push Failed in updateIncident.");
			}
	}

	private void closeIncident(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long incident_id = Long.parseLong(req.getParameter("incident_id"));
		String closing_comment = req.getParameter("closing_comment");

		boolean success = false;
		try {
			success = IncidentModel.closeIncident(incident_id, closing_comment.replaceAll("'", ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success) {
			message = "The Incident has been successfully closed. <a href=\"/cbers/patientStatus\" class=\"alert-link\">Go Back</a>";
		} else {
			message = "Error: Incident not closed!!";
		}
		req.getSession().setAttribute("incidentCloseError", message);
		resp.sendRedirect(resp.encodeRedirectURL("/cbers/incidentLog?incident_id="+incident_id));

		if (FireBaseMessage.SEND_MESSAGE)
			try {
				FireBaseMessage.send(FireBaseModel.getTokenFromIncident(incident_id), "Doctor closed your incident.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FCM Push Failed in closeIncident.");
			}
	}

}
