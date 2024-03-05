//package com.turuchie.mellowhealthportal.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.turuchie.mellowhealthportal.models.CountryInfo;
//
//@Component
//public class ApacheClientAPIKeyUtils {
//    private final ObjectMapper objectMapper;
//
//    @Value("${countryflags.api.key}")
//    private String countryFlagsApiKey;
//
//    public ApacheClientAPIKeyUtils(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    public List<CountryInfo> getCountryInfoList() {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            String apiUrl = "https://restcountries.com/v3.1/all";
//            HttpGet httpGet = new HttpGet(apiUrl);
//
//            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                String responseBody = EntityUtils.toString(response.getEntity());
//                return extractCountryInfoList(responseBody);
//            }
//        } catch (Exception e) {
//            handleException("Error fetching country info list", e);
//            return List.of();
//        }
//    }
//
//    private List<CountryInfo> extractCountryInfoList(String responseBody) {
//        try {
//            JsonNode rootNode = objectMapper.readTree(responseBody);
//
//            List<CountryInfo> countryInfoList = new ArrayList<>();
//            for (JsonNode countryNode : rootNode) {
//                String countryCode = countryNode.get("cca2").asText();
//                String phoneCode = fetchPhoneCodeFromCountryFlags(countryCode);
//
//                CountryInfo countryInfo = new CountryInfo(countryCode, phoneCode);
//                countryInfoList.add(countryInfo);
//            }
//
//            return countryInfoList;
//        } catch (Exception e) {
//            handleException("Error extracting country info list", e);
//            return List.of();
//        }
//    }
//
//    private String fetchPhoneCodeFromCountryFlags(String countryCode) {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            String apiUrl = "https://api.countryflags.io/v3.1/" + countryCode + "/?access_key=" + countryFlagsApiKey;
//            HttpGet httpGet = new HttpGet(apiUrl);
//
//            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                String responseBody = EntityUtils.toString(response.getEntity());
//                return extractPhoneCode(responseBody);
//            }
//        } catch (Exception e) {
//            handleException("Error fetching phone code", e);
//            return "";
//        }
//    }
//
//    private String extractPhoneCode(String responseBody) {
//        try {
//            JsonNode rootNode = objectMapper.readTree(responseBody);
//            return rootNode.get("callingCodes").get(0).asText();
//        } catch (Exception e) {
//            handleException("Error extracting phone code", e);
//            return "";
//        }
//    }
//
//    private void handleException(String message, Exception e) {
//        // Log the exception using a logging framework (e.g., SLF4J)
//        // Example: logger.error(message, e);
//        e.printStackTrace(); // Print stack trace (remove in production)
//    }
//}
