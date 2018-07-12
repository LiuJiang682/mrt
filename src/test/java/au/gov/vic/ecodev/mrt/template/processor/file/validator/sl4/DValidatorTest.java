package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator;
import au.gov.vic.ecodev.mrt.template.processor.model.MrtTemplateValue;
import au.gov.vic.ecodev.mrt.template.processor.model.Template;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DValidator.class)
public class DValidatorTest {

	private Map<String, List<String>> params;
	private Template mockDataBean;
	private VictoriaMapServices mockVictoriaMapServices;

	@Test
	public void shouldIncreaseTheRecordCount() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
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
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
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
		givenMandatoryFields();
		givenDrillCode(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenPrecisionParams();
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 7 columns, only got 0")));
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
		assertThat(messages.get(0), is(equalTo("Line 6 requires minimum 7 columns, only got 2")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnIncorrectEASTING_MGAMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
	public void shouldReturnIncorrectNORTHING_MGAMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
	public void shouldReturnIncorrectEASTING_AMGMessage() {
		givenMandatoryAmgFields();
		TestFixture.givenCurrentLineNumber(params);
		givenDrillCode(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "39220.1", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
	public void shouldReturnIncorrectNorthing_AMGMessage() {
		// Given
		givenMandatoryAmgFields();
		TestFixture.givenCurrentLineNumber(params);
		givenDrillCode(params);
		givenMockTemplate();
		String[] datas = { "D", "KPDD001", "392200", "65896.1", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
	public void shouldReturnIncorrectDrill_CodeMessage() {
		givenTestConditions();
		String[] datas = givenTestDatas();
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6's Drill_Code column must exist in H0400! But got: XX")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}

	@Test
	public void shouldReturnNoDrill_CodeMessage() {
		// Given
		givenMandatoryFields();
		TestFixture.givenCurrentLineNumber(params);
		givenMockTemplate();
		String[] datas = givenTestDatas();
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: No Drill_Code exist at H0400!")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingElvationColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		params.get(Strings.COLUMN_HEADERS).set(3, "xxx");
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Missing Elevation column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidElvationValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "xx", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Elevation is expected as a number, but got: xx")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingTotalDepthColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		params.get(Strings.COLUMN_HEADERS).set(4, "xxx");
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Missing Total Hole Depth column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidTotalDepthValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "xx", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Total Hole Depth is expected as a number, but got: xx")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		params.get(Strings.COLUMN_HEADERS).set(7, "xxx");
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Missing either Azimuth_MAG or Latitude")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidAzimuthMagValueMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "370" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		params.get(Strings.COLUMN_HEADERS).set(6, "xxx");
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
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
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-91", "310" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("Line 6: Dip is expected as a number between -90 to 90, but got: -91")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnMissingHoleIdColumnMessage() {
		givenTestConditions();
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "-90", "270" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		params.get(Strings.COLUMN_HEADERS).set(0, "xxx");
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		givenPrecisionParams();
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(2)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template SL4 missing Hole_id column")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@Test
	public void shouldReturnInvalidHoleIdValueMessage() {
		//Given
		givenTestConditions();
		String[] datas = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "310" };
		DValidator dValidator = new DValidator();
		dValidator.init(datas);
		givenBoreHoleCondition(dValidator);
		givenPrecisionParams();
		params.put(SL4ColumnHeaders.AZIMUTH_MAG.getCode(), TestFixture.givenAzimuthMagList());
		// When
		Optional<List<String>> errorMessages = dValidator.validate(params, mockDataBean);
		// Then
		assertThat(errorMessages.isPresent(), is(true));
		List<String> messages = errorMessages.get();
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line 6: Template SL4 column Hole_id cannot be null or empty")));
		verify(mockDataBean, times(0)).put(Matchers.anyString(), Matchers.anyListOf(String.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldCallBoreHolePositionValidatorWhenDataBeanExist() throws Exception {
		//Given
		params = new HashMap<>();
		givenMockTemplate();
		DValidator dValidator = new DValidator();
		List<String> messages = new ArrayList<>();
		BoreHolePositionValidator mockBoreHolePositionValidator = PowerMockito.mock(BoreHolePositionValidator.class);
		PowerMockito.whenNew(BoreHolePositionValidator.class).withAnyArguments()
			.thenReturn(mockBoreHolePositionValidator);
		//When
		dValidator.doBoreHolePositionValidation(params, mockDataBean, messages, 0);
		//Then
		verify(mockBoreHolePositionValidator).validate(Matchers.anyList());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotCallBoreHolePositionValidatorWhenDataBeanIsNull() throws Exception {
		//Given
		params = new HashMap<>();
		DValidator dValidator = new DValidator();
		List<String> messages = new ArrayList<>();
		BoreHolePositionValidator mockBoreHolePositionValidator = PowerMockito.mock(BoreHolePositionValidator.class);
		PowerMockito.whenNew(BoreHolePositionValidator.class).withAnyArguments()
			.thenReturn(mockBoreHolePositionValidator);
		//When
		dValidator.doBoreHolePositionValidation(params, mockDataBean, messages, 0);
		//Then
		verify(mockBoreHolePositionValidator, times(0)).validate(Matchers.anyList());
	}
	
	private void givenPrecisionParams() {
		params.put(Strings.AZIMUTH_MAG_PRECISION, Arrays.asList("6"));
		params.put(Strings.DIP_PRECISION, Arrays.asList("6"));
	}

	private void givenMandatoryFields() {
		String[] mandatoryFields = TestFixture.getMandatoryColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
	}

	private void givenMandatoryAmgFields() {
		String[] mandatoryFields = TestFixture.getMandatoryAmgColumns();
		params = new HashMap<>();
		params.put(Strings.COLUMN_HEADERS, Arrays.asList(mandatoryFields));
	}

	private void givenDrillCode(Map<String, List<String>> params) {
		List<String> drillCodes = Arrays.asList(new String[] { "DD", "RC" });
		params.put(Strings.TITLE_PREFIX + H0400Validator.DRILL_CODE_TITLE, drillCodes);
	}

	private void givenMockTemplate() {
		mockDataBean = Mockito.mock(Template.class);
	}

	private void givenTestConditions() {
		givenMandatoryFields();
		TestFixture.givenCurrentLineNumber(params);
		givenDrillCode(params);
		givenMockTemplate();
	}

	protected void doValuesAssert(ArgumentCaptor<MrtTemplateValue> valueCaptor) {
		MrtTemplateValue value = valueCaptor.getValue();
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

	private String[] givenTestDatas() {
		String[] datas = { "D", "KPDD001", "392200", "6589600", "320", "210", "XX", "-90", "270" };
		return datas;
	}
	
	private void givenBoreHoleCondition(DValidator dValidator) {
		TemplateProcessorContext mockContext = Mockito.mock(TemplateProcessorContext.class);
		mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), Matchers.any(BigDecimal.class)))
			.thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54NorthEast(
				Matchers.anyString(), Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class)))
			.thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		dValidator.setTemplateProcessorContext(mockContext);
		params.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		when(mockDataBean.get(Strings.KEY_H0100)).thenReturn(Arrays.asList("Tenement_no", "EL123"));
	}
}
