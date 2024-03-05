package com.turuchie.mellowhealthportal.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class ApiUtils {
	private static final String REST_COUNTRIES_API = "https://restcountries.com/v3.1/name/";
	
	// Standard Java Library
    public static String get(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method to GET
        connection.setRequestMethod("GET");

        // Read response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }   

    public List<String> getCountryPhoneCodes(String countryName) {
        try {
            String apiUrl = REST_COUNTRIES_API + countryName + "?fullText=true";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            // Assuming "maps" is an array within the JSON response
            JsonNode mapsArray = rootNode.get(0).get("maps");

            List<String> phoneCodes = new ArrayList<>();
            if (mapsArray.isArray()) {
                for (JsonNode phoneCodeNode : mapsArray) {
                    String phoneCode = phoneCodeNode.get("phone").asText();
                    phoneCodes.add(phoneCode);
                }
            }

            return phoneCodes;

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception as needed
            return List.of();
        }
    }
   
}
