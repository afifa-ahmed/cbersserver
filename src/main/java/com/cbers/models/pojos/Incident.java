package com.cbers.models.pojos;

import java.util.Date;

import com.cbers.models.enums.State;

public class Incident {

	private long id;
	private long patient_id;
	private String incident_detail;
	private String solution;
	private State state;
	private Date created_at;
	private Date closed_at;
	private String closing_comment;

	public Incident(long id, long patient_id, String incident_detail, String solution, State state, Date created_at,
			Date closed_at, String closing_comment) {
		super();
		this.id = id;
		this.incident_detail = incident_detail;
		this.patient_id = patient_id;
		this.solution = solution;
		this.state = state;
		this.created_at = created_at;
		this.closed_at = closed_at;
		this.closing_comment = closing_comment;
	}

	public Incident(long patient_id, String incident_detail, String solution) {
		super();
		this.incident_detail = incident_detail;
		this.patient_id = patient_id;
		this.solution = solution;
		state = State.OPEN;
	}

	public Incident(String incident_detail, String solution, Date created_at) {
		this.incident_detail = incident_detail;
		this.solution = solution;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", patient_id=" + patient_id + ", incident_detail=" + incident_detail
				+ ", solution=" + solution + ", state=" + state + ", created_at=" + created_at + ", closed_at="
				+ closed_at + ", closing_comment=" + closing_comment + "]";
	}

	public long getId() {
		return id;
	}

	public String getIncident_detail() {
		return incident_detail;
	}

	public long getPatient_id() {
		return patient_id;
	}

	public String getSolution() {
		return solution;
	}

	public State getState() {
		return state;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public Date getClosed_at() {
		return closed_at;
	}

	public String getClosing_comment() {
		return closing_comment;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIncident_detail(String incident_detail) {
		this.incident_detail = incident_detail;
	}

	public void setPatient_id(long patient_id) {
		this.patient_id = patient_id;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setClosed_at(Date closed_at) {
		this.closed_at = closed_at;
	}

	public void setClosing_comment(String closing_comment) {
		this.closing_comment = closing_comment;
	}


}
