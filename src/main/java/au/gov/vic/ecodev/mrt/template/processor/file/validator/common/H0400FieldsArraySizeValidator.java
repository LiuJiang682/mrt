package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.StringArrayDataCopyer;

public class H0400FieldsArraySizeValidator {
	
	private static final String TITLE_H0400 = "H0400";
	private static final String TITLE_H0401 = "H0401";
	private static final String TITLE_H0402 = "H0402";
	private final String[] strs;
	private final Map<String, List<String>> templateParamMap;
	
	public H0400FieldsArraySizeValidator(final String[] strs, 
			final Map<String, List<String>> templateParamMap) {
		if (null ==  templateParamMap) {
			throw new IllegalArgumentException("Parameter templateParamMap cannot be null!");
		}
		this.strs = strs;
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		new ThreeRowsFieldArraySizeValidator(TITLE_H0400, TITLE_H0401, TITLE_H0402)
			.validate(messages, 
				templateParamMap.get(Strings.TITLE_PREFIX 
						+ H0400Validator.DRILL_CODE_TITLE), 
				templateParamMap.get(Strings.TITLE_PREFIX 
						+ H0401Validator.DRILL_CONTRACTOR_TITLE), 
				new StringArrayDataCopyer().getList(strs));
	}
}
