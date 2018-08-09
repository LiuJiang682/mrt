package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.common.util.IDGenerator;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeoChemistryHoleIdNotInBoreHoleSearcherTest {

	private static final String INSERT_GEOCHEMISTRY_SQL = "INSERT INTO DH_GEOCHEMISTRY(ID, LOADER_ID, HOLE_ID, SAMPLE_ID, FILE_NAME, SAMPLE_FROM, SAMPLE_TO, DRILL_CODE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_BOREHOLE_SQL = "INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, FILE_NAME, ROW_NUMBER, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnTemplatePropertiesWhenDownHoleIdExist() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 102;
		insertGeoChemistry(id, LOADER_ID);
		GeoChemistryHoleIdNotInBoreHoleSearcher testInstance = new GeoChemistryHoleIdNotInBoreHoleSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(false));
		assertThat(values.size(), is(equalTo(1)));
		assertThat(values.get(0), is(equalTo("STD003")));
		deleteGeoChemistry(id);
	}
	
	@Test
	public void shouldReturnEmptyTemplatePropertiesWhenBoreHoleIdExist() {
		//Given
		final long LOADER_ID = 101;
		insertBoreHole(LOADER_ID);
		GeoChemistryHoleIdNotInBoreHoleSearcher testInstance = new GeoChemistryHoleIdNotInBoreHoleSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(true));
		deleteBoreHole(LOADER_ID);
	}
	
	@Test
	public void shouldReturnEmpthyTemplatePropertiesWhenHoleIdExist() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 100;
		insertGeoChemistry(id, LOADER_ID);
		insertBoreHole(LOADER_ID);
		
		GeoChemistryHoleIdNotInBoreHoleSearcher testInstance = new GeoChemistryHoleIdNotInBoreHoleSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(true));
		deleteGeoChemistry(id);
		deleteBoreHole(LOADER_ID);
	}

	private void deleteGeoChemistry(long id) {
		jdbcTemplate.update("DELETE DH_GEOCHEMISTRY WHERE ID = ?", new Object[] {id});
	}

	private void deleteBoreHole(final long LOADER_ID) {
		jdbcTemplate.update("DELETE DH_BOREHOLE WHERE LOADER_ID = ? and HOLE_ID = ?", new Object[] {LOADER_ID, "STD003"});
	}
	
	private void insertBoreHole(final long LOADER_ID) {
		jdbcTemplate.update(INSERT_BOREHOLE_SQL, new Object[] {LOADER_ID,
				"STD003", "myTest.txt", "1", "U", "N/A", 1, new Date(), new Date(), 210, 320, null, "Y", "MTR"});
	}

	private void insertGeoChemistry(long id, final long LOADER_ID) {
		jdbcTemplate.update(INSERT_GEOCHEMISTRY_SQL, new Object[] {id, 
				LOADER_ID, "STD003", "ABC123", "myTest.txt", 0, 1, "DD"});
	}
}
