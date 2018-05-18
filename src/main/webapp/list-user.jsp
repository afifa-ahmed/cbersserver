<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script> -->
<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link href="css/font-awesome.min.css" rel="stylesheet"/>
<script src="js/bootstrap.min.js"></script>
     <link rel="shortcut icon" href="favicon.ico" />        
     <title>User List</title>
</head>

<body>

	<div class="container">
	
		<div class="row align-items-center">
		<div class="col">
			<img class="img-fluid" src="HeartCBERS.png" />
		</div>
		<div class="col-8">
			<h2>Users</h2>
		</div>
		<div class="col text-right">
		<a href="/cbers/logout" >Sign out</a>
		</div>
		
		</div>
		<br>
		
		<div class="row">
			<div class="col">
				<div class="dropdown">
  					<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    					Filter User
  					</button>
  					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
  						<a class="dropdown-item" href="/cbers/user">All</a>
    					<a class="dropdown-item" href="/cbers/user?role=ADMIN">Admins</a>
    					<a class="dropdown-item" href="/cbers/user?role=DOCTOR">Doctors</a>
    					<a class="dropdown-item" href="/cbers/user?role=PATIENT">Patients</a>
  					</div>
				</div>
			</div>
			<div class="col">
			</div>
				<div class="col-md-6 text-right">
					<form action="new-user.jsp">
						<button class="btn btn-link">
							<span class="fa fa-plus"></span> Add New User
						</button>
					</form>
				</div>
		</div>

		<!--Devices List-->
		<c:if test="${not empty message}">
			<c:choose>
				<c:when  test="${fn:contains(message, 'Error')}">
					<div class="alert alert-danger">${message}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${message}</div>
				</c:otherwise>
			</c:choose>
		</c:if>
			<c:choose>
				<c:when test="${not empty userList}">
					<table class="table table-striped  text-center">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Email</th>
								<th>Phone</th>
								<th>DOB</th>
								<th>Role</th>
								<th>Update</th>
							</tr>
						</thead>
						<c:forEach var="user" items="${userList}">
							<tr>
								<td><c:out value="${user.id}" /></td>
								<td><c:out value="${user.name}" /></td>
								<td><c:out value="${user.email}" /></td>
								<td><c:out value="${user.phone}" /></td>
								<td><c:out value="${user.dob}" /></td>
								<td><c:out value="${user.role}" /></td>
								<td> 
                                     <a href="/cbers/user?email=${user.email}" title="Update detail of this user">
										<span class="fa fa-pencil-square-o">
                                        </span>
                                      </a>
                                </td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-info">No Users Found</div>
				</c:otherwise>
			</c:choose>
		<!-- </form> -->
	</div>
</body>
</html>