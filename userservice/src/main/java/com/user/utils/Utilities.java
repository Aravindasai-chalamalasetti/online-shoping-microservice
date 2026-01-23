package com.user.utils;

import com.user.dto.GenericDetailsDTO;
import com.user.model.GenericDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class Utilities {

    // Date format constants
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    // Phone validation pattern (10 digits with optional country code)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+[0-9]{1,3})?[0-9]{10}$");

    public static String base64Encode(final String code) {
        byte[] encodedBytes = Base64.getEncoder().encode(code.getBytes());
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String base64Decode(final String code) {
        byte[] decodedBytes = Base64.getDecoder().decode(code.getBytes());
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static String generateUuid(final String email) {
        return String.format("%s-%s", UUID.randomUUID(),
                StringUtils.lowerCase(Utilities.base64Encode(email)).replace("=", ""));
    }

    public static GenericDetailsDTO createGenericDetails(final String name) {
        GenericDetailsDTO builder = new GenericDetailsDTO.GenericDetailsDTOBuilder()
                .setCreatedBy(name)
                .setCreatedTime(getCurrentTime()).build();
        return builder;
    }

    public static GenericDetailsDTO modifyGenericDetails(final String name, GenericDetails genericDetails) {
        GenericDetailsDTO builder = new GenericDetailsDTO.GenericDetailsDTOBuilder()
                .setCreatedBy(genericDetails.getCreatedBy())
                .setCreatedTime(genericDetails.getCreatedTime())
                .setModifiedBy(name)
                .setModifiedTime(getCurrentTime()).build();
        return builder;
    }

    public static Timestamp getCurrentTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String encryptPassword(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static boolean checkIsJwtTokenValid(final List<String> authorizedToken) {
        if (Objects.nonNull(authorizedToken) && !authorizedToken.isEmpty()) {
            return JWTTokenProvider.isValid(authorizedToken.get(0));
        }
        return false;
    }

    // Overloaded method for single token
    public static boolean checkIsJwtTokenValid(final String token) {
        if (token != null && !token.trim().isEmpty()) {
            return JWTTokenProvider.isValid(token);
        }
        return false;
    }

    public static boolean checkPassword(final String password, final String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String spiltName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        String[] fullName = name.split(" ");
        return fullName.length > 0 ? fullName[0] : name;
    }

    // New methods for validation
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static String formatPhoneNumber(String phoneNumber, String countryCode) {
        if (phoneNumber == null) return null;

        // Remove any non-digit characters
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        // If phone number is exactly 10 digits, add country code
        if (phoneNumber.length() == 10 && countryCode != null && !countryCode.trim().isEmpty()) {
            // Remove + from country code if present
            String cleanCountryCode = countryCode.replace("+", "");
            return "+" + cleanCountryCode + phoneNumber;
        }

        return phoneNumber;
    }

    public static Date parseDate(String dateStr) throws ParseException {
        synchronized (DATE_FORMATTER) {
            return DATE_FORMATTER.parse(dateStr);
        }
    }

    public static String formatDate(Date date) {
        if (date == null) return null;
        synchronized (DATE_FORMATTER) {
            return DATE_FORMATTER.format(date);
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Extract Bearer token from Authorization header
    public static String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }
}
