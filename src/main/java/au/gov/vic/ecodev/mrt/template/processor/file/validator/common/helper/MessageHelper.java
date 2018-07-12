package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class MessageHelper {

	private final long lineNumber;
	private final String code;
	
	public MessageHelper(final long lineNumber, final String code) {
		this.lineNumber = lineNumber;
		this.code = code;
	}
	
	public final void constructMissingHeaderMessages(List<String> messages) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" is missing header ")
				.append(code)
				.toString();
		messages.add(message);
	}
	
	public final void constructInvalidNumberDataMessage(List<String> messages, 
			String string) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("Line number ")
				.append(lineNumber)
				.append(" has invalid data: ")
				.append(string)
				.append(" for column: ")
				.append(code)
				.toString();
		messages.add(message);
	}
	
}
