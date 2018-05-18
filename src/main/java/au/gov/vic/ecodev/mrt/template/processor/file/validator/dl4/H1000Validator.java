package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ErrorMessageChecker;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;

public class H1000Validator implements Validator {

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
			String message = "Template Dl4 H1000 row requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "Template Dl4 H1000 row requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			String[] headersArray = Arrays.copyOfRange(strs, Numeral.ONE, strs.length);
			headers = Arrays.asList(headersArray);
			doStandardHeaderCheck(messages, templateParamMap, headers);
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
			dataBean.put(Dl4ColumnHeaders.HOLE_ID.getCode(), 
					templateParamMap.get(Dl4ColumnHeaders.HOLE_ID.getCode()));
			dataBean.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
					templateParamMap.get(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		}
	}

	protected final void doStandardHeaderCheck(List<String> messages, 
			Map<String, List<String>> templateParamMap,
			List<String> headers) {
		Optional<String> holeIdOptional =  headers.stream()
				.filter(header -> Dl4ColumnHeaders.HOLE_ID.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (holeIdOptional.isPresent()) {
			templateParamMap.put(Dl4ColumnHeaders.HOLE_ID.getCode(), 
					Arrays.asList(holeIdOptional.get()));
		} else {
			String message = "Template Dl4 H1000 row requires the Hole_id column";
			messages.add(message);
		}
		new DepthFromValidator().validate(messages, templateParamMap, headers);

	}
}
