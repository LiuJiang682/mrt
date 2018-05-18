package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
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
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H1000ValidatorTest {

	private H1000Validator testInstance;
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
		assertThat(messages.get(0),
				is(equalTo("ERROR: Template DG4 H1000 row requires minimum 6 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H1000", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("ERROR: Template DG4 H1000 row requires minimum 6 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingHoleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_idd", "From", "Sample_Id", "To", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Hole_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingFromMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "From1", "Sample_Id", "To", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the From column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingToMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "From", "Sample_Id", "To1", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the To column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingSampleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Dip" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Sample_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		List<String> sampleIdList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(nullValue()));
	}

	@Test
	public void shouldReturnMissingDrillingCodeMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code1", "Sample_id" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Drill_code column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		List<String> sampleIdList = params.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		assertThat(sampleIdList, is(nullValue()));
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFields() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(5)));
		assertThat(columnHeaders.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo("from")));
		assertThat(columnHeaders.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Dg4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(columnHeaders.get(4), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(6)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(6)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Dg4ColumnHeaders.FROM.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(capturedKeyValues.get(4), is(equalTo(Dg4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(capturedKeyValues.get(5), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(6)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("from")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("To")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Sample_id")));
		assertThat(values.get(4).size(), is(equalTo(1)));
		assertThat(values.get(4).get(0), is(equalTo("Drill_code")));
		assertThat(values.get(5).size(), is(equalTo(5)));
		assertThat(values.get(5).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(5).get(1), is(equalTo("from")));
		assertThat(values.get(5).get(2), is(equalTo("To")));
		assertThat(values.get(5).get(3), is(equalTo("Drill_code")));
		assertThat(values.get(5).get(4), is(equalTo("Sample_id")));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFieldsWithH0702Multiply() {
		// Given
		String[] strs = { "H1000", "Hole_id", "from", "To", "Drill_code", "Sample_id", "Job_no" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX 
				+ H0702Validator.JOB_NO_TITLE, Arrays.asList("Multiply"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(6)));
		assertThat(columnHeaders.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo("from")));
		assertThat(columnHeaders.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Dg4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(columnHeaders.get(4), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(columnHeaders.get(5), is(equalTo("Job_no")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(6)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(6)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Dg4ColumnHeaders.FROM.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(capturedKeyValues.get(4), is(equalTo(Dg4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(capturedKeyValues.get(5), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(6)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("from")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("To")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Sample_id")));
		assertThat(values.get(4).size(), is(equalTo(1)));
		assertThat(values.get(4).get(0), is(equalTo("Drill_code")));
		assertThat(values.get(5).size(), is(equalTo(6)));
		assertThat(values.get(5).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(5).get(1), is(equalTo("from")));
		assertThat(values.get(5).get(2), is(equalTo("To")));
		assertThat(values.get(5).get(3), is(equalTo("Drill_code")));
		assertThat(values.get(5).get(4), is(equalTo("Sample_id")));
		assertThat(values.get(5).get(5), is(equalTo("Job_no")));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H1000Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}