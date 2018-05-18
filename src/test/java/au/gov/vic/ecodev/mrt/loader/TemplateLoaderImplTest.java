package au.gov.vic.ecodev.mrt.loader;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.loader.TemplateLoaderImpl;
import au.gov.vic.ecodev.mrt.template.loader.fsm.ExtractTemplateNameState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.GenerateStatusLogState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.LoaderState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.MoveFileToNextStageState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.ProcessTemplateFileState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.RetrieveTemplateState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.ScanDirectoryState;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.loader.fsm.UnzipZipFileState;

public class TemplateLoaderImplTest {

	@Test
	public void shouldReturnInstance() {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		// When
		TemplateLoaderImpl templateLoaderImpl = new TemplateLoaderImpl(templateLoaderStateMachineContext);
		// Then
		assertThat(templateLoaderImpl, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenPropertiesIsNull() {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = null;
		// When
		new TemplateLoaderImpl(templateLoaderStateMachineContext);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldExecuteLoadWith2States() throws Exception {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		LoaderState mockState = Mockito.mock(LoaderState.class);
		when(templateLoaderStateMachineContext.getNextState()).thenReturn(mockState, mockState, (LoaderState)null);
		TemplateLoaderImpl templateLoaderImpl = new TemplateLoaderImpl(templateLoaderStateMachineContext);
		// When
		templateLoaderImpl.load();
		//Then
		verify(templateLoaderStateMachineContext, times(3)).getNextState();
		verify(mockState, times(2)).on(eq(templateLoaderStateMachineContext));
	}
	
	@Test
	public void shouldExecuteLoadWith6States() throws Exception {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		LoaderState scanDir = Mockito.mock(ScanDirectoryState.class);
		LoaderState extractTemplateName = Mockito.mock(ExtractTemplateNameState.class);
		LoaderState retrieveTemplate = Mockito.mock(RetrieveTemplateState.class);
		LoaderState unzipZipFile = Mockito.mock(UnzipZipFileState.class);
		LoaderState processTemplate = Mockito.mock(ProcessTemplateFileState.class);
		LoaderState generateStatusLog = Mockito.mock(GenerateStatusLogState.class);
		LoaderState moveFile = Mockito.mock(MoveFileToNextStageState.class);
		when(templateLoaderStateMachineContext.getNextState()).thenReturn(scanDir, extractTemplateName, retrieveTemplate, 
				unzipZipFile, processTemplate, generateStatusLog, moveFile, null);
		TemplateLoaderImpl templateLoaderImpl = new TemplateLoaderImpl(templateLoaderStateMachineContext);
		// When
		templateLoaderImpl.load();
		//Then
		verify(templateLoaderStateMachineContext, times(8)).getNextState();
		verify(scanDir).on(eq(templateLoaderStateMachineContext));
		verify(extractTemplateName).on(eq(templateLoaderStateMachineContext));
		verify(retrieveTemplate).on(eq(templateLoaderStateMachineContext));
		verify(unzipZipFile).on(eq(templateLoaderStateMachineContext));
		verify(processTemplate).on(eq(templateLoaderStateMachineContext));
		verify(generateStatusLog).on(eq(templateLoaderStateMachineContext));
		verify(moveFile).on(eq(templateLoaderStateMachineContext));
	}
	
	@Test
	public void shouldExecuteLoadWith1StatesAndAbNormalExit() throws Exception {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		LoaderState mockState = Mockito.mock(LoaderState.class);
		when(templateLoaderStateMachineContext.getNextState()).thenReturn(mockState , (LoaderState)null);
		TemplateLoaderImpl templateLoaderImpl = new TemplateLoaderImpl(templateLoaderStateMachineContext);
		// When
		templateLoaderImpl.load();
		//Then
		verify(templateLoaderStateMachineContext, times(2)).getNextState();
		verify(mockState).on(eq(templateLoaderStateMachineContext));
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldExecuteLoadWithRaisedException() throws Exception {
		// Given
		TemplateLoaderStateMachineContext templateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		LoaderState mockState = Mockito.mock(LoaderState.class);
		when(templateLoaderStateMachineContext.getNextState()).thenReturn(mockState).thenThrow(new RuntimeException());
		TemplateLoaderImpl templateLoaderImpl = new TemplateLoaderImpl(templateLoaderStateMachineContext);
		// When
		templateLoaderImpl.load();
		fail("Program reached unexpected point!");
	}
}
