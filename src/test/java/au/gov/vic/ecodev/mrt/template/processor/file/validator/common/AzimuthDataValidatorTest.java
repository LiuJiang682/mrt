package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class AzimuthDataValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenAzimuthMagIsCorrectRange() {
		// Given
		Map<String, List<String>> params = new HashMap<>();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "270" };
		int lineNumber = 6;
		AzimuthDataValidator testInstance = new AzimuthDataValidator(datas, lineNumber, 
				 params, 7, "Azimuth_MAG");
		List<String> messages = new ArrayList<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMissingPrecisionMessageWhenParamIsNotProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		AzimuthDataValidator testInstance = new AzimuthDataValidator(null, 0, 
				params, 8, "Azimuth_MAG");
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), 
				is(equalTo("Line 0:  Azimuth_Mag_Precision is NOT populated! Check the database table TEMPLATE_CONTEXT_PROPERTIES.")));
	}
	
	@Test
	public void shouldReturnMessageWhenAzimuthMagIsLessThenZero() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "-0.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		AzimuthDataValidator testInstance = new AzimuthDataValidator(datas, lineNumber, 
				 params, 7, "Azimuth_MAG");
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
	public void shouldReturnMessageWhenAzimuthMagIsGreaterThen360() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90", "360.000001" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		AzimuthDataValidator testInstance = new AzimuthDataValidator(datas, lineNumber, 
				 params, 7, "Azimuth_MAG");
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: 360.000001")));
	}
}
