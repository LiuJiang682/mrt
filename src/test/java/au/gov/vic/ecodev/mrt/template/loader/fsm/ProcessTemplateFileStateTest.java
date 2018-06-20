package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.context.properties.DefaultStringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.files.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.mrt.template.files.TemplateFileSelector;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessor;
import au.gov.vic.ecodev.mrt.template.processor.TemplateProcessorFactory;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.processor.file.parser.sl4.Sl4TemplateFileParser;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.mrt.template.processor.services.DbPersistentServices;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TemplateProcessorFactory.class, ProcessTemplateFileState.class, Sl4TemplateFileParser.class})
public class ProcessTemplateFileStateTest {

	private static final String NOT_TEMPLATE_FILE = "File: C:\\Data\\eclipse-workspace\\mrt\\src\\test\\resources\\template is not a SL4 template file.";

	private static final String NO_TEMPLATE_PROCESSOR_CLASS = "No template processor class name provided from au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor when processing C:\\Data\\eclipse-workspace\\mrt\\src\\test\\resources\\template";

	private static final String NO_TEMPLATE_PROCESSOR = "No template processor object created for au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor when processing C:\\Data\\eclipse-workspace\\mrt\\src\\test\\resources\\template";

	private static final String TEST_FILE_DIRECTORY = "src/test/resources/template";

	private ProcessTemplateFileState processTemplateFileState;
	private TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext;

