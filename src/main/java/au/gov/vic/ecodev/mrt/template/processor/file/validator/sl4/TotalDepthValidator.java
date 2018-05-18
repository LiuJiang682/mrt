package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class TotalDepthValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	
	public TotalDepthValidator(String[] strs, int lineNumber, List<String> columnHeaders) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
	}

	public void validate(List<String> messages) {
		int elevationIndex = columnHeaders.indexOf(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode());
		if (Numeral.NOT_FOUND == elevationIndex) {
			messages.add(constructMissingElevationMessage(lineNumber));
			return;
		}
		
		++elevationIndex;
		String elevationString = strs[elevationIndex];
		if (!NumberUtils.isParsable(elevationString)) {
			String message = new StringBuilder("Line ")
					.append(lineNumber)
					.append(": Total Hole Depth is expected as a number, but got: ")
					.append(elevationString)
					.toString();
			messages.add(message);
		}
	}

	private String constructMissingElevationMessage(int lineNumber2) {
		String message = new StringBuilder("Line ")
				.append(lineNumber)
				.append(": Missing Total Hole Depth column")
				.toString();
		return message;
	}

}
