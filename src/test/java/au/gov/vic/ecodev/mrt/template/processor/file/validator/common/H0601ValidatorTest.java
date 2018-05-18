package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0601ValidatorTest {

	private H0601Validator testInstance;
	private Template mockDataBean;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0601", "Sample_Type", "Diamond core" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> sampletype = params
				.get(Strings.TITLE_PREFIX + H0601Validator.SAMPLE_TYPE_TITLE);
		assertThat(sampletype.size(), is(equalTo(1)));
		assertThat(sampletype.get(0), is(equalTo("Diamond core")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0601")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Sample_Type")));
		assertThat(values.get(1), is(equalTo("Diamond core")));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageAnd2SampleCodes() {
		// Given
		String[] strs = { "H0601", "Sample_Type", "Diamond core", "RC Chips" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> sampleType = params
				.get(Strings.TITLE_PREFIX + H0601Validator.SAMPLE_TYPE_TITLE);
		assertThat(sampleType.size(), is(equalTo(2)));
		assertThat(sampleType.get(0), is(equalTo("Diamond core")));
		assertThat(sampleType.get(1), is(equalTo("RC Chips")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0601")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Sample_Type")));
		assertThat(values.get(1), is(equalTo("Diamond core")));
		assertThat(values.get(2), is(equalTo("RC Chips")));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0601", "dd", "3" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0601 title must be Sample_Type, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessageWhenDataIs2Columns() {
		// Given
		String[] strs = { "H0600", "Sample_Type" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0601 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturneIncorrectLengthMessageWhenDataIsNull() {
		//Given
		String[] strs = null;
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0601 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenTestInstance(String[] strs) {
		testInstance = new H0601Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}