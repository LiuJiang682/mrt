package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.dao.TemplateMandatoryHeaderFieldDao;
import au.gov.vic.ecodev.mrt.model.TemplateMandatoryHeaderField;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class TemplateMandatoryHeaderUpdaterTest {

	private TemplateMandatoryHeaderUpdater testInstance;
	private TemplateMandatoryHeaderFieldDao mockDao;
	private Template mockTemplate;
	
	@Test
	public void shouldUpdateTheDatabase() {
		//Given
		givenTestInstance();
		when(mockTemplate.get(eq("H1000"))).thenReturn(Arrays.asList("Hole_id", "MGA_E", "MGA_N"));
		when(mockTemplate.get(eq("H1001"))).thenReturn(Arrays.asList(null, "meters", "meters"));
		//When
		testInstance.update();
		//Then
		ArgumentCaptor<TemplateMandatoryHeaderField> entityCaptor = 
				ArgumentCaptor.forClass(TemplateMandatoryHeaderField.class);
		verify(mockDao, times(2)).updateOrSave(entityCaptor.capture());
		List<TemplateMandatoryHeaderField> retrieved = entityCaptor.getAllValues();
		assertThat(retrieved, is(notNullValue()));
		assertThat(retrieved.size(), is(equalTo(2)));
	}
	
	@Test
	public void shouldReturnTestInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoIsNull() {
		//Given
		mockDao = null;
		//When
		new TemplateMandatoryHeaderUpdater(1, null, null, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMandatoryIndexIsEmpty() {
		//Given
		mockDao = Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		//When
		new TemplateMandatoryHeaderUpdater(1, mockDao, null, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPersistentKeyListIsEmpty() {
		//Given
		mockDao = Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		//When
		new TemplateMandatoryHeaderUpdater(1, mockDao, Arrays.asList(1), null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsEmpty() {
		//Given
		mockDao = Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		//When
		new TemplateMandatoryHeaderUpdater(1, mockDao, Arrays.asList(1), Arrays.asList("H1001"), null);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		mockDao = Mockito.mock(TemplateMandatoryHeaderFieldDao.class);
		mockTemplate = Mockito.mock(Template.class);
		testInstance = new TemplateMandatoryHeaderUpdater(1, mockDao, 
				Arrays.asList(1, 2), 
				Arrays.asList("H1001"), mockTemplate);
	}
}
