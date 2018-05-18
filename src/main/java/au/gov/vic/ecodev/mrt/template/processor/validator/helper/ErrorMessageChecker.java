package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class ErrorMessageChecker {

	private final List<String> messages;

	public ErrorMessageChecker(final List<String> messages) {
		if (null == messages) {
			throw new IllegalArgumentException("ErrorMessageChecker: Parameter messages cannot be null!");
		}
		this.messages = messages;
	}
	
	public final boolean isContainsErrorMessages() {
		boolean containsError = false;
		if (!messages.isEmpty()) {
			for(String message : messages) {
				if (message.startsWith(Strings.LOG_INFO_HEADER)) {
					continue;
				} else if (message.startsWith(Strings.LOG_WARNING_HEADER)) {
					continue;
				} else {
					//Either the header is ERROR or blank
					containsError = true;
					break;
				}
			}
		}
		return containsError;
	}
}
