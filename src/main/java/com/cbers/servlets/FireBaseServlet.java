package com.cbers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.FireBaseModel;
import com.cbers.models.pojos.CbersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(urlPatterns = {"/fcm"})
public class FireBaseServlet extends CbersServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6242852158491002057L;

	@Override
	protected void doPost (HttpServletRequest req,
			HttpServletResponse resp)
					throws ServletException, IOException {

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
			}

			long patient_id = -1;
			String token = null;

			try {
				patient_id = Long.parseLong(req.getParameter("patient_id"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Invalid patient id."));
				resp.setStatus(400);
			}

			token = req.getParameter("token");

			if (patient_id > 0 && token != null) {
				try {
					if (FireBaseModel.addToken(patient_id, token) == 1) {
						jsonString =  objectMapper.writeValueAsString(new CbersResponse("Success", "Token added"));
						resp.setStatus(200);
					} else {
						jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "DB update failed"));
						resp.setStatus(500);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Something went wrong"));
					resp.setStatus(500);
				}


			} else {
				jsonString =  objectMapper.writeValueAsString(new CbersResponse("Failure", "Invalid payload"));
				resp.setStatus(401);
			}

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(jsonString);
			out.flush();
			return;
		}
	}

}