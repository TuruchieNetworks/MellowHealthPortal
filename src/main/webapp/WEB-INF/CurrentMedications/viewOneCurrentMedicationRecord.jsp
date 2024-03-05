<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>

    <!-- Your own local CSS -->
    <link rel="stylesheet" href="/styles/patientCasestyles.css"/>
    <link rel="stylesheet" href="/styles/standaloneStyles.css"/>

    <!-- Bootstrap JS -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <!-- Local JS -->
	<script src="<c:url value='/scripts.js'/>"></script>

    <meta charset="ISO-8859-1">
    <title>Mellow Health Portal!</title>
</head>
<body class="container-fluid p-8" style="
    <c:choose>
	     <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days = true }">
            background: rgba(1, 110.64, 110, 0.9);
        </c:when>
	    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days = false}">
            background: rgba(13, 330.2, 360, 0.9);
        </c:when>
	    <c:when test="${loggedInPatient.patientCases.size() % 2 == 1}">
            background: rgba(1, 10.64, 60, 0.9);
        </c:when>
        <c:otherwise>
            background: rgba(0.21, 0.180, 25, 0.9); background: rgba(0.531, 0.64, 16, 0.9); 
        </c:otherwise>
    </c:choose>
    font-family: cursive;">
            <h1 class="text-center border-bottom border-2" style="margin-top:5px;border-radius:5%;background: rgba(0.21, 0.180, 25, 0.9);color: brown; font-family: fantasy;font-size:32px">
                <a class="text-decoration-none text-brown" href="/mellowHealth/physicians/${loggedInPatient.id}">
                    Welcome To The Mellow Health Patient Cases Portal <c:out value="Dr. ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/> : You Have <c:out value="${loggedInPatient.patientCases.size()}"/> Patients In Your Care!
                </a>
            </h1>

    <table class="table table-dark text-center" style="border-radius: 5%;">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Patient</th>
            <th scope="col">Gender</th>
            <th scope="col">Chief Complaint</th>
            <th scope="col">Objective Findings</th>
            <th scope="col">Treatment Plan</th>
           	<th scope="col">Mellow Physician Visits</th>
           	<th scope="col">Actions</th>
        </tr>
        </thead>
		<tbody>
		</tbody>
		</table>
		<div class="btn btn-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;margin:5px 0;width:100%;">
		    <form action="/mellowHealth/currentMedications/${oneCurrentMedication.id}" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:25%;"/>
		    </form>
			<c:if test="${not empty oneSearchedSingleCurrentMedicationsList}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;">
					    <c:out value="${oneSearchedSingleCurrentMedicationsList[0].patient.patientFirstName} ${oneSearchedSingleCurrentMedicationsList[0].patient.patientLastName} Date Of Birth: ${oneSearchedSingleCurrentMedicationsList[0].patient.dateOfBirth} Insurance Provider: ${oneSearchedSingleCurrentMedicationsList[0].patientCase.insuranceInformation.providerName}"/>
					</a>
			    </p>
			</c:if>
		</div>
        <div class="column-card">
    		<div class="inner-column-card">
	        	<p class="btn btn-outline-success"  style="margin:10px;">
	        		<c:out value="Current Medication ID: ${oneSearchedSingleCurrentMedicationsList[0].id}"/>
			        <a class="inner-column-card" href="/mellowHealth/patientsPortal/patients/${oneCurrentMedication.patient.id}"  style="text-decoration:none; 
		         	<c:choose>
	                   <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
			                color: rgb(211, 180, 355);
	                   </c:when>
	                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
			                color:aqua;
	                    </c:when>
	                    <c:when test="${createdAt.contains('Wed') || createdAt.contains('Thur')}">
			                color:rgb(311, 180, 235);
	                    </c:when>
	                    <c:otherwise>
	                        color:aqua; color:rgb(201, 380, 235);
	                    </c:otherwise>
	            	</c:choose>">
	            	<c:out value="Drug Name: ${oneSearchedSingleCurrentMedicationsList[0].drugName} ${oneSearchedSingleCurrentMedicationsList[0].patient.patientLastName}: Mellow Patient Since ${onePatientCreatedAt}"/>
	            	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			            <c:out value="Administration Route: ${oneCurrentMedication.administrationRoute}"/>
		           		<div class="inner-column-card btn btn-outline-primary" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 10.64, 0.60, 0.9);">
			            	<c:out value="Dosage: ${oneCurrentMedication.dosage}"/>
			           		<div class="inner-column-card btn btn-outline-primary" style="display:flex;justify-content:space-between;align-items:center;text-align:center;background:rgba(1.33, 10.64, 0.60, 0.9);">
				            	<c:out value="Dosage Frequency: ${oneCurrentMedication.dosageFrequency}"/>
				            	<h5>
				            		<c:out value="Frequency Per Hour: ${oneCurrentMedication.dosageFrequencyPerHour}"/>
				            	</h5>
				            </div>
				            </div>
			            <p>
			            	<c:out value="Dosage Quantity: ${oneCurrentMedication.dosageQuantity}"/>
			            	<c:out value="Active Ingredient: ${oneCurrentMedication.activeIngredient}"/>
				            <c:out value="Insurance Provider: ${oneCurrentMedication.patientCase.insuranceInformation.providerName}"/>
				           	<c:out value="Coverage Details: ${oneCurrentMedication.patientCase.insuranceInformation.expirationDate}"/>
				       
			            </p>
		            </div>
				<div class="btn btn-outline-success column-card" style="padding:10px;">
					<div class="btn btn-outline-success inner-generic-display-container" style="padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);background:rgba(13.33, 0.64, 0.60, 0.9);">
	        			<form action="/mellowHealth/patientsPortal/patients/${oneCurrentMedication.patient.id}" method="get" style="margin:5px;width:100%;">
						    <input type="hidden" name="_method" value="get">
						    <input class ="btn btn-outline-success" type="submit" value="View Patient Portal" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
						</form>
						<c:if test="${loggedInPatient.id == oneSearchedSingleCurrentMedicationsList[0].patient.id}">
				    		<form action="/mellowHealth/patientsPortal/patients/${oneCurrentMedication.patient.id}" method="get" style="margin:5px;width:100%;">
							    <input type="hidden" name="_method" value="edit">
							    <input class ="btn btn-outline-warning" type="submit" value="Update Current Medication" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
							</form>
		        			<form action="/mellowHealth/currentMedications/deleteCurrentMedication/${oneSearchedSingleCurrentMedicationsList[0].id}" method="post" style="margin:5px;width:100%;">
							    <input type="hidden" name="_method" value="delete">
							    <input class ="btn btn-outline-danger" type="submit" value="Delete Current Medication" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
							</form>
						</c:if>
		        	</div>
	       		</div>
			</a>
		</p>
	  	</div>
		</div>
		<div class="btn btn-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;margin:5px 0;width:100%;">
		    <form action="/mellowHealth/patientAddresses" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:25%;"/>
		    </form>
			<c:if test="${not empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;">
					    <c:out value="${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth}"/>
					</a>
			    </p>
			</c:if>
		</div>
		
		<c:forEach items="${allPatientCasesWithFilter}" var="patientCase">
		    <div class="column-card" style="text-decoration:none; color:aqua;background: rgba(13, 0.64, 0.1, 0.9);">
		        <div class="inner-column-card btn btn-outline-success" style="">
		        	<a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="background: rgba(13, 78.64, 60, 0.9); color:aliceblue; margin:5px;">
		        		<c:out value="Mellow Patient ID: ${patientCase.id} ${searchedPatientCaseCreatedAt} Visit" />
		        	</a>
		        	<div class="inner-column-card btn btn-outline-success" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
		        		<a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none; color:aqua;widt:100%;">
		        			<c:out value="${patientCase.patient.patientFirstName} ${patientCase.patient.patientLastName}"/>
		        			<p>
		        				<c:out value="Date Of Birth: ${patientCase.patient.dateOfBirth}"/>
		        			</p>
		        		</a>
		        	</div>
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
						<p>
							<c:out value="${searchedPatientAge} Yr Old"/>
						</p>
						<p>
							<c:out value="${patientCase.patient.race} ${patientCase.patient.gender}"/>
						</p>
					</a>
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
							"><c:out value="Day ${patientCaseAccountLengthDays}: ${patientCase.diagnosticRecords.size()} Diagnostic Records!" />
						</a>
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
			                <p style="border-radius:5%;padding:5px;background:rgba(3.33, 0.64, 0.60, 0.9);">
			                	<c:out value="Treating Physician: Dr. ${patientCase.physician.firstName}  ${patientCase.physician.lastName}"/>
			                	<c:out value="Contact Details: ${patientCase.physician.email}  ${patientCase.physician.certificationSpecialty}"/>
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
									    <c:out value="${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth}"/>
									</a>
							    </p>
				             </c:otherwise>
			             </c:choose>
				</c:forEach>
				

				<c:choose>
				<c:when test="${loggedInPatient.patientCases.size() < 1}">
				<h1 style="width: 100%"><a style="
		    		<c:choose>
			            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					         color: rgb(4.12, 58.0, 515);background: rgba(13, 64, 60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					         color: rgb(412, 580, 515); background: rgba(13, 114, 160, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 0.180, 255); color:pink;
			             </c:otherwise>
		             </c:choose>
	              	  margin:10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientCases/newPatientCase" class="btn btn-danger">YOU HAVE NOT YET SCHEDULED ANY VISIT, CLICK HERE TO SCHEDULE NEW VISIT!</a>
	            </h1>
				</c:when>
				<c:otherwise>
					<h1 class="column-card" style="width: 100%">
						<a style="
				    		<c:choose>
					            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
							         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							         color: rgb(412, 580, 515); background: rgba(13, 0.114, 0.160, 0.9); 
					            </c:when>
					            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
					             </c:when>
					             <c:otherwise>
					                  color: rgb(21, 180, 255); color:pink;background: rgba(13, 114, 160, 0.9); background: rgba(0.531, 0.64, 16, 0.9); 
					             </c:otherwise>
				             </c:choose> 
				             margin:10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" class="btn btn-success">
				             <c:out value="${loggedInPatient.patientCases.size()} PATIENT CASES- ${loggedInPatient.physiciansPatients.size()} PATIENTS IN CARE!"/>
				             <c:out value="Mellow Physician Since: ${createdAt}"/>
			             </a>
	             	</h1>
				 </c:otherwise>
			     </c:choose>
	    	<div class="form-group btn btn-outline-primary"style="display:flex;align-items:center;padding:10px;
				<c:choose>
		              <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
				           color: rgb(412, 580, 515);background:rgba(17, 64, 30, 0.9); 
		              </c:when>
		              <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				          color: rgb(412, 580, 515);background:rgba(17, 0.64, 10, 0.9); 
		              </c:when>
		              <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				          color: rgb(412, 580, 51);background:rgba(11, 34, 73, 0.9); 
		             </c:when>
		             <c:otherwise>
		                  color: rgb(21, 180, 255);color:pink;background:rgba(13, 4, 60, 0.9); 
		            </c:otherwise>
	             </c:choose>
	             	border-radius:5%;padding:5px;">
				 <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;text-align:center; width:100%;">
					 <h3 style="font-family:fantasy;background:rgba(8, 2, 11, 0.7);border-radius:5%;padding:10px;">
					 	<c:out value="Total Patient Cases In Your Care: ${loggedInPatient.patientCases.size()}- ${loggedInPatient.physiciansPatients.size()} Private Patient Cases in Dr. ${loggedInPatient.patientFirstName} ${createdAt}!"/>
					 </h3>
				 </a>
		   </div>
    	<table class="table table-dark text-center" style="border-radius: 5%;">
	    <thead>
	         <tr>
			     <th scope="col">Chief Complaint</th>
			     <th scope="col">Objective Findings</th>
			     <th scope="col">Differential Diagnosis</th>
			     <th scope="col">Follow Up</th>
			     <th scope="col">Patient Demographics</th>
	         </tr>
	    </thead>
		<tbody>  
		<c:forEach items="${allPatientCasesWithFilter}" var="patientCase">
			<div class="btn btn-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;margin:5px 0;">
			    <form action="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px; ">
			        <label  style="padding:10px">Search Patient Name</label>
			        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
			        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:25%;"/>
			    </form>
				<c:if test="${not empty searchedPatientCase}">
				    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;">
						    <c:out value="${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth}"/>
						</a>
				    </p>
				</c:if>
			</div>

	     	<div class="inner-column-card" style="">
	     		<div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px; background:rgba(1.33, 0.64, 30.60, 0.9);">
			        <p class="btn btn-outline-primary column-card" style="text-align:center;
					<c:choose>
					    <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
						      color: rgb(4.12, 115.80, 351.5);  background: rgba(0.17, 0.64, 1.30, 0.9); 
				         </c:when>
				         <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						      color: rgb(92, 380, 15.15); background: rgba(17, 0.64, 1.30, 0.9); 
				         </c:when>
				         <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			        	      color: rgb(412, 580, 51);  background: rgba(11, 34, 73, 0.9); 
				         </c:when>
				         <c:otherwise>
				              color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9); 
				         </c:otherwise>
			         </c:choose>
		             ">
		             <c:out value="${patientCase.patient.patientFirstName }" /> <c:out value="${patientCase.patient.patientLastName }" />: 
		             <a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;color: rgb(412, 280, 5.15);text-align:center;">
		   		      	<c:out value="${searchedPatientCaseCreatedAt} Chief Complaint: ${patientCase.chiefComplaint} Day ${patientCaseAccountLengthDays} Case Update!" />
		   		     </a>
		   		     <c:out value="${patientCase.patient.email}"/> <c:out value="following: ${patientCase.physician.physiciansPatients.size()} Physicians"/>
		   		     </p>
   		     </div>
	        <div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:10px; background:rgba(1.33, 0.64, 30.60, 0.9);">
	        <div class="btn btn-outline-warning column-card" style="display:flex;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px; background:rgba(1.33, 0.64, 30.60, 0.9);">
		        <p class="btn btn-outline-primary" style="text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none; 
			    		<c:choose>
	     		            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
						         color: rgb(412, 580, 515);
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						         color: rgb(41, 580, 515); 
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						     	 color: rgb(42, 580, 515); 
				             </c:when>
				             <c:otherwise>
				                  color: rgb(21, 180, 255); color:pink;
				             </c:otherwise>
				          </c:choose>
		              	  text-align:center;">
		              	  <c:out value="${patientCase.currentMedications.size()} Current Medications  ${patientCase.adverseEffects.size()} Adverse Effects"/>
	                  </a>
	                  <c:out value="Past Medical Info: ${patientCase.patient.pastMedicalHistories.size()} Medical Histories"/>
              	  </p>
			      <p class="btn btn-outline-success" style="text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);"><c:out value="${patientCase.physicalAssessments.size()} Physical Assessment Records"/>
			          <a class="btn btn-outline-success" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none; color:khaki;text-align:center;">
						    <c:out value="${patientCase.diagnosticRecords.size()} Diagnostic Records  ${patientCase.painAssessments.size()} Pain Assessments  ${patientCase.currentMedications.size()} Current Medications  ${patientCase.painAssessments.size()} Pain Assessments"/> <c:out value="${searchedPatientCaseDayCreatedAt} visit"/> 
					  </a>
				  </p>
				  </div>
	        	  <div class="btn btn-outline-warning main-container-column" style="background:rgba(1.33, 0.64, 30.60, 0.9);width:100%;">
			        <p class="btn btn-outline-warning" style="text-align:center;background:rgba(0.1, 30.1, 32, 0.9);">Follow Up Schedule: 
			   		  <a href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;
				    	<c:choose>
				            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
						         color: rgb(412, 580, 515);
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						         color: rgb(41, 580, 515); 
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						     	 color: rgb(42, 580, 515); 
				             </c:when>
				             <c:otherwise>
				                  color: rgb(21, 180, 255); color:pink;
				             </c:otherwise>
				          </c:choose>
			              text-align:center;">
			             <c:out value="${patientCase.physicalAssessments.size()} Physical Assessment Records"/>
		              </a>
                 	  <c:out value="Insurance Id: ${patientCase.insuranceInformation.insuranceId}"/>
              	   </p>
			  	<div class="d-flex justify-content-around" style="border-radius:5%;display:flex;align-items:center; 	
			      <c:choose>
		              <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
				         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
		              </c:when>
		              <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				         color: rgb(412, 580, 515); background: rgba(0.13, 1.14, 1.60, 0.9); 
		              </c:when>
		              <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
		              </c:when>
		              <c:otherwise>
		                 color: rgb(21, 180, 255); color:pink; background: rgba(0.531, 0.64, 16, 0.9); 
		              </c:otherwise>
				  </c:choose>
				  ">
				  <p style="
				  <c:choose>
					  <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
			    		 color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
			          </c:when>
			          <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			     	     color: rgb(412, 580, 515); background: rgba(0.13, 0.114, 16, 0.9); 
			          </c:when>
			          <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			          </c:when>
			          <c:otherwise>
			              color: rgb(21, 180, 255); color:pink;
			          </c:otherwise>
			          </c:choose>
			          border-radius:5%;padding:5px;font-weight:bold;">Physician In Care: Dr. ${patientCase.physician.firstName}
						 <a href="/mellowHealth/patientsPortal/physicians/${patientCase.physician.id}" style="text-decoration:none; 
						 	 <c:choose>
								<c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					            	color: rgb(412, 580, 515);
				                </c:when>
				                <c:otherwise>
				                    color: rgb(211, 180, 255);
				                </c:otherwise>
			                </c:choose>text-align:center;"><c:out value="Patient: ${patientCase.patient.patientFirstName}"/>
			             </a>
		    		 <p class="btn btn-outline-warning"  style="
			    		<c:choose>
				            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
						         color: rgb(412, 580, 515);background: rgba(13, 0.64, 60, 0.9); 
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						         color: rgb(412, 580, 515); background: rgba(13, 1.14, 16, 0.9); 
				            </c:when>
				            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
				             </c:when>
				             <c:otherwise>
				                  color: rgb(21, 180, 255); color:pink;
				             </c:otherwise>
			             </c:choose>
			             border-radius:5%;padding:5px;font-weight:bold;">${patientCase.patient.patientFirstName}- ${patientCase.patient.dateOfBirth}
			             <a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.id}" style="text-decoration:none;
			    			<c:choose>
			                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
					                color: rgb(412, 580, 515);
			                    </c:when>
			                    <c:otherwise>
			                        color: rgb(211, 180, 255);
			                    </c:otherwise>
			               	</c:choose>
		                    text-align:center;text-decoration:none;			        
				   			<c:choose>
							    <c:when test="${course.price lt 20}">
							         color:rgb(11, 180, 245);color:pink;
							    </c:when>
							    <c:otherwise>
							         color: rgb(111, 180, 255);
							    </c:otherwise>
						  </c:choose>
				             text-align:center; font-weight:bold;"><c:out value="${patientCase.patient.race}- ${patientCase.patient.gender}"/>
				          </a>
				     </p>
				 </div>
			</div>
			</div>
			 <div class="form-group column-card" style="
			    	<c:choose>
			            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					         color: rgb(412, 580, 515);background: rgba(0.13, 0.64, 1.60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					         color: rgb(412, 580, 515); background: rgba(86, 0.114, 0.160, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 180, 255);background: rgba(78, 10, 80, 0.9); color:pink; background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:otherwise>
			        </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
				 	<h1 style=" margin: 10px; width: 100%">
				 		<a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style="color:black; margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;font-weight:bold;">
				 		<c:out value="SCHEDULE NEW PHYSICIAN VISIT!"/>
				 		</a>
				 	</h1>		
					<h1 style="width: 100%;margin:10px;">
						<a style="color:black; font-weight:bold; width: 100%; display:block; padding: 10px"  href="/mellowHealth/physicians/${loggedInPatient.id}" class="btn btn-primary">
							<c:out value="REPORT NEW INCIDENT!"/>
						</a>
					</h1>	
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
									    <c:out value="${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth}"/>
									</a>
							    </p>
				             </c:otherwise>
			             </c:choose> 
    		</c:forEach>
    	</tbody>
    	</table>
		<div class="form-group"style="
	    	<c:choose>
	            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
			         color: rgb(412, 580, 515);background: rgba(13, 0.64, 60, 0.9); 
	            </c:when>
	            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			         color: rgb(412, 580, 515); background: rgba(0.130, 0.114, 0.160, 0.9); 
	            </c:when>
	            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
	             </c:when>
	             <c:otherwise>
	                  color: rgb(21, 180, 255);background: rgba(78, 10, 80, 0.9); background: rgba(0.531, 0.64, 16, 0.9); 
	             </c:otherwise>
	             </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
				 <h1 style=" margin: 0px 15px 0 0; width: 100%">
				 <a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-primary" style="
			    	<c:choose>
			            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					         color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					         color: rgb(412, 580, 515); background: rgba(30, 0.114, 160, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 180, 255); color:pink;
			             </c:otherwise>
			             </c:choose>width: 100%; display:block; padding: 10px">Schedule A New Visit!</a>
			     </h1>
				 <h1 style="margin: 0px 15px 0 0; width: 100%"><a href="/mellowHealth/patientsPortal/patients" class="btn btn-warning" style="
			    		<c:choose>
			            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					         color: rgb(412, 580, 515); background: rgba(130, 1.14, 160, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 180, 255); color:pink; background: rgba(0.531, 0.64, 16, 0.9); 
			             </c:otherwise>
			             </c:choose>
			             width: 100%; display:block; padding: 10px">Patients Portal Access!</a>
			      </h1>
				  <form action="/mellowHealth/patientsPortal/patients/${patientCase.patient.id}" method="get">
				     <input type="submit" value="${loggedInPatient.patientCases.size()}: Patient Cases- ${loggedInPatient.physiciansPatients.size()}: Private Patients!" class="btn btn-success"style="
			    		<c:choose>
			            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
					         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					         color: rgb(412, 580, 515); background: rgba(553, 0.114, 16, 0.9); 
			            </c:when>
			            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
					     	 color: rgb(412, 580, 515); background: rgba(0.531,64, 16, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 180, 255); color:pink;
			             </c:otherwise>
			             </c:choose>margin: 10px 0; width:100%; padding: 10px"/>
				  </form>
		</div>
		<div class="form-group"style="
		    	<c:choose>
		            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
				         color: rgb(412, 580, 515);background: rgba(0.13, 0.64, 1.60, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				         color: rgb(412, 580, 515); background: rgba(86, 0.114, 0.160, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 16, 0.9); 
		             </c:when>
		             <c:otherwise>
		                  color: rgb(21, 180, 255);background: rgba(78, 10, 80, 0.9); color:pink; background: rgba(0.531, 0.64, 16, 0.9); 
		             </c:otherwise>
		        </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
		 	<h1 style=" margin: 10px; width: 100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;font-weight:bold;">SCHEDULE NEW PHYSICIAN VISIT!</a></h1>		
			<h1 style="width: 100%;margin:10px; "><a style=" width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-primary"><c:out value="You have ${loggedInPatient.patientCases.size()} Patients In Care${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>!</a></h1>	
		</div>
		<div class="form-group"style="
		    <c:choose>
		            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
				         color: rgb(412, 580, 515);background: rgba(13, 0.64, 0.60, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				         color: rgb(412, 580, 515); background: rgba(10, 0.114, 0.160, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 0.316, 0.9); 
		             </c:when>
		             <c:otherwise>
		                 color: rgb(21, 180, 255);background: rgba(78, 10, 80, 0.9); color:pink; background: rgba(18.31, 2.64, 1.116, 0.9); 
		             </c:otherwise>
		     </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
		
			<h1 style=" margin: 10px; width: 100%"><a href="/mellowHealth/physicians/logout" class="btn btn-danger" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
			<h1 style="width: 100%;margin:10px"><a style=" margin:10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/physicians" class="btn btn-success">GO BACK!</a></h1>
		</div>	
		<div class="form-group"style="
		    <c:choose>
		            <c:when test="${patientCase.patient.gender == 'Male' || patientCase.patient.race == 'Black'}">
				         color: rgb(412, 580, 515);background: rgba(13, 0.64, 0.60, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				         color: rgb(412, 580, 515); background: rgba(10, 0.114, 0.160, 0.9); 
		            </c:when>
		            <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
				     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 0.316, 0.9); 
		             </c:when>
		             <c:otherwise>
		                  color: rgb(21, 180, 255);background: rgba(78, 10, 80, 0.9); color:pink; background: rgba(18.31, 2.64, 1.116, 0.9); 
		             </c:otherwise>
		    </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
	     <h1 style="width:100%"><a style="background: rgba(68, 8, 120, 0.9); margin:10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }" class="btn btn-success">${loggedInPatient.patientFirstName} Patients Portal Access!</a></h1>
	</div>
</body>
</html>