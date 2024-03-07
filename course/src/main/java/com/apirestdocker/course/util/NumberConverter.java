package com.apirestdocker.course.util;

public class NumberConverter {

	public static double convertToDouble(String number) {
		return Double.parseDouble(number);
	}

	public static boolean isNumeric(String number) {
		if (number == null) {
            return false;
        }
        try {
            Long.parseLong(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}
	
}
