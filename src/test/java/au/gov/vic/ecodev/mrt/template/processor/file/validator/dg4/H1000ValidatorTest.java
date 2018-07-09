package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
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
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
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
				is(equalTo("ERROR: Template DG4 H1000 row requires minimum 5 columns, only got 0")));
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
				is(equalTo("ERROR: Template DG4 H1000 row requires minimum 5 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingHoleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_idd", "Depth From", "Sample ID", "Depth To" };
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
		String[] strs = { "H1000", "Hole_id", "From1", "Sample ID", "Depth To" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Depth From column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingToMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Sample ID", "Depth To1" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Depth To column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnMissingSampleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Depth To", "Dip" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Sample ID column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		List<String> sampleIdList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(sampleIdList, is(nullValue()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFields() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Depth To",  "Sample ID" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(4)));
		assertThat(columnHeaders.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo("Depth From")));
		assertThat(columnHeaders.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(4)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(4)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Dg4ColumnHeaders.FROM.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));

		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(4)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Depth From")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("Depth To")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Sample ID")));
		
		ArgumentCaptor<String> key1Captor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> value1Captor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(key1Captor.capture(), value1Captor.capture());
		List<String> aKeys = key1Captor.getAllValues();
		List<MrtTemplateValue> aValueList = value1Captor.getAllValues();
		assertThat(aKeys, is(notNullValue()));
		assertThat(aKeys.size(), is(equalTo(1)));
		assertThat(aKeys.get(0), is(equalTo("H1000")));
		assertThat(aValueList, is(notNullValue()));
		assertThat(aValueList.size(), is(equalTo(1)));
		List<String> aValues = aValueList.get(0).getDatas();
		assertThat(aValues.size(), is(equalTo(4)));
		assertThat(aValues.get(0), is(equalTo("Hole_id")));
		assertThat(aValues.get(1), is(equalTo("Depth From")));
		assertThat(aValues.get(2), is(equalTo("Depth To")));
		assertThat(aValues.get(3), is(equalTo("Sample ID")));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFieldsWithH0702Multiply() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Depth To", "Sample ID", "Job_no" };
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
		assertThat(columnHeaders.size(), is(equalTo(5)));
		assertThat(columnHeaders.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo("Depth From")));
		assertThat(columnHeaders.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(columnHeaders.get(4), is(equalTo("Job_no")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(4)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(4)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Dg4ColumnHeaders.FROM.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Dg4ColumnHeaders.TO.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
//		assertThat(capturedKeyValues.get(4), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(4)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Dg4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Depth From")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("Depth To")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Sample ID")));
		
		ArgumentCaptor<String> key1Captor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> value1Captor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(key1Captor.capture(), value1Captor.capture());
		List<String> aKeys = key1Captor.getAllValues();
		List<MrtTemplateValue> aValueList = value1Captor.getAllValues();
		assertThat(aKeys, is(notNullValue()));
		assertThat(aKeys.size(), is(equalTo(1)));
		assertThat(aKeys.get(0), is(equalTo("H1000")));
		assertThat(aValueList, is(notNullValue()));
		assertThat(aValueList.size(), is(equalTo(1)));
		List<String> aValues = aValueList.get(0).getDatas();
		assertThat(aValues.size(), is(equalTo(5)));
		assertThat(aValues.get(0), is(equalTo("Hole_id")));
		assertThat(aValues.get(1), is(equalTo("Depth From")));
		assertThat(aValues.get(2), is(equalTo("Depth To")));
		assertThat(aValues.get(3), is(equalTo("Sample ID")));
		assertThat(aValues.get(4), is(equalTo("Job_no")));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H1000Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
