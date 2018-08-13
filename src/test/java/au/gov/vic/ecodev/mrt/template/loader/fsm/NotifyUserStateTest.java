package au.gov.vic.ecodev.mrt.template.loader.fsm;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.mail.Mailer;
import au.gov.vic.ecodev.mrt.template.loader.fsm.helpers.TemplateLoaderStateMachineContextFinalStepHelper;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NotifyUserState.class)
public class NotifyUserStateTest {

	private static final String TEST_WEB_URL = "http://localhost:8090/";
	private NotifyUserState testInstance;
	private TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext;
	private Mailer mockMailer;
	private MrtConfigProperties mockMrtConfigProperties;
	
	@Rule
	private final EnvironmentVariables environmentVariables = new EnvironmentVariables();
	
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
	
	@Test
	public void shouldReturnEmailBuilderMap() throws Exception {
		//Given
		givenTestInstance();
		List<Map<String, Object>> ownerEmails = new ArrayList<>();
		ownerEmails.add(TestFixture.getEmailProperties());
		//When
		Map<String, Set<String>> emailBuilderMap = testInstance.getEmailBuilderMap(ownerEmails);
		//Then
		assertThat(emailBuilderMap, is(notNullValue()));
		assertThat(emailBuilderMap.size(), is(equalTo(1)));
		Entry<String, Set<String>> entry = emailBuilderMap.entrySet().iterator().next();
		assertThat(entry.getKey(), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
		Set<String> value = entry.getValue();
		assertThat(value.size(), is(equalTo(1)));
		assertThat(value.iterator().next(), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
	}
	
	@Test
	public void shouldReturnEmailBuilderMap1EntryWhenDuplicateProvided() throws Exception {
		//Given
		givenTestInstance();
		List<Map<String, Object>> ownerEmails = new ArrayList<>();
		ownerEmails.add(TestFixture.getEmailProperties());
		ownerEmails.add(TestFixture.getEmailProperties());
		//When
		Map<String, Set<String>> emailBuilderMap = testInstance.getEmailBuilderMap(ownerEmails);
		//Then
		assertThat(emailBuilderMap, is(notNullValue()));
		assertThat(emailBuilderMap.size(), is(equalTo(1)));
		Entry<String, Set<String>> entry = emailBuilderMap.entrySet().iterator().next();
		assertThat(entry.getKey(), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
		Set<String> value = entry.getValue();
		assertThat(value.size(), is(equalTo(1)));
		assertThat(value.iterator().next(), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
	}
	
	@Test
	public void shouldReturnTrueWhenDoEmailDispatchCalledWithCorrectData() throws Exception {
		//Given
		givenTestInstance();
		Map<String, Set<String>> emailBuildersMap = new HashMap<>();
		Set<String> emailSet = new HashSet<>();
		emailSet.add("jiang.liu@ecodev.vic.gov.au");
		emailBuildersMap.put("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder", emailSet);
		//When
		boolean emailSent = testInstance.doEmailDispatch(mockTemplateLoaderStateMachineContext, emailBuildersMap);
		//Then
		assertThat(emailSent, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenMailerThrowsException() throws Exception {
		//Given
		givenTestInstance();
		PowerMockito.doThrow(new RuntimeException()).when(mockMailer).send(Matchers.anyString(), Matchers.anyString(), 
				Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
		Map<String, Set<String>> emailBuildersMap = new HashMap<>();
		Set<String> emailSet = new HashSet<>();
		emailSet.add("jiang.liu@ecodev.vic.gov.au");
		emailBuildersMap.put("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder", emailSet);
		//When
		boolean emailSent = testInstance.doEmailDispatch(mockTemplateLoaderStateMachineContext, emailBuildersMap);
		//Then
		assertThat(emailSent, is(false));
	}
	
	@Test
	public void shouldReturnOwnerEmailPropertiesWhenTemplateProvided() throws Exception {
		//Given
		givenTestInstance();
		Message message = new DefaultMessage();
		List<Map<String, Object>> owenerEmails = new ArrayList<>();
		Map<String, Object> emailMap = new HashMap<>();
		emailMap.put("OWNER_EMAILS", "jiang.liu@ecodev.vic.gov.au");
		emailMap.put("EMAILS_BUILDER", "au.gov.vic.ecodev.mrt.mail.Mailer");
		owenerEmails.add(emailMap);
		message.setTemplateOwnerEmail(owenerEmails);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		//When
		List<Map<String, Object>> emailProperties = testInstance.getTemplateOwnerEmail(mockTemplateLoaderStateMachineContext);
		//Then
		assertThat(emailProperties, is(notNullValue()));
		assertThat(emailProperties.size(), is(equalTo(1)));
		Map<String, Object> retrievedEmailMap = emailProperties.get(0);
		assertThat(retrievedEmailMap.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
		assertThat(retrievedEmailMap.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.Mailer")));
	}
	
	@Test
	public void shouldReturnOwnerEmailPropertiesWhenTemplateIsNotProvided() throws Exception {
		//Given
		givenTestInstance();
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		//When
		List<Map<String, Object>> emailProperties = testInstance.getTemplateOwnerEmail(mockTemplateLoaderStateMachineContext);
		//Then
		assertThat(emailProperties, is(notNullValue()));
		assertThat(emailProperties.size(), is(equalTo(1)));
		Map<String, Object> retrievedEmailMap = emailProperties.get(0);
		assertThat(retrievedEmailMap.get("OWNER_EMAILS"), is(equalTo("jiang.liu@ecodev.vic.gov.au")));
		assertThat(retrievedEmailMap.get("EMAILS_BUILDER"), is(equalTo("au.gov.vic.ecodev.mrt.mail.MrtEmailBodyBuilder")));
	}
	
	@Test
	public void shouldReturnPropertyWebUrlWhenNoEnvVarProvided() throws Exception {
		//Given
		givenTestInstance();
		//When
		String webUrl = testInstance.getWebUrl(mockMrtConfigProperties);
		//Then
		assertThat(webUrl, is(notNullValue()));
		assertThat(webUrl, is(equalTo(TEST_WEB_URL)));
	}
	
	@Test
	public void shouldReturnEnvWebUrlWhenEnvVarProvided() throws Exception {
		//Given
		givenTestInstance();
		final String envUrl = "http://wdaud7210fgy.internal.vic.gov.au:8090/";
		environmentVariables.set(Strings.EMAIL_WEB_URL, envUrl);
		//When
		String webUrl = testInstance.getWebUrl(mockMrtConfigProperties);
		//Then
		assertThat(webUrl, is(notNullValue()));
		assertThat(webUrl, is(equalTo(envUrl)));
	}
	
	private void givenTestInstance() throws Exception {
		testInstance = new NotifyUserState();
		mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		mockMrtConfigProperties = Mockito.mock(MrtConfigProperties.class);
		when(mockMrtConfigProperties.getMailSmtpAuth()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpStartTlsEnable()).thenReturn("true");
		when(mockMrtConfigProperties.getMailSmtpHost()).thenReturn("127.0.0.1");
		when(mockMrtConfigProperties.getMailSmtpPort()).thenReturn("25");
		when(mockMrtConfigProperties.getToEmail()).thenReturn("jiang.liu@ecodev.vic.gov.au");
		when(mockMrtConfigProperties.getEmailUser()).thenReturn("");
		when(mockMrtConfigProperties.getEmailUserPwd()).thenReturn("");
		when(mockMrtConfigProperties.getEmailWebUrl()).thenReturn(TEST_WEB_URL);
		when(mockTemplateLoaderStateMachineContext.getMrtConfigProperties()).thenReturn(mockMrtConfigProperties);
		mockMailer = Mockito.mock(Mailer.class);
		PowerMockito.whenNew(Mailer.class).withArguments(mockMrtConfigProperties).thenReturn(mockMailer);
		Message message = new DefaultMessage();
		message.setBatchId(1);
		List<Map<String, Object>> owenerEmails = new ArrayList<>();
		owenerEmails.add(TestFixture.getEmailProperties());
		message.setTemplateOwnerEmail(owenerEmails);
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
	}
}
