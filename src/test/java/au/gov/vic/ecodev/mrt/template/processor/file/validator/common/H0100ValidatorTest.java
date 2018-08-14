package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0100ValidatorTest {

	private H0100Validator testInstance;
	private Template mockDataBean;
	
	@Test
	public void shouldReturnEmptyErrorMessage() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL123"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL123")));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage() {
		//Given
		String[] strs = {"H0100", "Tenement"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0100 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage0() {
		//Given
		String[] strs = null;
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0100 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectTenementMessage() {
		//Given
		String[] strs = {"H0100", "Tenement", "123"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Tenement must be in the format of abc123, but got: 123")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectTenementMessageWithUnderLineNumber() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538_006036_06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Tenement must be in the format of abc123, but got: EL5538_006036_06333")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithMultiHyphenBreak() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538-EL006036-EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,EL006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithMultiSpaceBreak() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538 EL006036 EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,EL006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithMultiSpaceBreakWithInTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL 5538 EL006036 EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,EL006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithDifferentTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538 MIN006036 EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,MIN006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithCommaBreakTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538,MIN006036,EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,MIN006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithVertialLineTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538|MIN006036|EL06333"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,MIN006036,EL06333")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithMixSpaceTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538 MIN006036 EL 06333 MIN 12345 EL 3310, EL5519"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,MIN006036,EL06333,MIN12345,EL3310,EL5519")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithNoDelimTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538 MIN006036EL06333MIN5396"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0100")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Tenement")));
		assertThat(values.get(1), is(equalTo("EL5538,MIN006036,EL06333,MIN5396")));
	}
	
	@Test
	public void shouldReturnEmptyMessageWithInvalidSecondTenement() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL5538 MIN006036a"};
		givenTestInstance(strs);
		//When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Tenement must be in the format of abc123, but got: EL5538 MIN006036a")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldSetTheStringArray() {
		//Given
		String[] strs = {"H0100", "Tenement", "EL123"};
		testInstance = new H0100Validator();
		//When
		testInstance.init(strs);
		//Then
		assertThat(Whitebox.getInternalState(testInstance, "strs"), is(equalTo(strs)));
	}
	
	private void givenTestInstance(final String[] strs) {
		testInstance = new H0100Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
