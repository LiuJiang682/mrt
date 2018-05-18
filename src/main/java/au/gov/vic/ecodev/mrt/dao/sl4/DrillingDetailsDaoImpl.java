package au.gov.vic.ecodev.mrt.dao.sl4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.sl4.rowmapper.DrillingDetailsRowMapper;
import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class DrillingDetailsDaoImpl implements DrillingDetailsDao {

	private static final Logger LOGGER = Logger.getLogger(DrillingDetailsDaoImpl.class);

	private static final String SELECT_WHERE_SQL = "SELECT ID, DRILL_TYPE, DRILL_COMPANY, DRILL_DESCRIPTION FROM DH_DRILLING_DETAILS WHERE DRILL_TYPE = ? AND DRILL_COMPANY = ?";

	private static final String UPDATE_SQL = "UPDATE DH_DRILLING_DETAILS SET DRILL_TYPE = ?, DRILL_COMPANY = ?, DRILL_DESCRIPTION = ? where ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_DRILLING_DETAILS(ID, DRILL_TYPE, DRILL_COMPANY, DRILL_DESCRIPTION) values (?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM DH_DRILLING_DETAILS WHERE ID = ?";

	private static final String SELECT_SQL = "SELECT ID, DRILL_TYPE, DRILL_COMPANY, DRILL_DESCRIPTION FROM DH_DRILLING_DETAILS WHERE ID = ?";;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		DrillingDetails drillingDetails = (DrillingDetails) entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, drillingDetails.getId());
		if (Numeral.ZERO == count) {
			int rows = jdbcTemplate.update(INSERT_SQL, new Object[] { drillingDetails.getId(),
					drillingDetails.getDrillType(), drillingDetails.getDrillCompany(), 
					drillingDetails.getDrillDescription() });
			return Numeral.ONE == rows;
		} else {
			int rows = jdbcTemplate.update(UPDATE_SQL, new Object[] { drillingDetails.getDrillType(), 
					drillingDetails.getDrillCompany(), 
					drillingDetails.getDrillDescription(), drillingDetails.getId() });
			return Numeral.ONE == rows;
		}
	}

	@Override
	public Entity get(long id) {
		DrillingDetails drillingDetails = null;
		try {
			drillingDetails = (DrillingDetails) jdbcTemplate.queryForObject(SELECT_SQL, 
				new Object[] {id}, 
				new DrillingDetailsRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No DrillingDetails found for id: " + id, e);
		}
		return drillingDetails;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("Inside DrillingDetailsDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public DrillingDetails getByDrillingTypeAndCompany(String drillingType, String drillingCompany) {
		DrillingDetails drillingDetails = null;
		try {
			drillingDetails = (DrillingDetails) jdbcTemplate.queryForObject(SELECT_WHERE_SQL, 
				new Object[] {drillingType, drillingCompany}, 
				new DrillingDetailsRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No DrillingDetails found for drillingType: " + drillingType + ", drillingCompany: " + drillingCompany, e);
		}
		return drillingDetails;
	}

}
