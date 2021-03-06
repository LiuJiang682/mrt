package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
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

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class H0401ValidatorTest {

	private H0401Validator testInstance;
	private Template mockDataBean;

	@Test
	public void shouldReturnEmptyErrorMessage() {
		// Given
		String[] strs = { "H0401", "Drill_contractor", "DD" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> drillingCodes = params.get(Strings.TITLE_PREFIX 
				+ H0401Validator.DRILL_CONTRACTOR_TITLE);
		assertThat(drillingCodes.size(), is(equalTo(1)));
		assertThat(drillingCodes.get(0), is(equalTo("DD")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0401")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Drill_contractor")));
		assertThat(values.get(1), is(equalTo("DD")));
	}

	@Test
	public void shouldReturnEmptyErrorMessageAnd2DrillingCodes() {
		// Given
		String[] strs = { "H0401", "Drill_contractor", "DD", "RC" };
		givenTestInstance(strs);
		Map<String, List<String>> params = new HashMap<>();
		// When
		Optional<List<String>> errorMessages = testInstance.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.isEmpty(), is(false));
		List<String> drillingCodes = params.get(Strings.TITLE_PREFIX 
				+ H0401Validator.DRILL_CONTRACTOR_TITLE);
		assertThat(drillingCodes.size(), is(equalTo(2)));
		assertThat(drillingCodes.get(0), is(equalTo("DD")));
		assertThat(drillingCodes.get(1), is(equalTo("RC")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("H0401")));
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("Drill_contractor")));
		assertThat(values.get(1), is(equalTo("DD")));
		assertThat(values.get(2), is(equalTo("RC")));
	}

	@Test
	public void shouldReturnIncorrectLengthMessage() {
		// Given
		String[] strs = { "H0401", "State" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0401 requires minimum 3 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
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
		assertThat(messages.get(0), is(equalTo("ERROR: H0401 requires minimum 3 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectStatementMessage() {
		// Given
		String[] strs = { "H0401", "dd", "3" };
		givenTestInstance(strs);
		// When
		Optional<List<String>> errorMessages = testInstance.validate(null, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: H0401 title must be Drill_contractor, but got dd")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	private void givenTestInstance(final String[] strs) {
		testInstance = new H0401Validator();
		testInstance.init(strs);
		mockDataBean = Mockito.mock(Template.class);
	}
}
