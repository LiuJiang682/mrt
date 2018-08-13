package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
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
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class DValidatorTest {

	private Map<String, List<String>> params;
	private Template mockDataBean;

	@Test
	public void shouldIncreaseTheRecordCount() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "210",  "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenPrecisionParams();
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("1")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D1")));
		doValuesAssert(valueCaptor);
	}

	@Test
	public void shouldIncreaseTheRecordCountOnTop() {
		givenTestConditions();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("1"));
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		String[] datas = { "D", "KPDD001", "210", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("2")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D2")));
		doValuesAssert(valueCaptor);
	}

	@Test
	public void shouldResetTheRecordCount() {
		givenTestConditions();
		params.put(Strings.NUMBER_OF_DATA_RECORDS_ADDED, Arrays.asList("abc"));
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		String[] datas = { "D", "KPDD001", "210", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(false));
		assertThat(params.get(Strings.NUMBER_OF_DATA_RECORDS_ADDED).get(0), is(equalTo("1")));
		ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<MrtTemplateValue> valueCaptor = ArgumentCaptor.forClass(MrtTemplateValue.class);
		verify(mockDataBean).put(keyCaptor.capture(), valueCaptor.capture());
		assertThat(keyCaptor.getValue(), is(equalTo("D1")));
		doValuesAssert(valueCaptor);
	}

	@Test
	public void shouldReturnIncorrectLineNumber() {
		givenMockTemplate();
		params = new HashMap<>();
		String[] datas = null;
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Not current line number has been passing down!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectColumnSizeMessageWhenStrsIsNull() {
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = null;
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 4 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectColumnSizeMessage() {
		// Given
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 4 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "210",  "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		params.get(Strings.COLUMN_HEADERS).set(3, "xxx");
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6:  Missing either Azimuth_MAG or Azimuth_TRUE")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidAzimuthMagValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "210",  "-90", "370" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenPrecisionParams();
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Azimuth_MAG is expected as a number between 0 to 360, but got: 370")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingDipColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "210", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		params.get(Strings.COLUMN_HEADERS).set(2, "xxx");
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6:  Missing Dip column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidDipValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001",  "210",  "-91", "310" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenPrecisionParams();
		params.put(Ds4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Dip is expected as a number between -90 to 90, but got: -91")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenPrecisionParams() {
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
	}

	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getDs4MandatoryColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
	}

	private void givenCurrentLineNumber(Map<String, List<String>> params) {
		String[] lineNumbers = { "6" };
		params.put(Strings.CURRENT_LINE, Arrays.asList(lineNumbers));
	}

	private void givenDrillCode(Map<String, List<String>> params) {
		List<String> drillCodes = Arrays.asList(new String[] { "DD", "RC" });
		params.put(H0400Validator.DRILL_CODE_TITLE, drillCodes);
	}

	private void givenMockTemplate() {
		mockDataBean = Mockito.mock(Template.class);
	}

	private void givenTestConditions() {
		givenMandatoryFields();
		givenCurrentLineNumber(params);
		givenDrillCode(params);
		givenMockTemplate();
	}

	private void doValuesAssert(ArgumentCaptor<MrtTemplateValue> valueCaptor) {
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("KPDD001")));
		assertThat(values.get(1), is(equalTo("210")));
		assertThat(values.get(2), is(equalTo("-90")));
		assertThat(values.get(3), is(equalTo("270")));
	}
}
