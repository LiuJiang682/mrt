package au.gov.vic.ecodev.mrt.dao.dg4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.model.dg4.GeoChemistry;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class GeoChemistryDaoImpl implements GeoChemistryDao {
	
	private static final Logger LOGGER = Logger.getLogger(GeoChemistryDaoImpl.class);

	private static final String UPDATE_SQL = "UPDATE DH_GEOCHEMISTRY SET LOADER_ID = ?, HOLE_ID = ?, SAMPLE_ID = ?, SAMPLE_FROM = ?, SAMPLE_TO = ?, DRILL_CODE = ? WHERE ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_GEOCHEMISTRY(ID, LOADER_ID, HOLE_ID, SAMPLE_ID, SAMPLE_FROM, SAMPLE_TO, DRILL_CODE) VALUES (?, ?, ?, ?, ?, ?, ?) ";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM DH_GEOCHEMISTRY WHERE ID = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		GeoChemistry geoChemistry = (GeoChemistry)entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, new Object[] {geoChemistry.getId()});
		if (Numeral.ZERO == count) {
			int row = jdbcTemplate.update(INSERT_SQL, geoChemistry.getId(), geoChemistry.getLoaderId(),
					geoChemistry.getHoleId(), geoChemistry.getSampleId(), geoChemistry.getFrom(),
					geoChemistry.getTo(), geoChemistry.getDrillCode());
			return Numeral.ONE == row;
		} else {
			int row = jdbcTemplate.update(UPDATE_SQL, geoChemistry.getLoaderId(), geoChemistry.getHoleId(),
					geoChemistry.getSampleId(), geoChemistry.getFrom(), geoChemistry.getTo(),
					geoChemistry.getDrillCode(), geoChemistry.getId());
			return Numeral.ONE == row;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("GeoChemistryDaoImpl:setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;	
	}

}
