package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
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
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DipValidator;

public class DipValidatorTest {

	@Test
	public void shouldReturnNoMessageWhenDipIsCorrectRange() {
		// Given
		Map<String, List<String>> params = new HashMap<>();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		int lineNumber = 6;
		DipValidator testInstance = new DipValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMessageWhenDipIsGreaterThen90() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "90.000001", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		DipValidator testInstance = new DipValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Dip is expected as a number between -90 to 90, but got: 90.000001")));
	}
	
	@Test
	public void shouldReturnMessageWhenDipIsLessThenNegative90() {
		// Given
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-99", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
		int lineNumber = 6;
		DipValidator testInstance = new DipValidator(datas, lineNumber, 
				TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("Line 6: Dip is expected as a number between -90 to 90, but got: -99")));
	}
	
	@Test
	public void shouldReturnMissingDipMessageWhenDipColumnHeaderIsNotProvided() {
		//Given
		DipValidator testInstance = new DipValidator(null, 0, new ArrayList<String>(), null);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0:  Missing Dip column")));
	}
	
	@Test
	public void shouldReturnMissingPrecisionMessageWhenParamIsNotProvided() {
		//Given
		Map<String, List<String>> params = new HashMap<>();
		DipValidator testInstance = new DipValidator(null, 0, TestFixture.getColumnHeaderList(), params);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 0:  Dip_Precision is NOT populated! Check the database table TEMPLATE_CONTEXT_PROPERTIES.")));
	}
	
}
