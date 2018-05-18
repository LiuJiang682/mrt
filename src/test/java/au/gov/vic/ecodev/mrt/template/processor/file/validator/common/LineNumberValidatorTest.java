package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class LineNumberValidatorTest {

	@Test
	public void shouldReturnEmptyMessageWhenLineNumberIsProvided() {
		//Given
		List<String> lineNumberStrings = new ArrayList<>();
		lineNumberStrings.add("6");
		List<String> messages = new ArrayList<>();
		//When
		int lineNumber = new LineNumberValidator(lineNumberStrings).validate(messages);
		//Then
		assertThat(lineNumber, is(equalTo(6)));
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnNoParameterMessageWhenLineNumberIsNull() {
		//Given
		List<String> lineNumberStrings = null;
		List<String> messages = new ArrayList<>();
		//When
		int lineNumber = new LineNumberValidator(lineNumberStrings).validate(messages);
		//Then
		assertThat(lineNumber, is(equalTo(-1)));
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Not current line number has been passing down!")));
	}
	
	@Test
	public void shouldReturnInvalidParameterMessageWhenLineNumberIsProvidedIsNotInteger() {
		//Given
		List<String> lineNumberStrings = new ArrayList<>();
		lineNumberStrings.add("a");
		List<String> messages = new ArrayList<>();
		//When
		int lineNumber = new LineNumberValidator(lineNumberStrings).validate(messages);
		//Then
		assertThat(lineNumber, is(equalTo(-1)));
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Expected line number is a number, but got: a")));
	}
}
