package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
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
import au.gov.vic.ecodev.mrt.template.fields.GeodeticDatum;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator;
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
	public void shouldReturnMissingSampleIdMessage() {
		// Given
		String[] strs = { "H1000", "Sample Idd", "Easting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Sample ID column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingMgaEMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "aEasting_MGA", "Northing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Easting_MGA column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingAmgEMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "EASTING_AMGa", "Northing_AMG", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Latitude", "Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Easting_AMG column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidH0501DataMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "EASTING_AMGa", "Northing_AMG", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(H0501Validator.GEODETIC_DATUM_TITLE, Arrays.asList(new String[] {"abc"}));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0501 Geodetic_datum must be either GDA94 or AGD84. But got: abc")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingMgaNMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "Easting_MGA", "ANorthing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Northing_MGA column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingAmgNMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "Easting_AMG", "ANorthing_MGA", "Sample_type", "Total Hole Depth", "Drill Code", "Dip", "Latitude", "Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Northing_AMG column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingSampleTypeMessage() {
		// Given
		String[] strs = { "H1000", "Sample ID", "Easting_AMG", "Northing_AMG", "Sample_type2", "Total Hole Depth", "Drill Code", "Dip", "Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Sample_type column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingH0501Message() {
		// Given
		String[] strs = null;
		givenTestInstance(strs);
		Map<String, List<String>> templateParamMap = new HashMap<>();
		List<String> messages = new ArrayList<>();
		// When
		testInstance.doH0501RelatedFieldsCheck(messages, templateParamMap, null);
		// Then
		assertThat(messages.size(), is(equalTo(1)));
		String message = messages.get(0);
		assertThat(message, is(equalTo("No H0501 Geodetic_datum data!")));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveFields() {
		// Given
		String[] strs = { "H1000", "Sample ID", "Easting_MGA", "Northing_MGA",  "Sample_type"};
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(4)));
		assertThat(columnHeaders.get(0), is(equalTo(Sg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo(Sg4ColumnHeaders.EASTING_MGA.getCode())));
		assertThat(columnHeaders.get(2), is(equalTo(Sg4ColumnHeaders.NORTHING_MGA.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Sg4ColumnHeaders.SAMPLE_TYPE.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(2)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		assertThat(keys.size(), is(equalTo(2)));
		assertThat(keys.get(0), is(equalTo("Sample ID")));
		assertThat(keys.get(1), is(equalTo("Sample_type")));
		List<List> values = valueCaptor.getAllValues();
		assertThat(values.size(), is(equalTo(2)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo("Sample ID")));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Sample_type")));
		
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
		assertThat(aValues.get(0), is(equalTo(Sg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(aValues.get(1), is(equalTo(Sg4ColumnHeaders.EASTING_MGA.getCode())));
		assertThat(aValues.get(2), is(equalTo(Sg4ColumnHeaders.NORTHING_MGA.getCode())));
		assertThat(aValues.get(3), is(equalTo(Sg4ColumnHeaders.SAMPLE_TYPE.getCode())));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldCheckAllMustHaveAmgFields() {
		// Given
		String[] strs = { "H1000", "Sample ID", "Easting_AMG", "Northing_AMG", "Sample_type"};
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(4)));
		assertThat(columnHeaders.get(0), is(equalTo(Sg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo(Sg4ColumnHeaders.EASTING_AMG.getCode())));
		assertThat(columnHeaders.get(2), is(equalTo(Sg4ColumnHeaders.NORTHING_AMG.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(Sg4ColumnHeaders.SAMPLE_TYPE.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean, times(2)).put(keyCaptor.capture(), valueCaptor.capture());
		List<String> keys = keyCaptor.getAllValues();
		assertThat(keys.size(), is(equalTo(2)));
		assertThat(keys.get(0), is(equalTo("Sample ID")));
		assertThat(keys.get(1), is(equalTo("Sample_type")));
		List<List> values = valueCaptor.getAllValues();
		assertThat(values.size(), is(equalTo(2)));
		assertThat(values.get(0).size(), is(equalTo(1)));
		assertThat(values.get(0).get(0), is(equalTo("Sample ID")));
		assertThat(values.get(1).size(), is(equalTo(1)));
		assertThat(values.get(1).get(0), is(equalTo("Sample_type")));
		
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
		assertThat(aValues.get(0), is(equalTo(Sg4ColumnHeaders.SAMPLE_ID.getCode())));
		assertThat(aValues.get(1), is(equalTo(Sg4ColumnHeaders.EASTING_AMG.getCode())));
		assertThat(aValues.get(2), is(equalTo(Sg4ColumnHeaders.NORTHING_AMG.getCode())));
		assertThat(aValues.get(3), is(equalTo(Sg4ColumnHeaders.SAMPLE_TYPE.getCode())));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H1000Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
	
	private Map<String, List<String>> givenH0501Param() {
		Map<String, List<String>> params = new HashMap<>();
		params.put(H0501Validator.GEODETIC_DATUM_TITLE, Arrays.asList(new String[] {GeodeticDatum.GDA94.name()}));
		return params;
	}
	
	private Map<String, List<String>> givenH0501AmgParam() {
		Map<String, List<String>> params = new HashMap<>();
		params.put(H0501Validator.GEODETIC_DATUM_TITLE, Arrays.asList(new String[] {GeodeticDatum.AGD84.name()}));
		return params;
	}
}
