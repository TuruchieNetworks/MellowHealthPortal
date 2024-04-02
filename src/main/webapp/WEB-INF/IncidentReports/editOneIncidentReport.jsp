<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true"%>
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
<title>Edit Incident Report!</title>
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
				<c:out value="Report New Incident Today ${currentDateTime} ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}!"/>
			</h1>
		</a>
			<div class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;">
				<form action="/mellowHealth/incidentReports/editIncidentReport/${incidentReport.id}" class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
				    <label style="padding:5px 10px">Search Patient Name</label>
					<input style="padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName"/>
					<input class="btn btn-outline-primary" type="submit" value="Search Patient"/>
				</form>
				<p style="color:rgba(311, 31, 321, 0.9);">${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth}</p>	
			</div>
		<div class="row">
							            
			<div class="col">
				<form:form action="/mellowHealth/process/incidentReports/editIncidentReport/${incidentReport.id}" method="PATCH" modelAttribute="incidentReport">
				<!-- Add this block to display global errors -->
				<!--form:errors path="*" cssClass="text-danger"/! -->
			       <div class="form-group">
		                <label style="padding:10px 0">Select Registered Patients</label>
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
		                    <c:choose>
		                        <c:when test="${searchedPatientCase[0].patient.id != null && searchedPatientCase[0].patient.patientFirstName.length() > 1}">
		                            <form:option value="${searchedPatientCase[0].patient.id}" label="Searched Mellow Patient: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${searchedPatientAge} yr Old Contact: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} Reported Incidents: ${searchedPatientCase[0].incidentReports.size()} ${dayCurrentDateTime}"/>
		                        </c:when>
		                        <c:otherwise>
		                            <form:option value="${mostRecentPatientCase.patient.id}" label="${mostRecentPatientCase.patient.patientFirstName} ${mostRecentPatientCase.patient.patientLastName} Date Of Birth: ${mostRecentPatientCase.patient.dateOfBirth} ${searchedPatientAge} yr Old ${mostRecentPatientCase.patient.patientAddresses[0].phoneNumber} Reported Incidents: ${mostRecentPatientCase.incidentReports.size()}"/>
		                        </c:otherwise>
		                    </c:choose>
		                </form:select>
		            </div>

			       <div class="form-group">
		                <label style="padding:10px 0">Select Registered Patients</label>
		                	<form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientCaseSelect', 'patientName', 'selectedPatientDiv')">
		                	<c:choose>
		                        <c:when test="${searchedPatientCase != null && searchedPatientCase[0].patient.patientFirstName.length() > 1}">
		                            <form:option value="${searchedPatientCase[0].id}" label="${searchedPatientCase[0].onset} Cheif Complaint: ${searchedPatientCase[0].chiefComplaint} ${searchedPatientCaseCreatedAt} visit"/>
		                        </c:when>
		                        <c:otherwise>
		                            <form:option value="${mostRecentPatientCase.id}" label="${mostRecentPatientCase.onset} Cheif Complaint: ${mostRecentPatientCase.chiefComplaint} ${mostRecentPatientCaseCreatedAt} Visit!"/>
		                       </c:otherwise>
		                    </c:choose>
		                </form:select>
		            </div>

				    <div class="form-group">
				        <label style="padding:5px 0 10px 0">Onset Of Event</label>
					    <div class="form-group">
				        	<form:errors path="onset" class="text-danger" />
					    </div>
				        <form:input type="Date" value="${incidenceOnset}" path="onset" class="form-control" placeholder="Please Enter Date Of Occurence!"/>
				    </div>

					<div class="form-group">
					    <label style="padding: 5px 0">Time Of Occurrence</label>
					    <div class="form-group">
					        <form:errors path="timeOfOccurrence" class="text-danger" />
					    </div>
						<form:select path="timeOfOccurrence" class="form-control" style="cursor:pointer">
					    <form:option value="" class="text-primary">Please Select Time Of Occurence</form:option>
					    <c:forEach var="i" items="${timeFormat}">
					        <c:choose>
					            <c:when test="${i < 3}">
					                <form:option value="${i}:00 AM" class="text-primary">${i}:00 AM</form:option>
					            </c:when>
					            <c:when test="${i >= 3 && i <= 7}">
					                <form:option value="${i}:00 AM" class="text-primary">${i}:00 AM</form:option>
					            </c:when>
					            <c:when test="${i >= 7 && i <= 9}">
					                <form:option value="${i}:00 AM" class="text-warning">${i}:00 AM</form:option>
					            </c:when>
					            <c:when test="${i > 9}">
					                <form:option value="${i}:00 AM" class="text-danger">${i}:00 AM</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					    <c:forEach var="i" items="${timeFormat}">
					        <c:choose>
					            <c:when test="${i < 3}">
					                <form:option value="${i}:00 PM" class="text-primary">${i}:00 PM</form:option>
					            </c:when>
					            <c:when test="${i >= 3 && i <= 7}">
					                <form:option value="${i}:00 PM" class="text-primary">${i}:00 PM</form:option>
					            </c:when>
					            <c:when test="${i >= 7 && i <= 9}">
					                <form:option value="${i}:00 PM" class="text-warning">${i}:00 PM</form:option>
					            </c:when>
					            <c:when test="${i > 9}">
					                <form:option value="${i}:00 PM" class="text-danger">${i}:00 PM</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					</form:select>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0 10px 0">Event</label>
					    <div class="form-group">
					        <form:errors path="event" class="text-danger" />
					    </div>
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;"></div>
					    <form:textarea path="event" class="form-control" placeholder="Please Describe New Event!" style="width:100%; padding:8px; border-radius:5%;" />
					</div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Relieving Agent Taken</label>
					    <div class="form-group">
				        	<form:errors path="relievingAgentTaken" class="text-danger" />
					    </div>
				        <form:textarea path="relievingAgentTaken" class="form-control" placeholder="Please Enter Any Relieving Agent Taken!"/>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Aggravating Agent Taken</label>
					    <div class="form-group">
				        	<form:errors path="aggravatingAgentTaken" class="text-danger" />
					    </div>
				        <form:textarea path="aggravatingAgentTaken" class="form-control" placeholder="Please Enter Any Aggravating Agent Taken!"/>
				    </div>

					<!-- Condition Status -->
					
					<div class="form-group">
				        <label style="padding:5px 0 10px 0">Current Condition Status</label>
					    <div class="form-group">
				        	<form:errors path="conditionStatus" class="text-danger" />
					    </div>
						<form:select path="conditionStatus" class="form-control" style="cursor:pointer">

					        <form:option value="">Please Select Condition Status</form:option>
					    	<form:option value="${incidenceConditionStatus}" class="text-danger">${incidenceConditionStatus}</form:option>
						    <c:forEach var="i" items="${painScale}">
						        <c:choose>
						            <c:when test="${i < 3}">
						                <form:option value="Pain Scale: ${i}: Little To No Pain" class="text-primary">${i} - No Pain</form:option>
						            </c:when>
						            <c:when test="${i >= 3 && i <= 6}">
						                <form:option value="Pain Scale: ${i}: Slightly Discomforting Pain" class="text-warning">${i} - Slightly Tender</form:option>
						            </c:when>
						            <c:when test="${i > 6}">
						                <form:option value="Pain Scale: ${i}: Extreme Pain And Discomfort" class="text-danger">${i} - Extreme Pain</form:option>
						            </c:when>
						        </c:choose>
						    </c:forEach>
						 				            
						</form:select>
					</div>
				    <input type="submit" value="Edit New Incident Report Today ${currentDateTime} " class="btn btn-outline-success" style="margin: 10px 0; width: 100%; padding: 10px;"/>
				</form:form>
				<h1 style="width:100%;">
					<a style=" margin:10px 0;width:100%;display:block; padding:10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-warning">
						<c:out value="CANCEL!"/>
					</a>
				</h1>
			</div>
		</div>
	</div>
	
</body>
</html>