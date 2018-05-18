package au.gov.vic.ecodev.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringNumberFormatValidator {

	private final String string;
	
	public StringNumberFormatValidator(final String string) {
		this.string = string;
	}
	
	public boolean isValid() {
		if (StringUtils.isEmpty(string)) {
			return false;
		}
		if ((!NumberUtils.isParsable(string)) 
				&& (!NumberUtils.isCreatable(string))) {
			return false;
		}
		return true;
	}
}
