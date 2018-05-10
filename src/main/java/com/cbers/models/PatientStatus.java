package com.cbers.models;

import java.util.Date;

public class PatientStatus {

	private long id;
	private String name;
	private long phone;
	private int age;
	private int temperature;
	private int heartRate;
	private String bloodPressure;
	private String bloodSugar;
	private ColorCode code;
	private Date createdAt;
	private State state;
	
	public PatientStatus(long id, String name, long phone, int age, int temperature, int heartRate,
			String bloodPressure, String bloodSugar, ColorCode code, Date createdAt, State state) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.age = age;
		this.temperature = temperature;
		this.heartRate = heartRate;
		this.bloodPressure = bloodPressure;
		this.bloodSugar = bloodSugar;
		this.code = code;
		this.createdAt = createdAt;
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getPhone() {
		return phone;
	}

	public int getAge() {
		return age;
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

	public String getBloodSugar() {
		return bloodSugar;
	}

	public ColorCode getCode() {
		return code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public State getState() {
		return state;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void setAge(int age) {
		this.age = age;
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

	public void setBloodSugar(String bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public void setCode(ColorCode code) {
		this.code = code;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setState(State state) {
		this.state = state;
	}

}
