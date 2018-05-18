package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class MandatoryRowChecker {

	private final List<String> values;
	private final String columnName;
	
	public MandatoryRowChecker(final List<String> values, final String columnName) {
		this.values = values;
		this.columnName = columnName;
	}
	
	public void validate(final List<String> messages) {
		if (CollectionUtils.isEmpty(values)) {
			String message = new StringBuilder("No ")
					.append(columnName)
					.append(" in the templateParamMap!")
					.toString();
			messages.add(message);
		}
	}
}
