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
<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.2.1/js/buttons.bootstrap.min.js">
</script>
<title>Account</title>
<script type="text/javascript">

function setupNavBar() {
	$('#navBarLiDashboard').removeClass('active');
	$('#navBarLiApproval').removeClass('active');
	$('#navBarLiCalendar').removeClass('active');
	$('#navBarLiAdmin').removeClass('active');
	$('#navBarLiAccount').addClass('active');
	$('#navBarEmpName').html($('#empNameFromSession').val());
}

function init() { 

}

function validate() {
	var errorMsg = '<ul>';
	var validate = true;
	
	var pass = $('#password').val();
	var passConf = $('#confirmPassword').val();
	
	if(pass!=passConf) {
		validate = false;
		errorMsg = '<li>Passwords not matching</li>';
	}
	
	if(pass=='' && passConf=='') {
		validate = false;
		errorMsg = '<li>Please Enter a valid password</li>';
	}
	
	if(pass!='' && passConf!='' && pass==passConf && pass.length < 6) {
		validate = false;
		errorMsg = '<li>Password should be atleast 6 characters</li>';
	}
	
	errorMsg = '</ul>';
}

</script>
</head>
<body onload="init();setupNavBar();">
	<%@ include file="navbar.html" %>
	<input type="hidden" id='empNameFromSession' value="<%= session.getAttribute("employeeName") %>">
	<div class="container" style="padding-top: 5em; width: 50%;">
		<form:form method="post" action="account" modelAttribute="loginForm" commandName="loginForm" cssClass="form-signin">
			<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
		        <a href="#" class="close" data-dismiss="alert">&times;</a>
		        <strong>Error!</strong>
		        <span id="jsErrorMessage"></span>
		    </div>
			<p id="errorMsgP" class="bg-danger">${errorMessage}</p>
			<p class="bg-success">${successMessage}</p>
			
<!-- 			<h2 class="form-signin-heading">Please sign in</h2> -->
			<div class="form-group">
				<label for="username">Username</label>
				<form:hidden path="username" />
				<p><c:out value="${loginForm.username}"/></p>
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<!--     <input type="text" class="form-control" id="pin" placeholder="Enter 6 Digit Pin"> -->
				<form:input path="password" cssClass="form-control"  placeholder="Password" size="30" type="password" />
			</div>
			<div class="form-group">
				<label for="password">Confirm Password</label>
				<!--     <input type="text" class="form-control" id="pin" placeholder="Enter 6 Digit Pin"> -->
				<input id="confirmPassword" class="form-control" placeholder="Password" size="30" type="password" />
			</div>
			<button type="submit" class="btn btn-default" onclick="return validate();">Save</button>
		</form:form>
	</div>
	
</body>
</html>