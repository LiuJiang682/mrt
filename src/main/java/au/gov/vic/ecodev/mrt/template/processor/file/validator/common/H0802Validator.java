package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.param.helper.sl4.ParamHelper;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0802Validator implements Validator {

	public static final String ASSAY_DESCRIPTION_TITLE = "Assay_description";
	private static final String FIELD_NAME = "H0802";

	private String[] strs;

	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		new TemplateFieldBasicValidator(strs, ASSAY_DESCRIPTION_TITLE, FIELD_NAME)
				.validate(messages);
		new H0800FieldsArraySizeValidator(strs, templateParamMap).validate(messages);
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			new ParamHelper(strs, Strings.TITLE_PREFIX + ASSAY_DESCRIPTION_TITLE, templateParamMap)
				.addParamToMap();
		}
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
