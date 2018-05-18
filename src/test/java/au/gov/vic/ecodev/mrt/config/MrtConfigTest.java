package au.gov.vic.ecodev.mrt.config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MrtConfigTest {

	@Autowired
	private MrtConfig config;

	@Test
	public void shouldAutowiredConfig() {
		assertThat(config, is(notNullValue()));
	}

	@Test
	public void shouldReturnConfigProperties() {
		// Given
		// When
		MrtConfigProperties properties = config.mrtConfigProperties();
		// Then
		assertThat(properties, is(notNullValue()));
		assertThat(properties.getDirectoryToScan(), is(equalTo("src/test/resources/zip")));
	}

	@Test
	public void shouldReturnStateMachineContext() {
		// Given
		// When
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext =
				config.templateLoaderStateMachineContext();
		// Then
		assertThat(templateLoaderStateMachineContext, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnJdbcTemplate() {
		assertThat(config.getJdbcTemplate(), is(notNullValue()));
	}
	
	@Test
	public void shouldReturnPersistentServices() {
		assertThat(config.persistentServices(), is(notNullValue()));
	}
	
	@Test
	public void shouldReturnConfigredTemplateClasses() {
		//Given
		JdbcTemplate jdbcTemplate = config.getJdbcTemplate();
		//When
		//Then
		jdbcTemplate.query("select class_names from template_config where template_name = ?", 
				new Object[] {"mrt"}, new ResultSetExtractor<List<String>>() {

					@Override
					public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<String> className = new ArrayList<String>();
						while (rs.next()) {
							String cls = rs.getString("class_names");
							System.out.println(cls);
							className.add(cls);
						}
						return className;
					}
			
		});
	}
}
