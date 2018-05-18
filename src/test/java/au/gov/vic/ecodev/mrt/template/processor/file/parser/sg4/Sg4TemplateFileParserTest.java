package au.gov.vic.ecodev.mrt.template.processor.file.parser.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sg4.Sg4Template;

public class Sg4TemplateFileParserTest {

	private static final String TEST_DATA_FILE = "src/test/resources/template/EL5478_201702_01_Sg4.txt";

	@Test
	public void shouldBuildTemplateFile() throws Exception {
		// Given
		File file = new File(TEST_DATA_FILE);
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		TestFixture.givenSg4TemplateProperties(mockContext);
		when(mockContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		Sg4TemplateFileParser testInstance = new Sg4TemplateFileParser(file, mockContext);
		// When
		testInstance.parse();
		// Then
		verify(mockContext, times(0)).saveStatusLog(Matchers.eq(LogSeverity.ERROR),
				Matchers.anyString());
		ArgumentCaptor<Template> dataBeanCaptor = ArgumentCaptor.forClass(Template.class);
		verify(mockContext).saveDataBean(dataBeanCaptor.capture());
		Template capturedDataBean = dataBeanCaptor.getValue();
		assertThat(capturedDataBean, is(instanceOf(Sg4Template.class)));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		Sg4TemplateFileParser testInstance = new Sg4TemplateFileParser(file, context);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenFileIsNull() {
		// Given
		File file = null;
		TemplateProcessorContext context = Mockito.mock(TemplateProcessorContext.class);
		// When
		new Sg4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisExceptionWhenContextIsNull() {
		// Given
		File file = new File("abc");
		TemplateProcessorContext context = null;
		// When
		new Sg4TemplateFileParser(file, context);
		fail("Program reached unexpected point!");
	}
}
