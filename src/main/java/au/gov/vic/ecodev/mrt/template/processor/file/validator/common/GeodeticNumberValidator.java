package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.regex.Pattern;

public class GeodeticNumberValidator {

	private static final Pattern INTEGER_PATTEN = Pattern.compile("^[+-]?\\d+$");
	
	private static final Pattern SIX_DIGITS_PATTEN = Pattern.compile("^([0-9]{6,})\\.([0-9]*)$");
	
	public final boolean isValidGeodeticNumber(String string) {
		if (INTEGER_PATTEN.matcher(string).matches()) {
			return true;
		} else if (SIX_DIGITS_PATTEN.matcher(string).matches()) {
			return true;
		}
		
		return false;
	}
	
}
