package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class StringListHelper {

	public List<String> trimOffTheTitle(List<String> data) {
		List<String> plainData = null;
		if ((null != data) 
				&& (Numeral.ONE < data.size())) {
			plainData = data.subList(Numeral.ONE, data.size());
		}
		return plainData;
	}
}
