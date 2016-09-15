<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">
<link rel="stylesheet"	href="/lms/resources/css/bootstrap-year-calendar.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
<script	src="/lms/resources/js/bootstrap-year-calendar.js"></script>
<script	src="/lms/resources/js/bootstrap-year-calendar-custom-code.js"></script>
<script	src="http://www.bootstrap-year-calendar.com/js/bootstrap-popover.js"></script>
<script	src="http://www.bootstrap-year-calendar.com/js/respond.min.js"></script>
<script	src="/lms/resources/js/date-util.js"></script>

<script type="text/javascript">
function getData() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "${home}api/getLeaves/"+$("#employeeId").val()+"/"+$("#companyAccountId").val(),
		//data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			console.log("DONE");
			enableSearchButton(true);
		}
	});
	
	function enableSearchButton(flag) {
		alert('enableSearchButton: '+flag);
	}

	function display(data) {
		//alert('display: '+data);
//		var jsonData = JSON.stringify(data);
//		alert('json.stringify: '+ jsonData);

		var dataSource = $('#calendar').data('calendar').getDataSource();
//     	alert(Object.keys(dataSource).length);
//     	for(var i in dataSource) {
//     		alert('dataSource['+i+']: '+dataSource[i]);
//     	}
    	
		var x=0;
	    for(var i in data) {
	    	++x;
	    	var obj = data[i];
//	    	alert(JSON.stringify(obj));
//	    	alert('dataSource['+i+']: '+dataSource[i])
	    	for(var j in obj) {
	    		if(j=='name') {
	    			dataSource[x].name = obj[j];
	    		} else if(j=='location') {
	    			dataSource[x].location = obj[j];
	    		} else if(j=='startDate') {
	    			var startDate = obj[j];
	    			var arrStartDate = startDate.split('-');
					dataSource[x].startDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2]);
	    		} else if(j=='endDate') {
	    			var endDate = obj[j];
	    			var arrEndDate = endDate.split('-');
	    			dataSource[x].endDate = new Date(arrEndDate[0], arrEndDate[1]-1, arrEndDate[2]);
	    		}
	    	}
	    }
	    $('#calendar').data('calendar').setDataSource(dataSource);
	}
}

function validate() {
	if($('#dtFrom').val()=='' || $('#dtTo').val()=='') {
		var errMsg = '<ul><li>Dates cannot be empty</li></ul>';	
		$('#errorMsgModal').html(errMsg);
		return false;
	} else {
		var strFrom = $('#dtFrom').val();
		var strTo = $('#dtTo').val();
		
		$('#spanDtFrom').html($('#dtFrom').val());
		$('#spanDtTo').html($('#dtTo').val());
		$('#spanLeaveType').html($('#leaveType option:selected').text());
		$('#spanAppliedDays').html($('#dtTo').val());
		$('#spanRemainingLeaves').html($('#leaveType').val());
		
		var dtToArray = $('#dtTo').val().split('/');
		var dtToMonth = dtToArray[0];
		var dtToDate = dtToArray[1];
		var dtToYear = dtToArray[2];
		$('#dtTo').val(dtToDate + '-' + dtToMonth + '-' + dtToYear);
		
		var dtFromArray = $('#dtFrom').val().split('/');
		var dtFromMonth = dtFromArray[0];
		var dtFromDate = dtFromArray[1];
		var dtFromYear = dtFromArray[2];
		$('#dtFrom').val(dtFromDate + '-' + dtFromMonth + '-' + dtFromYear);
		
		$('#event-modal').modal('hide');
		$('#myModal').modal('show');
		return true;
	}
}

function saveLeaves() {
	var form = document.getElementById('leavesForm');
	form.setAttribute("method", 'post');
    form.setAttribute("action", 'saveLeave');
	form.submit();
}

function cancelModal() {
	document.getElementById("divErrorMessage").style.display="none";
	document.getElementById("divConfirmationMessage").style.display="none";
	document.getElementById("myModalLabel").innerHTML = "Confirmation";
}

function setupNavBar() {
	$('#navBarLiCalendar').addClass('active');
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').removeClass('active');
}

function init() {
	//Bind yearChanged event and set the 'year' hidden variable to the changed year from calendar.
	$('#calendar').bind('yearChanged', function(e){ 
		
		$("#year").val($('#calendar').data('calendar').getYear());
		
		var form = document.getElementById('leavesForm');
		form.setAttribute("method", 'get');
	    form.setAttribute("action", 'calendar');
		form.submit();
	});
	
	//Setup Menu/NavBar
	setupNavBar();
	
	//alert($('#calendar').data('calendar').getYear());
	
	//Get data to render the calendar
	//$('#calendar').data('calendar').setYear($("#year").val());
	//$('#calendar').calendar({startYear:$("#year").val()});
	getData();
}
</script>
	
