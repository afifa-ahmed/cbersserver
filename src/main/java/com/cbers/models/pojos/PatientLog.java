package com.cbers.models.pojos;

import java.util.Date;

import com.cbers.models.enums.ColorCode;

public class PatientLog {

	private int id;
	private int temperature;
	private int heartRate;
	private String bloodPressure;
	private int bloodSugar;
	private ColorCode code;
	private Date createdAt;

	public PatientLog(int id, int temperature, int heartRate, String bloodPressure, int bloodSugar, ColorCode code,
			Date createdAt) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.heartRate = heartRate;
		this.bloodPressure = bloodPressure;
		this.bloodSugar = bloodSugar;
		this.code = code;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "PatientLog [id=" + id + ", temperature=" + temperature + ", heartRate=" + heartRate + ", bloodPressure="
				+ bloodPressure + ", bloodSugar=" + bloodSugar + ", code=" + code + ", createdAt=" + createdAt + "]";
	}

	public int getId() {
		return id;
	}

	public int getTemperature() {
		return temperature;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public int getBloodSugar() {
		return bloodSugar;
	}

	public ColorCode getCode() {
		return code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public void setBloodSugar(int bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public void setCode(ColorCode code) {
		this.code = code;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


}
