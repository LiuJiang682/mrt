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

import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;

public class AzimuthMagValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed_Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo(Ds4ColumnHeaders.AZIMUTH_MAG.getCode())));
	}
	
	@Test
	public void shouldReturnUsingMagAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "MAG_Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found MAG_Azimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("MAG_Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthSpaceMAGAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found Azimuth MAG column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("Azimuth MAG")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthHyphenMAGAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth-MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found Azimuth-MAG column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("Azimuth-MAG")));
	}
	
	@Test
	public void shouldReturnUsingAzimuthMAGAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "AzimuthMAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found AzimuthMAG column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("AzimuthMAG")));
	}
	
	@Test
	public void shouldReturnUsingMAGSpaceAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "MAG Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found MAG Azimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("MAG Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingMAGHyphenAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "MAG-Azimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found MAG-Azimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("MAG-Azimuth")));
	}
	
	@Test
	public void shouldReturnUsingMAGAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "MAGAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found MAGAzimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("MAGAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingMaGAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "MaGAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found MaGAzimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("MaGAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingmagAzimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "magAzimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found magAzimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("magAzimuth")));
	}
	
	@Test
	public void shouldReturnUsingmagazimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "magazimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found magazimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("magazimuth")));
	}
	
	@Test
	public void shouldReturnUsingmagaZimuthAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "magaZimuth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found magaZimuth column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("magaZimuth")));
	}
	
	@Test
	public void shouldReturnUsingmagaZimuTHAsAzimuthMagWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "magaZimuTH", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: H1000 requires the Azimuth_MAG column, found magaZimuTH column, use it as Azimuth_MAG")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList.get(0), is(equalTo("magaZimuTH")));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagMessageWhenAzimuthStarMAGProvided() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Azimuth*MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("ERROR: H1000 requires the Azimuth_MAG column")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList, is(nullValue()));
	}
	
	@Test
	public void shouldReturnMissingAzimuthMagMessageWhenAzimuthMagNoExist() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Surveyed Depth", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new AzimuthMagHeaderValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("ERROR: H1000 requires the Azimuth_MAG column")));
		List<String> AzimuthMagList = params.get(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		assertThat(AzimuthMagList, is(nullValue()));
	}
}
