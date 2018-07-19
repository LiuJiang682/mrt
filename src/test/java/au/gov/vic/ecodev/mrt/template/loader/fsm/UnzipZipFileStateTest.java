package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.files.FileMover;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.utils.file.zip.extract.ZipFileExtractor;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UnzipZipFileState.class)
public class UnzipZipFileStateTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldExecuteOnMethod() throws IOException {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message mockMessage = Mockito.mock(Message.class);
		List<File> fileList = new ArrayList<>();
		File zipFile = TestFixture.getZipFile("src/test/resources/testData/my.zip");
		fileList.add(zipFile);
		when(mockMessage.getFileList()).thenReturn(fileList);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		MrtConfigProperties mockConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockConfigProperties.getZipFileExtractDestination()).thenReturn("src/test/resources/testData/extract");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockConfigProperties);
		UnzipZipFileState unzipZipFileState = new UnzipZipFileState();
		//When
		unzipZipFileState.on(mockTemplateLoaderStateMachineContext);
		//Then
		ArgumentCaptor<List> fileListCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockMessage).setFileList(fileListCaptor.capture());
		List<File> extractedDirs = fileListCaptor.getValue();
		assertThat(extractedDirs, is(notNullValue()));
		assertThat(extractedDirs.size(), is(equalTo(1)));
		File extractedDir = extractedDirs.get(0);
		assertThat(extractedDir.getName(), is(equalTo("my")));
		String movedZipFile = extractedDir.getAbsolutePath() + ".zip";
		Files.delete(zipFile);
		Files.delete(extractedDir);
		Files.delete(new File(movedZipFile));
	}
	
	@Test
	public void shouldExecuteOnMethodWithAbnormalExit() {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		List<File> fileList = new ArrayList<>();
		fileList.add(new File("abc"));
		message.setFileList(fileList);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		MrtConfigProperties mockConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockConfigProperties );
		UnzipZipFileState unzipZipFileState = new UnzipZipFileState();
		//When
		unzipZipFileState.on(mockTemplateLoaderStateMachineContext);
		//Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(logSeverityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Failed to move files to destination: null")));
	}
	
	@Test
	public void shouldLogErrorWhenExecuteOnMethodWithNoDestination() {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		List<File> fileList = new ArrayList<>();
		fileList.add(new File("abc"));
		message.setFileList(fileList);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		MrtConfigProperties mockConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockConfigProperties );
		UnzipZipFileState unzipZipFileState = new UnzipZipFileState();
		//When
		unzipZipFileState.on(mockTemplateLoaderStateMachineContext);
		//Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(logSeverityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Failed to move files to destination: null")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToGenerateStatusLogState();
	}
	
	@Test
	public void shouldLogErrorWhenExecuteOnMethodWithMoveFailed() throws Exception {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties properties = new MrtConfigProperties();
		Whitebox.setInternalState(properties, "zipFileExtractDestination", "abc");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(properties);
		Message message = new DefaultMessage();
		List<File> fileList = new ArrayList<>();
		File file = new File("abc");
		fileList.add(file);
		message.setFileList(fileList);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		FileMover mockFileMover = Mockito.mock(FileMover.class);
		PowerMockito.whenNew(FileMover.class).withArguments(eq(fileList)).thenReturn(mockFileMover);
		when(mockFileMover.moveFile(eq("abc"))).thenReturn(fileList);
		ZipFileExtractor mockZipFileExtractor = Mockito.mock(ZipFileExtractor.class);
		PowerMockito.whenNew(ZipFileExtractor.class).withArguments(eq(file)).thenReturn(mockZipFileExtractor);
		when(mockZipFileExtractor.extract()).thenThrow(new IOException("Test"));
		UnzipZipFileState unzipZipFileState = new UnzipZipFileState();
		//When
		unzipZipFileState.on(mockTemplateLoaderStateMachineContext);
		//Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(logSeverityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Test")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToGenerateStatusLogState();
	}
	
	@Test
	public void shouldLogErrorWhenExecuteOnMethodWithNullZipFileList() throws Exception {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties properties = new MrtConfigProperties();
		Whitebox.setInternalState(properties, "zipFileExtractDestination", "abc");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(properties);
		Message message = new DefaultMessage();
		message.setFileList(null);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		UnzipZipFileState unzipZipFileState = new UnzipZipFileState();
		//When
		unzipZipFileState.on(mockTemplateLoaderStateMachineContext);
		//Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockTemplateLoaderStateMachineContext).saveStatusLog(logSeverityCaptor.capture(), 
				logMessageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue(), is(equalTo("Empty zip file list!")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToGenerateStatusLogState();
	}
	
}
