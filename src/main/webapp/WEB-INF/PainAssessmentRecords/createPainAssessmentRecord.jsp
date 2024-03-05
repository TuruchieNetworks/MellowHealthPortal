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
		<a style="width: 100%; display:block; padding:0px;color:brown;text-decoration:none;" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }">
			<h1 style="color:azure;border-bottom:2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">Register A New Patient Visit Today, <c:out value="${currentDateTime} ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>!</h1>
		</a>
		<div class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;">
			<form action="/mellowHealth/painAssessments/newPainAssessment" class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
			    <label style="padding:5px 10px">Search Patient Name</label>
				<input style="padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName"/>
				<input class="btn btn-outline-primary" type="submit" value="Search Patient"/>
			</form>
			<div class="form-group" id="selectedPatientDiv" style="font-weight: bold; margin-top: 5px;">
				<p style="color:rgba(311, 31, 321, 0.9);">
					<c:forEach items="${allPatientCasesWithFilter}" var="searchedSinglePhysicalAssessmentsList">  
						<c:out value="${searchedSinglePhysicalAssessmentsList.patient.patientFirstName} ${searchedSinglePhysicalAssessmentsList.patient.patientLastName} Date Of Birth: ${searchedSinglePhysicalAssessmentsList.patient.dateOfBirth}"/>
					</c:forEach>
				</p>
			</div>
		</div>
		<div class="row">            
			<div class="col">
				<form:form action="/mellowHealth/process/painAssessments/createNewPainAssessment" method="POST" modelAttribute="painAssessment">
				<!-- Add this block to display global errors -->
				<!--form:errors path="*" cssClass="text-danger"/!-->
				<c:choose>
			    <c:when test="${not empty allPatientCasesWithFilter}">
					<div class="form-group">
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
					    	<label style="padding:10px 0">Select Registered Patients</label>
					    </div>
						<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
			                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
							    <c:forEach items="${allPatientCasesWithFilter}" var="patientCase">  
				                    <c:choose>
				                        <c:when test="${allPatientCasesWithFilter.size() < 1}">
				                        	<form:option value="${matchedPatientCase.patient.id}" label="${searchedPatient.patientFirstName} ${searchedPatient.patientLastName}"/>
				                        </c:when>
				                        <c:otherwise>
				                            <form:option value="${patientCase.patient.id}" label="Searched Patient- ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} Date Of Birth: ${searchedPatient.dateOfBirth} ${searchedPatientAge} yrs Old"/>
				                        </c:otherwise>
				                    </c:choose>
			                    </c:forEach>
			                </form:select>
			            </div>
						<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						<label style="padding:10px 0">Treating Physician</label>	
			                <form:select path="physician.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
							    <c:forEach items="${allPatientCasesWithFilter}" var="patientCase">  
				                    <c:choose>
				                        <c:when test="${allPatientCasesWithFilter.size() < 1}">
				                        	<form:option value="${matchedPatientCase.physician.id}" label="Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName}"/>
				                        </c:when>
				                        <c:otherwise>
				                            <form:option value="${patientCase.physician.id}" label="Dr. ${matchedDiagnosticRecords} ${patientCase.physician.firstName} ${patientCase.physician.lastName} ${patientCase.physician.email}"/>
				                        </c:otherwise>
				                    </c:choose>
			                    </c:forEach>
			                </form:select>
		                </div>
		            </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						<label style="padding:10px 0">Treating Physician</label>	
		                <form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
							<c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase" varStatus="status">
							    <c:if test="${status.index < patientCaseCreatedAtList.size() and status.index < patientCaseDayCreatedAtList.size()}">
						        <!-- Access the current element from each collection using status.index -->
						        <c:set var="patientCase" value="${matchedPatientCase}" />
						        <c:set var="patientCaseCreatedAt" value="${patientCaseCreatedAtList[status.index]}" />
						        <c:set var="patientCaseDayCreatedAt" value="${patientCaseCreatedAtList[status.index]}" />
		                            <form:option value="${patientCase.patient.id}" label="${patientCaseCreatedAt} ${patientCase.chiefComplaint}"/>    
		                        </c:if>                 
			                </c:forEach>
		                </form:select>
	                </div>
						<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						<label style="padding:10px 0">Treating Physician</label>	
			     		<form:select path="physicalAssessment.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
			                <c:forEach items="${searchedSinglePhysicalAssessmentsList}" var="assessment">
							    <form:option value="${assessment.id}" label="${physicalAssessmentCreatedAtList}- General Appearance ${assessment.generalAppearanceDescription} Heart Description: ${assessment.heartDescription} Day ${assessmentHistories} History"/>
							</c:forEach>
						</form:select>
	                </div>
				</c:when>

				<c:otherwise>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				    	<label style="padding:10px 0">Registered Patient</label>						
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
                            <form:option value="${matchedPatientCase.patient.id}" label="Mellow Patient Details: ${matchedPatientCase.patient.patientFirstName} ${matchedPatientCase.patient.patientLastName} ${searchedPatientCaseCreatedAt} Visit Insurance Information: ${matchedPatientCase.insuranceInformation.insuranceId} Provider: ${matchedPatientCase.insuranceInformation.providerName} Date Of Birth: ${matchedPatientCase.patient.dateOfBirth} ${searchedPatientAge} yrs Old"/> 
		                </form:select>
		            </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
		                <label style="padding:10px 0">Treating Physician</label>		
		                <form:select path="physician.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
	                        <form:option value="${matchedPatientCase.physician.id}" label="Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName} ${matchedPatientCase.physician.email}"/>
		                </form:select>
	                </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				    	<label style="padding:10px 0">Current Condition</label>						
		                <form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
		                <c:forEach items="${patientCaseCreatedAtList}" var="patientCaseCreatedAt"> 
                            <form:option value="${matchedPatientCase.id}" label="${patientCaseCreatedAt} Visit: ${matchedPatientCase.chiefComplaint} Insured Mellow Patient Details: ${searchedPatient.patientFirstName} ${searchedPatient.patientLastName} Insurance ID: ${searchedPatientCaseInsurance.insuranceId} Date Of Birth: ${searchedPatient.dateOfBirth} ${searchedPatientAge} yrs Old"/> 
		                </c:forEach>
		                </form:select>
		            </div>
						<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						<label style="padding:10px 0">Treating Physician</label>	
			                <form:select path="physicalAssessment.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
							    <c:forEach items="${searchedSinglePhysicalAssessmentsList}" var="assessment">
		                            <form:option value="${assessment.id}" label="${physicalAssessmentCreatedAtList}- General Appearance ${assessment.generalAppearanceDescription} Heart Description: ${assessment.heartDescription} Day ${assessmentHistories} History"/>
			                    </c:forEach>
			                </form:select>
		                </div>
		        </c:otherwise>
	           	</c:choose>
				<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				</div>
				
				<!-- Description -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Description</label>
				    <div class="form-group">
				        <form:errors path="description" class="text-danger" />
				    </div>
				    <form:textarea  class="form-control" path="description" placeholder="Enter Pain Description (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>
				
				<!-- Location -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Location</label>
				    <div class="form-group">
				        <form:errors path="location" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="location" placeholder="Enter Pain Location (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>
				
				<!-- Travel Location -->
				<div class="form-group">
				    <label style="padding:5px 0">Travel Location</label>
				    <form:input class="form-control" path="travelLocation" placeholder="Enter Travel Location" style="width:100%; padding:8px; border-radius:5%;" />
				</div>

				<!-- Duration -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Duration</label>
				    <div class="form-group">
				        <form:errors path="duration" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="duration" placeholder="Enter Pain Duration (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>
				
				<!-- Frequency -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Frequency</label>
				    <div class="form-group">
				        <form:errors path="frequency" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="frequency" placeholder="Enter Pain Frequency (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>

				<!-- Start Time -->
				<div class="form-group">
				    <label style="padding:5px 0">Start Time</label>
				    <div class="form-group">
				        <form:errors path="startTime" class="text-danger" />
				    </div>
				    <form:select path="startTime" class="form-control" style="cursor:pointer">
				        <form:option value="">Please Select Time</form:option>
				        <c:forEach var="i" items="${timeFormat}">
				            <form:option value="${i}:00 AM">${i}:00 AM</form:option>
				            <form:option value="${i}:15 AM">${i}:15 AM</form:option>
				            <form:option value="${i}:30 AM">${i}:30 AM</form:option>
				            <form:option value="${i}:45 AM">${i}:45 AM</form:option>
				        </c:forEach>
				        <c:forEach var="i" items="${timeFormat}">
				            <form:option value="${i}:00 PM">${i}:00 PM</form:option>
				            <form:option value="${i}:15 PM">${i}:15 PM</form:option>
				            <form:option value="${i}:30 PM">${i}:30 PM</form:option>
				            <form:option value="${i}:45 PM">${i}:45 PM</form:option>
				        </c:forEach>
				    </form:select>
				</div>

				<!-- Pain Scale -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Scale</label>
					<form:select path="painScale" class="form-control" style="cursor:pointer">
				        <form:option value="">Please Select Pain Grade</form:option>
					    <c:forEach var="i" items="${painScaleValue}">
					        <c:choose>
					            <c:when test="${i < 5}">
					                <form:option value="${i}" class="text-primary">${i} - No Pain</form:option>
					            </c:when>
					            <c:when test="${i >= 5 && i <= 7}">
					                <form:option value="${i}" class="text-warning">${i} - Slightly Tender</form:option>
					            </c:when>
					            <c:when test="${i > 7}">
					                <form:option value="${i}" class="text-danger">${i} - Extreme Pain</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					</form:select>
				</div>
				
				<!-- Characteristics -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Characteristics</label>
				    <div class="form-group">
				        <form:errors path="characteristics" class="text-danger" />
				    </div>
				    <form:textarea class="form-control" path="characteristics" placeholder="Enter Pain Characteristics (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>

				<!-- Trigger Factor -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Trigger Factor</label>
				    <div class="form-group">
				        <form:errors path="triggerFactor" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="triggerFactor" placeholder="Enter Pain Trigger Factor (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>
				
				<!-- Aggravating Factor -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Aggravating Factor</label>
				    <div class="form-group">
				        <form:errors path="aggravatingFactor" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="aggravatingFactor" placeholder="Enter Pain Aggravating Factor (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>
				
				<!-- Relieving Factor -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Relieving Factor</label>
				    <div class="form-group">
				        <form:errors path="relievingFactor" class="text-danger" />
				    </div>
				    <form:input class="form-control" path="relievingFactor" placeholder="Enter Pain Relieving Factor (1-255 characters)" style="width:100%; padding:8px; border-radius:5%;" />
				</div>

				<!-- Pain Status -->
				<div class="form-group">
				    <label style="padding:5px 0">Pain Status</label>
				    <div class="form-group">
				        <form:errors path="hasPain" class="text-danger" />
				    </div>
				    <form:select path="hasPain" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Current Pain Status"/>
				        <form:option value="true" label="In Pain"/>
				        <form:option value="false" label="Not In Pain"/>
				    </form:select>
				</div>

				<!-- Previous Episode -->
				<div class="form-group">
				    <label style="padding:5px 0">Previous Episode</label>
				    <div class="form-group">
				        <form:errors path="previousEpisode" class="text-danger" />
				    </div>
				    <form:select path="previousEpisode" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Previous Episode Status"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
				<!-- Difficulty Urinating -->
				<div class="form-group">
				    <label style="padding:5px 0">Difficulty Urinating</label>
				    <div class="form-group">
				        <form:errors path="difficultyUrinating" class="text-danger" />
				    </div>
				    <form:select path="difficultyUrinating" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Difficulty Urinating"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
				<!-- Urinary Incontinence -->
				<div class="form-group">
				    <label style="padding:5px 0">Urinary Incontinence</label>
				    <div class="form-group">
				        <form:errors path="urinaryIncontinence" class="text-danger" />
				    </div>
				    <form:select path="urinaryIncontinence" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Urinary Incontinence"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
				<!-- Fecal Incontinence -->
				<div class="form-group">
				    <label style="padding:5px 0">Fecal Incontinence</label>
				    <div class="form-group">
				        <form:errors path="fecalIncontinence" class="text-danger" />
				    </div>
				    <form:select path="fecalIncontinence" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Fecal Incontinence"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
				<!-- Fever Night Sweats Weight Loss -->
				<div class="form-group">
				    <label style="padding:5px 0">Fever Night Sweats Weight Loss</label>
				    <div class="form-group">
				        <form:errors path="feverNightSweatsWeightLoss" class="text-danger" />
				    </div>
				    <form:select path="feverNightSweatsWeightLoss" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Fever Night Sweats Weight Loss"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
				<!-- History Of Back Pain -->
				<div class="form-group">
				    <label style="padding:5px 0">History Of Back Pain</label>
				    <div class="form-group">
				        <form:errors path="historyOfBackPain" class="text-danger" />
				    </div>
				    <form:select path="historyOfBackPain" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select History Of Back Pain"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>

	            <div class="form-group">
	                <label style="padding:5px 0">Treatment Plan</label>
	                <form:textarea path="relatedSymptoms" class="form-control" rows="5" maxlength="500" placeholder="Please Provide Any Related Symptom"></form:textarea>
	                <form:errors path="relatedSymptoms" class="text-danger" />
	            </div>
				
				<!-- Illicit Drug Use -->
				<div class="form-group">
				    <label style="padding:5px 0">Illicit Drug Use</label>
				    <div class="form-group">
				        <form:errors path="illicitDrugUse" class="text-danger" />
				    </div>
				    <form:select path="illicitDrugUse" class="form-control" style="cursor:pointer">
				        <form:option value="" label="-- Please Select Illicit Drug Use"/>
				        <form:option value="true" label="Yes"/>
				        <form:option value="false" label="No"/>
				    </form:select>
				</div>
				
		        <div class="btn btn-outline-success column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:10px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			    	<input type="submit" value="Create New Pain Assessment Today, ${dayCurrentDateTime}!" class="btn btn-outline-success" style="margin: 10px 0; width: 100%; padding: 10px;"/>
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