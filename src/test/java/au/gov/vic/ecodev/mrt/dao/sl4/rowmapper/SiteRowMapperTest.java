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

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.model.sl4.Site;

public class SiteRowMapperTest {

	@Test
	public void shouldReturnSiteRecord() throws SQLException {
		//Given
		SiteRowMapper testInstance = new SiteRowMapper();
		ResultSet mockResultSet = Mockito.mock(ResultSet.class);
		when(mockResultSet.getLong(eq("LOADER_ID"))).thenReturn(1l);
		when(mockResultSet.getString(eq("SITE_ID"))).thenReturn("KPDD001");
		when(mockResultSet.getLong(eq("GSV_SITE_ID"))).thenReturn(1l);
		when(mockResultSet.getString(eq("ROW_NUMBER"))).thenReturn("1");
		when(mockResultSet.getString(eq("PARISH"))).thenReturn("N/A");
		when(mockResultSet.getString(eq("PROSPECT"))).thenReturn("Kryptonite");
		when(mockResultSet.getBigDecimal(eq("AMG_ZONE"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getBigDecimal(eq("EASTING"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getBigDecimal(eq("NORTHING"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getBigDecimal(eq("LATITUDE"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getBigDecimal(eq("LONGITUDE"))).thenReturn(BigDecimal.TEN);
		when(mockResultSet.getBigDecimal(eq("LOCN_ACC"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getString(eq("LOCN_DATUM_CD"))).thenReturn("GDA94");
		when(mockResultSet.getBigDecimal(eq("ELEVATION_GL"))).thenReturn(BigDecimal.TEN);
		when(mockResultSet.getBigDecimal(eq("ELEV_ACC"))).thenReturn(BigDecimal.ZERO);
		when(mockResultSet.getString(eq("ELEV_DATUM_CD"))).thenReturn("AHD");
		when(mockResultSet.getInt(eq("NUM_DATA_RECORDS"))).thenReturn(-1);
		when(mockResultSet.getInt(eq("ISSUE_COLUMN_INDEX"))).thenReturn(-1);
		when(mockResultSet.getString(eq("FILE_NAME"))).thenReturn("myTest.txt");
		//When
		Site site = testInstance.mapRow(mockResultSet, 1);
		//Then
		assertThat(site, is(notNullValue()));
		assertThat(site.getLoaderId(), is(equalTo(1l)));
		assertThat(site.getSiteId(), is(equalTo("KPDD001")));
		assertThat(site.getGsvSiteId(), is(equalTo(1l)));
		assertThat(site.getRowNumber(), is(equalTo("1")));
		assertThat(site.getParish(), is(equalTo("N/A")));
		assertThat(site.getProspect(), is(equalTo("Kryptonite")));
		assertThat(site.getAmgZone(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getEasting(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getNorthing(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLatitude(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLongitude(), is(equalTo(BigDecimal.TEN)));
		assertThat(site.getLocnAcc(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getLocnDatumCd(), is(equalTo("GDA94")));
		assertThat(site.getElevationGl(), is(equalTo(BigDecimal.TEN)));
		assertThat(site.getElevAcc(), is(equalTo(BigDecimal.ZERO)));
		assertThat(site.getElevDatumCd(), is(equalTo("AHD")));
		assertThat(site.getNumberOfDataRecord(), is(equalTo(-1)));
		assertThat(site.getFileName(), is(equalTo("myTest.txt")));
		assertThat(site.getIssueColumnIndex(), is(equalTo(-1)));
	}
}
