package au.gov.vic.ecodev.mrt.dao.sg4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.model.sg4.SurfaceGeochemistry;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class SurfaceGeochemistryDaoImpl implements SurfaceGeochemistryDao {

	private static final Logger LOGGER = Logger.getLogger(SurfaceGeochemistryDaoImpl.class);
	
	private static final String UPDATE_SQL = "UPDATE DH_SURFACE_GEOCHEMISTRY SET LOADER_ID = ?, SAMPLE_ID = ?, EASTING = ?, NORTHING = ?, SAMPLE_TYPE = ?, AMG_ZONE = ? WHERE ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_SURFACE_GEOCHEMISTRY(ID, LOADER_ID, SAMPLE_ID, EASTING, NORTHING, SAMPLE_TYPE, AMG_ZONE) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM DH_SURFACE_GEOCHEMISTRY WHERE ID = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		SurfaceGeochemistry surfaceGeochemistry = (SurfaceGeochemistry) entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, surfaceGeochemistry.getId());
		if (Numeral.ZERO == count) {
			int row = jdbcTemplate.update(INSERT_SQL, surfaceGeochemistry.getId(), 
					surfaceGeochemistry.getLoaderId(), surfaceGeochemistry.getSampleId(), 
					surfaceGeochemistry.getEasting(), surfaceGeochemistry.getNorthing(), 
					surfaceGeochemistry.getSampleType(), surfaceGeochemistry.getAmgZone());
			return Numeral.ONE == row;
		} else {
			int row = jdbcTemplate.update(UPDATE_SQL, surfaceGeochemistry.getLoaderId(), 
					surfaceGeochemistry.getSampleId(), surfaceGeochemistry.getEasting(), 
					surfaceGeochemistry.getNorthing(), surfaceGeochemistry.getSampleType(),
					surfaceGeochemistry.getAmgZone(), surfaceGeochemistry.getId());
			return Numeral.ONE == row;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("Inside SurfaceGeochemistryDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

}
