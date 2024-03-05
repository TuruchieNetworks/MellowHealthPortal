package com.turuchie.mellowhealthportal.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.turuchie.mellowhealthportal.models.MyRequestData;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientVitalRecordService;

@Component
public class ApiIntegrationUtil {
	@Autowired
	private PatientVitalRecordService patientVitalRecordServ;

    private final RestTemplate restTemplate;

    public ApiIntegrationUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createPatientVitalRecord(String apiUrl, PatientVitalRecord requestData) {
        try {
            // Make a POST request to the API to create a new PatientVitalRecord
            ResponseEntity<PatientVitalRecord> responseEntity = restTemplate.postForEntity(apiUrl, requestData, PatientVitalRecord.class);

            // Handle the response and potential exceptions
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                PatientVitalRecord createdRecord = responseEntity.getBody();
                // You can handle the created record as needed
                System.out.println("New PatientVitalRecord created with ID: " + createdRecord.getId());
            } else {
                // Handle error (e.g., log or throw an exception)
                System.err.println("API request failed with status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., log or throw an exception)
            System.err.println("Error during API integration: " + e.getMessage());
        }
    }


    public void integrateApiDataIntoModel(Model model, Long id) {
        try {
            // Replace with your actual API endpoint and request data
            String apiUrl = "https://api.example.com/dOata";
            
            // Example: Creating a request object
            MyRequestData requestData = new MyRequestData();
            requestData.setSystolicBloodPressure(patientVitalRecordServ.getOne(id).getSystolicBloodPressure());
            requestData.setDiastolicBloodPressure(patientVitalRecordServ.getOne(id).getDiastolicBloodPressure());

            // Make a POST request to the API
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestData, String.class);

            // Handle the response and potential exceptions
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                model.addAttribute("apiResponse", responseBody);
            } else {
                // Handle error (e.g., log or throw an exception)
                System.err.println("API request failed with status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., log or throw an exception)
            System.err.println("Error during API integration: " + e.getMessage());
        }
    }
}