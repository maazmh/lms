<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<title>Approval</title>
<script type="text/javascript">
function approve(leaveId, empName, from, to, leaveType) {
	document.getElementById('spanAction').innerHTML = 'Approve';
	document.getElementById('spanEmpName').innerHTML = empName;
	document.getElementById('spanDtFrom').innerHTML = from;
	document.getElementById('spanDtTo').innerHTML = to;
	document.getElementById('spanLeaveType').innerHTML = leaveType;
	
	document.getElementById('leaveId').value = leaveId;
	document.getElementById('selectedAction').value = 'A'; //A as in Approve
}

function reject(leaveId, empName, from, to, leaveType) {
	document.getElementById('spanAction').innerHTML = 'Reject';
	document.getElementById('spanEmpName').innerHTML = empName;
	document.getElementById('spanDtFrom').innerHTML = from;
	document.getElementById('spanDtTo').innerHTML = to;
	document.getElementById('spanLeaveType').innerHTML = leaveType;
	
	document.getElementById('leaveId').value = leaveId;
	document.getElementById('selectedAction').value = 'R'; //R as in Reject
}

function saveChanges() {
	var leaveId = document.getElementById('leaveId').value;
	var selectAction = document.getElementById('selectedAction').value;
	document.getElementById('note').value = document.getElementById('notes').value;
	
	var form = document.getElementById('approvalForm');
	if(selectAction=='A') {
		form.submit();
	} else if(selectAction=='R') {
		form.setAttribute("action", 'reject');
		form.submit();
	} 
}

function setupNavBar() {
	$('#navBarLiDashboard').removeClass('active');
	$('#navBarLiApproval').addClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').removeClass('active');
	$('#navBarEmpName').html($('#empNameFromSession').val());
}
</script>
</head>
<body onload="setupNavBar();">
	<%@ include file="navbar.html" %>
	<input type="hidden" id='empNameFromSession' value="<%= session.getAttribute("employeeName") %>">
	<div class="container" style="padding-top: 5em;">
		<form:form method="post" action="approve" modelAttribute="approvalForm" commandName="approvalForm" cssClass="form-signin">
			<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
		        <a href="#" class="close" data-dismiss="alert">&times;</a>
		        <strong>Error!</strong>
		        <span id="jsErrorMessage"></span>
		    </div>
			<p id="errorMsgP" class="bg-danger">${errorMessage}</p>
			<p class="bg-success">${successMessage}</p>
			<form:hidden path="leaveId"/>
			<form:hidden path="note"/>
			<input type="hidden" id="selectedAction"/>
			
			<table class="table table-hover">
				<thead>
					<tr>
						<td><b>Name</b></td>
						<td><b>From</b></td>
						<td><b>To</b></td>
						<td><b>Leave Type</b></td>
						<td><b>Leave Description</b></td>
						<td><b>Applied On</b></td>
						<td><b>Action</b></td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty approvalForm.leaves}">
						<c:forEach var="leave" items="${approvalForm.leaves}">
							<tr>
								<td>${leave.employeeName}</td>
								<td>${leave.dtFrom}</td>
								<td>${leave.dtTo}</td>
								<td>${leave.leaveType}</td>
								<td>${leave.leaveDescription}</td>
								<td>${leave.dtAppliedOn}</td>
								<td>
									<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" 
										onclick="approve(${leave.idLeave},'${leave.employeeName}','${leave.dtFrom}','${leave.dtTo}','${leave.leaveType}');">
											Approve
									</button>&nbsp;
									<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal" 
										onclick="reject(${leave.idLeave},'${leave.employeeName}','${leave.dtFrom}','${leave.dtTo}','${leave.leaveType}');">
										Reject
									</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</form:form>
	</div>
	
	<!-- Confirmation Modal Dialog -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">Confirmation</h4>
	      </div>
	      <div class="modal-body">
	        <p>Click Save Changes to <b><span id="spanAction"></span></b> <b><span id="spanLeaveType"></span></b> Leave for <b><span id="spanEmpName"></span></b> for Dates: <b><span id="spanDtFrom"></span></b> to <b><span id="spanDtTo"></span></b></p>
	      	<br>
	      	<div class="form-group">
	            <label for="message-text" class="control-label">Note:</label>
	            <textarea class="form-control" id="notes"></textarea>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onclick="saveChanges();">Save changes</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>