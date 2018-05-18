package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateContextPropertiesDaoImpl implements TemplateContextPropertiesDao {

	private static final Logger LOGGER = Logger.getLogger(TemplateContextPropertiesDaoImpl.class);
	
	private static final String SQL = "SELECT PROPERTY_VALUE FROM TEMPLATE_CONTEXT_PROPERTIES WHERE TEMPLATE_NAME = ? AND PROPERTY_NAME = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getTemplateContextProperty(String templateName, String propertyName) {
		String propertyValue = null;
		try {
			propertyValue = jdbcTemplate.queryForObject(SQL, new Object[] {templateName.toUpperCase(), 
					propertyName}, 
					String.class);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No property value found for template: " + templateName + ", property name: " + propertyName, e);
		}
		return propertyValue;
	}

}
