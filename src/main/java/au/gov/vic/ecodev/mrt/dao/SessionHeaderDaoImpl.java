package au.gov.vic.ecodev.mrt.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.rowmapper.SessionHeaderRowMapper;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class SessionHeaderDaoImpl implements SessionHeaderDao {
	
	private static final Logger LOGGER = Logger.getLogger(SessionHeaderDaoImpl.class);

	private static final String SELECT_SQL = "SELECT ID, TEMPLATE, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT FROM SESSION_HEADER WHERE ID = ?";

	private static final String UPDATE_SQL = "UPDATE SESSION_HEADER SET TEMPLATE = ?, PROCESS_DATE = ?, TENEMENT = ?, TENEMENT_HOLDER = ?, REPORTING_DATE = ?, PROJECT_NAME = ?, STATUS = ?, COMMENTS = ?, EMAIL_SENT = ? where ID = ?";

	private static final String INSERT_SQL = "INSERT INTO SESSION_HEADER(ID, TEMPLATE, PROCESS_DATE, TENEMENT, TENEMENT_HOLDER, REPORTING_DATE, PROJECT_NAME, STATUS, COMMENTS, EMAIL_SENT) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM SESSION_HEADER WHERE ID = ?";


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean updateOrSave(Entity entity) {
		SessionHeader sessionHeader = (SessionHeader) entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, sessionHeader.getSessionId());
		if (Numeral.ZERO == count) {
			int rows = jdbcTemplate.update(INSERT_SQL, new Object[] { sessionHeader.getSessionId(),
					sessionHeader.getTemplate(), sessionHeader.getProcessDate(), sessionHeader.getTenement(), 
					sessionHeader.getTenementHolder(), sessionHeader.getReportingDate(), 
					sessionHeader.getProjectName(), sessionHeader.getStatus().name(),
					sessionHeader.getComments(), sessionHeader.getEmailSent() });
			return Numeral.ONE == rows;
		} else {
			int rows = jdbcTemplate.update(UPDATE_SQL, new Object[] { sessionHeader.getTemplate(),
					sessionHeader.getProcessDate(), sessionHeader.getTenement(), 
					sessionHeader.getTenementHolder(), 
					sessionHeader.getReportingDate(), sessionHeader.getProjectName(), 
					sessionHeader.getStatus().name(), sessionHeader.getComments(), 
					sessionHeader.getEmailSent(), sessionHeader.getSessionId() });
			return Numeral.ONE == rows;
		}
	}

	@Override
	public Entity get(long id) {
		SessionHeader sessionHeader = null;
		try {
			sessionHeader = (SessionHeader) jdbcTemplate.queryForObject(SELECT_SQL, 
					new Object[] { id },
					new SessionHeaderRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No SessionHeader found for id: " + id, e);
		}
		return sessionHeader;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("Inside SessionHeaderDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
}
