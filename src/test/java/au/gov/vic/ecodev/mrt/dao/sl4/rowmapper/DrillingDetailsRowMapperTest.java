package au.gov.vic.ecodev.mrt.dao.sl4.rowmapper;

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

import au.gov.vic.ecodev.mrt.model.sl4.DrillingDetails;

public class DrillingDetailsRowMapperTest {

	@Test
	public void shouldReturnDrillingDetailsRecord() throws SQLException {
		//Given
		DrillingDetailsRowMapper testInstance = new DrillingDetailsRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("ID"))).thenReturn(1l);
		when(mockResultSet.getString(eq("DRILL_TYPE"))).thenReturn("DD");
		when(mockResultSet.getString(eq("DRILL_COMPANY"))).thenReturn("Drill Faster Pty Ltd");
		when(mockResultSet.getString(eq("DRILL_DESCRIPTION"))).thenReturn("Diamond drilling");
		//When
		DrillingDetails drillingDetails = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(drillingDetails, is(notNullValue()));
		assertThat(drillingDetails.getId(), is(equalTo(1l)));
		assertThat(drillingDetails.getDrillType(), is(equalTo("DD")));
		assertThat(drillingDetails.getDrillCompany(), is(equalTo("Drill Faster Pty Ltd")));
		assertThat(drillingDetails.getDrillDescription(), is(equalTo("Diamond drilling")));
	}
}
