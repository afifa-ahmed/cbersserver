package com.cbers.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbers.db.DbUtils;
import com.cbers.utils.Util;

public class PatientStatusModel {

	public static Map<String, List<PatientStatus>> getAllPatientStatus() {
		String query = "select u.`id` id, u.`name` name, u.`phone` phone, u.`dob` dob, ps.`temperature` temperature, "
				+ "ps.`heart_rate` heart_rate, ps.`blood_pressure` blood_pressure, ps.`blood_sugar` blood_sugar, ps.`state` status, "
				+ "ps.`updated_at` created_at, ph.`state` incident_state from `users` u join `patient_status` ps on (u.id = ps.`patient_id`) "
				+ "left join `patient_history` ph on (ps.`patient_id` = ph.`patient_id` AND ph.`state` = 'OPEN');";

		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		Map<String, List<PatientStatus>> patients = new HashMap<>();
		patients.put("RED", new ArrayList<>());
		patients.put("ORANGE", new ArrayList<>());
		patients.put("GREEN", new ArrayList<>());
		patients.put("OPEN", new ArrayList<>());

		System.out.println("\nReturning PatientStatus Result: "+result+"\n");

		for (Map<String, String> patientStatus : result) {
			int age = Util.getAgeFromDOB(Util.getDateFromDbString(patientStatus.get("dob")));
			ColorCode code = ColorCode.valueOf(patientStatus.get("status"));
			State state = patientStatus.get("incident_state") == null ? null : State.valueOf(patientStatus.get("incident_state"));

			PatientStatus pStat = new PatientStatus(Long.parseLong(patientStatus.get("id")), patientStatus.get("name"), 
					Long.parseLong(patientStatus.get("phone")), age, Integer.parseInt(patientStatus.get("temperature")), 
					Integer.parseInt(patientStatus.get("heart_rate")), patientStatus.get("blood_pressure"), 
					Integer.parseInt(patientStatus.get("blood_sugar")), code, Util.getDateFromDbString(patientStatus.get("created_at")), 
					state);

			if (State.OPEN.equals(state)) {
				patients.get("OPEN").add(pStat);
			} else {
				switch (code) {
				case RED:
					patients.get("RED").add(pStat);
					break;
				case ORANGE:
					patients.get("ORANGE").add(pStat);
					break;
				case GREEN:
					patients.get("GREEN").add(pStat);
					break;
				}
			}
		}
		System.out.println("\nReturning PatientStatus: "+patients+"\n");
		return patients;
	}

	public static int addPatienStatus(PatientStatus patientStatus) {
		String queryPH = "UPDATE `patient_status` SET `temperature` = "+patientStatus.getTemperature()+", `heart_rate` = "+patientStatus.getHeartRate()+", "
				+ "`blood_pressure` = '"+patientStatus.getBloodPressure()+"', `blood_sugar` = "+patientStatus.getBloodSugar()+", "
				+ "`state` = '"+patientStatus.getCode()+"', `updated_at` = NOW() WHERE `patient_id` = "+patientStatus.getId()+";";
		String queryPL = "INSERT INTO `patient_logs` (`patient_id`, `temperature`, `heart_rate`, `blood_pressure`, `blood_sugar`, `state`) VALUES "
				+ "( "+patientStatus.getId()+", "+patientStatus.getTemperature()+", "+patientStatus.getHeartRate()+", '"+patientStatus.getBloodPressure()+"', "
				+ ""+patientStatus.getBloodSugar()+", '"+patientStatus.getCode()+"')";

		try {
			return DbUtils.runUpdate(queryPH) + DbUtils.runUpdate(queryPL);
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}

}
