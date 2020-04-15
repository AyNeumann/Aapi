package com.example.Aapi.helper;

import org.apache.commons.text.WordUtils;

/**
 * Helper service to manipulate String.
 * Use org.apache.commons.text.WordUtils 
 * @see http://commons.apache.org/proper/commons-text/javadocs/api-release/index.html
 * @author Aymeric NEUMANN
 *
 */
public class StringFormatHelper {

	/**
	 * Capitalize first letter of the first word.
	 * @param toCapitalize String to capitalize
	 * @return capitalized String - first word only
	 */
	public static String capitalize(String toCapitalize) {
		return WordUtils.capitalize(toCapitalize);
	}
	
	/**
	 * Capitalize first letter of each word.
	 * @param toCapitalize String to capitalize
	 * @return capitalized String - each word
	 */
	public static String capitalizeFully(String toCapitalize) {
		return WordUtils.capitalizeFully(toCapitalize);
	}
}
