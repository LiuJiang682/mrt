package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

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

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0201ValidatorTest {

	private H0201Validator testInstance;
	private Template mockDataBean;
	
	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0201", "End_date_of_data_acquisition", "7-Feb-11" };
		givenTestInstance(strs);
		Map<String, List<String>> map = new HashMap<>();
		map.put("H0200", Arrays.asList("6-Feb-11"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(map, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0201")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("End_date_of_data_acquisition")));
		assertThat(values.get(1), is(equalTo("7-Feb-11")));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessageWithZeroColumn() {
		// Given
		String[] strs = null;
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0201", "End_date_of_data_acquisition" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0201", "dd", "7-Feb-11" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(new HashMap<String, List<String>>(), 
				mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 title must be End_date_of_data_acquisition, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectDateFormatMessage() {
		// Given
		String[] strs = { "H0201", "End_date_of_data_acquisition", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 must be in dd-MMM-yy format, but got SA")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturn2Message() {
		// Given
		String[] strs = { "H0201", "dd", "SA" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(1), is(equalTo("ERROR: H0201 must be in dd-MMM-yy format, but got SA")));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 title must be End_date_of_data_acquisition, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEndDateBeforeStartDateMessage() {
		// Given
		String[] strs = { "H0201", "End_date_of_data_acquisition", "6-Feb-11" };
		givenTestInstance(strs);
		Map<String, List<String>> map = new HashMap<>();
		map.put(Strings.KEY_H0200, Arrays.asList("8-Feb-11"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(map, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0201 got value: 6-Feb-11 is earlier than H0200 value: 8-Feb-11")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnStartDateUnparsableMessage() {
		// Given
		String[] strs = { "H0201", "End_date_of_data_acquisition", "6-Feb-11" };
		givenTestInstance(strs);
		Map<String, List<String>> map = new HashMap<>();
		map.put(Strings.KEY_H0200, Arrays.asList("abc"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(map, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0200 got value: abc is unparsable: Unparseable date: \"abc\"")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance(String[] strs) {
		testInstance = new H0201Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
	
}
