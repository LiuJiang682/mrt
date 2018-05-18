package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.dao.TemplateDisplayPropertiesDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateDisplayPropertiesHelperTest {

	@Autowired
	private TemplateDisplayPropertiesHelper testInstance;
	
	@Test
	public void shouldReturnMrtDisplayPropertiesMap() throws Exception {
		//Given
		String template = "MRT";
		//When
		Map<String, Object> results = testInstance.getTemplateDisplayProperties(template);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.size(), is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoIsNull() {
		//Given
		TemplateDisplayPropertiesDao templateDisplayPropertiesDao = null;
		//When
		new TemplateDisplayPropertiesHelper(templateDisplayPropertiesDao);
		fail("Program reached unexpected point!");
	}
}
