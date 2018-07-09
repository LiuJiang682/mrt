package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.Sl4DefaultValidator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class Sl4DefaultValidatorTest {

	private Sl4DefaultValidator testInstance;
	private Template mockDataBean;

	@Test
	public void shouldReturnEmptyMessageWhenStringArrayIsPopulatedAndUpdateDataBean() {
		// Given
		givenTestInstance();
		String[] strs = { "abc", "123" };
		testInstance.init(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("abc")));
		MrtTemplateValue values = valueCaptor.getValue();
		assertThat(values, is(notNullValue()));
		assertThat(values.getDatas().isEmpty(), is(false));
		assertThat(values.getDatas().get(0), is(equalTo("123")));
	}

	@Test
	public void shouldReturnIncorrectSizeMessageWhenStringArrayIsEof() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("2"));
		params.put(Strings.KEY_H0532, Arrays.asList("Surface_Location_Survey_Instrument", "GPS"));
		params.put(Strings.KEY_H0533, Arrays.asList("Surface_Location_Survey_Company"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Number_of_data_records in the templateParamMap is 3 and Number_of_records_added is 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnNoExpectedRecordMessageWhenStringArrayIsEofAndNoExpectedRecordParam() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		params.put(Strings.KEY_H0532, Arrays.asList("Surface_Location_Survey_Instrument", "GPS"));
		params.put(Strings.KEY_H0533, Arrays.asList("Surface_Location_Survey_Company"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No Number_of_data_records in the templateParamMap")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnNoActualRecordMessageWhenStringArrayIsEofAndNoActualRecordParam() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		params.put(Strings.KEY_H0532, Arrays.asList("Surface_Location_Survey_Instrument", "GPS"));
		params.put(Strings.KEY_H0533, Arrays.asList("Surface_Location_Survey_Company"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No Number_of_records_added in the templateParamMap")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnEmptyMessageWhenStringArrayIsEof() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		params.put(Strings.KEY_H0532, Arrays.asList("Surface_Location_Survey_Instrument", "GPS"));
		params.put(Strings.KEY_H0533, Arrays.asList("Surface_Location_Survey_Company"));
		// When
		Optional<List<String>> messages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(messages.isPresent(), is(false));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingH0532MessageWhenStringArrayIsEofAndNoH0532Params() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		params.put(Strings.KEY_H0533, Arrays.asList("Surface_Location_Survey_Company"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No H0532 in the templateParamMap!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingH0533MessageWhenStringArrayIsEofAndNoH0533Params() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_TITLE, Arrays.asList("3"));
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("3"));
		params.put(Strings.KEY_H0532, Arrays.asList("Surface_Location_Survey_Instrument", "GPS"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No H0533 in the templateParamMap!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnNullParamsMessageWhenStringArrayIsEofAndParamIsNull() {
		// Given
		givenTestInstance();
		String[] strs = { "EOF" };
		testInstance.init(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Parameter templateParamMap cannot be null!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnEmptyMessageWhenStringArrayIsNull() {
		// Given
		givenTestInstance();
		// When
		Optional<List<String>> messages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(messages.isPresent(), is(false));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance() {
		testInstance = new Sl4DefaultValidator();
		mockDataBean = Mockito.mock(Template.class);
	}
}
