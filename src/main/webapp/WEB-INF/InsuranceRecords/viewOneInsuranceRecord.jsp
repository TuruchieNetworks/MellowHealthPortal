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
<link rel="stylesheet" href="/css/main.css"/>
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
		<a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" style=" margin: 0 15px 0 0; display:block; padding: 10px;color: khaki;text-decoration:none;font-size:26px;">
			<c:out value="Welcome To Your ${oneInsuranceInformation.providerName} Insurance Dashboard ${loggedInPatient.patientFirstName} ${currentDateTime}!"/>
		</a>
	</h1>
	<table class="table table-dark" style="text-align:center;border-radius:5%;">
		  <thead>
		    <tr>
		      <th scope="col">Id</th>
		      <th scope="col">Insurance ID</th>
		      <th scope="col">Patient Detail</th>
		      <th scope="col">Start Date</th>
		      <th scope="col">Exp Date</th>
		      <th scope="col">Actions</th>
		  </thead>
		<tbody>
		            <tr style="text-decoration:none; color:aqua"class="table-dark">
		                <td><c:out value="${oneInsuranceInformation.patient.id}" /></td>
		                <td>
		                	<a href="/mellowHealth/patientsPortal/patients/${oneInsuranceInformation.patient.id}"style="text-decoration:none; color:aqua">
		                		<c:out value="${oneInsuranceInformation.insuranceId}"/>
		                		<c:choose>
	        						<c:when test="${oneTotalInsuredPeriod < 2}">
		                				<c:out value="- ${oneTotalInsuredPeriod} Month Coverage"/>
		                			</c:when>
		                			<c:otherwise>
		                				<c:out value="- ${oneTotalInsuredPeriod} Months Coverage"/>
		                			</c:otherwise>
		                		</c:choose>
		                	</a>
		                </td>
		                <td>
		                	<a href="/mellowHealth/patientsPortal/patients/${oneInsuranceInformation.patient.id}"style="text-decoration:none; color:silk">
		                		<c:out value="${oneInsuranceInformation.providerName}"/>
		                	</a>
		                </td>
		                <td>
		                	<a href="/mellowHealth/patientsPortal/patients/${oneInsuranceInformation.patient.id}"style="text-decoration:none; 
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
					            <c:choose>
					                <c:when test="${oneInsuranceInformation.startDate != null && oneInsuranceInformation.expirationDate != null}">
						               					<c:out value="${oneInsuranceInformation.startDate}"/>
					                </c:when>
					                <c:otherwise>
					                    Coverage Length Calculation Error
					                </c:otherwise>
					            </c:choose>
	               			</a>
	               		</td>
		                <td>
		                	<a href="/mellowHealth/patientsPortal/patients/${oneInsuranceInformation.patient.id}"style="text-decoration:none; 
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
	               				<c:out value="${oneInsuranceInformation.expirationDate}"/>
	               			</a>
	               		</td>
		           		<c:choose>
						<c:when test="${oneInsuranceInformation.patient.id == loggedInPatient.id}">
				        <td style=" 	
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
				             " class="d-flex justify-content-around">
								<form action="/mellowHealth/insuranceRecords/editInsuranceRecord/${oneInsuranceInformation.id}" method="get" style="margin:0 10px 0 0; ">
								    <input type="hidden" name="_method" value="edit">
								    <input class ="btn btn-warning" type="submit" value="Edit Record" style=" margin:10px 0; width: 100%; display:block; padding: 10px" >
								</form>
								<form action="/mellowHealth/insuranceRecords/deleteInsuranceRecord/${oneInsuranceInformation.id}" method="post">
								    <input type="hidden" name="_method" value="delete">
									<input class ="btn btn-danger" type="submit" value="Delete Record" style=" margin:10px 0; width: 100%; display:block; padding: 10px">
								</form>
						</td>
		 				</c:when>
						<c:otherwise>
						<td class="table-dark d-flex justify-content-around">
							<a href="/mellowHealth/patientsPortal/patients/${oneInsuranceInformation.patient.id}"style="text-decoration:none; 
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
		               			<c:out value="${oneInsuranceInformation.providerName}: Coverage Period ${oneInsuranceInformation.startDate} - ${oneInsuranceInformation.expirationDate}"/>
		               		</a>
		               	</td>
						</c:otherwise>
            			</c:choose>
		            </tr>
		</tbody>
		</table>
	<div class="form-group"style="
	    <c:choose>
	       <c:when test="${loggedInPatient.insuranceRecords.size() <= 2}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 0.64, 0.36, 0.9);
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
		<h1 style=" margin: 5px; width: 100%">
			<a href="/mellowHealth/insuranceRecords" class="btn btn-outline-success" style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">
		        <c:out value="Valid Period: ${lengthOfCoverageDays} Days Remaining"/>
			</a>
		</h1>
	 	<h1 style="margin: 5px;width: 100%">
	 		<a style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords" class="btn btn-outline-primary">
		        <c:out value="Remaining Coverage: ${oneCoveragePeriod} Months"/>
	 		</a>
	 	</h1>
	 	<h1 style="margin: 5px;width: 100%">
	 		<a style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords" class="btn btn-outline-danger">
		        <c:out value="Total Insured Period: ${oneTotalInsuredPeriod} Months"/>	
	 		</a>
	 	</h1>
	</div>
	<div class="form-group btn btn-outline-primary" style="
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
		<h1 style=" margin: 5px; width: 100%">
			<a href="/mellowHealth/insuranceRecords/newInsuranceRecord" class="btn btn-outline-success" style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">
				<c:out value="${lengthOfCoverageDays} DAYS REMAINING: ADD NEW INSURANCE RECORD!"/>
			</a>
		</h1>
	 	<h1 style="margin: 5px;width: 100%">
	 		<a style=" margin:5px; width: 100%; display:block; padding: 10px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords/${mostRecentInsuranceReport.id}" class="btn btn-outline-success">
	 			<c:out value="VIEW ${oneCoveragePeriod} MONTHS COVERAGE INSURANCE INFORMATION!"/>
	 		</a>
	 	</h1>
	</div>
	<div class="form-group btn btn-outline-warning"style="
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
		<h1 style=" margin: 10px 15px 0 0; width: 100%">
			<a href="/mellowHealth/insuranceRecords/newInsuranceRecord" class="btn btn-success" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">
				<c:out value="Add New Record ${dayCurrentDateTime}!"/>
			</a>
		</h1>
		<h1 style=" margin: 10px 15px 0 0; width: 100%">
			<a href="/mellowHealth/patientsPortal/${loggedInPatient.id}" class="btn btn-outline-primary" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;background: rgba(0.2, 0.13, 10.160, 0.9);">
				<c:out value="${loggedInPatient.patientFirstName}: ${accountLengthDays}Days Portal Access"/>
			</a>
		</h1>
		<h1 style=" margin: 10px 15px 0 0; width: 100%">
			<a href="/instructors/logout" class="btn btn-warning" style="width: 100%; display:block; padding: 10px">
				<c:out value="LOGOUT HERE!"/>
			</a>
		</h1>
		<form action="/mellowHealth/insuranceRecords" method="get">
	    <c:choose>
	       <c:when test="${loggedInPatient.insuranceRecords.size() < 2}">
			<input type="submit" value="View ${loggedInPatient.insuranceRecords.size()} Insurance Record!" class="btn btn-primary"style="margin: 10px 0; width:100%; padding: 10px;background:rgba(10.1, 110.3, 10, 0.9)"/>
	       </c:when>
	       <c:otherwise>
			<input type="submit" value="View ${loggedInPatient.insuranceRecords.size()} Insurance Records!" class="btn btn-primary"style="margin: 10px 0; width:100%; padding: 10px;background:rgba(10.1, 110.3, 10, 0.9)"/>
	       </c:otherwise>
	    </c:choose>
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
	 		<a style=" margin:10px 15px 0 0 ; width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/insuranceRecords" class="btn btn-success">
	 			<c:out value="VIEW INSURANCE RECORDS!"/>
	 		</a>
	 	</h1>
	 	<h1 style=" margin: 10px 15px; width: 100%">
	 		<a href="/mellowHealth/insuranceRecords/newInsuranceRecord" class="btn btn-warning"style=" margin: 0 0 0 0px; width: 100%; display:block; padding: 10px;font-weight:bold;">
	 			<c:out value="ADD NEW RECORD!"/>
	 		</a>
	 	</h1>
		<h1 style="background:rgba(10.1, 110.3, 10, 0.9);margin: 10px 0; width: 100%">
			<a href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-primary" style=" margin: 10px 10px 10 0; width: 100%; display:block; padding: 10px;">
				<c:out value="${loggedInPatient.patientFirstName} Profile Access"/>
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
		<h1 style=" margin: 10px 15px 0 0; width: 100%">
			<a href="/instructors/logout" class="btn btn-danger" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">
				<c:out value="LOGOUT HERE!"/>
			</a>
		</h1>
		<h1 style="width:100%">
			<a style="background:rgba(68, 8, 120, 0.9);margin:16px 0 0 0;width:100%; display:block; padding: 10px" href="/mellowHealth/patientsPortal/patients/${loggedInPatient.id}" class="btn btn-success">
				<c:out value="${loggedInPatient.patientFirstName} Profile Access"/>
			</a>
		</h1>
		</div>	
</body>
</html>