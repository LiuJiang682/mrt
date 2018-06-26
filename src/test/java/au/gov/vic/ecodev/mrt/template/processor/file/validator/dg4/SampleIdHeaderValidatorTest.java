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

import au.gov.vic.ecodev.mrt.template.fields.Dg4ColumnHeaders;

public class SampleIdHeaderValidatorTest {

	@Test
	public void shouldReturnNoMessageWithStandardHeader() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Sample ID", "Azimuth_MAG", "Dip", "Survey_instrument" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SampleIdHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(true));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(depthFromList.get(0), is(equalTo(Dg4ColumnHeaders.SAMPLE_ID.getCode())));
	}
	
	@Test
	public void shouldReturnUsingDepthUnderLineToAsDepthToWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Sample_ID", "Azimuth_MAG", "Dip", "Survey_instrument" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SampleIdHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Sample ID column, found Sample_ID column, use it as Sample ID")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Sample_ID")));
	}
	
	@Test
	public void shouldReturnUsingDepthHypenFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "Sample-ID" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SampleIdHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Sample ID column, found Sample-ID column, use it as Sample ID")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(depthFromList.get(0), is(equalTo("Sample-ID")));
	}
	
	@Test
	public void shouldReturnUsingDepthFromAsDepthFromWarningMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id",  "Azimuth_MAG", "Dip", "Survey_instrument", "SampleID" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SampleIdHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), 
				is(equalTo("WARNING: Template DG4 H1000 row requires the Sample ID column, found SampleID column, use it as Sample ID")));
		List<String> depthFromList = params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode());
		assertThat(depthFromList.get(0), is(equalTo("SampleID")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		String template = "DG4";
		String row = "H1000";
		String code = Dg4ColumnHeaders.TO.getCode();
		//When
		SampleIdHeaderValidator testInstance = new SampleIdHeaderValidator(template, row, code);
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenTemplateIsNull() {
		String template = null;
		String row = null;
		String code = null;
		//When
		new SampleIdHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRowIsNull() {
		String template = "DG4";
		String row = null;
		String code = null;
		//When
		new SampleIdHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenCodeIsNull() {
		String template = "DG4";
		String row = "H1000";
		String code = null;
		//When
		new SampleIdHeaderValidator(template, row, code);
		fail("Program reached unexpected point!");
	}
	
	@Test
	public void shouldReturnMissingDepthFromMessage() {
		// Given
		String[] strs = { "H1000", "Hole_id", "Azimuth_MAG", "Dip", "Survey_instrument", "Sample ID1" };
		List<String> errorMessages = new ArrayList<>();
		Map<String, List<String>> params = new HashMap<>();
		// When
		new SampleIdHeaderValidator("DG4", "H1000", Dg4ColumnHeaders.SAMPLE_ID.getCode())
			.validate(errorMessages, params, Arrays.asList(strs));
		// Then
		assertThat(CollectionUtils.isEmpty(errorMessages), is(false));
		assertThat(errorMessages.size(), is(equalTo(1)));
		assertThat(errorMessages.get(0), is(equalTo("ERROR: Template DG4 H1000 row requires the Sample ID column")));
		assertThat(params.get(Dg4ColumnHeaders.SAMPLE_ID.getCode()), is(nullValue()));
	}
}
