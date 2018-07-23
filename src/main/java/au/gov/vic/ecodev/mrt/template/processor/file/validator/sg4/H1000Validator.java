package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.GeodeticDatum;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.H0702HeaderChecker;
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
			doH0501RelatedFieldsCheck(messages, templateParamMap, headers);
			new H0702HeaderChecker().doOptionalFieldHeaderCheck(messages, 
					templateParamMap, headers);
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			templateParamMap.put(Strings.COLUMN_HEADERS, headers);
			dataBean.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), 
					templateParamMap.get(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
			dataBean.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
					templateParamMap.get(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected final void doStandardHeaderCheck(List<String> messages, 
			Map<String, List<String>> templateParamMap, List<String> headers) {

		new SampleIdHeaderValidator().validate(messages, templateParamMap, headers);
		new SampleTypeHeaderValidator().validate(messages, templateParamMap, headers);
	}

	protected final void doH0501RelatedFieldsCheck(final List<String> messages,
			final Map<String, List<String>> templateParamMap, final List<String> headers) {
		List<String> h0501List = templateParamMap.get(H0501Validator.GEODETIC_DATUM_TITLE);
		if (CollectionUtils.isEmpty(h0501List)) {
			String message = "No H0501 Geodetic_datum data!";
			messages.add(message);
		} else {
			try {
				GeodeticDatum h0501 = GeodeticDatum.valueOf(h0501List.get(Numeral.ZERO));
				switch (h0501) {
				case GDA94:
					doGda94ColumnsCheck(messages, templateParamMap, headers);
					break;

				case AGD84:
					doAgd84ColumnsCheck(messages, headers);
				}
			} catch (IllegalArgumentException e) {
				String message = "H0501 Geodetic_datum must be either GDA94 or AGD84. But got: "
						+ h0501List.get(Numeral.ZERO);
				messages.add(message);
			}
		}
	}

	protected final void doAgd84ColumnsCheck(final List<String> messages, final List<String> headers) {
		if (!headers.stream().anyMatch(Sg4ColumnHeaders.EASTING_AMG.getCode()::equalsIgnoreCase)) {
			String message = "H1000 requires the Easting_AMG column";
			messages.add(message);
		}
		if (!headers.stream().anyMatch(Sg4ColumnHeaders.NORTHING_AMG.getCode()::equalsIgnoreCase)) {
			String message = "H1000 requires the Northing_AMG column";
			messages.add(message);
		}
	}

	protected final void doGda94ColumnsCheck(final List<String> messages,
			final Map<String, List<String>> templateParamMap, final List<String> headers) {
		if (!headers.stream().anyMatch(Sg4ColumnHeaders.EASTING_MGA.getCode()::equalsIgnoreCase)) {
			String message = "H1000 requires the Easting_MGA column";
			messages.add(message);
		}
		if (!headers.stream().anyMatch(Sg4ColumnHeaders.NORTHING_MGA.getCode()::equalsIgnoreCase)) {
			String message = "H1000 requires the Northing_MGA column";
			messages.add(message);
		}
	}

}
