package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

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
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class DValidatorTest {

	private Map<String, List<String>> params;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldIncreaseTheRecordCount() {
		givenTestConditions();
		givenDrillCode(params);
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "DD", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
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
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "DD", "370" };
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
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "DD", "370" };
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
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 5 columns, only got 0")));
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 5 columns, only got 2")));
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
	public void shouldReturnInvalidDrillCodeValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "dd", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6's Drill_Code column must exist in H0400! But got: dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingHoleIdColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "DD", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		params.get(Strings.COLUMN_HEADERS).set(0, "xx");
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template DG4 missing Hole_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidHoleIdValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "",  "A",  "0", "1", "DD", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template DG4 column Hole_id cannot be null or empty")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingSampleIdColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "A",  "0", "1", "DD", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		params.get(Strings.COLUMN_HEADERS).set(1, "xx");
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template DG4 missing Sample_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidSampleIdValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "",  "0", "1", "DD", "370" };
		params.put(Dg4ColumnHeaders.DRILL_CODE.getCode(), 
				Arrays.asList(Dg4ColumnHeaders.DRILL_CODE.getCode()));
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template DG4 column Sample_id cannot be null or empty")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getDg4MandatoryColumns();
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
		givenDrillCode(params);
	}
	
	private void givenDrillCode(Map<String, List<String>> params) {
		List<String> drillCodes = Arrays.asList(new String[] { "DD", "RC" });
		params.put(Strings.TITLE_PREFIX + H0400Validator.DRILL_CODE_TITLE, drillCodes);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doValuesAssert(ArgumentCaptor<List> valueCaptor) {
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("KPDD001")));
		assertThat(values.get(1), is(equalTo("A")));
		assertThat(values.get(2), is(equalTo("0")));
		assertThat(values.get(3), is(equalTo("1")));
	}
}