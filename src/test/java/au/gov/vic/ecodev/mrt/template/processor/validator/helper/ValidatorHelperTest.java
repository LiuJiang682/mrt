package au.gov.vic.ecodev.mrt.template.processor.validator.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class ValidatorHelperTest {
	
	@Test
	public void shouldReturnEmptyMessagesWhenEmptyMessagePassInWithNullDataBean() {
		//Given
		List<String> messages = new ArrayList<>();
		Template mockTemplate = null;
		String[] datas = {"abc", "def"};
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(false));
	}
	
	@Test
	public void shouldReturnMessagesWhenWarningMessagePassInWithNullDataBean() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add(Strings.LOG_INFO_HEADER + "xxx");
		Template mockTemplate = null;
		String[] datas = {"abc", "def"};
		ValidatorHelper testInstance = new ValidatorHelper(messages, true);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(true));
		List<String> messageList = messagesOptional.get();
		assertThat(messageList.size(), is(equalTo(1)));
		assertThat(messageList.get(0), is(equalTo(Strings.LOG_INFO_HEADER + "xxx")));
	}
	
	@Test
	public void shouldReturnMessagesWhenMessagePassIn() {
		//Given
		List<String> messages = new ArrayList<>();
		messages.add("abc");
		Template mockTemplate = Mockito.mock(Template.class);
		String[] datas = {"abc", "def"};
		ValidatorHelper testInstance = new ValidatorHelper(messages, true);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(true));
		List<String> messageList = messagesOptional.get();
		assertThat(messageList.size(), is(equalTo(1)));
		assertThat(messageList.get(0), is(equalTo("abc")));
		verify(mockTemplate, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEmptyMessagesWhenEmptyMessagePassInWithOneData() {
		//Given
		List<String> messages = new ArrayList<>();
		Template mockTemplate = Mockito.mock(Template.class);
		String[] datas = {"abc"};
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(false));
		verify(mockTemplate, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEmptyMessagesWhenEmptyMessagePassInWithEmptyData() {
		//Given
		List<String> messages = new ArrayList<>();
		Template mockTemplate = Mockito.mock(Template.class);
		String[] datas = {};
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(false));
		verify(mockTemplate, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEmptyMessagesWhenEmptyMessagePassInWithNoData() {
		//Given
		List<String> messages = new ArrayList<>();
		Template mockTemplate = Mockito.mock(Template.class);
		String[] datas = null;
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(false));
		verify(mockTemplate, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEmptyMessagesWhenEmptyMessagePassIn() {
		//Given
		List<String> messages = new ArrayList<>();
		Template mockTemplate = Mockito.mock(Template.class);
		String[] datas = {"abc", "def"};
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//When
		Optional<List<String>> messagesOptional = testInstance.updateDataBeanOrCreateErrorOptional(datas, mockTemplate);
		//Then
		assertThat(messagesOptional.isPresent(), is(false));
		verify(mockTemplate).put(eq("abc"), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		List<String> messages = new ArrayList<>();
		//When
		ValidatorHelper testInstance = new ValidatorHelper(messages, false);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenMessagesIsNull() {
		//Given
		List<String> messages = null;
		//When
		new ValidatorHelper(messages, false);
		fail("Program reached unexpected point!");
	}
	

}
