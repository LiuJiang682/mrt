package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class LabelledColumnIndexListExtractor {

	private final Template template;
	
	public LabelledColumnIndexListExtractor(final Template template) {
		if (null == template) {
			throw new IllegalArgumentException("LabelledColumnIndexListExtractor:template cannot be null!");
		}
		this.template = template;
	}
	
	public final List<Integer> getColumnIndexListByStartWith(final String startWith) {
		if (StringUtils.isEmpty(startWith)) {
			throw new IllegalArgumentException("LabelledColumnIndexListExtractor::getColumnIndexListByStartWith - startWith cannot be null or empty!");
		}
		List<String> keys = template.getKeys();
		List<String> duplicateKeys = keys.stream()
				.filter(key -> key.startsWith(startWith))
				.collect(Collectors.toList());
		List<Integer> indexList = new ArrayList<>();
		duplicateKeys.stream()
			.forEach(key -> {
				List<String> positionStringList = template.get(key);
				List<Integer> positionList = positionStringList.stream()
						.map(Integer::valueOf)
						.collect(Collectors.toList());
				indexList.addAll(positionList);
			});
		return indexList;
	}
}
