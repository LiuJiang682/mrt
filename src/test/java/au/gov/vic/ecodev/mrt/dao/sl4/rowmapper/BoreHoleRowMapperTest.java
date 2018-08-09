package au.gov.vic.ecodev.mrt.dao.sl4.rowmapper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.model.sl4.BoreHole;

public class BoreHoleRowMapperTest {

	@Test
	public void shouldReturnBoreHoleRecord() throws SQLException {
		//Given
		BoreHoleRowMapper testInstance = new BoreHoleRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("LOADER_ID"))).thenReturn(1l);
		when(mockResultSet.getString(eq("HOLE_ID"))).thenReturn("KPDD001");
		when(mockResultSet.getString(eq("ROW_NUMBER"))).thenReturn("1");
		when(mockResultSet.getString(eq("BH_AUTHORITY_CD"))).thenReturn("U");
		when(mockResultSet.getString(eq("BH_REGULATION_CD"))).thenReturn("UNK");
		Date date = new Date(System.currentTimeMillis());
		when(mockResultSet.getDate(eq("DRILLING_START_DT"))).thenReturn(date);
		when(mockResultSet.getDate(eq("DRILLING_COMPLETION_DT"))).thenReturn(date);
		when(mockResultSet.getBigDecimal(eq("DEPTH"))).thenReturn(BigDecimal.TEN);
		when(mockResultSet.getBigDecimal(eq("ELEVATION_KB"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getBigDecimal(eq("AZIMUTH_MAG"))).thenReturn(BigDecimal.TEN);
		when(mockResultSet.getString(eq("BH_CONFIDENTIAL_FLG"))).thenReturn("Y");
		when(mockResultSet.getString(eq("FILE_NAME"))).thenReturn("myTest.txt");
		//When
		BoreHole boreHole = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(boreHole, is(notNullValue()));
		assertThat(boreHole.getLoaderId(), is(equalTo(1l)));
		assertThat(boreHole.getHoleId(), is(equalTo("KPDD001")));
		assertThat(boreHole.getRowNumber(), is(equalTo("1")));
		assertThat(boreHole.getBhAuthorityCd(), is(equalTo("U")));
		assertThat(boreHole.getBhRegulationCd(), is(equalTo("UNK")));
		assertThat(boreHole.getDrillingStartDate(), is(equalTo(date)));
		assertThat(boreHole.getDrillingCompletionDate(), is(equalTo(date)));
		assertThat(boreHole.getMdDepth(), is(equalTo(BigDecimal.TEN)));
		assertThat(boreHole.getElevationKb(), is(equalTo(BigDecimal.ZERO)));
		assertThat(boreHole.getAzimuthMag(), is(equalTo(BigDecimal.TEN)));
		assertThat(boreHole.getBhConfidentialFlag(), is(equalTo("Y")));
		assertThat(boreHole.getFileName(), is(equalTo("myTest.txt")));
	}
}
