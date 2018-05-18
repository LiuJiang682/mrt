package au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4;

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
public class DownHoleHoleIdNotInBoreHoleSearcherTest {

	private static final String INSERT_DOWNHOLE_SQL = "INSERT INTO DH_DOWNHOLE(ID, LOADER_ID, HOLE_ID, SURVEYED_DEPTH, AZIMUTH_MAG, DIP, AZIMUTH_TRUE) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_BOREHOLE_SQL = "INSERT INTO DH_BOREHOLE(LOADER_ID, HOLE_ID, BH_AUTHORITY_CD, BH_REGULATION_CD, DILLING_DETAILS_ID, DRILLING_START_DT, DRILLING_COMPLETION_DT, DEPTH, ELEVATION_KB, AZIMUTH_MAG, BH_CONFIDENTIAL_FLG, DEPTH_UOM) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void shouldReturnTemplatePropertiesWhenDownHoleIdExist() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 102;
		jdbcTemplate.update(INSERT_DOWNHOLE_SQL, new Object[] {id, 
				LOADER_ID, "STD003", 3, 0, 0, null});
		DownHoleHoleIdNotInBoreHoleSearcher testInstance = new DownHoleHoleIdNotInBoreHoleSearcher();
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
		jdbcTemplate.update("DELETE DH_DOWNHOLE WHERE ID = ?", new Object[] {id});
	}
	
	@Test
	public void shouldReturnEmptyTemplatePropertiesWhenBoreHoleIdExist() {
		//Given
		final long LOADER_ID = 101;
		jdbcTemplate.update(INSERT_BOREHOLE_SQL, new Object[] {LOADER_ID,
				"STD003", "U", "N/A", 1, new Date(), new Date(), 210, 320, null, "Y", "MTR"});
		DownHoleHoleIdNotInBoreHoleSearcher testInstance = new DownHoleHoleIdNotInBoreHoleSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(true));
		jdbcTemplate.update("DELETE DH_BOREHOLE WHERE LOADER_ID = ? and HOLE_ID = ?", new Object[] {LOADER_ID, "STD003"});
	}
	
	@Test
	public void shouldReturnEmpthyTemplatePropertiesWhenHoleIdExist() {
		//Given
		long id = IDGenerator.getUID().longValue();
		final long LOADER_ID = 100;
		jdbcTemplate.update(INSERT_DOWNHOLE_SQL, new Object[] {id, 
				LOADER_ID, "STD003", 3, 0, 0, null});
		jdbcTemplate.update(INSERT_BOREHOLE_SQL, new Object[] {LOADER_ID,
				"STD003", "U", "N/A", 1, new Date(), new Date(), 210, 320, null, "Y", "MTR"});
		
		DownHoleHoleIdNotInBoreHoleSearcher testInstance = new DownHoleHoleIdNotInBoreHoleSearcher();
		testInstance.setJdbcTemplate(jdbcTemplate);
		testInstance.setKey(LOADER_ID);
		//When
		TemplateProperties templateProperty = testInstance.search();
		//Then
		assertThat(templateProperty, is(notNullValue()));
		List<String> values = ((StringListTemplateProperties)templateProperty).getValue();
		assertThat(CollectionUtils.isEmpty(values), is(true));
		jdbcTemplate.update("DELETE DH_DOWNHOLE WHERE ID = ?", new Object[] {id});
		jdbcTemplate.update("DELETE DH_BOREHOLE WHERE LOADER_ID = ? and HOLE_ID = ?", new Object[] {LOADER_ID, "STD003"});
	}
}
