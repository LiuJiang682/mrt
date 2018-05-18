package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.StringArrayDataCopyer;

public class H0700FieldsArraySizeValidator {

	private final String[] strs;
	private final Map<String, List<String>> templateParamMap;
	
	public H0700FieldsArraySizeValidator(String[] strs, Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		if (null ==  templateParamMap) {
			throw new IllegalArgumentException("Parameter templateParamMap cannot be null!");
		}
		this.templateParamMap = templateParamMap;
	}

	public void validate(List<String> messages) {
		List<String> samplePrepCodeList = templateParamMap.get(Strings.TITLE_PREFIX 
				+ H0700Validator.SAMPLE_PREP_CODE_TITLE);
		List<String> samplePrepDescList = new StringArrayDataCopyer().getList(strs);
		int samplePrepCodeListSize = (null == samplePrepCodeList) ? Numeral.ZERO : samplePrepCodeList.size();
		int samplePrepDescListSize = (null == samplePrepDescList) ? Numeral.ZERO : samplePrepDescList.size();
		if (samplePrepCodeListSize != samplePrepDescListSize) {
			String message = new StringBuilder("H0700 and H0701 have inconsistent list size: ")
					.append(samplePrepCodeListSize)
					.append(" and ")
					.append(samplePrepDescListSize)
					.toString();
			messages.add(message);
		}
		if (Numeral.ZERO == samplePrepCodeListSize) {
			messages.add("H0700 and H0701 all have zero entry");
		}
	}
}
