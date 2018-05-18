package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

@Component
public class TemplateDisplayPropertiesPopulator {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TemplateDisplayPropertiesPopulator(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesPopulator:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void doPopulation(Map<String, List<Map<String, Object>>> resultMap, List<String> classesList,
			Map<String, Object> templateFieldMap, long batchId) {
		if ((!CollectionUtils.isEmpty(classesList)) && (MapUtils.isNotEmpty(templateFieldMap))) {
			classesList.stream().forEach(cls -> {
				new TemplateHeaderRetriever(jdbcTemplate)
					.extractTemplateHeaders(resultMap, batchId, cls);
				extractMandatoryFields(resultMap, templateFieldMap, batchId, cls);
				new TemplateOptionalFieldsRetriever(jdbcTemplate)
					.extractOptionalFields(resultMap, batchId, cls);
			});
		}

	}

	private void extractMandatoryFields(Map<String, List<Map<String, Object>>> resultMap, 
			Map<String, Object> templateFieldMap,
			long batchId, String cls) {
		Object object = templateFieldMap.get(cls);
		if (object instanceof List) {
			@SuppressWarnings({ "unchecked" })
			List<LinkedHashMap<String, String>> templateClassesList = 
				(List<LinkedHashMap<String, String>>) object;
			templateClassesList.stream()
				.forEach(classAndFields -> {
				classAndFields.entrySet().stream()
					.forEach(element -> {
						String fieldString = element.getValue();
						List<Map<String, Object>> results = 
								new TemplateDisplayPropertiesJdbcTemplateHelper(jdbcTemplate)
									.getList(element.getKey(), fieldString, batchId);
						AtomicInteger counter = new AtomicInteger();
						results.stream()
							.forEach(result -> {
								String currentKey = new StringBuilder(cls)
										.append(Strings.UNDER_LINE_DATA_KEY)
										.append(counter.incrementAndGet())
										.toString();
								List<Map<String, Object>> dataRecord = resultMap.get(currentKey);
								if (null == dataRecord) {
									dataRecord = new ArrayList<>();
									dataRecord.add(result);
								} else {
									Map<String, Object> datas = dataRecord.get(Numeral.ZERO);
									result.forEach((key, value) -> {
										datas.put(key, value);
									});
								}
								resultMap.put(currentKey, dataRecord);
							});
				});
			});
		}
	}

}
