<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<title>Welcome To Mellow Health Physician Portal</title>
</head>
<body class="container-fluid p-6"  style="background:khaki;font-family:cursive;background:rgba(332.11, 324.5, 121, 0.9);">
	<h1 style="color:azure;border-bottom: 2px solid aliceblue;font-weight:bold;font-size:32px;background:chocolate;padding:10px;font-family:cursive;text-align:center;">Mellow Health Physician Login and Registration</h1>
	<div class="container-fluid p-8" style="text-align:center;">
		<div class="row" style="display:flex;flex-wrap:wrap;width:100%;">
			<div class="col"style="margin-bottom:10px;">
				<h1 style="color:azure;border-bottom: 2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">Register New Physician</h1>
				<form:form action="/mellowHealth/process/physicians/register" method="post" modelAttribute="physician">
				    <div class="form-group">
				        <label style="padding:3px 0;">First Name</label>
					    <div class="form-group">
					        <form:errors path="firstName" class="text-danger" />
					    </div>
				        <form:input path="firstName" class="form-control" placeholder="Please enter your first name!"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Last Name</label>
					    <div class="form-group">
					        <form:errors path="lastName" class="text-danger" />
					    </div>
				        <form:input path="lastName" class="form-control" placeholder="Please enter your last name!"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Email</label>
					    <div class="form-group">
					        <form:errors path="email" class="text-danger" />
					    </div>
				        <form:input path="email" class="form-control"  placeholder="Please enter the email!"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Password</label>
					    <div class="form-group">
					        <form:errors path="password" class="text-danger" />
					    </div>
				        <form:input path="password" class="form-control" placeholder="Please Enter Password!"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Confirm Password</label>
					    <div class="form-group">
					        <form:errors path="confirm" class="text-danger" />
					    </div>
				        <form:input path="confirm" class="form-control" placeholder="Please Confirm Password!"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">DEA License Number</label>
					    <div class="form-group">
				        	<form:errors path="deaLicenseNumber" class="text-danger" />
					    </div>
				        <form:input path="deaLicenseNumber" class="form-control" placeholder="Please enter DEA License Number!" />
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Board Certification</label>
					    <div class="form-group">
				        	<form:errors path="isBoardCertified" class="text-danger" />
					    </div>
                        <form:select path="isBoardCertified" class="form-control" style="cursor:pointer;">
                            <form:option value="" label="--Board Certification Status --"/>
                            <form:option value="Yes" label="Certified!"/>
                            <form:option value="No" label="Under-Review!"/>
                            <form:option value="Pending" label="Pending!"/>
                        </form:select>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Specialty</label>
					    <div class="form-group">
				        	<form:errors path="certificationSpecialty" class="text-danger"/>
					    </div>
				        <form:input path="certificationSpecialty" class="form-control" placeholder="Please Enter Certification Specialty!"/>
				    </div>
				    <input type="submit" value="Create Mellow Health Physician Account!" class="btn btn-primary" style="margin: 10px 0; width: 100%; padding: 10px;border-bottom: 2px solid aliceblue;"/>
				</form:form>
			</div>
			<div class="col">
			<h1 style="color:azure;border-bottom: 2px solid aliceblue;background:rgba(2.11, 24.5, 1.21, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">Login To Your Health Portal</h1>
			<form:form action="/mellowHealth/process/physicians/login" method="post" modelAttribute="loginPhysician">
				    <div class="form-group">
				        <form:errors path="email" class="text-danger"/>
				    </div>				    
				    <div class="form-group">
				        <label style="padding:3px 0;">Email</label>
				        <form:input path="email" class="form-control"  placeholder="Please Enter Email address!"/>
				    </div>
				    <div class="form-group">
				    	<form:errors path="password" class="text-danger"/>
				    </div>
				    <div class="form-group">
				        <label style="padding:3px 0;">Password</label>
				        <form:input path="password" class="form-control" placeholder="Please Enter Password!"/>
				    </div>
				    <input type="submit" value="Physician Portal Login" class="btn btn-success" style="margin: 10px 0; width: 100%; padding: 10px;border-bottom: 2px solid aliceblue;"/>
				</form:form>
		 		<h1 style="width:100%"><a style="background:rgba(68, 8, 120, 0.9);margin:16px 0 0 0;width:100%; display:block; padding: 10px" href="/mellowHealth/patientsPortal/login" class="btn btn-success">Patient Login Portal!</a></h1>
			</div>
		</div>
	</div>
	
</body>
</html>