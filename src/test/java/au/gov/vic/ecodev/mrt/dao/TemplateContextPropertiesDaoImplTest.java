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
public class TemplateContextPropertiesDaoImplTest {

	@Autowired
	private TemplateContextPropertiesDao templateContextPropertiesDao;
	
	@Test
	public void shouldReturnSl4TemplateClass() {
		// Given
		String template = "Sl4";
		String propertyName = "MANDATORY.VALIDATE.FIELDS";
		// When
		String classes = templateContextPropertiesDao.getTemplateContextProperty(template, propertyName);
		// Then
		assertThat(classes, is(equalTo("H0002,H0005,H0200,H0201,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D")));
	}
	
	@Test
	public void shouldBeInstantiatedDao() {
		// Given
		// When
		// Then
		assertThat(templateContextPropertiesDao, is(notNullValue()));
	}
}
