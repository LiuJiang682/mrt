package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.constants.LogSeverity;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;
import au.gov.vic.ecodev.utils.file.finder.DirectoryTreeReverseTraversalZipFileFinder;
import au.gov.vic.ecodev.utils.file.helper.MessageHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H0203H0534H0535DataIntegrityValidator.class, MessageHandler.class })
public class H0203H0534H0535DataIntegrityValidatorTest {

	private H0203H0534H0535DataIntegrityValidator testInstance;
	private Template mockDataBean;
	private Map<String, List<String>> templateParamMap;
	private TemplateProcessorContext context;
	private File file;

	@Test
	public void shouldReturnEmptyMessageWhenStringArrayIsEof() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		templateParamMap.put(Strings.KEY_H0534,
				Arrays.asList("Downhole_Direction_Survey_Instrument", "Single shot camera - SS"));
		templateParamMap.put(Strings.KEY_H0535,
				Arrays.asList("Downhole_Direction_Survey_Company", "Small Time Mining NL"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		verify(context, times(0)).saveStatusLog(Matchers.any(LogSeverity.class), Matchers.anyString());
		assertThat(dataBean, is(notNullValue()));
	}

	@Test
	public void shouldReturnNoActualRecordMessageWhenStringArrayIsEofAndNoActualRecordParam() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.KEY_H0534,
				Arrays.asList("Downhole_Direction_Survey_Instrument", "Single shot camera - SS"));
		templateParamMap.put(Strings.KEY_H0535,
				Arrays.asList("Downhole_Direction_Survey_Company", "Small Time Mining NL"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(message.contains("No Number_of_records_added in the templateParamMap"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnMissingH0534MessageWhenStringArrayIsEofAndNoH0532Params() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		templateParamMap.put(Strings.KEY_H0535,
				Arrays.asList("Downhole_Direction_Survey_Company", "Small Time Mining NL"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(message.contains("No H0534 in the templateParamMap!"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnMissingH0535MessageWhenStringArrayIsEofAndNoH0533Params() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		templateParamMap.put(Strings.KEY_H0534,
				Arrays.asList("Downhole_Direction_Survey_Instrument", "Single shot camera - SS"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(message.contains("No H0535 in the templateParamMap!"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnMissingH0534AndH0535MessageWhenStringArrayIsEofAndNoH0534H0535Params() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context, times(2)).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		List<String> messages = messageCaptor.getAllValues();
		assertThat(messages.size(), is(equalTo(2)));
		String message = messages.get(0);
		String message1 = messages.get(1);
		assertThat(message.contains("No H0534 in the templateParamMap!"), is(true));
		assertThat(message1.contains("No H0535 in the templateParamMap!"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnNoExpectedRecordMessageWhenStringArrayIsEofAndNoExpectedRecordParam() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		templateParamMap.put(Strings.KEY_H0534,
				Arrays.asList("Downhole_Direction_Survey_Instrument", "Single shot camera - SS"));
		templateParamMap.put(Strings.KEY_H0535,
				Arrays.asList("Downhole_Direction_Survey_Company", "Small Time Mining NL"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(message.contains("No Number_of_data_records in the templateParamMap"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnNullParamsMessageWhenStringArrayIsEofAndParamIsNull() throws Exception {
		// Given
		givenTestInstance();
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(message.contains("Parameter templateParamMap cannot be null!"), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
	}

	@Test
	public void shouldReturnIncorrectSizeMessageWhenStringArrayIsEof() throws Exception {
		// Given
		givenTestInstance();
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		templateParamMap.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("2"));
		templateParamMap.put(Strings.KEY_H0534,
				Arrays.asList("Downhole_Direction_Survey_Instrument", "Single shot camera - SS"));
		templateParamMap.put(Strings.KEY_H0535,
				Arrays.asList("Downhole_Direction_Survey_Company", "Small Time Mining NL"));
		templateParamMap.put(Strings.CURRENT_LINE, Arrays.asList("6"));
		// When
		Template dataBean = testInstance.doFileDataIntegrityCheck(templateParamMap, mockDataBean, context, file);
		// Then
		ArgumentCaptor<LogSeverity> logSeverityCaptor = ArgumentCaptor.forClass(LogSeverity.class);
		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(context).saveStatusLog(logSeverityCaptor.capture(), messageCaptor.capture());
		assertThat(logSeverityCaptor.getValue(), is(equalTo(LogSeverity.ERROR)));
		String message = messageCaptor.getValue();
		assertThat(
				message.contains(
						"Number_of_data_records in the templateParamMap is 3 and Number_of_records_added is 2"),
				is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		assertThat(dataBean, is(nullValue()));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance() throws Exception {
		testInstance = new H0203H0534H0535DataIntegrityValidator();
		mockDataBean = Mockito.mock(Template.class);
		context = Mockito.mock(TemplateProcessorContext.class);
		file = new File("abc");
		DirectoryTreeReverseTraversalZipFileFinder mockFileFinder = PowerMockito
				.mock(DirectoryTreeReverseTraversalZipFileFinder.class);
		PowerMockito.whenNew(DirectoryTreeReverseTraversalZipFileFinder.class).withAnyArguments()
				.thenReturn(mockFileFinder);
	}
}
