package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

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
public class GeoChemistryFromGtTotalDepthSearcherTest {
	private static final String INSERT_GEOCHEMISTRY_SQL = "INSERT INTO DH_GEOCHEMISTRY(ID, LOADER_ID, HOLE_ID, SAMPLE_ID, SAMPLE_FROM, SAMPLE_TO, DRILL_CODE) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnHoleIdOfDepthFromGtTotalDepth() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 1;
		jdbcTemplate.update(INSERT_GEOCHEMISTRY_SQL, new Object[] {id, 
				LOADER_ID, "KPDD001", "ABC123", 300, 1, "DD"});
		GeoChemistryFromGtTotalDepthSearcher testInstance = new GeoChemistryFromGtTotalDepthSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(false));
		assertThat(values.size(), is(equalTo(1)));
		assertThat(values.get(0), is(equalTo("KPDD001")));
		jdbcTemplate.update("DELETE DH_GEOCHEMISTRY WHERE ID = ?", new Object[] {id});
	}
	
	@Test
	public void shouldReturnNoHoleIdOfDepthFromGtTotalDepth() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 1;
		jdbcTemplate.update(INSERT_GEOCHEMISTRY_SQL, new Object[] {id, 
				LOADER_ID, "KPDD001", "ABC123", 100, 1, "DD"});
		GeoChemistryFromGtTotalDepthSearcher testInstance = new GeoChemistryFromGtTotalDepthSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(true));
		jdbcTemplate.update("DELETE DH_GEOCHEMISTRY WHERE ID = ?", new Object[] {id});
	}
}
