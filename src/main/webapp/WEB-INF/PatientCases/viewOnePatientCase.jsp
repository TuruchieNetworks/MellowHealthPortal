<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
<!-- YOUR own local CSS -->
    <link rel="stylesheet" href="/styles/patientCaseStyles.css"/>
    <link rel="stylesheet" href="/styles/standaloneStyles.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Mellow Health Patient Case Portal!</title>
</head>
<body class="container-fluid p-8" style="
            <c:choose>
               <c:when test="${onePhysician.patientCases.size() % 2 < 1}">
	                color: khaki;background:aqua;background:rgba(80.2, 400.4, 316.6, 0.9);
               </c:when>
               <c:when test="${onePhysician.patientCases.size() % 2 == 1}">
	                color:pink; background:rgba(113.802, 124.4, 316.6, 0.9);
               </c:when>
               <c:otherwise>
                    color: rgb(211, 180, 255);background:rgba(380.2, 380.4, 36.6, 0.9);
               </c:otherwise>
 			</c:choose>
			font-family:cursive;">
			<c:choose>
				<c:when test="${patientCase.patient.id == loggedInPatient.id}">
					<h1 style="text-align:center;border-bottom: 2px solid chocolate;color: brown; font-family:fantasy;font-size:26px;border-radius:5%;background:rgba(10.2, 3.3, 3.6, 0.9);margin-top:5px;">
						<a style="width: 100%; display:block; padding:10px;color:aqua;text-decoration:none;"  href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}">
							<c:out value="Welcome To Your Mellow Health Patients Case  Portal ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} ${dayCurrentDateTime}!"/>
						</a>
					</h1>
				</c:when>
				<c:otherwise>
    				<h1 style="text-align:center;border-bottom: 2px solid chocolate;color: brown; font-family:fantasy;border-radius:5%;background: rgba(7.8, 0.10, 10.101, 0.9)">
    					<a style="width: 100%; display:block; padding: 8px;color:aquamarine;text-decoration:none;" href="/mellowHealth/patientsPortal/patients">
    						<c:out value="Welcome To Your Mellow Health Patients Case Portal ${loggedInPatient.patientFirstName} ${dayCurrentDateTime}!"/>
    					</a>
    				</h1>
				</c:otherwise>
			</c:choose>
	        <div class="column-card">
	    		<div class="inner-column-card" style="padding:15px;">
		        	<p class="btn btn-outline-success" style="border-radius:5%;">
		        		<c:out value="Patient Case ID: ${patientCase.id}"/>
				        <a class="inner-column-card" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}"  style="padding:10px; 
				         	<c:choose>
			                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
					                color: rgb(211, 180, 355);
			                    </c:when>
			                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					                color:aqua;
			                    </c:when>
			                    <c:when test="${patientCase.chiefComplaint == 'Wednesday' || patientCase.chiefComplaint == 'Thursday'}">
					                color:rgb(311, 180, 235);
			                    </c:when>
			                    <c:otherwise>
			                        color:aqua; color:rgb(201, 380, 235);
			                    </c:otherwise>
			            	</c:choose>">
			            	<c:out value="${createdAt} Day ${onePatientCaseAccountDaysHistory} Treatment History"/>
			               	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;background:rgba(1.33, 0.64, 0.60, 0.9);">
					            <c:out value="Chief Complaint: ${patientCase.chiefComplaint}: ${patientCase.subjectiveSymptoms}"/>
					            <c:choose>
				                    <c:when test="${patientCase.diagnosticRecords.size() < 0}">
					               		<div class="inner-column-card btn btn-outline-primary" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px;background:rgba(1.33, 10.64, 0.60, 0.9);">
							            	<c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Record ${patientCase.physicalAssessments.size()} Physical Assessments"/>
						            		<c:out value="${patientCase.currentMedications.size()} Medications- ${patientCase.adverseEffects.size()} Adverse Effects"/>
						               </div>
							            <c:choose>
					                    	<c:when test="${patientCase.patient.pastMedicalHistories.size() < 1}">
							        			<form class="column-card" action="/mellowHealth/pastMedicalHistories/newPastMedicalHistory" method="get" style="padding:5px;width:100%;background:rgba(1.33, 10.64, 0.60, 0.9);">
												    <input type="hidden" name="_method" value="get">
												    <input class ="btn btn-outline-success" type="submit" value="ADD NEW PAST MEDICAL HISTORY" style="width: 100%;padding:7px;width:100%;" >
												</form>
											</c:when>
				                    		<c:otherwise>
								            <p>
								            	<c:out value="Patient with ${mostRecentPastMedicalRecordMonthsHistory} Yr History Of ${mostRecentPastMedicalRecord.medicalCondition}"/>
								            	<c:out value="${createdAt} visit- ${patientCase.patient.email}"/>
								            </p>
								            </c:otherwise>
							            </c:choose>
			                    		</c:when>
					                    <c:otherwise>
								        	<c:choose>
							                   	<c:when test="${patientCase.diagnosticRecords.size() < 1}">
								               		<form class="column-card" action="/mellowHealth/diagnosticRecords/newDiagnosticRecord" method="get" style="padding:5px;background:rgba(1.33, 10.64, 0.60, 0.9);">
													    <input type="hidden" name="_method" value="get">
													    <input class ="btn btn-outline-warning" type="submit" value="ADD NEW DIAGNOSTIC REPORT" style="width: 100%;padding:7px;width:100%;" >
													</form>
								            	</c:when>
								            	<c:otherwise>
								               		<div class="inner-column-card btn btn-outline-primary" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:5px;background:rgba(1.33, 10.64, 0.60, 0.9);">
									            		<c:out value="${oneSearchedPatientCaseDiagnosticReportCreatedAt} Diagnostic Report"/>
										           		<c:out value="${oneSearchedPatientCaseDiagnosticRecord.physicalExamFindings} Differential Diagnosis- ${oneSearchedPatientCaseDiagnosticRecord.differentialDiagnosis}"/>
									            	</div>
								            	</c:otherwise>
							            	</c:choose>
								            <c:choose>
						                    	<c:when test="${patientCase.patient.pastMedicalHistories.size() < 1}">
								        			<form class="column-card" action="/mellowHealth/pastMedicalRecords/newPastMedicalRecord" method="get" style="padding:5px;background:rgba(1.33, 10.64, 0.60, 0.9);">
													    <input type="hidden" name="_method" value="get">
													    <input class ="btn btn-outline-warning" type="submit" value="ADD PAST MEDICAL HISTORY" style="width: 100%;padding:7px;width:100%;" >
													</form>
												</c:when>
					                    		<c:otherwise>
									           		 <p>
											            <c:choose>
									                    	<c:when test="${mostRecentPastMedicalRecordYearsHistory < 1}">
												            	<c:out value="Patient with ${mostRecentPastMedicalRecordMonthsHistory} Months History Of ${mostRecentPastMedicalRecord.medicalCondition}"/>
												            	<c:out value="${createdAt} visit- ${patientCase.patient.email}"/>
												            </c:when>
												            <c:otherwise>
												            	<c:out value="Patient with ${mostRecentPastMedicalRecordYearsHistory} Yr History Of ${mostRecentPastMedicalRecord.medicalCondition}"/>
												            	<c:out value="${createdAt} visit: ${patientCase.patient.email}"/>
												            </c:otherwise>
												        </c:choose>
										            </p>
											    </c:otherwise>
									        </c:choose>
					                    </c:otherwise>
					            	</c:choose>
					            	</div>
				            	</a>
				            </p>
					        </div>
			               	<div class="column-card btn btn-outline-primary" style="background:rgba(1.33, 10.64, 0.60, 0.9);">
				               	<div class="inner-column-card btn btn-outline-primary" style="padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						            <p>
						            	<c:out value="Follow Up Consultation: ${oneSearchedPatientCaseDiagnosticRecord.followUpConsultation}"/>
						            </p>
				               		<div class="inner-column-card btn btn-outline-primary" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
							            <p>
							            	<c:out value="Differential Diagnosis: ${oneSearchedPatientCaseDiagnosticRecord.differentialDiagnosis}"/>
							            </p>
							            <p>
							            	<c:out value="Recreational Substance: ${patientCase.patient.recreationalSubstance}"/>
							            </p>
						           	</div>
							        <p class="btn btn-outline-primary">
							           	<c:out value="Insurance Provider: ${patientCase.insuranceInformation.providerName}"/>
							           	<c:out value="Coverage Details: ${patientCase.insuranceInformation.expirationDate}"/>
							        </p>
					         </div>
					            
     		               	 <div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
 					            <p>${createdAt} event
					            	<a class="inner-column-card" href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment">
						            	<c:out value="Book New Physical Assessment"/>
					            	</a>
					            </p>
					            <c:choose>
			                    	<c:when test="${patientCase.patientVitalRecords.size() < 1}">
						       			<form action="/mellowHealth/patientVitalRecords/newPatientVitalRecord" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-warning" type="submit" value="Record New Patient Vitals" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
			                    	<c:when test="${patientCase.patientVitalRecords.size() == 1}">
						       			<form action="/mellowHealth/patientVitalRecords/${mostRecentPatientVitalRecord.id}" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-success" type="submit" value="${patientCase.patientVitalRecords.size()} Patient Vital Record" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
		                    		<c:otherwise>
						       			<form action="/mellowHealth/patientVitalRecords" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary" type="submit" value="${patientCase.patientVitalRecords.size()} Patient Vital Records" style="width: 100%;padding:7px;width:100%;" >
										</form>
						            </c:otherwise>
					            </c:choose>

								<p>
					            <c:choose>
			                    	<c:when test="${patientCase.incidentReports.size() < 1}">
						       			<form action="/mellowHealth/patientVitalRecords/newPatientVitalRecord" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-success" type="submit" value="Report New Incident" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
			                    	<c:when test="${patientCase.incidentReports.size() == 1}">
						       			<form action="/mellowHealth/incidentReports/${mostRecentIncidentReport.id}" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-warning" type="submit" value="${patientCase.incidentReports.size()} Reported Incident" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
		                    		<c:otherwise>
						       			<form action="/mellowHealth/incidentReports" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-danger" type="submit" value="${patientCase.incidentReports.size()} Reported Incidents" style="width: 100%;padding:7px;width:100%;" >
										</form>
						            </c:otherwise>
					            </c:choose>
					            </p>
					            <p>
					            	<a class="inner-column-card" href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment">
						            	<c:out value="Date Of Birth: ${patientCase.patient.dateOfBirth} ${patientAge} yrs Old"/>
					            	</a>
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
								     <c:if test="${bmi <= 18.4}">
										  <c:out value="Patient  BMI: ${bmi} Indicates Patient is Underweight- Normal Range is between 18.5 and 24.9"/>
								     </c:if>
									 <c:if test="${bmi >= 18.5 && bmi <= 24.9}">
										  <c:out value="Patient  BMI: ${bmi} is Within Normal Range"/>
									</c:if>
									<c:if test="${bmi >= 25.0 && bmi <= 39.9}">
										  <c:out value="Patient  BMI: ${bmi} Indicates Patient is Obesed Range- Normal Range is between 18.5 and 24.9"/>
									</c:if>
					           	 </p>
				            	<p>
				            		<c:out value="Contact Details: ${patientCase.patient.patientAddresses[0].phoneNumber}"/>
				            	</p>
				            	<p class="btn btn-outline-primary">
				            		<c:out value="Total Mellow Health Cases: ${patientCase.patient.patientCases.size()}"/>
					            </p>
					     </div>
				    </div>
		            </div>
				    
					<div class="generic-display-wraps" style="background: rgba(7, 1, 0.10, 0.9);text-align:center; border-radius:5%;padding:10px;margin:5px 0">
						<h1 style="">
							<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-outline-warning" style="">
								<c:out value="Add New Case!"/>
							</a>
						</h1>
						<h1 style="">
							<a href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-outline-primary" style="">
								<c:out value="VISIT HISTORY!"/>
							</a>
						</h1>
						<h1 style="">
							<a href="/mellowHealth/physicians/logout" class="btn btn-outline-danger" style="">
								<c:out value="LOGOUT HERE!"/>
							</a>
						</h1>
						<form action="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" method="get" style="">
							<h1 style="">
								<input type="submit" value="PATIENTS PORTAL ACCESS!" class="btn btn-outline-primary" style="width:100%;background:rgba(0.531, 0.64, 16, 0.9);"/>
							</h1>
						</form>
					</div>

					<!-- Patient Physician Panel -->
		            <div class="column-card">
		            <div class="inner-column-card" style="align-items:center;padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
		               	<div class="user-display-block" style="align-items:center;text-align:center;background:rgba(13.33, 0.64, 0.60, 0.9);">
		               		<p class="inner-column-card btn btn-outline-primary"style="align-items:center;text-align:center;background:rgba(13.33, 0.64, 0.60, 0.9);">
					        	<c:out value="${patientCaseCreatedAt} Record: PatientCase Onset: ${patientCase.onset}"/>
					        </p>
			        		<div class="user-display-block btn btn-outline-primary " style="">
				        		<div class="btn btn-outline-primary unwrapped-inner-column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;background:rgba(1.33, 0.64, 0.60, 0.9);background:rgba(13.33, 0.64, 0.60, 0.9);">
				        			<p class="btn btn-outline-primary column-card">
				        				<c:out value="Dr. ${patientCase.physician.firstName} ${patientCase.physician.lastName}"/>
				        			</p>
				        			<form action="/mellowHealth/painAssessments" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="License Id: ${patientCase.physician.deaLicenseNumber}" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
									</form>
				        		</div>
			               		<div class="btn btn-outline-primary inner-column-card" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
					        		<form action="/mellowHealth/currentMedications" method="get" style="width:100%;margin:5px;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning user-display-block" type="submit" value="Current Treatments In Patients Case: ${patientCase.currentMedications.size()} Medications" style="width: 100%;padding:10px;" >
									</form>
									<c:choose>
			                    	<c:when test="${patientCase.doseRegimenRecords.size() < 1}">	
										<form action="/mellowHealth/doseRegimenRecords/newDoseRegimenRecord" method="get" style="width:100%;margin:5px;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary user-display-block" type="submit" value="Dose Regimen Records: ${patientCase.doseRegimenRecords.size()}" style="width: 100%;padding:10px;" >
										</form>
									</c:when>
			                    	<c:when test="${patientCase.doseRegimenRecords.size() == 1}">	
										<form action="/mellowHealth/doseRegimenRecords/${mostRecentDoseRegimen.id}" method="get" style="width:100%;margin:5px;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary user-display-block" type="submit" value="Dose Regimen Record: ${patientCase.doseRegimenRecords.size()} ${mostRecentDoseRegimenCreatedAt}" style="width: 100%;padding:10px;" >
										</form>
									</c:when>
									<c:otherwise>
										<form action="/mellowHealth/doseRegimenRecords" method="get" style="width:100%;margin:5px;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary user-display-block" type="submit" value="Dose Regimen Records: ${patientCase.doseRegimenRecords.size()}" style="width: 100%;padding:10px;" >
										</form>
									</c:otherwise>
									</c:choose>
					        	</div>
				        	</div>

		               		<div class="btn btn-outline-primary inner-column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
		               		<c:choose>
		                    	<c:when test="${patientCase.diagnosticRecords.size() < 1}">	
				        			<form class="inner-column-card" action="/mellowHealth/diagnosticRecords/newDiagnosticRecord" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success inner-column-card" type="submit" value="Schedule Diagnostic Assessment" style="width: 100%;padding:10px;width:100%;" >
									</form>
								</c:when>
		                    	<c:when test="${patientCase.diagnosticRecords.size() == 1}">	
				        			<form action="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticRecord.id}" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="View Diagnostic Record" style="width: 100%;padding:10px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
				        			<form action="/mellowHealth/diagnosticRecords" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="View Diagnostic Records" style="width: 100%;padding:10px;width:100%;" >
									</form>
								</c:otherwise>
								</c:choose>
				        		<div class="btn btn-outline-warning inner-column-card" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);background:rgba(13.33, 0.64, 0.60, 0.9);width:100%;">
					        		<c:choose>
				                    	<c:when test="${patientCase.incidentReports.size() < 1}">		
						        			<form action="/mellowHealth/incidenceReports/${mostRecentIncidentReport.id}" method="get" style="width:100%;margin:5px 0;">
											    <input type="hidden" name="_method" value="get">
											    <input class ="btn btn-outline-primary" type="submit" value="${patientCase.patient.incidentReports.size()} New Incident" style="width: 100%;padding:7px;width:100%;" >
											</form>
				                    	</c:when>
				                    	<c:otherwise>
						        			<form action="/mellowHealth/incidentReports" method="get" style="width:100%;margin:5px 0;">
											    <input type="hidden" name="_method" value="get">
											    <input class ="btn btn-outline-danger" type="submit" value="${patientCase.patient.incidentReports.size()} New Incidents" style="width: 100%;padding:7px;width:100%;">
											</form>
				                    	</c:otherwise>
				            		</c:choose>
				        			<form action="/mellowHealth/painAssessments" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="${patientCase.adverseEffects.size()} Adverse Effects" style="width: 100%;padding:7px;width:100%;" >
									</form>
				        		</div>
				        		<c:choose>
			                    	<c:when test="${patientCase.physicalAssessments.size() < 1}">	
					        			<form action="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary" type="submit" value="Schedule New Physical Assessment" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
			                    	<c:when test="${patientCase.physicalAssessments.size() == 1}">	
					        			<form action="/mellowHealth/diagnosis/physicalAssessments/${mostRecentPhysicalAssessmentRecord.id}" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary" type="submit" value="Physical Assessment Record: ${patientCase.physicalAssessments.size()}" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:when>
									<c:otherwise>
					        			<form action="/mellowHealth/diagnosis/physicalAssessments" method="get" style="width:100%;">
										    <input type="hidden" name="_method" value="get">
										    <input class ="btn btn-outline-primary" type="submit" value="Physical Assessment Records: ${patientCase.physicalAssessments.size()}" style="width: 100%;padding:7px;width:100%;" >
										</form>
									</c:otherwise>
								</c:choose>
					        </div>
			               	<div class="btn btn-outline-primary column-card" style="align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
					        	<p class="btn btn-outline-primary" style="">
					        		<c:out value="Specialty: ${patientCase.physician.certificationSpecialty}"/>
					        	</p>
					        </div>
					   </div>
				   </div>

					<!-- Diagnostic Panel -->
	               	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
               			<div class="inner-column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;background:rgba(13, 0.64, 0.60, 0.9);">
		               		<div class="btn btn-outline-primary column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
				        	<c:choose>
					            <c:when test="${patientCase.patientVitalRecords.size() < 1}">
				        			<form action="/mellowHealth/patientVitalRecords/newPatientVitalRecord" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="Create New Patient Vital Record" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
					            <c:when test="${patientCase.patientVitalRecords.size() == 1}">
				        			<form action="/mellowHealth/patientVitalRecords" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="Patient Vital Records: ${patientCase.patientVitalRecords.size()} Patient Vital Records" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
									<form action="/mellowHealth/patientVitalRecords" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="Patient Vital Records: ${patientCase.patientVitalRecords.size()} Patient Vital Records" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:otherwise>
							</c:choose>
				        	<c:choose>
					            <c:when test="${patientCase.patient.coagulationRecords.size() < 1}">
									<form action="/mellowHealth/coagulationRecords" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-danger" type="submit" value="Enter New Coagulation Records" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
					            <c:when test="${patientCase.patient.coagulationRecords.size() == 1}">
									<form action="/mellowHealth/coagulationRecords" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-danger" type="submit" value="Coagulation Records: ${patientCase.patient.coagulationRecords.size()} Coagulation Record" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
									<form action="/mellowHealth/coagulationRecords" method="get" style="width:100%;margin:5px 0;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-danger" type="submit" value="Coagulation Records: ${patientCase.patient.coagulationRecords.size()} Coagulation Records" style="width: 100%;padding:7px;width:100%;" >
									</form>
								</c:otherwise>
							</c:choose>
				        	<c:choose>
					            <c:when test="${patientCase.doseRegimenRecords.size() < 1}">
					       			<form action="/mellowHealth/doseRegimenRecords" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="Enter New Dose Regimen Information" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
									</form>
								</c:when>
					            <c:when test="${patientCase.doseRegimenRecords.size() == 1}">
					       			<form action="/mellowHealth/doseRegimenRecords" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="View ${patientCase.doseRegimenRecords.size()} Dose Regimen Information" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
					       			<form action="/mellowHealth/doseRegimenRecords" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-success" type="submit" value="View All ${patientCase.doseRegimenRecords.size()} Dose Regimen Information" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
									</form>
								</c:otherwise>
							</c:choose>
				        	<c:choose>
					            <c:when test="${patientCase.physicalAssessments.size() < 1}">
					       			<form action="/mellowHealth/diagnosis/physicalAssessments" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="Schedule New Physical Assessment" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
					       			<form action="/mellowHealth/diagnosis/physicalAssessments" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="View All ${patientCase.physicalAssessments.size()} Physical Assessment Records" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
									</form>
								</c:otherwise>
							</c:choose>
								
				        	</div>
			        		<div class="btn btn-outline-primary inner-column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);background:rgba(13.33, 0.64, 0.60, 0.9);">
							<c:choose>
					            <c:when test="${patientCase.followUpRecords.size() < 1}">
			        			<form action="/mellowHealth/followUpRecords/newFollowUpRecord" method="get" style="width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-primary" type="submit" value="Schedule New Follow Up" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
								</form>
								</c:when>
					            <c:when test="${patientCase.followUpRecords.size() == 1}">
			        			<form action="/mellowHealth/followUpRecords/${mostRecentFollowUpRecord.id}" method="get" style="width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-primary" type="submit" value="${patientCase.followUpRecords.size()} Follow Up Detail" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
								</form>
								</c:when>
								<c:otherwise>
			        			<form action="/mellowHealth/followUpRecords/${mostRecentFollowUpRecord.id}" method="get" style="width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-primary" type="submit" value="${patientCase.followUpRecords.size()} Follow Up Details" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
								</form>
								</c:otherwise>
							</c:choose>
								<c:choose>
			                    <c:when test="${patientCase.painAssessments.size() < 1}">
				        			<form action="/mellowHealth/painAssessments/newPainAssessment" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="Schedule New Pain Assessment" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
			                    <c:when test="${patientCase.painAssessments.size() == 1}">
				        			<form action="/mellowHealth/painAssessments/newPainAssessment" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="View ${patientCase.painAssessments.size()} Pain Assessment Record" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
									</form>
								</c:when>
								<c:otherwise>
									<form action="/mellowHealth/painAssessments/newPainAssessment" method="get" style="width:100%;">
									    <input type="hidden" name="_method" value="get">
									    <input class ="btn btn-outline-warning" type="submit" value="View ${patientCase.painAssessments.size()} Pain Assessment Records" style=" margin:5px; width: 100%;padding:7px;width:100%;" >
									</form>
								</c:otherwise>
							</c:choose>
		        		</div>
	               		<div class="btn btn-outline-primary inner-column-card" style="align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			        		<p class="btn btn-outline-primary" style="margin:5px;">
			        			<c:out value="Contact Details: ${patientCase.physician.email}"/>
			        		</p>
				        	
			        		<p class="btn btn-outline-primary">
			        			Mellow Patient Since: <c:out value="${loggedInPatientCreatedAt}"/>
			        		</p>
					        <c:if test="${loggedInPatient.id == patientCase.patient.id}">
					        <div class="inner-column-card">
								<p class="btn btn-outline-warning column-card"  style="">
					        	<c:choose>
				                    <c:when test="${patientCase.patient.patientCases.size() < 2}">
						   				<c:out value="${patientCase.patient.patientCases.size()} Patient Visit"/>
				                    </c:when>
				                    <c:otherwise>
						   				<c:out value="${patientCase.patient.patientCases.size()} Patient Visits"/>
				                    </c:otherwise>
			               		</c:choose>
				            	<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" style="background: rgba(7, 1, 0.10, 0.9);">
				            		<c:out value="SCHEDULE PATIENT VISIT"/>
							    </a>
							 </p>
							</div>
				        </c:if>
			        	</div>
			        	</div>
	        		</div>
			    </div>
				    
				<div class="generic-display-wraps" style="background: rgba(7, 1, 0.10, 0.9);text-align:center; border-radius:5%;padding:10px;margin:5px 0">
					<h1 style="">
						<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-outline-warning" style="">
							<c:out value="Add New Case!"/>
						</a>
					</h1>
					<h1 style="">
						<a href="/mellowHealth/physicians" class="btn btn-outline-primary" style="">
							<c:out value="VIEW PROFILE!"/>
						</a>
					</h1>
					<h1 style="">
						<a href="/mellowHealth/physicians/logout" class="btn btn-outline-danger" style="">
							<c:out value="LOGOUT HERE!"/>
						</a>
					</h1>
					<form action="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" method="get" style="">
						<h1 style="">
							<input type="submit" value="PATIENTS PORTAL ACCESS!" class="btn btn-outline-primary" style="width:100%;background:rgba(0.531, 0.64, 16, 0.9);"/>
						</h1>
					</form>
				</div>	
				
		<table class="table table-dark" style="text-align:center;border-radius:5%;">
		  	<thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Treatment Plan</th>
			      <th scope="col">Physician</th>
			      <th scope="col">Follow Up Details</th>
			      <th scope="col">Actions Tab</th>
			    </tr>
		  	</thead>
			<tbody>
			</tbody>
			</table>
			    <div class="main-container-column" style="text-decoration:none; color:aqua;text-align:center;">
					<div class="main-container-column" style="padding:10px;">
				        <a href="/mellowHealth/diagnosticRecords" style="padding:5px;
							<c:choose>
			                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
					                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
			                    </c:when>
			                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
			                    </c:when>
			                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					                color: rgb(412, 580, 515);  background: rgba(17, 104, 130, 0.9); 
			                    </c:when>
		                    <c:otherwise>
		                        color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
		                    </c:otherwise>
		               		</c:choose>
		               			width:100%;" class="column-card
		               		<c:choose>
			                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
					            	btn btn-warning
			                    </c:when>
			                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					            	btn btn-primary
			                    </c:when>
			                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					            	btn btn-danger
			                    </c:when>
		                    <c:otherwise>
		                    	btn btn-primary
		                    </c:otherwise>
		               		</c:choose>	
		               		">
		               		<div class="btn btn-outline-danger " style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;background:rgba(13, 0.64, 0.60, 0.9);border-radius:5%;">
		               				<p class="btn btn-outline-warning">
		               					<c:out value="Patient Details: ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName}"/>
		               					<c:out value="Date Of Birth: ${patientCase.patient.dateOfBirth}- ${patientAge}yrs old"/>
		               				</p>
		               				<p class="btn btn-outline-danger">
		               					<c:out value=" ${patientCase.patient.patientLastName} ${createdAt} visit ${patientCase.diagnosticRecords.size()} Diagnostic Records"/>
		               				</p>
		               				<p>
			               				<c:out value="Chief Complaint: ${patientCase.chiefComplaint}: ${patientCase.painAssessments.size()} Pain Assessments"/>
					               		<c:out value="${patientCase.subjectiveSymptoms}"/>
				               		</p>
				               		<p class="btn btn-outline-warning">
				               			<c:out value="Currently Taking: ${patientCase.patientMedication}"/>
				               		</p>
				               		<p class="btn btn-outline-danger ">
				               			<c:out value="Medical History: ${patientCase.patient.pastMedicalHistories.size()} Past Medical Records- Following ${patientCase.patient.physiciansPatients.size()} Physicians"/>
				               		</p>
				         	 </div>
				             <div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; background:rgba(1.33, 0.64, 10.60, 0.9);border-radius:5%;">
						            <div>
						            	<c:out value="Historic Findings ${oneSearchedPatientCaseDiagnosticRecord.historyFindings}"/>
						            	
						            	<p class="btn btn-outline-primary" style=" background:rgba(1.33, 0.64, 10.60, 0.9);border-radius:5%;">
						            		<c:out value="Physical Exam Findings: ${oneSearchedPatientCaseDiagnosticRecord.physicalExamFindings} ${patientCase.patient.recentEmergencies.size()} Recent Emergencies"/>
						            	</p>
						            </div>
						            <p>
						            	<c:out value="Diagnostic Work Up: ${oneSearchedPatientCaseDiagnosticRecord.diagnosticWorkUp} "/>
						          
			            			</p>
						            <p>
						            	<c:out value="Differential Diagnosis: ${oneSearchedPatientCaseDiagnosticRecord.differentialDiagnosis} ${patientCase.currentMedications.size()} Current Medications"/>
						          
			            			</p>
			            			<div class="btn btn-outline-primary" style=" background:rgba(1.33, 0.64, 10.60, 0.9);border-radius:5%;">
							            <p>
							            	<c:out value="${patientCase.patient.recentEmergencies.size()} Recent Emergencies"/>
							          
				            				<c:out value="Incident Reports: ${patientCase.patient.incidentReports.size()} New Incidents"/>
				            			</p>
							            <p>
							            	<c:out value="Recreational Substance: ${patientCase.patient.recreationalSubstance}"/>
							            </p>
						            </div>
					         </div>
	               		</a>
				    <c:choose>
					<c:when test="${patientCase.patient.id == loggedInPatient.id}">
               	     <div class="btn btn-outline-warning main-container-column" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center; padding:15px;background:rgba(1.33, 0.64, 0.60, 0.9);">
		               	  <div class="btn btn-outline-primary column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;padding:5px;background:rgba(1.33, 0.64, 0.60, 0.9);width:100%;">
		               		<a href="/mellowHealth/diagnosticRecords"   style="text-decoration:none; 
				                <c:choose>
				                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
						                color: rgb(141, 580, 515);
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(211, 180, 255);
				                    </c:otherwise>
				                </c:choose>
					            text-align:center;display:flex;flex-direction:column;background;rgba(1.33, 0.64, 60, 0.9);">
					            <c:out value="Objective Findings: ${patientCase.diagnosticRecords.size()} Diagnostic Records- ${createdAt}"/>
					         </a>
						      </div>
			               	  <div class="column-card" style="text-decoration:none; 
				                <c:choose>
				                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
						                color: rgb(141, 580, 515);
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(211, 180, 255);
				                    </c:otherwise>
				                </c:choose>
				            	background;rgba(1.33, 0.64, 60, 0.9);">
			               		 <a class="column-card" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}"  style="text-decoration:none;background:rgba(112.33, 120.64, 10.60, 0.9);">
					        			<c:out value="Treatment Plan: ${oneSearchedPatientCaseDiagnosticRecord.treatmentPlan} Past Medical Records"/>
				               		 <p class="btn btn-outline-warning " style="background:rgba(1.33, 0.64, 0.60, 0.9)">
					        			<c:out value="Past Medical History: ${patientCase.patient.pastMedicalHistories.size()} Past Medical Records"/>
					        		</p>
					        	</a>
			               		<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;">
						            <c:out value="Current Treatment: ${patientCase.patientMedication}"/>
					        		<p class="btn btn-outline-warning " style="background:rgba(1.33, 0.64, 0.60, 0.9);">
							            <c:out value="Subjective Symptoms: ${patientCase.subjectiveSymptoms}"/>
					        		</p>
					        	</a>
					        </div>
							<div style="display:flex;flex-wrap:wrap;align-items:center;width:100%;">
		               		<div class="btn btn-outline-danger" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);width:100%;">
								<form action="/mellowHealth/hospitalDashboard/patientCases/edit/${patientCase.id}" method="get" style="margin:0 10px;width:100%;">
								    <input type="hidden" name="_method" value="edit">
								    <input class ="btn btn-warning" type="submit" value="Edit Case" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%;" >
								</form>
								<form action="/mellowHealth/hospitalDashboard/patientCases/delete/${patientCase.id}" method="post" style="margin:0 10px;width:100%;">
								    <input type="hidden" name="_method" value="delete">
									<input class ="btn btn-danger" type="submit" value="Delete Case" style=" margin:10px 0; width: 100%; display:block; padding: 10px;width:100%">
								</form>
							</div>
					        <div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
					            <p>Patient Name: <c:out value="${patientCase.patientName}"/></p>
					            <p>Date Of Birth: <c:out value="${patientCase.patient.dateOfBirth}"/></p>   	
					            <p>Patient Email: <c:out value="${patientCase.patient.email}"/></p> 
					         	<div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);"> 	
						            <p class="btn btn-outline-warning">Race: <c:out value="${patientCase.patient.race}"/></p>
						            <p class="btn btn-outline-warning">Gender: <c:out value="${patientCase.patient.gender}"/></p>
						        </div>
						            <p>Visitation: <c:out value="${createdAt}"/></p>
					            </div>
							</div>
						</div>
					 </div>
			 		</c:when>
					<c:otherwise>
					<div class="btn btn-outline-warning main-container-column" style="height:100%; 	
			        	<c:choose>
				            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
						         color: rgb(412, 580, 515);background:rgba(13, 64, 60, 0.9); 
				            </c:when>
				            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
						         color: rgb(412, 580, 515);background:rgba(13, 114, 160, 0.9); 
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						     	 color: rgb(412, 580, 515);background: rgba(133, 64, 60, 0.9); 
				             </c:when>
				             <c:otherwise>
				                  color: rgb(21, 180, 255);color:pink;background:rgba(133, 64, 60, 0.9);
				             </c:otherwise>
				         </c:choose>">
	               	     <div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
	               			<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
				                <c:choose>
				                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
						                color: rgb(141, 580, 515);
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(211, 180, 255);
				                    </c:otherwise>
				                </c:choose>
				            	text-align:center;display:flex;flex-direction:column;background;rgba(1.33, 0.64, 60, 0.9);">Objective Findings: <c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Records-${createdAt}"/>
				         	</a>
				         </div>
			             <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; color:pink;">
			            	 <c:out value="${patientCase.patientName}"/>: 
			            	 <div style="text-decoration:none;
				        		<c:choose>
					         	   <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
								         color: rgb(412, 580, 515);background:rgba(13, 64, 60, 0.9); 
					        	    </c:when>
					        	    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
								         color: rgb(412, 580, 515);background:rgba(13, 114, 160, 0.9); 
					      	      </c:when>
				      	      	  <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							     		 color: rgb(412, 580, 515);background: rgba(133, 64, 60, 0.9); 
				     	          </c:when>
				    	          <c:otherwise>
				    	              	color: rgb(21, 180, 255);color:pink;background:rgba(133, 64, 60, 0.9);
				    	          </c:otherwise>
				     	          </c:choose>
				     	        		border-radius:5%;">
					     	      <c:out value="${patientCase.chiefComplaint}"/> - <c:out value="${patientCase.painAssessments.size()} Pain Assessments"/>
						          <div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:0 10px; padding:5px; background:rgba(121.33, 0.64, 30.60, 0.9);">
						        	   <p style="text-align:center;padding:5px">
						        	   		<a class="btn btn-outline-warning " href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;">Complaint Details</a>
						        	   	</p>
						        	   <p class="btn btn-outline-danger " style="text-align:center;margin:5px 15px 15px 15px;padding:10px">
						        		    <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
							          			color:pink;text-align:center;">
							          			<c:out value="${patientCase.chiefComplaint}"/>
							          			<p>
							          			 	Obective Analysis: <c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Records"/>
							          			</p>
							          		</a>
							          	</p>
							       </div>
					        	
				               		<div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
							   			<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" style="text-decoration:none;">
							   		  		<p class="btn btn-outline-warning">SCHEDULE PATIENT VISIT
							            	</p>
							            </a>
							        </div>
			     	        	</div>
			             	</a>
			            </div>
					</c:otherwise>
		            </c:choose>
		          </div>
		          </div>

				<!-- Treatment Info Panel -->	    
	        	<c:if test="${patientCase.patient.id == loggedInPatient.id}">   
					<p style="
			        <c:choose>
			            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
					         color: rgb(412, 580, 515);background: rgba(1.3, 13.64, 1.60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					         color: rgb(412, 5.80, 51.5); background: rgba(1.3, 11.4, 16.60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(13.3, 1.64, 60, 0.9); background: rgba(11.3, 3.64, 12.60, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(201, 180, 255);background:rgba(4.3, 1.64, 41.60, 0.9); 
			          </c:otherwise>
			          </c:choose>
			             width: 100%; border-radius:5%;padding:10px;text-align:center;color:azure;font-size:26px
			             ">
			             <a class="btn btn-outline-success" href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" style="text-decoration:none; color:azure;text-align:center;width: 100%;">
				             <c:out value="Treatment Plan for ${patientCase.patientName}'s ${oneSearchedPatientCaseDayCreatedAt} Visit!"/>
				     	</a>
				     </p>
	        	</c:if>

		    	<div class="column-card" style="background:rgba(48, 18, 32, 0.9); border-radius:5%;padding:10px;">
			        <div class="btn btn-outline-warning inner-column-card" style="background:rgba(1.33, 0.64, 30.60, 0.9);padding:10px;">
			          	<div class="column-card" style="">
			          		<p class="btn btn-outline-warning" style=";border-radius:5%;">
			          			<c:out value="Day ${lengthOfPatientConditionDays} ${oneSearchedPatientCaseDayCreatedAt } Visit History"/>
			          		</p>
			          	</div>
			        	<p class="btn btn-outline-primary" style="text-align:center;flex-direction:column;display:flex; justify-content:center;
				           	 <c:choose>
					            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
							         color: rgb(412, 580, 515);background: rgba(0.13, 0.64, 60, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
							         color: rgb(412, 5.80, 5.15); background: rgba(13, 14, 16.201, 0.9);background: rgba(13, 114, 160, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							     	 color: rgb(412, 580, 515);background: rgba(7.8, 0.10, 10.101, 0.9)
					             </c:when>
					             <c:otherwise>
					              	 background: rgba(93, 164, 10.60, 0.9);background: rgba(133, 0.64, 60, 0.9);
					             </c:otherwise>
			             	   </c:choose>
				             	text-align:center; padding:15px;">
					            <a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="padding:10px;border-radius:5%;background: rgba(0.13, 0.64, 10, 0.9);
					        	    <c:choose>
					                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
							                color: rgb(130, 580, 325); 
					                    </c:when>
					                    <c:when test="${patientCase.chiefComplaint.contains('PM')}">
							                color: rgb(130, 581.0, 30.25);font-weight:bold; 
					                    </c:when>
					                    <c:otherwise>
					                        color: rgb(211, 18.0, 2.55)
					                    </c:otherwise>
					                  </c:choose>
				               		text-align:center;">
				               	    <c:out value="${patientCase.patientName} Mellow patient Since ${onePatientCreatedAt}"/>
			                	</a>
				               	<c:out value="Insurance: ${patientCase.insuranceInformation.providerName}"/>
			            	</p>
			      	   </div>
			      
			            <div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:5px; background:rgba(133, 0.64, 30.60, 0.9);">
			        	   <p class="" style="">
			        	   		<c:choose>
				                    <c:when test="${patientCase.diagnosticRecords.size() > 1}">
					        	   		<a class="btn btn-outline-warning " href="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticReport.id}" style="background:rgba(3, 0.64, 0.60, 0.9);padding:10px;">
					        	   			<c:out value="Complaint Details:  ${patientCase.diagnosticRecords.size()} Diagnostic Records"/>
				        	   			</a>
					                </c:when>
					                <c:otherwise>
					        	   		<a class="btn btn-outline-warning " href="/mellowHealth/diagnosticRecords/newDiagnosticRecord" style="background:rgba(3, 0.64, 0.60, 0.9);padding:10px;">
					        	   			<c:out value="Complaint Details:  ${patientCase.diagnosticRecords.size()} Diagnostic Records"/>
				        	   			</a>
				                    </c:otherwise>
				                  </c:choose>
			        	   	</p>
			        	   <div class="btn btn-outline-danger inner-column-card" style="border-radius:5%;">
			        		    <p>
			        		    	<a href="/mellowHealth/diagnosticRecords" style="text-decoration:none;color:pink;text-align:center;">
				          				<c:out value="${oneSearchedPatientCaseCreatedAt}: ${patientCase.chiefComplaint}"/>
				          			</a>
				          		</p>
				          		<p>
			        	   		<c:choose>
				                    <c:when test="${patientCase.diagnosticRecords.size() > 1}">
						          		<a href="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticReport.id}" class="btn btn-outline-danger" style=" background:rgba(13, 0.64, 30.60, 0.9);">
					          			 	<c:out value="Differential Diagnosis:  ${mostRecentDiagnosticReport.differentialDiagnosis}"/>
						          		</a>
						          	</c:when>
						          	<c:otherwise>
						          		<a href="/mellowHealth/diagnosticRecords" class="btn btn-outline-danger" style=" background:rgba(13, 0.64, 30.60, 0.9);">
					          			 	<c:out value="Differential Diagnosis:  ${mostRecentDiagnosticReport.differentialDiagnosis}"/>
						          		</a>
						          	</c:otherwise>
						          	</c:choose>
				          		</p>
				          	</div>
				        </div>  
			            <div class="btn btn-outline-warning inner-column-card" style="background:rgba(1.33, 0.64, 30.60, 0.9);">
		        		<p style="border-radius:5%;
			        		<c:choose>
					            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
							         color: rgb(412, 580, 515);background: rgba(0.13, 6.4, 60, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
							         color: rgb(412, 5.80, 5.15); background: rgba(13, 114, 160, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							         color: rgb(412, 580, 515);background: rgba(12.13, 0.64, 0.60, 0.9); 
					             </c:when>
					             <c:otherwise>
					                  color: rgb(21, 180, 255); color:pink;background: rgba(133, 0.64, 60, 0.9);
					             </c:otherwise>
				              </c:choose>">
					             <a class="btn btn-outline-danger" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;
				        		 	<c:choose>
					                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
							                color: khaki;
					                    </c:when>
					                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
							                color:rgb(41, 580, 511);
					                    </c:when>
					                    <c:otherwise>
					                        color: rgb(111, 180, 55); color:azure; background;rgba(133, 0.64, 60, 0.9);
					                    </c:otherwise>
				               		</c:choose>
				               		text-align:center;">
				               		<c:out value="Follow up Schedule ${mostRecentDiagnosticReport.followUpConsultation}"/>
				               		<c:out value="${patientCase.painAssessments.size()} Pain Assessments"/>
				               	</a>
		               		</p>
					        <p class="btn btn-outline-warning" style="border-radius:5%;">
					            <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
					                <c:choose>
					                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
							                color: rgb(141, 580, 515);
					                    </c:when>
					                    <c:otherwise>
					                        color: rgb(211, 180, 255);
					                    </c:otherwise>
					                </c:choose>
					            	text-align:center;display:flex;flex-direction:column;background:rgba(0.1, 0.2, 12, 0.9);padding:5px;border-radius:5%;">
					            	<c:out value="Physical Exam Findings: ${mostRecentDiagnosticReport.physicalExamFindings} ${patientCase.painAssessments.size()} Pain Assessments"/>: ${patientCase.physicalAssessments.size()} Physical Assessments 
					            </a>
					        </p>
					        </div>
			                <div class="btn btn-outline-warning inner-column-card" style="background:rgba(13.3, 0.64, 30.60, 0.9)">
								<p class="column-card" style="">
									<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="background:rgba(13.3, 0.64, 30.60, 0.9);
								        <c:choose>
								            <c:when test="${patientCase.patient.insuranceRecords.size() lt 20}">
								                color: rgb(71, 180, 24);
								            </c:when>
								            <c:otherwise>
								                color: rgb(21, 180, 25);
								            </c:otherwise>
								        </c:choose>
								        ">
									   <c:out value="Treatment Plan: ${mostRecentDiagnosticReport.treatmentPlan} ${patientCase.patient.physiciansPatients.size()} Private Physicians"/>
									   <c:out value="${patientCase.patient.patientCases.size()} Mellow Health Cases"/>
									</a>
								</p>
								<p class="btn btn-outline-warning inner-column-card"  style="background:rgba(1.3, 0.64, 30.60, 0.9);">
								    <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="background:rgba(3.3, 30.64, 0.60, 0.9);text-decoration:none;
								        <c:choose>
								            <c:when test="${patientCase.patient.insuranceRecords.size() lt 20}">
								                color: rgb(171, 180, 145);
								            </c:when>
								            <c:otherwise>
								                color: rgb(111, 180, 155);
								            </c:otherwise>
								        </c:choose>
								        ">
									    <c:out value="Deductible: ${patientCase.insuranceInformation.insuranceId}"/>
										<c:out value="Patient Email: ${patientCase.patient.email}"/>
								    </a>
								</p>
							</div>
				    </div>

					<div class="form-group"style="
			    		<c:choose>
				            <c:when test="${onePhysician.patientCases.size() <= 2 }">
						     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
				            </c:when>
				            <c:when test="${onePhysician.patientCases.size() % 2 == 1}">
						     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
				            </c:when>
				            <c:when test="${onePhysician.patientCases.size() % 2 == 0}">
						     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
				            </c:when>
				            <c:otherwise>
				                  color: rgb(21, 180, 255);background: rgba(7, 100, 8, 0.9);
				             </c:otherwise>
			             </c:choose>
		             	display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">
					<c:choose>
						<c:when test="${onePhysician.patientCases.size() < 1}">
						 	<h1 style="width: 100%">
							 	<a class="btn btn-danger" style="
								    <c:choose>
							            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
									         color: rgb(321, 180, 122.55);background:rgba(13, 1.64, 1.60, 0.9); 
							            </c:when>
							            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
									         color: rgb(321, 180, 122.55);background: rgba(1.3, 11.4, 16.60, 0.9); 
							            </c:when>
							            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
									     	 color: rgb(321, 180, 122.55);background: rgba(13.3, 1.64, 60, 0.9);background:rgba(13.3, 1.64, 60, 0.9);
							             </c:when>
							             <c:otherwise>
							                 color: rgb(321, 180, 122.55);background: rgba(11.8, 28, 0.80, 0.9); background: rgba(22.113, 1.64, 111.60, 0.9); 
							             </c:otherwise>
						        	 </c:choose> 
					         		margin:10px 0;width:100%;display:block;padding:10px;font-weight:bold;font-size:18px;" href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase">
					         		<c:out value="There are no admitted Patients in Dr. ${patientCase.physician.firstName}'s Care Yet!"/>
					         	</a>
				         	</h1>
						</c:when>
						<c:when test="${onePhysician.patientCases.size() == 1}">
							<h1 style="width: 100%">
								<a style="
								<c:choose>
						            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
								         color: rgb(321, 180, 122.55);background:rgba(13, 1.64, 1.60, 0.9); 
						            </c:when>
						            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
								         color: rgb(321, 180, 122.55);background: rgba(1.3, 11.4, 16.60, 0.9); 
						            </c:when>
						            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
								     	 color: rgb(321, 180, 122.55);background: rgba(13.3, 1.64, 60, 0.9);background:rgba(13.3, 1.64, 60, 0.9);
						             </c:when>
						             <c:otherwise>
						                 color: rgb(321, 180, 122.55);background: rgba(11.8, 28, 0.80, 0.9); background: rgba(22.113, 1.64, 111.60, 0.9); 
						             </c:otherwise>
						         </c:choose> 
				              margin:10px 0;width:100%;display:block;padding:10px;font-weight:bold;font-size:18px;" href="/mellowHealth/physicians/${loggedInPhysician.id }" class="btn btn-danger">
				              <c:out value="You Have Only One Patient In Your Care Dr. ${patientCase.physician.firstName}- Patients Details: ${patientCase.patientName} chief complaint ${patientCase.chiefComplaint} ${patientCase.patient.race} ${patientCase.patient.gender}: ${patientCase.physician.patientCases.size()} Admissions!"/> </a></h1>
						</c:when>
						<c:otherwise>
			 				<h1 style="width: 100%">
				 				<a style="
					        		<c:choose>
							            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
									         color: rgb(412, 5.80, 515);background: rgba(13, 1.64, 1.60, 0.9); 
							            </c:when>
							            <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
									         color: rgb(312, 222.80, 51.5); background: rgba(1.3, 11.4, 16.60, 0.9); 
							            </c:when>
							            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
									     	 color: rgb(412, 58.0, 515); background: rgba(13.3, 1.64, 60, 0.9);background:rgba(13.3, 1.64, 60, 0.9);
							             </c:when>
							             <c:otherwise>
							                  color: rgb(421, 18.0, 122.55);background: rgba(11.8, 28, 0.80, 0.9); background: rgba(2.113, 10.64, 1.60, 0.9); 
							             </c:otherwise>
						             </c:choose> 
					             	  margin:10px 0;width:100%;display:block;padding:10px;font-weight:bold;font-size:18px;" href="/mellowHealth/physicians/${patientCase.physician.id }" class="btn btn-success">
					             	 <p>
					             	 	<c:out value="Chief complaint: ${patientCase.chiefComplaint}"/> <c:out value="${patientAge} Yr Old ${patientCase.patient.race}"/> <c:out value="${patientCase.patient.gender}: ${createdAt} Visit!"/>
					             	
					             	 	<c:out value="Day ${lengthOfPatientConditionDays} Complaint History Dr. ${patientCase.physician.firstName}'s Care- ${patientCase.patient.physiciansPatients.size()} Private Physicians!"/>
					             	 </p>
					             </a>
				         	 </h1>
						 </c:otherwise>
					  </c:choose>
				</div>
				<div class="form-group column-card" style="background: rgba(0.178, 11, 10.10, 0.9);text-align:center; border-radius:5%;padding:12px;margin:10px">
					<h1 style=" margin:5px; width: 100%;">
						<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-outline-warning" style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(0.178, 11, 10.10, 0.9);color:yellow;">
							<c:out value="Add New Case!"/>
						</a>
					</h1>
					<h1 style=" margin:5px; width: 100%">
						<a href="/mellowHealth/physicians" class="btn btn-outline-primary" style="width: 100%; display:block; padding: 10px;">
							<c:out value="VIEW PROFILE!"/>
						</a>
					</h1>
					<h1 style=" margin:5px; width: 100%">
						<a href="/mellowHealth/physicians/logout" class="btn btn-outline-danger" style="width: 100%; display:block; padding: 10px;margin:5px;">
							<c:out value="LOGOUT HERE!"/>
						</a>
					</h1>
					<form action="/mellowHealth/physicians" method="get" style="margin:5px;width:100%;">
						<h1 style="border-radius:5%;">
							<input type="submit" value="PHYSICIAN CLUB ACCESS!" class="btn btn-outline-primary" style="margin:5px;width:100%;padding:10px;background:rgba(0.531, 0.64, 16, 0.9);"/>
						</h1>
					</form>
				</div>	
				<div class="form-group column-card"style="background:rgba(7.8, 0.10, 10.101, 0.9); border-radius:5%;padding:10px;margin:10px">
				 	<h1 style="width: 100%;margin:10px;">
				 		<a style="width:100%;display:block;padding:10px" href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-primary">
				 			<c:out value="VIEW MORE PATIENT CASES!"/>
				 		</a>
				 	</h1>
				 	<h1 style="width:100%;margin:10px;">
				 		<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style="width:100%; display:block; padding: 10px">
				 			<c:out value="CREATE A NEW CASE!"/>
				 		</a>
				 	</h1>	
					<h1 style=" width:100%;margin:10px;">
						<a href="/mellowHealth/physicians/logout" class="btn btn-danger"style="width: 100%; display:block; padding: 10px">
							<c:out value="LOGOUT HERE!"/>
						</a>
					</h1>
				</div>
				<div class="form-group column-card"style="
				    <c:choose>
			            <c:when test="${onePhysician.patientCases.size() <= 2 }">
					     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
			            </c:when>
			            <c:when test="${onePhysician.patientCases.size() % 2 == 1}">
					     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
			            </c:when>
			            <c:when test="${onePhysician.patientCases.size() % 2 == 0}">
					     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
			            </c:when>
			            <c:otherwise>
			                  color: rgb(21, 180, 255);background: rgba(7, 100, 8, 0.9);
			             </c:otherwise>
			             </c:choose>
		             padding:10px;margin:5px;">
					 <h1 style="width:100%;margin:5px;">
					 	<a style=" margin:5px; width: 100%; display:block; padding: 10px"  href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-success">
					 		<c:out value="GO BACK!"/>
					 	</a>
					 </h1>	
					 <h1 style="width:100%; margin:5px">
					 	<a style="background: rgba(0.68, 8, 120, 0.9); width:100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients" class="btn btn-outline-success">
					 		<c:out value="${loggedInPatient.patientFirstName} Mellow Patient Portal Access!"/>
					 	</a>
					 </h1>
				</div>
</body>
</html>