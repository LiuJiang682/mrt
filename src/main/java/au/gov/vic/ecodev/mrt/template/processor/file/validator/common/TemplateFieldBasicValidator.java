package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateFieldBasicValidator {

	private final String[] strs;
	private final String title;
	private final String fieldName;

	public TemplateFieldBasicValidator(String[] strs, String title, String fieldName) {
		this.strs = strs;
		this.title = title;
		this.fieldName = fieldName;
	}

	public void validate(final List<String> messages) {
		if (null == strs) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append(fieldName)
					.append(" requires minimum 3 columns, only got 0")
					.toString();
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message =  new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append(fieldName)
					.append(" requires minimum 3 columns, only got ")
					.append(strs.length)
					.toString();
			messages.add(message);
		} else if (!title.equalsIgnoreCase(strs[Numeral.ONE])) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append(fieldName)
					.append(" title must be ")
					.append(title) 
					.append(", but got ")
					.append(strs[Numeral.ONE])
					.toString();
			messages.add(message);
		}
	}

}
