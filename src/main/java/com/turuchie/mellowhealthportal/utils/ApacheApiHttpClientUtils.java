package com.turuchie.mellowhealthportal.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turuchie.mellowhealthportal.models.CountryInfo;

@Component
public class ApacheApiHttpClientUtils {
    private final ObjectMapper objectMapper;

    // Inject your GeoNames API username
    private final String geoNamesApiKey = "your_geo_names_api_key_here";

    public ApacheApiHttpClientUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private void handleException(String message, Exception e) {
        // Log the exception using a logging framework (e.g., SLF4J)
        // Example: logger.error(message, e);

        // Print stack trace (remove in production)
        e.printStackTrace();
    }

    public List<String> getCountryCodes() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiUrl = "https://restcountries.com/v3.1/all";
            HttpGet httpGet = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return extractCountryCodes(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
            return List.of();
        }
    }

    private List<String> extractCountryCodes(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            List<String> countryCodes = new ArrayList<>();
            for (JsonNode countryNode : rootNode) {
                String countryCode = countryNode.get("cca2").asText();
                countryCodes.add(countryCode);
            }

            return countryCodes;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
            return List.of();
        }
    }

    public List<String> getNearbyPostalCodes(double latitude, double longitude, int radius) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiUrl = "http://api.geonames.org/findNearbyPostalCodesJSON?lat=" + latitude +
                             "&lng=" + longitude +
                             "&radius=" + radius +
                             "&maxRows=5" +  // You can adjust maxRows as needed
                             "&username=" + geoNamesApiKey;

            HttpGet httpGet = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return extractPostalCodes(responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
            return List.of();
        }
    }

    private List<String> extractPostalCodes(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode postalCodesNode = rootNode.path("postalCodes");

            List<String> postalCodes = new ArrayList<>();
            for (JsonNode codeNode : postalCodesNode) {
                String postalCode = codeNode.get("postalCode").asText();
                postalCodes.add(postalCode);
            }

            return postalCodes;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
            return List.of();
        }
    }
    public List<CountryInfo> getCountryInfoList() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String apiUrl = "https://restcountries.com/v3.1/all";
            HttpGet httpGet = new HttpGet(apiUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return extractCountryInfoList(responseBody);
            }
        } catch (Exception e) {
            handleException("Error fetching country info list", e);
            return List.of();
        }
    }

    private List<CountryInfo> extractCountryInfoList(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            List<CountryInfo> countryInfoList = new ArrayList<>();
            for (JsonNode countryNode : rootNode) {
                String countryCode = countryNode.get("cca2").asText();
                String phoneCode =  countryNode.get("iso").asText();
                String utcDiff = countryNode.get("iso").asText();
				CountryInfo countryInfo = new CountryInfo(countryCode, phoneCode, utcDiff);
                countryInfoList.add(countryInfo);
            }

            return countryInfoList;
        } catch (Exception e) {
            handleException("Error extracting country info list", e);
            return List.of();
        }
    }

}