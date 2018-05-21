package au.gov.vic.ecodev.mrt.template.processor.file.parser.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.files.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Sl4TemplateFileParser.class)
public class Sl4TemplateFileParserTest {

	private static final String TEST_DATA_FILE = "src/test/resources/template/EL5478_201702_01_Collar.txt";

	@Test
	public void shouldReturneInstance() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		Sl4TemplateFileParser testInstance = new Sl4TemplateFileParser(file, context);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenFileIsNull() {
		// Given
		File file = null;
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		new Sl4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenContextIsNull() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = null;
		// When
		new Sl4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldBuildTemplateFile() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenSl4TemplateProperties(mockContext);
		givenMapServices(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		givenMockZipFileFinder();
		Sl4TemplateFileParser testInstance = new Sl4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(eq(LogSeverity.ERROR),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Sl4Template.class)));
	}

	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenSaveTemplateFileFailed() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenSl4TemplateProperties(mockContext);
		givenMapServices(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(false);
		givenMockZipFileFinder();
		Sl4TemplateFileParser testInstance = new Sl4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(Matchers.any(LogSeverity.class),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Sl4Template.class)));
	}

	private void givenMapServices(TemplateProcessorContext mockContext) {
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
	}

	private void givenMockZipFileFinder() throws Exception {
		DirectoryTreeReverseTraversalZipFileFinder mockZipFileFinder = 
				Mockito.mock(DirectoryTreeReverseTraversalZipFileFinder.class);
		when(mockZipFileFinder.findZipFile()).thenReturn("abc.zip");
		PowerMockito.whenNew(DirectoryTreeReverseTraversalZipFileFinder.class)
			.withArguments(Matchers.anyString())
			.thenReturn(mockZipFileFinder);
	}
}
