package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.ProjectionZone;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.param.helper.sl4.ParamHelper;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0531Validator implements Validator {

	public static final String PROJECTION_ZONE_TITLE = "Projection_zone";
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0531 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0531 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!PROJECTION_ZONE_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0531 title must be Projection_zone, but got " + strs[Numeral.ONE];
				messages.add(message);
			} 
			if (!isValidProjectionZoneValue(strs[Numeral.TWO])) {
				String message = "H0531 must be either 54 or 55, but got " + strs[Numeral.TWO];
				messages.add(message);
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			new ParamHelper(strs, Strings.TITLE_PREFIX + PROJECTION_ZONE_TITLE, templateParamMap)
				.addParamToMap();
		}
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	private boolean isValidProjectionZoneValue(String string) {
		boolean isValid = true;
		
		try {
			ProjectionZone.fromString(string);
		} catch (IllegalArgumentException e) {
			isValid = false;
		}
		return isValid;
	}

}
