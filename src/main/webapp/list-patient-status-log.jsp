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
<title>Status Logs</title>
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
				<c:choose>
					<c:when test="${not empty patient_name}">
						<h3>${patient_name}'s Status Logs</h3>
					</c:when>
					<c:otherwise>
						<h3>Patient's Status Logs</h3>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col text-right">
				<a href="/cbers/logout">Sign out</a>
			</div>

		</div>
		<br>

		<!--Incident Message-->
		<c:if test="${sessionScope.incidentCreateError != null}">
			<c:choose>
				<c:when test="${fn:contains(incidentCreateError, 'Error')}">
					<div class="alert alert-danger">${incidentCreateError}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${incidentCreateError}</div>
				</c:otherwise>
			</c:choose>
			<c:remove var="incidentCreateError" />
		</c:if>

		<!--allPatients Status List-->
		<c:choose>
			<c:when test="${empty allPatients}">
				<br>
				<div class="alert alert-info">No Patients Status Found</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped  text-center">
					<thead class="thead-dark">
						<tr>
							<th>#</th>
							<th>Temperature</th>
							<th>HeartRate</th>
							<th>BP</th>
							<th>Sugar</th>
							<th>Updated At</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="patient" items="${allPatients}">

							<c:choose>
								<c:when test="${patient.code == 'RED'}">
									<tr class="table-danger">
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${patient.code == 'ORANGE'}">
											<tr class="table-warning">
										</c:when>
										<c:otherwise>
											<tr class="table-success">
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

							<td><c:out value="${patient.id}" /></td>
							<td><c:out value="${patient.temperature}" /></td>
							<td><c:out value="${patient.heartRate}" /></td>
							<td><c:out value="${patient.bloodPressure}" /></td>
							<td><c:out value="${patient.bloodSugar}" /></td>
							<td><c:out value="${patient.createdAt}" /></td>
							</tr>
						</c:forEach>
					</tbody>

				</table>


			</c:otherwise>
		</c:choose>

		<div class="row">
			<div class="col">
				<a class="btn btn-outline-warning  btn-md" href="#"
					onclick="history.back();return false;">Go Back</a>
			</div>

			<div class="col text-right">
				<a class="btn btn-primary  btn-md"
					href="/cbers/incident?patient_id=${patient_id}"> <span
					class="fa fa-eye"></span> View Patient History
				</a>
			</div>
		</div>

	</div>
</body>
</html>