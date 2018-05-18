package au.gov.vic.ecodev.mrt.template.processor.file.parser.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryFromGtTotalDepthSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dg4.GeoChemistryToGtTotalDepthSearcher;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dg4.Dg4Template;

public class Dg4TemplateFileParserTest {

	private static final String TEST_DATA_FILE = "src/test/resources/template/EL5478_201702_01_Dg4.txt";
	
	@Test
	public void shouldBuildTemplateFile() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenDg4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(TemplateCriteria.class))).thenReturn(stringListProperties);
		Dg4TemplateFileParser testInstance = new Dg4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(eq(LogSeverity.ERROR),
				Matchers.anyString());
		verify(mockContext).saveStatusLog(eq(LogSeverity.WARNING),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Dg4Template.class)));
		verify(mockContext).getTemplateContextProperty(eq("DG4:MANDATORY.VALIDATE.FIELDS"));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext, times(3)).search(criteriaCaptor.capture());
		List<TemplateCriteria> capturedCriterias = criteriaCaptor.getAllValues();
		assertThat(capturedCriterias.get(0).getSearcherClassName(), is(equalTo(GeoChemistryHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriterias.get(0).getTemplateName(), is(equalTo("DG4")));
		assertThat(((SqlCriteria)capturedCriterias.get(0)).getKey(), is(equalTo(0l)));
		assertThat(capturedCriterias.get(1).getSearcherClassName(), is(equalTo(GeoChemistryFromGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriterias.get(1).getTemplateName(), is(equalTo("DG4")));
		assertThat(((SqlCriteria)capturedCriterias.get(1)).getKey(), is(equalTo(0l)));
		assertThat(capturedCriterias.get(2).getSearcherClassName(), is(equalTo(GeoChemistryToGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriterias.get(2).getTemplateName(), is(equalTo("DG4")));
		assertThat(((SqlCriteria)capturedCriterias.get(2)).getKey(), is(equalTo(0l)));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		// When
		Dg4TemplateFileParser testInstance = new Dg4TemplateFileParser(file, mockTemplateProcessorContext);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenFileIsNull() {
		// Given
		File file = null;
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		new Dg4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenContextIsNull() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = null;
		// When
		new Dg4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
}
