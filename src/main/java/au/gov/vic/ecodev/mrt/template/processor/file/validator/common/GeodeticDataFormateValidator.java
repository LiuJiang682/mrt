package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

public class GeodeticDataFormateValidator {
	
	private final String[] strs;
	private final int lineNumber;
	private final String columnName;
	private final int index;
	
	public GeodeticDataFormateValidator(final String[] strs, final int lineNumber,
			final String columnName, int index) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnName = columnName;
		this.index = index;
	}

	public void validate(List<String> errorMessages) {
		int position = index;
		++position;
		if (!new GeodeticNumberValidator().isValidGeodeticNumber(strs[position])) {
			String message = new StringBuilder("Line ")
					.append(lineNumber)
					.append("'s ")
					.append(columnName)
					.append(" column must be either integer or has 6 digits before the decimal point! But got: " 
							+ strs[position])
					.toString();
			errorMessages.add(message);
		} 
	}
	
	
	
}
