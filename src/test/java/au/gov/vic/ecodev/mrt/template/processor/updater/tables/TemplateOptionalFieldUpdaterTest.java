package au.gov.vic.ecodev.mrt.template.processor.updater.tables;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.dao.TemplateOptionalFieldDao;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.model.TemplateOptionalField;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;

public class TemplateOptionalFieldUpdaterTest {

	private TemplateOptionalFieldUpdater testInstance;
	private TemplateOptionalFieldDao mockTemplateOptionalFieldDao;
	private Template mockTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void shouldUpdateTheDataBaseWithOptionalFields() throws TemplateProcessorException {
		//Given
		givenTestCondition();
		when(mockTemplateOptionalFieldDao.batchUpdate(Matchers.anyList())).thenReturn(true);
		List<String> dataRecordList = TestFixture.getDListWithOptionalFields();
		//When
		testInstance.update(dataRecordList, 6);
		boolean flag = testInstance.flush();
		//Then
		assertThat(flag, is(true));
		ArgumentCaptor<List> entitiesCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockTemplateOptionalFieldDao).batchUpdate(entitiesCaptor.capture());
		List<List> entityList = entitiesCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(entityList), is(false));
		assertThat(entityList.size(), is(equalTo(1)));
		List<Entity> entities = entityList.get(0);
		TemplateOptionalField templateOptionalField1 = (TemplateOptionalField) entities.get(0);
		assertThat(templateOptionalField1.getSessionId(), is(equalTo(100l)));
		assertThat(templateOptionalField1.getFileName(), is(equalTo("myTest.txt")));
		assertThat(templateOptionalField1.getTemplateName(), is(equalTo("SL4")));
		assertThat(templateOptionalField1.getTemplateHeader(), is(equalTo("Zn")));
		assertThat(templateOptionalField1.getRowNumber(), is(equalTo("6")));
		assertThat(templateOptionalField1.getFieldValue(), is(equalTo("0.01")));
		TemplateOptionalField templateOptionalField2 = (TemplateOptionalField) entities.get(1);
		assertThat(templateOptionalField2.getSessionId(), is(equalTo(100l)));
		assertThat(templateOptionalField2.getFileName(), is(equalTo("myTest.txt")));
		assertThat(templateOptionalField2.getTemplateName(), is(equalTo("SL4")));
		assertThat(templateOptionalField2.getTemplateHeader(), is(equalTo("Au")));
		assertThat(templateOptionalField2.getRowNumber(), is(equalTo("6")));
		assertThat(templateOptionalField2.getFieldValue(), is(equalTo("DDU")));
	}
	
	@Test
	public void shouldInitializedIndexListWhenInitMethodCalled() throws TemplateProcessorException {
		//Given
		givenTestCondition();
		//When
		//Then
		List<Integer> indexList = testInstance.getIndexList();
		assertThat(indexList, is(notNullValue()));
		assertThat(indexList.size(), is(equalTo(2)));
		assertThat(indexList.get(0), is(equalTo(3)));
		assertThat(indexList.get(1), is(equalTo(9)));
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
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		//Given
		long sessionId = 100l;
		mockTemplate = null;
		mockTemplateOptionalFieldDao = null;
		//When
		new TemplateOptionalFieldUpdater(sessionId, mockTemplate, 
				mockTemplateOptionalFieldDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDaoIsNull() {
		//Given
		long sessionId = 100l;
		mockTemplate = Mockito.mock(Template.class);
		mockTemplateOptionalFieldDao = null;
		//When
		new TemplateOptionalFieldUpdater(sessionId, mockTemplate, 
				mockTemplateOptionalFieldDao);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenNoHeaderProvided() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		//When
		testInstance.init(mandatoryFieldIndexList);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		long sessionId = 100l;
		mockTemplate = Mockito.mock(Sl4Template.class);
		mockTemplateOptionalFieldDao = Mockito.mock(TemplateOptionalFieldDao.class);
		
		testInstance = new TemplateOptionalFieldUpdater(sessionId, mockTemplate, 
				mockTemplateOptionalFieldDao);
	}
	
	private void givenTestCondition() throws TemplateProcessorException {
		givenTestInstance();
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderListWithOptionalFields());
		when(mockTemplate.get(eq("H1000"))).thenReturn(headers);
		when(mockTemplate.get(eq(Strings.CURRENT_FILE_NAME))).thenReturn(Arrays.asList("myTest.txt"));
		List<Integer> mandatoryFieldIndexList = new ArrayList<>();
		for (int i = 0; i <= 8; i++) {
			if (3 != i) {
				mandatoryFieldIndexList.add(i);
			}
		}
		
		testInstance.init(mandatoryFieldIndexList);
	}
}
