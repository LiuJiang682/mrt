package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.StringArrayDataCopyer;

public class H0800FieldsArraySizeValidator {
	
	private static final String TITLE_H0802 = "H0802";
	private static final String TITLE_H0801 = "H0801";
	private static final String TITLE_H0800 = "H0800";
	private final String[] strs;
	private final Map<String, List<String>> templateParamMap;
	
	public H0800FieldsArraySizeValidator(final String[] strs, 
			final Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		if (null ==  templateParamMap) {
			throw new IllegalArgumentException("Parameter templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		new ThreeRowsFieldArraySizeValidator(TITLE_H0800, TITLE_H0801, TITLE_H0802)
			.validate(messages, 
					templateParamMap.get(Strings.TITLE_PREFIX 
							+ H0800Validator.ASSAY_CODE_TITLE), 
					templateParamMap.get(Strings.TITLE_PREFIX 
							+ H0801Validator.ASSAY_COMPANY_TITLE), 
					new StringArrayDataCopyer().getList(strs));
	}
}
