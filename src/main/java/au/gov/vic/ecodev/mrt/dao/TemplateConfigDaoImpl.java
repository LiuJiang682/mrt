package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

@Repository
public class TemplateConfigDaoImpl implements TemplateConfigDao {

	private static final Logger LOGGER = Logger.getLogger(TemplateConfigDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getTemplateClasses(String templateName) {
		String sql = "select class_names from template_config where template_name = ?";
		String classes = Strings.EMPTY;
		try {
				classes = jdbcTemplate.queryForObject(sql, new Object[] {templateName.toUpperCase()}, 
						String.class);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No template class found for template: " + templateName, e);
		}
		return classes;
	}

}
