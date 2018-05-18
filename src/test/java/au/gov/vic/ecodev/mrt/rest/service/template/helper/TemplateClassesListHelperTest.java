package au.gov.vic.ecodev.mrt.rest.service.template.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.gov.vic.ecodev.mrt.dao.TemplateConfigDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateClassesListHelperTest {

	@Autowired
	private TemplateClassesListHelper testInstance;
	
	@Test
	public void shouldReturnMrtClassesList() {
		//Given
		String template = "MRT";
		//When
		List<String> classesList = testInstance.getTemplateClassesList(template);
		//Then
		assertThat(classesList, is(notNullValue()));
		assertThat(classesList.size(), is(equalTo(5)));
		assertThat(classesList.get(0), is(equalTo("SL4")));
		assertThat(classesList.get(1), is(equalTo("DS4")));
		assertThat(classesList.get(2), is(equalTo("DL4")));
		assertThat(classesList.get(3), is(equalTo("DG4")));
		assertThat(classesList.get(4), is(equalTo("SG4")));
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
		TemplateConfigDao templateConfigDao = null;
		//When
		new TemplateClassesListHelper(templateConfigDao);
		fail("Program reached unexpected point!");
	}
}
