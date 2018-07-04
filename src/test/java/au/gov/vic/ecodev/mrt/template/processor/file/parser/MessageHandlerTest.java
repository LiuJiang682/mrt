package au.gov.vic.ecodev.mrt.template.processor.file.parser;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.model.sl4.Sl4Template;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MessageHandler.class)
public class MessageHandlerTest {
	
	private List<String> messages;
	private TemplateProcessorContext mockTemplateProcessorContext;
	private Template dataBean;
	private MessageHandler testInstance;
	private File file;
	private int lineNumber;
	
	@Test
	public void shouldUpdateTheDatabaseWithCorrectSeverityMessages() throws Exception {
		//Given
		givenTestInstance();
		messages.add("Missing file");
		messages.add("WARNING: Use depth instead of Surveyed_depth");
		messages.add("INFO: Both Azimuth_MAG and Azimuth_TRUE presented");
		messages.add("ERROR: Either Azimuth_MAG or Azimuth_TRUE presented");
		DirectoryTreeReverseTraversalZipFileFinder mockZipFileFinder = 
				Mockito.mock(DirectoryTreeReverseTraversalZipFileFinder.class);
		when(mockZipFileFinder.findZipFile()).thenReturn("abc.zip", "def.zip");
		PowerMockito.whenNew(DirectoryTreeReverseTraversalZipFileFinder.class)
			.withArguments(eq(file.getParent())).thenReturn(mockZipFileFinder);
		//When
		Template retrievedTemplate = testInstance.doMessagesHandling();
		//Then
		assertThat(retrievedTemplate, is(nullValue()));
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateProcessorContext, times(4)).saveStatusLog(severityCaptor .capture(), 
				logMessageCaptor.capture());
		List<LogSeverity> severities = severityCaptor.getAllValues();
		assertThat(severities.size(), is(equalTo(4)));
		assertThat(severities.get(0), is(equalTo(LogSeverity.ERROR)));
		assertThat(severities.get(1), is(equalTo(LogSeverity.WARNING)));
		assertThat(severities.get(2), is(equalTo(LogSeverity.INFO)));
		assertThat(severities.get(3), is(equalTo(LogSeverity.ERROR)));
		List<String> messages = logMessageCaptor.getAllValues();
		assertThat(messages.size(), is(equalTo(4)));
		final String expectedString0 = file.getAbsolutePath() + "-line number 6: Missing file";
		assertThat(messages.get(0), is(equalTo(expectedString0)));
		final String expectedString1 = file.getAbsolutePath() + "-line number 6: Use depth instead of Surveyed_depth";
		assertThat(messages.get(1), is(equalTo(expectedString1)));
		final String expectedString2 = file.getAbsolutePath() + "-line number 6: Both Azimuth_MAG and Azimuth_TRUE presented";
		assertThat(messages.get(2), is(equalTo(expectedString2)));
		final String expectedString3 = file.getAbsolutePath() + "-line number 6: Either Azimuth_MAG or Azimuth_TRUE presented";
		assertThat(messages.get(3), is(equalTo(expectedString3)));
		ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateProcessorContext, times(2)).addFailedFiles(fileNameCaptor.capture());
		List<String> fileNames = fileNameCaptor.getAllValues();
		assertThat(fileNames.get(0), is(equalTo("abc.zip")));
		assertThat(fileNames.get(1), is(equalTo("def.zip")));
		verify(mockZipFileFinder, times(2)).findZipFile();
		PowerMockito.verifyNew(DirectoryTreeReverseTraversalZipFileFinder.class, times(2));
		PowerMockito.verifyNoMoreInteractions();
	}
	
	@Test
	public void shouldUpdateTheDatabaseWithNoErrorMessages() throws Exception {
		//Given
		givenTestInstance();
		messages.add("WARNING: Use depth instead of Surveyed_depth");
		messages.add("INFO: Both Azimuth_MAG and Azimuth_TRUE presented");
		//When
		Template retrievedTemplate = testInstance.doMessagesHandling();
		//Then
		assertThat(retrievedTemplate, is(equalTo(dataBean)));
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateProcessorContext, times(2)).saveStatusLog(severityCaptor .capture(), 
				logMessageCaptor.capture());
		List<LogSeverity> severities = severityCaptor.getAllValues();
		assertThat(severities.size(), is(equalTo(2)));
		assertThat(severities.get(0), is(equalTo(LogSeverity.WARNING)));
		assertThat(severities.get(1), is(equalTo(LogSeverity.INFO)));
		List<String> messages = logMessageCaptor.getAllValues();
		assertThat(messages.size(), is(equalTo(2)));
		final String expectedString0 = file.getAbsolutePath() + "-line number 6: Use depth instead of Surveyed_depth";
		assertThat(messages.get(0), is(equalTo(expectedString0)));
		final String expectedString1 = file.getAbsolutePath() + "-line number 6: Both Azimuth_MAG and Azimuth_TRUE presented";
		assertThat(messages.get(1), is(equalTo(expectedString1)));
		verify(mockTemplateProcessorContext, times(0)).addFailedFiles(Matchers.anyString());
		PowerMockito.verifyNoMoreInteractions();
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
		assertThat(Whitebox.getInternalState(testInstance, "messages"), is(equalTo(messages)));
		assertThat(Whitebox.getInternalState(testInstance, "templateProcessorContext"), 
				is(equalTo(mockTemplateProcessorContext)));
		assertThat(Whitebox.getInternalState(testInstance, "template"), is(equalTo(dataBean)));
	}

	private void givenTestInstance() {
		messages = new ArrayList<>();
		mockTemplateProcessorContext = Mockito.mock(TemplateProcessorContext.class);
		dataBean = new Sl4Template();
		file = new File("ABC");
		lineNumber = 6;
		
		testInstance = new MessageHandler(messages, mockTemplateProcessorContext, 
				dataBean, file, lineNumber);
	}
}
