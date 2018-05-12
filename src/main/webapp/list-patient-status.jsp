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
				<div class="alert alert-info">No Patients Found</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped  text-center">
					<thead class="thead-dark">
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
										<td><a
											href="/cbers/patientStatusLog?patient_id=${patient.id}"
											title="View Patient Logs">${patient.name}</a></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident('${patient.id}');" href=#>
														<span class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incidentLog?patient_id=${patient.id}"
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
										<td><a
											href="/cbers/patientStatusLog?patient_id=${patient.id}"
											title="View Patient Logs">${patient.name}</a></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident('${patient.id}');" href=#>
														<span class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incidentLog?patient_id=${patient.id}"
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
										<td><a
											href="/cbers/patientStatusLog?patient_id=${patient.id}"
											title="View Patient Logs">${patient.name}</a></td>
										<td><c:out value="${patient.age}" /></td>
										<td><c:out value="${patient.temperature}" /></td>
										<td><c:out value="${patient.heartRate}" /></td>
										<td><c:out value="${patient.bloodPressure}" /></td>
										<td><c:out value="${patient.bloodSugar}" /></td>
										<td><c:out value="${patient.createdAt}" /></td>
										<td><c:choose>
												<c:when test="${empty patient.state}">
													<a onclick="openCreateIncident('${patient.id}');" href=#>
														<span class="fa fa-plus"></span> Create
													</a>
												</c:when>
												<c:otherwise>
													<a href="/cbers/incidentLog?patient_id=${patient.id}"
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
		function openCreateIncident(id) {
			console.log('Id: ' + id);

			var inputs = $('<input type="hidden" name="patient_id" value="'+id+'">');
			$('#createIncidentForm').append(inputs);
			jQuery('#exampleModal').modal('show');
		}
	</script>

	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Advice</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="/cbers/incident" method="post" role="form"
						id="createIncidentForm" data-toggle="validator">
						<input type="hidden" id="action" name="action" value="create">
						<div class="form-group row">
							<label for="incident" class="col-sm-2 col-form-label">
								Incident:</label>
							<div class="col-sm-10">
								<input type="text" name="incident" id="incident"
									class="form-control" required />
							</div>
						</div>
						<div class="form-group row">
							<label for="solution" class="col-sm-2 col-form-label">
								Solution:</label>
							<div class="col-sm-10">
								<input type="text" name="solution" id="solution"
									class="form-control" required />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" data-dismiss="modal">Close</button>
					<a href="#" id="submitModal" class="btn btn-success success">Submit</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			jQuery('#submitModal').on('click', function(e) {
				e.preventDefault();
				/* when the submit button in the modal is clicked, submit the form */
				jQuery('#createIncidentForm').submit();
			});
		</script>
	</div>


</body>
</html>