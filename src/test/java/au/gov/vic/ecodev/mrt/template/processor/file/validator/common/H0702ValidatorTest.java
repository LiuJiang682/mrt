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

public class H0702ValidatorTest {

	private H0702Validator testInstance;
	private Template mockDataBean;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0702", "Job_no", "Multiply" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> sampleCode = params
				.get(Strings.TITLE_PREFIX + H0702Validator.JOB_NO_TITLE);
		assertThat(sampleCode.size(), is(equalTo(1)));
		assertThat(sampleCode.get(0), is(equalTo("Multiply")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0702")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Job_no")));
		assertThat(values.get(1), is(equalTo("Multiply")));
	}
	
	@Test
	public void shouldReturnIncorrectLengthErrorMessageWith2SampleCodes() {
		// Given
		String[] strs = { "H0702", "Job_no", "DD", "RC" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0702 requires 3 columns, but got 4")));
		assertThat(params.isEmpty(), is(true));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), 
				Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0702", "dd", "3" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0702 title must be Job_no, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessageWhenDataIs2Columns() {
		// Given
		String[] strs = { "H0702", "Job_nob" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0702 requires minimum 3 columns, only got 2")));
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
		assertThat(messages.get(0), is(equalTo("ERROR: H0702 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenTestInstance(String[] strs) {
		testInstance = new H0702Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
