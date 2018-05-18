package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0002ValidatorTest {

	private H0002Validator testInstance;
	private Template mockDataBean;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void shouldReturnEmptyErrorMessage() {
		//Given
		String[] strs = {"H0002", "Version", "4"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0002")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Version")));
		assertThat(values.get(1), is(equalTo("4")));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage() {
		//Given
		String[] strs = {"H0002", "Version"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0002 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage0() {
		//Given
		String[] strs = null;
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0002 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectVersionMessage() {
		//Given
		String[] strs = {"H0002", "Version", "5"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0002 requires version 4, only got 5")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenTestInstance(final String[] strs) {
		testInstance = new H0002Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
