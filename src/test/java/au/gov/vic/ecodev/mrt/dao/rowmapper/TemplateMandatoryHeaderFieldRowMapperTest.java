package au.gov.vic.ecodev.mrt.dao.rowmapper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;

public class TemplateMandatoryHeaderFieldRowMapperTest {

	@Test
	public void shouldReturnTemplateOptionalField() throws SQLException {
		//Given
		TemplateMandatoryHeaderFieldRowMapper testInstance = new TemplateMandatoryHeaderFieldRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("ID"))).thenReturn(100l);
		when(mockResultSet.getLong(eq("LOADER_ID"))).thenReturn(110l);
		when(mockResultSet.getString("TEMPLATE_NAME")).thenReturn("SL4");
		when(mockResultSet.getString("ROW_NUMBER")).thenReturn("6");
		when(mockResultSet.getString("COLUMN_HEADER")).thenReturn("COLUMN_HEADER");
		when(mockResultSet.getString("FIELD_VALUE")).thenReturn("FIELD_VALUE");
		when(mockResultSet.getString("FILE_NAME")).thenReturn("FILE_NAME");
		//When
		TemplateMandatoryHeaderField templateMandatoryHeaderField = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(templateMandatoryHeaderField, is(notNullValue()));
		assertThat(templateMandatoryHeaderField.getId(), is(equalTo(100l)));
		assertThat(templateMandatoryHeaderField.getSessionId(), is(equalTo(110l)));
		assertThat(templateMandatoryHeaderField.getTemplateName(), is(equalTo("SL4")));
		assertThat(templateMandatoryHeaderField.getColumnHeader(), is(equalTo("COLUMN_HEADER")));
		assertThat(templateMandatoryHeaderField.getRowNumber(), is(equalTo("6")));
		assertThat(templateMandatoryHeaderField.getFieldValue(), is(equalTo("FIELD_VALUE")));
		assertThat(templateMandatoryHeaderField.getFileName(), is(equalTo("FILE_NAME")));
	}
}
