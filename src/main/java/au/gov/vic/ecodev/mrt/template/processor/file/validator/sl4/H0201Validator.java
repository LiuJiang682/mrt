package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.StringToDateConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.TemplateFieldBasicValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.ThirdColumnNullIgnoreDateBasicValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H0201Validator implements Validator {

	private static final String END_DATE_OF_DATA_ACQUISITION_TITLE = "End_date_of_data_acquisition";
	private static final String KEY_H0201 = "H0201";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		new TemplateFieldBasicValidator(strs, END_DATE_OF_DATA_ACQUISITION_TITLE, KEY_H0201)
			.validate(messages);
		Date endDate = new ThirdColumnNullIgnoreDateBasicValidator(strs, 
				Strings.DATE_FORMAT_DD_MMM_YY, KEY_H0201).validate(messages);
		if (null != endDate) {
			List<String> startDateList = templateParamMap.get(Strings.KEY_H0200);
			if (!CollectionUtils.isEmpty(startDateList)) {
				String startDateString = startDateList.get(Numeral.ZERO);
				try {
					Date startDate = new StringToDateConventor(Strings.DATE_FORMAT_DD_MMM_YY)
							.parse(startDateString);
					if (startDate.after(endDate)) {
						handleEndDateBeforeStartDateError(messages, startDateString);
					}
				} catch (ParseException e) {
					handleParseException(messages, startDateString, e);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	private void handleEndDateBeforeStartDateError(List<String> messages, String startDateString) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append(KEY_H0201)
				.append(" got value: ")
				.append(strs[Numeral.TWO])
				.append(" is earlier than H0200 value: ")
				.append(startDateString)
				.toString();
		messages.add(message);
	}

	private void handleParseException(List<String> messages, 
			String startDateString, ParseException e) {
		String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
				.append("H0200 got value: ")
				.append(startDateString)
				.append(" is unparsable: ")
				.append(e.getMessage())
				.toString();
		messages.add(message);
	}

}
