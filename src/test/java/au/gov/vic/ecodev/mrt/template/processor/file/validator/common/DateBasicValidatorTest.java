package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DateBasicValidatorTest {

	@Test
	public void shouldReturnNoMessage() {
		// Given
		String dateFormat = "dd-MMM-yy";
		String fieldName = "H0200";
		String[] datas = {"Date", "Date", "6-Feb-11"};
		ThirdColumnNullIgnoreDateBasicValidator testInstance = new ThirdColumnNullIgnoreDateBasicValidator(datas, dateFormat, fieldName);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages.isEmpty(), is(true));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		String dateFormat = "dd-MMM-yy";
		String fieldName = "H0200";
		// When
		ThirdColumnNullIgnoreDateBasicValidator testInstance = new ThirdColumnNullIgnoreDateBasicValidator(null, dateFormat, fieldName);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenDateFormatIsNull() {
		// Given
		String dateFormat = null;
		String fieldName = null;
		// When
		new ThirdColumnNullIgnoreDateBasicValidator(null, dateFormat, fieldName);
		fail("Program reached unexpected point!");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenFieldNameIsNull() {
		// Given
		String dateFormat = "dd-MMM-yy";
		String fieldName = null;
		// When
		new ThirdColumnNullIgnoreDateBasicValidator(null, dateFormat, fieldName);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnErrorMessage() {
		// Given
		String dateFormat = "dd-MMM-yy";
		String fieldName = "H0200";
		String[] datas = {"Date", "Date", "6"};
		ThirdColumnNullIgnoreDateBasicValidator testInstance = new ThirdColumnNullIgnoreDateBasicValidator(datas, dateFormat, fieldName);
		List<String> messages = new ArrayList<>();
		// When
		testInstance.validate(messages);
		// Then
		assertThat(messages.isEmpty(), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0200 must be in dd-MMM-yy format, but got 6")));
	}
}
