package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class MandatoryStringDataValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final String code;
	private final String templateName;
	
	public MandatoryStringDataValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			final String code, final String templateName) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		this.code = code;
		this.templateName = templateName;
	}

	public void validate(List<String> messages) {
		int index = columnHeaders.indexOf(code);
		if (Numeral.NOT_FOUND == index) {
			messages.add(constructMissingHoleIdMessage(lineNumber));
			return;
		}
	
		++index;
		String string = strs[index];
		if (StringUtils.isEmpty(string)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("Line ")
					.append(lineNumber)
					.append(": Template ")
					.append(templateName)
					.append(" column ")
					.append(code)
					.append(" cannot be null or empty")
					.toString();
			messages.add(message);
		}
	}

	private String constructMissingHoleIdMessage(int currentLineNumber) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line ")
				.append(currentLineNumber)
				.append(": Template ")
				.append(templateName)
				.append(" missing ")
				.append(code)
				.append(" column")
				.toString();
		return message;
	}
}
