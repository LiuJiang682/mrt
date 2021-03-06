package au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4;

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

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class Dg4ToSl4FromCrossCheckerTest {

	private TemplateProcessorContext mockContext;
	private Dg4ToSl4FromCrossChecker testInstance;
	
	@Test
	public void shouldReturnFalseWhenContextSearchReturnPopulatedList() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(Arrays.asList("STD001"));
		when(mockContext.search(Matchers.any(SqlCriteria.class))).thenReturn(stringListProperties);
		//When
		boolean flag = testInstance.doDepthCrossCheck(1l);
		//Then
		assertThat(flag, is(false));
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockContext).saveStatusLog(severityCaptor.capture(), logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Hole_id: STD001 From is greater than total depth!")));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext).search(criteriaCaptor.capture());
		TemplateCriteria capturedCriteria = criteriaCaptor.getValue();
		assertThat(capturedCriteria.getSearcherClassName(), is(equalTo(GeoChemistryFromGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriteria.getTemplateName(), is(equalTo("DG4")));
		assertThat(((SqlCriteria)capturedCriteria).getKey(), is(equalTo(1l)));
	}
	
	@Test
	public void shouldReturnTrueWhenContextSearchReturnEmptyList() throws TemplateProcessorException {
		//Given
		givenTestInstance();
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(SqlCriteria.class))).thenReturn(stringListProperties);
		//When
		boolean flag = testInstance.doDepthCrossCheck(1l);
		//Then
		assertThat(flag, is(true));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext).search(criteriaCaptor.capture());
		TemplateCriteria capturedCriteria = criteriaCaptor.getValue();
		assertThat(capturedCriteria.getSearcherClassName(), is(equalTo(GeoChemistryFromGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriteria.getTemplateName(), is(equalTo("DG4")));
		assertThat(((SqlCriteria)capturedCriteria).getKey(), is(equalTo(1l)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		//Given
		TemplateProcessorContext context = null;
		//When
		new Dg4ToSl4FromCrossChecker(context);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
	
		testInstance = new Dg4ToSl4FromCrossChecker(mockContext);
	}
}
