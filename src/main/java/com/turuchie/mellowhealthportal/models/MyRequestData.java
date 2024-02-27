package com.turuchie.mellowhealthportal.models;
import org.springframework.beans.factory.annotation.Autowired;

import com.turuchie.mellowhealthportal.services.PatientVitalRecordService;

public class MyRequestData {
    @Autowired
    private PatientVitalRecordService patientVitalRecordServ;

    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private String param1;
    private String param2;

    // Constructors, getters, setters

    public void fetchBloodPressureValues(Long id) {
        // Assuming you have a method in your service to fetch blood pressure values by patient ID
        // Adjust this according to your actual service method
        if (id != null) {
            // Fetch blood pressure values using the service
            Integer systolicBP = patientVitalRecordServ.getOne(id).getSystolicBloodPressure();
            Integer diastolicBP = patientVitalRecordServ.getOne(id).getDiastolicBloodPressure();

            // Set the fetched values to the corresponding fields
            this.systolicBloodPressure = systolicBP;
            this.diastolicBloodPressure = diastolicBP;
        }
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

	public PatientVitalRecordService getPatientVitalRecordServ() {
		return patientVitalRecordServ;
	}

	public void setPatientVitalRecordServ(PatientVitalRecordService patientVitalRecordServ) {
		this.patientVitalRecordServ = patientVitalRecordServ;
	}

	public Integer getSystolicBloodPressure() {
		return systolicBloodPressure;
	}

	public void setSystolicBloodPressure(Integer systolicBloodPressure) {
		this.systolicBloodPressure = systolicBloodPressure;
	}

	public Integer getDiastolicBloodPressure() {
		return diastolicBloodPressure;
	}

	public void setDiastolicBloodPressure(Integer diastolicBloodPressure) {
		this.diastolicBloodPressure = diastolicBloodPressure;
	}

    // Getters and Setters

}