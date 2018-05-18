package au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;

public class AzimuthMagAzimuthTrueValidatorTest {

	
	@Test
	public void shouldCallBothAzimuthMagAndAzimuthTrueValidator() {
		//Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_Mag", "Azimuth_TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		//When
		new AzimuthMagAzimuthTrueValidator().validate(errorMessages, params, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		assertThat(params.size(), is(equalTo(2)));
		assertThat(params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode()).get(0), 
				is(equalTo("Azimuth_Mag")));
		assertThat(params.get("Azimuth_TRUE").get(0), 
				is(equalTo("Azimuth_TRUE")));
	}
	
	@Test
	public void shouldReturnOnlyAzimuthMagParamWhenOnlyAzimuthMagProvided() {
		//Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_Mag", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		//When
		new AzimuthMagAzimuthTrueValidator().validate(errorMessages, params, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		assertThat(params.size(), is(equalTo(1)));
		assertThat(params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode()).get(0), 
				is(equalTo("Azimuth_Mag")));
	}
	
	@Test
	public void shouldReturnOnlyAzimuthTrueParamWhenOnlyAzimuthTrueProvided() {
		//Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		//When
		new AzimuthMagAzimuthTrueValidator().validate(errorMessages, params, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		assertThat(params.size(), is(equalTo(1)));
		assertThat(params.get(Strings.AZIMUTH_TRUE).get(0), 
				is(equalTo((Strings.AZIMUTH_TRUE))));
	}
	
	@Test
	public void shouldReturnMissingAzimuthParamWhenNoAzimuthProvided() {
		//Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		//When
		new AzimuthMagAzimuthTrueValidator().validate(errorMessages, params, Arrays.asList(strs));
		//Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.get(0), is(equalTo(AzimuthMagAzimuthTrueValidator.MISSING_AZIMUTH_COLUMN)));
	}
}
