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
<link rel="stylesheet" href="/styles/styles.css"/>
<link rel="stylesheet" href="/styles/standaloneStyles.css"/>
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
	<h1 style="color:azure;border-bottom: 2px solid aliceblue;font-weight:bold;font-size:32px;background:chocolate;padding:10px;font-family:cursive;text-align:center;">WELCOME TO MELLOW HEALTH!</h1>
	<div class="container-fluid p-6" style=" text-align:center;">
   	<c:choose>
        <c:when test="${empty oneSearchedPatientCase}">
			<a style="width: 100%; display:block; padding:0px;color:brown;text-decoration:none;" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }">
				<h1 style="color:azure;border-bottom:2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">
					<c:out value="Register Physical Assessment Record Today ${currentDateTime}"/> <c:out value="${loggedInPatient.patientFirstName}"/> <c:out value="${loggedInPatient.patientLastName}!"/>
				</h1>
			</a>
        </c:when>
		<c:otherwise>
			<a style="width: 100%; display:block; padding:0px;color:brown;text-decoration:none;" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }">
				<h1 style="color:azure;border-bottom:2px solid aliceblue;background:rgba(2.11, 24.5, 121, 0.9);border-radius:5%;font-weight:bold;font-size:22px;padding:10px;font-family:cursive;text-align:center;">
					<c:out value="Register Physical Assessment Record Today ${currentDateTime}"/> <c:out value="${oneSearchedPatientCase.patient.patientFirstName}"/> <c:out value="${oneSearchedPatientCase.patient.patientLastName}!"/>
				</h1>
			</a>
		</c:otherwise>
	</c:choose>
		
			<div class ="btn btn-primary"  style="width:100%;display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;">
				<form action="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" class ="btn btn-primary"  style="width:100%;display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;">
				    <label style="padding:5px 10px">Search Patient Name</label>
					<input style="width: 40%;padding:5px;border-radius:7%;margin:0 5px" type="text" name="searchedPatientName" placeholder="Please Enter Patient's Name"/>
					<input style="width:25%;" class="btn btn-outline-primary" type="submit" value="Search Patient"/>
				</form>
			</div>   
		<div class="row">
							            
			<div class="col">
				<form:form action="/mellowHealth/diagnosis/process/physicalAssessments/createNewPhysicalAssessment" method="POST" modelAttribute="physicalAssessment">
				<!-- Add this block to display global errors -->
				<!-- form:errors path="*" cssClass="text-danger"/-->
			    	<c:choose>
				        <c:when test="${empty searchedPatientCase}">
							<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						    	<label style="padding:10px 0">Registered Patient</label>	
				                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
								    <c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase">  
										<form:option value="${matchedPatientCase.patient.id}" label="Patient Name: ${matchedPatientCase.patient.patientFirstName} ${matchedPatientCase.patient.patientLastName} Date Of Birth- ${matchedPatientCase.patient.dateOfBirth}: ${searchedPatientAge} Yr Old ${matchedPatientCase.patient.race} ${matchedPatientCase.patient.gender}"/>
				                    </c:forEach>
				                </form:select>
				            </div>
				            
							<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;"></div>
				            
					     	<div class="form-group">
						        <label style="padding:5px 0">Treating Physician</label>
				                <form:select path="physician.id" class="form-control" style="cursor:pointer" id="patientSelect">
								    <c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase">  
				                   		<form:option value="${matchedPatientCase.physician.id}" label="${searchedPatientCaseCreatedAt} Treating Physician: Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName} Contact Details: ${matchedPatientCase.physician.email}"/>
				                    </c:forEach>
				                </form:select>
				            </div>

					     	<div class="form-group">
						        <label style="padding:5px 0">Patient Case</label>
				                <form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect">
								    <c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase">  
				                   		<form:option value="${matchedPatientCase.id}" label="Chief Complaint:- ${matchedPatientCase.chiefComplaint} ${matchedPatientCase.onset} Incident. Treating Physician: Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName}, ${searchedPatientCaseCreatedAt} Visit"/>
				                    </c:forEach>
				                </form:select>
				            </div>
				        </c:when>
	        
				        <c:otherwise>
							<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;">
						    	<label style="padding:10px 0">Registered Patient</label>						
				                <form:select path="patient.id" class="form-control" style="cursor:pointer" id="patientSelect" onchange="updateSelectedPatient('patientSelect', 'patientName', 'selectedPatientDiv')">
									<form:option value="${searchedPatientCase[0].patient.id}" label="Patient Name: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth- ${searchedPatientCase[0].patient.dateOfBirth}: ${searchedPatientAge} Yr Old ${searchedPatientCase[0].patient.race} ${searchedPatientCase[0].patient.gender}"/>
				                </form:select>
				            </div>

							<div class="form-group" id="selectedPatientDiv" style="font-weight:bold; margin-top: 5px;"></div>            
					     	<div class="form-group">
						        <label style="padding:5px 0">Treating Physician</label>
				                <form:select path="physician.id" class="form-control" style="cursor:pointer" id="patientSelect">
								    <c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase">  
				                   		<form:option value="${matchedPatientCase.physician.id}" label="${searchedPatientCaseCreatedAt} Treating Physician: Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName} Contact Details: ${matchedPatientCase.physician.email}"/>
				                    </c:forEach>
				                </form:select>
				            </div>
		
					     	<div class="form-group">
						        <label style="padding:5px 0">Patient Case</label>
				                	<form:select path="patientCase.id" class="form-control" style="cursor:pointer" id="patientSelect">
				                	<c:forEach items="${allPatientCasesWithFilter}" var="matchedPatientCase"> 
			                   			<form:option value="${matchedPatientCase.id}" label="Chief Complaint: ${matchedPatientCase.chiefComplaint} ${matchedPatientCase.onset} Incident. Treating Physician: Dr. ${matchedPatientCase.physician.firstName} ${matchedPatientCase.physician.lastName}, ${searchedPatientCaseCreatedAt} Visit"/>
			                   		</c:forEach>
				                </form:select>
				            </div>
			            </c:otherwise>
		           	</c:choose>
					<div class="form-group">
				        <label style="padding:5px 0">General Appearance</label>
					    <div class="form-group">
				        	<form:errors path="generalAppearanceDescription" class="text-danger" />
					    </div>
				        <form:textarea path="generalAppearanceDescription" class="form-control" placeholder="Please Describe General Appearance!"></form:textarea>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0">General Appearance</label>
					    <div class="form-group">
				        	<form:errors path="oralCavityDescription" class="text-danger" />
					    </div>
				        <form:textarea path="oralCavityDescription" class="form-control" placeholder="Please Describe Oral Cavity!"></form:textarea>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0">Extremities Appearance</label>
					    <div class="form-group">
				        	<form:errors path="extremitiesDescription" class="text-danger" />
					    </div>
				        <form:textarea path="extremitiesDescription" class="form-control" placeholder="Please Describe General Extrimities During This Visit!"></form:textarea>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0">HEENT Description</label>
					    <div class="form-group">
				        	<form:errors path="heentDescription" class="text-danger" />
					    </div>
				        <form:textarea path="heentDescription" class="form-control" placeholder="Please enter HEENT Findings During This Visit!"></form:textarea>
				    </div>
					
					<div class="form-group">
					    <label style="padding:5px 0">Neurologic Description</label>
					    <div class="form-group">
					        <form:errors path="neurologicDescription" class="text-danger" />
					    </div>
					    <form:textarea path="neurologicDescription" class="form-control" placeholder="Please Enter Neurologic Description During This Visit!"></form:textarea>
					</div>

					<div class="form-group">
				        <label style="padding:5px 0">Neck Description</label>
					    <div class="form-group">
				        	<form:errors path="neckDescription" class="text-danger" />
					    </div>
				        <form:textarea path="neckDescription" class="form-control" placeholder="Please Enter Neck Description During This Visit!"></form:textarea>
				    </div>

					<div class="form-group">
				        <label style="padding:5px 0">Lymph Nodes</label>
					    <div class="form-group">
				        	<form:errors path="lymphNodesDescription" class="text-danger" />
					    </div>
				        <form:textarea path="lymphNodesDescription" class="form-control" placeholder="Please Describe Lymph Node Examination During This Visit!"></form:textarea>
				    </div>

					<div class="form-group">
					    <label style="padding:5px 0">Skin Description</label>
					    <div class="form-group">
					        <form:errors path="skinDescription" class="text-danger" />
					    </div>
					    <form:textarea path="skinDescription" class="form-control" placeholder="Please Enter Skin Description During This Visit!"></form:textarea>
					</div>
					
					<div class="form-group">
					    <label style="padding:5px 0">Lungs Description</label>
					    <div class="form-group">
					        <form:errors path="lungsDescription" class="text-danger" />
					    </div>
					    <form:textarea path="lungsDescription" class="form-control" placeholder="Please Enter lungs Descriptions During This Visit!"></form:textarea>
					</div>
					
					<div class="form-group">
					    <label style="padding:5px 0">Heart Description</label>
					    <div class="form-group">
					        <form:errors path="heartDescription" class="text-danger" />
					    </div>
					    <form:textarea path="heartDescription" class="form-control" placeholder="Please Enter Heart Descriptions During This Visit!"></form:textarea>
					</div>
					
					<div class="form-group">
					    <label style="padding:5px 0">Abdomen Description</label>
					    <div class="form-group">
					        <form:errors path="abdomenDescription" class="text-danger" />
					    </div>
					    <form:textarea path="abdomenDescription" class="form-control" placeholder="Please Enter Abdominal Descriptions During This Visit!"></form:textarea>
					</div>

				<div class="unwrapped-inner-column-card btn btn-outline-primary" style="align-items:center;text-align:center;margin:10px 0; padding:5px;background:rgba(1.33, 0.64, 0.60, 0.9);">
					<p class="unwrapped-inner-column-card btn btn-outline-primary">
						<input type="submit" value="Add New Physical Assessment Record" class="btn btn-outline-success" style=" width: 100%; padding: 10px;background:rgba(1.33, 0.64, 0.60, 0.9)"/>
					</p>
				</div>
				</form:form>
				<div class="unwrapped-inner-column-card btn btn-outline-warning" style="align-items:center;text-align:center;margin:5px 0;background:rgba(1.33, 0.64, 0.60, 0.9);">
					<p class="unwrapped-inner-column-card btn btn-outline-warning" style="">
						<a style="width:100%;display:block; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-warning">
							<c:out value="CANCEL!"/>
						</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html> 