package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;

public class AzimuthMagValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenAzimuthMagIsCorrectRange() {
		// Given
		Map<String, List<String>> params = new HashMap<>();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "270" };
		int lineNumber = 6;
		AzimuthMagValidator testInstance = new AzimuthMagValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMessageWhenAzimuthMagIsGreaterThen360() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "360.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		int lineNumber = 6;
		AzimuthMagValidator testInstance = new AzimuthMagValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: 360.000001")));
	}
	
	@Test
	public void shouldReturnMessageWhenAzimuthMagIsLessThenZero() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "-0.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		int lineNumber = 6;
		AzimuthMagValidator testInstance = new AzimuthMagValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: -0.000001")));
	}
	
	@Test
	public void shouldReturnMissingAzmithMessageWhenAzimuthColumnHeaderIsNotProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		AzimuthMagValidator testInstance = new AzimuthMagValidator(null, 0,
				new ArrayList<String>(), params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0: Missing either Azimuth_MAG or Latitude")));
	}
	
	@Test
	public void shouldReturnMissingAzmithParamMessageWhenAzimuthParamIsNotProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		AzimuthMagValidator testInstance = new AzimuthMagValidator(null, 0,
				new ArrayList<String>(), params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0: Missing Azimuth_MAG from templateParamMap")));
	}
	
	@Test
	public void shouldReturnMissingPrecisionMessageWhenParamIsNotProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		AzimuthMagValidator testInstance = new AzimuthMagValidator(null, 0, TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0:  Azimuth_Mag_Precision is NOT populated! Check the database table TEMPLATE_CONTEXT_PROPERTIES.")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParameterMapIsNull() {
		// Given
		Map<String, List<String>> params = null;
		// When
		new AzimuthMagValidator(null, 0, new ArrayList<String>(), params);
		fail("Program reached unexpected point!");
	}
}
