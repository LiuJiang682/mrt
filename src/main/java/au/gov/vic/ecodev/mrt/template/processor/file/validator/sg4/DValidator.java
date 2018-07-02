package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.EastingValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.LineNumberValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.ListSizeValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.NorthingValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.RecordCounter;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class DValidator implements Validator {

	private String[] strs;
	private TemplateProcessorContext context;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}
	
	public void setTemplateProcessorContext(final TemplateProcessorContext context) {
		this.context = context;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		int lineNumber = new LineNumberValidator(templateParamMap.get(Strings.CURRENT_LINE))
				.validate(messages);
		if (Numeral.INVALID_LINE_NUMBER != lineNumber) {
			if (null == strs) {
				String message = new StringBuilder("Line ")
						.append(lineNumber)
						.append(" requires minimum 5 columns, only got 0")
						.toString();
				messages.add(message);
			} else if (strs.length < Numeral.FIVE) {
				String message = new StringBuilder("Line ")
						.append(lineNumber)
						.append(" requires minimum 5 columns, only got ") 
						.append(strs.length)
						.toString();
				messages.add(message);
			} else {
				List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
				int columnCount = new ListSizeValidator(columnHeaders).validate(messages);
				if (Numeral.INVALID_COLUMN_COUNT != columnCount) {
					new SampleIdDataValidator(strs, lineNumber, columnHeaders, templateParamMap)
						.validate(messages);
					new EastingValidator(strs, lineNumber, columnHeaders, 
							Sg4ColumnHeaders.EASTING_MGA.getCode(),
							Sg4ColumnHeaders.EASTING_AMG.getCode()).validate(messages);
					new NorthingValidator(strs, lineNumber, columnHeaders,
							Sg4ColumnHeaders.NORTHING_MGA.getCode(),
							Sg4ColumnHeaders.NORTHING_AMG.getCode()).validate(messages);
					new SampleTypeDataValidator(strs, lineNumber, columnHeaders, templateParamMap)
						.validate(messages);
					new SamplePositionValidator(strs, lineNumber, templateParamMap, context)
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
