package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import java.util.ArrayList;
import java.util.List;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateNameExtractor {

	public final List<String> extractTemplateName(List<String> fileNames) {
		List<String> templateNames = new ArrayList<>();
		fileNames.stream()
			.forEach(fileName -> {
				String[] names = fileName.split(Strings.UNDER_LINE);
				templateNames.add(names[Numeral.ZERO]);
			});
		return templateNames;
	}
}
