package com.turuchie.mellowhealthportal.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UtilInputConverter {
    // Transient property to collect various lists
    private transient List<String> pastMedicalHistory;
    private transient List<String> chiefComplaints;

    // Other constructors, getters, and setters can be added as needed
    public UtilInputConverter() {}

    // Getter and setter for pastMedicalHistory
    public List<String> getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(List<String> pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    // Getter and setter for chiefComplaints
    public List<String> getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(List<String> chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    // Method to convert contents to string
    @Override
    public String toString() {
        return "UtilInputConverter{" +
                "pastMedicalHistory=" + listToString(pastMedicalHistory) +
                ", chiefComplaints=" + listToString(chiefComplaints) +
                '}';
    }

    // Helper method to convert List to a readable string
    private String listToString(List<String> list) {
        if (list == null) {
            return "null";
        }
        return Arrays.toString(list.toArray());
    }

    public List<String> convertStringToArray(String input) {
        return Arrays.asList(input.split("\\s*,\\s*"));
    }
    
}