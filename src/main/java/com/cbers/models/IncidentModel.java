package com.cbers.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cbers.db.DbUtils;
import com.cbers.models.enums.State;
import com.cbers.models.pojos.Incident;
import com.cbers.models.pojos.IncidentLog;
import com.cbers.utils.Util;

public class IncidentModel {

	public static List<Incident> getPatientIncidents(long patientId) {
		String query = "select * from incidents where patient_id = "+patientId+";";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		List<Incident> openIncidents = new ArrayList<>();
		List<Incident> closedIncidents = new ArrayList<>();
		for (Map<String, String> incident : result) {
			State state = State.valueOf(incident.get("state"));
			Date closed_at = incident.get("closed_at") == null ? null : Util.getDateFromDbString(incident.get("closed_at"));
			if (state.equals(State.OPEN)) {
				openIncidents.add(new Incident(Long.parseLong(incident.get("id")), Long.parseLong(incident.get("patient_id")), incident.get("incident_detail"), 
						incident.get("solution"), state, Util.getDateFromDbString(incident.get("created_at")), 
						closed_at, incident.get("closing_comment")));
			} else {
				closedIncidents.add(new Incident(Long.parseLong(incident.get("id")), Long.parseLong(incident.get("patient_id")), incident.get("incident_detail"), 
						incident.get("solution"), state, Util.getDateFromDbString(incident.get("created_at")), 
						closed_at, incident.get("closing_comment")));
			}
		}

		List<Incident> finalIncidents = new ArrayList<>();
		finalIncidents.addAll(openIncidents);
		finalIncidents.addAll(closedIncidents);
		return finalIncidents;
	}

	public static Incident getLatestIncident(long patientId) {
		String query = "select * from incidents where patient_id = "+patientId+" order by id desc limit 1;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		if (result.size() == 1) {
			Map<String, String> incident = result.get(0);
			Date closed_at = incident.get("closed_at") == null ? null : Util.getDateFromDbString(incident.get("closed_at"));
			return new Incident(Long.parseLong(incident.get("id")), Long.parseLong(incident.get("patient_id")), incident.get("incident_detail"), 
					incident.get("solution"), State.valueOf(incident.get("state")), Util.getDateFromDbString(incident.get("created_at")), 
					closed_at, incident.get("closing_comment"));
		} else 
			return null;
	}

	public static long addIncident(Incident incident) throws SQLException {
		String query = "select id from  `incidents`  where `patient_id` = "+incident.getPatient_id()+" and state = 'OPEN';";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		if (result.size() > 0) {
			return -2;
		}

		query = "INSERT INTO `incidents` (`patient_id`, `incident_detail`, `solution`) VALUES "
				+ "("+incident.getPatient_id()+", '"+incident.getIncident_detail()+"', '"+incident.getSolution()+"');";
		int rows = DbUtils.runUpdate(query);


		if (rows == 1 ) {
			Incident insertedIncident = getLatestIncident(incident.getPatient_id());
			query = "INSERT INTO `incident_logs` (`incident_id`, `incident_detail`, `solution`) VALUES ("+insertedIncident.getId()+", "
					+ "'"+insertedIncident.getIncident_detail()+"', '"+insertedIncident.getSolution()+"');";
			DbUtils.runUpdate(query);
			return insertedIncident.getId();
		}

		return 0;
	}

	public static boolean updateIncident(long incident_id, String incident_detail, String solution) throws SQLException {
		String query = "UPDATE `incidents` SET `incident_detail` = '"+incident_detail+"',"
				+ " `solution` = '"+solution+"' WHERE `id` = "+incident_id+";";
		int rows = DbUtils.runUpdate(query);

		query = "INSERT INTO `incident_logs` (`incident_id`, `incident_detail`, `solution`) VALUES ("+incident_id+", "
				+ "'"+incident_detail+"', '"+solution+"');";
		DbUtils.runUpdate(query);
		return rows == 1;
	}

	public static boolean closeIncident(long incident_id, String closing_comment) throws SQLException {
		String query = "UPDATE `incidents` SET `state` = '"+State.CLOSED+"',"
				+ " `closing_comment` = '"+closing_comment+"', closed_at = NOW() WHERE `id` = "+incident_id+";";
		int rows = DbUtils.runUpdate(query);

		return rows == 1;
	}

	public static List<IncidentLog> getAllIncidents(long incident_id) {
		String query = "select * from incident_logs where incident_id = "+incident_id+" order by id desc;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		List<IncidentLog> incidentLogs = new ArrayList<>();
		int i = 1;
		for (Map<String, String> incident : result) {
			incidentLogs.add(new IncidentLog(i++, incident.get("incident_detail"), incident.get("solution"), 
					Util.getDateFromDbString(incident.get("created_at"))));
		}
		return incidentLogs;
	}

}
