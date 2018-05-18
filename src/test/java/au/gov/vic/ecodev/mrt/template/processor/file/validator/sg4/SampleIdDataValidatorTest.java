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

public class SampleIdDataValidatorTest {

	private SampleIdDataValidator testInstance;

	@Test
	public void shouldReturnNoMessageWhenSampleIdIsCorrect() {
		// Given
		String[] strs = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		testInstance = new SampleIdDataValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnMissingSampleIDDataMessageWhenSampleIdDataIsNull() {
		// Given
		String[] strs = { "D", null, "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		testInstance = new SampleIdDataValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template SG4 column Sample ID cannot be null or empty")));
	}

	@Test
	public void shouldReturnMissingSampleIDDataMessageWhenSampleIdColumnHeaderIsNotProvided() {
		// Given
		String[] strs = { "D", "KPDD001", "3" };
		Map<String, List<String>> params = new HashMap<>();
		params.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		List<String> headers = TestFixture.getSg4MandatoryColumnsList();
		headers.set(0, "xxx");
		testInstance = new SampleIdDataValidator(strs, 0, headers, params);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Template SG4 missing Sample ID column")));
	}

	@Test
	public void shouldReturnMissingSampleIdMessageWhenSampleIdParamIsNotProvidedInParamMap() {
		givenTestInstance();
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages, is(notNullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0),
				is(equalTo("ERROR: Line 6: Template SG4 missing Sample ID column label from parameter")));
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
		new SampleIdDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
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
		new SampleIdDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		String[] strs = {};
		int lineNumber = 6;
		List<String> columnHeaders = TestFixture.getSg4MandatoryFieldsList();
		Map<String, List<String>> templateParamMap = new HashMap<>();
		testInstance = new SampleIdDataValidator(strs, lineNumber, columnHeaders, templateParamMap);
	}
}
