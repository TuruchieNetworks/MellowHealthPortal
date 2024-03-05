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
				<form action="/mellowHealth/currentMedications/newCurrentMedication" class ="btn btn-primary"  style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
				    <label style="padding:5px 10px">Search Patient Name</label>
					<input style="padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName"/>
					<input class="btn btn-outline-primary" type="submit" value="Search Patient"/>
				</form>
				<p style="color:rgba(311, 31, 321, 0.9);">
				<c:forEach items="${searchedPatientCase}" var="patientCase">  
					<c:out value="Mellow Patient Details: ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} Date Of Birth: ${patientCase.patient.dateOfBirth}"/>
				</c:forEach>
				</p>
				<div class="form-group" id="selectedPatientDiv" style="font-weight: bold; margin-top: 5px;"></div>
			</div>
		<div class="row">            
			<div class="col">
				<form:form action="/mellowHealth/process/currentMedications/createNewCurrentMedication" method="POST" modelAttribute="currentMedication">
				<!-- Add this block to display global errors -->
				<!-- form:errors path="*" cssClass="text-danger"/> -->
				<c:choose>
			    <c:when test="${not empty allPatientCasesWithFilter}">
					<div class="form-group">
					    <div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
					    	<label style="padding:10px 0">Select Registered Patients</label>
					    </div>
						<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
			                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
							    <c:forEach items="${allPatientCasesWithFilter}" var="patientCase">  	    
		                            <form:option value="${patientCase.patient.id}" label="Searched Patient- ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} Date Of Birth: ${searchedPatient.dateOfBirth} ${searchedPatientAge} yrs Old"/>
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
							    <c:forEach items="${allPatientCasesWithFilter}" var="patientCase">  
		                            <form:option value="${patientCase.id}" label="${searchedPatientCaseCreatedAt}  ${patientCase.chiefComplaint}"/>                     
			                    </c:forEach>
			                </form:select>
		                </div>
				</c:when>

				<c:otherwise>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				    	<label style="padding:10px 0">Registered Patient</label>						
		                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
                            <form:option value="${matchedPatientCase.patient.id}" label="Mellow Patient Details: ${matchedPatientCase.patient.patientFirstName} ${searchedPatient.patientLastName} ${searchedPatientCaseCreatedAt} Visit Insurance Information: ${matchedPatientCase.insuranceInformation.insuranceId} Provider: ${matchedPatientCase.insuranceInformation.providerName} Date Of Birth: ${searchedPatient.dateOfBirth} ${searchedPatientAge} yrs Old"/> 
		                </form:select>
		            </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
		                <label style="padding:10px 0">Treating Physician</label>		
		                <form:select path="physician.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
	                        <form:option value="${searchedPatientCasePhysiciansList.id}" label="Dr. ${searchedPatientCasePhysiciansList.firstName} ${searchedPatientCasePhysiciansList.lastName} ${searchedPatientCasePhysiciansList.email}"/>
		                </form:select>
	                </div>
					<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				    	<label style="padding:10px 0">Current Condition</label>						
		                <form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
                            <form:option value="${matchedPatientCase.id}" label="${searchedPatientCaseCreatedAt} Visit: ${matchedPatientCase.chiefComplaint} Insured Mellow Patient Details: ${searchedPatient.patientFirstName} ${searchedPatient.patientLastName} Insurance ID: ${searchedPatientCaseInsurance.insuranceId} Date Of Birth: ${searchedPatient.dateOfBirth} ${searchedPatientAge} yrs Old"/> 
		                </form:select>
		            </div>
		        </c:otherwise>
	           	</c:choose>
				<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
				</div>

					<div class="form-group">
					    <label style="padding:5px 0">Drug Name</label>
					    <div class="form-group">
				        	<form:errors path="drugName" class="text-danger" />
					    </div>
					    <form:input class="form-control" path="drugName" placeholder="Please Enter Drug Name!" style="width:100%; padding:8px; border-radius:5%;"/>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Administration Route</label>
					    <div class="form-group">
				        	<form:errors path="administrationRoute" class="text-danger" />
					    </div>
					    <form:input class="form-control" path="administrationRoute" placeholder="Please Enter Administration Route!" style="width:100%; padding:8px; border-radius:5%;"/>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Active Ingredient</label>
					    <div class="form-group">
				        	<form:errors path="activeIngredient" class="text-danger" />
					    </div>
					    <form:input class="form-control" path="activeIngredient" placeholder="Please Enter Active Ingredient!" style="width:100%; padding:8px; border-radius:5%;"/>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Pharmaceutical Form</label>
					    <div class="form-group">
				        	<form:errors path="pharmaceuticalForm" class="text-danger" />
					    </div>
					    <form:input class="form-control" path="pharmaceuticalForm" placeholder="Please Enter Pharmacetical Name!" style="width:100%; padding:8px; border-radius:5%;"/>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Regimen</label>
					    <div class="form-group">
				        	<form:errors path="regimen" class="text-danger" />
					    </div>
					    <form:select path="regimen" class="form-control" style="cursor:pointer">
						    <form:option value="" class="text-primary">Please Select Dose Frequency</form:option>
						    <c:forEach var="i" items="${doseHrFrequency}">
						        <c:choose>
						            <c:when test="${i < 3}">
						                <form:option value="${i} - Orally" class="text-primary">${i} - Orally</form:option>
						            </c:when>
						            <c:when test="${i >= 3 && i <= 4}">
						                <form:option value="${i} - Orally" class="text-warning">${i} - Orally</form:option>
						            </c:when>
						            <c:when test="${i > 4}">
						                <form:option value="${i} - Orally" class="text-danger">${i} - Orally</form:option>
						            </c:when>
						        </c:choose>
						    </c:forEach>
						    <c:forEach var="i" items="${doseHrFrequency}">
						        <c:choose>
						            <c:when test="${i < 3}">
						                <form:option value="${i} - Orally With Food" class="text-primary">${i} - Orally With Food</form:option>
						            </c:when>
						            <c:when test="${i >= 3 && i <= 4}">
						                <form:option value="${i} - Orally With Food" class="text-warning">${i} - Orally With Food</form:option>
						            </c:when>
						            <c:when test="${i > 4}">
						                <form:option value="${i} - Orally With Food" class="text-danger">${i} - Orally With Food</form:option>
						            </c:when>
						        </c:choose>
						    </c:forEach>
						</form:select>
					</div>

					<div class="form-group">
					    <label style="padding: 5px 0">Dose</label>
					    <div class="form-group">
					        <form:errors path="dosage" class="text-danger" />
					    </div>
						<form:select path="dosage" class="form-control" style="cursor:pointer">
					    <form:option value="" class="text-primary">Please Select Dosage</form:option>
					    <c:forEach var="i" items="${frequencyFormat}">
					        <c:choose>
					            <c:when test="${i < 2}">
					                <form:option value="${i} - Packs" class="text-primary">${i} - Packs</form:option>
					            </c:when>
					            <c:when test="${i >= 2 && i <= 35}">
					                <form:option value="${i} - Packs" class="text-primary">${i} - Packs</form:option>
					            </c:when>
					            <c:when test="${i >= 35 && i <= 70}">
					                <form:option value="${i} - Packs" class="text-warning">${i} - Packs</form:option>
					            </c:when>
					            <c:when test="${i > 70}">
					                <form:option value="${i} - Packs" class="text-danger">${i} - Packs</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					</form:select>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Dosage Frequency</label>
					    <div class="form-group">
					        <form:select path="dosageFrequency" class="form-control" style="cursor:pointer">
					    		<option value="" class="text-primary">Please Select Dosage</option>>
					            <option value="Per hr" class="text-warning">Per Hour</option>
					            <option value="Every 2Hrs" class="text-primary">Every 2 Hours</option>
					            <option value="Every 4Hrs" class="text-primary">Every 4 Hours</option>
					            <option value="Every 6Hrs" class="text-primary">Every 6 Hours</option>
					            <option value="Every 8Hrs" class="text-primary">Every 8 Hours</option>
					            <option value="Every 12Hrs" class="text-warning">Every 12 Hours</option>
					            <option value="Once Per Day" class="text-warning">Once Per Day</option>
					            <option value="One Every 2 Days" class="text-warning">Once Every 2 Days</option>
					            <option value="One Every 3 Days" class="text-danger">Once Every 3 Days</option>
					            <option value="Once Per Week" class="text-danger">Once Per Week</option>
					            <option value="One Every 2 Weeks" class="text-danger">Once Every 2 Weeks</option>
					            <option value="Once Per 2 Month" class="text-danger">Once Per Month</option>
					        </form:select>
					    </div>
					</div>

					<div class="form-group">
					    <label style="padding: 5px 0">Dosage Quantity</label>
					    <div class="form-group">
					        <form:errors path="dosageQuantity" class="text-danger" />
					    </div>
						<form:select path="dosageQuantity" class="form-control" style="cursor:pointer">
					    <c:forEach var="i" items="${doseFormat}">
					        <c:choose>
					            <c:when test="${i < 5}">
					                <form:option value="${i}" class="text-primary">${i}</form:option>
					            </c:when>
					            <c:when test="${i >= 5 && i <= 7}">
					                <form:option value="${i}" class="text-warning">${i}</form:option>
					            </c:when>
					            <c:when test="${i > 7}">
					                <form:option value="${i}" class="text-danger">${i}</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					</form:select>
					</div>

					<div class="form-group">
					    <label style="padding: 5px 0">Dosage Frequency Per Hour</label>
					    <div class="form-group">
					        <form:errors path="dosageFrequencyPerHour" class="text-danger" />
					    </div>
						<form:select path="dosageFrequencyPerHour" class="form-control" style="cursor:pointer">
					    <c:forEach var="i" items="${frequencyFormat}">
					        <c:choose>
					            <c:when test="${i < 5}">
					                <form:option value="${i}" class="text-primary">${i} - Per Hour</form:option>
					            </c:when>
					            <c:when test="${i >= 5 && i <= 7}">
					                <form:option value="${i}" class="text-warning">${i} - Per Hour</form:option>
					            </c:when>
					            <c:when test="${i > 7}">
					                <form:option value="${i}" class="text-danger">${i} - Per Hour</form:option>
					            </c:when>
					        </c:choose>
					    </c:forEach>
					</form:select>
					</div>

					<div class="form-group">
					    <label style="padding:5px 0">Current Medication</label>
					    <div class="form-group">
				        	<form:errors path="currentlyMedicating" class="text-danger" />
					    </div>
					    <form:select path="currentlyMedicating" class="form-control" style="cursor:pointer">
                            <form:option value="" label="-- Please Select Treatment Status"/>
                            <form:option value="true" label="Currently Medicating"/>
                            <form:option value="false" label="Not Medicating"/>
		                </form:select>
		            </div>		

				    <div class="form-group">
				        <label style="padding:5px 0">Start Date</label>
					    <div class="form-group">
				        	<form:errors path="startDate" class="text-danger" />
					    </div>
				        <form:input type="date" path="startDate" class="form-control" placeholder="Please Enter Start Date!"></form:input>
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