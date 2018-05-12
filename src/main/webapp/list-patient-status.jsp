<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
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
			<h1>Patient Status</h2>
		</div>
		<div class="col text-right">
		<a href="/cbers/logout" >Sign out</a>
		</div>
		
		</div>
		<br>
		<!--Search Form -->
		<%-- <form action="/qa_inventory/device" method="get" id="seachDeviceForm" role="form" >
			<input type="hidden" id="searchAction" name="action" value="">
			<div class="form-group">
				<div class="row">
					<div class="col">
						<select class="form-control" id="searchBy" required onchange="changeSearchBy();">
							<option value="" disabled>Search By</option>
							<option value="searchByName">Label</option>
							<option value="searchByImei">Identification</option>
							<option value="searchByOwner">Owner</option>
							<!-- <option value="searchById">ID</option> -->
							<option value="searchByType">Type</option>
							<option value="searchByActive">Active</option>
						</select>
						<script>
							function changeSearchBy() { 
								var selectedText = $('#searchBy').find("option:selected").val();
    							$('#searchAction').val(selectedText);
    							sessionStorage.setItem("SelItem", selectedText);
    							if(selectedText !== '') {
    								$("#deviceDetail").prop("readonly", false);
    							}
								};
						</script>
					</div>
					<div class="col-6">
						<input type="text" name="deviceDetail" id="deviceDetail"
							value="${fn:escapeXml(param.deviceDetail)}" class="form-control"
							required placeholder="Type the Name, IMEI or owner of the device" readonly/>
					</div>
					<div class="col">
					<button type="submit" class="btn btn-info" onclick="return verifySearch();">
						<span class="fa fa-search"></span> Search
					</button>
					<script>
						function verifySearch() {
							var selectedText = $('#searchBy').find("option:selected").val();
							console.log('Selected Text: '+selectedText);
							var searchText = $('#deviceDetail').val();
							switch (selectedText) {
							case 'searchByActive':
								if (searchText.toUpperCase() === 'YES' || searchText.toUpperCase() === 'NO'){
									$('#errorDiv').css("display", "none");
									return true;
								} else {
									console.log('Not a correct status: '+searchText.toUpperCase());
									$('#errorDiv').text('Please input YES or NO');
									$('#errorDiv').css("display", "inline");
									return false;
								}
							break;
							case 'searchByImei': case 'searchById':
								console.log('Search Text: '+searchText);
								if (isNaN(searchText)){
									console.log('Not a number: '+searchText);
									$('#errorDiv').text('Please input number.');
									$('#errorDiv').css("display", "inline");
									return false;
									}
								break;
							case 'searchByType':
								console.log('Search Text: '+searchText);
								if (searchText.toUpperCase() === 'ANDROID' || searchText.toUpperCase() === 'IOS' || 
										searchText.toUpperCase() === 'WINDOWS' || searchText.toUpperCase() === 'SIM'){
									$('#errorDiv').css("display", "none");
									return true;
								} else {
									console.log('Not a Type: '+searchText.toUpperCase());
									$('#errorDiv').text('Please enter correct type like ANDROID, IOS or SIM');
									$('#errorDiv').css("display", "inline");
									return false;
								}
								break;
							/* case 'searchByOwner':
								console.log('Owner: '+(searchText.lastIndexOf('@olacabs.com') < 0));
								if (searchText.lastIndexOf('@olacabs.com') < 0) {
									console.log('Not a owner from ola: '+searchText);
									$('#errorDiv').text('Please enter valid email of owner');
									$('#errorDiv').css("display", "inline");
									return false;
								}
								break; */
							}
								$('#errorDiv').css("display", "none");
								return true;
							}
						
					</script>
				</div>
				</div>
			</div>
			<div class="row">
				
				<div class="col-md-10">
					<div  id="errorDiv" class="alert alert-danger" role="alert" style="display: none;"></div>
				</div>
			</div>
			<br>
		</form> --%>


		<%-- <div class="row">
			<div class="col">
				<form action="/qa_inventory/device" method="get" id="goToAdd" role="form">
					<input type="hidden" id="resetAction" name="action" value="reset">
					<button class="btn btn-success" onclick="clearSearch();">
						<span class="fa fa-refresh"></span> Refresh
					</button>
					<script>
					function clearSearch() { 
						$('#searchBy').val('');
						sessionStorage.setItem("SelItem", '');
						};
					</script>
				</form>
			</div>
			<c:if test="${fn:contains(sessionScope.userRole, 'ADMIN')}">
				<div class="col-md-6 text-right">
					<form action="new-device.jsp">
						<button class="btn btn-link">
							<span class="fa fa-plus"></span> Add Device
						</button>
					</form>
				</div>
			</c:if>
		</div> --%>

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
		<!-- <form action="/qa_inventory/device" method="post" id="deviceForm" role="form">
			<input type="hidden" id="idDevice" name="deviceDetail"> 
			<input type="hidden" id="action" name="action"> -->
			<c:choose>
				<c:when test="${not empty userList}">
					<table class="table table-striped  text-center">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Email</th>
								<th>Password</th>
								<th>Phone</th>
								<th>DOB</th>
								<th>Role</th>
								<%-- <c:if test="${sessionScope.userRole == 'ADMIN'}">
									<th>Transfer</th>
								</c:if>
								<c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
									<th>Update</th>
								</c:if> --%>
							</tr>
						</thead>
						<c:forEach var="user" items="${userList}">
							<c:set var="classSucess" value="" />
							<%-- <c:if test="${userDetail == user.id}">
								<c:set var="classSucess" value="info" />
							</c:if> --%>
							<tr <%-- class="${classSucess}" --%>>
								<td><c:out value="${user.id}" /></td>
								<td><c:out value="${user.name}" /></td>
								<td><c:out value="${user.email}" /></td>
								<td><c:out value="${user.password}" /></td>
								<td><c:out value="${user.phone}" /></td>
								<td><c:out value="${user.dob}" /></td>
								<td><c:out value="${user.role}" /></td>
								<%-- <td>
									<a title="Click to see Logs for this item" href="/qa_inventory/userLog?userId=${user.id}">
										<c:out value="${user.imei}" />
									</a>
								</td>
								<td><c:out value="${user.owner}" /></td>
								<td><c:out value="${device.active}" /></td> --%>
								<%-- <c:if test="${fn:contains(sessionScope.userRole, 'ADMIN')}">
									<td> 
                                         <c:if test="${sessionScope.userRole == 'SUPER_ADMIN'}">
                                         	<a href="/qa_inventory/device?deviceDetail=${device.id}&action=searchById" title="Change any detail of this item">
												<span class="fa fa-pencil-square-o">
                                            	</span>
                                            </a>
										</c:if>
										<c:if test="${sessionScope.userRole == 'ADMIN'}">
											<a href="/qa_inventory/device?deviceDetail=${device.id}&action=searchById" title="Change owner of this item">
												<span class="fa fa-exchange">
                                            	</span>
                                        	</a>
										</c:if>
                                    </td>
                                </c:if> --%>
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