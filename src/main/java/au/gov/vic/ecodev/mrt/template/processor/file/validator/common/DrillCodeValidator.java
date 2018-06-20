package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class DrillCodeValidator {

	private final String[] strs;
	private final int lineNumber;
	private final List<String> columnHeaders;
	private final Map<String, List<String>> templateParamMap;
	private final String code;
	
	public DrillCodeValidator(final String[] strs, 
			int lineNumber, final List<String> columnHeaders, 
			final Map<String, List<String>> templateParamMap, 
			final String code) {
		this.strs = strs;
		this.lineNumber = lineNumber;
		this.columnHeaders = columnHeaders;
		this.templateParamMap = templateParamMap;
		this.code = code;
	}

	public void validate(List<String> messages) {
		int drillCodeIndex = columnHeaders.indexOf(code);
		if (Numeral.NOT_FOUND == drillCodeIndex) {
			List<String> drillCodeVariation = templateParamMap.get(code);
			if (CollectionUtils.isEmpty(drillCodeVariation)) {
				String message = new StringBuilder("Line ")
						.append(lineNumber)
						.append("'s Drill_Code variation column does NOT exist in H0400!")
						.toString();
				messages.add(message);
				return;
			} else {
				drillCodeIndex = columnHeaders.indexOf(drillCodeVariation.get(
						au.gov.vic.ecodev.mrt.constants.Constants.Numeral.ZERO));
			}
		}
		++drillCodeIndex;
		List<String> existDrillCodes = templateParamMap
				.get(Strings.TITLE_PREFIX + H0400Validator.DRILL_CODE_TITLE);
		if (CollectionUtils.isEmpty(existDrillCodes)) {
			String message = new StringBuilder("Line ")
					.append(lineNumber)
					.append(": No Drill_Code exist at H0400!")
					.toString();
			messages.add(message);
		} else if(!existDrillCodes.contains(strs[drillCodeIndex])) {
			String message = new StringBuilder("Line ")
					.append(lineNumber)
					.append("'s Drill_Code column must exist in H0400! But got: ")
					.append(strs[drillCodeIndex])
					.toString();
			messages.add(message);
		}
		
	}

}
