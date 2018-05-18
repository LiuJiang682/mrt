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

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0501ValidatorTest {

	private H0501Validator testInstance;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageWithGDA94() {
		// Given
		String[] strs = { "H0501", "Geodetic_datum", "GDA94" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> numberOfRecords = params.get(H0501Validator.GEODETIC_DATUM_TITLE);
		assertThat(numberOfRecords.size(), is(equalTo(1)));
		assertThat(numberOfRecords.get(0), is(equalTo("GDA94")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0501")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Geodetic_datum")));
		assertThat(values.get(1), is(equalTo("GDA94")));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageWithAGD84() {
		// Given
		String[] strs = { "H0501", "Geodetic_datum", "AGD84" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> numberOfRecords = params.get(H0501Validator.GEODETIC_DATUM_TITLE);
		assertThat(numberOfRecords.size(), is(equalTo(1)));
		assertThat(numberOfRecords.get(0), is(equalTo("AGD84")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0501")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Geodetic_datum")));
		assertThat(values.get(1), is(equalTo("AGD84")));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0501", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0501 requires minimum 3 columns, only got 2")));
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
		assertThat(messages.get(0), is(equalTo("H0501 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectGeodeticDatumMessage() {
		// Given
		String[] strs = { "H0501", "Geodetic_datum", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0501 must be either GDA94 or AGD84, but got SA")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0501", "dd", "GDA94" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0501 title must be Geodetic_datum, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithNullParam() {
		// Given
		String string = null;
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithEmptyParam() {
		// Given
		String string = "";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithRubbishParam() {
		// Given
		String string = "abc";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithParamAsGDA94() {
		// Given
		String string = "GDA94";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithParamAsAGD84() {
		// Given
		String string = "AGD84";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIsValidGeodeticDatumValueCalledWithParamAsAGD84MixCase() {
		// Given
		String string = "Agd84";
		String[] strs = {};
		givenTestInstance(strs);
		// When
		boolean flag = testInstance.isValidGeodeticDatumValue(string);
		// Then
		assertThat(flag, is(false));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H0501Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
