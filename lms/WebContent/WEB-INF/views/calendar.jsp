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
<script	src="/lms/resources/js/bootstrap-year-calendar.js"></script>
<script	src="/lms/resources/js/bootstrap-year-calendar-custom-code.js"></script>
<script	src="http://www.bootstrap-year-calendar.com/js/bootstrap-popover.js"></script>
<script	src="http://www.bootstrap-year-calendar.com/js/respond.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>

<script type="text/javascript">
function getData() {
	//console.log('sada');
	//console.log(document.getElementById("employeeId").value);
	//alert(new Date(2016, 4, 28));
	//alert('fds');
	//alert($("#employeeId"));
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
	    		//alert('obj['+j+']: '+obj[j]);
	    		if(j=='name') {
	    			dataSource[x].name = obj[j];
	    			//alert('obj['+j+']: '+obj[j]);
	    		} else if(j=='location') {
	    			dataSource[x].location = obj[j];
	    			//alert('obj['+j+']: '+obj[j]);
	    		} else if(j=='startDate') {
	    			var startDate = obj[j];
	    			var arrStartDate = startDate.split('-');
					dataSource[x].startDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2]);
	    			//alert('obj['+j+']: '+obj[j]);
	    		} else if(j=='endDate') {
	    			var endDate = obj[j];
	    			var arrEndDate = endDate.split('-');
	    			dataSource[x].endDate = new Date(arrEndDate[0], arrEndDate[1]-1, arrEndDate[2]);
	    			//alert('obj['+j+']: '+obj[j]);
	    		}
	    	}
	    }
	    $('#calendar').data('calendar').setDataSource(dataSource);
	}
}

function validate() {
	document.getElementById("spanDtFrom").innerHTML = document.getElementById('dtFrom').value;
	document.getElementById("spanDtTo").innerHTML = document.getElementById('dtTo').value;
	return true;
}

function saveLeaves() {
	
}
</script>
	
<title>Calendar</title>
</head>
<body onload="getData();">
	<div class="container" style="padding-top: 25px;">
		<form:form method="post" action="applyLeave" modelAttribute="leavesForm" commandName="leavesForm" cssClass="form-signin">
			<form:hidden path="employeeId"/>
			<form:hidden path="leavesAllocated"/>
			<form:hidden path="leavesUsed"/>
			<form:hidden path="leavesRemaining"/>
			<form:hidden path="sickLeavesUsed"/>
			<form:hidden path="unpaidLeavesUsed"/>
			<form:hidden path="leavesPendingApproval"/>
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
						</div>
					</td>
				</tr>
			</table>
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
							<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="return validate();">Apply Leave</button>
						</div>
					</td>
					<td>
						<div class="form-group">
							​​<label for="leaveType">Leaves Type</label>
							<form:select path="leaveType" cssClass="form-control">
								<form:options items="${leaveTypes}" />
							</form:select>
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
	        Click OK to confirm leaves. <br>
	        Date From: <span id="spanDtFrom"></span><br>
	        Date To: <span id="spanDtTo"></span><br>
	        Leave Type: <span id="spanLeaveType"></span><br>
	        Applied Days: <span id="spanAppliedDays"></span><br>
	        Remaining Leaves after Approval: <span id="spanRemainingLeaves"></span>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onclick="saveLeaves();">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>