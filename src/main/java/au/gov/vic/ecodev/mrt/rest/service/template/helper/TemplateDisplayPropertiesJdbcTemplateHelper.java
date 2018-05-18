package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class TemplateDisplayPropertiesJdbcTemplateHelper {

	private final JdbcTemplate jdbcTemplate;
	
	public TemplateDisplayPropertiesJdbcTemplateHelper(final JdbcTemplate jdbcTemplate) {
		if (null == jdbcTemplate) {
			throw new IllegalArgumentException("TemplateDisplayPropertiesJdbcTemplateHelper:jdbcTemplate cannot be null!");
		}
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Map<String, Object>> getList(final String template, final String fields, final long batchId) {
		String sql = null;
		
		if (fields.contains(Strings.JSON_SQL_TAG)) {
			sql = fields.substring(fields.lastIndexOf(Strings.JSON_SQL_TAG) + Strings.JSON_SQL_TAG.length(), 
					fields.length());
		} else {
			sql = new StringBuilder("SELECT ")
				.append(fields)
				.append(" FROM ")
				.append(template)
				.append(" WHERE LOADER_ID = ?")
				.toString();
		}
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, 
				new Object[] { batchId });
		return results;
	}
}
