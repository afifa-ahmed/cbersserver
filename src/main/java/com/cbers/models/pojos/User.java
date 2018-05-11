package com.cbers.models.pojos;

import java.util.Date;

import com.cbers.models.enums.Role;

public class User {

	private long id;
	private String name;
	private String email;
	private String password;
	private long phone;
	private Date dob;
	private Role role;
	
	public User(long id, String name, String email, String password, long phone, Date dob, Role role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.dob = dob;
		this.role = role;
	}
	
	public User(String name, String email, String password, long phone, Date dob, Role role) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.dob = dob;
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone
				+ ", dob=" + dob + ", role=" + role + "]";
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public long getPhone() {
		return phone;
	}

	public Date getDob() {
		return dob;
	}

	public Role getRole() {
		return role;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
