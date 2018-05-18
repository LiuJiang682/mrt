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

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0502ValidatorTest {

	private H0502Validator testInstance;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageWithNominal() {
		// Given
		String[] strs = { "H0502", "Vertical_datum", "Nominal" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> numberOfRecords = params.get(H0502Validator.VERTICAL_DATUM_TITLE);
		assertThat(numberOfRecords.size(), is(equalTo(1)));
		assertThat(numberOfRecords.get(0), is(equalTo("Nominal")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0502")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Vertical_datum")));
		assertThat(values.get(1), is(equalTo("Nominal")));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageWithAHD() {
		// Given
		String[] strs = { "H0502", "Vertical_datum", "AHD" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> numberOfRecords = params.get(H0502Validator.VERTICAL_DATUM_TITLE);
		assertThat(numberOfRecords.size(), is(equalTo(1)));
		assertThat(numberOfRecords.get(0), is(equalTo("AHD")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0502")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Vertical_datum")));
		assertThat(values.get(1), is(equalTo("AHD")));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0502", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0502 requires minimum 3 columns, only got 2")));
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
		assertThat(messages.get(0), is(equalTo("H0502 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectVerticalDatumMessage() {
		// Given
		String[] strs = { "H0502", "Vertical_datum", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0502 must be either Nominal or AHD, but got SA")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0502", "dd", "Nominal" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0502 title must be Vertical_datum, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithNullParam() {
		// Given
		String string = null;
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithEmptyParam() {
		// Given
		String string = "";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithRubbishParam() {
		// Given
		String string = "abc";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithParamAsNominal() {
		// Given
		String string = "Nominal";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithParamAsAHD() {
		// Given
		String string = "AHD";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidVerticalDatumValueCalledWithParamAsAGD84MixCase() {
		// Given
		String string = "Agd84";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidVerticalDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H0502Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
