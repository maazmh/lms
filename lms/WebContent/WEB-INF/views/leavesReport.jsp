<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.2.1/css/buttons.bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.2.0/css/select.bootstrap.min.css">
<link rel="stylesheet"	href="/lms/resources/css/bootstrap-select.min.css">
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">

	
<script type="text/javascript" language="javascript" src="//code.jquery.com/jquery-1.12.3.js"></script>
<script type="text/javascript" language="javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.2.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.2.1/js/buttons.bootstrap.min.js"></script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/select/1.2.0/js/dataTables.select.min.js"></script>
<script	src="/lms/resources/js/bootstrap-select.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>

<title>Reports</title>
<script type="text/javascript">
function init() {
	setupNavBar();
	setupTable();
}

function setupNavBar() {
	$('#navBarLiDashboard').removeClass('active');
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').removeClass('active');
	$('#navBarEmpName').html($('#empNameFromSession').val());
	$('#navBarLiReport').addClass('active');
}

var table;
function setupTable() {
	/*
	=====================================================================================
								Leave Table
	=====================================================================================
	*/
	table = $('#leaveTable').DataTable( {
	    paging: true,
        //"pagingType": "simple",
	    autowidth: true,
	    //ordering: false,
	    searching:true,
	    "search": {
	       "smart": true
	    },
	    pagelength:20,
	    "processing": true,
        //"serverSide": true,
        //"ajax": "${home}api/getAllEmployees/1",
        columns: [
   			{ data: "employeeName" },
   			{ data: "department" },
   			{ data: "dtFrom" },
            { data: "dtTo" },
            { data: "isApproved" }
		],
        select: true
	} );
	
	//alert('table obj: '+table);
}

function search() {
	//alert(table);
	var companyAccountId = $('#companyIdFromSession').val();
	var empIds = $('#empIds').val();
	var dept = $('#department').val();
	var dtFrom = $('#dtFrom').val();
	var dtTo = $('#dtTo').val();
	var leaveType = $('#leaveType').val();
	var isApproved = $('#approved').val();
	
	var url = '${home}api/search?companyAccountId='+companyAccountId;
	if(empIds!=null && empIds!='') {
		url += '&empIds='+empIds;
	}
	if(dept!=null && dept!='') {
		url += '&dept='+dept;
	}
	if(dtFrom!=null && dtTo!=null && dtFrom!='' && dtTo!='') {
		url += '&dtFrom='+dtFrom+'&dtTo='+dtTo;
	}
	if(leaveType!=null && leaveType!='') {
		url += '&leaveType='+leaveType;
	}
	if(isApproved!=null && isApproved!='') {
		url += '&isApproved='+isApproved;
	}
	
	//alert('url: '+ url);
	
	table.ajax.url(url).load();
}
</script>
</head>
<body onload="init();">
	<%@ include file="navbar.html" %>
	<input type="hidden" id='empNameFromSession' value="<%= session.getAttribute("employeeName") %>">
	<input type="hidden" id='companyIdFromSession' value="<%= session.getAttribute("companyAccountId") %>">
	<div class="container" style="padding-top: 5em;">
		<form:form method="post" action="search" modelAttribute="reportForm" commandName="reportForm" cssClass="form-signin">
			<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
		        <a href="#" class="close" data-dismiss="alert">&times;</a>
		        <strong>Error!</strong>
		        <span id="jsErrorMessage"></span>
		    </div>
			<p id="errorMsgP" class="bg-danger">${errorMessage}</p>
			<p class="bg-success">${successMessage}</p>
			<div class="row">
			    <div class="col-sm-3 col-md-3"><label>Staff Name</label></div>
			    <div class="col-sm-3 col-md-3">
			    	<form:select path="empIds" cssClass="selectpicker" multiple="true" data-live-search="true" data-max-options="6">
						<form:options items="${mapEmployees}"/>
					</form:select>
			    </div>
			    <div class="col-sm-3 col-md-3"><label>Department</label></div>
			    <div class="col-sm-3 col-md-3">
			    	<form:select path="department" cssClass="form-control">
						<form:option value="" label="-- Select --"/>
						<form:options items="${mapDepts}"/>
					</form:select>
			    </div>
			</div>
			<div class="row" style="padding-top: 1.5em;">
			    <div class="col-sm-3 col-md-3"><label>Date</label></div>
			    <div class="col-sm-3 col-md-3">
			    	<div class="input-group input-daterange" data-provide="datepicker" data-date-format="dd-mm-yyyy">
					    <form:input path="dtFrom" cssClass="form-control"  placeholder="Leave From" />
					    <span class="input-group-addon">to</span>
					    <form:input path="dtTo" cssClass="form-control"  placeholder="Leave To" />
					</div>
			    </div>
			    <div class="col-sm-3 col-md-3"><label>Leave Type</label></div>
			    <div class="col-sm-3 col-md-3">
			    	<form:select path="leaveType" cssClass="form-control">
						<form:options items="${leaveTypes}" />
					</form:select>
			    </div>
			</div>
			<div class="row" style="padding-top: 1.5em;">
			    <div class="col-sm-3 col-md-3"><label>Approved</label></div>
			    <div class="col-sm-3 col-md-3">
			    	<form:select path="approved" cssClass="form-control">
						<form:option value="" label="-- All --"  />
						<form:option value="1" label="Approved" />
						<form:option value="0" label="UnApproved" />
					</form:select>
			    </div>
			    <div class="col-sm-3 col-md-3"><label></label></div>
			    <div class="col-sm-3 col-md-3">
			    	
			    </div>
			</div>
			<div class="row" style="padding-top: 1.5em;">
				<div class="col-sm-12 col-md-12">
					<button id="btnSearch" type="button" class="btn btn-primary" onclick="search();">Search</button>
				</div>
			</div>
		</form:form>
		<hr>
		<table id="leaveTable" class="table table-striped table-bordered" cellspacing="0" width="100%">
			<thead>
	            <tr>
	                <th>Name</th>
	                <th>Department</th>
	                <th>From</th>
	                <th>To</th>
	                <th>isApproved</th>
	            </tr>
	        </thead>
		</table>
	</div>
</body>
</html>