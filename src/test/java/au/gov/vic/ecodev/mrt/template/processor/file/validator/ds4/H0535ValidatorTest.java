package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0535ValidatorTest {

	private H0535Validator testInstance;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnNoErrorMessage() {
		// Given
		String[] strs = { "H0535", "Downhole_Direction_Survey_Company", "SS" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0535")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(2)));
		assertThat(values.get(0), is(equalTo("Downhole_Direction_Survey_Company")));
		assertThat(values.get(1), is(equalTo("SS")));
		assertThat(CollectionUtils.isEmpty(params.get("H0535")), is(false));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnNoErrorMessageWith2Strings() {
		// Given
		String[] strs = { "H0535", "Downhole_Direction_Survey_Company" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0535")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.size(), is(equalTo(1)));
		assertThat(values.get(0), is(equalTo("Downhole_Direction_Survey_Company")));
		assertThat(CollectionUtils.isEmpty(params.get("H0535")), is(false));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage0() {
		// Given
		String[] strs = null;
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0535 requires minimum 2 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0535" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("H0535 requires minimum 2 columns, only got 1")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0535", "dd", "SS" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		final String expectedMsg = "H0535 title must be Downhole_Direction_Survey_Company, but got dd";
		final String retrievedMsg = messages.get(0);
		assertThat(retrievedMsg, is(equalTo(expectedMsg)));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenTestInstance(final String[] strs) {
		testInstance = new H0535Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
