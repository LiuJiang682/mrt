package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public abstract class MandatoryRowValidator implements Validator {

	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = getFieldName() + " requires minimum 2 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.TWO) {
			String message = getFieldName() + " requires minimum 2 columns, only got " + strs.length;
			messages.add(message);
		} else if (!getTitle().equalsIgnoreCase(strs[Numeral.ONE])) {
			String message = getFieldName() + " title must be " + getTitle() + ", but got " + strs[Numeral.ONE];
			messages.add(message);
		}
		
		if (messages.isEmpty()) {
			String[] values = Arrays.copyOfRange(strs, Numeral.ONE, strs.length);
			List<String> valueList = Arrays.asList(values);
			templateParamMap.put(getFieldName(), valueList);
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected abstract String getTitle();
	protected abstract String getFieldName();
}
