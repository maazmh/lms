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

	
<script type="text/javascript" language="javascript" src="//code.jquery.com/jquery-1.12.3.js">
</script>
<script type="text/javascript" language="javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js">
</script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js">
</script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js">
</script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.2.1/js/dataTables.buttons.min.js">
</script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.2.1/js/buttons.bootstrap.min.js">
</script>
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/select/1.2.0/js/dataTables.select.min.js">
</script>
<script	src="/lms/resources/js/bootstrap-select.js"></script>
<title>Admin</title>
<script type="text/javascript">

function setupNavBar() {
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').addClass('active');
}

function init() { 
	/*
	=====================================================================================
								Employee Table
	=====================================================================================
	*/
	var table = $('#employee').DataTable( {
	    paging: false,
        "pagingType": "simple",
	    autowidth: true,
	    ordering: false,
	    searching:true,
	    "search": {
	       "smart": true
	    },
	    pagelength:20,
	    "processing": true,
        //"serverSide": true,
        "ajax": "${home}api/getAllEmployees/1",
        columns: [
   			{ data: "idEmployee" },
   			{ data: "firstName" },
   			{ data: "lastName" },
            { data: "departmentName" },
            { data: "emailId" },
            //{ data: "departmentId" }
//             { data: "idEmployee", render: function ( data, type, full, meta ) {
// 	                //var btnRendererStr = "<button class='btn btn-primary btn-sm' onClick='editEmp();'>Edit</button> &nbsp;&nbsp;&nbsp;";
// 	                //btnRendererStr += "<button class='btn btn-primary btn-sm'>Delete</button>"; 
// 	            	alert(data);
// 	                var btnRendererStr = "<a href='#' onclick='deleteEmpConfirmationModal("+data+");'><span class='glyphicon glyphicon-remove'></span></a>&nbsp;&nbsp;&nbsp;";
// 	                return btnRendererStr;
//             	}
//             }
           ],
//         "columnDefs": [ {
//             "targets": -1,
//             "data": "emailId",
//             "defaultContent": "<button>Click!</button>"
//         } ],
        select: true
	} );
	
// 	$('#employee tbody').on( 'click', 'button', function () {
//         var data = table.row( $(this).parents('tr') ).data();
//         alert( data.idEmployee +"'s salary is: "+ data.lastName );
//     } );
	
	
	$('#employee tbody').on('click', 'td', function () {
        //alert(table.row( this ).data().firstName);
        //alert(table.column( this ).index());
        var clickedRow = table.row( this ).data();
        
        // Populate the modal
        $('#employeeId').val(clickedRow.idEmployee);
        $('#firstName').val(clickedRow.firstName);
        $('#lastName').val(clickedRow.lastName);
        $('#emailId').val(clickedRow.emailId);
        $('#department').val(clickedRow.departmentId);
        $('#leavesAllocated').val(clickedRow.leavesAllocated);
        $('#leavesCarriedForward').val(clickedRow.leavesCarriedForward);
        
        var arrApprovers=[];
        for(var i in clickedRow.approvers) {
        	arrApprovers.push(clickedRow.approvers[i].idApprovers);
        }
        $('#approvers').selectpicker('val', arrApprovers);
        if(clickedRow.admin==true) {
        	$("#radioAdmin1").prop("checked", true);
        } else {
        	$("#radioAdmin0").prop("checked", true)
        }
        
        if(clickedRow.deleted==true) {
        	$("#radioDeleted1").prop("checked", true);
        } else {
        	$("#radioDeleted0").prop("checked", true)
        }
        
        
        $('#saveOrEditModal').modal('show');
    } );
	
	$('#employee tbody').on('dblclick', 'tr', function () {
        alert(table.row( this ).data().firstName);
    } );
	
}

function resetModalFieldsForNew() {
	$('#employeeId').val(null);
    $('#firstName').val(null);
    $('#lastName').val(null);
    $('#emailId').val(null);
    $('#department').val(null);
    $('#approvers').selectpicker('val', []);
    $('#leavesAllocated').val(null);
    $('#leavesCarriedForward').val(null);
    $('#admin').val(0);
}

function saveChanges() {
	if(validateForm()) {
		$('#adminForm').submit();
	}
}

function validateForm() {
	var val = true;
	var validationMessage = '<ul>';
	if($('#firstName').val()=='') {
		val = false;
		validationMessage += '<li>First Name cannot be Empty</li>';
	}
	if($('#lastName').val()=='') {
		val = false;
		validationMessage += '<li>Last Name cannot be Empty</li>';
	}
	if($('#emailId').val()=='') {
		val = false;
		validationMessage += '<li>Email Id cannot be Empty</li>';
	}
	if($('#department').val()=='') {
		val = false;
		validationMessage += '<li>Select a department</li>';
	}
	if($('#approvers').val()=='') {
		val = false;
		validationMessage += '<li>Select atleast one approver</li>';
	}
	if($('#leavesAllocated').val()=='') {
		val = false;
		validationMessage += '<li>Leaves allocated cannot be null or empty</li>';
	}
	if($('#leavesCarriedForward').val()=='') {
		val = false;
		validationMessage += '<li>Leaves Carried Forward cannot be null. Enter 0 if no leaves carried forward.</li>';
	}
	validationMessage += '</ul>';
	
	if(!val) {
		$('#errorMsgModal').html(validationMessage);
	}
	return val;
}

