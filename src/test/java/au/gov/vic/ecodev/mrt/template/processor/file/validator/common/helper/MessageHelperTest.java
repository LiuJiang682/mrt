package au.gov.vic.ecodev.mrt.template.processor.file.validator.common.helper;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MessageHelperTest {

	@Test
	public void shouldReturnMissingHeaderMessage() {
		//Given
		long lineNumber = 1;
		String code = "abc";
		List<String> messages = new ArrayList<>();
		//When
		new MessageHelper(lineNumber, code).constructMissingHeaderMessages(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 is missing header abc")));
	}
	
	@Test
	public void shouldReturnInvalidNumberDataMessage() {
		//Given
		long lineNumber = 1;
		String code = "abc";
		List<String> messages = new ArrayList<>();
		//When
		new MessageHelper(lineNumber, code).constructInvalidNumberDataMessage(messages, "fce");
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 has invalid data: fce for column: abc")));
	}
	
}
