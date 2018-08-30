package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
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
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.config.MrtConfig;
import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RetrieveTemplateStateTest {

	@Autowired
	private MrtConfig config;
	
	private TemplateLoaderStateMachineContext templateLoaderStateMachineContext;
	private RetrieveTemplateState retrieveTemplateState;
	
	@Test
	public void shouldExecuteOn() {
		//Given
		givenTestInstance();
		givenTestMessage();
		//When
		retrieveTemplateState.on(templateLoaderStateMachineContext);
		//Then
		List<String> templateClasses = templateLoaderStateMachineContext.getMessage().getTemplateClasses();
		assertThat(templateClasses, is(notNullValue()));
		assertThat(templateClasses.size(), is(equalTo(1)));
//		assertThat(templateClasses.get(0), is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4,DL4,DG4")));
//		assertThat(templateClasses.get(0), is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor")));
//		assertThat(templateClasses.get(0), is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor")));
		assertThat(templateClasses.get(0), is(equalTo("SL4:au.gov.vic.ecodev.mrt.template.processor.sl4.Sl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector:mandatory,DS4:au.gov.vic.ecodev.mrt.template.processor.ds4.Ds4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DL4:au.gov.vic.ecodev.mrt.template.processor.dl4.Dl4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,DG4:au.gov.vic.ecodev.mrt.template.processor.dg4.Dg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector,SG4:au.gov.vic.ecodev.mrt.template.processor.sg4.Sg4TemplateProcessor:au.gov.vic.ecodev.mrt.template.files.H0202HeaderTemplateFileSelector")));
	}

	
	@Test
	public void shouldExitWhenDaoReturnNullString() {
		//Given
		givenTestInstance();
		givenTestMessage();
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockPersistentServices.getTemplateClasses(Matchers.anyString())).thenReturn(null);
		Whitebox.setInternalState(templateLoaderStateMachineContext, "persistentServices", mockPersistentServices);
		//When
		retrieveTemplateState.on(templateLoaderStateMachineContext);
		//Then
		List<String> templateClasses = templateLoaderStateMachineContext.getMessage().getTemplateClasses();
		assertThat(templateClasses, is(notNullValue()));
		ArgumentCaptor<Long> batchIdCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockPersistentServices).saveStatusLog(batchIdCaptor.capture(), 
				severityCaptor.capture(),
				logMessageCaptor.capture());
		assertThat(batchIdCaptor.getValue(), is(equalTo(1L)));
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue().startsWith("Not template class found for file:"), is(true));
		assertThat(templateLoaderStateMachineContext.getMessage().getFileList().size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldAddFailedFileWhenHandleTemplateNotFoundCalled() {
		//Given
		givenTestInstance();
		givenTestMessage();
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockPersistentServices.getTemplateClasses(Matchers.anyString())).thenReturn(null);
		Whitebox.setInternalState(templateLoaderStateMachineContext, "persistentServices", mockPersistentServices);
		//When
		retrieveTemplateState.handleTemplateNotFound(templateLoaderStateMachineContext, 0);
		//Then
		List<File> failedFiles = templateLoaderStateMachineContext.getMessage().getFailedFiles();
		assertThat(CollectionUtils.isEmpty(failedFiles), is(false));
		ArgumentCaptor<Long> batchIdCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<LogSeverity> severityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockPersistentServices).saveStatusLog(batchIdCaptor.capture(), 
				severityCaptor.capture(),
				logMessageCaptor.capture());
		assertThat(batchIdCaptor.getValue(), is(equalTo(1L)));
		assertThat(severityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		assertThat(logMessageCaptor.getValue().startsWith("Not template class found for file:"), is(true));
		assertThat(templateLoaderStateMachineContext.getMessage().getFileList().size(), is(equalTo(0)));
	}
	
	private void givenTestInstance() {
		templateLoaderStateMachineContext = config.templateLoaderStateMachineContext();
		retrieveTemplateState = new RetrieveTemplateState();
	}
	
	private void givenTestMessage() {
		Message message = TestFixture.getMessageWithListOfFileNames();
		List<File> files = new ArrayList<File>();
		files.add(new File("mrt_eco.zip"));
		message.setFileList(files);
//		message.setFileList(Arrays.asList(new File("mrt_eco.zip")));
		message.setBatchId(1L);
		Whitebox.setInternalState(templateLoaderStateMachineContext, "message", message);
	}
}
