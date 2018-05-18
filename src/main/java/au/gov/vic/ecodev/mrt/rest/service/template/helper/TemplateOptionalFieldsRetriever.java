package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateOptionalFieldsRetriever {

	private static final String HEADER_PREFIX = "H";
	private static final String FIELD_VALUE = "FIELD_VALUE";
	private static final String TEMPLATE_HEADER = "TEMPLATE_HEADER";
	private static final String ROW_NUMBER = "ROW_NUMBER";
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateOptionalFieldsRetriever(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateOptionalFieldsRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public void extractOptionalFields(Map<String, List<Map<String, Object>>> resultMap, long batchId, String template) {
		List<Map<String, Object>> optionalFields = new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
				.getList(template, batchId);
		optionalFields.stream()
			.forEach(optionalField -> {
				String rowNumber = (String) optionalField.get(ROW_NUMBER);
				if (!Strings.KEY_H1000.equalsIgnoreCase(rowNumber)) {
					String header = (String) optionalField.get(TEMPLATE_HEADER);
					String value = (String) optionalField.get(FIELD_VALUE);
					String key = new StringBuilder(template)
							.append((rowNumber.startsWith(HEADER_PREFIX)) 
									? Strings.UNDER_LINE : Strings.UNDER_LINE_DATA_KEY)
							.append(rowNumber)
							.toString();
					List<Map<String, Object>> dataRecord = resultMap.get(key);
					if (null == dataRecord) {
						dataRecord = new ArrayList<>();
						Map<String, Object> dataMap = new HashMap<>();
						dataMap.put(header, value);
						dataRecord.add(dataMap);
					} else {
						Map<String, Object> dataMap = dataRecord.get(Numeral.ZERO);
						dataMap.put(header, value);
					}
					resultMap.put(key, dataRecord);
				}
			});
		
	}

}
