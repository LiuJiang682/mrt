package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.ThirdColumnNullIgnoreDateBasicValidator;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.TemplateFieldBasicValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0200Validator implements Validator {

	public static final String START_DATE_OF_DATA_ACQUISITION_TITLE = "Start_date_of_data_acquisition";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {	
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		new TemplateFieldBasicValidator(strs, START_DATE_OF_DATA_ACQUISITION_TITLE, Strings.KEY_H0200)
			.validate(messages);
		new ThirdColumnNullIgnoreDateBasicValidator(strs, Strings.DATE_FORMAT_DD_MMM_YY, Strings.KEY_H0200)
			.validate(messages);
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			templateParamMap.put(Strings.KEY_H0200, Arrays.asList(strs[Numeral.TWO]));
		}
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
