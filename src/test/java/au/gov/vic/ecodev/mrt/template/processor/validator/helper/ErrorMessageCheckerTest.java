package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;

public class ErrorMessageCheckerTest {

	@Test
	public void shouldReturnInstance() {
		// Given
		List<String> messages = new ArrayList<>();
		// When
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		// Then
		assertThat(testInstance, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTheMessagesIsNull() {
		// Given
		List<String> messages = null;
		// When
		new ErrorMessageChecker(messages);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnFalseWhenMessageIsEmpty() {
		//Given
		List<String> messages = new ArrayList<>();
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenMessageOnlyHaveWarning() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_WARNING_HEADER + "xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenMessageOnlyHaveInfo() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_INFO_HEADER + "xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenMessageOnlyHaveInfoAndWarning() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_INFO_HEADER + "xxx");
		messages.add(Strings.LOG_WARNING_HEADER + "xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenMessageHasNotHeader() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add("xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenMessageHasErrorHeader() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_ERROR_HEADER + "xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenMessageHasMultiErrorHeader() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_INFO_HEADER + "xxx");
		messages.add(Strings.LOG_WARNING_HEADER + "xxx");
		messages.add("xxx");
		messages.add(Strings.LOG_WARNING_HEADER + "xxx");
		messages.add(Strings.LOG_ERROR_HEADER + "xxx");
		ErrorMessageChecker testInstance = new ErrorMessageChecker(messages);
		//When
		boolean flag = testInstance.isContainsErrorMessages();
		//Then
		assertThat(flag, is(true));
	}
}
