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
<!-- For any Bootstrap that uses JS -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Mellow Health Physicians Dashboard!</title>
</head>
<body class="container-fluid p-8" style="
 		                <c:choose>
	                    <c:when test="${loggedInPhysician.isBoardCertified = 'Yes'}">
			                color: khaki;background:aqua;background: rgba(1.3, 290.64, 360, 0.9);
	                    </c:when>
	                    <c:when test="${loggedInPhysician.isBoardCertified = 'No'}">
			                color:pink; background: rgba(62.13, 110.123, 380.160, 0.9);
	                    </c:when>
	                    <c:otherwise>
	                        color: rgb(211, 180, 255);background:rgba(110.2, 100.4, 336.6, 0.9);
	                    </c:otherwise>
	               		</c:choose>font-family:cursive;">
	<h1 style="text-align:center;border-bottom: 2px solid chocolate;color: brown; font-family:fantasy;background:rgba(10.2, 3.3, 3.6, 0.9);margin-top:5px;border-radius:5%;">
	<a href="/mellowHealth/physicians/${loggedInPhysician.id}" style=" margin: 0 15px 0 0; display:block; padding: 10px;color: khaki;text-decoration:none;">Welcome To The Physician Dashboard Portal<c:out value=" Dr. ${loggedInPhysician.firstName}"/>, <c:out value="${dayCurrentDateTime}"/>!</a></h1>
	<table class="table table-dark" style="text-align:center;border-radius:5%;">
		  <thead>
		    <tr>
		      <th scope="col">Id</th>
		      <th scope="col">Physician</th>
		      <th scope="col">Specialty</th>
		      <th scope="col">Board Certified</th>
		      <th scope="col">Patients In Care</th>
		  </thead>
		<tbody>
		    <c:forEach items="${allPhysicians}" var="physician">
		            <tr style="text-decoration:none; color:aqua"class="table-dark">
		                <td><c:out value="${physician.id}" /></td>
		                <td><a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; color:aqua"><c:out value="Dr. ${physician.firstName} ${physician.lastName}"/></a></td>
		                <td ><a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; color:silk"><c:out value="${physician.certificationSpecialty}"/></a></td>
		                <td><a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; 
		                <c:choose>
	                    <c:when test="${physician.isBoardCertified == 'Yes'}">
			                color: khaki;
	                    </c:when>
	                    <c:when test="${physician.isBoardCertified == 'No'}">
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
						</c:choose></a></td>

		           		<c:choose>
						<c:when test="${physician.id == loggedInPhysician.id}">
				        <td  class="table-dark d-flex justify-content-around">
			             <a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; 
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
	               			padding:15px; border-radius:5%; width:100%; height:100%;"
	               		<c:choose>
	                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
			            	class= "btn btn-warning"
	                    </c:when>
	                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
			            	class= "btn btn-primary"
	                    </c:when>
	                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
			            	class= "btn btn-danger"
	                    </c:when>
	                    <c:otherwise>
	                    	class= "btn btn-primary"  
	                    </c:otherwise>
	               		</c:choose>	
	               			><c:out value="Patients In Care: ${physician.physiciansPatients.size()}"/>
	               			<p style="font-size:16px;"> Cases In Treatment:- ${physician.patientCases.size()} Cases!</p> 
						</a>
						</td>
						</td>
		 				</c:when>

						<c:otherwise>
						  <td class="table-dark d-flex justify-content-around">
							  <a href="/mellowHealth/physicians/${physician.id}"style="text-decoration:none; 
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
				               			padding:15px; border-radius:5%; width:100%; height:100%;"
				               		<c:choose>
				                    <c:when test="${createdAt.contains('Sat') || createdAt.contains('Sun')}">
						            	class= "btn btn-warning"
				                    </c:when>
				                    <c:when test="${createdAt.contains('Mon') || createdAt.contains('Tue')}">
						            	class= "btn btn-primary"
				                    </c:when>
				                    <c:when test="${createdAt.contains('Thur') || createdAt.contains('Fri')}">
						            	class= "btn btn-danger"
				                    </c:when>
				                    <c:otherwise>
				                    	class= "btn btn-primary"  
				                    </c:otherwise>
				               		</c:choose>	
		               			><c:out value="Patients In Care: ${physician.physiciansPatients.size()} Patients!"/>
		               			<p style="font-size:16px;"> Cases In Treatment:- ${physician.patientCases.size()} In Care!</p> 
							</a>
						</td>
						</c:otherwise>
            			</c:choose>
		            </tr>
		    </c:forEach>
		</tbody>
		</table>
	<div class="form-group"style="
 		                <c:choose>
	                    <c:when test="${loggedInPhysician.isBoardCertified = 'Yes' }">
			                color: khaki;background:aqua;background: rgba(13, 0.64, 60, 0.9);
	                    </c:when>
	                    <c:when test="${loggedInPhysician.isBoardCertified = 'No'}">
			                color:pink; background: rgba(13, 0.123, 0.160, 0.9);
	                    </c:when>
	                    <c:otherwise>
	                        color: rgb(211, 180, 255);background: rgba(5.2, 11.4, 11.6, 0.9);
	                    </c:otherwise>
	               		</c:choose>
	               		display:flex; justify-content:space-between; padding:15px;border-radius:5%;">
		<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-success" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;background: rgba(2, 10.13, 0.160, 0.9);">Add A New Visit!</a></h1>
		<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians/${loggedInPhysician.id}" class="btn btn-primary" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px;">View Dr. ${loggedInPhysician.firstName }'s Portal!</a></h1>
		<h1 style=" margin: 10px 15px 0 0; width: 100%"><a href="/mellowHealth/physicians/logout" class="btn btn-warning" style="width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
		<form action="/mellowHealth/hospitalDashboard/patientCases" method="get">
			<input type="submit" value="View Patient Cases!" class="btn btn-primary"style="margin: 10px 0; width:100%; padding: 10px;background:rgba(10.1, 110.3, 10, 0.9)"/>
		</form>
	</div>	
	<div class="form-group"style="
	    		<c:choose>
	            <c:when test="${loggedInPhysician.isBoardCertified = 'Yes' }">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
	            </c:when>
	            <c:when test="${loggedInPhysician.isBoardCertified = 'No'}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
	            </c:when>
	            <c:otherwise>
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9); 
	             </c:otherwise>
	             </c:choose>
	             display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">

	 	<h1 style="width: 100%"><a style=" margin:10px 15px 0 0 ; width: 100%; display:block; padding: 12px;background: rgba(13, 0.123, 0.160, 0.9);"  href="/mellowHealth/hospitalDashboard/patientCases" class="btn btn-success">VIEW HOSPITAL CASES!</a></h1>
	 	<h1 style=" margin: 10px 15px; width: 100%"><a href="/mellowHealth/hospitalDashboard/patientCases/newPatientCase" class="btn btn-warning"style=" margin: 0 0 0 0px; width: 100%; display:block; padding: 10px;font-weight:bold;">ADD NEW HEALTH CHANGE!</a></h1>
		<h1 style="background:rgba(10.1, 110.3, 10, 0.9);margin: 10px 0; width: 100%"><a href="/mellowHealth/physicians/${loggedInPhysician.id}" class="btn btn-primary" style=" margin: 10px 10px 10 0; width: 100%; display:block; padding: 10px;">View Dr. ${loggedInPhysician.firstName}'s Profile!</a></h1>
	</div>
	<div class="form-group"style="
	    		<c:choose>
	            <c:when test="${loggedInPhysician.isBoardCertified = 'Yes' }">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 0.36, 0.9);
	            </c:when>
	            <c:when test="${loggedInPhysician.isBoardCertified = 'No'}">
			     	 color: rgb(412, 580, 515); background: rgba(10.531, 10.64, 3.6, 0.9); 
	            </c:when>
	            <c:otherwise>
	                  color: rgb(21, 180, 255);background: rgba(7, 100, 8, 0.9);
	             </c:otherwise>
	             </c:choose>
	             display:flex; justify-content:space-between;align-items:center; border-radius:5%;padding:10px;margin:10px 0;">
			 	<h1 style=" margin: 10px 5px 0 0; width: 100%"><a href="/mellowHealth/physicians/logout" class="btn btn-danger" style=" margin: 0 15px 0 0; width: 100%; display:block; padding: 10px">LOGOUT HERE!</a></h1>
		 		<h1 style="width:100%"><a style="background:rgba(68, 8, 120, 0.9);margin:15px 0 0 0;width:100%; display:block; padding: 10px" href="/mellowHealth/physicians/${loggedInPhysician.id}" class="btn btn-success">Access Dr. ${loggedInPhysician.firstName }'s Portal!</a></h1>
		</div>	
</body>
</html>