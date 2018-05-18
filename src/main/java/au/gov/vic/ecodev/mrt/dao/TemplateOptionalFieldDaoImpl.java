package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.rowmapper.TemplateOptionalFieldRowMapper;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class TemplateOptionalFieldDaoImpl implements TemplateOptionalFieldDao {

	private static final Logger LOGGER = Logger.getLogger(TemplateOptionalFieldDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		TemplateOptionalField templateOptionalField = (TemplateOptionalField)entity;
		String countSql = "SELECT COUNT(ID) FROM DH_OPTIONAL_FIELDS WHERE ID = ?";
		int count = jdbcTemplate.queryForObject(countSql, Integer.class, 
				new Object[] {templateOptionalField.getId()});
		if (Numeral.ZERO == count) {
			String insertSql = "INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE) VALUES (?, ?, ?, ?, ?, ?)";
			int row = jdbcTemplate.update(insertSql, templateOptionalField.getId(), 
					templateOptionalField.getSessionId(), templateOptionalField.getTemplateName(),
					templateOptionalField.getTemplateHeader(), templateOptionalField.getRowNumber(),
					templateOptionalField.getFieldValue());
			return Numeral.ONE == row;
		} else {
			String updateSql = "UPDATE DH_OPTIONAL_FIELDS SET LOADER_ID = ?, TEMPLATE_NAME = ?, TEMPLATE_HEADER = ?, ROW_NUMBER = ?, FIELD_VALUE = ? WHERE ID = ?";
			int row = jdbcTemplate.update(updateSql, 
					templateOptionalField.getSessionId(), templateOptionalField.getTemplateName(),
					templateOptionalField.getTemplateHeader(), templateOptionalField.getRowNumber(),
					templateOptionalField.getFieldValue(), templateOptionalField.getId());
			return Numeral.ONE == row;
		}
	}

	@Override
	public Entity get(long id) {
		TemplateOptionalField templateOptionalField = null;
		String selectSql = "SELECT ID, LOADER_ID, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE ID = ?";
		
		try {
			templateOptionalField = jdbcTemplate.queryForObject(selectSql, new Object[]{id}, 
					new TemplateOptionalFieldRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No SessionHeader found for id: " + id, e);
		}
		return templateOptionalField;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
