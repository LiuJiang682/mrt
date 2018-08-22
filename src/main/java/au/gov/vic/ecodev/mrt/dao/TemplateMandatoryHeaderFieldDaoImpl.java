package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.rowmapper.TemplateMandatoryHeaderFieldRowMapper;
import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class TemplateMandatoryHeaderFieldDaoImpl implements TemplateMandatoryHeaderFieldDao {

	private static final Logger LOGGER = Logger.getLogger(TemplateMandatoryHeaderFieldDaoImpl.class);
	
	private static final String SELECT_SQL = "SELECT COUNT(ID) FROM DH_MANDATORY_HEADERS WHERE ID = ?";
	
	private static final String INSERT_SQL = "INSERT INTO DH_MANDATORY_HEADERS (ID, LOADER_ID, TEMPLATE_NAME, FILE_NAME, ROW_NUMBER, COLUMN_HEADER, FIELD_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_SQL = "UPDATE DH_MANDATORY_HEADERS SET LOADER_ID = ?, TEMPLATE_NAME = ?, FILE_NAME = ?, ROW_NUMBER = ?, COLUMN_HEADER = ?, FIELD_VALUE = ? WHERE ID = ?";
	
	private static final String GET_SELECT_SQL = "SELECT ID, LOADER_ID, TEMPLATE_NAME, FILE_NAME, ROW_NUMBER, COLUMN_HEADER, FIELD_VALUE FROM DH_MANDATORY_HEADERS WHERE ID = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		TemplateMandatoryHeaderField templateMandatoryHeaderField = (TemplateMandatoryHeaderField)entity;
		try {
			int count = jdbcTemplate.queryForObject(SELECT_SQL, Integer.class, 
					new Object[] {templateMandatoryHeaderField.getId()});
			if (Numeral.ZERO == count) {
				int row = jdbcTemplate.update(INSERT_SQL, templateMandatoryHeaderField.getId(), 
						templateMandatoryHeaderField.getSessionId(), 
						templateMandatoryHeaderField.getTemplateName(), 
						templateMandatoryHeaderField.getFileName(),
						templateMandatoryHeaderField.getRowNumber(),
						templateMandatoryHeaderField.getColumnHeader(),
						templateMandatoryHeaderField.getFieldValue());
				return Numeral.ONE == row;
			} else {
				int row = jdbcTemplate.update(UPDATE_SQL, 
						templateMandatoryHeaderField.getSessionId(),
						templateMandatoryHeaderField.getFileName(),
						templateMandatoryHeaderField.getTemplateName(),
						templateMandatoryHeaderField.getRowNumber(),
						templateMandatoryHeaderField.getColumnHeader(),
						templateMandatoryHeaderField.getFieldValue(), 
						templateMandatoryHeaderField.getId());
				return Numeral.ONE == row;
			}
		} catch (DataAccessException e) {
			LOGGER.error("Failed to insert " + templateMandatoryHeaderField + ", due to " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Entity get(long id) {
		TemplateMandatoryHeaderField templateMandatoryHeaderField = null;
		
		try {
			templateMandatoryHeaderField = jdbcTemplate.queryForObject(GET_SELECT_SQL, new Object[]{id}, 
					new TemplateMandatoryHeaderFieldRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No SessionHeader found for id: " + id, e);
		}
		return templateMandatoryHeaderField;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
