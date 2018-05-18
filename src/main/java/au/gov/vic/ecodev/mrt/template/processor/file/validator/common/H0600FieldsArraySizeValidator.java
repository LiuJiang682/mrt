package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.StringArrayDataCopyer;

public class H0600FieldsArraySizeValidator {
	
	private static final String TITLE_H0602 = "H0602";
	private static final String TITLE_H0601 = "H0601";
	private static final String TITLE_H0600 = "H0600";
	private final String[] strs;
	private final Map<String, List<String>> templateParamMap;
	
	public H0600FieldsArraySizeValidator(final String[] strs, 
			final Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		if (null ==  templateParamMap) {
			throw new IllegalArgumentException("Parameter templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		new ThreeRowsFieldArraySizeValidator(TITLE_H0600, TITLE_H0601, TITLE_H0602)
			.validate(messages, 
					templateParamMap.get(Strings.TITLE_PREFIX 
							+ H0600Validator.SAMPLE_CODE_TITLE), 
					templateParamMap.get(Strings.TITLE_PREFIX 
							+ H0601Validator.SAMPLE_TYPE_TITLE), 
					new StringArrayDataCopyer().getList(strs));
	}
}
