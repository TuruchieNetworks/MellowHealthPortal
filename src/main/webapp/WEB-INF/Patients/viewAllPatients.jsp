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
<link rel="stylesheet" href="/styles/styles.css"/>
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Mellow Health!</title>
</head>
<body class="container-fluid p-8  mellow-health-body 
    <c:choose>
        <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || loggedInPatient.patientCases.size() % 2 == 1}">
	         primary-mellow-health-body
        </c:when>
        <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || loggedInPatient.patientCases.size() % 2 == 0}">
	         secondary-mellow-health-body
        </c:when>
	    <c:otherwise>
             tertiary-mellow-health-body
        </c:otherwise>
    </c:choose>">
	<h1 class="mellow-health-header" style="background:rgba(20.1, 0.4, 0.9, 0.9);">
		<a style=""  href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}">
			<c:out value="Welcome To The Mellow Heath Patients Dashboard ${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName} ${currentDateTime}!"/>
		</a>
	</h1>
	<table class="table table-dark"style="border-radius:5%;">
			  <thead>
			    <tr style="text-align:center">
			      <th scope="col">Id</th>
			      <th scope="col">Patient</th>
			      <th scope="col">Incidence</th>
			      <th scope="col">Gender</th>
			      <th scope="col">Race</th>
			      <th scope="col">BirthDate Info</th>
			      <th scope="col">Actions</th>
			      
			    </tr>
			  </thead>
			<tbody>
			 </tbody>
		</table>
			<div class="column-card btn btn-outline-success">
			      <div class="inner-column-card btn btn-outline-warning
		        	 <c:choose>
			            <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
					        primary-column-card  
			            </c:when>
			            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
					        secondary-column-card
			            </c:when>
			            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
					     	tertiary-column-card 
			             </c:when>
			             <c:otherwise>
			                neutral-column-card 
			             </c:otherwise>
			         </c:choose>
			         " style="background: rgba(17, 1, 30, 0.9);">
			         <c:out value="Patient ID: ${loggedInPatient.id} Insurance Provider: ${oneInsuranceReport.providerName}"/>
			      </div>
				  <div class="inner-column-card " style="background: rgba(17, 1, 30, 0.9);border-radius:5%;">
				  	 <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="main-column-card  
					  	<c:choose>
		                    <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
				                primary-main-column-card
		                    </c:when>
		                    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
				                secondary-main-column-card
		                    </c:when>
		                    <c:otherwise>
		                       tertiary-main-column-card
		                    </c:otherwise>
		               	</c:choose>
		               	">
		               	<c:out value="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
		              </a>
		          </div>
				  <div>
				  <div class="column-card btn btn-outline-warning" style="background: rgba(17, 1, 30, 0.9);">
				  	 <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class=" 
				  		<c:choose>
		                    <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
				                alt-primary-column-card
		                    </c:when>
		                    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
				                alt-secondary-column-card
		                    </c:when>
		                    <c:otherwise>
		                      	alt-tertiary-column-card
		                    </c:otherwise>
	               		</c:choose>">
	               		<c:out value="${loggedInPatient.recentEmergencies.size()} Recent Emergencies"/>
	               	  </a>
	              </div>
	              </div>
				  <div>
					  <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="
					  	<c:choose>
		                    <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
				                male-color-column
		                    </c:when>
		                    <c:otherwise>
		                    	female-color-column
		                    </c:otherwise>
		               	</c:choose>">
		               	<c:out value="${loggedInPatientAge} Yr old ${loggedInPatient.gender}"/>
		              </a>
	              </div>
				  <div>
					  <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style="text-decoration:none; 
					  	<c:choose>
		                    <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
				                color: khaki;
		                    </c:when>
		                    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
				                color:pink;
		                    </c:when>
		                    <c:otherwise>
		                        color: rgb(211, 180, 255); color;
		                    </c:otherwise>
		               	</c:choose>
		               	"><c:out value="${loggedInPatient.race}"/>
		              </a>
		          </div>
				  <div>
				  	<a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="
		        		<c:choose>
				            <c:when test="${loggedInPatient.gender == 'Female'}">
						         primary-date-column-card
				            </c:when>
				            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
						         secondary-date-column-card
				            </c:when>
				            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0 || patient.race != 'Black'}">
						     	 tertiary-date-column-card 
				             </c:when>
				             <c:otherwise>
				                 neutral-date-column-card
				             </c:otherwise>
			             </c:choose>
			            ">
			            <c:out value="${loggedInPatient.dateOfBirth}"/>
			       	  </a>
			      </div>
			      <c:choose>
					<c:when test="${loggedInPatient.id == loggedInPatient.id}">
					    <div class="d-flex justify-content-around inner-generic-display-container" style=" 	
			        		<c:choose>
					            <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
							         color: rgb(412, 580, 515);background: rgba(13, 64, 60, 0.9); 
					            </c:when>
					            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
							         color: rgb(412, 580, 515); background: rgba(13, 114, 160, 0.9); 
					            </c:when>
					            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
							     	 color: rgb(412, 580, 515); background: rgba(133, 64, 60, 0.9); 
					             </c:when>
					             <c:otherwise>
					                  color: rgb(21, 180, 255); color:pink;background: rgba(133, 64, 60, 0.9);
					             </c:otherwise>
				            </c:choose>
				            ">
				            <div class="d-flex justify-content-around inner-generic-display-container" >
								<form action="/mellowHealth/patientsPortal/patients/edit/${loggedInPatient.id}" method="get" style="margin:5px; width:100%">
									<input type="hidden" name="_method" value="edit">
									<input class ="btn btn-outline-warning" type="submit" value="Update Patient Profile" style="background: rgba(13, 0.64, 0.60, 0.9);" >
								</form>
								<c:if test="${loggedInPatient.patientAddresses.size() < 1}">
									<form action="/mellowHealth/patientAddresses/newPatientAddress" method="get" style="margin:5px; width:100%">
										<input type="hidden" name="_method" value="edit">
										<input class ="btn btn-outline-primary" type="submit" value="Update Contact Details" style="background: rgba(13, 0.64, 0.60, 0.9);" >
									</form>
								</c:if>
								<form action="/mellowHealth/patientsPortal/patients/delete/${loggedInPatient.id}" method="post" style=" margin:5px;width:100%">
									<input type="hidden" name="_method" value="delete">
									<input class ="btn btn-outline-danger deleteHover" type="submit" value="Delete Patient Profile" style="background: rgba(13, 0.64, 6, 0.9);">
								</form>
							</div>
						</div>
		 			</c:when>
					<c:otherwise>
						<div class="table-dark">
							 <a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="alt-actions-column 
						  		<c:choose>
				                    <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
						            	 alt-actions-column btn btn-warning
				                    </c:when>
				                    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
						            	 alt-primary-actions-column btn btn-primary
				                    </c:when>
				                    <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
						            	 alt-secondary-actions-column btn btn-danger
				                    </c:when>
				                    <c:otherwise>
				                    	 alt-actions-column btn btn-primary  
				                    </c:otherwise>
				               	</c:choose>
			               		">
			               		<c:out value="${loggedInPatient.dateOfBirth} Patient Since ${patientCreatedAt}"/>
			               	</a>
			            </div>
					</c:otherwise>
		          </c:choose>
			    </div>
			<c:forEach items="${allPhysicians}" var="physician" varStatus="status">
			    <c:if test="${status.index < allPhysicianCreatedAtDates.size() and status.index < allPhysicianCreatedAtDates.size()}">
			        <!-- Access the current element from each collection using status.index -->
			        <c:set var="physicianCreatedAt" value="${allPhysicianCreatedAtDates[status.index]}" />
			        <c:set var="physicianDayCreatedAt" value="${allPhysicianDayCreatedAtDates[status.index]}" />
						
					<table class="table table-dark">
						 <thead>
							<tr style="text-align:center">
							    <th scope="col">Id</th>
							    <th scope="col">Physician</th>
							    <th scope="col">Specialty</th>
							    <th scope="col">Board Certified</th>
							    <th scope="col">Patients In Care</th>
						    </tr>
					      </thead>
						<tbody>
						</tbody>
					</table>
			        <div class="form-group main-generic-display btn btn-outline-primary" style="background:rgba(3.33, 0.64, 0.60, 0.9);background:rgba(1.33, 0.64, 0.60, 0.9);">
			            <!--  HTML content with styles -->
			            <div style="margin:5px 0" class="generic-display-wraps btn btn-outline-success 
			                	<c:choose>
				                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
						                btn btn-outline-success 
				                    </c:when>
				                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
						                btn btn-outline-primary 
				                    </c:when>
				                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						                btn btn-outline-danger 
				                    </c:when>
				                    <c:otherwise>
				                        btn btn-outline-success
				                    </c:otherwise>
			               		</c:choose>">
				        		<!-- Start Tabs Of Loop structures within the loop -->
				                <div class="btn btn-outline-success inner-generic-display-container 
				                	<c:choose>
					                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
							                primary-inner-generic-display-container
					                    </c:when>
					                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
							                secondary-inner-generic-display-container
					                    </c:when>
					                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
							                tertiary-inner-generic-display-container
					                    </c:when>
					                    <c:otherwise>
					                        
					                    </c:otherwise>
				               		</c:choose>
				               		" style="background: rgba(3, 64, 60, 0.9);">
					            	<div class="inner-generic-tab 
					            		<c:choose>
						                    <c:when test="${physician.isBoardCertified == 'Yes'}">
								                btn btn-outline-success
						                    </c:when>
						                    <c:when test="${physician.isBoardCertified == 'Pending'}">
								                btn btn-outline-success
						                    </c:when>
						                    <c:otherwise>
						                        btn btn-outline-success
						                    </c:otherwise>
						               		</c:choose>
						               		" style="background:rgba(1.33, 0.64, 0.60, 0.9);" >
				               			<c:out value="Mellow Physician ID: ${physician.id}"/>
					            		<a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; color:aqua;background:rgba(12.33, 40.64, 30.60, 0.9);">
					            			<c:out value="Dr. ${physician.firstName} ${physician.lastName}"/>
					            		</a>
					            	</div>
					            </div>

				        		<!-- Middle Tabs within the loop -->
				                <div class="btn btn-outline-warning second-generic-display-container" style="background:rgba(10.33, 0.64, 0.60, 0.9);
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
					                        color: rgb(21, 180, 255); color:pink;background: rgba(1.33, 3.4, 0.60, 0.9); 
					                    </c:otherwise>
				               		</c:choose>">
					            	<div class="alt-inner-generic-tab btn btn-outline-warning " style="background:rgba(10.33, 0.64, 0.60, 0.9);">
					            		<a class="    
						            		<c:choose>
							                    <c:when test="${physician.isBoardCertified == 'Yes'}">
									                btn btn-outline-warning
							                    </c:when>
							                    <c:when test="${physician.isBoardCertified == 'Pending'}">
									                btn btn-outline-danger
							                    </c:when>
							                    <c:otherwise>
							                        btn btn-outline-success
							                    </c:otherwise>
							               	</c:choose>
							               	" href="/mellowHealth/physicians/${physician.id}" style="text-decoration:none; color:silk">
							               	<c:out value="${physician.certificationSpecialty} ${physician.email}"/>
					            		 </a>
					            	 </div>
					             </div>
					            
			        		<!-- End Of Tabs within the loop -->
			                <div class="alt-generic-tab-end btn btn-outline-warning" style="
			                	<c:choose>
				                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
						                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                    </c:when>
				                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
						                color: rgb(4.12, 0.580, 515);  background: rgba(0.17, 30.64, 10.30, 0.9); 
				                    </c:when>
				                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						                color: rgb(4.12, 580, 515);  background: rgba(0.17, 31.04, 330.30, 0.9); 
				                    </c:when>
			                    <c:otherwise>
			                        color: rgb(21, 180, 255); color:pink;background: rgba(100.33, 0.4, 0.60, 0.9); 
			                    </c:otherwise>
			               		</c:choose>
			               		">
					         	<div class="alt-inner-generic-tab-end btn btn-outline-warning" style="background:rgba(1.33, 0.64, 30.60, 0.9);">
					                <a class="
					                <c:choose>
					                    <c:when test="${physician.isBoardCertified == 'Yes'}">
							                btn btn-outline-success 
					                    </c:when>
					                    <c:when test="${physician.isBoardCertified == 'Pending'}">
							                btn btn-outline-danger 
					                    </c:when>
					                    <c:otherwise>
								            btn btn-outline-warning 
								        </c:otherwise>
				               		</c:choose>
				               		" href="/mellowHealth/physicians/${physician.id}" style="text-decoration:none; 
						                <c:choose>
						                    <c:when test="${physician.isBoardCertified == 'Yes'}">
								                color: khaki;
						                    </c:when>
						                    <c:when test="${physician.isBoardCertified == 'Pending'}">
								                color:pink;
						                    </c:when>
						                    <c:otherwise>
						                        color: rgb(211, 180, 255); color;
						                    </c:otherwise>
						               		</c:choose>
						               		">
						               		<c:choose>
							                <c:when test="${physician.isBoardCertified eq 'Yes'}">
									             Certified 
							                </c:when>
							                <c:otherwise>
							                   	Pending
							                </c:otherwise>
										</c:choose> 
										<c:out value="Mellow Physician Since: ${physicianCreatedAt}"/>
									</a>
								</div>
							</div>
						</div>
	
					     <div class="table-dark d-flex justify-content-around generic-display-action-container" style="
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
				            </c:choose>
				            background:rgba(1.33, 110.64, 0.60, 0.9);">
				             <a href="/mellowHealth/physicians/${physician.id}" style="background:rgba(0.33, 0.64, 0.60, 0.9);
								<c:choose>
				                    <c:when test="${patient.physiciansPatients.size() % 2 == 0}">
						                color: rgb(412, 580, 515);  background: rgba(0.3, 64, 0.60, 0.9); 
				                    </c:when>
				                    <c:when test="${patient.physiciansPatients.size() % 2 == 1}">
						                color: rgb(412, 580, 515);  background: rgba(17, 64, 130, 0.9); 
				                    </c:when>
				                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						                color: rgb(412, 580, 515);background: rgba(0.3, 64, 0.60, 0.9); 
				                    </c:when>
				                    <c:otherwise>
				                        color: rgb(21, 180, 255); color:pink;background: rgba(0.3, 64, 60, 0.9); 
				                    </c:otherwise>
			               		</c:choose>
			               			padding:5px; border-radius:5%; width:100%; height:100%;" class= "
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
		               			<div class="generic-action-wraps" style="background:rgba(1.33, 0.64, 0.60, 0.9);">
			               			<c:out value="Patients In Care: ${physician.physiciansPatients.size()}"/>
			               			<p style="font-size:16px;"> Cases In Treatment:- ${physician.patientCases.size()} Cases!</p> 
									<form:form style="background:rgba(1.33, 0.64, 0.60, 0.9);" action="/mellowHealth/patientsPortal/hospitalDashboard/process/physiciansPatients/newPhysiciansPatient" method="POST" modelAttribute="physiciansPatient">
									    <form:input type="hidden" path="physician.id" value="${physician.id}"/>
									    <form:input type="hidden" path="patient.id" value="${loggedInPatient.id}"/>
									    <form:input type="hidden" path="patientName" value="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
									    <input class="btn btn-outline-warning" type="submit" value="Follow Physician"/>
									</form:form>
									<c:forEach items="${physician.physiciansPatients}" var="physiciansPatient">
									<c:if test="${loggedInPatient.id == physiciansPatient.patient.id}">
										<form:form action="/mellowHealth/physiciansPatients/delete/${physiciansPatient.id}" method="DELETE" modelAttribute="physiciansPatient">
										    <form:input type="hidden" path="physician.id" value="${physician.id}"/>
										    <form:input type="hidden" path="patient.id" value="${loggedInPatient.id}"/>
										    <form:input type="hidden" path="patientName" value="${loggedInPatient.patientFirstName} ${loggedInPatient.patientLastName}"/>
										    <input class="btn btn-outline-danger" type="submit" value="Unfollow Physician"/>
										</form:form>
									</c:if>
									</c:forEach>
								</div>
							</a>
						</div>
					</div>
					<div class="form-group panel-board"style="text-decoration:none;
		        		<c:choose>
			            <c:when test="${loggedInPatient.gender == 'Female' || patient.race == 'Black'}">
					         color: rgb(412, 580, 515);background:rgba(1.3, 64, 60, 0.9); 
			            </c:when>
			            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
					         color: rgb(412, 580, 515);background:rgba(1.3, 1.14, 1.60, 0.9); 
			            </c:when>
			            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
					     	 color: rgb(412, 580, 515);background:rgba(1.33, 0.64, 0.60, 0.9); 
			             </c:when>
			             <c:otherwise>
			                  color: rgb(21, 180, 255); color:pink;background: rgba(1.33, 6.4, 60, 0.9);
			             </c:otherwise>
			             </c:choose>
			             ">
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/incidentReports/newIncident" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px; background:rgba(71, 81, 120, 0.3);" class="btn btn-success" >REPORT NEW INCIDENT!</a></h1>
					<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/patientsPortal/logout" class="btn btn-danger" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
					 	<h1 style="width: 100%"><a style=" margin:10px 0; width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }" class="btn btn-primary">PATIENT PORTAL ACCESS!</a></h1>
				</div>
			    <!-- End Of Loop structures within the loop -->
			    </c:if>
			</c:forEach>	
	<div class="form-group"style="
	    		<c:choose>
	            <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
			         color: rgb(4.12, 58.0, 5.15);background: rgba(13, 64, 60, 0.9); 
	            </c:when>
	            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
			         color: rgb(41.2, 5.80, 5.15); background: rgba(1.10, 0.114, 5.160, 0.9); 
	            </c:when>
	            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
			     	 color: rgb(412, 5.80, 51.5); background: rgba(0.531, 0.64, 3.16, 0.9); 
	             </c:when>
	             <c:otherwise>
	                  color: rgb(1.21, 0.180, 2.5);background: rgba(18, 1.10, 18, 0.9); color:pink;
	             </c:otherwise>
	             </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
		 <h1 style=" margin:10px 5px 10px 0;width: 100%"><a style="width:100%; display:block;padding:10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id }" class="btn btn-primary"><c:out value="${loggedInPatient.patientFirstName}"/>'s Profile Details!</a></h1>
		 <h1 style=" margin:10px 0px 10px 5px;width:100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase"  class="btn btn-warning" style="; width: 100%; display:block; padding: 10px">Report A New Incident!</a></h1>
	 </div>	
	<div class="form-group"style="
	    		<c:choose>
	            <c:when test="${loggedInPatient.gender == 'Male' || patient.race == 'Black'}">
			         color: rgb(412, 580, 515);background: rgba(1.3, 64, 60, 0.9); 
	            </c:when>
	            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'Yes' || patient.patientCases.size() % 2 == 1}">
			         color: rgb(412, 580, 515); background: rgba(10, 0.114, 0.160, 0.9); 
	            </c:when>
	            <c:when test="${loggedInPatient.hasTravelledOutsideTheUnitedStatesForMoreThan30Days == 'No' || patient.patientCases.size() % 2 == 0}">
			     	 color: rgb(412, 580, 515); background: rgba(0.531, 0.64, 3.16, 0.9); 
	             </c:when>
	             <c:otherwise>
	                  color: rgb(21, 180, 255);background: rgba(0.8, 3.33, 10, 0.9);
	             </c:otherwise>
	             </c:choose>display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0">
					 <h1 style=" margin:10px 5px 10px 0;width: 100%"><a href="/mellowHealth/patientsPortal/logout" class="btn btn-danger" style="width: 100%; display:block; padding: 10px">LOG OUT!</a></h1>	
					 <h1 style=" margin:10px 0 10px 5px;width: 100%"><a style="width: 100%; display:block; padding: 10px"  href="/mellowHealth/patientsPortal/patients" class="btn btn-success">GO BACK!</a></h1>
	</div>
</body>
</html>