function deleteEmpConfirmationModal(empId) {
	//$('#spanEmpNameToDelete').html(
}
function deleteEmp() {
	
}
</script>
</head>
<body onload="init();setupNavBar();">
	<%@ include file="navbar.html" %>
	<div class="container" style="padding-top: 5em;">
		<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
	        <a href="#" class="close" data-dismiss="alert">&times;</a>
	        <strong>Error!</strong>
	        <span id="jsErrorMessage"></span>
	    </div>
		<p id="errorMsgP" class="bg-danger">${errorMessage}</p>
		<p class="bg-success">${successMessage}</p>
		<div class="btn-group" role="group" aria-label="employeeBtnGroup">
			<button type="button" class="btn btn-primary" id='btnNew' data-toggle="modal" data-target="#saveOrEditModal" onclick="return resetModalFieldsForNew();">New</button>
		</div>
		<table id="employee" class="table table-striped table-bordered" cellspacing="0" width="100%">
			<thead>
	            <tr>
	            	<th>ID</th>
	                <th>First Name</th>
	                <th>Last Name</th>
	                <th>Department</th>
	                <th>Email ID</th>
	            </tr>
	        </thead>
		</table>
	</div>
	
	<!-- Confirmation Modal Dialog -->
	<div class="modal fade" id="saveOrEditModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" style='width: 80%;' role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">Add New Employee</h4>
	      </div>
	      <div class="modal-body">
	        <form:form method="post" action="save-employee" modelAttribute="adminForm" commandName="adminForm" cssClass="form-signin">
	        	<p id="errorMsgModal" class="bg-danger">${errorMessage}</p>
				<table width="100%" class="table">
					<tr>
						<td width="10%">
							<label for="firstName">First Name</label>
						</td>
						<td width="20%">
							<form:input path="firstName" cssClass="form-control"  placeholder="First Name" />
							<form:hidden path="employeeId"/>
						</td>
						<td width="5%">
						</td>
						<td width="10%">
							<label for="lastName">Last Name</label>
						</td>
						<td width="10%">
							<form:input path="lastName" cssClass="form-control"  placeholder="Last Name" />
						</td>
					</tr>
					<tr>
						<td width="10%">
							<label>Department</label>
						</td>
						<td width="20%">
							<form:select path="department" cssClass="form-control">
								<form:option value="" label="-- Select --"/>
								<form:options items="${mapDepts}"/>
							</form:select>
						</td>
						<td width="5%">
						</td>
						<td width="10%">
							<label>Email Id</label>
						</td>
						<td width="20%">
							<form:input path="emailId" cssClass="form-control"  placeholder="Email Id" />
						</td>
					</tr>
					<tr>
						<td width="10%">
							<label for="leavesAllocated">Leaves Allocated (Current Year)</label>
						</td>
						<td width="20%">
							<form:input path="leavesAllocated" cssClass="form-control"  placeholder="Leaves Allocated (Current Year)" />
						</td>
						<td width="5%">
						</td>
						<td width="10%">
							<label for="lastName">Leaves Carried Forward</label>
						</td>
						<td width="10%">
							<form:input path="leavesCarriedForward" cssClass="form-control"  placeholder="Leaves Carried Forward" />
						</td>
					</tr>
					<tr>
						<td width="10%">
							<label>Approvers</label>
						</td>
						<td width="20%">
<!-- 							<table id="tblApprover" class="table table-striped table-bordered" cellspacing="0" width="100%"> -->
<!-- 							</table> -->
							<form:select path="approvers" cssClass="selectpicker" multiple="true" data-live-search="true" data-max-options="3">
								<form:options items="${mapApprovers}"/>
							</form:select>
						</td>
						<td width="5%">
						</td>
					</tr>
					<tr>
						<td width="10%">
							<label>Admin</label>
						</td>
						<td width="20%">
							<p><form:radiobutton id='radioAdmin1' path="admin" value="1" />&nbsp;&nbsp;&nbsp;Yes </p>
							<p><form:radiobutton id='radioAdmin0' path="admin" value="0" />&nbsp;&nbsp;&nbsp;No </p>
						</td>
						<td width="5%">
						</td>
						<td width="10%">
							<label>Deleted</label>
						</td>
						<td width="20%">
							<p><form:radiobutton id='radioDeleted1' path="deleted" value="1" />&nbsp;&nbsp;&nbsp;Yes </p>
							<p><form:radiobutton id='radioDeleted0' path="deleted" value="0" />&nbsp;&nbsp;&nbsp;No </p>
						</td>
					</tr>
				</table>
			</form:form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onclick="saveChanges();">Save changes</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" style='width: 80%;' role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">Delete Confirmation</h4>
	      </div>
	      <div class="modal-body">
	      	  Are you sure you want to delete <span id='spanEmpNameToDelete'></span>?
	      	  <form:form method="post" action="delete-employee" modelAttribute="adminForm" commandName="adminForm" cssClass="form-signin">
	      	  	<input type="hidden" id="employeeIdToDelete" />
	      	  </form:form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary" onclick="deleteEmp();">Delete</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>