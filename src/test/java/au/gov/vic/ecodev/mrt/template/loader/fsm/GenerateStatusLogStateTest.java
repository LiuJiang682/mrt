package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.dao.StatusLogDao;
import au.gov.vic.ecodev.mrt.template.files.LogFileGenerator;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.services.PersistentServices;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GenerateStatusLogState.class)
public class GenerateStatusLogStateTest {

	@Test
	public void shouldGenerateStatusLog() {
		// Given
		GenerateStatusLogState testInstance = new GenerateStatusLogState();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		message.setBatchId(1);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistentServices);
		List<String> msgs = new ArrayList<>();
		msgs.add("Line number 6 : H0106 must be a number!");
		when(mockPersistentServices.getErrorMessageByBatchId(Matchers.anyLong(), 
				Matchers.any(LogSeverity.class))).thenReturn(msgs);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getLogFileOutputDirectory()).thenReturn("src/test/resources/generated/log");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		// When
		testInstance.on(mockTemplateLoaderStateMachineContext);
		// Then
		File logFile = new File("src/test/resources/generated/log/Batch_1.log");
		assertThat(logFile.exists(), is(true));
		File file = new File("src/test/resources/generated/log/Batch_1.log");
		file.delete();
	}

	@Test
	public void shouldReturnTrueAbnormalExistWhenGenerateLogFailed() throws Exception {
		// Given
		GenerateStatusLogState testInstance = new GenerateStatusLogState();
		TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		Message message = new DefaultMessage();
		message.setBatchId(1);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		PersistentServices mockPersistentServices = Mockito.mock(PersistentServices.class);
		when(mockTemplateLoaderStateMachineContext.getPersistentServcies()).thenReturn(mockPersistentServices);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getLogFileOutputDirectory()).thenReturn("src/test/resources/generated/log");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		LogFileGenerator mockLogFileGenerator = Mockito.mock(LogFileGenerator.class);
		when(mockLogFileGenerator.generateLogs()).thenReturn("Failed");
		PowerMockito.whenNew(LogFileGenerator.class).withArguments(Matchers.anyLong(), 
				Matchers.any(StatusLogDao.class), Matchers.anyString()).thenReturn(mockLogFileGenerator);
		// When
		testInstance.on(mockTemplateLoaderStateMachineContext);
		// Then
		assertThat(message.getDirectErrorMessage(), is(equalTo("Failed")));
		verify(mockTemplateLoaderStateMachineContext).setNextStepToNotifyUser();
	}
}
