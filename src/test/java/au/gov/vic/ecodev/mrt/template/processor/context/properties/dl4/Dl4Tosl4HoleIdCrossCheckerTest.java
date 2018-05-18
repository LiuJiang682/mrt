package au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class Dl4Tosl4HoleIdCrossCheckerTest {

	private TemplateProcessorContext mockContext;
	private Dl4ToSl4HoleIdCrossChecker testInstance;
	
	@Test
	public void shouldReturnTrueWhenContextSearchReturnEmptyList() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(SqlCriteria.class))).thenReturn(stringListProperties);
		//When
		boolean flag = testInstance.doHoleIdCrossCheck(1l);
		//Then
		assertThat(flag, is(true));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext).search(criteriaCaptor.capture());
		TemplateCriteria capturedCriteria = criteriaCaptor.getValue();
		assertThat(capturedCriteria.getSearcherClassName(), is(equalTo(LithologyHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriteria.getTemplateName(), is(equalTo("DL4")));
		assertThat(((SqlCriteria)capturedCriteria).getKey(), is(equalTo(1l)));
	}
	
	@Test
	public void shouldReturnFalseWhenContextSearchReturnPopulatedList() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(Arrays.asList("STD001"));
		when(mockContext.search(Matchers.any(SqlCriteria.class))).thenReturn(stringListProperties);
		//When
		boolean flag = testInstance.doHoleIdCrossCheck(1l);
		//Then
		assertThat(flag, is(false));
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockContext).saveStatusLog(severityCaptor.capture(), logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Hole_id: STD001 exist in DL4 but missing in SL4")));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext).search(criteriaCaptor.capture());
		TemplateCriteria capturedCriteria = criteriaCaptor.getValue();
		assertThat(capturedCriteria.getSearcherClassName(), is(equalTo(LithologyHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriteria.getTemplateName(), is(equalTo("DL4")));
		assertThat(((SqlCriteria)capturedCriteria).getKey(), is(equalTo(1l)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//Then
		assertThat(testInstance, is(notNullValue()));
		assertThat(Whitebox.getInternalState(testInstance, "context"), is(equalTo(mockContext)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		//Given
		TemplateProcessorContext context = null;
		//When
		new Dl4ToSl4HoleIdCrossChecker(context);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
	
		testInstance = new Dl4ToSl4HoleIdCrossChecker(mockContext);
	}
}