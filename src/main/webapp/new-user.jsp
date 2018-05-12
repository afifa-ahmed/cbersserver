<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="shortcut icon" href="favicon.ico" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script type="text/javascript"
	src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />

<link rel="shortcut icon" href="favicon.ico" />
<title>Add/Edit User</title>
</head>
<body>
	<div class="container">

		<c:choose>
			<c:when test="${empty user}">
				<c:if test="${empty action}">
					<c:set var="action" value="add" />
				</c:if>
				<form action="/cbers/user" method="post" role="form"
					data-toggle="validator">
					<input type="hidden" id="action" name="action" value="add">
					<h2>Add User</h2>
					<div class="form-group row">
						<label for="name" class="col-sm-2 col-form-label">User
							Name:</label>
						<div class="col-sm-10">
							<input type="text" name="name" id="name" class="form-control"
								value="${fn:escapeXml(user.name)}" required />
						</div>
					</div>
					<div class="form-group row">
						<label for="email" class="col-sm-2 col-form-label">User
							Email Id:</label>
						<div class="col-sm-10">
							<input type="text" name="email" id="email" class="form-control"
								value="${fn:escapeXml(user.email)}" required />
						</div>
					</div>
					<div class="form-group row">
						<label for="password" class="col-sm-2 col-form-label">User
							Password:</label>
						<div class="col-sm-10">
							<input type="text" name="password" id="password"
								class="form-control" value="${fn:escapeXml(user.password)}"
								required />
						</div>
					</div>
					<div class="form-group row">
						<label for="phone" class="col-sm-2 col-form-label">User
							Mobile Number:</label>
						<div class="col-sm-10">
							<input type="text" name="phone" id="phone" class="form-control"
								value="${fn:escapeXml(user.phone)}" required />
						</div>
					</div>
					<div class="form-group row">
						<label for="role" class="col-sm-2 col-form-label">User
							Role:</label>
						<div class="col-sm-10">
							<select name="role" id="role" class="form-control" required>
								<option value="" disabled selected>Select</option>
								<option value="ADMIN">Admin</option>
								<option value="DOCTOR">Doctor</option>
								<option value="PATIENT">Patient</option>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label for="dob" class="col-sm-2 col-form-label">Date Of
							Birth:</label>
						<div class="col-sm-10">
							<div class="input-group" id="dateDiv">
								<input type="text" class="form-control" id="dob" name="dob"
									value="" placeholder="Select the DOB">
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" disabled>
										<span class="fa fa-calendar"></span>
									</button>
								</div>
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="form-group col-1">
							<button type="submit" disabled="" class="btn btn-primary  btn-md"
								onclick="return verifyData();">Save</button>
						</div>
						<div class="form-group col-1">
							<a class="btn btn-outline-danger  btn-md" href="/cbers/user">Cancel</a>
						</div>
					</div>
				</form>

			</c:when>
			<c:otherwise>
				<c:if test="${empty action}">
					<c:set var="action" value="edit" />
				</c:if>
				<form action="user" method="post" role="form"
					data-toggle="validator">
					<input type="hidden" id="action" name="action" value="edit">
					<input type="hidden" id="id" name="id" value="${fn:escapeXml(user.id)}">
					<h2>Update User</h2>

					<div class="form-group row">
						<label for="name" class="col-sm-2 col-form-label">User
							Name:</label>
						<div class="col-sm-10">
							<input type="text" name="name" id="name" class="form-control"
								value="${fn:escapeXml(user.name)}" required  readonly/>
						</div>
					</div>
					<div class="form-group row">
						<label for="email" class="col-sm-2 col-form-label">User
							Email Id:</label>
						<div class="col-sm-10">
							<input type="text" name="email" id="email" class="form-control"
								value="${fn:escapeXml(user.email)}" required readonly/>
						</div>
					</div>
					<div class="form-group row">
						<label for="role" class="col-sm-2 col-form-label">User
							Role:</label>
						<div class="col-sm-10">
						<input type="text" name="role" id="role" class="form-control"
								value="${fn:escapeXml(user.role)}" required readonly/>
						</div>
					</div>
					<div class="form-group row">
						<label for="password" class="col-sm-2 col-form-label">User
							Password:</label>
						<div class="col-sm-10">
							<input type="text" name="password" id="password"
								class="form-control" value="${fn:escapeXml(user.password)}"
								required />
						</div>
					</div>
					<div class="form-group row">
						<label for="phone" class="col-sm-2 col-form-label">User
							Mobile Number:</label>
						<div class="col-sm-10">
							<input type="text" name="phone" id="phone" class="form-control"
								value="${fn:escapeXml(user.phone)}" required />
						</div>
					</div>
					<div class="form-group row">
						<label for="dob" class="col-sm-2 col-form-label">Date Of
							Birth:</label>
						<div class="col-sm-10">
							<div class="input-group" id="dateDiv">
								<input type="text" class="form-control" id="dob" name="dob"
									value="" placeholder="Select the DOB">
								<div class="input-group-append">
									<button class="btn btn-outline-secondary" disabled>
										<span class="fa fa-calendar"></span>
									</button>
								</div>
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="form-group col-1">
							<button type="submit" disabled="" class="btn btn-primary  btn-md"
								onclick="return verifyData();">Save</button>
						</div>
						<div class="form-group col-1">
							<a class="btn btn-outline-danger  btn-md" href="/cbers/user">Cancel</a>
						</div>
					</div>


				</form>


			</c:otherwise>
		</c:choose>


		<script type="text/javascript">
			function verifyData() {
				var phone = jQuery('#phone').val();
				if (isNaN(phone)) {
					console.log('Not a number: ' + phone);
					jQuery('#errorDiv').text('Please enter a number for phone');
					jQuery('#errorDiv').css("display", "inline");
					return false;
				}

				jQuery('#errorDiv').css("display", "none");
			}
		</script>
		<script>
			jQuery('form').each(function() {
				jQuery(this).data('serialized', jQuery(this).serialize())
			}).on(
					'change input',
					function() {
						jQuery(this).find('input:submit, button:submit').prop(
								'disabled',
								jQuery(this).serialize() == jQuery(this).data(
										'serialized'));
					}).find('input:submit, button:submit').prop('disabled',
					true);
		</script>

		<script type="text/javascript">
			jQuery(function() {
				jQuery('input[name="dob"]').daterangepicker({
					singleDatePicker : true,
					showDropdowns : true,
					minYear : 1901,
					maxYear : parseInt(moment().format('YYYY'), 10),
					locale : {
						cancelLabel : 'Clear',
						format : 'YYYY-MM-DD HH:mm:ss'
					}
				});

				jQuery('input[name="dob"]').on(
						'apply.daterangepicker',
						function(ev, picker) {
							jQuery(this).val(
									picker.startDate
											.format('YYYY-MM-DD HH:mm:ss'));
						});

				jQuery('input[name="dob"]').on('cancel.daterangepicker',
						function(ev, picker) {
							jQuery(this).val('');
						});

			});
		</script>

		<div>
			<div id="errorDiv" class="alert alert-danger" role="alert"
				style="display: none;"></div>
		</div>
	</div>
</body>
</html>