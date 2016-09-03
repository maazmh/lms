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
		url : "${home}api/getLeaves/"+document.getElementById("employeeId").value,
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
	if(document.getElementById('dtFrom').value=='' || document.getElementById('dtTo').value=='') {
		document.getElementById("btnSaveChanges").style.display="none";
		document.getElementById("divErrorMessage").style.display="block";
		document.getElementById("divConfirmationMessage").style.display="none";
		document.getElementById("myModalLabel").innerHTML = "Error";
		return false;
	} else {
		var strFrom = document.getElementById('dtFrom').value;
		var strTo = document.getElementById('dtTo').value;
		
		document.getElementById("btnSaveChanges").style.display="block";
		document.getElementById("divErrorMessage").style.display="none";
		document.getElementById("divConfirmationMessage").style.display="block";
		
		document.getElementById("myModalLabel").innerHTML = "Confirmation";
		
		document.getElementById("spanDtFrom").innerHTML = document.getElementById('dtFrom').value;
		document.getElementById("spanDtTo").innerHTML = document.getElementById('dtTo').value;
		document.getElementById("spanLeaveType").innerHTML = document.getElementById('leaveType').value;
		document.getElementById("spanAppliedDays").innerHTML = document.getElementById('dtTo').value;
		document.getElementById("spanRemainingLeaves").innerHTML = document.getElementById('leaveType').value;
		
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
		<form:form method="post" action="saveLeave" modelAttribute="leavesForm" commandName="leavesForm" cssClass="form-signin">
			<form:hidden path="employeeId"/>
			<form:hidden path="leavesAllocated"/>
			<form:hidden path="leavesUsed"/>
			<form:hidden path="leavesRemaining"/>
			<form:hidden path="sickLeavesUsed"/>
			<form:hidden path="unpaidLeavesUsed"/>
			<form:hidden path="leavesPendingApproval"/>
			<form:hidden path="year"/>
			<table width="100%">
				<tr>
					<td>
						<div class="form-group">
							<label>Employee Name</label>
							<p class="form-control-static"><c:out value="${leavesForm.employeeName}"/></p>
							<label>Leaves Used</label>
							<p class="form-control-static"><c:out value="${leavesForm.leavesUsed}"/></p>
							<label>Sick Leaves</label>
							<p class="form-control-static"><c:out value="${leavesForm.sickLeavesUsed}"/></p>
							<label>Leaves Pending Approval</label>
							<p class="form-control-static"><c:out value="${leavesForm.leavesPendingApproval}"/></p>
						</div>
					</td>
					<td>
						<div class="form-group">
							<label>Leaves Allocated</label>
							<p class="form-control-static"><c:out value="${leavesForm.leavesAllocated}"/></p>
							<label>Leaves Remaining</label>
							<p class="form-control-static"><c:out value="${leavesForm.leavesRemaining}"/></p>
							<label>Unpaid Leaves</label>
							<p class="form-control-static"><c:out value="${leavesForm.unpaidLeavesUsed}"/></p>
							<label>Leaves Carried Forward</label>
							<p class="form-control-static"><c:out value="${leavesForm.carriedForwardLeaves}"/></p>
						</div>
					</td>
				</tr>
			</table>
			<hr>
			<table width="100%">
				<tr>
					<td width="50%">
						<div class="form-group">
							<label>Select Dates</label>
							<div class="input-group input-daterange" data-provide="datepicker" data-date-format="dd-mm-yyyy">
<!-- 							    <input type="text" class="form-control" > -->
							    <form:input path="dtFrom" cssClass="form-control"  placeholder="Leave From" />
							    <span class="input-group-addon">to</span>
<!-- 							    <input type="text" class="form-control" > -->
							    <form:input path="dtTo" cssClass="form-control"  placeholder="Leave To" />
							</div>
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="return validate();">Apply Leave</button>
						</div>
					</td>
					<td>
						<div class="form-group">
							​​<label for="leaveType">Leaves Type</label>
							<form:select path="leaveType" cssClass="form-control">
								<form:options items="${leaveTypes}" />
							</form:select>
							​​<label for="leaveReason">Leaves Reason</label>
							<form:input path="leaveReason" cssClass="form-control" />
						</div>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	<div id="calendar" class="calendar" data-provide="calendar"></div>
	
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
	      	<div id="divErrorMessage" style="display: none;">
	      		Please Enter Both Dates.
	      	</div>
	      	<div id="divConfirmationMessage" style="display: none;">
		        Click OK to confirm leaves. <br>
		        Date From: <span id="spanDtFrom"></span><br>
		        Date To: <span id="spanDtTo"></span><br>
		        Leave Type: <span id="spanLeaveType"></span><br>
		        Applied Days: <span id="spanAppliedDays"></span><br>
		        Remaining Leaves after Approval: <span id="spanRemainingLeaves"></span>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="btnSaveChanges" type="button" class="btn btn-primary" onclick="saveLeaves();">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>