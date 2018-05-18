package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

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

public class AzimuthTrueValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("Azimuth_TRUE")));
	}
	
	@Test
	public void shouldReturnUsingTRUEAzimuthAliasAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "TRUE_Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found TRUE_Azimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("TRUE_Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthSpaceTRUEsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found Azimuth TRUE column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("Azimuth TRUE")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthHyphenTRUEAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth-TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found Azimuth-TRUE column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("Azimuth-TRUE")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthTRUEAsAzimuthTrueWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "AzimuthTRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found AzimuthTRUE column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("AzimuthTRUE")));
	}
	
	@Test
	public void shouldReturnUsingTRUESpaceAzimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "TRUE Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found TRUE Azimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("TRUE Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingTRUEHyphenAzimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "TRUE-Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found TRUE-Azimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("TRUE-Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingTRUEAzimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "TRUEAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found TRUEAzimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("TRUEAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingTrueAzimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "TrueAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found TrueAzimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("TrueAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingtrueAzimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "trueAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found trueAzimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("trueAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingtrueazimuthAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "trueazimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found trueazimuth column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("trueazimuth")));
	}
	
	@Test
	public void shouldReturnUsingtrueazimuThAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "trueazimuTh", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found trueazimuTh column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("trueazimuTh")));
	}
	
	@Test
	public void shouldReturnUsingtrueazimuTHAsAzimuthTRUEWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "trueazimuTH", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_TRUE column, found trueazimuTH column, use it as Azimuth_TRUE")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList.get(0), is(equalTo("trueazimuTH")));
	}
	
	@Test
	public void shouldReturnMissingAzimuthTRUEMessageWhenAzimuthStarTRUEProvided() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth*TRUE", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("ERROR: H1000 requires the Azimuth_TRUE column")));
		List<String> AzimuthMagList = params.get("Azimuth_TRUE");
		assertThat(AzimuthMagList, is(nullValue()));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagMessageWhenAzimuthMagNoExist() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthTrueHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("ERROR: H1000 requires the Azimuth_TRUE column")));
		List<String> AzimuthTrueList = params.get("Azimuth_TRUE");
		assertThat(AzimuthTrueList, is(nullValue()));
	}
}
