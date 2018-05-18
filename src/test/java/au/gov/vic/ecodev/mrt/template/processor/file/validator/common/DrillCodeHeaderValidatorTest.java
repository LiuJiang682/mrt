package au.gov.vic.ecodev.mrt.template.processor.file.validator.common;

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

import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;

public class DrillCodeHeaderValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Depth", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DrillCodeHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> drillCodeList = params.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		assertThat(drillCodeList.get(0), is(equalTo(Dg4ColumnHeaders.DRILL_CODE.getCode())));
	}
	
	@Test
	public void shouldReturnUsingDepthSpaceFromAsDrillCodeWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "Drill code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DrillCodeHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Drill_code column, found Drill code column, use it as Drill_code")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Drill code")));
	}
	
	@Test
	public void shouldReturnUsingDrillHypenCodeAsDrillCodeWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "Drill-code" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DrillCodeHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Drill_code column, found Drill-code column, use it as Drill_code")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Drill-code")));
	}
	
	@Test
	public void shouldReturnUsingDrillCodeAsDrillCodeWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "Drillcode" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DrillCodeHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Drill_code column, found Drillcode column, use it as Drill_code")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.DRILL_CODE.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Drillcode")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String template = "DG4";
		String row = "H1000";
		String code = Dg4ColumnHeaders.DRILL_CODE.getCode();
		//When
		DrillCodeHeaderValidator testInstance = new DrillCodeHeaderValidator(template, row, code);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		String template = null;
		String row = null;
		String code = null;
		//When
		new DrillCodeHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRowIsNull() {
		String template = "DG4";
		String row = null;
		String code = null;
		//When
		new DrillCodeHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		String template = "DG4";
		String row = "H1000";
		String code = null;
		//When
		new DrillCodeHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnMissingDepthFromMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Drill_code1" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new DrillCodeHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.DRILL_CODE.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Drill_code column")));
		assertThat(params.get(Dg4ColumnHeaders.DRILL_CODE.getCode()), is(nullValue()));
	}
}
