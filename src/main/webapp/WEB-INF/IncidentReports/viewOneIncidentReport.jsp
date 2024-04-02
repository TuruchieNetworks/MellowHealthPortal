<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
<!-- YOUR own local CSS -->
    <link rel="stylesheet" href="/styles/standaloneStyles.css"/>
    <link rel="stylesheet" href="/styles/patientCaseStyles.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Mellow Health Physicians Dashboard!</title>
</head>
<body class="container-fluid p-8" style="
 	<c:choose>
	     <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			 color: khaki;background:aqua;background: rgba(1.3, 290.64, 360, 0.9);
	     </c:when>
	     <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
			 color:pink; background: rgba(62.13, 110.123, 380.160, 0.9);
	     </c:when>
	     <c:otherwise>
	         color: rgb(211, 180, 255);background:rgba(110.2, 100.4, 336.6, 0.9);
	     </c:otherwise>
	</c:choose>font-family:cursive;">
	<h1 style="text-align:center;border-bottom: 2px solid chocolate;color: brown; font-family:fantasy;background:rgba(10.2, 3.3, 3.6, 0.9);margin-top:5px;border-radius:5%;">
		<a href="/mellowHealth/insuranceRecords/${mostRecentInsuranceReport.id}" style=" margin: 0 15px 0 0; display:block; padding: 10px;color: khaki;text-decoration:none;font-size:24px;">
			<c:out value="Welcome To Your ${mostRecentInsuranceReport.providerName} Incident Report Dashboard ${loggedInPatient.patientFirstName} ${currentDateTime}!"/>
		</a>
	</h1>
	<table class="table table-dark" style="text-align:center;border-radius:5%;">
		  <thead>
		    <tr>
		      <th scope="col">Id</th>
		      <th scope="col">Physical Exam</th>
		      <th scope="col">Diagnostic WorkUp</th>
		      <th scope="col">Differential Diagnosis</th>
		      <th scope="col">Status</th>
		      <th scope="col">Actions</th>
		   </tr>
		  </thead>
		<tbody>
		</tbody>
		</table>
		<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;">
		    <form action="/mellowHealth/incidentReports/${oneIncidentReport.id}" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
		    <c:choose>
			<c:when test="${empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
					    <c:out value="Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${loggedInPatient.patientVitalRecords.size()} Patient Vital Records Today ${dayCurrentDateTime} "/>
					</a>
			    </p>
			</c:when>
			<c:otherwise>
			   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		<c:choose>
					<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].patientVitalRecords.size()} Patient Vital Records!"/>
						</a>
					</c:when>
					<c:otherwise>
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
					    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${searchedPatientCase[0].patient.patientVitalRecords.size()} Patient Vital Records!"/>
						</a>
					</c:otherwise>
				</c:choose>
			    </p>
			</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${oneIncidentReport.patient.id == loggedInPatient.id}">
		<div class="main-container-column btn btn-outline-success" style="width:100%;display:flex;align-items:center;flex-direction:column;padding:12px;background:rgba(1.33, 10.64, 0.60, 0.9);">
			<div class="btn btn-outline-primary column-card" style="display:flex;justify-content:center;align-items:center;text-align:center;padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);width:100%;">
			     <div class="inner-column-card btn btn-outline-success" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:5px;">
			      	<c:out value="Incidence ID: ${oneIncidentReport.id} Incidence Onset: ${oneIncidentReport.onset} Day: ${incidentOnsetDaysHistory} Progress"/>
			         <a class="inner-column-card btn btn-outline-success" href="/mellowHealth/incidentReports/${oneIncidentReport.id}"style="text-decoration:none; color:aqua;background:rgba(1.33, 0.64, 0.60, 0.9);">
			           <c:out value="${oneIncidentReport.timeOfOccurrence} Event: ${oneIncidentReport.event} ${incidentReportDayCreatedAt} Record"/>
			         </a>
			      </div>
				  <div class="column-card btn btn-outline-danger" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:5px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			      	<c:out value="Respiratory Rate: ${oneIncidentReport.patientCase.patientVitalRecords[0].respiratoryRate} Pulse Rate: ${oneIncidentReport.patientCase.patientVitalRecords[0].pulseRate}"/>
			         <a href="/mellowHealth/patientVitalRecords/${oneIncidentReport.patientCase.patientVitalRecords[0].id}" class="inner-column-card 
					    <c:choose>
					        <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].heartRate < 70}">
					            btn btn-outline-primary
					        </c:when>
					        <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].heartRate > 70 && incidence.patientCase.patientVitalRecords[0].heartRate < 80}">
					            btn btn-outline-warning
					        </c:when>
					        <c:otherwise>
					            btn btn-outline-danger
					        </c:otherwise>
					    </c:choose>"
					    style="text-decoration:none;background:rgba(1.33, 0.64, 0.60, 0.9);">
					    <c:out value="Relieving Agent: ${oneIncidentReport.relievingAgentTaken}- ${oneIncidentReport.onset} ${incidentOnsetDaysHistory} Days History"/>
					</a>

			       </div>
		       </div>
			   <div class="inner-column-card 					    
			   <c:choose>
			        <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].heartRate < 70}">
			            btn btn-outline-primary
			        </c:when>
			        <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].heartRate > 70 && incidence.patientCase.patientVitalRecords[0].heartRate < 80}">
			            btn btn-outline-warning
			        </c:when>
			        <c:otherwise>
			            btn btn-outline-danger
			        </c:otherwise>
			    </c:choose>" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;padding:10px;background:rgba(0.9, 10.1, 0.3, 0.9);">
		          <a href="/mellowHealth/patientVitalRecords/${oneIncidentReport.patientCase.patientVitalRecords[0].id}" style="text-decoration:none; 
					  <c:choose>
					      <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
							 color: khaki;
					      </c:when>
					      <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
							 color:pink;
					      </c:when>
					      <c:otherwise>
					          color: rgb(211, 180, 255); color;
					      </c:otherwise>
		              </c:choose>">
		              <p class="inner-column-card">
			              <c:out value="Current Condition Status: ${oneIncidentReport.conditionStatus} Recorded Blood Pressure: ${oneIncidentReport.patientCase.patientVitalRecords[0].systolicBloodPressure}/${oneIncidentReport.patientCase.patientVitalRecords[0].diastolicBloodPressure}mmHg "/>
		              </p>
		              <c:out value="Pulse Rate: ${oneIncidentReport.patientCase.patientVitalRecords[0].pulseRate}bpm Respiratory Rate: ${oneIncidentReport.patientCase.patientVitalRecords[0].respiratoryRate}bpm "/>
	             </a>
		         <a href="/mellowHealth/incidentReports" style="text-decoration:none; 
					 <c:choose>
					     <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
							  color: khaki;
					     </c:when>
					     <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
							  color:pink;
					     </c:when>
					     <c:otherwise>
					          color: rgb(211, 180, 255); color;
					     </c:otherwise>
		               </c:choose>">
		               <div style="background:rgba(1.33, 0.64, 0.60, 0.9);align-items:center;" class="column-card
					     <c:if test="${bmi <= 18.4}">
							  btn btn-outline-warning
					     </c:if>
					     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
							  btn btn-outline-success
					     </c:if>
					     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
							  btn btn-outline-danger
					     </c:if>
		               ">
			            <p class="inner-column-card
							     <c:if test="${bmi <= 18.4}">
									  btn btn-outline-warning
							     </c:if>
							     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
									  btn btn-outline-success
							     </c:if>
							     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
									  btn btn-outline-danger
							     </c:if>" style="background:rgba(50.9, 10.1, 1, 0.9);">
			               	<c:out value="Weight: ${oneIncidentReport.patientCase.patientVitalRecords[0].weight}Lb"/>
			               	<c:out value="Height: ${oneIncidentReport.patientCase.patientVitalRecords[0].height}cm"/>
			               	<c:out value="BMI: ${bmi}"/>

		               	</p>
			            <p class="inner-column-card
						     <c:if test="${bmi <= 18.4}">
								  btn btn-outline-warning
						     </c:if>
						     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
								  btn btn-outline-success
						     </c:if>
						     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
								  btn btn-outline-danger
						     </c:if>" style="background:rgba(0.9, 10.1, 1, 0.9);">
						     <c:if test="${bmi <= 18.4}">
								  <c:out value="Patient  BMI: ${bmi} Indicates patient is 'underweight' Normal range is between 18.5 and 24.9"/>
						     </c:if>
							 <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
								  <c:out value="Patient  BMI: ${bmi} is within normal range"/>
							</c:if>
							<c:if test="${bmi >= 25.0 && bmi <= 39.9}">
								  <c:out value="Patient  BMI: ${bmi} Indicates patient is 'Obesed' Normal Range Is between 18.5 and 24.9"/>
							</c:if>
				           	 <c:out value="${incidentReportCreatedAt} Record"/>
			           	 </p>
		               </div>
	             </a>
				<c:out value="Body Temperature: ${oneIncidentReport.patientCase.patientVitalRecords[0].bodyTemperature}F"/>
               	<c:choose>
				     <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].abdominalPainAssessments.size() <=1}">
				           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].abdominalPainAssessments.size()} Abdominal Pain Assessment"/>
				     </c:when>
				     <c:otherwise>
			           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].abdominalPainAssessments.size()} Abdominal Pain Assessments"/>
				     </c:otherwise>
			     </c:choose>
               	<c:choose>
				     <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].nauseaVomitAssessments.size() <=1}">
				           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].nauseaVomitAssessments.size()} Nausea Vomit Assessment"/>
				     </c:when>
				     <c:otherwise>
			           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].nauseaVomitAssessments.size()} Nausea Vomit Assessments"/>
				     </c:otherwise>
			     </c:choose>
               	<c:choose>
				     <c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].nauseaVomitAssessments.size() <=1}">
				           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].patientVisitEvaluations.size()} Patient Visit Evaluation"/>
				     </c:when>
				     <c:otherwise>
			           <c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].patientVisitEvaluations.size()} Patient Visit Evaluations"/>
				     </c:otherwise>
			     </c:choose>
	           
               	<div class="column-card" style="background:rgba(50.9, 10.1, 11, 0.9);padding:px;">				
               		<form action="/mellowHealth/incidentReports" method="get" style="width:100%;margin:5px;">
					    <input type="hidden" name="_method" value="get">
						<input class ="
					     <c:if test="${bmi <= 18.4}">
							  btn btn-outline-warning
					     </c:if>
					     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
							  btn btn-outline-success
					     </c:if>
					     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
							  btn btn-outline-danger
					     </c:if>" type="submit" value="Relieving Agent: ${oneIncidentReport.relievingAgentTaken} ${oneIncidentReport.timeOfOccurrence}" style="width:100%;padding:10px;">
					</form>				
               		<form action="/mellowHealth/incidentReports" method="get" style="width:100%;margin:5px;" class="">
					    <input type="hidden" name="_method" value="get">
						<input class ="btn btn-outline-danger" type="submit" value="Aggravating Agent: ${oneIncidentReport.aggravatingAgentTaken} ${oneIncidentReport.onset}" style="width:100%;padding:10px;background:rgba(50.9, 10.1, 1, 0.9);">
					</form>
				</div>	  
            </div>
		   		<div class="column-card ">
	               <c:out value="Incidence History: ${incidentReportAccountDaysHistory} Days"/>
	               <c:out value="PatientCase Progress: Day ${patientCaseAccountDaysHistory}"/>
	           	   <p>
	           	   		<c:out value="Presenting Chief Complaint: ${oneIncidentReport.patientCase.patientVitalRecords[0].patientCase.chiefComplaint}: ${searchedPatientVitalCreatedAt} Record"/>
	           	   	</p>
	           	</div>
			 <div class="inner-column-card btn btn-outline-primary" style="width:100%;display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
       		 <c:choose>
				<c:when test="${oneIncidentReport.patientCase.patientVitalRecords[0].patient.id == loggedInPatient.id}">
			        <div class="column-card" style="width:100%;	
		        		<c:choose>
				            <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
						         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
				            </c:when>
				            <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
						         color: rgb(412, 580, 515); background: rgba(13, 114, 160, 0.9); 
				            </c:when>
				            <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
						     	 color: rgb(412, 580, 515); background: rgba(133, 64, 60, 0.9); 
				             </c:when>
				             <c:otherwise>
				                  color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9);
				             </c:otherwise>
			             </c:choose>
			             ">
 		 				<div class="btn btn-outline-primary unwrapped-inner-column-card" style="border-radius:5%;background:rgba(1.33, 0.64, 0.60, 0.9);padding:10px;">
							<form action="/mellowHealth/incidentReports/editIncidentReport/${oneIncidentReport.id}" method="get" style="margin:5px;width:100%;">
								    <input type="hidden" name="_method" value="edit">
								    <input class ="btn btn-outline-warning" type="submit" value="Edit Incident Report" style="width:100%;padding:10px" >
								</form>
								<form action="/mellowHealth/incidentReports/deleteIncidentReport/${oneIncidentReport.id}" method="post" style="margin:5px;width:100%;">
								    <input type="hidden" name="_method" value="delete">
									<input class ="btn btn-outline-danger" type="submit" value="Delete Incident Report" style="width:100%;padding:10px">
								</form>
							</div>
						</div>
		 				</c:when>
						<c:otherwise>
						<div class="table-dark d-flex justify-content-around">
							<a href="/mellowHealth/incidentReports/${incidences.id}" style="text-decoration:none; 
								<c:choose>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
						                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                    </c:when>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
						                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                    </c:when>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
						                color: rgb(412, 580, 515);  background: rgba(17, 104, 130, 0.9); 
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
				                    </c:otherwise>
			               		</c:choose>
		               			padding:15px; border-radius:5%; width:100%; height:100%;" class= "
			               		<c:choose>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
						            	btn btn-warning
				                    </c:when>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
						            	btn btn-primary
				                    </c:when>
				                    <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
						            	btn btn-danger
				                    </c:when>
				                    <c:otherwise>
				                    	btn btn-primary  
				                    </c:otherwise>
			               		</c:choose>">
		               			<c:out value="${oneIncidentReport.patientCase.patientVitalRecords[0].patientCase.insuranceInformation.providerName}: Coverage Period ${oneIncidentReport.patientCase.patientVitalRecords[0].patientCase.insuranceInformation.insuranceId} - ${lengthOfCoverage} years"/>
		               		</a>
		               	</div>
						</c:otherwise>
            		</c:choose>
            	</div>
		  	</div>
	 	<div style="width: 100%;border-radius:5%;margin:10px 0;">
	    <c:choose>
	       <c:when test="${loggedInPatient.incidentReports.size() < 1}">
				<div class="unwrapped-inner-column-card btn btn-outline-primary" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/incidentReports/newIncidentReport" class="btn btn-success">
			 			<c:out value="REPORT NEW INCIDENCE ${currentDateTime}!"/>
			 		</a>
		 		</div>
	       </c:when>
	       <c:otherwise>
				<div class="unwrapped-inner-column-card btn btn-outline-primary" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/incidentReports/newIncidentReport" class="btn btn-success">
			 			<c:out value="REPORT NEW INCIDENCE ${currentDateTime}!"/>
			 		</a>
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/incidentReports" class="btn btn-outline-warning">
			 			<c:out value="VIEW ALL INCIDENT REPORTS!"/>
			 		</a>
		 		</div>
	 		</c:otherwise>
	      </c:choose>
	 	</div>
		</c:if>
		<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;margin:10px 0;">
		    <form action="/mellowHealth/incidentReports/${oneIncidentReport.id}" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
		    <c:choose>
			<c:when test="${empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
					    <c:out value="Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${loggedInPatient.patientVitalRecords.size()} Patient Vital Records Today ${dayCurrentDateTime} "/>
					</a>
			    </p>
			</c:when>
			<c:otherwise>
			   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		<c:choose>
					<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].patientVitalRecords.size()} Patient Vital Records!"/>
						</a>
					</c:when>
					<c:otherwise>
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
					    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${searchedPatientCase[0].patient.patientVitalRecords.size()} Patient Vital Records!"/>
						</a>
					</c:otherwise>
				</c:choose>
			    </p>
			</c:otherwise>
			</c:choose>
		</div>
		<!-- Patient Cases -->
		<c:if test="${mostRecentPatientCase.patient.id == loggedInPatient.id}">
		<c:forEach items="${allPatientCasesWithFilter}" var="patientCase">
		    <div class="column-card" style="color:aqua;background: rgba(13, 0.64, 0.1, 0.9);">
		        <div class="inner-column-card btn btn-outline-success" style="align-items:center; padding:10px;">
					<p class="column-card">
			        	<a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="background: rgba(13, 78.64, 60, 0.9); color:aliceblue;">
			        		<c:out value="Mellow Patient ID: ${patientCase.id} ${patientCaseCreatedAt} Visit"/>
			        	</a>
		        	</p>
		        	<div class="column-card btn btn-outline-success" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
		        		<a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none; color:aqua;widt:100%;">
		        			<c:out value="${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName}"/>
		        			<p>
		        				<c:out value="Date Of Birth: ${patientCase.patient.dateOfBirth}"/>
		        			</p>
		        			<c:out value="Phone Number: ${patientCase.patient.patientAddresses[0].phoneNumber}"/>
		        		</a>
		        	</div>
		        	<div class="column-card" style="">
				        <a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;background:rgba(1.33, 0.64, 30.60, 0.9);
							<c:choose>
								<c:when test="${patientCase.patient.gender == 'Male'}">
									color:silk;
								</c:when>
								<c:when test="${patientCase.patient.gender == 'Female'}">
									color:pink;
								</c:when>
						        <c:otherwise>
					                color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
				                </c:otherwise>
			           		</c:choose>
							">
							<c:out value="${searchedPatientAge} Yr Old"/>
							<c:out value="${patientCase.patientVitalRecords[0].weight} Lb, ${patientCase.patientVitalRecords[0].height}cm, Heart Rate: ${patientCase.patientVitalRecords[0].heartRate}bpm"/>
						</div>
		               	<c:choose>
						     <c:when test="${patientCase.patient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == true}">
								<p>
									<c:out value="${patientCase.patient.race} ${patientCase.patient.gender}"/>
						        	<c:out value="With Recent Travel History"/>
						      	</p>
						     </c:when>
						     <c:otherwise>
								<p>
									<c:out value="${patientCase.patient.race} ${patientCase.patient.gender}"/>
						     		<c:out value="With No Recent Travel History"/>
						     	</p>
						     </c:otherwise>
						</c:choose>
					</a>
						<p class="column-card">
		               	<c:choose>
						     <c:when test="${patientCase.patientVitalRecords[0].abdominalPainAssessments.size() <=1}">
						           <c:out value="${patientCase.patientVitalRecords[0].abdominalPainAssessments.size()} Abdominal Pain Assessment"/>
						     </c:when>
						     <c:otherwise>
					           <c:out value="${patientCase.patientVitalRecords[0].abdominalPainAssessments.size()} Abdominal Pain Assessments"/>
						     </c:otherwise>
					     </c:choose>
		               	<c:choose>
						     <c:when test="${patientCase.patientVitalRecords[0].nauseaVomitAssessments.size() <=1}">
						           <c:out value="${patientCase.patientVitalRecords[0].nauseaVomitAssessments.size()} Nausea Vomit Assessment"/>
						     </c:when>
						     <c:otherwise>
					           <c:out value="${patientCase.patientVitalRecords[0].nauseaVomitAssessments.size()} Nausea Vomit Assessments"/>
						     </c:otherwise>
					     </c:choose>
		               	<c:choose>
						     <c:when test="${patientCase.patientVitalRecords[0].nauseaVomitAssessments.size() <=1}">
						           <c:out value="${patientCase.patientVitalRecords[0].patientVisitEvaluations.size()} Patient Visit Evaluation"/>
						     </c:when>
						     <c:otherwise>
					           <c:out value="${patientCase.patientVitalRecords[0].patientVisitEvaluations.size()} Patient Visit Evaluations"/>
						     </c:otherwise>
					     </c:choose>
					     </p>
						<c:if test="${patientCase.patient.pastMedicalHistories.size() > 0}">
							<p>
								<c:out value="With ${searchedPatientLengthOfMedicalCondition} yr History Of ${searchedMostRecentPastMedicalRecord.medicalCondition} Currently taking: ${searchedMostRecentPastMedicalRecord.treatmentPlan}"/>
							</p>
						</c:if>
				</div>
		        <div class="column-card btn btn-outline-primary">
			        <a class="inner-generic-tab" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;background:rgba(1.33, 0.64, 0.60, 0.9);
		            	<c:choose>
				            <c:when test="${patientCase.patient.gender == 'Female' || patientCase.patient.race == 'Black'}">
				                color: rgb(312, 280, 31.5);
			                </c:when>
			                <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
				                color: rgb(412, 580, 515);
			                </c:when>
			                <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			                    color: rgb(412, 580, 515);
			                </c:when>
			                <c:otherwise>
			                </c:otherwise>
		           		</c:choose>
	           			"><c:out value="${patientCase.chiefComplaint}"/>
		                <div style="border-radius:5%;padding:5px;background:rgba(3.33, 0.64, 0.60, 0.9);">
		                	<c:out value="${patientCase.onset} Subjective Complaint"/>
		                	<p>
		                		<c:out value="${patientCase.subjectiveSymptoms} ${searchPatientCaseCreatedAt} visit"/>
		                	</p>
		                </div>
	           		</a>   
			        <div class="inner-generic-tab btn btn-outline-warning" style="">
			            <a class="btn btn-outline-warning" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;background:rgba(13.33, 0.64, 0.60, 0.9);
							<c:choose>
								<c:when test="${patientCase.patient.gender == 'Male'}">
						            color: rgb(321, 189.0, 25.5);
								</c:when>
								<c:when test="${patientCase.patient.gender == 'Female'}">
									color:pink;
								</c:when>
							    <c:otherwise>
						            color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
					            </c:otherwise>
				            </c:choose>
							"><c:out value="Day ${patientCaseAccountDaysHistory}: ${patientCase.diagnosticRecords.size()} Diagnostic Records!" />
						</a>	<form action="/mellowHealth/incidentReports/${oneIncidentReport.id}" method="get" style="margin:5px;width:100%;">
							    <input type="hidden" name="_method" value="get">
								<input class ="
							     <c:if test="${bmi <= 18.4}">
									  btn btn-outline-primary
							     </c:if>
							     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
									  btn btn-outline-success
							     </c:if>
							     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
									  btn btn-outline-danger
							     </c:if>" type="submit" value="BMI: ${bmi}" style="margin:5px;width:100%;padding:10px">
							</form>
			            <p class="column-card
							     <c:if test="${bmi <= 18.4}">
									  btn btn-outline-primary
							     </c:if>
							     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
									  btn btn-outline-success
							     </c:if>
							     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
									  btn btn-outline-danger
							     </c:if>">
					     <c:if test="${bmi <= 18.4}">
							  <c:out value="Patient  BMI: ${bmi} Indicates Patient Is Underweight Normal Range Is between 18.5 and 24.9"/>
					     </c:if>
						 <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
							  <c:out value="Patient  BMI: ${bmi} Is Within Normal Range"/>
						</c:if>
						<c:if test="${bmi >= 25.0 && bmi <= 39.9}">
							  <c:out value="Patient  BMI: ${bmi} Indicates Patient Is Obesed Range Normal Range Is between 18.5 and 24.9"/>
						</c:if>
			           	 <c:out value="${recordCreatedAt} Record"/>
			           	 </p>
			            <a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;background:rgba(1.33, 0.64, 0.60, 0.9);
							<c:choose>
								<c:when test="${patientCase.patient.gender == 'Male'}">
						            color: rgb(121, 180, 255);
								</c:when>
								<c:when test="${patientCase.patient.gender == 'Female'}">
						            color: rgb(10.21, 180, 123.55);
								</c:when>
							    <c:otherwise>
						            color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
					            </c:otherwise>
				           	</c:choose>
							">
							<c:out value="${patientCase.physicalAssessments.size()} Physical Assessment Records"/>
						</a>
					</div>
						<a class= "inner-generic-tab
							<c:choose>
						      <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black' || patientCase.physician.patientCases.size() % 2 == 1}">
					          	 	btn btn-warning
					          </c:when>
					          <c:when test="${patientCase.physician.patientCases.size() % 2 == 1 || patientCase.patient.patientCases.size() % 2 == 1}">
				              	 	btn btn-primary
					          </c:when>
					          <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					    	       	btn btn-danger
					            </c:when>
						        <c:otherwise>
				 	            	btn btn-primary 
				                </c:otherwise>
							</c:choose> 
			    			" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none; 
							<c:choose>
				                <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                </c:when>
				                <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                </c:when>
				                <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					                color: rgb(412, 580, 515);  background: rgba(17, 104, 130, 0.9); 
				                </c:when>
				                <c:otherwise>
				                    color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
				                </c:otherwise>
			           		</c:choose>
			           		padding:15px; border-radius:5%; width:100%; height:100%;
			                ">
			                <p style="border-radius:5%;padding:5px;background:rgba(3.33, 0.64, 0.60, 0.9);">
			                	<c:out value="${patientCase.physicalAssessments.size()} Physical Assessment Records"/>
			                </p>
				            <p class="column-card
							     <c:if test="${bmi <= 18.4}">
									  btn btn-outline-primary
							     </c:if>
							     <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
									  btn btn-outline-success
							     </c:if>
							     <c:if test="${bmi >= 25.0 && bmi <= 39.9}">
									  btn btn-outline-danger
							     </c:if>">
				               	<c:out value="Weight: ${patientCase.patientVitalRecords[0].weight}Lb"/>
				               	<c:out value="Height: ${patientCase.patientVitalRecords[0].height}cm"/>
				               	<c:out value="Body Temperature: ${patientCase.patientVitalRecords[0].bodyTemperature}F"/>
			               	</p>	
			                <p style="border-radius:5%;padding:5px;background:rgba(3.33, 0.64, 0.60, 0.9);">
			                	<c:out value="Treating Physician: Dr. ${patientCase.physician.firstName}  ${patientCase.physician.lastName}"/>
			                	<c:out value="Contact Details: ${patientCase.physician.email}  ${patientCase.physician.certificationSpecialty}"/>
			                </p>	
			                <p style="border-radius:5%;padding:5px;background:rgba(3.33, 0.64, 0.60, 0.9);">
			                	<c:out value="Dea LicenseNumber Details: ${patientCase.physician.deaLicenseNumber}"/>
			                </p>
			           	</a>
	       			</div>
					</div>
			    		<c:choose>
				            <c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			           			<div class="btn btn-outline-danger column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
				           			<div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
										<form action="/mellowHealth/hospitalDashboard/patientCases/edit/${patientCase.id}" method="get" style="margin:5px;width:100%;">
										    <input type="hidden" name="_method" value="edit">
										    <input class ="btn btn-outline-warning" type="submit" value="Edit Case" style="padding: 10px;width:100%;" >
										</form>
									</div>
				           			<div class="btn btn-outline-danger column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
										<form action="/mellowHealth/hospitalDashboard/patientCases/delete/${patientCase.id}" method="post" style="margin:5px;width:100%;">
										    <input type="hidden" name="_method" value="delete">
				 							<input class ="btn btn-outline-danger" type="submit" value="Delete Case" style="padding: 10px;width:100%">
										</form>
									</div>
								</div>
							</c:when>
				            <c:otherwise>
					             <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
						   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;">
									    <c:out value="${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${searchedPatientCase[0].patient.patientCases.size()} Patient Cases Today ${currentDateTime}"/>
									</a>
					    </p>
		             </c:otherwise>
	             </c:choose>
			<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;">
			    <form action="/mellowHealth/diagnosticRecords/${oneIncidentReport.id}" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
			        <label  style="padding:10px">Search Patient Name</label>
			        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
			        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
			    </form>
			    <c:choose>
				<c:when test="${empty searchedPatientCase}">
				    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
						    <c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${loggedInPatient.physicalAssessments.size()} Physical Assessments!"/>
						</a>
				    </p>
				</c:when>
				<c:otherwise>
				   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		<c:choose>
						<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
				   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
							    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].physicalAssessments.size()} Physical Assessments!"/>
							</a>
						</c:when>
						<c:otherwise>
				   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${searchedPatientCase[0].physicalAssessments.size()} Physical Assessments!"/>
							</a>
 						</c:otherwise>
					</c:choose>
 				    </p>
				</c:otherwise>
				</c:choose>
			</div>
	</c:forEach>
	</c:if>
	<div class="form-group column-card" style="
	    <c:choose>
	       <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
	       </c:when>
	       <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
	       </c:when>
	       <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 36, 0.9); 
	       </c:when>
	       <c:otherwise>
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9); 
	       </c:otherwise>
	      </c:choose>
	   margin:15px 0">
	 	<h1 style="width: 100%;padding: 10px;">
	    <c:choose>
	       <c:when test="${loggedInPatient.diagnosticRecords.size() < 1}">
				<div class="unwrapped-inner-column-card btn btn-outline-primary" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${mostRecentInsuranceReport.id}" class="btn btn-success">
			 			<c:out value="ADD NEW DIAGNOSTIC REPORT!"/>
			 		</a>
		 		</div>
	       </c:when>
	       <c:otherwise>
				<div class="unwrapped-inner-column-card btn btn-outline-primary" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/diagnosticRecords/newDiagnosticRecord" class="btn btn-success">
			 			<c:out value="ADD NEW DIAGNOSTIC REPORT!"/>
			 		</a>
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticReport.id}" class="btn btn-outline-warning">
			 			<c:out value="VIEW MOST RECENT DIAGNOSTIC REPORT!"/>
			 		</a>
		 		</div>
	 		</c:otherwise>
	      </c:choose>
	 	</h1>
	</div>
	<div class="form-group column-card" style="
 		<c:choose>
	        <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			    color: khaki;background:aqua;background: rgba(13, 0.64, 60, 0.9);
	        </c:when>
	        <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
			    color:pink; background: rgba(13, 0.123, 0.160, 0.9);
	        </c:when>
	        <c:otherwise>
	            color: rgb(211, 180, 255);background: rgba(5.2, 11.4, 11.6, 0.9);
	        </c:otherwise>
	    </c:choose>
	    display:flex;align-items:center;justify-content:space-between; padding:10px;border-radius:5%;">
		<h1 style="margin:5px;width:100%">
			<a href="/mellowHealth/diagnosticRecords/newPhysicalAssessment" class="btn btn-outline-success" style="width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">
				<c:out value="Add New Record"/>
			</a>
		</h1>
		<h1 style="margin:5px;width: 100%">
			<a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-secondary" style="width: 100%; display:block; padding: 10px;background:rgba(0.1, 0.3, 10, 0.9);">
				<c:out value="${loggedInPatient.patientFirstName} Profile Access"/>
			</a>
		</h1>
		<h1 style=" margin:5px;width: 100%">
			<a href="/mellowHealth/patientsPortal/logout" class="btn btn-outline-warning" style="width: 100%; display:block; padding: 10px;background:rgba(10.1, 0.3, 10, 0.9);">
				<c:out value="LOGOUT HERE!"/>
			</a>
		</h1>
		<form action="/mellowHealth/insuranceRecords" method="get" style="margin:5px;width:100%;">
			<input type="submit" value="View All Insurance Records!" class="btn btn-outline-primary"style="width:100%; padding:10px;background:rgba(10.1, 0.3, 10, 0.9);"/>
		</form>
	</div>	
	<div class="form-group column-card" style="align-items:center;
	    		<c:choose>
	            <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
	            </c:when>
	            <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
	            </c:when>
	            <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 36, 0.9); 
	            </c:when>
	            <c:otherwise>
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9); 
	             </c:otherwise>
	             </c:choose>">
	 	<h1 style="margin:5px; width: 100%;">
	 		<a style="width: 100%; display:block; padding:12px;background:rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${insurance.insuranceId}" class="btn btn-outline-success">
	 			<c:out value="VIEW INSURANCE RECORDS!"/>
	 		</a>
	 	</h1>
	 	<h1 style=" margin:5px; width: 100%">
	 		<a href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" class="btn btn-outline-warning"style="width: 100%; display:block; padding: 10px;font-weight:bold;background:rgba(10.1, 0.3, 10, 0.9);">
	 			<c:out value="ADD PAST MEDICAL RECORD!"/>
	 		</a>
	 	</h1>
		<h1 class="column-card" style="background:rgba(10.1, 0.3, 10, 0.9);margin:5px; width: 100%">
			<a href="/mellowHealth/patientsPortal/patients" class="btn btn-outline-primary" style="width: 100%; display:block; padding: 10px;font-weight:bold;background:rgba(10.1, 0.3, 10, 0.9);">
				<c:out value="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} Patient Profile!"/>
			</a>
		</h1>
	</div>
	<div class="form-group column-card"style="align-items:center;
	    <c:choose>
	        <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
	        </c:when>
	        <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 1}">
		     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
            </c:when>
	        <c:when test="${loggedInPatient.insuranceRecords.size() % 2 == 0}">
		     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
	        </c:when>
            <c:otherwise>
	             color: rgb(21, 180, 255);background: rgba(7, 100, 8, 0.9);
            </c:otherwise>
	   </c:choose>
	   display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">
   	   <h1 style="margin:5px; width: 100%">
		 	<a href="/mellowHealth/patientsPortal/logout" class="btn btn-danger" style=" margin:5px; width: 100%; display:block; padding: 10px">
		 		<c:out value="LOGOUT HERE ${currentDateTime}"/>
			</a>
	 	</h1>
		<h1 style="width:100%;margin:5px;">
		 	<a style="background:rgba(8, 8, 10, 0.9);margin:5px;width:100%; display:block; padding: 10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-success">
		 		<c:out value="Mellow Patient Since ${loggedInPatientCreatedAt}"/>
			</a>
	 	</h1>
	</div>	
</body>
</html>