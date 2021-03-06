package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.AssayCodeValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0800Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.OptionalHeaderMandatoryFieldValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H1002Validator implements Validator {

	private static final String FIELD_NAME_H1002 = "H1002";
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		List<String> assayCodeList = templateParamMap.get(Strings.TITLE_PREFIX 
				+ H0800Validator.ASSAY_CODE_TITLE);
		
		if (CollectionUtils.isEmpty(assayCodeList)) {
			String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
					.append("No H0800 data provided")
					.toString();
			messages.add(message);
		} else {
			if (null != strs) {
				List<String> headers = templateParamMap.get(Strings.COLUMN_HEADERS);
				List<String> mandatoryFieldList = templateParamMap.get(Strings
						.TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER);
				new OptionalHeaderMandatoryFieldValidator(strs, headers, mandatoryFieldList, 
						FIELD_NAME_H1002).validate(messages);
				for (int index = Numeral.ONE; index < strs.length; index++) {
					String assayCode = strs[index];
					new AssayCodeValidator(assayCode, assayCodeList)
						.doAssayCodeLookUpValidation(messages);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
