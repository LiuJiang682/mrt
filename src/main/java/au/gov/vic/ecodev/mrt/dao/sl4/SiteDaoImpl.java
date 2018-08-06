package au.gov.vic.ecodev.mrt.dao.sl4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.dao.sl4.rowmapper.SiteRowMapper;
import au.gov.vic.ecodev.mrt.model.sl4.Site;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

@Repository
public class SiteDaoImpl implements SiteDao {

	private static final Logger LOGGER = Logger.getLogger(SiteDaoImpl.class);

	private static final String UPDATE_SQL = "UPDATE lOC_SITE SET GSV_SITE_ID=?, PARISH=?, PROSPECT=?, AMG_ZONE=?, EASTING=?, NORTHING=?, LATITUDE=?, LONGITUDE=?, LOCN_ACC=?, LOCN_DATUM_CD=?, ELEVATION_GL=?, ELEV_ACC=?, ELEV_DATUM_CD=?, NUM_DATA_RECORDS=?, FILE_NAME=?, ISSUE_COLUMN_INDEX = ? WHERE LOADER_ID = ? AND SITE_ID = ?";

	private static final String INSERT_SQL = "INSERT INTO lOC_SITE(LOADER_ID, SITE_ID, GSV_SITE_ID, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, NUM_DATA_RECORDS, FILE_NAME, ISSUE_COLUMN_INDEX) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String COUNT_SQL = "SELECT COUNT(LOADER_ID) FROM lOC_SITE WHERE LOADER_ID = ? AND SITE_ID = ?";
	
	private static final String SELECT_SQL = "SELECT LOADER_ID, SITE_ID, GSV_SITE_ID, PARISH, PROSPECT, AMG_ZONE, EASTING, NORTHING, LATITUDE, LONGITUDE, LOCN_ACC, LOCN_DATUM_CD, ELEVATION_GL, ELEV_ACC, ELEV_DATUM_CD, NUM_DATA_RECORDS, FILE_NAME, ISSUE_COLUMN_INDEX FROM lOC_SITE WHERE LOADER_ID = ? AND SITE_ID = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean updateOrSave(Entity entity) {
		Site site = (Site) entity;
		int count = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, site.getLoaderId(), site.getSiteId());
		if (Numeral.ZERO == count) {
			int rows = jdbcTemplate.update(INSERT_SQL, new Object[] {site.getLoaderId(), site.getSiteId(),
					site.getGsvSiteId(), site.getParish(), site.getProspect(), site.getAmgZone(),
					site.getEasting(), site.getNorthing(), site.getLatitude(), site.getLongitude(),
					site.getLocnAcc(), site.getLocnDatumCd(), site.getElevationGl(), site.getElevAcc(),
					site.getElevDatumCd(), site.getNumberOfDataRecord(), site.getFileName(),
					site.getIssueColumnIndex()});
			return Numeral.ONE == rows;
		} else {
			int rows = jdbcTemplate.update(UPDATE_SQL, new Object[] {
					site.getGsvSiteId(), site.getParish(), site.getProspect(), site.getAmgZone(),
					site.getEasting(), site.getNorthing(), site.getLatitude(), site.getLongitude(),
					site.getLocnAcc(), site.getLocnDatumCd(), site.getElevationGl(), site.getElevAcc(),
					site.getElevDatumCd(), site.getNumberOfDataRecord(), site.getFileName(), site.getIssueColumnIndex(),
					site.getLoaderId(), site.getSiteId()});
			return Numeral.ONE == rows;
		}
	}

	@Override
	public Entity get(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LOGGER.debug("Inside SiteDaoImpl.setJdbcTemplate: " + jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Site getSiteBySessionIdAndSiteId(long sessionId, String siteId) {
		Site site = null;
		try {
			site = jdbcTemplate.queryForObject(SELECT_SQL, new Object[] {sessionId, siteId}, 
					new SiteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			LOGGER.warn("No site found for LOADER_ID: " + sessionId + ", siteId: " + siteId, e);
		}
		return site;
	}

}
