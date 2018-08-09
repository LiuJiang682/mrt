package au.gov.vic.ecodev.mrt.dao.sl4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.sl4.rowmapper.BoreHoleRowMapper;
import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class BoreHoleDaoImpl implements BoreHoleDao {

	private static final Logger LOGGER = Logger.getLogger(BoreHoleDaoImpl.class);
	
	private static final String SELECT_SQL = "SELECT LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG FROM DH_BOREHOLE WHERE LOADER_ID = ? AND HOLE_ID = ?";

	private static final String UPDATE_SQL = "UPDATE DH_BOREHOLE SET FILE_NAME=?, ROW_NUMBER=?, BH_AUTHORITY_CD=?, BH_REGULATION_CD=?, DILLING_DETAILS_ID=?, DRILLING_START_DT=?, DRILLING_COMPLETION_DT=?, DEPTH=?, ELEVATION_KB=?, AZIMUTH_MAG=?, BH_CONFIDENTIAL_FLG=? WHERE LOADER_ID = ? AND HOLE_ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(LOADER_ID) FROM DH_BOREHOLE WHERE LOADER_ID = ? AND HOLE_ID = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		BoreHole boreHole = (BoreHole)entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, 
				new Object[] {boreHole.getLoaderId(), boreHole.getHoleId()});
		if (Numeral.ZERO == count) {
			int rows = jdbcTemplate.update(INSERT_SQL, boreHole.getLoaderId(), boreHole.getHoleId(), 
					boreHole.getFileName(), boreHole.getRowNumber(),
					boreHole.getBhAuthorityCd(), boreHole.getBhRegulationCd(), 
					boreHole.getDillingDetailsId(), boreHole.getDrillingStartDate(), 
					boreHole.getDrillingCompletionDate(), boreHole.getMdDepth(), 
					boreHole.getElevationKb(), boreHole.getAzimuthMag(),
					boreHole.getBhConfidentialFlag(), boreHole.getDepthUom());
			return Numeral.ONE == rows;
		} else {
			int rows = jdbcTemplate.update(UPDATE_SQL, 
					boreHole.getFileName(), boreHole.getRowNumber(),
					boreHole.getBhAuthorityCd(), boreHole.getBhRegulationCd(), 
					boreHole.getDillingDetailsId(), boreHole.getDrillingStartDate(), 
					boreHole.getDrillingCompletionDate(),boreHole.getMdDepth(), 
					boreHole.getElevationKb(), boreHole.getAzimuthMag(), 
					boreHole.getBhConfidentialFlag(), boreHole.getLoaderId(), 
					boreHole.getHoleId());
			return Numeral.ONE == rows;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("Inside BoreHoleDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public BoreHole getBySessionIdAndHoleId(long sessionId, String siteId) {
		BoreHole boreHole = null;
		try {
			boreHole = jdbcTemplate.queryForObject(SELECT_SQL, 
					new Object[] {sessionId, siteId}, new BoreHoleRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No boreHole found for LOADER_ID: " + sessionId + ", siteId: " + siteId, e);
		}
		return boreHole;
	}

}
