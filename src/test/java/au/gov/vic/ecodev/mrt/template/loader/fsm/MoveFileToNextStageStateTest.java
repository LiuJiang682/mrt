package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.files.FileMover;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MoveFileToNextStageState.class)
public class MoveFileToNextStageStateTest {

	private static final String FAILED_DIRECTORY = "src/test/resources/failed";
	private static final String PASSED_DIRECTORY = "src/test/resources/passed";

	@Test
	public void shouldMoveFileToDirectory() throws Exception {
		//Given
		TestFixture.deleteFilesInDirectory(PASSED_DIRECTORY);
		TestFixture.deleteFilesInDirectory(FAILED_DIRECTORY);
		MoveFileToNextStageState testInstance = new MoveFileToNextStageState();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getPassedFileDirectory()).thenReturn(PASSED_DIRECTORY);
		when(mockMrtConfigProperties.getFailedFileDirectory()).thenReturn(FAILED_DIRECTORY);
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		Message mockMessage = Mockito.mock(Message.class);
		when(mockMessage.getPassedFiles()).thenReturn(TestFixture.getDummyFileList("src/test/resources/Dummy.zip"));
		when(mockMessage.getFailedFiles()).thenReturn(TestFixture.getDummyFileList("src/test/resources/Dummy1.zip"));
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(mockMessage);
		//When
		testInstance.on(mockTemplateLoaderStateMachineContext);
		//Then
		File passFileDirectory = 
				new File(mockTemplateLoaderStateMachineContext.getMrtConfigProperties().getPassedFileDirectory());
		assertThat(passFileDirectory.list().length > Numeral.ZERO, is(true));
		File failedFileDirectory = 
				new File(mockTemplateLoaderStateMachineContext.getMrtConfigProperties().getFailedFileDirectory());
		assertThat(failedFileDirectory.list().length > Numeral.ZERO, is(true));
	}
	
	@Test
	public void shouldGenerateDirectErrorMessageWhenOnly2Of3FileMoved() throws Exception {
		//Given
		MoveFileToNextStageState testInstance = new MoveFileToNextStageState();
		File[] fileArray = {new File("abc"), new File("def"), new File("ghi")};
		List<File> files = Arrays.asList(fileArray);
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		File[] movedFilesArray = {new File("abc"), new File("def")};
		FileMover mockFileMover = Mockito.mock(FileMover.class);
		when(mockFileMover.moveFile(eq(PASSED_DIRECTORY), eq(true))).thenReturn(Arrays.asList(movedFilesArray));
		PowerMockito.whenNew(FileMover.class).withArguments(eq(files)).thenReturn(mockFileMover);
		//When
		testInstance.moveFile(files, PASSED_DIRECTORY, true, mockTemplateLoaderStateMachineContext);
		//Then
		assertThat(message.getDirectErrorMessage(), is(equalTo("Failed move file to: src/test/resources/passed, expected move 3 but only moved: C:\\Data\\eclipse-workspace\\mrt\\abc, C:\\Data\\eclipse-workspace\\mrt\\def")));
	}
	
	@Test
	public void shouldNoGenerateDirectErrorMessageWhenAll3FileMoved() throws Exception {
		//Given
		MoveFileToNextStageState testInstance = new MoveFileToNextStageState();
		File[] fileArray = {new File("abc"), new File("def"), new File("ghi")};
		List<File> files = Arrays.asList(fileArray);
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = 
				Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		File[] movedFilesArray = {new File("abc"), new File("def"), new File("ghi")};
		FileMover mockFileMover = Mockito.mock(FileMover.class);
		when(mockFileMover.moveFile(eq(PASSED_DIRECTORY), eq(true))).thenReturn(Arrays.asList(movedFilesArray));
		PowerMockito.whenNew(FileMover.class).withArguments(eq(files)).thenReturn(mockFileMover);
		//When
		testInstance.moveFile(files, PASSED_DIRECTORY, true, mockTemplateLoaderStateMachineContext);
		//Then
		assertThat(message.getDirectErrorMessage(), is(nullValue()));
	}
}
