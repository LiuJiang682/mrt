package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.param.helper.sl4.ParamHelper;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0702Validator implements Validator {

	public static final String JOB_NO_TITLE = "Job_no";
	private static final String FIELD_NAME = "H0702";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		new TemplateFieldBasicValidator(strs, JOB_NO_TITLE, FIELD_NAME)
			.validate(messages);
		if ((null != strs) 
				&& (Numeral.THREE < strs.length)) {
			String message =  new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append(FIELD_NAME)
					.append(" requires 3 columns, but got ")
					.append(strs.length)
					.toString();
			messages.add(message);
		}
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if ((!hasErrorMessage) 
				&& (Strings.JOB_NO_MULTIPLY.equalsIgnoreCase(strs[Numeral.TWO]))) {
			new ParamHelper(strs, Strings.TITLE_PREFIX + JOB_NO_TITLE, templateParamMap)
				.addParamToMap();
		}
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
