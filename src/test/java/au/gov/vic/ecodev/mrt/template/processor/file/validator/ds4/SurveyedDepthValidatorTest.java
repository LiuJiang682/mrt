package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;

public class SurveyedDepthValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", Ds4ColumnHeaders.SURVEYED_DEPTH.getCode(), "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode())));
	}
	
	@Test
	public void shouldReturnUsingDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Depth column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("Depth")));
	}
	
	@Test
	public void shouldReturnUsingSurveyedSpaceDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Surveyed Depth column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("Surveyed Depth")));
	}
	
	@Test
	public void shouldReturnUsingDepthSpaceSurveyedAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth Surveyed", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Depth Surveyed column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("Depth Surveyed")));
	}
	
	@Test
	public void shouldReturnUsingSurveyedHyphenDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed-Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found Surveyed-Depth column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("Surveyed-Depth")));
	}
	
	@Test
	public void shouldReturnUsingSurveyedDepthAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "SurveyedDepth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found SurveyedDepth column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("SurveyedDepth")));
	}
	
	@Test
	public void shouldReturnUsingDepthSurveyedAsSurveyedDepthWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "DepthSurveyed", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Surveyed_Depth column, found DepthSurveyed column, use it as Surveyed_Depth")));
		List<String> surveryedDepthList = params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo("DepthSurveyed")));
	}
	
	@Test
	public void shouldReturnMissingSurveyedDepthMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SurveyedDepthValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("ERROR: H1000 requires the Surveyed_Depth column")));
		assertThat(params.get(Ds4ColumnHeaders.SURVEYED_DEPTH.getCode()), is(nullValue()));
	}
}
