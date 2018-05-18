package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class DValidatorTest {

	private Map<String, List<String>> params;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldIncreaseTheRecordCount() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "210",  "-90", "270" };
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("1")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D1")));
		doValuesAssert(valueCaptor);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void shouldIncreaseTheRecordCountOnTop() {
		givenTestConditions();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("1"));
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		String[] datas = { "D", "KPDD001", "210", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("2")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D2")));
		doValuesAssert(valueCaptor);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldResetTheRecordCount() {
		givenTestConditions();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("abc"));
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		String[] datas = { "D", "KPDD001", "210", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("1")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D1")));
		doValuesAssert(valueCaptor);
	}

	@Test
	public void shouldReturnIncorrectLineNumber() {
		givenMockTemplate();
		params = new HashMap<>();
		String[] datas = null;
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Not current line number has been passing down!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectColumnSizeMessageWhenStrsIsNull() {
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		givenMockTemplate();
		String[] datas = null;
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectColumnSizeMessage() {
		// Given
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectColumnHeaderCountMessage() {
		// Given
		params = new HashMap<>();
		givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "60", "0", "0", "SS", "DD" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No column header has been passing down")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidDepthFromValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "A",  "-90", "370" };
		params.put(Dl4ColumnHeaders.DEPTH_FROM.getCode(), 
				Arrays.asList(Dl4ColumnHeaders.DEPTH_FROM.getCode()));
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template Dl4 Depth_from column is expected as a number, but got: A")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getDl4MandatoryColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
	}

	private void givenCurrentLineNumber(Map<String, List<String>> params) {
		String[] lineNumbers = { "6" };
		params.put(Strings.CURRENT_LINE, Arrays.asList(lineNumbers));
	}

	private void givenMockTemplate() {
		mockDataBean = Mockito.mock(Template.class);
	}

	private void givenTestConditions() {
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		givenMockTemplate();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doValuesAssert(ArgumentCaptor<List> valueCaptor) {
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("KPDD001")));
		assertThat(values.get(1), is(equalTo("210")));
		assertThat(values.get(2), is(equalTo("-90")));
		assertThat(values.get(3), is(equalTo("270")));
	}
}
