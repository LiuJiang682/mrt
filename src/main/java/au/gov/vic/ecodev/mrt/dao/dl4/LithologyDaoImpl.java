package au.gov.vic.ecodev.mrt.dao.dl4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.model.dl4.Lithology;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class LithologyDaoImpl implements LithologyDao {

	private static final Logger LOGGER = Logger.getLogger(LithologyDaoImpl.class);
	
	private static final String UPDATE_SQL = "UPDATE DH_LITHOLOGY SET LOADER_ID = ?, HOLE_ID = ?, FILE_NAME = ?, ROW_NUMBER =?, DEPTH_FROM = ? WHERE ID = ?";

	private static final String INSERT_SQL = "INSERT INTO DH_LITHOLOGY(ID, LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, DEPTH_FROM) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(ID) FROM DH_LITHOLOGY WHERE ID = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		Lithology lithology = (Lithology)entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, new Object[] {lithology.getId()});
		if (Numeral.ZERO == count) {
			int row = jdbcTemplate.update(INSERT_SQL, lithology.getId(), lithology.getLoaderId(),
					lithology.getHoleId(), lithology.getFileName(), 
					lithology.getRowNumber(), lithology.getDepthFrom());
			return Numeral.ONE == row;
		} else {
			int row = jdbcTemplate.update(UPDATE_SQL, lithology.getLoaderId(), 
					lithology.getHoleId(), lithology.getFileName(), 
					lithology.getRowNumber(), lithology.getDepthFrom(), lithology.getId());
			return Numeral.ONE == row;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("LithologyDaoImpl:setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

}
