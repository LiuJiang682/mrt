package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

public class ExtractTemplateNameStateTest {
	
	private ExtractTemplateNameState extractTemplateState;

	@Test
	public void shouldExtractTemplateName() {
		//Given
		givenTestInstance();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("mrt_eco.zip");
		message.setFileNames(fileNames );
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockPersistentServices.getNextBatchId(Arrays.asList("mrt"), Arrays.asList("mrt_eco.zip"))).thenReturn(1L);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistentServices);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getPassedFileDirectory()).thenReturn("passedDir");
		when(mockMrtConfigProperties.getFailedFileDirectory()).thenReturn("failedDir");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		//When
		extractTemplateState.on(mockTemplateLoaderStateMachineContext);
		//Then
		List<String> templateName = message.getFileNames();
		assertThat(templateName.get(0), is(equalTo("mrt")));
		assertThat(message.getBatchId(), is(equalTo(1L)));
		assertThat(message.getPassedFileDirectory(), is(equalTo("passedDir")));
		assertThat(message.getFailedFileDirectory(), is(equalTo("failedDir")));
	}
	
	@Test
	public void shouldExtractTemplateNameButFailedToSave() {
		//Given
		givenTestInstance();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		List<String> fileNames = new ArrayList<>();
		fileNames.add("mrt_eco.zip");
		message.setFileNames(fileNames );
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockPersistentServices.getNextBatchId(Arrays.asList("mrt"), Arrays.asList("mrt_eco.zip"))).thenReturn(-1L);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistentServices);
		//When
		extractTemplateState.on(mockTemplateLoaderStateMachineContext);
		//Then
		List<String> templateName = message.getFileNames();
		assertThat(templateName.get(0), is(equalTo("mrt")));
		assertThat(message.getBatchId(), is(equalTo(0L)));
		assertThat(message.getDirectErrorMessage(), is(equalTo("Unable to create session!")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToNotifyUser();
	}

	
	@Test
	public void shouldNotExtractTemplateNameWhenFileNameIsEmpty() {
		givenTestInstance();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		//When
		extractTemplateState.on(mockTemplateLoaderStateMachineContext);
		//Then
		verify(mockTemplateLoaderStateMachineContext, times(0)).setMessage(Matchers.any());
		verify(mockTemplateLoaderStateMachineContext).setNextStepToEnd();
	}
	
	@Test
	public void shouldNotExtractTemplateNameWhenFileNameIsIncorrect() {
		givenTestInstance();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		message.setFileNames(Arrays.asList("MyJava.java"));
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistentServices);
		//When
		extractTemplateState.on(mockTemplateLoaderStateMachineContext);
		//Then
		assertThat(message.getDirectErrorMessage(), is(equalTo("No template name found in file: MyJava.java")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToNotifyUser();
	}
	
	private void givenTestInstance() {
		extractTemplateState = new ExtractTemplateNameState();
	}
	
}
