package au.gov.vic.ecodev.mrt.template.context.properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

public class TemplateSearcherFactoryTest {

	private PersistentServices mockPersistentServices;
	private JdbcTemplate mockJdbcTemplate;
	private TemplateSearcherFactory testInstance;
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamsAreAllNull() {
		//Given
		PersistentServices persistentServices = null;
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateSearcherFactory(persistentServices, jdbcTemplate);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenJdbcTemplateIsNull() {
		//Given
		PersistentServices persistentServices = Mockito.mock(PersistentServices.class);
		JdbcTemplate jdbcTemplate = null;
		//When
		new TemplateSearcherFactory(persistentServices, jdbcTemplate);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnTheParameterMapWhenCriteriaProvided() throws Exception {
		//Given
		givenTestInstance();
		TemplateCriteria criteria = new SqlCriteria("abc", "DS4",  new Long(123));
		//When
		Map<String, Object> params = testInstance.getAllParameters(mockPersistentServices, mockJdbcTemplate, criteria);
		//Then
		assertThat(params, is(notNullValue()));
		assertThat(params.size(), is(equalTo(5)));
		assertThat(params.get("persistentServices"), is(equalTo(mockPersistentServices)));
		assertThat(params.get("jdbcTemplate"), is(equalTo(mockJdbcTemplate)));
		assertThat(params.get("key"), is(equalTo(123l)));
	}
	
	@Test
	public void shouldInitialTemplateSearcherWhenCriteriaProvided() throws Exception {
		//Given
		givenTestInstance();
		TemplateSearcher searcher = new DownHoleHoleIdNotInBoreHoleSearcher();
		Map<String, Object> params = new HashMap<>();
		params.put("jdbcTemplate", mockJdbcTemplate);
		params.put("key", new Long(123));
		//When
		testInstance.doSearcherInitialise(searcher, Arrays.asList("jdbcTemplate", "key"), params);
		//Then
		assertThat(Whitebox.getInternalState(searcher, "jdbcTemplate"), is(equalTo(mockJdbcTemplate)));
		assertThat(Whitebox.getInternalState(searcher, "key"), is(equalTo(new Long(123))));
	}
	
	private void givenTestInstance() {
		mockPersistentServices = Mockito.mock(PersistentServices.class);
		mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		
		testInstance = new TemplateSearcherFactory(mockPersistentServices,
				mockJdbcTemplate);
	}
}
