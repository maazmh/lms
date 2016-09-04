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
<title>Admin</title>
<script type="text/javascript">

function setupNavBar() {
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').addClass('active');
}

function init() { 
	$('#employee').DataTable( {
	    paging: true,
	    autowidth: true,
	    ordering: true,
	    searching:true,
	    "processing": true,
        "serverSide": true,
        "ajax": "${home}api/getAllEmployees/1"
	} );
}

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function() {
	editor = new $.fn.dataTable.Editor( {
		table: "#employee",
		fields: [ 
		    {
				label: "Id:",
				name: "idEmployee"
			},
			{
				label: "First Name:",
				name: "firstName"
			},
			{
				label: "Last Name:",
				name: "lastName"
			}, 
			{
				label: "Department:",
				name: "department"
			}, 
			{
				label: "Email Id:",
				name: "emailId"
			}
		]
	} );
	
	var table = $('#example').DataTable( {
        columns: [
			{ data: "idEmployee" },
			{ data: "firstName" },
			{ data: "lastName" },
            { data: "department" },
            { data: "emailId" }
        ],
        select: true
    } );
	
	// Display the buttons
	new $.fn.dataTable.Buttons( table, [
		{ extend: "create", editor: editor },
		{ extend: "edit",   editor: editor },
		{ extend: "remove", editor: editor }
	] );

	
} 	);
</script>
</head>
<body onload="init();setupNavBar();">
	<%@ include file="navbar.html" %>
	<div class="container" style="padding-top: 5em;">
		<form:form method="post" action="save" modelAttribute="adminForm" commandName="adminForm" cssClass="form-signin">
			<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
		        <a href="#" class="close" data-dismiss="alert">&times;</a>
		        <strong>Error!</strong>
		        <span id="jsErrorMessage"></span>
		    </div>
			<p id="errorMsgP" class="bg-danger">${errorMessage}</p>
			<p class="bg-success">${successMessage}</p>
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
		        <tfoot>
		            <tr>
		                <th>ID</th>
		                <th>First Name</th>
		                <th>Last Name</th>
		                <th>Department</th>
		                <th>Email ID</th>
		            </tr>
		        </tfoot>
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
	        <p>Click Save Changes to </p>
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