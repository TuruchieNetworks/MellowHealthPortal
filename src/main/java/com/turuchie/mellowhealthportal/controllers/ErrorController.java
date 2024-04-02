package com.turuchie.mellowhealthportal.controllers;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turuchie.mellowhealthportal.utils.InsuranceUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	
	@Autowired
	private InsuranceUtil insuranceUtil;



    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error status code
        insuranceUtil.formatAndSetInsuranceDateAttributes(model);
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Handle different error status codes
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Set formatted date attributes in the model
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                // Pass error code and message to the model		Long patientId = (Long) session.getAttribute("patient_id");
        		// Add formatted dates to the model
                model.addAttribute("statusCode", statusCode);
                model.addAttribute("errorMessage", errorMsg);
                return "AdminDashboard/error.jsp"; // Show custom 500 page
            } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                // Pass error code and message to the model
                model.addAttribute("statusCode", statusCode);
                model.addAttribute("errorMessage", errorMsg);
                return "AdminDashboard/error.jsp"; // Show custom 500 page
            }
        }
        return "AdminDashboard/error"; // Default error page
    }

    public String getErrorPath() {
        return "/error";
    }
}
