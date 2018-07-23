package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0005Validator implements Validator {

	private static final String TITLE_STATE = "state";
	private static final String STATE_VIC = "VIC";
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
			String message = "H0005 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0005 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!STATE_VIC.equalsIgnoreCase(strs[Numeral.TWO])) {
				String message = "H0005 must be Vic, but got " + strs[Numeral.TWO];
				messages.add(message);
			}
			if (!TITLE_STATE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0005 title must be State, but got " + strs[Numeral.ONE];
				messages.add(message);
			}
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
