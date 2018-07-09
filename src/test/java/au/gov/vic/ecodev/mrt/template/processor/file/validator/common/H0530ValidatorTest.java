package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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

import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0503Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0530ValidatorTest {

	private H0530Validator testInstance;
	private Template mockDataBean;

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
		assertThat(messages.get(0), is(equalTo("H0530 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0530", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0530 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0530", "dd", "Projected" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0530 title must be Coordinate_system, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectCoordinateSystemValueMessage() {
		// Given
		String[] strs = { "H0530", "Coordinate_system", "SA" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0530 must be either Projected or Latitude/Longitude, but got SA")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnNoErrorMessage() {
		// Given
		String[] strs = { "H0530", "Coordinate_system", "Projected" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0530")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(2)));
		assertThat(values.get(0), is(equalTo("Coordinate_system")));
		assertThat(values.get(1), is(equalTo("Projected")));
	}
	
	@Test
	public void shouldReturn2ErrorMessages() {
		// Given
		String[] strs = { "H0530", "dd", "U" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("H0530 title must be Coordinate_system, but got dd")));
		assertThat(messages.get(1), is(equalTo("H0530 must be either Projected or Latitude/Longitude, but got U")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidH0503Message() {
		// Given
		String[] strs = { "H0530", "Coordinate_system", "Latitude/Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(H0503Validator.PROJECTION_TITLE, Arrays.asList("Australian Map Grid (AGD)"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0530 is Latitude/Longitude, H0503 must be empty or Non-projected, but got: Australian Map Grid (AGD)")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidH0531Message() {
		// Given
		String[] strs = { "H0530", "Coordinate_system", "Latitude/Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(H0531Validator.PROJECTION_ZONE_TITLE, Arrays.asList("55"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0530 is Latitude/Longitude, H0531 must be empty or Non-projected, but got: 55")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenTestInstance(final String[] strs) {
		testInstance = new H0530Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