<title>Calendar</title>
</head>
<body onload="init();">
	<%@ include file="navbar.html" %>
	<div class="container" style="padding-top: 5em;">
		<form:form method="post" id="dummyForm" action="saveLeave" modelAttribute="leavesForm" commandName="leavesForm" cssClass="form-signin">
			<table width="100%">
				<tr>
					<td>
						<label>Employee Name</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.employeeName}"/></p>
					</td>
					<td>
						<label>Department</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.department}"/></p>
					</td>
					<td>
						<label>Leaves Allocated</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.leavesAllocated}"/></p>
					</td>
					<td>
						<label>Leaves Carried Forward</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.carriedForwardLeaves}"/></p>
					</td>
				</tr>
				<tr>
					<td>
						<label>Vacation Leaves</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.leavesUsed}"/></p>
					</td>
					<td>
						<label>Sick Leaves</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.sickLeavesUsed}"/></p>
					</td>
					<td>
						<label>Unpaid Leaves</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.unpaidLeavesUsed}"/></p>
					</td>
					<td>
						<label>Leaves Remaining</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.leavesRemaining}"/></p>
					</td>
				</tr>
				<tr>
					<td>
						<label>Leaves Pending Approval</label>
					</td>
					<td>
						<p class="form-control-static"><c:out value="${leavesForm.leavesPendingApproval}"/></p>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</form:form>
		<div id="calendar" class="calendar" data-provide="calendar"></div>
		<hr>
		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<td><b>Name</b></td>
					<td><b>Leave Type</b></td>
					<td><b>Leave Note</b></td>
					<td><b>From</b></td>
					<td><b>To</b></td>
					<td><b>Applied On</b></td>
					<td><b>Approved</b></td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty leavesForm.lstLeaves}">
					<c:forEach var="leave" items="${leavesForm.lstLeaves}">
						<tr id='idTrTable${leave.idLeave}'>
						<c:if test="${leave.isApproved == true}">
							<script>$("#idTrTable${leave.idLeave}").addClass('success');</script>
						</c:if>
						<c:if test="${leave.isApproved == false}">
							<script>$("#idTrTable${leave.idLeave}").addClass('danger');</script>
						</c:if>
							<td>${leave.employeeName}</td>
							<td>${leave.leaveType}</td>
							<td>${leave.leaveDescription}</td>
							<td>${leave.dtFrom}</td>
							<td>${leave.dtTo}</td>
							<td>${leave.dtAppliedOn}</td>
							<c:if test="${leave.isApproved == true}">
								<td>Approved</td>
							</c:if>
							<c:if test="${leave.isApproved == false}">
								<td>Rejected</td>
							</c:if>
							<c:if test="${empty leave.isApproved}">
								<td>Pending Approval</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<!-- Button trigger modal -->
<!-- 	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"> -->
<!-- 	  Launch demo modal -->
<!-- 	</button> -->
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Confirmation</h4>
	      </div>
	      <div class="modal-body">
	      	<div id="divConfirmationMessage">
		        Click OK to confirm leaves. <br>
		        Date From: <b><span id="spanDtFrom"></span></b><br>
		        Date To: <b><span id="spanDtTo"></span></b><br>
		        Leave Type: <b><span id="spanLeaveType"></span></b><br>
		        Applied Days: <b><span id="spanAppliedDays"></span></b><br>
		        Remaining Leaves after Approval: <b><span id="spanRemainingLeaves"></span></b>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="btnSaveChanges" type="button" class="btn btn-primary" onclick="saveLeaves();">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="event-modal" tabindex="-1" role="dialog" aria-labelledby="eventModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="eventModalLabel">Confirmation</h4>
	      </div>
	      <div class="modal-body">
		      <form:form method="post" action="saveLeave" modelAttribute="leavesForm" commandName="leavesForm" cssClass="form-signin">
				<form:hidden path="companyAccountId"/>
				<form:hidden path="employeeId"/>
				<form:hidden path="leavesAllocated"/>
				<form:hidden path="leavesUsed"/>
				<form:hidden path="leavesRemaining"/>
				<form:hidden path="sickLeavesUsed"/>
				<form:hidden path="unpaidLeavesUsed"/>
				<form:hidden path="leavesPendingApproval"/>
				<form:hidden path="year"/>
				<p id="errorMsgModal" class="bg-danger"></p>
		      	<div class="form-group">
		      		​​<label for="leaveType">Leaves Type</label>
					<form:select path="leaveType" cssClass="form-control">
						<form:options items="${leaveTypes}" />
					</form:select>
					​​<label for="leaveReason">Leaves Reason</label>
					<form:input path="leaveReason" cssClass="form-control" />
		      		<label>Select Dates</label>
					<div class="input-group input-daterange" data-provide="datepicker" data-date-format="dd-mm-yyyy">
					    <form:input path="dtFrom" cssClass="form-control"  placeholder="Leave From" />
					    <span class="input-group-addon">to</span>
					    <form:input path="dtTo" cssClass="form-control"  placeholder="Leave To" />
					</div>
		      	</div>
		      </form:form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="btnValidate" type="button" class="btn btn-primary" onclick="validate();">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>