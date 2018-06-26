package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.SingleWordColumnHeaderValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.H0702HeaderChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H1000Validator implements Validator {

	private static final String ROW_H1000 = "H1000";
	
	private String[] strs;

	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		List<String> headers = null;

		if (null == strs) {
			String message = Strings.LOG_ERROR_HEADER 
					+ "Template DG4 H1000 row requires minimum 5 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.FIVE) {
			String message = Strings.LOG_ERROR_HEADER 
					+ "Template DG4 H1000 row requires minimum 5 columns, only got " + strs.length;
			messages.add(message);
		} else {
			String[] headersArray = Arrays.copyOfRange(strs, Numeral.ONE, strs.length);
			headers = Arrays.asList(headersArray);
			doStandardHeaderCheck(messages, templateParamMap, headers);
			new H0702HeaderChecker().doOptionalFieldHeaderCheck(messages, 
					templateParamMap, headers);
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			templateParamMap.put(Strings.COLUMN_HEADERS, headers);
			addColumnHeadersToDataBean(templateParamMap, dataBean);
		} 
		return new ValidatorHelper(messages, hasErrorMessage).updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}


	private void addColumnHeadersToDataBean(Map<String, List<String>> templateParamMap, Template dataBean) {
		if (null != dataBean) {
			dataBean.put(Dg4ColumnHeaders.HOLE_ID.getCode(), 
					templateParamMap.get(Dg4ColumnHeaders.HOLE_ID.getCode()));
			dataBean.put(Dg4ColumnHeaders.FROM.getCode(), 
					templateParamMap.get(Dg4ColumnHeaders.FROM.getCode()));
			dataBean.put(Dg4ColumnHeaders.TO.getCode(), 
					templateParamMap.get(Dg4ColumnHeaders.TO.getCode()));
			dataBean.put(Dg4ColumnHeaders.SAMPLE_ID.getCode(), 
					templateParamMap.get(Dg4ColumnHeaders.SAMPLE_ID.getCode()));
		}
	}

	protected final void doStandardHeaderCheck(List<String> messages, 
			Map<String, List<String>> templateParamMap,
			List<String> headers) {
		new SingleWordColumnHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000, 
				Dg4ColumnHeaders.HOLE_ID.getCode())
			.validate(messages, templateParamMap, headers);
		new DepthFromHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.FROM.getCode())
			.validate(messages, templateParamMap, headers);
		new DepthToHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.TO.getCode())
			.validate(messages, templateParamMap, headers);
		new SampleIdHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(messages, templateParamMap, headers);
	}
}
