package au.gov.vic.ecodev.mrt.dao.sl4.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.sl4.Site;

public class SiteRowMapper implements RowMapper<Site> {

	private static final String COLUMN_HEADER_FILE_NAME = "FILE_NAME";
	private static final String COLUMN_HEADER_ISSUE_COLUMN_INDEX = "ISSUE_COLUMN_INDEX";
	private static final String COLUMN_HEADER_NUM_DATA_RECORDS = "NUM_DATA_RECORDS";
	private static final String COLUMN_HEADER_ELEV_DATUM_CD = "ELEV_DATUM_CD";
	private static final String COLUMN_HEADER_ELEV_ACC = "ELEV_ACC";
	private static final String COLUMN_HEADER_ELEVATION_GL = "ELEVATION_GL";
	private static final String COLUMN_HEADER_LOCN_DATUM_CD = "LOCN_DATUM_CD";
	private static final String COLUMN_HEADER_LOCN_ACC = "LOCN_ACC";
	private static final String COLUMN_HEADER_LONGITUDE = "LONGITUDE";
	private static final String COLUMN_HEADER_LATITUDE = "LATITUDE";
	private static final String COLUMN_HEADER_NORTHING = "NORTHING";
	private static final String COLUMN_HEADER_EASTING = "EASTING";
	private static final String COLUMN_HEADER_AMG_ZONE = "AMG_ZONE";
	private static final String COLUMN_HEADER_PROSPECT = "PROSPECT";
	private static final String COLUMN_HEADER_PARISH = "PARISH";
	private static final String COLUMN_HEADER_GSV_SITE_ID = "GSV_SITE_ID";
	private static final String COLUMN_HEADER_SITE_ID = "SITE_ID";
	private static final String COLUMN_HEADER_LOADER_ID = "LOADER_ID";

	@Override
	public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
		Site site = new Site();
		site.setLoaderId(rs.getLong(COLUMN_HEADER_LOADER_ID));
		site.setSiteId(rs.getString(COLUMN_HEADER_SITE_ID));
		site.setGsvSiteId(rs.getLong(COLUMN_HEADER_GSV_SITE_ID));
		site.setParish(rs.getString(COLUMN_HEADER_PARISH));
		site.setProspect(rs.getString(COLUMN_HEADER_PROSPECT));
		site.setAmgZone(rs.getBigDecimal(COLUMN_HEADER_AMG_ZONE));
		site.setEasting(rs.getBigDecimal(COLUMN_HEADER_EASTING));
		site.setNorthing(rs.getBigDecimal(COLUMN_HEADER_NORTHING));
		site.setLatitude(rs.getBigDecimal(COLUMN_HEADER_LATITUDE));
		site.setLongitude(rs.getBigDecimal(COLUMN_HEADER_LONGITUDE));
		site.setLocnAcc(rs.getBigDecimal(COLUMN_HEADER_LOCN_ACC));
		site.setLocnDatumCd(rs.getString(COLUMN_HEADER_LOCN_DATUM_CD));
		site.setElevationGl(rs.getBigDecimal(COLUMN_HEADER_ELEVATION_GL));
		site.setElevAcc(rs.getBigDecimal(COLUMN_HEADER_ELEV_ACC));
		site.setElevDatumCd(rs.getString(COLUMN_HEADER_ELEV_DATUM_CD));
		site.setNumberOfDataRecord(rs.getInt(COLUMN_HEADER_NUM_DATA_RECORDS));
		site.setFileName(rs.getString(COLUMN_HEADER_FILE_NAME));
		site.setIssueColumnIndex(rs.getInt(COLUMN_HEADER_ISSUE_COLUMN_INDEX));
		return site;
	}

}
