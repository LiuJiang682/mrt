package au.gov.vic.ecodev.mrt.template.loader.fsm.helpers;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.jdbc.core.JdbcTemplate;

import au.gov.vic.ecodev.mrt.dao.SessionHeaderDaoImpl;
import au.gov.vic.ecodev.mrt.model.SessionHeader;
import au.gov.vic.ecodev.mrt.model.fields.SessionStatus;
import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TemplateLoaderStateMachineContextFinalStepHelper.class)
public class TemplateLoaderStateMachineContextFinalStepHelperTest {

	private TemplateLoaderStateMachineContextFinalStepHelper testInstance;
	private TemplateLoaderStateMachineContext mockTemplateLoaderStateMachineContext;

	@Test
	public void shouldPerformDoFinalCleanUp() throws Exception {
		// Given
		mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);
		testInstance = PowerMockito.mock(TemplateLoaderStateMachineContextFinalStepHelper.class);
		Whitebox.setInternalState(testInstance, "templateLoaderStateMachineContext", mockTemplateLoaderStateMachineContext);
		PowerMockito.doNothing().when(testInstance).doSessionStatusUpdate();
		PowerMockito.doCallRealMethod().when(testInstance, "doFinalCleanUp");
		// When
		testInstance.doFinalCleanUp();
		// Then
		verify(testInstance).doSessionStatusUpdate();
		verify(mockTemplateLoaderStateMachineContext).setMessage(eq(null));
	}
	
	@Test
	public void shouldUpdateTheSessionStatusToCompleted() throws Exception {
		// Given
		givenTestInstance(false);
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		SessionHeader mockSessionHeader = Mockito.mock(SessionHeader.class);
		SessionHeaderDaoImpl mockSessionHeaderDaoImpl = Mockito.mock(SessionHeaderDaoImpl.class);
		PowerMockito.whenNew(SessionHeaderDaoImpl.class).withNoArguments().thenReturn(mockSessionHeaderDaoImpl);
		when(mockSessionHeaderDaoImpl.get(Matchers.anyLong())).thenReturn(mockSessionHeader);
		when(mockTemplateLoaderStateMachineContext.getJdbcTemplate()).thenReturn(mockJdbcTemplate);
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		testInstance.doSessionStatusUpdate();
		// Then
		verify(mockSessionHeaderDaoImpl).get(Matchers.anyLong());
		verify(mockSessionHeaderDaoImpl).updateOrSave(eq(mockSessionHeader));
		verify(mockSessionHeader).setStatus(eq(SessionStatus.COMPLETED.name()));
		verify(mockSessionHeader).setEmailSent(eq("N"));
	}
	
	@Test
	public void shouldUpdateTheSessionStatusToCompletedAndEmailSent() throws Exception {
		// Given
		givenTestInstance(true);
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		SessionHeader mockSessionHeader = Mockito.mock(SessionHeader.class);
		SessionHeaderDaoImpl mockSessionHeaderDaoImpl = Mockito.mock(SessionHeaderDaoImpl.class);
		PowerMockito.whenNew(SessionHeaderDaoImpl.class).withNoArguments().thenReturn(mockSessionHeaderDaoImpl);
		when(mockSessionHeaderDaoImpl.get(Matchers.anyLong())).thenReturn(mockSessionHeader);
		when(mockTemplateLoaderStateMachineContext.getJdbcTemplate()).thenReturn(mockJdbcTemplate);
		Message message = new DefaultMessage();
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		testInstance.doSessionStatusUpdate();
		// Then
		verify(mockSessionHeaderDaoImpl).get(Matchers.anyLong());
		verify(mockSessionHeaderDaoImpl).updateOrSave(eq(mockSessionHeader));
		verify(mockSessionHeader).setStatus(eq(SessionStatus.COMPLETED.name()));
		verify(mockSessionHeader).setEmailSent(eq("Y"));
	}

	@Test
	public void shouldUpdateTheSessionStatusToFail() throws Exception {
		// Given
		givenTestInstance(false);
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		SessionHeader mockSessionHeader = Mockito.mock(SessionHeader.class);
		SessionHeaderDaoImpl mockSessionHeaderDaoImpl = Mockito.mock(SessionHeaderDaoImpl.class);
		PowerMockito.whenNew(SessionHeaderDaoImpl.class).withNoArguments().thenReturn(mockSessionHeaderDaoImpl);
		when(mockSessionHeaderDaoImpl.get(Matchers.anyLong())).thenReturn(mockSessionHeader);
		when(mockTemplateLoaderStateMachineContext.getJdbcTemplate()).thenReturn(mockJdbcTemplate);
		Message message = new DefaultMessage();
		message.setFailedFiles(Arrays.asList(new File("abc")));
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		testInstance.doSessionStatusUpdate();
		// Then
		verify(mockSessionHeaderDaoImpl).get(Matchers.anyLong());
		verify(mockSessionHeaderDaoImpl).updateOrSave(eq(mockSessionHeader));
		verify(mockSessionHeader).setStatus(eq(SessionStatus.FAILED.name()));
		verify(mockSessionHeader).setEmailSent(eq("N"));
	}
	@Test
	public void shouldUpdateTheSessionStatusToFailAndEmailSent() throws Exception {
		// Given
		givenTestInstance(true);
		JdbcTemplate mockJdbcTemplate = Mockito.mock(JdbcTemplate.class);
		SessionHeader mockSessionHeader = Mockito.mock(SessionHeader.class);
		SessionHeaderDaoImpl mockSessionHeaderDaoImpl = Mockito.mock(SessionHeaderDaoImpl.class);
		PowerMockito.whenNew(SessionHeaderDaoImpl.class).withNoArguments().thenReturn(mockSessionHeaderDaoImpl);
		when(mockSessionHeaderDaoImpl.get(Matchers.anyLong())).thenReturn(mockSessionHeader);
		when(mockTemplateLoaderStateMachineContext.getJdbcTemplate()).thenReturn(mockJdbcTemplate);
		Message message = new DefaultMessage();
		message.setFailedFiles(Arrays.asList(new File("abc")));
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		testInstance.doSessionStatusUpdate();
		// Then
		verify(mockSessionHeaderDaoImpl).get(Matchers.anyLong());
		verify(mockSessionHeaderDaoImpl).updateOrSave(eq(mockSessionHeader));
		verify(mockSessionHeader).setStatus(eq(SessionStatus.FAILED.name()));
		verify(mockSessionHeader).setEmailSent(eq("Y"));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance(false);
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenContextIsNull() {
		// Given
		TemplateLoaderStateMachineContext context = null;
		// When
		new TemplateLoaderStateMachineContextFinalStepHelper(context, false);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(boolean emailSent) {
		mockTemplateLoaderStateMachineContext = Mockito.mock(TemplateLoaderStateMachineContext.class);

		testInstance = new TemplateLoaderStateMachineContextFinalStepHelper(mockTemplateLoaderStateMachineContext, emailSent);
	}
}
