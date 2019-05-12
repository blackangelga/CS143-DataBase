/*
 * Copyright (C) 2016 Thomas Kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package CafeDansaDB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation.java
 * Validates the input of adding and editing Dancer objects.
 * RegEx Patterns were found on Google.
 * The necessary portions of the Dancer that needed validation
 * are the Dancer Name, Phone Number, and Email. All other fields
 * are given as a choice from a JComboBox and do not require
 * such validation.
 * <pre>
 *  Project: CafeDansa Database
 *  Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 *  Course: CS 143
 *  Created on Apr 12, 2016, 4:34:19 PM
 *  Revised on Arp 14, 2016, 2:27:35 PM
 * </pre>
 * @author Thomas Kercheval
 */
public class Validation {
    
    /** Regex expression for email validation. */
    public final static String EMAIL_PATTERN = 
            "^\\s*[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\\s*$";
    /** Regex expression for phone number validation. */
    public final static String PHONE_PATTERN = 
            "\\D{0,1}([0-9]\\d{2})(\\D*)([0-9]\\d{2})(\\D*)(\\d{4})";
    /** Regex expression for name validation. */
    public final static String NAME_PATTERN = "^\\s*([A-Z][a-z]+\\s*){2,3}$";
    
    /**
     * Check to see if input is a double type.
     * @param field The item we are validating as a double.
     * @return true if input is a double
     */
    public static boolean isDouble(String field) {
        Pattern pat = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher mat = pat.matcher(field);
        return mat.matches();
    }
    
    /**
     * Check to see if input is a valid phone number.
     * @param field The item we are validating as a phone number.
     * @return true if input is a valid phone number
     */
    public static boolean isPhone(String field) {
        String noLetters = "[^A-Za-z]+";
        return field.matches(PHONE_PATTERN) && field.matches(noLetters);
    }
    
    /**
     * Check to see if input is a valid Dancer Name.
     * @param field The item we are validating as a Dancer Name.
     * @return true if input is a valid Dancer Name.
     */
    public static boolean isName(String field) {
        return field.matches(NAME_PATTERN);
    }
    
    /**
     * Check to see if input is a valid Email Address.
     * @param field The item we are validating as a Email Address.
     * @return true if input is a valid Email Address.
     */
    public static boolean isEmail(String field) {
        return field.matches(EMAIL_PATTERN);
    }
}
