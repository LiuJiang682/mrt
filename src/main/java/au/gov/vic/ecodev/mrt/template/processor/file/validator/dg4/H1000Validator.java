package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.DrillCodeHeaderValidator;
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
					+ "Template DG4 H1000 row requires minimum 6 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.SIX) {
			String message = Strings.LOG_ERROR_HEADER 
					+ "Template DG4 H1000 row requires minimum 6 columns, only got " + strs.length;
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

//	protected final void doOptionalFieldHeaderCheck(List<String> messages, Map<String, List<String>> templateParamMap,
//			List<String> headers) {
//		List<String> h0702List = templateParamMap.get(Strings.TITLE_PREFIX 
//				+ H0702Validator.JOB_NO_TITLE);
//		if (!CollectionUtils.isEmpty(h0702List)) {
//			String h0702Value = h0702List.get(Numeral.ZERO);
//			if (Strings.JOB_NO_MULTIPLY.equalsIgnoreCase(h0702Value)) {
//				if (!matchesAny(headers, HEADER_JOB)) {
//					if (!matchesAny(headers, HEADER_BATCH)) {
//						String message = new StringBuilder(Strings.LOG_ERROR_HEADER)
//								.append("Missing Job_no/batch_no header when H0702 value is Multiply")
//								.toString();
//						messages.add(message);
//					}
//				}
//			}
//		}
//		
//	}
//
//	protected final boolean matchesAny(List<String> headers, String string) {
//		Optional<String> matchedStringOptional = headers.stream()
//				.filter(header -> StringUtils.containsIgnoreCase(header, string))
//				.findFirst();
//		if (matchedStringOptional.isPresent()) {
//			return true;
//		} else {
//			return false;
//		}
//	}

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
			dataBean.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
					templateParamMap.get(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		}
	}

	protected final void doStandardHeaderCheck(List<String> messages, 
			Map<String, List<String>> templateParamMap,
			List<String> headers) {
		new SingleWordColumnHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000, 
				Dg4ColumnHeaders.HOLE_ID.getCode())
			.validate(messages, templateParamMap, headers);
		new SingleWordColumnHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.FROM.getCode())
			.validate(messages, templateParamMap, headers);
		new SingleWordColumnHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.TO.getCode())
			.validate(messages, templateParamMap, headers);
		new SingleWordColumnHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(messages, templateParamMap, headers);
		new DrillCodeHeaderValidator(Strings.TEMPLATE_NAME_DG4, ROW_H1000,
				Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(messages, templateParamMap, headers);
	}
}
