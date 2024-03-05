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
    <link rel="stylesheet" href="/styles/styles.css"/>
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
		    <form action="/mellowHealth/diagnosticRecords" class="btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:space-between;align-items:center;text-align:center; padding:5px;background:rgba(1.33, 0.64, 30.60, 0.9);border-radius:7%;margin:5px;width:100%;">
		        <label  style="padding:10px">Search Patient Name</label>
		        <input style="width:40%;padding:5px;border-radius:7%;margin:5px" type="text" name="searchedPatientName"/>
		        <input class="btn btn-outline-primary" type="submit" value="Search Patient" style="margin:5px;width:30%;"/>
		    </form>
			<c:if test="${not empty searchedDiagnosticRecord}">
			    <p class="btn btn-outline-primary form-control" style="color:rgba(311, 31, 321, 0.9);background:rgba(11, 0.31, 1, 0.9);">
		   		  	<a class="btn btn-outline-primary" href="/mellowHealth/hospitalDashboard/patientCases/${patientCase.patient.id}" style="text-decoration:none;">
					    <c:out value="${searchedDiagnosticRecord[0].patient.patientFirstName} ${searchedDiagnosticRecord[0].patient.patientLastName} Date Of Birth: ${searchedDiagnosticRecord[0].patient.dateOfBirth} ${diagnosticRecordPatientAge}yrs Old"/>
					</a>
			    </p>
			</c:if>
		</div>
		<c:forEach items="${allDiagnosticRecordsWithFilter}" var="diagnosticRecord" varStatus="status">
		<div class="main-container-column btn btn-outline-success" style="width:100%;">
			<div class="btn btn-outline-primary" style="display:flex;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			     <div class="column-card btn btn-outline-success">
			      	<c:out value="Diagnostic ID: ${diagnosticRecord.id} ${diagnosticRecord.patientCase.onset}"/>
			         <a href="/mellowHealth/diagnosticRecords/${diagnosticRecord.id}"style="text-decoration:none; color:aqua">
			           <c:out value="Physical Findings: ${diagnosticRecord.physicalExamFindings}"/>
			         </a>
			      </div>
				  <div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px; padding:5px;background:rgba(1.33, 0.64, 0.60, 0.9);">
			      	<c:out value="Diagnostic Work Up: ${diagnosticRecord.patientCase.onset} Day: ${visitHistory} Progress"/>
			         <a href="/mellowHealth/diagnosticRecords/${diagnosticRecord.id}"style="text-decoration:none; color:silk">
			            <c:out value="${diagnosticRecord.diagnosticWorkUp}"/>
			         </a>
			       </div>
		       </div>
			   <div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
		          <a href="/mellowHealth/diagnosticRecords/${diagnosticRecord.id}"style="text-decoration:none; 
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
		              <c:out value="${diagnosticRecord.differentialDiagnosis}"/>
	             </a>
		         <a href="/mellowHealth/diagnosticRecords/${diagnosticRecord.id}"style="text-decoration:none; 
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
	             	   <c:out value="Day ${recordHistories} ${diagnosticRecordCreatedAt} Record"/>
	             </a>
	             </div>
				 <div class="inner-column-card btn btn-outline-primary" style="width:100%;display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:5px;padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
           		 <c:choose>
					<c:when test="${diagnosticRecord.patient.id == loggedInPatient.id}">
				        <div class="d-flex justify-content-around inner-column-card" style="width:100%;	
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
				 				<div class="btn btn-outline-primary" style="width:100%;display:flex;justify-content:center;align-items:center;text-align:center;margin:5px;padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
									<form action="/mellowHealth/diagnosticRecords/editPysicalAssessment/${diagnosticRecord.id}" method="get" style="margin:5px;width:100%;">
									    <input type="hidden" name="_method" value="edit">
									    <input class ="btn btn-outline-warning" type="submit" value="Edit Record" style=" margin:5px;width:100%;padding:10px" >
									</form>
									<form action="/mellowHealth/diagnosticRecords/deletePhysicalAssessment/${diagnosticRecord.id}" method="post" style="margin:5px;width:100%;">
									    <input type="hidden" name="_method" value="delete">
										<input class ="btn btn-outline-danger" type="submit" value="Delete Record" style="margin:5px;width:100%;padding:10px">
									</form>
								</div>
						</div>
		 				</c:when>
						<c:otherwise>
						<div class="table-dark d-flex justify-content-around">
							<a href="/mellowHealth/diagnosticRecords/${diagnosticRecord.id}" style="text-decoration:none; 
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
		               			<c:out value="${diagnosticRecord.insurance.providerName}: Coverage Period ${diagnosticRecord.insurance.insuranceId} - ${lengthOfCoverage} years"/>
		               		</a>
		               	</div>
						</c:otherwise>
            			</c:choose>
            		</div>
		       </div>
		        </c:forEach>
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
	       <c:when test="${loggedInPatient.insuranceRecords.size() < 1}">
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
			 		<a style="width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/diagnosticRecords/${mostRecentDiagnosticRecord.id}" class="btn btn-outline-warning">
			 			<c:out value="VIEW MOST RECENT DIAGNOSTIC REPORT!"/>
			 		</a>
		 		</div>
	 		</c:otherwise>
	      </c:choose>
	 	</h1>
	</div>
	<div class="form-group inner-generic-display-container" style="
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
			<a href="/mellowHealth/patientsPortal/logout" class="btn btn-outline-warning" style="width: 100%; display:block; padding: 10px;">
				<c:out value="LOGOUT HERE!"/>
			</a>
		</h1>
		<form action="/mellowHealth/insuranceRecords" method="get" style="margin:5px;width:100%;padding:10px;">
			<input type="submit" value="View All Insurance Records!" class="btn btn-outline-primary"style="width:100%; padding:10px;margin:5px;background:rgba(10.1, 0.3, 10, 0.9);"/>
		</form>
	</div>	
	<div class="form-group inner-generic-display-container" style="
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

	 	<h1 style="margin:5px; width: 100%;">
	 		<a style="width: 100%; display:block; padding:12px;background:rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${nsurance.insuranceId}" class="btn btn-outline-success">
	 			<c:out value="VIEW INSURANCE RECORDS!"/>
	 		</a>
	 	</h1>
	 	<h1 style=" margin:5px; width: 100%">
	 		<a href="/mellowHealth/diagnosis/physicalAssessments/newPhysicalAssessment" class="btn btn-outline-warning"style=" margin: 0 0 0 0px; width: 100%; display:block; padding: 10px;font-weight:bold;">
	 			<c:out value="ADD PAST MEDICAL RECORD!"/>
	 		</a>
	 	</h1>
		<h1 style="background:rgba(10.1, 0.3, 10, 0.9);margin:5px; width: 100%">
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
		 	<a style="background:rgba(8, 8, 10, 0.9);margin:5px;width:100%; display:block; padding: 10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-outline-success">
		 		<c:out value="Mellow Patient Since ${loggedInPatientCreatedAt}"/>
			</a>
	 	</h1>
	</div>	
</body>
</html>