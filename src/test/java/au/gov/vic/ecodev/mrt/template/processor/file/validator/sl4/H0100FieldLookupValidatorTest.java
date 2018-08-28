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

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;

public class H0100FieldLookupValidatorTest {
	
	private H0100FieldLookupValidator testInstance;
	private Map<String, List<String>> params;
	
	@Test
	public void shouldReturnMissingTenementMessage() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80" };
		givenTestInstance(strs);
		givenH0100TestConditions();
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Missing Tenement_no data while h0100 has multiply values.")));
	}
	
	@Test
	public void shouldReturnMissingTenementMessageWhenDataIsEqualLength() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "" };
		givenTestInstance(strs);
		givenH0100TestConditions();
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Missing Tenement_no data while h0100 has multiply values.")));
	}
	
	@Test
	public void shouldReturnInvalidTenementNoMessage() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "", "EL" };
		givenTestInstance(strs);
		givenH0100TestConditions();
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 0: Expected Tenement_no EL123,EL456, but got: EL")));
	}
	
	@Test
	public void shouldReturnNoMessageWhenH0100IsNull() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "", "EL" };
		givenTestInstance(strs);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenH0100IsOneElement() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "", "EL" };
		givenTestInstance(strs);
		params.put(Strings.KEY_H0100, Arrays.asList("EL123"));
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenH1000IsNull() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "", "EL" };
		givenTestInstance(strs);
		params.put(Strings.KEY_H0100, Arrays.asList("EL123", "EL456"));
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.isEmpty(), is(true));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "310", "EL123" };
		givenTestInstance(strs);
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamMapIsNull() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "310", "EL123" };
		params = null;
		//When
		new H0100FieldLookupValidator(strs, 0, params);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(String[] strs) {
		params = new HashMap<>();
		testInstance = new H0100FieldLookupValidator(strs, 0, params);
	}

	private void givenH0100TestConditions() {
		params.put(Strings.KEY_H0100, Arrays.asList("EL123", "EL456"));
		List<String> headers = new ArrayList<String>(TestFixture.getColumnHeaderList());
		headers.add("Tenement_no");
		params.put(Strings.COLUMN_HEADERS, headers);
	}
}
