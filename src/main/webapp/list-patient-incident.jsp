<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link href="css/font-awesome.min.css" rel="stylesheet" />
<script src="js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="favicon.ico" />
<title>Incident History</title>
</head>

<body>

	<div class="container">

		<div class="row align-items-center">
			<div class="col">
				<a href="/cbers/patientStatus"> <img class="img-fluid"
					src="HeartCBERS.png" />
				</a>
			</div>
			<div class="col-8">
				<h2>Patient History</h2>
			</div>
			<div class="col text-right">
				<a href="/cbers/logout">Sign out</a>
			</div>

		</div>
		<br>

		<!--IncidentLog List-->
		<c:choose>
			<c:when test="${not empty patientIncidentList}">
				<table class="table table-striped  text-center">
					<thead>
						<tr>
							<th>Incident Id</th>
							<th>Incident Detail</th>
							<th>Advice given</th>
							<th>Started On</th>
							<th>Closed On</th>
							<th>Closing Comment</th>
						</tr>
					</thead>
					<c:forEach var="incident" items="${patientIncidentList}">
						<tr>
							<td><c:out value="${incident.id}" /></td>
							<td><c:out value="${incident.incident_detail}" /></td>
							<td><c:out value="${incident.solution}" /></td>
							<td><c:out value="${incident.created_at}" /></td>
							<td><c:out value="${incident.closed_at}" /></td>
							<td><c:out value="${incident.closing_comment}" /></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<br>
				<div class="alert alert-info">No Advice Found</div>
			</c:otherwise>
		</c:choose>

		<div class="row">
			<div class="col">
				<a class="btn btn-outline-warning  btn-md" href="#"
					onclick="history.back();return false;">Go Back</a>
			</div>
		</div>

	</div>
</body>
</html>