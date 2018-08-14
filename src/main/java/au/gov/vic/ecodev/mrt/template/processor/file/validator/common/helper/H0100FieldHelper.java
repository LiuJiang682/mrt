package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0100FieldHelper {

	public void doTenementNoSplit(final Template dataBean,
			final Map<String, List<String>> templateParamMap) {
		List<String> tenementList = dataBean.get(Strings.KEY_H0100);
		if (Numeral.TWO == tenementList.size()) {
			String tenements = tenementList.get(Numeral.ONE);
			String[] tenementArray = tenements.split(Strings.COMMA);
			tenementList = Arrays.asList(tenementArray);
		}
		templateParamMap.put(Strings.KEY_H0100, tenementList);
	}
}
