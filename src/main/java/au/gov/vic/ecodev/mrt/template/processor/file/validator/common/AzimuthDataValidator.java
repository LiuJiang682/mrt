package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.common.util.StringListToIntegerConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class AzimuthDataValidator {

	private static final int THREE_HUNDRED_SIXTY = 360;
	
	private final String[] datas;
	private final int lineNumber;
	private final Map<String, List<String>> params;
	private int azimuthIndex;
	private final String columnName;
	
	public AzimuthDataValidator(String[] datas, int lineNumber, 
			Map<String, List<String>> params, int azimuthMagIndex,
			String columnName) {
		this.datas = datas;
		this.lineNumber = lineNumber;
		this.params = params;
		this.azimuthIndex = azimuthMagIndex;
		this.columnName = columnName;
	}

	public void validate(List<String> messages) {
		int scale;
		try {
			scale = new StringListToIntegerConventor(
					params.get(Strings.AZIMUTH_MAG_PRECISION))
					.parse();
		} catch (Exception e) {
			messages.add(constructPrecisionErrorMessage(lineNumber));
			return;
		}
		
		++azimuthIndex;
		String azimuthMagString = datas[azimuthIndex];
		try {
			BigDecimal azimuthMag = new BigDecimal(azimuthMagString).setScale(scale);
			BigDecimal zero = new BigDecimal(Numeral.ZERO).setScale(scale);
			BigDecimal threeHundredSixty = new BigDecimal(THREE_HUNDRED_SIXTY).setScale(scale);
			if ((Numeral.NEGATIVE_ONE == azimuthMag.compareTo(zero)) 
					|| (Numeral.NEGATIVE_ONE == threeHundredSixty.compareTo(azimuthMag))) {
				messages.add(constructAzimuthMagErrorMessage(lineNumber, azimuthMagString));
			}
		} catch (NumberFormatException e) {
			messages.add(constructAzimuthMagErrorMessage(lineNumber, azimuthMagString));
		} 
	}

	private String constructPrecisionErrorMessage(int lineNumber) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(":  Azimuth_Mag_Precision is NOT populated! Check the database table TEMPLATE_CONTEXT_PROPERTIES.")
				.toString();
		return message;
	}
	
	private String constructAzimuthMagErrorMessage(int lineNumber, String azimuthMagString) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(": ")
				.append(columnName)
				.append(" is expected as a number between 0 to 360, but got: ")
				.append(azimuthMagString)
				.toString();
		return message;
	}
}
