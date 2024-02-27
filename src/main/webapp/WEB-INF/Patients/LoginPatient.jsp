<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<!-- YOUR own local CSS -->
<link rel="stylesheet" href="/css/main.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>MELLOW HEALTH LOGIN</title>
</head>
<body class="container-fluid p-4"  style="background:khaki;font-family:cursive;">
	<h1 style="color:azure;border-bottosm: 2px solid aliceblue;font-weight:bold;font-size:32px;background:chocolate;padding:10px;font-family:cursive;text-align:center;">WELCOME TO MELLOW HEALTH!</h1>
	<div class="container" style=" text-align:center;">
	    <a href="/mellowHealth/patientsPortal/login" style="width: 100%; display:block; padding:10px;color:brown;text-decoration:none;">
			<h1 style="color:azure;border-bottom: 2px solid aliceblue;background:rgba(2.11, 24.5, 1.21, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">PATIENT PORTAL ACCESS</h1>
	    </a>
		<div class="container">
		<div class="row">
			<div class="col">
			<h1 style="color:azure;border-bottom: 2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">Mellow Patients Login</h1>
			<form:form action="/mellowHealth/patientsPortal/process/login" method="post" modelAttribute="loginPatient">
				    <div class="form-group">
				        <label style="padding:5px 0">Email</label>
				        <form:input path="email" class="form-control"  style="padding:10px;" placeholder="Please enter email address!" />
				        <form:errors path="email" class="text-danger"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:5px 0;">Password</label>
				        <form:input path="password" class="form-control"  style="padding:10px;" placeholder="Please enter password!" />
				        <form:errors path="password" class="text-danger"/>
				    </div>
				    <input type="submit" value="PATIENT PORTAL LOGIN!" class="btn btn-success" style="margin:10px 0; width:100%; padding:10px; font-weight:bold; font-size: 18px;"/>
				</form:form>
				<a style="background:rgba(6.8, 1.8, 20, 0.9);margin:10px 0;width:100%; display:block; padding: 10px;border-radius:5%;" href="/mellowHealth/patientsPortal/register" class="btn btn-success"><h1 style="color:azure;border-bottom: 2px solid aliceblue;background:rgba(112.11, 24.5, 1, 0.9);border-radius:5%;font-weight:bold;font-size:20px;padding:10px;font-family:cursive;text-align:center;">Dont Have A Mellow Health Account?</h1></a>
		 		<h1 style="background:rgba(6.8, 0.8, 2.320, 0.9);width:100%; padding: 10px; border-radius:5%;margin:5px 0" ><a style="background:rgba(48, 0.8, 12.0, 0.9);margin:10px 0;width:100%; display:block; padding: 10px; font-size:22px;" href="/mellowHealth/physicians/login" class="btn btn-success">MELLOW PHYSICIAN ACCESS!</a></h1>
			</div>
		</div>
	</div>
</body>
</html>