package au.gov.vic.ecodev.mrt.dao;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateUpdaterConfigDaoImplTest {

	@Autowired
	private TemplateUpdaterConfigDao templateUpdaterConfigDao;
	
	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(templateUpdaterConfigDao, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnSl4TemplateClass() {
		// Given
		String template = "Sl4Template";
		// When
		String classes = templateUpdaterConfigDao.getTemplateUpdaterClass(template);
		// Then
		assertThat(classes, is(equalTo("au.gov.vic.ecodev.mrt.template.processor.updater.sl4.Sl4TemplateUpdater")));
	}
}
