package au.gov.vic.ecodev.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class StringToDateConventor {

	private final String pattern;
	
	public StringToDateConventor(final String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			throw new IllegalArgumentException("Parameter pattern cannot be empty!");
		}
		this.pattern = pattern;
	}

	public Date parse(String dateString) throws ParseException {
		return (StringUtils.isEmpty(dateString)) ? null : new SimpleDateFormat(this.pattern).parse(dateString);
	}

}
