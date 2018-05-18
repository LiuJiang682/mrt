package au.gov.vic.ecodev.mrt.template.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	
	public static void displayTemplateData(Template template) {
		List<String> templateKeys = template.getKeys();
		Collections.sort(templateKeys);
		List<String> dataList = new ArrayList<>();
		for (String key : templateKeys) {
			List<String> values = template.get(key);
			if (null != values) {
				String contents = values.stream().collect(Collectors.joining(Strings.SPACE));
				if (Pattern.matches("^D\\d+", key)) {
					dataList.add(key + Strings.SPACE + contents);
				} else {
					LOGGER.info(key + Strings.SPACE + contents);
				}
			}
			
		}
		dataList.stream()
			.forEach(s -> {LOGGER.info(s);});
	}
}
