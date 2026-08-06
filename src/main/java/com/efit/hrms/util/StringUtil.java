package com.efit.hrms.util;

public class StringUtil {
	 public static String toTitleCase(String input) {
	        if (input == null || input.trim().isEmpty()) {
	            return input;
	        }

	        StringBuilder result = new StringBuilder();

	        for (String word : input.toLowerCase().trim().split("\\s+")) {
	            result.append(Character.toUpperCase(word.charAt(0)))
	                  .append(word.substring(1))
	                  .append(" ");
	        }

	        return result.toString().trim();
	    }
}

