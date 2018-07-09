package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0005ValidatorTest {

	private H0005Validator testInstance;
	private Template mockDataBean;

	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0005", "State", "VIC" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0005")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("State")));
		assertThat(values.get(1), is(equalTo("VIC")));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0005", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0005 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage0() {
		// Given
		String[] strs = null;
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0005 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectStateMessage() {
		// Given
		String[] strs = { "H0005", "State", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0005 must be Vic, but got SA")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0005", "dd", "Vic" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0005 title must be State, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturn2Message() {
		// Given
		String[] strs = { "H0005", "dd", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("H0005 must be Vic, but got SA")));
		assertThat(messages.get(1), is(equalTo("H0005 title must be State, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H0005Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
