package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class NumberStringValidator {

	private final String data;
	private final String template;
	private final String columnName;
	private final int lineNumber;
	
	public NumberStringValidator(final String data, final String template, final String columnName,
			final int lineNumber) {
		this.data = data;
		if (null == template) {
			throw new IllegalArgumentException("NumberStringValidator:template cannot be null or empty!");
		}
		this.template = template;
		if (null == columnName) {
			throw new IllegalArgumentException("NumberStringValidator:columnName cannot be null or empty!");
		}
		this.columnName = columnName;
		this.lineNumber = lineNumber;
	}

	public void validate(List<String> messages) {
		if ((!NumberUtils.isParsable(data)) 
				&& (!NumberUtils.isCreatable(data))) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template ")
					.append(template)
					.append(Strings.SPACE)
					.append(columnName)
					.append(" column is expected as a number, but got: ")
					.append(data)
					.toString();
			messages.add(message);
		}
	}
}
