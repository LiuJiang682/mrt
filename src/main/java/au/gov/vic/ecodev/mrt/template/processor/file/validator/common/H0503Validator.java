package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.Projection;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0503Validator implements Validator {

	public static final String PROJECTION_TITLE = "Projection";
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0503 requires minimum 2 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.TWO) {
			String message = "H0503 requires minimum 2 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!PROJECTION_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0503 title must be Projection, but got " + strs[Numeral.ONE];
				messages.add(message);
			} 
			if (Numeral.TWO < strs.length) {
				if (!isValidProjectionFieldValue(strs[Numeral.TWO])) {
					String message = "H0503 must be one of the following value: Non-projected, UTM, Lambert Conformable, Albers Equal Area, MGA, AGD or empty, but got " + strs[Numeral.TWO];
					messages.add(message);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			if (Numeral.TWO < strs.length) {
				List<String> params = Arrays.asList(new String[] {strs[Numeral.TWO]});
				templateParamMap.put(PROJECTION_TITLE, params);
			}
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected final boolean isValidProjectionFieldValue(String string) {
		boolean isValid = true;
		
		try {
			Projection.fromString(string);
		} catch (IllegalArgumentException e) {
			isValid = false;
		}
		return isValid;
	}

}