	@Test
	public void shouldProcessTemplate() throws Exception {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1L);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies())
			.thenReturn(mockPersistentServices);
		givenTemplateContextProperty();
		givenMapServices(mockTemplateLoaderStateMachineContext);
		givenMockZipFileFinder();
		when(mockTemplateLoaderStateMachineContext.saveDataBean(Matchers.any(Template.class)))
			.thenReturn(true);
		TestFixture.givenSl4TemplateProperties(mockTemplateLoaderStateMachineContext);
		// TODO -- Use all templates
		// String className = "SL4,DS4,DL4,DG4";
		String className = "SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor";
		File file = new File(TEST_FILE_DIRECTORY);
		// When
		processTemplateFileState.processingTemplate(className, file, mockTemplateLoaderStateMachineContext);
		// Then
		verify(mockTemplateLoaderStateMachineContext, times(0)).saveStatusLog(eq(LogSeverity.ERROR), 
				Matchers.anyString());
		verify(mockTemplateLoaderStateMachineContext, times(0)).addFailedFiles(Matchers.anyString());
		verify(mockTemplateLoaderStateMachineContext).addPassedFiles(Matchers.anyString());
	}	
	
	@Test
	public void shouldLogExceptionAndSetFailedFileWhenProcessRaiseException() throws Exception {
		// Given
		givenTestInstance();

		Message mockMessage = Mockito.mock(Message.class);
		// TODO -- Use all templates
		// when(mockMessage.getTemplateClasses()).thenReturn(Arrays.asList("SL4,DS4,DL4,DG4"));
		when(mockMessage.getTemplateClasses())
				.thenReturn(Arrays.asList("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		when(mockMessage.getFileList()).thenReturn(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		PowerMockito.mockStatic(TemplateProcessorFactory.class);
		TemplateProcessor mockProcessor = Mockito.mock(TemplateProcessor.class);
		PowerMockito.doThrow(new TemplateProcessorException("Failed test")).when(mockProcessor).processFile();
		PowerMockito.doReturn(mockProcessor).when(TemplateProcessorFactory.class, "getProcessor", Matchers.anyString());
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Failed test")));
		ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).addFailedFiles(fileCaptor.capture());
		List<String> fileNames = fileCaptor.getAllValues();
		assertThat(fileNames.size(), is(equalTo(1)));
		String fileName = fileNames.get(0).replaceAll("\\\\", "/");
		assertThat(fileName.endsWith(TEST_FILE_DIRECTORY), is(true));
	}
	
	@Test
	public void shouldLogExceptionAndSetFailedFileWhenNoTemplateFound() throws Exception {
		// Given
		givenTestInstance();

		Message mockMessage = new DefaultMessage();
		mockMessage.setTemplateClasses(Arrays.asList("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		mockMessage.setFileList(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		PowerMockito.mockStatic(TemplateProcessorFactory.class);
		TemplateProcessor mockProcessor = Mockito.mock(TemplateProcessor.class);
		PowerMockito.doReturn(mockProcessor).when(TemplateProcessorFactory.class, "getProcessor", Matchers.anyString());
		TemplateFileSelector mockTemplateFileSelector = Mockito.mock(TemplateFileSelector.class);
		PowerMockito.whenNew(TemplateFileSelector.class).withArguments(Matchers.anyString())
			.thenReturn(mockTemplateFileSelector);
		when(mockTemplateFileSelector.getTemplateFileInDirectory(Matchers.anyListOf(String.class)))
			.thenReturn(Optional.empty());
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo(NOT_TEMPLATE_FILE)));
		ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).addFailedFiles(fileCaptor.capture());
		List<String> fileNames = fileCaptor.getAllValues();
		assertThat(fileNames.size(), is(equalTo(1)));
		String fileName = fileNames.get(0).replaceAll("\\\\", "/");
		assertThat(fileName.endsWith(TEST_FILE_DIRECTORY), is(true));
	}
	
	@Test
	public void shouldLogExceptionAndSetFailedFileWhenProcessorIsNull() throws Exception {
		// Given
		givenTestInstance();

		Message message = new DefaultMessage();
		message.setTemplateClasses(Arrays
				.asList("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		message.setFileList(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PowerMockito.mockStatic(TemplateProcessorFactory.class);
		PowerMockito.doReturn(null).when(TemplateProcessorFactory.class, "getProcessor", Matchers.anyString());
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo(NO_TEMPLATE_PROCESSOR)));
		ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).addFailedFiles(fileCaptor.capture());
		List<String> fileNames = fileCaptor.getAllValues();
		assertThat(fileNames.size(), is(equalTo(1)));
		String fileName = fileNames.get(0).replaceAll("\\\\", "/");
		assertThat(fileName.endsWith(TEST_FILE_DIRECTORY), is(true));
	}
	
	@Test
	public void shouldLogExceptionAndSetFailedFileWhenTemplateMetaDataInvalid() throws Exception {
		// Given
		givenTestInstance();

		Message message = new DefaultMessage();
		message.setTemplateClasses(Arrays.asList("au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		message.setFileList(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo(NO_TEMPLATE_PROCESSOR_CLASS)));
		ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).addFailedFiles(fileCaptor.capture());
		List<String> fileNames = fileCaptor.getAllValues();
		assertThat(fileNames.size(), is(equalTo(1)));
		String fileName = fileNames.get(0).replaceAll("\\\\", "/");
		assertThat(fileName.endsWith(TEST_FILE_DIRECTORY), is(true));
	}
	
	@Test
	public void shouldLogExceptionAndSetFailedFilesWhenProcessRaiseException() throws Exception {
		// Given
		givenTestInstance();

		Message mockMessage = new DefaultMessage();
		mockMessage.setTemplateClasses(Arrays
				.asList("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor", "abc"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		File extractedDir1 = new File(TEST_FILE_DIRECTORY + "1");
		extractedDirs.add(extractedDir1);
		mockMessage.setFileList(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		PowerMockito.mockStatic(TemplateProcessorFactory.class);
		TemplateProcessor mockProcessor = Mockito.mock(TemplateProcessor.class);
		PowerMockito.doThrow(new TemplateProcessorException("Failed test")).when(mockProcessor).processFile();
		PowerMockito.doReturn(mockProcessor).when(TemplateProcessorFactory.class, "getProcessor", Matchers.anyString());
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Failed test")));
		ArgumentCaptor<String> fileCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext, times(2)).addFailedFiles(fileCaptor.capture());
		List<String> fileNames = fileCaptor.getAllValues();
		assertThat(fileNames.size(), is(equalTo(2)));
		String fileName1 = fileNames.get(0).replaceAll("\\\\", "/");
		assertThat(fileName1.endsWith(TEST_FILE_DIRECTORY), is(true));
		String fileName2 = fileNames.get(1).replaceAll("\\\\", "/");
		assertThat(fileName2.endsWith(TEST_FILE_DIRECTORY + "1"), is(true));
	}

	@Test
	public void shouldExecuteOnMethod() throws Exception {
		// Given
		givenTestInstance();
		Message mockMessage = new DefaultMessage();
		mockMessage.setTemplateClasses(Arrays
				.asList("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor"));
		List<File> extractedDirs = new ArrayList<>();
		File extractedDir = new File(TEST_FILE_DIRECTORY);
		extractedDirs.add(extractedDir);
		mockMessage.setFileList(extractedDirs);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		givenTemplateContextProperty();
		when(mockTemplateLoaderStateMachineContext.saveDataBean(Matchers.any(Template.class))).thenReturn(true);
		DbPersistentServices mockPersistenServices = Mockito.mock(DbPersistentServices.class);
		when(mockPersistenServices.saveDataBean(Matchers.any(JdbcTemplate.class), Matchers.anyLong(),
				Matchers.any(Template.class))).thenCallRealMethod();
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistenServices);
		TestFixture.givenSl4TemplateProperties(mockTemplateLoaderStateMachineContext);
		givenMapServices(mockTemplateLoaderStateMachineContext);
		givenMockZipFileFinder();
		// When
		processTemplateFileState.on(mockTemplateLoaderStateMachineContext);
		// Then
		verify(mockTemplateLoaderStateMachineContext, times(0)).saveStatusLog(eq(LogSeverity.ERROR), 
				Matchers.anyString());
		verify(mockTemplateLoaderStateMachineContext, times(0)).addFailedFiles(Matchers.anyString());
		verify(mockTemplateLoaderStateMachineContext).addPassedFiles(Matchers.anyString());
	}

	@Test
	public void shouldNotProcessTemplateWhenClassNameIsNotProvided() throws Exception {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		String className = "";
		File file = new File(TEST_FILE_DIRECTORY);
		// When
		processTemplateFileState.processingTemplate(className, file, mockTemplateLoaderStateMachineContext);
		// Then
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(severityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("No template processor class name provided from  when processing C:\\Data\\eclipse-workspace\\mrt\\src\\test\\resources\\template")));
	}

	@Test(expected = ClassNotFoundException.class)
	public void shouldNotRaiseExceptionWhenClassNameIsIncorrect() throws Exception {
		// Given
		givenTestInstance();
		String className = "SL4:asb";
		File file = new File(TEST_FILE_DIRECTORY);
		// When
		processTemplateFileState.processingTemplate(className, file, mockTemplateLoaderStateMachineContext);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		processTemplateFileState = new ProcessTemplateFileState();
		mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
	}
	
	private void givenTemplateContextProperty() throws TemplateProcessorException {
		when(mockTemplateLoaderStateMachineContext.getTemplateContextProperty(eq("sl4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(TestFixture.getSl4TemplateProperties());
		when(mockTemplateLoaderStateMachineContext.getTemplateContextProperty(eq("sl4:AZIMUTHMAG.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
		when(mockTemplateLoaderStateMachineContext.getTemplateContextProperty(eq("sl4:DIP.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
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
