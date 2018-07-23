package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0202Validator implements Validator {

	private static final String DATA_FORMAT_TITLE = "Data_format";
	private static final String DATA_FORMAT_DG4 = "dg4";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = Strings.LOG_ERROR_HEADER 
					+ "H0202 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = Strings.LOG_ERROR_HEADER 
					+ "H0202 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!DATA_FORMAT_DG4.equalsIgnoreCase(strs[Numeral.TWO])) {
				String message = Strings.LOG_ERROR_HEADER 
						+ "H0202 must be DG4, but got " + strs[Numeral.TWO];
				messages.add(message);
			}
			if (!DATA_FORMAT_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = Strings.LOG_ERROR_HEADER 
						+ "H0202 title must be Data_format, but got " + strs[Numeral.ONE];
				messages.add(message);
			}
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

}
