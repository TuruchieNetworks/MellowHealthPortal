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
						<a style="width: 100%; display:block; padding:10px;color:aqua;text-decoration:none;"  href="/mellowHealth/physicians/${loggedInPhysician.id}">
							<c:out value="Welcome To Your Patients Case Portal ${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName} ${dayCurrentDateTime}!"/>
						</a>
					</h1>
				</c:when>
				<c:otherwise>
    				<h1 style="text-align:center;border-bottom: 2px solid chocolate;color: brown; font-family:fantasy;border-radius:5%;background: rgba(7.8, 0.10, 10.101, 0.9)">
    					<a style="width: 100%; display:block; padding: 8px;color:aquamarine;text-decoration:none;" href="/mellowHealth/physicians">Welcome To <c:out value="Dr. ${patientCase.physician.firstName}" />'s Patients Cases Portal <c:out value="Dr. ${loggedInPhysician.firstName}"/> <c:out value="${dayCurrentDateTime}"/>!</a></h1>
				</c:otherwise>
			</c:choose>
			    <div class="main-container-column" style="text-decoration:none; color:aqua;text-align:center;">
			        <div class="column-card">
			    		<div class="inner-column-card" style="">
				        	<p class="btn btn-outline-success">
				        		<c:out value="Patient Case ID: ${patientCase.id}"/>
					        <a class="inner-column-card" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}"  style="text-decoration:none; 
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
				            	</c:choose>"><c:out value="${createdAt} Day ${oneVisitHistory} Treatment History"/>
				               	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						            <c:out value="${patientCase.chiefComplaint}: ${patientCase.subjectiveSymptoms}"/>
				               		<div class="inner-column-card btn btn-outline-primary" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 10.64, 0.60, 0.9);">
						            	<c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Records: ${patientCase.physicalAssessments.size()} Physical Assessments"/>
						            	<c:out value="Patient Medication: ${patientCase.currentMedications.size()} Medications- ${patientCase.adverseEffects.size()} Adverse Effects"/>
						            </div>
						            <p><c:out value="${createdAt} visit- ${patientCase.patient.email}"/></p>
					            </div>
				               	<div class="column-card btn btn-outline-primary" style="background:rgba(1.33, 10.64, 0.60, 0.9);">
					               	<div class="inner-column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
							            <p>
							            	<c:out value="Recent Admissions: ${patientCase.patient.recentAdmissions.size()} Recent Admissions"/>
							            </p>
					               		<div class="inner-column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
								            <p>
								            	<c:out value="Recent Emergencies: ${patientCase.patient.recentEmergencies.size()} Recent Emergencies"/>
								            </p>
								            <p>
								            	<c:out value="Recreational Substance: ${patientCase.patient.recreationalSubstance}"/>
								            </p>
							           	</div>
						            </div>
					               	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
							            <p>
							            	<c:out value="Date Of Birth: ${patientCase.patient.dateOfBirth}"/>
							            </p>
						            	<p>
						            		<c:out value="Following: ${patientCase.patient.physiciansPatients.size()} Physicians"/>
						            	</p>
						            	<p>
						            		<c:out value="Total Mellow Health Cases: ${patientCase.patient.patientCases.size()}"/>
						            	</p>
						            </div>
					            </div>
			            	</a>
			            	</p>
		            	</div>
		            </div>
			    	<div class="inner-column-card" style="">
		               	<div class="btn btn-outline-primary user-display-block" style="">
		               		<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="background:rgba(13.33, 0.64, 0.60, 0.9);">
				        		<p class="btn btn-outline-primary">
				        			<c:out value="Dr. ${patientCase.physician.firstName}" />
				        		</p>
			               		<div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
					        		<p class="btn btn-outline-primary" style="margin:5px">
					        			Patient Cases In Treatment: <c:out value="${patientCase.physician.patientCases.size()}"/>
					        		</p>
					        		<p class="btn btn-outline-primary" style="margin:5px;">
					        			Private Patients In Care: <c:out value="${patientCase.patient.physiciansPatients.size()}"/>
					        		</p>
					        	</div>
				               	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
				               		<div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						        		<p class="btn btn-outline-primary">
						        			Specialty: <c:out value="${patientCase.physician.certificationSpecialty}"/>
						        		</p>
						        	</div>
			               			<div class="inner-column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;background:rgba(13, 0.64, 0.60, 0.9);">
					               		<div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
							        		<p class="btn btn-outline-primary" style="margin:5px;">
							        			Contact Details: <c:out value="${patientCase.physician.email}"/>
							        		</p>
							        	
							        		<p class="btn btn-outline-primary">
							        			Mellow Patient Since: <c:out value="${loggedInPatientCreatedAt}"/>
							        		</p>
							        	</div>
						        	</div>
						        	<c:if test="${loggedInPatient.id == patientCase.patient.id}">
					               		<div class="btn btn-outline-primary">
								   			<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase">
								   		  		<p class="btn btn-outline-warning">SCHEDULE PATIENT VISIT!
								            	</p>
								            </a>
								        </div>
							        </c:if>
				    			</div>
				        	</a>
				        </div>
				     </div>
				</div>
				    
				<div class="form-group" style="display:flex;justify-content:space-between;flew-wrap:wrap;align-items:center;background: rgba(78, 10, 0.10, 0.9);text-align:center; border-radius:5%;padding:12px;margin:10px 0">
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">Add New Case!</a></h1>
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians" class="btn btn-primary" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">VIEW PROFILE!</a></h1>
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians/logout" class="btn btn-danger" style="width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
					<form action="/mellowHealth/physicians" method="get" style="display:flex;justify-content:space-between;flew-wrap:wrap;">
						<h1 style="display:flex;justify-content:space-between;flew-wrap:wrap;"><input type="submit" value="PHYSICIAN CLUB ACCESS!" class="btn btn-outline-primary" style="display:flex;justify-content:space-between;flew-wrap:wrap;margin:15px 10px 0px 0px;width:100%;padding:10px;background:rgba(0.531, 0.64, 16, 0.9);"/></h1>
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
					<div class="main-container-column">
				        <a class="column-card" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="
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
		               			border-radius:5%; width:100%;" class= "
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
		               		<div class="btn btn-outline-danger " style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;background:rgba(13, 0.64, 0.60, 0.9);">
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
				             <div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 10.60, 0.9);">
						            <p>
						            	<c:out value="Recent Admissions: ${patientCase.patient.recentAdmissions.size()} Recent Admissions"/>
						            </p>
						            <p>
						            	<c:out value="Recent Emergencies: ${patientCase.patient.recentEmergencies.size()} Recent Emergencies"/>
						            </p>
						            <p>
						            	<c:out value="Recreational Substance: ${patientCase.patient.recreationalSubstance}"/>
						            </p>
			            			<p>
			            				<c:out value="Incident Reports: ${patientCase.patient.incidentReports.size()} New Incidents"/>
			            			</p>
					         </div>
	               		</a>
				    <c:choose>
					<c:when test="${patientCase.patient.id == loggedInPatient.id}">
	               	     <div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:0 10px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			               	  <div class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);width:100%;">
			               		<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}"   style="text-decoration:none; 
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
			               	  <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
						                <c:choose>
						                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
								                color: rgb(141, 580, 515);
						                    </c:when>
						                    <c:otherwise>
						                        color: rgb(211, 180, 255);
						                    </c:otherwise>
						                </c:choose>
						            text-align:center;display:flex;flex-direction:column;background;rgba(1.33, 0.64, 60, 0.9);">
						            
					        		
				               		 <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}"  style="text-decoration:none;">
					               		 <p class="btn btn-outline-warning " style="text-align:center;flex-direction:column;">
						        			Past Medical History: <c:out value="${patientCase.patient.pastMedicalHistories.size()} Past Medical Records"/>
						        		</p>
						        	</a>
				               		<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;">
						        		<p class="btn btn-outline-warning " style="text-align:center;flex-direction:column;">
								            Past Medications: <c:out value="${patientCase.patientMedication}"/>
								            Subjective Symptoms: <c:out value="${patientCase.subjectiveSymptoms}"/>
						        		</p>
						        	</a>
						        </a>
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
					<div class="table-dark" style="height:100%; 	
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
	    
	        	<c:if test="${patientCase.patient.id == loggedInPatient.id}">   
					<a class="btn btn-primary" style="text-decoration:none; color:azure;text-align:center;width: 100%;">
					<h1 style="
			        <c:choose>
			            <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
					         color: rgb(412, 580, 515);background: rgba(111.3, 13.64, 12.60, 0.9); 
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
			             width: 100%; border-radius:5%;padding:15px;text-align:center;color:azure;font-size:28px
			             ">Treatment Plan for ${patientCase.patientName}'s ${createdAt} Visit!</h1>
				     </a>
	        	</c:if>   

		    	<div class="form-group d-flex main-container-column" style="background:rgba(48, 18, 32, 0.9); border-radius:5%;padding:10px;margin:5px 0;text-align:center;">
		        <div class="btn btn-outline-warning inner-column-card" style="background:rgba(1.33, 0.64, 30.60, 0.9);">
		          	<a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;">
		          		<p class="btn btn-outline-warning">
		          			<c:out value="Day ${oneVisitHistory} ${dayCreatedAt } Visit History"/>
		          		</p>
		          	</a>
		        	<p class="btn btn-outline-primary " style="text-align:center;flex-direction:column;
		         		<c:choose>
			                <c:when test="${patientCase.chiefComplaint.contains('AM')}">
					            border-radius:5%; padding:5px 0;
			                </c:when>
			                <c:otherwise>
			                    border-radius:5%; padding:5px 0;
			                </c:otherwise>
			             </c:choose>display:flex; justify-content:center;
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
		             	</c:choose>text-align:center;margin:5px 15px 15px 15px; padding:15px;">
			            <a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;width:100%;padding:10px;border-radius:5%;background: rgba(0.13, 0.64, 10, 0.9);
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
		               		<c:out value="${patientCase.patientName}"/>
	                	</a>
		               	<c:out value="Insurance: ${patientCase.insuranceInformation.providerName}"/>
		               </p>
		             </div>
		            <div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:0 10px; padding:5px; background:rgba(133, 0.64, 30.60, 0.9);">
		        	   <p style="text-align:center;padding:5px">
		        	   		<a class="btn btn-outline-warning " href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;">Complaint Details</a>
		        	   	</p>
		        	   <p class="btn btn-outline-danger " style="text-align:center;margin:5px 15px 15px 15px;padding:10px">
		        		    <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
			          			color:pink;text-align:center;">
			          			<c:out value="${patientCase.chiefComplaint}"/>
			          			<p>
			          			 	Diagnosis Analysis: <c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Records"/>
			          			</p>
			          		</a>
			          	</p>
			        </div>
		            <div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:0 10px; padding:10px; background:rgba(1.33, 0.64, 30.60, 0.9);">
	        		<p style="border-radius:5%;display:flex; justify-content:center;text-align:center
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
			             </c:choose>
		             	text-align:center;width:100%;display:flex;flex-direction:column;">
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
	               		text-align:center;">Follow up Schedule: <c:out value="${patientCase.painAssessments.size()} Pain Assessments"/></a>
	               		</p>
				        <p class="btn btn-outline-purple" style="">
				            <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none; 
				                <c:choose>
				                    <c:when test="${patientCase.chiefComplaint.contains('AM')}">
						                color: rgb(141, 580, 515);
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(211, 180, 255);
				                    </c:otherwise>
				                </c:choose>
				            text-align:center;display:flex;flex-direction:column;background:rgba(0.1, 0.2, 12, 0.9);padding:5px;border-radius:5%;"><c:out value="${patientCase.painAssessments.size()} Pain Assessments"/>: ${patientCase.physicalAssessments.size()} Physical Assessments 
				            </a>
				        </p>
				        </div>
		                <div class="btn btn-outline-warning" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:0 10px; padding:10px; background:rgba(13.3, 0.64, 30.60, 0.9);">
						    <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;
						        <c:choose>
						            <c:when test="${patientCase.patient.insuranceRecords.size() lt 20}">
						                color: rgb(71, 180, 245);
						            </c:when>
						            <c:otherwise>
						                color: rgb(211, 180, 255);
						            </c:otherwise>
						        </c:choose>
						        ">
								<p class="btn btn-outline-warning" style="text-align:center;display:flex; justify-content:space-between;flex-wrap:wrap;">
								       Demographic Details: <c:out value="${patientCase.patient.physiciansPatients.size()} Private Physicians"/> <c:out value="${patientCase.patient.patientCases.size()} Mellow Health Cases"/>
								</p>
							</a>
						    <a href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" style="text-decoration:none;
						        <c:choose>
						            <c:when test="${patientCase.patient.insuranceRecords.size() lt 20}">
						                color: rgb(71, 180, 245);
						            </c:when>
						            <c:otherwise>
						                color: rgb(211, 180, 255);
						            </c:otherwise>
						        </c:choose>
						        ">
							     <p class="btn btn-outline-warning" style="text-align:center;display:flex; justify-content:space-between;flex-wrap:wrap;">
							       Deductible: <c:out value="${patientCase.insuranceInformation.insuranceId}"/>	<c:out value="Patient Email: ${patientCase.patient.email}"/>
							     </p>
						    </a>
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
						 	<h1 style="width: 100%"><a class="btn btn-danger" style="
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
				         		margin:10px 0;width:100%;display:block;padding:10px;font-weight:bold;font-size:18px;" href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase">There are no admitted Patients in Dr. ${patientCase.physician.firstName}'s Care Yet!</a>
				         	</h1>
						</c:when>
						<c:when test="${onePhysician.patientCases.size() == 1}">
							<h1 style="width: 100%"><a style="
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
				              margin:10px 0;width:100%;display:block;padding:10px;font-weight:bold;font-size:18px;" href="/mellowHealth/physicians/${loggedInPhysician.id }" class="btn btn-danger">You Have Only One Patient In Your Care Dr. ${patientCase.physician.firstName}- Patients Details:<c:out value="${patientCase.patientName}"/> chief complaint <c:out value="${patientCase.chiefComplaint}"/> <c:out value="${patientCase.patient.race}"/> <c:out value="${patientCase.patient.gender}"/>: <c:out value="${patientCase.physician.patientCases.size()}"/> Admissions!</a></h1>
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
					             	 </p>
					             	 <p>
					             	 	<c:out value="Day ${visitHistory} Complaint History"/> <c:out value="Dr. ${patientCase.physician.firstName}'s Care- ${patientCase.patient.physiciansPatients.size()} Private Physicians!"/> </p>
					             </a>
				         	 </h1>
						 </c:otherwise>
					  </c:choose>
				</div>
				<div class="form-group" style="display:flex;justify-content:space-between;flew-wrap:wrap;align-items:center;background: rgba(78, 10, 0.10, 0.9);text-align:center; border-radius:5%;padding:12px;margin:10px 0">
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">Add New Case!</a></h1>
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians" class="btn btn-primary" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">VIEW PROFILE!</a></h1>
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians/logout" class="btn btn-danger" style="width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
					<form action="/mellowHealth/physicians" method="get" style="display:flex;justify-content:space-between;flew-wrap:wrap;">
						<h1 style="display:flex;justify-content:space-between;flew-wrap:wrap;"><input type="submit" value="PHYSICIAN CLUB ACCESS!" class="btn btn-outline-primary" style="display:flex;justify-content:space-between;flew-wrap:wrap;margin:15px 10px 0px 0px;width:100%;padding:10px;background:rgba(0.531, 0.64, 16, 0.9);"/></h1>
					</form>
				</div>	
				<div class="form-group"style="display:flex; justify-content:space-between;background:rgba(7.8, 0.10, 10.101, 0.9); border-radius:5%;padding:10px;margin:10px 0">
				 	<h1 style="width: 100%"><a style="margin:10px 0;width:100%;display:block;padding:10px" href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-primary">VIEW MORE PATIENT CASES!</a></h1>
				 	<h1 style="width:100%;margin:10px;"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style="width:100%; display:block; padding: 10px">CREATE A NEW CASE!</a></h1>	
					<h1 style=" width:100%;margin:10px 0;"><a href="/mellowHealth/physicians/logout" class="btn btn-danger"style="width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
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
					 <h1 style="width:100%"><a style=" margin:10px 5px 10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-success">GO BACK!</a></h1>	
					 <h1 style="width:100%">
					 	<a style="background: rgba(68, 8, 120, 0.9); margin:10px 5px; width: 99%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients" class="btn btn-success">
					 		<c:out value="Dr. ${loggedInPatient.patientFirstName} Patients Portal Access!"/>
					 	</a>
					 </h1>
				</div>
</body>
</html>