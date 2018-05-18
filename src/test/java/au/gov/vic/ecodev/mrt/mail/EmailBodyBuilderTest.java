package au.gov.vic.ecodev.mrt.mail;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class EmailBodyBuilderTest {

	private static final String TEST_DIRECT_ERROR_MESSAGE_EMAIL_BODY = "Hi\n\nThe template file process is failed due to: directErrorMessage";
	private static final String TEST_NORMAL_EMAIL_BODY = "Hi\n\nThe log file for batch: 1 is available at src/test/resources/generated/log\n\nThe successfull processed files at src/test/resources/passed\n\nThe failed processed files at src/test/resources/failed";
	private EmailBodyBuilder testInstance;
	private TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext;
	
	@Test
	public void shouldReturnEmailBodyWithDirectErrorMessage() {
		//Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1l);
		message.setDirectErrorMessage("directErrorMessage");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		//When
		String body = testInstance.build();
		//Then
		assertThat(body, is(equalTo(TEST_DIRECT_ERROR_MESSAGE_EMAIL_BODY)));
	}
	
	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		//When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		// Given
		TemplateLoaderStateMachineContext mcokTemplateLoaderStateMachineContext = null;
		// When
		new EmailBodyBuilder(mcokTemplateLoaderStateMachineContext);
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnEmailBody() {
		// Given
		givenTestInstance();
		MrtConfigProperties mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getPassedFileDirectory()).thenReturn("src/test/resources/passed");
		when(mockMrtConfigProperties.getFailedFileDirectory()).thenReturn("src/test/resources/failed");
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		Message message = new DefaultMessage();
		message.setBatchId(1);
		message.setLogFileName("src/test/resources/generated/log");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		String body = testInstance.build();
		// Then
		assertThat(body, is(equalTo(TEST_NORMAL_EMAIL_BODY)));
	}

	private void givenTestInstance() {
		mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		// When
		testInstance = new EmailBodyBuilder(mockTemplateLoaderStateMachineContext);
	}
}
