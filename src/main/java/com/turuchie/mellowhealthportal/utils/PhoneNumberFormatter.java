//package com.turuchie.mellowhealthportal.utils;
//
//import org.springframework.stereotype.Component;
//
//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//@Component
//public class PhoneNumberFormatter {
//
//	public static String formatPhoneNumber(String phoneNumber, String countryCode) {
//	    if (phoneNumber == null || countryCode == null) {
//	        // Handle null parameters
//	        return phoneNumber; // or throw an exception
//	    }
//
//	    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//
//	    try {
//	        // Parse the input phone number
//	        com.google.i18n.phonenumbers.Phonenumber.PhoneNumber parsedPhoneNumber =
//	                phoneNumberUtil.parse(phoneNumber, countryCode);
//
//	        // Format the phone number
//	        return phoneNumberUtil.format(parsedPhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//	    } catch (NumberParseException e) {
//	        // Handle parsing exception
//	        e.printStackTrace();
//	        return phoneNumber; // Return original phone number if parsing fails
//	    }
//	}
//
//
//    public static void main(String[] args) {
//        // Example usage
//        String formattedNumber = formatPhoneNumber("4044044040", "US");
//        System.out.println(formattedNumber); // Output: +1 404-404-4040
//    }
//}
