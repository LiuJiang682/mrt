package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.LineNumberValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DipValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.RecordCounter;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.common.ListSizeValidator;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class DValidator implements Validator {

	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		int lineNumber = new LineNumberValidator(templateParamMap.get(Strings.CURRENT_LINE)).validate(messages);
		
		if (Numeral.INVALID_LINE_NUMBER != lineNumber) {
			if (null == strs) {
				String message = "Line " + lineNumber + " requires minimum 4 columns, only got 0";
				messages.add(message);
			} else if (strs.length < Numeral.FOUR) {
				String message = "Line " + lineNumber + " requires minimum 4 columns, only got " + strs.length;
				messages.add(message);
			} else {
				List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
				int columnHeaderCount = new ListSizeValidator(columnHeaders).validate(messages);
				if (Numeral.INVALID_COLUMN_COUNT != columnHeaderCount) {
					new AzimuthValidator(strs, lineNumber, 
							columnHeaders, templateParamMap).validate(messages);
					new DipValidator(strs, lineNumber, columnHeaders, templateParamMap)
						.validate(messages);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			String count = new RecordCounter().incrementAndGet(templateParamMap);
			strs[Numeral.ZERO] += count;
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
