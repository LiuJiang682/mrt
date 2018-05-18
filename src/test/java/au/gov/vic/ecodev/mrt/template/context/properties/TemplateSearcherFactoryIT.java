package au.gov.vic.ecodev.mrt.template.context.properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;
import au.gov.vic.ecodev.mrt.template.searcher.TemplateSearcher;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateSearcherFactoryIT {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PersistentServices persistentServices;

	@Test
	public void shouldReturnSearcherObjectWhenGetSearcherCalled() throws TemplateProcessorException {
		// Given
		TemplateSearcherFactory testInstance = new TemplateSearcherFactory(persistentServices, 
				jdbcTemplate);
		TemplateCriteria mockCriteria = 
				new SqlCriteria(DownHoleHoleIdNotInBoreHoleSearcher.class.getName(), 
				"DS4", 1l);
		// When
		TemplateSearcher templateSearcher = testInstance.getSearcher(mockCriteria);
		// Then
		assertThat(templateSearcher, is(notNullValue()));
		assertThat(templateSearcher, is(instanceOf(DownHoleHoleIdNotInBoreHoleSearcher.class)));
		DownHoleHoleIdNotInBoreHoleSearcher searcher = (DownHoleHoleIdNotInBoreHoleSearcher) templateSearcher;
		assertThat(Whitebox.getInternalState(searcher, "jdbcTemplate"), is(equalTo(jdbcTemplate)));
		assertThat(Whitebox.getInternalState(searcher, "key"), is(equalTo(1l)));
	}

	@Test
	public void shouldReturnListOfFieldsWhenGetInitFieldsCalled() {
		// Given
		TemplateSearcherFactory testInstance = new TemplateSearcherFactory(persistentServices, jdbcTemplate);
		// When
		List<String> fields = testInstance.getInitFields("DS4", DownHoleHoleIdNotInBoreHoleSearcher.class.getName());
		// Then
		assertThat(CollectionUtils.isEmpty(fields), is(false));
		assertThat(fields.size(), is(equalTo(2)));
		assertThat(fields.get(0), is(equalTo("jdbcTemplate")));
		assertThat(fields.get(1), is(equalTo("key")));
	}
}
