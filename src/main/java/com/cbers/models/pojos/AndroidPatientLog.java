package com.cbers.models.pojos;

import java.util.Date;

import com.cbers.models.enums.ColorCode;

public class AndroidPatientLog  extends PatientLog {

	private String query;
	private String solution;

	public AndroidPatientLog(int id, int temperature, int heartRate, String bloodPressure, int bloodSugar,
			ColorCode code, Date createdAt, String query, String solution) {
		super(id, temperature, heartRate, bloodPressure, bloodSugar, code, createdAt);
		this.query = query;
		this.solution = solution;
	}

	public AndroidPatientLog(PatientLog patientLog, String query, String solution) {
		super(patientLog.getId(), patientLog.getTemperature(), patientLog.getHeartRate(), patientLog.getBloodPressure(), 
				patientLog.getBloodSugar(), patientLog.getCode(), patientLog.getCreatedAt());
		this.query = query;
		this.solution = solution;
	}

	public String getQuery() {
		return query;
	}

	public String getSolution() {
		return solution;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

}
