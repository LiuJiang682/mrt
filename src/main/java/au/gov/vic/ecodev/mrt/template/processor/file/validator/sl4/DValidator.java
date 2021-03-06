package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.DrillCodeValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.EastingValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.LineNumberValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.MandatoryStringDataValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.NorthingValidator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper.H0100FieldHelper;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.IssueColumnIndexHelper;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.RecordCounter;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.common.ListSizeValidator;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

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
	
	public void setZipFileName(final String zipFileName) {
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		int lineNumber = new LineNumberValidator(templateParamMap.get(Strings.CURRENT_LINE)).validate(messages);
		if (Numeral.INVALID_LINE_NUMBER != lineNumber) {
			if (null == strs) {
				String message = "Line " + lineNumber + " requires minimum 7 columns, only got 0";
				messages.add(message);
			} else if (strs.length < Numeral.SEVEN) {
				String message = "Line " + lineNumber + " requires minimum 7 columns, only got " + strs.length;
				messages.add(message);
			} else {
				List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
				int columnCount = new ListSizeValidator(columnHeaders).validate(messages);
				if (Numeral.INVALID_COLUMN_COUNT != columnCount) {
					new MandatoryStringDataValidator(strs, lineNumber, columnHeaders,
							SL4ColumnHeaders.HOLE_ID.getCode(), Strings.TEMPLATE_NAME_SL4).validate(messages);
					new EastingValidator(strs, lineNumber, columnHeaders,
							SL4ColumnHeaders.EASTING_MGA.getCode(), 
							SL4ColumnHeaders.EASTING_AMG.getCode()).validate(messages);
					new NorthingValidator(strs, lineNumber, columnHeaders, 
							SL4ColumnHeaders.NORTHING_MGA.getCode(),
							SL4ColumnHeaders.NORTHING_AMG.getCode()).validate(messages);
					new DrillCodeValidator(strs, lineNumber, columnHeaders, templateParamMap, 
							SL4ColumnHeaders.DRILL_CODE.getCode()).validate(messages);
					new AzimuthMagValidator(strs, lineNumber,
							columnHeaders, templateParamMap).validate(messages);
					new DipValidator(strs, lineNumber, columnHeaders, templateParamMap).validate(messages);
					new ElevationValidator(strs, lineNumber, columnHeaders).validate(messages);
					new TotalDepthValidator(strs, lineNumber, columnHeaders).validate(messages);
					doH0100FieldValidation(strs, templateParamMap, dataBean, messages, lineNumber);
				}
			}
		}
		
		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			String count = new RecordCounter().incrementAndGet(templateParamMap);
			strs[Numeral.ZERO] += count;
		} 
		int issueColumnIndex = new IssueColumnIndexHelper().
				getIssueColumnIndex(templateParamMap.remove(Strings.ISSUE_COLUMN_INDEX));
		return new ValidatorHelper(messages, hasErrorMessage, issueColumnIndex)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected void doH0100FieldValidation(String[] strs, 
			Map<String, List<String>> templateParamMap, Template dataBean,
			List<String> messages, int lineNumber) {
		if (null != dataBean) {
			List<String> h0100List = templateParamMap.get(Strings.KEY_H0100);
			if (CollectionUtils.isEmpty(h0100List)) {
				new H0100FieldHelper().doTenementNoSplit(dataBean, templateParamMap);
			}
			new H0100FieldLookupValidator(strs, lineNumber, templateParamMap)
				.validate(messages);
			new BoreHolePositionValidator(strs, lineNumber, templateParamMap, context)
				.validate(messages);
		}
		
		
	}
}
