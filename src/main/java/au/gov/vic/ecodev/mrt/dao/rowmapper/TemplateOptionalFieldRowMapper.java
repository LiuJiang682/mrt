package au.gov.vic.ecodev.mrt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;

public class TemplateOptionalFieldRowMapper implements RowMapper<TemplateOptionalField> {

	@Override
	public TemplateOptionalField mapRow(ResultSet rs, int rowNum) throws SQLException {
		TemplateOptionalField templateOptionalField = new TemplateOptionalField();
		templateOptionalField.setId(rs.getLong("ID"));
		templateOptionalField.setSessionId(rs.getLong("LOADER_ID"));
		templateOptionalField.setTemplateName(rs.getString("TEMPLATE_NAME"));
		templateOptionalField.setTemplateHeader(rs.getString("TEMPLATE_HEADER"));
		templateOptionalField.setRowNumber(rs.getString("ROW_NUMBER"));
		templateOptionalField.setFieldValue(rs.getString("FIELD_VALUE"));
		return templateOptionalField;
	}

}
