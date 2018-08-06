package au.gov.vic.ecodev.mrt.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	private static final String GET_SELECT_SQL = "SELECT ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE FROM DH_OPTIONAL_FIELDS WHERE ID = ?";

	private static final String SELECT_SQL = "SELECT COUNT(ID) FROM DH_OPTIONAL_FIELDS WHERE ID = ?";

	private static final String UPDATE_SQL = "UPDATE DH_OPTIONAL_FIELDS SET LOADER_ID = ?, FILE_NAME = ?, TEMPLATE_NAME = ?, TEMPLATE_HEADER = ?, ROW_NUMBER = ?, FIELD_VALUE = ? WHERE ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_OPTIONAL_FIELDS(ID, LOADER_ID, FILE_NAME, TEMPLATE_NAME, TEMPLATE_HEADER, ROW_NUMBER, FIELD_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		TemplateOptionalField templateOptionalField = (TemplateOptionalField)entity;
		try {
			int count = jdbcTemplate.queryForObject(SELECT_SQL, Integer.class, 
					new Object[] {templateOptionalField.getId()});
			if (Numeral.ZERO == count) {
				int row = jdbcTemplate.update(INSERT_SQL, templateOptionalField.getId(), 
						templateOptionalField.getSessionId(), templateOptionalField.getFileName(),
						templateOptionalField.getTemplateName(), 
						templateOptionalField.getTemplateHeader(), 
						templateOptionalField.getRowNumber(),
						templateOptionalField.getFieldValue());
				return Numeral.ONE == row;
			} else {
				int row = jdbcTemplate.update(UPDATE_SQL, 
						templateOptionalField.getSessionId(),
						templateOptionalField.getFileName(),
						templateOptionalField.getTemplateName(),
						templateOptionalField.getTemplateHeader(), 
						templateOptionalField.getRowNumber(),
						templateOptionalField.getFieldValue(), 
						templateOptionalField.getId());
				return Numeral.ONE == row;
			}
		} catch (DataAccessException e) {
			LOGGER.error("Failed to insert " + templateOptionalField + ", due to " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Entity get(long id) {
		TemplateOptionalField templateOptionalField = null;
		
		try {
			templateOptionalField = jdbcTemplate.queryForObject(GET_SELECT_SQL, new Object[]{id}, 
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

	@Override
	public boolean batchUpdate(List<Entity> entities) {
		List<Object[]> argumentList = new ArrayList<>(entities.size());
		
		entities.stream().forEach(entity -> {
			TemplateOptionalField templateOptionalField = (TemplateOptionalField)entity;
			Object[] arguments = new Object[] {
					templateOptionalField.getId(), 
					templateOptionalField.getSessionId(),
					templateOptionalField.getFileName(),
					templateOptionalField.getTemplateName(),
					templateOptionalField.getTemplateHeader(), 
					templateOptionalField.getRowNumber(),
					templateOptionalField.getFieldValue()
			};
			argumentList.add(arguments);
		});
		int[] rows = jdbcTemplate.batchUpdate(INSERT_SQL, argumentList);
		return rows.length == entities.size();
	}

}
