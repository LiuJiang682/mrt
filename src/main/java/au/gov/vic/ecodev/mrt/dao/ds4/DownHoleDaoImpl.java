package au.gov.vic.ecodev.mrt.dao.ds4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.model.ds4.DownHole;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class DownHoleDaoImpl implements DownHoleDao {
	
	private static final Logger LOGGER = Logger.getLogger(DownHoleDaoImpl.class);

	private static final String UPDATE_SQL = "UPDATE DH_DOWNHOLE SET LOADER_ID = ?, HOLE_ID = ?, FILE_NAME = ?, SURVEYED_DEPTH = ?, AZIMUTH_MAG = ?, DIP = ?, AZIMUTH_TRUE = ? WHERE ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_DOWNHOLE(ID, LOADER_ID, HOLE_ID, FILE_NAME, SURVEYED_DEPTH, AZIMUTH_MAG, DIP, AZIMUTH_TRUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM DH_DOWNHOLE WHERE ID = ? ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		DownHole downHole = (DownHole)entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, 
				new Object[] {downHole.getId()});
		if (Numeral.ZERO == count) {
			int row = jdbcTemplate.update(INSERT_SQL, downHole.getId(), downHole.getLoaderId(),
					downHole.getHoleId(), downHole.getFileName(),
					downHole.getSurveyedDepth(), downHole.getAzimuthMag(),
					downHole.getDip(), downHole.getAzimuthTrue());
			return Numeral.ONE == row;
		} else {
			int row = jdbcTemplate.update(UPDATE_SQL,  downHole.getLoaderId(),
					downHole.getHoleId(), downHole.getFileName(), downHole.getSurveyedDepth(), 
					downHole.getAzimuthMag(), downHole.getDip(), downHole.getAzimuthTrue(), 
					downHole.getId());
			return Numeral.ONE == row;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("DownHoleDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

}
