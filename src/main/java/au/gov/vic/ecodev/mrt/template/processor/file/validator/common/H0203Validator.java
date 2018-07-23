package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0203Validator implements Validator {

	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, 
			Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0203 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0203 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!StringUtils.isNumeric(strs[Numeral.TWO])) {
				String message = "H0203 must be number, but got " + strs[Numeral.TWO];
				messages.add(message);
			}
			if (!Strings.NUMBER_OF_DATA_RECORDS_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0203 title must be Number_of_data_records, but got " + strs[Numeral.ONE];
				messages.add(message);
			}
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			List<String> params = Arrays.asList(new String[] {strs[Numeral.TWO]});
			templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, params);
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
