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
import com.cbers.models.UserModel;
import com.cbers.models.enums.ColorCode;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.PatientStatus;
import com.cbers.models.pojos.User;
import com.cbers.utils.Util;

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


	void loadPatientStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, List<PatientStatus>> allPatientStatus = PatientStatusModel.getAllPatientStatus();

		List<PatientStatus> allPatient = new ArrayList<>();
		allPatient.addAll( allPatientStatus.get(ColorCode.RED.toString()));
		allPatient.addAll( allPatientStatus.get(ColorCode.ORANGE.toString()));
		allPatient.addAll( allPatientStatus.get(ColorCode.GREEN.toString()));

		String nextJSP = "/list-patient-status.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("redPatients", allPatientStatus.get(ColorCode.RED.toString()));
		req.setAttribute("orangePatients", allPatientStatus.get(ColorCode.ORANGE.toString()));
		req.setAttribute("greenPatients", allPatientStatus.get(ColorCode.GREEN.toString()));
		req.setAttribute("allPatients", allPatient);
		System.out.println("Forwarding patientStatus..."+allPatientStatus);
		dispatcher.forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request > ["+req+"], Session > ["+req.getSession(false)+"]");

		String authString = req.getHeader("Authorization");
		if (!isUserAuthenticated(authString)) {
			resp.sendError(401, "Unauthorized Access");
			return;
		}

		String email = Util.decrypt(req.getParameter("email"));
		int temperature = Integer.parseInt(Util.decrypt(req.getParameter("temperature")));
		int heartRate = Integer.parseInt(Util.decrypt(req.getParameter("heart_rate")));
		String bloodPressure = Util.decrypt(req.getParameter("blood_pressure"));
		int bloodSugar = Integer.parseInt(Util.decrypt(req.getParameter("blood_sugar")));

		User user = UserModel.getUser(email);
		if (!Role.PATIENT.equals(user.getRole())) {
			resp.sendError(401, "Unauthorized Role Access");
			return;
		}
		Long id = user.getId();
		String name = user.getName();
		long phone = user.getPhone();

		ColorCode code = null;

		String[] bp = bloodPressure.split("-");
		int bpLow = Integer.parseInt(bp[0]);
		int bpHigh = Integer.parseInt(bp[1]);

		if (temperature > 102 || heartRate > 120 || bloodSugar > 350 || bpLow < 60 || bpHigh > 200) {
			code = ColorCode.RED;
		} else if (temperature > 100 || heartRate > 90 || bloodSugar > 200 || bpLow < 80 || bpHigh > 150) {
			code = ColorCode.ORANGE;
		} else {
			code = ColorCode.GREEN;
		}

		PatientStatus patientStatus = new PatientStatus(id, name, phone, temperature, heartRate, bloodPressure, bloodSugar, code);
		int number = PatientStatusModel.addPatienStatus(patientStatus );
		if (number > 0) {
			resp.setStatus(200);
		}
		else if (number == 0) {
			resp.sendError(500, "Nothing inserted");
		} else if (number == -2) {
			resp.sendError(500, "DB Update Failed.");
		} else {
			resp.sendError(500, "Something went wrong");
		}
		return;

	}




}
