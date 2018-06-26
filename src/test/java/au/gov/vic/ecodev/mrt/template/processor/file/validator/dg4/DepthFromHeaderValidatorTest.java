package au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;

public class DepthFromHeaderValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth From", "Azimuth_MAG", "Dip", "Survey_instrument" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.FROM.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo(Dg4ColumnHeaders.FROM.getCode())));
	}
	
	@Test
	public void shouldReturnUsingDepthUnderLineFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth_From", "Azimuth_MAG", "Dip", "Survey_instrument" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.FROM.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Depth From column, found Depth_From column, use it as Depth From")));
		List<String> depthFromList = params.get(Strings.TITLE_PREFIX + Dg4ColumnHeaders.FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Depth_From")));
	}
	
	@Test
	public void shouldReturnUsingDepthHypenFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "Depth-From" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.FROM.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Depth From column, found Depth-From column, use it as Depth From")));
		List<String> depthFromList = params.get(Strings.TITLE_PREFIX + Dg4ColumnHeaders.FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Depth-From")));
	}
	
	@Test
	public void shouldReturnUsingDepthFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "DepthFrom" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.FROM.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Depth From column, found DepthFrom column, use it as Depth From")));
		List<String> depthFromList = params.get(Strings.TITLE_PREFIX + Dg4ColumnHeaders.FROM.getCode());
		assertThat(depthFromList.get(0), is(equalTo("DepthFrom")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String template = "DG4";
		String row = "H1000";
		String code = Dg4ColumnHeaders.FROM.getCode();
		//When
		DepthFromHeaderValidator testInstance = new DepthFromHeaderValidator(template, row, code);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		String template = null;
		String row = null;
		String code = null;
		//When
		new DepthFromHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRowIsNull() {
		String template = "DG4";
		String row = null;
		String code = null;
		//When
		new DepthFromHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		String template = "DG4";
		String row = "H1000";
		String code = null;
		//When
		new DepthFromHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnMissingDepthFromMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code1" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DepthFromHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.FROM.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Depth From column")));
		assertThat(params.get(Dg4ColumnHeaders.FROM.getCode()), is(nullValue()));
	}
}
