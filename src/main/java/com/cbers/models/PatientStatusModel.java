package com.cbers.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbers.db.DbUtils;
import com.cbers.models.enums.ColorCode;
import com.cbers.models.enums.State;
import com.cbers.models.pojos.PatientLog;
import com.cbers.models.pojos.PatientStatus;
import com.cbers.utils.Util;

public class PatientStatusModel {

	public static Map<String, List<PatientStatus>> getAllPatientStatus() {
		String query = "select u.`id` id, u.`name` name, u.`phone` phone, u.`dob` dob, ps.`temperature` temperature, "
				+ "ps.`heart_rate` heart_rate, ps.`blood_pressure` blood_pressure, ps.`blood_sugar` blood_sugar, ps.`state` status, "
				+ "ps.`updated_at` created_at, ph.`state` incident_state from `users` u join `patient_status` ps on (u.id = ps.`patient_id`) "
				+ "left join `incidents` ph on (ps.`patient_id` = ph.`patient_id` AND ph.`state` = 'OPEN');";

		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		Map<String, List<PatientStatus>> patients = new HashMap<>();
		patients.put("RED", new ArrayList<>());
		patients.put("ORANGE", new ArrayList<>());
		patients.put("GREEN", new ArrayList<>());

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

	public static Map<String, List<PatientLog>> getPatientStatusLogs(long patient_id) {
		String query = "select * from patient_logs where patient_id = "+patient_id+" order by id desc limit 20;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);

		Map<String, List<PatientLog>> patients = new HashMap<>();
		patients.put("RED", new ArrayList<>());
		patients.put("ORANGE", new ArrayList<>());
		patients.put("GREEN", new ArrayList<>());

		System.out.println("\nReturning PatientStatus Result: "+result+"\n");


		int i = 1;
		for (Map<String, String> patientStatus : result) {
			ColorCode code = ColorCode.valueOf(patientStatus.get("state"));

			PatientLog pStat = new PatientLog(i++, Integer.parseInt(patientStatus.get("temperature")), 
					Integer.parseInt(patientStatus.get("heart_rate")), patientStatus.get("blood_pressure"), 
					Integer.parseInt(patientStatus.get("blood_sugar")), code,
					Util.getDateFromDbString(patientStatus.get("created_at")));

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
		System.out.println("\nReturning PatientStatus: "+patients+"\n");

		return patients;
	}

	public static PatientLog getLatestPatientStatus(String email) {
		String query = "select pl.* from `patient_logs` pl join `users` u on (pl.`patient_id` = u.`id`) "
				+ "where u.`email` = '"+email+"' order by pl.id desc limit 1;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);

		System.out.println("\nReturning PatientStatus Result: "+result+"\n");

		if (result.size() != 1) {
			return null;
		}

		Map<String, String> patientStatus = result.get(0);

		PatientLog pStat = new PatientLog(Integer.parseInt(patientStatus.get("patient_id")), Integer.parseInt(patientStatus.get("temperature")), 
				Integer.parseInt(patientStatus.get("heart_rate")), patientStatus.get("blood_pressure"), 
				Integer.parseInt(patientStatus.get("blood_sugar")), ColorCode.valueOf(patientStatus.get("state")),
				Util.getDateFromDbString(patientStatus.get("created_at")));

		System.out.println("\nReturning PatientStatus: "+pStat+"\n");

		return pStat;
	}

}
