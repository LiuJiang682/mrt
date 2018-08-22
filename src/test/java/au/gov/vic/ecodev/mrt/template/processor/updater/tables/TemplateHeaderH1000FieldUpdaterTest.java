package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dg4.Dg4Template;

public class TemplateHeaderH1000FieldUpdaterTest {

	private TemplateHeaderH1000FieldUpdater testInstance;
	private TemplateOptionalFieldDao mockTemplateOptionalFieldDao;
	
	@Test
	public void shouldUpdateDatabase() {
		//Given
		givenTestInstance();
		//When
		testInstance.update();
		//Then
		ArgumentCaptor<Entity> entityCaptor = ArgumentCaptor.forClass(Entity.class);
		verify(mockTemplateOptionalFieldDao).updateOrSave(entityCaptor.capture());
		Entity entity = entityCaptor.getValue();
		assertThat(entity, is(notNullValue()));
		assertThat(entity, is(instanceOf(TemplateOptionalField.class)));
		TemplateOptionalField templateOptionalField = (TemplateOptionalField)entity;
		assertThat(templateOptionalField.getSessionId(), is(equalTo(1l)));
		assertThat(templateOptionalField.getFileName(), is(equalTo("myTest.txt")));
		assertThat(templateOptionalField.getTemplateName(), is(equalTo(Strings.TEMPLATE_NAME_DG4)));
		assertThat(templateOptionalField.getTemplateHeader(), is(equalTo(Strings.KEY_H1000)));
		assertThat(templateOptionalField.getRowNumber(), is(equalTo(Strings.ZERO)));
		assertThat(templateOptionalField.getFieldValue(), 
				is(equalTo(String.join(",", TestFixture.getDg4ColumnHeaderList()))));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenHeadersIsNull() {
		//Given
		Template template = null;
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = null;
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, template, mockTemplateOptionalFieldDao, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaosIsNull() {
		//Given
		Template template = getTestTemplate();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = null;
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, template, mockTemplateOptionalFieldDao, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateNameIsNull() {
		//Given
		Template template = getTestTemplate();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, template, mockTemplateOptionalFieldDao, Arrays.asList(1),null);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		Template template = getTestTemplate();
		mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		long sessionId = 1l;
		List<Integer> mandatoryIndexList = Arrays.asList(0, 1, 2, 3);
		testInstance = 
				new TemplateHeaderH1000FieldUpdater(sessionId, template, mockTemplateOptionalFieldDao, 
						mandatoryIndexList, "DG4");
	}
	
	private Template getTestTemplate() {
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		Template template = new Dg4Template();
		template.put(Strings.KEY_H1000, headers);
		template.put(Strings.CURRENT_FILE_NAME, Arrays.asList("myTest.txt"));
		return template;
	}
}
