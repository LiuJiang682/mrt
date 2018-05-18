package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0802ValidatorTest {

	private H0802Validator testInstance;
	private Template mockDataBean;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0802", "Assay_description", "DD"};
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE, 
				Arrays.asList("DD"));
		params.put(Strings.TITLE_PREFIX + H0801Validator.ASSAY_COMPANY_TITLE, 
				Arrays.asList("DD"));
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> drillingCodes = params.get(Strings.TITLE_PREFIX +
				H0802Validator.ASSAY_DESCRIPTION_TITLE);
		assertThat(drillingCodes.size(), is(equalTo(1)));
		assertThat(drillingCodes.get(0), is(equalTo("DD")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0802")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Assay_description")));
		assertThat(values.get(1), is(equalTo("DD")));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void shouldReturnEmptyErrorMessageAnd2DrillingCodes() {
		// Given
		String[] strs = { "H0802", "Assay_description", "DD", "RC" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		params.put(Strings.TITLE_PREFIX + H0800Validator.ASSAY_CODE_TITLE, 
				TestFixture.getHeadlessDrillingCode());
		params.put(Strings.TITLE_PREFIX + H0801Validator.ASSAY_COMPANY_TITLE, 
				TestFixture.getHeadlessDrillingCompanyList());
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> drillingCodes = params.get(Strings.TITLE_PREFIX 
				+ H0802Validator.ASSAY_DESCRIPTION_TITLE);
		assertThat(drillingCodes.size(), is(equalTo(2)));
		assertThat(drillingCodes.get(0), is(equalTo("DD")));
		assertThat(drillingCodes.get(1), is(equalTo("RC")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0802")));
		List<String> values = valueCaptor.getValue();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Assay_description")));
		assertThat(values.get(1), is(equalTo("DD")));
		assertThat(values.get(2), is(equalTo("RC")));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0802", "State" };
		givenTestInstance(strs);
		Map<String, List<String>> paramMaps = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(paramMaps, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0802 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage0() {
		// Given
		String[] strs = null;
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0802 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0802", "dd", "3" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(3)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0802 title must be Assay_description, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H0802Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
