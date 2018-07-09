package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

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

import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.GeodeticDatum;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H1000Validator;
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
		assertThat(messages.get(0), is(equalTo("H1000 requires minimum 7 columns, only got 0")));
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
		assertThat(messages.get(0), is(equalTo("H1000 requires minimum 7 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingHoleIdMessage() {
		// Given
		String[] strs = { "H1000", "Hole_idd", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
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
	public void shouldReturnMissingElevationMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation1", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Elevation column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingTotalDepthMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "1Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Total Hole Depth column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingDrillCodeMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "DrillCode", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Drill Code column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingDipMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "1Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Dip column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "2Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H1000 requires the Azimuth_MAG column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingMgaEMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "aEasting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
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
		String[] strs = { "H1000", "Hole_id", "EASTING_AMGa", "Northing_AMG", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Latitude", "Longitude" };
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
		String[] strs = { "H1000", "Hole_id", "EASTING_AMGa", "Northing_AMG", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
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
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "ANorthing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
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
		String[] strs = { "H1000", "Hole_id", "Easting_AMG", "ANorthing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Latitude", "Longitude" };
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
	public void shouldReturnMissingLatitudeMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_AMG", "Northing_AMG", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Latitude column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingLongitudeMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_AMG", "Northing_AMG", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Latitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H1000 requires the Longitude column")));
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

	@Test
	public void shouldCheckAllMustHaveFields() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Azimuth_MAG" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501Param();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(8)));
		assertThat(columnHeaders.get(0), is(equalTo(SL4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo(SL4ColumnHeaders.EASTING_MGA.getCode())));
		assertThat(columnHeaders.get(2), is(equalTo(SL4ColumnHeaders.NORTHING_MGA.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(SL4ColumnHeaders.ELEVATION.getCode())));
		assertThat(columnHeaders.get(4), is(equalTo(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode())));
		assertThat(columnHeaders.get(5), is(equalTo(SL4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(columnHeaders.get(6), is(equalTo(SL4ColumnHeaders.DIP.getCode())));
		assertThat(columnHeaders.get(7), is(equalTo(SL4ColumnHeaders.AZIMUTH_MAG.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H1000")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(8)));
		assertThat(values.get(0), is(equalTo("Hole_id")));
		assertThat(values.get(1), is(equalTo("Easting_MGA")));
		assertThat(values.get(2), is(equalTo("Northing_MGA")));
		assertThat(values.get(3), is(equalTo("Elevation")));
		assertThat(values.get(4), is(equalTo("Total Hole Depth")));
		assertThat(values.get(5), is(equalTo("Drill Code")));
		assertThat(values.get(6), is(equalTo("Dip")));
		assertThat(values.get(7), is(equalTo("Azimuth_MAG")));
	}
	
	@Test
	public void shouldCheckAllMustHaveAmgFields() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Easting_AMG", "Northing_AMG", "Elevation", "Total Hole Depth", "Drill Code", "Dip", "Latitude", "Longitude" };
		givenTestInstance(strs);
		Map<String, List<String>> params = givenH0501AmgParam();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		List<String> columnHeaders = params.get(Strings.COLUMN_HEADERS);
		assertThat(CollectionUtils.isEmpty(columnHeaders), is(false));
		assertThat(columnHeaders.size(), is(equalTo(9)));
		assertThat(columnHeaders.get(0), is(equalTo(SL4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(columnHeaders.get(1), is(equalTo(SL4ColumnHeaders.EASTING_AMG.getCode())));
		assertThat(columnHeaders.get(2), is(equalTo(SL4ColumnHeaders.NORTHING_AMG.getCode())));
		assertThat(columnHeaders.get(3), is(equalTo(SL4ColumnHeaders.ELEVATION.getCode())));
		assertThat(columnHeaders.get(4), is(equalTo(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode())));
		assertThat(columnHeaders.get(5), is(equalTo(SL4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(columnHeaders.get(6), is(equalTo(SL4ColumnHeaders.DIP.getCode())));
		assertThat(columnHeaders.get(7), is(equalTo(SL4ColumnHeaders.LATITUDE.getCode())));
		assertThat(columnHeaders.get(8), is(equalTo(SL4ColumnHeaders.LONGITUDE.getCode())));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H1000")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(9)));
		assertThat(values.get(0), is(equalTo(SL4ColumnHeaders.HOLE_ID.getCode())));
		assertThat(values.get(1), is(equalTo(SL4ColumnHeaders.EASTING_AMG.getCode())));
		assertThat(values.get(2), is(equalTo(SL4ColumnHeaders.NORTHING_AMG.getCode())));
		assertThat(values.get(3), is(equalTo(SL4ColumnHeaders.ELEVATION.getCode())));
		assertThat(values.get(4), is(equalTo(SL4ColumnHeaders.TOTAL_HOLE_DEPTH.getCode())));
		assertThat(values.get(5), is(equalTo(SL4ColumnHeaders.DRILL_CODE.getCode())));
		assertThat(values.get(6), is(equalTo(SL4ColumnHeaders.DIP.getCode())));
		assertThat(values.get(7), is(equalTo(SL4ColumnHeaders.LATITUDE.getCode())));
		assertThat(values.get(8), is(equalTo(SL4ColumnHeaders.LONGITUDE.getCode())));
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
