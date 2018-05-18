package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.OptionalHeaderMandatoryFieldValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H1003Validator implements Validator {

	private static final String FIELD_NAME_H1003 = "H1003";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null != strs) {
			List<String> headers = templateParamMap.get(Strings.COLUMN_HEADERS);
			List<String> mandatoryFieldList = templateParamMap.get(Strings.TEMPLATE_PROP_DG4_H1001_MANDATORY_FIELDS_HEADER);
			new OptionalHeaderMandatoryFieldValidator(strs, headers, mandatoryFieldList, 
					FIELD_NAME_H1003).validate(messages);
		}
		return new ValidatorHelper(messages, false)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
