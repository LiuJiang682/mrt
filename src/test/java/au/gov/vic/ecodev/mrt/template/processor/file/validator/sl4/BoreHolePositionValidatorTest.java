package au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.map.services.VictoriaMapServices;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator;

public class BoreHolePositionValidatorTest {

	private TemplateProcessorContext mockContext;
	private BoreHolePositionValidator testInstance;
	private Map<String, List<String>> templateParamMap;
	
	@Test
	public void shouldReturnNoMessageWhenCorrect54NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		given54RelatedParams();
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect54NorthingEastingCooridateProvidedIsNotInTenement() {
		//Given
		givenTestInstance();
		given54RelatedParams();
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: 392200, 6589600 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementMga54NorthEast(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("392200"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("6589600"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect54NorthingEastingCooridateNoTenement() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementMga54NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnNoMessageWhenCorrect54LatitudeLongitudeCooridateProvided() {
		//Given
		givenLatitudeLongitudeTestInstance(null);
		given54RelatedParams();
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga54LatitudeLongitude(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-35.33781"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("143.5544"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhen54LatitudeLongitudeCooridateIsNotInTenement() {
		//Given
		givenLatitudeLongitudeTestInstance(null);
		given54RelatedParams();
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: -35.33781, 143.5544 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-35.33781"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("143.5544"))));
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementMga54LatitudeLongitude(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("-35.33781"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("143.5544"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhen54LatitudeLongitudeNoTenement() {
		//Given
		givenLatitudeLongitudeTestInstance(null);
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-35.33781"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("143.5544"))));
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementMga54LatitudeLongitude(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnNoMessageWhenCorrectAmg54NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		given54RelatedParams();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementAgd54NorthEast(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("392200"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("6589600"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrectAmg54NorthingEastingCooridateProvidedIsNotInTenement() {
		//Given
		givenTestInstance();
		given54RelatedParams();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: 392200, 6589600 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementAgd54NorthEast(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("392200"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("6589600"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrectAmg54NorthingEastingCooridateNoTenementProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd54NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementAgd54NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnNoMessageWhenCorrect55NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect55NorthingEastingCooridateProvidedIsNotInTenement() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: 392200, 6589600 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementMga55NorthEast(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("392200"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("6589600"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect55NorthingEastingCooridateNoTenementProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementMga55NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnNoMessageWhenCorrectAmg55NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrectAmg55NorthingEastingCooridateProvidedIsNotInTenement() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: 392200, 6589600 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementAgd55NorthEast(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("392200"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("6589600"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrectAmg55NorthingEastingCooridateNoTenementProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementAgd55NorthEast(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementAgd55NorthEast(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnErrorMessageWhenIncorred54NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1's bore hole coordinate 392200.0, 6589600.0 are not inside Victoria")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnErrorMessageWhenIncorredAmg54NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd54NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1's bore hole coordinate 392200.0, 6589600.0 are not inside Victoria")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd54NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnErrorMessageWhenIncorred55NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1's bore hole coordinate 392200.0, 6589600.0 are not inside Victoria")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnErrorMessageWhenIncorredAmg55NorthingEastingCooridateProvided() {
		//Given
		givenTestInstance();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinAgd55NorthEast(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1's bore hole coordinate 392200.0, 6589600.0 are not inside Victoria")));
		ArgumentCaptor<BigDecimal> eastingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> northingCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinAgd55NorthEast(eastingCaptor.capture(), 
				northingCaptor.capture());
		assertCoordinate(eastingCaptor, northingCaptor);
	}
	
	@Test
	public void shouldReturnNoMessageWhenCorrect55LatitudeLongitudeProvided() {
		//Given
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55LatitudeLongitude(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "-38.11095", "147.06802" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		testInstance = new BoreHolePositionValidator(strs, batchId, 
				templateParamMap, mockContext);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(0)));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-38.11095"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("147.06802"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect55LatitudeLongitudeProvided() {
		//Given
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55LatitudeLongitude(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		when(mockContext.getMessage()).thenReturn(new DefaultMessage());
		String[] strs = { "D", "KPDD001", null, null, "320", "210", "DD", "80", "-38.11095", "147.06802" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
		testInstance = new BoreHolePositionValidator(strs, batchId, 
				templateParamMap, mockContext);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: Line number 1 bore hole ID: KPDD001, coordinate: -38.11095, 147.06802 is not inside the tenement: EL123")));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-38.11095"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("147.06802"))));
		ArgumentCaptor<String> tnoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<BigDecimal> latitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeTnoCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinTenementMga55LatitudeLongitude(tnoCaptor.capture(),
				latitudeTnoCaptor.capture(), 
				longitudeTnoCaptor.capture());
		String capturedTno = tnoCaptor.getValue();
		assertThat(capturedTno, is(equalTo("EL123")));
		BigDecimal capturedLatitudeTno = latitudeTnoCaptor.getValue();
		assertThat(capturedLatitudeTno, is(equalTo(new BigDecimal("-38.11095"))));
		BigDecimal caputedLongitudeTno = longitudeTnoCaptor.getValue();
		assertThat(caputedLongitudeTno, is(equalTo(new BigDecimal("147.06802"))));
	}
	
	@Test
	public void shouldReturnWarningMessageWhenCorrect55LatitudeLongitudeNoTenementProvided() {
		//Given
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockVictoriaMapServices.isWithinTenementMga55LatitudeLongitude(Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(false);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "-38.11095", "147.06802" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		testInstance = new BoreHolePositionValidator(strs, batchId, 
				templateParamMap, mockContext);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-38.11095"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("147.06802"))));
		
		verify(mockVictoriaMapServices, times(0)).isWithinTenementMga55LatitudeLongitude(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnWarningMessageWhen55NoTenementProvided() {
		//Given
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockVictoriaMapServices.isWithinMga55LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class))).thenReturn(true);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "-38.11095", "147.06802" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("55"));
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		testInstance = new BoreHolePositionValidator(strs, batchId, 
				templateParamMap, mockContext);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("WARNING: No tenement number provided!")));
		ArgumentCaptor<BigDecimal> latitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<BigDecimal> longitudeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		verify(mockVictoriaMapServices).isWithinMga55LatitudeLongitude(latitudeCaptor.capture(), 
				longitudeCaptor.capture());
		BigDecimal capturedLatitude = latitudeCaptor.getValue();
		assertThat(capturedLatitude, is(equalTo(new BigDecimal("-38.11095"))));
		BigDecimal caputedLongitude = longitudeCaptor.getValue();
		assertThat(caputedLongitude, is(equalTo(new BigDecimal("147.06802"))));
		verify(mockVictoriaMapServices, times(0)).isWithinTenementMga55LatitudeLongitude(
				Matchers.anyString(),
				Matchers.any(BigDecimal.class),
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnErrorMessageWhenNoColumnHeaderProvided() {
		//Given
		givenTestInstance();
		templateParamMap.remove(Strings.COLUMN_HEADERS);
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: No column header has been passing down")));
	}
	
	@Test
	public void shouldReturnErrorMessageWhenProjectZoneIsNotProvided() {
		//Given
		givenTestInstance();
		List<String> messages = new ArrayList<>();
		//When
		testInstance.validate(messages);
		//Then
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: No projection_zone passing down")));
	}
	
	@Test
	public void shouldReturnMissingLatitudeHeaderMessageWhenNotLatitudeColumnHeaderProvided() {
		//Given
		givenLatitudeLongitudeTestInstance(null);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		columnHeaders.set(7, "");
		List<String> messages = new ArrayList<>();
		//When
		BigDecimal latitude = testInstance.getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LATITUDE.getCode());
		assertThat(latitude, is(nullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 is missing header Latitude")));
	}
	
	@Test
	public void shouldReturnInvalidNumberDataMessageWhenInvalidLatitudeProvided() {
		//Given
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "abc", "143.5544" };
		givenLatitudeLongitudeTestInstance(strs);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		List<String> messages = new ArrayList<>();
		//When
		BigDecimal latitude = testInstance.getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LATITUDE.getCode());
		assertThat(latitude, is(nullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 has invalid data: abc for column: Latitude")));
	}
	
	@Test
	public void shouldReturnMissingLongitudeHeaderMessageWhenNotLatitudeColumnHeaderProvided() {
		//Given
		givenLatitudeLongitudeTestInstance(null);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		columnHeaders.set(8, "");
		List<String> messages = new ArrayList<>();
		//When
		BigDecimal latitude = testInstance.getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LONGITUDE.getCode());
		assertThat(latitude, is(nullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 is missing header Longitude")));
	}
	
	@Test
	public void shouldReturnInvalidNumberDataMessageWhenInvalidLongitudeProvided() {
		//Given
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "-35.33781", "abc" };
		givenLatitudeLongitudeTestInstance(strs);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		List<String> messages = new ArrayList<>();
		//When
		BigDecimal latitude = testInstance.getCoordinateData(messages, columnHeaders, 
				SL4ColumnHeaders.LONGITUDE.getCode());
		assertThat(latitude, is(nullValue()));
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 has invalid data: abc for column: Longitude")));
	}
	
	@Test
	public void shouldReturnInvalidNumberDataMessageWhenDoLatitudeLongitudeCheckWithInvalidLatitude() {
		//Given
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "abc", "143.5544" };
		givenLatitudeLongitudeTestInstance(strs);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		List<String> tnoList = new ArrayList<>();
		//When
		testInstance.doLatitudeLongitudeCheck(messages, "KPDD001", "54", columnHeaders, 
				mockVictoriaMapServices, tnoList);
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 has invalid data: abc for column: Latitude")));
		verify(mockVictoriaMapServices, times(0)).isWithinMga54LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnInvalidNumberDataMessageWhenDoLatitudeLongitudeCheckWithInvalidLongitude() {
		//Given
		String[] strs = { "D", "", null, null, "320", "210", "DD", "80", "-35.33781", "abc" };
		givenLatitudeLongitudeTestInstance(strs);
		List<String> columnHeaders = templateParamMap.get(Strings.COLUMN_HEADERS);
		VictoriaMapServices mockVictoriaMapServices = Mockito.mock(VictoriaMapServices.class);
		when(mockContext.getMapServices()).thenReturn(mockVictoriaMapServices);
		List<String> messages = new ArrayList<>();
		List<String> tnoList = new ArrayList<>();
		//When
		testInstance.doLatitudeLongitudeCheck(messages, "KPDD001", "54", columnHeaders, 
				mockVictoriaMapServices, tnoList);
		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0), is(equalTo("ERROR: Line number 1 has invalid data: abc for column: Longitude")));
		verify(mockVictoriaMapServices, times(0)).isWithinMga54LatitudeLongitude(Matchers.any(BigDecimal.class), 
				Matchers.any(BigDecimal.class));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamStrsIsNull() {
		//Given
		//when
		new BoreHolePositionValidator(null, 0, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateParamMapIsNull() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "310" };
		//when
		new BoreHolePositionValidator(strs, 0, null, null);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenParamContextIsNull() {
		//Given
		String[] strs = { "D", "", "392200", "6589600", "320", "210", "DD", "80", "310" };
		//when
		new BoreHolePositionValidator(strs, 0, new HashMap<String, List<String>>(),
				null);
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		String[] strs = { "D", "KPDD001", "392200", "6589600", "320", "210", "DD", "80", "310" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getColumnHeaderList());
		
		testInstance = new BoreHolePositionValidator(strs, batchId, 
				templateParamMap, mockContext);
	}
	
	private void givenLatitudeLongitudeTestInstance(String[] strsParam) {
		mockContext = Mockito.mock(TemplateProcessorContext.class);
		String[] strs = { "D", "KPDD001", null, null, "320", "210", "DD", "80", "-35.33781", "143.5544" };
		long batchId = 1l;
		templateParamMap = new HashMap<>();
		templateParamMap.put(Strings.COLUMN_HEADERS, TestFixture.getAMGColumnHeaderList());
		
		testInstance = new BoreHolePositionValidator((null == strsParam) ? strs : strsParam, 
				batchId, templateParamMap, mockContext);
	}
	
	private void assertCoordinate(ArgumentCaptor<BigDecimal> eastingCaptor, ArgumentCaptor<BigDecimal> northingCaptor) {
		BigDecimal capturedEasting = eastingCaptor.getValue();
		assertThat(capturedEasting, is(equalTo(new BigDecimal("392200"))));
		BigDecimal capturedNorthing = northingCaptor.getValue();
		assertThat(capturedNorthing, is(equalTo(new BigDecimal("6589600"))));
	}
	
	private void given54RelatedParams() {
		templateParamMap.put(Strings.TITLE_PREFIX + H0531Validator.PROJECTION_ZONE_TITLE, 
				Arrays.asList("54"));
		templateParamMap.put(Strings.KEY_H0100, 
				Arrays.asList("Tenement_no", "EL123"));
	}
}
