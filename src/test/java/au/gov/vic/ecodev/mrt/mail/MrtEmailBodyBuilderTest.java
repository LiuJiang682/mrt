package au.gov.vic.ecodev.mrt.mail;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.loader.fsm.TemplateLoaderStateMachineContext;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;

public class MrtEmailBodyBuilderTest {

	private static final String TEST_EMAIL_WITH_BORE_HOLE_AND_SAMPLE = "Hi\n\nThe log file for batch: 1 is available at src/test/resources/generated/log\n\nThe successfull processed files at src/test/resources/passed\n\nThe failed processed files at src/test/resources/failed\n\nThe following bore Hole Ids are outside the tenement: KPDD001\n\nThe following sample Ids are outside the tenement: KPDD001";
	private static final String TEST_EMAIL_WITH_SAMPLE_ID = "Hi\n\nThe log file for batch: 1 is available at src/test/resources/generated/log\n\nThe successfull processed files at src/test/resources/passed\n\nThe failed processed files at src/test/resources/failed\n\nThe following sample Ids are outside the tenement: KPDD001";
	private static final String TEST_EMAIL_WITH_BORE_HOLE_ID = "Hi\n\nThe log file for batch: 1 is available at src/test/resources/generated/log\n\nThe successfull processed files at src/test/resources/passed\n\nThe failed processed files at src/test/resources/failed\n\nThe following bore Hole Ids are outside the tenement: KPDD001";
	private static final String TEST_DIRECT_ERROR_MESSAGE_EMAIL_BODY = "Hi\n\nThe template file process is failed due to: directErrorMessage";
	private static final String TEST_NORMAL_EMAIL_BODY = "Hi\n\nThe log file for batch: 1 is available at src/test/resources/generated/log\n\nThe successfull processed files at src/test/resources/passed\n\nThe failed processed files at src/test/resources/failed";
	
	private MrtEmailBodyBuilder testInstance;
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
		MrtEmailBodyBuilder testInstance = new MrtEmailBodyBuilder();
		testInstance.setTemplateProcessorContext(mcokTemplateLoaderStateMachineContext);
		testInstance.build();
		fail("Program reached unexpected point!");
	}

	@Test
	public void shouldReturnEmailBody() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1);
		message.setLogFileName("src/test/resources/generated/log");
		message.setPassedFileDirectory("src/test/resources/passed");
		message.setFailedFileDirectory("src/test/resources/failed");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		String body = testInstance.build();
		// Then
		assertThat(body, is(equalTo(TEST_NORMAL_EMAIL_BODY)));
	}
	
	@Test
	public void shouldReturnEmailBodyWithBoreHoleIds() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1);
		message.setLogFileName("src/test/resources/generated/log");
		List<String> boreHoleIds = new ArrayList<>();
		boreHoleIds.add("KPDD001");
		message.setBoreHoleIdsOutSideTenement(boreHoleIds);
		message.setPassedFileDirectory("src/test/resources/passed");
		message.setFailedFileDirectory("src/test/resources/failed");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		String body = testInstance.build();
		// Then
		assertThat(body, is(equalTo(TEST_EMAIL_WITH_BORE_HOLE_ID)));
	}
	
	@Test
	public void shouldReturnEmailBodyWithSampleIds() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1);
		message.setLogFileName("src/test/resources/generated/log");
		List<String> boreHoleIds = new ArrayList<>();
		boreHoleIds.add("KPDD001");
		message.setSampleIdsOutSideTenement(boreHoleIds);
		message.setPassedFileDirectory("src/test/resources/passed");
		message.setFailedFileDirectory("src/test/resources/failed");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		String body = testInstance.build();
		// Then
		assertThat(body, is(equalTo(TEST_EMAIL_WITH_SAMPLE_ID)));
	}
	
	@Test
	public void shouldReturnEmailBodyWithBoreHoleIdAndSampleIds() {
		// Given
		givenTestInstance();
		Message message = new DefaultMessage();
		message.setBatchId(1);
		message.setLogFileName("src/test/resources/generated/log");
		List<String> boreHoleIds = new ArrayList<>();
		boreHoleIds.add("KPDD001");
		message.setBoreHoleIdsOutSideTenement(boreHoleIds);
		message.setSampleIdsOutSideTenement(boreHoleIds);
		message.setPassedFileDirectory("src/test/resources/passed");
		message.setFailedFileDirectory("src/test/resources/failed");
		when(mockTemplateLoaderStateMachineContext.getMessage()).thenReturn(message);
		// When
		String body = testInstance.build();
		// Then
		assertThat(body, is(equalTo(TEST_EMAIL_WITH_BORE_HOLE_AND_SAMPLE)));
	}

	private void givenTestInstance() {
		mockTemplateLoaderStateMachineContext = Mockito
				.mock(TemplateLoaderStateMachineContext.class);
		// When
		testInstance = new MrtEmailBodyBuilder();
		testInstance.setTemplateProcessorContext(mockTemplateLoaderStateMachineContext);
	}
	
}
