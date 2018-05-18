package au.gov.vic.ecodev.mrt.dao.rowmapper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.model.SessionHeader;

public class SessionHeaderRowMapperTest {

	@Test
	public void shouldReturnSessionHeader() throws SQLException {
		//Given
		SessionHeaderRowMapper testInstance = new SessionHeaderRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("ID"))).thenReturn(1l);
		when(mockResultSet.getString(eq("TEMPLATE"))).thenReturn("MRT");
		Date date = new Date(System.currentTimeMillis());
		when(mockResultSet.getDate(eq("PROCESS_DATE"))).thenReturn(date);
		when(mockResultSet.getString(eq("TENEMENT"))).thenReturn("TENEMENT");
		when(mockResultSet.getString(eq("TENEMENT_HOLDER"))).thenReturn("TENEMENT_HOLDER");
		when(mockResultSet.getDate(eq("REPORTING_DATE"))).thenReturn(date);
		when(mockResultSet.getString(eq("PROJECT_NAME"))).thenReturn("PROJECT_NAME");
		when(mockResultSet.getString(eq("STATUS"))).thenReturn("RUNNING");
		when(mockResultSet.getString(eq("COMMENTS"))).thenReturn("COMMENTS");
		when(mockResultSet.getString(eq("EMAIL_SENT"))).thenReturn("N");
		//When
		SessionHeader sessionHeader = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(sessionHeader, is(notNullValue()));
		assertThat(sessionHeader.getSessionId(), is(equalTo(1l)));
		assertThat(sessionHeader.getTemplate(), is(equalTo("MRT")));
		assertThat(sessionHeader.getProcessDate(), is(equalTo(date)));
		assertThat(sessionHeader.getTenement(),  is(equalTo("TENEMENT")));
		assertThat(sessionHeader.getTenementHolder(), is(equalTo("TENEMENT_HOLDER")));
		assertThat(sessionHeader.getReportingDate(), is(equalTo(date)));
		assertThat(sessionHeader.getProjectName(), is(equalTo("PROJECT_NAME")));
		assertThat(sessionHeader.getStatus().name(), is(equalTo("RUNNING")));
		assertThat(sessionHeader.getComments(), is(equalTo("COMMENTS")));
		assertThat(sessionHeader.getEmailSent(), is(equalTo("N")));
	}
}
