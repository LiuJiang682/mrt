package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.VerticalDatum;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.validator.Validator;
import au.gov.vic.ecodev.mrt.template.processor.validator.helper.ValidatorHelper;
import au.gov.vic.ecodev.utils.validator.helper.ErrorMessageChecker;

public class H0502Validator implements Validator {

	public static final String VERTICAL_DATUM_TITLE = "Vertical_datum";
	
	private String[] strs;
	
	@Override
	public void init(String[] strs) {
		this.strs = strs;
	}

	@Override
	public Optional<List<String>> validate(Map<String, List<String>> templateParamMap, Template dataBean) {
		List<String> messages = new ArrayList<>();
		if (null == strs) {
			String message = "H0502 requires minimum 3 columns, only got 0";
			messages.add(message);
		} else if (strs.length < Numeral.THREE) {
			String message = "H0502 requires minimum 3 columns, only got " + strs.length;
			messages.add(message);
		} else {
			if (!isValidVerticalDatumValue(strs[Numeral.TWO])) {
				String message = "H0502 must be either Nominal or AHD, but got " + strs[Numeral.TWO];
				messages.add(message);
			}
			if (!VERTICAL_DATUM_TITLE.equalsIgnoreCase(strs[Numeral.ONE])) {
				String message = "H0502 title must be Vertical_datum, but got " + strs[Numeral.ONE];
				messages.add(message);
			}
		}

		boolean hasErrorMessage = new ErrorMessageChecker(messages).isContainsErrorMessages();
		if (!hasErrorMessage) {
			List<String> params = Arrays.asList(new String[] {strs[Numeral.TWO]});
			templateParamMap.put(VERTICAL_DATUM_TITLE, params);
		} 
		return new ValidatorHelper(messages, hasErrorMessage)
				.updateDataBeanOrCreateErrorOptional(strs, dataBean);
	}

	protected final boolean isValidVerticalDatumValue(String string) {
		boolean isLegalVerticalDatumValue = false;
		
		for (VerticalDatum value : VerticalDatum.values()) {
			if (value.name().equals(string)) {
				isLegalVerticalDatumValue = true;
				break;
			}
		}

		return isLegalVerticalDatumValue;
	}

}
