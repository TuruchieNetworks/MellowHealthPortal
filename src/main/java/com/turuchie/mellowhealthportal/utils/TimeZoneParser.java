package com.turuchie.mellowhealthportal.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.turuchie.mellowhealthportal.models.CountryInfo;

public class TimeZoneParser {

    public static CountryInfo parseTimeZoneString(String inputString) {
        CountryInfo countryInfo = new CountryInfo(inputString, inputString, inputString);

        // Split the input into lines
        String[] lines = inputString.split("\n");

        for (String line : lines) {
            // Trim the line
            String trimmedLine = line.trim();

            // Use a regex pattern to extract relevant information
            Pattern pattern = Pattern.compile("^(\\S+)\\s+(\\d+)(?:\\s*\\([^)]+\\))?\\s+([+-]?\\d{2}:\\d{2})");
            Matcher matcher = pattern.matcher(trimmedLine);

            // Check if the line matches the pattern
            if (matcher.matches()) {
                String country = matcher.group(1);
                String code = matcher.group(2);
                String utcDifference = matcher.group(3);

                if (utcDifference.startsWith("+") || utcDifference.startsWith("-")) {
                    // If the UTC difference starts with "+" or "-", set it as UTC difference
                    countryInfo.setUtcDiff(utcDifference);
                } else {
                    // Otherwise, assume it's a code and set it as such
                    countryInfo.setCountryCode(code);
                }

                countryInfo.setPhoneCode(country);

                // Assuming that one line provides enough information for one entry
                break;
            }
        }

        return countryInfo;
    }
}
