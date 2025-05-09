package com.flixify.backend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataIntegrityViolationExceptionParser {

    private static String parseDuplicateKeyViolationMessage(String rawMessage) {
        
        // Regex to extract the field and value from the raw DB message
        Pattern pattern = Pattern.compile("Key \\((.+?)\\)=\\((.+?)\\)");
        Matcher matcher = pattern.matcher(rawMessage);

        if (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            return String.format("A record with %s = '%s' already exists.", field, value);
        }

        return "A record with duplicate value already exists.";
    }

    public static String parse(Exception e) {
        String message = e.getMessage();
        if (message != null) {
            if (message.contains("duplicate key value violates unique constraint")) {
                return parseDuplicateKeyViolationMessage(message);
            }
        }
        return message;
    }
}
