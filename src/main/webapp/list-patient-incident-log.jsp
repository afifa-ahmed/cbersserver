<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script> -->
	<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link href="css/font-awesome.min.css" rel="stylesheet" />
<script src="js/bootstrap.min.js"></script>
<link rel="shortcut icon" href="favicon.ico" />
<title>Advice Logs</title>
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
				<h2>Incident History</h2>
			</div>
			<div class="col text-right">
				<a href="/cbers/logout">Sign out</a>
			</div>

		</div>
		<br>

		<!--IncidentLog List-->
		<c:choose>
			<c:when test="${not empty incidentLogs}">
				<table class="table table-striped  text-center">
					<thead>
						<tr>
							<th>#</th>
							<th>Incident/Query</th>
							<th>Advice given</th>
							<th>Date</th>
						</tr>
					</thead>
					<c:forEach var="incident" items="${incidentLogs}">
						<tr>
							<td><c:out value="${incident.id}" /></td>
							<td><c:out value="${incident.incident_detail}" /></td>
							<td><c:choose>
									<c:when test="${not empty incident.solution}">
										<c:out value="${incident.solution}" />
									</c:when>
									<c:otherwise>
										<c:set var="open_query" value="TRUE" scope="page" />
										<a href="#" title="Answer Patient's Query"
											onclick="openReplyQuery('${incident_id}','${incident.incident_log_id}');"
											href=#><span class="fa fa-reply"> </span> Reply </a>
									</c:otherwise>
								</c:choose></td>
							<td><c:out value="${incident.created_at}" /></td>
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
				<a class="btn btn-outline-warning  btn-md"
					href="/cbers/patientStatus"><span
					class="fa
					 fa-arrow-alt-left"></span>Go Back</a>
			</div>
			<div class="col">
				<a class="btn btn-primary  btn-md"
					href="/cbers/incident?incident_id=${incident_id}"> <span
					class="fa fa-eye"></span> View Patient History
				</a>
			</div>
			<div class="col text-right">
				<c:choose>
					<c:when test="${empty pageScope.open_query}">
						<a class="btn btn-info  btn-md"
							onclick="openUpdateIncident('${incident_id}');" href=#> <span
							class="fa fa-pencil-square-o"></span> Update Advice
						</a>
						<a class="btn btn-success  btn-md"
							onclick="openCloseIncident('${incident_id}');" href=#> <span
							class="fa fa-times"></span> Close Incident
						</a>
					</c:when>
					<c:otherwise>
						<button class="btn btn-info  btn-md"
							title="Please reply the query first." disabled>
							<span class="fa fa-pencil-square-o"></span> Update Advice
						</button>
						<button class="btn btn-success  btn-md"
							title="Please reply the query first." disabled>
							<span class="fa fa-times"></span> Close Incident
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<br>


		<!--IncidentUpdate Message-->
		<c:if test="${sessionScope.incidentUpdateError != null}">
			<c:choose>
				<c:when test="${fn:contains(incidentUpdateError, 'Error')}">
					<div class="alert alert-danger">${incidentUpdateError}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${incidentUpdateError}</div>
				</c:otherwise>
			</c:choose>
			<c:remove var="incidentUpdateError" />
		</c:if>

		<!-- script Update -->
		<script>
			function openUpdateIncident(id) {
				console.log('Id: ' + id);
				var inputs = $('<input type="hidden" name="incident_id" value="'+id+'">');
				$('#updateIncidentForm').append(inputs);
				jQuery('#exampleModal').modal('show');
			}
		</script>

		<!-- Modal Update -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Update Advice</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form action="/cbers/incident" method="post" role="form"
							id="updateIncidentForm" data-toggle="validator">
							<input type="hidden" id="action" name="action" value="update">
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
						<div class="text-right">
							<div id="errorDivUpdate" class="alert alert-danger" role="alert"
								style="display: none;"></div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary" data-dismiss="modal">Close</button>
						<a href="#" id="submitModal" class="btn btn-success success">Submit</a>
					</div>
				</div>
			</div>

			<script type="text/javascript">
				jQuery('#submitModal').on(
						'click',
						function(e) {
							e.preventDefault();

							console.log('Submit clicked, verifying modal');
							var incident = jQuery('#incident').val();
							if (!incident || '' == incident) {
								console.log('incident: ' + incident);
								jQuery('#errorDivUpdate').text(
										'Please enter incident details');
								jQuery('#errorDivUpdate').css("display",
										"inline");
								return false;
							}
							var solution = jQuery('#solution').val();
							if (!solution || '' == solution) {
								console.log('solution: ' + solution);
								jQuery('#errorDivUpdate').text(
										'Please enter solution details');
								jQuery('#errorDivUpdate').css("display",
										"inline");
								return false;
							}

							jQuery('#errorDivUpdate').css("display", "none");
							/* when the submit button in the modal is clicked, submit the form */
							jQuery('#updateIncidentForm').submit();
						});
			</script>
		</div>

		<!--IncidentClose Message-->
		<c:if test="${sessionScope.incidentCloseError != null}">
			<c:choose>
				<c:when test="${fn:contains(incidentCloseError, 'Error')}">
					<div class="alert alert-danger">${incidentCloseError}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${incidentCloseError}</div>
				</c:otherwise>
			</c:choose>
			<c:remove var="incidentCloseError" />
		</c:if>

		<!-- script Close -->
		<script>
			function openCloseIncident(id) {
				console.log('Id: ' + id);
				var inputs = $('<input type="hidden" name="incident_id" value="'+id+'">');
				$('#closeIncidentForm').append(inputs);
				jQuery('#modalClose').modal('show');
			}
		</script>

		<!-- Modal Close -->
		<div class="modal fade" id="modalClose" tabindex="-1" role="dialog"
			aria-labelledby="closeModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="closeModalLabel">Close Incident</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form action="/cbers/incident" method="post" role="form"
							id="closeIncidentForm" data-toggle="validator">
							<input type="hidden" id="action" name="action" value="close">
							<div class="form-group row">
								<label for="closing_comment" class="col-sm-3 col-form-label">
									Observation:</label>
								<div class="col-sm-8">
									<input type="text" name="closing_comment" id="closing_comment"
										class="form-control" required />
								</div>
							</div>
						</form>
						<div class="text-right">
							<div id="errorDivClose" class="alert alert-danger" role="alert"
								style="display: none;"></div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary" data-dismiss="modal">Close</button>
						<a href="#" id="submitCloseModal" class="btn btn-success success">Submit</a>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				jQuery('#submitCloseModal').on(
						'click',
						function(e) {
							e.preventDefault();

							console.log('Submit clicked, verifying modal');
							var closing_comment = jQuery('#closing_comment')
									.val();
							if (!closing_comment || '' == closing_comment) {
								console.log('closing_comment: '
										+ closing_comment);
								jQuery('#errorDivClose').text(
										'Please enter closing comments.');
								jQuery('#errorDivClose').css("display",
										"inline");
								return false;
							}

							jQuery('#errorDivClose').css("display", "none");
							/* when the submit button in the modal is clicked, submit the form */
							jQuery('#closeIncidentForm').submit();
						});
			</script>
		</div>


		<!--QueryReply Message-->
		<c:if test="${sessionScope.queryReplyError != null}">
			<c:choose>
				<c:when test="${fn:contains(queryReplyError, 'Error')}">
					<div class="alert alert-danger">${queryReplyError}</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-success">${queryReplyError}</div>
				</c:otherwise>
			</c:choose>
			<c:remove var="queryReplyError" />
		</c:if>

		<!-- script QueryReply -->
		<script>
			function openReplyQuery(id, logId) {
				console.log('Id: ' + id);
				console.log('logId: ' + logId);
				var inputs = $('<input type="hidden" name="incident_id" value="'+id+'"><input type="hidden" name="incident_log_id" value="'+logId+'">');
				$('#queryReplyForm').append(inputs);
				jQuery('#modalQueryReply').modal('show');
			}
		</script>

		<!-- Modal Close -->
		<div class="modal fade" id="modalQueryReply" tabindex="-1"
			role="dialog" aria-labelledby="queryReplyModalLabel"
			aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="queryReplyModalLabel">Reply to
							query</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form action="/cbers/incident" method="post" role="form"
							id="queryReplyForm" data-toggle="validator">
							<input type="hidden" id="action" name="action" value="reply">
							<div class="form-group row">
								<label for="query_reply" class="col-sm-3 col-form-label">
									Reply:</label>
								<div class="col-sm-8">
									<input type="text" name="query_reply" id="query_reply"
										class="form-control" required />
								</div>
							</div>
						</form>
						<div class="text-right">
							<div id="errorDivQueryReply" class="alert alert-danger"
								role="alert" style="display: none;"></div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary" data-dismiss="modal">Close</button>
						<a href="#" id="submitQueryReplyModal"
							class="btn btn-success success">Submit</a>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				jQuery('#submitQueryReplyModal').on(
						'click',
						function(e) {
							e.preventDefault();

							console.log('Submit clicked, verifying modal');
							var query_reply = jQuery('#query_reply').val();
							if (!query_reply || '' == query_reply) {
								console.log('query_reply: ' + query_reply);
								jQuery('#errorDivQueryReply').text(
										'Please enter your reply.');
								jQuery('#errorDivQueryReply').css("display",
										"inline");
								return false;
							}

							jQuery('#errorDivQueryReply')
									.css("display", "none");
							/* when the submit button in the modal is clicked, submit the form */
							jQuery('#queryReplyForm').submit();
						});
			</script>
		</div>


	</div>
</body>
</html>