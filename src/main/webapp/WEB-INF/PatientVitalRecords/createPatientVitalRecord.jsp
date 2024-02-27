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
<link rel="stylesheet" href="/css/styles.css"/>
<!-- Local JavaScript section -->
<script src="<c:url value='/scripts.js'/>"></script>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<meta charset="ISO-8859-1">
<title>Add Patients Vitals!</title>
</head>
<body class="container-fluid p-8" style="
 	<c:choose>
	     <c:when test="${loggedInPatient.patientCases.size() <= 2 }">
			 color: khaki;background:aqua;background: rgba(1.3, 0.64, 10, 0.9);
	     </c:when>
	     <c:when test="${loggedInPatient.patientCases.size() % 2 == 1}">
			 color:pink; background: rgba(62.13, 110.123, 380.160, 0.9);
	     </c:when>
	     <c:otherwise>
	         color: rgb(211, 180, 255);background:rgba(0.2, 0.4, 27.6, 0.9);
	     </c:otherwise>
	</c:choose>font-family:cursive;">
	<h1 style="color:azure;border-bottosm: 2px solid aliceblue;font-weight:bold;font-size:32px;background:chocolate;padding:10px;font-family:cursive;text-align:center;">WELCOME TO MELLOW HEALTH!</h1>
	<div class="container-fluid p-6" style=" text-align:center;">
		<a style="width: 100%; display:block; padding:0px;color:brown;text-decoration:none;" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }">
			<h1 style="color:azure;border-bottom:2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">
				<c:out value="Register Your Vital Signs Today ${currentDateTime}"/> <c:out value="${loggedInPatient.patientFirstName}"/> <c:out value="${loggedInPatient.patientLastName}!"/>
			</h1>
		</a>
			<div class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;">
				<form action="/mellowHealth/patientVitals/patients/newPatientVitals" class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
				    <label style="padding:5px 10px">Search Patient Name</label>
					<input style="padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName"/>
					<input class="btn btn-outline-primary" type="submit" value="Search Patient"/>
				</form>
				<p style="color:rgba(311, 31, 321, 0.9);">${matchedPatientFullName.patientFirstName} ${matchedPatientFullName.patientLastName} Date Of Birth: ${matchedPatientFullName.dateOfBirth}</p>	
			</div>
		<div class="row">
							            
			<div class="col">
				<form:form action="/mellowHealth/patientVitals/process/patients/createNewPatientVitals" method="POST" modelAttribute="patientVitals">
				<!-- Add this block to display global errors -->
				<!--form:errors path="*" cssClass="text-danger"/!-->
			       <div class="form-group">
		                <label style="padding:10px 0">Select Registered Patients</label>
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
		                    <c:choose>
		                        <c:when test="${matchedPatientFullName != null && matchedPatientFullName.patientFirstName.length() > 1}">
		                            <form:option value="${loggedInPatient.id}" label="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
		                        </c:when>
		                        <c:otherwise>
		                            <form:option value="${loggedInPatient.id}" label="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
		                        </c:otherwise>
		                    </c:choose>
		                </form:select>
		            </div>

			       <div class="form-group">
		                <label style="padding:10px 0">Select Registered Patients</label>
		                	<form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientCaseSelect', 'patientName', 'selectedPatientDiv')">
		                		<form:option value="" label="-- Select Patient Case--"/>
						    	<c:forEach items="${loggedInPatient.patientCases}" var="patientCase">
		                       		<form:option value="${patientCase.id}" label="${patientCase.chiefComplaint} ${patientCase.createdAt} Visit!"/>
		                       	</c:forEach>
		                </form:select>
		            </div>

					<div class="form-group">
					    <label style="padding:5px 0 10px 0">Systolic Blood Pressure</label>
					    <div class="form-group">
					        <form:errors path="systolicBloodPressure" class="text-danger" />
					    </div>
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;"></div>
					    <form:input type="number" path="systolicBloodPressure" placeholder="Please Enter Systolic BloodPressure Value!" style="width:100%; padding:8px; border-radius:5%;" />
					</div>

					<div class="form-group">
					    <label style="padding:5px 0 10px 0">Diastolic Blood Pressure</label>
					    <div class="form-group">
					        <form:errors path="diastolicBloodPressure" class="text-danger" />
					    </div>
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;"></div>
					    <form:input type="number" path="diastolicBloodPressure" placeholder="Please Enter Systolic BloodPressure Value!" style="width:100%; padding:8px; border-radius:5%;" />
					</div>

				    <div class="form-group">
				        <label style="padding:5px 0 10px 0">Heart Rate</label>
					    <div class="form-group">
				        	<form:errors path="heartRate" class="text-danger" />
					    </div>
				        <form:input type="number" path="heartRate" class="form-control" placeholder="Please Enter Measured Heart Rate!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Respiratory Rate</label>
					    <div class="form-group">
				        	<form:errors path="respiratoryRate" class="text-danger" />
					    </div>
				        <form:input type="number" path="respiratoryRate" class="form-control" placeholder="Please Enter Measured Respiratory Rate!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Pulse Rate</label>
					    <div class="form-group">
				        	<form:errors path="pulseRate" class="text-danger" />
					    </div>
				        <form:input type="number" path="pulseRate" class="form-control" placeholder="Please Enter Measured Pulse Rate!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Body Temperature In Fahrenheit</label>
					    <div class="form-group">
				        	<form:errors path="bodyTemperature" class="text-danger" />
					    </div>
				        <form:input type="number" path="bodyTemperature" class="form-control" placeholder="Please Enter Measured Pulse Rate!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Height in cm</label>
					    <div class="form-group">
				        	<form:errors path="height" class="text-danger" />
					    </div>
				        <form:input type="number" path="height" class="form-control" placeholder="Please Enter Height!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Weight</label>
					    <div class="form-group">
				        	<form:errors path="weight" class="text-danger" />
					    </div>
				        <form:input type="number" path="weight" class="form-control" placeholder="Please Enter Measured Body Weight!"/>
				    </div><input type="submit" value="Add Patient Vitals" class="btn btn-success" style="margin: 10px 0; width: 100%; padding: 10px;"/>
				</form:form>
				<h1 style="width:100%;"><a style=" margin:10px 0;width:100%;display:block; padding:10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-warning">CANCEL!</a></h1>
			</div>
		</div>
	</div>
	
</body>
</html> 