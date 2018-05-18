package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.template.loader.fsm.LoaderState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.LoadingStep;
import au.gov.vic.ecodev.mrt.template.loader.fsm.ScanDirectoryState;

public class LoadingStepTest {

	@Test
	public void shouldReturnScanDirectoryStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.SCAN_DIR;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(ScanDirectoryState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsExtractTemplateName() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.SCAN_DIR;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.EXTRACT_TEMPLATE_NAME)));
	}
	
	@Test
	public void shouldReturnExtractTemplateNameStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.EXTRACT_TEMPLATE_NAME;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(ExtractTemplateNameState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsRetrieveTemplate() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.EXTRACT_TEMPLATE_NAME;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.RETRIEVE_TEMPLATE)));
	}
	
	@Test
	public void shouldReturnRetieveTemplateStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.RETRIEVE_TEMPLATE;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(RetrieveTemplateState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsExtractZipFile() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.RETRIEVE_TEMPLATE;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.UNZIP_ZIP_FILE)));
	}
	
	@Test
	public void shouldReturnUnzipZipFileStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.UNZIP_ZIP_FILE;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(UnzipZipFileState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsProcessTemplateFile() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.UNZIP_ZIP_FILE;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.PROCESS_TEMPLATE_FILE)));
	}
	
	@Test
	public void shouldReturnProcessTemplateFileStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.PROCESS_TEMPLATE_FILE;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(ProcessTemplateFileState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsGenerateStatusLog() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.PROCESS_TEMPLATE_FILE;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.GENERATE_STATUS_LOG)));
	}
	
	@Test
	public void shouldReturnGenerateStatusLogStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.GENERATE_STATUS_LOG;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(GenerateStatusLogState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsMoveFileToNextStageState() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.GENERATE_STATUS_LOG;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.MOVE_FILE_TO_NEXT_STAGE)));
	}
	
	@Test
	public void shouldReturnMoveFileToNextStageStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.MOVE_FILE_TO_NEXT_STAGE;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(MoveFileToNextStageState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsNotifyUser() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.MOVE_FILE_TO_NEXT_STAGE;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(equalTo(LoadingStep.NOTIFY_USER)));
	}
	
	@Test
	public void shouldReturnNotifyUserStateObject() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.NOTIFY_USER;
		//When
		LoaderState loaderState = loadingStep.getState();
		//Then
		assertThat(loaderState, is(instanceOf(NotifyUserState.class)));
	}
	
	@Test
	public void shouldReturnNextStepAsNull() throws Exception {
		//Given
		LoadingStep loadingStep = LoadingStep.NOTIFY_USER;
		//When
		LoadingStep next = loadingStep.getNextStep();
		//Then
		assertThat(next, is(nullValue()));
	}
}
