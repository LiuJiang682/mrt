package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.common.util.StringListToIntegerConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class DipValidator {

	private static final String UPPER_BOUNDRY_NINETY = "90";
	private static final String LOW_BOUNDRY_NEGATIVE_NINETY = "-90";
	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final Map<String, List<String>> templateParamMap;
	
	public DipValidator(String[] strs, int lineNumber, List<String> columnHeaders,
			Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> errors) {
		int dipIndex = columnHeaders.indexOf(SL4ColumnHeaders.DIP.getCode());
		if (Numeral.NOT_FOUND == dipIndex) {
			errors.add(constructMissingDipMessage(lineNumber));
			return;
		}
		
		int scale;
		try {
			scale = new StringListToIntegerConventor(templateParamMap.get(Strings.DIP_PRECISION))
					.parse();
		} catch (Exception e) {
			errors.add(constructPrecisionErrorMessage(lineNumber));
			return;
		}
		
		++dipIndex;
		String dipString = strs[dipIndex];
		try {
			BigDecimal dip = new BigDecimal(dipString).setScale(scale);
			BigDecimal negativeNinety = new BigDecimal(LOW_BOUNDRY_NEGATIVE_NINETY).setScale(scale);
			BigDecimal ninety = new BigDecimal(UPPER_BOUNDRY_NINETY).setScale(scale);
			if ((au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE == dip.compareTo(negativeNinety))
					||(au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE == ninety.compareTo(dip))) {
				errors.add(constructDipErrorMessage(lineNumber, dipString));
			}
		} catch (NumberFormatException e) {
			errors.add(constructDipErrorMessage(lineNumber, dipString));
		} 
	}

	private String constructDipErrorMessage(int lineNumber, String dipString) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(": Dip is expected as a number between -90 to 90, but got: ")
				.append(dipString)
				.toString();
		return message;
	}

	private String constructPrecisionErrorMessage(int lineNumber) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(":  Dip_Precision is NOT populated! Check the database table TEMPLATE_CONTEXT_PROPERTIES.")
				.toString();
		return message;
	}
	
	private String constructMissingDipMessage(int lineNumber) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(":  Missing Dip column")
				.toString();
		return message;
	}

}
