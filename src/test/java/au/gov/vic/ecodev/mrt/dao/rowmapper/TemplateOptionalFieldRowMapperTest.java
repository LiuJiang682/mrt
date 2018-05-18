package au.gov.vic.ecodev.mrt.dao.rowmapper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;

public class TemplateOptionalFieldRowMapperTest {

	@Test
	public void shouldReturnTemplateOptionalField() throws SQLException {
		//Given
		TemplateOptionalFieldRowMapper testInstance = new TemplateOptionalFieldRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("ID"))).thenReturn(100l);
		when(mockResultSet.getLong(eq("LOADER_ID"))).thenReturn(110l);
		when(mockResultSet.getString("TEMPLATE_NAME")).thenReturn("SL4");
		when(mockResultSet.getString("TEMPLATE_HEADER")).thenReturn("Au");
		when(mockResultSet.getString("ROW_NUMBER")).thenReturn("6");
		when(mockResultSet.getString("FIELD_VALUE")).thenReturn("FIELD_VALUE");
		//When
		TemplateOptionalField templateOptionalField = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(templateOptionalField, is(notNullValue()));
		assertThat(templateOptionalField.getId(), is(equalTo(100l)));
		assertThat(templateOptionalField.getSessionId(), is(equalTo(110l)));
		assertThat(templateOptionalField.getTemplateName(), is(equalTo("SL4")));
		assertThat(templateOptionalField.getTemplateHeader(), is(equalTo("Au")));
		assertThat(templateOptionalField.getRowNumber(), is(equalTo("6")));
		assertThat(templateOptionalField.getFieldValue(), is(equalTo("FIELD_VALUE")));
	}
}
