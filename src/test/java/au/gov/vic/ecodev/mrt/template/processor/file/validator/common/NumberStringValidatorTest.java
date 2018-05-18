package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class NumberStringValidatorTest {

	private NumberStringValidator testInstance;
	private String data;

	@Test
	public void shouldReturnNoMessageWhenDataIsNumber() {
		// Given
		givenTestInstance("6");
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenDataIsNegativeNumber() {
		// Given
		givenTestInstance("-6");
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenDataIsPositiveNumber() {
		// Given
		givenTestInstance("+6");
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoMessageWhenDataIsDecimalNumber() {
		// Given
		givenTestInstance("6.8");
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoNumericalMessageWhenDataIsNumber() {
		// Given
		givenTestInstance("A");
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template DG4 From column is expected as a number, but got: A")));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance("6");
		// When
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		// Given
		String data = "ABC";
		String template = null;
		String columnName = null;
		int lineNumber = 6;
		// When
		new NumberStringValidator(data, template, columnName, lineNumber);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenColumnNameIsNull() {
		// Given
		String data = "ABC";
		String template = "DG4";
		String columnName = null;
		int lineNumber = 6;
		// When
		new NumberStringValidator(data, template, columnName, lineNumber);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance(String string) {
		data = string;
		String template = "DG4";
		String columnName = "From";
		int lineNumber = 6;

		testInstance = new NumberStringValidator(data, template, columnName, lineNumber);
	}
}
