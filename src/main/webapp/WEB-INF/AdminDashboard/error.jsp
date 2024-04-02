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
            background: rgba(0.21, 0.180, 25, 0.9); background: rgba(0.531, 0.64, 16, 0.9);font-family: cursive;">
            <h1 class="text-center border-bottom border-2" style="margin-top:5px;border-radius:5%;background: rgba(0.21, 0.180, 25, 0.9);color: brown; font-family: fantasy;font-size:32px">
                <a class="text-decoration-none text-brown" href="/mellowHealth/patientsPortal/login">
                    <c:out value="Mellow Health Error Dashboard ${currentDateTime}!"/>
                </a>
            </h1>

        <div class="column-card"  style="text-align:center;">
    		<div class="inner-column-card" style="">
	        	<p class="btn btn-outline-success" style="margin:15px;text-align:center;align-items:center;">
	        		<c:out value="ErrorCode"/>
			        <a class="inner-column-card" href="/mellowHealth/patientsPortal/patients"  style="display:flex;flex-direction:column;justify-content:center;text-align:center;text-decoration:none; color:aqua; color:rgb(201, 380, 235);">
		        		<c:choose>
			        		<c:when test="">
					        	<c:out value="${statusCode} Page Not Found Error!"/>
			        		</c:when>
			        		<c:otherwise>
					        	<c:out value="${statusCode} Internal Server Error!"/>
					        </c:otherwise>
				        </c:choose>
		            	<div class="column-card btn btn-outline-primary" style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;text-align:center;margin:10px 0; padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);">
				            <c:out value="Error Message: The Page You are Looking For Does Not Exist"/>
			           		<div class="inner-column-card btn btn-outline-primary" style="display:flex;flex-direction:column;justify-content:center;text-align:center;margin:5px; padding:10px;background:rgba(1.33, 10.64, 0.60, 0.9);">
				        		<c:out value="Use The Links Provided In Links-Panel Bellow To Renavigate, ${dayCurrentDateTime}"/>
				            </div>
				            <c:out value="Error Details: ${errorMessage} Today, ${currentDateTime}"/>
			            </div>
						<div class="btn btn-outline-success column-card" style="padding:15px;">
							<div class="btn btn-outline-success inner-generic-display-container" style="padding:10px;background:rgba(1.33, 0.64, 0.60, 0.9);background:rgba(13.33, 0.64, 0.60, 0.9);">
			        			<form action="/mellowHealth/patientsPortal/patients" method="get" style="margin:5px;width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-success" type="submit" value="Mellow Patient Portal" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
								</form>
					    		<form action="/mellowHealth/patientsPortal/register" method="get" style="margin:5px;width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-warning" type="submit" value="Create New Patient Account" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
								</form>
			        			<form action="/mellowHealth/physicians" method="get" style="margin:5px;width:100%;">
								    <input type="hidden" name="_method" value="get">
								    <input class ="btn btn-outline-danger" type="submit" value="Mellow Physicians Portal" style="width:100%;background:rgba(13.33, 0.64, 0.60, 0.9);" >
								</form>
				        	</div>
			       		</div>
					</a>
				</p>
	  	</div>
		</div>
</body>
</html>