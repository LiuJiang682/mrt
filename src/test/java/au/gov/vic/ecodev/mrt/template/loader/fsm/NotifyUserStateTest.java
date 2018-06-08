package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.mail.Mailer;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateLoaderStateMachineContextFinalStepHelper;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NotifyUserState.class)
public class NotifyUserStateTest {

	private NotifyUserState testInstance;
	private TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext;
	private Mailer mockMailer;
	
	@Test
	public void shouldSendUserEmail() throws Exception {
		// Given
		givenTestInstance();
		TemplateLoaderStateMachineContextFinalStepHelper mockTemplateLoaderStateMachineContextFinalStepHelper = 
				Mockito.mock(TemplateLoaderStateMachineContextFinalStepHelper.class);
		PowerMockito.whenNew(TemplateLoaderStateMachineContextFinalStepHelper.class)
			.withArguments(eq(mockTemplateLoaderStateMachineContext), eq(true))
			.thenReturn(mockTemplateLoaderStateMachineContextFinalStepHelper);
		// When
		testInstance.on(mockTemplateLoaderStateMachineContext);
		// Then
		verify(mockMailer).send(Matchers.anyString(), 
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
		PowerMockito.verifyNew(TemplateLoaderStateMachineContextFinalStepHelper.class);
	}
	
	@Test
	public void shouldExistAbnormal() throws Exception {
		//Given
		givenTestInstance();
		PowerMockito.doThrow(new RuntimeException()).when(mockMailer)
			.send(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), 
				Matchers.anyString(), Matchers.anyString());
		TemplateLoaderStateMachineContextFinalStepHelper mockTemplateLoaderStateMachineContextFinalStepHelper = 
				Mockito.mock(TemplateLoaderStateMachineContextFinalStepHelper.class);
		PowerMockito.whenNew(TemplateLoaderStateMachineContextFinalStepHelper.class)
			.withArguments(eq(mockTemplateLoaderStateMachineContext), eq(false))
			.thenReturn(mockTemplateLoaderStateMachineContextFinalStepHelper);
		//When
		testInstance.on(mockTemplateLoaderStateMachineContext);
		//Then
		verify(mockMailer).send(Matchers.anyString(), 
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
	}
	
	private void givenTestInstance() throws Exception {
		testInstance = new NotifyUserState();
		mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getMailSmtpAuth()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpStartTlsEnable()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpHost()).thenReturn("127.0.0.1");
		when(mockMrtConfigProperties.getMailSmtpPort()).thenReturn("25");
		when(mockMrtConfigProperties.getToEmail()).thenReturn("jiang.liu@ecodev.vic.gov.au");
		when(mockMrtConfigProperties.getEmailUser()).thenReturn("");
		when(mockMrtConfigProperties.getEmailUserPwd()).thenReturn("");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		mockMailer = Mockito.mock(Mailer.class);
		PowerMockito.whenNew(Mailer.class).withArguments(mockMrtConfigProperties).thenReturn(mockMailer);
		Message message = new DefaultMessage();
		message.setBatchId(1);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
	}
}
