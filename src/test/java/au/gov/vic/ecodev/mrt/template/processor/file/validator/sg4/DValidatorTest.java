package au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.fields.Sg4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

public class DValidatorTest {

	private Map<String, List<String>> params;
	private Template mockDataBean;
	private TemplateProcessorContext mockContext;
	
	private DValidator dValidator;
	private VictoriaMapServices mockVictoriaMapServices;
	
	@Test
	public void shouldIncreaseTheRecordCount() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
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
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
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
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
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
	public void shouldReturnIncorrectEASTING_MGAMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo(
				"Line 6's Easting_MGA column must be either integer or has 6 digits before the decimal point! But got: 39220.1")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectEASTING_AMGMessage() {
		givenAmgTestConditions();
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo(
				"Line 6's Easting_AMG column must be either integer or has 6 digits before the decimal point! But got: 39220.1")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectNORTHING_MGAMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo(
				"Line 6's Northing_MGA column must be either integer or has 6 digits before the decimal point! But got: 65896.1")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectNORTHING_AMGMessage() {
		givenAmgTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo(
				"Line 6's Northing_AMG column must be either integer or has 6 digits before the decimal point! But got: 65896.1")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingSampleIDColumnMessage() {
		//Given
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		params.get(Strings.COLUMN_HEADERS).set(0, "xxx");
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template SG4 missing Sample ID column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingSampleTypeColumnMessage() {
		//Given
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		givenTestInstance(datas);
		params.get(Strings.COLUMN_HEADERS).set(3, "xxx");
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template SG4 missing Sample_type column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnNoColumnHeadersMessageWhenColumnHeadersIsNotProvided() {
		//Given
		params = new HashMap<>();
		TestFixture.givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		//When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		//Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("No column header has been passing down")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectLineNumber() {
		//Given
		givenMandatoryFields();
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
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
		//Given
		givenMandatoryFields();
		TestFixture.givenCurrentLineNumber(params);
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 5 columns, only got 0")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnIncorrectColumnSizeMessage() {
		// Given
		givenMandatoryFields();
		TestFixture.givenCurrentLineNumber(params);
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 5 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	private void givenMandatoryAmgFields() {
		String[] mandatoryFields = TestFixture.getSg4MandatoryAmgColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
		params.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		params.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		params.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, Arrays.asList("54"));
		params.put(Strings.KEY_H0100, Arrays.asList("Tenemnet_no", "EL100"));
	}
	
	protected void doValuesAssert(ArgumentCaptor<MrtTemplateValue> valueCaptor) {
		MrtTemplateValue value = valueCaptor.getValue();
		assertThat(value, is(notNullValue()));
		List<String> values = value.getDatas();
		assertThat(values.isEmpty(), is(false));
		assertThat(values.get(0), is(equalTo("KPDD001")));
		assertThat(values.get(1), is(equalTo("392200")));
		assertThat(values.get(2), is(equalTo("6589600")));
		assertThat(values.get(3), is(equalTo("320")));
		assertThat(values.get(4), is(equalTo("210")));
		assertThat(values.get(5), is(equalTo("DD")));
		assertThat(values.get(6), is(equalTo("-90")));
		assertThat(values.get(7), is(equalTo("270")));
	}
	
	private void givenTestConditions() {
		givenMandatoryFields();
		TestFixture.givenCurrentLineNumber(params);
		givenMockTemplate();
		givenMockContext();
	}

	private void givenMockContext() {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54NorthEast(Matchers.anyString(), 
				Matchers.any(BigDecimal.class), Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
	}
	
	private void givenMockTemplate() {
		mockDataBean = Mockito.mock(Template.class);
	}
	
	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getSg4MandatoryColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
		params.put(Sg4ColumnHeaders.SAMPLE_ID.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_ID.getCode()));
		params.put(Sg4ColumnHeaders.SAMPLE_TYPE.getCode(), 
				Arrays.asList(Sg4ColumnHeaders.SAMPLE_TYPE.getCode()));
		params.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, Arrays.asList("54"));
		params.put(Strings.KEY_H0100, Arrays.asList("Tenemnet_no", "EL100"));
	}
	
	private void givenAmgTestConditions() {
		givenMandatoryAmgFields();
		TestFixture.givenCurrentLineNumber(params);
		givenMockTemplate();
		givenMockContext();
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(
				Matchers.any(BigDecimal.class), Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(), 
				Matchers.any(BigDecimal.class), Matchers.any(BigDecimal.class))).thenReturn(true);
	}
	
	private void givenTestInstance(String[] datas) {
		dValidator = new DValidator();
		dValidator.init(datas);
		dValidator.setTemplateProcessorContext(mockContext);
	}
}
