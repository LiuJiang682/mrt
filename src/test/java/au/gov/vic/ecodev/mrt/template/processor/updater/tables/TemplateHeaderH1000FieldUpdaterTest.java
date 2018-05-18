package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

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
		assertThat(templateOptionalField.getTemplateName(), is(equalTo(Strings.TEMPLATE_NAME_DG4)));
		assertThat(templateOptionalField.getTemplateHeader(), is(equalTo(Strings.KEY_H1000)));
		assertThat(templateOptionalField.getRowNumber(), is(equalTo(Strings.KEY_H1000)));
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
		List<String> headers = null;
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = null;
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, headers, mockTemplateOptionalFieldDao, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaosIsNull() {
		//Given
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = null;
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, headers, mockTemplateOptionalFieldDao, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateNameIsNull() {
		//Given
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		TemplateOptionalFieldDao mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		long sessionId = 1l;
		//When
		new TemplateHeaderH1000FieldUpdater(sessionId, headers, mockTemplateOptionalFieldDao, null);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		List<String> headers = TestFixture.getDg4ColumnHeaderList();
		mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		long sessionId = 1l;
		
		testInstance = 
				new TemplateHeaderH1000FieldUpdater(sessionId, headers, mockTemplateOptionalFieldDao, "DG4");
	}
}