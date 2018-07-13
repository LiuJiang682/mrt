package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DataRecordNumberValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class Dg4DefaultValidator implements Validator {

	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
//		if (ArrayUtils.isNotEmpty(strs)) {
//			if ((Numeral.ONE == strs.length) 
//					&&(Strings.EOF.equalsIgnoreCase(strs[Numeral.ZERO]))) {
//				if (null == templateParamMap) {
//					String Message = "Parameter templateParamMap cannot be null!";
//					messages.add(Message);
//				} else {
//					List<String> expectedRecordsList = templateParamMap.get(Strings.NUMBER_OF_DATA_RECORDS_TITLE);
//					List<String> actualRecordsList = templateParamMap.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED);
//					new DataRecordNumberValidator(expectedRecordsList, 
//							actualRecordsList).validate(messages);
//				}
//			}
//		}
//		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
//		return new ValidatorHelper(messages, hasErrorMessage)
		return new ValidatorHelper(messages, false)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
