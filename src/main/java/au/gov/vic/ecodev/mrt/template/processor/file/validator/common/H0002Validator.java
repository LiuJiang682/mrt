package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0002Validator implements Validator {

	private static final String CURRENT_VERSION = "4";
	
	private String[] strs;

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, 
			Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0002 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0002 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else if (!CURRENT_VERSION.equals(strs[Numeral.TWO])) {
			String message = "H0002 requires version 4, only got " + strs[Numeral.TWO];
			messages.add(message);
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

}
