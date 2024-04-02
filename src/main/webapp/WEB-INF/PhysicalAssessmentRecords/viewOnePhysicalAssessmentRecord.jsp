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
<!-- Local CSS -->
    <link rel="stylesheet" href="/styles/patientCasestyles.css"/>
    <link rel="stylesheet" href="/styles/standaloneStyles.css"/>
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
		<a href="/mellowHealth/insuranceRecords/${mostRecentInsuranceReport.id}" style=" margin: 0 15px 0 0; display:block; padding: 10px;color: khaki;text-decoration:none;font-size:28px;">
			<c:out value="Welcome To Your ${mostRecentInsuranceReport.providerName} Insurance Dashboard ${loggedInPatient.patientFirstName} ${currentDateTime}!"/>
		</a>
	</h1>
	<table class="table table-dark" style="text-align:center;border-radius:5%;">
		  <thead>
		    <tr>
		      <th scope="col">Id</th>
		      <th scope="col">Medical Condtion</th>
		      <th scope="col">Treatment Plan</th>
		      <th scope="col">Start Date</th>
		      <th scope="col">Length Of Condition</th>
		      <th scope="col">Actions</th>
		  </thead>
		<tbody>
		</tbody>
	</table>
		<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;">
		    <form action="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id }" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName" placeHolder ="Please Enter Patient Name"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
		    <c:choose>
			<c:when test="${empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
					    <c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
					</a>
			    </p>
			</c:when>
			<c:otherwise>
			   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		<c:choose>
					<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].physicalAssessments.size()} Physical Assessments"/>
						</a>
					</c:when>
					<c:otherwise>
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
					    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
						</a>
					</c:otherwise>
				</c:choose>
			    </p>
			</c:otherwise>
			</c:choose>
		</div>
		    <div class="column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;margin:5px 0;width:100%;text-decoration:none; color:aqua;background: rgba(13, 0.64, 0.1, 0.9);">
		            <div style="text-decoration:none; color:aqua"class="table-dark column-card">
		                <div>
		                	<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}"style="text-decoration:none; color:aqua">
		                		<c:out value="${onePhysicalAssessment.patient.patientFirstName} ${onePhysicalAssessment.patient.patientLastName} Assessment ID: ${onePhysicalAssessment.id}" />
		                	</a>
		                </div>
		                <div>
		                	<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}"style="text-decoration:none; color:aqua">
		                		<c:out value="${onePhysicalAssessment.generalAppearanceDescription}"/>
		                	</a>
		                </div>
		                <div>
		                	<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}"style="text-decoration:none; color:silk">
		                		<c:out value="${onePhysicalAssessment.lungsDescription}"/>
		                	</a>
		                </div>
		                <div>
		                	<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}"style="text-decoration:none; 
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
	               				<c:out value="${onePhysicalAssessment.heartDescription}"/>
	               			</a>
	               		</div>
		                <div>
		                	<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}"style="text-decoration:none; 
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
	               				<c:out value="Day ${assessmentHistories}  ${createdAt} Record"/>
	               			</a>
	               		</div>
		           		<c:choose>
						<c:when test="${onePhysicalAssessment.patient.id == loggedInPatient.id}">
				        <div style=" 	
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
					                  color: rgb(21, 180, 255); color:pink;background:rgba(133, 64, 60, 0.9);
					             </c:otherwise>
				             </c:choose>
				             " class="d-flex justify-content-around main-container-column">
								<form action="/mellowHealth/diagnosis/physicalAssessments/editPysicalAssessment/${onePhysicalAssessment.id}" method="get" style="margin:2px;">
								    <input type="hidden" name="_method" value="edit">
								    <input class ="btn btn-warning" type="submit" value="Edit Record" style=" margin:5px; width: 100%;padding: 10px" >
								</form>
								<form action="/mellowHealth/diagnosis/physicalAssessments/deletePhysicalAssessment/${onePhysicalAssessment.id}" method="post" style="margin:2px;">
								    <input type="hidden" name="_method" value="delete">
									<input class ="btn btn-danger" type="submit" value="Delete Record" style=" margin:5px; width: 100%;padding: 10px">
								</form>
						</div>
		 				</c:when>
						<c:otherwise>
						<div class="table-dark d-flex justify-content-around">
							<a href="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id}" style="text-decoration:none; 
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
		               			<c:out value="${onePhysicalAssessment.generalAppearanceDescription}: Coverage Period ${onePhysicalAssessment.heartDescription} - ${lengthOfCondition} years"/>
		               		</a>
		               	</div>
						</c:otherwise>
            			</c:choose>
		            </div>
		            </div>
				    <div class="form-group column-card" style="
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
				   display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:5px 0;">	
			        <div class="btn btn-outline-warning column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						<form action="/mellowHealth/insuranceRecords/${onePhysicalAssessment.patientCase.insuranceInformation.id}" method="get" style="width:100%;">
							<input type="hidden" name="_method" value="get">
							<input class ="btn btn-outline-warning" type="submit" value="View Insurance Record" style="padding: 10px;width:100%;" >
						</form>
					</div>
			        <div class="btn btn-outline-warning inner-column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:2px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						<h1 style="width:100%;margin:5px;">
						 	<a style="background:rgba(8, 8, 10, 0.9);margin:5px;width:100%;" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-success">
						 		<c:out value="Mellow Patient Since ${loggedInPatientCreatedAt}"/>
							</a>
					 	</h1>
				 	</div>
				</div>
				    <div class="form-group column-card" style="
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
				   display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:5px 0;">
			        <div class="btn btn-outline-warning inner-column-card" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center;margin:5px; padding:2px;background:rgba(1.33, 0.64, 0.60, 0.9);">
						<h1 style="width:100%;margin:5px;">
						 	<a style="background:rgba(8, 8, 10, 0.9);margin:5px;width:100%;" href="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticReport.id}" class="btn btn-outline-success">
						 		<c:out value="View Diagnostic Record for ${createdAt}'s Visit"/>
							</a>
					 	</h1>
				 	</div>	

			        <div class="btn btn-outline-danger inner-column-card" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
				   	    <h1 style=" width: 100%">
						 	<a href="/mellowHealth/patientsPortal/logout" class="btn btn-danger" style=" width: 100%; display:block; padding: 5px; color:azure;">
						 		<c:out value="LOGOUT HERE ${currentDateTime}"/>
							</a>
					 	</h1>
			    	</div>
				</div>
		<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;">
		    <form action="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id }" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName" placeHolder ="Please Enter Patient Name"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
		    <c:choose>
			<c:when test="${empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
					    <c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
					</a>
			    </p>
			</c:when>
			<c:otherwise>
			   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		<c:choose>
					<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].physicalAssessments.size()} Physical Assessments"/>
						</a>
					</c:when>
					<c:otherwise>
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
					    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
						</a>
					</c:otherwise>
				</c:choose>
			    </p>
			</c:otherwise>
			</c:choose>
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
				            <c:if test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
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
							</c:if> 
		<div class="btn btn-primary generic-display-container" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:space-between;text-align:center;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;padding:5px;width:100%;">
		    <form action="/mellowHealth/diagnosis/physicalAssessments/${onePhysicalAssessment.id }" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName" placeHolder ="Please Enter Patient Name"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
		    <c:choose>
			<c:when test="${empty searchedPatientCase}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none;">
					    <c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
					</a>
			    </p>
			</c:when>
			<c:otherwise>
			   <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		<c:choose>
					<c:when test="${searchedPatientCase[0].patient.id == loggedInPatient.id}">
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
						    <c:out value="Searched Patient Details: ${searchedPatientCase[0].patient.patientFirstName} ${searchedPatientCase[0].patient.patientLastName} Date Of Birth: ${searchedPatientCase[0].patient.dateOfBirth} ${patientAge} yrs Old ${searchedPatientCase[0].patient.race}- ${searchedPatientCase[0].patient.gender} Contact Details: ${searchedPatientCase[0].patient.patientAddresses[0].phoneNumber} ${searchedPatientCase[0].physicalAssessments.size()} Physical Assessments"/>
						</a>
					</c:when>
					<c:otherwise>
			   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${searchedPatientCase[0].id}" style="text-decoration:none;">
					    	<c:out value="${dayCurrentDateTime} Enter Logged In Patient Details: ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
						</a>
					</c:otherwise>
				</c:choose>
			    </p>
			</c:otherwise>
			</c:choose>
		</div>  
				</c:forEach>
	<div class="form-group"style="
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
	    display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">
	 	<h1 style="width: 100%">
	 		<a style=" margin:10px 15px 0 0 ; width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${mostRecentInsuranceReport.id}" class="btn btn-success">
	 			<c:out value="VIEW CURRENT INSURANCE INFORMATION!"/>
	 		</a>
	 	</h1>
	</div>
	<div class="form-group"style="
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
	    display:flex; justify-content:space-between; padding:15px;border-radius:5%;">
		<h1 style=" margin:5px; width: 100%">
			<a href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" class="btn btn-outline-success" style=" margin: 5px; width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">
				<c:out value="Add New Record"/>
			</a>
		</h1>
		<h1 style=" margin:5px; width: 100%">
			<a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-secondary" style=" margin:5px; width: 100%; display:block; padding: 10px;background:rgba(0.1, 0.3, 10, 0.9);">
				<c:out value="${loggedInPatient.patientFirstName} Profile Access"/>
			</a>
		</h1>
		<h1 style=" margin:5px; width: 100%">
			<a href="/mellowHealth/patientsPortal/logout" class="btn btn-outline-warning" style="width: 100%; display:block; padding: 10px; margin:5px;">
				<c:out value="LOGOUT HERE!"/>
			</a>
		</h1>
		<form action="/mellowHealth/insuranceRecords" method="get" style=" margin:5px;width:100%;">
			<input type="submit" value="View All Insurance Records!" class="btn btn-outline-primary"style="margin:5px; width:100%; padding: 10px;background:rgba(10.1, 0.3, 10, 0.9);"/>
		</form>
	</div>	
	<div class="form-group"style="
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
	             display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">

	 	<h1 style="width: 100%">
	 		<a style=" margin:10px 15px 0 0 ; width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${insuranceReport.id}" class="btn btn-outline-success">
	 			<c:out value="VIEW INSURANCE RECORDS!"/>
	 		</a>
	 	</h1>
	 	<h1 style=" margin: 10px 15px; width: 100%">
	 		<a href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" class="btn btn-outline-warning"style=" margin: 0 0 0 0px; width: 100%; display:block; padding: 10px;font-weight:bold;">
	 			<c:out value="ADD PAST MEDICAL RECORD!"/>
	 		</a>
	 	</h1>
		<h1 style="background:rgba(10.1, 0.3, 10, 0.9);margin: 10px 0; width: 100%">
			<a href="/mellowHealth/patientsPortal/patients" class="btn btn-outline-primary" style=" margin: 10px 10px 10 0; width: 100%; display:block; padding: 10px;font-weight:bold;">
				<c:out value="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} Patient Profile!"/>
			</a>
		</h1>
	</div>
	<div class="form-group"style="
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
		 	<a style="background:rgba(8, 8, 10, 0.9);margin:5px;width:100%; display:block; padding: 10px" href="/mellowHealth/patientsPortal/${loggedInPatient.id}" class="btn btn-outline-success">
		 		<c:out value="Mellow Patient Since ${loggedInPatientCreatedAt}"/>
			</a>
	 	</h1>
	</div>	
</body>
</html>