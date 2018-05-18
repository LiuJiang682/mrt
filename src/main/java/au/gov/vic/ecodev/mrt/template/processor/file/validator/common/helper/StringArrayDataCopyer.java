package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.Arrays;
import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class StringArrayDataCopyer {

	public final List<String> getList(final String[] strs) {
		List<String> descriptions = null;
		if ((null != strs) 
				&& (Numeral.TWO < strs.length)) {
			descriptions = Arrays.asList(Arrays.copyOfRange(strs, 
					Numeral.TWO, strs.length));
		}
		return descriptions;
	}

}
