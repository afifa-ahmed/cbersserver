package com.cbers.models.pojos;

import java.util.Date;

import com.cbers.models.enums.Role;

public class IncidentLog {

	private int id;
	private long incident_log_id;
	private long incident_id;
	private String incident_detail;
	private String solution;
	private Role created_by;
	private Date created_at;

	public IncidentLog(int id, long incident_log_id, long incident_id, String incident_detail, String solution, Role created_by, Date created_at) {
		super();
		if (Role.ADMIN.equals(created_at)) {
			throw new IllegalArgumentException("Illegal Role.");
		}
		this.id = id;
		this.incident_log_id = incident_log_id;
		this.setIncident_id(incident_id);
		this.incident_detail = incident_detail;
		this.solution = solution;
		this.setCreated_by(created_by);
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "IncidentLog [id=" + id + ", incident_log_id=" + incident_log_id + ", incident_id=" + incident_id
				+ ", incident_detail=" + incident_detail + ", solution=" + solution + ", created_by=" + created_by
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

	public long getIncident_id() {
		return incident_id;
	}

	public void setIncident_id(long incident_id) {
		this.incident_id = incident_id;
	}

	public Role getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Role created_by) {
		this.created_by = created_by;
	}

	public long getIncident_log_id() {
		return incident_log_id;
	}

	public void setIncident_log_id(long incident_log_id) {
		this.incident_log_id = incident_log_id;
	}

}
