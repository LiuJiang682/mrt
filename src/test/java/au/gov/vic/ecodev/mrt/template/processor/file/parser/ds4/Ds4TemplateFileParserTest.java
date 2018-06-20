package au.gov.vic.ecodev.mrt.template.processor.file.parser.ds4;

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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.context.properties.SqlCriteria;
import au.gov.vic.ecodev.mrt.template.context.properties.StringListTemplateProperties;
import au.gov.vic.ecodev.mrt.template.criteria.TemplateCriteria;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleHoleIdNotInBoreHoleSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.DownHoleSurveyedDepthGtTotalDepthSearcher;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.Ds4ToSl4HoleIdCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.context.properties.ds4.Ds4ToSl4SurveyedDepthCrossChecker;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.ds4.Ds4Template;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Ds4TemplateFileParser.class)
public class Ds4TemplateFileParserTest {

	private static final String TEST_DATA_FILE = "src/test/resources/template/MtStavely_201703_03_Survey.txt";
	
	@Test
	public void shouldBuildTemplateFile() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenDs4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		StringListTemplateProperties stringListProperties = new StringListTemplateProperties(null);
		when(mockContext.search(Matchers.any(TemplateCriteria.class))).thenReturn(stringListProperties);
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(Matchers.any(LogSeverity.class),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Ds4Template.class)));
		ArgumentCaptor<TemplateCriteria> criteriaCaptor = ArgumentCaptor.forClass(TemplateCriteria.class);
		verify(mockContext, times(2)).search(criteriaCaptor.capture());
		List<TemplateCriteria> capturedCriterias = criteriaCaptor.getAllValues();
		assertThat(capturedCriterias.get(0).getSearcherClassName(), is(equalTo(DownHoleHoleIdNotInBoreHoleSearcher.class.getName())));
		assertThat(capturedCriterias.get(0).getTemplateName(), is(equalTo("DS4")));
		assertThat(((SqlCriteria)capturedCriterias.get(0)).getKey(), is(equalTo(0l)));
		assertThat(capturedCriterias.get(1).getSearcherClassName(), is(equalTo(DownHoleSurveyedDepthGtTotalDepthSearcher.class.getName())));
		assertThat(capturedCriterias.get(1).getTemplateName(), is(equalTo("DS4")));
		assertThat(((SqlCriteria)capturedCriterias.get(1)).getKey(), is(equalTo(0l)));
	}
	
	@Test
	public void shouldReturnTrueWhenBothCheckPassed() throws Exception {
		//Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockContext);
		Ds4ToSl4HoleIdCrossChecker mockDs4ToSl4HoleIdCrossChecker = Mockito.mock(Ds4ToSl4HoleIdCrossChecker.class);
		when(mockDs4ToSl4HoleIdCrossChecker.doHoleIdCrossCheck(Matchers.anyLong())).thenReturn(true);
		PowerMockito.whenNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4HoleIdCrossChecker);
		Ds4ToSl4SurveyedDepthCrossChecker mockDs4ToSl4SurveyedDepthCrossChecker = Mockito.mock(Ds4ToSl4SurveyedDepthCrossChecker.class);
		when(mockDs4ToSl4SurveyedDepthCrossChecker.doSurvyedDepthCrossCheck(Matchers.anyLong())).thenReturn(true);
		PowerMockito.whenNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4SurveyedDepthCrossChecker);
		//When
		boolean flag = testInstance.doDs4ToSl4DataIntegrityCheck();
		assertThat(flag, is(true));
		PowerMockito.verifyNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext));
		PowerMockito.verifyNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext));
	}
	
	@Test
	public void shouldReturnFalseWhenHoleIdCheckFailed() throws Exception {
		//Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockContext);
		Ds4ToSl4HoleIdCrossChecker mockDs4ToSl4HoleIdCrossChecker = Mockito.mock(Ds4ToSl4HoleIdCrossChecker.class);
		when(mockDs4ToSl4HoleIdCrossChecker.doHoleIdCrossCheck(Matchers.anyLong())).thenReturn(false);
		PowerMockito.whenNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4HoleIdCrossChecker);
		Ds4ToSl4SurveyedDepthCrossChecker mockDs4ToSl4SurveyedDepthCrossChecker = Mockito.mock(Ds4ToSl4SurveyedDepthCrossChecker.class);
		when(mockDs4ToSl4SurveyedDepthCrossChecker.doSurvyedDepthCrossCheck(Matchers.anyLong())).thenReturn(true);
		PowerMockito.whenNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4SurveyedDepthCrossChecker);
		//When
		boolean flag = testInstance.doDs4ToSl4DataIntegrityCheck();
		assertThat(flag, is(false));
		PowerMockito.verifyNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext));
		PowerMockito.verifyNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext));
	}
	
	@Test
	public void shouldReturnFalseWhenSurveyedDepthCheckFailed() throws Exception {
		//Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockContext);
		Ds4ToSl4HoleIdCrossChecker mockDs4ToSl4HoleIdCrossChecker = Mockito.mock(Ds4ToSl4HoleIdCrossChecker.class);
		when(mockDs4ToSl4HoleIdCrossChecker.doHoleIdCrossCheck(Matchers.anyLong())).thenReturn(true);
		PowerMockito.whenNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4HoleIdCrossChecker);
		Ds4ToSl4SurveyedDepthCrossChecker mockDs4ToSl4SurveyedDepthCrossChecker = Mockito.mock(Ds4ToSl4SurveyedDepthCrossChecker.class);
		when(mockDs4ToSl4SurveyedDepthCrossChecker.doSurvyedDepthCrossCheck(Matchers.anyLong())).thenReturn(false);
		PowerMockito.whenNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4SurveyedDepthCrossChecker);
		//When
		boolean flag = testInstance.doDs4ToSl4DataIntegrityCheck();
		assertThat(flag, is(false));
		PowerMockito.verifyNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext));
		PowerMockito.verifyNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext));
	}
	
	@Test
	public void shouldReturnFalseWhenBothCheckFailed() throws Exception {
		//Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockContext);
		Ds4ToSl4HoleIdCrossChecker mockDs4ToSl4HoleIdCrossChecker = Mockito.mock(Ds4ToSl4HoleIdCrossChecker.class);
		when(mockDs4ToSl4HoleIdCrossChecker.doHoleIdCrossCheck(Matchers.anyLong())).thenReturn(false);
		PowerMockito.whenNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4HoleIdCrossChecker);
		Ds4ToSl4SurveyedDepthCrossChecker mockDs4ToSl4SurveyedDepthCrossChecker = Mockito.mock(Ds4ToSl4SurveyedDepthCrossChecker.class);
		when(mockDs4ToSl4SurveyedDepthCrossChecker.doSurvyedDepthCrossCheck(Matchers.anyLong())).thenReturn(false);
		PowerMockito.whenNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext)).thenReturn(mockDs4ToSl4SurveyedDepthCrossChecker);
		//When
		boolean flag = testInstance.doDs4ToSl4DataIntegrityCheck();
		assertThat(flag, is(false));
		PowerMockito.verifyNew(Ds4ToSl4HoleIdCrossChecker.class).withArguments(eq(mockContext));
		PowerMockito.verifyNew(Ds4ToSl4SurveyedDepthCrossChecker.class).withArguments(eq(mockContext));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		File file = new File("abc");
		TemplateProcessorContext mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		//When
		Ds4TemplateFileParser testInstance = new Ds4TemplateFileParser(file, mockTemplateProcessorContext);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenFileIsNull() {
		// Given
		File file = null;
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		new Ds4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenContextIsNull() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = null;
		// When
		new Ds4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
}
