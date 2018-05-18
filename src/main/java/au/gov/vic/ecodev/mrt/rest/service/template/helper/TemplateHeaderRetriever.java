package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateHeaderRetriever {

	private final JdbcTemplate jdbcTemplate;
	
	public TemplateHeaderRetriever(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateHeaderRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void extractTemplateHeaders(Map<String, List<Map<String, Object>>> resultMap, 
			long batchId, String template) {
		String headersString = new TemplateOptionalFieldsJdbcTemplateRetriever(jdbcTemplate)
				.getHeaders(template, batchId);
		String[] headersArray = headersString.split(Strings.COMMA);
		Map<String, Object> headers = new HashMap<>(headersArray.length);
		for (int i = Numeral.ZERO; i < headersArray.length; i++) {
			String key = String.valueOf(i + Numeral.ONE);
			headers.put(String.valueOf(key), headersArray[i]);
		}
		List<Map<String, Object>> list = new ArrayList<>(Numeral.ONE);
		list.add(headers);
		resultMap.put(template + "_Headers", list);
	}
}
