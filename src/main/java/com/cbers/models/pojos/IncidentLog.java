package com.cbers.models.pojos;

import java.util.Date;

public class IncidentLog {

	private int id;
	private String incident_detail;
	private String solution;
	private Date created_at;

	public IncidentLog(int id, String incident_detail, String solution, Date created_at) {
		super();
		this.id = id;
		this.incident_detail = incident_detail;
		this.solution = solution;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "IncidentLog [id=" + id + ", incident_detail=" + incident_detail + ", solution=" + solution
				+ ", created_at=" + created_at + "]";
	}

	public long getId() {
		return id;
	}

	public String getIncident_detail() {
		return incident_detail;
	}

	public String getSolution() {
		return solution;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIncident_detail(String incident_detail) {
		this.incident_detail = incident_detail;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

}
