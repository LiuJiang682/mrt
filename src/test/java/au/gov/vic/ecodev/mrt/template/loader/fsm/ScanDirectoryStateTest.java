package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
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
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.files.DirectoryFilesScanner;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ScanDirectoryState.class)
public class ScanDirectoryStateTest {

	private static final String TEST_DIR = "src/test/resources/testData";

	@Test
	public void shouldScanDirectory() throws Throwable {
		//Given
		File zipFile = TestFixture.getZipFile("src/test/resources/testData/my.zip");
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContent =  Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getDirectoryToScan()).thenReturn(TEST_DIR);
		when(mockTemplateLoaderStateMachineContent.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties );
		ScanDirectoryState testInstance = new ScanDirectoryState();
		//When
		testInstance.on(mockTemplateLoaderStateMachineContent);
		//Then
		ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
		verify(mockTemplateLoaderStateMachineContent).setMessage(messageCaptor.capture());
		Message message = messageCaptor.getValue();
		List<?> fileNames = message.getFileList();
		assertThat(CollectionUtils.isEmpty(fileNames), is(false));
		List<String> absoluteFileNames = message.getAbsoluteFileNames();
		assertThat(CollectionUtils.isEmpty(absoluteFileNames), is(false));
		Files.delete(zipFile);
	}
	
	@Test
	public void shouldScanDirectoryWithAbnormalExist() throws Exception {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContent =  Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getDirectoryToScan()).thenReturn(TEST_DIR);
		when(mockTemplateLoaderStateMachineContent.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		DirectoryFilesScanner mockDirectoryFilesScanner = Mockito.mock(DirectoryFilesScanner.class);
		when(mockDirectoryFilesScanner.scan()).thenThrow(new IOException("Test"));
		PowerMockito.whenNew(DirectoryFilesScanner.class).withArguments(TEST_DIR).thenReturn(mockDirectoryFilesScanner);
		ScanDirectoryState testInstance = new ScanDirectoryState();
		//When
		testInstance.on(mockTemplateLoaderStateMachineContent);
		//Then
		ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
		verify(mockTemplateLoaderStateMachineContent).setMessage(messageCaptor.capture());
		Message capturedMessage = messageCaptor.getValue();
		assertThat(capturedMessage.getDirectErrorMessage(), is(equalTo("Test")));
		verify(mockTemplateLoaderStateMachineContent).setNextStepToGenerateStatusLogState();
	}
	
	@Test
	public void shouldScanDirectoryWithAbnormalExistWhenNoFile() throws Exception {
		//Given
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContent =  Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getDirectoryToScan()).thenReturn(TEST_DIR);
		when(mockTemplateLoaderStateMachineContent.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		DirectoryFilesScanner mockDirectoryFilesScanner = Mockito.mock(DirectoryFilesScanner.class);
		when(mockDirectoryFilesScanner.scan()).thenReturn(new ArrayList<File>());
		PowerMockito.whenNew(DirectoryFilesScanner.class).withArguments(TEST_DIR).thenReturn(mockDirectoryFilesScanner);
		ScanDirectoryState testInstance = new ScanDirectoryState();
		//When
		testInstance.on(mockTemplateLoaderStateMachineContent);
		//Then
		verify(mockTemplateLoaderStateMachineContent, times(0)).setMessage(Matchers.any());
		verify(mockTemplateLoaderStateMachineContent).setNextStepToEnd();
	}
}
