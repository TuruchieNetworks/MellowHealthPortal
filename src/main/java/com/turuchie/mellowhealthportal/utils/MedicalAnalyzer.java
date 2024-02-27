//package com.turuchie.mellowhealthportal.utils;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MedicalAnalyzer {
//    public static void main(String[] args) {
//        // Sample WebMD data (replace this with your actual dataset)
//        List<MedicalRecord> webMdData = List.of(
//            new MedicalRecord("Headache", "Rest and pain relievers", "Stress"),
//            new MedicalRecord("Fever", "Fluids and rest", "Viral infection")
//            // Add more data...
//        );
//
//        // Sample patient complaints (replace this with actual patient data)
//        List<String> patientComplaints = List.of(
//            "I have a headache",
//            "Feeling tired and feverish"
//            // Add more patient complaints...
//        );
//
//        // Create a frequency table for patient complaints
//        Map<String, Integer> complaintFrequency = new HashMap<>();
//
//        // Process patient complaints
//        patientComplaints.forEach(complaint -> {
//            // Assume the complaint is in lowercase for case-insensitive matching
//            String normalizedComplaint = complaint.toLowerCase();
//
//            // Increment the frequency count for each complaint
//            complaintFrequency.put(normalizedComplaint, complaintFrequency.getOrDefault(normalizedComplaint, 0) + 1);
//        });
//
//        // Find the most common complaint
//        String mostCommonComplaint = null;
//        int maxFrequency = 0;
//
//        for (Map.Entry<String, Integer> entry : complaintFrequency.entrySet()) {
//            if (entry.getValue() > maxFrequency) {
//                maxFrequency = entry.getValue();
//                mostCommonComplaint = entry.getKey();
//            }
//        }
//
//        // Now 'mostCommonComplaint' contains the most frequently occurring patient complaint
//        System.out.println("Most common complaint: " + mostCommonComplaint);
//
//        // Match the most common complaint against WebMD data
//        MedicalRecord matchingCondition = webMdData.stream()
//            .filter(entry -> entry.getCondition().toLowerCase().equals(mostCommonComplaint))
//            .findFirst()
//            .orElse(null);
//
//        // If a match is found, use the corresponding data
//        if (matchingCondition != null) {
//            System.out.println("Match found in WebMD data: " + matchingCondition);
//        } else {
//            System.out.println("No match found in WebMD data");
//        }
//    }
//}
//
//class MedicalRecord {
//    private String disease;
//    private String treatment;
//    private String condition;
//
//    public MedicalRecord(String disease, String treatment, String condition) {
//        this.disease = disease;
//        this.treatment = treatment;
//        this.condition = condition;
//    }
//
//    public String getCondition() {
//        return condition;
//    }
//
//    @Override
//    public String toString() {
//        return "MedicalRecord{" +
//            "disease='" + disease + '\'' +
//            ", treatment='" + treatment + '\'' +
//            ", condition='" + condition + '\'' +
//            '}';
//    }
//}
//
//
