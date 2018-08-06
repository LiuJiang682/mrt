package au.gov.vic.ecodev.mrt.dao.sl4.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;

public class DrillingDetailsRowMapper implements RowMapper<DrillingDetails> {

	private static final String COLUMN_HEADER_FILE_NAME = "FILE_NAME";
	private static final String COLUMN_HEADER_DRILL_DESCRIPTION = "DRILL_DESCRIPTION";
	private static final String COLUMN_HEADER_DRILL_COMPANY = "DRILL_COMPANY";
	private static final String COLUMN_HEADER_DRILL_TYPE = "DRILL_TYPE";
	private static final String COLUMN_HEADER_ID = "ID";

	@Override
	public DrillingDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		DrillingDetails drillingDetails = new DrillingDetails();
		drillingDetails.setId(rs.getLong(COLUMN_HEADER_ID));
		drillingDetails.setFileName(rs.getString(COLUMN_HEADER_FILE_NAME));
		drillingDetails.setDrillType(rs.getString(COLUMN_HEADER_DRILL_TYPE));
		drillingDetails.setDrillCompany(rs.getString(COLUMN_HEADER_DRILL_COMPANY));
		drillingDetails.setDrillDescription(rs.getString(COLUMN_HEADER_DRILL_DESCRIPTION));
		return drillingDetails;
	}

}
