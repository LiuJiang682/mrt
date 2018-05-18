package au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4;

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

import au.gov.vic.ecodev.mrt.template.fields.Dl4ColumnHeaders;

public class DepthFromValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", Dl4ColumnHeaders.DEPTH_FROM.getCode(), "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> surveryedDepthList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(surveryedDepthList.get(0), is(equalTo(Dl4ColumnHeaders.DEPTH_FROM.getCode())));
	}
	
	@Test
	public void shouldReturnUsingDepthAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found Depth column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Depth")));
	}
	
	@Test
	public void shouldReturnUsingDepthSpaceFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found Depth From column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Depth From")));
	}
	
	@Test
	public void shouldReturnUsingFromSpaceDepthAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "From Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found From Depth column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("From Depth")));
	}
	
	@Test
	public void shouldReturnUsingDepthHyphenFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth-From", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found Depth-From column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Depth-From")));
	}
	
	@Test
	public void shouldReturnUsingDepthFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "DepthFrom", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found DepthFrom column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("DepthFrom")));
	}
	
	@Test
	public void shouldReturnUsingFromDepthAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "FromDepth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found FromDepth column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("FromDepth")));
	}
	
	@Test
	public void shouldReturnUsingFromHyphenDepthAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "From-Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template Dl4 H1000 row requires the Depth_From column, found From-Depth column, use it as Depth_From")));
		List<String> depthFromList = params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("From-Depth")));
	}
	
	@Test
	public void shouldReturnMissingDepthFromMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromValidator().validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("ERROR: Template Dl4 H1000 row requires the Depth_From column")));
		assertThat(params.get(Dl4ColumnHeaders.DEPTH_FROM.getCode()), is(nullValue()));
	}
}
