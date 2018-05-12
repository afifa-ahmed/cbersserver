package com.cbers.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbers.models.UserModel;
import com.cbers.models.enums.Role;
import com.cbers.models.pojos.User;
import com.cbers.utils.Util;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;



@WebServlet(
		name = "UserServlet",
		urlPatterns = {"/user"}
		)
public class UserServlet extends CbersServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6242852158491002057L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request > ["+req+"], Session > ["+req.getSession(false)+"]");
		if (req.getSession(false) == null || req.getSession(false).getAttribute("userName") == null) {
			System.out.println("User not logged in, redirect to login.");
			goToLogin(req, resp);
			return;
		} 

		if (req.getSession(false).getAttribute("userRole") == null || 
				!req.getSession(false).getAttribute("userRole").equals(Role.ADMIN.toString())) {
			System.out.println("User not an admin, redirect to login.");
			unAuthorizedAccess(req, resp);
			return;
		}

		loadUsers(req, resp);
	}

	private void loadUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		if (email == null) {
			String role = req.getParameter("role");
			List<User> result = new ArrayList<>();
			if (role == null || "all".equalsIgnoreCase(role)) {
				result = UserModel.getAllUsers();

			} else {
				result = UserModel.getUsersByRole(Role.valueOf(role));
			}
			System.out.println("Loading Users...");
			forwardListUsers(req, resp, result);
		} else {
			loadUserEdit(req, resp, email);
		}
	}

	private void loadUserEdit(HttpServletRequest req, HttpServletResponse resp, String email)
			throws ServletException, IOException {
		User user = null;
		try {
			user = UserModel.getUser(email);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		req.setAttribute("user", user);
		req.setAttribute("action", "edit");
		String nextJSP = "/new-user.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Request > ["+req+"], Session > ["+req.getSession(false)+"]");
		if (req.getSession(false) == null || req.getSession(false).getAttribute("userName") == null) {
			System.out.println("User not logged in, redirect to login.");
			goToLogin(req, resp);
			return;
		}

		if (req.getSession(false).getAttribute("userRole") == null || 
				!req.getSession(false).getAttribute("userRole").equals(Role.ADMIN.toString())) {
			System.out.println("User not an admin, redirect to login.");
			unAuthorizedAccess(req, resp);
			return;
		}

		String action = req.getParameter("action");
		if (!"add".equalsIgnoreCase(action) && !"edit".equals(action)) {
			resp.setStatus(400);
			return;
		}
		switch (action) {
		case "add":
			addUserAction(req, resp);
			break;
		case "edit":
			editUserAction(req, resp);
			break;            
		}

	}

	private void forwardListUsers(HttpServletRequest req, HttpServletResponse resp, List<User> userList)
			throws ServletException, IOException {
		String nextJSP = "/list-user.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		req.setAttribute("userList", userList);
		System.out.println("Forwarding Users..."+userList);
		dispatcher.forward(req, resp);
	}

	private void addUserAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		long phone = Long.parseLong(req.getParameter("phone"));
		Date dob = Util.getDateFromDbString(req.getParameter("dob"));
		Role role = Role.valueOf(req.getParameter("role").toUpperCase());

		User user = new User(name, email, password, phone, dob, role);
		long idUser = 0;
		String message = null;
		try {
			idUser = UserModel.addUser(user);
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			message = "Error: This email already exists.";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<User> UserList = UserModel.getAllUsers();
		if (message == null && idUser > 0 ) {
			message = "The new User has been successfully created.";
		} else if (message == null) {
			message = "Error: User not created!!";
		}
		req.setAttribute("idUser", idUser);
		req.setAttribute("message", message);
		forwardListUsers(req, resp, UserList);
	}

	private void editUserAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String password = req.getParameter("password");
		long phone = Long.parseLong(req.getParameter("phone"));
		Date dob = Util.getDateFromDbString(req.getParameter("dob"));
		long id = Long.parseLong(req.getParameter("id"));

		boolean success = false;
		try {
			success = UserModel.editUser(id, password, phone, dob);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = null;
		if (success) {
			message = "The User has been successfully updated.";
		} else {
			message = "Error: User not updated!!";
		}
		List<User> UserList = UserModel.getAllUsers();
		req.setAttribute("idUser", id);
		req.setAttribute("message", message);
		forwardListUsers(req, resp, UserList);
	} 
}