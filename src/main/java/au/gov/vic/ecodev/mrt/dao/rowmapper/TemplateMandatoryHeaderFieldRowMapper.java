package au.gov.vic.ecodev.mrt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;

public class TemplateMandatoryHeaderFieldRowMapper
	implements RowMapper<TemplateMandatoryHeaderField> {

	@Override
	public TemplateMandatoryHeaderField mapRow(ResultSet rs, int rowNum) 
			throws SQLException {
		TemplateMandatoryHeaderField templateMandatoryHeaderField = 
				new TemplateMandatoryHeaderField();
		templateMandatoryHeaderField.setId(rs.getLong("ID"));
		templateMandatoryHeaderField.setSessionId(rs.getLong("LOADER_ID"));
		templateMandatoryHeaderField.setFileName(rs.getString("FILE_NAME"));
		templateMandatoryHeaderField.setTemplateName(rs.getString("TEMPLATE_NAME"));
		templateMandatoryHeaderField.setColumnHeader(rs.getString("COLUMN_HEADER"));
		templateMandatoryHeaderField.setRowNumber(rs.getString("ROW_NUMBER"));
		templateMandatoryHeaderField.setFieldValue(rs.getString("FIELD_VALUE"));
		return templateMandatoryHeaderField;
	}

}
