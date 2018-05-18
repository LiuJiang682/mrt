package au.gov.vic.ecodev.mrt.template.processor.file.parser.dl4;

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
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.LithologyDepthFromGtTotalDepthSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.dl4.LithologyHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.dl4.Dl4Template;

public class Dl4TemplateFileParserTest {

	private static final String TEST_DATA_FILE = "src/test/resources/template/MtStavely_201703_03_Dl4.txt";
	
	@Test
	public void shouldBuildTemplateFile() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenDl4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(TemplateCriteria.class))).thenReturn(stringListProperties);
		Dl4TemplateFileParser testInstance = new Dl4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(Matchers.any(LogSeverity.class),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Dl4Template.class)));
		verify(mockContext).getTemplateContextProperty(eq("DL4:MANDATORY.VALIDATE.FIELDS"));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext, times(2)).search(criteriaCaptor.capture());
		List<TemplateCriteria> capturedCriterias = criteriaCaptor.getAllValues();
		assertThat(capturedCriterias.get(0).getSearcherClassName(), is(equalTo(LithologyHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriterias.get(0).getTemplateName(), is(equalTo("DL4")));
		assertThat(((SqlCriteria)capturedCriterias.get(0)).getKey(), is(equalTo(0l)));
		assertThat(capturedCriterias.get(1).getSearcherClassName(), is(equalTo(LithologyDepthFromGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriterias.get(1).getTemplateName(), is(equalTo("DL4")));
		assertThat(((SqlCriteria)capturedCriterias.get(1)).getKey(), is(equalTo(0l)));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		File file = new File("abc");
		TemplateProcessorContext mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		//When
		Dl4TemplateFileParser testInstance = new Dl4TemplateFileParser(file, mockTemplateProcessorContext);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenFileIsNull() {
		// Given
		File file = null;
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		new Dl4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenContextIsNull() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = null;
		// When
		new Dl4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
}
