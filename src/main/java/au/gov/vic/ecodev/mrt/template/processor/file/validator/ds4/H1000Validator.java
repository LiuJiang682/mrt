package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

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
			String message = "H1000 requires minimum 5 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.FIVE) {
			String message = "H1000 requires minimum 5 columns, only got " + strs.length;
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
			dataBean.put(Ds4ColumnHeaders.HOLE_ID.getCode(), 
					templateParamMap.get(Ds4ColumnHeaders.HOLE_ID.getCode()));
			dataBean.put(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(), 
					templateParamMap.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode()));
			dataBean.put(Ds4ColumnHeaders.DIP.getCode(), 
					templateParamMap.get(Ds4ColumnHeaders.DIP.getCode()));
			List<String> azimuthMagList = templateParamMap.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
			if (!CollectionUtils.isEmpty(azimuthMagList)) {
				dataBean.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), 
					azimuthMagList);
			}
			List<String> azimuthTrueList = templateParamMap.get(Strings.AZIMUTH_TRUE);
			if (!CollectionUtils.isEmpty(azimuthTrueList)) {
				dataBean.put(Strings.AZIMUTH_TRUE, 
						azimuthTrueList);
			}
		}
	}

	protected final void doStandardHeaderCheck(List<String> messages, 
			Map<String, List<String>> templateParamMap,
			List<String> headers) {
		Optional<String> holeIdOptional =  headers.stream()
				.filter(header -> Ds4ColumnHeaders.HOLE_ID.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (holeIdOptional.isPresent()) {
			templateParamMap.put(Ds4ColumnHeaders.HOLE_ID.getCode(), 
					Arrays.asList(holeIdOptional.get()));
		} else {
			String message = "H1000 requires the Hole_id column";
			messages.add(message);
		}
		new SurveyedDepthValidator().validate(messages, templateParamMap, headers);
		new AzimuthMagAzimuthTrueValidator().validate(messages, templateParamMap, headers);
		Optional<String> dipOptional =  headers.stream()
				.filter(header -> Ds4ColumnHeaders.DIP.getCode().equalsIgnoreCase(header))
				.findFirst();
		if (dipOptional.isPresent()) { 
			templateParamMap.put(Ds4ColumnHeaders.DIP.getCode(), 
					Arrays.asList(dipOptional.get()));
		} else {
			String message = "H1000 requires the Dip column";
			messages.add(message);
		}
	}
}
