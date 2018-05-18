package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
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
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
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
		assertThat(messages.get(0), is(equalTo("H1000 requires minimum 5 columns, only got 0")));
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
		assertThat(messages.get(0), is(equalTo("H1000 requires minimum 5 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingHoleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_idd", "Surveyed_Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Hole_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingSurveyedDepthMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Surveyed_Depth column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList, is(nullValue()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnUsingDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "Azimuth_MAG", "Dip" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Depth column, use it as Surveyed_Depth")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(5)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(5)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Ds4ColumnHeaders.DIP.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())));
		assertThat(capturedKeyValues.get(4), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(5)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Depth")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("Dip")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).size(), is(equalTo(4)));
		assertThat(values.get(4).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(4).get(1), is(equalTo("Depth")));
		assertThat(values.get(4).get(2), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).get(3), is(equalTo("Dip")));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void shouldReturnUsingSurveyedSpaceDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth_MAG", "Dip"};
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Surveyed Depth column, use it as Surveyed_Depth")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(5)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(5)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Ds4ColumnHeaders.DIP.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())));
		assertThat(capturedKeyValues.get(4), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(5)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Surveyed Depth")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo("Dip")));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).size(), is(equalTo(4)));
		assertThat(values.get(4).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(4).get(1), is(equalTo("Surveyed Depth")));
		assertThat(values.get(4).get(2), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).get(3), is(equalTo("Dip")));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Dip", "Survey_instrument", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Azimuth column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingDipMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_MAG", "Survey_instrument", "Drill_code" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Dip column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFields() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_MAG", "Dip" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(4)));
		assertThat(columnHeaders.get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())));
		assertThat(columnHeaders.get(2), is(equalTo(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Ds4ColumnHeaders.DIP.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(5)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> capturedKeyValues = keyCaptor.getAllValues();
		List<List> values = valueCaptor.getAllValues();
		assertThat(CollectionUtils.isEmpty(capturedKeyValues), is(false));
		assertThat(capturedKeyValues.size(), is(equalTo(5)));
		assertThat(capturedKeyValues.get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(capturedKeyValues.get(1), is(equalTo(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())));
		assertThat(capturedKeyValues.get(2), is(equalTo(Ds4ColumnHeaders.DIP.getCode())));
		assertThat(capturedKeyValues.get(3), is(equalTo(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())));
		assertThat(capturedKeyValues.get(4), is(equalTo("H1000")));
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(5)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo(Ds4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Surveyed_Depth")));
		assertThat(values.get(2).size(), is(equalTo(1)));
		assertThat(values.get(2).get(0), is(equalTo(Ds4ColumnHeaders.DIP.getCode())));
		assertThat(values.get(3).size(), is(equalTo(1)));
		assertThat(values.get(3).get(0), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).size(), is(equalTo(4)));
		assertThat(values.get(4).get(0), is(equalTo("Hole_id")));
		assertThat(values.get(4).get(1), is(equalTo("Surveyed_Depth")));
		assertThat(values.get(4).get(2), is(equalTo("Azimuth_MAG")));
		assertThat(values.get(4).get(3), is(equalTo("Dip")));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H1000Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
