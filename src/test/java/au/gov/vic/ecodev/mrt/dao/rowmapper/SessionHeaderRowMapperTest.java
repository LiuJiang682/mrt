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
import java.sql.Timestamp;

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
		when(mockResultSet.getString("FILE_NAME")).thenReturn("FILE_NAME");
		Date date = new Date(System.currentTimeMillis());
		when(mockResultSet.getDate(eq("PROCESS_DATE"))).thenReturn(date);
		when(mockResultSet.getString(eq("TENEMENT"))).thenReturn("TENEMENT");
		when(mockResultSet.getString(eq("TENEMENT_HOLDER"))).thenReturn("TENEMENT_HOLDER");
		when(mockResultSet.getDate(eq("REPORTING_DATE"))).thenReturn(date);
		when(mockResultSet.getString(eq("PROJECT_NAME"))).thenReturn("PROJECT_NAME");
		when(mockResultSet.getString(eq("STATUS"))).thenReturn("RUNNING");
		when(mockResultSet.getString(eq("COMMENTS"))).thenReturn("COMMENTS");
		when(mockResultSet.getString(eq("EMAIL_SENT"))).thenReturn("N");
		//This is mock test, in the real life approved and rejected must be mutual exclusive
		when(mockResultSet.getInt(eq("APPROVED"))).thenReturn(1);
		when(mockResultSet.getInt(eq("REJECTED"))).thenReturn(1);
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		when(mockResultSet.getTimestamp("CREATED")).thenReturn(currentTimeStamp);
		//When
		SessionHeader sessionHeader = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(sessionHeader, is(notNullValue()));
		assertThat(sessionHeader.getSessionId(), is(equalTo(1l)));
		assertThat(sessionHeader.getTemplate(), is(equalTo("MRT")));
		assertThat(sessionHeader.getFileName(), is(equalTo("FILE_NAME")));
		assertThat(sessionHeader.getProcessDate(), is(equalTo(date)));
		assertThat(sessionHeader.getTenement(),  is(equalTo("TENEMENT")));
		assertThat(sessionHeader.getTenementHolder(), is(equalTo("TENEMENT_HOLDER")));
		assertThat(sessionHeader.getReportingDate(), is(equalTo(date)));
		assertThat(sessionHeader.getProjectName(), is(equalTo("PROJECT_NAME")));
		assertThat(sessionHeader.getStatus().name(), is(equalTo("RUNNING")));
		assertThat(sessionHeader.getComments(), is(equalTo("COMMENTS")));
		assertThat(sessionHeader.getEmailSent(), is(equalTo("N")));
		assertThat(sessionHeader.getApproved(), is(equalTo(1)));
		assertThat(sessionHeader.getRejected(), is(equalTo(1)));
		assertThat(sessionHeader.getCreated(), is(equalTo(currentTimeStamp)));
	}
}
