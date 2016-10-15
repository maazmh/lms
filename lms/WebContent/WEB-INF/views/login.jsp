<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"	href="/lms/resources/css/lms.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<title>Login</title>
</head>
<body>
	<div class="container" style="padding-top: 12em; width: 30%;">
		<div id="divHeader">
			<img alt="Mondia Media" src="http://www.mondiamedia.com/fileadmin/templates/img/logo-print.png" height="30px;">
		</div>
		<form:form method="post" action="login" modelAttribute="loginForm" commandName="loginForm" cssClass="form-signin">
			<div id="divJsErrorMessages" style="display: none;" class="alert alert-danger alert-error">
		        <a href="#" class="close" data-dismiss="alert">&times;</a>
		        <strong>Error!</strong>
		        <span id="jsErrorMessage"></span>
		    </div>
			<p id="errorMsgP" class="bg-danger">${loginFailedMessage}</p>
			<p class="bg-success">${successMessage}</p>
			
<!-- 			<h2 class="form-signin-heading">Please sign in</h2> -->
			<div class="form-group">
				<label for="username">Username</label>
				<form:input path="username" cssClass="form-control"  placeholder="Email id" />
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<!--     <input type="text" class="form-control" id="pin" placeholder="Enter 6 Digit Pin"> -->
				<form:input path="password" cssClass="form-control"  placeholder="Password" size="30" type="password" />
			</div>
			<button type="submit" class="btn btn-default" onclick="return validate();">Login</button>
			<a href="#" onclick="register();">Forgot Password</a>
		</form:form>
	</div>
</body>
</html>