package au.gov.vic.ecodev.mrt.template.processor.file.validator.param.helper.sl4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;

public class ParamHelper {

	private final String[] strs;
	private final String title;
	private final Map<String, List<String>> templateParamMap;

	public ParamHelper(final String[] strs, final String title, final Map<String, List<String>> templateParamMap) {
		this.strs = strs;
		this.title = title;
		this.templateParamMap = templateParamMap;
	}

	public void addParamToMap() {
		String[] contents = Arrays.copyOfRange(strs, Numeral.TWO, strs.length);
		List<String> params = Arrays.asList(contents);
		templateParamMap.put(title, params);
	}
}
