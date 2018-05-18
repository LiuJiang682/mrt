package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class TemplateOptionalFieldsJdbcTemplateRetriever {

	private static final String SELECT_HEADER_SQL = "SELECT FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = ? AND TEMPLATE_NAME =? AND ROW_NUMBER = 'H1000' AND TEMPLATE_HEADER = 'H1000'";
	private final JdbcTemplate jdbcTemplate;
	
	public TemplateOptionalFieldsJdbcTemplateRetriever(JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateOptionalFieldsJdbcTemplateRetriever:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Map<String, Object>> getList(String template, long batchId) {
		String sql = "SELECT LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE LOADER_ID = ? AND TEMPLATE_NAME = ?";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, 
				new Object[] {batchId, template});
		return result;
	}

	public String getHeaders(String template, long batchId) {
		String value = jdbcTemplate.queryForObject(SELECT_HEADER_SQL, 
				String.class, new Object[] {batchId, template});
		return value;
	}

}
