package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;

public class ThreeRowsFieldArraySizeValidator {

	private static final String COMMA_SPACE = ", ";
	private static final String SPACE_AND_SPACE = " and ";
	private final String row1Name;
	private final String row2Name;
	private final String row3Name;
	
	public ThreeRowsFieldArraySizeValidator(final String row1Name, final String row2Name, 
			final String row3Name) {
		if (StringUtils.isEmpty(row1Name)) {
			throw new IllegalArgumentException("ThreeRowsFieldArraySizeValidator:row1Name parameter cannot be null or empty!");
		}
		this.row1Name = row1Name;
		if (StringUtils.isEmpty(row2Name)) {
			throw new IllegalArgumentException("ThreeRowsFieldArraySizeValidator:row2Name parameter cannot be null or empty!");
		}
		this.row2Name = row2Name;
		if (StringUtils.isEmpty(row3Name)) {
			throw new IllegalArgumentException("ThreeRowsFieldArraySizeValidator:row3Name parameter cannot be null or empty!");
		}
		this.row3Name = row3Name;
	}

	public void validate(List<String> messages, List<String> row1List,
			List<String> row2List, List<String> row3List) {
		int row1ListSize = (null == row1List) ? Numeral.ZERO : row1List.size();
		int row2ListSize = (null == row2List) ? Numeral.ZERO : row2List.size();
		int row3ListSize = (null == row3List) ? Numeral.ZERO : row3List.size();
		
		if ((row1ListSize != row2ListSize) 
				|| (row1ListSize != row3ListSize)) {
			String message = new StringBuilder(row1Name)
					.append(COMMA_SPACE)
					.append(row2Name)
					.append(SPACE_AND_SPACE)
					.append(row3Name)
					.append(" have inconsistent list size: ")
					.append(row1ListSize)
					.append(COMMA_SPACE)
					.append(row2ListSize)
					.append(SPACE_AND_SPACE)
					.append(row3ListSize)
					.toString();
			messages.add(message);
		}
		if (Numeral.ZERO == row1ListSize) {
			String message = new StringBuilder(row1Name)
					.append(COMMA_SPACE)
					.append(row2Name)
					.append(SPACE_AND_SPACE)
					.append(row3Name)
					.append(" all have zero entry")
					.toString();
			messages.add(message);
		}
	}
}
