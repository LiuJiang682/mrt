package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

public class ListSizeValidatorTest {

	@Test
	public void shouldReturnColumnCountWhenListProvided() {
		//Given
		List<String> headers = new ArrayList<>();
		headers.add("abc");
		List<String> messages = new ArrayList<>();
		//When
		int columnCount = new ListSizeValidator(headers).validate(messages);
		//Then
		assertThat(columnCount, is(equalTo(1)));
		assertThat(CollectionUtils.isEmpty(messages), is(true));
	}
	
	@Test
	public void shouldReturnColumnCountWhenEmptyListProvided() {
		//Given
		List<String> headers = new ArrayList<>();
		List<String> messages = new ArrayList<>();
		//When
		int columnCount = new ListSizeValidator(headers).validate(messages);
		//Then
		assertThat(columnCount, is(equalTo(-1)));
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No column header has been passing down")));
	}
	
	@Test
	public void shouldReturnNoParameterMessageWhenLineNumberIsNull() {
		//Given
		List<String> headers = null;
		List<String> messages = new ArrayList<>();
		//When
		int columnCount = new ListSizeValidator(headers).validate(messages);
		//Then
		assertThat(columnCount, is(equalTo(-1)));
		assertThat(CollectionUtils.isEmpty(messages), is(false));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No column header has been passing down")));
	}
}
