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
<title>User List</title>
</head>

<body>

	<div class="container">

		<div class="row align-items-center">
			<div class="col">
				<img class="img-fluid" src="HeartCBERS.png" />
			</div>
			<div class="col-8">
				<h2>Patient Status</h2>
			</div>
			<div class="col text-right">
				<a href="/cbers/logout">Sign out</a>
			</div>

		</div>
		<br>

		<!--Incident Message-->
		<c:if test="${not empty message}">
			<c:choose>
				<c:when test="${fn:contains(message, 'Error')}">
					<div class="alert alert-danger">${message}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${message}</div>
				</c:otherwise>
			</c:choose>
		</c:if>

		<!--allPatients Status List-->
		<c:choose>
			<c:when test="${empty allPatients}">
				<br>
				<div class="alert alert-info">No Patients Found</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped  text-center">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Age</th>
							<th>Temperature</th>
							<th>HeartRate</th>
							<th>BP</th>
							<th>Sugar</th>
							<th>Updated At</th>
							<th>Advice</th>
						</tr>
					</thead>

					<!--redPatients List-->
					<c:choose>
						<c:when test="${not empty redPatients}">
							<tbody class="table-danger">
								<c:forEach var="patient" items="${redPatients}">
									<tr>
										<td><c:out value="${patient.id}" /></td>
										<td><c:out value="${patient.name}" /></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident();" href=#> <span
														class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incident?patient_id=${patient.id}"
														title="View Incident History"> <span
														class="fa fa-pencil-square-o"> </span>
													</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>

					<!--orangePatients List-->
					<c:choose>
						<c:when test="${not empty orangePatients}">
							<tbody class="table-warning">
								<c:forEach var="patient" items="${orangePatients}">
									<tr>
										<td><c:out value="${patient.id}" /></td>
										<td><c:out value="${patient.name}" /></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident();" href=#> <span
														class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incident?patient_id=${patient.id}"
														title="View Incident History"> <span
														class="fa fa-pencil-square-o"> </span>
													</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>

					<!--greenPatients List-->
					<c:choose>
						<c:when test="${not empty greenPatients}">
							<tbody class="table-success">
								<c:forEach var="patient" items="${greenPatients}">
									<tr>
										<td><c:out value="${patient.id}" /></td>
										<td><c:out value="${patient.name}" /></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident();" href=#> <span
														class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incident?patient_id=${patient.id}"
														title="View Incident History"> <span
														class="fa fa-pencil-square-o"> </span>
													</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>

				</table>


			</c:otherwise>
		</c:choose>

	</div>
	<script>
		function openCreateIncident() {
			var form = $('<form action="create-incident.jsp"> </form>');
			$('body').append(form);
			form.submit();
		}
	</script>


</body>
</html>