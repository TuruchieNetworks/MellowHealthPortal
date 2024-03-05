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
<title>Add New Visit!</title>
</head>
<body class="container-fluid p-8" style="
 	<c:choose>
	     <c:when test="${loggedInPatient.patientCases.size() <= 2 }">
			 color: khaki;background:aqua;background: rgba(1.3, 15.64, 10, 0.9);
	     </c:when>
	     <c:when test="${loggedInPatient.patientCases.size() % 2 == 1}">
			 color:pink; background: rgba(62.13, 110.123, 0.160, 0.9);
	     </c:when>
	     <c:otherwise>
	         color: rgb(211, 180, 255);background:rgba(0.2, 0.4, 27.6, 0.9);
	     </c:otherwise>
	</c:choose>font-family:cursive;">
	<h1 style="color:azure;border-bottosm: 2px solid aliceblue;font-weight:bold;font-size:32px;background:chocolate;padding:10px;font-family:cursive;text-align:center;">WELCOME TO MELLOW HEALTH!</h1>
	<div class="container-fluid p-6" style=" text-align:center;">
		<a style="width: 100%; display:block; padding:0px;color:brown;text-decoration:none;" href="/mellowHealth/physicians/${loggedInPhysician.id }">
			<h1 style="color:azure;border-bottom:2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">Register A New Patient Visit Today, <c:out value="${currentDateTime} ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>!</h1>
		</a>
			<div class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;">
				<form action="/mellowHealth/patientAddresses/newPatientAddress" class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
				    <label style="padding:5px 10px">Search Patient Name</label>
					<input style="padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName"/>
					<input class="btn btn-outline-primary" type="submit" value="Search Patient"/>
				</form>
				<p style="color:rgba(311, 31, 321, 0.9);">${matchedPatientFullName.patientFirstName} ${matchedPatientFullName.patientLastName} Date Of Birth: ${matchedPatientFullName.dateOfBirth}</p>	
				<div class="form-group" id="selectedPatientDiv" style="font-weight: bold; margin-top: 5px;"></div>
			</div>
		<div class="row">            
			<div class="col">
				<form:form action="/mellowHealth/process/patientAddresses/createNewPatientAddress" method="POST" modelAttribute="patientAddress">
				<!-- Add this block to display global errors -->
				<!--form:errors path="*" cssClass="text-danger"/!-->
				<c:choose>
			    <c:when test="${not empty matchedPatientCharacterList}">
					<div class="form-group">
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
					    	<label style="padding:10px 0">Select Registered Patients</label>
					    </div>
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
						    <c:forEach items="${matchedPatientCharacterList}" var="matchedPatient">  
			                    <c:choose>
			                        <c:when test="${matchedPatientCharacterList.size() < 1}">
			                        	<form:option value="${searchedPatient.id}" label="${searchedPatient.patientFirstName} ${searchedPatient.patientLastName}"/>
			                        </c:when>
			                        <c:otherwise>
			                            <form:option value="${matchedPatient.id}" label="${matchedPatient.patientFirstName} ${matchedPatient.patientLastName}"/>
			                        </c:otherwise>
			                    </c:choose>
		                    </c:forEach>
		                </form:select>
		            </div>
				</c:when>
				<c:otherwise>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				    	<label style="padding:10px 0">Registered Patient</label>						
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
                            <form:option value="${loggedInPatient.id}" label="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
		                </form:select>
		            </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
					</div>
		        </c:otherwise>
	           	</c:choose>
					<div class="form-group">
					    <label style="padding:5px 0">Street</label>
					    <div class="form-group">
				        	<form:errors path="street" class="text-danger" />
					    </div>
					    <form:input class="form-control" path="street" placeholder="Please Enter Street Details!" style="width:100%; padding:8px; border-radius:5%;"/>
					</div>

					<div class="form-group">
				        <label style="padding:5px 0">City</label>
					    <div class="form-group">
				        	<form:errors path="city" class="text-danger" />
					    </div>
				        <form:input path="city" class="form-control" placeholder="Please Enter City Details!"/>
				    </div>

				    <div class="form-group">
				        <label style="padding:5px 0">State</label>
					    <div class="form-group">
				        	<form:errors path="state" class="text-danger" />
					    </div>
				        <form:input path="state" class="form-control" placeholder="Please Enter State Details!"/>
				    </div>

				    <div class="form-group">
				        <label style="padding:5px 0">Zip Code</label>
					    <div class="form-group">
				        	<form:errors path="zipCode" class="text-danger" />
					    </div>
				        <form:input path="zipCode" class="form-control" placeholder="Please Enter City Zip Code!"/>
				    </div>

					<div class="form-group">
					    <label style="padding: 5px 0">Contact Details</label>
					    <div class="form-group">
					        <form:errors path="phoneNumber" class="text-danger" />
					    </div>
					    <div class="input-group">
					        <form:input path="phoneNumber" id="phoneNumber" class="form-control" placeholder="Enter phone number" onchange="addCountryCodeToPhoneNumber()" />
					    </div>
					</div>

			        <div class="btn btn-outline-success column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:10px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
				    	<input type="submit" value="Add New Patient Adress Today, ${dayCurrentDateTime}!" class="btn btn-outline-success" style="margin: 10px 0; width: 100%; padding: 10px;"/>
					</div>
				</form:form>
			    <div class="btn btn-outline-warning column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:10px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
					<h1 style="width:100%;">
						<a style=" margin:10px 0;width:100%;display:block; padding:10px" href="/mellowHealth/patientsPortal/patients" class="btn btn-outline-warning">
							<c:out value="GO BACK!"/>
						</a>
					</h1>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>