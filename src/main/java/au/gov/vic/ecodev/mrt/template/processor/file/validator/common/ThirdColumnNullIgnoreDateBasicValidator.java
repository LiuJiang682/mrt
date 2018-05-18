package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.common.util.StringToDateConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class ThirdColumnNullIgnoreDateBasicValidator {

	private final String[] strs;
	private final String dateFormatPattern;
	private final String fieldName;
	
	public ThirdColumnNullIgnoreDateBasicValidator(final String[] strs, final String dateFormatPattern, final String fieldName) {
		if (StringUtils.isEmpty(dateFormatPattern)) {
			throw new IllegalArgumentException("DateBasicValidator: Parameter dateFormatPattern cannot be null or empty!");
		}
		if (StringUtils.isEmpty(fieldName)) {
			throw new IllegalArgumentException("DateBasicValidator: Parameter fieldName cannot be null or empty!");
		}
		this.dateFormatPattern = dateFormatPattern;
		this.fieldName = fieldName;
		this.strs = strs;
	}

	public Date validate(final List<String> messages) {
		Date date = null;
		if ((null != strs) 
				&& (Numeral.THREE == strs.length)) {
			String content = strs[Numeral.TWO]; 
			try {
				date = new StringToDateConventor(dateFormatPattern)
						.parse(content);
				if (null == date) {
					generateErrorMessage(content, messages);
				}
			} catch (ParseException e) {
				generateErrorMessage(content, messages);
			}
		}
		return date;
	}
	
	private void generateErrorMessage(final String content, final List<String> messages) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append(fieldName)
				.append(" must be in dd-MMM-yy format, but got ") 
				.append(content)
				.toString();
		messages.add(message);
	}
}
