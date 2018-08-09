package au.gov.vic.ecodev.mrt.dao.sl4.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;

public class BoreHoleRowMapper implements RowMapper<BoreHole> {

	private static final String COLUMN_HEADER_FILE_NAME = "FILE_NAME";
	private static final String COLUMN_HEADER_DILLING_DETAILS_ID = "DILLING_DETAILS_ID";
	private static final String COLUMN_HEADER_AZIMUTH_MAG = "AZIMUTH_MAG";
	private static final String COLUMN_HEADER_BH_CONFIDENTIAL_FLG = "BH_CONFIDENTIAL_FLG";
	private static final String COLUMN_HEADER_ELEVATION_KB = "ELEVATION_KB";
	private static final String COLUMN_HEADER_MD_DEPTH = "DEPTH";
	private static final String COLUMN_HEADER_DRILLING_COMPLETION_DT = "DRILLING_COMPLETION_DT";
	private static final String COLUMN_HEADER_DRILLING_START_DT = "DRILLING_START_DT";
	private static final String COLUMN_HEADER_BH_REGULATION_CD = "BH_REGULATION_CD";
	private static final String COLUMN_HEADER_BH_AUTHORITY_CD = "BH_AUTHORITY_CD";
	private static final String COLUMN_HEADER_HOLE_ID = "HOLE_ID";
	private static final String COLUMN_HEADER_LOADER_ID = "LOADER_ID";

	@Override
	public BoreHole mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoreHole boreHole = new BoreHole();
		boreHole.setLoaderId(rs.getLong(COLUMN_HEADER_LOADER_ID));
		boreHole.setHoleId(rs.getString(COLUMN_HEADER_HOLE_ID));
		boreHole.setFileName(rs.getString(COLUMN_HEADER_FILE_NAME));
		boreHole.setRowNumber(rs.getString("ROW_NUMBER"));
		boreHole.setBhAuthorityCd(rs.getString(COLUMN_HEADER_BH_AUTHORITY_CD));
		boreHole.setBhRegulationCd(rs.getString(COLUMN_HEADER_BH_REGULATION_CD));
		boreHole.setDillingDetailsId(rs.getLong(COLUMN_HEADER_DILLING_DETAILS_ID));
		boreHole.setDrillingStartDate(rs.getDate(COLUMN_HEADER_DRILLING_START_DT));
		boreHole.setDrillingCompletionDate(rs.getDate(COLUMN_HEADER_DRILLING_COMPLETION_DT));
		boreHole.setMdDepth(rs.getBigDecimal(COLUMN_HEADER_MD_DEPTH));
		boreHole.setElevationKb(rs.getBigDecimal(COLUMN_HEADER_ELEVATION_KB));
		boreHole.setAzimuthMag(rs.getBigDecimal(COLUMN_HEADER_AZIMUTH_MAG));
		boreHole.setBhConfidentialFlag(rs.getString(COLUMN_HEADER_BH_CONFIDENTIAL_FLG));
		return boreHole;
	}

}
