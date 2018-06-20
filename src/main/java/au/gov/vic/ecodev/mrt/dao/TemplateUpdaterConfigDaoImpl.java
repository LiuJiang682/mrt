package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Strings;

@Repository
public class TemplateUpdaterConfigDaoImpl implements TemplateUpdaterConfigDao {

	private static final Logger LOGGER = Logger.getLogger(TemplateUpdaterConfigDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getTemplateUpdaterClass(String templateName) {
		String sql = "select class_names from template_updater_config where template_name = ?";
		String classes = Strings.EMPTY;
		try {
				classes = jdbcTemplate.queryForObject(sql, new Object[] {templateName}, String.class);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No template class found for template: " + templateName, e);
		}
		return classes;
	}

}
