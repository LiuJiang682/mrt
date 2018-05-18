package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

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

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;

public class SampleTypeDataValidatorTest {

	private SampleTypeDataValidator testInstance;

	@Test
	public void shouldReturnMissingSampleTypeDataMessageWhenSampleTypeDataIsNull() {
		// Given
		String[] strs = { "D", "KPDD001", "39220.1", "6589600", null, "210", "DD", "-90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		testInstance = new SampleTypeDataValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template SG4 column Sample_type cannot be null or empty")));
	}

	@Test
	public void shouldReturnMissingSampleTypeDataMessageWhenSampleTypeColumnHeaderIsNotProvided() {
		// Given
		String[] strs = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		headers.set(3, "xxx");
		testInstance = new SampleTypeDataValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template SG4 missing Sample_type column")));
	}

	@Test
	public void shouldReturnMissingSampleTypeMessageWhenSampleTypeParamIsNotProvidedInParamMap() {
		givenTestInstance();
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("ERROR: Line 6: Template SG4 missing Sample_type column label from parameter")));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisedExceptionWhenColumnHeadersIsNull() {
		// Given
		String[] strs = {};
		int lineNumber = 6;
		List<String> columnHeaders = null;
		Map<String, List<String>> templateParamMap = null;
		// When
		new SampleTypeDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaisedExceptionWhenTemplateParamMapsIsNull() {
		// Given
		String[] strs = {};
		int lineNumber = 6;
		List<String> columnHeaders = TestFixture.getSg4MandatoryFieldsList();
		Map<String, List<String>> templateParamMap = null;
		// When
		new SampleTypeDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		String[] strs = {};
		int lineNumber = 6;
		List<String> columnHeaders = TestFixture.getSg4MandatoryFieldsList();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		testInstance = new SampleTypeDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
	}
}
