package au.gov.vic.ecodev.mrt.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDisplayPropertiesDaoImpl implements TemplateDisplayPropertiesDao {

	private static final String SELECT_SQL = "SELECT DISPLAY_PROPERTIES FROM TEMPLATE_DISPLAY_PROPERTIES WHERE TEMPLATE = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getDisplayProperties(String template) {
		String displayProperties = jdbcTemplate.queryForObject(SELECT_SQL, 
				String.class, new Object[] {template});
		return displayProperties;
	}

}
